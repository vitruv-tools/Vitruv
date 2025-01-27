package tools.vitruv.framework.cli;

import org.apache.commons.cli.CommandLine;

import tools.vitruv.framework.vsum.VirtualModelBuilder;

public interface VirtualModelBuilderApplication {
  VirtualModelBuilder apply (CommandLine cmd, VirtualModelBuilder builder);
}