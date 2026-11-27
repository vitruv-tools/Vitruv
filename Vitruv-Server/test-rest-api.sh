#!/bin/bash
# Automated REST API test for Vitruv Server.
#
# Starts a fresh server, exercises every endpoint,
# reports PASS or FAIL for each assertion, then shuts the server down.
#
# Usage (from MINGW64 / Git Bash):
#   cd /path/to/Vitruv-Server
#   bash test-rest-api.sh
#
# Prerequisites:
#   - Both Vitruv and Vitruv-Server already built (mvn install -DskipTests)
#   - No other process listening on port 8080

set -uo pipefail

BASE="http://localhost:8080"
REPO="C:/vitruv-test-auto"
VITRUV_SERVER="$(cd "$(dirname "$0")" && pwd)"
MODEL_PATH="$REPO/system.model"
CLIENT_TEMP="C:/tmp/vitruv-client-temp"
BODY_FILE="/tmp/vt_body.txt"

GREEN='\033[0;32m'
RED='\033[0;31m'
CYAN='\033[0;36m'
NC='\033[0m'

PASS=0
FAIL=0

step() { echo -e "\n${CYAN}=== $* ===${NC}"; }

ok() {
  echo -e "  ${GREEN}PASS${NC}  $*"
  PASS=$((PASS + 1))
}

fail() {
  echo -e "  ${RED}FAIL${NC}  $*"
  FAIL=$((FAIL + 1))
}

body() { cat "$BODY_FILE"; }

curl_get() {
  curl -s -o "$BODY_FILE" -w "%{http_code}" "$BASE$1"
}

curl_get_h() {
  curl -s -o "$BODY_FILE" -w "%{http_code}" -H "$2: $3" "$BASE$1"
}

curl_post() {
  if [ -n "${2:-}" ]; then
    curl -s -o "$BODY_FILE" -w "%{http_code}" -X POST "$BASE$1" \
      -H "Content-Type: application/json" -d "$2"
  else
    curl -s -o "$BODY_FILE" -w "%{http_code}" -X POST "$BASE$1"
  fi
}

curl_patch() {
  curl -s -o "$BODY_FILE" -w "%{http_code}" -X PATCH "$BASE$1" \
    -H "Content-Type: application/json" -d "$2"
}

curl_delete() {
  curl -s -o "$BODY_FILE" -w "%{http_code}" -X DELETE "$BASE$1"
}

assert_status() {
  local desc="$1" code="$2" expected="$3"
  if [ "$code" = "$expected" ]; then
    ok "$desc  (HTTP $code)"
  else
    fail "$desc  (expected HTTP $expected, got HTTP $code, body: $(body))"
  fi
}

assert_body() {
  local desc="$1" pattern="$2"
  if grep -q "$pattern" "$BODY_FILE"; then
    ok "$desc"
  else
    fail "$desc  (pattern '$pattern' not found in: $(body))"
  fi
}

assert_body_not() {
  local desc="$1" pattern="$2"
  if grep -q "$pattern" "$BODY_FILE"; then
    fail "$desc  (unexpected pattern '$pattern' found in: $(body))"
  else
    ok "$desc"
  fi
}

# Run a sequence of ClientMain inputs in a single JVM invocation.
# Pass newline-separated option sequences; always end with 0 to exit.
client() {
  printf '%b\n' "$1" | mvn exec:java \
    -pl remote \
    -Dexec.classpathScope=test \
    -Dexec.mainClass=tools.vitruv.framework.remote.server.ClientMain \
    "-Dexec.args=localhost 8080 $CLIENT_TEMP" \
    -q 2>/dev/null
}

# Extract the 7-char short SHA from the last commit response body.
last_sha() {
  grep -o '"commitSha" *: *"[^"]*"' "$BODY_FILE" \
    | head -1 | grep -o '"[^"]*"$' | tr -d '"' | cut -c1-7
}

step "0. Setup"

echo "  Removing old test repo..."
rm -rf "$REPO"
mkdir -p "$REPO"
rm -rf "$CLIENT_TEMP"
mkdir -p "$CLIENT_TEMP"

cd "$REPO"
git init -q --initial-branch=master
git config user.email "test@example.com"
git config user.name "Test User"
git commit --allow-empty -m "init" -q
echo "  Repo initialised at $REPO"

cd "$VITRUV_SERVER"

echo "  Starting server..."
mvn exec:java \
  -pl remote \
  -Dexec.classpathScope=test \
  -Dexec.mainClass=tools.vitruv.framework.remote.server.ServerMain \
  "-Dexec.args=$REPO" \
  -q > /tmp/vt_server.log 2>&1 &
