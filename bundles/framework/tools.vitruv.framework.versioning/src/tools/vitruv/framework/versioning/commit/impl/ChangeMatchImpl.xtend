/** 
 */
package tools.vitruv.framework.versioning.commit.impl

import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.commit.ChangeMatch
import tools.vitruv.framework.versioning.commit.CommitPackage

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Change Match</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
class ChangeMatchImpl extends MinimalEObjectImpl.Container implements ChangeMatch {
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected new() {
		super()
	}

	@Accessors(PUBLIC_GETTER)
	static val serialVersionUID = 1L
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	VURI originalVURI
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	TransactionalChange originalChange
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	Map<VURI, List<TransactionalChange>> targetToCorrespondentChanges

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override protected EClass eStaticClass() {
		return CommitPackage::Literals::CHANGE_MATCH
	} // ChangeMatchImpl
}
