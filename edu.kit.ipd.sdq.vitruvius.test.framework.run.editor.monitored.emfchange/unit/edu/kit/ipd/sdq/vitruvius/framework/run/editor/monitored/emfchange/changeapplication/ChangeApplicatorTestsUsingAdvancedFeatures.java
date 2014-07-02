package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.net.URL;

import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.emfmodels.advancedfeatures.AttributeListContaining;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.emfmodels.advancedfeatures.RootContainer;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Metamodels;

public class ChangeApplicatorTestsUsingAdvancedFeatures extends AbstractChangeApplicatorTests<RootContainer> {

    @Override
    protected URL getModel() {
        return Files.TEST_ADVANCEDFEATURES;
    }

    @Override
    public void setUp() {
        registerMetamodels();
        super.setUp();
    }

    @Override
    protected void registerMetamodels() {
        Metamodels.registerAdvancedFeaturesMetamodel();
    }

    @Test
    public void addToAttributeList() {
        String attr1 = "that rounds";
        String attr2 = "the mortal temples";

        AttributeListContaining alc = sourceRoot.getAttributeListContaining();
        alc.getAttrList().add(attr1);
        alc.getAttrList().add(attr2);

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void removeFromAttributeList() {
        AttributeListContaining alc = sourceRoot.getAttributeListContaining();

        alc.getAttrList().remove(alc.getAttrList().size() - 1);
        alc.getAttrList().remove(0);

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void clearAttributeList() {
        AttributeListContaining alc = sourceRoot.getAttributeListContaining();

        alc.getAttrList().clear();

        synchronizeChangesAndAssertEquality();
    }

}
