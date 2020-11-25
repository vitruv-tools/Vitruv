package tools.vitruv.extensions.constructionsimulation.util;

import java.nio.file.Path;

import org.eclipse.core.resources.ResourcesPlugin;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import tools.vitruv.framework.vsum.VirtualModelConfiguration;
import tools.vitruv.framework.vsum.VirtualModelImpl;

public class IntegrationUtil {
	private IntegrationUtil() {
	}

	public static InternalVirtualModel createVsum(Path workspace, final Iterable<VitruvDomain> metamodels) {
		var projectPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().append("/vitruvius.meta").toFile();
		var interactionProvider = UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null);
		var userInteractor = UserInteractionFactory.instance.createUserInteractor(interactionProvider);
		var configuration = new VirtualModelConfiguration();
		metamodels.forEach(configuration::addMetamodel);
		return new VirtualModelImpl(projectPath, userInteractor, configuration);
	}
}
