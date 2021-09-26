package tools.vitruv.variability.vave.tests;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

//import cipm.consistency.commitintegration.diff.util.JavaModelComparator;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
//import jamopp.options.ParserOptions;
//import jamopp.parser.jdt.JaMoPPJDTParser;
//import jamopp.parser.jdt.singlefile.JaMoPPJDTSingleFileParser;
import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.variability.vave.VirtualProductModel;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.impl.VirtualVaVeModeIImpl;
import vavemodel.Configuration;
import vavemodel.FeatureOption;
import vavemodel.VavemodelFactory;

/**
 * This test is for the new version of JaMoPP which is currently not supported by the Vitruv Java Domain and therefore disabled.
 */
@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
@Disabled
public class JamoppNewTest {

	// TODO 0: Load a single java file into an Ecore model instance.
	// TODO 1: Multiple depending java files loading into same resourceset
	// TODO 2: Diffing / EMFCompare of two java model instances
	// TODO 3: Internalize first loaded model into a vave instance
	// TODO 4: Load second model, diff it against first model, and then propagate recorded changes into externalized product and internalize it again
	// TODO 5: Also load UML domain and UML2Java consistency preservation in vave and do steps of 4.

//	@Test
//	public void loadModelFromCodeTest() throws IOException {
//		JamoppLibraryHelper.registerStdLib();
//		JamoppParser jp = new JamoppParser();
//		CompilationUnit cu = jp.parseCompilationUnitFromDisk("testresources\\main\\Main.java");
//		System.out.println(cu);
//	}

//	@Test
//	public void loadModelFromCodeTest() throws IOException {
//		JaMoPPJDTParser jdtp = new JaMoPPJDTParser();
//		ResourceSet rs = jdtp.parseDirectory(Paths.get("testresources"));
//		System.out.println(rs);
//	}
//
//	@Test
//	public void loadModelFromCodeTest2() throws IOException {
//		Path dir = Paths.get("testresources");
//		Path target = Paths.get("testresources/model");
//		
//		// 1. Parse the code.
//		ParserOptions.CREATE_LAYOUT_INFORMATION.setValue(Boolean.FALSE);
//		ParserOptions.RESOLVE_EVERYTHING.setValue(Boolean.TRUE);
//		ParserOptions.REGISTER_LOCAL.setValue(Boolean.TRUE);
//		JaMoPPJDTSingleFileParser parser = new JaMoPPJDTSingleFileParser();
//		parser.setResourceSet(new ResourceSetImpl());
////		parser.setExclusionPatterns(CommitIntegrationSettingsContainer
////				.getSettingsContainer().getProperty(SettingKeys.JAVA_PARSER_EXCLUSION_PATTERNS).
////				split(";"));
//		System.out.println("Parsing " + dir.toString());
//		ResourceSet resourceSet = parser.parseDirectory(dir);
//		resolveAllProxies(resourceSet);
//		System.out.println("Parsed " + resourceSet.getResources().size() + " files.");
//		
////		// 3. Create one resource with all Java models.
////		System.out.println("Creating one resource with all Java models.");
////		ResourceSet next = new ResourceSetImpl();
////		Resource all = next.createResource(URI.createFileURI(target.toAbsolutePath().toString()));
////		for (Resource r : new ArrayList<>(resourceSet.getResources())) {
////			all.getContents().addAll(r.getContents());
////		}
////		System.out.println(all);
//	}
//	
//	@Test // this is TODO 0
//	public void loadModelFromCodeViaEMFTest() {
//		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		JaMoPPJDTParser jdtp = new JaMoPPJDTParser();
//		jdtp.setResourceSet(resourceSet);
//		Resource resource = jdtp.parseFile(Paths.get("testresources/main/Person.java"));
//		System.out.println(resourceSet);
//	}
//
//	@Test // this is TODO 1
//	public void loadMultipleJavaFiles() {
//		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		JaMoPPJDTParser jdtp = new JaMoPPJDTParser();
//		jdtp.setResourceSet(resourceSet);
//		Resource resourceMain = jdtp.parseFile(Paths.get("testresources/main/Main.java"));
//		Resource resourcePerson = jdtp.parseFile(Paths.get("testresources/main/Person.java"));
//		System.out.println(resourceSet);
//	}
//
//	@Test // this is TODO 2
//	public void diffJavaFiles() {
//		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		JaMoPPJDTParser jdtp = new JaMoPPJDTParser();
//		jdtp.setResourceSet(resourceSet);
//		Resource resourceMain = jdtp.parseFile(Paths.get("testresources/main/Main.java"));
//		Resource resourcePerson = jdtp.parseFile(Paths.get("testresources/main/Person.java"));
//
//		final ResourceSet resourceSet2 = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		JaMoPPJDTParser jdtp2 = new JaMoPPJDTParser();
//		jdtp.setResourceSet(resourceSet);
//		Resource resourceMain2 = jdtp.parseFile(Paths.get("testresources2/main/Main.java"));
//		Resource resourcePerson2 = jdtp.parseFile(Paths.get("testresources2/main/Person.java"));
//
//		EMFCompare comparator = EMFCompare.builder().build();
//		// compare resource sets directly only works if the resources represent the same files. if the resources represent the same files but in a different location, they will not be matched.
//		IComparisonScope scope = new DefaultComparisonScope(resourceMain, resourceMain2, null);
//		Comparison res = comparator.compare(scope);
//
//		EList<Diff> diffs = res.getDifferences();
//		System.out.println(resourceMain);
//	}
//
//	@Test // this is TODO 2.5
//	public void diffJavaFilesAndMerge() {
//		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		JaMoPPJDTParser jdtp = new JaMoPPJDTParser();
//		jdtp.setResourceSet(resourceSet);
//		Resource resourceMain = jdtp.parseFile(Paths.get("testresources/main/Main.java"));
//		Resource resourcePerson = jdtp.parseFile(Paths.get("testresources/main/Person.java"));
//
//		final ResourceSet resourceSet2 = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		JaMoPPJDTParser jdtp2 = new JaMoPPJDTParser();
//		jdtp.setResourceSet(resourceSet);
//		Resource resourceMain2 = jdtp.parseFile(Paths.get("testresources2/main/Main.java"));
//		Resource resourcePerson2 = jdtp.parseFile(Paths.get("testresources2/main/Person.java"));
//
////		EMFCompare comparator = EMFCompare.builder().build();
////		// compare resource sets directly only works if the resources represent the same files. if the resources represent the same files but in a different location, they will not be matched.
////		IComparisonScope scope = new DefaultComparisonScope(resourceMain, resourceMain2, null);
////		Comparison comparison = comparator.compare(scope);
////
////		EList<Diff> differences = comparison.getDifferences();
////		
////	    IMerger.Registry mergerRegistry = new IMerger.RegistryImpl();
////	    IBatchMerger merger = new BatchMerger(mergerRegistry);
////	    merger.copyAllLeftToRight(differences, new BasicMonitor());
//		
//		Comparison comparison = JavaModelComparator.compareJavaModels(resourcePerson, resourcePerson2, null, null, null);
//		List<Diff> differences = comparison.getDifferences();
//		
//		System.out.println(resourceMain);
//	}

