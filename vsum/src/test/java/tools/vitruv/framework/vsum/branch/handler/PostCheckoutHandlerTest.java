package tools.vitruv.framework.vsum.branch.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PostCheckoutHandler}.
 *
 * <p>The VirtualModel is mocked in all tests because these tests focus on the handler's behavior: 
 * whether it delegates correctly to the model, how it reacts to failures, and whether it validates its inputs. 
 */
class PostCheckoutHandlerTest {

    /**
     * Verifies that a normal branch switch causes exactly one reload call on the VirtualModel.
     */
    @Test
    @DisplayName("triggers a VirtualModel reload when a branch switch is reported")
    void handlerTriggersReload() throws Exception {
        var virtualModel = mock(VirtualModel.class);
        var handler = new PostCheckoutHandler(virtualModel);

        handler.onBranchSwitch("master", "feature-vcs");

        // the VirtualModel must be told to reload exactly once so that its in-memory resources are replaced with the files now present on the new branch.
        verify(virtualModel).reload();
    }

    /**
     * Verifies that any exception thrown by the VirtualModel during reload is caught and re-thrown as a {@link BranchOperationException}.
     */
    @Test
    @DisplayName("wraps any VirtualModel reload exception in a BranchOperationException")
    void handlerWrapsReloadException() {
        var virtualModel = mock(VirtualModel.class);
        // configure the mock to throw an arbitrary runtime exception during reload to simulate a VirtualModel failure (for example, a resource that cannot be parsed).
        doThrow(new IllegalStateException("reload failed")).when(virtualModel).reload();
        var handler = new PostCheckoutHandler(virtualModel);

        // the handler must not let the internal exception escape unwrapped
        assertThrows(BranchOperationException.class, () -> handler.onBranchSwitch("master", "feature-vcs"));
    }

    /**
     * Verifies that null branch names are rejected before any model interaction takes place.
     * Both the old and new branch names are required so that the handler can produce a meaningful log message and exception message when something goes wrong.
     */
    @Test
    @DisplayName("rejects null values for both old and new branch names")
    void handlerRejectsNullBranchNames() {
        var virtualModel = mock(VirtualModel.class);
        var handler = new PostCheckoutHandler(virtualModel);

        assertThrows(NullPointerException.class, () -> handler.onBranchSwitch(null, "feature-vcs"), "null old branch name must be rejected");
        assertThrows(NullPointerException.class, () -> handler.onBranchSwitch("master", null), "null new branch name must be rejected");
    }

    /**
     * Verifies that constructing a handler with a null VirtualModel throws a {@link NullPointerException} immediately.
     */
    @Test
    @DisplayName("rejects a null VirtualModel at construction time")
    void constructorRejectsNullVirtualModel() {
        assertThrows(NullPointerException.class, () -> new PostCheckoutHandler(null), "null VirtualModel must be rejected at construction time");
    }
}