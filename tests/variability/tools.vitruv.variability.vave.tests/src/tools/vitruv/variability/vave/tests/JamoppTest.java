package tools.vitruv.variability.vave.tests;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.EList;
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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
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

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
@Disabled
public class JamoppTest {

	// TODO 0: Load a single java file into an Ecore model instance.
	// TODO 1: Multiple depending java files loading into same resourceset
	// TODO 2: Diffing / EMFCompare of two java model instances
	// TODO 3: Internalize first loaded model into a vave instance
	// TODO 4: Load second model, diff it against first model, and then propagate recorded changes into externalized product and internalize it again
	// TODO 5: Also load UML domain and UML2Java consistency preservation in vave and do steps of 4.

	@Test // this is TODO 0
	public void loadModelFromCodeViaEMFTest() {
		// Register factory for class and Java files in case of not running as plugin
//		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("java", new JavaSourceOrClassFileResourceFactoryImpl())
//		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("class", new JavaSourceOrClassFileResourceFactoryImpl())

		// This is necessary to resolve classes from standard library (e.g. Object, List etc.)
		JamoppLibraryHelper.registerStdLib();

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resource = ResourceSetUtil.loadOrCreateResource(resourceSet, URI.createFileURI("testsrc\\main\\Main.java"));
		System.out.println(resource);
	}

	@Test // this is TODO 1
	public void loadMultipleJavaFiles() {
		JamoppLibraryHelper.registerStdLib();
		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resourceMain = ResourceSetUtil.loadOrCreateResource(resourceSet, URI.createFileURI("testsrc\\main\\Main.java"));
		Resource resourcePerson = ResourceSetUtil.loadOrCreateResource(resourceSet, URI.createFileURI("testsrc\\main\\Person.java"));
		resolveAllProxies(resourceSet);
		System.out.println(resourceMain);
	}

	@Test // this is TODO 2
	public void diffJavaFiles() {
		JamoppLibraryHelper.registerStdLib();
		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resourceMain = ResourceSetUtil.loadOrCreateResource(resourceSet, URI.createFileURI("testsrc\\main\\Main.java"));
		Resource resourcePerson = ResourceSetUtil.loadOrCreateResource(resourceSet, URI.createFileURI("testsrc\\main\\Person.java"));
		resolveAllProxies(resourceSet);

		final ResourceSet resourceSet2 = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resourceMain2 = ResourceSetUtil.loadOrCreateResource(resourceSet2, URI.createFileURI("testsrc\\main\\Main.java"));
		Resource resourcePerson2 = ResourceSetUtil.loadOrCreateResource(resourceSet2, URI.createFileURI("testsrc\\main\\Person.java"));
		resolveAllProxies(resourceSet2);

		EMFCompare comparator = EMFCompare.builder().build();
		// compare resource sets directly only works if the resources represent the same files. if the resources represent the same files but in a different location, they will not be matched.
		IComparisonScope scope = new DefaultComparisonScope(resourceMain, resourceMain2, null);
		Comparison res = comparator.compare(scope);

		EList<Diff> diffs = res.getDifferences();
		System.out.println(resourceMain);
	}

