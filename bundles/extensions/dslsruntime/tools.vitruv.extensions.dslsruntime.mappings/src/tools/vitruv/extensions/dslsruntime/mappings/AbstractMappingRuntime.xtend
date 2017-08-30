package tools.vitruv.extensions.dslsruntime.mappings

import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IElementsRegistry
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingRegistry4Reactions
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingRegistry4DependingCandidatesGenerators
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IBothSidesCombiningRegistry
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.ILeftAndRightCandidatesRegistry
import tools.vitruv.extensions.dslsruntime.mappings.impl.SetMultimapElementsAndHashMapCandidatesAndInstanceHalvesRegistry

/**
 * <p>Base class instantiated by all runtime registry classes that are generated
 * for mappings defined with the mappings language.</p>
 * 
 * <p>It is the <b>only entry point to all mapping platform code</b> from reactions or Xtend code.
 * It provides, for example, access to methods for maintaining runtime information about instances of relevant
 * metaclasses ({@link IElementsRegistry getElementsRegistry()}) and to current instances of mappings as well as to
 * candidates for instances of mappings 
 * (see {@link tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingRegistry4Reactions IMappingRegistry4Reactions}.
 * </p>
 * 
 * <p>Access to and manipulation of runtime information for mappings is restricted and realized in three different ways:</p>
 * 
 * <p>First, only the methods of {@link tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingRegistry4Reactions IMappingRegistry4Reactions} 
 * are accessible from generated reactions code. (This is achieved by delegating these methods to the {@link #registry} field.)</p>
 * 
 * <p>Second, the methods of {@link tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingRegistry4DependingCandidatesGenerators IMappingRegistry4DependingCandidatesGenerators}
 * are made available to generated Xtend code but not to reactions code. (This is realized by generating a package protected getter 
 * in the generated mapping class. This getter delegates to the protected getter in AbstractMappingRuntime, which is a superclass of the generated mapping class.)</p>
 * 
 * <p>Third and last, the methods of {@link tools.vitruv.extensions.dslsruntime.mappings.interfaces.IElementsRegistry IElementsRegistry} 
 * and {@link tools.vitruv.extensions.dslsruntime.mappings.interfaces.ILeftAndRightInstanceHalvesRegistry ILeftAndRightInstanceHalvesRegistry}
 * are made available to generated Xtend code but not to reactions code via ordinary protected getters.</p>
 */
abstract class AbstractMappingRuntime<L extends IMappingInstanceHalf, R extends IMappingInstanceHalf> implements IMappingRegistry4Reactions<L,R> {
	@Delegate
	val SetMultimapElementsAndHashMapCandidatesAndInstanceHalvesRegistry<L,R> registry
	
	/** 
	 * @param mappingName the name of the mapping for debugging and error messages.
	 */
	protected new(String mappingName) {
		this.registry = new SetMultimapElementsAndHashMapCandidatesAndInstanceHalvesRegistry(mappingName)
		// TODO MK reestablish all mapping runtime information from previous runs of Vitruvius
		// As discussed with HK and JG, the goal is to visit all correspondences after they were loaded
		// and to build up the instance part of the registry by looking at the correspondence tags.
		// The elements part of the registry has to be reestablished by visiting 
		// all contents of all models and afterwards the candidates part has to be restablished by computing
		// all cartesian products of these elements but subtracting the instances that were restablished from the correspondences.
	}
	
	protected def IMappingRegistry4DependingCandidatesGenerators<L,R> getRegistry4DependingCandidatesGenerator() {
		return registry
	}
	
	protected def IElementsRegistry getElementsRegistry() {
		return registry.elementsRegistry
	}
	
	protected def ILeftAndRightCandidatesRegistry<L,R> getLeftAndRightRegistry() {
		return registry.leftAndRightRegistry
	}
	
	protected def IBothSidesCombiningRegistry<L,R> getBothSidesCombiningRegistry() {
		return registry
	}
}