package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;

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
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.junit.Before;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.VitruviusEmfBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.util.BuildProjects;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaAddBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaRemoveBuilder;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.PCMJaMoPPTransformationTestBase;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

/**
 * Test class that contains utillity methods that can be used by JaMoPP2PCM transformation tests
 *
 */
public class JaMoPP2PCMTransformationTest extends PCMJaMoPPTransformationTestBase {

    private static final Logger logger = Logger.getLogger(JaMoPP2PCMTransformationTest.class.getSimpleName());

    protected Package mainPackage;
    protected Package secondPackage;

    @Before
    public void setUpTest() {
        // add PCM Java Builder to Project under test
        final PCMJavaAddBuilder pcmJavaBuilder = new PCMJavaAddBuilder();
        pcmJavaBuilder.addBuilderToProject(TestUtil.getTestProject());
        // build the project
        BuildProjects.issueIncrementalBuildForAllProjectsWithBuilder(PCMJaMoPPNamespace.BUILDER_ID);
        this.resourceSet = new ResourceSetImpl();
    }

    @Override
    protected void afterTest() {
        // Remove PCM Java Builder
        final PCMJavaRemoveBuilder pcmJavaRemoveBuilder = new PCMJavaRemoveBuilder();
        pcmJavaRemoveBuilder.removeBuilderFromProject(TestUtil.getTestProject());
    }

    @SuppressWarnings("restriction")
    @Override
    protected CorrespondenceInstance getCorrespondenceInstance() throws Throwable {
        final Project project = (Project) TestUtil.getTestProject();
        final ResourceInfo info = project.getResourceInfo(false, false);
        final ProjectDescription description = ((ProjectInfo) info).getDescription();
        final boolean makeCopy = false;
        for (final ICommand command : description.getBuildSpec(makeCopy)) {
            if (command.getBuilderName().equals(PCMJaMoPPNamespace.BUILDER_ID)) {
                final BuildCommand buildCommand = (BuildCommand) command;
                final IncrementalProjectBuilder ipb = buildCommand.getBuilder(TestUtil.getTestProject()
                        .getActiveBuildConfig());
                final VitruviusEmfBuilder vitruviusEmfBuilder = (VitruviusEmfBuilder) ipb;
                final VSUMImpl vsum = TestUtil
                        .getFieldFromClass(VitruviusEmfBuilder.class, "vsum", vitruviusEmfBuilder);
                final VURI jaMoPPVURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
                final VURI pcmVURI = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
                final CorrespondenceInstance corresponcenceInstance = vsum.getCorrespondenceInstanceOriginal(pcmVURI,
                        jaMoPPVURI);
                return corresponcenceInstance;
            }
        }
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

    protected BasicComponent addSecondPackage() throws Throwable {
        this.secondPackage = this.createPackageWithPackageInfo(new String[] { PCM2JaMoPPTestUtils.REPOSITORY_NAME,
                PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME });
        return this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(this.secondPackage,
                BasicComponent.class);
    }

    protected void createPackage(final String[] namespace) throws Throwable {
        final IPackageFragmentRoot packageRoot = this.getIJavaProject();
        final String namespaceDotted = StringUtils.join(namespace, ".");
        final boolean force = true;
        packageRoot.createPackageFragment(namespaceDotted, force, new NullProgressMonitor());
        Thread.sleep(TestUtil.WAITING_TIME_FOR_SYNCHRONIZATION);
    }

    protected Package createPackageWithPackageInfo(final String[] namespace) throws Throwable {
        final String namespaceDotted = StringUtils.join(namespace, ".");
        String packageFile = StringUtils.join(namespace, "/");
        packageFile = packageFile + "/package-info.java";
        final Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
        jaMoPPPackage.setName(namespaceDotted);
        final VURI packageVURI = this.createVURIForSrcFile(packageFile);
        final Resource resource = this.resourceSet.createResource(packageVURI.getEMFUri());
        EcoreResourceBridge.saveEObjectAsOnlyContent(jaMoPPPackage, resource);
        logger.info("Namespace of new package: " + jaMoPPPackage.getNamespacesAsString());
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
        final RefactoringContribution refacContrib = RefactoringCore
                .getRefactoringContribution(IJavaRefactorings.RENAME_PACKAGE);
        final RefactoringStatus status = new RefactoringStatus();
        final RenameJavaElementDescriptor desc = (RenameJavaElementDescriptor) refacContrib.createDescriptor();
        desc.setUpdateReferences(true);
        desc.setJavaElement(javaPackage);
        desc.setNewName(newName);
        final NullProgressMonitor monitor = new NullProgressMonitor();
        final Refactoring refactoring = desc.createRefactoring(status);
        refactoring.checkInitialConditions(monitor);
        refactoring.checkFinalConditions(monitor);
        final Change change = refactoring.createChange(monitor);
        change.perform(monitor);
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

}
