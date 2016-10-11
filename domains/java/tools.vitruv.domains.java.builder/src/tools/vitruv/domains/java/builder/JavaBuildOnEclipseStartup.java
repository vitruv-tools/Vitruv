package tools.vitruv.domains.java.builder;

import org.eclipse.ui.IStartup;

import tools.vitruv.domains.emf.util.BuildProjects;

/**
 * {@link JavaBuildOnEclipseStartup} issues an incremental build of all open projects just after Eclipse
 * started. This ensures that the respective builder objects do exist (and install EMF editor
 * monitors) before the user starts editing files.
 */
public class JavaBuildOnEclipseStartup implements IStartup {
    @Override
    public void earlyStartup() {
        BuildProjects.issueIncrementalBuildForAllProjectsWithBuilder(JavaBuilder.BUILDER_ID);
    }
}
