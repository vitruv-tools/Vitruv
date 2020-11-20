package tools.vitruv.extensions.constructionsimulation.util;

import static java.util.Collections.emptyList;

import java.nio.file.Path;

import org.eclipse.core.resources.ResourcesPlugin;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import tools.vitruv.testutils.util.TestSetup;

public class IntegrationUtil {

	private IntegrationUtil() {
	}

	public static InternalVirtualModel createVsum(Path workspace, final Iterable<VitruvDomain> metamodels) {
		var projectPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().append("/vitruvius.meta").toFile().toPath();
		return TestSetup.createVirtualModel(workspace, projectPath, metamodels, emptyList(), UserInteractionFactory.instance.createDialogUserInteractor());
	}

}
