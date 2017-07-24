/** 
 */
package tools.vitruv.framework.versioning.branch.impl

import java.util.Collection
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.NotificationChain
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList
import org.eclipse.emf.ecore.util.InternalEList
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.UserBranch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.Named
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.Set

class BranchImpl implements Branch, Named {
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Commit currentHeadCommit
	@Accessors(PUBLIC_GETTER)
	val Set<Author> contributors
	@Accessors(PUBLIC_GETTER)
	val Set<UserBranch> childBranches
	@Accessors(PUBLIC_GETTER)
	val String name

	new(String name) {
		this.name = name
		contributors = newHashSet
		childBranches = newHashSet
	}

	override isMasterBranch() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