SERVER_PID=$!

echo -n "  Waiting for server to be ready"
READY=0
for i in $(seq 1 30); do
  if curl -s "$BASE/health" > /dev/null 2>&1; then
    READY=1
    echo " OK"
    break
  fi
  echo -n "."
  sleep 2
done

if [ "$READY" -eq 0 ]; then
  echo " TIMEOUT — server did not start within 60s"
  echo "  Server log:"
  tail -20 /tmp/vt_server.log
  exit 1
fi

step "1. Health Check"

CODE=$(curl_get "/health")
assert_status "GET /health" "$CODE" "200"
assert_body   "body says 'running'" "running"

step "2. View Types"

CODE=$(curl_get "/vsum/view/types")
assert_status "GET /vsum/view/types" "$CODE" "200"
assert_body   "contains 'default'" "default"

CODE=$(curl_get_h "/vsum/view/selector" "View-Type" "default")
assert_status "GET /vsum/view/selector with View-Type header" "$CODE" "200"

CODE=$(curl_get "/vsum/view/selector")
assert_status "GET /vsum/view/selector without header returns 405" "$CODE" "405"

step "3. Branch Lifecycle"

CODE=$(curl_get "/vsum/branch")
assert_status "GET /vsum/branch (fresh)" "$CODE" "200"
assert_body   "master present" "master"
assert_body   "master parentBranch is self-referential" '"parentBranch"'

CODE=$(curl_get "/vsum/branch/topology")
assert_status "GET /vsum/branch/topology (fresh)" "$CODE" "200"
assert_body   "topology has master key" "master"

CODE=$(curl_get "/vsum/branch/nonexistent")
assert_status "GET non-existent branch returns 405" "$CODE" "405"

CODE=$(curl_get "/vsum/branch/master/state")
assert_status "GET master branch state" "$CODE" "200"
assert_body   "state is ACTIVE" "ACTIVE"

step "5. Model Content (ClientMain)"

echo "  Creating system model on master..."
client "1\n$MODEL_PATH\n0"

CODE=$(curl_post "/vsum/commit" '{"message":"init system model"}')
assert_status "POST /vsum/commit (init)" "$CODE" "200"
assert_body   "response has commitSha" "commitSha"
INIT_SHA=$(last_sha)
echo "  init SHA: $INIT_SHA"

CODE=$(curl_post "/vsum/branch" '{"name":"feature/brake","fromBranch":"master"}')
assert_status "POST create feature/brake" "$CODE" "200"
assert_body   "response contains feature/brake" "feature/brake"

CODE=$(curl_post "/vsum/branch" '{"fromBranch":"master"}')
assert_status "POST create branch without name returns 500" "$CODE" "500"

CODE=$(curl_post "/vsum/branch" '{"name":"feature/pump","fromBranch":"master"}')
assert_status "POST create feature/pump" "$CODE" "200"

CODE=$(curl_get "/vsum/branch")
assert_status "GET /vsum/branch now has 3 entries" "$CODE" "200"
assert_body   "feature/brake listed" "feature/brake"
assert_body   "feature/pump listed"  "feature/pump"

CODE=$(curl_get "/vsum/branch/topology")
assert_status "GET /vsum/branch/topology after branches created" "$CODE" "200"
assert_body   "master has children" "feature/brake"

CODE=$(curl_get "/vsum/branch/feature%2Fbrake")
assert_status "GET /vsum/branch/feature%2Fbrake" "$CODE" "200"
assert_body   "parentBranch is master" "master"

CODE=$(curl_get "/vsum/branch/feature%2Fbrake/state")
assert_status "GET branch state" "$CODE" "200"
assert_body   "state is ACTIVE" "ACTIVE"

CODE=$(curl_post "/vsum/branch/feature%2Fbrake/switch")
assert_status "POST switch to feature/brake" "$CODE" "200"
assert_body   "response is feature/brake" "feature/brake"

CODE=$(curl_patch "/vsum/branch/feature%2Fbrake/maturity" '{"maturity":"REVIEWED"}')
assert_status "PATCH maturity to REVIEWED" "$CODE" "200"

CODE=$(curl_get "/vsum/branch/feature%2Fbrake")
assert_body   "maturity persisted as REVIEWED" "REVIEWED"
ok "maturity persisted"

CODE=$(curl_patch "/vsum/branch/feature%2Fbrake/maturity" '{"maturity":"INVALID"}')
assert_status "PATCH invalid maturity returns 400" "$CODE" "400"

CODE=$(curl_post "/vsum/branch/master/switch")
assert_status "POST switch back to master" "$CODE" "200"

