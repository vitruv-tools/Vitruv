/**
 */
package tools.vitruv.framework.versioning.author.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import tools.vitruv.framework.change.echange.EChange;

import tools.vitruv.framework.versioning.author.Author;
import tools.vitruv.framework.versioning.author.AuthorPackage;

import tools.vitruv.framework.versioning.branch.Branch;
import tools.vitruv.framework.versioning.branch.BranchPackage;
import tools.vitruv.framework.versioning.branch.UserBranch;

import tools.vitruv.framework.versioning.commit.Commit;
import tools.vitruv.framework.versioning.commit.SimpleCommit;

import tools.vitruv.framework.versioning.impl.NamedImpl;

import tools.vitruv.framework.versioning.repository.Repository;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Author</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getOwnedBranches <em>Owned Branches</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getContributedBranches <em>Contributed Branches</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getCommits <em>Commits</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getCurrentRepository <em>Current Repository</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl#getCurrentBranch <em>Current Branch</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AuthorImpl extends NamedImpl implements Author {
	/**
	 * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected String email = EMAIL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOwnedBranches() <em>Owned Branches</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<UserBranch> ownedBranches;

	/**
	 * The cached value of the '{@link #getContributedBranches() <em>Contributed Branches</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributedBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<Branch> contributedBranches;

	/**
	 * The cached value of the '{@link #getCommits() <em>Commits</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommits()
	 * @generated
	 * @ordered
	 */
	protected EList<Commit> commits;

	/**
	 * The cached value of the '{@link #getCurrentRepository() <em>Current Repository</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentRepository()
	 * @generated
	 * @ordered
	 */
	protected Repository currentRepository;

	/**
	 * The cached value of the '{@link #getCurrentBranch() <em>Current Branch</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentBranch()
	 * @generated
	 * @ordered
	 */
	protected Branch currentBranch;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AuthorPackage.Literals.AUTHOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmail(String newEmail) {
		String oldEmail = email;
		email = newEmail;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AuthorPackage.AUTHOR__EMAIL, oldEmail, email));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UserBranch> getOwnedBranches() {
		if (ownedBranches == null) {
			ownedBranches = new EObjectWithInverseResolvingEList<UserBranch>(UserBranch.class, this, AuthorPackage.AUTHOR__OWNED_BRANCHES, BranchPackage.USER_BRANCH__OWNER);
		}
		return ownedBranches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Branch> getContributedBranches() {
		if (contributedBranches == null) {
			contributedBranches = new EObjectWithInverseResolvingEList.ManyInverse<Branch>(Branch.class, this, AuthorPackage.AUTHOR__CONTRIBUTED_BRANCHES, BranchPackage.BRANCH__CONTRIBUTORS);
		}
		return contributedBranches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Commit> getCommits() {
		if (commits == null) {
			commits = new EObjectResolvingEList<Commit>(Commit.class, this, AuthorPackage.AUTHOR__COMMITS);
		}
		return commits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Repository getCurrentRepository() {
		if (currentRepository != null && currentRepository.eIsProxy()) {
			InternalEObject oldCurrentRepository = (InternalEObject)currentRepository;
			currentRepository = (Repository)eResolveProxy(oldCurrentRepository);
			if (currentRepository != oldCurrentRepository) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AuthorPackage.AUTHOR__CURRENT_REPOSITORY, oldCurrentRepository, currentRepository));
			}
		}
		return currentRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Repository basicGetCurrentRepository() {
		return currentRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentRepository(Repository newCurrentRepository) {
		Repository oldCurrentRepository = currentRepository;
		currentRepository = newCurrentRepository;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AuthorPackage.AUTHOR__CURRENT_REPOSITORY, oldCurrentRepository, currentRepository));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Branch getCurrentBranch() {
		if (currentBranch != null && currentBranch.eIsProxy()) {
			InternalEObject oldCurrentBranch = (InternalEObject)currentBranch;
			currentBranch = (Branch)eResolveProxy(oldCurrentBranch);
			if (currentBranch != oldCurrentBranch) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AuthorPackage.AUTHOR__CURRENT_BRANCH, oldCurrentBranch, currentBranch));
			}
		}
		return currentBranch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Branch basicGetCurrentBranch() {
		return currentBranch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentBranch(Branch newCurrentBranch) {
		Branch oldCurrentBranch = currentBranch;
		currentBranch = newCurrentBranch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AuthorPackage.AUTHOR__CURRENT_BRANCH, oldCurrentBranch, currentBranch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleCommit createSimpleCommit(String message, Commit parent, EList<EChange> changes) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserBranch createBranch(String branchName) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void switchToBranch(Branch targetBranch) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void switchToRepository(Repository repository) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AuthorPackage.AUTHOR__OWNED_BRANCHES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOwnedBranches()).basicAdd(otherEnd, msgs);
			case AuthorPackage.AUTHOR__CONTRIBUTED_BRANCHES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getContributedBranches()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AuthorPackage.AUTHOR__OWNED_BRANCHES:
				return ((InternalEList<?>)getOwnedBranches()).basicRemove(otherEnd, msgs);
			case AuthorPackage.AUTHOR__CONTRIBUTED_BRANCHES:
				return ((InternalEList<?>)getContributedBranches()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AuthorPackage.AUTHOR__EMAIL:
				return getEmail();
			case AuthorPackage.AUTHOR__OWNED_BRANCHES:
				return getOwnedBranches();
			case AuthorPackage.AUTHOR__CONTRIBUTED_BRANCHES:
				return getContributedBranches();
			case AuthorPackage.AUTHOR__COMMITS:
				return getCommits();
			case AuthorPackage.AUTHOR__CURRENT_REPOSITORY:
				if (resolve) return getCurrentRepository();
				return basicGetCurrentRepository();
			case AuthorPackage.AUTHOR__CURRENT_BRANCH:
				if (resolve) return getCurrentBranch();
				return basicGetCurrentBranch();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AuthorPackage.AUTHOR__EMAIL:
				setEmail((String)newValue);
				return;
			case AuthorPackage.AUTHOR__OWNED_BRANCHES:
				getOwnedBranches().clear();
				getOwnedBranches().addAll((Collection<? extends UserBranch>)newValue);
				return;
			case AuthorPackage.AUTHOR__CONTRIBUTED_BRANCHES:
				getContributedBranches().clear();
				getContributedBranches().addAll((Collection<? extends Branch>)newValue);
				return;
			case AuthorPackage.AUTHOR__COMMITS:
				getCommits().clear();
				getCommits().addAll((Collection<? extends Commit>)newValue);
				return;
			case AuthorPackage.AUTHOR__CURRENT_REPOSITORY:
				setCurrentRepository((Repository)newValue);
				return;
			case AuthorPackage.AUTHOR__CURRENT_BRANCH:
				setCurrentBranch((Branch)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AuthorPackage.AUTHOR__EMAIL:
				setEmail(EMAIL_EDEFAULT);
				return;
			case AuthorPackage.AUTHOR__OWNED_BRANCHES:
				getOwnedBranches().clear();
				return;
			case AuthorPackage.AUTHOR__CONTRIBUTED_BRANCHES:
				getContributedBranches().clear();
				return;
			case AuthorPackage.AUTHOR__COMMITS:
				getCommits().clear();
				return;
			case AuthorPackage.AUTHOR__CURRENT_REPOSITORY:
				setCurrentRepository((Repository)null);
				return;
			case AuthorPackage.AUTHOR__CURRENT_BRANCH:
				setCurrentBranch((Branch)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AuthorPackage.AUTHOR__EMAIL:
				return EMAIL_EDEFAULT == null ? email != null : !EMAIL_EDEFAULT.equals(email);
			case AuthorPackage.AUTHOR__OWNED_BRANCHES:
				return ownedBranches != null && !ownedBranches.isEmpty();
			case AuthorPackage.AUTHOR__CONTRIBUTED_BRANCHES:
				return contributedBranches != null && !contributedBranches.isEmpty();
			case AuthorPackage.AUTHOR__COMMITS:
				return commits != null && !commits.isEmpty();
			case AuthorPackage.AUTHOR__CURRENT_REPOSITORY:
				return currentRepository != null;
			case AuthorPackage.AUTHOR__CURRENT_BRANCH:
				return currentBranch != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case AuthorPackage.AUTHOR___CREATE_SIMPLE_COMMIT__STRING_COMMIT_ELIST:
				return createSimpleCommit((String)arguments.get(0), (Commit)arguments.get(1), (EList<EChange>)arguments.get(2));
			case AuthorPackage.AUTHOR___CREATE_BRANCH__STRING:
				return createBranch((String)arguments.get(0));
			case AuthorPackage.AUTHOR___SWITCH_TO_BRANCH__BRANCH:
				switchToBranch((Branch)arguments.get(0));
				return null;
			case AuthorPackage.AUTHOR___SWITCH_TO_REPOSITORY__REPOSITORY:
				switchToRepository((Repository)arguments.get(0));
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (email: ");
		result.append(email);
		result.append(')');
		return result.toString();
	}

} //AuthorImpl
