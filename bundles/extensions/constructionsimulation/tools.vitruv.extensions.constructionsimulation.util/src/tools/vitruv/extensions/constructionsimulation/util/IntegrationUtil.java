package tools.vitruv.extensions.constructionsimulation.util;

import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.vsum.InternalVirtualModel;

public class IntegrationUtil {

    private IntegrationUtil() {
    }

    public static InternalVirtualModel createVSUM(final Iterable<Metamodel> metamodels) {
    	return TestUtil.createVSUM("vitruvius.meta", metamodels);
    }

}
