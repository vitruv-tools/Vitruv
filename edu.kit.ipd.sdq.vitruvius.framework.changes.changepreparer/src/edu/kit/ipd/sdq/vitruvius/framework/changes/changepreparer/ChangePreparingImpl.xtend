package edu.kit.ipd.sdq.vitruvius.framework.changes.changepreparer

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.RecordedChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.RecordedCompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.EMFModelChange
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProcessableChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.ProcessableChangeFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.FileChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.VitruviusChange
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject
import org.eclipse.emf.ecore.resource.Resource
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootFactory
import java.util.Collections
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding

class ChangePreparingImpl implements ChangePreparing {
	private static Logger logger = Logger.getLogger(ChangePreparingImpl);
	private val ModelProviding modelProviding;
	
	public new(ModelProviding modelProviding) {
		this.modelProviding = modelProviding;
	}
	
	override prepareAllChanges(RecordedChange unpreparedChange) {
		prepareChange(unpreparedChange);
	}
	
	private def dispatch ProcessableChange prepareChange(RecordedChange unpreparedChange) {
		throw new UnsupportedOperationException("General recorded changes cannot be handled.")
	}
	
	private def dispatch ProcessableChange prepareChange(RecordedCompositeChange unpreparedChange) {
		val compositeChange = ProcessableChangeFactory.instance.createProcessableCompositeChange();
		for (change : unpreparedChange.changes) {
			val preparedInnerChange = prepareChange(change);
			if (preparedInnerChange != null) {
				compositeChange.addChange(preparedInnerChange);	
			}
		}
		return compositeChange;
	}
	
	private def dispatch ProcessableChange prepareChange(EMFModelChange unpreparedChange) {
		val change = new EMFModelChangeTransformation(unpreparedChange).getChange();
		unpreparedChange.changeDescription.applyAndReverse();
		return change;
	}
	
	private def dispatch ProcessableChange prepareChange(FileChange fileChange) {
        val VURI sourceModelURI = fileChange.getURI();
        switch (fileChange.getFileChangeKind()) {
        case CREATE:
            return this.prepareFileCreated(sourceModelURI)
        case DELETE:
            return this.prepareFileDeleted(sourceModelURI)
        }
	}
	
	private def VitruviusChange prepareFileCreated(VURI sourceModelURI) {
        val ModelInstance newModelInstance = modelProviding.getAndLoadModelInstanceOriginal(sourceModelURI);
        val Resource resource = newModelInstance.getResource();
        var EObject rootElement = null;
        if (1 == resource.getContents().size()) {
            rootElement = resource.getContents().get(0);
        } else if (1 < resource.getContents().size()) {
            throw new RuntimeException(
                    "The requested model instance resource '" + resource + "' has to contain at most one root element "
                            + "in order to be added to the VSUM without an explicit import!");
        } else { // resource.getContents().size() == null --> no element in newModelInstance
            logger.info("Empty model file created: " + sourceModelURI
                    + ". Synchronization for 'root element created' not triggerd.");
            return null;
        }
        val InsertRootEObject<EObject> createRootEObj = RootFactory.eINSTANCE.createInsertRootEObject();
        createRootEObj.setNewValue(rootElement);
        val VitruviusChange rootAdd = ProcessableChangeFactory.getInstance().createVitruviusChange(null, Collections.singletonList(createRootEObj), sourceModelURI);
        return rootAdd;
    }

    private def VitruviusChange prepareFileDeleted(VURI sourceModelURI) {
        val ModelInstance oldModelInstance = modelProviding.getAndLoadModelInstanceOriginal(sourceModelURI);
        val Resource resource = oldModelInstance.getResource();
        if (0 < resource.getContents().size()) {
            val EObject rootElement = resource.getContents().get(0);
            val RemoveRootEObject<EObject> deleteRootObj = RootFactory.eINSTANCE.createRemoveRootEObject();
            deleteRootObj.setOldValue(rootElement);
            val VitruviusChange rootDeleted = ProcessableChangeFactory.getInstance().createVitruviusChange(null, Collections.singletonList(deleteRootObj), sourceModelURI);
            return rootDeleted;
        }
        logger.info("Deleted resource " + sourceModelURI + " did not contain any EObject");
        return null;
    }
	
}