package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.junit.Before;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.CollectionDataType;
import de.uka.ipd.sdq.pcm.repository.CompositeDataType;
import de.uka.ipd.sdq.pcm.repository.DataType;
import de.uka.ipd.sdq.pcm.repository.InnerDeclaration;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationProvidedRole;
import de.uka.ipd.sdq.pcm.repository.OperationRequiredRole;
import de.uka.ipd.sdq.pcm.repository.OperationSignature;
import de.uka.ipd.sdq.pcm.repository.Parameter;
import de.uka.ipd.sdq.pcm.repository.ParameterModifier;
import de.uka.ipd.sdq.pcm.repository.PrimitiveDataType;
import de.uka.ipd.sdq.pcm.repository.PrimitiveTypeEnum;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import de.uka.ipd.sdq.pcm.system.System;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.DataTypeCorrespondenceHelper;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.ChangeDescription2ChangeConverter;
import edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine.EMFModelPropagationEngineImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.synctransprovider.TransformationExecutingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.PCMJaMoPPTransformationTestBase;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;
import edu.kit.ipd.sdq.vitruvius.tests.jamopppcm.util.JaMoPPPCMTestUtil;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

/**
 * super class for all repository and system tests. Contains helper methods
 *
 * @author Langhamm
 *
 */
public class PCM2JaMoPPTransformationTest extends PCMJaMoPPTransformationTestBase {

    protected VSUMImpl vsum;
    protected SyncManagerImpl syncManager;
    protected MetaRepositoryImpl metaRepository;
    protected ChangeRecorder changeRecorder;

    private ChangeDescription2ChangeConverter changeDescrition2ChangeConverter;

    protected CorrespondenceInstance correspondenceInstance;

    /**
     * Set up SyncMangaer and metaRepository facility. Creates a fresh VSUM, Metarepository etc.
     * before each test
     *
     * @throws Exception
     */
    @Before
    public void setUpTest() throws Exception {
        this.metaRepository = JaMoPPPCMTestUtil.createJaMoPPPCMMetaRepository();
        this.vsum = TestUtil.createVSUM(this.metaRepository);
        final TransformationExecutingProvidingImpl syncTransformationProvider = new TransformationExecutingProvidingImpl();
        final EMFModelPropagationEngineImpl propagatingChange = new EMFModelPropagationEngineImpl(
                syncTransformationProvider);
        this.syncManager = new SyncManagerImpl(this.vsum, propagatingChange, this.vsum, this.metaRepository, this.vsum);
        this.resourceSet = new ResourceSetImpl();
        this.changeRecorder = new ChangeRecorder();
        this.changeDescrition2ChangeConverter = new ChangeDescription2ChangeConverter();
    }

    @Override
    protected void afterTest() {
        this.correspondenceInstance = null;
    }

    private CorrespondenceInstance getCorrespondenceInstanceForProject(final String projectName) throws Throwable {
        final VURI pcmMMUri = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
        final VURI jaMoPPURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
        return this.vsum.getCorrespondenceInstanceOriginal(pcmMMUri, jaMoPPURI);
    }

    @Override
    protected CorrespondenceInstance getCorrespondenceInstance() throws Throwable {
        if (null == this.correspondenceInstance) {
            this.correspondenceInstance = this.getCorrespondenceInstanceForProject(TestUtil.PROJECT_URI);
        }
        return this.correspondenceInstance;
    }

    protected void triggerSynchronization(final VURI vuri) {
        final ChangeDescription cd = this.changeRecorder.endRecording();
        cd.applyAndReverse();
        final List<Change> changes = this.changeDescrition2ChangeConverter.getChanges(cd, vuri);
        cd.applyAndReverse();
        this.syncManager.synchronizeChanges(changes);
        this.changeRecorder.beginRecording(Collections.EMPTY_LIST);
    }

    protected void triggerSynchronization(final EObject eObject) {
        final VURI vuri = VURI.getInstance(eObject.eResource());
        this.triggerSynchronization(vuri);
    }

    protected void synchronizeFileChange(final FileChangeKind fileChangeKind, final VURI vuri) {
        final FileChange fileChange = new FileChange(fileChangeKind, vuri);
        this.syncManager.synchronizeChange(fileChange);
    }