	@Test // this is TODO 3
	public void internalizeJavaIntoVave(@TestProject final Path projectFolder) throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new JavaDomainProvider().getDomain());

		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, new HashSet<>(), UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), projectFolder);

		// load java files into models
		JamoppLibraryHelper.registerStdLib();
		final ResourceSet javaResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resourcePerson = ResourceSetUtil.loadOrCreateResource(javaResourceSet, URI.createFileURI("testsrc\\main\\Person.java"));
		resolveAllProxies(javaResourceSet);

		// diff against empty resource
		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource dummyResource = dummyResourceSet.createResource(URI.createFileURI("testsrc\\main\\Person.java"));

		// compare resource sets directly only works if the resources represent the same files. if the resources represent the same files but in a different location, they will not be matched.
		EMFCompare comparator = EMFCompare.builder().build();
		IComparisonScope scope = new DefaultComparisonScope(resourcePerson, dummyResource, null);
		Comparison comparison = comparator.compare(scope);
		List<Diff> differences = comparison.getDifferences();

		// create config and externalize product
		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);

		// final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ResourceSet resourceSet = dummyResourceSet;
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();

		// Let's merge every single diff
		IMerger.Registry mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance();
		IBatchMerger merger = new BatchMerger(mergerRegistry);
		merger.copyAllLeftToRight(differences, new BasicMonitor());

		final TransactionalChange recordedChange = changeRecorder.endRecording();
		// propagate recorded changes into vmp1
		vmp1.propagateChange(recordedChange);

		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1
	}

	@Test // this is TODO 4
	public void multipleInternalizeMultipleJavaIntoVave(@TestProject final Path projectFolder) throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new JavaDomainProvider().getDomain());

		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, new HashSet<>(), UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), projectFolder);

		JamoppLibraryHelper.registerStdLib();

		// load first revision of java files into models
		final ResourceSet javaR1ResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource javaR1ResourceMain = ResourceSetUtil.loadOrCreateResource(javaR1ResourceSet, URI.createFileURI("testsrc\\main\\Main.java"));
		Resource javaR1ResourcePerson = ResourceSetUtil.loadOrCreateResource(javaR1ResourceSet, URI.createFileURI("testsrc\\main\\Person.java"));
		resolveAllProxies(javaR1ResourceSet);

//		ResourceSet javaR1AllRS = new ResourceSetImpl();
//		Resource javaR1AllR = javaR1AllRS.createResource(URI.createFileURI("ALL.java"));
//		for (Resource r : new ArrayList<>(javaR1ResourceSet.getResources())) {
//			javaR1AllR.getContents().addAll(r.getContents());
//		}

		// load second revision of java files into models
		final ResourceSet javaR2ResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource javaR2ResourceMain = ResourceSetUtil.loadOrCreateResource(javaR2ResourceSet, URI.createFileURI("testsrc\\main\\Main.java"));
		javaR2ResourceMain.setURI(URI.createFileURI("testsrc/main/Main.java"));
		Resource javaR2ResourcePerson = ResourceSetUtil.loadOrCreateResource(javaR2ResourceSet, URI.createFileURI("testsrc\\main\\Person.java"));
		javaR2ResourcePerson.setURI(URI.createFileURI("testsrc/main/Person.java"));
		resolveAllProxies(javaR2ResourceSet);

//		ResourceSet javaR2AllRS = new ResourceSetImpl();
//		Resource javaR2AllR = javaR2AllRS.createResource(URI.createFileURI("ALL.java"));
//		for (Resource r : new ArrayList<>(javaR2ResourceSet.getResources())) {
//			javaR2AllR.getContents().addAll(r.getContents());
//		}

		// BEGIN DIFFING AND PROPAGATION

		// create empty dummy resource (could be considered R0)
//		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		Resource emptyDummyPersonResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Person.java"));
//		final TransactionalChange recordedChange1 = computeChanges(javaR1ResourcePerson, emptyDummyPersonResource, dummyResourceSet);
//		Resource emptyDummyMainResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Main.java"));
//		final TransactionalChange recordedChange2 = computeChanges(javaR1ResourceMain, emptyDummyMainResource, dummyResourceSet);

		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());

		// final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(dummyResourceSet);
		changeRecorder.addToRecording(dummyResourceSet);
		changeRecorder.beginRecording();

		Resource emptyDummyPersonResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Person.java"));
		mergeChanges(javaR1ResourcePerson, emptyDummyPersonResource, dummyResourceSet);
		Resource emptyDummyMainResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Main.java"));
		mergeChanges(javaR1ResourceMain, emptyDummyMainResource, dummyResourceSet);
		// TODO: the order of recorded changes is important here! first person, then main!

		final TransactionalChange recordedChange = changeRecorder.endRecording();

		// ...
		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);

		// propagate recorded changes into vmp1
		vmp1.propagateChange(recordedChange);
