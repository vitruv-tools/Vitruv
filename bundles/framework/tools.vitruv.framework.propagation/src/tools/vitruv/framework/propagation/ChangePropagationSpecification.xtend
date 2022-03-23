package tools.vitruv.framework.propagation

import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.propagation.ResourceAccess
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.Metamodel

/**
 * A specification of change propagation, which is able to process changes
 * and update other, dependent models to reflect these changes as well.
 */
interface ChangePropagationSpecification extends ChangePropagationObservable {
	/**
	 * Sets the <code>UserInteractor</code> to be used by this <code>ChangePropagationSpecification</code>
	 * whenever the user needs to be involved into the decision how to propagate a change. This may
	 * happen during the execution of this specification, i.e., when calling {@link #propagateChange}.
	 * 
	 * @param userInteractor	the {@link UserInteractor} to be used when executing this specification.
	 * 							May be <code>null</code> but can then lead to exceptions if it is accessed 
	 * 							within {@link #propagateChange}.
	 */
	def void setUserInteractor(UserInteractor userInteractor)

	/**
	 * Returns the metamodel containing the namespace URIs of the root packages of the metamodel of elements
	 * whose changes are handled by this <code>ChangePropagationSpecification</code>.
	 * 
	 * @return a metamodel representing the source namespace URIs 
	 */
	def Metamodel getSourceMetamodel()

	/**
	 * Returns the metamodel containing the namespace URIs of the root packages of the metamodel of elements
	 * whose are changed by this <code>ChangePropagationSpecification</code> when executing {@link #propagateChange}.
	 * 
	 * @return a metamodel representing the target namespace URIs
	 */
	def Metamodel getTargetMetamodel()

	/**
	 * Returns whether this <code>ChangePropagationSpecification</code> handles the given change, i.e.,
	 * whether {@link #propagateChange} will perform changes when applied to that change.
	 * 
	 * @param change				the atomic change for which to check whether this specification reacts 
	 * 								to it. Must not be <code>null</code>.
	 * @param correspondenceModel	the correspondence model to retrieve information about the target
	 * 								model from. Must not be <code>null</code>.
	 * @return	<code>true</code> if {@link #propagateChange} will perform modifications in response to the
	 * 			given change, <code>false</code> otherwise
	 */
	def boolean doesHandleChange(EChange change, CorrespondenceModel correspondenceModel)

	/**
	 * Performs modifications in target models identified by accessing the given <code>CorrespondenceModel</code>
	 * for the elements changed by the given <code>EChange</code> in order to reflect the changes in the
	 * target model.
	 * 
	 * @param change 			  	the atomic change which shall be propagated. Should be in an instance of a
	 * 								source metamodel of this specification (see {@link #getSourceMetamodelRootNsUris}).
	 * 								Must not be <code>null</code>.
	 * @param correspondenceModel 	the correspondence model to retrieve information about the target
	 * 								model from. Must not be <code>null</code>.
	 * @param resourceAccess		an object for resource access, in particular to create new model files.
	 * 								Must not be <code>null</code>.
	 */
	def void propagateChange(EChange change, CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess)
}
