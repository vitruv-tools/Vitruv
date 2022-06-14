package tools.vitruv.framework.propagation;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.common.util.URI;

import static java.nio.file.Files.createFile;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;

/**
 * Provides functionality to mark file system folders as project root folders with a marker file and identify such a
 * project folder from any given subfolder.
 */
public final class ProjectMarker {
	private static final String TEST_PROJECT_MARKER_FILE_NAME = "test_project.marker_vitruv";

	private ProjectMarker() {
	}

	/**
	 * Marks the folder with the given path as a project root folder by placing a marker file to be identified by
	 * {@link #getProjectRootFolder(URI)}.
	 * @param projectRootFolder the path of the root folder to mark, must not be <code>null</code>
	 * @throws IOException if something went wrong when creating the marker file (see
	 * {@link Files#createFile(Path, java.nio.file.attribute.FileAttribute...)}.
	 */
	public static void markAsProjectRootFolder(Path projectRootFolder) throws IOException {
		createFile(projectRootFolder.resolve(TEST_PROJECT_MARKER_FILE_NAME));
	}

	/**
	 * Returns the path to the project in which the element with the given path is contained. This requires any of the
	 * folders in the containment hierarchy above the folder of the contained element to be initialized as a project root
	 * folder by calling {@link #markAsProjectRootFolder(Path)}. Otherwise, an {@link IllegalStateException} will be thrown.
	 * @param containedElementPath the absolute path to the element to search the project root folder for, must not be
	 * <code>null</code>
	 * @return the path to the project root folder of the element at the given path
	 */
	public static Path getProjectRootFolder(Path containedElementPath) {
		Path potentialProjectPath = containedElementPath;
		// Remove last segment as long as the folder does not contain the marker file
		while (!Files.exists(potentialProjectPath.resolve(TEST_PROJECT_MARKER_FILE_NAME))) {
			potentialProjectPath = potentialProjectPath.getParent();
			checkState(potentialProjectPath != null, "No project folder for %s found", containedElementPath);
		}
		return potentialProjectPath;
	}

}
