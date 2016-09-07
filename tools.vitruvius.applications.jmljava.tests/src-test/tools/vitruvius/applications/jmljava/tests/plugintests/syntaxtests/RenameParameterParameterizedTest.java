package tools.vitruvius.applications.jmljava.tests.plugintests.syntaxtests;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

public class RenameParameterParameterizedTest extends
		FrameworkTestParameterizedBase {

	@Parameters(name = "{index}: {1}.{2} with {3} -> {4}")
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

						for (ILocalVariable parameter : method.getParameters()) {
							result.add(new String[] {cu.getElementName(), type.getElementName(), method.getElementName(), parameterTypes.toString(), parameter.getElementName()});
						}
					}
				}
				return result;
			}
		};
		return data(testParameterConstructor);
	}
	
	private final String compilationUnitName;
	private final String typeName;
	private final String methodName;
	private final String parameterTypes;
	private final String parameterName;
	
	public RenameParameterParameterizedTest(String compilationUnitName,
			String typeName, String methodName, String parameterTypes,
			String parameterName) {
		super();
		this.compilationUnitName = compilationUnitName;
		this.typeName = typeName;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.parameterName = parameterName;
	}

	@Override
	protected Object[] getCurrentParameters() {
		return new Object[] { compilationUnitName, typeName, methodName,
				parameterTypes, parameterName };
	}
	
	@Ignore
	@Test
	public void testRenameMethod() throws Exception {
		ILocalVariable parameterToRename = codeElementUtil.getParameter(compilationUnitName, typeName, methodName, parameterTypes, parameterName);
		assertNotNull(parameterToRename);
		editorManipulator.renameParameter(parameterToRename);
		waitForSynchronisationToFinish();
		this.checkSyntaxAndCompareDiffIfAvailable(new Object(){}.getClass().getEnclosingMethod().getName());
	}

}
