package tools.vitruv.framework.cli.options;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class FileUtils {
  public static File copyFile(String filePath, Path folderPath, String relativeSubfolder) {
    File source;
    File target;
    if (new File(filePath).isAbsolute()) {
      source = Path.of(filePath).toFile();
    } else {
      source = Path.of(new File("").getAbsolutePath() + "/" + filePath).toFile();
    }
    if (folderPath.isAbsolute()) {
      target = folderPath.toFile();
    } else {
      target = Path.of(
          new File("").getAbsolutePath() + "/" + folderPath.toString() + "/" + relativeSubfolder + source.getName())
          .toFile();
    }
    // Files.copy throws a misleading Exception if the target File and/or the
    // folders of the target file are not existing.
    System.out.println("Copying file " + source.getAbsolutePath() + " to  " + target.getAbsolutePath());
    target.getParentFile().mkdirs();
    try {
      target.createNewFile();
      Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return target;
  }
}
