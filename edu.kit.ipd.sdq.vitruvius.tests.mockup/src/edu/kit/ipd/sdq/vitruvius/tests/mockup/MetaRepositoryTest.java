package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProjectInput;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ViewType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableConcatMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;

public class MetaRepositoryTest {

    @Test
    public void testAll() {

        testAddMapping("MockupProject/metamodels/pcm_mockup.ecore", "MockupProject/metamodels/uml_mockup.ecore");

        testAddViewType("MockupProject/metamodels/pcm_mockup.ecore", "MockupProject/metamodels/uml_mockup.ecore",
                " MockupProject/viewtypes/pcm__uml_mockup.ecore");

        testGetProjectInput("MockupProject/metamodels/pcm_mockup.ecore", "MockupProject/metamodels/uml_mockup.ecore",
                " MockupProject/viewtypes/pcm__uml_mockup.ecore");

        // generiere VSUM plugins (jetzt erst mal hart verdrahtet)

        // TODO KEEP ON WORKING HERE
    }

    public MetaRepositoryImpl testMetaRepository() {
        MetaRepositoryImpl metaRepository = new MetaRepositoryImpl();
        assertNotNull(metaRepository);
        return metaRepository;
    }

    public Metamodel testAddMetamodel(final MetaRepositoryImpl metaRepository, final VURI uri) {
        Metamodel mm = new Metamodel(uri);
        metaRepository.addMetamodel(mm);
        return mm;
    }

    public void testAddMapping(final String uri1String, final String uri2String) {
        MetaRepositoryImpl metaRepository = testMetaRepository();
        testAddMapping(metaRepository, uri1String, uri2String);
    }

    public Pair<VURI, VURI> testAddMapping(final MetaRepositoryImpl metaRepository, final String uri1String,
            final String uri2String) {
        VURI uri1 = VURI.getInstance(uri1String);
        Metamodel mm1 = testAddMetamodel(metaRepository, uri1);

        VURI uri2 = VURI.getInstance(uri2String);
        Metamodel mm2 = testAddMetamodel(metaRepository, uri2);

        Mapping mapping = new Mapping(mm1, mm2);
        metaRepository.addMapping(mapping);

        Mapping mapping2 = metaRepository.getMapping(uri1, uri2);
        assertEquals(mapping, mapping2);

        return new Pair<VURI, VURI>(uri1, uri2);
    }

    public void testAddViewType(final String uri1String, final String uri2String, final String viewTypeURIString) {
        MetaRepositoryImpl metaRepository = testMetaRepository();

        VURI uri1 = VURI.getInstance(uri1String);
        testAddMetamodel(metaRepository, uri1);

        VURI uri2 = VURI.getInstance(uri2String);
        testAddMetamodel(metaRepository, uri2);

        testAddViewType(metaRepository, uri1, uri2, viewTypeURIString);
    }

    public VURI testAddViewType(final MetaRepositoryImpl metaRepository, final VURI uri1, final VURI uri2,
            final String viewTypeURIString) {

        VURI viewTypeURI = VURI.getInstance(viewTypeURIString);
        ViewType vt = new ViewType(viewTypeURI, uri1, uri2);
        metaRepository.addViewType(vt);

        ViewType vt2 = metaRepository.getViewType(viewTypeURI);
        assertEquals(vt, vt2);

        return viewTypeURI;
    }

    private void testGetProjectInput(final String uri1String, final String uri2String, final String viewTypeURIString) {
        MetaRepositoryImpl metaRepository = testMetaRepository();

        Pair<VURI, VURI> mmURIs = testAddMapping(metaRepository, uri1String, uri2String);

        VURI uri1 = mmURIs.getFirst();
        VURI uri2 = mmURIs.getSecond();

        VURI vtURI = testAddViewType(metaRepository, uri1, uri2, viewTypeURIString);

        ProjectInput projectInput = metaRepository.getProjectInput();
        assertMetamodels(uri1, metaRepository, projectInput);
        assertMetamodels(uri2, metaRepository, projectInput);
        assertViewType(vtURI, metaRepository, projectInput);
        assertMapping(uri1, uri2, metaRepository, projectInput);
    }

    private void assertMetamodels(final VURI uri1, final MetaRepositoryImpl metaRepository,
            final ProjectInput projectInput) {
        ClaimableMap<VURI, Metamodel> uri2MetamodelMap = projectInput.getUri2MetamodelMap();
        Metamodel mm11 = uri2MetamodelMap.get(uri1);
        Metamodel mm12 = metaRepository.getMetamodel(uri1);
        assertEquals(mm11, mm12);
    }

    private void assertViewType(final VURI vtURI, final MetaRepositoryImpl metaRepository,
            final ProjectInput projectInput) {
        ClaimableMap<VURI, ViewType> uri2ViewTypeMap = projectInput.getUri2ViewTypeMap();
        ViewType viewType1 = uri2ViewTypeMap.get(vtURI);
        ViewType viewType2 = metaRepository.getViewType(vtURI);
        assertEquals(viewType1, viewType2);
    }

    private void assertMapping(final VURI uri1, final VURI uri2, final MetaRepositoryImpl metaRepository,
            final ProjectInput projectInput) {
        ClaimableConcatMap<VURI, Mapping> uris2MappingMap = projectInput.getUris2MappingMap();
        Mapping mapping1 = uris2MappingMap.get(uri1, uri2);
        Mapping mapping2 = metaRepository.getMapping(uri1, uri2);
        assertEquals(mapping1, mapping2);
    }
}
