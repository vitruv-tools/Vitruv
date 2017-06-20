/** 
 */
package tools.vitruv.framework.versioning.author.impl

import java.lang.reflect.InvocationTargetException
import java.util.Collection
import java.util.Date
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.NotificationChain
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.util.EObjectResolvingEList
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList
import org.eclipse.emf.ecore.util.InternalEList
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.BranchFactory
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.UserBranch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitFactory
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.impl.NamedImpl
import tools.vitruv.framework.versioning.repository.Repository

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Author</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getEmail <em>Email</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getOwnedBranches <em>Owned Branches</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getContributedBranches <em>Contributed Branches</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getCommits <em>Commits</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getCurrentRepository <em>Current Repository</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getCurrentBranch <em>Current Branch</em>}</li>
 * </ul>
 * @generated
 */
class AuthorImpl extends NamedImpl implements Author {
	/** 
	 * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_EDEFAULT = null
	/** 
	 * The cached value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected String email = EMAIL_EDEFAULT
	/** 
	 * The cached value of the '{@link #getOwnedBranches() <em>Owned Branches</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<UserBranch> ownedBranches
	/** 
	 * The cached value of the '{@link #getContributedBranches() <em>Contributed Branches</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributedBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<Branch> contributedBranches
	/** 
	 * The cached value of the '{@link #getCommits() <em>Commits</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommits()
	 * @generated
	 * @ordered
	 */
	protected EList<Commit> commits
	/** 
	 * The cached value of the '{@link #getCurrentRepository() <em>Current Repository</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentRepository()
	 * @generated
	 * @ordered
	 */
	protected Repository currentRepository
	/** 
	 * The cached value of the '{@link #getCurrentBranch() <em>Current Branch</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentBranch()
	 * @generated
	 * @ordered
	 */
	protected Branch currentBranch

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
		return AuthorPackage::Literals::AUTHOR
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override String getEmail() {
		return email
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setEmail(String newEmail) {
		var String oldEmail = email
		email = newEmail
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification::SET, AuthorPackage::AUTHOR__EMAIL, oldEmail, email))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<UserBranch> getOwnedBranches() {
		if (ownedBranches === null) {
			ownedBranches = new EObjectWithInverseResolvingEList<UserBranch>(typeof(UserBranch), this,
				AuthorPackage::AUTHOR__OWNED_BRANCHES, BranchPackage::USER_BRANCH__OWNER)
		}
		return ownedBranches
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<Branch> getContributedBranches() {
		if (contributedBranches === null) {
			contributedBranches = new EObjectWithInverseResolvingEList.ManyInverse<Branch>(typeof(Branch), this,
				AuthorPackage::AUTHOR__CONTRIBUTED_BRANCHES, BranchPackage::BRANCH__CONTRIBUTORS)
		}
		return contributedBranches
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<Commit> getCommits() {
		if (commits === null) {
			commits = new EObjectResolvingEList<Commit>(typeof(Commit), this, AuthorPackage::AUTHOR__COMMITS)
		}
		return commits
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Repository getCurrentRepository() {
		if (currentRepository !== null && currentRepository.eIsProxy()) {
			var InternalEObject oldCurrentRepository = (currentRepository as InternalEObject)
			currentRepository = eResolveProxy(oldCurrentRepository) as Repository
			if (currentRepository !== oldCurrentRepository) {
				if (eNotificationRequired())
					eNotify(
						new ENotificationImpl(this, Notification::RESOLVE, AuthorPackage::AUTHOR__CURRENT_REPOSITORY,
							oldCurrentRepository, currentRepository))
			}
		}
		return currentRepository
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def Repository basicGetCurrentRepository() {
		return currentRepository
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setCurrentRepository(Repository newCurrentRepository) {
		var Repository oldCurrentRepository = currentRepository
		currentRepository = newCurrentRepository
		if (eNotificationRequired())
			eNotify(
				new ENotificationImpl(this, Notification::SET, AuthorPackage::AUTHOR__CURRENT_REPOSITORY,
					oldCurrentRepository, currentRepository))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Branch getCurrentBranch() {
		if (currentBranch !== null && currentBranch.eIsProxy()) {
			var InternalEObject oldCurrentBranch = (currentBranch as InternalEObject)
			currentBranch = eResolveProxy(oldCurrentBranch) as Branch
			if (currentBranch !== oldCurrentBranch) {
				if (eNotificationRequired())
					eNotify(
						new ENotificationImpl(this, Notification::RESOLVE, AuthorPackage::AUTHOR__CURRENT_BRANCH,
							oldCurrentBranch, currentBranch))
			}
		}
		return currentBranch
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def Branch basicGetCurrentBranch() {
		return currentBranch
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setCurrentBranch(Branch newCurrentBranch) {
		var Branch oldCurrentBranch = currentBranch
		currentBranch = newCurrentBranch
		if (eNotificationRequired())
			eNotify(
				new ENotificationImpl(this, Notification::SET, AuthorPackage::AUTHOR__CURRENT_BRANCH, oldCurrentBranch,
					currentBranch))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	override SimpleCommit createSimpleCommit(String message, Commit parent, EList<EChange> changes) {
		val SimpleCommit simpleCommit = CommitFactory::eINSTANCE.createSimpleCommit()
		addCommitMessageToCommit(simpleCommit, message)
		addCommitToCollections(simpleCommit)
		simpleCommit.setParent(parent)
		return simpleCommit
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	override UserBranch createBranch(String branchName) {
		val UserBranch branch = BranchFactory::eINSTANCE.createUserBranch()
		branch.setName(branchName)
		branch.setBranchedFrom(this.getCurrentBranch())
		branch.getContributors().add(this)
		branch.setOwner(this)
		branch.setCurrentHeadCommit(this.getCurrentBranch().getCurrentHeadCommit())
		this.getCurrentRepository().getBranches().add(branch)
		return branch
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	override void switchToBranch(Branch targetBranch) {
		this.setCurrentBranch(targetBranch)
		if (!this.getContributedBranches().contains(targetBranch)) {
			this.getContributedBranches().add(targetBranch)
		}
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	override void switchToRepository(Repository repository) {
		this.setCurrentRepository(repository)
		this.setCurrentBranch(repository.getMaster())
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	override NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {

		switch (featureID) {
			case AuthorPackage::AUTHOR__OWNED_BRANCHES: {
				return (((getOwnedBranches() as InternalEList<?>) as InternalEList<InternalEObject>)).basicAdd(otherEnd,
					msgs)
			}
			case AuthorPackage::AUTHOR__CONTRIBUTED_BRANCHES: {
				return (((getContributedBranches() as InternalEList<?>) as InternalEList<InternalEObject>)).basicAdd(
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
			case AuthorPackage::AUTHOR__OWNED_BRANCHES: {
				return ((getOwnedBranches() as InternalEList<?>)).basicRemove(otherEnd, msgs)
			}
			case AuthorPackage::AUTHOR__CONTRIBUTED_BRANCHES: {
				return ((getContributedBranches() as InternalEList<?>)).basicRemove(otherEnd, msgs)
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
			case AuthorPackage::AUTHOR__EMAIL: {
				return getEmail()
			}
			case AuthorPackage::AUTHOR__OWNED_BRANCHES: {
				return getOwnedBranches()
			}
			case AuthorPackage::AUTHOR__CONTRIBUTED_BRANCHES: {
				return getContributedBranches()
			}
			case AuthorPackage::AUTHOR__COMMITS: {
				return getCommits()
			}
			case AuthorPackage::AUTHOR__CURRENT_REPOSITORY: {
				if (resolve) return getCurrentRepository()
				return basicGetCurrentRepository()
			}
			case AuthorPackage::AUTHOR__CURRENT_BRANCH: {
				if (resolve) return getCurrentBranch()
				return basicGetCurrentBranch()
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
			case AuthorPackage::AUTHOR__EMAIL: {
				setEmail((newValue as String))
				return
			}
			case AuthorPackage::AUTHOR__OWNED_BRANCHES: {
				getOwnedBranches().clear()
				getOwnedBranches().addAll((newValue as Collection<? extends UserBranch>))
				return
			}
			case AuthorPackage::AUTHOR__CONTRIBUTED_BRANCHES: {
				getContributedBranches().clear()
				getContributedBranches().addAll((newValue as Collection<? extends Branch>))
				return
			}
			case AuthorPackage::AUTHOR__COMMITS: {
				getCommits().clear()
				getCommits().addAll((newValue as Collection<? extends Commit>))
				return
			}
			case AuthorPackage::AUTHOR__CURRENT_REPOSITORY: {
				setCurrentRepository((newValue as Repository))
				return
			}
			case AuthorPackage::AUTHOR__CURRENT_BRANCH: {
				setCurrentBranch((newValue as Branch))
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
			case AuthorPackage::AUTHOR__EMAIL: {
				setEmail(EMAIL_EDEFAULT)
				return
			}
			case AuthorPackage::AUTHOR__OWNED_BRANCHES: {
				getOwnedBranches().clear()
				return
			}
			case AuthorPackage::AUTHOR__CONTRIBUTED_BRANCHES: {
				getContributedBranches().clear()
				return
			}
			case AuthorPackage::AUTHOR__COMMITS: {
				getCommits().clear()
				return
			}
			case AuthorPackage::AUTHOR__CURRENT_REPOSITORY: {
				setCurrentRepository((null as Repository))
				return
			}
			case AuthorPackage::AUTHOR__CURRENT_BRANCH: {
				setCurrentBranch((null as Branch))
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
			case AuthorPackage::AUTHOR__EMAIL: {
				return email !== null
			}
			case AuthorPackage::AUTHOR__OWNED_BRANCHES: {
				return ownedBranches !== null && !ownedBranches.isEmpty()
			}
			case AuthorPackage::AUTHOR__CONTRIBUTED_BRANCHES: {
				return contributedBranches !== null && !contributedBranches.isEmpty()
			}
			case AuthorPackage::AUTHOR__COMMITS: {
				return commits !== null && !commits.isEmpty()
			}
			case AuthorPackage::AUTHOR__CURRENT_REPOSITORY: {
				return currentRepository !== null
			}
			case AuthorPackage::AUTHOR__CURRENT_BRANCH: {
				return currentBranch !== null
			}
		}
		return super.eIsSet(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	override Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {

		switch (operationID) {
			case AuthorPackage::AUTHOR___CREATE_SIMPLE_COMMIT__STRING_COMMIT_ELIST: {
				return createSimpleCommit((arguments.get(0) as String), (arguments.get(1) as Commit),
					(arguments.get(2) as EList<EChange>))
			}
			case AuthorPackage::AUTHOR___CREATE_BRANCH__STRING: {
				return createBranch((arguments.get(0) as String))
			}
			case AuthorPackage::AUTHOR___SWITCH_TO_BRANCH__BRANCH: {
				switchToBranch((arguments.get(0) as Branch))
				return null
			}
			case AuthorPackage::AUTHOR___SWITCH_TO_REPOSITORY__REPOSITORY: {
				switchToRepository((arguments.get(0) as Repository))
				return null
			}
		}
		return super.eInvoke(operationID, arguments)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override String toString() {
		if (eIsProxy()) return super.toString()
		var StringBuffer result = new StringBuffer(super.toString())
		result.append(" (email: ")
		result.append(email)
		result.append(Character.valueOf(')').charValue)
		return result.toString()
	}

	/** 
	 * @param commit
	 * @param message
	 */
	def private void addCommitMessageToCommit(Commit commit, String message) {
		val CommitMessage commitMessage = CommitFactory::eINSTANCE.createCommitMessage()
		commitMessage.setAuthor(this)
		commitMessage.setDate(new Date())
		commitMessage.setMessage(message)
		commit.setCommitmessage(commitMessage)
	}

	/** 
	 * @param commit
	 */
	def private void addCommitToCollections(Commit commit) {
		this.getCommits().add(commit)
		this.getCurrentRepository().getCommits().add(commit)
	} // AuthorImpl
}
