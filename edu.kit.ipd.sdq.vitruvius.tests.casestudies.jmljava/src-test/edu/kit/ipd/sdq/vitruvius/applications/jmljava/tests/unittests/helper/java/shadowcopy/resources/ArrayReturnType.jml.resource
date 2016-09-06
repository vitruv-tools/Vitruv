package a.b.c;

class ArrayReturnType {

	//@ requires a != null;
	//@ ensures \result == a;
	public String[] test(String[] a);

}