package Mahout;

import java.util.Comparator;

public class TagpesoComparator implements Comparator<Tagpeso> {
	public int compare(Tagpeso arg0, Tagpeso arg1) {
		int peso1=arg0.getPeso();
		int peso2=arg1.getPeso();
			if (peso1>peso2){return -1;}
			else if (peso1<peso2){return 1;}
			else{return 0;}
			}
	}