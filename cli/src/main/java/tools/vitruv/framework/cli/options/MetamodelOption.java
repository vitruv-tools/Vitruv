package tools.vitruv.framework.cli.options;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.cli.CommandLine;

import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class MetamodelOption extends VitruvCLIOption {
  public MetamodelOption() {
    super("m", "metamodel", true,
        "A semicolon separated list of pairs of paths to the metamodels and their genmodels that are used in the reactions, e.g., MyMetamodel.ecore,MyGenmodel.genmodel;MyMetamodel1.ecore,MyGenmodel1.genmodel");
  }

  @Override
  public VirtualModelBuilder applyInternal(CommandLine cmd, VirtualModelBuilder builder) {
    System.out.println(getPath(cmd, builder));
    System.out.println(cmd.getOptionValue(getOpt()));
    for (String metamodel : cmd.getOptionValue(getOpt()).split(";")) {
      String metamodelPath = metamodel.split(",")[0];
      String genmodelPath = metamodel.split(",")[1];
      copyFile(metamodelPath, getPath(cmd, builder));
      copyFile(genmodelPath, getPath(cmd, builder));
      // TODO modify genmodel paths
    }
    return builder;
  }

  private void copyFile(String filePath, Path folderPath) {
    try {
      Path source;
      Path target;
      if (new File(filePath).isAbsolute()) {
        source = Path.of(filePath);
      } else {
        source = Path.of(new File("").getAbsolutePath() + "/" +  filePath);
      }
      if (folderPath.isAbsolute()) {
        System.out.println(folderPath.isAbsolute());
        target = folderPath;
      } else {
        target = Path.of(new File("").getAbsolutePath() + "/" +  folderPath.toString());
      }
      System.out.println("Copy " + source.toString() + " to " + Path.of(target.toAbsolutePath() + "/models/src/main/ecore/" + Paths.get(filePath).getFileName()).toString());      
      Files.copy(source, Path.of(target.toAbsolutePath() + "/models/src/main/ecore/" + Paths.get(filePath).getFileName()), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
