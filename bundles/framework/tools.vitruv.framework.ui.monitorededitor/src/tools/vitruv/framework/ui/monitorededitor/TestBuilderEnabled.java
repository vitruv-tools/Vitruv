package tools.vitruv.framework.ui.monitorededitor;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;

import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator;
import static tools.vitruv.framework.util.ProjectBuildUtils.hasBuilder;

public class TestBuilderEnabled extends PropertyTester {

    private static final String IS_ENABLED = "isEnabled";
    private final VitruviusProjectBuilderApplicator builderApplicator;

    protected TestBuilderEnabled(final VitruviusProjectBuilderApplicator builderApplicator) {
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