package tools.vitruvius.domains.jml.language.postprocessing

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.impl.EcorePackageImpl
import org.eclipse.xtext.GeneratedMetamodel
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor

/**
 * Post processor for JML meta model. The following actions are performed:
 * <ul>
 * <li>A method to transform the model element to its concrete syntax representation is added.</li>
 * </ul>
 * 
 * @see http://jevopisdeveloperblog.blogspot.de/2011/03/implement-tostring-with-xtexts.html
 */
class JMLPostProcessor implements IXtext2EcorePostProcessor {

	private static val ECORE_PACKAGE = EcorePackageImpl.init

	override process(GeneratedMetamodel metamodel) {
		//metamodel.EPackage.EClassifiers.filter(EClass).forEach[process]
		metamodel.EPackage.EClassifiers.filter(EClass).filter[name.equals("CompilationUnit")].forEach[addNamePropertyForCU]
	}

	private def createBodyAnnotation(String body) {
		val eannotation = EcoreFactory.eINSTANCE.createEAnnotation
		eannotation.source = "http://www.eclipse.org/emf/2002/GenModel"
		eannotation.details.put("body", body)
		return eannotation
	}

	private def addNamePropertyForCU(EClass c) {
		val eoperation = EcoreFactory.eINSTANCE.createEOperation
		eoperation.name = "getName"
		eoperation.EType = ECORE_PACKAGE.EString
		eoperation.EAnnotations.add(createBodyAnnotation(getNamePropertyForCUBodyString().toString))
		c.EOperations.add(eoperation)
	}

	private def getNamePropertyForCUBodyString() '''
	if (getTypedeclaration().size() > 0) {
		<%org.eclipse.emf.ecore.EStructuralFeature%> feature = getTypedeclaration().get(0).getClassOrInterfaceDeclaration().eClass().getEStructuralFeature("identifier");
		String typeName = (String)getTypedeclaration().get(0).getClassOrInterfaceDeclaration().eGet(feature);
		String pkgName = "";
		if (getPackagedeclaration() != null) {
			pkgName = getPackagedeclaration().getQualifiedname() + ".";
		}
		return pkgName + typeName;
	}
	throw new <%java.lang.IllegalStateException%>("There has to be at least one type in the compilation unit in order to construct its name.");'''
}
