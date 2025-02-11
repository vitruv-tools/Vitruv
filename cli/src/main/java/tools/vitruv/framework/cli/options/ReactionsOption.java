package tools.vitruv.framework.cli.options;

import org.apache.commons.cli.CommandLine;

import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class ReactionsOption extends VitruvCLIOption {

  public ReactionsOption() {
    super("rs", "reactions", true,
        "The path to the folder the Reactions files are stored in.");
  }

  @Override
  public VirtualModelBuilder applyInternal(CommandLine cmd, VirtualModelBuilder builder) {
    throw new UnsupportedOperationException("Functionality not implemented yet!");
  }

}
