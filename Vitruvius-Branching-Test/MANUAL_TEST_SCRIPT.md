# Manual Test Script – Vitruvius Branching

This document describes manual end-to-end tests for validating:

- Git hook integration
- VSUM reload behavior
- Branch switching
- Merge handling (fast-forward & non-fast-forward)
- Conflict resolution
- Model synchronization & metadata generation

---

## Prerequisites

- Vitruvius built (`mvn clean install`)
- Fresh test repository
- Two terminals:
    - Terminal 1 → ManualTestHarness
    - Terminal 2 → Git operations

---

# Setup


## Terminal 2 - Initialize Vitruvius Watcher
```bash
cd ~/abschlussarbeiten/Vitruvius-Branching-Test
mvn exec:java -pl vsum \
    -Dexec.mainClass="tools.vitruv.methodologisttemplate.vsum.ManualTest" \
    -Dexec.args="$HOME/vitruvius-manual-test" \
    -Dexec.classpathScope=test
```

## Terminal 1 – Initialize Test Environment

```bash
rm -rf ~/vitruvius-manual-test
mkdir ~/vitruvius-manual-test
cd ~/vitruvius-manual-test
git init
```

After executing this command, you can see different options to interact with Vitruvius.

## Test 1: Create example models via Vitruvius
On Terminal 1, please choose option 1 (Create models (via Vitruv API))
```
1
```
Expected: 
- example.model (System)
- example.model2 (Root - created by reactions)

On Terminal 2, verify whether the models were created correctly
```bash
ls -la # should include vsum/
cat vsum/models.models # should include paths to 2 model files
cat vsum/uuid.uuid # should include uuid of elements from both models

git add . # stage all the model files
git commit -m "initial: create models via Vitruvius" # commit the model files

# since we are committing model files, a changelog file will be automatically created under .vitruvius/changelogs/
# this file needs to be committed also. You can commit it now or later before switching to another branch.
git commit -am "added changelog"

# verify the changelog
ls .vitruvius/changelogs/
cat .vitruvius/changelogs/<commit-sha>.txt
```

## Test 2: Add a new Component (on master branch)
Purpose: Validate model update propagation and changelog generation.
On Terminal 1, please choose option 3 and enter the component name that you want: Add component to system.\
By doing that, you are adding a new Component to the System (example.model). The Root (example.model2) will also be 
changed automatically by Vitruvius's CPR.


```
3
WebServer
```

On Terminal 2:
```bash
git commit -am "add WebServer component" # add and commit the change on example.model

cat example.model # here you should see that the WebServer Component is now existent in the System also
cat example.model2 # here you should see that the WebServer Component is now existent in the Root also

git commit -am "added changelog"
```


## Test 3: Create a new branch and add a Router to System
Purpose: Validate post-checkout reload and branch-specific modifications.
On Terminal 2
```bash
git checkout -b feature-router
git status # now you can see a feature-router.metadata is created automatically. This file contains branch metadata
```
Expected from Terminal 1: ReloadWatcher is triggered and VirtualModel is reloaded.
Now on Terminal 1, choose option 4 to add a new Router to the System with a defined name: Add router to system.
```bash
4
MainRouter
```
Verify on Terminal 2:
```bash
git commit -am "add MainRouter"

cat example.model # here you should see that the MainRouter is now existent in the System also
cat example.model2 # here you should see that the MainRouter is now existent in the Root also
cat vsum/uuid.uuid # uuid to the MainRouter element should exist also

git commit -am "added changelog"
```

## Test 4: Switch back to master branch
Purpose: Ensure branch isolation.\
On Terminal 2
```bash
git checkout master
```
On Terminal 1, please choose option 2: View current models
```bash
2 # Only WebServer should be present, not MainRouter
```
## Test 5: Switch to feature branch again.
Purpose: Verify the correct restoration of branch state.
On Terminal 2
```bash
git checkout feature-router
```
On Terminal 1, please choose option :
```bash
2 # Both WebServer and MainRouter should be present.
```
## Test 6: Merge feature branch into master
Purpose: Validate merge watcher behavior and metadata generation.
### Scenario 1: Fast-forward merge
For fast-forward merge, git only moves the pointer of the branch forward, so no merge commit will be created. In this case,
we also neither expect a changelog for commit nor a merge metadata to be created under .vitruvius/ \
On Terminal 2:
```bash
git checkout master
git merge feature-router
ls .vitruvius/merges/ # should not exist
```
On Terminal 1, please choose option 2 to check if now, the MainRouter is also present for master branch:
```
2
```
### Scenario B: Non-fast-forward merge
Here we first revert our previous merge.\
On Terminal 2:
```bash
git reset --hard HEAD~2
echo "test" > README.md
git add .
git commit -m "diverge"
git merge --no-ff feature-router
ls .vitruvius/merges/ # should now exist .vitruvius/merges/<merge-commit-sha>.metadata
cat .vitruvius/merges/<merge-commit-sha>.metadata # read the logged merge metadata
```

## Test 7: Rename Component
Purpose: Verify rename propagation and semantic changelog.\
On Terminal 1, please first choose option 5, select Component 1 (WebServer) and choose a new name you want: 
```
5
1
WebServer-v2
```
On Terminal 2:
```bash
git commit -am "rename WebServer"
git commit -am "added changelog"
```
On Terminal 1:
```bash
2 # WebServer-v2 and MainRouter should be present
```

## Test 8: Delete Component
Purpose: Ensure delete propagation across model correspondences.\
On Terminal 1, please choose option 6 (Delete component) and select Component 2 (MainRouter):
```
6
2
```
On Terminal 2:
```bash
git commit -am "remove MainRouter"
git commit -am "added changelog"
```
On Terminal 1:
```bash
2 # MainRouter should disappear
```

## Test 9: Conflict Merge Scenario
Purpose: Test conflict handling and merge validation.\
### First, create a new feature branch A.
On Terminal 2:
```bash
git checkout -b branch-a
```
On Terminal 1, please choose option 3 to add a new Component with a defined name:
```
3
ComponentA
```
Then commit the changes on Terminal 2:
On Terminal 2:
```bash
git commit -am "add ComponentA"
git commit -am "added changelog"
```
### Now, create another feature branch B.
On Terminal 2:
```bash
git checkout master
git checkout -b branch-b
```
On Terminal 1, please choose option 3 to add a new Component with a defined name:
```
3
ComponentB
```
Then commit the changes on Terminal 2:
On Terminal 2:
```bash
git commit -am "add ComponentB"
git commit -am "added changelog"
```
### Finally, merge with conflicts
On Terminal 2:
```bash
git checkout branch-a
git merge branch-b
```

## Test 10: Delete all models
Purpose: Verify deletion of all models\
On Terminal 1, please first choose option 7: Delete all models and confirm yes
```
7
yes
```
On Terminal 2:
```bash
git commit -am "delete all models"
git commit -am "added changelog"
ls example.model* # should be empty
cat vsum/models.models
cat vsum/uuid.uuid
```
On Terminal 1:
```bash
2 No model should be present
```

