package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;

public class MetaRepositoryTest {
	
	@Test
	public void testAll() {
		MetaRepositoryImpl metaRepository = testMetaRepository();
		
		testAddMapping(metaRepository, "MockupProject/metamodel/pcm_mockup.ecore", "MockupProject/metamodel/uml_mockup.ecore");
		
		
		
		// generiere VSUM plugins (jetzt erst mal hart verdrahtet)
		
		// TODO KEEP ON WORKING HERE
	}

	public MetaRepositoryImpl testMetaRepository() {
		MetaRepositoryImpl metaRepository = new MetaRepositoryImpl();
		assertNotNull(metaRepository);
		return metaRepository;
	}
	
	public void testAddMapping(MetaRepositoryImpl metaRepository, String uri1String, String uri2String) {
		VURI uri1 = new VURI(uri1String);
		testAddMetamodel(metaRepository, uri1);
		
		VURI uri2 = new VURI(uri2String);
		testAddMetamodel(metaRepository, uri2);
		
		Mapping mapping = new Mapping(uri1, uri2);
		metaRepository.addMapping(mapping);
		assertTrue(true);
	}

	public void testAddMetamodel(MetaRepositoryImpl metaRepository, VURI uri) {
		Metamodel mm1 = new Metamodel(uri);
		metaRepository.addMetamodel(mm1);
		
		assertEquals(metaRepository.getMetamodel(uri), mm1);
	}
}
