package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference;

/**
 * A factory for {@link EChange} objects.
 */
final class EChangeFactory {

    private EChangeFactory() {
    }

    /**
     * Creates a {@link CreateNonRootEObject} object.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change, i.e. the containment of the new
     *            object.
     * @param affectedFeature
     *            The {@link EReference} feature affected in <code>affectedObject</code>.
     * @param newObject
     *            The created {@link EObject}.
     * @param newValue
     *            The reference feature's new value.
     * @param <T>
     *            The reference type.
     * @return The new {@link CreateNonRootEObject} instance.
     * 
     */
    public static <T> CreateNonRootEObject<T> createCreateNonRootObject(EObject affectedObject,
            EReference affectedFeature, EObject newObject, T newValue) {
        assert affectedObject != newObject;
        CreateNonRootEObject<T> createObj = ChangeFactory.eINSTANCE.createCreateNonRootEObject();
        createObj.setAffectedEObject(affectedObject);
        createObj.setAffectedFeature(affectedFeature);
        createObj.setChangedEObject(newObject);
        createObj.setNewValue(newValue);
        return createObj;
    }

    /**
     * Creates a {@link DeleteNonRootEObject} object.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change, i.e. the containment of the new
     *            object.
     * @param affectedFeature
     *            The {@link EReference} feature affected in <code>affectedObject</code>.
     * @param deletedObject
     *            The deleted {@link EObject}.
     * @param newValue
     *            The reference feature's new value.
     * @param <T>
     *            The reference type.
     * @return The new {@link DeleteNonRootEObject} instance.
     * 
     */
    public static <T> DeleteNonRootEObject<T> createDeleteNonRootObject(EObject affectedObject,
            EReference affectedFeature, EObject deletedObject, T newValue) {
        assert affectedObject != deletedObject;
        DeleteNonRootEObject<T> delObj = ChangeFactory.eINSTANCE.createDeleteNonRootEObject();
        delObj.setAffectedEObject(affectedObject);
        delObj.setAffectedFeature(affectedFeature);
        delObj.setChangedEObject(deletedObject);
        delObj.setNewValue(newValue);
        return delObj;
    }

    /**
     * Creates an {@link UpdateEReference} object.
     * 
     * @param affectedObject
     *            The affected {@link EObject}.
     * @param affectedFeature
     *            The affected feature in <code>affectedObject</code>.
     * @param newValue
     *            The feature's new value.
     * @param <T>
     *            The reference type.
     * @return The new {@link UpdateEReference} object.
     */
    public static <T> UpdateEReference<T> createUpdateEReference(EObject affectedObject, EReference affectedFeature,
            T newValue) {
        UpdateEReference<T> result = ChangeFactory.eINSTANCE.createUpdateEReference();
        result.setAffectedEObject(affectedObject);
        result.setAffectedFeature(affectedFeature);
        result.setNewValue(newValue);
        return result;
    }

    /**
     * Creates an {@link UpdateEAttribue} object.
     * 
     * @param affectedObject
     *            The affected {@link EObject}.
     * @param affectedFeature
     *            The affected feature in <code>affectedObject</code>.
     * @param newValue
     *            The feature's new value.
     * @param <T>
     *            The attribute type.
     * @return The new {@link UpdateEAttribute} object.
     */
    public static <T> UpdateEAttribute<T> createUpdateEAttribute(EObject affectedObject, EAttribute affectedFeature,
            T newValue) {
        UpdateEAttribute<T> result = ChangeFactory.eINSTANCE.createUpdateEAttribute();
        result.setAffectedEObject(affectedObject);
        result.setAffectedFeature(affectedFeature);
        result.setNewValue(newValue);
        return result;
    }

    /**
     * Creates an {@link RemoveFromEList} object.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change.
     * @param affectedFeature
     *            The affected feature in <code>affectedObject</code>.
     * @param remObj
     *            The object removed from the feature.
     * @param index
     *            The index of <code>remObj</code> in the feature's list. This index is relative to
     *            the list changes preceding this change.
     * @param <T>
     *            The type of the affected feature, e.g. {@link EReference} or {@link EAttribute}.
     * @return The created {@link RemoveFromEList} object.
     */
    public static <T extends EStructuralFeature> RemoveFromEList<T> createRemoveFromEList(EObject affectedObject,
            T affectedFeature, Object remObj, int index) {
        RemoveFromEList<T> indexObj = ChangeFactory.eINSTANCE.createRemoveFromEList();
        indexObj.setAffectedEObject(affectedObject);
        indexObj.setAffectedFeature(affectedFeature);
        indexObj.setUpdate(remObj);
        indexObj.setIndex(index);
        if (remObj instanceof EObject && ((EObject) remObj).eResource() != null) {
            EObject remEObj = (EObject) remObj;
            indexObj.setRemovedObjectURIFragment(remEObj.eResource().getURIFragment(remEObj));
        }
        return indexObj;
    }

    /**
     * Creates an {@link InsertInEList} object.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change.
     * @param affectedFeature
     *            The affected feature in <code>affectedObject</code>.
     * @param insertedObj
     *            The object added to the feature.
     * @param index
     *            The index of <code>remObj</code> in the feature's list. This index is relative to
     *            the list changes preceding this change.
     * @param <T>
     *            The type of the affected feature, e.g. {@link EReference} or {@link EAttribute}.
     * @return The created {@link RemoveFromEList} object.
     */
    public static <T extends EStructuralFeature> InsertInEList<T> createInsertInEList(EObject affectedObject,
            T affectedFeature, Object insertedObj, int index) {
        InsertInEList<T> indexObj = ChangeFactory.eINSTANCE.createInsertInEList();
        indexObj.setAffectedEObject(affectedObject);
        indexObj.setAffectedFeature(affectedFeature);
        indexObj.setUpdate(insertedObj);
        indexObj.setIndex(index);
        return indexObj;
    }
}