	@Test // this is TODO 3
	public void internalizeJavaIntoVave(@TestProject final Path projectFolder) throws Exception {
		// create vave instance
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new JavaDomainProvider().getDomain());
		domains.add(new UmlDomainProvider().getDomain());

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
//		JavaToUmlChangePropagationSpecification javaumlcps = new JavaToUmlChangePropagationSpecification();
//		changePropagationSpecifications.add(javaumlcps);

		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), projectFolder);

		// load java files into models
//		JamoppLibraryHelper.registerStdLib();
		final ResourceSet javaResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		Resource resourceMain = ResourceSetUtil.loadOrCreateResource(javaResourceSet, URI.createFileURI("testresources\\main\\Main.java"));
		Resource resourcePerson = ResourceSetUtil.loadOrCreateResource(javaResourceSet, URI.createFileURI("testresources\\main\\Person.java"));
		resolveAllProxies(javaResourceSet);

		// diff against empty resource
		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource dummyResource = ResourceSetUtil.loadOrCreateResource(dummyResourceSet, URI.createFileURI("dummy\\Person.java"));

//		JaMoPPFeatureFilter jamoppFeatureFilter = new JaMoPPFeatureFilter(new PackageIgnoreChecker(List.of()));
//		DiffBuilder diffProcessor = new DiffBuilder();
//		DefaultDiffEngine diffEngine = new DefaultDiffEngine(diffProcessor) {
//			@Override
//			protected FeatureFilter createFeatureFilter() {
//				return jamoppFeatureFilter;
//			}
//		};
//		
//		HierarchicalMatchEngineFactory matchEngineFactory = JavaMatchEngineFactoryGenerator.generateMatchEngineFactory();
//		matchEngineFactory.setRanking(20);
//		Object engineRegistry = EMFCompareRCPPlugin.getDefault().getMatchEngineFactoryRegistry();
//		engineRegistry.add(matchEngineFactory);
//		
//		Object builder = EMFCompare.builder()
//			.setMatchEngineFactoryRegistry(engineRegistry)
//			.setDiffEngine(diffEngine);
//
//		EMFCompare comparator = builder().build();
//		
//		// compare resource sets directly only works if the resources represent the same files. if the resources represent the same files but in a different location, they will not be matched.
//		IComparisonScope scope = new DefaultComparisonScope(resourcePerson, dummyResource, null);
//		Comparison res = comparator.compare(scope);
//		List<Diff> differences = res.getDifferences();

