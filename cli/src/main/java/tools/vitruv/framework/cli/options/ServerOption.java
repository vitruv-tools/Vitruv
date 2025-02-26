package tools.vitruv.framework.cli.options;

import org.apache.commons.cli.CommandLine;

import tools.vitruv.framework.cli.configuration.VitruvConfiguration;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class ServerOption extends VitruvCLIOption {
  public ServerOption() {
    super(
        "s",
        "server",
        true,
        "Denotes whether the vitruv application is supposed to run as a server (with the option and a parameter which denotes the port) or as a monolith (without the option).");
  }

  @Override
  public VirtualModelBuilder applyInternal(
      CommandLine cmd, VirtualModelBuilder builder, VitruvConfiguration configuration) {
    configuration.setServer(true);
    configuration.setPort(Integer.parseInt(cmd.getOptionValue(getOpt())));
    return builder;
  }

  @Override
  public void prepare(CommandLine cmd, VitruvConfiguration configuration) {}
  
}