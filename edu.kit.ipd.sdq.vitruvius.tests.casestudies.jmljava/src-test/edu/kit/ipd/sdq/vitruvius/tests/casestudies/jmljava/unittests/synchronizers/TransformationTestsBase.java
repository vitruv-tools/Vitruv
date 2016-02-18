package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.synchronizers;

import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.EMFComparePrettyPrinter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.containers.CompilationUnit;
import org.junit.Assert;
import org.junit.Before;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.correspondences.Java2JMLCorrespondenceAdder;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.Utilities;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopy;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyImpl;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JMLMetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JaMoPPMetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CSSynchronizer;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ContractsBuilder;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.BlackboardImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.CorrespondingProvidingMock;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.Initializer;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader.IResourceFiles;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelProvidingMock;

public abstract class TransformationTestsBase {

    private static class CSSynchronizerModule extends AbstractModule {

        private final ShadowCopyFactory shadowCopyFactory;
        private final UserInteracting userInteracting;
        private final SynchronisationAbortedListener syncAbortedListener;

        CSSynchronizerModule(final ShadowCopyFactory shadowCopyFactory, final UserInteracting userInteracting,
                final SynchronisationAbortedListener syncAbortedListener) {
            this.shadowCopyFactory = shadowCopyFactory;
            this.userInteracting = userInteracting;
            this.syncAbortedListener = syncAbortedListener;
        }

        @Override
        protected void configure() {
            this.bind(ShadowCopyFactory.class).toInstance(this.shadowCopyFactory);
            this.bind(UserInteracting.class).toInstance(this.userInteracting);
            this.bind(SynchronisationAbortedListener.class).toInstance(this.syncAbortedListener);
        }

    }

    private static class JavaModelURIProvider implements ModelURIProvider {

        private final List<VURI> uris = new ArrayList<VURI>();

        public JavaModelURIProvider(final VURI modelUri) {
            this.uris.add(modelUri);
        }

        @Override
        public List<VURI> getAllRelevantURIs() {
            return this.uris;
        }

    }

    private static class CorrespondenceInstanceProxy implements InvocationHandler {
        private final CorrespondenceInstance ci;
        private final List<Pair<String, String>> updateCalls = new ArrayList<Pair<String, String>>();

        public CorrespondenceInstanceProxy(final CorrespondenceInstance ci) {
            this.ci = ci;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            if (method.getName().equals("update") && args.length == 2) {
                final String firstTUIDString = this.getTUIDString(args[0]);
                final String secondTUIDString = this.getTUIDString(args[1]);
                this.updateCalls.add(new Pair<String, String>(firstTUIDString, secondTUIDString));
            }

            return method.invoke(this.ci, args);
        }

        private String getTUIDString(final Object arg) {
            if (arg instanceof EObject) {
                return this.ci.calculateTUIDFromEObject((EObject) arg).toString();
            }
            if (arg instanceof TUID) {
                return ((TUID) arg).toString();
            }
            throw new IllegalArgumentException();
        }

        public List<Pair<String, String>> getUpdateCalls() {
            return this.updateCalls;
        }
    }

    private static final Mapping MAPPING_JAVA2JML = constructMapping();
    private ModelProvidingMock modelProviding;
    private CorrespondingProvidingMock correspondingProding;
    private CorrespondenceInstanceProxy correspondenceInstanceUpdateRecorder;
    protected CSSynchronizer synchronizer;
    protected CorrespondenceInstanceDecorator correspondenceInstance;
    protected UserInteracting userInteracting;
    protected SynchronisationAbortedListener syncAbortedListener;
    private Blackboard blackboard;

    private static Mapping constructMapping() {
        Initializer.initLogging();
        Initializer.initJaMoPP();
        Initializer.initJML();

        final Metamodel mmJava = new JaMoPPMetaModelProvider().getMetaModel();
        final Metamodel mmJml = new JMLMetaModelProvider().getMetaModel();
        return new Mapping(mmJava, mmJml);
    }

