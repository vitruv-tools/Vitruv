package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateNonRootEObjectImpl;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteNonRootEObjectImpl;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEAttributeImpl;

public final class ChangeAssert {
    private static final Logger LOGGER = Logger.getLogger(ChangeAssert.class);

    private ChangeAssert() {
    }

    public static void printChangeList(Collection<Change> changes) {
        System.err.println("Change-list related assertion failure, got change list:");
        for (Change c : changes) {
            EChange change = ((EMFModelChange) c).getEChange();
            System.err.println("\t" + change);
            if (change instanceof EFeatureChange<?>) {
                EFeatureChange<?> fc = (EFeatureChange<?>) change;
                System.err.println("\t\tAffected: " + fc.getAffectedEObject());
                System.err.println("\t\tIn feature: " + fc.getAffectedFeature());
            }
        }
    }

    public static void assertCorrectlyOrderedAndChangeListSize(List<Change> changes, int expectedSize) {
        assertContainsNoForwardReferences(changes);
        assertContainsNoReferencesToPreviouslyDeletedObjects(changes);
        if (changes.size() != expectedSize) {
            printChangeList(changes);
            fail("Got " + changes.size() + " changes, expected " + expectedSize);
        }
    }

    public static void assertContainsAttributeChange(Collection<Change> changes, EStructuralFeature feature,
            Object newValue) {
        assert feature != null;

        for (Change c : changes) {
            EChange innerChange = ((EMFModelChange) c).getEChange();
            if (innerChange instanceof UpdateEAttributeImpl<?>) {
                UpdateEAttributeImpl<?> attrChange = (UpdateEAttributeImpl<?>) innerChange;
                if (attrChange.getNewValue().equals(newValue) && attrChange.getAffectedFeature() == feature) {
                    return;
                }
            }
        }

        assert false : "Could not find attribute update for " + feature.getName() + "=" + newValue;
    }

    public static void assertContainsAddChange(Collection<Change> changes, EStructuralFeature feature,
            EObject addedObject) {
        assert feature != null;

        for (Change c : changes) {
            EChange innerChange = ((EMFModelChange) c).getEChange();
            if (innerChange instanceof CreateNonRootEObjectImpl<?>) {
                CreateNonRootEObjectImpl<?> attrChange = (CreateNonRootEObjectImpl<?>) innerChange;
                if (attrChange.getChangedEObject() == addedObject && attrChange.getAffectedFeature() == feature) {
                    return;
                }
            }
        }

        assert false : "Could not find added object " + addedObject + " in " + feature.getName();
    }

    public static void assertAllAddRemoveBeforeSetAttribute(List<Change> changes) {
        boolean observedARChange = false;
        for (Change cc : changes) {
            assert cc instanceof EMFModelChange;
            EChange c = ((EMFModelChange) cc).getEChange();

            if (c instanceof CreateNonRootEObject<?> || c instanceof DeleteNonRootEObject<?>) {
                assert !observedARChange : "Detected add/remove change after attribute/reference change!";
            } else {
                if (!(c instanceof InsertInEList<?> || c instanceof RemoveFromEList<?>)) {
                    observedARChange = true;
                }
            }
        }
    }

    public static void assertContainsRemoveChange(Collection<Change> changes, EStructuralFeature feature,
            EObject removedObject) {
        assert feature != null;

        for (Change c : changes) {
            EChange innerChange = ((EMFModelChange) c).getEChange();
            if (innerChange instanceof DeleteNonRootEObjectImpl<?>) {
                DeleteNonRootEObjectImpl<?> attrChange = (DeleteNonRootEObjectImpl<?>) innerChange;
                if (attrChange.getChangedEObject() == removedObject && attrChange.getAffectedFeature() == feature) {
                    return;
                }
            }
        }

        assert false : "Could not find removed object " + removedObject + " in " + feature.getName();
    }

