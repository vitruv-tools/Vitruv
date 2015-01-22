package a.b.c;

class Invariants {

	//@ invariant tryMethod();
	//@ constraint i >= \old(i);
	
	public /*@helper*/ boolean tryMethod() {
		return true;
	}
	
	private int i;

}