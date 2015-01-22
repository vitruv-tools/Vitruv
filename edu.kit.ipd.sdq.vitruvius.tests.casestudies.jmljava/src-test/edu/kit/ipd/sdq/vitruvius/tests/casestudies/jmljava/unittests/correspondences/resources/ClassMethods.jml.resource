class ClassMethods {
	
	public int regularMethod(int a, final String b) throws Exception;
	
	//@ ensures \result;
	public boolean specifiedMethod();
	
	/*@
		requires a;
	*/
	public void specifiedMethod2(boolean a);

}