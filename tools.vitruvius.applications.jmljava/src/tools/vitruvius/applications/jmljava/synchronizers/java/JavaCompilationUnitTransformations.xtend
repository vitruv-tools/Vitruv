package tools.vitruvius.applications.jmljava.synchronizers.java

import com.google.inject.Inject
import tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier
import tools.vitruvius.domains.jml.language.jML.CompilationUnit
import tools.vitruvius.domains.jml.language.jML.ImportDeclaration
import tools.vitruvius.domains.jml.language.jML.JMLPackage
import tools.vitruvius.applications.jmljava.correspondences.Java2JMLCorrespondenceAdder
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import tools.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener
import tools.vitruvius.framework.util.command.TransformationResult
import tools.vitruvius.framework.userinteraction.UserInteractionType
import java.util.ArrayList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.containers.ContainersPackage
import org.emftext.language.java.imports.Import
import static extension tools.vitruvius.framework.util.bridges.CollectionBridge.*

class JavaCompilationUnitTransformations extends Java2JMLTransformationBase {
	
	private static val LOGGER = Logger.getLogger(JavaCompilationUnitTransformations)
	
	@Inject
	new(ShadowCopyFactory shadowCopyFactory, SynchronisationAbortedListener synchronisationAbortedListener) {
		super(shadowCopyFactory, synchronisationAbortedListener)
	}
	
	override protected getLogger() {
		return LOGGER
	}
	
	override getClassOfMappedEObject() {
		return org.emftext.language.java.containers.CompilationUnit
	}
	
	override setCorrespondenceForFeatures() {
		featureCorrespondenceMap.put(ContainersPackage.eINSTANCE.compilationUnit.getEStructuralFeature("imports"), JMLPackage.eINSTANCE.compilationUnit_Importdeclaration)
		featureCorrespondenceMap.put(ContainersPackage.eINSTANCE.compilationUnit.getEStructuralFeature("classifiers"), JMLPackage.eINSTANCE.compilationUnit_Typedeclaration)
	}
	
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		val changedObjects = new ArrayList<EObject>()
		
		LOGGER.trace("CreateNonRootEObjectInList\t" + oldAffectedEObject + "." + affectedReference.name + " (" + newValue + " @ " + index + ")")
		
		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)
		
		 if (jmlFeature == JMLPackage.eINSTANCE.compilationUnit_Importdeclaration) {
			val jmlCu = getSingleCorrespondingEObjectOfType(oldAffectedEObject, CompilationUnit)
			if (jmlCu != null) {
				LOGGER.trace("Creating " + newValue)
				
				val jmlImport = CommonSynchronizerTasks.createJMLImportDeclaration(newValue as Import)
				jmlCu.importdeclaration.add(index, jmlImport)

				Java2JMLCorrespondenceAdder.addCorrespondences(newValue as Import, jmlImport, correspondenceModel)
	
				changedObjects.add(jmlImport)
				changedObjects.add(jmlCu)
			}
		} else if (jmlFeature == JMLPackage.eINSTANCE.compilationUnit_Typedeclaration) {
			val jmlCu = getSingleCorrespondingEObjectOfType(oldAffectedEObject, CompilationUnit)
			if (jmlCu != null) {
				LOGGER.trace("Creating " + newValue)
				
				val javaCu = (oldAffectedEObject as org.emftext.language.java.containers.CompilationUnit).modelInstanceElement
				if (javaCu.classifiers.exists[name.equals((newValue as Classifier).name)]) {
					LOGGER.info("Aborted transformation because of name clash.")
					userInteracting.showMessage(UserInteractionType.MODAL, "There already is a classifier, which has the same name.");
					syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
					return new TransformationResult
				}
				
				val jmlType = CommonSynchronizerTasks.createJMLClass(newValue as Class)
				jmlCu.typedeclaration.add(index, jmlType)

				Java2JMLCorrespondenceAdder.addCorrespondences(newValue as Class, jmlType, correspondenceModel)
	
				changedObjects.add(jmlType)
				changedObjects.add(jmlCu)
			}
		}
		return new TransformationResult
	}
	
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		val changedObjects = new ArrayList<EObject>()
		
		LOGGER.trace("DeleteNonRootEObjectInList\t" + oldAffectedEObject + "." + affectedReference.name + " (" + oldValue + " @ " + index + ")")
		
		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)
				
		if (jmlFeature == JMLPackage.eINSTANCE.compilationUnit_Importdeclaration) {
			val jmlCu = getSingleCorrespondingEObjectOfType(oldAffectedEObject, CompilationUnit)
			if (jmlCu != null) {
				LOGGER.trace("Deleting " + oldValue)
				
				val jmlImport = getSingleCorrespondingEObjectOfType(oldValue, ImportDeclaration)
				
				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(jmlImport.toSet)
				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(oldValue.toSet)
				
				jmlCu.importdeclaration.remove(jmlImport)

				changedObjects.add(jmlCu)
			}
		} else if (jmlFeature == JMLPackage.eINSTANCE.compilationUnit_Typedeclaration) {
			val jmlCu = getSingleCorrespondingEObjectOfType(oldAffectedEObject, CompilationUnit)
			if (jmlCu != null) {
				LOGGER.trace("Deleting " + oldValue)
				
				val jmlType = getSingleCorrespondingEObjectOfType(oldValue, ClassifierDeclarationWithModifier)
				
				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(jmlType.toSet)
				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(oldValue.toSet)
				
				jmlCu.typedeclaration.remove(jmlType)
				
				changedObjects.add(jmlCu)
			}
		}
		return new TransformationResult
	}
	
}