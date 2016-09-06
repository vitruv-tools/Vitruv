package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.NormalClassDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.RegularModifier
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.correspondences.Java2JMLCorrespondenceAdder
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.correspondences.MatchingModelElementsFinder
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener
import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteractionType
import java.util.ArrayList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ClassifiersPackage
import org.emftext.language.java.commons.Commentable
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Member
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.Modifier

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

class JavaClassTransformations extends Java2JMLTransformationBase {

	private static val Logger LOGGER = Logger.getLogger(JavaClassTransformations)

	@Inject
	new(ShadowCopyFactory shadowCopyFactory, SynchronisationAbortedListener synchronisationAbortedListener) {
		super(shadowCopyFactory, synchronisationAbortedListener)
	}

	override protected getLogger() {
		return LOGGER
	}

	override getClassOfMappedEObject() {
		return Class
	}

	override setCorrespondenceForFeatures() {
		featureCorrespondenceMap.put(
			ClassifiersPackage.eINSTANCE.class_.getEStructuralFeature("annotationsAndModifiers"),
			JMLPackage.eINSTANCE.modifiable_Modifiers)
			featureCorrespondenceMap.put(ClassifiersPackage.eINSTANCE.class_.getEStructuralFeature("members"),
				JMLPackage.eINSTANCE.normalClassDeclaration_BodyDeclarations)
		}

		// TODO what about renaming the class?
		override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
			EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
			val changedObjects = new ArrayList<EObject>()

			LOGGER.trace(
				"CreateNonRootEObjectInList\t" + oldAffectedEObject + "." + affectedReference.name + " (" + newValue +
					" @ " + index + ")")

