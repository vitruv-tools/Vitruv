public class JavaMethodBodyChangesTransformationTest {
	
	public /*@ pure */ boolean pureCalledMethodMarked();
	
	public /*@ pure */ boolean pureMethodMarked();

	public boolean nonPureMethodNotMarked();
	
	public boolean nonPureMethodNotMarked2();
	
	
	
	public /*@ pure */ boolean pureMethodUsedInSpec(); 
	//@ requires pureMethodUsedInSpec();
	public void dummyMethod();
	
	
	public /*@ pure */ boolean pureMethodTransitivelyUsedInSpec();
	public /*@ pure */ boolean pureMethodTransitivelyUsedInSpecIntermediate();
	//@ requires pureMethodTransitivelyUsedInSpecIntermediate();
	public void dummyMethod2();
	
	
	public boolean nonPureMethodCalling();
	public boolean nonPureMethodCalled();
	
	
	
	public boolean field;
	
}