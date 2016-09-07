public class JavaParameterTransformationsTest {

	private boolean dummyMethod (char c) throws Exception ;

	/*@
	requires calledMethod();
	requires c != 'A';
	ensures \result;
	model private boolean modelMethod (char c) {
		calledMethod();
		return true;
	}
	*/
	
	/*@
	model private boolean otherModelMethod (char c) {
		return false;
	}
	*/
	
	//@ ensures \result;
	private boolean calledMethod ();

	//@ requires modelMethod(b);
	//@ requires a >= 0;
	//@ ensures \result != null;
	public String testMethod (int a, char b);
	
	private void abc (int a);
}