    @Before
    public void setup() throws Exception {
        final Pair<ModelInstance, ModelInstance> modelInstances = this.getModelInstances();
        this.modelProviding = createModelProviding(modelInstances);
        this.correspondenceInstanceUpdateRecorder = createCorrespondenceInstanceProxy(this.modelProviding,
                modelInstances);
        this.correspondenceInstance = createCorrespondenceInstance(this.correspondenceInstanceUpdateRecorder);
        this.userInteracting = createUserInteracting();
        this.syncAbortedListener = createSyncAbortedListener();
        this.blackboard = this.createBlackboard(this.correspondenceInstance, this.modelProviding,
                this.correspondingProding);
        this.synchronizer = createChangeSynchronizer(this.blackboard, this.userInteracting, this.syncAbortedListener,
                modelInstances);
    }

    private Blackboard createBlackboard(final CorrespondenceInstanceDecorator correspondenceInstance,
            final ModelProvidingMock modelProviding, CorrespondingProvidingMock correspondingProvidingMock) {
        final Blackboard blackboard = new BlackboardImpl(correspondenceInstance, modelProviding,
                correspondingProvidingMock);
        return blackboard;
    }

    private static SynchronisationAbortedListener createSyncAbortedListener() {
        return EasyMock.createStrictMock(SynchronisationAbortedListener.class);
    }

    private static UserInteracting createUserInteracting() {
        return EasyMock.createStrictMock(UserInteracting.class);
    }

    private static CorrespondenceInstanceDecorator createCorrespondenceInstance(
            final CorrespondenceInstanceProxy proxy) {
        return (CorrespondenceInstanceDecorator) Proxy.newProxyInstance(
                CorrespondenceInstanceProxy.class.getClassLoader(), new Class[] { CorrespondenceInstance.class },
                proxy);
    }

    private static CorrespondenceInstanceProxy createCorrespondenceInstanceProxy(final ModelProviding modelProviding,
            final Pair<ModelInstance, ModelInstance> modelInstances) throws IOException {
        final URI dummyURICorrespondenceInstance = getDummyURI();

        final CorrespondenceInstance ci = ContractsBuilder.createCorrespondenceInstance(MAPPING_JAVA2JML,
                modelProviding, VURI.getInstance(dummyURICorrespondenceInstance),
                new ResourceImpl(dummyURICorrespondenceInstance));

        final CompilationUnit javaCu = modelInstances.getFirst()
                .getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        final edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit jmlCu = modelInstances.getSecond()
                .getUniqueRootEObjectIfCorrectlyTyped(
                        edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit.class);

        Java2JMLCorrespondenceAdder.addCorrespondencesForCompilationUnit(javaCu, jmlCu, ci);

        return new CorrespondenceInstanceProxy(ci);
    }

    private static ModelProvidingMock createModelProviding(final Pair<ModelInstance, ModelInstance> modelInstances)
            throws IOException {
        final ModelProvidingMock modelProviding = new ModelProvidingMock();
        modelProviding.add(modelInstances.getFirst());
        EcoreResourceBridge.saveResource(modelInstances.getFirst().getResource());
        modelProviding.add(modelInstances.getSecond());
        EcoreResourceBridge.saveResource(modelInstances.getSecond().getResource());
        return modelProviding;
    }

    private static CSSynchronizer createChangeSynchronizer(final Blackboard blackboard,
            final UserInteracting userInteracting, final SynchronisationAbortedListener syncAbortedListener,
            final Pair<ModelInstance, ModelInstance> modelInstances) throws IOException {

        final ShadowCopyFactory shadowCopyFactory = new ShadowCopyFactory() {
            @Override
            public ShadowCopy create(final CorrespondenceInstance ci, final boolean useJMLCopy) {
                return new ShadowCopyImpl(ci, new JavaModelURIProvider(modelInstances.getFirst().getURI()), useJMLCopy);
            }

            @Override
            public ShadowCopy create(final CorrespondenceInstance ci) {
                return new ShadowCopyImpl(ci, new JavaModelURIProvider(modelInstances.getFirst().getURI()));
            }
        };
        final Injector injector = Guice
                .createInjector(new CSSynchronizerModule(shadowCopyFactory, userInteracting, syncAbortedListener));

        final CSSynchronizer synchronizer = injector.getInstance(CSSynchronizer.class);
        synchronizer.setBlackboard(blackboard);
        return synchronizer;
    }

