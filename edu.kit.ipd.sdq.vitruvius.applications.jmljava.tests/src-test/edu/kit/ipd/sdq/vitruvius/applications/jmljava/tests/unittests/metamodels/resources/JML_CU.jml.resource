package a.b.c;

import x.y.z.OtherClass;
import x.y.z.*;
import static x.y.z.AnotherClass.*;
import static x.y.z.AnotherClass.abc;

public class ExampleClass {

	private String testField;
	public String someField, otherField;

	public String testMethod();
	public String testMethod(int qq);
	public void testMethod2() throws RuntimeException;
	public void testMethod2(int a);
	public void testMethod2(int... a);

}