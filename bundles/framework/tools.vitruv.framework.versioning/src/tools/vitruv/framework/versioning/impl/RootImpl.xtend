/** 
 */
package tools.vitruv.framework.versioning.impl

import java.lang.reflect.InvocationTargetException
import java.util.Collection
import org.eclipse.emf.common.notify.NotificationChain
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import org.eclipse.emf.ecore.util.EObjectContainmentEList
import org.eclipse.emf.ecore.util.InternalEList
import tools.vitruv.framework.versioning.Root
import tools.vitruv.framework.versioning.VersioningPackage
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.author.AuthorFactory
import tools.vitruv.framework.versioning.branch.BranchFactory
import tools.vitruv.framework.versioning.branch.MasterBranch
import tools.vitruv.framework.versioning.commit.CommitFactory
import tools.vitruv.framework.versioning.commit.InitialCommit
import tools.vitruv.framework.versioning.repository.Repository
import tools.vitruv.framework.versioning.repository.RepositoryFactory

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.impl.RootImpl#getRepositories <em>Repositories</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.impl.RootImpl#getAuthors <em>Authors</em>}</li>
 * </ul>
 * @generated
 */
class RootImpl extends MinimalEObjectImpl.Container implements Root {
	/** 
	 * The cached value of the '{@link #getRepositories() <em>Repositories</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositories()
	 * @generated
	 * @ordered
	 */
	protected EList<Repository> repositories
	/** 
	 * The cached value of the '{@link #getAuthors() <em>Authors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthors()
	 * @generated
	 * @ordered
	 */
	protected EList<Author> authors

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
		return VersioningPackage.Literals.ROOT
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<Repository> getRepositories() {
		if (repositories === null) {
			repositories = new EObjectContainmentEList<Repository>(Repository, this,
				VersioningPackage.ROOT__REPOSITORIES)
		}
		return repositories
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<Author> getAuthors() {
		if (authors === null) {
			authors = new EObjectContainmentEList<Author>(Author, this, VersioningPackage.ROOT__AUTHORS)
		}
		return authors
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	override Author createAuthor(String name, String email) {
		val Author author = AuthorFactory.eINSTANCE.createAuthor()
		author.setName(name)
		author.setEmail(email)
		this.getAuthors().add(author)
		return author
	}

	/** 
	 */
	override Repository createRepository() {
		val Repository repository = RepositoryFactory.eINSTANCE.createRepository()
		val MasterBranch masterBranch = BranchFactory.eINSTANCE.createMasterBranch()
		val InitialCommit initialCommit = CommitFactory.eINSTANCE.createInitialCommit()
		repository.setInitialCommit(initialCommit)
		masterBranch.setCurrentHeadCommit(initialCommit)
		repository.setMaster(masterBranch)
		repository.getBranches().add(masterBranch)
		repository.getCommits().add(initialCommit)
		this.getRepositories().add(repository)
		return repository
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {

		switch (featureID) {
			case VersioningPackage.ROOT__REPOSITORIES: {
				return ((getRepositories() as InternalEList<?>)).basicRemove(otherEnd, msgs)
			}
			case VersioningPackage.ROOT__AUTHORS: {
				return ((getAuthors() as InternalEList<?>)).basicRemove(otherEnd, msgs)
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
			case VersioningPackage.ROOT__REPOSITORIES: {
				return getRepositories()
			}
			case VersioningPackage.ROOT__AUTHORS: {
				return getAuthors()
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
			case VersioningPackage.ROOT__REPOSITORIES: {
				getRepositories().clear()
				getRepositories().addAll((newValue as Collection<? extends Repository>))
				return
			}
			case VersioningPackage.ROOT__AUTHORS: {
				getAuthors().clear()
				getAuthors().addAll((newValue as Collection<? extends Author>))
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
			case VersioningPackage.ROOT__REPOSITORIES: {
				getRepositories().clear()
				return
			}
			case VersioningPackage.ROOT__AUTHORS: {
				getAuthors().clear()
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
			case VersioningPackage.ROOT__REPOSITORIES: {
				return repositories !== null && !repositories.isEmpty()
			}
			case VersioningPackage.ROOT__AUTHORS: {
				return authors !== null && !authors.isEmpty()
			}
		}
		return super.eIsSet(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {

		switch (operationID) {
			case VersioningPackage.ROOT___CREATE_AUTHOR__STRING_STRING: {
				return createAuthor((arguments.get(0) as String), (arguments.get(1) as String))
			}
			case VersioningPackage.ROOT___CREATE_REPOSITORY: {
				return createRepository()
			}
		}
		return super.eInvoke(operationID, arguments)
	} // RootImpl
}
