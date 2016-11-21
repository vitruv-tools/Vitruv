package tool.vitruv.applications.umljava.uml2java

import tool.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import org.junit.Test
import org.eclipse.uml2.uml.UMLFactory
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*;

class UmlToJavaClassTest extends AbstractUmlJavaTest {
	private static val CLASS_NAME = "UmlClass";

	@Test
	public def void testCreateClass() {
		val umlClass = UMLFactory.eINSTANCE.createClass();
		umlClass.name = CLASS_NAME;
		rootElement.packagedElements += umlClass;
		saveAndSynchronizeChanges(umlClass);
		assertModelExists(buildJavaFilePath(CLASS_NAME));
	}
}