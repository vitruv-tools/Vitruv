package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
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
import org.eclipse.text.edits.TextEdit;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;

import de.uka.ipd.sdq.pcm.core.entity.NamedElement;
import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.CollectionDataType;
import de.uka.ipd.sdq.pcm.repository.CompositeComponent;
import de.uka.ipd.sdq.pcm.repository.CompositeDataType;
import de.uka.ipd.sdq.pcm.repository.DataType;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationSignature;
import de.uka.ipd.sdq.pcm.repository.Parameter;
import de.uka.ipd.sdq.pcm.repository.PrimitiveDataType;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryComponent;
import de.uka.ipd.sdq.pcm.seff.ResourceDemandingSEFF;
import de.uka.ipd.sdq.pcm.system.System;
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.VitruviusEmfBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.util.BuildProjects;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaAddBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaRemoveBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPTransformationExecuterBase;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ClassMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.TestUserInteractor;
import edu.kit.ipd.sdq.vitruvius.tests.VitruviusCasestudyTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

/**
 * Test class that contains utillity methods that can be used by JaMoPP2PCM transformation tests
 *
 */
@SuppressWarnings("restriction")
public class JaMoPP2PCMTransformationTest extends VitruviusCasestudyTest {

    private static final Logger logger = Logger.getLogger(JaMoPP2PCMTransformationTest.class.getSimpleName());

    protected static final int SELECT_BASIC_COMPONENT = 0;
    private static final int SELECT_COMPOSITE_COMPONENT = 1;
    private static final int SELECT_SYSTEM = 2;
    private static final int SELECT_NOTHING_DECIDE_LATER = 3;

    protected Package mainPackage;
    protected Package secondPackage;

    @Override
    protected void beforeTest() throws Throwable {
        // remove PCM java builder from Project
        this.afterTest();
        this.testUserInteractor = new TestUserInteractor();
        // add PCM Java Builder to Project under test
        final PCMJavaAddBuilder pcmJavaBuilder = new PCMJavaAddBuilder();
        pcmJavaBuilder.addBuilderToProject(TestUtil.getTestProject());
        // build the project
        BuildProjects.issueIncrementalBuildForAllProjectsWithBuilder(PCMJaMoPPNamespace.BUILDER_ID);
        this.resourceSet = new ResourceSetImpl();
        // set new user interactor
        this.setUserInteractor(this.testUserInteractor);
    }

    @Override
    protected void afterTest() {
        // Remove PCM Java Builder
        final PCMJavaRemoveBuilder pcmJavaRemoveBuilder = new PCMJavaRemoveBuilder();
        pcmJavaRemoveBuilder.removeBuilderFromProject(TestUtil.getTestProject());
    }

    @Override
    protected CorrespondenceInstance getCorrespondenceInstance() throws Throwable {
        final PCMJavaBuilder pcmJavaBuilder = this.getPCMJavaBuilderFromProject();
        if (null == pcmJavaBuilder) {
            return null;
        }
        final VSUMImpl vsum = TestUtil.getFieldFromClass(VitruviusEmfBuilder.class, "vsum", pcmJavaBuilder);
        final VURI jaMoPPVURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
        final VURI pcmVURI = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
        final CorrespondenceInstance corresponcenceInstance = vsum.getCorrespondenceInstanceOriginal(pcmVURI,
                jaMoPPVURI);
        return corresponcenceInstance;
    }

    private PCMJavaBuilder getPCMJavaBuilderFromProject() throws Throwable {
        final Project project = (Project) TestUtil.getTestProject();
        final ResourceInfo info = project.getResourceInfo(false, false);
        final ProjectDescription description = ((ProjectInfo) info).getDescription();
        final boolean makeCopy = false;
        for (final ICommand command : description.getBuildSpec(makeCopy)) {
            if (command.getBuilderName().equals(PCMJaMoPPNamespace.BUILDER_ID)) {
                final BuildCommand buildCommand = (BuildCommand) command;
                final IncrementalProjectBuilder ipb = buildCommand.getBuilder(TestUtil.getTestProject()
                        .getActiveBuildConfig());
                final PCMJavaBuilder pcmJavaBuilder = (PCMJavaBuilder) ipb;
                return pcmJavaBuilder;
            }
        }
        logger.warn("Could not find any PCMJavaBuilder");
        return null;
    }

