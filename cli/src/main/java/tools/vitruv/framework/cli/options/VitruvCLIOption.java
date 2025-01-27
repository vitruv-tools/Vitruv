package tools.vitruv.framework.cli.options;

import java.nio.file.Path;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import tools.vitruv.framework.cli.VirtualModelBuilderApplication;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public abstract class VitruvCLIOption extends Option implements VirtualModelBuilderApplication{
  public VitruvCLIOption(String abbreviationName, String longName, boolean hasArguments, String description) {
    super(abbreviationName, longName, hasArguments, description);
  }

  protected Path getPath(CommandLine cmd, VirtualModelBuilder builder) {
    return new FolderOption().getPath(cmd, builder);
  }

  @Override
  public final VirtualModelBuilder apply(CommandLine cmd, VirtualModelBuilder builder) {
    if (!cmd.hasOption(getOpt())) {
      throw new IllegalArgumentException("Command called but not present!");
    }
    return applyInternal(cmd, builder);
  }

  public abstract VirtualModelBuilder applyInternal(CommandLine cmd, VirtualModelBuilder builder);
}
