public class JMLInvariantExpressionTransformationsTest {

	//@ invariant calledMethod();
	//@ invariant calledMethod2();
	//@ invariant 3 == 3 && calledMethod();
	
	public /*@ helper */ boolean calledMethod();
	
	public /*@ helper */ boolean calledMethod2();
	
	public boolean calledMethod3();
	
}