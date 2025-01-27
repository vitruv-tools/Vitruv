package tools.vitruv.framework.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.vitruv.framework.cli.options.FolderOption;
import tools.vitruv.framework.cli.options.MetamodelOption;
import tools.vitruv.framework.cli.options.VitruvCLIOption;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class CLI {

  public static void parseCLI(String[] args) {
    Options options = new Options();
    options.addOption(new MetamodelOption());
    options.addOption(new FolderOption());
    CommandLineParser parser = new DefaultParser();
    try {
      CommandLine line = parser.parse(options, args);
      VirtualModelBuilder builder = new VirtualModelBuilder();
      
      for(Option option : line.getOptions()) {
        System.out.println("Evaluating option " + option.getLongOpt() + " with value " + option.getValuesList());
        ((VitruvCLIOption)option).apply(line, builder);
      }
      // TODO run ecore generation
      // TODO run reactions generation
      System.out.println(builder.buildAndInitialize());
    } catch (ParseException exp) {
      System.out.println("Parsing failed.  Reason: " + exp.getMessage());
    }
  }
}
