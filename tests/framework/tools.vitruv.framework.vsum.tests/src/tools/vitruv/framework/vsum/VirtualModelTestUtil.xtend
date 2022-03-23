package tools.vitruv.framework.vsum

import allElementTypes.Root
import edu.kit.ipd.sdq.activextendannotations.Utility
import java.nio.file.Path
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.propagation.ResourceAccess
import tools.vitruv.framework.propagation.impl.AbstractChangePropagationSpecification
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider

import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.getCorrespondingEObjects
import allElementTypes.AllElementTypesPackage
import tools.vitruv.framework.propagation.Metamodel

/**
 * Utility methods for the VSUM and view test cases.
 */
@Utility
class VirtualModelTestUtil {

    /**
     * Create a recorder, start recording a resource set, apply changes, stop and return the recorded changes.
     */
    def static VitruviusChange recordChanges(ResourceSet resourceSet, Runnable changesToPerform) {
        val recorder = new ChangeRecorder(resourceSet)
        recorder.addToRecording(resourceSet)
        recorder.beginRecording
        changesToPerform.run()
        val result = recorder.endRecording
        recorder.close
        return result
    }

    /**
     * Creates an empty virtual model without a change propagation specification.
     */
    static def createAndLoadTestVirtualModel(Path folder) {
        return new VirtualModelBuilder().withStorageFolder(folder).withDomain(
            new AllElementTypesDomainProvider().domain).withUserInteractor(
            UserInteractionFactory.instance.createUserInteractor(
                UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null))).buildAndInitialize()
    }

    /**
     * Creates an empty virtual model with a {@link RedundancyChangePropagationSpecification}.
     */
    static def createAndLoadTestVirtualModelWithConsistencyPreservation(Path folder) {
        val aetDomain = new AllElementTypesDomainProvider().domain
        return new VirtualModelBuilder().withStorageFolder(folder).withDomain(aetDomain).
            withChangePropagationSpecification(new RedundancyChangePropagationSpecification(
            	Metamodel.with(AllElementTypesPackage.eNS_URI), Metamodel.with(AllElementTypesPackage.eNS_URI)
            )).
            withUserInteractor(UserInteractionFactory.instance.createUserInteractor(
                UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null))).buildAndInitialize()
    }

    /**
     * Creates a model resource URI for a given project folder. Use the suffix to distinguish multiple URIs.
     */
    static def createTestModelResourceUri(Path projectFolder, String suffix) {
        URI.createFileURI(projectFolder.resolve("root" + suffix + ".allElementTypes").toString)
    }

    static class RedundancyChangePropagationSpecification extends AbstractChangePropagationSpecification {
        static def getTargetResourceUri(URI sourceUri) {
            URI.createFileURI(sourceUri.trimFileExtension.toFileString + "Copy." + sourceUri.fileExtension)
        }

        new(Metamodel sourceMetamodel, Metamodel targetMetamodel) {
            super(sourceMetamodel, targetMetamodel)
        }

        override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
            if(change instanceof InsertRootEObject) {
                return change.newValue instanceof Root
            }
            return false
        }

        override propagateChange(EChange change, CorrespondenceModel correspondenceModel,
            extension ResourceAccess resourceAccess) {
            if(!doesHandleChange(change, correspondenceModel)) {
                return
            }
            val typedChange = change as InsertRootEObject<Root>
            val insertedRoot = typedChange.newValue
            // If there is a corresponding element, reuse it, otherwise create one
            val correspondingRoots = correspondenceModel.getCorrespondingEObjects(insertedRoot).filter(Root)
            val correspondingRoot = if(correspondingRoots.size == 1) {
                    correspondingRoots.get(0)
                } else {
                    val newRoot = aet.Root => [
                        id = insertedRoot.id
                    ]
                    correspondenceModel.createAndAddCorrespondence(List.of(insertedRoot), List.of(newRoot))
                    newRoot
                }

            if(insertedRoot.eContainer !== null) {
                val correspondingObjects = correspondenceModel.getCorrespondingEObjects(insertedRoot.eContainer, Root)
                assertEquals(1, correspondingObjects.size)
                correspondingObjects.get(0).recursiveRoot = correspondingRoot
            }
            val resourceURI = typedChange.resource.URI
            persistAsRoot(correspondingRoot, resourceURI.targetResourceUri)
        }
    }

}
