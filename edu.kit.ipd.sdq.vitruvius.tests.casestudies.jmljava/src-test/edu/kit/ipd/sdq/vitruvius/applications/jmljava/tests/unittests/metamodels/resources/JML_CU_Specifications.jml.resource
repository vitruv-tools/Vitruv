package a.b.c;

public class Subject {

	//@ invariant calledMethod2();
	
	private boolean dummyMethod (char c);

	/*@
	ensures \result;
	model private boolean modelMethod2 (char c) {
		calledMethod2();
		return true;
	}
	*/
	
	/*@
	 model private boolean otherModelMethod (char c) {
		return false;
	}
	*/
	
	//@ private ensures \result;
	private /*@ helper pure */ boolean calledMethod2 ();

	/*@
	  ensures \result != null;
	*/
	public String testMethod (int a, char c);
	
	//@ invariant otherModelMethod('C');
}