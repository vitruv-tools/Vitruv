# Vitruv
[![GitHub Action CI](https://github.com/vitruv-tools/Vitruv/workflows/CI/badge.svg)](https://github.com/vitruv-tools/Vitruv/actions?query=workflow%3ACI)
[![Issues](https://img.shields.io/github/issues/vitruv-tools/Vitruv.svg)](https://github.com/vitruv-tools/Vitruv/issues)
[![License](https://img.shields.io/github/license/vitruv-tools/Vitruv.svg)](https://raw.githubusercontent.com/vitruv-tools/Vitruv/master/LICENSE)

Vitruv is a framework for view-based software development. It assumes different models to be used for describing a software system,
which are automatically kept consistent by the framework and its applications.

## Installation
Vitruv can be installed in Eclipse Oxygen via the [nightly update site](http://vitruv.tools/updatesite/nightly). A wiki page provides [detailled instructions for using or extending Vitruv](https://github.com/vitruv-tools/Vitruv/wiki/Getting-Started).

## Project Development

Vitruv is short for Vitruvius (VIew-cenTRic engineering Using a VIrtual Underlying Single model) and is developed at the
Chair for Software Design and Quality (SDQ) at the Karlsruhe Institute of Technology (KIT): http://sdq.ipd.kit.edu/

The project page can be found at: https://sdqweb.ipd.kit.edu/wiki/Vitruvius

## Project Basics

Vitruv is realized as Eclipse Plug-ins and makes use of the Eclipse Modeling Framework (EMF).
It depends on the following Eclipse tools:
- EMF
- Xtext
- Xtend

## Project Structure

The project is structured as follows:

| Section | Description |
| ------- | ----------- |
| Framework | The framework consists of the central elements for defining a virtual single underlying model (VSUM) and provides extension points for the different domains and applications to be used with the framework. |
| Extensions | Extensions extend the framework with new functionality and can be used by different domains and applications. |
| DSLs | The provided DSLs can be used to specify consistency-restoring transformations between two different domains. They generate code that is used to extend the framework for keeping instances of a specific pair of models consistent. |
| Domains | A domain defines the necessary concepts for one metamodel. It defines the metamodel namespaces a domain consists of, defines utilities specific for this metamodel and defines the way in which editors for this metamodel are watched for changes.
| Applications | An application is defined for a pair of domains. It essentially consists of the consistency-restoring transformations that keep model instances of these two domains consistent. It can also define further features for the domains, e.g. the integration of existing models.
| Views | A view is defined for a single or a set of domains. It specifies a specify view on the models, e.g. a UML class diagram view on Java code. |
| Build | Build projects define features of the Vitruv project and information for their continuous integration. |
