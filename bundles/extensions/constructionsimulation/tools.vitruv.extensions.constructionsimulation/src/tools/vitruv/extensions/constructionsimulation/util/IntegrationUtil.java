package tools.vitruv.extensions.constructionsimulation.util;

import java.nio.file.Path;

import org.eclipse.core.resources.ResourcesPlugin;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class IntegrationUtil {
	private IntegrationUtil() {
	}

	public static InternalVirtualModel createVsum(Path workspace, final Iterable<VitruvDomain> domains) {
		return new VirtualModelBuilder()
			.withStorageFolder(ResourcesPlugin.getWorkspace().getRoot().getLocation().append("/vitruvius.meta").toFile())
			.withUserInteractorForResultProvider(UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null))
			.withDomains(domains)
			.buildAndInitialize();
	}
}
