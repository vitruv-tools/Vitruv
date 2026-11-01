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
