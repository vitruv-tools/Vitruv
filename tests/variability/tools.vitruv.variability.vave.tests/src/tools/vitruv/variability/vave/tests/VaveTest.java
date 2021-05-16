package tools.vitruv.variability.vave.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.variability.vave.Vave;
import tools.vitruv.variability.vave.impl.VaveImpl;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class VaveTest {
	@Test
	public void testVaveModelCreation() {
		Set<VitruvDomain> domains = new HashSet<>();
		final VitruvDomainRepositoryImpl targetDomains = new VitruvDomainRepositoryImpl(domains);

		Vave vave = new VaveImpl(targetDomains);

		VirtualModel vsum = vave.externalizeProduct(Paths.get("testStorageFolder"));

		assertNotNull(vsum);
	}
}
