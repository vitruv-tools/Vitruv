package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

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
import org.emftext.language.java.containers.CompilationUnit;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.correspondences.Java2JMLCorrespondenceAdder;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.Utilities;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopy;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyImpl;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JMLMetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JaMoPPMetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelImpl;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader.IResourceFiles;

public class CommonTasks {

    public static class JavaModelURIProvider implements ModelURIProvider {

        private final List<VURI> uris = new ArrayList<VURI>();

        public JavaModelURIProvider(VURI modelUri) {
            uris.add(modelUri);
        }

        @Override
        public List<VURI> getAllRelevantURIs() {
            return uris;
        }

    }
    
    public static ShadowCopyFactory createShadowCopyFactory(final ModelInstance javaModelInstance) {
        return new ShadowCopyFactory() {
            @Override
            public ShadowCopy create(CorrespondenceModel ci, boolean useJMLCopy) {
                return new ShadowCopyImpl(ci, new JavaModelURIProvider(javaModelInstance.getURI()), useJMLCopy);
            }

            @Override
            public ShadowCopy create(CorrespondenceModel ci) {
                return new ShadowCopyImpl(ci, new JavaModelURIProvider(javaModelInstance.getURI()));
            }
        };
    }
    
    public static CorrespondenceModel createCorrespondenceModel(Mapping mapping, ModelProviding modelProviding,
            Pair<ModelInstance, ModelInstance> modelInstances) throws IOException {
        URI dummyURICorrespondenceModel = getDummyURI();

        CorrespondenceModel ci = new CorrespondenceModelImpl(mapping, modelProviding,
                VURI.getInstance(dummyURICorrespondenceModel), new ResourceImpl(dummyURICorrespondenceModel));

        CompilationUnit javaCu = modelInstances.getFirst().getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.CompilationUnit jmlCu = modelInstances.getSecond()
                .getUniqueRootEObjectIfCorrectlyTyped(edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.CompilationUnit.class);

        Java2JMLCorrespondenceAdder.addCorrespondencesForCompilationUnit(javaCu, jmlCu, ci);

        return ci;
    }

    public static ModelProvidingMock createModelProviding(Pair<ModelInstance, ModelInstance> modelInstances)
            throws IOException {
        ModelProvidingMock modelProviding = new ModelProvidingMock();
        modelProviding.add(modelInstances.getFirst());
        EcoreResourceBridge.saveResource(modelInstances.getFirst().getResource());
        modelProviding.add(modelInstances.getSecond());
        EcoreResourceBridge.saveResource(modelInstances.getSecond().getResource());
        return modelProviding;
    }
    
    public static Mapping createMapping() {
        Metamodel mmJava = new JaMoPPMetaModelProvider().getMetaModel();
        Metamodel mmJml = new JMLMetaModelProvider().getMetaModel();
        return new Mapping(mmJava, mmJml);
    }
    
    public static URI getDummyURI() throws IOException {
        File tmpFile = File.createTempFile("dummyuri", Utilities.getRandomString());
        tmpFile.deleteOnExit();
        tmpFile.delete();
        return URI.createFileURI(tmpFile.getAbsolutePath());
    }
    
    public static ModelInstance loadModelInstance(IResourceFiles resourceFile, Object caller) throws IOException {
        return loadModelInstance(resourceFile, caller.getClass());
    }
    
    public static ModelInstance loadModelInstance(IResourceFiles resourceFile, Class<?> caller) throws IOException {
        Resource resource = ModelLoader.loadRelativeResource(resourceFile, caller);
        return new ModelInstance(VURI.getInstance(resource), resource);
    }
    
    public static ModelInstance loadModelInstance(IResourceFiles resourceFile, Class<?> caller, boolean serializeFirst) throws IOException {
        Resource resource = null;
        if (serializeFirst) {
            resource = ModelLoader.serializeAndLoadRelativeResource(resourceFile, caller);
        } else {
            resource = ModelLoader.loadRelativeResource(resourceFile, caller);            
        }
        return new ModelInstance(VURI.getInstance(resource), resource);
    }
    
    public static void assertEqualsModel(EObject expectedRoot, EObject actualRoot) throws IOException {
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
    
}
