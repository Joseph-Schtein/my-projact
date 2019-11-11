

import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom> {

	public Monom_Comperator() {;}
	
	public int compare(Monom o1, Monom o2) {
		int dp = o1.get_power() - o2.get_power();
		return dp;
	}
	// ******** add your code below *********
	public int compareCoefficient(Monom m1, Monom m2) {// I add this method  before boaz say we don't need th write it so it stay
		if(m1.get_coefficient()>m2.get_coefficient())
			return 1;
		
		else if(m1.get_coefficient()<m2.get_coefficient())
			return -1;
		
		else
			return 0;
	}
	
}