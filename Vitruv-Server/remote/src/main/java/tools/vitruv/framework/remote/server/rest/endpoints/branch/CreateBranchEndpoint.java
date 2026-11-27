package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * {@code POST /vsum/branch}
 *
 * <p>Creates a new branch derived from an existing branch.
 *
 * <p>Expected request body:
 * <pre>
 * {
 *   "name": "feature/my-feature",
 *   "fromBranch": "main"
 * }
 * </pre>
 *
 * <p>Returns the newly created branch as a JSON object:
 * <pre>
 * {
 *   "name": "feature/my-feature",
 *   "state": "ACTIVE",
 *   "parentBranch": "main",
 *   "createdAt": "2026-04-07T12:00:00",
 *   "lastModified": "2026-04-07T12:00:00"
 * }
 * </pre>
 */
public class CreateBranchEndpoint implements PostEndpoint {

  private final BranchManager branchManager;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link CreateBranchEndpoint}.
   *
   * @param branchManager the branch manager used to create the branch.
   * @param mapper the JSON mapper used to deserialize the request and serialize the response.
   */
  public CreateBranchEndpoint(BranchManager branchManager, JsonMapper mapper) {
    this.branchManager = branchManager;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    try {
      String body = wrapper.getRequestBodyAsString();
      CreateBranchRequest request = mapper.deserialize(body, CreateBranchRequest.class);
      BranchResponse response = BranchResponse.from(
          branchManager.createBranch(request.name(), request.fromBranch()));
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(response);
    } catch (BranchOperationException | IOException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
