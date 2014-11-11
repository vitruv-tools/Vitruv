//package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.Set;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.eclipse.emf.ecore.EAttribute;
//import org.eclipse.emf.ecore.EObject;
//import org.eclipse.emf.ecore.EReference;
//import org.eclipse.emf.ecore.resource.Resource;
//import org.eclipse.emf.ecore.util.EcoreUtil;
//import org.emftext.language.java.classifiers.Class;
//import org.emftext.language.java.classifiers.ClassifiersFactory;
//import org.emftext.language.java.classifiers.Interface;
//import org.emftext.language.java.commons.CommonsPackage;
//import org.emftext.language.java.commons.NamedElement;
//import org.emftext.language.java.containers.CompilationUnit;
//import org.emftext.language.java.containers.ContainersFactory;
//import org.emftext.language.java.containers.ContainersPackage;
//import org.emftext.language.java.containers.Package;
//import org.emftext.language.java.modifiers.ModifiersFactory;
//import org.junit.Test;
//
//import de.uka.ipd.sdq.pcm.repository.BasicComponent;
//import de.uka.ipd.sdq.pcm.repository.OperationInterface;
//import de.uka.ipd.sdq.pcm.repository.Repository;
//import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPP2CMUtils;
//import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
//import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
//import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory;
//import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
//import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
//import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
//import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
//import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject;
//import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectFactory;
//import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
//import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.PCMJaMoPPTransformationTestBase;
//import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPUtils;
//
///**
// * Test for JaMoPP 2 PCM Curent Test cases: i) C Package a) package that corresponds to repository
// * b) package that corresponds to a basic component ii) C Class iii) C Interface
// *
// * @author Langhamm
// *
// */
//public class JaMoPP2PCMTest extends PCMJaMoPPTransformationTestBase {
//
//    private static final Logger logger = Logger.getLogger(JaMoPP2PCMTest.class.getSimpleName());
//
//    private Package mainPackage;
//    private Package secondPackage;
//
//    /**
//     * Test i) a) --> first package is created --> should be mapped to a repository
//     *
//     * @throws Exception
//     */
//    @Test
//    public void testAddFirstPackage() throws Exception {
//        final Repository repo = this.addFirstPackage();
//        assertEquals("Name of the repository is not the same as the name of the package",
//                PCM2JaMoPPUtils.REPOSITORY_NAME, repo.getEntityName());
//    }
//
//    /**
//     * Test i) b) --> second packages is added --> should be mapped to a basic component
//     *
//     * @throws Exception
//     */
//    @Test
//    public void testAddSecondPackage() throws Exception {
//        final Repository repo = this.addFirstPackage();
//        final BasicComponent bc = this.addSecondPackage();
//        // repository of basic component has to be the repository
//        assertTrue("Repository of basic compoennt is not the repository: " + repo,
//                repo.equals(bc.getRepository__RepositoryComponent()));
//        assertTrue("The name of the basic component is not " + PCM2JaMoPPUtils.BASIC_COMPONENT_NAME, bc.getEntityName()
//                .equalsIgnoreCase(PCM2JaMoPPUtils.BASIC_COMPONENT_NAME));
//    }
//
//    /**
//     * Test ii) class that in mapped package and same name as component + impl--> should be the new
//     * implementing class for the component
//     *
//     * @throws Exception
//     */
//    @Test
//    public void testAddClassInPackageWithCorrespondingComponent() throws Exception {
//        this.addFirstPackage();
//        final BasicComponent bc = this.addSecondPackage();
//
//        final BasicComponent bcForClass = this.addClassInSecondPackage();
//
//        assertTrue("BasicComponent for package is not basic component for class", bc.equals(bcForClass));
//    }
//
//    /**
//     * Test ii) class in non corresponding package --> should not be mapped to a Basic Component
//     *
//     * @throws Exception
//     */
//    @Test
//    public void testAddClassInPackageWithoutCorrespondingComponent() throws Exception {
//        this.addFirstPackage();
//        this.addSecondPackage();
//
//        final Package packageWithoutCorrespondence = ContainersFactory.eINSTANCE.createPackage();
//        packageWithoutCorrespondence.setName("packageWithoutCorrespondence");
//        packageWithoutCorrespondence.getNamespaces().add("packageWithoutCorrespondence");
//        final EObject eObject = this.addClassInPackage(packageWithoutCorrespondence);
//
//        assertTrue(
//                "The corresponding object for the class created in a package that does not correspond to a component should be null",
//                null == eObject);
//
//    }
//
//    /**
//     * Test iii) interface in repository package --> should be mapped to operation interface
//     *
//     * @throws Exception
//     */
//    @Test
//    public void testAddInterfaceInRepositoryPackage() throws Exception {
//        final Repository repo = this.addFirstPackage();
//        final OperationInterface opIf = this.addInterfaceInMainPackage();
//
//        assertTrue("The created operation interface is null", null != opIf);
//        assertEquals("OperationInterface name does not equals the expected interface Name.", opIf.getEntityName(),
//                PCM2JaMoPPUtils.INTERFACE_NAME);
//        assertEquals("The created operation interface is not in the repository", repo, opIf.getRepository__Interface());
//    }
//
//    /**
//     * test iii) interface in non-repository package --> should not be mapped
//     *
//     * @throws Exception
//     */
//    @Test
//    public void testAddInterfaceInNonRepositoryPackage() throws Exception {
//        this.addFirstPackage();
//        this.addSecondPackage();
//
//        final EObject eObject = this.addInterfaceInSecondPackage();
//
//        assertTrue("Corresponding object for interface that is created in non main package is not null: " + eObject,
//                null == eObject);
//    }
//
//    @Test
//    public void testRenamePackageWithCorrespondence() {
//        this.addFirstPackage();
//        this.addSecondPackage();
//        this.addClassInSecondPackage();
//        final UpdateSingleValuedEAttribute<String> updateEAttribute = AttributeFactory.eINSTANCE
//                .createUpdateSingleValuedEAttribute();
//        updateEAttribute.setOldAffectedEObject(this.secondPackage);
//        updateEAttribute.setAffectedFeature(this.getNameAttribute(this.secondPackage));
//        updateEAttribute.setNewValue(PCM2JaMoPPUtils.BASIC_COMPONENT_NAME + PCM2JaMoPPUtils.RENAME);
//        this.secondPackage.setName(PCM2JaMoPPUtils.BASIC_COMPONENT_NAME + PCM2JaMoPPUtils.RENAME);
//
//        super.triggerSynchronization(vuri);
//
//        assertTrue("Nothing should happen since the name of the component matches the name of the implementing class",
//                PCM2JaMoPPUtils.isEmptyTransformationChangeResult(transformationChangeResult));
//    }
//
//    @Test
//    public void testRenameClassWithCorrespondence() {
//        this.addFirstPackage();
//        this.addSecondPackage();
//        this.addClassInSecondPackage();
//        final CompilationUnit compilationUnitInSecondPackage = this.secondPackage.getCompilationUnits().get(0);
//        final Class classInSecondPackage = (Class) compilationUnitInSecondPackage.getClassifiers().get(0);
//        final UpdateSingleValuedEAttribute<String> updateEAttribute = AttributeFactory.eINSTANCE
//                .createUpdateSingleValuedEAttribute();
//        updateEAttribute.setOldAffectedEObject(classInSecondPackage);
//        updateEAttribute.setAffectedFeature(this.getNameAttribute(classInSecondPackage));
//        updateEAttribute.setNewValue(classInSecondPackage.getName() + PCM2JaMoPPUtils.RENAME);
//        final TransformationChangeResult transformationChangeResult = changeSynchronizer
//                .synchronizeChange(updateEAttribute);
//        classInSecondPackage.setName(PCM2JaMoPPUtils.IMPLEMENTING_CLASS_NAME + PCM2JaMoPPUtils.RENAME);
//        final BasicComponent basicComponent = (BasicComponent) transformationChangeResult.getExistingObjectsToSave()
//                .toArray()[0];
//        assertEquals("The BasicComponent should have the same name as the renamed class",
//                classInSecondPackage.getName(), basicComponent.getEntityName());
//    }
//
//    @Test
//    public void testRenameInterfaceWithCorrespondence() {
//        this.addFirstPackage();
//        this.addSecondPackage();
//        this.addInterfaceInMainPackage();
//        final Interface jaMoPPIf = (Interface) this.mainPackage.getCompilationUnits().get(0).getClassifiers().get(0);
//        final UpdateSingleValuedEAttribute<String> updateEAttribute = AttributeFactory.eINSTANCE
//                .createUpdateSingleValuedEAttribute();
//        updateEAttribute.setOldAffectedEObject(jaMoPPIf);
//        updateEAttribute.setAffectedFeature(this.getNameAttribute(jaMoPPIf));
//        updateEAttribute.setNewValue(jaMoPPIf.getName() + PCM2JaMoPPUtils.RENAME);
//        final TransformationChangeResult transformationChangeResult = changeSynchronizer
//                .synchronizeChange(updateEAttribute);
//        final OperationInterface opIf = (OperationInterface) transformationChangeResult.getExistingObjectsToSave()
//                .toArray()[0];
//        jaMoPPIf.setName(PCM2JaMoPPUtils.INTERFACE_NAME + PCM2JaMoPPUtils.RENAME);
//        assertEquals("The OperationInterface should have the same name as the renamed interface", jaMoPPIf.getName(),
//                opIf.getEntityName());
//    }
//
//    @Test
//    public void testDeletePackageWithCorrespondence() {
//        this.addFirstPackage();
//        this.addSecondPackage();
//        this.addClassInSecondPackage();
//
//        final DeleteNonRootEObjectInList<Package> deleteNonRootEObj = ContainmentFactory.eINSTANCE
//                .createDeleteNonRootEObjectInList();
//        deleteNonRootEObj.setOldValue(this.secondPackage);
//        deleteNonRootEObj.setOldAffectedEObject(this.mainPackage);
//        deleteNonRootEObj.setAffectedFeature(null);
//
//        final TransformationChangeResult transformationChangeResult = changeSynchronizer
//                .synchronizeChange(deleteNonRootEObj);
//        final EObject[] eObj = (EObject[]) transformationChangeResult.getExistingObjectsToSave().toArray();
//        this.correspondenceInstance.removeAllCorrespondences(this.secondPackage);
//        EcoreUtil.delete(this.secondPackage);
//
//        assertTrue("Returned EObject is not null", null == eObj);
//        assertTrue("Second package still has correspondences",
//                null == this.correspondenceInstance.getAllCorrespondences(this.secondPackage));
//    }
//
//    @Test
//    public void testDeleteRootPackage() {
//        this.addFirstPackage();
//        this.addSecondPackage();
//        this.addClassInSecondPackage();
//
//        final DeleteRootEObject<EObject> deleteRoot = ObjectFactory.eINSTANCE.createDeleteRootEObject();
//        deleteRoot.setOldValue(this.mainPackage);
//
//        final TransformationChangeResult transformationChangeResult = changeSynchronizer.synchronizeChange(deleteRoot);
//        this.correspondenceInstance.removeAllCorrespondences(this.mainPackage);
//        EcoreUtil.delete(this.mainPackage);
//        final Set<Object> allCorrespondences = this.correspondenceInstance
//                .getAllEObjectsInCorrespondencesWithType(Object.class);
//
//        assertTrue("Returned TransformationChangeResult is not null",
//                PCM2JaMoPPUtils.isEmptyTransformationChangeResult(transformationChangeResult));
//        assertTrue("Main packages still has correspondences",
//                null == this.correspondenceInstance.getAllCorrespondences(this.mainPackage));
//        assertTrue(
//                "Even though the root object was deleted there are still correspondences in correspondence instance",
//                null == allCorrespondences || 0 == allCorrespondences.size());
//    }
//
//    @Test
//    public void testDeleteClassWithCorrespondence() {
//        this.addFirstPackage();
//        this.addSecondPackage();
//        this.addClassInSecondPackage();
//
//        final CompilationUnit compilationUnitInSecondPackage = this.secondPackage.getCompilationUnits().get(0);
//        final Class classInSecondPackage = (Class) compilationUnitInSecondPackage.getClassifiers().get(0);
//        final DeleteNonRootEObjectInList<Class> deleteClass = ContainmentFactory.eINSTANCE
//                .createDeleteNonRootEObjectInList();
//        deleteClass.setOldValue(classInSecondPackage);
//        deleteClass.setOldAffectedEObject(this.secondPackage);
//        deleteClass.setAffectedFeature(null);
//        final TransformationChangeResult transformationChangeResult = changeSynchronizer.synchronizeChange(deleteClass);
//        final EObject[] eObjectsToDelete = (EObject[]) transformationChangeResult.getExistingObjectsToDelete()
//                .toArray();
//
//        assertTrue("eObjectsToDelete shuld be not 0", 0 < eObjectsToDelete.length);
//        assertTrue("The class still has corresponding objects",
//                null == this.correspondenceInstance.getAllCorrespondences(classInSecondPackage));
//        assertTrue("The compilation unit still has corresponding objects",
//                null == this.correspondenceInstance.getAllCorrespondences(compilationUnitInSecondPackage));
//    }
//
//    @Test
//    public void testDeleteInterfaceWithCorrespondence() {
//        this.addFirstPackage();
//        this.addSecondPackage();
//        this.addInterfaceInMainPackage();
//
//        final CompilationUnit compilationUnitInMainPackage = this.mainPackage.getCompilationUnits().get(0);
//        final Interface interfaceInMainPackage = (Interface) compilationUnitInMainPackage.getClassifiers().get(0);
//        final DeleteNonRootEObjectInList<Interface> deleteInterface = ContainmentFactory.eINSTANCE
//                .createDeleteNonRootEObjectInList();
//        deleteInterface.setOldValue(interfaceInMainPackage);
//        deleteInterface.setOldAffectedEObject(this.secondPackage);
//        deleteInterface.setAffectedFeature(null);
//        final TransformationChangeResult transformationChangeResult = changeSynchronizer
//                .synchronizeChange(deleteInterface);
//        final EObject[] eObjects = (EObject[]) transformationChangeResult.getExistingObjectsToDelete().toArray();
//
//        assertTrue("eObjectsToDelete shuld be not 0", 0 < eObjects.length);
//        assertTrue("The interface still has corresponding objects",
//                null == this.correspondenceInstance.getAllCorrespondences(interfaceInMainPackage));
//        assertTrue("The compilation unit still has corresponding objects",
//                null == this.correspondenceInstance.getAllCorrespondences(compilationUnitInMainPackage));
//    }
//
//    private Repository addFirstPackage() throws Exception {
//        this.mainPackage = this.createPackage(new String[] { PCM2JaMoPPUtils.REPOSITORY_NAME });
//        return this.correspondenceInstance.claimUniqueCorrespondingEObjectByType(this.mainPackage, Repository.class);
//    }
//
//    private Package createPackage(final String[] namespace) throws Exception {
//        final String namespaceDotted = StringUtils.join(namespace, ".");
//        String packageFile = StringUtils.join(namespace, "/");
//        packageFile = packageFile + "package-info.java";
//        final VURI packageVURI = this.createVURIForSrcFile(packageFile);
//        final Package jaMoPPPackage = JaMoPPP2CMUtils.createPackage(namespaceDotted);
//        final Resource resource = this.resourceSet.createResource(packageVURI.getEMFUri());
//        EcoreResourceBridge.saveEObjectAsOnlyContent(jaMoPPPackage, resource);
//        logger.info("Namespace of new package: " + jaMoPPPackage.getNamespacesAsString());
//        Thread.sleep(WAITING_TIME_FOR_SYNCHRONIZATION);
//        return jaMoPPPackage;
//    }
//
//    private VURI createVURIForSrcFile(final String srcFilePath) {
//        final String vuriKey = super.getProjectPath() + "src/" + srcFilePath;
//        return VURI.getInstance(vuriKey);
//    }
//
//    private BasicComponent addSecondPackage() throws Exception {
//        this.secondPackage = this.createPackage(new String[] { PCM2JaMoPPUtils.REPOSITORY_NAME,
//                PCM2JaMoPPUtils.BASIC_COMPONENT_NAME });
//        return this.correspondenceInstance.claimUniqueCorrespondingEObjectByType(this.secondPackage,
//                BasicComponent.class);
//    }
//
//    private BasicComponent addClassInSecondPackage() {
//        final EObject createdEObject = this.addClassInPackage(this.secondPackage);
//        return (BasicComponent) createdEObject;
//    }
//
//    private EObject addClassInPackage(final Package packageForClass) {
//        final CompilationUnit cu = ContainersFactory.eINSTANCE.createCompilationUnit();
//        cu.setName(PCM2JaMoPPUtils.IMPLEMENTING_CLASS_NAME + ".java");
//        final Class jaMoPPClass = ClassifiersFactory.eINSTANCE.createClass();
//        jaMoPPClass.setName(PCM2JaMoPPUtils.IMPLEMENTING_CLASS_NAME);
//        jaMoPPClass.addModifier(ModifiersFactory.eINSTANCE.createPublic());
//        cu.getClassifiers().add(jaMoPPClass);
//        packageForClass.getCompilationUnits().add(cu);
//        cu.getNamespaces().addAll(packageForClass.getNamespaces());
//        final CreateNonRootEObjectInList<CompilationUnit> eoc = ContainmentFactory.eINSTANCE
//                .createCreateNonRootEObjectInList();
//        eoc.setNewValue(cu);
//        eoc.setOldAffectedEObject(packageForClass);
//        eoc.setAffectedFeature((EReference) packageForClass.eClass().getEStructuralFeature(
//                ContainersPackage.PACKAGE__COMPILATION_UNITS));
//        final TransformationChangeResult transformationChangeResult = changeSynchronizer.synchronizeChange(eoc);
//        final EObject createdEObject = (EObject) transformationChangeResult.getExistingObjectsToSave().toArray()[0];
//        return createdEObject;
//    }
//
//    private OperationInterface addInterfaceInMainPackage() {
//        final EObject eObject = this.addInterfaceInPackage(this.mainPackage);
//        return (OperationInterface) eObject;
//    }
//
//    private EObject addInterfaceInSecondPackage() {
//        return this.addInterfaceInPackage(this.secondPackage);
//    }
//
//    private EObject addInterfaceInPackage(final Package pack) {
//        final CompilationUnit cu = ContainersFactory.eINSTANCE.createCompilationUnit();
//        cu.setName(PCM2JaMoPPUtils.INTERFACE_NAME + ".java");
//        final Interface jaMoPPInterface = ClassifiersFactory.eINSTANCE.createInterface();
//        jaMoPPInterface.setName(PCM2JaMoPPUtils.INTERFACE_NAME);
//        cu.getClassifiers().add(jaMoPPInterface);
//        pack.getCompilationUnits().add(cu);
//        cu.getNamespaces().addAll(pack.getNamespaces());
//        final CreateNonRootEObjectInList<CompilationUnit> eoc = ContainmentFactory.eINSTANCE
//                .createCreateNonRootEObjectInList();
//        eoc.setNewValue(cu);
//        eoc.setOldAffectedEObject(pack);
//        eoc.setAffectedFeature((EReference) pack.eClass().getEStructuralFeature(
//                ContainersPackage.PACKAGE__COMPILATION_UNITS));
//        eoc.setNewValue(cu);
//
//        final TransformationChangeResult transformationChangeResult = changeSynchronizer.synchronizeChange(eoc);
//        final EObject createdEObject = (EObject) transformationChangeResult.getExistingObjectsToSave().toArray()[0];
//        return createdEObject;
//    }
//
//    private EAttribute getNameAttribute(final NamedElement ne) {
//        return (EAttribute) ne.eClass().getEStructuralFeature(CommonsPackage.NAMED_ELEMENT__NAME);
//    }
//}
