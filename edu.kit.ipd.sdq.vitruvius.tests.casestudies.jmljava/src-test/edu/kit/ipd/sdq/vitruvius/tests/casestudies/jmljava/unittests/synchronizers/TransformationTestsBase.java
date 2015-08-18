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
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ContractsBuilder;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.Initializer;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelProvidingMock;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader.IResourceFiles;

public abstract class TransformationTestsBase {
    
    private static class CSSynchronizerModule extends AbstractModule {

        private final ShadowCopyFactory shadowCopyFactory;
        private final UserInteracting userInteracting;
        private final SynchronisationAbortedListener syncAbortedListener;

        CSSynchronizerModule(ShadowCopyFactory shadowCopyFactory, UserInteracting userInteracting, SynchronisationAbortedListener syncAbortedListener) {
            this.shadowCopyFactory = shadowCopyFactory;
            this.userInteracting = userInteracting;
            this.syncAbortedListener = syncAbortedListener;
        }

        @Override
        protected void configure() {
            bind(ShadowCopyFactory.class).toInstance(shadowCopyFactory);
            bind(UserInteracting.class).toInstance(userInteracting);
            bind(SynchronisationAbortedListener.class).toInstance(syncAbortedListener);
        }

    }

    private static class JavaModelURIProvider implements ModelURIProvider {

        private final List<VURI> uris = new ArrayList<VURI>();

        public JavaModelURIProvider(VURI modelUri) {
            uris.add(modelUri);
        }

        @Override
        public List<VURI> getAllRelevantURIs() {
            return uris;
        }

    }

    private static class CorrespondenceInstanceProxy implements InvocationHandler {
        private final CorrespondenceInstance ci;
        private final List<Pair<String, String>> updateCalls = new ArrayList<Pair<String, String>>();

        public CorrespondenceInstanceProxy(CorrespondenceInstance ci) {
            this.ci = ci;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("update") && args.length == 2) {
                String firstTUIDString = getTUIDString(args[0]);
                String secondTUIDString = getTUIDString(args[1]);
                updateCalls.add(new Pair<String, String>(firstTUIDString, secondTUIDString));
            }
            
            return method.invoke(ci, args);
        }
        
        private String getTUIDString(Object arg) {
            if (arg instanceof EObject) {
                return ci.calculateTUIDFromEObject((EObject)arg).toString();
            }
            if (arg instanceof TUID) {
                return ((TUID)arg).toString();
            }
            throw new IllegalArgumentException();
        }

