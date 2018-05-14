package tools.vitruv.extensions.constructionsimulation.util;

import java.io.File;
import java.util.Collections;

import org.eclipse.core.resources.ResourcesPlugin;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.userinteraction.impl.UserInteractorImpl;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import tools.vitruv.testutils.util.TestUtil;

public class IntegrationUtil {

    private IntegrationUtil() {
    }

    public static InternalVirtualModel createVsum(final Iterable<VitruvDomain> metamodels) {
    	File projectFolder = ResourcesPlugin.getWorkspace().getRoot().getLocation().append("/vitruvius.meta").toFile();
    	return TestUtil.createVirtualModel(projectFolder, true, metamodels, Collections.emptyList(), new UserInteractorImpl());
    }

}
