package tools.vitruv.framework.domains.ui.builder;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil.hasBuilder;

public class TestBuilderEnabled extends PropertyTester {

    private static final String IS_ENABLED = "isEnabled";
    private final String builderId;

    protected TestBuilderEnabled(final String builderId) {
        this.builderId = builderId;
    }

    @Override
    public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {

        if (IS_ENABLED.equals(property)) {
            final IProject project = Platform.getAdapterManager().getAdapter(receiver, IProject.class);

            if (project != null) {
                return hasBuilder(project, builderId);
            }
        }

        return false;
    }
    
}