    public static void assertContainsUnsetChange(Collection<Change> changes, EStructuralFeature feature,
            EObject affectedObject) {
        assert feature != null;

        for (Change c : changes) {
            EChange innerChange = ((EMFModelChange) c).getEChange();
            if (innerChange instanceof UnsetEFeature<?>) {
                UnsetEFeature<?> unsetFeature = (UnsetEFeature<?>) innerChange;
                if (unsetFeature.getAffectedEObject() == affectedObject && unsetFeature.getAffectedFeature() == feature) {
                    return;
                }
            }
        }
    }

    public static void assertContainsListChange(Collection<Change> changes, EStructuralFeature feature,
            Object changedObject, EObject parentObject, int index, boolean isAdd) {
        assert feature != null;

        for (Change c : changes) {
            EChange innerChange = ((EMFModelChange) c).getEChange();

            EChange candidate = innerChange;
            if (innerChange instanceof CreateNonRootEObject<?>) {
                CreateNonRootEObject<?> createChange = (CreateNonRootEObject<?>) innerChange;
                if (createChange.getListUpdate() != null) {
                    candidate = createChange.getListUpdate();
                }
            } else if (innerChange instanceof DeleteNonRootEObject<?>) {
                DeleteNonRootEObject<?> deleteChange = (DeleteNonRootEObject<?>) innerChange;
                if (deleteChange.getListUpdate() != null) {
                    candidate = deleteChange.getListUpdate();
                }
            }

            if ((isAdd && candidate instanceof InsertInEList<?>) || (!isAdd && candidate instanceof RemoveFromEList<?>)) {
                UpdateEList<?> attrChange = (UpdateEList<?>) candidate;
                if (attrChange.getUpdate() == changedObject && attrChange.getAffectedFeature() == feature
                        && attrChange.getIndex() == index && attrChange.getAffectedEObject() == parentObject) {
                    return;
                }
            }
        }

        printChangeList(changes);
        fail("Could not find list change for object " + changedObject + " in " + feature.getName());
    }

    public static void assertContainsNoReferencesToPreviouslyDeletedObjects(List<Change> changeList) {
        Set<Object> scannedDeletions = new HashSet<>();
        for (Change c : changeList) {
            EMFModelChange mc = (EMFModelChange) c;
            EChange change = (EChange) mc.getEChange();
            if (change instanceof EFeatureChange<?>) {
                EFeatureChange<?> updateChange = (EFeatureChange<?>) change;
                if (scannedDeletions.contains(updateChange.getAffectedEObject())) {
                    LOGGER.fatal("Detected a pre-insertion update change to " + updateChange.getAffectedEObject()
                            + " (" + updateChange + ")");
                    fail("The change list contains a reference to a previously deleted object.");
                }
            }
            if (change instanceof DeleteNonRootEObject<?>) {
                DeleteNonRootEObject<?> deleteChange = (DeleteNonRootEObject<?>) change;
                Object deletedObject = deleteChange.getChangedEObject();
                scannedDeletions.add(deletedObject);
            }
        }
    }

    public static void assertContainsNoForwardReferences(List<Change> changeList) {
        Set<Object> scannedAffectedObjects = new HashSet<>();
        for (Change c : changeList) {
            EMFModelChange mc = (EMFModelChange) c;
            EChange change = (EChange) mc.getEChange();
            if (change instanceof UpdateEReference<?>) {
                UpdateEReference<?> updateChange = (UpdateEReference<?>) change;
                scannedAffectedObjects.add(updateChange.getAffectedEObject());
            }
            if (change instanceof CreateNonRootEObject<?>) {
                CreateNonRootEObject<?> createChange = (CreateNonRootEObject<?>) change;
                Object createdObject = createChange.getChangedEObject();
                if (scannedAffectedObjects.contains(createdObject)) {
                    LOGGER.fatal("Detected a post-deletion update change to " + createdObject + " (" + createChange
                            + ")");
                    fail("The change list contains a reference to a previously deleted object.");
                }
            }
        }
    }
}
