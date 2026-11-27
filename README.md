# Vitruv
[![GitHub Action CI](https://github.com/vitruv-tools/Vitruv/actions/workflows/ci.yml/badge.svg)](https://github.com/vitruv-tools/Vitruv/actions/workflows/ci.yml)
[![Latest Release](https://img.shields.io/github/release/vitruv-tools/Vitruv.svg)](https://github.com/vitruv-tools/Vitruv/releases/latest)
[![Issues](https://img.shields.io/github/issues/vitruv-tools/Vitruv.svg)](https://github.com/vitruv-tools/Vitruv/issues)
[![License](https://img.shields.io/github/license/vitruv-tools/Vitruv.svg)](https://raw.githubusercontent.com/vitruv-tools/Vitruv/main/LICENSE)

[Vitruvius](https://vitruv.tools) is a framework for view-based (software) development.
It assumes different models to be used for describing a system, which are automatically kept consistent by the framework executing (semi-)automated rules that preserve consistency.
These models are modified only via views, which are projections from the underlying models.
For general information on Vitruvius, see our [GitHub Organisation](https://github.com/vitruv-tools) and our [Wiki](https://github.com/vitruv-tools/.github/wiki).

This project contains the central Vitruvius framework, providing the definition of a V-SUM (Virtual Single Underlying Model) containing development artifacts to be kept consistent and to be accessed and modified via views.
In the implementation, a V-SUM is called `VirtualModel`, which is instantiated with a set of `ChangePropagationSpecifications` (no matter whether they are developed with the [Vitruv-DSLs](https://github.com/vitruv-tools/Vitruv-DSLs) or just as an implementation of the interface defined in the [Vitruv-Change](https://github.com/vitruv-tools/Vitruv-Change) repository).
The `VirtualModel` then provides functionality to derive and modify views and to propagate the changes in these views back to the `VirtualModel`, which then executes the `ChangePropagationSpecifications` to preserve consistency.

## Constraints

A `VirtualModel` can check constraints on its models, independently of the constraint language. A language is plugged in as a `ConstraintChecker` (package `tools.vitruv.framework.vsum.constraints`), e.g. the `VitruvOclConstraintChecker` of [VitruviusOCL](https://github.com/vitruv-tools/VitruviusOCL):

```java
VirtualModel vsum = new VirtualModelBuilder()
    .withStorageFolder(Path.of("vsum"))
    .withChangePropagationSpecifications(...)
    .withConstraintChecker(VitruvOclConstraintChecker.fromClasspath("constraints/prices.ocl"))
    .buildAndInitialize();

ConstraintValidationReport report = vsum.validateConstraints();          // all constraints
report = vsum.validateConstraints(List.of("shop::Item::positivePrice")); // selected constraints
```

- `getConstraints()` lists the constraints of all registered checkers with id, name, context type, severity, source, and the metamodels they read (`metamodels`, at least the metamodel of the context type).
- The `ConstraintValidationReport` tells per constraint whether it is satisfied; each violation is a `ResolvedConstraintViolation` with the violating `EObject`, its `Uuid` in the V-SUM, its resource and a message. `affectedResourceUris()` returns the models that contain violations.
- Checkers that implement `EditableConstraintChecker` allow `createConstraint`, `updateConstraint` and `deleteConstraint` while the V-SUM is running. Changes are stored in the storage folder and survive a restart; this includes the constraints the V-SUM was built with.

The [Vitruv-Server](https://github.com/vitruv-tools/Vitruv-Server) offers the same operations via REST.

### Validating automatically

Monitoring tools can be notified about the consistency of the V-SUM instead of polling it:

```java
VirtualModel vsum = new VirtualModelBuilder()
    // ...
    .withConstraintValidationAfterPropagation(true)   // or vsum.setConstraintValidationAfterPropagation(true)
    .buildAndInitialize();

vsum.addConstraintValidationListener((report, trigger) ->
    System.out.println(trigger + ": " + (report.isSatisfied() ? "consistent" : "inconsistent")));
```

- with validation after propagation enabled, all constraints are validated after every propagated change (`PROPAGATION`) and after every created, updated or deleted constraint (`CONSTRAINTS_CHANGED`); it is disabled by default
- `validateConstraints()` of all constraints always notifies the listeners (`REQUEST`); validations of selected constraints do not
- listeners run while the V-SUM does not change, so a report belongs to its current state; they must not modify the V-SUM and should return quickly
- failing checkers or listeners are logged and do not affect the propagated change

## Reading models

Besides views, a `VirtualModel` offers read access to its models, e.g. for monitoring or exporting:

```java
List<ModelDescriptor> models = vsum.getModels();     // URI, metamodels (name, nsURI), number of roots
List<EPackage> metamodels = vsum.getMetamodels();    // root packages of the metamodels in use

String firstRootUuid = vsum.readModels(access -> {
  Resource model = access.getModelResource(uri).orElseThrow();
  Uuid uuid = access.getUuid(model.getContents().get(0)).orElseThrow();
  return uuid.getRawValue();
});
```

`readModels` runs while the V-SUM does not change, so everything read in one call belongs to the same state. The models must not be modified through it (changes go through views), and the access must not be used after the call.

## Framework-internal Dependencies

This project depends on the following other projects from the Vitruvius framework:
- [Vitruv-Change](https://github.com/vitruv-tools/Vitruv-Change)

## Module Overview

| Name         | Description                                                                                  |
|--------------|----------------------------------------------------------------------------------------------|
| views        | Definition of view types on the underlying models.                                           |
| vsum         | Definition of V-SUMs with consistency preservation rules between meta-models and view types. |
| remote       | Client-server infrastructure for working with V-SUMs.                                        |
| *testutils*  | *Utilities for testing in Vitruvius or V-SUM projects.*                                      |


# Branching for Vitruvius

## Repository Structure

```
feature/branch-change-propagation/
├── Vitruv/                    # Vitruv core framework (branch layer added)
├── Vitruv-Change/             # Vitruv change metamodel (upstream, no changes)
├── Vitruv-Server/             # REST server (new REST Endpoints)
├── Vitruvius-Branching-Test/  # Integration tests and case study
└── README.md
```

Each folder is a self-contained export of the corresponding Git repository at the submitted branch.
The folders contain no `.git` directories.

| Folder | Source branch |
|---|---|
| `Vitruv/` | `feature/rest-api` |
| `Vitruv-Change/` | `main` |
| `Vitruv-Server/` | `feature/vcs` |
| `Vitruvius-Branching-Test/` | `master` |

## Folder Details

### Vitruv/

The Vitruv core framework. Vitruv maintains consistency between multiple related models
by propagating changes through a reactions-based rule system. Its central component is
the VSUM (Virtual Single Underlying Model), which holds all model resources and their
correspondence links.

Key modules of the original framework:

- `vsum/` contains VSUM lifecycle and resource management.
- `views/` handles view creation and change propagation.
- `applications/` contains the reactions interpreter and consistency preservation rules.
- `testutils/` provides shared test infrastructure used by downstream modules.

**Thesis contribution:** We added the `branch` and `versioning` packages to the `vsum` module,
and extended existing framework classes to integrate the branch layer into the VSUM lifecycle.

The `branch` package implements all branching, semantic changelog, and conflict detection logic.
The main classes are:

- `BranchAwareVirtualModel` wraps the VSUM and coordinates branch switching, committing,
  and merging across all subsystems.
- `BranchManager` creates, lists, switches, and deletes Git branches, and tracks branch
  metadata such as maturity state and parent relationships.
- `CommitManager` captures model changes and records them as Git commits. A post-commit
  hook amends each commit with the current VSUM state and triggers changelog generation.
- `MergeManager` orchestrates the full merge workflow: it runs the semantic pre-check,
  delegates to JGit for the actual file-level merge, and triggers post-merge VSUM
  reconstruction. It also supports pluggable conflict resolution via a
  `ConflictResolutionProvider`.
- `SemanticConflictDetector` finds the merge base of two branches, loads their changelogs
  from the Git object store, and classifies overlapping changes as direct conflicts or
  auto-resolvable pairs based on the change origin field.
- `SemanticMergeEngine` (adapted from the supervisor's reference implementation) replays
  changelog transactions against the live VSUM to detect indirect and replay-level conflicts.

The `storage` sub-package implements changelog recording. `SemanticChangeBuffer` collects
EChange objects between commits. `EChangeToEntryConverter` converts them to UUID-keyed
JSON records. `SemanticChangelogManager` writes those records into the Git object store
(not the working tree) so they survive branch switches without causing conflicts.
The `handler` sub-package contains `PostCommitHandler`, the background `VsumPostCommitWatcher`,
and `GitHookInstaller`. The `util` sub-package contains the trigger file classes that the
hook and watcher use to communicate.

The `versioning` package implements named version snapshots. `VersioningService` creates
version tags, supports rollback to a past model state, and allows creating a new branch
from a version tag. Its companion data classes represent version metadata, rollback previews,
and rollback results.

### Vitruv-Change/

Defines the change metamodel that the whole Vitruv ecosystem uses to represent model
modifications. Every edit made to a model is expressed as a typed `EChange` instance
before it is propagated to other models.

Key concepts:

- The `EChange` hierarchy contains atomic change types such as `CreateEObject`,
  `DeleteEObject`, `ReplaceSingleValuedEAttribute`, `InsertEReference`, and others.
- `VitruviusChange` is an ordered sequence of EChanges that together form one logical
  transaction.
- `changederivation/` derives EChange sequences by state-based diff between two model snapshots.

**contribution:** This folder contains the upstream Vitruv-Change repository as-is.
No source code changes were made. It is included so that the full build dependency chain
can be reproduced without network access.

### Vitruv-Server/

An existing server that wraps the Vitruv framework in an HTTP server, giving external
clients access to VSUM operations such as loading resources, applying changes, and querying
views over HTTP.

**contribution:** The `remote` Maven module was pre-existing. We extended it by
adding new endpoint classes for all branching, versioning, and merge functionalities
implemented in the Vitruv branch layer. The new endpoints cover branch lifecycle
(create, list, get single branch, switch, delete, set maturity), commit and changelog
retrieval, delta comparison between branches, conflict preview before merge,
merge triggering with an optional resolution strategy (THEIRS or OURS), and versioning
(create, list, get, retrieve model as text or JSON or a Mermaid diagram,
retrieve view as HTML, rollback preview, rollback confirm, branch from version, delete).
We also extended the existing routing infrastructure to support parameterised URL paths
such as `/vsum/branch/{name}` by adding path-segment and query-parameter decoding.

**Integration tests.** The 10 test classes in `Vitruv-Server/remote/src/test/java/`
cover all 42 endpoint classes with a total of 133 tests. The tests start an embedded
HTTP server on a random port, interact with it through the same HTTP client that
production users would use, and verify both success and error responses.

**End-to-end REST test script.** The `test-rest-api.sh` shell script at the root of
this folder starts a fresh server, runs all REST endpoints in sequence, and reports
each assertion as PASS or FAIL. All 100 assertions pass on the submitted code.

### Vitruvius-Branching-Test/

Integration tests that verify the full stack from model edit through to Git commit,
branch merge, and conflict detection. The domain model is based on the brake
system metamodel by Hagel.

**contribution:** We wrote the following test classes.

The case study (`BrakeCaseStudyIntegrationTest`) exercises four scenarios against the
brake system domain model:

- **S1 (clean merge):** two branches make non-overlapping changes to the model.
  We verify that the merged model contains both sets of changes and that no conflict
  is reported.
- **S2 (conflict detection and auto-resolution):** both branches modify the same
  model element. We verify that the semantic conflict detector reports the conflict
  and that THEIRS auto-resolution produces the expected model state.
- **S3 (versioning and rollback):** a version snapshot is created, the model is
  modified further, and then a rollback is performed. We verify that the model
  returns to the state captured in the snapshot.
- **S4 (branch lifecycle):** the scenario covers branch creation, switching, isolation
  of changes per branch, topology queries, maturity state transitions, and branch deletion.

Additional integration test classes (`CaseStudyIntegrationTest`,
`VitruviusModelVersioningIntegrationTest`, `BranchSwitchingIntegrationTest`,
`PerBranchVsumTest`) verify the core branch and versioning layer in isolation
using a simpler domain model.


## How to Build and Run

### Prerequisites

- Java 17 or later
- Maven 3.9 or later
- Git (used at runtime by the JGit-based VCS layer)
- Bash (for the end-to-end REST test script)

### 1. Build the Vitruv core

```bash
cd Vitruv/
./mvnw install -DskipTests
```
This installs all Vitruv framework artifacts into the local Maven repository so that
the downstream modules can resolve them.

### 2. Run the REST server integration tests
```bash
cd Vitruv-Server/remote/
../../mvnw test
```

This starts an embedded HTTP server on a random port, runs all integration tests,
and shuts the server down. The tests must be run from the `remote/` sub-directory.

### 3. Run the case study and branch layer tests

```bash
cd Vitruvius-Branching-Test/
./mvnw test
```
This runs the 8 case study tests and all branch layer unit and integration tests.

### 4. Run the end-to-end REST test script
```bash
cd Vitruv-Server/
bash test-rest-api.sh
```
The script starts a fresh server instance, runs all REST endpoints in order, and reports
each assertion as PASS or FAIL. The server is shut down automatically when the script ends.
