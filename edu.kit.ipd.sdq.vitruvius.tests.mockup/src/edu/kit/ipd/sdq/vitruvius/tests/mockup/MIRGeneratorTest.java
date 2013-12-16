package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.design.mir.generator.MIRGenerator;

public class MIRGeneratorTest {

	@Test
	public void test() {
		MIRGenerator mirGenerator = mock(MIRGenerator.class);
		when(mirGenerator.generateSyncTransformations(null, null)).thenReturn((Collection) Collections.emptyList());
		fail("Not yet implemented");
	}

}
