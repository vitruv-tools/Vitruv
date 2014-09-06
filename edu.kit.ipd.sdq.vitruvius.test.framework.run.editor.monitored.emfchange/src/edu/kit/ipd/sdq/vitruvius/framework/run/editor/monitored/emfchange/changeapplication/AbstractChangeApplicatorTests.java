package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import static org.junit.Assert.fail;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.After;
import org.junit.Before;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.ChangeDescription2ChangeConverter;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Models;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.BasicTestCase;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.ChangeAssert;

public abstract class AbstractChangeApplicatorTests<T extends EObject> extends BasicTestCase {
    private static final Boolean PRINT_MODELS_ON_EQUALITY_FAIL = Boolean.TRUE;
    private static final int MODEL_PRINT_DEPTH = 2;
    private static final Boolean MODEL_PRINT_ONLY_CONTAINMENTS = Boolean.TRUE;

    private boolean hasRegisteredMetaModels = false;

    protected Resource sourceRes;
    protected Resource targetRes;
    protected T sourceRoot;
    protected T targetRoot;

    private ChangeRecorder changeRecorder;

    private ChangeRecorder sourceResettingCR;
    private ChangeRecorder targetResettingCR;

    @Before
    public void setUp() {
        System.err.println("setting up");

        if (!hasRegisteredMetaModels) {
            registerMetamodels();
            hasRegisteredMetaModels = true;
        }

        sourceRes = Models.loadModel(getModel());
        targetRes = Models.loadModel(getModel());

        sourceRoot = (T) sourceRes.getContents().get(0);
        targetRoot = (T) targetRes.getContents().get(0);

        sourceResettingCR = new ChangeRecorder(sourceRes);
        targetResettingCR = new ChangeRecorder(targetRes);

        changeRecorder = new ChangeRecorder(sourceRes);
    }

    protected abstract URL getModel();

    protected abstract void registerMetamodels();

    @After
    public void tearDown() {
        System.err.println("tearing down");

        sourceResettingCR.endRecording().apply();
        targetResettingCR.endRecording().apply();
        sourceRes.unload();
        targetRes.unload();
    }

    protected List<Change> getChangesAndEndRecording() {
        ChangeDescription cd = changeRecorder.endRecording();
        ChangeDescription2ChangeConverter converter = new ChangeDescription2ChangeConverter();
        cd.applyAndReverse();
        List<Change> changes = converter.getChanges(cd);
        cd.applyAndReverse();

        return changes;
    }

    protected void assertSourceAndTargetStructuralEquality() {
        if (!EcoreUtil.equals(sourceRoot, targetRoot)) {
            if (PRINT_MODELS_ON_EQUALITY_FAIL) {
                System.err.println("**** Source model vs. target model ****");
                System.err.println("Source model:");
                printModel(sourceRoot);
                System.err.println("\n\nTarget model:");
                printModel(targetRoot);
                System.err.println("\n");
                findDifference(sourceRoot, targetRoot);
            }

            fail("Models are not equal after synchronization");
        }
    }

    public static void printModel(EObject root) {
        printModelElement(root, 0, new HashSet<EObject>());
    }

    private static void printModelElement(EObject element, int depth, Set<EObject> visitedObjects) {
        if (visitedObjects.contains(element)) {
            printIndentedLine("^ " + element, depth);
            return;
        } else {
            printIndentedLine("+ " + element, depth);
            visitedObjects.add(element);
        }

        if (depth > MODEL_PRINT_DEPTH) {
            return;
        }

        for (EStructuralFeature feature : element.eClass().getEStructuralFeatures()) {
            if (!element.eIsSet(feature)) {
                continue;
            }

            if (feature instanceof EReference) {

                if (MODEL_PRINT_ONLY_CONTAINMENTS && !((EReference) feature).isContainment()) {
                    continue;
                }

                printIndentedLine("Feature " + feature.getName(), depth + 1);
                if (feature.isMany()) {
                    @SuppressWarnings("unchecked")
                    EList<EObject> featureList = (EList<EObject>) element.eGet(feature);

                    for (EObject e : featureList) {
                        printModelElement(e, depth + 2, visitedObjects);
                    }
                } else if (!MODEL_PRINT_ONLY_CONTAINMENTS) {
                    printModelElement((EObject) element.eGet(feature), depth + 2, visitedObjects);
                }
            } else {
                printIndentedLine("(attr) " + element.eGet(feature), depth + 2);
            }
        }
    }