CODE=$(curl_post "/vsum/branch/feature%2Fbrake/switch")
assert_status "POST switch to feature/brake for model work" "$CODE" "200"

echo "  Adding BrakeController and BrakeSensor on feature/brake..."
client "3\nBrakeController\n3\nBrakeSensor\n0"

CODE=$(curl_post "/vsum/commit" '{"message":"add brake components"}')
assert_status "POST /vsum/commit (brake components)" "$CODE" "200"
BRAKE_SHA=$(last_sha)
echo "  brake SHA: $BRAKE_SHA"

CODE=$(curl_post "/vsum/branch/master/switch")
assert_status "POST switch back to master" "$CODE" "200"

CODE=$(curl_post "/vsum/branch/feature%2Fpump/switch")
assert_status "POST switch to feature/pump" "$CODE" "200"

echo "  Adding PumpController on feature/pump..."
client "3\nPumpController\n0"

CODE=$(curl_post "/vsum/commit" '{"message":"add pump component"}')
assert_status "POST /vsum/commit (pump component)" "$CODE" "200"
PUMP_SHA=$(last_sha)
echo "  pump SHA: $PUMP_SHA"

CODE=$(curl_post "/vsum/branch/feature%2Fbrake/switch")
assert_status "POST switch to feature/brake for rename" "$CODE" "200"

echo "  Renaming BrakeController to BrakeECU..."
client "5\n1\nBrakeECU\n0"

CODE=$(curl_post "/vsum/commit" '{"message":"rename BrakeController to BrakeECU"}')
assert_status "POST /vsum/commit (rename)" "$CODE" "200"
RENAME_SHA=$(last_sha)
echo "  rename SHA: $RENAME_SHA"

CODE=$(curl_post "/vsum/commit" '{"message":""}')
assert_status "POST /vsum/commit blank message returns 400" "$CODE" "400"

step "7. List Commits"

CODE=$(curl_get "/vsum/commit/feature%2Fbrake")
assert_status "GET /vsum/commit/feature%2Fbrake" "$CODE" "200"
assert_body   "rename commit present" "rename"

CODE=$(curl_get "/vsum/commit/master")
assert_status "GET /vsum/commit/master" "$CODE" "200"
assert_body   "init commit present" "init"

step "8. Changelog"

CODE=$(curl_get "/vsum/changelog/feature%2Fbrake/$RENAME_SHA")
assert_status "GET changelog for rename commit" "$CODE" "200"
assert_body   "changelog has fileChanges" "fileChanges"

CODE=$(curl_get "/vsum/changelog/feature%2Fbrake/0000000")
assert_status "GET changelog unknown SHA returns 405" "$CODE" "405"

FULL_SHA="${RENAME_SHA}000000000000000000000000000000000"
CODE=$(curl_get "/vsum/changelog/feature%2Fbrake/$RENAME_SHA")
assert_status "GET changelog short SHA accepted" "$CODE" "200"

step "9. Branch History"

CODE=$(curl_get "/vsum/branch/feature%2Fbrake/history")
assert_status "GET /vsum/branch/feature%2Fbrake/history" "$CODE" "200"
assert_body   "history has commits"     "commits"
assert_body   "history has changePreview" "changePreview"

step "10. Delta"

CODE=$(curl_get "/vsum/delta/feature%2Fbrake?base=master")
assert_status "GET delta feature/brake vs master" "$CODE" "200"
assert_body   "delta has commits" "commits"

CODE=$(curl_get "/vsum/delta/feature%2Fpump?base=master")
assert_status "GET delta feature/pump vs master" "$CODE" "200"
assert_body   "PumpController in delta" "PumpController"

CODE=$(curl_get "/vsum/delta/feature%2Fbrake")
assert_status "GET delta without base returns 405 (main not found)" "$CODE" "405"

step "11. Conflict Preview"

CODE=$(curl_get "/vsum/branch/feature%2Fbrake/conflicts?base=master")
assert_status "GET conflicts (no conflicts)" "$CODE" "200"
assert_body   "conflictCount is 0" '"conflictCount" : 0'

step "12. Merge"

CODE=$(curl_post "/vsum/branch/master/switch")
assert_status "POST switch to master before merge" "$CODE" "200"

CODE=$(curl_post "/vsum/merge" '{"sourceBranch":"feature/brake","deleteAfterMerge":false}')
assert_status "POST merge feature/brake into master" "$CODE" "200"
assert_body   "merge successful" '"successful" : true'

CODE=$(curl_post "/vsum/merge" \
  '{"sourceBranch":"feature/pump","deleteAfterMerge":false,"resolutionStrategy":"THEIRS"}')