    protected Repository addFirstPackage() throws Throwable {
        this.mainPackage = this.createPackageWithPackageInfo(new String[] { PCM2JaMoPPTestUtils.REPOSITORY_NAME });
        final CorrespondenceInstance ci = this.getCorrespondenceInstance();
        if (null == ci) {
            throw new RuntimeException("Could not get correspondence instance.");
        }
        final Repository repo = ci.claimUniqueCorrespondingEObjectByType(this.mainPackage, Repository.class);
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
        return this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(this.secondPackage,
                correspondingType);
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

    protected void renamePackage(final Package packageToRename, String newName) throws Throwable {
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
            final ICompilationUnit cu = this.findICompilationUnitWithClassName(entityName + ".java");
            final int offset = cu.getBuffer().getContents().indexOf(entityName);
            if (cu.getBuffer() instanceof IBuffer.ITextEditCapability) {
                logger.info(cu.getBuffer());
            }
            final ReplaceEdit edit = new ReplaceEdit(offset, entityName.length(), newName);
            this.editCompilationUnit(cu, edit);
            TestUtil.waitForSynchronization();
            final VURI vuri = VURI.getInstance(cu.getResource());
            final Classifier jaMoPPClass = this.getJaMoPPClassifierForVURI(vuri);
            return this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(jaMoPPClass, type);
        } catch (final Throwable e) {
            logger.warn(e.getMessage());
        }
        return null;

    }

    protected void editCompilationUnit(final ICompilationUnit cu, final TextEdit... edits) throws JavaModelException {
        cu.becomeWorkingCopy(new NullProgressMonitor());
        for (final TextEdit edit : edits) {
            cu.applyTextEdit(edit, null);
        }
        cu.save(new NullProgressMonitor(), true);
        cu.commitWorkingCopy(true, new NullProgressMonitor());
        cu.save(new NullProgressMonitor(), true);
    }

    protected ICompilationUnit findICompilationUnitWithClassName(String entityName) throws Throwable {
        if (!entityName.endsWith("." + PCMJaMoPPNamespace.JaMoPP.JAVA_FILE_EXTENSION)) {
            entityName = entityName + "." + PCMJaMoPPNamespace.JaMoPP.JAVA_FILE_EXTENSION;
        }
        final IJavaProject javaProject = JavaCore.create(TestUtil.getTestProject());
        for (final IPackageFragmentRoot packageFragmentRoot : javaProject.getPackageFragmentRoots()) {
            final IJavaElement[] children = packageFragmentRoot.getChildren();
            for (final IJavaElement iJavaElement : children) {
                if (iJavaElement instanceof IPackageFragment) {
                    final IPackageFragment fragment = (IPackageFragment) iJavaElement;
                    final IJavaElement[] javaElements = fragment.getChildren();
                    for (int k = 0; k < javaElements.length; k++) {
                        final IJavaElement javaElement = javaElements[k];
                        if (javaElement.getElementType() == IJavaElement.COMPILATION_UNIT) {
                            final ICompilationUnit compilationUnit = (ICompilationUnit) javaElement;
                            if (compilationUnit.getElementName().equals(entityName)) {
                                return compilationUnit;
                            }
                        }
                    }
                }
            }
        }
        throw new RuntimeException("Could not find a compilation unit with name " + entityName);
    }

    private IPackageFragmentRoot getIJavaProject() throws Throwable {
        final IProject project = TestUtil.getTestProject();
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
        final String vuriKey = super.getProjectPath() + TestUtil.SOURCE_FOLDER + "/" + srcFilePath;
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
        final IPackageFragmentRoot packageRoot = this.getIJavaProject();
        final IPackageFragment packageFragment = this.getPackageFragmentToForJaMoPPPackage(packageForClass);
        final NewClassWizardPage classWizard = new NewClassWizardPage();
        classWizard.setPackageFragment(packageFragment, false);
        classWizard.setPackageFragmentRoot(packageRoot, false);
        classWizard.setTypeName(implementingClassName, true);
        classWizard.createType(new NullProgressMonitor());

        final VURI vuri = this.getVURIForElementInPackage(packageFragment, PCM2JaMoPPTestUtils.IMPLEMENTING_CLASS_NAME);
        final Classifier jaMoPPClass = this.getJaMoPPClassifierForVURI(vuri);
        TestUtil.waitForSynchronization(5 * 1000);
        return this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(jaMoPPClass,
                classOfCorrespondingObject);
    }

