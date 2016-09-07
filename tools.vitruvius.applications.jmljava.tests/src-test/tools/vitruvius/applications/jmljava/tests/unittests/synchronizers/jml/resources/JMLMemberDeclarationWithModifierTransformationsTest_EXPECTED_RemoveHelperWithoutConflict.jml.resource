public abstract class JMLMemberDeclarationWithModifierTransformationsTest {

	//@ invariant helperMethodMarked();
	
	public /*@ helper */ boolean helperMethodMarked();
	
	public boolean notNecessaryHelperMethodMarked();

	public boolean helperMethodNotMarked();
	
	
	public /*@ pure */ boolean pureCalledMethodMarked();
	
	public /*@ pure */ boolean pureMethodMarked();
	
	public boolean pureMethodNotMarked();

	public boolean nonPureMethodNotMarked();
	
	public boolean nonPureMethodNotMarked2();
	
	//@ requires pureCalledMethodMarked();
	public void dummy();
	
	public void pureMethodNotMarkedCalledInMethod();
	public void pureMethodNotMarkedCallingMethod();
	
	public boolean field;
	
	
	
	public JMLMemberDeclarationWithModifierTransformationsTest get();
	
	public /*@ pure */ boolean pureMethodUsedComplex();
	
	//@ ensures \result == get().pureMethodUsedComplex();
	public abstract boolean dummy2();
	
}