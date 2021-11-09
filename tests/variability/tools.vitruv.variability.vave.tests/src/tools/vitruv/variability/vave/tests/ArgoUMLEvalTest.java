package tools.vitruv.variability.vave.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
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
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.diff.FeatureFilter;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.diff.IDiffProcessor;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.merge.BatchMerger;
import org.eclipse.emf.compare.merge.IBatchMerger;
import org.eclipse.emf.compare.merge.IMerger;
import org.eclipse.emf.compare.rcp.EMFCompareRCPPlugin;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.EqualityHelper;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.JavaClasspath;
import org.emftext.language.java.JavaClasspath.Initializer;
import org.emftext.language.java.JavaPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.cache.CacheBuilder;

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
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.variability.vave.VirtualProductModel;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.impl.FeatureModel;
import tools.vitruv.variability.vave.impl.VirtualVaVeModeIImpl;
import tools.vitruv.variability.vave.tests.comparator.HierarchicalMatchEngineFactory;
import tools.vitruv.variability.vave.tests.comparator.SimilarityChecker;
import vavemodel.Configuration;
import vavemodel.Conjunction;
import vavemodel.CrossTreeConstraint;
import vavemodel.Expression;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.FeatureRevision;
import vavemodel.GroupType;
import vavemodel.SystemRevision;
import vavemodel.TreeConstraint;
import vavemodel.Variable;
import vavemodel.VavemodelFactory;

/**
 * Test that runs the entire ArgoUML evaluation as batch.
 */
@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
//@Disabled
public class ArgoUMLEvalTest {

	protected VirtualVaVeModel vave = null;

	protected int productNumber = 0;
	protected int sysrev = -1;

	protected final Path projectFolder = Paths.get("C:\\FZI\\vave-project-folder");
	protected final Path vaveResourceLocation = Paths.get("C:\\FZI\\vave-resource-location\\temp");
	protected final Path vaveProjectMarker = vaveResourceLocation.resolve("test_project.marker_vitruv");

	@BeforeEach
	public void setUp() throws Exception {
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
		for (int i = 0; i < 100; i++)
			irp.addUserInteractions(new UserInteractionBase[] { ftui, ftui });

		this.vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, irp, projectFolder);

		// set up jamopp
		JavaClasspath.getInitializers().clear();
		JavaClasspath.getInitializers().add(new Initializer() {
			@Override
			public void initialize(Resource resource) {
			}

			@Override
			public boolean requiresLocalClasspath() {
				return true;
			}

			@Override
			public boolean requiresStdLib() {
				return false;
			}
		});
		EPackage.Registry.INSTANCE.put("http://www.emftext.org/java", JavaPackage.eINSTANCE);