//		vmp1.propagateChange(recordedChange2);
		// vmp1.propagateChangedState(javaR1AllR);

		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1

		// TODO: do the second revision!
	}

	@Test // this is TODO 4.5
	public void multipleInternalizeMultipleJavaIntoAllVave(@TestProject final Path projectFolder) throws Exception {
		// create vave instance
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new JavaDomainProvider().getDomain());

		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, new HashSet<>(), UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), projectFolder);

		JamoppLibraryHelper.registerStdLib();

		{
			final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
			resourceSet.getResource(URI.createFileURI("testsrc\\main\\Main.java"), true);
			resourceSet.getResource(URI.createFileURI("testsrc\\main\\Person.java"), true);
//		Resource resourceMain = ResourceSetUtil.loadOrCreateResource(resourceSet, URI.createFileURI("testsrc\\main\\Main.java"));
//		Resource resourcePerson = ResourceSetUtil.loadOrCreateResource(resourceSet, URI.createFileURI("testsrc\\main\\Person.java"));
//		resourceMain.unload();
//		resourcePerson.unload();
		}

		// load first revision of java files into models
		final ResourceSet javaR1ResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		Resource javaR1ResourceMain = ResourceSetUtil.loadOrCreateResource(javaR1ResourceSet, URI.createFileURI("testsrc\\main\\Main.java"));
//		Resource javaR1ResourcePerson = ResourceSetUtil.loadOrCreateResource(javaR1ResourceSet, URI.createFileURI("testsrc\\main\\Person.java"));
		Resource javaR1ResourceMain = javaR1ResourceSet.getResource(URI.createFileURI("testsrc\\main\\Main.java"), true);
		Resource javaR1ResourcePerson = javaR1ResourceSet.getResource(URI.createFileURI("testsrc\\main\\Person.java"), true);
//		resolveAllProxies(javaR1ResourceSet);

		ResourceSet javaR1AllRS = new ResourceSetImpl();
		Resource javaR1AllR = javaR1AllRS.createResource(URI.createFileURI("ALL.java"));
		for (Resource r : new ArrayList<>(javaR1ResourceSet.getResources())) {
			javaR1AllR.getContents().addAll(r.getContents());
		}
		resolveAllProxies(javaR1AllRS);

//		// load second revision of java files into models
//		final ResourceSet javaR2ResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		Resource javaR2ResourceMain = ResourceSetUtil.loadOrCreateResource(javaR2ResourceSet, URI.createFileURI("testsrc2\\main\\Main.java"));
//		javaR2ResourceMain.setURI(URI.createFileURI("testsrc/main/Main.java"));
//		Resource javaR2ResourcePerson = ResourceSetUtil.loadOrCreateResource(javaR2ResourceSet, URI.createFileURI("testsrc2\\main\\Person.java"));
//		javaR2ResourcePerson.setURI(URI.createFileURI("testsrc/main/Person.java"));
//		resolveAllProxies(javaR2ResourceSet);

//		ResourceSet javaR2AllRS = new ResourceSetImpl();
//		Resource javaR2AllR = javaR2AllRS.createResource(URI.createFileURI("ALL.java"));
//		for (Resource r : new ArrayList<>(javaR2ResourceSet.getResources())) {
//			javaR2AllR.getContents().addAll(r.getContents());
//		}

		// BEGIN DIFFING AND PROPAGATION

		// create empty dummy resource (could be considered R0)
