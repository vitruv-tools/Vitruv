package tools.vitruv.variability.vave.tests.generator;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import ca.slashdev.javapp.JavaPp;
import ca.slashdev.javapp.JavaPpException;

/**
 * This generator uses the JavaPP preprocessor.
 */
public class JavaPpGenerator {

	private static final int MIN_FILESIZE = 5;

	private JavaPp pp;

	private String prefix = "//#";

	private File templatesFolder;
	private File outputFolder;

	private String[] features;

	public JavaPpGenerator(File templatesFolder, String[] features) {
		if (features == null)
			throw new IllegalArgumentException("Features was null.");

		this.templatesFolder = templatesFolder;
		this.features = features;

		Hashtable<Object, Object> env = new Hashtable<Object, Object>();
		for (String feature : this.features) {
			env.put(feature, "on");
		}
		this.pp = new JavaPp(prefix, env);
	}

	public void generateFiles(File dir) {
		outputFolder = new File(dir, "src");
		if (!outputFolder.exists())
			outputFolder.mkdirs();

		// Generate Java files
		this.generateFilesRecursively(this.templatesFolder);
	}

	public void generateFile(File dir, File f) {
		outputFolder = new File(dir, "src");
		if (!outputFolder.exists())
			outputFolder.mkdirs();

		if (f.isFile()) {
			if (f.getName().endsWith(".java")) {
				String pathname = f.getPath().substring(f.getPath().indexOf(this.templatesFolder.getPath()) + this.templatesFolder.getPath().length());

				File srcfile = new File(this.outputFolder, pathname);

				if (!srcfile.exists())
					srcfile.getParentFile().mkdirs();

				this.generateClass(srcfile, f.getPath());
			}
		}
	}

	private void generateFilesRecursively(File dir) {
		File[] filelist = dir.listFiles();

		for (File f : filelist) {
			if (f.isDirectory() && !f.getName().contains(".svn")) {
				this.generateFilesRecursively(f);
			} else if (f.isFile()) {
				if (f.getName().endsWith(".java")) {
					String pathname = f.getPath().substring(f.getPath().indexOf(this.templatesFolder.getPath()) + this.templatesFolder.getPath().length());

					File srcfile = new File(this.outputFolder, pathname);

					if (!srcfile.exists())
						srcfile.getParentFile().mkdirs();

					this.generateClass(srcfile, f.getPath());
				}
			}
		}
	}

	/** Generates the file for a class. */
	private void generateClass(File file, String templateName) {
		try {
			pp.process(new File(templateName), file);

			if (file.length() <= MIN_FILESIZE)
				file.delete();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (JavaPpException e2) {
			e2.printStackTrace();
		}
	}

}