			val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)

			if (jmlFeature == JMLPackage.eINSTANCE.modifiable_Modifiers) {
				CommonSynchronizerTransformations.createNonRootEObjectInList(oldAffectedEObject, newValue as Modifier,
					correspondenceModel)
				return new TransformationResult
			} else if (jmlFeature == JMLPackage.eINSTANCE.normalClassDeclaration_BodyDeclarations) {
				val jmlNormalClassDecl = getSingleCorrespondingEObjectOfType(oldAffectedEObject, NormalClassDeclaration)
				if (jmlNormalClassDecl != null) {

					if (jmlNormalClassDecl != null &&
						MatchingModelElementsFinder.findMatchingMember(newValue as Commentable,
							jmlNormalClassDecl.getChildrenOfType(MemberDeclWithModifier)) != null) {
						LOGGER.info("Aborted transformation because of name clash with JML.")
						userInteracting.showMessage(UserInteractionType.MODAL,
							"There already is a " + newValue.class.simpleName.toLowerCase +
								" in JML, which has the same signature.");
								syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
								return new TransformationResult
							}

							if (newValue instanceof Method) {
								LOGGER.trace("Creating (Method) " + newValue)

								val jmlMethod = CommonSynchronizerTasks.createJMLMethod(newValue as Method)
								jmlNormalClassDecl.bodyDeclarations.add(index, jmlMethod)
								Java2JMLCorrespondenceAdder.addCorrespondences(newValue as Method, jmlMethod,
									correspondenceModel)
								changedObjects.add(jmlMethod)
								changedObjects.add(jmlNormalClassDecl)

							} else if (newValue instanceof Field) {
								LOGGER.trace("Creating (Field) " + newValue)

								val jmlField = CommonSynchronizerTasks.createJMLField(newValue as Field)
								jmlNormalClassDecl.bodyDeclarations.add(index, jmlField)
								Java2JMLCorrespondenceAdder.addCorrespondences(newValue as Field, jmlField,
									correspondenceModel)
								changedObjects.add(jmlField)
								changedObjects.add(jmlNormalClassDecl)

							} else {
								LOGGER.warn(
									"The given newValue has an unsupported type, so the change will be ignored.")
							}

						}
					}
					return new TransformationResult
				}

				override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
					EReference affectedReference, EObject oldValue, int index,
					EObject[] oldCorrespondingEObjectsToDelete) {
						val changedObjects = new ArrayList<EObject>()

						LOGGER.trace(
							"DeleteNonRootEObjectInList\t" + oldAffectedEObject + "." + affectedReference.name + " (" +
								oldValue + " @ " + index + ")")

						val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)

						if (jmlFeature == JMLPackage.eINSTANCE.modifiable_Modifiers) {
							val jmlClassifierDecl = getSingleCorrespondingEObjectOfType(oldAffectedEObject,
								ClassifierDeclarationWithModifier)
							if (jmlClassifierDecl != null) {
								LOGGER.trace("Deleting " + oldValue)

								val jmlModifier = getSingleCorrespondingEObjectOfType(oldValue, RegularModifier)

								correspondenceModel.
									removeCorrespondencesThatInvolveAtLeastAndDependend(oldValue.toSet)
								correspondenceModel.
									removeCorrespondencesThatInvolveAtLeastAndDependend(jmlModifier.toSet)

								jmlClassifierDecl.modifiers.remove(jmlModifier)

								changedObjects.add(jmlClassifierDecl)
							}
						} else if (jmlFeature == JMLPackage.eINSTANCE.normalClassDeclaration_BodyDeclarations) {
							val jmlNormalClassDecl = getSingleCorrespondingEObjectOfType(oldAffectedEObject,
								NormalClassDeclaration)
							if (jmlNormalClassDecl != null) {
								LOGGER.trace("Deleting " + oldValue)

								if (!simulateElementRemoval(oldValue as Member)) {
									userInteracting.showMessage(
										UserInteractionType.
											MODAL
										,
										"The change is not allowed since this member is referenced in other methods or specifications."
									);
									syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
									return new TransformationResult
								}

								var jmlSpecifiedElement = getSingleCorrespondingEObjectOfType(oldValue,
									JMLSpecifiedElement)

								correspondenceModel.
									removeCorrespondencesThatInvolveAtLeastAndDependend(jmlSpecifiedElement.toSet)
								correspondenceModel.
									removeCorrespondencesThatInvolveAtLeastAndDependend(oldValue.toSet)

								jmlNormalClassDecl.bodyDeclarations.remove(jmlSpecifiedElement)

								changedObjects.add(jmlNormalClassDecl)
							}
						}
						return new TransformationResult
					}

					override replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference,
						EObject oldValue, EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete,
						EObject[] newCorrespondingEObjects) {
						val changedObjects = new ArrayList<EObject>()

						LOGGER.trace(
							"ReplaceNonRootEObjectInList\t" + affectedEObject + "." + affectedReference.name + " (" +
								oldValue + " @ " + index + " -> " + newValue + ")")

						val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)

						if (jmlFeature == JMLPackage.eINSTANCE.modifiable_Modifiers) {
							CommonSynchronizerTransformations.replaceNonRootEObjectInList(affectedEObject,
								oldValue as Modifier, newValue as Modifier, correspondenceModel)
							return new TransformationResult
						}

					}

				}
				/*
				 * 	override protected execute(UpdateEAttribute change, ModelInstance sourceModel, CorrespondenceModel correspondenceInstance) {
				 * 		
				 * 		val changedClazz = change.affectedEObject as Class
				 * 		val correspondingClassifiers = correspondenceInstance.claimCorrespondingEObjectsByType(changedClazz, ClassOrInterfaceDeclaration).filter(ClassDeclaration)
				 * 		if (correspondingClassifiers.size > 1) {
				 * 			LOGGER.warn("A Java class can not correspond to multiple classes in JML. Skipping transformation.")
				 * 			return null
				 * 		}
				 * 		val correspondingClass = correspondingClassifiers.get(0);
				 * 		
				 * 		if (change.affectedFeature.name == "name" && classGivesNameToCompilationUnit(changedClazz)) {
				 * 
				 * 			// calculate new URIs
				 * 			val oldJavaUri = sourceModel.URI
				 * 			val oldJmlUri = VURI.getInstance(correspondingClass.eResource.URI)
				 * 			
				 * 			val newJavaUriString = createNewUriAfterNameChange(oldJavaUri.EMFUri, change.newValue as String, ".java")
				 * 			val newJmlUriString = createNewUriAfterNameChange(oldJmlUri.EMFUri, change.newValue as String, ".jml")
				 * 			
				 * 			val newJavaUri = VURI.getInstance(newJavaUriString)
				 * 			val newJmlUri = VURI.getInstance(newJmlUriString)
				 * 			
				 * 			// perform change
				 * 			val oldJmlCu = correspondingClass.eContainer as CompilationUnit
				 * 			val newJmlCu = EcoreUtil.copy(oldJmlCu) as CompilationUnit
				 * 			val newCorrespondingClass = newJmlCu.typedeclaration.filter[classOrInterfaceDeclaration instanceof ClassDeclaration].findFirst[(classOrInterfaceDeclaration as ClassDeclaration).identifier == correspondingClass.identifier]
				 * 			(newCorrespondingClass.classOrInterfaceDeclaration as ClassDeclaration).identifier = change.newValue as String
				 * 			
				 * 			// build result
				 * 			val javaCU = changedClazz.containingCompilationUnit
				 * 			
				 * 			val newRoots = new HashSet<Pair<EObject, VURI>>()
				 * 			newRoots.add(new Pair<EObject, VURI>(javaCU, newJavaUri))
				 * 			newRoots.add(new Pair<EObject, VURI>(newJmlCu, newJmlUri))
				 * 			
				 * 			val emfChange = new EMFChangeResult(#[].toSet, newRoots, #[oldJavaUri, oldJmlUri].toSet)
				 * 			
				 * 			// update correspondences
				 * 			correspondenceInstance.update(oldJmlCu, newJmlCu)
				 * 			// update Java correspondences?
				 * 			
				 * 			return emfChange
				 * 		}
				 * 		
				 * 		throw new UnsupportedOperationException("TODO: auto-generated method stub")
				 * 	}
				 * 	
				 * 	private def classGivesNameToCompilationUnit(Class clazz) {
				 * 		val cu = clazz.containingCompilationUnit
				 * 		
				 * 		// the compilation unit has no name attribute, so we compare the first classifier
				 * 		if (cu.name == null && cu.classifiers != null && cu.classifiers.size > 0 && cu.classifiers.get(0) == clazz) {
				 * 			return true
				 * 		}
				 * 		
				 * 		// the compilation unit has a name attribute, so we compare the names
				 * 		if (cu.name == getFullQualifiedName(clazz) + ".java") {
				 * 			return true
				 * 		}
				 * 		
				 * 		return false;
				 * 	}
				 * 	
				 * 	private def getFullQualifiedName(Class clazz) {
				 * 		return StringUtils.join(clazz.containingCompilationUnit.namespaces, '.') + '.' + clazz.name
				 * 	}
				 */