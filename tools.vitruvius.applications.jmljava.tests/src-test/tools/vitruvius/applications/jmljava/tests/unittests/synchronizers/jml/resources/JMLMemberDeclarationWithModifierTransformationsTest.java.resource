public abstract class JMLMemberDeclarationWithModifierTransformationsTest {

	public boolean helperMethodMarked() {
		return true;
	}
	
	public boolean notNecessaryHelperMethodMarked() {
		return true;
	}

	public boolean helperMethodNotMarked() {
		return true;
	}


	public boolean pureCalledMethodMarked() {
		return true;
	}

	public boolean pureMethodMarked() {
		return pureCalledMethodMarked();
	}

	public boolean pureMethodNotMarked() {
		return true;
	}

	public boolean nonPureMethodNotMarked() {
		field = true;
		return field;
	}
	
	public boolean nonPureMethodNotMarked2() {
		return nonPureMethodNotMarked();
	}

	public void dummy() {
	}
	
	public void pureMethodNotMarkedCalledInMethod() {
	}
	public void pureMethodNotMarkedCallingMethod() {
		pureMethodNotMarkedCalledInMethod();
	}
	
	public boolean field;
	
	
	
	public JMLMemberDeclarationWithModifierTransformationsTest get() {
		return this;
	}
	
	public /*@ pure */ boolean pureMethodUsedComplex() {
		return true;
	}
	
	//@ ensures \result == get().pureMethodUsedComplex();
	public abstract boolean dummy2();
}