package tools.vitruv.variability.vave.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.merge.BatchMerger;
import org.eclipse.emf.compare.merge.IBatchMerger;
import org.eclipse.emf.compare.merge.IMerger;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.JavaClasspath;
import org.emftext.language.java.JavaPackage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification;
import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.description.impl.TransactionalChangeImpl;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.change.interaction.FreeTextUserInteraction;
import tools.vitruv.framework.change.interaction.UserInteractionBase;
import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.userinteraction.PredefinedInteractionResultProvider;
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
 * Experiments with ArgoUML, JaMoPP, EMFCompare, and Vitruv Java-UML consistency preservation.
 */
@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
//@Disabled
public class ArgoUMLTest {

	@Test
	// @Disabled
	public void singleRevisionTest(@TestProject final Path projectFolder) throws Exception {
		System.out.println("MAX HEAP: " + Runtime.getRuntime().maxMemory());

		long timeStart = System.currentTimeMillis();

		// create vave instance
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new JavaDomainProvider().getDomain());
		domains.add(new UmlDomainProvider().getDomain());

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		JavaToUmlChangePropagationSpecification javaumlcps = new JavaToUmlChangePropagationSpecification();
		changePropagationSpecifications.add(javaumlcps);

		PredefinedInteractionResultProvider irp = UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null);
		FreeTextUserInteraction ftui = new InteractionFactoryImpl().createFreeTextUserInteraction();
		ftui.setText("umloutput");
		irp.addUserInteractions(new UserInteractionBase[] { ftui, ftui });

		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, irp, projectFolder);

		// set up jamopp
//		JamoppLibraryHelper.registerStdLib();
		EPackage.Registry.INSTANCE.put("http://www.emftext.org/java", JavaPackage.eINSTANCE);

		ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		resourceSet.getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
		resourceSet.getLoadOptions().put("DISABLE_LOCATION_MAP", Boolean.TRUE);

		// register jar files
		System.out.println("REGISTERING JAR FILES");
		JavaClasspath cp = JavaClasspath.get(resourceSet);
		cp.registerClassifierJar(URI.createFileURI(Paths.get("C:\\FZI\\argouml\\jars\\rt.jar").toString()));
