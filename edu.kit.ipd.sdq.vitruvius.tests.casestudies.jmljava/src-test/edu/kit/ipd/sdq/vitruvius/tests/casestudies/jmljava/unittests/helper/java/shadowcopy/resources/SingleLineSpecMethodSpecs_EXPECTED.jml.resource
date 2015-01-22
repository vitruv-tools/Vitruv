package a.b.c;

public class SingleLineSpecMethodSpecs {

	//@ requires b == null;
	//@ requires qq123 >= 0;
	//@ ensures \result >= 0;
	int specifiedMethod(int a, String b);
	
}