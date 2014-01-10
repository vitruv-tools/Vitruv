package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.*;

public class MetaRepositoryTest {

	@Test
	public void test() {
		MetaRepositoryImpl metaRepository = new MetaRepositoryImpl();
		Metamodel mm1 = new Metamodel(new URI("aproject/metamodels/mm1.ecore"));
		metaRepository.addMetamodel(mm1);
		Metamodel mm2 = new Metamodel(new URI("aproject/metamodels/mm2.ecore"));
		metaRepository.addMetamodel(mm1);
		// TODO KEEP ON WORKING HERE
	}

}