    /**
     * innerDeclaration must have 3 correspondences: one field, one getter and one setter
     *
     * @param innerDec
     * @throws Throwable
     */
    protected void assertInnerDeclaration(final InnerDeclaration innerDec) throws Throwable {
        final Set<EObject> correspondingObjects = this.getCorrespondenceInstance()
                .getAllCorrespondingEObjects(innerDec);
        int fieldsFound = 0;
        int methodsFound = 0;
        for (final EObject eObject : correspondingObjects) {
            if (eObject instanceof Field) {
                fieldsFound++;
                final Field field = (Field) eObject;
                assertTrue("field name unexpected",
                        field.getName().toLowerCase().contains(innerDec.getEntityName().toLowerCase()));
            } else if (eObject instanceof Method) {
                methodsFound++;
            } else {
                fail("unexpected corresponding object for inner declartion found: " + eObject);
            }
        }
        assertEquals("unexpected number of corresponding fields found", 1, fieldsFound);
        assertEquals("unexpected number of corresponding methods found", 2, methodsFound);
    }

    @SuppressWarnings("unchecked")
    protected void assertDataTypeCorrespondence(final DataType dataType) throws Throwable {
        if (dataType instanceof CollectionDataType) {
            final CollectionDataType cdt = (CollectionDataType) dataType;
            this.assertCorrespondnecesAndCompareNames(cdt, 2, new java.lang.Class[] { CompilationUnit.class,
                    Classifier.class }, new String[] { cdt.getEntityName() + "Impl.java", cdt.getEntityName() });
        } else if (dataType instanceof CompositeDataType) {
            final CompositeDataType cdt = (CompositeDataType) dataType;
            this.assertCorrespondnecesAndCompareNames(cdt, 2, new java.lang.Class[] { CompilationUnit.class,
                    Classifier.class }, new String[] { cdt.getEntityName() + "Impl.java", cdt.getEntityName() });
        } else if (dataType instanceof PrimitiveDataType) {
            final PrimitiveDataType pdt = (PrimitiveDataType) dataType;
            assertTrue("No correspondence exists for DataType " + dataType,
                    null != DataTypeCorrespondenceHelper.claimJaMoPPTypeForPrimitiveDataType(pdt));
        }

    }