    private static URI getDummyURI() throws IOException {
        final File tmpFile = File.createTempFile("dummyuri", Utilities.getRandomString());
        tmpFile.deleteOnExit();
        tmpFile.delete();
        return URI.createFileURI(tmpFile.getAbsolutePath());
    }

    abstract protected Pair<ModelInstance, ModelInstance> getModelInstances() throws Exception;

    protected void callSynchronizer(final Change change) {
        replay(this.userInteracting);
        replay(this.syncAbortedListener);
        this.callSynchronizerInternal(change);
        verify(this.userInteracting);
        verify(this.syncAbortedListener);
    }

    private void callSynchronizerInternal(final Change change) {
        if (change instanceof EMFModelChange) {
            this.synchronizer.transformChanges2Commands(this.blackboard);
            return;
        }
        if (change instanceof CompositeChange) {
            this.synchronizer.executeTransformation((CompositeChange) change, this.blackboard);
            return;
        }
        throw new IllegalArgumentException(
                "The synchronizer can only be called with EMFModelChanges or with CompositeChanges!");
    }

    protected ModelInstance loadModelInstance(final IResourceFiles resourceFile) throws IOException {
        final Resource resource = ModelLoader.loadRelativeResource(resourceFile, this.getClass());
        return new ModelInstance(VURI.getInstance(resource), resource);
    }

    protected static void assertEqualsModel(final EObject expectedRoot, final EObject actualRoot) throws IOException {
        final IComparisonScope comparisonScope = new DefaultComparisonScope(actualRoot, expectedRoot, null);
        final Comparison comparison = EMFCompare.builder().build().compare(comparisonScope);
        final EList<Diff> differences = comparison.getDifferences();
        if (differences.size() > 0) {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final PrintStream ps = new PrintStream(baos);
            EMFComparePrettyPrinter.printDifferences(comparison, ps);
            final String failString = "Differences detected:\n\n" + baos.toString("UTF-8");
            fail(failString);
        }
    }

    protected void assertEqualsModel(final IResourceFiles expectedResourceFile, final EObject actualRoot)
            throws IOException {
        final EObject expectedRoot = ModelLoader.loadRelativeResourceModel(expectedResourceFile, this.getClass());
        assertEqualsModel(expectedRoot, actualRoot);
    }

    protected void assertNumberOfCorrespondences(final int expected, final EObject object) {
        Assert.assertEquals(expected,
                this.correspondenceInstance.getCorrespondences(CollectionBridge.toList(object)).size());
    }

    protected void assertNumberOfRealUpdateCalls(final int expected) {
        int realUpdateCalls = 0;
        for (final Pair<String, String> call : this.correspondenceInstanceUpdateRecorder.getUpdateCalls()) {
            if (!call.getFirst().equals(call.getSecond())) {
                ++realUpdateCalls;
            }
        }
        assertEquals(expected, realUpdateCalls);
    }

    protected <T extends EObject> CloneContainer<T> createClones(final T original) {
        return new CloneContainer<T>(original);
    }

    protected <T extends EObject> CloneContainer<T> createClonesForSerializedObject(final T original)
            throws IOException {
        return new CloneContainer<T>(reloadModel(original), reloadModel(original));
    }

    private static <T extends EObject> T reloadModel(final T original) throws IOException {
        final URI modelUri = original.eResource().getURI();
        final Resource resource = new ResourceSetImpl().createResource(modelUri);
        resource.load(Collections.EMPTY_MAP);
        return Utilities.getChildEqualToEObject(resource.getContents().get(0), original);
    }

    public static class CloneContainer<T extends EObject> {
        private final T oldObject;
        private final T newObject;

        public CloneContainer(final T original) {
            this(Utilities.clone(original), Utilities.clone(original));
        }

        public CloneContainer(final T firstCopy, final T secondCopy) {
            this.oldObject = firstCopy;
            this.newObject = secondCopy;
        }

        public T changed() {
            return this.newObject;
        }

        public T original() {
            return this.oldObject;
        }
    }
}
