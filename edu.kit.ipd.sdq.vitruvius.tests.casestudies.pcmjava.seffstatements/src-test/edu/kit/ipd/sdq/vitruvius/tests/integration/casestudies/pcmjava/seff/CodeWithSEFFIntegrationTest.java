package edu.kit.ipd.sdq.vitruvius.tests.integration.casestudies.pcmjava.seff;

import edu.kit.ipd.sdq.vitruvius.codeintegration.tests.CodeIntegrationTest;

public class CodeWithSEFFIntegrationTest extends CodeIntegrationTest {

    private static final String TEST_BUNDLE_NAME_JSCIENCE = "edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.seffstatements";
    private static final String TEST_PROJECT_NAME_JSCIENCE = "Calculator-JScience";
    private static final String SRC_AND_MODEL_FOLDER_JSCIENCE = "exampleCode/Calculator-JScience";

    @Override
    protected String getTestProjectName() {
        return CodeWithSEFFIntegrationTest.TEST_PROJECT_NAME_JSCIENCE;
    }

    @Override
    protected String getTestBundleName() {
        return CodeWithSEFFIntegrationTest.TEST_BUNDLE_NAME_JSCIENCE;
    }

    @Override
    protected String getTestSourceAndModelFolder() {
        return CodeWithSEFFIntegrationTest.SRC_AND_MODEL_FOLDER_JSCIENCE;
    }

}