//		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		Resource emptyDummyPersonResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Person.java"));
//		final TransactionalChange recordedChange1 = computeChanges(javaR1ResourcePerson, emptyDummyPersonResource, dummyResourceSet);
//		Resource emptyDummyMainResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Main.java"));
//		final TransactionalChange recordedChange2 = computeChanges(javaR1ResourceMain, emptyDummyMainResource, dummyResourceSet);

		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());

		final ChangeRecorder changeRecorder = new ChangeRecorder(dummyResourceSet);
		changeRecorder.addToRecording(dummyResourceSet);
		changeRecorder.beginRecording();

		Resource emptyDummyResource = dummyResourceSet.createResource(URI.createFileURI("ALL.java"));
		mergeChanges(javaR1AllR, emptyDummyResource, dummyResourceSet);

		final TransactionalChange recordedChange = changeRecorder.endRecording();

		// ...
		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);

		// propagate recorded changes into vmp1
		vmp1.propagateChange(recordedChange);

		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1
	}

	@Test // this is TODO 5
	public void multipleInternalizeMultipleJavaMultipleDomainIntoVave(@TestProject final Path projectFolder) throws Exception {
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

		JamoppLibraryHelper.registerStdLib();

		// load first revision of java files into models
		final ResourceSet javaR1ResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		javaR1ResourceSet.getLoadOptions().put(org.emftext.language.java.resource.java.IJavaOptions.DISABLE_LAYOUT_INFORMATION_RECORDING, Boolean.TRUE);
//		javaR1ResourceSet.getLoadOptions().put(org.emftext.language.java.resource.java.IJavaOptions.DISABLE_LOCATION_MAP, Boolean.TRUE);
		javaR1ResourceSet.getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
		Resource javaR1ResourcePI = ResourceSetUtil.loadOrCreateResource(javaR1ResourceSet, URI.createFileURI("testsrc\\main\\package-info.java"));
		Resource javaR1ResourcePerson = ResourceSetUtil.loadOrCreateResource(javaR1ResourceSet, URI.createFileURI("testsrc\\main\\Person.java"));
		Resource javaR1ResourceMain = ResourceSetUtil.loadOrCreateResource(javaR1ResourceSet, URI.createFileURI("testsrc\\main\\Main.java"));
		resolveAllProxies(javaR1ResourceSet);

		// load second revision of java files into models
		final ResourceSet javaR2ResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource javaR2ResourcePI = ResourceSetUtil.loadOrCreateResource(javaR2ResourceSet, URI.createFileURI("testsrc2\\main\\package-info.java"));
		javaR2ResourcePI.setURI(URI.createFileURI("testsrc/main/package-info.java"));
		Resource javaR2ResourcePerson = ResourceSetUtil.loadOrCreateResource(javaR2ResourceSet, URI.createFileURI("testsrc2\\main\\Person.java"));
		javaR2ResourcePerson.setURI(URI.createFileURI("testsrc/main/Person.java"));
		Resource javaR2ResourceMain = ResourceSetUtil.loadOrCreateResource(javaR2ResourceSet, URI.createFileURI("testsrc2\\main\\Main.java"));
		javaR2ResourceMain.setURI(URI.createFileURI("testsrc/main/Main.java"));
		resolveAllProxies(javaR2ResourceSet);

		// BEGIN DIFFING AND PROPAGATION

		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());

		// final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(dummyResourceSet);
		changeRecorder.addToRecording(dummyResourceSet);
		changeRecorder.beginRecording();

		Resource emptyDummyPIResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/package-info.java"));
		mergeChanges(javaR1ResourcePI, emptyDummyPIResource, dummyResourceSet);
		Resource emptyDummyPersonResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Person.java"));
		mergeChanges(javaR1ResourcePerson, emptyDummyPersonResource, dummyResourceSet);
		Resource emptyDummyMainResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Main.java"));
		mergeChanges(javaR1ResourceMain, emptyDummyMainResource, dummyResourceSet);
		// TODO: the order of recorded changes is important here! first person, then main!

		// Resource emptyDummyUMLResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/test/test.uml"));
		// emptyDummyUMLResource.save(null);

		final TransactionalChange recordedChange = changeRecorder.endRecording();

		// ...
		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);

		// BEGIN CHANGE ORDER TEST

		ArrayList<EChange> newEChanges = new ArrayList<>();
		ArrayList<EChange> toAppend = new ArrayList<>();
		for (EChange change : recordedChange.getEChanges()) {
			if ((change instanceof ReplaceSingleValuedEReference) // ) {
					&& !((EObject) ((ReplaceSingleValuedEReference) change).getNewValue()).eResource().getURI().equals(((ReplaceSingleValuedEReference) change).getAffectedEObject().eResource().getURI())) {
//					&& ((EObject)((ReplaceSingleValuedEReference)change).getNewValue()).eResource().getURI().toString().contains("Person")) {
				toAppend.add(change);
				System.out.println("moved change to back " + change + " - ");
			} else {
				newEChanges.add(change);
			}
		}
		ArrayList<EChange> orderedChanges = new ArrayList<>();
		orderedChanges.addAll(newEChanges);
		orderedChanges.addAll(toAppend);
		TransactionalChange orderedChange = new TransactionalChangeImpl(orderedChanges);

