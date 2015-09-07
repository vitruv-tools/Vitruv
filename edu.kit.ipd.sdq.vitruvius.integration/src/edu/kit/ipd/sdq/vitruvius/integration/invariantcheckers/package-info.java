/**
 * Contains all base classes and some helpers that are needed for checking and enforcing invariants on models that should be integrated into
 * Vitruvius. 
 * 
 * Every "StandardInvariantEnforcer" has a certain "source" model(type) on which invariants are enforced. The "target" model implicitly defines which invariant should be checked/enforced.
 * It is explicitly defines via a marker interface. See project documentation for more details on this subject.
 * 
 * "InvariantEnforcerFacade" is (dynamically) configurable to support different types of models. A strategy-object can be passed that defines which elements of the model should be checked.
 * The "real" enforcer inside this class checks for invariants and enforces them. This facade class is used to solve simple syntactical/idenfitier conflicts, where conflicting model elements are independent from other elements.
 * 
 * 
 */
package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers;