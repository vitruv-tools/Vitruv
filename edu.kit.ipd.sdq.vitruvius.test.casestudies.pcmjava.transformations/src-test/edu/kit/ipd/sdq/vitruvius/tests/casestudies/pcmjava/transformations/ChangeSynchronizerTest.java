package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Before;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ChangeSynchronizer;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;

public class ChangeSynchronizerTest {

    ChangeSynchronizer changeSynchronizer;

    @Before
    public void setUp() throws Exception {
        // init synchronizer
        this.changeSynchronizer = new ChangeSynchronizer();
        final Repository repFac = RepositoryFactory.eINSTANCE.createRepository();
        final VURI pcmURI = VURI.getInstance("pcm");
        final VURI jamoppURI = VURI.getInstance("jamopp");
        final Metamodel pcm = new Metamodel(pcmURI, ".repository");
        final Metamodel jamopp = new Metamodel(jamoppURI, ".java");
        final Mapping mapping = new Mapping(pcm, jamopp);
        final VURI correspondenceInstanceURI = VURI.getInstance("/tmp/correspondence.xmi");
        final ResourceSet resourceSet = new ResourceSetImpl();
        final Resource resource = resourceSet.createResource(correspondenceInstanceURI.getEMFUri());
        final CorrespondenceInstance correspondenceInstance = new CorrespondenceInstance(mapping,
                correspondenceInstanceURI, resource);
        this.changeSynchronizer.setCorrespondenceInstance(correspondenceInstance);
    }

    /**
     * Test change synchronizing
     */
    @Test
    public void testAddRepository() {
        this.createAndSyncRepository();

        // change name of root EObject
        // repo.setEntityName("TestSetName");
        // final UpdateEAttribute<Object> repoNameChange =
        // ChangeFactory.eINSTANCE.createUpdateEAttribute();
        // repoNameChange.setAffectedEObject(repo);
        // final EStructuralFeature nameFeature = repo.eG
        // repoNameChange.setAffectedFeature(nameFeature);
        // repoNameChange.setNewValue(repo.getEntityName());

        // create Interface in reopsitory

        // change InterfaceName

        // remove Interface

        // remove root EObject
    }

    @Test
    public void testRepositoryNameChange() {
        final Repository repo = this.createAndSyncRepository();
        repo.setEntityName("TestNameChange");

        final UpdateEAttribute<Object> repoNameChange = ChangeFactory.eINSTANCE.createUpdateEAttribute();
        repoNameChange.setAffectedEObject(repo);
        EAttribute affectedFeature = (EAttribute) repo.eClass().getEStructuralFeature("entityName");
        repoNameChange.setAffectedFeature(affectedFeature);
        repoNameChange.setNewValue(repo.getEntityName());
        System.out.println(repoNameChange.getNewValue());
        this.changeSynchronizer.synchronizeChange(repoNameChange);
    }

    @Test
    public void testAddInterface() {
        final Repository repo = this.createAndSyncRepository();
        this.addInterfaceToReposiotry(repo);
    }

    private OperationInterface addInterfaceToReposiotry(final Repository repo) {
        final OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        opInterface.setRepository__Interface(repo);
        final CreateNonRootEObject<EObject> createInterfaceChange = ChangeFactory.eINSTANCE
                .createCreateNonRootEObject();
        createInterfaceChange.setChangedEObject(opInterface);
        createInterfaceChange.setAffectedEObject(repo);
        createInterfaceChange.setAffectedFeature((EReference) opInterface.eContainingFeature());
        createInterfaceChange.setNewValue(opInterface);
        this.changeSynchronizer.synchronizeChange(createInterfaceChange);
        return opInterface;
    }

    private Repository createAndSyncRepository() {
        final Repository repo = RepositoryFactory.eINSTANCE.createRepository();
        final CreateRootEObject createRootEObj = ChangeFactory.eINSTANCE.createCreateRootEObject();
        createRootEObj.setChangedEObject(repo);
        this.changeSynchronizer.synchronizeChange(createRootEObj);
        return repo;
    }
}
