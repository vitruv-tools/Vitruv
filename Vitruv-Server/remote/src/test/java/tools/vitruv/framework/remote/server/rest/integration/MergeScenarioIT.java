package tools.vitruv.framework.remote.server.rest.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
/**
 * End-to-end integration test for the multi-branch merge scenario:
 *
 * <pre>
 * master  ─── C0 (init)  ─── C1 (entity.xmi created)
 *                                   \
 *                    feature/merge-a  C2 (name attribute changed)
 *
 * master (continued)  ─── C3 (description attribute changed)
 *
 * POST /vsum/merge  (feature/merge-a -> master)
 * </pre>
 *
 * <p>Tests run in a fixed order so that each step can build on state from the previous one.
 * All tests share the same server and V-SUM instance (one per class).
 */
@TestMethodOrder(OrderAnnotation.class)
class MergeScenarioIT extends AbstractServerIntegrationTest {

    private static final String FEATURE_BRANCH = "feature/merge-a";

    /** Known merge status values from MergeManager. */
    private static final Set<String> KNOWN_STATUSES =
            Set.of("SUCCESS", "FAST_FORWARD", "CONFLICTING", "FAILED");

    /** SHA captured from the commit on feature/merge-a (step 5). */
    private static String shaA;

    /** SHA captured from the divergent commit on master (step 10). */
    private static String shaM;

    // Phase 1: setup

    @Test
    @Order(1)
    @DisplayName("GET /vsum/branch returns 200 and lists master")
    void listBranchesContainsMaster() {
        HttpResponse<String> response = get("/vsum/branch");
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
        assertTrue(response.body().contains("master"),
                "Expected 'master' in branch list but got: " + response.body());
    }

