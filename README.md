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

## Installation

Vitruvius can be installed in Eclipse via the [nightly update site](https://vitruv.tools/updatesite/nightly). A wiki page provides [detailed instructions for using or extending Vitruvius or parts of it](https://github.com/vitruv-tools/.github/wiki/Getting-Started).

## Framework-internal Dependencies

This project depends on the following other projects from the Vitruvius framework:
- [Vitruv-Change](https://github.com/vitruv-tools/Vitruv-Change)
