package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.internal.events.BuildCommand;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.internal.resources.ProjectDescription;
import org.eclipse.core.internal.resources.ProjectInfo;
import org.eclipse.core.internal.resources.ResourceInfo;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.jdt.ui.wizards.NewInterfaceWizardPage;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.emftext.language.java.annotations.AnnotationInstance;
import org.emftext.language.java.annotations.AnnotationsFactory;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.types.TypeReference;
import org.junit.runner.Description;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.system.System;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.VitruviusEmfBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.util.BuildProjects;
import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaAddBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaRemoveBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.GeneralChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.VitruviusChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.domains.java.change.feature.reference.JavaInsertEReference;
import edu.kit.ipd.sdq.vitruvius.domains.java.change.feature.reference.ReferenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceModelUtil;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.MonitoredEditor;
import edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.TestUserInteractor;
import edu.kit.ipd.sdq.vitruvius.tests.VitruviusCasestudyTest;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

/**
 * Test class that contains utillity methods that can be used by JaMoPP2PCM
 * transformation tests
 *
 */
@SuppressWarnings("restriction")
public class JaMoPP2PCMTransformationTest extends VitruviusCasestudyTest {

	private static final Logger logger = Logger.getLogger(JaMoPP2PCMTransformationTest.class.getSimpleName());

	protected static final int SELECT_BASIC_COMPONENT = 0;
	private static final int SELECT_COMPOSITE_COMPONENT = 1;
	private static final int SELECT_SYSTEM = 2;
	protected static final int SELECT_NOTHING_DECIDE_LATER = 3;

	protected Package mainPackage;
	protected Package secondPackage;

	public JaMoPP2PCMTransformationTest() {
		super();
	}

	public JaMoPP2PCMTransformationTest(
			Supplier<? extends Change2CommandTransformingProviding> change2CommandTransformingProvidingSupplier) {
		super(change2CommandTransformingProvidingSupplier);
	}

	@Override
	protected void beforeTest(final Description description) throws Throwable {
		super.beforeTest(description);
		this.testUserInteractor = new TestUserInteractor();
		// add PCM Java Builder to Project under test
		final PCMJavaAddBuilder pcmJavaBuilder = new PCMJavaAddBuilder();
		pcmJavaBuilder.addBuilderToProject(this.currentTestProject);
		// build the project
		BuildProjects.issueIncrementalBuildForAllProjectsWithBuilder(PCMJavaBuilder.BUILDER_ID);
		injectChange2CommandTransformingProviding();

		this.resourceSet = new ResourceSetImpl();
		// set new user interactor
		this.setUserInteractor(this.testUserInteractor);
		// deactivate the EMF Monitor
		this.deactivateEMFMonitor();
	}

	private void injectChange2CommandTransformingProviding() throws Throwable {
		/*
		 * FIXME HK This is really hacky: We "inject" the specified
		 * Change2CommandTransformingProviding into the PCMJavaBuilder and its
		 * ChangeSynchronizer to use the one we expect instead of the one loaded
		 * via extension points. If this does not work any more, check for
		 * changed field names.
		 */
		PCMJavaBuilder builder = getPCMJavaBuilderFromProject();
		Change2CommandTransformingProviding transformingProviding = syncTransformationProviderSupplier.get();
		JavaBridge.setFieldInClass(VitruviusEmfBuilder.class, "transformingProviding", builder, transformingProviding);
		ChangeSynchronizing changeSynchronizing = JavaBridge.getFieldFromClass(VitruviusEmfBuilder.class,
				"changeSynchronizing", builder);
		JavaBridge.setFieldInClass(ChangeSynchronizerImpl.class, "change2CommandTransformingProviding",
				changeSynchronizing, transformingProviding);
	}

	@Override
	protected void afterTest(final org.junit.runner.Description description) {
		// tell the code monitor to not report changes any more
		try {
			final PCMJavaBuilder pcmJavaBuilder = this.getPCMJavaBuilderFromProject();
			if (null != pcmJavaBuilder) {
				final MonitoredEditor monitoredEditor = JavaBridge.getFieldFromClass(PCMJavaBuilder.class,
						"javaMonitoredEditor", pcmJavaBuilder);
				monitoredEditor.setReportChanges(false);
			}
		} catch (final Throwable e) {
			// do nothing since we
		}
		// Remove PCM Java Builder
		final PCMJavaRemoveBuilder pcmJavaRemoveBuilder = new PCMJavaRemoveBuilder();
		pcmJavaRemoveBuilder.removeBuilderFromProject(this.currentTestProject);
	}

