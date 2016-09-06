package a.b.c;

class AbstractMethodInClass {
	
	//@ requires check2();
	//@ ensures \result == qq123;
	private abstract boolean check(boolean parameterB);


	//@ ensures \result;
	private boolean check2();

}