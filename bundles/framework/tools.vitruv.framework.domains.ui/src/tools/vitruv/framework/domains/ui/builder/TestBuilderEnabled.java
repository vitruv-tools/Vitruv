package tools.vitruv.framework.domains.ui.builder;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;

import tools.vitruv.framework.domains.VitruvProjectBuilderApplicator;

import static tools.vitruv.framework.util.ProjectBuildUtils.hasBuilder;

public class TestBuilderEnabled extends PropertyTester {

    private static final String IS_ENABLED = "isEnabled";
    private final VitruvProjectBuilderApplicator builderApplicator;

    protected TestBuilderEnabled(final VitruvProjectBuilderApplicator builderApplicator) {
        this.builderApplicator = builderApplicator;
    }

    @Override
    public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {

        if (IS_ENABLED.equals(property)) {
            final IProject project = Platform.getAdapterManager().getAdapter(receiver, IProject.class);

            if (project != null) {
                return hasBuilder(project, builderApplicator.getBuilderId());
            }
        }

        return false;
    }
}