    private VURI getVURIForElementInPackage(final IPackageFragment packageFragment, final String elementName) {
        String vuriKey = packageFragment.getResource().getFullPath().toString() + "/" + elementName + "."
                + PCMJaMoPPNamespace.JaMoPP.JAVA_FILE_EXTENSION;
        if (vuriKey.startsWith("/")) {
            vuriKey = vuriKey.substring(1, vuriKey.length());
        }
        final VURI vuri = VURI.getInstance(vuriKey);
        return vuri;
    }

    private IPackageFragment getPackageFragmentToForJaMoPPPackage(final Package packageForClass) throws Throwable {
        final IPackageFragmentRoot packageRoot = this.getIJavaProject();
        for (final IJavaElement javaElement : packageRoot.getChildren()) {
            if (javaElement instanceof IPackageFragment
                    && javaElement.getElementName().equals(
                            packageForClass.getNamespacesAsString() + packageForClass.getName())) {
                return (IPackageFragment) javaElement;
            }
        }
        throw new RuntimeException("No packageFragment found for JaMoPP package " + packageForClass);
    }

    protected ConcreteClassifier getJaMoPPClassifierForVURI(final VURI vuri) {
        final Resource resource = EcoreResourceBridge.loadResourceAtURI(vuri.getEMFUri(), new ResourceSetImpl());
        final CompilationUnit cu = (CompilationUnit) resource.getContents().get(0);
        final Classifier jaMoPPClassifier = cu.getClassifiers().get(0);
        return (ConcreteClassifier) jaMoPPClassifier;
    }

    /**
     * Change user interactor in changeSynchronizer of PCMJaMoPPTransformationExecuter by invoking
     * the setUserInteractor method of the class ChangeSynchronizer
     *
     * @throws Throwable
     */
    private void setUserInteractor(final UserInteracting newUserInteracting) throws Throwable {
        final PCMJavaBuilder pcmJavaBuilder = this.getPCMJavaBuilderFromProject();
        final SyncManagerImpl syncManagerImpl = TestUtil.getFieldFromClass(VitruviusEmfBuilder.class,
                "changeSynchronizing", pcmJavaBuilder);
        this.setUserInteractor(newUserInteracting, syncManagerImpl);
    }

    protected CompositeComponent addSecondPackageCorrespondsToCompositeComponent() throws Throwable {
        this.testUserInteractor.addNextSelections(SELECT_COMPOSITE_COMPONENT);
        return this.createSecondPackage(CompositeComponent.class, PCM2JaMoPPTestUtils.REPOSITORY_NAME,
                PCM2JaMoPPTestUtils.COMPOSITE_COMPONENT_NAME);
    }

    protected de.uka.ipd.sdq.pcm.system.System addSecondPackageCorrespondsToSystem() throws Throwable {
        this.testUserInteractor.addNextSelections(SELECT_SYSTEM);
        return this.createSecondPackage(System.class, PCM2JaMoPPTestUtils.SYSTEM_NAME);
    }

    protected void addSecondPackageCorrespondsWithoutCorrespondences() throws Throwable {
        this.testUserInteractor.addNextSelections(SELECT_NOTHING_DECIDE_LATER);
        this.createSecondPackageWithoutCorrespondence(PCM2JaMoPPTestUtils.REPOSITORY_NAME,
                PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME);
    }

    protected void assertRepositoryAndPCMName(final Repository repo, final RepositoryComponent repoComponent,
            final String expectedName) {

        assertEquals("Repository of compoennt is not the repository: " + repo, repo.getId(), repoComponent
                .getRepository__RepositoryComponent().getId());

        this.assertPCMNamedElement(repoComponent, expectedName);
    }

    protected void assertRepositoryAndPCMNameForDatatype(final Repository repo, final DataType dt,
            final String expectedName) {

        assertEquals("Repository of compoennt is not the repository: " + repo, repo.getId(), dt
                .getRepository__DataType().getId());
        if (dt instanceof CompositeDataType) {
            this.assertPCMNamedElement((CompositeDataType) dt, expectedName);
        } else if (dt instanceof CollectionDataType) {
            this.assertPCMNamedElement((CollectionDataType) dt, expectedName);
        } else {
            throw new RuntimeException("Primitive data types should not have a correspondence to classes");
        }
    }

    protected void assertPCMNamedElement(final NamedElement pcmNamedElement, final String expectedName) {
        assertEquals("The name of pcm named element is not " + expectedName, expectedName,
                pcmNamedElement.getEntityName());
    }

