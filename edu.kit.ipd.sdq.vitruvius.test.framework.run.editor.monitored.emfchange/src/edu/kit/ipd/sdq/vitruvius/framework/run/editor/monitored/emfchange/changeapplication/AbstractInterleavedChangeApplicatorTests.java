package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.junit.Before;

public abstract class AbstractInterleavedChangeApplicatorTests<A extends EObject, B extends EObject> {
    protected AbstractChangeApplicatorTests<A> testA;
    protected AbstractChangeApplicatorTests<B> testB;

    protected static final URI MODEL_A_URI = URI.createURI("pathmap://INTERLEAVED_TEST/MODEL_A");
    protected static final URI MODEL_B_URI = URI.createURI("pathmap://INTERLEAVED_TEST/MODEL_B");

    protected abstract void registerMetaModels();

    protected abstract URL getModelA();

    protected abstract URL getModelB();

    protected void synchronizeChangesAndAssertEqualityA() {
        testA.synchronizeChangesAndAssertEquality();
    }

    protected void synchronizeChangesAndAssertEqualityB() {
        testB.synchronizeChangesAndAssertEquality();
    }

    @Before
    public void setUp() {
        testA = new AbstractChangeApplicatorTests<A>() {
            @Override
            protected URL getModel() {
                return getModelA();
            }

            @Override
            protected void registerMetamodels() {
                AbstractInterleavedChangeApplicatorTests.this.registerMetaModels();
            }
        };

        testB = new AbstractChangeApplicatorTests<B>() {
            @Override
            protected URL getModel() {
                return getModelB();
            }

            @Override
            protected void registerMetamodels() {
                AbstractInterleavedChangeApplicatorTests.this.registerMetaModels();
            }
        };

        testA.setUp();
        testB.setUp();

        URIConverter.URI_MAP.put(MODEL_A_URI, testA.sourceRes.getURI());
        URIConverter.URI_MAP.put(MODEL_B_URI, testB.sourceRes.getURI());
        testA.sourceRes.setURI(MODEL_A_URI);
        testA.targetRes.setURI(MODEL_A_URI);
        testB.sourceRes.setURI(MODEL_B_URI);
        testB.targetRes.setURI(MODEL_B_URI);
        testA.sourceRes.getResourceSet().getResources().add(testB.sourceRes);
        testA.targetRes.getResourceSet().getResources().add(testB.targetRes);
    }

}
