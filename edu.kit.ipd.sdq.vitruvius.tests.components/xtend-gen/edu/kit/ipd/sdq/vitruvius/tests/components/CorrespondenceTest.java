package edu.kit.ipd.sdq.vitruvius.tests.components;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.components.MetaRepositoryTest;
import edu.kit.ipd.sdq.vitruvius.tests.components.VSUMTest;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Assert;
import org.junit.Test;
import pcm_mockup.Interface;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Repository;
import uml_mockup.UPackage;

@SuppressWarnings("all")
public class CorrespondenceTest extends VSUMTest {
  private final static String interfaceCRefName = "interfaces";
  
  private final static Logger LOGGER = Logger.getLogger(CorrespondenceTest.class.getSimpleName());
  
  @Test
  public void testAllInCommand() {
    final VSUMImpl vsum = this.testMetaRepositoryAndVSUMCreation();
    final Callable<Void> _function = new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        CorrespondenceTest.this.testAll(vsum);
        return null;
      }
    };
    EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(_function, vsum);
  }
  
  private void testAll(final VSUMImpl vsum) {
    String _pCMInstanceUri = this.getPCMInstanceUri();
    Repository repo = this.<Repository>testLoadObject(vsum, _pCMInstanceUri, Repository.class);
    String _uMLInstanceURI = this.getUMLInstanceURI();
    UPackage pkg = this.<UPackage>testLoadObject(vsum, _uMLInstanceURI, UPackage.class);
    InternalCorrespondenceInstance correspondenceInstance = this.testCorrespondenceInstanceCreation(vsum);
    boolean _hasCorrespondences = correspondenceInstance.hasCorrespondences();
    Assert.assertFalse(_hasCorrespondences);
    Correspondence repo2pkg = this.createRepo2PkgCorrespondence(repo, pkg, correspondenceInstance);
    this.testAllClaimersAndGettersForEObjectCorrespondences(repo, pkg, correspondenceInstance, repo2pkg);
    Interface repoInterface = this.testHasCorrespondences(repo, pkg, correspondenceInstance);
    this.testSimpleRemove(pkg, correspondenceInstance, repo2pkg, repoInterface);
    this.testRecursiveRemove(repo, pkg, correspondenceInstance, repo2pkg);
    Correspondence _createRepo2PkgCorrespondence = this.createRepo2PkgCorrespondence(repo, pkg, correspondenceInstance);
    repo2pkg = _createRepo2PkgCorrespondence;
    this.testCreateRepo2PkgCorrespondenceAndUpdateTUID(repo, pkg, correspondenceInstance, repo2pkg);
    correspondenceInstance.removeCorrespondencesOfEObjectAndChildrenOnBothSides(pkg);
    this.testCorrespondencePersistence(vsum, repo, pkg, correspondenceInstance);
  }
  
  @Test
  public void testCorrespondenceUpdate() {
    final VSUMImpl vsum = this.testMetaRepositoryAndVSUMCreation();
    final Callable<Void> _function = new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        String _pCMInstanceUri = CorrespondenceTest.this.getPCMInstanceUri();
        Repository repo = CorrespondenceTest.this.<Repository>testLoadObject(vsum, _pCMInstanceUri, Repository.class);
        String _uMLInstanceURI = CorrespondenceTest.this.getUMLInstanceURI();
        UPackage pkg = CorrespondenceTest.this.<UPackage>testLoadObject(vsum, _uMLInstanceURI, UPackage.class);
        InternalCorrespondenceInstance correspondenceInstance = CorrespondenceTest.this.testCorrespondenceInstanceCreation(vsum);
        CorrespondenceInstanceUtil.createAndAddCorrespondence(correspondenceInstance, repo, pkg);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Before we remove the pkg from the resource it has the tuid \'");
        TUID _calculateTUIDFromEObject = correspondenceInstance.calculateTUIDFromEObject(pkg);
        _builder.append(_calculateTUIDFromEObject, "");
        _builder.append("\'.");
        CorrespondenceTest.LOGGER.trace(_builder);
        CorrespondenceTest.this.removePkgFromFileAndUpdateCorrespondence(pkg, correspondenceInstance);
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("After removing the pkg from the resource it has the tuid \'");
        TUID _calculateTUIDFromEObject_1 = correspondenceInstance.calculateTUIDFromEObject(pkg);
        _builder_1.append(_calculateTUIDFromEObject_1, "");
        _builder_1.append("\'.");
        CorrespondenceTest.LOGGER.trace(_builder_1);
        CorrespondenceTest.this.saveUPackageInNewFileAndUpdateCorrespondence(vsum, pkg, correspondenceInstance);
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("After adding the pkg to the new resource it has the tuid \'");
        TUID _calculateTUIDFromEObject_2 = correspondenceInstance.calculateTUIDFromEObject(pkg);
        _builder_2.append(_calculateTUIDFromEObject_2, "");
        _builder_2.append("\'.");
        CorrespondenceTest.LOGGER.trace(_builder_2);
        CorrespondenceTest.this.assertRepositoryCorrespondences(repo, correspondenceInstance);
        return null;
      }
    };
    EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(_function, vsum);
  }
  
  @Test
  public void testMoveRootEObjectBetweenResource() {
    final VSUMImpl vsum = this.testMetaRepositoryAndVSUMCreation();
    final Callable<Void> _function = new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        String _pCMInstanceUri = CorrespondenceTest.this.getPCMInstanceUri();
        Repository repo = CorrespondenceTest.this.<Repository>testLoadObject(vsum, _pCMInstanceUri, Repository.class);
        String _uMLInstanceURI = CorrespondenceTest.this.getUMLInstanceURI();
        UPackage pkg = CorrespondenceTest.this.<UPackage>testLoadObject(vsum, _uMLInstanceURI, UPackage.class);
        InternalCorrespondenceInstance correspondenceInstance = CorrespondenceTest.this.testCorrespondenceInstanceCreation(vsum);
        CorrespondenceInstanceUtil.createAndAddCorrespondence(correspondenceInstance, repo, pkg);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Before we remove the pkg from the resource it has the tuid \'");
        TUID _calculateTUIDFromEObject = correspondenceInstance.calculateTUIDFromEObject(pkg);
        _builder.append(_calculateTUIDFromEObject, "");
        _builder.append("\'.");
        CorrespondenceTest.LOGGER.trace(_builder);
        String _tmpUMLInstanceURI = CorrespondenceTest.this.getTmpUMLInstanceURI();
        CorrespondenceTest.this.moveUMLPackageTo(pkg, _tmpUMLInstanceURI, vsum, correspondenceInstance);
        String _newUMLInstanceURI = CorrespondenceTest.this.getNewUMLInstanceURI();
        CorrespondenceTest.this.moveUMLPackageTo(pkg, _newUMLInstanceURI, vsum, correspondenceInstance);
        CorrespondenceTest.this.assertRepositoryCorrespondences(repo, correspondenceInstance);
        return null;
      }
    };
    EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(_function, vsum);
  }
  
  private void assertRepositoryCorrespondences(final Repository repo, final InternalCorrespondenceInstance correspondenceInstance) {
    List<EObject> _list = CollectionBridge.<EObject>toList(repo);
    Set<Correspondence> correspondences = correspondenceInstance.getCorrespondences(_list);
    int _size = correspondences.size();
    Assert.assertEquals("Only one correspondence is expected for the repository.", 1, _size);
    for (final Correspondence correspondence : correspondences) {
      {
        Assert.assertTrue("Correspondence is not from the type EObjectCorrespondence", 
          (correspondence instanceof Correspondence));
        Correspondence eoc = correspondence;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("EObject with TUID: ");
        TUID _elementATUID = eoc.getElementATUID();
        _builder.append(_elementATUID, "");
        _builder.append(" corresponds to EObject with TUID: ");
        TUID _elementBTUID = eoc.getElementBTUID();
        _builder.append(_elementBTUID, "");
        CorrespondenceTest.LOGGER.info(_builder);
        TUID _elementATUID_1 = eoc.getElementATUID();
        EObject a = correspondenceInstance.resolveEObjectFromTUID(_elementATUID_1);
        TUID _elementBTUID_1 = eoc.getElementBTUID();
        EObject b = correspondenceInstance.resolveEObjectFromTUID(_elementBTUID_1);
        Assert.assertNotNull("Left Object is null", a);
        Assert.assertNotNull("Right Object is null", b);
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("A: ");
        _builder_1.append(a, "");
        _builder_1.append(" corresponds to B: ");
        _builder_1.append(b, "");
        CorrespondenceTest.LOGGER.info(_builder_1);
      }
    }
  }
  
  private void moveUMLPackageTo(final UPackage pkg, final String string, final VSUMImpl vsum, final InternalCorrespondenceInstance correspondenceInstance) {
    this.saveUPackageInNewFileAndUpdateCorrespondence(vsum, pkg, correspondenceInstance);
  }
  
  private void saveUPackageInNewFileAndUpdateCorrespondence(final VSUMImpl vsum, final UPackage pkg, final InternalCorrespondenceInstance correspondenceInstance) {
    TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(pkg);
    String _newUMLInstanceURI = this.getNewUMLInstanceURI();
    VURI newVURI = VURI.getInstance(_newUMLInstanceURI);
    vsum.saveModelInstanceOriginalWithEObjectAsOnlyContent(newVURI, pkg, oldTUID);
    correspondenceInstance.updateTUID(oldTUID, pkg);
  }
  
  private String getNewUMLInstanceURI() {
    StringConcatenation _builder = new StringConcatenation();
    String _currentProjectModelFolder = this.getCurrentProjectModelFolder();
    _builder.append(_currentProjectModelFolder, "");
    _builder.append("MyNewUML.uml_mockup");
    return _builder.toString();
  }
  
  private String getTmpUMLInstanceURI() {
    StringConcatenation _builder = new StringConcatenation();
    String _currentProjectFolderName = this.getCurrentProjectFolderName();
    _builder.append(_currentProjectFolderName, "");
    _builder.append("MyTmpUML.uml_mockup");
    return _builder.toString();
  }
  
  private void removePkgFromFileAndUpdateCorrespondence(final UPackage pkg, final InternalCorrespondenceInstance correspondenceInstance) {
    TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(pkg);
    EcoreUtil.remove(pkg);
    correspondenceInstance.updateTUID(oldTUID, pkg);
  }
  
  private void testCorrespondencePersistence(final VSUMImpl vsum, final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp) {
    abstract class __CorrespondenceTest_1 implements MIRMappingRealization {
      long serialVersionUID;
    }
    
    Correspondence repo2pkg = this.createRepo2PkgCorrespondence(repo, pkg, corresp);
    Assert.assertNotNull("Correspondence instance is null", corresp);
    if ((corresp instanceof MappedCorrespondenceInstance)) {
      MIRMappingRealization mapping = new __CorrespondenceTest_1() {
        {
          serialVersionUID = 1L;
        }
        @Override
        public String getMappingID() {
          return null;
        }
        
        @Override
        public TransformationResult applyEChange(final EChange eChange, final Blackboard blackboard) {
          return null;
        }
      };
      ((MappedCorrespondenceInstance) corresp).registerMappingForCorrespondence(repo2pkg, mapping);
    }
    String _pCMInstanceUri = this.getPCMInstanceUri();
    VURI pcmVURI = VURI.getInstance(_pCMInstanceUri);
    vsum.saveExistingModelInstanceOriginal(pcmVURI);
    VSUMImpl vsum2 = this.testMetaRepositoryVSUMAndModelInstancesCreation();
    String _pCMInstanceUri_1 = this.getPCMInstanceUri();
    Repository repo2 = this.<Repository>testLoadObject(vsum2, _pCMInstanceUri_1, Repository.class);
    String _uMLInstanceURI = this.getUMLInstanceURI();
    UPackage pkg2 = this.<UPackage>testLoadObject(vsum2, _uMLInstanceURI, UPackage.class);
    InternalCorrespondenceInstance corresp2 = this.testCorrespondenceInstanceCreation(vsum2);
    boolean _hasCorrespondences = corresp2.hasCorrespondences();
    Assert.assertTrue(_hasCorrespondences);
    List<EObject> _list = CollectionBridge.<EObject>toList(repo2);
    List<EObject> _list_1 = CollectionBridge.<EObject>toList(pkg2);
    Correspondence repo2pkg2 = corresp2.claimUniqueCorrespondence(_list, _list_1);
    this.testAllClaimersAndGettersForEObjectCorrespondences(repo2, pkg2, corresp2, repo2pkg2);
  }
  
  private <T extends EObject> T testLoadObject(final VSUMImpl vsum, final String uri, final Class<T> clazz) {
    VURI vURI = VURI.getInstance(uri);
    ModelInstance instance = vsum.getAndLoadModelInstanceOriginal(vURI);
    T obj = instance.<T>getUniqueRootEObjectIfCorrectlyTyped(clazz);
    return obj;
  }
  
  private InternalCorrespondenceInstance testCorrespondenceInstanceCreation(final VSUMImpl vsum) {
    VURI pcmMMVURI = VURI.getInstance(MetaRepositoryTest.PCM_MM_URI);
    VURI umlMMVURI = VURI.getInstance(MetaRepositoryTest.UML_MM_URI);
    InternalCorrespondenceInstance corresp = vsum.getCorrespondenceInstanceOriginal(pcmMMVURI, umlMMVURI);
    Assert.assertNotNull(corresp);
    return corresp;
  }
  
  private Correspondence createRepo2PkgCorrespondence(final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp) {
    Correspondence repo2pkg = CorrespondenceInstanceUtil.createAndAddCorrespondence(corresp, repo, pkg);
    return repo2pkg;
  }
  
  private void testAllClaimersAndGettersForEObjectCorrespondences(final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp, final Correspondence repo2pkg) {
    Correspondence uniqueRepoCorrespondence = CorrespondenceInstanceUtil.claimUniqueCorrespondence(corresp, repo);
    Assert.assertEquals(uniqueRepoCorrespondence, repo2pkg);
    Correspondence uniquePkgCorrespondence = CorrespondenceInstanceUtil.claimUniqueCorrespondence(corresp, pkg);
    Assert.assertEquals(uniquePkgCorrespondence, repo2pkg);
    Set<EObject> _correspondingEObjects = CorrespondenceInstanceUtil.getCorrespondingEObjects(corresp, repo);
    EObject correspForRepo = CollectionBridge.<EObject>claimOne(_correspondingEObjects);
    Assert.assertEquals(correspForRepo, pkg);
    Set<EObject> _correspondingEObjects_1 = CorrespondenceInstanceUtil.getCorrespondingEObjects(corresp, pkg);
    EObject correspForPkg = CollectionBridge.<EObject>claimOne(_correspondingEObjects_1);
    Assert.assertEquals(correspForPkg, repo);
    List<Interface> interfaces = repo.getInterfaces();
    int _size = interfaces.size();
    Assert.assertEquals(_size, 1);
    Interface iface = interfaces.get(0);
    List<EObject> _list = CollectionBridge.<EObject>toList(iface);
    Set<Correspondence> _correspondences = corresp.getCorrespondences(_list);
    Correspondence correspForIface = CollectionBridge.<Correspondence>claimNotMany(_correspondences);
    Assert.assertNull(correspForIface);
    List<EObject> _list_1 = CollectionBridge.<EObject>toList(repo);
    Set<Correspondence> allRepoCorrespondences = corresp.getCorrespondences(_list_1);
    int _size_1 = allRepoCorrespondences.size();
    Assert.assertEquals(_size_1, 1);
    boolean _contains = allRepoCorrespondences.contains(repo2pkg);
    Assert.assertTrue(_contains);
    List<EObject> _list_2 = CollectionBridge.<EObject>toList(pkg);
    Set<Correspondence> allPkgCorrespondences = corresp.getCorrespondences(_list_2);
    int _size_2 = allPkgCorrespondences.size();
    Assert.assertEquals(_size_2, 1);
    boolean _contains_1 = allPkgCorrespondences.contains(repo2pkg);
    Assert.assertTrue(_contains_1);
    Set<Repository> allRepoTypeCorresp = CorrespondenceInstanceUtil.<Repository>getAllEObjectsOfTypeInCorrespondences(corresp, Repository.class);
    boolean _contains_2 = allRepoTypeCorresp.contains(repo);
    Assert.assertTrue(_contains_2);
    Set<UPackage> allPkgTypeCorresp = CorrespondenceInstanceUtil.<UPackage>getAllEObjectsOfTypeInCorrespondences(corresp, UPackage.class);
    boolean _contains_3 = allPkgTypeCorresp.contains(pkg);
    Assert.assertTrue(_contains_3);
    Set<EObject> allCorrespForRepo = CorrespondenceInstanceUtil.getCorrespondingEObjects(corresp, repo);
    int _size_3 = allCorrespForRepo.size();
    Assert.assertEquals(_size_3, 1);
    boolean _contains_4 = allCorrespForRepo.contains(pkg);
    Assert.assertTrue(_contains_4);
    Set<EObject> allCorrespForPkg = CorrespondenceInstanceUtil.getCorrespondingEObjects(corresp, pkg);
    int _size_4 = allCorrespForPkg.size();
    Assert.assertEquals(_size_4, 1);
    boolean _contains_5 = allCorrespForPkg.contains(repo);
    Assert.assertTrue(_contains_5);
  }
  
  private Interface testHasCorrespondences(final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp) {
    List<EObject> _list = CollectionBridge.<EObject>toList(repo);
    boolean _hasCorrespondences = corresp.hasCorrespondences(_list);
    Assert.assertTrue(_hasCorrespondences);
    List<EObject> _list_1 = CollectionBridge.<EObject>toList(pkg);
    boolean _hasCorrespondences_1 = corresp.hasCorrespondences(_list_1);
    Assert.assertTrue(_hasCorrespondences_1);
    List<Interface> repoInterfaces = repo.getInterfaces();
    int _size = repoInterfaces.size();
    Assert.assertEquals(_size, 1);
    Interface repoInterface = repoInterfaces.get(0);
    List<EObject> _list_2 = CollectionBridge.<EObject>toList(repoInterface);
    boolean _hasCorrespondences_2 = corresp.hasCorrespondences(_list_2);
    boolean _not = (!_hasCorrespondences_2);
    Assert.assertTrue(_not);
    return repoInterface;
  }
  
  private void testSimpleRemove(final UPackage pkg, final CorrespondenceInstance corresp, final Correspondence repo2pkg, final Interface repoInterface) {
    List<uml_mockup.Interface> pkgInterfaces = pkg.getInterfaces();
    int _size = pkgInterfaces.size();
    Assert.assertEquals(_size, 1);
    uml_mockup.Interface pkgInterface = pkgInterfaces.get(0);
    CorrespondenceInstanceUtil.createAndAddCorrespondence(corresp, repoInterface, pkgInterface);
    corresp.removeCorrespondencesOfEObjectAndChildrenOnBothSides(repoInterface);
    List<EObject> _list = CollectionBridge.<EObject>toList(repoInterface);
    Set<Correspondence> repoInterfaceCorresp = corresp.getCorrespondences(_list);
    boolean _isEmpty = repoInterfaceCorresp.isEmpty();
    Assert.assertTrue(_isEmpty);
    List<EObject> _list_1 = CollectionBridge.<EObject>toList(pkgInterface);
    Set<Correspondence> pkgInterfaceCorresp = corresp.getCorrespondences(_list_1);
    boolean _isEmpty_1 = pkgInterfaceCorresp.isEmpty();
    Assert.assertTrue(_isEmpty_1);
    Set<EObject> correspForRepoInterface = CorrespondenceInstanceUtil.getCorrespondingEObjects(corresp, repoInterface);
    boolean _isEmpty_2 = correspForRepoInterface.isEmpty();
    Assert.assertTrue(_isEmpty_2);
    Set<EObject> correspForPkgInterface = CorrespondenceInstanceUtil.getCorrespondingEObjects(corresp, pkgInterface);
    boolean _isEmpty_3 = correspForPkgInterface.isEmpty();
    Assert.assertTrue(_isEmpty_3);
    Set<Interface> correspForRepoInterfaceType = CorrespondenceInstanceUtil.<Interface>getAllEObjectsOfTypeInCorrespondences(corresp, Interface.class);
    boolean _isEmpty_4 = correspForRepoInterfaceType.isEmpty();
    Assert.assertTrue(_isEmpty_4);
    Set<uml_mockup.Interface> correspForPkgInterfaceType = CorrespondenceInstanceUtil.<uml_mockup.Interface>getAllEObjectsOfTypeInCorrespondences(corresp, uml_mockup.Interface.class);
    boolean _isEmpty_5 = correspForPkgInterfaceType.isEmpty();
    Assert.assertTrue(_isEmpty_5);
  }
  
  private void testRecursiveRemove(final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp, final Correspondence repo2pkg) {
    corresp.removeCorrespondencesOfEObjectAndChildrenOnBothSides(repo2pkg);
    List<EObject> _list = CollectionBridge.<EObject>toList(repo);
    Set<Correspondence> repoCorresp = corresp.getCorrespondences(_list);
    boolean _isEmpty = repoCorresp.isEmpty();
    Assert.assertTrue(_isEmpty);
    List<EObject> _list_1 = CollectionBridge.<EObject>toList(pkg);
    Set<Correspondence> pkgCorresp = corresp.getCorrespondences(_list_1);
    boolean _isEmpty_1 = pkgCorresp.isEmpty();
    Assert.assertTrue(_isEmpty_1);
    Set<EObject> correspForRepo = CorrespondenceInstanceUtil.getCorrespondingEObjects(corresp, repo);
    boolean _isEmpty_2 = correspForRepo.isEmpty();
    Assert.assertTrue(_isEmpty_2);
    Set<EObject> correspForPkg = CorrespondenceInstanceUtil.getCorrespondingEObjects(corresp, pkg);
    boolean _isEmpty_3 = correspForPkg.isEmpty();
    Assert.assertTrue(_isEmpty_3);
    Set<Repository> correspForRepoType = CorrespondenceInstanceUtil.<Repository>getAllEObjectsOfTypeInCorrespondences(corresp, Repository.class);
    boolean _isEmpty_4 = correspForRepoType.isEmpty();
    Assert.assertTrue(_isEmpty_4);
    Set<UPackage> correspForPkgType = CorrespondenceInstanceUtil.<UPackage>getAllEObjectsOfTypeInCorrespondences(corresp, UPackage.class);
    boolean _isEmpty_5 = correspForPkgType.isEmpty();
    Assert.assertTrue(_isEmpty_5);
  }
  
  private void testCreateRepo2PkgCorrespondenceAndUpdateTUID(final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp, final Correspondence repo2pkg) {
    Repository newRepo = Pcm_mockupFactory.eINSTANCE.createRepository();
    corresp.updateTUID(repo, newRepo);
    List<EObject> _list = CollectionBridge.<EObject>toList(repo);
    Set<Correspondence> repoCorresp = corresp.getCorrespondences(_list);
    boolean _isEmpty = repoCorresp.isEmpty();
    Assert.assertTrue(_isEmpty);
    Correspondence uniqueNewRepoCorrespondence = CorrespondenceInstanceUtil.claimUniqueCorrespondence(corresp, newRepo);
    Assert.assertEquals(uniqueNewRepoCorrespondence, repo2pkg);
    Correspondence uniquePkgCorrespondence = CorrespondenceInstanceUtil.claimUniqueCorrespondence(corresp, pkg);
    Assert.assertEquals(uniquePkgCorrespondence, repo2pkg);
    Set<EObject> _correspondingEObjects = CorrespondenceInstanceUtil.getCorrespondingEObjects(corresp, newRepo);
    EObject correspForNewRepo = CollectionBridge.<EObject>claimOne(_correspondingEObjects);
    Assert.assertEquals(correspForNewRepo, pkg);
    Set<EObject> _correspondingEObjects_1 = CorrespondenceInstanceUtil.getCorrespondingEObjects(corresp, pkg);
    EObject correspForPkg = CollectionBridge.<EObject>claimOne(_correspondingEObjects_1);
    Assert.assertEquals(correspForPkg, newRepo);
  }
}
