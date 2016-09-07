package tools.vitruvius.dsls.mapping.generator

import tools.vitruvius.dsls.mapping.helpers.EMFHelper
import tools.vitruvius.dsls.mapping.helpers.JavaGeneratorHelper.ImportHelper
import java.util.Collections
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EFactory
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.generator.IFileSystemAccess

import static extension tools.vitruvius.dsls.mapping.helpers.JavaGeneratorHelper.*
import static extension tools.vitruvius.framework.util.bridges.JavaHelper.*
import java.util.Set
import org.eclipse.emf.ecore.EDataType

/**
 * Generates a class that can be used in generated code
 * to reference EClasses, EFactories, EPackages and EStrucutralFeatures
 */
class EMFGeneratorHelper {
	private extension MappingLanguageGeneratorNameProvider nameProvider
	
	private Map<EPackage, List<Object>> referencedEMFEntities

	new(MappingLanguageGeneratorNameProvider nameProvider) {
		this.referencedEMFEntities = newHashMap
		this.nameProvider = nameProvider
	}
	
	private def String classFQN() {
		nameProvider.constantsClassName
	}
	
	public def String ePackageInstance(extension ImportHelper ih, EPackage ePackage) {
		addEntity(ePackage, ePackage)
		'''«typeRef(classFQN)».«ePackage.constantClassName».«ePackage.fieldName»'''
	}

	public def String eRef(extension ImportHelper ih, EClassifier eClassifier) {
		addEntity(eClassifier.EPackage, eClassifier)

		'''«typeRef(classFQN)».«eClassifier.EPackage.constantClassName».«eClassifier.fieldName»'''
	}

	public def String eRef(extension ImportHelper ih, EStructuralFeature feature) {
		val eClass = feature.EContainingClass
		addEntity(eClass.EPackage, eClass)
		addEntity(eClass.EPackage, feature)

		'''«typeRef(classFQN)».«eClass.EPackage.constantClassName».«feature.fieldName»'''
	}
	
	
	public static def String inferType(extension ImportHelper ih, EStructuralFeature feature) {
		if (feature.many) {
				if (feature.ordered)
					'''«typeRef(List)»<«typeRef(feature.EType)»>'''
				else
					'''«typeRef(Set)»<«typeRef(feature.EType)»>'''
		} else
			typeRef(feature.EType)
	}
	
	public static def String getterName(EStructuralFeature feature) {
		// TODO DW "is" for boolean attributes
		'''get«feature.name.toFirstUpper»'''
	}
	
	public static def String setterName(EStructuralFeature feature) {
		'''set«feature.name.toFirstUpper»'''
	}
	
	public def String eRefGet(extension ImportHelper ih, String instance, EStructuralFeature feature) {
		// TODO DW: this method currently assumes that the getter name for a feature X is getX
		// and isX for boolean features. This should be tested more thoroughly using reflection.
/*		try {
			val c = feature.EContainingClass.instanceTypeName
			val containingClass = (Class.forName(feature.EContainingClass.instanceTypeName))
			val getterMethods = containingClass.methods.filter[name.equals(feature.getterName) && (parameters.size == 0)]
			if (getterMethods.size == 1) {
				return '''«instance».«feature.getterName»()'''
			}
		} catch (ClassNotFoundException e) {
			// fall back to normal case
		}*/
		
		'''«instance».«feature.getterName»()'''
		
		//'''((«inferType(ih, feature)») «instance».eGet(«eRef(ih, feature)»))'''
	}
	
	
	public def String eRefSet(extension ImportHelper ih, String instance, EStructuralFeature feature, String newValue) {
		// TODO DW: this method currently assumes that the setter name for a feature X is setX
		// This should be tested more thoroughly using reflection.
/*		try {
			val containingClass = (Class.forName(feature.EContainingClass.instanceTypeName))
			val setterMethods = containingClass.methods.filter[name.equals(feature.setterName) && (parameters.size == 1)]
			if (setterMethods.size == 1) {
				return '''«instance».«feature.setterName»(«newValue»)'''
			}
		} catch (ClassNotFoundException e) {
			// fall back to normal case
		}*/
		
		'''«instance».«feature.setterName»(«newValue»)'''
		
//		'''«instance».eSet(«eRef(ih, feature)», «newValue»)'''
	}
	
	public def String eRefAdd(extension ImportHelper ih, String instance, EStructuralFeature ref, String newValue) {
		if (!ref.many)
			return '''/* add of "«newValue.toString»" to feature «ref.name» which is not many */'''
			
		'''«eRefGet(ih, instance, ref)».add(«newValue»)'''
	}
	
