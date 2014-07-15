package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder;

import org.eclipse.ui.IStartup;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.util.BuildProjects;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaNamespace;

/**
 * {@link BuildOnEclipseStartup} issues an incremental build of all open projects just after Eclipse
 * started. This ensures that the respective builder objects do exist (and install EMF editor
 * monitors) before the user starts editing files.
 */
public class BuildOnEclipseStartup implements IStartup {
    @Override
    public void earlyStartup() {
        BuildProjects.issueIncrementalBuildForAllProjectsWithBuilder(PCMJavaNamespace.BUILDER_ID);
    }
}
