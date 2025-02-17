package tools.vitruv.framework.cli.options;

import java.io.File;

import tools.vitruv.change.testutils.TestUserInteraction;
import org.apache.commons.cli.CommandLine;

import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.framework.cli.configuration.VitruvConfiguration;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class UserInteractorOption extends VitruvCLIOption {
  public static final String DEFAULT = "default";

  public UserInteractorOption() {
    super("u", "userinteractor", true,
        "Specify the path to a specific user interactor, use the keyword '" + DEFAULT
            + "' to denote that you want to use a default user interactor without functionality.");
  }

  @Override
  public VirtualModelBuilder applyInternal(CommandLine cmd, VirtualModelBuilder builder,
      VitruvConfiguration configuration) {
    String userInteractorPath = cmd.getOptionValue(getOpt());
    if (userInteractorPath.equals(DEFAULT)) {
      return builder.withUserInteractorForResultProvider(
          new TestUserInteraction.ResultProvider(new TestUserInteraction()));
    }
    return builder;
  }

  @Override
  public void prepare(CommandLine cmd, VitruvConfiguration configuration) {

  }
}
