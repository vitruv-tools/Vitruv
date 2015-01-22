public class JMLMethodDeclarationTransformationsTest {

	private boolean dummyMethod (char c) throws Exception ;

	/*@
	requires calledMethod();
	requires c != 'A';
	ensures \result;
	model private boolean modelMethod2 (char c) {
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

	//@ requires modelMethod2(c);
	//@ requires a >= 0;
	//@ ensures \result != null;
	public String testMethod (int a, char c);
	
	private void abc (int a);
}