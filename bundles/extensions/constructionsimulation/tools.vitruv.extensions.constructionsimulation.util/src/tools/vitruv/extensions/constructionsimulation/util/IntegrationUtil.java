package tools.vitruv.extensions.constructionsimulation.util;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.vsum.InternalVirtualModel;

public class IntegrationUtil {

    private IntegrationUtil() {
    }

    public static InternalVirtualModel createVsum(final Iterable<VitruvDomain> metamodels) {
    	return TestUtil.createVirtualModel("vitruvius.meta", true, metamodels);
    }

}
