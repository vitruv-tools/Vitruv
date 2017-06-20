/** 
 */
package tools.vitruv.framework.versioning.commit.impl

import java.util.Collection
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.NotificationChain
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.util.EObjectContainmentEList
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList
import org.eclipse.emf.ecore.util.InternalEList
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.author.impl.SignedImpl
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.CommitPackage
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.SimpleCommit

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Commit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.CommitImpl#getChecksum <em>Checksum</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.CommitImpl#getChanges <em>Changes</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.CommitImpl#getCommitmessage <em>Commitmessage</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.CommitImpl#getCommitsBranchedFromThis <em>Commits Branched From This</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.CommitImpl#getCommitsMergedFromThis <em>Commits Merged From This</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.CommitImpl#getIdentifier <em>Identifier</em>}</li>
 * </ul>
 * @generated
 */
abstract class CommitImpl extends SignedImpl implements Commit {
	/** 
	 * The default value of the '{@link #getChecksum() <em>Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChecksum()
	 * @generated
	 * @ordered
	 */
	protected static final long CHECKSUM_EDEFAULT = 1000L
	/** 
	 * The cached value of the '{@link #getChecksum() <em>Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChecksum()
	 * @generated
	 * @ordered
	 */
	protected long checksum = CHECKSUM_EDEFAULT
	/** 
	 * The cached value of the '{@link #getChanges() <em>Changes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<EChange> changes
	/** 
	 * The cached value of the '{@link #getCommitmessage() <em>Commitmessage</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitmessage()
	 * @generated
	 * @ordered
	 */
	protected CommitMessage commitmessage
	/** 
	 * The cached value of the '{@link #getCommitsBranchedFromThis() <em>Commits Branched From This</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitsBranchedFromThis()
	 * @generated
	 * @ordered
	 */
	protected EList<SimpleCommit> commitsBranchedFromThis
	/** 
	 * The cached value of the '{@link #getCommitsMergedFromThis() <em>Commits Merged From This</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitsMergedFromThis()
	 * @generated
	 * @ordered
	 */
	protected EList<MergeCommit> commitsMergedFromThis
	/** 
	 * The default value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifier()
	 * @generated
	 * @ordered
	 */
	protected static final int IDENTIFIER_EDEFAULT = 2000
	/** 
	 * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifier()
	 * @generated
	 * @ordered
	 */
	protected int identifier = IDENTIFIER_EDEFAULT

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected new() {
		super()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override protected EClass eStaticClass() {
		return CommitPackage.Literals.COMMIT
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override long getChecksum() {
		return checksum
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<EChange> getChanges() {
		if (changes === null) {
			changes = new EObjectContainmentEList<EChange>(EChange, this, CommitPackage.COMMIT__CHANGES)
		}
		return changes
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override CommitMessage getCommitmessage() {
		if (commitmessage !== null && commitmessage.eIsProxy()) {
			var InternalEObject oldCommitmessage = (commitmessage as InternalEObject)
			commitmessage = eResolveProxy(oldCommitmessage) as CommitMessage
			if (commitmessage !== oldCommitmessage) {
				if (eNotificationRequired())
					eNotify(
						new ENotificationImpl(this, Notification.RESOLVE, CommitPackage.COMMIT__COMMITMESSAGE,
							oldCommitmessage, commitmessage))
			}
		}
		return commitmessage
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def CommitMessage basicGetCommitmessage() {
		return commitmessage
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setCommitmessage(CommitMessage newCommitmessage) {
		var CommitMessage oldCommitmessage = commitmessage
		commitmessage = newCommitmessage
		if (eNotificationRequired())
			eNotify(
				new ENotificationImpl(this, Notification.SET, CommitPackage.COMMIT__COMMITMESSAGE, oldCommitmessage,
					commitmessage))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<SimpleCommit> getCommitsBranchedFromThis() {
		if (commitsBranchedFromThis === null) {
			commitsBranchedFromThis = new EObjectWithInverseResolvingEList<SimpleCommit>(SimpleCommit, this,
				CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS, CommitPackage.SIMPLE_COMMIT__PARENT)
		}
		return commitsBranchedFromThis
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<MergeCommit> getCommitsMergedFromThis() {
		if (commitsMergedFromThis === null) {
			commitsMergedFromThis = new EObjectWithInverseResolvingEList.ManyInverse<MergeCommit>(MergeCommit, this,
				CommitPackage.COMMIT__COMMITS_MERGED_FROM_THIS, CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS)
		}
		return commitsMergedFromThis
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override int getIdentifier() {
		return identifier
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	override NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {

		switch (featureID) {
			case CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS: {
				return (((getCommitsBranchedFromThis() as InternalEList<?>) as InternalEList<InternalEObject>)).
					basicAdd(otherEnd, msgs)
			}
			case CommitPackage.COMMIT__COMMITS_MERGED_FROM_THIS: {
				return (((getCommitsMergedFromThis() as InternalEList<?>) as InternalEList<InternalEObject>)).basicAdd(
					otherEnd, msgs)
			}
		}
		return super.eInverseAdd(otherEnd, featureID, msgs)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {

		switch (featureID) {
			case CommitPackage.COMMIT__CHANGES: {
				return ((getChanges() as InternalEList<?>)).basicRemove(otherEnd, msgs)
			}
			case CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS: {
				return ((getCommitsBranchedFromThis() as InternalEList<?>)).basicRemove(otherEnd, msgs)
			}
			case CommitPackage.COMMIT__COMMITS_MERGED_FROM_THIS: {
				return ((getCommitsMergedFromThis() as InternalEList<?>)).basicRemove(otherEnd, msgs)
			}
		}
		return super.eInverseRemove(otherEnd, featureID, msgs)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case CommitPackage.COMMIT__CHECKSUM: {
				return getChecksum()
			}
			case CommitPackage.COMMIT__CHANGES: {
				return getChanges()
			}
			case CommitPackage.COMMIT__COMMITMESSAGE: {
				if (resolve) return getCommitmessage()
				return basicGetCommitmessage()
			}
			case CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS: {
				return getCommitsBranchedFromThis()
			}
			case CommitPackage.COMMIT__COMMITS_MERGED_FROM_THIS: {
				return getCommitsMergedFromThis()
			}
			case CommitPackage.COMMIT__IDENTIFIER: {
				return getIdentifier()
			}
		}
		return super.eGet(featureID, resolve, coreType)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	override void eSet(int featureID, Object newValue) {

		switch (featureID) {
			case CommitPackage.COMMIT__CHANGES: {
				getChanges().clear()
				getChanges().addAll((newValue as Collection<? extends EChange>))
				return
			}
			case CommitPackage.COMMIT__COMMITMESSAGE: {
				setCommitmessage((newValue as CommitMessage))
				return
			}
			case CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS: {
				getCommitsBranchedFromThis().clear()
				getCommitsBranchedFromThis().addAll((newValue as Collection<? extends SimpleCommit>))
				return
			}
			case CommitPackage.COMMIT__COMMITS_MERGED_FROM_THIS: {
				getCommitsMergedFromThis().clear()
				getCommitsMergedFromThis().addAll((newValue as Collection<? extends MergeCommit>))
				return
			}
		}
		super.eSet(featureID, newValue)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void eUnset(int featureID) {

		switch (featureID) {
			case CommitPackage.COMMIT__CHANGES: {
				getChanges().clear()
				return
			}
			case CommitPackage.COMMIT__COMMITMESSAGE: {
				setCommitmessage((null as CommitMessage))
				return
			}
			case CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS: {
				getCommitsBranchedFromThis().clear()
				return
			}
			case CommitPackage.COMMIT__COMMITS_MERGED_FROM_THIS: {
				getCommitsMergedFromThis().clear()
				return
			}
		}
		super.eUnset(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override boolean eIsSet(int featureID) {

		switch (featureID) {
			case CommitPackage.COMMIT__CHECKSUM: {
				return checksum !== CHECKSUM_EDEFAULT
			}
			case CommitPackage.COMMIT__CHANGES: {
				return changes !== null && !changes.isEmpty()
			}
			case CommitPackage.COMMIT__COMMITMESSAGE: {
				return commitmessage !== null
			}
			case CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS: {
				return commitsBranchedFromThis !== null && !commitsBranchedFromThis.isEmpty()
			}
			case CommitPackage.COMMIT__COMMITS_MERGED_FROM_THIS: {
				return commitsMergedFromThis !== null && !commitsMergedFromThis.isEmpty()
			}
			case CommitPackage.COMMIT__IDENTIFIER: {
				return identifier !== IDENTIFIER_EDEFAULT
			}
		}
		return super.eIsSet(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override String toString() {
		if (eIsProxy()) return super.toString()
		var StringBuffer result = new StringBuffer(super.toString())
		result.append(" (checksum: ")
		result.append(checksum)
		result.append(", identifier: ")
		result.append(identifier)
		result.append(Character.valueOf(')').charValue)
		return result.toString()
	} // CommitImpl
}