	@Override
	protected CorrespondenceModel getCorrespondenceModel() throws Throwable {
		final VSUMImpl vsum = this.getVSUM();
		final VURI jaMoPPVURI = VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE);
		final VURI pcmVURI = VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE);
		final CorrespondenceModel corresponcenceInstance = vsum.getCorrespondenceModelOriginal(pcmVURI, jaMoPPVURI);
		return corresponcenceInstance;
	}

	protected VSUMImpl getVSUM() throws Throwable {
		final PCMJavaBuilder pcmJavaBuilder = this.getPCMJavaBuilderFromProject();
		if (null == pcmJavaBuilder) {
			return null;
		}
		final VSUMImpl vsum = JavaBridge.getFieldFromClass(VitruviusEmfBuilder.class, "vsum", pcmJavaBuilder);
		return vsum;
	}

	private PCMJavaBuilder getPCMJavaBuilderFromProject() throws Throwable {
		final Project project = (Project) this.currentTestProject;
		final ResourceInfo info = project.getResourceInfo(false, false);
		final ProjectDescription description = ((ProjectInfo) info).getDescription();
		final boolean makeCopy = false;
		for (final ICommand command : description.getBuildSpec(makeCopy)) {
			if (command.getBuilderName().equals(PCMJavaBuilder.BUILDER_ID)) {
				final BuildCommand buildCommand = (BuildCommand) command;
				final IncrementalProjectBuilder ipb = buildCommand
						.getBuilder(this.currentTestProject.getActiveBuildConfig());
				final PCMJavaBuilder pcmJavaBuilder = (PCMJavaBuilder) ipb;
				return pcmJavaBuilder;
			}
		}
		logger.warn("Could not find any PCMJavaBuilder");
		return null;
	}

	protected Repository addRepoContractsAndDatatypesPackage() throws Throwable {
		this.mainPackage = this.createPackageWithPackageInfo(new String[] { PCM2JaMoPPTestUtils.REPOSITORY_NAME });
		this.createPackageWithPackageInfo(new String[] { PCM2JaMoPPTestUtils.REPOSITORY_NAME, "contracts" });
		this.createPackageWithPackageInfo(new String[] { PCM2JaMoPPTestUtils.REPOSITORY_NAME, "datatypes" });
		final CorrespondenceModel ci = this.getCorrespondenceModel();
		if (null == ci) {
			throw new RuntimeException("Could not get correspondence instance.");
		}
		TestUtil.waitForSynchronization();
		final Repository repo = CollectionBridge.claimOne(
				CorrespondenceModelUtil.getCorrespondingEObjectsByType(ci, this.mainPackage, Repository.class));
		return repo;
	}

	protected BasicComponent addSecondPackageCorrespondsToBasicComponent() throws Throwable {
		this.testUserInteractor.addNextSelections(SELECT_BASIC_COMPONENT);
		return this.createSecondPackage(BasicComponent.class, PCM2JaMoPPTestUtils.REPOSITORY_NAME,
				PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME);
	}

	protected <T> T createSecondPackage(final Class<T> correspondingType, final String... namespace) throws Throwable {
		this.secondPackage = this.createPackageWithPackageInfo(namespace);
		TestUtil.waitForSynchronization();
		return CollectionBridge.claimOne(CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), this.secondPackage, correspondingType));
	}

	private void createSecondPackageWithoutCorrespondence(final String... namespace) throws Throwable {
		this.secondPackage = this.createPackageWithPackageInfo(namespace);
		TestUtil.waitForSynchronization();
	}

	protected void createPackage(final String[] namespace) throws Throwable {
		final IPackageFragmentRoot packageRoot = this.getIJavaProject();
		final String namespaceDotted = StringUtils.join(namespace, ".");
		final boolean force = true;
		packageRoot.createPackageFragment(namespaceDotted, force, new NullProgressMonitor());
		TestUtil.waitForSynchronization();
	}

	protected Package createPackageWithPackageInfo(final String... namespace) throws Throwable {
		String packageFile = StringUtils.join(namespace, "/");
		packageFile = packageFile + "/package-info.java";
		final Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
		final List<String> namespaceList = Arrays.asList(namespace);
		jaMoPPPackage.setName(namespaceList.get(namespaceList.size() - 1));
		jaMoPPPackage.getNamespaces().addAll(namespaceList.subList(0, namespaceList.size() - 1));
		final VURI packageVURI = this.createVURIForSrcFile(packageFile);
		final Resource resource = this.resourceSet.createResource(packageVURI.getEMFUri());
		EcoreResourceBridge.saveEObjectAsOnlyContent(jaMoPPPackage, resource);
		TestUtil.waitForSynchronization();
		return jaMoPPPackage;
	}

	protected Package renamePackage(final Package packageToRename, String newName) throws Throwable {
		final Resource resource = packageToRename.eResource();
		final IFile iFile = EMFBridge.getIFileForEMFUri(resource.getURI());
		IPath iPath = iFile.getProjectRelativePath();
		iPath = iPath.removeLastSegments(1);
		final String oldPackageName = packageToRename.getName();
		if (oldPackageName.contains(".")) {
			newName = oldPackageName.substring(0, oldPackageName.lastIndexOf(".") + 1) + newName;
		}
		final IFolder iFolder = iFile.getProject().getFolder(iPath);
		final IJavaElement javaPackage = JavaCore.create(iFolder);
		this.refactorRenameJavaElement(newName, javaPackage, IJavaRefactorings.RENAME_PACKAGE);
		final Package newPackage = this.findJaMoPPPackageWithName(newName);
		return newPackage;
	}

	private void refactorRenameJavaElement(final String newName, final IJavaElement iJavaElement,
			final String refactorRenameActionName) throws CoreException {
		final RefactoringContribution refacContrib = RefactoringCore
				.getRefactoringContribution(refactorRenameActionName);
		final RefactoringStatus status = new RefactoringStatus();
		final RenameJavaElementDescriptor desc = (RenameJavaElementDescriptor) refacContrib.createDescriptor();
		desc.setUpdateReferences(true);
		desc.setJavaElement(iJavaElement);
		desc.setNewName(newName);
		final NullProgressMonitor monitor = new NullProgressMonitor();
		final Refactoring refactoring = desc.createRefactoring(status);
		refactoring.checkInitialConditions(monitor);
		refactoring.checkFinalConditions(monitor);
		final Change change = refactoring.createChange(monitor);
		change.perform(monitor);
	}

	protected <T> T renameClassifierWithName(final String entityName, final String newName, final Class<T> type)
			throws Throwable {
		try {
			final ICompilationUnit cu = CompilationUnitManipulatorHelper
					.findICompilationUnitWithClassName(entityName + ".java", this.currentTestProject);
			final int offset = cu.getBuffer().getContents().indexOf(entityName);
			if (cu.getBuffer() instanceof IBuffer.ITextEditCapability) {
				logger.info(cu.getBuffer());
			}
			final ReplaceEdit edit = new ReplaceEdit(offset, entityName.length(), newName);
			CompilationUnitManipulatorHelper.editCompilationUnit(cu, edit);
			TestUtil.waitForSynchronization();
			final VURI vuri = VURI.getInstance(cu.getResource());
			final Classifier jaMoPPClass = this.getJaMoPPClassifierForVURI(vuri);
			return CollectionBridge.claimOne(CorrespondenceModelUtil
					.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), jaMoPPClass, type));
		} catch (final Throwable e) {
			logger.warn(e.getMessage());
		}
		return null;

	}

	private Package findJaMoPPPackageWithName(final String newName) throws Throwable {
		final IJavaProject javaProject = JavaCore.create(this.currentTestProject);
		for (final IPackageFragmentRoot packageFragmentRoot : javaProject.getPackageFragmentRoots()) {
			final IJavaElement[] children = packageFragmentRoot.getChildren();
			for (final IJavaElement iJavaElement : children) {
				if (iJavaElement instanceof IPackageFragment) {
					final IPackageFragment fragment = (IPackageFragment) iJavaElement;
					if (fragment.getElementName().equals(newName)) {
						final VURI vuri = this.getVURIForElementInPackage(fragment, "package-info");
						final Package jaMoPPPackage = this.getJaMoPPRootForVURI(vuri);
						return jaMoPPPackage;
					}
					// final IJavaElement[] javaElements =
					// fragment.getChildren();
					// for (int k = 0; k < javaElements.length; k++) {
					// final IJavaElement javaElement = javaElements[k];
					// if (javaElement.getElementType() ==
					// IJavaElement.PACKAGE_FRAGMENT) {
					// final IPackageFragment packageFragment =
					// (IPackageFragment) javaElement;
					// if (packageFragment.getElementName().equals(newName)) {
					// final VURI vuri =
					// this.getVURIForElementInPackage(packageFragment,
					// "package-info.java");
					// final Package jaMoPPPackage =
					// this.getJaMoPPRootForVURI(vuri);
					// return jaMoPPPackage;
					// }
					// }
					// }
				}
			}
		}
		throw new RuntimeException("Could not find a compilation unit with name " + newName);
	}

	private IPackageFragmentRoot getIJavaProject() throws Throwable {
		final IProject project = this.currentTestProject;
		final IJavaProject javaProject = JavaCore.create(project);
		final IFolder sourceFolder = project.getFolder(TestUtil.SOURCE_FOLDER);
		if (!sourceFolder.exists()) {
			final boolean force = true;
			final boolean local = true;
			sourceFolder.create(force, local, new NullProgressMonitor());
		}
		final IPackageFragmentRoot packageFragment = javaProject.getPackageFragmentRoot(sourceFolder);
		return packageFragment;
	}

	private VURI createVURIForSrcFile(final String srcFilePath) {
		final String vuriKey = this.getProjectPath() + TestUtil.SOURCE_FOLDER + "/" + srcFilePath;
		return VURI.getInstance(vuriKey);
	}

	protected <T> T addClassInSecondPackage(final Class<T> classOfCorrespondingObject) throws Throwable {
		final T createdEObject = this.addClassInPackage(this.secondPackage, classOfCorrespondingObject);
		return createdEObject;
	}

	protected <T> T addClassInPackage(final Package packageForClass, final Class<T> classOfCorrespondingObject)
			throws Throwable {
		final String implementingClassName = PCM2JaMoPPTestUtils.IMPLEMENTING_CLASS_NAME;
		return this.addClassInPackage(packageForClass, classOfCorrespondingObject, implementingClassName);
	}

	protected <T> T addClassInPackage(final Package packageForClass, final Class<T> classOfCorrespondingObject,
			final String implementingClassName) throws Throwable, CoreException, InterruptedException {
		final Classifier jaMoPPClass = this.createClassInPackage(packageForClass, implementingClassName);
		final Set<T> eObjectsByType = CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), jaMoPPClass, classOfCorrespondingObject);
		return CollectionBridge.claimOne(eObjectsByType);
	}

	protected Classifier createClassInPackage(final Package packageForClass, final String implementingClassName)
			throws Throwable, CoreException, InterruptedException {
		String packageName = packageForClass.getName();
		String packageNamespace = packageForClass.getNamespacesAsString() + packageName;
		return createClassInPackage(implementingClassName, packageNamespace);
	}

	protected Classifier createClassInPackage(final String implementingClassName, String packageNamespace)
			throws Throwable, CoreException, InterruptedException {
		final IPackageFragmentRoot packageRoot = this.getIJavaProject();
		final IPackageFragment packageFragment = this.getPackageFragment(packageNamespace);
		final NewClassWizardPage classWizard = new NewClassWizardPage();
		classWizard.setPackageFragment(packageFragment, false);
		classWizard.setPackageFragmentRoot(packageRoot, false);
		classWizard.setTypeName(implementingClassName, true);
		classWizard.createType(new NullProgressMonitor());

		final VURI vuri = this.getVURIForElementInPackage(packageFragment, implementingClassName);
		final Classifier jaMoPPClass = this.getJaMoPPClassifierForVURI(vuri);
		TestUtil.waitForSynchronization();
		return jaMoPPClass;
	}

	private VURI getVURIForElementInPackage(final IPackageFragment packageFragment, final String elementName) {
		String vuriKey = packageFragment.getResource().getFullPath().toString() + "/" + elementName + "."
				+ JaMoPPNamespace.JAVA_FILE_EXTENSION;
		if (vuriKey.startsWith("/")) {
			vuriKey = vuriKey.substring(1, vuriKey.length());
		}
		final VURI vuri = VURI.getInstance(vuriKey);
		return vuri;
	}

	private IPackageFragment getPackageFragment(String packageNamespace) throws Throwable {
		final IPackageFragmentRoot packageRoot = this.getIJavaProject();
		for (final IJavaElement javaElement : packageRoot.getChildren()) {
			if (javaElement instanceof IPackageFragment && javaElement.getElementName().equals(packageNamespace)) {
				return (IPackageFragment) javaElement;
			}
		}
		throw new RuntimeException("No packageFragment found for JaMoPP package " + packageNamespace);
	}

	protected ConcreteClassifier getJaMoPPClassifierForVURI(final VURI vuri) {
		final CompilationUnit cu = this.getJaMoPPRootForVURI(vuri);
		final Classifier jaMoPPClassifier = cu.getClassifiers().get(0);
		return (ConcreteClassifier) jaMoPPClassifier;
	}

	private <T extends JavaRoot> T getJaMoPPRootForVURI(final VURI vuri) {
		final Resource resource = EcoreResourceBridge.loadResourceAtURI(vuri.getEMFUri(), new ResourceSetImpl());
		// unchecked is OK for the test.
		@SuppressWarnings("unchecked")
		final T javaRoot = (T) resource.getContents().get(0);
		return javaRoot;
	}

	/**
	 * Change user interactor in changeSynchronizer of
	 * PCMJaMoPPTransformationExecuter by invoking the setUserInteractor method
	 * of the class ChangeSynchronizer
	 *
	 * @throws Throwable
	 */
	private void setUserInteractor(final UserInteracting newUserInteracting) throws Throwable {
		final PCMJavaBuilder pcmJavaBuilder = this.getPCMJavaBuilderFromProject();
		final Change2CommandTransformingProviding transformingProviding = JavaBridge
				.getFieldFromClass(VitruviusEmfBuilder.class, "transformingProviding", pcmJavaBuilder);
		this.setUserInteractor(newUserInteracting, transformingProviding);
	}

	protected CompositeComponent addSecondPackageCorrespondsToCompositeComponent() throws Throwable {
		this.testUserInteractor.addNextSelections(SELECT_COMPOSITE_COMPONENT);
		return this.createSecondPackage(CompositeComponent.class, PCM2JaMoPPTestUtils.REPOSITORY_NAME,
				PCM2JaMoPPTestUtils.COMPOSITE_COMPONENT_NAME);
	}

	protected org.palladiosimulator.pcm.system.System addSecondPackageCorrespondsToSystem() throws Throwable {
		this.testUserInteractor.addNextSelections(SELECT_SYSTEM);
		return this.createSecondPackage(System.class, PCM2JaMoPPTestUtils.SYSTEM_NAME);
	}

	protected void addSecondPackageCorrespondsWithoutCorrespondences() throws Throwable {
		this.testUserInteractor.addNextSelections(SELECT_NOTHING_DECIDE_LATER);
		this.createSecondPackageWithoutCorrespondence(PCM2JaMoPPTestUtils.REPOSITORY_NAME,
				PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME);
	}

	protected void assertRepositoryAndPCMName(final Repository repo, final RepositoryComponent repoComponent,
			final String expectedName) throws Throwable {

		assertEquals("Repository of compoennt is not the repository: " + repo, repo.getId(),
				repoComponent.getRepository__RepositoryComponent().getId());

		this.assertPCMNamedElement(repoComponent, expectedName);
	}

	protected void assertResourceAndFileForEObjects(final EObject... eObjects) throws Throwable {
		for (final EObject eObject : eObjects) {
			final Resource eResource = eObject.eResource();
			assertNotNull("Resource of eObject " + eObject + " is null", eResource);
			final IFile iFile = EMFBridge.getIFileForEMFUri(eResource.getURI());
			assertTrue("No IFile for eObject " + eObject + " in resource " + eResource + " found.", iFile.exists());
		}
	}

	protected void assertFilesOnlyForEObjects(final EObject... eObjects) throws Throwable {
		final Set<String> fullFilePaths = new HashSet<String>();
		for (final EObject eObject : eObjects) {
			final IFile iFile = EMFBridge.getIFileForEMFUri(eObject.eResource().getURI());
			fullFilePaths.add(iFile.getFullPath().toString());
		}
		final IFolder folder = this.currentTestProject.getFolder("model");
		final List<String> foundAdditionalFiles = new ArrayList<>();
		for (final IResource iResource : folder.members()) {
			final String iResourcePath = iResource.getFullPath().toString();
			if (!fullFilePaths.contains(iResourcePath)) {
				foundAdditionalFiles.add(iResourcePath);
			}
		}
		if (0 < foundAdditionalFiles.size()) {
			final StringBuilder failMsg = new StringBuilder("Found addtional files in model folder: ");
			foundAdditionalFiles.forEach(str -> failMsg.append(str).append(", "));
			fail(failMsg.toString());
		}
	}

	protected void assertRepositoryAndPCMNameForDatatype(final Repository repo, final DataType dt,
			final String expectedName) throws Throwable {

		assertEquals("Repository of compoennt is not the repository: " + repo, repo.getId(),
				dt.getRepository__DataType().getId());
		if (dt instanceof CompositeDataType) {
			this.assertPCMNamedElement((CompositeDataType) dt, expectedName);
		} else if (dt instanceof CollectionDataType) {
			this.assertPCMNamedElement((CollectionDataType) dt, expectedName);
		} else {
			throw new RuntimeException("Primitive data types should not have a correspondence to classes");
		}
	}

	protected void assertPCMNamedElement(final NamedElement pcmNamedElement, final String expectedName)
			throws Throwable {
		assertEquals("The name of pcm named element is not " + expectedName, expectedName,
				pcmNamedElement.getEntityName());
		this.assertResourceAndFileForEObjects(pcmNamedElement);
	}

	protected OperationInterface addInterfaceInContractsPackage() throws Throwable {
		return this.createInterfaceInPackage("contracts");
	}

	private OperationInterface createInterfaceInPackage(final String packageName)
			throws Throwable, CoreException, InterruptedException {
		final String interfaceName = PCM2JaMoPPTestUtils.INTERFACE_NAME;
		return this.createInterfaceInPackageBasedOnJaMoPPPackageWithCorrespondence(packageName, interfaceName);
	}

	protected OperationInterface createInterfaceInPackageBasedOnJaMoPPPackageWithCorrespondence(
			final String packageName, final String interfaceName)
					throws Throwable, CoreException, InterruptedException {
		Package jaMoPPPackage = this.getPackageWithNameFromCorrespondenceModel(packageName);
		return this.createInterfaceInPackage(jaMoPPPackage.getNamespacesAsString() + jaMoPPPackage.getName(),
				interfaceName, true);
	}

	protected ConcreteClassifier createInterfaceInPackageBasedOnJaMoPPPackageWithoutCorrespondence(final String packageName,
			final String interfaceName) throws Throwable, CoreException, InterruptedException {
		Package jaMoPPPackage = this.getPackageWithNameFromCorrespondenceModel(packageName);
		return this.createJaMoPPInterfaceInPackage(jaMoPPPackage.getNamespacesAsString(), interfaceName);
	}

	protected OperationInterface createInterfaceInPackage(String packageNamespace, final String interfaceName, boolean claimOne)
			throws Throwable, CoreException, InterruptedException {
		final Classifier jaMoPPIf = createJaMoPPInterfaceInPackage(packageNamespace, interfaceName);
		Set<OperationInterface> correspondingOpInterfaces = CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), jaMoPPIf, OperationInterface.class);
		if(claimOne){
			return CollectionBridge.claimOne(correspondingOpInterfaces);
		}
		if(null==correspondingOpInterfaces || 0 == correspondingOpInterfaces.size()){
			return null;
		}
		logger.warn("More than one corresponding interfaces found for interface " + jaMoPPIf + ". Returning the first");
		return correspondingOpInterfaces.iterator().next();
	}

	protected ConcreteClassifier createJaMoPPInterfaceInPackage(String packageNamespace, final String interfaceName)
			throws Throwable, CoreException, InterruptedException {
		final IPackageFragment packageFragment = this.getPackageFragment(packageNamespace);
		final NewInterfaceWizardPage interfaceWizard = new NewInterfaceWizardPage();
		interfaceWizard.setPackageFragment(packageFragment, false);
		interfaceWizard.setPackageFragmentRoot(this.getIJavaProject(), false);
		interfaceWizard.setTypeName(interfaceName, true);
		interfaceWizard.createType(new NullProgressMonitor());
		final VURI vuri = this.getVURIForElementInPackage(packageFragment, interfaceName);
		TestUtil.waitForSynchronization();
		final ConcreteClassifier jaMoPPIf = this.getJaMoPPClassifierForVURI(vuri);
		return jaMoPPIf;
	}

	protected OperationInterface addInterfaceInSecondPackageWithCorrespondence(final String packageName)
			throws Throwable {
		this.testUserInteractor.addNextSelections(0);
		return this.createInterfaceInPackage(packageName);
	}

	protected EObject addInterfaceInPackageWithoutCorrespondence(final String packageName) throws Throwable {
		this.testUserInteractor.addNextSelections(1);
		Package jaMoPPPackage = this.getPackageWithNameFromCorrespondenceModel(packageName);
		return this.createInterfaceInPackage(jaMoPPPackage.getNamespacesAsString() + jaMoPPPackage.getName(), "I" + packageName, false);
	}

	protected Package getPackageWithNameFromCorrespondenceModel(final String name) throws Throwable {
		final Set<Package> packages = CorrespondenceModelUtil
				.getAllEObjectsOfTypeInCorrespondences(this.getCorrespondenceModel(), Package.class);
		for (final Package currentPackage : packages) {
			if (currentPackage.getName().equals(name)) {
				return currentPackage;
			}
		}
		throw new RuntimeException("Could not find package with name " + name);
	}

	protected OperationSignature addMethodToInterfaceWithCorrespondence(final String interfaceName) throws Throwable {
		final String methodName = PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME;
		return this.addMethodToInterfaceWithCorrespondence(interfaceName, methodName);
	}

	protected OperationSignature addMethodToInterfaceWithCorrespondence(final String interfaceName,
			final String methodName) throws Throwable, JavaModelException {
		final String methodString = "\nvoid " + methodName + "();\n";
		final ICompilationUnit cu = CompilationUnitManipulatorHelper.addMethodToCompilationUnit(interfaceName,
				methodString, this.currentTestProject);
		return this.findOperationSignatureForJaMoPPMethodInCompilationUnit(methodName, interfaceName, cu);
	}

	protected ResourceDemandingSEFF addClassMethodToClassThatOverridesInterfaceMethod(final String className,
			final String methodName) throws Throwable {
		final String methodString = "\n\tpublic void " + methodName + " () {\n\t}\n";
		final ICompilationUnit icu = CompilationUnitManipulatorHelper.addMethodToCompilationUnit(className,
				methodString, this.currentTestProject);
		final Method jaMoPPMethod = this.findJaMoPPMethodInICU(icu, methodName);
		final ClassMethod classMethod = (ClassMethod) jaMoPPMethod;
		return CollectionBridge.claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(
				this.getCorrespondenceModel(), classMethod, ResourceDemandingSEFF.class));
	}

	protected OperationSignature findOperationSignatureForJaMoPPMethodInCompilationUnit(final String methodName,
			final String interfaceName, final ICompilationUnit cu) throws Throwable {
		final VURI vuri = VURI.getInstance(cu.getResource());
		final Classifier classifier = this.getJaMoPPClassifierForVURI(vuri);
		final Interface jaMoPPInterface = (Interface) classifier;
		for (final Method jaMoPPMethod : jaMoPPInterface.getMethods()) {
			if (jaMoPPMethod.getName().equals(methodName)) {
				return CollectionBridge.claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(
						this.getCorrespondenceModel(), jaMoPPMethod, OperationSignature.class));
			}
		}
		logger.warn("No JaMoPP method with name " + methodName + " found in " + interfaceName);
		return null;
	}

	protected OperationSignature renameMethodInClassWithName(final String className, final String methodName)
			throws Throwable {
		final ICompilationUnit cu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
				this.currentTestProject);
		final IMethod iMethod = cu.getType(className).getMethod(methodName, null);
		final int offset = iMethod.getNameRange().getOffset();
		final int length = iMethod.getNameRange().getLength();
		final String newMethodName = methodName + PCM2JaMoPPTestUtils.RENAME;
		final ReplaceEdit replaceEdit = new ReplaceEdit(offset, length, newMethodName);
		CompilationUnitManipulatorHelper.editCompilationUnit(cu, replaceEdit);
		TestUtil.waitForSynchronization();
		return this.findOperationSignatureForJaMoPPMethodInCompilationUnit(newMethodName, className, cu);
	}

	protected void assertDataTypeName(final TypeReference typeReference, final DataType pcmDataType) {
		final String jaMoPPTypeName = PCM2JaMoPPUtils.getNameFromJaMoPPType(typeReference);
		final String pcmTypeName = this.getNameFromPCMDataType(pcmDataType);
		assertEquals("The name of the PCM datatype does not equal the JaMoPP type name", jaMoPPTypeName, pcmTypeName);
	}

	protected String getNameFromPCMDataType(final DataType pcmDataType) {
		if (null == pcmDataType) {
			return "void";
		} else if (pcmDataType instanceof CollectionDataType) {
			return ((CollectionDataType) pcmDataType).getEntityName();
		} else if (pcmDataType instanceof CompositeDataType) {
			return ((CompositeDataType) pcmDataType).getEntityName();
		} else if (pcmDataType instanceof PrimitiveDataType) {
			return this.getNameFromPrimitveDataType((PrimitiveDataType) pcmDataType);
		}
		throw new RuntimeException("getNameFromPCMDataType failed");
	}

	private String getNameFromPrimitveDataType(final PrimitiveDataType pcmDataType) {
		switch (pcmDataType.getType()) {
		case BOOL:
			return "boolean";
		case CHAR:
			return "char";
		case BYTE:
			return "byte";
		case DOUBLE:
			return "double";
		case INT:
			return "int";
		case LONG:
			return "long";
		case STRING:
			return "String";
		}
		throw new RuntimeException("getNameFromPrimitveDataType");
	}

	protected Parameter addParameterToSignature(final String interfaceName, final String methodName,
			final String typeName, final String parameterName, final String[] parameterTypeSignatures)
					throws Throwable {
		final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(interfaceName,
				this.currentTestProject);
		final IMethod iMethod = icu.getType(interfaceName).getMethod(methodName, parameterTypeSignatures);
		final String parameterStr = typeName + " " + parameterName;
		return this.insertParameterIntoSignature(methodName, parameterName, icu, iMethod, parameterStr);
	}

	protected Parameter insertParameterIntoSignature(final String methodName, final String parameterName,
			final ICompilationUnit icu, final IMethod iMethod, final String parameterStr)
					throws JavaModelException, Throwable {
		final int offset = iMethod.getSourceRange().getOffset() + iMethod.getSourceRange().getLength() - 2;
		final InsertEdit insertEdit = new InsertEdit(offset, parameterStr);
		CompilationUnitManipulatorHelper.editCompilationUnit(icu, insertEdit);
		TestUtil.waitForSynchronization();
		final ConcreteClassifier concreateClassifier = this
				.getJaMoPPClassifierForVURI(VURI.getInstance(icu.getResource()));
		final Method jaMoPPMethod = (Method) concreateClassifier.getMembersByName(methodName).get(0);
		final org.emftext.language.java.parameters.Parameter jaMoPPParam = this
				.getJaMoPPParameterFromJaMoPPMethod(jaMoPPMethod, parameterName);
		return CollectionBridge.claimOne(CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), jaMoPPParam, Parameter.class));
	}

	protected OperationSignature addReturnTypeToSignature(final String interfaceName, final String methodName,
			final String typeName, String oldTypeName) throws Throwable {
		if (null == oldTypeName) {
			oldTypeName = "void";
		}
		final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(interfaceName,
				this.currentTestProject);
		final IMethod iMethod = icu.getType(interfaceName).getMethod(methodName, null);
		final String retTypeStr = typeName;
		final int offset = iMethod.getSourceRange().getOffset()
				+ iMethod.getSourceRange().toString().indexOf(oldTypeName);
		final ReplaceEdit replaceEdit = new ReplaceEdit(offset + 1, oldTypeName.length() + 1, retTypeStr);
		CompilationUnitManipulatorHelper.editCompilationUnit(icu, replaceEdit);
		TestUtil.waitForSynchronization();
		final ConcreteClassifier concreateClassifier = this
				.getJaMoPPClassifierForVURI(VURI.getInstance(icu.getResource()));
		final Method jaMoPPMethod = (Method) concreateClassifier.getMembersByName(methodName).get(0);
		return CollectionBridge.claimOne(CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), jaMoPPMethod, OperationSignature.class));
	}

	protected org.emftext.language.java.parameters.Parameter getJaMoPPParameterFromJaMoPPMethod(
			final Method jaMoPPMethod, final String parameterName) {
		for (final org.emftext.language.java.parameters.Parameter jaMoPPParam : jaMoPPMethod.getParameters()) {
			if (jaMoPPParam.getName().equals(parameterName)) {
				return jaMoPPParam;
			}
		}
		throw new RuntimeException(
				"JaMoPP param with name " + parameterName + " not found in method " + jaMoPPMethod.getName());
	}

	protected org.emftext.language.java.parameters.Parameter findJaMoPPParameterInICU(final ICompilationUnit icu,
			final String interfaceName, final String methodName, final String parameterName) {
		final Method method = this.findJaMoPPMethodInICU(icu, methodName);
		return this.getJaMoPPParameterFromJaMoPPMethod(method, parameterName);
	}

	protected Method findJaMoPPMethodInICU(final ICompilationUnit icu, final String methodName) {
		final ConcreteClassifier cc = this.getJaMoPPClassifierForVURI(VURI.getInstance(icu.getResource()));
		final List<Member> jaMoPPMethods = cc.getMembersByName(methodName);
		for (final Member member : jaMoPPMethods) {
			if (member instanceof Method && member.getName().equals(methodName)) {
				return (Method) member;
			}
		}
		throw new RuntimeException("No method with name " + methodName + " found in " + icu);
	}

	protected String getNameFromPCMPrimitiveDataType(final PrimitiveDataType primitiveDataType) {
		return primitiveDataType.getType().getName();
	}

	protected Package getDatatypesPackage() throws Throwable {
		return this.getPackageWithNameFromCorrespondenceModel("datatypes");
	}

	protected IMethod findIMethodByName(final String typeName, final String methodName, final ICompilationUnit icu)
			throws JavaModelException {
		final IType type = icu.getAllTypes()[0];
		logger.info(type.getMethods());
		for (final IMethod method : type.getMethods()) {
			if (method.getElementName().equals(methodName)) {
				return method;
			}
		}
		throw new RuntimeException("Method not " + methodName + " not found in classifier " + typeName);
	}

	protected IField findIFieldByName(final String className, final String fieldName, final ICompilationUnit icu)
			throws JavaModelException {
		final IType type = icu.getType(className);
		return type.getField(fieldName);
	}

	protected String addPackageAndImplementingClass(final String componentName)
			throws Throwable, CoreException, InterruptedException {
		this.testUserInteractor.addNextSelections(SELECT_BASIC_COMPONENT);
		final Package mediaStorePackage = this.createPackageWithPackageInfo(PCM2JaMoPPTestUtils.REPOSITORY_NAME,
				componentName);
		this.testUserInteractor.addNextSelections(0);

		final String implementingClassName = componentName + "Impl";
		this.addClassInPackage(mediaStorePackage, BasicComponent.class, implementingClassName);
		return implementingClassName;
	}

	protected OperationProvidedRole addImplementsCorrespondingToOperationProvidedRoleToClass(final String className,
			final String implementingInterfaceName) throws Throwable {
		final ICompilationUnit classCompilationUnit = CompilationUnitManipulatorHelper
				.findICompilationUnitWithClassName(className, this.currentTestProject);
		this.importCompilationUnitWithName(implementingInterfaceName, classCompilationUnit);
		final IType classType = classCompilationUnit.getType(className);
		final String newSource = " implements " + implementingInterfaceName;
		int offset = classType.getSourceRange().getOffset();
		final int firstBracket = classType.getSource().indexOf("{");
		offset = offset + firstBracket - 1;
		final InsertEdit insertEdit = new InsertEdit(offset, newSource);
		CompilationUnitManipulatorHelper.editCompilationUnit(classCompilationUnit, insertEdit);
		TestUtil.waitForSynchronization(3 * TestUtil.WAITING_TIME_FOR_SYNCHRONIZATION);
		final org.emftext.language.java.classifiers.Class jaMoPPClass = (org.emftext.language.java.classifiers.Class) this
				.getJaMoPPClassifierForVURI(VURI.getInstance(classCompilationUnit.getResource()));
		final EList<TypeReference> classImplements = jaMoPPClass.getImplements();
		for (final TypeReference implementsReference : classImplements) {
			final Set<OperationProvidedRole> correspondingEObjects = CorrespondenceModelUtil
					.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), implementsReference,
							OperationProvidedRole.class);
			if (null != correspondingEObjects && 0 < correspondingEObjects.size()) {
				return correspondingEObjects.iterator().next();
			}
		}
		throw new RuntimeException("Could not find a operation provided role for the newly created implmenets");
	}

	protected void importCompilationUnitWithName(final String implementingInterfaceName,
			final ICompilationUnit classCompilationUnit) throws Throwable, JavaModelException {
		final ICompilationUnit interfaceCompilationUnit = CompilationUnitManipulatorHelper
				.findICompilationUnitWithClassName(implementingInterfaceName, this.currentTestProject);
		final String namespace = interfaceCompilationUnit.getType(implementingInterfaceName).getFullyQualifiedName();
		classCompilationUnit.createImport(namespace, null, null);
	}

	protected <T> T addFieldToClassWithName(final String className, final String fieldType, final String fieldName,
			final Class<T> correspondingType) throws Throwable {
		final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
				this.currentTestProject);
		if (!fieldType.equals("String")) {
			this.importCompilationUnitWithName(fieldType, icu);
		}
		final IType iClass = icu.getAllTypes()[0];
		final int offset = CompilationUnitManipulatorHelper.getOffsetForClassifierManipulation(iClass);
		final String fieldStr = "private " + fieldType + " " + fieldName + ";";
		final InsertEdit insertEdit = new InsertEdit(offset, fieldStr);
		CompilationUnitManipulatorHelper.editCompilationUnit(icu, insertEdit);
		TestUtil.waitForSynchronization();
		final Field jaMoPPField = this.getJaMoPPFieldFromClass(icu, fieldName);
		if (correspondingType == null) {
			return null;
		}
		return CollectionBridge.claimOne(CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), jaMoPPField, correspondingType));
	}

	protected Field getJaMoPPFieldFromClass(final ICompilationUnit icu, final String fieldName) {
		final ConcreteClassifier cc = this.getJaMoPPClassifierForVURI(VURI.getInstance(icu.getResource()));
		final Field field = (Field) cc.getMembersByName(fieldName).get(0);
		return field;
	}

	/**
	 * Create change for annotation manually and notify change synchonizer
	 *
	 * @param annotableAndModifiable
	 * @throws Throwable
	 */
	protected void addAnnotationToMember(final AnnotableAndModifiable annotableAndModifiable,
			final String annotationName) throws Throwable {
		final JavaInsertEReference<EObject, EObject> createChange = ReferenceFactory.eINSTANCE
				.createJavaInsertEReference();
		createChange.setIsCreate(true);
		createChange.setOldAffectedEObject(annotableAndModifiable);
		final AnnotationInstance newAnnotation = AnnotationsFactory.eINSTANCE.createAnnotationInstance();
		final Classifier classifier = this.createClassifierFromName(annotationName);
		newAnnotation.setAnnotation(classifier);
		annotableAndModifiable.getAnnotationsAndModifiers().add(newAnnotation);
		createChange.setAffectedEObject(EcoreUtil.copy(annotableAndModifiable));
		final EReference containingReference = (EReference) newAnnotation.eContainingFeature();
		@SuppressWarnings("unchecked")
		final int index = ((EList<EObject>) createChange.getAffectedEObject().eGet(containingReference))
				.indexOf(newAnnotation);
		createChange.setAffectedFeature(containingReference);
		createChange.setIndex(index);
		createChange.setNewValue(newAnnotation);
		final ChangeSynchronizing cs = this.getChangeSynchronizing();
		final VURI vuri = VURI.getInstance(annotableAndModifiable.eResource());
		final GeneralChange change = VitruviusChangeFactory.getInstance()
				.createGeneralChange(Collections.singletonList(createChange), vuri);
		cs.synchronizeChange(change);
	}

	// add Annotation via the framework
	protected <T> T addAnnotationToClassifier(final AnnotableAndModifiable annotable, final String annotationName,
			final Class<T> classOfCorrespondingObject, final String className) throws Throwable {
		final ICompilationUnit cu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
				this.currentTestProject);
		final IType type = cu.getType(className);
		final int offset = CompilationUnitManipulatorHelper.getOffsetForAddingAnntationToClass(type);
		final InsertEdit insertEdit = new InsertEdit(offset, "@" + annotationName);
		CompilationUnitManipulatorHelper.editCompilationUnit(cu, insertEdit);
		TestUtil.waitForSynchronization();
		final Set<T> eObjectsByType = CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), annotable, classOfCorrespondingObject);
		return CollectionBridge.claimOne(eObjectsByType);
	}

	// add Annotation via the framework
	protected <T> T addAnnotationToField(final String fieldName, final String annotationName,
			final Class<T> classOfCorrespondingObject, final String className) throws Throwable {
		final ICompilationUnit cu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
				this.currentTestProject);
		final IType type = cu.getType(className);
		final int offset = CompilationUnitManipulatorHelper.getOffsetForAddingAnntationToField(type, fieldName);
		final InsertEdit insertEdit = new InsertEdit(offset, "@" + annotationName + " ");
		CompilationUnitManipulatorHelper.editCompilationUnit(cu, insertEdit);
		TestUtil.waitForSynchronization();
		final Field jaMoPPField = this.getJaMoPPFieldFromClass(cu, fieldName);
		final Set<T> eObjectsByType = CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), jaMoPPField, classOfCorrespondingObject);
		return CollectionBridge.claimOne(eObjectsByType);
	}

	private ChangeSynchronizing getChangeSynchronizing() throws Throwable {
		final PCMJavaBuilder pcmJavaBuilder = this.getPCMJavaBuilderFromProject();
		final ChangeSynchronizing changeSynchronizing = JavaBridge.getFieldFromClass(VitruviusEmfBuilder.class,
				"changeSynchronizing", pcmJavaBuilder);
		return changeSynchronizing;
	}

	private Classifier createClassifierFromName(final String annotationName) {
		final org.emftext.language.java.classifiers.Class jaMoPPClass = ClassifiersFactory.eINSTANCE.createClass();
		jaMoPPClass.setName(annotationName);
		return jaMoPPClass;
	}

	private void deactivateEMFMonitor() throws Throwable {
		final PCMJavaBuilder pcmJavaBuilder = this.getPCMJavaBuilderFromProject();
		final ChangeSynchronizing dummyChangeSynchronizing = new ChangeSynchronizing() {

			@Override
			public List<List<VitruviusChange>> synchronizeChange(VitruviusChange change) {
				final StringBuilder changeMessage = new StringBuilder();
				change.getEChanges().forEach(eChange -> changeMessage.append(eChange).append(", "));
				logger.info("Detected (but ignored) changes: " + changeMessage);
				return null;
			}

		};
		JavaBridge.setFieldInClass(VitruviusEmfBuilder.class, "changeSynchronizing", pcmJavaBuilder,
				dummyChangeSynchronizing);

	}

	protected void assertCorrespondingSEFF(final ResourceDemandingSEFF correspondingSeff, String methodName)
			throws Throwable {
		final ClassMethod jaMoPPMethod = CollectionBridge.claimOne(CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), correspondingSeff, ClassMethod.class));
		assertEquals(jaMoPPMethod.getName(), methodName);
	}

	protected void assertOperationInterface(final Repository repo, final OperationInterface opIf,
			final String expectedName) {
		assertTrue("The created operation interface is null", null != opIf);
		assertEquals("OperationInterface name does not equals the expected interface Name.", opIf.getEntityName(),
				expectedName);
		assertEquals("The created operation interface is not in the repository", repo.getId(),
				opIf.getRepository__Interface().getId());
	}

}