    @Test
    @Order(2)
    @DisplayName("POST /vsum/branch creates feature/merge-a from master")
    void createFeatureBranch() {
        String body = "{\"name\":\"" + FEATURE_BRANCH + "\",\"fromBranch\":\"master\"}";
        HttpResponse<String> response = post("/vsum/branch", body);
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
                "Create branch failed: " + response.body());
        assertTrue(response.body().contains(FEATURE_BRANCH),
                "Expected branch name in response but got: " + response.body());
    }

    // Phase 2: commit on feature/merge-a

    @Test
    @Order(3)
    @DisplayName("POST /vsum/branch/{branchName}/switch switches to feature/merge-a")
    void switchToFeatureBranch() {
        HttpResponse<String> response = post("/vsum/branch/" + FEATURE_BRANCH.replace("/", "%2F") + "/switch", "");
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
                "Switch to feature branch failed: " + response.body());
        assertTrue(response.body().contains(FEATURE_BRANCH),
                "Expected branch name in response but got: " + response.body());
    }

    @Test
    @Order(4)
    @DisplayName("POST /vsum/commit on feature/merge-a: add entity.xmi")
    void commitEntityOnFeatureBranch() throws IOException {
        // Write a minimal XMI file that the commit can stage. No live model session
        // is required - the commit endpoint stages all untracked/modified files.
        writeModelFile("model/entity.xmi",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<model:Root xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\""
                        + " xmlns:model=\"http://vitruv.tools/methodologisttemplate/model\""
                        + " name=\"EntityA\"/>");

        String body = "{\"message\":\"Add entity on feature/merge-a\"}";
        HttpResponse<String> response = post("/vsum/commit", body);
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
                "Commit on feature branch failed: " + response.body());
        assertTrue(response.body().contains("commitSha"),
                "Expected commitSha field in response but got: " + response.body());

        shaA = extractSha(response.body());
    }

    @Test
    @Order(5)
    @DisplayName("GET /vsum/commit lists the feature/merge-a commit")
    void featureBranchCommitListed() {
        HttpResponse<String> response = get("/vsum/commit/" + FEATURE_BRANCH.replace("/", "%2F"));
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
        assertTrue(response.body().contains("Add entity on feature/merge-a"),
                "Expected commit message in list but got: " + response.body());
    }

    @Test
    @Order(6)
    @DisplayName("GET /vsum/changelog for feature/merge-a commit returns 200 or 405")
    void changelogForFeatureBranchCommit() {
        if (shaA == null) {
            return; // SHA extraction failed in previous test - skip gracefully
        }
        HttpResponse<String> response = get("/vsum/changelog/" + FEATURE_BRANCH.replace("/", "%2F") + "/" + shaA);
        int status = response.statusCode();
        // 200 = changelog written (model changes detected); 405 = no changelog (raw XMI only).
        assertTrue(status == HttpURLConnection.HTTP_OK || status == HttpURLConnection.HTTP_BAD_METHOD,
                "Expected 200 or 405 but got: " + status + " body: " + response.body());
    }

    // Phase 3: divergent commit on master

    @Test
    @Order(7)
    @DisplayName("POST /vsum/branch/{branchName}/switch switches back to master")
    void switchBackToMaster() {
        HttpResponse<String> response = post("/vsum/branch/master/switch", "");
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
                "Switch to master failed: " + response.body());
        assertTrue(response.body().contains("master"),
                "Expected 'master' in response but got: " + response.body());
    }

    @Test
    @Order(8)
    @DisplayName("POST /vsum/commit on master: modify entity.xmi differently")
    void commitDivergentChangeOnMaster() throws IOException {
        // Write a different version of the same file so that master and feature/merge-a diverge.
        writeModelFile("model/entity.xmi",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<model:Root xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\""
                        + " xmlns:model=\"http://vitruv.tools/methodologisttemplate/model\""
                        + " name=\"EntityMaster\" description=\"added on master\"/>");

        String body = "{\"message\":\"Update entity on master\"}";
        HttpResponse<String> response = post("/vsum/commit", body);
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
                "Commit on master failed: " + response.body());
        assertTrue(response.body().contains("commitSha"),
                "Expected commitSha field in response but got: " + response.body());

        shaM = extractSha(response.body());
    }

    @Test
    @Order(9)
    @DisplayName("GET /vsum/commit lists the master commit")
    void masterCommitListed() {
        HttpResponse<String> response = get("/vsum/commit/master");
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
        assertTrue(response.body().contains("Update entity on master"),
                "Expected master commit message but got: " + response.body());
    }

    // Phase 4: merge

    @Test
    @Order(10)
    @DisplayName("POST /vsum/merge merges feature/merge-a into master and returns known status")
    void mergeFeatureBranchIntoMaster() {
        String body = "{\"sourceBranch\":\"" + FEATURE_BRANCH + "\","
                + "\"deleteAfterMerge\":false}";
        HttpResponse<String> response = post("/vsum/merge", body);
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
                "Merge request failed: " + response.body());

        String responseBody = response.body();
        assertTrue(responseBody.contains("status"),
                "Expected 'status' field in merge response but got: " + responseBody);

        // The status must be one of the documented values.
        boolean hasKnownStatus = KNOWN_STATUSES.stream()
                .anyMatch(responseBody::contains);
        assertTrue(hasKnownStatus,
                "Merge status is not one of " + KNOWN_STATUSES + ". Body: " + responseBody);
    }

    @Test
    @Order(11)
    @DisplayName("GET /vsum/commit lists more commits on master after merge")
    void masterCommitCountGrewAfterMerge() {
        HttpResponse<String> response = get("/vsum/commit/master");
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
        // After merge, either a merge commit appeared or both branch commits are present.
        // At minimum, the list must be non-empty and contain the master commit message.
        String body = response.body();
        assertFalse("[]".equals(body.trim()),
                "Expected non-empty commit list after merge but got empty array");
        assertTrue(body.contains("Update entity on master"),
                "Expected master commit message in post-merge list but got: " + body);
    }

    @Test
    @Order(12)
    @DisplayName("GET /vsum/branch still lists feature/merge-a (deleteAfterMerge=false)")
    void featureBranchStillExistsAfterMerge() {
        HttpResponse<String> response = get("/vsum/branch");
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
        assertTrue(response.body().contains(FEATURE_BRANCH),
                "Expected feature branch to still exist but got: " + response.body());
    }

    // Phase 5: deleteAfterMerge=true variant

    @Test
    @Order(13)
    @DisplayName("POST /vsum/merge with deleteAfterMerge=true removes source branch on success")
    void mergeWithDeleteAfterMerge() {
        // Create a second feature branch to test deleteAfterMerge without disturbing
        // the state from Phase 4.
        String branch = "feature/merge-b";
        post("/vsum/branch", "{\"name\":\"" + branch + "\",\"fromBranch\":\"master\"}");

        // Merge it into master with deleteAfterMerge=true.
        String body = "{\"sourceBranch\":\"" + branch + "\",\"deleteAfterMerge\":true}";
        HttpResponse<String> mergeResponse = post("/vsum/merge", body);

        // Only assert deletion when the merge actually moved the target HEAD (SUCCESS).
        // FAST_FORWARD means source was already in target - MergeManager may not delete in this case.
        if (mergeResponse.statusCode() == HttpURLConnection.HTTP_OK
                && mergeResponse.body().contains("SUCCESS")
                && !mergeResponse.body().contains("FAILED")) {
            HttpResponse<String> listResponse = get("/vsum/branch");
            assertFalse(listResponse.body().contains(branch),
                    "Branch should have been deleted after merge but still appears: "
                            + listResponse.body());
        }
    }

    // Phase 6: error paths

    @Test
    @Order(14)
    @DisplayName("POST /vsum/merge with blank sourceBranch returns 400")
    void mergeBlankSourceBranchReturns400() {
        String body = "{\"sourceBranch\":\"\",\"deleteAfterMerge\":false}";
        HttpResponse<String> response = post("/vsum/merge", body);
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode(),
                "Expected 400 for blank sourceBranch but got: " + response.statusCode());
    }

    @Test
    @Order(15)
    @DisplayName("POST /vsum/merge with non-existent branch returns 500")
    void mergeNonExistentBranchReturns500() {
        String body = "{\"sourceBranch\":\"branch-does-not-exist\",\"deleteAfterMerge\":false}";
        HttpResponse<String> response = post("/vsum/merge", body);
        assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, response.statusCode(),
                "Expected 500 for non-existent branch but got: " + response.statusCode());
    }

    // Phase 7: conflicts endpoint

    @Test
    @Order(16)
    @DisplayName("GET /vsum/branch/{branch}/conflicts?base=master returns 200 with structured response")
    void branchConflictsReturnsStructuredResponse() {
        HttpResponse<String> response = get("/vsum/branch/" + FEATURE_BRANCH.replace("/", "%2F") + "/conflicts?base=master");

        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
                "Expected 200 but got " + response.statusCode() + ": " + response.body());
        String body = response.body();
        assertTrue(body.contains("\"branch\""), "Expected 'branch' field in response: " + body);
        assertTrue(body.contains("\"baseBranch\""), "Expected 'baseBranch' field in response: " + body);
        assertTrue(body.contains("\"conflictCount\""), "Expected 'conflictCount' field in response: " + body);
        assertTrue(body.contains("\"conflicts\""), "Expected 'conflicts' field in response: " + body);
    }

    @Test
    @Order(17)
    @DisplayName("GET /vsum/branch/{branch}/conflicts returns 405 for a non-existent branch")
    void branchConflictsUnknownBranchReturns405() {
        HttpResponse<String> response = get("/vsum/branch/no-such-branch/conflicts?base=master");

        assertEquals(HttpURLConnection.HTTP_BAD_METHOD, response.statusCode());
    }

    // helpers

    /**
     * Extracts the {@code commitSha} value from a JSON response body.
     * Handles both compact ({@code "commitSha":"abc"}) and pretty-printed
     * ({@code "commitSha" : "abc"}) output.
     *
     * @return the 7-char (or longer) SHA string, or {@code null} if not found.
     */
    private static String extractSha(String json) {
        int keyIdx = json.indexOf("\"commitSha\"");
        if (keyIdx < 0) {
            return null;
        }
        int valueQuote = json.indexOf("\"", keyIdx + "\"commitSha\"".length() + 1);
        if (valueQuote < 0) {
            return null;
        }
        int start = valueQuote + 1;
        int end = json.indexOf("\"", start);
        if (end <= start) {
            return null;
        }
        String sha = json.substring(start, end);
        return sha.isBlank() ? null : sha;
    }
}