//		Comparison comparison = JavaModelComparator.compareJavaModels(resourcePerson, dummyResource, null, null, null);
//		List<Diff> differences = comparison.getDifferences();

		// ...
		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();

		final Resource monitoredResource = ResourceSetUtil.loadOrCreateResource(resourceSet, URI.createFileURI("testresources\\main\\Person.java"));
		resolveAllProxies(resourceSet);
		// Let's merge every single diff
//	    IMerger.Registry mergerRegistry = new IMerger.RegistryImpl();
//	    IBatchMerger merger = new BatchMerger(mergerRegistry);
//	    merger.copyAllLeftToRight(differences, new BasicMonitor());

		final TransactionalChange recordedChange = changeRecorder.endRecording();
		// propagate recorded changes into vmp1
		vmp1.propagateChange(recordedChange);

		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1

	}

	protected static void resolveAllProxies(ResourceSet rs) {
		if (!resolveAllProxiesRecursive(rs, 0)) {
			System.err.println("Resolution of some Proxies failed...");
			Iterator<Notifier> it = rs.getAllContents();
			while (it.hasNext()) {
				Notifier next = it.next();
				if (next instanceof EObject) {
					EObject o = (EObject) next;
					if (o.eIsProxy()) {
						try {
							it.remove();
						} catch (UnsupportedOperationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	protected static boolean resolveAllProxiesRecursive(ResourceSet rs, int resourcesProcessedBefore) {
		boolean failure = false;
		List<EObject> eobjects = new LinkedList<EObject>();
		for (Iterator<Notifier> i = rs.getAllContents(); i.hasNext();) {
			Notifier next = i.next();
			if (next instanceof EObject) {
				eobjects.add((EObject) next);
			}
		}
		int resourcesProcessed = rs.getResources().size();
		if (resourcesProcessed == resourcesProcessedBefore) {
			return true;
		}

		System.out.println("Resolving cross-references of " + eobjects.size() + " EObjects.");
		int resolved = 0;
		int notResolved = 0;
		int eobjectCnt = 0;
		for (EObject next : eobjects) {
			eobjectCnt++;
			if (eobjectCnt % 1000 == 0) {
				System.out.println(eobjectCnt + "/" + eobjects.size() + " done: Resolved " + resolved + " crossrefs, " + notResolved + " crossrefs could not be resolved.");
			}

			InternalEObject nextElement = (InternalEObject) next;
			for (EObject crElement : nextElement.eCrossReferences()) {
				crElement = EcoreUtil.resolve(crElement, rs);
				if (crElement.eIsProxy()) {
					failure = true;
					notResolved++;
					System.out.println("Can not find referenced element in classpath: " + ((InternalEObject) crElement).eProxyURI());
				} else {
					resolved++;
				}
			}
		}

		System.out.println(eobjectCnt + "/" + eobjects.size() + " done: Resolved " + resolved + " crossrefs, " + notResolved + " crossrefs could not be resolved.");

		// call this method again, because the resolving might have triggered loading
		// of additional resources that may also contain references that need to be resolved.
		return !failure && resolveAllProxiesRecursive(rs, resourcesProcessed);
	}

}
