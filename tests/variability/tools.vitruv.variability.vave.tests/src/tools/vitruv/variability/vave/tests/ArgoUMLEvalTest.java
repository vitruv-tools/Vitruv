package tools.vitruv.variability.vave.tests;

import java.io.IOException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification;
import tools.vitruv.domains.java.JamoppLibraryHelper;
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
import tools.vitruv.variability.vave.impl.FeatureModel;
import tools.vitruv.variability.vave.impl.VirtualVaVeModeIImpl;
import vavemodel.Configuration;
import vavemodel.CrossTreeConstraint;
import vavemodel.Expression;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.GroupType;
import vavemodel.TreeConstraint;
import vavemodel.Variable;
import vavemodel.VavemodelFactory;

/**
 * Test that runs the entire ArgoUML evaluation as batch.
 */
@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
@Disabled
public class ArgoUMLEvalTest {

	protected VirtualVaVeModel vave = null;

	protected int productNumber = 0;

	@BeforeEach
	public void setUp(@TestProject final Path projectFolder) throws Exception {
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

		this.vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, irp, projectFolder);

		// set up jamopp
		JamoppLibraryHelper.registerStdLib();

		this.productNumber = 0;
	}

	protected Collection<Resource> parse(Path location) throws Exception {
		ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		resourceSet.getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
		resourceSet.getLoadOptions().put("DISABLE_LOCATION_MAP", Boolean.TRUE);
		EPackage.Registry.INSTANCE.put("http://www.emftext.org/java", JavaPackage.eINSTANCE);

		// register jar files
		System.out.println("REGISTERING JAR FILES");
		JavaClasspath cp = JavaClasspath.get(resourceSet);
		cp.registerClassifierJar(URI.createFileURI(Paths.get("C:\\FZI\\argouml\\jars\\rt.jar").toString()));
//		FileSystem fs = FileSystems.getFileSystem(java.net.URI.create("jrt:/"));
//		Path objClassFilePath = fs.getPath("modules", "java.beans", "java/beans/Object.class");
//		cp.registerClassifierJar(URI.createFileURI(Paths.get("C:\\FZI\\argouml\\jars\\sax2.jar").toString()));
		List<Path> jarFiles = new ArrayList<>();
		// Path[] libraryFolders = new Path[] { Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-core-infra\\lib"), Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-core-model-euml\\lib"), Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-core-model-mdr\\lib") };
		// Path[] libraryFolders = new Path[] { Paths.get("C:\\FZI\\argouml\\argouml\\src\\") };
		Path[] libraryFolders = new Path[] { location };
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
		Path[] sourceFolders = new Path[] { location.resolve("argouml-core-model\\src"), location.resolve("argouml-core-model-euml\\src"), location.resolve("argouml-core-model-mdr\\src"), location.resolve("argouml-core-model-mdr\\build\\java")
//				, Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-core-diagrams-sequence2\\src")
//				, Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-app\\src")
		};
		for (Path sourceFolder : sourceFolders) {
			Files.walk(sourceFolder).forEach(f -> {
				if (Files.isDirectory(f) && !f.equals(sourceFolder) && !f.getFileName().toString().startsWith(".") && !f.getFileName().toString().equals("META-INF") && !f.getFileName().toString().equals("test_project.marker_vitruv") && !f.getFileName().toString().equals("umloutput") && !f.getFileName().toString().contains("-") && !f.getFileName().toString().startsWith("build-eclipse")
						&& !f.getFileName().toString().startsWith("bin") && !f.getFileName().toString().startsWith("template")) {
					Path packageInfoPath = f.resolve(Paths.get("package-info.java"));
					try {
						if (!Files.exists(packageInfoPath)) {
							Files.createFile(packageInfoPath);
							Files.writeString(packageInfoPath, "package " + sourceFolder.relativize(f).toString().replace(java.io.File.separator, ".") + ";");
						}
						javaFiles.add(packageInfoPath);
						System.out.println("ADDED PACKAGE INFO FILE: " + packageInfoPath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (Files.isRegularFile(f) && f.getFileName().toString().endsWith(".java") && !f.getFileName().toString().equals("package-info.java")) {
					javaFiles.add(f);
					System.out.println("ADDED JAVA FILE: " + f);
				}
			});
		}

		// parse files
		System.out.println("PARSING JAVA FILES");
		List<Resource> resources = new ArrayList<>();
		for (Path javaFile : javaFiles) {
			System.out.println("FILE: " + javaFile);
			Resource resource = resourceSet.getResource(URI.createFileURI(javaFile.toString()), true);
			resources.add(resource);
		}

		// resolve proxies
		System.out.println("RESOLVING PROXIES");
		resolveAllProxies(resourceSet);

		return resources;
	}

	protected VirtualProductModel externalize(Configuration configuration, Path projectFolder) throws Exception {
		// externalize product
		System.out.println("EXTERNALIZING PRODUCT");
		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
		final VirtualProductModel vmp = vave.externalizeProduct(projectFolder.resolve("vsum" + (productNumber++)), config);

		return vmp;
	}

	protected void internalize(VirtualProductModel vmp, Collection<Resource> resources, Expression<FeatureOption> e) throws Exception {
		// diff changes
		System.out.println("DIFFING");
		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(dummyResourceSet);
		changeRecorder.addToRecording(dummyResourceSet);
		changeRecorder.beginRecording();

		for (Resource resource : resources) {
			EMFCompare comparator = EMFCompare.builder().build();

			Resource referenceResource;
			if (vmp.getModelInstance(resource.getURI()) != null) {
				referenceResource = vmp.getModelInstance(resource.getURI()).getResource();
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
			if ((change instanceof ReplaceSingleValuedEReference) && !((EObject) ((ReplaceSingleValuedEReference) change).getNewValue()).eResource().getURI().equals(((ReplaceSingleValuedEReference) change).getAffectedEObject().eResource().getURI())) {
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
		vmp.propagateChange(orderedChange);

		// internalize changes in product into system
		System.out.println("INTERNALIZING CHANGES IN PRODUCT INTO SYSTEM");
		vave.internalizeChanges(vmp, e);
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

	/**
	 * This test starts from a product with no (or only core) features and adds individual features.
	 * 
	 * @param projectFolder
	 * @throws Exception
	 */
	@Test
	// @Disabled
	public void EvalTestAdditive(@TestProject final Path projectFolder) throws Exception {
		// initialize vave system
		// done in @Before

		// internalize domain
		FeatureModel fm = new FeatureModel(null, null, new HashSet<FeatureOption>(), new HashSet<TreeConstraint>(), new HashSet<CrossTreeConstraint>());

		Feature Fcore = VavemodelFactory.eINSTANCE.createFeature();
		Fcore.setName("Core"); // this represents the ArgoUML root feature as well as the mandatory features Diagrams and Class of the original ArgoUML feature model
		Feature Flogging = VavemodelFactory.eINSTANCE.createFeature();
		Flogging.setName("Logging");
		Feature Fcognitive = VavemodelFactory.eINSTANCE.createFeature();
		Fcognitive.setName("Cognitive");
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
		fm.getFeatureOptions().add(Flogging);
		fm.getFeatureOptions().add(Fcognitive);
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

		this.vave.internalizeDomain(fm);

		// # REVISION 0 (ArgoUML-SPL)

		{
			// externalize empty product with expression TRUE
			VirtualProductModel vmp = this.externalize(VavemodelFactory.eINSTANCE.createConfiguration(), projectFolder);

			// internalize ArgoUML R0 Product P0 (Core) generated by generator with expression Core
			Collection<Resource> resources = this.parse(Paths.get("C:\\FZI\\argouml\\argouml\\src\\")); // TODO: change path to correct product
			Variable<FeatureOption> expression = VavemodelFactory.eINSTANCE.createVariable();
			expression.setOption(Fcore);
			this.internalize(vmp, resources, expression);
		}

		{
			// externalize Core.1
			Configuration configuration = VavemodelFactory.eINSTANCE.createConfiguration();
			// TODO: add features and feature revisions to configuration
			configuration.getOption().add(Fcore);
			VirtualProductModel vmp = this.externalize(configuration, projectFolder);

			// internalize ArgoUML R0 Product P1 (Core, Activity) generated by generator with expression Activity
			Collection<Resource> resources = this.parse(Paths.get("C:\\FZI\\argouml\\argouml\\src\\")); // TODO: change path to correct product
			Variable<FeatureOption> expression = VavemodelFactory.eINSTANCE.createVariable(); // TODO: create correct expression
			// TODO: set feature option of variable
			expression.setOption(Flogging);
			this.internalize(vmp, resources, expression);
		}

		// externalize Core.1

		// internalize ArgoUML R0 Product P2 (Core, Logging) with Logging

		// externalize Core.1, Activity.1, Logging.1

		// internalize ArgoUML R0 Product P3 (Core, Activity, Logging) with Activity && Logging

		// ...

		// TODO: repeat above for every feature and every pair of features

		// ...

		// internalize ArgoUML R0 Product P? (Core, ... three-wise ...) with ... && ... && ...

		// at this point R0 should be fully supported

		// # REVISION 1 (ArgoUML future revisions merged successively into ArgoUML-SPL)

		// TODO: to be determined

		// # REVISION 2

		// TODO: to be determined

		// # REVISION N

		// TODO: to be determined

		// AUSWERTUNG: compare ground truth UML diagrams for certain variants (extracted from Java code via some external tool) with UML diagrams that was created via Vitruv change propagation for certain variants.

		// 1. externalize product with random config -> this results in files on the disk with: jamopp model and uml model for given config
		// ...

		// 2. generate argouml-spl variant with same config via javapp: this results in ground truth java code for argouml variant for given config

		// 3. use external tool to create uml diagram for argouml variant in step 2 -> this results in ground truth uml model

		// 4. compare ground truth uml model from step 3 with uml model created by vave from step 1

		// 5. repeat above as many times as you want

	}

	/**
	 * This test starts from a product with all features and removes individual features.
	 * 
	 * @param projectFolder
	 * @throws Exception
	 */
	@Test
	// @Disabled
	public void EvalTestSubtractive(@TestProject final Path projectFolder) throws Exception {
		// initialize vave system

		// TODO

		// # REVISION 0 (ArgoUML-SPL)

		// externalize empty product with expression TRUE

		// internalize ArgoUML R0 Product P0 (Core, ... ALL)

		// externalize Core.1, ... ALL

		// internalize ArgoUML R0 Product P1 (Core, ALL - Activity) with !Activity

		// externalize Core.1, ... ALL

		// internalize ArgoUML R0 Product P2 (Core, Logging) with Logging

		// externalize Core.1, Activity.1, Logging.1

		// internalize ArgoUML R0 Product P3 (Core, Activity, Logging) with Activity && Logging

		// ...

		// TODO: repeat above for every feature and every pair of features

		// ...

		// internalize ArgoUML R0 Product P? (Core, ... three-wise ...) with ... && ... && ...

		// # REVISION 1 (ArgoUML future revisions merged successively into ArgoUML-SPL)

		// TODO: to be determined

		// # REVISION 2

		// TODO: to be determined

		// # REVISION N

		// TODO: to be determined

	}

}
