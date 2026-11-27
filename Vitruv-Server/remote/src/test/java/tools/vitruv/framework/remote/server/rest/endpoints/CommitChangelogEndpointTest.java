package tools.vitruv.framework.remote.server.rest.endpoints;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.endpoints.changelog.ChangelogEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.commit.CommitEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.commit.CommitRequest;
import tools.vitruv.framework.remote.server.rest.endpoints.commit.ListCommitsEndpoint;
import tools.vitruv.framework.vsum.branch.CommitManager;
import tools.vitruv.framework.vsum.branch.data.CommitResult;
import tools.vitruv.framework.vsum.branch.data.CommitSummary;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for commit and changelog REST endpoints.
 *
 * <p>{@link CommitManager}, {@link JsonMapper} and {@link HttpWrapper} are mocked so that no
 * real Git repository or EMF setup is required.
 */
class CommitChangelogEndpointTest {

  private CommitManager commitManager;
  private JsonMapper mapper;
  private HttpWrapper wrapper;

  @BeforeEach
  void setUp() {
    commitManager = mock(CommitManager.class);
    mapper = mock(JsonMapper.class);
    wrapper = mock(HttpWrapper.class);
  }
  

  @Nested
  @DisplayName("ListCommitsEndpoint - GET /vsum/commit")
  class ListCommitsEndpointTests {
    private ListCommitsEndpoint endpoint;
    @BeforeEach
    void setUp() {
      endpoint = new ListCommitsEndpoint(commitManager, mapper);
    }

    @Test
    @DisplayName("returns serialized commit list for valid branch")
    void returnsSerializedCommitList() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("master");
      List<CommitSummary> summaries = List.of(
          new CommitSummary("abc123", "abc123", "master",
              "Test User", "test@example.com", "2026-04-07T14:00:00",
              "Initial commit", List.of(), false, 0));
      when(commitManager.listCommits("master")).thenReturn(summaries);
      when(mapper.serialize(summaries)).thenReturn("[{\"sha\":\"abc123\"}]");

      String result = endpoint.process(wrapper);

      assertEquals("[{\"sha\":\"abc123\"}]", result);
      verify(wrapper).setContentType(any());
    }

    @Test
    @DisplayName("returns empty list when branch has no commits")
    void returnsEmptyListForBranchWithNoCommits() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("empty-branch");
      when(commitManager.listCommits("empty-branch")).thenReturn(List.of());
      when(mapper.serialize(List.of())).thenReturn("[]");

      String result = endpoint.process(wrapper);

