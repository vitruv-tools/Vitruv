public class JMLInvariantExpressionTransformationsTest {

	//@ invariant calledMethod2();
	//@ invariant calledMethod2();
	//@ invariant 3 == 3 && calledMethod2();
	
	public boolean calledMethod();
	
	public /*@ helper */ boolean calledMethod2();
	
	public boolean calledMethod3();
	
}