package tools.vitruv.extensions.dslsruntime.reactions.structure

import com.google.common.collect.ImmutableList
import java.util.List
import java.util.regex.Pattern

import static com.google.common.base.Preconditions.*

/**
 * Describes paths between reactions segments inside the reactions import hierarchy.
 */
class ReactionsImportPath {

	public static final ReactionsImportPath EMPTY_PATH = new ReactionsImportPath(#[]);

	// path string representation:

	/**
	 * The separator used between path segments in the String representation of {@link ReactionsImportPath ReactionsImportPaths}.
	 */
	public static final String PATH_STRING_SEPARATOR = ".";
	static final Pattern PATH_STRING_SEPARATOR_PATTERN = Pattern.compile(Pattern.quote(PATH_STRING_SEPARATOR));

	/**
	 * Creates a {@link ReactionsImportPath} from the given path String.
	 * 
	 * @param pathString the pathString
	 * @return the reactions import path
	 */
	static def ReactionsImportPath fromPathString(String pathString) {
		if (pathString.isNullOrEmpty) return EMPTY_PATH;
		val pathSegments = PATH_STRING_SEPARATOR_PATTERN.split(pathString, -1);
		return create(pathSegments);
	}

	// construction:

	static def ReactionsImportPath create(Iterable<String> pathSegments) {
		if (pathSegments.isNullOrEmpty) return EMPTY_PATH;
		return new ReactionsImportPath(pathSegments);
	}

	static def ReactionsImportPath create(String... pathSegments) {
		return create(pathSegments as Iterable<String>);
	}

	static def ReactionsImportPath create(Iterable<String> parentPath, String... pathSegments) {
		return create(parentPath, pathSegments as Iterable<String>);
	}

	static def ReactionsImportPath create(Iterable<String> parentPath, Iterable<String> pathSegments) {
		return create((parentPath ?: #[]) + (pathSegments ?: #[]));
	}

	// the names of the reactions segments along the path:
	val List<String> segments; // immutable, can be empty, does not contain null

	private new(Iterable<String> pathSegments) {
		checkNotNull(pathSegments, "pathSegments is null");
		this.segments = ImmutableList.copyOf(pathSegments);
	}

	/**
	 * Gets an unmodifiable view on the segments of this import path.
	 * 
	 * @return an unmodifiable view on the segments of this import path, can be empty
	 */
	def List<String> getSegments() {
		return segments;
	}

	/**
	 * Gets the number of segments this import path consists of.
	 * 
	 * @return the number of segments this import path consists of
	 */
	def int getLength() {
		return segments.size();
	}

	/**
	 * Checks whether this import path is empty.
	 * 
	 * @return <code>true</code> if this import path is empty
	 */
	def boolean isEmpty() {
		return (this.length == 0);
	}

	/**
	 * Gets the path segment at the specified index.
	 * 
	 * @param index the index
	 * @return the path segment at the specified index
	 */
	def String getSegment(int index) {
		return segments.get(index);
	}

	/**
	 * Gets the last path segment.
	 * 
	 * @return the last path segment
	 */
	def String getLastSegment() {
		return segments.get(segments.size() - 1);
	}

	/**
	 * Gets the first path segment.
	 * 
	 * @return the first path segment
	 */
	def String getFirstSegment() {
		return segments.get(0);
	}

	/**
	 * Creates a reactions import path with the given segments appended.
	 * 
	 * @param pathSegments the path segments to append, can be <code>null</code>
	 * @return the resulting reactions import path
	 */
	def ReactionsImportPath append(String... pathSegments) {
		return this.append(pathSegments as Iterable<String>);
	}

	/**
	 * Creates a reactions import path with the given segments appended.
	 * 
	 * @param pathSegments the path segments to append, can be <code>null</code>
	 * @return the resulting reactions import path
	 */
	def ReactionsImportPath append(Iterable<String> pathSegments) {
		return ReactionsImportPath.create(this.segments, pathSegments);
	}

	/**
	 * Creates a reactions import path with the given path appended.
	 * 
	 * @param path the path to append, can be <code>null</code>
	 * @return the resulting reactions import path
	 */
	def ReactionsImportPath append(ReactionsImportPath path) {
		return ReactionsImportPath.create(this.segments, path?.segments);
	}

	/**
	 * Creates a reactions import path with the given segments prepended.
	 * 
	 * @param pathSegments the path segments to prepend, can be <code>null</code>
	 * @return the resulting reactions import path
	 */
	def ReactionsImportPath prepend(String... pathSegments) {
		return this.prepend(pathSegments as Iterable<String>);
	}

	/**
	 * Creates a reactions import path with the given segments prepended.
	 * 
	 * @param pathSegments the path segments to prepend, can be <code>null</code>
	 * @return the resulting reactions import path
	 */
	def ReactionsImportPath prepend(Iterable<String> pathSegments) {
		return ReactionsImportPath.create(pathSegments, this.segments);
	}

	/**
	 * Creates a reactions import path with the given path prepended.
	 * 
	 * @param path the path to prepend, can be <code>null</code>
	 * @return the resulting reactions import path
	 */
	def ReactionsImportPath prepend(ReactionsImportPath path) {
		return ReactionsImportPath.create(path?.segments, this.segments);
	}

	/**
	 * Gets the reactions import path with the last segment omitted.
	 * 
	 * @return the resulting reactions import path, can be empty
	 */
	def ReactionsImportPath getParent() {
		val parentPath = segments.take(length - 1);
		return ReactionsImportPath.create(parentPath);
	}

	/**
	 * Gets the reactions import path with the first segment omitted.
	 * 
	 * @return the resulting reactions import path, can be empty
	 */
	def ReactionsImportPath relativeToRoot() {
		val tailPath = segments.tail;
		return ReactionsImportPath.create(tailPath);
	}

	/**
	 * Creates a reactions import path that is the sub-path of this path starting with the segment following the specified segment.
	 * 
	 * @return the resulting reactions import path, or an empty path if the specified segment is not contained or the last segment of this path
	 */
	def ReactionsImportPath relativeTo(String pathSegment) {
		val index = segments.indexOf(pathSegment);
		if (index == -1 || index == (length - 1)) {
			// segment is not contained or the last segment of the path:
			return EMPTY_PATH;
		}
		val relativePathSegments = segments.subList(index + 1, length);
		return ReactionsImportPath.create(relativePathSegments);
	}

	/**
	 * Creates a reactions import path that is the sub-path of this path ending with the specified segment.
	 * 
	 * @return the resulting reactions import path, or an empty path if the specified segment is not contained in this path
	 */
	def ReactionsImportPath subPathTo(String pathSegment) {
		val index = segments.indexOf(pathSegment);
		if (index == -1) {
			// segment is not contained in this path:
			return EMPTY_PATH;
		}
		val subPathSegments = segments.subList(0, index + 1);
		return ReactionsImportPath.create(subPathSegments);
	}

	/**
	 * Gets the String representation of this import path.
	 * 
	 * @return the String representation of this import path
	 */
	def String getPathString() {
		return segments.join(PATH_STRING_SEPARATOR);
	}

	override String toString() {
		return "ReactionsImportPath=" + this.pathString;
	}

	override int hashCode() {
		return segments.hashCode();
	}

	override boolean equals(Object obj) {
		if (this === obj) return true;
		if (obj === null) return false;
		if (!(obj instanceof ReactionsImportPath)) return false;
		val other = obj as ReactionsImportPath;
		if (!segments.equals(other.segments)) return false;
		return true;
	}
}
