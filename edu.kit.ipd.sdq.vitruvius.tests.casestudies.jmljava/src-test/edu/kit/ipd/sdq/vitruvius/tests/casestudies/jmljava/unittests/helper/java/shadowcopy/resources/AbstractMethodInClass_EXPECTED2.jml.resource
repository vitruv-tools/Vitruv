package a.b.c;

class AbstractMethodInClass {
	
	//@ requires qq123();
	//@ ensures \result == parameterB;
	private abstract boolean check(boolean parameterB);


	//@ ensures \result;
	private boolean check2();

}