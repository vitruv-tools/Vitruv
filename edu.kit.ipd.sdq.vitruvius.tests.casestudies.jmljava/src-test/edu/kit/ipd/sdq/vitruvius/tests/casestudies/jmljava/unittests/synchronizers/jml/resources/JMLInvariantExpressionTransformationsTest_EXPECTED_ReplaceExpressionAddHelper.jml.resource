public class JMLInvariantExpressionTransformationsTest {

	//@ invariant calledMethod();
	//@ invariant calledMethod3();
	//@ invariant 3 == 3 && calledMethod2();
	
	public /*@ helper */ boolean calledMethod();
	
	public /*@ helper */ boolean calledMethod2();
	
	public /*@ helper */ boolean calledMethod3();
	
}