    protected <T> Set<NamedElement> assertCorrespondnecesAndCompareNames(
            final de.uka.ipd.sdq.pcm.core.entity.NamedElement pcmNamedElement, final int expectedSize,
            final java.lang.Class<? extends EObject>[] expectedClasses, final String[] expectedNames) throws Throwable {
        final Set<EObject> correspondences = this.getCorrespondenceInstance().claimCorrespondingEObjects(
                pcmNamedElement);
        assertEquals("correspondences.size should be " + expectedSize, expectedSize, correspondences.size());
        final Set<NamedElement> jaMoPPElements = new HashSet<NamedElement>();
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
            jaMoPPElements.add(jaMoPPElement);
        }
        return jaMoPPElements;
    }

    protected void assertEmptyCorrespondence(final EObject eObject) throws Throwable {
        try {
            final Set<EObject> correspondences = this.getCorrespondenceInstance().claimCorrespondingEObjects(eObject);
            fail("correspondences.size should be " + 0 + " but is " + correspondences.size());
        } catch (final RuntimeException re) {
            // expected Runtime expception:
        }

    }

    protected void assertEqualsTypeReference(final TypeReference expected, final TypeReference actual) {
        assertTrue("type reference are not from the same type", expected.getClass().equals(actual.getClass()));
        // Note: not necessary to check Primitive type: if the classes are from
        // the same type (e.g.
        // Int) the TypeReferences are equal
        if (expected instanceof ClassifierReference) {
            final ClassifierReference expectedClassifierRef = (ClassifierReference) expected;
            final ClassifierReference actualClassifierRef = (ClassifierReference) actual;
            assertEquals("Target of type reference does not have the same name", expectedClassifierRef.getTarget()
                    .getName(), actualClassifierRef.getTarget().getName());
        }
        if (expected instanceof NamespaceClassifierReference) {
            final NamespaceClassifierReference expectedNamespaceClassifierRef = (NamespaceClassifierReference) expected;
            final NamespaceClassifierReference actualNamespaceClassifierRef = (NamespaceClassifierReference) actual;
            this.assertEqualsTypeReference(expectedNamespaceClassifierRef.getPureClassifierReference(),
                    actualNamespaceClassifierRef.getPureClassifierReference());
        }
    }

    private OperationSignature createAndSyncOperationSignature(final Repository repo,
            final OperationInterface opInterface) throws IOException {
        final OperationSignature opSig = RepositoryFactory.eINSTANCE.createOperationSignature();
        opSig.setEntityName(PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME);
        opSig.setInterface__OperationSignature(opInterface);
        EcoreResourceBridge.saveResource(repo.eResource());
        this.triggerSynchronization(VURI.getInstance(repo.eResource()));
        return opSig;
    }

    @SuppressWarnings("unchecked")
    protected NamedElement assertSingleCorrespondence(
            final de.uka.ipd.sdq.pcm.core.entity.NamedElement pcmNamedElement,
            final java.lang.Class<? extends EObject> expectedClass, final String expectedName) throws Throwable {
        final Set<NamedElement> namedElements = this.assertCorrespondnecesAndCompareNames(pcmNamedElement, 1,
                new java.lang.Class[] { expectedClass }, new String[] { expectedName });
        return namedElements.iterator().next();
    }

    protected OperationInterface renameInterfaceAndSync(final OperationInterface opInterface) throws Throwable {
        final String newValue = opInterface.getEntityName() + PCM2JaMoPPTestUtils.RENAME;
        opInterface.setEntityName(newValue);
        EcoreResourceBridge.saveResource(opInterface.eResource());
        this.triggerSynchronization(VURI.getInstance(opInterface.eResource()));
        return opInterface;
    }

    protected BasicComponent addBasicComponentAndSync(final Repository repo, final String name) throws Throwable {
        final BasicComponent basicComponent = PCM2JaMoPPTestUtils.createBasicComponent(repo, name);
        EcoreResourceBridge.saveResource(repo.eResource());
        this.triggerSynchronization(VURI.getInstance(repo.eResource()));
        return basicComponent;
    }

    protected BasicComponent addBasicComponentAndSync(final Repository repo) throws Throwable {
        return this.addBasicComponentAndSync(repo, PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME);
    }

    protected OperationInterface addInterfaceToReposiotryAndSync(final Repository repo, final String interfaceName)
            throws Throwable {
        final OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        opInterface.setRepository__Interface(repo);
        opInterface.setEntityName(interfaceName);
        EcoreResourceBridge.saveResource(repo.eResource());
        this.triggerSynchronization(VURI.getInstance(repo.eResource()));
        return opInterface;
    }

    protected Repository createAndSyncRepository(final ResourceSet resourceSet, final String repositoryName)
            throws IOException {
        final Repository repo = PCM2JaMoPPTestUtils.createRepository(resourceSet, repositoryName);
        this.changeRecorder.beginRecording(Collections.singletonList(repo));
        this.synchronizeFileChange(FileChangeKind.CREATE, VURI.getInstance(repo.eResource()));
        return repo;
    }

    protected OperationSignature createAndSyncRepoInterfaceAndOperationSignature() throws IOException, Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);
        final OperationSignature opSig = this.createAndSyncOperationSignature(repo, opInterface);
        return opSig;
    }

    protected Parameter addAndSyncParameterWithPrimitiveTypeToSignature(final OperationSignature opSig) {
        final PrimitiveDataType dataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        dataType.setType(PrimitiveTypeEnum.INT);
        return this.addAndSyncParameterToSignature(opSig, dataType, PCM2JaMoPPTestUtils.PARAMETER_NAME);
    }

    protected Parameter addAndSyncParameterToSignature(final OperationSignature opSig, final DataType dataType,
            final String parameterName) {
        final Parameter param = RepositoryFactory.eINSTANCE.createParameter();
        param.setParameterName(parameterName);
        param.setDataType__Parameter(dataType);
        param.setModifier__Parameter(ParameterModifier.IN);
        param.setOperationSignature__Parameter(opSig);
        opSig.getParameters__OperationSignature().add(param);
        final VURI vuri = VURI.getInstance(opSig.eResource());
        this.triggerSynchronization(vuri);
        return param;
    }

    protected CompositeDataType createAndSyncCompositeDataType(final Repository repo, final String name) {
        final CompositeDataType cdt = this.createCompositeDataType(repo, name);
        this.triggerSynchronization(VURI.getInstance(repo.eResource()));
        return cdt;
    }

    protected CompositeDataType createCompositeDataType(final Repository repo, final String name) {
        final CompositeDataType cdt = RepositoryFactory.eINSTANCE.createCompositeDataType();
        cdt.setEntityName(name);
        cdt.setRepository__DataType(repo);
        return cdt;
    }

    protected CompositeDataType createAndSyncCompositeDataType(final Repository repo) {
        return this.createAndSyncCompositeDataType(repo, PCM2JaMoPPTestUtils.COMPOSITE_DATA_TYPE_NAME);
    }

    protected Parameter createAndSyncRepoOpSigAndParameter() throws IOException, Throwable {
        final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();

        final Parameter param = this.addAndSyncParameterWithPrimitiveTypeToSignature(opSig);
        return param;
    }

    public Parameter createAndSyncRepoOpSigAndParameterWithDataTypeName(final String compositeDataTypeName,
            final String parameterName) throws Throwable {
        final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();
        final CompositeDataType cdt = this.createAndSyncCompositeDataType(opSig.getInterface__OperationSignature()
                .getRepository__Interface(), compositeDataTypeName);
        final Parameter param = this.addAndSyncParameterToSignature(opSig, cdt, parameterName);
        return param;
    }

    protected InnerDeclaration createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration() throws IOException {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final CompositeDataType cdt = this.createAndSyncCompositeDataType(repo);
        final InnerDeclaration innerDec = this.addInnerDeclaration(cdt);
        this.triggerSynchronization(VURI.getInstance(repo.eResource()));
        return innerDec;
    }

    protected InnerDeclaration addInnerDeclaration(final CompositeDataType cdt) {
        final InnerDeclaration innerDec = RepositoryFactory.eINSTANCE.createInnerDeclaration();
        final PrimitiveDataType pdt = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        pdt.setType(PrimitiveTypeEnum.INT);
        innerDec.setDatatype_InnerDeclaration(pdt);
        innerDec.setCompositeDataType_InnerDeclaration(cdt);
        innerDec.setEntityName(PCM2JaMoPPTestUtils.INNER_DEC_NAME);
        cdt.getInnerDeclaration_CompositeDataType().add(innerDec);
        return innerDec;
    }

    protected OperationProvidedRole createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole() throws IOException,
    Throwable {
        final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();
        final OperationInterface opInterface = opSig.getInterface__OperationSignature();
        final BasicComponent basicComponent = this.addBasicComponentAndSync(opInterface.getRepository__Interface());

        final OperationProvidedRole operationProvidedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        operationProvidedRole
        .setEntityName(basicComponent.getEntityName() + "_provides_" + opInterface.getEntityName());
        operationProvidedRole.setProvidedInterface__OperationProvidedRole(opInterface);
        operationProvidedRole.setProvidingEntity_ProvidedRole(basicComponent);
        final VURI vuri = VURI.getInstance(opInterface.eResource());
        this.triggerSynchronization(vuri);
        return operationProvidedRole;
    }

    protected OperationRequiredRole createAndSyncRepoBasicCompInterfaceAndOperationReqiredRole() throws IOException,
    Throwable {
        final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();
        final OperationInterface opInterface = opSig.getInterface__OperationSignature();
        final BasicComponent basicComponent = this.addBasicComponentAndSync(opInterface.getRepository__Interface());

        final OperationRequiredRole operationRequiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        operationRequiredRole.setEntityName(opInterface.getEntityName().toLowerCase());
        operationRequiredRole.setRequiredInterface__OperationRequiredRole(opInterface);
        operationRequiredRole.setRequiringEntity_RequiredRole(basicComponent);
        final VURI vuri = VURI.getInstance(basicComponent.eResource());
        this.triggerSynchronization(vuri);
        return operationRequiredRole;
    }

    protected System createAndSyncSystem(final String name) throws Throwable {
        final System system = PCM2JaMoPPTestUtils.createSystem(this.resourceSet, name);
        this.changeRecorder.beginRecording(Collections.singletonList(system));
        this.synchronizeFileChange(FileChangeKind.CREATE, VURI.getInstance(system.eResource()));
        return system;
    }
}
