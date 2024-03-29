import java.util.ArrayList;
import java.util.Stack;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;


/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 * @param <T>
 *
 */

public class Polynom<T> implements Polynom_able{
	
	private ArrayList<Monom> monoms;
	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		monoms=new ArrayList();
		
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		monoms= new ArrayList();
		String monom = "";
		
		for(int i = 0; i < s.length(); i++) {
			
			if(s.length()-1 == i) { 
				monom+=s.charAt(i);
				Monom m = new Monom(monom);
				add(m);
			}
			
			else if(s.charAt(i)>47 && s.charAt(i)<58)
				monom+=s.charAt(i);
		
			else if(s.charAt(i)=='x'||s.charAt(i)=='.'||s.charAt(i)=='^')
				monom+=s.charAt(i);
			
			else if(s.charAt(i)=='+') {
				Monom m = new Monom(monom);
				add(m);
				monom = "";
			}
			
			else if(s.charAt(i)=='-') {
				if(monom.equals("")) {
					monom="-";
				}
				
				else {
					Monom m = new Monom(monom);
					add(m);
					monom = "-";
				}
			}
			
			else {
				throw new IllegalArgumentException();
			}
		}
		Comparator<Monom> com = new Monom_Comperator();
		monoms.sort(com);
	}	
	/**
	 * this method return the value of a function for the value x
	 * @param x value of the x axis
	 */
	@Override
	public double f(double x) {
		double function = 0;
		Iterator<Monom> iter = iteretor(); 
		while(iter.hasNext()) {
			Monom m = iter.next();
			function = function + m.get_coefficient()*Math.pow(x, m.get_power());
		}
		return function;
	}
	/**
	 * add 2 polynoms by we send every monom of p1 to the method add between monom and polynom 
	 * @param p1 a polynom that add with this polynom
	 */
	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> iter = p1.iteretor();  
		while(iter.hasNext()) {
			add(iter.next());
		}
	}

	/**
	 * adding for this polynom monom m1
	 * @param m1 the monom we add to this polynom
	 */
	@Override
	public void add(Monom m1){
		
		if(m1 == null) {
			throw new IllegalArgumentException("monom is a null");
		}
		
		Iterator<Monom> iter = iteretor(); 
		Comparator<Monom> com = new Monom_Comperator();
		boolean foundPower=false;		
		int place = 0;
		Monom next;
		
		if(m1.isZero())//if m1==0 it dosn't change the polynom so we and function here
			return;
		
		if(monoms.isEmpty()) {//if the polynom f(x)=0, just add m1
			monoms.add(m1);
			foundPower = true;
		}
			
		while(iter.hasNext() && !foundPower) {// as long we didn't get to the end of a polynom 
											  //and we not found monom with equal power of m1 in this polynom 
			next = iter.next();
				
			if(com.compare(next,m1)==0) {//next.power==m1.power we sum the coefficient
				next.add(m1);
				foundPower = true;

			}	
			
			else if(com.compare(next,m1)>0) {//next.power>m1.power, when we will find the place for m1 we add him between 2 monoms
				monoms.add(place, m1);
				foundPower = true;
			}
			
			else {//next.power<m1.power
				place++;
			}
		}
		
		if(!foundPower)// adding in the end of the polynom
			monoms.add(place, m1);
	}	
	
	/**
	 * substract polynoms by multiply every monom in p1 by -1 and addthe new monom to this polynom
	 * @param p1 the polynom we substract from this polynom 
	 */
	@Override
	public void substract(Polynom_able p1) {
		Iterator<Monom> iter = p1.iteretor();  
		int power = 0;
		double coefficiant = 0;
		Monom toSubstract;
		
		while(iter.hasNext()) {// multiply every monom in p1 by -1 and "add" this for this polynom
			
			toSubstract = iter.next();
			power = toSubstract.get_power();
			coefficiant = toSubstract.get_coefficient();
			toSubstract = new Monom(-1*coefficiant, power);
			add(toSubstract);
		}	
	}

	/**
	 * multiply between 2 polynoms,  first create array of polynoms in the size of p1,
	 * for every copy polynom in that array multiply by monom from p1, 
	 * and in the end summarize of these polynoms in the array and we got multiplication of 2 polynoms 
	 * @param p1 a polynom we want to multiply him by this 
	 */
	@Override
	public void multiply(Polynom_able p1) {
		Iterator<Monom> iterP1 = p1.iteretor();
		int p1Size = sizeOfPolynomAble(p1);// private method that return the size of p1
		int place = 0;
		Polynom_able[] tmp = new Polynom[p1Size];
		
		for(int i=0; i<tmp.length; i++) {//copy this for every cell in tmp
			tmp[i] = copy();
		}
		while(iterP1.hasNext()) {//multiply every polynom by monom from p1
			Monom next = iterP1.next();
			tmp[place].multiply(next);
			place++;
		}
		
		for(int i=1; i<tmp.length; i++) {//summarize the polynoms
			tmp[0].add(tmp[i]);
		}
		
		while(monoms.size()>0)//Delete the current polynom and replace is by tmp[0] the new multiply polynom
			monoms.remove(0);
		
	
		Iterator<Monom> iterTmp = tmp[0].iteretor();
		while(iterTmp.hasNext()) {
			monoms.add(iterTmp.next());
		}
		
	}
	/**
	 * multiply polynom by monom
	 * @param m1 the monom we multiply by this polynom
	 */
	@Override
	public void multiply(Monom m1) {
		Iterator<Monom> iter = iteretor(); 		
		while(iter.hasNext()){
			Monom tmp = iter.next();
			tmp.multipy(m1);		
		}	
	}
	/**
	 * check how many monoms there is for polynom_able
	 * @param p1
	 * @return the number of monoms in p1
	 */
	
	private int sizeOfPolynomAble(Polynom_able p1) {
		Iterator<Monom> iter = p1.iteretor();
		int size = 0;
		
		while(iter.hasNext()) {
			size++;
			iter.next();
		}
		
		return size;
	}
	/**
	 * check if this == p1 by the number of monoms in 2 of them and by there value
	 */
	@Override
	public boolean equals(Polynom_able p1) {
		Monom_Comperator comparePolynom = new Monom_Comperator();
		Iterator<Monom> iter1 = iteretor();
		Iterator<Monom> iter2 = p1.iteretor();
		boolean nonEqualMonom = false;
		while(iter1.hasNext() && iter2.hasNext() && !nonEqualMonom){
			Monom m1 = iter1.next();
			Monom m2 = iter2.next();
			
			if((iter1.hasNext() && !iter2.hasNext())||(!iter1.hasNext() && iter2.hasNext()))//if there isn't has next for one of the polynoms
				return false;
			
			else if(!(comparePolynom.compare(m1, m2)==0 && comparePolynom.compareCoefficient(m1, m2)==0))//there's Difference between by the power or coefficient 
				nonEqualMonom = true;
		}
		
		return !nonEqualMonom;
		
		
		
	}
	/**
	 * check if f(x) = 0
	 * @return true for f(x)==0, false for f(x)!=0
	 */
	@Override
	public boolean isZero() {
		if(monoms.size()==0)
			return true;
		
		else 
			return false;
	}
	
	/**
	 * finding approximately x2 so f(x2)==0, while x2 is:  x0<=x2<=x1 (1 , f(x2)<eps (2
	 * @param x0,x1 is part on the x axis 
	 */
	@Override
	public double root(double x0, double x1, double eps) {
		if(f(x0)*f(x1)>0)
			throw new IllegalArgumentException("f(x0)*f(x1)>0 the elements not correct");
		
		if(x0>x1)
			throw new IllegalArgumentException("x0>x1");
			
		double middle=(x0+x1)/2;
		boolean foundRoot=false;
		
		while(!foundRoot){//using binary search to find where f(x)=0
			middle = (x0+x1)/2;
			double fx0 = f(x0), fx1 = f(x1), fMiddle = f(middle);
			
			if(fMiddle < eps) 
				foundRoot = true;
				
			else if(fx0*fMiddle<0)
				fx1=fMiddle;
			
			else
				fx0=fMiddle; 
		}
		return middle;
	}	
	/**
	 * copy constructor
	 * 
	 * @return copy polynom of this 
	 */
	
	@Override
	public Polynom_able copy() {//copy constructor
		Polynom_able copyPolynom = new Polynom(); 
		Iterator<Monom> iter = iteretor(); 
		while(iter.hasNext()) {
			Monom next = iter.next();
			double a = next.get_coefficient();
			int b = next.get_power();
			Monom add = new Monom(a,b);
			copyPolynom.add(add);
		}
		
		return copyPolynom;
	}
	
	/**
	 * moving on every monom in this polynom and derivative the minom
	 * @return the derivative polynom
	 */
	@Override
	public Polynom_able derivative() {
		Iterator<Monom> iter = iteretor(); 		
		Polynom_able derPoly = new Polynom();
		
		while(iter.hasNext()){
			Monom next = iter.next();
			derPoly.add(next.derivative());
		}
		return derPoly;
	}
	
	/**
	 * calculating approximately the area between x0, x1 and over x axis using reimann integral 
	 * @param x0, x1 range of x axis we will calculate the f(x), 
	 * @param eps value of progressing of advancing on the x axis 
	 * @return double the area that trap between the function and the x axis 
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		double areaSum = 0;
		double fx0, fx1 = f(x1);
		double rectangleSum = 0;
		
		if(x0>x1)
			 throw new IllegalArgumentException("not valid elements");
		
		else if(x0==x1)
			return 0;
		
		while(x0<x1) {// using reimann integral to calculate the area			
			rectangleSum = f(x0)*eps; 
			if(rectangleSum>0)
				areaSum+=rectangleSum;
			
			x0+=eps;
		}
		
		return areaSum;
	}
	/**
	 * @return the iterator of monoms array list
	 */
	@Override
	public Iterator<Monom> iteretor() {
		return monoms.iterator();
	}
	
	/**
	 * @return String of this polynom 
	 */
	public String toString() {
		Iterator<Monom> iter = iteretor(); 		
		String output="";
		int place=0;
		
		if(monoms.size()-1==0)//if there is only one element
			output+="("+iter.next().toString()+")";
		
		if(iter.hasNext())//
			output+= "("+iter.next().toString()+") + ";
		
		if(place == monoms.size()-2)//before the last element 
			output+="("+iter.next().toString()+")";
		
		while(iter.hasNext()) { 
			output+= "("+iter.next().toString()+") + ";
			place++;
			if(place == monoms.size()-2)//before the last element 
				output+="("+iter.next().toString()+")";
		}
		
		return output;
	}
}