		this.productNumber = 0;
	}

	protected Collection<Resource> parse(Path location) throws Exception {
		ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		resourceSet.getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
		resourceSet.getLoadOptions().put("DISABLE_LOCATION_MAP", Boolean.TRUE);
		resourceSet.getLoadOptions().put(JavaClasspath.OPTION_USE_LOCAL_CLASSPATH, Boolean.TRUE);
		resourceSet.getLoadOptions().put(JavaClasspath.OPTION_REGISTER_STD_LIB, Boolean.FALSE);

		Path vitruv_project_folder = location.getParent().resolve("test_project.marker_vitruv");
		if (!Files.exists(vitruv_project_folder))
			Files.createDirectories(vitruv_project_folder);

		// register jar files
		System.out.println("REGISTERING JAR FILES");
		JavaClasspath cp = JavaClasspath.get(resourceSet);
		cp.registerClassifierJar(URI.createFileURI(Paths.get("resources\\rt.jar").toAbsolutePath().toString()));
		cp.registerClassifierJar(URI.createFileURI(Paths.get("resources\\jmi.jar").toAbsolutePath().toString()));
		List<Path> jarFiles = new ArrayList<>();
		// Path[] libraryFolders = new Path[] { location };
		Path[] libraryFolders = new Path[] { Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\") };
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
		Path[] sourceFolders = new Path[] { location.resolve("argouml-core-model\\src"), location.resolve("argouml-core-model-euml\\src"), location.resolve("argouml-core-model-mdr\\src"), location.resolve("argouml-app\\src"), location.resolve("argouml-core-diagrams-sequence2\\src") };
		// Path[] sourceFolders = new Path[] { location };
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

					// prefill the uriconverter
					Path relPath = sourceFolder.relativize(f);
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < relPath.getNameCount(); i++) {
						sb.append(relPath.getName(i));
						if (i < relPath.getNameCount() - 1)
							sb.append(".");
					}
					resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/" + sb.toString()), URI.createFileURI(f.toString()));
					System.out.println("PATHMAP: " + URI.createURI("pathmap:/javaclass/" + sb.toString()));
				}
			});
		}
		// manual workaround for now
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.model.mdr.UndoCoreHelperDecorator$StringSetter.java"), URI.createFileURI(location.resolve("argouml-core-model-mdr/src/org/argouml/model/mdr/UndoCoreHelperDecorator.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.diagram.DiagramFactory$DiagramType.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/diagram/DiagramFactory.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.diagram.sequence.ui.FigLifeLine$FigLifeLineHandler.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/diagram/sequence/ui/FigLifeLine.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.diagram.DiagramSettings$StereotypeStyle.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/diagram/DiagramSettings.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.diagram.sequence.ui.FigClassifierRole$TempFig.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/diagram/sequence/ui/FigClassifierRole.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.ui.ProjectBrowser$Position.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/ui/ProjectBrowser.java").toString()));

		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.reveng.SettingsTypes$Setting.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/reveng/SettingsTypes.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.reveng.SettingsTypes$Setting2.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/reveng/SettingsTypes.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.reveng.SettingsTypes$UniqueSelection.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/reveng/SettingsTypes.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.reveng.SettingsTypes$UniqueSelection2.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/reveng/SettingsTypes.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.reveng.SettingsTypes$UserString.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/reveng/SettingsTypes.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.reveng.SettingsTypes$UserString2.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/reveng/SettingsTypes.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.reveng.SettingsTypes$BooleanSelection.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/reveng/SettingsTypes.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.reveng.SettingsTypes$BooleanSelection2.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/reveng/SettingsTypes.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.reveng.SettingsTypes$PathSelection.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/reveng/SettingsTypes.java").toString()));
		resourceSet.getURIConverter().getURIMap().put(URI.createURI("pathmap:/javaclass/org.argouml.uml.reveng.SettingsTypes$PathListSelection.java"), URI.createFileURI(location.resolve("argouml-app/src/org/argouml/uml/reveng/SettingsTypes.java").toString()));

		// parse files
		System.out.println("PARSING JAVA FILES");
		List<Object[]> runtimemap = new ArrayList<>();
		List<Resource> resources = new ArrayList<>();
		for (Path javaFile : javaFiles) {
			System.out.println("FILE: " + javaFile);

			long localTimeStart = System.currentTimeMillis();
			Resource resource = resourceSet.getResource(URI.createFileURI(javaFile.toString()), true);
			long localTimeDiff = System.currentTimeMillis() - localTimeStart;
			runtimemap.add(new Object[] { localTimeDiff, javaFile.toString() });

			resources.add(resource);
		}

		System.out.println("NUM RESOURCES PARSED: " + resources.size());

		System.out.println("NUM RESOURCES IN RS: " + resourceSet.getResources().size());

		for (Resource resource : resources) {
			if (resource.getURI().toString().contains("argouml") && resource.getURI().toString().contains("pathmap"))
				System.out.println("DDD: " + resource.getURI());
		}

		for (Resource resource : resourceSet.getResources()) {
			if (resource.getURI().toString().contains("argouml") && resource.getURI().toString().contains("pathmap"))
				System.out.println("EEE: " + resource.getURI());
		}

		// resolve proxies
		System.out.println("RESOLVING PROXIES");
		resolveAllProxies(resourceSet);

		// convert pathmap uris to filesystem uris
		for (Resource resource : resources) {
			if (resource.getURI().toString().contains("pathmap:/javaclass/org.argouml"))
				if (resourceSet.getURIConverter().getURIMap().get(resource.getURI()) != null)
					resource.setURI(resourceSet.getURIConverter().getURIMap().get(resource.getURI()));
				else if (resource.getURI().toString().contains("$")) {
					String uriString = resource.getURI().toString().replace("$", ".");
					URI tempUri = resourceSet.getURIConverter().getURIMap().get(URI.createURI(uriString));
					if (tempUri != null)
						resource.setURI(tempUri);
					// TODO: add mapping to uri converter?
				}
//				else {
//					String filePathString = resource.getURI().toString().substring(0, resource.getURI().toString().lastIndexOf("$")) + ".java";
//					resourceSet.getURIConverter().getURIMap().put(resource.getURI(), URI.createURI(filePathString));
//					resource.setURI(resourceSet.getURIConverter().getURIMap().get(resource.getURI()));
//					System.out.println("XXX: " + resource.getURI());
//				}
		}

		// change uri of resources
		for (Resource resource : resources) {
			Path relativeResourcePath = null;
			String fileString = resource.getURI().toFileString();
			if (fileString != null) {
				Path resourcePath = Paths.get(fileString);
				if (resourcePath.startsWith(location))
					relativeResourcePath = location.relativize(resourcePath);
				URI vaveURI = URI.createFileURI(vaveResourceLocation.resolve(relativeResourcePath).toString());
				System.out.println("URI: " + vaveURI);
				resource.setURI(vaveURI);
			} else {
				System.out.println("!!!!!!: " + resource.getURI());
			}
		}

		System.out.println("NUM RESOURCES IN RS AFTER PROXY RESOLUTIONS: " + resourceSet.getResources().size());

		List<Object[]> sortedRuntimeList = runtimemap.stream().sorted((o1, o2) -> Long.valueOf(((Long) o1[0]) - ((Long) o2[0])).intValue()).collect(Collectors.toList());
		for (Object[] entry : sortedRuntimeList) {
			System.out.println("T: " + entry[0] + " - " + entry[1]);
		}

		return resources;
	}

	protected VirtualProductModel externalize(Configuration configuration, Path projectFolder) throws Exception {
		if (!Files.exists(vaveProjectMarker))
			Files.createDirectories(vaveProjectMarker);

		// externalize product
		System.out.println("EXTERNALIZING PRODUCT");
		final VirtualProductModel vmp = vave.externalizeProduct(projectFolder.resolve("vsum" + (productNumber++)), configuration);

		return vmp;
	}

	protected void internalize(Path productLocation, VirtualProductModel vmp, Collection<Resource> resources, Expression<FeatureOption> e) throws Exception {
		if (!Files.exists(vaveProjectMarker))
			Files.createDirectories(vaveProjectMarker);

		final MatchEngineFactoryImpl matchEngineFactory = new HierarchicalMatchEngineFactory(new EqualityHelper(EqualityHelper.createDefaultCache(CacheBuilder.newBuilder())), new SimilarityChecker());
		matchEngineFactory.setRanking(20);

		IMatchEngine.Factory.Registry registry = EMFCompareRCPPlugin.getDefault().getMatchEngineFactoryRegistry();
		registry.add(matchEngineFactory);

		IDiffProcessor diffProcessor = new DiffBuilder();
		IDiffEngine diffEngine = new DefaultDiffEngine(diffProcessor) {
			@Override
			protected FeatureFilter createFeatureFilter() {
				return new FeatureFilter() {
					@Override
					protected boolean isIgnoredReference(Match match, EReference reference) {
						return super.isIgnoredReference(match, reference);
					}

					@Override
					public boolean isIgnoredAttribute(EAttribute attribute) {
						return super.isIgnoredAttribute(attribute);
					}

					@Override
					public boolean checkForOrderingChanges(EStructuralFeature feature) {
						return super.checkForOrderingChanges(feature);
					}
				};
			}
		};

		// diff changes
		System.out.println("DIFFING");
		ResourceSet referenceResourceSet = vmp.getResourceSet();

		referenceResourceSet.getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
		referenceResourceSet.getLoadOptions().put("DISABLE_LOCATION_MAP", Boolean.TRUE);
		referenceResourceSet.getLoadOptions().put(JavaClasspath.OPTION_USE_LOCAL_CLASSPATH, Boolean.TRUE);
		referenceResourceSet.getLoadOptions().put(JavaClasspath.OPTION_REGISTER_STD_LIB, Boolean.FALSE);
		JavaClasspath refRSCP = JavaClasspath.get(referenceResourceSet, JavaClasspath.getInitializers());

		// register jar files
		System.out.println("REGISTERING JAR FILES");
		refRSCP.registerClassifierJar(URI.createFileURI(Paths.get("resources\\rt.jar").toAbsolutePath().toString()));
		refRSCP.registerClassifierJar(URI.createFileURI(Paths.get("resources\\jmi.jar").toAbsolutePath().toString()));
		List<Path> jarFiles = new ArrayList<>();
		// Path[] libraryFolders = new Path[] { location };
		Path[] libraryFolders = new Path[] { Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\") };
		for (Path libraryFolder : libraryFolders) {
			Files.walk(libraryFolder).forEach(f -> {
				if (Files.isRegularFile(f) && f.getFileName().toString().endsWith(".jar")) {
					jarFiles.add(f);
					System.out.println("ADDED JAR FILE: " + f);
				}
			});
		}
		for (Path jarFile : jarFiles) {
			refRSCP.registerClassifierJar(URI.createFileURI(jarFile.toString()));
		}

		final ChangeRecorder changeRecorder = new ChangeRecorder(referenceResourceSet);
		changeRecorder.addToRecording(referenceResourceSet);
		changeRecorder.beginRecording();

		for (Resource resource : resources) {
			// EMFCompare comparator = EMFCompare.builder().build();
			EMFCompare comparator = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).setDiffEngine(diffEngine).build();

			Resource referenceResource;

//			Path relativeResourcePath = productLocation.relativize(Paths.get(resource.getURI().toFileString()));
//			URI vaveURI = URI.createFileURI(vaveResourceLocation.resolve(relativeResourcePath).toString());
//			System.out.println("URI: " + vaveURI);
//			if (vmp.getModelInstance(vaveURI) != null) {
//				referenceResource = vmp.getModelInstance(vaveURI).getResource();
//			} else {
//				referenceResource = dummyResourceSet.createResource(vaveURI);
//			}

//			if (vmp.getModelInstance(resource.getURI()) != null) {
//				referenceResource = vmp.getModelInstance(resource.getURI()).getResource();
//			} else {
//				referenceResource = referenceResourceSet.createResource(resource.getURI());
//			}

			referenceResource = referenceResourceSet.getResource(resource.getURI(), false);
			if (referenceResource == null) {
				System.out.println("NEW RESOURCE DETECTED: " + resource.getURI());
				referenceResource = referenceResourceSet.createResource(resource.getURI());
			}

			IComparisonScope scope = new DefaultComparisonScope(resource, referenceResource, null);
			Comparison comparison = comparator.compare(scope);
			List<Diff> differences = comparison.getDifferences();

			System.out.println("NUM DIFFS: " + differences.size());
			System.out.println("RESOURCE: " + resource.getURI() + " - " + referenceResource.getURI());

			IMerger.Registry mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance();
			IBatchMerger merger = new BatchMerger(mergerRegistry);
			merger.copyAllLeftToRight(differences, new BasicMonitor());
		}

		final TransactionalChange recordedChange = changeRecorder.endRecording();
		changeRecorder.close();

		System.out.println("NUM RECORDED CHANGES: " + recordedChange.getEChanges().size());

		// order recorded changes
		System.out.println("ORDERING CHANGES");
		ArrayList<EChange> newEChanges = new ArrayList<>();
		ArrayList<EChange> toAppend = new ArrayList<>();
		ArrayList<EChange> toAppend2 = new ArrayList<>();
		for (EChange change : recordedChange.getEChanges()) {
			if ((change instanceof ReplaceSingleValuedEReference) && ((ReplaceSingleValuedEReference) change).getNewValueID() != null && ((ReplaceSingleValuedEReference) change).getNewValueID().contains("pathmap") && ((ReplaceSingleValuedEReference) change).getNewValueID().contains(".java#/1")) {
				// this is the workaround for the the problem vitruvius has with the ".length" field of arrays of all types outside of the actually parsed source code (e.g., java.lang.Object or java.lang.Byte).
//				System.out.println("IGNORE: " + change);
			} else if ((change instanceof ReplaceSingleValuedEReference) && ((ReplaceSingleValuedEReference) change).getNewValueID() != null && ((ReplaceSingleValuedEReference) change).getAffectedEObject() != null && !((EObject) ((ReplaceSingleValuedEReference) change).getNewValue()).eResource().getURI().equals(((ReplaceSingleValuedEReference) change).getAffectedEObject().eResource().getURI())) {
				toAppend2.add(change);
//				System.out.println("moved change to back: " + change);
			} else if ((change instanceof InsertEReference) && ((InsertEReference) change).getNewValue() != null && ((InsertEReference) change).getAffectedEObject() != null && !((EObject) ((InsertEReference) change).getNewValue()).eResource().getURI().equals(((InsertEReference) change).getAffectedEObject().eResource().getURI())) {
				toAppend.add(change);
//				System.out.println("moved change to back: " + change);
			} else {
				newEChanges.add(change);
			}
		}
		ArrayList<EChange> orderedChanges = new ArrayList<>();
		orderedChanges.addAll(newEChanges);
		orderedChanges.addAll(toAppend);
		orderedChanges.addAll(toAppend2);
		TransactionalChange orderedChange = new TransactionalChangeImpl(orderedChanges);

		System.out.println("NUM CHANGES: " + orderedChange.getEChanges().size());

		// propagate changes into product
		System.out.println("PROPAGATING CHANGES INTO PRODUCT");
		vmp.propagateChange(orderedChange);

		// internalize changes in product into system
		System.out.println("INTERNALIZING CHANGES IN PRODUCT INTO SYSTEM");
		vave.internalizeChanges(vmp, e);

		sysrev++;
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
//			boolean relevant2 = false;
//			if (nextElement.eResource().getURI().toString().contains("argouml") && nextElement.eResource().getURI().toString().contains("pathmap"))
//				relevant2 = true;
			nextElement = (InternalEObject) EcoreUtil.resolve(nextElement, rs);
//			if (relevant2 && nextElement.eIsProxy())
//				System.out.println("AAA: " + nextElement.eResource().getURI());
			for (EObject crElement : nextElement.eCrossReferences()) {
//				if (crElement.eIsProxy()) {
				crElement = EcoreUtil.resolve(crElement, rs);
				// nextElement.eResolveProxy((InternalEObject) crElement);
//				boolean relevant = false;
//				if (crElement.eResource().getURI().toString().contains("argouml") && crElement.eResource().getURI().toString().contains("pathmap"))
//					relevant = true;
				if (crElement.eIsProxy()) {
//					if (relevant)
//						System.out.println("BBB: " + crElement.eResource().getURI());
					failure = true;
					notResolved++;
					System.out.println("Can not find referenced element in classpath: " + ((InternalEObject) crElement).eProxyURI());
				} else {
					resolved++;
				}
//				}
			}
		}

		System.out.println(eobjectCnt + "/" + eobjects.size() + " done: Resolved " + resolved + " crossrefs, " + notResolved + " crossrefs could not be resolved.");

		// call this method again, because the resolving might have triggered loading of additional resources that may also contain references that need to be resolved.
		return !failure && resolveAllProxiesRecursive(rs, resourcesProcessed);
	}

	/**
	 * This test starts from a product with no (or only core) features and adds individual features.
	 * 
	 * @throws Exception
	 */
	@Test
	// @Disabled
	public void EvalTestAdditive() throws Exception {
		// initialize vave system
		// done in @Before

		// internalize domain
		FeatureModel fm = new FeatureModel(null, null, new HashSet<FeatureOption>(), new HashSet<TreeConstraint>(), new HashSet<CrossTreeConstraint>());

		Feature Fcore = VavemodelFactory.eINSTANCE.createFeature();
		Fcore.setName("Core"); // this represents the ArgoUML root feature as well as the mandatory features Diagrams and Class of the original ArgoUML feature model
		Feature Fcognitive = VavemodelFactory.eINSTANCE.createFeature();
		Fcognitive.setName("Cognitive");
		Feature Flogging = VavemodelFactory.eINSTANCE.createFeature();
		Flogging.setName("Logging");
		Feature Factivity = VavemodelFactory.eINSTANCE.createFeature();
		Factivity.setName("Activity");
		Feature Fsequence = VavemodelFactory.eINSTANCE.createFeature();
		Fsequence.setName("Sequence");
		Feature Fstate = VavemodelFactory.eINSTANCE.createFeature();
		Fstate.setName("State");
		Feature Fcollaboration = VavemodelFactory.eINSTANCE.createFeature();
		Fcollaboration.setName("Collaboration");
		Feature Fusecase = VavemodelFactory.eINSTANCE.createFeature();
		Fusecase.setName("UseCase");
		Feature Fdeployment = VavemodelFactory.eINSTANCE.createFeature();
		Fdeployment.setName("Deployment");

		fm.getFeatureOptions().add(Fcore);
		fm.getFeatureOptions().add(Fcognitive);
		fm.getFeatureOptions().add(Flogging);
		fm.getFeatureOptions().add(Factivity);
		fm.getFeatureOptions().add(Fsequence);
		fm.getFeatureOptions().add(Fstate);
		fm.getFeatureOptions().add(Fcollaboration);
		fm.getFeatureOptions().add(Fusecase);
		fm.getFeatureOptions().add(Fdeployment);

		fm.setRootFeature(Fcore);
		TreeConstraint treeCstr = VavemodelFactory.eINSTANCE.createTreeConstraint();
		treeCstr.setType(GroupType.ORNONE);
		treeCstr.getFeature().add(Flogging);
		treeCstr.getFeature().add(Fcognitive);
		Fcore.getTreeconstraint().add(treeCstr);
		fm.getTreeConstraints().add(treeCstr);

		int core_rev = -1;
		int logg_rev = -1;
		int cogn_rev = -1;

		this.vave.internalizeDomain(fm);
		sysrev++;

		Path variantsLocation = Paths.get("C:\\FZI\\git\\argouml-spl-revisions-variants-selection");

		{ // # REVISION 0 (ArgoUML-SPL)
			Path revision0VariantsLocation = variantsLocation.resolve("R0_variants");
			System.out.println("START REV 0");

			{ // CORE
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 0");
				// externalize empty product with expression TRUE
				VirtualProductModel vmp = this.externalize(VavemodelFactory.eINSTANCE.createConfiguration(), vaveResourceLocation.getParent().resolve("R0-V-empty-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-empty-ext"));

				// internalize ArgoUML R0 Product P0 (Core) generated by generator with expression Core
				// Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V\\src\\"));
				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-COGN-LOGG-ACTI-COLL-DEPL-SEQU-STAT-USEC\\src\\"));
				// Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("argouml-workaround\\src\\"));
				Variable<FeatureOption> expression = VavemodelFactory.eINSTANCE.createVariable();
				expression.setOption(Fcore);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-int-vsum\\src\\"), vmp, resources, expression);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-int"));

				core_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // COGNITIVE
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 1");
				// externalize Core.1
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				// add features and feature revisions to configuration
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ext"));

				// internalize ArgoUML R0 Product P1 (Core, Cognitive) generated by generator with expression Cognitive
				// Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-COGN\\src"));
				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-COGN-LOGG-ACTI-COLL-DEPL-SEQU-STAT-USEC\\src\\"));
				// Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("argouml-workaround\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fcognitive);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-COGN-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-COGN-int"));

				cogn_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // LOGGING
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 2");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-ext2-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ext2"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-LOGG\\src"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Flogging);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-LOGG-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // COGNITIVE && LOGGING
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 3");
				// externalize Core.1, Cognitive.1, Logging.1
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Fcognitive.getFeaturerevision().get(cogn_rev));
				configuration.getOption().add(Flogging.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-COGN-LOGG-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-COGN-LOGG-ext"));

				// internalize ArgoUML R0 Product P3 (Core, Cognitive, Logging) with Cognitive && Logging
				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-COGN-LOGG\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Fcognitive);
				variable2.setOption(Flogging);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-COGN-LOGG-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-COGN-LOGG-int"));

				cogn_rev++;
				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// repeat above for every feature and every pair of features
			// internalize ArgoUML R0 Product P? (Core, ... three-wise ...) with ... && ... && ...

			{ // ACTIVITYDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 4");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-ext4-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ext4"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-ACTI\\src"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Factivity);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-ACTI-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ACTI-int"));

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // ACTIVITYDIAGRAM_and_LOGGING
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 5");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Flogging.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(Factivity.getFeaturerevision().get(Factivity.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-LOGG-ACTI-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-ACTI-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-LOGG-ACTI\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Flogging);
				variable2.setOption(Factivity);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-LOGG-ACTI-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-ACTI-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // COLLABORATIONDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 6");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-ext6-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ext6"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-COLL\\src"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fcollaboration);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-COLL-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-COLL-int"));

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // COLLABORATIONDIAGRAM_and_LOGGING
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 7");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Flogging.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(Fcollaboration.getFeaturerevision().get(Fcollaboration.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-LOGG-COLL-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-COLL-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-LOGG-COLL\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Flogging);
				variable2.setOption(Fcollaboration);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-LOGG-COLL-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-COLL-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // DEPLOYMENTDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 8");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-ext8-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ext8"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-DEPL\\src"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fdeployment);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-DEPL-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-DEPL-int"));

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // COGNITIVE_and_DEPLOYMENTDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 9");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Fcognitive.getFeaturerevision().get(cogn_rev));
				configuration.getOption().add(Fdeployment.getFeaturerevision().get(Fdeployment.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-COGN-DEPL-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-COGN-DEPL-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-COGN-DEPL\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Fcognitive);
				variable2.setOption(Fdeployment);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-COGN-DEPL-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-COGN-DEPL-int"));

				cogn_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // DEPLOYMENTDIAGRAM_and_LOGGING
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 10");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Flogging.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(Fdeployment.getFeaturerevision().get(Fdeployment.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-LOGG-DEPL-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-DEPL-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-LOGG-DEPL\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Flogging);
				variable2.setOption(Fdeployment);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-LOGG-DEPL-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-DEPL-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // SEQUENCEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 11");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-ext11-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ext11"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-SEQU\\src"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fsequence);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-SEQU-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-SEQU-int"));

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // COGNITIVE_and_SEQUENCEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 12");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Fcognitive.getFeaturerevision().get(cogn_rev));
				configuration.getOption().add(Fsequence.getFeaturerevision().get(Fsequence.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-COGN-SEQU-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-COGN-SEQU-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-COGN-SEQU\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Fcognitive);
				variable2.setOption(Fsequence);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-COGN-SEQU-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-COGN-SEQU-int"));

				cogn_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // LOGGING_and_SEQUENCEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 13");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Flogging.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(Fsequence.getFeaturerevision().get(Fsequence.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-LOGG-SEQU-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-SEQU-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-LOGG-SEQU\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Flogging);
				variable2.setOption(Fsequence);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-LOGG-SEQU-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-SEQU-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // STATEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 14");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-ext14-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ext14"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-STAT\\src"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fstate);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-STAT-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-STAT-int"));

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // LOGGING_and_STATEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 15");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Flogging.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(Fstate.getFeaturerevision().get(Fstate.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-LOGG-STAT-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-STAT-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-LOGG-STAT\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Flogging);
				variable2.setOption(Fstate);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-LOGG-STAT-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-STAT-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // USECASEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 16");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-ext16-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ext16"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-USEC\\src"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fusecase);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-USEC-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-USEC-int"));

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // LOGGING_and_USECASEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 17");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Flogging.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(Fusecase.getFeaturerevision().get(Fusecase.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-LOGG-USEC-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-USEC-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-LOGG-USEC\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Flogging);
				variable2.setOption(Fusecase);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-LOGG-USEC-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-USEC-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // ACTIVITYDIAGRAM_and_STATEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 18");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Factivity.getFeaturerevision().get(Factivity.getFeaturerevision().size() - 1));
				configuration.getOption().add(Fstate.getFeaturerevision().get(Fstate.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-ACTI-STAT-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ACTI-STAT-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-ACTI-STAT\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Factivity);
				variable2.setOption(Fstate);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-ACTI-STAT-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-ACTI-STAT-int"));

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // COLLABORATIONDIAGRAM_and_SEQUENCEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 19");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Fcollaboration.getFeaturerevision().get(Fcollaboration.getFeaturerevision().size() - 1));
				configuration.getOption().add(Fsequence.getFeaturerevision().get(Fsequence.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-COLL-SEQU-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-COLL-SEQU-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-COLL-SEQU\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Fcollaboration);
				variable2.setOption(Fsequence);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-COLL-SEQU-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-COLL-SEQU-int"));

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // DEPLOYMENTDIAGRAM_and_USECASEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 20");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Fdeployment.getFeaturerevision().get(Fdeployment.getFeaturerevision().size() - 1));
				configuration.getOption().add(Fusecase.getFeaturerevision().get(Fusecase.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-DEPL-USEC-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-DEPL-USEC-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-DEPL-USEC\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Fdeployment);
				variable2.setOption(Fusecase);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-DEPL-USEC-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-DEPL-USEC-int"));

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // SEQUENCEDIAGRAM_and_STATEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 21");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Fsequence.getFeaturerevision().get(Fsequence.getFeaturerevision().size() - 1));
				configuration.getOption().add(Fstate.getFeaturerevision().get(Fstate.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-SEQU-STAT-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-SEQU-STAT-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-SEQU-STAT\\src"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Fsequence);
				variable2.setOption(Fstate);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-SEQU-STAT-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-SEQU-STAT-int"));

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			{ // COLLABORATIONDIAGRAM_and_LOGGING_and_SEQUENCEDIAGRAM
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 0 PROD 22");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Flogging.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(Fcollaboration.getFeaturerevision().get(Fcollaboration.getFeaturerevision().size() - 1));
				configuration.getOption().add(Fsequence.getFeaturerevision().get(Fsequence.getFeaturerevision().size() - 1));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R0-V-LOGG-COLL-SEQU-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-COLL-SEQU-ext"));

				Collection<Resource> resources = this.parse(revision0VariantsLocation.resolve("V-LOGG-COLL-SEQU\\src"));
				Conjunction<FeatureOption> conjunction1 = VavemodelFactory.eINSTANCE.createConjunction();
				Conjunction<FeatureOption> conjunction2 = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable3 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Flogging);
				variable2.setOption(Fcollaboration);
				variable3.setOption(Fsequence);
				conjunction1.getTerm().add(conjunction2);
				conjunction1.getTerm().add(variable1);
				conjunction2.getTerm().add(variable2);
				conjunction2.getTerm().add(variable3);
				this.internalize(vaveResourceLocation.getParent().resolve("R0-V-LOGG-COLL-SEQU-int-vsum\\src\\"), vmp, resources, conjunction1);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R0-V-LOGG-COLL-SEQU-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// at this point R0 should be fully supported

			// EVALUATION: externalize all configurations (or sample) for later comparison with ground truth
			Path evalVariantsLocation = vaveResourceLocation.getParent().resolve("R0-eval-variants");
			Files.createDirectory(evalVariantsLocation);
			this.createEvalVariants(evalVariantsLocation);
		}

		{ // # REVISION 1 (ArgoUML future revisions merged successively into ArgoUML-SPL)
			Path revisionVariantsLocation = variantsLocation.resolve("R1_variants");
			System.out.println("START REV 1");
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 1 PROD 0");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R1-V-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R1-V-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fcore);
				this.internalize(vaveResourceLocation.getParent().resolve("R1-V-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R1-V-int"));

				core_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// TODO: externalize for eval
		}

		{ // # REVISION 2
			Path revisionVariantsLocation = variantsLocation.resolve("R2_variants");
			System.out.println("START REV 2");
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 2 PROD 0");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Flogging.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R2-V-LOGG-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R2-V-LOGG-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V-LOGG\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Flogging);
				this.internalize(vaveResourceLocation.getParent().resolve("R2-V-COGN-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R2-V-LOGG-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// TODO: externalize for eval
		}

		{ // # REVISION 3
			Path revisionVariantsLocation = variantsLocation.resolve("R3_variants");
			System.out.println("START REV 3");
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 3 PROD 0");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R3-V-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R3-V-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fcore);
				this.internalize(vaveResourceLocation.getParent().resolve("R3-V-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R3-V-int"));

				core_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// TODO: externalize for eval
		}

		{ // # REVISION 4
			Path revisionVariantsLocation = variantsLocation.resolve("R4_variants");
			System.out.println("START REV 4");
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 4 PROD 0");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Fcognitive.getFeaturerevision().get(cogn_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R4-V-COGN-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R4-V-COGN-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V-COGN\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fcognitive);
				this.internalize(vaveResourceLocation.getParent().resolve("R4-V-COGN-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R4-V-COGN-int"));

				cogn_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// TODO: externalize for eval
		}

		{ // # REVISION 5
			Path revisionVariantsLocation = variantsLocation.resolve("R5_variants");
			System.out.println("START REV 5");
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 5 PROD 0");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Fcognitive.getFeaturerevision().get(cogn_rev));
				configuration.getOption().add(Flogging.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R5-V-COGN-LOGG-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R5-V-COGN-LOGG-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V-COGN-LOGG\\src\\"));
				Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
				Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
				Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
				variable1.setOption(Fcognitive);
				variable2.setOption(Flogging);
				conjunction.getTerm().add(variable1);
				conjunction.getTerm().add(variable2);
				this.internalize(vaveResourceLocation.getParent().resolve("R5-V-COGN-LOGG-int-vsum\\src\\"), vmp, resources, conjunction);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R5-V-COGN-LOGG-int"));

				cogn_rev++;
				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// TODO: externalize for eval
		}

		{ // # REVISION 6
			Path revisionVariantsLocation = variantsLocation.resolve("R6_variants");
			System.out.println("START REV 6");
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 6 PROD 0");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R6-V-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R6-V-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fcore);
				this.internalize(vaveResourceLocation.getParent().resolve("R6-V-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R6-V-int"));

				core_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// TODO: externalize for eval
		}

		{ // # REVISION 7
			Path revisionVariantsLocation = variantsLocation.resolve("R7_variants");
			System.out.println("START REV 7");
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 7 PROD 0");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R7-V-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R7-V-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fcore);
				this.internalize(vaveResourceLocation.getParent().resolve("R7-V-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R7-V-int"));

				core_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 7 PROD 1");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Fcore.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R7-V-LOGG-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R7-V-LOGG-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V-LOGG\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Flogging);
				this.internalize(vaveResourceLocation.getParent().resolve("R7-V-LOGG-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R7-V-LOGG-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// TODO: externalize for eval
		}

		{ // # REVISION 8
			Path revisionVariantsLocation = variantsLocation.resolve("R8_variants");
			System.out.println("START REV 8");
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 8 PROD 0");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R8-V-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R8-V-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fcore);
				this.internalize(vaveResourceLocation.getParent().resolve("R8-V-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R8-V-int"));

				core_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// TODO: externalize for eval
		}

		{ // # REVISION 9
			Path revisionVariantsLocation = variantsLocation.resolve("R9_variants");
			System.out.println("START REV 9");
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 9 PROD 0");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R9-V-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R9-V-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Fcore);
				this.internalize(vaveResourceLocation.getParent().resolve("R9-V-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R9-V-int"));

				core_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}
			{
				long timeStart = System.currentTimeMillis();

				System.out.println("START REV 9 PROD 1");
				Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
				configuration.getOption().add(Fcore.getFeaturerevision().get(core_rev));
				configuration.getOption().add(Fcore.getFeaturerevision().get(logg_rev));
				configuration.getOption().add(vave.getSystem().getSystemrevision().get(sysrev));
				VirtualProductModel vmp = this.externalize(configuration, vaveResourceLocation.getParent().resolve("R9-V-LOGG-ext-vsum\\src\\"));
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R9-V-LOGG-ext"));

				Collection<Resource> resources = this.parse(revisionVariantsLocation.resolve("V-LOGG\\src\\"));
				Variable<FeatureOption> variable = VavemodelFactory.eINSTANCE.createVariable();
				variable.setOption(Flogging);
				this.internalize(vaveResourceLocation.getParent().resolve("R9-V-LOGG-int-vsum\\src\\"), vmp, resources, variable);
				Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R9-V-LOGG-int"));

				logg_rev++;

				long timeDiff = System.currentTimeMillis() - timeStart;
				System.out.println("TOTAL TIME: " + timeDiff);
			}

			// TODO: externalize for eval
		}

		// --------------------------------------

		// AUSWERTUNG: compare ground truth UML diagrams for certain variants (extracted from Java code via some external tool) with UML diagrams that was created via Vitruv change propagation for certain variants.

		// 1. externalize product with random config -> this results in files on the disk with: jamopp model and uml model for given config
		// ...

		// 2. generate argouml-spl variant with same config via javapp: this results in ground truth java code for argouml variant for given config

		// 3. use external tool to create uml diagram for argouml variant in step 2 -> this results in ground truth uml model

		// 4. compare ground truth uml model from step 3 with uml model created by vave from step 1

		// 5. repeat above as many times as you want

	}

	private void createEvalVariants(Path targetLocation) throws Exception {
		List<Feature> optionalFeatures = this.vave.getSystem().getFeature().stream().filter(f -> !f.getName().equals("Core")).collect(Collectors.toList());
		SystemRevision latestSysRev = this.vave.getSystem().getSystemrevision().get(this.vave.getSystem().getSystemrevision().size() - 1);
		Feature coreFeature = this.vave.getSystem().getFeature().stream().filter(f -> f.getName().equals("Core")).findFirst().get();
		FeatureRevision coreFeatureRev = coreFeature.getFeaturerevision().get(coreFeature.getFeaturerevision().size() - 1);

		// only core
		Configuration emptyConfig = VavemodelFactory.eINSTANCE.createConfiguration();
		emptyConfig.getOption().add(latestSysRev);
		emptyConfig.getOption().add(coreFeatureRev);
		this.externalize(emptyConfig, targetLocation.resolve("V-vsum"));
		Files.move(vaveResourceLocation, targetLocation.resolve("V"));

		// single features
		for (Feature optionalFeature : optionalFeatures) {
			Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
			config.getOption().add(latestSysRev);
			config.getOption().add(coreFeatureRev);
			config.getOption().add(optionalFeature.getFeaturerevision().get(optionalFeature.getFeaturerevision().size() - 1));
			this.externalize(config, targetLocation.resolve("V-" + optionalFeature.getName().substring(0, 4) + "-vsum"));
			Files.move(vaveResourceLocation, targetLocation.resolve("V-" + optionalFeature.getName().substring(0, 4)));
		}

		// pair-wise feature interactions
		for (int i = 0; i < optionalFeatures.size(); i++) {
			for (int j = i + 1; j < optionalFeatures.size(); j++) {
				Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
				config.getOption().add(latestSysRev);
				config.getOption().add(coreFeatureRev);
				config.getOption().add(optionalFeatures.get(i).getFeaturerevision().get(optionalFeatures.get(i).getFeaturerevision().size() - 1));
				config.getOption().add(optionalFeatures.get(j).getFeaturerevision().get(optionalFeatures.get(j).getFeaturerevision().size() - 1));
				this.externalize(config, targetLocation.resolve("V-" + optionalFeatures.get(i).getName().substring(0, 4) + "-" + optionalFeatures.get(j).getName() + "-vsum"));
				Files.move(vaveResourceLocation, targetLocation.resolve("V-" + optionalFeatures.get(i).getName().substring(0, 4) + "-" + optionalFeatures.get(j).getName()));
			}
		}

		// selected three-wise feature interactions
		Configuration threeWiseConfig = VavemodelFactory.eINSTANCE.createConfiguration();
		threeWiseConfig.getOption().add(coreFeatureRev);

		Feature Flogging = vave.getSystem().getFeature().stream().filter(f -> f.getName().equals("Logging")).findFirst().get();
		Feature Fcollaboration = vave.getSystem().getFeature().stream().filter(f -> f.getName().equals("Collaboration")).findFirst().get();
		Feature Fsequence = vave.getSystem().getFeature().stream().filter(f -> f.getName().equals("Sequence")).findFirst().get();
		threeWiseConfig.getOption().add(Flogging.getFeaturerevision().get(Flogging.getFeaturerevision().size() - 1));
		threeWiseConfig.getOption().add(Fcollaboration.getFeaturerevision().get(Fcollaboration.getFeaturerevision().size() - 1));
		threeWiseConfig.getOption().add(Fsequence.getFeaturerevision().get(Fsequence.getFeaturerevision().size() - 1));

		this.externalize(threeWiseConfig, targetLocation.resolve("V-" + Flogging.getName().substring(0, 4) + "-" + Fcollaboration.getName().substring(0, 4) + "-" + Fsequence.getName().substring(0, 4) + "-vsum"));
		Files.move(vaveResourceLocation, vaveResourceLocation.getParent().resolve("R9-V-LOGG-ext"));

		// all features
		Configuration allConfig = VavemodelFactory.eINSTANCE.createConfiguration();
		allConfig.getOption().add(latestSysRev);
		allConfig.getOption().add(coreFeatureRev);
		allConfig.getOption().addAll(optionalFeatures.stream().map(f -> {
			return f.getFeaturerevision().get(f.getFeaturerevision().size() - 1);
		}).collect(Collectors.toList()));
		this.externalize(allConfig, targetLocation.resolve("V-" + optionalFeatures.stream().map(f -> f.getName()).collect(Collectors.joining("-")) + "-vsum"));
		Files.move(vaveResourceLocation, targetLocation.resolve("V-" + optionalFeatures.stream().map(f -> f.getName()).collect(Collectors.joining("-"))));
	}

}
