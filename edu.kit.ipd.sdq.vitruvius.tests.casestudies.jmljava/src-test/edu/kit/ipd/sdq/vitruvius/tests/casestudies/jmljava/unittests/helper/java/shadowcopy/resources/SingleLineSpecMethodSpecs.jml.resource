package a.b.c;

public class SingleLineSpecMethodSpecs {

	//@ requires b == null;
	//@ requires a >= 0;
	//@ ensures \result >= 0;
	int specifiedMethod(int a, String b);
	
}