package tools.vitruv.framework.vsum

import allElementTypes.AllElementTypesPackage
import allElementTypes.Root
import edu.kit.ipd.sdq.activextendannotations.Utility
import java.nio.file.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.root.InsertRootEObject
import tools.vitruv.change.atomic.uuid.Uuid
import tools.vitruv.change.atomic.uuid.UuidResolver
import tools.vitruv.change.composite.MetamodelDescriptor
import tools.vitruv.change.composite.description.VitruviusChange
import tools.vitruv.change.composite.description.VitruviusChangeResolver
import tools.vitruv.change.composite.recording.ChangeRecorder
import tools.vitruv.change.correspondence.Correspondence
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView
import tools.vitruv.change.interaction.UserInteractionFactory
import tools.vitruv.change.propagation.ResourceAccess
import tools.vitruv.change.propagation.impl.AbstractChangePropagationSpecification

import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

/**
 * Utility methods for the VSUM and view test cases.
 */
@Utility
class VirtualModelTestUtil {

    /**
     * Create a recorder, start recording a resource set, apply changes, stop and return the recorded changes.
     */
    def static VitruviusChange<Uuid> recordChanges(ResourceSet resourceSet, UuidResolver uuidResolver, Runnable changesToPerform) {
        val recorder = new ChangeRecorder(resourceSet)
        val changeResolver = VitruviusChangeResolver.forUuids(uuidResolver)
        recorder.addToRecording(resourceSet)
        recorder.beginRecording
        changesToPerform.run()
        val result = recorder.endRecording
        val resolvedChange = changeResolver.assignIds(result)
        recorder.close
        return resolvedChange
    }

    /**
     * Creates an empty virtual model without a change propagation specification.
     */
    static def createAndLoadTestVirtualModel(Path folder) {
        return new VirtualModelBuilder().withStorageFolder(folder).withUserInteractor(
            UserInteractionFactory.instance.createUserInteractor(
                UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null))).buildAndInitialize()
    }

    /**
     * Creates an empty virtual model with a {@link RedundancyChangePropagationSpecification}.
     */
    static def createAndLoadTestVirtualModelWithConsistencyPreservation(Path folder) {
        return new VirtualModelBuilder().withStorageFolder(folder).
            withChangePropagationSpecification(new RedundancyChangePropagationSpecification(
            	MetamodelDescriptor.with(AllElementTypesPackage.eNS_URI), MetamodelDescriptor.with(AllElementTypesPackage.eNS_URI)
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
            val sourceUriWithoutFileExtension = sourceUri.trimFileExtension.toFileString
            val copySuffix = "Copy"
        	if (sourceUriWithoutFileExtension.endsWith(copySuffix)) {
        		val sourceUriWithoutSuffix = sourceUriWithoutFileExtension.substring(0, sourceUriWithoutFileExtension.length - copySuffix.length) 
        		URI.createFileURI(sourceUriWithoutSuffix + "." + sourceUri.fileExtension)
        	} else {
        		URI.createFileURI(sourceUriWithoutFileExtension + copySuffix + "." + sourceUri.fileExtension)
        	}
        }

        new(MetamodelDescriptor sourceMetamodelDescriptor, MetamodelDescriptor targetMetamodelDescriptor) {
            super(sourceMetamodelDescriptor, targetMetamodelDescriptor)
        }

        override doesHandleChange(EChange<EObject> change, EditableCorrespondenceModelView<Correspondence> correspondenceModel) {
            if(change instanceof InsertRootEObject) {
                return change.newValue instanceof Root
            }
            return false
        }

        override propagateChange(EChange<EObject> change, EditableCorrespondenceModelView<Correspondence> correspondenceModel,
            extension ResourceAccess resourceAccess) {
            if(!doesHandleChange(change, correspondenceModel)) {
                return
            }
            val typedChange = change as InsertRootEObject<EObject>
            val insertedRoot = typedChange.newValue as Root
            // If there is a corresponding element, reuse it, otherwise create one
            val correspondingRoots = correspondenceModel.getCorrespondingEObjects(insertedRoot).filter(Root)
            val correspondingRoot = if(correspondingRoots.size == 1) {
                    correspondingRoots.get(0)
                } else {
                    val newRoot = aet.Root => [
                        id = insertedRoot.id
                    ]
                    correspondenceModel.addCorrespondenceBetween(insertedRoot, newRoot, null)
                    newRoot
                }

            if(insertedRoot.eContainer !== null) {
                val correspondingObjects = correspondenceModel.getCorrespondingEObjects(insertedRoot.eContainer, null).filter(Root)
                assertEquals(1, correspondingObjects.size)
                correspondingObjects.get(0).recursiveRoot = correspondingRoot
            }
            val resourceURI = typedChange.resource.URI
            persistAsRoot(correspondingRoot, resourceURI.targetResourceUri)
        }
    }

}
