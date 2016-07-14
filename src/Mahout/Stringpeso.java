package Mahout;

public class Stringpeso {
		private String tag;
		private int peso;
		
		public Stringpeso(String t, int peso){
			this.tag=t;
			this.peso=peso;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
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
			if (!(arg0 instanceof Stringpeso)){return false;}
			else
			return this.getTag().equals(((Stringpeso)arg0).getTag());
}
}
