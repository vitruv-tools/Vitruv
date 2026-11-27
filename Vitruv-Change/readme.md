# Vitruv Change
[![GitHub Action CI](https://github.com/vitruv-tools/Vitruv-Change/actions/workflows/ci.yml/badge.svg)](https://github.com/vitruv-tools/Vitruv-Change/actions/workflows/ci.yml)
[![Latest Release](https://img.shields.io/github/release/vitruv-tools/Vitruv-Change.svg)](https://github.com/vitruv-tools/Vitruv-Change/releases/latest)
[![Issues](https://img.shields.io/github/issues/vitruv-tools/Vitruv-Change.svg)](https://github.com/vitruv-tools/Vitruv-Change/issues)
[![License](https://img.shields.io/github/license/vitruv-tools/Vitruv-Change.svg)](https://raw.githubusercontent.com/vitruv-tools/Vitruv-Change/main/LICENSE)

[Vitruvius](https://vitruv.tools) is a framework for view-based (software) development.
It assumes different models to be used for describing a system, which are automatically kept consistent by the framework executing (semi-)automated rules that preserve consistency.
These models are modified only via views, which are projections from the underlying models.
For general information on Vitruvius, see our [GitHub Organisation](https://github.com/vitruv-tools) and our [Wiki](https://github.com/vitruv-tools/.github/wiki).

This project contains the underlying definition of changes in Ecore-based models and interfaces for specifying the propagation of changes between models to preserve their consistency with the central interface `ChangePropagationSpecification`, as well as an interface and a default implementation for orchestrating the execution of such specifications.
In addition, interactions to involve the user into the change preservation process are provided.

## Module Overview

| Name             | Description                                                       |
|------------------|-------------------------------------------------------------------|
| atomic           | Definition of the atomic change meta-model.                       |
| composite        | Definition of complex changes as a sequence of atomic changes.    |
| propagation      | Applying changes to update model states.                          |
| changederivation | Derivation of changes from an updated model state.                |
| interaction      | Requesting input from developers during consistency preservation. |
| correspondence   | Definition of annotatable mappings between model elements.        |
| *testutils*      | *Utilities for testing in Vitruvius or V-SUM projects.*           |
| *utils*          | *Utilities for Vitruvius or V-SUM projects.*                      |
