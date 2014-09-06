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
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ReplaceInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteEObject;

public final class ChangeAssert {
    private static final Logger LOGGER = Logger.getLogger(ChangeAssert.class);

    private ChangeAssert() {
    }

    public enum ListChangeKind {
        ADD, REMOVE
    }

    public enum StructuralChangeKind {
        CONTAINMENT, ORDINARY
    }

    public static void printChangeList(Collection<Change> changes) {
        System.err.println("Change-list related assertion failure, got change list:");
        for (Change c : changes) {
            EChange change = ((EMFModelChange) c).getEChange();
            System.err.println("\t" + change);
            if (change instanceof EFeatureChange<?>) {
                EFeatureChange<?> fc = (EFeatureChange<?>) change;
                System.err.println("\t\tAffected: " + fc.getNewAffectedEObject());
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

    public static void assertContainsSingleValuedAttributeChange(Collection<Change> changes,
            EStructuralFeature feature, Object newValue) {
        assert feature != null;

        for (Change c : changes) {
            EChange innerChange = ((EMFModelChange) c).getEChange();
            if (innerChange instanceof UpdateSingleValuedEAttribute<?>) {
                UpdateSingleValuedEAttribute<?> attrChange = (UpdateSingleValuedEAttribute<?>) innerChange;
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
            if (innerChange instanceof CreateNonRootEObjectSingle<?>) {
                CreateNonRootEObjectSingle<?> attrChange = (CreateNonRootEObjectSingle<?>) innerChange;
                if (attrChange.getNewValue() == addedObject && attrChange.getAffectedFeature() == feature) {
                    return;
                }
            } else if (innerChange instanceof CreateNonRootEObjectInList<?>) {
                CreateNonRootEObjectInList<?> addChange = (CreateNonRootEObjectInList<?>) innerChange;
                if (addChange.getNewValue() == addedObject && addChange.getAffectedFeature() == feature) {
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

            if (c instanceof CreateEObject<?> || c instanceof DeleteEObject<?>) {
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
            if (innerChange instanceof DeleteNonRootEObjectSingle<?>) {
                DeleteNonRootEObjectSingle<?> attrChange = (DeleteNonRootEObjectSingle<?>) innerChange;
                if (attrChange.getOldValue() == removedObject && attrChange.getAffectedFeature() == feature) {
                    return;
                }
            } else if (innerChange instanceof DeleteNonRootEObjectInList<?>) {
                DeleteNonRootEObjectInList<?> remChange = (DeleteNonRootEObjectInList<?>) innerChange;
                if (remChange.getOldValue() == removedObject && remChange.getAffectedFeature() == feature) {
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
                if (unsetFeature.getNewAffectedEObject() == affectedObject
                        && unsetFeature.getAffectedFeature() == feature) {
                    return;
                }
            }
        }
    }

    public static void assertContainsListChange(Collection<Change> changes, EStructuralFeature feature,
            Object changedObject, EObject parentObject, int index, ListChangeKind operationKind,
            StructuralChangeKind changeKind) {
        assert feature != null;

        for (Change outerChange : changes) {
            EChange c = ((EMFModelChange) outerChange).getEChange();

            if ((changeKind == StructuralChangeKind.CONTAINMENT && c.eClass().getEPackage() != ContainmentPackage.eINSTANCE)
                    || (changeKind == StructuralChangeKind.ORDINARY && c.eClass().getEPackage() == ContainmentPackage.eINSTANCE)) {
                continue;
            }

            if (c instanceof EFeatureChange<?>) {
                EFeatureChange<?> featureChange = (EFeatureChange<?>) c;
                if (featureChange.getAffectedFeature() != feature
                        || featureChange.getNewAffectedEObject() != parentObject) {
                    continue;
                }
            } else {
                continue;
            }

            if (c instanceof InsertInEList<?> && operationKind == ListChangeKind.ADD) {
                InsertInEList<?> change = (InsertInEList<?>) c;
                if (change.getNewValue() == changedObject && change.getIndex() == index) {
                    return;
                }
            }

            if (c instanceof RemoveFromEList<?> && operationKind == ListChangeKind.REMOVE) {
                RemoveFromEList<?> change = (RemoveFromEList<?>) c;
                if (change.getOldValue() == changedObject && change.getIndex() == index) {
                    return;
                }
            }

            if (c instanceof ReplaceInEList<?>) {
                ReplaceInEList<?> change = (ReplaceInEList<?>) c;
                if (change.getIndex() == index) {
                    if ((operationKind == ListChangeKind.ADD && change.getNewValue() == changedObject)
                            || (operationKind == ListChangeKind.REMOVE && change.getOldValue() == changedObject)) {
                        return;
                    }
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
                if (scannedDeletions.contains(updateChange.getNewAffectedEObject())) {
                    LOGGER.fatal("Detected a pre-insertion update change to " + updateChange.getNewAffectedEObject()
                            + " (" + updateChange + ")");
                    fail("The change list contains a reference to a previously deleted object.");
                }
            }
            if (change instanceof DeleteNonRootEObjectSingle<?>) {
                DeleteNonRootEObjectSingle<?> deleteChange = (DeleteNonRootEObjectSingle<?>) change;
                Object deletedObject = deleteChange.getOldValue();
                scannedDeletions.add(deletedObject);
            } else if (change instanceof DeleteNonRootEObjectInList<?>) {
                DeleteNonRootEObjectInList<?> deleteChange = (DeleteNonRootEObjectInList<?>) change;
                Object deletedObject = deleteChange.getOldValue();
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
                scannedAffectedObjects.add(updateChange.getNewAffectedEObject());
            }

            if (change instanceof CreateEObject<?>) {
                EObject createdObject = null;

                if (change instanceof CreateNonRootEObjectInList<?>) {
                    createdObject = ((CreateNonRootEObjectInList<?>) change).getNewValue();
                } else if (change instanceof CreateNonRootEObjectSingle<?>) {
                    createdObject = ((CreateNonRootEObjectSingle<?>) change).getNewValue();
                } else {
                    throw new UnsupportedOperationException("Unrecognized create operation: " + change);
                }

                if (scannedAffectedObjects.contains(createdObject)) {
                    LOGGER.fatal("Detected a post-deletion update change to " + createdObject + " (" + change + ")");
                    fail("The change list contains a reference to a previously deleted object.");
                }

            }
        }
    }
}