      assertEquals("[]", result);
    }

    @Test
    @DisplayName("throws 400 when branch name is missing from path")
    void throws400WhenBranchNameHeaderMissing() {
      when(wrapper.getPathSegment(0)).thenReturn(null);

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 400 when branch name path segment is blank")
    void throws400WhenBranchNameHeaderBlank() {
      when(wrapper.getPathSegment(0)).thenReturn("   ");

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 500 when CommitManager throws BranchOperationException")
    void throws500WhenCommitManagerThrows() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("master");
      when(commitManager.listCommits("master"))
          .thenThrow(new BranchOperationException("git error"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_INTERNAL_ERROR, ex.getStatusCode());
    }
  }
  

  @Nested
  @DisplayName("CommitEndpoint - POST /vsum/commit")
  class CommitEndpointTests {

    private CommitEndpoint endpoint;

    @BeforeEach
    void setUp() {
      endpoint = new CommitEndpoint(commitManager, mapper, Path.of("/tmp/test-repo"));
    }

    @Test
    @DisplayName("commits with valid message and returns serialized result")
    void commitsAndReturnsResult() throws Exception {
      String body = "{\"message\":\"Add model\"}";
      when(wrapper.getRequestBodyAsString()).thenReturn(body);
      when(mapper.deserialize(body, CommitRequest.class))
          .thenReturn(new CommitRequest("Add model", null, null));

      CommitResult result = mock(CommitResult.class);
      when(result.getCommitSha()).thenReturn("abc123def456");
      when(result.getBranch()).thenReturn("master");
      when(result.getAuthorName()).thenReturn("Test User");
      when(result.getAuthorEmail()).thenReturn("test@example.com");
      when(result.getAuthorDate()).thenReturn(LocalDateTime.of(2026, 4, 7, 14, 0));
      when(result.getStagedFiles()).thenReturn(List.of("system.model"));
      when(result.isHasModelChanges()).thenReturn(true);
      when(commitManager.commit("Add model")).thenReturn(result);
      when(mapper.serialize(any())).thenReturn("{\"commitSha\":\"abc123def456\"}");

      String response = endpoint.process(wrapper);

      assertEquals("{\"commitSha\":\"abc123def456\"}", response);
      verify(wrapper).setContentType(any());
    }

    @Test
    @DisplayName("throws 400 when commit message is blank")
    void throws400WhenCommitMessageBlank() throws Exception {
      String body = "{\"message\":\"   \"}";
      when(wrapper.getRequestBodyAsString()).thenReturn(body);
      when(mapper.deserialize(eq(body), any()))
          .thenReturn(new CommitRequest("   ", null, null));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
      verify(commitManager, never()).commit(any());
    }

    @Test
    @DisplayName("throws 500 when CommitManager throws BranchOperationException")
    void throws500WhenCommitManagerThrows() throws Exception {
      String body = "{\"message\":\"Add model\"}";
      when(wrapper.getRequestBodyAsString()).thenReturn(body);
      when(mapper.deserialize(eq(body), any()))
          .thenReturn(new CommitRequest("Add model", null, null));
      when(commitManager.commit("Add model"))
          .thenThrow(new BranchOperationException("nothing to commit"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_INTERNAL_ERROR, ex.getStatusCode());
    }
  }


  @Nested
  @DisplayName("ChangelogEndpoint - GET /vsum/changelog")
  class ChangelogEndpointTests {

    private ChangelogEndpoint endpoint;

    @BeforeEach
    void setUp() {
      endpoint = new ChangelogEndpoint(commitManager, mapper);
    }

    @Test
    @DisplayName("returns raw changelog JSON when found")
    void returnsSerializedChangelog() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("master");
      when(wrapper.getPathSegment(1)).thenReturn("a1b2c3d");
      when(commitManager.readChangelogRaw("master", "a1b2c3d"))
          .thenReturn("{\"formatVersion\":\"1.0\"}");

      String result = endpoint.process(wrapper);

      assertEquals("{\"formatVersion\":\"1.0\"}", result);
      verify(wrapper).setContentType(any());
    }

    @Test
    @DisplayName("throws 405 when no changelog exists for branch and SHA")
    void throws405WhenNoChangelogFound() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("master");
      when(wrapper.getPathSegment(1)).thenReturn("a1b2c3d");
      when(commitManager.readChangelogRaw("master", "a1b2c3d")).thenReturn(null);

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_METHOD, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 400 when branch name is missing from path")
    void throws400WhenBranchNameMissing() {
      when(wrapper.getPathSegment(0)).thenReturn(null);
      when(wrapper.getPathSegment(1)).thenReturn("a1b2c3d");

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 400 when commit SHA is missing from path")
    void throws400WhenCommitShaMissing() {
      when(wrapper.getPathSegment(0)).thenReturn("master");
      when(wrapper.getPathSegment(1)).thenReturn(null);

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 400 when commit SHA path segment is blank")
    void throws400WhenCommitShaBlank() {
      when(wrapper.getPathSegment(0)).thenReturn("master");
      when(wrapper.getPathSegment(1)).thenReturn("   ");

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("throws 500 when CommitManager throws BranchOperationException")
    void throws500WhenCommitManagerThrows() throws Exception {
      when(wrapper.getPathSegment(0)).thenReturn("master");
      when(wrapper.getPathSegment(1)).thenReturn("a1b2c3d");
      when(commitManager.readChangelogRaw("master", "a1b2c3d"))
          .thenThrow(new BranchOperationException("io error"));

      ServerHaltingException ex = assertThrows(ServerHaltingException.class,
          () -> endpoint.process(wrapper));

      assertEquals(HTTP_INTERNAL_ERROR, ex.getStatusCode());
    }
  }
}
