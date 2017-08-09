package tools.vitruv.framework.tuid

import java.io.Serializable
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.ForwardHashedBackwardLinkedTree
import tools.vitruv.framework.tuid.impl.TuidImpl

/** 
 * A class for Temporarily Unique IDentifiers (Tuids) that internally uses a{@link ForwardHashedBackwardLinkedTree} to ensure that depending Tuids are indirectly changed,
 * i.e. a call to toString() always returns the current Tuid based on the links to its predecessors.
 * Implements the multiton design pattern to ensure that there exists only one Tuid instance per
 * String key.<br/>
 * <br/>
 * Helpful thoughts for Tuid modifications in file systems and Java projects:<br/>
 * <br/>
 * If a package is renamed in Java, then only the paths of all contained classifiers are affected.
 * The paths of subpackages are not affected but they are no longer subpackages. If a package is
 * renamed in Java, then the depth of its path may change arbitrarily. In such cases{@link #renameLastSegment(String)} should be called. <br/>
 * <br/>
 * If a folder is renamed, then the paths of all contained elements are affected but the depth may
 * not change. In such cases {@link #renameLastSegment(String)} should be called. <br/>
 * <br/>
 * If a package is moved in Java, then it may only be completely moved to another folder and
 * subpackages are not affected. It is not possible to move subpackages to another package. It is
 * however possible to move a package to a folder in which the package or a subpackage is already
 * existing, then the packages are merged. In such cases {@link #moveLastSegment(String)} should be
 * called. <br/>
 * <br/>
 * If a folder is moved, then the paths of all contained elements are affected and the depth may
 * change. If the destination folder already exists the containing elements of both folders are
 * merged. In such cases {@link #moveLastSegment(String)} should be called. <br/>
 * <br/>
 * @author kramerm
 */
interface Tuid extends Serializable {

	/** 
	 * Returns the unique Tuid (instance) for the specified tuidString (key).
	 * @param tuidString
	 * @return the unique Tuid for the specified tuidString
	 */
	static def Tuid getInstance(String tuidString) {
		return TuidImpl::getInstance(tuidString, false)
	}

	static def reinitialize() {
		TuidImpl::reinitialize
	}

	/**
	 * Returns a String representation of all registered Tuid instances.
	 * @return a String representation of all registered Tuid instances
	 */
	def static String toStrings() {
		TuidImpl::toStrings
	}

	/**
	 * Returns whether the Tuid instance is valid in the sense that all Tuid instances that are
	 * contained in the forward (tree) registry are also contained in the backward (link) registry
	 * and vice-versa.
	 * @return whether the Tuid instance is valid
	 * @throws a {@link IllegalStateException} if the Tuid instance is not valid
	 */
	def static boolean validate() {
		TuidImpl::validate
	}

	/**
	 * Either a) renames the last segment of this Tuid to the last segment of the given {@link newTuid} 
	 * if they differ and all previous segments are the same or b) moves the last segment of this Tuid to the second but last 
	 * segment of the given {@link newTuid}.
	 */
	def void updateTuid(Tuid newTuid)

	def void updateTuid(EObject newObject)
}
