package tools.vitruv.framework.cli;

import org.apache.commons.cli.CommandLine;

import tools.vitruv.framework.vsum.VirtualModelBuilder;

public interface VirtualModelBuilderApplication {
  VirtualModelBuilder preBuild (CommandLine cmd, VirtualModelBuilder builder);
  VirtualModelBuilder postBuild (CommandLine cmd, VirtualModelBuilder builder);
}