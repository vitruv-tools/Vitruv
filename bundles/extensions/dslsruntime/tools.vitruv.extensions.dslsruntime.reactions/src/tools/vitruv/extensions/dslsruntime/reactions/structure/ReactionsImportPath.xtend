package tools.vitruv.extensions.dslsruntime.reactions.structure

import com.google.common.collect.ImmutableList
import java.util.List
import java.util.regex.Pattern

import static com.google.common.base.Preconditions.*

/**
 * This class describes the path between reactions segments inside the reactions import hierarchy.
 */
class ReactionsImportPath {

	// path string representation:

	public static final String PATH_STRING_SEPARATOR = ".";
	private static final Pattern PATH_STRING_SEPARATOR_PATTERN = Pattern.compile(Pattern.quote(PATH_STRING_SEPARATOR));

	public static def ReactionsImportPath fromPathString(String pathString) {
		checkNotNull(pathString, "pathString is null");
		val pathSegments = PATH_STRING_SEPARATOR_PATTERN.split(pathString, -1);
		return create(pathSegments);
	}

	// construction:

	public static def ReactionsImportPath create(Iterable<String> pathSegments) {
		return new ReactionsImportPath(pathSegments);
	}

	public static def ReactionsImportPath create(String... pathSegments) {
		return create(pathSegments as Iterable<String>);
	}

	public static def ReactionsImportPath create(Iterable<String> parentPath, String... pathSegments) {
		return create(parentPath, pathSegments as Iterable<String>);
	}

	public static def ReactionsImportPath create(Iterable<String> parentPath, Iterable<String> pathSegments) {
		return create((parentPath ?: #[]) + (pathSegments ?: #[]));
	}

	// the names of the reactions segments along the path:
	private val List<String> segments; // immutable, not empty, does not contain null

	private new(Iterable<String> pathSegments) {
		checkArgument(!pathSegments.isNullOrEmpty, "pathSegments is null or empty");
		this.segments = ImmutableList.copyOf(pathSegments);
	}

	/**
	 * Gets an unmodifiable view on the segments contained in this import path.
	 */
	public def List<String> getSegments() {
		return segments;
	}

	/**
	 * Gets the length of this import path. 
 	*/
	public def int getLength() {
		return segments.size();
	}

	/**
	 * Gets the path segment at the specified index. 
	 */
	public def String getSegment(int index) {
		return segments.get(index);
	}

	/**
	 * Gets the last path segment.
	 */
	public def String getLastSegment() {
		return segments.get(segments.size() - 1);
	}

	/**
	 * Gets the first path segment.
	 */
	public def String getFirstSegment() {
		return segments.get(0);
	}

	/**
	 * Creates a reactions import path with the given segments appended.
	 * 
	 * @return the resulting reactions import path
	 */
	public def ReactionsImportPath append(String... pathSegments) {
		return this.append(pathSegments as Iterable<String>);
	}

	/**
	 * Creates a reactions import path with the given segments appended.
	 * 
	 * @return the resulting reactions import path
	 */
	public def ReactionsImportPath append(Iterable<String> pathSegments) {
		return ReactionsImportPath.create(this.segments, pathSegments);
	}

	/**
	 * Creates a reactions import path with the given path appended.
	 * 
	 * @return the resulting reactions import path
	 */
	public def ReactionsImportPath append(ReactionsImportPath path) {
		return ReactionsImportPath.create(this.segments, path?.segments);
	}

	/**
	 * Creates a reactions import path with the given segments prepended.
	 * 
	 * @return the resulting reactions import path
	 */
	public def ReactionsImportPath prepend(String... pathSegments) {
		return this.prepend(pathSegments as Iterable<String>);
	}

	/**
	 * Creates a reactions import path with the given segments prepended.
	 * 
	 * @return the resulting reactions import path
	 */
	public def ReactionsImportPath prepend(Iterable<String> pathSegments) {
		return ReactionsImportPath.create(pathSegments, this.segments);
	}

	/**
	 * Creates a reactions import path with the given path prepended.
	 * 
	 * @return the resulting reactions import path
	 */
	public def ReactionsImportPath prepend(ReactionsImportPath path) {
		return ReactionsImportPath.create(path?.segments, this.segments);
	}

	/**
	 * Gets the reactions import path with the last segment omitted.
	 * 
	 * @return the resulting reactions import path, or <code>null</code> if the resulting path would be empty
	 */
	public def ReactionsImportPath getParent() {
		val parentPath = segments.take(length - 1);
		if (parentPath.empty) return null;
		return ReactionsImportPath.create(parentPath);
	}

	/**
	 * Gets the reactions import path with the first segment omitted.
	 * 
	 * @return the resulting reactions import path, or <code>null</code> if the resulting path would be empty
	 */
	public def ReactionsImportPath relativeToRoot() {
		val tailPath = segments.tail;
		if (tailPath.empty) return null;
		return ReactionsImportPath.create(tailPath);
	}

	/**
	 * Creates a reactions import path that is the sub-path of this path starting with the segment following the specified segment.
	 * 
	 * @return the resulting reactions import path, or <code>null</code> if the specified segment is not contained or the last segment of this path
	 */
	public def ReactionsImportPath relativeTo(String pathSegment) {
		val index = segments.indexOf(pathSegment);
		if (index == -1 || index == (length - 1)) {
			// segment is not contained or the last segment of the path:
			return null;
		}
		val relativePathSegments = segments.subList(index + 1, length);
		return ReactionsImportPath.create(relativePathSegments);
	}

	public def String getPathString() {
		return segments.join(PATH_STRING_SEPARATOR);
	}

	public override String toString() {
		return "ReactionsImportPath=" + this.pathString;
	}

	public override int hashCode() {
		return segments.hashCode();
	}

	public override boolean equals(Object obj) {
		if (this === obj) return true;
		if (obj === null) return false;
		if (!(obj instanceof ReactionsImportPath)) return false;
		val other = obj as ReactionsImportPath;
		if (!segments.equals(other.segments)) return false;
		return true;
	}
}
