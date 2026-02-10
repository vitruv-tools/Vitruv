package tools.vitruv.framework.vsum.branch.handler;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Integration tests for {@link PreCommitHandler}.
 *
 * <p>Tests validation logic with mocked VirtualModel and resources.
 */
@DisplayName("PreCommitHandler Tests")
class PreCommitHandlerTest {

    @Test
    @DisplayName("Empty VSUM should be valid")
    @SuppressWarnings("unchecked")
    void testValidate_EmptyVsumIsValid() {
        InternalVirtualModel mockVsum = Mockito.mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of());

        EditableCorrespondenceModelView<Correspondence> mockCorr = mock(EditableCorrespondenceModelView.class);
        when(mockVsum.getCorrespondenceModel()).thenReturn(mockCorr);

        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));

        PreCommitHandler handler = new PreCommitHandler(mockVsum);
        ValidationResult result = handler.validate();

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    @DisplayName("Loaded resources without errors should be valid")
    @SuppressWarnings("unchecked")
    void testValidate_LoadedResourcesAreValid() {
        Resource mockResource = Mockito.mock(Resource.class);
        when(mockResource.isLoaded()).thenReturn(true);
        when(mockResource.getErrors()).thenReturn(new BasicEList<>());
        when(mockResource.getWarnings()).thenReturn(new BasicEList<>());
        when(mockResource.getAllContents()).thenReturn(new EmptyTreeIterator());

        InternalVirtualModel mockVsum = Mockito.mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of(mockResource));

        EditableCorrespondenceModelView<Correspondence> mockCorr = mock(EditableCorrespondenceModelView.class);
        when(mockVsum.getCorrespondenceModel()).thenReturn(mockCorr);

        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));

        PreCommitHandler handler = new PreCommitHandler(mockVsum);
        ValidationResult result = handler.validate();

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    @DisplayName("Resource with load error should fail validation")
    @SuppressWarnings("unchecked")
    void testValidate_ResourceWithLoadError() {
        Resource mockResource = Mockito.mock(Resource.class);
        when(mockResource.isLoaded()).thenReturn(true);
        when(mockResource.getErrors()).thenReturn(createErrorDiagnostics());
        when(mockResource.getWarnings()).thenReturn(new BasicEList<>());
        when(mockResource.getURI()).thenReturn(org.eclipse.emf.common.util.URI.createURI("models/User.xmi"));

        InternalVirtualModel mockVsum = Mockito.mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of(mockResource));

        EditableCorrespondenceModelView<Correspondence> mockCorr = mock(EditableCorrespondenceModelView.class);
        when(mockVsum.getCorrespondenceModel()).thenReturn(mockCorr);

        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));

        PreCommitHandler handler = new PreCommitHandler(mockVsum);
        ValidationResult result = handler.validate();

        assertFalse(result.isValid());
        assertFalse(result.getErrors().isEmpty());
        assertTrue(result.getErrors().get(0).contains("Malformed XMI"));
    }

    @Test
    @DisplayName("Resource with warning should pass validation")
    @SuppressWarnings("unchecked")
    void testValidate_ResourceWithWarning() {
        Resource mockResource = Mockito.mock(Resource.class);
        when(mockResource.isLoaded()).thenReturn(true);
        when(mockResource.getErrors()).thenReturn(new BasicEList<>());
        when(mockResource.getWarnings()).thenReturn(createWarningDiagnostics());
        when(mockResource.getURI()).thenReturn(org.eclipse.emf.common.util.URI.createURI("models/User.xmi"));
        when(mockResource.getAllContents()).thenReturn(new EmptyTreeIterator());

        InternalVirtualModel mockVsum = Mockito.mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of(mockResource));

        EditableCorrespondenceModelView<Correspondence> mockCorr = mock(EditableCorrespondenceModelView.class);
        when(mockVsum.getCorrespondenceModel()).thenReturn(mockCorr);

        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));

        PreCommitHandler handler = new PreCommitHandler(mockVsum);
        ValidationResult result = handler.validate();

        assertTrue(result.isValid());
        assertTrue(result.hasWarnings());
        assertTrue(result.getWarnings().get(0).contains("Deprecated"));
    }

    @Test
    @DisplayName("Unloaded resource should be loaded during validation")
    @SuppressWarnings("unchecked")
    void testValidate_UnloadedResourceTriesToLoad() throws IOException {
        Resource mockResource = Mockito.mock(Resource.class);
        when(mockResource.isLoaded()).thenReturn(false);
        when(mockResource.getErrors()).thenReturn(new BasicEList<>());
        when(mockResource.getWarnings()).thenReturn(new BasicEList<>());
        when(mockResource.getAllContents()).thenReturn(new EmptyTreeIterator());

        InternalVirtualModel mockVsum = Mockito.mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of(mockResource));

        EditableCorrespondenceModelView<Correspondence> mockCorr = mock(EditableCorrespondenceModelView.class);
        when(mockVsum.getCorrespondenceModel()).thenReturn(mockCorr);

        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));

        PreCommitHandler handler = new PreCommitHandler(mockVsum);
        handler.validate();

        verify(mockResource).load(any());
    }

    @Test
    @DisplayName("Null correspondence model should fail validation")
    void testValidate_NullCorrespondenceModel() {
        InternalVirtualModel mockVsum = Mockito.mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of());
        when(mockVsum.getCorrespondenceModel()).thenReturn(null);
        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));

        PreCommitHandler handler = new PreCommitHandler(mockVsum);
        ValidationResult result = handler.validate();

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Correspondence model is null")));
    }

    @Test
    @DisplayName("Null UUID resolver should fail validation")
    @SuppressWarnings("unchecked")
    void testValidate_NullUuidResolver() {
        InternalVirtualModel mockVsum = Mockito.mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of());

        EditableCorrespondenceModelView<Correspondence> mockCorr = mock(EditableCorrespondenceModelView.class);
        when(mockVsum.getCorrespondenceModel()).thenReturn(mockCorr);

        when(mockVsum.getUuidResolver()).thenReturn(null);

        PreCommitHandler handler = new PreCommitHandler(mockVsum);
        ValidationResult result = handler.validate();

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("UUID resolver is null")));
    }

    @Test
    @DisplayName("Multiple resources should all be validated")
    @SuppressWarnings("unchecked")
    void testValidate_MultipleResources() {
        Resource resource1 = Mockito.mock(Resource.class);
        when(resource1.isLoaded()).thenReturn(true);
        when(resource1.getErrors()).thenReturn(new BasicEList<>());
        when(resource1.getWarnings()).thenReturn(new BasicEList<>());
        when(resource1.getAllContents()).thenReturn(new EmptyTreeIterator());

        Resource resource2 = Mockito.mock(Resource.class);
        when(resource2.isLoaded()).thenReturn(true);
        when(resource2.getErrors()).thenReturn(new BasicEList<>());
        when(resource2.getWarnings()).thenReturn(new BasicEList<>());
        when(resource2.getAllContents()).thenReturn(new EmptyTreeIterator());

        InternalVirtualModel mockVsum = Mockito.mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of(resource1, resource2));

        EditableCorrespondenceModelView<Correspondence> mockCorr = mock(EditableCorrespondenceModelView.class);
        when(mockVsum.getCorrespondenceModel()).thenReturn(mockCorr);
        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));
        PreCommitHandler handler = new PreCommitHandler(mockVsum);
        ValidationResult result = handler.validate();

        assertTrue(result.isValid());
    }


    /**
     * Creates error diagnostics for testing resource load failures.
     */
    private static EList<Resource.Diagnostic> createErrorDiagnostics() {
        Resource.Diagnostic error = new Resource.Diagnostic() {
            @Override
            public String getMessage() {
                return "Malformed XMI";
            }
            @Override
            public String getLocation() {
                return "models/User.xmi";
            }
            @Override
            public int getLine() {
                return 42;
            }
            @Override
            public int getColumn() {
                return 10;
            }
        };

        EList<Resource.Diagnostic> errors = new BasicEList<>();
        errors.add(error);
        return errors;
    }

    /**
     * Creates warning diagnostics for testing resource warnings.
     */
    private static EList<Resource.Diagnostic> createWarningDiagnostics() {
        Resource.Diagnostic warning = new Resource.Diagnostic() {
            @Override
            public String getMessage() {
                return "Deprecated attribute";
            }
            @Override
            public String getLocation() {
                return null;
            }
            @Override
            public int getLine() {
                return 0;
            }
            @Override
            public int getColumn() {
                return 0;
            }
        };

        EList<Resource.Diagnostic> warnings = new BasicEList<>();
        warnings.add(warning);
        return warnings;
    }

    /**
     * Helper class: Empty tree iterator for mocking resource contents.
     */
    private static class EmptyTreeIterator implements org.eclipse.emf.common.util.TreeIterator<EObject> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public EObject next() {
            return null;
        }

        @Override
        public void prune() {
        }
    }
}