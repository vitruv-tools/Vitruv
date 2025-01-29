package tools.vitruv.framework.cli.options;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    for (String modelPaths : cmd.getOptionValue(getOpt()).split(";")) {
      String metamodelPath = modelPaths.split(",")[0];
      String genmodelPath = modelPaths.split(",")[1];
      File metamodel = modifyAndCopyFile(metamodelPath, getPath(cmd, builder));
      File genmodel = modifyAndCopyFile(genmodelPath, getPath(cmd, builder));
      modifyPathsInsideGenmodel(metamodel, genmodel);
    }
    return builder;
  }

  private File modifyAndCopyFile(String filePath, Path folderPath) {
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
          new File("").getAbsolutePath() + "/" + folderPath.toString() + "/models/src/main/ecore/" + source.getName())
          .toFile();
    }
    // Files.copy throws a misleading Exception if the target File and/or the
    // folders of the target file are not existing.
    target.getParentFile().mkdirs();
    try {
      target.createNewFile();
      Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return target;
  }

  private void modifyPathsInsideGenmodel(File metamodel, File genmodel) {
// TODO
  }
}
