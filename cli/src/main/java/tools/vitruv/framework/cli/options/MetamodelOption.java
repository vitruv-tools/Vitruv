package tools.vitruv.framework.cli.options;

import java.io.IOException;
import java.nio.file.Files;
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
      System.out.println(metamodelPath + " " + genmodelPath);
      copyFile(metamodelPath);
      copyFile(genmodelPath);
      // TODO modify genmodel paths
    }
    return builder;
  }

  private void copyFile(String filePath) {
    try {
      Files.copy(Paths.get(filePath), Paths.get(Paths.get("").toAbsolutePath().toString()
          + "/cli/internal/src/main/ecore/" + Paths.get(filePath).getFileName()), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
