package tools.vitruv.dsls.commonalities.participation

import java.util.LinkedHashSet
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.ToString
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.ParticipationClass

/**
 * The variable <code>root</code> portion of a participation's containment
 * context.
 * <p>
 * In the context of participation matching the term 'root' has a different
 * meaning than the term 'root (container) class(es)':</br>
 * Usually a participation will specify <code>Resource</code> as its root
 * container class. This Resource participation class is then also the 'root'
 * of the participation. However, while participations only support a single
 * Resource root container class (TODO currently), their 'root' may actually
 * comprise multiple participation classes. For example, when marking a
 * participation class as <code>root</code> (TODO not actually supported yet)
 * or as <code>singleton</code> (via the keyword <code>single</code>), then
 * this participation class resembles the <code>head</code> of a chain of root
 * participation classes, in which each class in that chain is related with
 * another root class via a containment relationship.
 * <p>
 * For example, in a participation of the form <code>{'A', 'B', 'single C',
 * 'Resource'}</code> with containment relations <code>{'A in C', 'B in C',
 * 'C in Resource'}</code>, the <code>'Resource'</code> class is the root
 * container, <code>{'C', 'Resource'}</code> is the set of root classes and
 * <code>{'A', 'B'}</code> is the set of non-root classes.
 * <p>
 * When commonalities are referenced by other commonalities (in the form of
 * commonality references), the mappings for these references specify different
 * roots for the referenced participations. All reference mappings for a
 * specific domain are taken into account when determining the root for the
 * referenced participation. The root may therefore comprise multiple classes
 * which act as containers for classes of the referenced participation. These
 * roots for the various domains substitute the referenced participations' own
 * roots during the matching process.
 * <p>
 * An exception to this are commonality reference mappings with an 'attribute
 * reference' operator. These attribute reference operators are used when a
 * containment relationship is expressed implicitly by the attributes of the
 * related objects (eg. in Java the relationship between a package and its sub
 * packages is expressed implicitly by the namespaces and name attributes). The
 * root for such a commonality reference mapping would be similar to the
 * participation's own root, except that it additionally specifies an
 * 'attribute reference root' and 'attribute reference containments'.
 * <p>
 * It is also possible for a participation to not specify any root (i.e. by not
 * specifying any participation class of type <code>Resource</code>). In that
 * case the participation can only exist in contexts in which its commonality
 * is externally referenced by other commonalities.
 * <p>
 * Root objects of commonality participations are implicitly contained in the
 * root of their intermediate model. Their {@link ParticipationRoot} is
 * therefore empty.
 */
@ToString
class ParticipationRoot {
	/**
	 * The external commonality reference mappings which specify this root, or
	 * empty if this root is specified by the participation itself.
	 */
	@Accessors(PUBLIC_GETTER)
	val Set<CommonalityReferenceMapping> referenceMappings = new LinkedHashSet
	/**
	 * Note: In the context of an external reference mapping, these
	 * participation classes may originate from an external participation.
	 */
	@Accessors(PUBLIC_GETTER)
	val Set<ParticipationClass> classes = new LinkedHashSet
	/**
	 * Containment relationships within the root itself.
	 */
	@Accessors(PUBLIC_GETTER)
	val Set<Containment> rootContainments = new LinkedHashSet
	/**
	 * Containment relationships between the root's head class and non-root
	 * participation classes.
	 */
	@Accessors(PUBLIC_GETTER)
	val Set<Containment> boundaryContainments = new LinkedHashSet

	// TODO Support for multiple attribute reference mappings for the same
	// domain? There can be multiple attribute reference roots then.
	@Accessors(PACKAGE_SETTER, PUBLIC_GETTER)
	var ParticipationClass attributeReferenceRoot = null
	@Accessors(PUBLIC_GETTER)
	val Set<OperatorContainment> attributeReferenceContainments = new LinkedHashSet

	/**
	 * Whether the root is empty.
	 * <p>
	 * For instance, this is the case if the participation does not specify a
	 * Resource class.
	 * <p>
	 * Commonality participations have an empty root as well, since their root
	 * container is not explicitly specified.
	 */
	def isEmpty() {
		return classes.empty
	}

	def isRootClass(ParticipationClass participationClass) {
		return classes.contains(participationClass)
	}
}