        public List<Pair<String, String>> getUpdateCalls() {
            return updateCalls;
        }
    }

    private static final Mapping MAPPING_JAVA2JML = constructMapping();
    private ModelProvidingMock modelProviding;
    private CorrespondenceInstanceProxy correspondenceInstanceUpdateRecorder;
    protected CSSynchronizer synchronizer;
    protected CorrespondenceInstance correspondenceInstance;
    protected UserInteracting userInteracting;
    protected SynchronisationAbortedListener syncAbortedListener;

    private static Mapping constructMapping() {
        Initializer.initLogging();
        Initializer.initJaMoPP();
        Initializer.initJML();

        Metamodel mmJava = new JaMoPPMetaModelProvider().getMetaModel();
        Metamodel mmJml = new JMLMetaModelProvider().getMetaModel();
        return new Mapping(mmJava, mmJml);
    }

    @Before
    public void setup() throws Exception {
        Pair<ModelInstance, ModelInstance> modelInstances = getModelInstances();
        modelProviding = createModelProviding(modelInstances);
        correspondenceInstanceUpdateRecorder = createCorrespondenceInstanceProxy(modelProviding, modelInstances);
        correspondenceInstance = createCorrespondenceInstance(correspondenceInstanceUpdateRecorder);
        userInteracting = createUserInteracting();
        syncAbortedListener = createSyncAbortedListener();
        synchronizer = createChangeSynchronizer(correspondenceInstance, userInteracting, syncAbortedListener, modelInstances);
    }

    private static SynchronisationAbortedListener createSyncAbortedListener() {
        return EasyMock.createStrictMock(SynchronisationAbortedListener.class);
    }

    private static UserInteracting createUserInteracting() {
        return EasyMock.createStrictMock(UserInteracting.class);
    }

    private static CorrespondenceInstance createCorrespondenceInstance(CorrespondenceInstanceProxy proxy) {
        return (CorrespondenceInstance) Proxy.newProxyInstance(CorrespondenceInstanceProxy.class.getClassLoader(),
                new Class[] { CorrespondenceInstance.class }, proxy);
    }

    private static CorrespondenceInstanceProxy createCorrespondenceInstanceProxy(ModelProviding modelProviding,
            Pair<ModelInstance, ModelInstance> modelInstances) throws IOException {
        URI dummyURICorrespondenceInstance = getDummyURI();

        CorrespondenceInstance ci = ContractsBuilder.createCorrespondenceInstance(MAPPING_JAVA2JML, modelProviding,
                VURI.getInstance(dummyURICorrespondenceInstance), new ResourceImpl(dummyURICorrespondenceInstance));

        CompilationUnit javaCu = modelInstances.getFirst().getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit jmlCu = modelInstances.getSecond()
                .getUniqueRootEObjectIfCorrectlyTyped(edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit.class);

        Java2JMLCorrespondenceAdder.addCorrespondencesForCompilationUnit(javaCu, jmlCu, ci);

        return new CorrespondenceInstanceProxy(ci);
    }

    private static ModelProvidingMock createModelProviding(Pair<ModelInstance, ModelInstance> modelInstances)
            throws IOException {
        ModelProvidingMock modelProviding = new ModelProvidingMock();
        modelProviding.add(modelInstances.getFirst());
        EcoreResourceBridge.saveResource(modelInstances.getFirst().getResource());
        modelProviding.add(modelInstances.getSecond());
        EcoreResourceBridge.saveResource(modelInstances.getSecond().getResource());
        return modelProviding;
    }

    private static CSSynchronizer createChangeSynchronizer(final CorrespondenceInstance correspondenceInstance,
            UserInteracting userInteracting, SynchronisationAbortedListener syncAbortedListener, final Pair<ModelInstance, ModelInstance> modelInstances) throws IOException {

        ShadowCopyFactory shadowCopyFactory = new ShadowCopyFactory() {
            @Override
            public ShadowCopy create(CorrespondenceInstance ci, boolean useJMLCopy) {
                return new ShadowCopyImpl(ci, new JavaModelURIProvider(modelInstances.getFirst().getURI()), useJMLCopy);
            }

            @Override
            public ShadowCopy create(CorrespondenceInstance ci) {
                return new ShadowCopyImpl(ci, new JavaModelURIProvider(modelInstances.getFirst().getURI()));
            }
        };
        Injector injector = Guice.createInjector(new CSSynchronizerModule(shadowCopyFactory, userInteracting, syncAbortedListener));

        CSSynchronizer synchronizer = injector.getInstance(CSSynchronizer.class);
        synchronizer.setCorrespondenceInstance(correspondenceInstance);
        return synchronizer;
    }

    private static URI getDummyURI() throws IOException {
        File tmpFile = File.createTempFile("dummyuri", Utilities.getRandomString());
        tmpFile.deleteOnExit();
        tmpFile.delete();
        return URI.createFileURI(tmpFile.getAbsolutePath());
    }

    abstract protected Pair<ModelInstance, ModelInstance> getModelInstances() throws Exception;

    protected EMFChangeResult callSynchronizer(Change change) {
        replay(userInteracting);
        replay(syncAbortedListener);
        EMFChangeResult result = callSynchronizerInternal(change);
        verify(userInteracting);
        verify(syncAbortedListener);
        return result;
    }
    
    private EMFChangeResult callSynchronizerInternal(Change change) {
        if (change instanceof EMFModelChange) {
            return synchronizer.transformChanges2Commands(null);
        }
        if (change instanceof CompositeChange) {
            return synchronizer.executeTransformation((CompositeChange)change, correspondenceInstance);
        }
        throw new IllegalArgumentException("The synchronizer can only be called with EMFModelChanges or with CompositeChanges!");
    }

    protected ModelInstance loadModelInstance(IResourceFiles resourceFile) throws IOException {
        Resource resource = ModelLoader.loadRelativeResource(resourceFile, this.getClass());
        return new ModelInstance(VURI.getInstance(resource), resource);
    }

    protected static void assertEqualsModel(EObject expectedRoot, EObject actualRoot) throws IOException {
        IComparisonScope comparisonScope = new DefaultComparisonScope(actualRoot, expectedRoot, null);
        Comparison comparison = EMFCompare.builder().build().compare(comparisonScope);
        EList<Diff> differences = comparison.getDifferences();
        if (differences.size() > 0) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            EMFComparePrettyPrinter.printDifferences(comparison, ps);
            String failString = "Differences detected:\n\n" + baos.toString("UTF-8");
            fail(failString);
        }
    }

    protected void assertEqualsModel(IResourceFiles expectedResourceFile, EObject actualRoot) throws IOException {
        EObject expectedRoot = ModelLoader.loadRelativeResourceModel(expectedResourceFile,
                this.getClass());
        assertEqualsModel(expectedRoot, actualRoot);
    }

    protected void assertNumberOfCorrespondences(int expected, EObject object) {
        Assert.assertEquals(expected, correspondenceInstance.getAllCorrespondences(object).size());
    }
    
    protected void assertNumberOfRealUpdateCalls(int expected) {
        int realUpdateCalls = 0;
        for (Pair<String, String> call : correspondenceInstanceUpdateRecorder.getUpdateCalls()) {
            if (!call.getFirst().equals(call.getSecond())) {
                ++realUpdateCalls;
            }
        }
        assertEquals(expected, realUpdateCalls);
    }
    
    protected <T extends EObject> CloneContainer<T> createClones(T original) {
        return new CloneContainer<T>(original);
    }
    
    protected <T extends EObject> CloneContainer<T> createClonesForSerializedObject(T original) throws IOException {
        return new CloneContainer<T>(reloadModel(original), reloadModel(original));
    }
    
    private static <T extends EObject> T reloadModel(T original) throws IOException {
        URI modelUri = original.eResource().getURI();
        Resource resource = new ResourceSetImpl().createResource(modelUri);
        resource.load(Collections.EMPTY_MAP);
        return Utilities.getChildEqualToEObject(resource.getContents().get(0), original);
    }
   
    public static class CloneContainer<T extends EObject> {
        private final T oldObject;
        private final T newObject;
        
        public CloneContainer(T original) {
            this(Utilities.clone(original), Utilities.clone(original));
        }
        
        public CloneContainer(T firstCopy, T secondCopy) {
            oldObject = firstCopy;
            newObject = secondCopy;
        }
        
        public T changed() {
            return newObject;
        }
        
        public T original() {
            return oldObject;
        }
    }
}
