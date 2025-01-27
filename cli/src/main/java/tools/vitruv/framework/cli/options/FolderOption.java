package tools.vitruv.framework.cli.options;

import java.nio.file.Path;
import org.apache.commons.cli.CommandLine;

import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class FolderOption extends VitruvCLIOption {
  private Path folder;
  public FolderOption() {
    super("f", "folder", true,
        "The path to the folder the Vitruv project should be instantiated in.");
  }

  @Override
  protected Path getPath(CommandLine cmd, VirtualModelBuilder builder) {
    return folder;
  }

  @Override
  public VirtualModelBuilder applyInternal(CommandLine cmd, VirtualModelBuilder builder) {
    System.out.println(cmd.getOptionValue(getOpt()));
    folder = Path.of(cmd.getOptionValue(getOpt()));
    return builder.withStorageFolder(folder);
  }
}
