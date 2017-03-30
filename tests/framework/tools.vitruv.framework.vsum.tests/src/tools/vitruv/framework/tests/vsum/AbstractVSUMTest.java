package tools.vitruv.framework.tests.vsum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;

public abstract class AbstractVSUMTest extends MetaRepositoryTest {
    protected InternalVirtualModel createMetaRepositoryAndVSUM(final String mm1URIString, final String fileExt1,
            final String mm2URIString, final String fileExt2) {
        List<Metamodel> metamodels = new ArrayList<Metamodel>();
        metamodels.add(new Metamodel(VURI.getInstance(mm1URIString), mm1URIString,
                new AttributeTUIDCalculatorAndResolver(mm1URIString, "id"), fileExt1));
        metamodels.add(new Metamodel(VURI.getInstance(mm2URIString), mm2URIString,
                new AttributeTUIDCalculatorAndResolver(mm2URIString, "id"), fileExt2));
        return createVSUM(metamodels);
    }

    protected InternalVirtualModel createVSUM(final Iterable<Metamodel> metamodels) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InternalVirtualModel vsum = TestUtil.createVSUM(VSUM_NAME, metamodels, Collections.emptyList(), classLoader);
        return vsum;
    }
}
