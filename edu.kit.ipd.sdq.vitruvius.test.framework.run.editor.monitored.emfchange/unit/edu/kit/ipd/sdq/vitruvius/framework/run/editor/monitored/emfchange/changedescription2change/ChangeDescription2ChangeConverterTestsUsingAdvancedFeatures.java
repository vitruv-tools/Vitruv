package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.emfmodels.advancedfeatures.AttributeListContaining;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.emfmodels.advancedfeatures.DummyData;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.emfmodels.advancedfeatures.ReferenceListContaining;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.emfmodels.advancedfeatures.RootContainer;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Metamodels;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.ChangeAssert;

public class ChangeDescription2ChangeConverterTestsUsingAdvancedFeatures extends
        AbstractChangeDescription2ChangeConverterTests<RootContainer> {

    @Override
    protected URL getModel() {
        return Files.TEST_ADVANCEDFEATURES;
    }

    @Override
    protected void registerMetamodels() {
        super.registerMetamodels();
        Metamodels.registerAdvancedFeaturesMetamodel();
    }

    @Test
    public void addToAttributeList() {
        String attr1 = "that rounds";
        String attr2 = "the mortal temples";

        AttributeListContaining alc = sourceRoot.getAttributeListContaining();
        int originalSize = alc.getAttrList().size();
        alc.getAttrList().add(attr1);
        alc.getAttrList().add(attr2);

        List<Change> changes = super.getChangesAndEndRecording();
        assert changes.size() == 2 * CHANGES_PER_LIST_ADD_DELOP : "Got " + changes.size() + " changes instead of " + 2
                * CHANGES_PER_LIST_ADD_DELOP;
        EStructuralFeature changedAttribute = alc.eClass().getEStructuralFeature("attrList");
        ChangeAssert.assertContainsListChange(changes, changedAttribute, attr1, alc, originalSize, true);
        ChangeAssert.assertContainsListChange(changes, changedAttribute, attr2, alc, originalSize, true);
    }

    @Test
    public void removeFromAttributeList() {
        AttributeListContaining alc = sourceRoot.getAttributeListContaining();

        alc.getAttrList().remove(alc.getAttrList().size() - 1);
        alc.getAttrList().remove(0);

        List<Change> changes = super.getChangesAndEndRecording();
        assert changes.size() == 2 * CHANGES_PER_LIST_ADD_DELOP : "Got " + changes.size() + " changes instead of " + 2
                * CHANGES_PER_LIST_ADD_DELOP;
        EStructuralFeature changedAttribute = alc.eClass().getEStructuralFeature("attrList");

        ChangeAssert.assertContainsListChange(changes, changedAttribute, null, alc, 0, false);
        ChangeAssert
                .assertContainsListChange(changes, changedAttribute, null, alc, alc.getAttrList().size() + 1, false);
    }

    @Test
    public void clearAttributeList() {
        AttributeListContaining alc = sourceRoot.getAttributeListContaining();

        int originalSize = alc.getAttrList().size();
        alc.getAttrList().clear();

        List<Change> changes = super.getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, CHANGES_PER_UNSET + originalSize * CHANGES_PER_LIST_ADD_DELOP);

        Change lastChange = changes.remove(changes.size() - 1);
        assert ((EMFModelChange) lastChange).getEChange() instanceof UnsetEFeature<?>;
        UnsetEFeature<?> unsetChange = (UnsetEFeature<?>) ((EMFModelChange) lastChange).getEChange();
        assert unsetChange.getAffectedEObject() == sourceRoot.getAttributeListContaining();
        assert unsetChange.getAffectedFeature() == alc.eClass().getEStructuralFeature("attrList");

        for (Change c : changes) {
            assert ((EMFModelChange) c).getEChange() instanceof RemoveFromEList<?>;
            RemoveFromEList<?> change = (RemoveFromEList<?>) ((EMFModelChange) c).getEChange();
            assert change.getIndex() == 0;
            assert change.getAffectedEObject() == alc;
            assert change.getAffectedFeature() == alc.eClass().getEStructuralFeature("attrList");
        }
    }

    @Test
    public void addToNonContainmentReferenceList() {
        DummyData dd1 = sourceRoot.getDummyDataContainerContaining().getDummyDataObjs().get(2);

        ReferenceListContaining rlc = sourceRoot.getReferenceListContaining();
        rlc.getNonContainmentRefList().add(dd1);

        List<Change> changes = getChangesAndEndRecording();
        assert changes.size() == CHANGES_PER_LIST_ADD_DELOP : "Got " + changes.size() + " changes instead of "
                + CHANGES_PER_LIST_ADD_DELOP;
        ChangeAssert.assertContainsListChange(changes, rlc.eClass().getEStructuralFeature("nonContainmentRefList"),
                dd1, rlc, rlc.getNonContainmentRefList().size() - 1, true);
    }

    @Test
    public void removeFromNonContainmentReferenceList() {
        ReferenceListContaining rlc = sourceRoot.getReferenceListContaining();
        DummyData dd0 = rlc.getNonContainmentRefList().remove(0);

        List<Change> changes = getChangesAndEndRecording();
        assert changes.size() == CHANGES_PER_LIST_ADD_DELOP : "Got " + changes.size() + " changes instead of "
                + CHANGES_PER_LIST_ADD_DELOP;
        ChangeAssert.assertContainsListChange(changes, rlc.eClass().getEStructuralFeature("nonContainmentRefList"),
                dd0, rlc, 0, false);
    }

    @Test
    public void clearNonContainmentReferenceList() {
        ReferenceListContaining rlc = sourceRoot.getReferenceListContaining();
        Collection<EObject> originalObjs = new HashSet<EObject>(rlc.getNonContainmentRefList());
        int oSz = originalObjs.size();
        rlc.getNonContainmentRefList().clear();

        List<Change> changes = getChangesAndEndRecording();

        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, CHANGES_PER_UNSET + oSz * CHANGES_PER_LIST_ADD_DELOP);
        for (EObject o : originalObjs) {
            ChangeAssert.assertContainsListChange(changes, rlc.eClass().getEStructuralFeature("nonContainmentRefList"),
                    o, rlc, 0, false);
        }
    }

}
