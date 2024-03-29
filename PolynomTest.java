public class PolynomTest {
	
	public static void main(String[] args) {
		//test1();
		//test2();
		//test3();
		test4();
		//test5();
	}
	public static void test1() {
		System.out.println("***** TEST 1 *****");
		Polynom p1 = new Polynom();
		String[] monoms = {"1","x","x^2", "0.5x^2"};
		double aa=0;
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			p1.add(m);
			aa = p1.area(0, 1, 0.0001);
			p1.substract(p1);//we subtract from p1 the same Monom that we add him 2 lines before
			System.out.println(aa);
		}
		System.out.println(p1.toString());
		System.out.println();
		
	}	
	
	public static void test2() {
		System.out.println("***** Test 2 *****");
		Polynom p1 = new Polynom(), p2 =  new Polynom();
		String[] monoms1 = {"2", "-x","-3x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.5x","3x^2","-3","-1.5x^2"};
		
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		System.out.println("p1: "+p1);
		System.out.println("p2: "+p2);
		p1.add(p2);
		System.out.println("p1+p2: "+p1);
		p1.multiply(p2);
		System.out.println("(p1+p2)*p2: "+p1);
		String s1 = p1.toString();
		System.out.println("from string: "+p1);
		System.out.println();
	}
	
	public static void test3(){
		System.out.println("***** TEST 3 *****");
		Polynom poly1 = new Polynom("3.5x^3-1.4x^7+3");//(3.0) + (3.5x^3) + (-1.4^7)
		double area1 = poly1.area(0, 1, 0.0000001);//3.7
		System.out.println(poly1.toString() + " and area is: " + area1);
		
		Polynom poly2 = new Polynom("2.4x^7+2.5x^2-2x");//(-2.0x) + (2.5x^2) + (2.4^7)
		double area2 = poly2.area(0, 1, 0.0000001);//0.3202
		System.out.println(poly2.toString() + " and area is: " + area2);
		
		poly1.add(poly2);
		System.out.println(poly1.toString());//(3.0) + (-2.0x) + (2.5x^2) + (3.5x^3) + (1.0^7)
 	}
	
	public static void test4() {
		System.out.println("***** TEST 4 *****");
		Polynom p1 = new Polynom("2x^3");
		System.out.println(p1.toString());//(2x^3)
		Monom m1 = new Monom("-5x^2");
		p1.add(m1);
		System.out.println(p1.toString());//(-5.0x^2) + (2.0x^3)
		Monom m2 = new Monom("-5x^");//Exception
		p1.add(m2);
	}
	
	public static void test5() {
		System.out.println("***** TEST 5 *****");
		Polynom p1 = new Polynom();
		String[] monoms = {"x^3","-1.341","-5x","x","x-"};
		for(int i = 0; i< monoms.length; i++) {
			Monom m1 = new Monom(monoms[i]);
			p1.add(m1);
			System.out.println(p1.toString());
			// RESULT
			//(1.0x^3)
			//(-1.341) + (1.0x^3)
			//(-1.341) + (-5.0x) + (1.0x^3)
			//(-1.341) + (-4.0x) + (1.0x^3)
			//Exception
		}
	}
	
	public static void test6() {
		
	}
}