assert_status "POST merge feature/pump with THEIRS" "$CODE" "200"
assert_body   "merge with THEIRS successful" '"successful" : true'

CODE=$(curl_post "/vsum/merge" '{"sourceBranch":""}')
assert_status "POST merge blank sourceBranch returns 400" "$CODE" "400"

step "13. Delete Branch"

CODE=$(curl_delete "/vsum/branch/feature%2Fbrake")
assert_status "DELETE /vsum/branch/feature%2Fbrake" "$CODE" "200"

CODE=$(curl_get "/vsum/branch/feature%2Fbrake/state")
assert_status "GET state of deleted branch" "$CODE" "200"
assert_body   "state is DELETED" "DELETED"

step "14. Versioning"

CODE=$(curl_post "/vsum/branch/master/switch")
assert_status "POST switch to master for versioning" "$CODE" "200"

CODE=$(curl_post "/vsum/version" '{"versionId":"v1.0","description":"stable baseline"}')
assert_status "POST create version v1.0" "$CODE" "200"
assert_body   "v1.0 in response" "v1.0"

CODE=$(curl_post "/vsum/version" '{"versionId":"v1.1"}')
assert_status "POST create version v1.1 (no description)" "$CODE" "200"

CODE=$(curl_post "/vsum/version" '{"description":"no id"}')
assert_status "POST version without versionId returns 400" "$CODE" "400"

CODE=$(curl_get "/vsum/version")
assert_status "GET /vsum/version list" "$CODE" "200"
assert_body   "v1.0 in list" "v1.0"
assert_body   "v1.1 in list" "v1.1"

CODE=$(curl_delete "/vsum/version/v1.1")
assert_status "DELETE /vsum/version/v1.1" "$CODE" "200"

CODE=$(curl_get "/vsum/version")
assert_status "GET version list after delete" "$CODE" "200"
assert_body_not "v1.1 no longer listed" "v1.1"

CODE=$(curl_get "/vsum/version/v1.0")
assert_status "GET /vsum/version/v1.0" "$CODE" "200"
assert_body   "description present" "stable baseline"

CODE=$(curl_get "/vsum/version/nonexistent")
assert_status "GET non-existent version returns 405" "$CODE" "405"

CODE=$(curl_get "/vsum/version/v1.0/model")
assert_status "GET model snapshot (text)" "$CODE" "200"

CODE=$(curl_get "/vsum/version/v1.0/model?format=json")
assert_status "GET model snapshot (json)" "$CODE" "200"
assert_body   "JSON has models key" "models"

CODE=$(curl_get "/vsum/version/v1.0/model?format=mermaid")
assert_status "GET model snapshot (mermaid)" "$CODE" "200"
assert_body   "mermaid output has graph keyword" "graph"

CODE=$(curl_get "/vsum/version/v1.0/view")
assert_status "GET model view (HTML)" "$CODE" "200"
assert_body   "HTML page returned" "<html"

echo "  Adding post-version component..."
client "3\nPostVersionComponent\n0"
CODE=$(curl_post "/vsum/commit" '{"message":"post-version: add component"}')
assert_status "POST /vsum/commit after version tag" "$CODE" "200"

CODE=$(curl_post "/vsum/version/v1.0/rollback/preview")
assert_status "POST rollback preview for v1.0" "$CODE" "200"
assert_body   "preview has commitsToAbandon" "commitsToAbandon"

CODE=$(curl_post "/vsum/version/v1.0/rollback/confirm")
assert_status "POST rollback confirm to v1.0" "$CODE" "200"
assert_body   "rollback successful" '"successful" : true'

CODE=$(curl_post "/vsum/version/nonexistent/rollback/preview")
assert_status "POST rollback preview unknown version returns 405" "$CODE" "405"

CODE=$(curl_post "/vsum/version/v1.0/branch" '{"branchName":"hotfix/from-v1.0"}')
assert_status "POST create branch from version v1.0" "$CODE" "200"
assert_body   "hotfix branch returned" "hotfix/from-v1.0"

echo ""
echo -e "${CYAN}========================================${NC}"
if [ "$FAIL" -eq 0 ]; then
  echo -e "  ${GREEN}All $PASS tests passed.${NC}"
else
  echo -e "  ${GREEN}Passed: $PASS${NC}  ${RED}Failed: $FAIL${NC}"
fi
echo -e "${CYAN}========================================${NC}"
echo ""

kill "$SERVER_PID" 2>/dev/null || true
echo "Server stopped."

[ "$FAIL" -eq 0 ]
