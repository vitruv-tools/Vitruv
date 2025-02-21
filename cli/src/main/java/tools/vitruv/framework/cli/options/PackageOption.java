package tools.vitruv.framework.cli.options;

import org.apache.commons.cli.CommandLine;
import tools.vitruv.framework.cli.configuration.VitruvConfiguration;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class PackageOption extends VitruvCLIOption {
  public PackageOption() {
    super("p", "package", true, "The package name of the Vitruv project.");
  }

  @Override
  public VirtualModelBuilder applyInternal(
      CommandLine cmd, VirtualModelBuilder builder, VitruvConfiguration configuration) {
    return builder;
  }

  @Override
  public void prepare(CommandLine cmd, VitruvConfiguration configuration) {
    configuration.setPackageName(cmd.getOptionValue(getOpt()));
  }
}
