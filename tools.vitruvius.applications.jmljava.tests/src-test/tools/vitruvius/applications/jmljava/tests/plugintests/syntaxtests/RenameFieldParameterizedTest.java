package tools.vitruvius.applications.jmljava.tests.plugintests.syntaxtests;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

public class RenameFieldParameterizedTest extends
		FrameworkTestParameterizedBase {

	@Parameters(name = "{index}: {1}.{2}")
	public static Collection<Object[]> data() throws Exception {
		TestParameterConstructor testParameterConstructor = new TestParameterConstructor() {
			@Override
			public Collection<Object[]> create(ICompilationUnit cu) throws Exception {
				Collection<Object[]> result = new Vector<Object[]>();
				for (IType type : cu.getAllTypes()) {
					if (!type.isClass()) {
						continue;
					}
					for (IField field : type.getFields()) {
						result.add(new String[] {cu.getElementName(), type.getElementName(), field.getElementName()});						
					}
				}
				return result;
			}
		};
		return data(testParameterConstructor);
	}
	
	private final String cuName;
	private final String typeName;
	private final String fieldName;
	
	public RenameFieldParameterizedTest(String cuName, String typeName, String fieldName) {
		this.cuName = cuName;
		this.typeName = typeName;
		this.fieldName = fieldName;
	}

	@Override
	protected Object[] getCurrentParameters() {
		return new Object[] {cuName, typeName, fieldName};
	}
	
	@Ignore
	@Test
	public void testRenameField() throws Exception {
		IField fieldToRename = codeElementUtil.getField(cuName, typeName, fieldName);
		assertNotNull(fieldToRename);
		editorManipulator.renameField(fieldToRename);
		waitForSynchronisationToFinish();
		this.checkSyntaxAndCompareDiffIfAvailable(new Object(){}.getClass().getEnclosingMethod().getName());
	}

}
