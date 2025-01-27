package tools.vitruv.framework.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.vitruv.framework.cli.options.MetamodelOption;
import tools.vitruv.framework.cli.options.VitruvCLIOption;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class CLI {
  public static void main(String[] args) {
    CLI.parseCLI(new String[]{"-m", "D:/temp/vitruv-cli/Methodologist-Template/model/src/main/ecore/model.ecore,D:/temp/vitruv-cli/Methodologist-Template/model/src/main/ecore/model.genmodel"});
  }

  public static void parseCLI(String[] args) {
    System.out.println("asdfasdfasdfasd");
    VitruvCLIOption metamodel = new MetamodelOption();
    Options options = new Options();
    options.addOption(metamodel);
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