	public def String eSetOrAdd(extension ImportHelper ih, String instance, EStructuralFeature ref, String newValue) {
		if (ref.many)
			eRefAdd(ih, instance, ref, newValue)
		else
			eRefSet(ih, instance, ref, newValue)
	}

	public def String eCreate(extension ImportHelper ih, EClass eClass) {
		addEntity(eClass.EPackage, eClass)
		
		'''«typeRef(classFQN)».«eClass.EPackage.constantClassName».create«eClass.name.toFirstUpper»()'''
	}
	
	public def String eContainsOrIsEqual(extension ImportHelper ih, String instance, EStructuralFeature feature, String otherValue) {
		if (feature.many)
			'''«eRefGet(ih, instance, feature)».contains(«otherValue»)'''
		else if (feature.EType instanceof EDataType && (feature.EType as EDataType).instanceTypeName.equals("java.lang.String"))
			'''«eRefGet(ih, instance, feature)».equals(«otherValue»)'''
		else
			'''(«eRefGet(ih, instance, feature)» == «otherValue»)'''
	}
	
	

	def void generateCode(IFileSystemAccess fsa) {
		fsa.generateJavaFile(classFQN, [ ih |
			'''
			public final class «classFQN.toSimpleName» {
				«FOR pkg : referencedEMFEntities.keySet»
«««					// EPackage «pkg.commentString»
					public static class «pkg.constantClassName» {
						«FOR it : getFromMultimap(pkg)»
							«ih.createERef(it)»
							
						«ENDFOR»
					}
					
				«ENDFOR»
			}
		'''
		])
	}

	// TODO: Multimap Class!
	private def void addEntity(EPackage pkg, Object obj) {
		if (!referencedEMFEntities.containsKey(pkg)) {
			referencedEMFEntities.put(pkg, newArrayList)
			addEntity(pkg, pkg)
			addEntity(pkg, pkg.EFactoryInstance)
		}
			
		if (!referencedEMFEntities.get(pkg).contains(obj))
			referencedEMFEntities.get(pkg).add(obj)
	}

	private def List<Object> getFromMultimap(EPackage pkg) {
		referencedEMFEntities.get(pkg).immutableCopy ?: Collections.emptyList
	}

	def private constantClassName(EPackage pkg) {
		pkg.name.toFirstUpper
	}

	def dispatch private CharSequence fieldName(EPackage pkg) {
		"_PACKAGE"
	}

	def dispatch private CharSequence fieldName(EFactory factory) {
		"_FACTORY"
	}

	def dispatch private CharSequence fieldName(EClass eClass) {
		eClass.name.toUpperCase + "_ECLASS"
	}

	def dispatch private CharSequence fieldName(EStructuralFeature feature) {
		val containerClass = feature.EContainingClass

		'''«containerClass.name.toUpperCase»__«feature.name.toUpperCase»'''
	}

	def dispatch private createERef(extension ImportHelper ih, Object obj) '''
		// no static constant created for «obj.toString»
	'''

	def dispatch private createERef(extension ImportHelper ih, EPackage pkg) '''
		public static final «typeRef(EPackage)» «pkg.fieldName» =
			«EMFHelper.getJavaExpressionThatReturns(pkg, false)»;
	'''

	def dispatch private createERef(extension ImportHelper ih, EFactory factory) '''
		public static final «typeRef(EFactory)» «factory.fieldName» =
			«factory.EPackage.fieldName».getEFactoryInstance();
	'''

	def dispatch private createERef(extension ImportHelper ih, EClass eClass) {
		val classifierID = eClass.classifierID
		val pkg = eClass.EPackage
		val factory = pkg.EFactoryInstance

		'''
			public static final «typeRef(EClass)» «eClass.fieldName» =
				(«typeRef(EClass)») «pkg.fieldName».getEClassifiers().get(«classifierID»);
				
			public static «typeRef(eClass)» create«eClass.name.toFirstUpper»() {
				return («typeRef(eClass)») («factory.fieldName».create(«eClass.fieldName»));
			}
		'''
	}

	def dispatch private createERef(extension ImportHelper ih, EAttribute feature) {
		val featureID = feature.featureID
		val containerClass = feature.EContainingClass

		'''
			public static final «EAttribute.simpleName» «feature.fieldName» =
				(«typeRef(EAttribute)») «containerClass.fieldName».getEStructuralFeature(«featureID»);
		'''
	}

	def dispatch private createERef(extension ImportHelper ih, EReference feature) {
		val featureID = feature.featureID
		val containerClass = feature.EContainingClass

		'''
			public static final «EReference.simpleName» «feature.fieldName» =
				(«typeRef(EReference)») «containerClass.fieldName».getEStructuralFeature(«featureID»);
		'''
	}
}