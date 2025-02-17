package tools.vitruv.framework.cli.options;

import java.io.File;
import org.apache.commons.cli.CommandLine;

import tools.vitruv.framework.cli.configuration.VitruvConfiguration;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class ReactionOption extends VitruvCLIOption {

  private File reactionsFile;

  public ReactionOption() {
    super("r", "reaction", true, "The path to the file the Reactions are stored in.");
  }

  @Override
  public VirtualModelBuilder applyInternal(CommandLine cmd, VirtualModelBuilder builder,
      VitruvConfiguration configuration) {
    String reactionsPath = cmd.getOptionValue(getOpt());
    reactionsFile = FileUtils.copyFile(reactionsPath, getPath(cmd, builder),
        "/consistency/src/main/reactions/");
    return builder;
  }

  @Override
  public VirtualModelBuilder postBuild(CommandLine cmd, VirtualModelBuilder builder,
      VitruvConfiguration configuration) {
    // TODO extract the name of the generated reaction, find that, load that, and add that to the
    // classpath as well as the builder
    return builder.withChangePropagationSpecification(null);
  }

  @Override
  public void prepare(CommandLine cmd, VitruvConfiguration configuration) {

  }


}
