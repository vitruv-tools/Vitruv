package tools.vitruv.extensions.changevisualization.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import tools.vitruv.extensions.changevisualization.common.ModelRepositoryChanges;

/**
 * This class helps to store and load {@link ModelRepositoryChanges}
 */
public final class ChangeDataSetPersistenceHelper {

	/**
	 * The default file ending
	 */
	public static final String FILE_ENDING = ".pcf";

	/**
	 * The description to use in dialogs
	 */
	public static final String FILE_DESCRIPTION = "Propagated Changes Files (" + FILE_ENDING + ")";

	/**
	 * This marks the end of the file
	 */
	private static final String EOF_MARKER = "THE_END";

	/**
	 * No instances of ChangeDataSetPersistenceHelper are used
	 */
	private ChangeDataSetPersistenceHelper() {
		// Not used
	}

	/**
	 * Loads {@link ModelRepositoryChanges} from a given file.
	 * @param file The file to load, must not be <code>null</code>
	 * @return List of ModelRepositoryChanges loaded
	 * @throws IOException if an error occurred when reading the file
	 */
	public static List<ModelRepositoryChanges> load(File file) throws IOException {
		FileInputStream fIn = new FileInputStream(file);
		GZIPInputStream gzIn = new GZIPInputStream(fIn);

		List<ModelRepositoryChanges> modelRepositoryChanges = new ArrayList<>();
		try (ObjectInputStream oIn = new ObjectInputStream(gzIn)) {
			Object readObject = oIn.readObject();
			while (!EOF_MARKER.equals(readObject)) {
				modelRepositoryChanges.add((ModelRepositoryChanges) readObject);
				readObject = oIn.readObject();
			}
	 	} catch (IOException | ClassNotFoundException e) {
			throw new IOException("File " + file + " could not be loaded", e);
		}
	
		return modelRepositoryChanges;
	}

	/**
	 * Saves given {@link ModelRepositoryChanges} to a given file.
	 * @param file The file to save to, must not be <code>null</code>
	 * @param modelRepositoryChangesToSave List of repository changes to save, must not be <code>null</code>
	 * @throws IOException if an error occurred when writing the file
	 */
	public static void save(File file, List<ModelRepositoryChanges> modelRepositoryChangesToSave) throws IOException {
		FileOutputStream fOut = null;
		if (!file.getName().toLowerCase().endsWith(FILE_ENDING)) {
			fOut = new FileOutputStream(new File(file.getParentFile(), file.getName() + FILE_ENDING));
		} else {
			fOut = new FileOutputStream(file);
		}
		GZIPOutputStream gzOut = new GZIPOutputStream(fOut);

		try (ObjectOutputStream oOut = new ObjectOutputStream(gzOut)) {
			for (ModelRepositoryChanges modelRepositoryChanges : modelRepositoryChangesToSave) {
				oOut.writeObject(modelRepositoryChanges);
			}
			// Last step, write the EOF_MARKER
			oOut.writeObject(EOF_MARKER);
			oOut.flush();
		}
	}

}
