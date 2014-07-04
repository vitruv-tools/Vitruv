package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.Test;

import pcm_mockup.Interface;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Repository;
import uml_mockup.UPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FeatureInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class CorrespondenceTest extends VSUMTest {
    private static final String interfaceCRefName = "interfaces";

    @Override
    @Test
    public void testAll() {
        VSUMImpl vsum = testMetaRepositoryVSUMAndModelInstancesCreation();
        VURI pcmVURI = VURI.getInstance(PCM_INSTANCE_URI);
        VURI umlVURI = VURI.getInstance(UML_INSTANCE_URI);
        ModelInstance pcmInstance = vsum.getModelInstanceOriginal(pcmVURI);
        ModelInstance umlInstance = vsum.getModelInstanceOriginal(umlVURI);
        Repository repo = pcmInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository.class);
        UPackage pkg = umlInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage.class);
        VURI pcmMMVURI = VURI.getInstance(PCM_MM_URI);
        VURI umlMMVURI = VURI.getInstance(UML_MM_URI);
        CorrespondenceInstance corresp = vsum.getCorrespondenceInstanceOriginal(pcmMMVURI, umlMMVURI);
        assertNotNull(corresp);

        EObjectCorrespondence repo2pkg = testAllClaimersAndGettersForEObjectCorrespondences(repo, pkg, corresp);

        Pair<FeatureInstance, FeatureInstance> repoIfaceFIAndPkgIfaceFI = testAllClaimersAndGettersForFeatureCorrespondences(
                repo, pkg, corresp, repo2pkg);

        Interface repoInterface = testHasCorrespondences(repo, pkg, corresp);

        testSimpleRemove(pkg, corresp, repo2pkg, repoInterface);

        testRecursiveRemove(repo, pkg, corresp, repo2pkg, repoIfaceFIAndPkgIfaceFI);

        testUpdate(repo, pkg, corresp, repo2pkg);
    }

    private EObjectCorrespondence testAllClaimersAndGettersForEObjectCorrespondences(final Repository repo,
            final UPackage pkg, final CorrespondenceInstance corresp) {
        EObjectCorrespondence repo2pkg = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
        repo2pkg.setElementA(repo);
        repo2pkg.setElementB(pkg);
        corresp.addSameTypeCorrespondence(repo2pkg);

        // claimAllCorrespondence is already indirectly tested via claimUniqueCorrespondence
        Correspondence uniqueRepoCorrespondence = corresp.claimUniqueCorrespondence(repo);
        assertEquals(uniqueRepoCorrespondence, repo2pkg);
        Correspondence uniquePkgCorrespondence = corresp.claimUniqueCorrespondence(pkg);
        assertEquals(uniquePkgCorrespondence, repo2pkg);

        // claimCorrespondingEObject is already indirectly tested via
        // claimUniqueCorrespondingEObject
        EObject correspForRepo = corresp.claimUniqueCorrespondingEObject(repo);
        assertEquals(correspForRepo, pkg);
        EObject correspForPkg = corresp.claimUniqueCorrespondingEObject(pkg);
        assertEquals(correspForPkg, repo);

        List<Interface> interfaces = repo.getInterfaces();
        assertEquals(interfaces.size(), 1);
        Interface iface = interfaces.get(0);
        Correspondence correspForIface = corresp.claimUniqueOrNullCorrespondenceForEObject(iface);
        assertNull(correspForIface);
        // TODO test exception throwing of claimUniqueOrNullCorrespondenceForEObject

        Set<Correspondence> allRepoCorrespondences = corresp.getAllCorrespondences(repo);
        assertEquals(allRepoCorrespondences.size(), 1);
        assertTrue(allRepoCorrespondences.contains(repo2pkg));
        Set<Correspondence> allPkgCorrespondences = corresp.getAllCorrespondences(pkg);
        assertEquals(allPkgCorrespondences.size(), 1);
        assertTrue(allPkgCorrespondences.contains(repo2pkg));

        Set<Repository> allRepoTypeCorresp = corresp.getAllEObjectsInCorrespondencesWithType(Repository.class);
        assertTrue(allRepoTypeCorresp.contains(repo));
        Set<UPackage> allPkgTypeCorresp = corresp.getAllEObjectsInCorrespondencesWithType(UPackage.class);
        assertTrue(allPkgTypeCorresp.contains(pkg));

        Set<EObject> allCorrespForRepo = corresp.getAllCorrespondingEObjects(repo);
        assertEquals(allCorrespForRepo.size(), 1);
        assertTrue(allCorrespForRepo.contains(pkg));
        Set<EObject> allCorrespForPkg = corresp.getAllCorrespondingEObjects(pkg);
        assertEquals(allCorrespForPkg.size(), 1);
        assertTrue(allCorrespForPkg.contains(repo));

        return repo2pkg;
    }

    private Pair<FeatureInstance, FeatureInstance> testAllClaimersAndGettersForFeatureCorrespondences(
            final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp,
            final EObjectCorrespondence repo2pkg) {
        EContainmentReferenceCorrespondence repoIfaceCRef2PkgIfaceCRef = CorrespondenceFactory.eINSTANCE
                .createEContainmentReferenceCorrespondence();
        repoIfaceCRef2PkgIfaceCRef.setElementA(repo);
        repoIfaceCRef2PkgIfaceCRef.setElementB(pkg);
        EStructuralFeature repoIfaceCRef = repo.eClass().getEStructuralFeature(interfaceCRefName);
        assertTrue(repoIfaceCRef instanceof EReference);
        repoIfaceCRef2PkgIfaceCRef.setFeatureA((EReference) repoIfaceCRef);
        EStructuralFeature pkgIfaceCRef = pkg.eClass().getEStructuralFeature(interfaceCRefName);
        assertTrue(pkgIfaceCRef instanceof EReference);
        repoIfaceCRef2PkgIfaceCRef.setFeatureB((EReference) pkgIfaceCRef);
        repoIfaceCRef2PkgIfaceCRef.setParent(repo2pkg);
        repoIfaceCRef2PkgIfaceCRef.setType(CorrespondenceType.BIJECTION);

        corresp.addSameTypeCorrespondence(repoIfaceCRef2PkgIfaceCRef);

        FeatureInstance repoIfaceFI = FeatureInstance.getInstance(repo, repoIfaceCRef);
        FeatureInstance pkgIfaceFI = FeatureInstance.getInstance(pkg, pkgIfaceCRef);

        FeatureInstance correspForRepoIfaceFI = corresp.claimUniqueCorrespondingFeatureInstance(repoIfaceFI);
        assertEquals(correspForRepoIfaceFI, pkgIfaceFI);
        FeatureInstance correspForPkgIfaceFI = corresp.claimUniqueCorrespondingFeatureInstance(pkgIfaceFI);
        assertEquals(correspForPkgIfaceFI, repoIfaceFI);

        Set<FeatureInstance> allCorrespForRepoIfaceFI = corresp.getAllCorrespondingFeatureInstances(repoIfaceFI);
        assertEquals(allCorrespForRepoIfaceFI.size(), 1);
        assertTrue(allCorrespForRepoIfaceFI.contains(pkgIfaceFI));
        Set<FeatureInstance> allCorrespForPkgIfaceFI = corresp.getAllCorrespondingFeatureInstances(pkgIfaceFI);
        assertEquals(allCorrespForPkgIfaceFI.size(), 1);
        assertTrue(allCorrespForPkgIfaceFI.contains(repoIfaceFI));

        return new Pair<FeatureInstance, FeatureInstance>(repoIfaceFI, pkgIfaceFI);
    }

    private Interface testHasCorrespondences(final Repository repo, final UPackage pkg,
            final CorrespondenceInstance corresp) {
        assertTrue(corresp.hasCorrespondences(repo));
        assertTrue(corresp.hasCorrespondences(pkg));
        List<Interface> repoInterfaces = repo.getInterfaces();
        assertEquals(repoInterfaces.size(), 1);
        Interface repoInterface = repoInterfaces.get(0);
        assertTrue(!corresp.hasCorrespondences(repoInterface));
        return repoInterface;
    }

    private void testSimpleRemove(final UPackage pkg, final CorrespondenceInstance corresp,
            final EObjectCorrespondence repo2pkg, final Interface repoInterface) {
        List<uml_mockup.Interface> pkgInterfaces = pkg.getInterfaces();
        assertEquals(pkgInterfaces.size(), 1);
        uml_mockup.Interface pkgInterface = pkgInterfaces.get(0);
        EObjectCorrespondence repoIface2PkgIface = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
        repoIface2PkgIface.setElementA(repoInterface);
        repoIface2PkgIface.setElementB(pkgInterface);
        corresp.addSameTypeCorrespondence(repoIface2PkgIface);

        corresp.removeAllCorrespondences(repoInterface);
        Set<Correspondence> repoInterfaceCorresp = corresp.getAllCorrespondences(repoInterface);
        assertTrue(repoInterfaceCorresp.isEmpty());
        Set<Correspondence> pkgInterfaceCorresp = corresp.getAllCorrespondences(pkgInterface);
        assertTrue(pkgInterfaceCorresp.isEmpty());
        Set<EObject> correspForRepoInterface = corresp.getAllCorrespondingEObjects(repoInterface);
        assertTrue(correspForRepoInterface.isEmpty());
        Set<EObject> correspForPkgInterface = corresp.getAllCorrespondingEObjects(pkgInterface);
        assertTrue(correspForPkgInterface.isEmpty());
        Set<Interface> correspForRepoInterfaceType = corresp.getAllEObjectsInCorrespondencesWithType(Interface.class);
        assertTrue(correspForRepoInterfaceType.isEmpty());
        Set<uml_mockup.Interface> correspForPkgInterfaceType = corresp
                .getAllEObjectsInCorrespondencesWithType(uml_mockup.Interface.class);
        assertTrue(correspForPkgInterfaceType.isEmpty());
    }

    private void testRecursiveRemove(final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp,
            final EObjectCorrespondence repo2pkg, final Pair<FeatureInstance, FeatureInstance> repoIfaceFIAndPkgIfaceFI) {
        corresp.removeCorrespondenceAndAllDependentCorrespondences(repo2pkg);
        Set<Correspondence> repoCorresp = corresp.getAllCorrespondences(repo);
        assertTrue(repoCorresp.isEmpty());
        Set<Correspondence> pkgCorresp = corresp.getAllCorrespondences(pkg);
        assertTrue(pkgCorresp.isEmpty());
        Set<EObject> correspForRepo = corresp.getAllCorrespondingEObjects(repo);
        assertTrue(correspForRepo.isEmpty());
        Set<EObject> correspForPkg = corresp.getAllCorrespondingEObjects(pkg);
        assertTrue(correspForPkg.isEmpty());
        Set<Repository> correspForRepoType = corresp.getAllEObjectsInCorrespondencesWithType(Repository.class);
        assertTrue(correspForRepoType.isEmpty());
        Set<UPackage> correspForPkgType = corresp.getAllEObjectsInCorrespondencesWithType(UPackage.class);
        assertTrue(correspForPkgType.isEmpty());

        FeatureInstance repoIfaceFI = repoIfaceFIAndPkgIfaceFI.getFirst();
        FeatureInstance pkgIfaceFI = repoIfaceFIAndPkgIfaceFI.getSecond();
        Set<FeatureInstance> correspForRepoIfaceFI = corresp.getAllCorrespondingFeatureInstances(repoIfaceFI);
        assertTrue(correspForRepoIfaceFI.isEmpty());
        Set<FeatureInstance> correspForPkgIfaceFI = corresp.getAllCorrespondingFeatureInstances(pkgIfaceFI);
        assertTrue(correspForPkgIfaceFI.isEmpty());
    }

    private void testUpdate(final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp,
            final EObjectCorrespondence repo2pkg) {
        Repository newRepo = Pcm_mockupFactory.eINSTANCE.createRepository();
        corresp.update(repo, newRepo);
        Set<Correspondence> repoCorresp = corresp.getAllCorrespondences(repo);
        assertTrue(repoCorresp.isEmpty());
        Correspondence uniqueNewRepoCorrespondence = corresp.claimUniqueCorrespondence(newRepo);
        assertEquals(uniqueNewRepoCorrespondence, repo2pkg);
        Correspondence uniquePkgCorrespondence = corresp.claimUniqueCorrespondence(pkg);
        assertEquals(uniquePkgCorrespondence, repo2pkg);
        EObject correspForNewRepo = corresp.claimUniqueCorrespondingEObject(newRepo);
        assertEquals(correspForNewRepo, pkg);
        EObject correspForPkg = corresp.claimUniqueCorrespondingEObject(pkg);
        assertEquals(correspForPkg, newRepo);
        // TODO is this really enough update testing?
    }
}