//		FileSystem fs = FileSystems.getFileSystem(java.net.URI.create("jrt:/"));
//		Path objClassFilePath = fs.getPath("modules", "java.beans", "java/beans/Object.class");
//		cp.registerClassifierJar(URI.createFileURI(Paths.get("C:\\FZI\\argouml\\jars\\sax2.jar").toString()));
		List<Path> jarFiles = new ArrayList<>();
		// Path[] libraryFolders = new Path[] { Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-core-infra\\lib"), Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-core-model-euml\\lib"), Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-core-model-mdr\\lib") };
		Path[] libraryFolders = new Path[] { Paths.get("C:\\FZI\\argouml\\argouml\\src\\") };
		for (Path libraryFolder : libraryFolders) {
			Files.walk(libraryFolder).forEach(f -> {
				if (Files.isRegularFile(f) && f.getFileName().toString().endsWith(".jar")) {
					jarFiles.add(f);
					System.out.println("ADDED JAR FILE: " + f);
				}
			});
		}
		for (Path jarFile : jarFiles) {
			cp.registerClassifierJar(URI.createFileURI(jarFile.toString()));
		}

		// collect files to parse
		List<Path> javaFiles = new ArrayList<>();
		Path location = Paths.get("C:/FZI/git/argouml-spl-revisions-variants-temp/V/src");
		Path[] sourceFolders = new Path[] { location.resolve("argouml-core-model\\src"), location.resolve("argouml-core-model-euml\\src"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model-mdr\\build\\java"), location.resolve("argouml-core-model-mdr\\src"),
//				location.resolve("argouml-app\\src"), location.resolve("argouml-core-diagrams-sequence2\\src") 
		};
//		Path[] sourceFolders = new Path[] { Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model\\src"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model-euml\\src"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model-mdr\\build\\java"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model-mdr\\src"),
//				Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-app\\src"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-diagrams-sequence2\\src")
//				// , Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-core-umlpropertypanels\\src")
//		};
//		Path[] sourceFolders = new Path[] { Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model\\src"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model-euml\\src"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model-mdr\\build\\java"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model-mdr\\src") };
//		Path[] sourceFolders = new Path[] { Paths.get("testsrc7/p1"), Paths.get("testsrc7/p2") };
//		Path[] sourceFolders = new Path[] { Paths.get("testsrc8") };
		for (Path sourceFolder : sourceFolders) {
			Files.walk(sourceFolder).forEach(f -> {
				if (Files.isDirectory(f) && !f.equals(sourceFolder) && !f.getFileName().toString().startsWith(".") && !f.getFileName().toString().equals("META-INF") && !f.getFileName().toString().equals("test_project.marker_vitruv") && !f.getFileName().toString().equals("umloutput") && !f.getFileName().toString().contains("-") && !f.getFileName().toString().startsWith("build-eclipse")
						&& !f.getFileName().toString().startsWith("bin") && !f.getFileName().toString().startsWith("template")) {
//					Path packageInfoPath = f.resolve(Paths.get("package-info.java"));
//					try {
//						if (!Files.exists(packageInfoPath)) {
//							Files.createFile(packageInfoPath);
//							Files.writeString(packageInfoPath, "package " + sourceFolder.relativize(f).toString().replace(java.io.File.separator, ".") + ";");
//						}
//						javaFiles.add(packageInfoPath);
//						System.out.println("ADDED PACKAGE INFO FILE: " + packageInfoPath);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
				} else if (Files.isRegularFile(f) && f.getFileName().toString().endsWith(".java") && !f.getFileName().toString().equals("package-info.java")) {
					javaFiles.add(f);
					System.out.println("ADDED JAVA FILE: " + f);
				}
			});
		}

		System.out.println("TOTAL NUMBER OF JAVA FILES: " + javaFiles.size());

		// parse files
		System.out.println("PARSING JAVA FILES");
		List<Object[]> runtimemap = new ArrayList<>();
		List<ResourceSet> resourceSets = new ArrayList<>();
		List<Resource> resources = new ArrayList<>();
//		// workaround: parse GenericArgoMenuBar.java first
//		long timeStart = System.currentTimeMillis();
//		Path problematicFile = Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-app\\src\\org\\argouml\\ui\\cmd\\GenericArgoMenuBar.java");
//		System.out.println("FILE: " + problematicFile);
//		resources.add(resourceSet.getResource(URI.createFileURI(problematicFile.toString()), true));
//		javaFiles.remove(javaFiles.indexOf(problematicFile));
//		long timeDiff = System.currentTimeMillis() - timeStart;
//		System.out.println("TIME: " + timeDiff);
		for (Path javaFile : javaFiles) {
			System.out.println("FILE: " + javaFile);
//			// workaround attempt for slow parsing
//			ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//			resourceSet.getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
//			resourceSet.getLoadOptions().put("DISABLE_LOCATION_MAP", Boolean.TRUE);

			long localTimeStart = System.currentTimeMillis();
			Resource resource = resourceSet.getResource(URI.createFileURI(javaFile.toString()), true);
			long localTimeDiff = System.currentTimeMillis() - localTimeStart;
			runtimemap.add(new Object[] { localTimeDiff, javaFile.toString() });

			resources.add(resource);
//			resourceSets.add(resourceSet);
		}

//		// workaround attempt for slow parsing: merge all resources into a single resource set to use from here onward
//		ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		resourceSet.getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
//		resourceSet.getLoadOptions().put("DISABLE_LOCATION_MAP", Boolean.TRUE);
//		for (ResourceSet tempResourceSet : resourceSets) {
//			resourceSet.getResources().addAll(tempResourceSet.getResources());
//		}

		// resolve proxies
		System.out.println("RESOLVING PROXIES");
		resolveAllProxies(resourceSet);

		List<Object[]> sortedRuntimeList = runtimemap.stream().sorted((o1, o2) -> Long.valueOf(((Long) o1[0]) - ((Long) o2[0])).intValue()).collect(Collectors.toList());
		for (Object[] entry : sortedRuntimeList) {
			System.out.println("T: " + entry[0] + " - " + entry[1]);
		}

		long timeDiff = System.currentTimeMillis() - timeStart;
		System.out.println("TOTAL TIME: " + timeDiff);

		// externalize product
		System.out.println("EXTERNALIZING PRODUCT");
		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);

		// diff changes
		System.out.println("DIFFING");
		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(dummyResourceSet);
		changeRecorder.addToRecording(dummyResourceSet);
		changeRecorder.beginRecording();

		for (Resource resource : resources) {
			EMFCompare comparator = EMFCompare.builder().build();

			Resource referenceResource;
			if (vmp1.getModelInstance(resource.getURI()) != null) {
				referenceResource = vmp1.getModelInstance(resource.getURI()).getResource();
			} else {
				referenceResource = dummyResourceSet.createResource(resource.getURI());
			}

			IComparisonScope scope = new DefaultComparisonScope(resource, referenceResource, null);
			Comparison comparison = comparator.compare(scope);
			List<Diff> differences = comparison.getDifferences();

			IMerger.Registry mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance();
			IBatchMerger merger = new BatchMerger(mergerRegistry);
			merger.copyAllLeftToRight(differences, new BasicMonitor());
		}

		final TransactionalChange recordedChange = changeRecorder.endRecording();
		changeRecorder.close();

		// order recorded changes
		System.out.println("ORDERING CHANGES");
		ArrayList<EChange> newEChanges = new ArrayList<>();
		ArrayList<EChange> toAppend = new ArrayList<>();
		ArrayList<EChange> toAppend2 = new ArrayList<>();
		for (EChange change : recordedChange.getEChanges()) {
			if ((change instanceof ReplaceSingleValuedEReference) && ((ReplaceSingleValuedEReference) change).getNewValueID().contains("pathmap") && ((ReplaceSingleValuedEReference) change).getNewValueID().contains(".java#/1")) {
				// this is the workaround for the the problem vitruvius has with the ".length" field of arrays of all types outside of the actually parsed source code (e.g., java.lang.Object or java.lang.Byte).
				System.out.println("IGNORE: " + change);
			} else if ((change instanceof ReplaceSingleValuedEReference) && !((EObject) ((ReplaceSingleValuedEReference) change).getNewValue()).eResource().getURI().equals(((ReplaceSingleValuedEReference) change).getAffectedEObject().eResource().getURI())) {
				toAppend2.add(change);
				System.out.println("moved change to back: " + change);
			} else if ((change instanceof InsertEReference) && !((EObject) ((InsertEReference) change).getNewValue()).eResource().getURI().equals(((InsertEReference) change).getAffectedEObject().eResource().getURI())) {
				toAppend.add(change);
				System.out.println("moved change to back: " + change);
			} else {
				newEChanges.add(change);
			}
		}
		ArrayList<EChange> orderedChanges = new ArrayList<>();
		orderedChanges.addAll(newEChanges);
		orderedChanges.addAll(toAppend);
		orderedChanges.addAll(toAppend2);
		TransactionalChange orderedChange = new TransactionalChangeImpl(orderedChanges);

		// propagate changes into product
		System.out.println("PROPAGATING CHANGES INTO PRODUCT");
		vmp1.propagateChange(orderedChange);

		// internalize changes in product into system
		System.out.println("INTERNALIZING CHANGES IN PRODUCT INTO SYSTEM");
		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1

		System.out.println("DONE");
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

		// call this method again, because the resolving might have triggered loading of additional resources that may also contain references that need to be resolved.
		return !failure && resolveAllProxiesRecursive(rs, resourcesProcessed);
	}

}