//		FeatureEChange<?, ?>: eChange.affectedEObject
//		EObjectExistenceEChange<?>: eChange.affectedEObject
//		InsertRootEObject<?>: eChange.newValue
//		RemoveRootEObject<?>: eChange.oldValue

		// END CHANGE ORDER TEST

		// propagate recorded changes into vmp1
		vmp1.propagateChange(orderedChange);

		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1

		// TODO: do the second revision!
	}

	@Test // this is TODO 5.5
	public void multipleInternalizeMultipleJavaMultipleDomainIntoAllVave(@TestProject final Path projectFolder) throws Exception {
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

		JamoppLibraryHelper.registerStdLib();

		// WORKAROUND
		{
			final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
			resourceSet.getResource(URI.createFileURI("testsrc\\main\\package-info.java"), true);
			resourceSet.getResource(URI.createFileURI("testsrc\\main\\Main.java"), true);
			resourceSet.getResource(URI.createFileURI("testsrc\\main\\Person.java"), true);
//		Resource resourceMain = ResourceSetUtil.loadOrCreateResource(resourceSet, URI.createFileURI("testsrc\\main\\Main.java"));
//		Resource resourcePerson = ResourceSetUtil.loadOrCreateResource(resourceSet, URI.createFileURI("testsrc\\main\\Person.java"));
//		resourceMain.unload();
//		resourcePerson.unload();
		}

		// load first revision of java files into models
		final ResourceSet javaR1ResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		Resource javaR1ResourcePI = javaR1ResourceSet.getResource(URI.createFileURI("testsrc\\main\\package-info.java"), true);
		Resource javaR1ResourceMain = javaR1ResourceSet.getResource(URI.createFileURI("testsrc\\main\\Main.java"), true);
		Resource javaR1ResourcePerson = javaR1ResourceSet.getResource(URI.createFileURI("testsrc\\main\\Person.java"), true);
//		resolveAllProxies(javaR1ResourceSet);

		ResourceSet javaR1AllRS = new ResourceSetImpl();
		Resource javaR1AllR = javaR1AllRS.createResource(URI.createFileURI("testsrc\\ALL.java"));
		for (Resource r : new ArrayList<>(javaR1ResourceSet.getResources())) {
			javaR1AllR.getContents().addAll(r.getContents());
		}
		resolveAllProxies(javaR1AllRS);

//		// load second revision of java files into models
//		final ResourceSet javaR2ResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		Resource javaR2ResourceMain = ResourceSetUtil.loadOrCreateResource(javaR2ResourceSet, URI.createFileURI("testsrc2\\main\\Main.java"));
//		javaR2ResourceMain.setURI(URI.createFileURI("testsrc/main/Main.java"));
//		Resource javaR2ResourcePerson = ResourceSetUtil.loadOrCreateResource(javaR2ResourceSet, URI.createFileURI("testsrc2\\main\\Person.java"));
//		javaR2ResourcePerson.setURI(URI.createFileURI("testsrc/main/Person.java"));
//		resolveAllProxies(javaR2ResourceSet);

//		ResourceSet javaR2AllRS = new ResourceSetImpl();
//		Resource javaR2AllR = javaR2AllRS.createResource(URI.createFileURI("ALL.java"));
//		for (Resource r : new ArrayList<>(javaR2ResourceSet.getResources())) {
//			javaR2AllR.getContents().addAll(r.getContents());
//		}

		// BEGIN DIFFING AND PROPAGATION

		// create empty dummy resource (could be considered R0)
//		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		Resource emptyDummyPersonResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Person.java"));
//		final TransactionalChange recordedChange1 = computeChanges(javaR1ResourcePerson, emptyDummyPersonResource, dummyResourceSet);
//		Resource emptyDummyMainResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Main.java"));
//		final TransactionalChange recordedChange2 = computeChanges(javaR1ResourceMain, emptyDummyMainResource, dummyResourceSet);

		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());

		final ChangeRecorder changeRecorder = new ChangeRecorder(dummyResourceSet);
		changeRecorder.addToRecording(dummyResourceSet);
		changeRecorder.beginRecording();

		final ResourceSet PIResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource PIResource = ResourceSetUtil.loadOrCreateResource(PIResourceSet, URI.createFileURI("testsrc\\main\\package-info.java"));

		Resource emptyDummyPIResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/package-info.java"));
		mergeChanges(PIResource, emptyDummyPIResource, dummyResourceSet);

		Resource emptyDummyResource = dummyResourceSet.createResource(URI.createFileURI("testsrc\\ALL.java"));
		mergeChanges(javaR1AllR, emptyDummyResource, dummyResourceSet);

		final TransactionalChange recordedChange = changeRecorder.endRecording();

		// ...
		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);

		// propagate recorded changes into vmp1
		vmp1.propagateChange(recordedChange);

		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1
	}

