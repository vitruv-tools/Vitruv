package tools.vitruvius.applications.pcmjava.builder;

import org.eclipse.ui.IStartup;

import tools.vitruvius.domains.emf.util.BuildProjects;

/**
 * {@link BuildOnEclipseStartup} issues an incremental build of all open projects just after Eclipse
 * started. This ensures that the respective builder objects do exist (and install EMF editor
 * monitors) before the user starts editing files.
 */
public class BuildOnEclipseStartup implements IStartup {
    @Override
    public void earlyStartup() {
        BuildProjects.issueIncrementalBuildForAllProjectsWithBuilder(PCMJavaBuilder.BUILDER_ID);
    }
}
