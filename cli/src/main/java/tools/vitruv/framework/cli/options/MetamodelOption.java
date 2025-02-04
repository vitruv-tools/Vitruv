package tools.vitruv.framework.cli.options;

import java.io.File;
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
      File metamodel = FileUtils.copyFile(metamodelPath, getPath(cmd, builder), "/model/src/main/ecore/");
      File genmodel = FileUtils.copyFile(genmodelPath, getPath(cmd, builder), "/model/src/main/ecore/");
      modifyPathsInsideGenmodel(metamodel, genmodel);
    }
    return builder;
  }

  private void modifyPathsInsideGenmodel(File metamodel, File genmodel) {
// TODO
  }
}