    protected OperationInterface addInterfaceInContractsPackage() throws Throwable {
        return this.createInterfaceInPackage("contracts", true);
    }

    private OperationInterface createInterfaceInPackage(final String packageName, final boolean throwException)
            throws Throwable, CoreException, InterruptedException {
        final String interfaceName = PCM2JaMoPPTestUtils.INTERFACE_NAME;
        return this.createInterfaceInPackage(packageName, throwException, interfaceName);
    }

    protected OperationInterface createInterfaceInPackage(final String packageName, final boolean throwException,
            final String interfaceName) throws Throwable, CoreException, InterruptedException {
        final IPackageFragment packageFragment = this.getPackageFragmentToForJaMoPPPackage(this
                .getPackageWithName(packageName));
        final NewInterfaceWizardPage interfaceWizard = new NewInterfaceWizardPage();
        interfaceWizard.setPackageFragment(packageFragment, false);
        interfaceWizard.setPackageFragmentRoot(this.getIJavaProject(), false);
        interfaceWizard.setTypeName(interfaceName, true);
        interfaceWizard.createType(new NullProgressMonitor());
        final VURI vuri = this.getVURIForElementInPackage(packageFragment, interfaceName);
        TestUtil.waitForSynchronization();
        final Classifier jaMoPPIf = this.getJaMoPPClassifierForVURI(vuri);
        if (throwException) {
            return this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(jaMoPPIf,
                    OperationInterface.class);
        } else {
            final Set<EObject> correspondingEObjects = this.getCorrespondenceInstance().getAllCorrespondingEObjects(
                    jaMoPPIf);
            if (null == correspondingEObjects || 0 == correspondingEObjects.size()) {
                return null;
            } else {
                return (OperationInterface) correspondingEObjects.iterator().next();
            }
        }
    }

    protected OperationInterface addInterfaceInSecondPackageWithCorrespondence(final String packageName)
            throws Throwable {
        this.testUserInteractor.addNextSelections(0);
        return this.createInterfaceInPackage(packageName, true);
    }

    protected EObject addInterfaceInSecondPackageWithoutCorrespondence(final String packageName) throws Throwable {
        this.testUserInteractor.addNextSelections(1);
        return this.createInterfaceInPackage(packageName, false);
    }

    protected Package getPackageWithName(final String name) throws Throwable {
        final Set<Package> packages = this.getCorrespondenceInstance().getAllEObjectsInCorrespondencesWithType(
                Package.class);
        for (final Package currentPackage : packages) {
            if (currentPackage.getName().equals(name)) {
                return currentPackage;
            }
        }
        throw new RuntimeException("Could not find datatypes package");
    }

    protected OperationSignature addMethodToInterfaceWithCorrespondence(final String interfaceName) throws Throwable {
        final String methodName = PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME;
        return this.addMethodToInterfaceWithCorrespondence(interfaceName, methodName);
    }

    protected OperationSignature addMethodToInterfaceWithCorrespondence(final String interfaceName,
            final String methodName) throws Throwable, JavaModelException {
        final String methodString = "void " + methodName + "();";
        final ICompilationUnit cu = this.addMethodToCompilationUnit(interfaceName, methodString);
        return this.findOperationSignatureForJaMoPPMethodInCompilationUnit(methodName, interfaceName, cu);
    }

    protected ICompilationUnit addMethodToCompilationUnit(final String compilationUnitName, final String methodString)
            throws Throwable, JavaModelException {
        final ICompilationUnit cu = this.findICompilationUnitWithClassName(compilationUnitName);
        final IType firstType = cu.getAllTypes()[0];
        final int offset = this.getOffsetForClassifierManipulation(firstType);
        final InsertEdit insertEdit = new InsertEdit(offset, methodString);
        this.editCompilationUnit(cu, insertEdit);
        TestUtil.waitForSynchronization();
        return cu;
    }

    protected int getOffsetForClassifierManipulation(final IType firstType) throws JavaModelException {
        return firstType.getNameRange().getOffset() + firstType.getNameRange().getLength() + 3;
    }

