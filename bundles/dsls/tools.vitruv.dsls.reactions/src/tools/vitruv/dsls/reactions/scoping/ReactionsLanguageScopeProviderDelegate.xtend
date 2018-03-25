package tools.vitruv.dsls.reactions.scoping

import com.google.inject.Inject

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.naming.QualifiedName

import static tools.vitruv.dsls.mirbase.mirBase.MirBasePackage.Literals.*;
import static tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguagePackage.Literals.*

import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.dsls.mirbase.scoping.MirBaseScopeProviderDelegate
import org.eclipse.emf.ecore.EcorePackage
import tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.InputTypesPackage
import tools.vitruv.dsls.reactions.reactionsLanguage.RoutineInput
import tools.vitruv.dsls.reactions.reactionsLanguage.CreateModelElement
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementReplacementChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeChange
import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsImport
import tools.vitruv.dsls.reactions.reactionsLanguage.RoutineOverrideImportPath
import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsImportsHelper.*


class ReactionsLanguageScopeProviderDelegate extends MirBaseScopeProviderDelegate {

	@Inject ReactionsImportScopeHelper reactionsImportScopeHelper;

	override getScope(EObject context, EReference reference) {
		// context differs during content assist: 
		// * if no input is provided yet, the container is the context as the element is not known yet
		// * if some input is already provided, the element is the context
		if (reference.equals(METACLASS_FEATURE_REFERENCE__FEATURE))
			return createEStructuralFeatureScope(context as MetaclassFeatureReference)
		else if (reference.equals(METACLASS_REFERENCE__METACLASS)) {
			val contextContainer = context.eContainer();
			if (context instanceof CreateModelElement) {
				return createQualifiedEClassScopeWithoutAbstract(context.metamodel);
			} else if (contextContainer instanceof CreateModelElement) {
				return createQualifiedEClassScopeWithoutAbstract(contextContainer.metamodel);
			} else if (contextContainer instanceof RoutineInput) {
				val inputElement = context as MetaclassReference;
				return createQualifiedEClassScopeWithSpecialInputTypes(inputElement.metamodel);
			} else if (context instanceof RoutineInput) {
				return createQualifiedEClassScopeWithSpecialInputTypes(null);
			} else if (context instanceof MetaclassReference) {
				return createQualifiedEClassScopeWithEObject(context.metamodel)
			} else if (contextContainer instanceof MetaclassReference) {
				return createQualifiedEClassScopeWithEObject(contextContainer.metamodel)
			}
		} else if (reference.equals(REACTIONS_IMPORT__IMPORTED_REACTIONS_SEGMENT)) {
			if (context instanceof ReactionsImport) {
				val contextContainer = context.eContainer();
				if (contextContainer instanceof ReactionsSegment) {
					return createReactionsImportScope(contextContainer);
				}
			}
		} else if (reference.equals(REACTION__OVERRIDDEN_REACTIONS_SEGMENT)) {
			if (context instanceof Reaction) {
				return createReactionOverrideScope(context.reactionsSegment);
			}
		} else if (reference.equals(ROUTINE_OVERRIDE_IMPORT_PATH__REACTIONS_SEGMENT)) {
			if (context instanceof Routine) {
				return createRoutineOverrideScope(context.reactionsSegment, null);
			} else if (context instanceof RoutineOverrideImportPath) {
				// follow the containers of the current override import path segment to find the container (the routine) which
				// contains the import path as a whole:
				var container = context.eContainer();
				while (container instanceof RoutineOverrideImportPath) {
					container = container.eContainer();
				}
				if (container instanceof Routine) {
					return createRoutineOverrideScope(container.reactionsSegment, context);
				}
			}
		}
		super.getScope(context, reference)
	}

	def createReactionsImportScope(ReactionsSegment reactionsSegment) {
		val visibleReactionsSegmentDescriptions = reactionsImportScopeHelper.getVisibleReactionsSegmentDescriptions(reactionsSegment);
		return new SimpleScope(visibleReactionsSegmentDescriptions);
	}

	def createReactionOverrideScope(ReactionsSegment reactionsSegment) {
		// excluding the root reactions segment here:
		val reactionsImportHierarchyWithoutRoot = reactionsSegment.reactionsImportHierarchy.filter[k, v | k.length > 1];
		return new SimpleScope(reactionsImportHierarchyWithoutRoot.entrySet.map [
			EObjectDescription.create(QualifiedName.create(it.key.lastSegment), it.value);
		]);
	}

	def createRoutineOverrideScope(ReactionsSegment reactionsSegment, RoutineOverrideImportPath routineOverrideImportPath) {
		// the reactions segment to use to determine the next possible segments in the import path:
		var importPathLastSegment = reactionsSegment;
		if (routineOverrideImportPath?.parent !== null) {
			val parentImportPath = routineOverrideImportPath.parent.toReactionsImportPath;
			importPathLastSegment = reactionsSegment.getReactionsSegment(parentImportPath);
			if (importPathLastSegment === null) {
				// invalid parent import path:
				return IScope.NULLSCOPE;
			}
		}
		return Scopes.scopeFor(importPathLastSegment.reactionsImports.filter[it.isResolvable].map[it.importedReactionsSegment]);
	}

	def createEStructuralFeatureScope(MetaclassFeatureReference featureReference) {
		if (featureReference?.metaclass !== null) {
			val changeType = featureReference.eContainer;
			val multiplicityFilterFunction = if (changeType instanceof ElementReplacementChangeType) {
				[EStructuralFeature feat | !feat.many];
			} else {
				[EStructuralFeature feat | true];
			}
			val typeFilterFunction = if (changeType instanceof ModelAttributeChange) {
				[EStructuralFeature feat | feat instanceof EAttribute];
			} else if (changeType instanceof ElementChangeType) {
				[EStructuralFeature feat | feat instanceof EReference];
			} else {
				throw new IllegalStateException();
			}
			createScope(IScope.NULLSCOPE, featureReference.metaclass.EAllStructuralFeatures.
				filter(multiplicityFilterFunction).filter(typeFilterFunction).iterator, [
				EObjectDescription.create(it.name, it)
			])
		} else {
			return IScope.NULLSCOPE
		}
	}

	def createQualifiedEClassScopeWithSpecialInputTypes(MetamodelImport metamodelImport) {
		val classifierDescriptions = 
			if (metamodelImport === null || metamodelImport.package === null) {
				#[createEObjectDescription(EcorePackage.Literals.EOBJECT, false),
					createEObjectDescription(InputTypesPackage.Literals.STRING, false),
					createEObjectDescription(InputTypesPackage.Literals.INTEGER, false),
					createEObjectDescription(InputTypesPackage.Literals.BOOLEAN, false),
					createEObjectDescription(InputTypesPackage.Literals.SHORT, false),
					createEObjectDescription(InputTypesPackage.Literals.LONG, false),
					createEObjectDescription(InputTypesPackage.Literals.BYTE, false),
					createEObjectDescription(InputTypesPackage.Literals.CHARACTER, false),
					createEObjectDescription(InputTypesPackage.Literals.FLOAT, false),
					createEObjectDescription(InputTypesPackage.Literals.DOUBLE, false)
				];
			} else {
				collectObjectDescriptions(metamodelImport.package, true, true, metamodelImport.useQualifiedNames)		
			}

		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions)
		return resultScope
	}
}
