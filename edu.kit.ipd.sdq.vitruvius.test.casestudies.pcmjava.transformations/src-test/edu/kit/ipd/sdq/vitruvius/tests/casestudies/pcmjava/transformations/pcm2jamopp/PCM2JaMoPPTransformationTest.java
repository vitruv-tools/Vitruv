package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.CollectionDataType;
import de.uka.ipd.sdq.pcm.repository.CompositeDataType;
import de.uka.ipd.sdq.pcm.repository.DataType;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationSignature;
import de.uka.ipd.sdq.pcm.repository.Parameter;
import de.uka.ipd.sdq.pcm.repository.ParameterModifier;
import de.uka.ipd.sdq.pcm.repository.PrimitiveDataType;
import de.uka.ipd.sdq.pcm.repository.PrimitiveTypeEnum;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.PCMJaMoPPTransformationTestBase;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPUtils;

public class PCM2JaMoPPTransformationTest extends PCMJaMoPPTransformationTestBase {

    private static Logger logger = Logger.getLogger(PCM2JaMoPPTransformationTest.class.getSimpleName());

    /**
     * Test change synchronizing
     *
     * @throws Throwable
     */
    @Test
    public void testAddRepository() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);

        this.assertRepositoryCorrespondences(repo);
    }

    private void assertRepositoryCorrespondences(final Repository repo) throws Throwable {
        if (null == this.getCorrespondenceInstance()) {
            fail("correspondence instance is still null - no transformation was executed.");
            return;
        }
        final Set<Package> jaMoPPPackages = this.getCorrespondenceInstance().getCorrespondingEObjectsByType(repo,
                Package.class);
        boolean foundRepositoryPackage = false;
        boolean foundDatatypesPackage = false;
        boolean foundContractsPackage = false;
        for (final Package pack : jaMoPPPackages) {
            if (pack.getName().equals(repo.getEntityName())) {
                foundRepositoryPackage = true;
            }
            if (pack.getName().equals("contracts")) {
                foundContractsPackage = true;
            }
            if (pack.getName().equals("datatypes")) {
                foundDatatypesPackage = true;
            }
        }
        assertTrue("No correspondeing repository package found", foundRepositoryPackage);
        assertTrue("No correspondeing datatype package found", foundDatatypesPackage);
        assertTrue("No correspondeing contracts package found", foundContractsPackage);
    }

    @Test
    public void testRepositoryNameChange() throws Throwable {
        // setup
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);

        // Test
        repo.setEntityName(PCM2JaMoPPUtils.REPOSITORY_NAME + PCM2JaMoPPUtils.RENAME);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));

        // check
        this.assertRepositoryCorrespondences(repo);
    }

    @Test
    public void testAddInterface() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);

        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPUtils.INTERFACE_NAME);

        this.assertOperationInterfaceCorrespondences(opInterface);
    }

    @Test
    public void testRenameInterface() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);
        OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo, PCM2JaMoPPUtils.INTERFACE_NAME);

        opInterface = this.renameInterfaceAndSync(opInterface);
        super.triggerSynchronization(VURI.getInstance(opInterface.eResource()));

        this.assertOperationInterfaceCorrespondences(opInterface);
    }

    @Test
    public void testAddBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);

        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        this.assertBasicComponentCorrespondences(basicComponent);
    }

    @Test
    public void testRenameBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        basicComponent.setEntityName(PCM2JaMoPPUtils.BASIC_COMPONENT_NAME + PCM2JaMoPPUtils.RENAME);
        this.triggerSynchronization(VURI.getInstance(repo.eResource()));

        this.assertBasicComponentCorrespondences(basicComponent);
    }

    @Test
    public void testAddOperationSignature() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPUtils.INTERFACE_NAME);

        final OperationSignature opSig = this.createAndSyncOperationSignature(repo, opInterface);

        this.assertOperationSignatureCorrespondence(opSig);
    }

    @Test
    public void testRenameOperationSignature() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPUtils.INTERFACE_NAME);
        final OperationSignature opSig = this.createAndSyncOperationSignature(repo, opInterface);

        opSig.setEntityName(PCM2JaMoPPUtils.OPERATION_SIGNATURE_1_NAME + PCM2JaMoPPUtils.RENAME);
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));

        this.assertOperationSignatureCorrespondence(opSig);
    }

    @Test
    public void testAddParameter() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPUtils.INTERFACE_NAME);
        final OperationSignature opSig = this.createAndSyncOperationSignature(repo, opInterface);

        final Parameter param = this.addAndSyncParameterToSignature(repo, opSig);

        this.assertParameterCorrespondences(param);
    }

    @Test
    public void testChangeParameterName() throws Throwable {
        fail("not yet implemented");
    }

    @Test
    public void testChangeParameterType() throws Throwable {
        fail("not yet implemented");
    }

    @SuppressWarnings("unchecked")
    private void assertBasicComponentCorrespondences(final BasicComponent basicComponent) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(
                basicComponent,
                3,
                new java.lang.Class[] { CompilationUnit.class, Package.class, Class.class },
                new String[] { basicComponent.getEntityName() + "Impl", basicComponent.getEntityName(),
                        basicComponent.getEntityName() + "Impl" });

    }

    /**
     * assert that the parameter type has a correspondence to the type of the parameter in the
     * JaMoPP model
     */
    private void assertParameterCorrespondences(final Parameter param) throws Throwable {
        this.assertDataTypeCorrespondence(param.getDataType__Parameter());
    }

    private void assertDataTypeCorrespondence(final DataType dataType) throws Throwable {
        if (dataType instanceof CollectionDataType) {
            final CollectionDataType cdt = (CollectionDataType) dataType;
            this.assertSingleCorrespondence(cdt, TypeReference.class, cdt.getEntityName());
        } else if (dataType instanceof CompositeDataType) {
            final CompositeDataType cdt = (CompositeDataType) dataType;
            this.assertSingleCorrespondence(cdt, TypeReference.class, cdt.getEntityName());
        } else if (dataType instanceof PrimitiveDataType) {
            assertTrue("No correspondence exists for DataType " + dataType, 0 < this.getCorrespondenceInstance()
                    .getAllCorrespondingEObjects(dataType).size());
        }

    }

    private <T> void assertCorrespondnecesAndCompareNames(
            final de.uka.ipd.sdq.pcm.core.entity.NamedElement pcmNamedElement, final int expectedSize,
            final java.lang.Class<? extends EObject>[] expectedClasses, final String[] expectedNames) throws Throwable {
        final Set<EObject> correspondences = this.getCorrespondenceInstance().claimCorrespondingEObjects(
                pcmNamedElement);
        assertEquals("correspondences.size should be " + expectedSize, expectedSize, correspondences.size());
        for (int i = 0; i < expectedClasses.length; i++) {
            final java.lang.Class<? extends EObject> expectedClass = expectedClasses[i];
            final EObject correspondingEObject = this.getCorrespondenceInstance()
                    .claimUniqueCorrespondingEObjectByType(pcmNamedElement, expectedClass);
            if (!expectedClass.isInstance(correspondingEObject)) {
                fail("Corresponding EObject " + correspondingEObject + " is not an instance of " + expectedClass);
            }
            final NamedElement jaMoPPElement = (NamedElement) correspondingEObject;
            assertTrue("The '" + jaMoPPElement.getClass().getSimpleName() + "' with name '" + jaMoPPElement.getName()
                    + "' does not contain " + "the '" + pcmNamedElement.getClass().getSimpleName() + "' with name "
                    + pcmNamedElement.getEntityName(), jaMoPPElement.getName()
                    .contains(pcmNamedElement.getEntityName()));
        }
    }

    private void assertOperationSignatureCorrespondence(final OperationSignature opSig) throws Throwable {
        this.assertSingleCorrespondence(opSig, InterfaceMethod.class, opSig.getEntityName());
    }

    private OperationSignature createAndSyncOperationSignature(final Repository repo,
            final OperationInterface opInterface) throws IOException {
        final OperationSignature opSig = RepositoryFactory.eINSTANCE.createOperationSignature();
        opSig.setEntityName(PCM2JaMoPPUtils.OPERATION_SIGNATURE_1_NAME);
        opSig.setInterface__OperationSignature(opInterface);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));
        return opSig;
    }

    @SuppressWarnings("unchecked")
    private void assertSingleCorrespondence(final de.uka.ipd.sdq.pcm.core.entity.NamedElement pcmNamedElement,
            final java.lang.Class<? extends EObject> expectedClass, final String expectedName) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(pcmNamedElement, 1, new java.lang.Class[] { expectedClass },
                new String[] { expectedName });
    }

    @SuppressWarnings("unchecked")
    private void assertOperationInterfaceCorrespondences(final OperationInterface opInterface) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(opInterface, 2, new java.lang.Class[] { CompilationUnit.class,
                Interface.class }, new String[] { opInterface.getEntityName(), opInterface.getEntityName() });
    }

    private OperationInterface renameInterfaceAndSync(final OperationInterface opInterface) throws Throwable {
        final String newValue = opInterface.getEntityName() + PCM2JaMoPPUtils.RENAME;
        opInterface.setEntityName(newValue);
        EcoreResourceBridge.saveResource(opInterface.eResource());
        super.triggerSynchronization(VURI.getInstance(opInterface.eResource()));
        return opInterface;
    }

    private BasicComponent addBasicComponentAndSync(final Repository repo) throws Throwable {
        final BasicComponent basicComponent = PCM2JaMoPPUtils.createBasicComponent(repo);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));
        return basicComponent;
    }

    private OperationInterface addInterfaceToReposiotryAndSync(final Repository repo, final String interfaceName)
            throws Throwable {
        final OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        opInterface.setRepository__Interface(repo);
        opInterface.setEntityName(interfaceName);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));
        return opInterface;
    }

    private Repository createAndSyncRepository(final ResourceSet resourceSet, final String repositoryName)
            throws IOException {
        final Repository repo = PCM2JaMoPPUtils.createRepository(resourceSet, repositoryName);
        this.changeRecorder.beginRecording(Collections.singletonList(repo));
        super.synchronizeFileChange(FileChangeKind.CREATE, VURI.getInstance(repo.eResource()));
        return repo;
    }

    private Parameter addAndSyncParameterToSignature(final Repository repo, final OperationSignature opSig) {
        final Parameter param = RepositoryFactory.eINSTANCE.createParameter();
        param.setParameterName(PCM2JaMoPPUtils.PARAMETER_NAME);
        final PrimitiveDataType dataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        dataType.setType(PrimitiveTypeEnum.INT);
        param.setDataType__Parameter(dataType);
        param.setModifier__Parameter(ParameterModifier.IN);
        param.setOperationSignature__Parameter(opSig);
        opSig.getParameters__OperationSignature().add(param);
        final VURI vuri = VURI.getInstance(repo.eResource());
        super.triggerSynchronization(vuri);
        return param;
    }
}
