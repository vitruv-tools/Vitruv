package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import pcm_mockup.Repository;
import uml_mockup.UPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class CorrespondenceTest extends VSUMTest {
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
        EObjectCorrespondence repo2pkg = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
        repo2pkg.setElementA(repo);
        repo2pkg.setElementB(pkg);
        corresp.addSameTypeCorrespondence(repo2pkg);
        Set<Correspondence> allRepoCorresp = corresp.getAllCorrespondences(repo);
        assertTrue(allRepoCorresp.contains(repo2pkg));
        Set<Correspondence> allPkgCorresp = corresp.getAllCorrespondences(pkg);
        assertTrue(allPkgCorresp.contains(repo2pkg));
        Set<Repository> allRepoTypeCorresp = corresp.getAllEObjectsInCorrespondencesWithType(Repository.class);
        assertTrue(allRepoTypeCorresp.contains(repo2pkg));
        Set<UPackage> allPkgTypeCorresp = corresp.getAllEObjectsInCorrespondencesWithType(UPackage.class);
        assertTrue(allPkgTypeCorresp.contains(repo2pkg));
        Correspondence correspondenceForEObjectIfUnique = corresp.getUniqueCorrespondenceForEObject(repo);
    }
}
