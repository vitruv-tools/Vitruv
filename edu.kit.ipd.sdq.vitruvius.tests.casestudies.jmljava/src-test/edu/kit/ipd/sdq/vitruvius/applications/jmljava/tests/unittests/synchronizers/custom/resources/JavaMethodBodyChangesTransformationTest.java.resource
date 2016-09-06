public class JavaMethodBodyChangesTransformationTest {

	public boolean pureCalledMethodMarked() {
		return true;
	}

	public boolean pureMethodMarked() {
		return pureCalledMethodMarked();
	}

	public boolean nonPureMethodNotMarked() {
		field = true;
		pureCalledMethodMarked();
		return field;
	}
	
	public boolean nonPureMethodNotMarked2() {
		nonPureMethodNotMarked();
		return true;
	}
	
	public boolean pureMethodUsedInSpec() {
		return true;
	}
	public void dummyMethod() {};
	
	
	public boolean pureMethodTransitivelyUsedInSpec() {
		return true;
	}
	public boolean pureMethodTransitivelyUsedInSpecIntermediate() {
		return pureMethodTransitivelyUsedInSpec();
	}
	public void dummyMethod2() {};
	
	
	public boolean nonPureMethodCalling() {
		return nonPureMethodCalled();
	}
	public boolean nonPureMethodCalled() {
		field = true;
		return field;
	}

	
	public boolean field;
}