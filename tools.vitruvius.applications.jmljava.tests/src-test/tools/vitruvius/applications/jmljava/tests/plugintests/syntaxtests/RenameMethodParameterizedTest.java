package tools.vitruvius.applications.jmljava.tests.plugintests.syntaxtests;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

public class RenameMethodParameterizedTest extends FrameworkTestParameterizedBase {

	@Parameters(name = "{index}: {1}.{2} with {3}")
	public static Collection<Object[]> data() throws Exception {
		TestParameterConstructor testParameterConstructor = new TestParameterConstructor() {
			@Override
			public Collection<Object[]> create(ICompilationUnit cu) throws Exception {
				Collection<Object[]> result = new Vector<Object[]>();
				for (IType type : cu.getAllTypes()) {
					if (!type.isClass()) {
						continue;
					}
					for (IMethod method : type.getMethods()) {
						if (method.isConstructor()) {
							continue;
						}
						StringBuilder parameterTypes = new StringBuilder();
						for (String parameterType : method.getParameterTypes()) {
							parameterTypes.append(Signature.toString(parameterType));
						}

						result.add(new String[] {cu.getElementName(), type.getElementName(), method.getElementName(), parameterTypes.toString()});
					}
				}
				return result;
			}
		};
		return data(testParameterConstructor);
	}
	
	private final String cuName;
	private final String typeName;
	private final String methodName;
	private final String parameterTypes;
	
	public RenameMethodParameterizedTest(String cuName, String typeName, String methodName, String parameterTypes) {
		this.cuName = cuName;
		this.typeName = typeName;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
	}

	@Override
	protected Object[] getCurrentParameters() {
		return new Object[] {cuName, typeName, methodName, parameterTypes};
	}
	
	@Ignore
	@Test
	public void testRenameMethod() throws Exception {
		IMethod methodToRename = codeElementUtil.getMethod(cuName, typeName, methodName, parameterTypes);
		assertNotNull(methodToRename);
		editorManipulator.renameMethod(methodToRename);
		waitForSynchronisationToFinish();
		this.checkSyntaxAndCompareDiffIfAvailable(new Object(){}.getClass().getEnclosingMethod().getName());
	}

}