//	@Test
//	public void multipleInternalizeSingleJavaMultipleDomainIntoVave(@TestProject final Path projectFolder) throws Exception {
//		// create vave instance
//		Set<VitruvDomain> domains = new HashSet<>();
//		domains.add(new JavaDomainProvider().getDomain());
//		domains.add(new UmlDomainProvider().getDomain());
//
//		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
//		JavaToUmlChangePropagationSpecification javaumlcps = new JavaToUmlChangePropagationSpecification();
//		changePropagationSpecifications.add(javaumlcps);
//
//		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, projectFolder);
//
//		JamoppLibraryHelper.registerStdLib();
//
//		// load first revision of java files into models
//		final ResourceSet javaR1ResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		Resource javaR1ResourcePerson = ResourceSetUtil.loadOrCreateResource(javaR1ResourceSet, URI.createFileURI("testsrc\\main\\Test.java"));
//		resolveAllProxies(javaR1ResourceSet);
//
//		// BEGIN DIFFING AND PROPAGATION
//
//		ResourceSet dummyResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//
//		// final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		final ChangeRecorder changeRecorder = new ChangeRecorder(dummyResourceSet);
//		changeRecorder.addToRecording(dummyResourceSet);
//		changeRecorder.beginRecording();
//
//		Resource emptyDummyPersonResource = dummyResourceSet.createResource(URI.createFileURI("testsrc/main/Test.java"));
//		mergeChanges(javaR1ResourcePerson, emptyDummyPersonResource, dummyResourceSet);
//
//		final TransactionalChange recordedChange = changeRecorder.endRecording();
//
//		// ...
//		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
//		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);
//
//		// propagate recorded changes into vmp1
//		vmp1.propagateChange(recordedChange);
//
//		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();
//		vave.internalizeChanges(vmp1, trueConstant); // system revision 1
//
//		// TODO: do the second revision!
//	}

	protected static void mergeChanges(Resource left, Resource right, ResourceSet rsright) {
		// compare resource sets directly only works if the resources represent the same files. if the resources represent the same files but in a different location, they will not be matched.
		EMFCompare comparator = EMFCompare.builder().build();

		IComparisonScope scope = new DefaultComparisonScope(left, right, null);
		Comparison comparison = comparator.compare(scope);
		List<Diff> differences = comparison.getDifferences();

		// Let's merge every single diff
		IMerger.Registry mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance();
		IBatchMerger merger = new BatchMerger(mergerRegistry);
		merger.copyAllLeftToRight(differences, new BasicMonitor());
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
