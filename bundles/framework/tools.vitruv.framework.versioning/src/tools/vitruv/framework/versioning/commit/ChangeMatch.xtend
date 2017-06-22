/** 
 */
package tools.vitruv.framework.versioning.commit

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.TransactionalChange
import java.util.Map
import java.util.List
import java.io.Serializable

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Change Match</b></em>'.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getChangeMatch()
 * @model
 * @generated
 */
interface ChangeMatch extends EObject, Serializable { // ChangeMatch
	def VURI getOriginalVURI()

	def TransactionalChange getOriginalChange()

	def Map<VURI, List<TransactionalChange>> getTargetToCorrespondentChanges()

	def void setOriginalVURI(VURI vuri)

	def void setOriginalChange(TransactionalChange t)

	def void setTargetToCorrespondentChanges(Map<VURI, List<TransactionalChange>> m)
}