    private static void printIndentedLine(String line, int indent) {
        for (int i = 0; i < indent; i++) {
            System.err.print("  ");
        }
        System.err.println(line);
    }

    private void findDifference(EObject source, EObject target) {
        String result = findDifference(source, target, new HashSet<EObject>());
        if (result != null) {
            System.err.println("**** Found a difference between the two models ****" + result);
        }
    }

    private String findDifference(EObject source, EObject target, Set<EObject> visitedObjects) {

        if (source != null && target != null && source.eClass() != target.eClass()) {
            return "\n(" + source + ", " + target + ")";
        } else if (source == null && source != target) {
            return "\n(" + source + ", " + target + ")";
        } else if (source == null && target == null) {
            return null;
        }

        if (visitedObjects.contains(source) && !visitedObjects.contains(target)) {
            return "\nVisited " + source + ", did not visit " + target + " yet.";
        } else if (visitedObjects.contains(source)) {
            return null;
        }

        visitedObjects.add(source);
        visitedObjects.add(target);

        if (source.eClass() != target.eClass()) {
            return "\n(" + source + ", " + target + ")";
        }

        for (EStructuralFeature feature : source.eClass().getEStructuralFeatures()) {
            EStructuralFeature correspFeature = target.eClass().getEStructuralFeature(feature.getName());
            if (feature instanceof EReference) {
                EReference ref = (EReference) feature;

                if (!ref.isMany()) {
                    String diff = findDifference((EObject) source.eGet(feature), (EObject) target.eGet(correspFeature),
                            visitedObjects);
                    if (diff != null) {
                        return "\nObject: " + source + ", Feature: " + feature.getName() + diff;
                    }
                } else {
                    EList<?> sourceList = (EList<?>) source.eGet(feature);
                    EList<?> targetList = (EList<?>) target.eGet(correspFeature);

                    if (sourceList.size() != targetList.size()) {
                        return "\nObject: " + source + ", Feature: " + feature.getName() + "\nDifferent list size";
                    }

                    Iterator<?> sourceIt = sourceList.iterator();
                    Iterator<?> targetIt = targetList.iterator();

                    while (sourceIt.hasNext()) {
                        EObject sourceSubObj = (EObject) sourceIt.next();
                        EObject targetSubObj = (EObject) targetIt.next();

                        String diff = findDifference(sourceSubObj, targetSubObj, visitedObjects);
                        if (diff != null) {
                            return "\nObject: " + sourceSubObj + ", Feature: " + feature.getName() + diff;
                        }
                    }
                }

            } else {
                if (source.eGet(feature) == null) {
                    if (target.eGet(feature) != null) {
                        return "\nObject: " + source + ", Feature: " + feature.getName() + "Difference in attribute "
                                + feature;
                    }
                } else if (!source.eGet(feature).equals(target.eGet(feature))) {
                    return "\nObject: " + source + ", Feature: " + feature.getName() + "Difference in attribute "
                            + feature;
                }
            }
        }

        return null;
    }

    protected void synchronizeChangesAndAssertEquality() {
        List<Change> changes = getChangesAndEndRecording();
        try {
            ChangeAssert.assertContainsNoReferencesToPreviouslyDeletedObjects(changes);
            ChangeAssert.assertContainsNoForwardReferences(changes);

            System.err.println("number of changes: " + changes.size());

            ChangeApplicator applicator = new ChangeApplicator(sourceRes, changes);
            applicator.applyChanges(targetRes);

            assertSourceAndTargetStructuralEquality();
        } catch (Exception e) {
            System.err.println("Caught exception on change list:");
            ChangeAssert.printChangeList(changes);
            throw e;
        }
        changeRecorder = new ChangeRecorder(sourceRes);
    }
}