    protected OperationSignature findOperationSignatureForJaMoPPMethodInCompilationUnit(final String methodName,
            final String interfaceName, final ICompilationUnit cu) throws Throwable {
        final VURI vuri = VURI.getInstance(cu.getResource());
        final Classifier classifier = this.getJaMoPPClassifierForVURI(vuri);
        final Interface jaMoPPInterface = (Interface) classifier;
        for (final Method jaMoPPMethod : jaMoPPInterface.getMethods()) {
            if (jaMoPPMethod.getName().equals(methodName)) {
                return this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(jaMoPPMethod,
                        OperationSignature.class);
            }
        }
        logger.warn("No JaMoPP method with name " + methodName + " found in " + interfaceName);
        return null;
    }

    protected OperationSignature renameMethodInClassWithName(final String className, final String methodName)
            throws Throwable {
        final ICompilationUnit cu = this.findICompilationUnitWithClassName(className);
        final IMethod iMethod = cu.getType(className).getMethod(methodName, null);
        final int offset = iMethod.getNameRange().getOffset();
        final int length = iMethod.getNameRange().getLength();
        final String newMethodName = methodName + PCM2JaMoPPTestUtils.RENAME;
        final ReplaceEdit replaceEdit = new ReplaceEdit(offset, length, newMethodName);
        this.editCompilationUnit(cu, replaceEdit);
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
            final String typeName, final String parameterName) throws Throwable {
        final ICompilationUnit icu = this.findICompilationUnitWithClassName(interfaceName);
        final IMethod iMethod = icu.getType(interfaceName).getMethod(methodName, null);
        final String parameterStr = typeName + " " + parameterName;
        final int offset = iMethod.getSourceRange().getOffset() + iMethod.getSourceRange().getLength() - 2;
        final InsertEdit insertEdit = new InsertEdit(offset, parameterStr);
        this.editCompilationUnit(icu, insertEdit);
        TestUtil.waitForSynchronization();
        final ConcreteClassifier concreateClassifier = this.getJaMoPPClassifierForVURI(VURI.getInstance(icu
                .getResource()));
        final Method jaMoPPMethod = (Method) concreateClassifier.getMembersByName(methodName).get(0);
        final org.emftext.language.java.parameters.Parameter jaMoPPParam = this.getJaMoPPParameterFromJaMoPPMethod(
                jaMoPPMethod, parameterName);
        return this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(jaMoPPParam, Parameter.class);

    }

    protected org.emftext.language.java.parameters.Parameter getJaMoPPParameterFromJaMoPPMethod(
            final Method jaMoPPMethod, final String parameterName) {
        for (final org.emftext.language.java.parameters.Parameter jaMoPPParam : jaMoPPMethod.getParameters()) {
            if (jaMoPPParam.getName().equals(parameterName)) {
                return jaMoPPParam;
            }
        }
        throw new RuntimeException("JaMoPP param with name " + parameterName + " not found in method "
                + jaMoPPMethod.getName());
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
        return this.getPackageWithName("datatypes");
    }

    protected CompositeDataType addClassThatCorrespondsToCompositeDatatype() throws Throwable {
        this.testUserInteractor.addNextSelections(ClassMappingTransformation.SELECT_CREATE_COMPOSITE_DATA_TYPE);
        final CompositeDataType cdt = this.addClassInPackage(this.getDatatypesPackage(), CompositeDataType.class);
        return cdt;
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

    @Override
    protected java.lang.Class<?> getEMFModelTransformationExecuterClass() {
        return PCMJaMoPPTransformationExecuterBase.class;
    }

    protected ResourceDemandingSEFF addImplementingClassMethodToClass(final String className,
            final String methodNameOfMethodToAdd) throws Throwable {
        final ICompilationUnit iCu = this.addMethodToCompilationUnit(className, methodNameOfMethodToAdd);
        final Method jaMoPPMethodInICU = this.findJaMoPPMethodInICU(iCu, methodNameOfMethodToAdd);
        final ResourceDemandingSEFF rdSeff = this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(
                jaMoPPMethodInICU, ResourceDemandingSEFF.class);
        return rdSeff;
    }

    protected void addPackageAndImplementingClass(final String componentName) throws Throwable, CoreException, InterruptedException {
        this.testUserInteractor.addNextSelections(SELECT_BASIC_COMPONENT);
        final Package mediaStorePackage = this.createPackageWithPackageInfo(PCM2JaMoPPTestUtils.REPOSITORY_NAME,
                componentName);
        this.testUserInteractor.addNextSelections(0);
        this.addClassInPackage(mediaStorePackage, BasicComponent.class, componentName + "Impl");
    }

}
