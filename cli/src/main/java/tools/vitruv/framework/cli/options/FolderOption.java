package tools.vitruv.framework.cli.options;

import java.nio.file.Path;
import org.apache.commons.cli.CommandLine;

import tools.vitruv.framework.cli.configuration.VitruvConfiguration;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class FolderOption extends VitruvCLIOption {
  public FolderOption() {
    super("f", "folder", true,
        "The path to the folder the Vitruv project should be instantiated in.");
  }

  @Override
  public Path getPath(CommandLine cmd, VirtualModelBuilder builder) {
    return Path.of(cmd.getOptionValue(getOpt().replaceAll("\\s", "")));
  }

  @Override
  public VirtualModelBuilder applyInternal(CommandLine cmd, VirtualModelBuilder builder,
      VitruvConfiguration configuration) {
    return builder.withStorageFolder(Path.of(configuration.getLocalPath() + "/virtualmodel/"));
  }

  @Override
  public void prepare(CommandLine cmd, VitruvConfiguration configuration) {
    configuration.setLocalPath(Path.of(cmd.getOptionValue(getOpt().replaceAll("\\s", ""))));
  }
}
