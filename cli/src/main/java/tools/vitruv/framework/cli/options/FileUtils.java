package tools.vitruv.framework.cli.options;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.net.URL;
import tools.vitruv.framework.cli.configuration.CustomClassLoader;

public final class FileUtils {

  public static final CustomClassLoader CLASS_LOADER = new CustomClassLoader(new URL[] {},
      ClassLoader.getSystemClassLoader());

  public static File copyFile(String filePath, Path folderPath, String relativeSubfolder) {
    File source;
    File target;
    if (new File(filePath).isAbsolute()) {
      source = Path.of(filePath).toFile();
    } else {
      source = Path.of(
          new File("").getAbsolutePath().replaceAll("\\s", "")
              + "/"
              + filePath.replaceAll("\\s", ""))
          .toFile();
    }
    if (folderPath.isAbsolute()) {
      target = folderPath.toFile();
    } else {
      target = Path.of(
          new File("").getAbsolutePath().replaceAll("\\s", "")
              + "/"
              + folderPath.toString().replaceAll("\\s", "")
              + "/"
              + relativeSubfolder
              + source.getName().replaceAll("\\s", ""))
          .toFile();
    }
    // Files.copy throws a misleading Exception if the target File and/or the
    // folders of the target file are not existing.
    System.out.println(
        "Copying file " + source.getAbsolutePath() + " to  " + target.getAbsolutePath());
    target.getParentFile().mkdirs();
    try {
      target.createNewFile();
      Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return target;
  }

  public static void createFile(String filePath) {
    File file = new File(filePath);
    try {
      File parentDir = file.getParentFile();
      if (parentDir != null && !parentDir.exists()) {
        parentDir.mkdirs();
      }
      file.createNewFile();
    } catch (IOException e) {
      System.out.println("An error occurred while creating the file: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static Path createNewFolder(Path path, String folder) {
    Path folderPath = Path.of(path.toString() + "/" + folder);
    folderPath.toFile().mkdirs();
    return folderPath;
  }

  public static String findOption(File file, String option) {
    try {
      for (String line : Files.readAllLines(file.toPath())) {
        if (line.startsWith(option)) {
          return line.substring(option.length()).trim();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new IllegalArgumentException("Option: " + option + "not found in given file!");
  }

  public static void addJarToClassPath(String jarPath) {
    try {
      URL jarUrl = new URL("file:///" + jarPath);
      CLASS_LOADER.addJar(jarUrl);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
