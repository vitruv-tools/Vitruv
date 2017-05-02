/**
 */
package tools.vitruv.framework.versioning.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.Repository;
import tools.vitruv.framework.versioning.Tag;
import tools.vitruv.framework.versioning.VersioningFactory;
import tools.vitruv.framework.versioning.VersioningPackage;

import tools.vitruv.framework.versioning.branch.Branch;
import tools.vitruv.framework.versioning.branch.BranchFactory;
import tools.vitruv.framework.versioning.branch.MasterBranch;
import tools.vitruv.framework.versioning.commit.Commit;
import tools.vitruv.framework.versioning.commit.CommitFactory;
import tools.vitruv.framework.versioning.commit.InitialCommit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.impl.RepositoryImpl#getTags <em>Tags</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.impl.RepositoryImpl#getAuthors <em>Authors</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.impl.RepositoryImpl#getCommits <em>Commits</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.impl.RepositoryImpl#getBranches <em>Branches</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.impl.RepositoryImpl#getInitialCommit <em>Initial Commit</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.impl.RepositoryImpl#getMaster <em>Master</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepositoryImpl extends MinimalEObjectImpl.Container implements Repository {
	/**
	 * The cached value of the '{@link #getTags() <em>Tags</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTags()
	 * @generated
	 * @ordered
	 */
	protected EList<Tag> tags;

	/**
	 * The cached value of the '{@link #getAuthors() <em>Authors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthors()
	 * @generated
	 * @ordered
	 */
	protected EList<Author> authors;

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
	 * The cached value of the '{@link #getBranches() <em>Branches</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<Branch> branches;

	/**
	 * The cached value of the '{@link #getInitialCommit() <em>Initial Commit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialCommit()
	 * @generated
	 * @ordered
	 */
	protected InitialCommit initialCommit;

	/**
	 * The cached value of the '{@link #getMaster() <em>Master</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaster()
	 * @generated
	 * @ordered
	 */
	protected MasterBranch master;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected RepositoryImpl() {
		super();
		this.setInitialCommit(CommitFactory.eINSTANCE.createInitialCommit());
		this.setMaster(BranchFactory.eINSTANCE.createMasterBranch());
		this.getBranches().add(this.getMaster());
		this.getCommits().add(this.getInitialCommit());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VersioningPackage.Literals.REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Tag> getTags() {
		if (tags == null) {
			tags = new EObjectContainmentEList<Tag>(Tag.class, this, VersioningPackage.REPOSITORY__TAGS);
		}
		return tags;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Author> getAuthors() {
		if (authors == null) {
			authors = new EObjectContainmentWithInverseEList<Author>(Author.class, this, VersioningPackage.REPOSITORY__AUTHORS, VersioningPackage.AUTHOR__REPOSITORY);
		}
		return authors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Commit> getCommits() {
		if (commits == null) {
			commits = new EObjectResolvingEList<Commit>(Commit.class, this, VersioningPackage.REPOSITORY__COMMITS);
		}
		return commits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Branch> getBranches() {
		if (branches == null) {
			branches = new EObjectContainmentEList<Branch>(Branch.class, this, VersioningPackage.REPOSITORY__BRANCHES);
		}
		return branches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitialCommit getInitialCommit() {
		return initialCommit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialCommit(InitialCommit newInitialCommit, NotificationChain msgs) {
		InitialCommit oldInitialCommit = initialCommit;
		initialCommit = newInitialCommit;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VersioningPackage.REPOSITORY__INITIAL_COMMIT, oldInitialCommit, newInitialCommit);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialCommit(InitialCommit newInitialCommit) {
		if (newInitialCommit != initialCommit) {
			NotificationChain msgs = null;
			if (initialCommit != null)
				msgs = ((InternalEObject)initialCommit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VersioningPackage.REPOSITORY__INITIAL_COMMIT, null, msgs);
			if (newInitialCommit != null)
				msgs = ((InternalEObject)newInitialCommit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersioningPackage.REPOSITORY__INITIAL_COMMIT, null, msgs);
			msgs = basicSetInitialCommit(newInitialCommit, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.REPOSITORY__INITIAL_COMMIT, newInitialCommit, newInitialCommit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MasterBranch getMaster() {
		if (master != null && master.eIsProxy()) {
			InternalEObject oldMaster = (InternalEObject)master;
			master = (MasterBranch)eResolveProxy(oldMaster);
			if (master != oldMaster) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersioningPackage.REPOSITORY__MASTER, oldMaster, master));
			}
		}
		return master;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MasterBranch basicGetMaster() {
		return master;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaster(MasterBranch newMaster) {
		MasterBranch oldMaster = master;
		master = newMaster;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.REPOSITORY__MASTER, oldMaster, master));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Author createAuthor(String name, String email) {
		final Author author = VersioningFactory.eINSTANCE.createAuthor();
		author.setName(name);
		author.setEmail(email);
		author.setRepository(this);
		author.setCurrentBranch(this.getMaster());
		return author;
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
			case VersioningPackage.REPOSITORY__AUTHORS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAuthors()).basicAdd(otherEnd, msgs);
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
			case VersioningPackage.REPOSITORY__TAGS:
				return ((InternalEList<?>)getTags()).basicRemove(otherEnd, msgs);
			case VersioningPackage.REPOSITORY__AUTHORS:
				return ((InternalEList<?>)getAuthors()).basicRemove(otherEnd, msgs);
			case VersioningPackage.REPOSITORY__BRANCHES:
				return ((InternalEList<?>)getBranches()).basicRemove(otherEnd, msgs);
			case VersioningPackage.REPOSITORY__INITIAL_COMMIT:
				return basicSetInitialCommit(null, msgs);
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
			case VersioningPackage.REPOSITORY__TAGS:
				return getTags();
			case VersioningPackage.REPOSITORY__AUTHORS:
				return getAuthors();
			case VersioningPackage.REPOSITORY__COMMITS:
				return getCommits();
			case VersioningPackage.REPOSITORY__BRANCHES:
				return getBranches();
			case VersioningPackage.REPOSITORY__INITIAL_COMMIT:
				return getInitialCommit();
			case VersioningPackage.REPOSITORY__MASTER:
				if (resolve) return getMaster();
				return basicGetMaster();
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
			case VersioningPackage.REPOSITORY__TAGS:
				getTags().clear();
				getTags().addAll((Collection<? extends Tag>)newValue);
				return;
			case VersioningPackage.REPOSITORY__AUTHORS:
				getAuthors().clear();
				getAuthors().addAll((Collection<? extends Author>)newValue);
				return;
			case VersioningPackage.REPOSITORY__COMMITS:
				getCommits().clear();
				getCommits().addAll((Collection<? extends Commit>)newValue);
				return;
			case VersioningPackage.REPOSITORY__BRANCHES:
				getBranches().clear();
				getBranches().addAll((Collection<? extends Branch>)newValue);
				return;
			case VersioningPackage.REPOSITORY__INITIAL_COMMIT:
				setInitialCommit((InitialCommit)newValue);
				return;
			case VersioningPackage.REPOSITORY__MASTER:
				setMaster((MasterBranch)newValue);
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
			case VersioningPackage.REPOSITORY__TAGS:
				getTags().clear();
				return;
			case VersioningPackage.REPOSITORY__AUTHORS:
				getAuthors().clear();
				return;
			case VersioningPackage.REPOSITORY__COMMITS:
				getCommits().clear();
				return;
			case VersioningPackage.REPOSITORY__BRANCHES:
				getBranches().clear();
				return;
			case VersioningPackage.REPOSITORY__INITIAL_COMMIT:
				setInitialCommit((InitialCommit)null);
				return;
			case VersioningPackage.REPOSITORY__MASTER:
				setMaster((MasterBranch)null);
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
			case VersioningPackage.REPOSITORY__TAGS:
				return tags != null && !tags.isEmpty();
			case VersioningPackage.REPOSITORY__AUTHORS:
				return authors != null && !authors.isEmpty();
			case VersioningPackage.REPOSITORY__COMMITS:
				return commits != null && !commits.isEmpty();
			case VersioningPackage.REPOSITORY__BRANCHES:
				return branches != null && !branches.isEmpty();
			case VersioningPackage.REPOSITORY__INITIAL_COMMIT:
				return initialCommit != null;
			case VersioningPackage.REPOSITORY__MASTER:
				return master != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case VersioningPackage.REPOSITORY___CREATE_AUTHOR__STRING_STRING:
				return createAuthor((String)arguments.get(0), (String)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

} //RepositoryImpl
