package Mahout;


import de.umass.lastfm.Tag;

public class Tagpeso {
	private Tag tag;
	private int peso;
	
	public Tagpeso(Tag t, int peso){
		this.tag=t;
		this.peso=peso;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	public void addpeso(){
		this.peso++;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0==null){return false;}
		if (arg0==this){return false;}
		if (arg0 instanceof Tag && this.tag.getName().equals(((Tag) arg0).getName())){return true;}
		if (!(arg0 instanceof Tagpeso)){return false;}
		if (!(this.tag.getName().equals(((Tagpeso)arg0).getTag().getName()))){return false;}
		//if (!(this.peso==((Tagpeso)arg0).getPeso())){return false;}
		else return true;
	}


	


}
