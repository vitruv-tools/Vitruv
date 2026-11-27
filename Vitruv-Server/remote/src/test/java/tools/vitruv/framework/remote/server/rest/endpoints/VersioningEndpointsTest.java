package tools.vitruv.framework.remote.server.rest.endpoints;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.endpoints.version.*;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;
import tools.vitruv.framework.vsum.branch.data.BranchState;
import tools.vitruv.framework.vsum.branch.data.MaturityLevel;
import tools.vitruv.framework.vsum.versioning.VersioningException;
import tools.vitruv.framework.vsum.versioning.VersioningService;
import tools.vitruv.framework.vsum.versioning.data.RollbackPreview;
import tools.vitruv.framework.vsum.versioning.data.RollbackResult;
import tools.vitruv.framework.vsum.versioning.data.VersionMetadata;

import java.time.LocalDateTime;
import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the versioning REST endpoints.
 *
 * <p>{@link VersioningService}, {@link JsonMapper} and {@link HttpWrapper} are mocked so
 * that no real Git repository or EMF setup is required.
 */
class VersioningEndpointsTest {

  private VersioningService versioningService;
  private JsonMapper mapper;
  private HttpWrapper wrapper;

  @BeforeEach
  void setUp() {
    versioningService = mock(VersioningService.class);
    mapper = mock(JsonMapper.class);
    wrapper = mock(HttpWrapper.class);
  }
  

  private static VersionMetadata sampleVersion() {
    return new VersionMetadata(
        "v1.0", "abc123def456abc123def456abc123def456abc1",
        "master", "Test User", "test@example.com",
        LocalDateTime.of(2026, 4, 7, 14, 0), "Stable baseline", MaturityLevel.DRAFT);
  }

  private static RollbackPreview samplePreview(VersionMetadata version) {
    return new RollbackPreview(
        version, "abc123def456abc123def456abc123def456abc1",
        "master", List.of(), List.of(), false);
  }
  

  @Nested
  @DisplayName("ListVersionsEndpoint - GET /vsum/version")
  class ListVersionsEndpointTests {

    private ListVersionsEndpoint endpoint;

    @BeforeEach
    void setUp() {
      endpoint = new ListVersionsEndpoint(versioningService, mapper);
    }

    @Test
    @DisplayName("returns serialized version list")
    void returnsSerializedVersionList() throws Exception {
      VersionMetadata v = sampleVersion();
      when(versioningService.listVersions()).thenReturn(List.of(v));
      when(mapper.serialize(any())).thenReturn("[{\"versionId\":\"v1.0\"}]");

      String result = endpoint.process(wrapper);

      assertEquals("[{\"versionId\":\"v1.0\"}]", result);
      verify(wrapper).setContentType(any());
    }

    @Test
    @DisplayName("returns empty JSON array when no versions exist")
    void returnsEmptyArrayWhenNoVersions() throws Exception {
      when(versioningService.listVersions()).thenReturn(List.of());
      when(mapper.serialize(any())).thenReturn("[]");

      String result = endpoint.process(wrapper);

      assertEquals("[]", result);
    }

    @Test
    @DisplayName("throws 500 when VersioningService throws VersioningException")
    void throws500WhenServiceThrows() throws Exception {
      when(versioningService.listVersions()).thenThrow(new VersioningException("store error"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_INTERNAL_ERROR, ex.getStatusCode());
    }
  }
  

  @Nested
  @DisplayName("CreateVersionEndpoint - POST /vsum/version")
  class CreateVersionEndpointTests {

    private CreateVersionEndpoint endpoint;

    @BeforeEach
    void setUp() {
      endpoint = new CreateVersionEndpoint(versioningService, mapper);
    }

    @Test
    @DisplayName("creates version and returns serialized response")
    void createsVersionAndReturnsResponse() throws Exception {
      String body = "{\"versionId\":\"v1.0\",\"description\":\"Baseline\"}";
      when(wrapper.getRequestBodyAsString()).thenReturn(body);
      when(mapper.deserialize(body, CreateVersionRequest.class))
          .thenReturn(new CreateVersionRequest("v1.0", "Baseline"));
      when(versioningService.createVersion("v1.0", "Baseline")).thenReturn(sampleVersion());
      when(mapper.serialize(any())).thenReturn("{\"versionId\":\"v1.0\"}");

      String result = endpoint.process(wrapper);

      assertEquals("{\"versionId\":\"v1.0\"}", result);
      verify(wrapper).setContentType(any());
    }

