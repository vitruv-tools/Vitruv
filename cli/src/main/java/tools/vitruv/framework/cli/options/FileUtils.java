package tools.vitruv.framework.cli.options;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.Enumeration;

import tools.vitruv.framework.cli.CLI;
import tools.vitruv.framework.cli.configuration.CustomClassLoader;

public final class FileUtils {

  public static final CustomClassLoader CLASS_LOADER = new CustomClassLoader(new URL[]{}, FileUtils.class.getClassLoader().getSystemClassLoader());

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
      // Ensure the directory exists
      File parentDir = file.getParentFile();
      if (parentDir != null && !parentDir.exists()) {
        parentDir.mkdirs();
      }
      // Create the file
      if (file.createNewFile()) {
        System.out.println("File created: " + file.getAbsolutePath());
      } else {
        System.out.println("File already exists: " + file.getAbsolutePath());
      }
    } catch (IOException e) {
      System.out.println("An error occurred while creating the file: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static Path createNewFolder(Path path, String folder) {
    Path folderPath = Path.of(path.toString() + "/" + folder);
    File file = folderPath.toFile();
    if (file.mkdirs()) {
      System.out.println("Directory created: " + file.getAbsolutePath());
    } else {
      System.out.println("Directory already exists: " + file.getAbsolutePath());
    }
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
      URL jarUrl = new URL("file:///"+jarPath);
      CLASS_LOADER.addJar(jarUrl);
    } catch (Exception e) {
        e.printStackTrace();
    }

    try {
      // Open the JAR file
      JarFile jarFile = new JarFile(new File(jarPath));
      
      // Get the entries in the JAR file
      Enumeration<JarEntry> entries = jarFile.entries();
      
      // Iterate through the entries
      while (entries.hasMoreElements()) {
          JarEntry entry = entries.nextElement();
          
          // Check if the entry is a class file
          if (entry.getName().endsWith(".class")) {
              // Print the class name
              String className = entry.getName().replace("/", ".").replace(".class", "");
              System.out.println(className);
          }
      }
      
      // Close the JAR file
      jarFile.close();
  } catch (IOException e) {
      e.printStackTrace();
  }

}
}
