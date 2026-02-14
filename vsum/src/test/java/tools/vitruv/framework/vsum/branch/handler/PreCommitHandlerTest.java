package tools.vitruv.framework.vsum.branch.handler;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PreCommitHandler}.
 *
 * <p>All tests use a mocked {@link InternalVirtualModel} so that the validation logic can be exercised in isolation 
 * without requiring a fully initialized Vitruvius runtime. 
 * The {@link #mockVsum(Resource...)} helper reduces boilerplate by wiring the correspondence model and UUID resolver stubs that every test requires.
 */
@SuppressWarnings("unchecked")
class PreCommitHandlerTest {
    
    /**
     * Verifies that a VirtualModel with no resources passes validation but produces a warning. 
     * An empty resource set is not an error (the model may be intentionally empty) but it is unusual enough to warrant a non-blocking notice.
     */
    @Test
    @DisplayName("Empty resource set passes validation with a warning")
    void emptyResourceSetPassesWithWarning() {
        var handler = new PreCommitHandler(mockVsum());

        var result = handler.validate();

        assertTrue(result.isValid(), "an empty resource set must not produce an error");
        // the implementation warns when no resources are found so the developer is aware.
        assertTrue(result.hasWarnings(), "an empty resource set must produce a warning about missing resources");
    }

    /**
     * Verifies that a loaded resource with no parse errors or warnings passes validation cleanly.
     */
    @Test
    @DisplayName("Loaded resource with no errors or warnings passes validation cleanly")
    void loadedResourceWithNoErrorsPasses() {
        var resource = mockCleanResource();
        var handler = new PreCommitHandler(mockVsum(resource));

        var result = handler.validate();

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    /**
     * Verifies that XMI parse errors reported by a resource cause validation to fail.
     * The error message from the diagnostic must appear in the result so the developer can identify which file contains the problem.
     */
    @Test
    @DisplayName("Resource with an XMI parse error fails validation")
    void resourceWithLoadErrorFailsValidation() {
        var resource = mock(Resource.class);
        when(resource.isLoaded()).thenReturn(true);
        when(resource.getErrors()).thenReturn(createErrorDiagnostics());
        when(resource.getWarnings()).thenReturn(new BasicEList<>());
        when(resource.getURI()).thenReturn(org.eclipse.emf.common.util.URI.createURI("models/User.xmi"));
        // getAllContents is not called when errors exist

        var handler = new PreCommitHandler(mockVsum(resource));
        var result = handler.validate();

        assertFalse(result.isValid(), "a resource with parse errors must fail validation");
        assertTrue(result.getErrors().get(0).contains("Malformed XMI"), "the parse error message must appear in the validation result");
    }

    /**
     * Verifies that resource warnings are surfaced as validation warnings without failing the overall result.
     * Warnings indicate non-blocking issues such as deprecated attributes.
     */
    @Test
    @DisplayName("Resource with a warning passes validation but reports the warning")
    void resourceWithWarningPassesWithWarning() {
        var resource = mock(Resource.class);
        when(resource.isLoaded()).thenReturn(true);
        when(resource.getErrors()).thenReturn(new BasicEList<>());
        when(resource.getWarnings()).thenReturn(createWarningDiagnostics());
        when(resource.getURI()).thenReturn(org.eclipse.emf.common.util.URI.createURI("models/User.xmi"));
        when(resource.getAllContents()).thenReturn(new EmptyTreeIterator());

        var handler = new PreCommitHandler(mockVsum(resource));
        var result = handler.validate();

        assertTrue(result.isValid(), "a resource warning must not fail validation");
        assertTrue(result.hasWarnings());
        assertTrue(result.getWarnings().get(0).contains("Deprecated"), "the warning message must appear in the validation result");
    }

    /**
     * Verifies that an unloaded resource is loaded during validation.
     * Resources must be loaded eagerly so that parse errors are detected before the commit is allowed.
     */
    @Test
    @DisplayName("Unloaded resource is loaded during validation")
    void unloadedResourceIsLoadedDuringValidation() throws IOException {
        var resource = mock(Resource.class);
        when(resource.isLoaded()).thenReturn(false);
        when(resource.getErrors()).thenReturn(new BasicEList<>());
        when(resource.getWarnings()).thenReturn(new BasicEList<>());
        when(resource.getAllContents()).thenReturn(new EmptyTreeIterator());

        var handler = new PreCommitHandler(mockVsum(resource));
        handler.validate();

        // the resource must have been loaded with an options map even if empty.
        verify(resource).load(any());
    }

    /**
     * Verifies that when multiple resources are present, all of them are inspected for errors.
     * If only the first resource were checked, a broken second resource would be silently committed.
     */
    @Test
    @DisplayName("All resources are validated when multiple are present")
    void allResourcesAreValidatedWhenMultipleArePresent() {
        var resource1 = mockCleanResource();
        var resource2 = mockCleanResource();

        var handler = new PreCommitHandler(mockVsum(resource1, resource2));
        var result = handler.validate();

        assertTrue(result.isValid());
        // both resources must have been traversed for proxy objects.
        verify(resource1).getAllContents();
        verify(resource2).getAllContents();
    }

    /**
     * Verifies that a null correspondence model causes validation to fail with a descriptive error message.
     * The correspondence model is required for the VirtualModel to be in a consistent state.
     */
    @Test
    @DisplayName("Null correspondence model fails validation with a descriptive error")
    void nullCorrespondenceModelFailsValidation() {
        var mockVsum = mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of());
        when(mockVsum.getCorrespondenceModel()).thenReturn(null);
        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));

        var result = new PreCommitHandler(mockVsum).validate();

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Correspondence model is null")), "the error message must identify the missing correspondence model");
    }

    /**
     * Verifies that a null UUID resolver causes validation to fail with a descriptive error message.
     * Without the resolver, object identities across branch switches cannot be maintained.
     */
    @Test
    @DisplayName("Null UUID resolver fails validation with a descriptive error")
    void nullUuidResolverFailsValidation() {
        var mockVsum = mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of());
        when(mockVsum.getCorrespondenceModel()).thenReturn(mock(EditableCorrespondenceModelView.class));
        when(mockVsum.getUuidResolver()).thenReturn(null);

        var result = new PreCommitHandler(mockVsum).validate();

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("UUID resolver is null")), "the error message must identify the missing UUID resolver");
    }

    /**
     * Creates a mock {@link InternalVirtualModel} pre-configured with a valid correspondence model and UUID resolver.
     * Optionally accepts resources to include in the model.
     * All tests that do not need to customize the correspondence model or UUID resolver should use this helper to avoid duplicating stub setup.
     */
    private static InternalVirtualModel mockVsum(Resource... resources) {
        var mockVsum = mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of(resources));
        when(mockVsum.getCorrespondenceModel()).thenReturn(mock(EditableCorrespondenceModelView.class));
        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));
        return mockVsum;
    }

    /**
     * Creates a mock {@link Resource} that is loaded, has no errors or warnings, and returns an empty iterator for its contents.
     * Used for tests that need a healthy resource without specific error or warning scenarios.
     */
    private static Resource mockCleanResource() {
        var resource = mock(Resource.class);
        when(resource.isLoaded()).thenReturn(true);
        when(resource.getErrors()).thenReturn(new BasicEList<>());
        when(resource.getWarnings()).thenReturn(new BasicEList<>());
        when(resource.getAllContents()).thenReturn(new EmptyTreeIterator());
        return resource;
    }

    /**
     * Creates a diagnostic list containing a single error with message "Malformed XMI" for use in resource error scenarios.
     */
    private static EList<Resource.Diagnostic> createErrorDiagnostics() {
        Resource.Diagnostic error = new Resource.Diagnostic() {
            @Override public String getMessage() { return "Malformed XMI"; }
            @Override public String getLocation() { return "models/User.xmi"; }
            @Override public int getLine() { return 42; }
            @Override public int getColumn() { return 10; }
        };
        EList<Resource.Diagnostic> list = new BasicEList<>();
        list.add(error);
        return list;
    }

    /**
     * Creates a diagnostic list containing a single warning with message "Deprecated attribute" for use in resource warning scenarios.
     */
    private static EList<Resource.Diagnostic> createWarningDiagnostics() {
        Resource.Diagnostic warning = new Resource.Diagnostic() {
            @Override public String getMessage() { return "Deprecated attribute"; }
            @Override public String getLocation() { return null; }
            @Override public int getLine() { return 0; }
            @Override public int getColumn() { return 0; }
        };
        EList<Resource.Diagnostic> list = new BasicEList<>();
        list.add(warning);
        return list;
    }

    /**
     * An empty {@link org.eclipse.emf.common.util.TreeIterator} for mocking resources that contain no model objects.
     */
    private static class EmptyTreeIterator
            implements org.eclipse.emf.common.util.TreeIterator<EObject> {
        @Override public boolean hasNext() { return false; }
        @Override public EObject next() { return null; }
        @Override public void prune() {}
    }
}