    @Test
    @DisplayName("throws 400 when versionId is blank")
    void throws400WhenVersionIdBlank() throws Exception {
      String body = "{\"versionId\":\"\",\"description\":\"Baseline\"}";
      when(wrapper.getRequestBodyAsString()).thenReturn(body);
      when(mapper.deserialize(eq(body), any()))
          .thenReturn(new CreateVersionRequest("", "Baseline"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
      verify(versioningService, never()).createVersion(any(), any());
    }

    @Test
    @DisplayName("throws 500 when version ID already exists")
    void throws500WhenVersionIdAlreadyExists() throws Exception {
      String body = "{\"versionId\":\"v1.0\",\"description\":\"Duplicate\"}";
      when(wrapper.getRequestBodyAsString()).thenReturn(body);
      when(mapper.deserialize(eq(body), any()))
          .thenReturn(new CreateVersionRequest("v1.0", "Duplicate"));
      when(versioningService.createVersion("v1.0", "Duplicate"))
          .thenThrow(new VersioningException("version v1.0 already exists"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_INTERNAL_ERROR, ex.getStatusCode());
    }
  }


  @Nested
  @DisplayName("GetVersionEndpoint - GET /vsum/version/detail")
  class GetVersionEndpointTests {

    private GetVersionEndpoint endpoint;

    @BeforeEach
    void setUp() {
      endpoint = new GetVersionEndpoint(versioningService, mapper);
    }

    @Test
    @DisplayName("returns serialized version for existing version ID")
    void returnsSerializedVersion() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("v1.0");
      when(versioningService.getVersion("v1.0")).thenReturn(sampleVersion());
      when(mapper.serialize(any())).thenReturn("{\"versionId\":\"v1.0\"}");

      String result = endpoint.process(wrapper);

      assertEquals("{\"versionId\":\"v1.0\"}", result);
      verify(wrapper).setContentType(any());
    }

    @Test
    @DisplayName("throws 400 when version ID is missing from path")
    void throws400WhenVersionIdHeaderMissing() {
      when(wrapper.getPathSegment(0)).thenReturn(null);

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 400 when version ID path segment is blank")
    void throws400WhenVersionIdHeaderBlank() {
      when(wrapper.getPathSegment(0)).thenReturn("   ");

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 405 when version does not exist")
    void throws405WhenVersionNotFound() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("nonexistent");
      when(versioningService.getVersion("nonexistent"))
          .thenThrow(new VersioningException("version not found"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_METHOD, ex.getStatusCode());
    }
  }


  @Nested
  @DisplayName("DeleteVersionEndpoint - DELETE /vsum/version/detail")
  class DeleteVersionEndpointTests {

    private DeleteVersionEndpoint endpoint;

    @BeforeEach
    void setUp() {
      endpoint = new DeleteVersionEndpoint(versioningService);
    }

    @Test
    @DisplayName("deletes version and returns null body")
    void deletesVersionAndReturnsNull() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("v1.0");

      String result = endpoint.process(wrapper);

      assertNull(result);
      verify(versioningService).deleteVersion("v1.0");
    }

    @Test
    @DisplayName("throws 400 when version ID is missing from path")
    void throws400WhenVersionIdHeaderMissing() {
      when(wrapper.getPathSegment(0)).thenReturn(null);

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 405 when version does not exist")
    void throws405WhenVersionNotFound() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("nonexistent");
      org.mockito.Mockito.doThrow(new VersioningException("version not found"))
          .when(versioningService).deleteVersion("nonexistent");

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_METHOD, ex.getStatusCode());
    }
  }


  @Nested
  @DisplayName("RollbackPreviewEndpoint - POST /vsum/version/rollback/preview")
  class RollbackPreviewEndpointTests {

    private RollbackPreviewEndpoint endpoint;

    @BeforeEach
    void setUp() {
      endpoint = new RollbackPreviewEndpoint(versioningService, mapper);
    }

    @Test
    @DisplayName("returns serialized rollback preview")
    void returnsSerializedPreview() throws Exception {
      VersionMetadata version = sampleVersion();
      RollbackPreview preview = samplePreview(version);
      when(wrapper.getPathSegment(0)).thenReturn("v1.0");
      when(versioningService.previewRollback("v1.0")).thenReturn(preview);
      when(mapper.serialize(any())).thenReturn("{\"versionId\":\"v1.0\"}");

      String result = endpoint.process(wrapper);

      assertEquals("{\"versionId\":\"v1.0\"}", result);
      verify(wrapper).setContentType(any());
    }

    @Test
    @DisplayName("throws 400 when version ID is missing from path")
    void throws400WhenVersionIdMissing() {
      when(wrapper.getPathSegment(0)).thenReturn(null);

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 405 when version does not exist")
    void throws405WhenVersionNotFound() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("nonexistent");
      when(versioningService.previewRollback("nonexistent"))
          .thenThrow(new VersioningException("version not found"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_METHOD, ex.getStatusCode());
    }
  }


  @Nested
  @DisplayName("RollbackConfirmEndpoint - POST /vsum/version/rollback/confirm")
  class RollbackConfirmEndpointTests {

    private RollbackConfirmEndpoint endpoint;

    @BeforeEach
    void setUp() {
      endpoint = new RollbackConfirmEndpoint(versioningService, mapper);
    }

    @Test
    @DisplayName("executes rollback and returns serialized result")
    void executesRollbackAndReturnsResult() throws Exception {
      VersionMetadata version = sampleVersion();
      RollbackPreview preview = samplePreview(version);
      RollbackResult rollbackResult = RollbackResult.success(version,
          "abc123def456abc123def456abc123def456abc1");
      when(wrapper.getPathSegment(0)).thenReturn("v1.0");
      when(versioningService.previewRollback("v1.0")).thenReturn(preview);
      when(versioningService.confirmRollback(preview)).thenReturn(rollbackResult);
      when(mapper.serialize(any())).thenReturn("{\"status\":\"SUCCESS\"}");

      String result = endpoint.process(wrapper);

      assertEquals("{\"status\":\"SUCCESS\"}", result);
      verify(wrapper).setContentType(any());
      verify(versioningService).previewRollback("v1.0");
      verify(versioningService).confirmRollback(preview);
    }

    @Test
    @DisplayName("throws 400 when version ID is missing from path")
    void throws400WhenVersionIdMissing() {
      when(wrapper.getPathSegment(0)).thenReturn(null);

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 405 when version does not exist during preview phase")
    void throws405WhenVersionNotFoundDuringPreview() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("nonexistent");
      when(versioningService.previewRollback("nonexistent"))
          .thenThrow(new VersioningException("version not found"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_METHOD, ex.getStatusCode());
      verify(versioningService, never()).confirmRollback(any());
    }
  }


  @Nested
  @DisplayName("CreateVersionBranchEndpoint - POST /vsum/version/branch")
  class CreateVersionBranchEndpointTests {

    private CreateVersionBranchEndpoint endpoint;

    @BeforeEach
    void setUp() {
      endpoint = new CreateVersionBranchEndpoint(versioningService, mapper);
    }

    @Test
    @DisplayName("creates branch from version and returns serialized branch response")
    void createsBranchFromVersionAndReturnsResponse() throws Exception {
      String body = "{\"branchName\":\"feature/from-v1.0\"}";
      when(wrapper.getPathSegment(0)).thenReturn("v1.0");
      when(wrapper.getRequestBodyAsString()).thenReturn(body);
      when(mapper.deserialize(body, CreateVersionBranchRequest.class))
          .thenReturn(new CreateVersionBranchRequest("feature/from-v1.0"));

      BranchMetadata branchMetadata = new BranchMetadata(
          "feature/from-v1.0", BranchState.ACTIVE, "master",
          LocalDateTime.of(2026, 4, 7, 14, 0), LocalDateTime.of(2026, 4, 7, 14, 0), MaturityLevel.DRAFT);
      when(versioningService.createBranchFromVersion("feature/from-v1.0", "v1.0"))
          .thenReturn(branchMetadata);
      when(mapper.serialize(any())).thenReturn("{\"name\":\"feature/from-v1.0\"}");

      String result = endpoint.process(wrapper);

      assertEquals("{\"name\":\"feature/from-v1.0\"}", result);
      verify(wrapper).setContentType(any());
    }

    @Test
    @DisplayName("throws 400 when version ID is missing from path")
    void throws400WhenVersionIdHeaderMissing() {
      when(wrapper.getPathSegment(0)).thenReturn(null);

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 400 when branchName in body is blank")
    void throws400WhenBranchNameBlank() throws Exception {
      String body = "{\"branchName\":\"\"}";
      when(wrapper.getPathSegment(0)).thenReturn("v1.0");
      when(wrapper.getRequestBodyAsString()).thenReturn(body);
      when(mapper.deserialize(eq(body), any()))
          .thenReturn(new CreateVersionBranchRequest(""));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
      verify(versioningService, never()).createBranchFromVersion(any(), any());
    }

    @Test
    @DisplayName("throws 405 when version does not exist")
    void throws405WhenVersionNotFound() throws Exception {
      String body = "{\"branchName\":\"feature/x\"}";
      when(wrapper.getPathSegment(0)).thenReturn("nonexistent");
      when(wrapper.getRequestBodyAsString()).thenReturn(body);
      when(mapper.deserialize(eq(body), any()))
          .thenReturn(new CreateVersionBranchRequest("feature/x"));
      when(versioningService.createBranchFromVersion("feature/x", "nonexistent"))
          .thenThrow(new VersioningException("version not found"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_METHOD, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 405 when branch name is already taken")
    void throws405WhenBranchAlreadyExists() throws Exception {
      String body = "{\"branchName\":\"existing-branch\"}";
      when(wrapper.getPathSegment(0)).thenReturn("v1.0");
      when(wrapper.getRequestBodyAsString()).thenReturn(body);
      when(mapper.deserialize(eq(body), any()))
          .thenReturn(new CreateVersionBranchRequest("existing-branch"));
      when(versioningService.createBranchFromVersion("existing-branch", "v1.0"))
          .thenThrow(new VersioningException("branch already exists"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_METHOD, ex.getStatusCode());
    }
  }
}
