package tools.vitruv.framework.vsum.branch;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.VirtualModel;

/** Tests for {@link PostCheckoutHandler}. */
class PostCheckoutHandlerTest {

    @Test
    @DisplayName("handler calls reload on virtual model when branch switches")
    void handlerTriggersReload() throws Exception {
        var virtualModel = mock(VirtualModel.class);
        var handler = new PostCheckoutHandler(virtualModel);

        handler.onBranchSwitch("main", "feature-login");

        verify(virtualModel).reload();
    }

    @Test
    @DisplayName("handler wraps reload exceptions in BranchOperationException")
    void handlerWrapsReloadException() {
        var virtualModel = mock(VirtualModel.class);
        doThrow(new IllegalStateException("Reload failed"))
                .when(virtualModel).reload();

        var handler = new PostCheckoutHandler(virtualModel);

        assertThrows(
                BranchOperationException.class,
                () -> handler.onBranchSwitch("main", "feature-login"));
    }

    @Test
    @DisplayName("handler rejects null branch names")
    void handlerRejectsNullBranchNames() {
        var virtualModel = mock(VirtualModel.class);
        var handler = new PostCheckoutHandler(virtualModel);

        assertThrows(
                NullPointerException.class,
                () -> handler.onBranchSwitch(null, "feature-login"));

        assertThrows(
                NullPointerException.class,
                () -> handler.onBranchSwitch("main", null));
    }
}