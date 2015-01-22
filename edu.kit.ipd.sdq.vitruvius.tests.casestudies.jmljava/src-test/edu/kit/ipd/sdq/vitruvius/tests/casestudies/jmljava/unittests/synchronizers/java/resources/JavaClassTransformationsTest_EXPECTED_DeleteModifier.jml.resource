class JavaClassTransformationsTest {

	private boolean dummyMethod (char c) throws Exception ;

	/*@
	requires calledMethod();
	requires c != 'A';
	ensures \result;
	model private boolean modelMethod (char c) {
		calledMethod();
		return true;
	}*/
	
	/*@	
	model private boolean otherModelMethod (char c) {
		return false;
	}
	*/
	
	//@ ensures \result;
	private boolean calledMethod ();

	//@ requires modelMethod(c);
	//@ requires a >= 0;
	//@ ensures \result != null;
	public String testMethod (int a, char c);
	
	//@ requires aField != null;
	private void abc (int a);
	
	private String[] aField;
	
	private boolean bField;
		
	/*@
	model int modelField;
	*/
}