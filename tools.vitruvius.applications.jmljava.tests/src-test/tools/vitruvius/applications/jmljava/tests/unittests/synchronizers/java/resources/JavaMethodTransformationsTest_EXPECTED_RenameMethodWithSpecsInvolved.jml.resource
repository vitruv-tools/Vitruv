public class JavaMethodTransformationsTest {

	private boolean dummyMethod (char c) throws Exception;

	/*@
	requires methodNameChanged();
	requires c != 'A';
	ensures \result;
	model private boolean modelMethod (char c) {
		methodNameChanged();
		return true;
	}
	*/
	
	/*@
	model private boolean otherModelMethod (char c) {
		return false;
	}
	*/
	
	//@ ensures \result;
	private boolean methodNameChanged ();

	//@ requires modelMethod(c);
	//@ requires a >= 0;
	//@ ensures \result != null;
	public String testMethod (int a, char c);
	
	private void abc (int a);
}