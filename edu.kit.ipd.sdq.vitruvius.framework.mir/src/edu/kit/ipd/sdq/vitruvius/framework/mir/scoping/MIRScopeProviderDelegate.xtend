package edu.kit.ipd.sdq.vitruvius.framework.mir.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.mwe2.language.scoping.QualifiedNameProvider
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.xbase.scoping.XImportSectionNamespaceScopeProvider
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.BaseMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass

class MIRScopeProviderDelegate extends XImportSectionNamespaceScopeProvider {
	@Inject
	QualifiedNameProvider qualifiedNameProvider;
	
	override getScope(EObject context, EReference reference) {
		if (reference.getEType.equals(EcorePackage.eINSTANCE.getEClass))
			return getEClassScope(context)
		else if (reference.getEType.equals(EcorePackage.eINSTANCE.getEStructuralFeature))
			return getEFeatureScope(context)
		else if ((context instanceof NamedFeature)
			&& (reference.equals(MIRPackage.eINSTANCE.namedFeature_ContainingNamedEClass)))
			return getContainingNamedEClassScope(context as NamedFeature)
		
		super.getScope(context, reference)
	}
	
	def getAllContentsOfEClass(Resource res, EClass namedParent, boolean allContents) {
		var contents = if (allContents) res.allContents.toList else res.contents
		return contents
			.filter[eClass.equals(namedParent)]
		
	}
	
	def createQualifiedEClassScope(Resource res) {
		var contents = getAllContentsOfEClass(res, MIRPackage.eINSTANCE.import, true).toList
		var classifierDescriptions = contents.filterNull.map [ importEObject |
				val import = importEObject as Import
				val importName = import.name
				
				if (importName != null && import.package != null) {
					val classifiers = import.package.getEClassifiers
					return classifiers.map [ classifier |
						EObjectDescription.create(
							QualifiedName
								.create(importName)
								.append(qualifiedNameProvider.getFullyQualifiedName(classifier).skipFirst(1)),
							classifier
						)
					]
				} else {
					return  #[]
				}
			]
		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions.flatten)
		return resultScope
	}
	
	def getEClassScope(EObject context) {
		var res = context.eResource
		return createQualifiedEClassScope(res);
	}
	
	def IScope getEFeatureScope(EObject context) {
		if (context == null)
			return IScope.NULLSCOPE;
			
		var parentBaseMapping = EcoreUtil2.getContainerOfType(context, BaseMapping)
		
		var featureDescriptions =
			if (parentBaseMapping != null && parentBaseMapping instanceof BaseMapping) {
				parentBaseMapping.mappedElements.filter(typeof(NamedEClass)).map [ namedClass |
					namedClass.representedEClass.getEAllStructuralFeatures.map [
							val qualifiedName =
								QualifiedName.create(it.name)
							if (qualifiedName != null)
								EObjectDescription.create(qualifiedName, it)
					]
				]
			} else {
				#[]
			}

		var resultScope = new SimpleScope(IScope.NULLSCOPE, featureDescriptions.flatten)
		return resultScope		
	}
	
	def getContainingNamedEClassScope(NamedFeature feature) {
		var resultScope = IScope.NULLSCOPE
		for (var container = feature.eContainer; container != null; container = container.eContainer) {
			if (container instanceof BaseMapping) {
				val mapping = container as BaseMapping
				resultScope = new SimpleScope(resultScope,
					mapping.mappedElements
						.filter(typeof(NamedEClass))
						.filter[name != null]
						.map [ namedClass |
						EObjectDescription.create(namedClass.name, namedClass)
					])
			}	
		}
		return resultScope
	}
}