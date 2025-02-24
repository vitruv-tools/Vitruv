package tools.vitruv.framework.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.vitruv.framework.cli.configuration.VitruvConfiguration;
import tools.vitruv.framework.cli.options.FolderOption;
import tools.vitruv.framework.cli.options.MetamodelOption;
import tools.vitruv.framework.cli.options.ReactionOption;
import tools.vitruv.framework.cli.options.UserInteractorOption;
import tools.vitruv.framework.cli.options.VitruvCLIOption;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class CLI {

  public static void main(String[] args) {
    new CLI().parseCLI(args);
  }

  public void parseCLI(String[] args) {
    Options options = new Options();
    options.addOption(new MetamodelOption());
    options.addOption(new FolderOption());
    options.addOption(new UserInteractorOption());
    options.addOption(new ReactionOption());
    CommandLineParser parser = new DefaultParser();
    VitruvConfiguration configuration = new VitruvConfiguration();

    try {
      CommandLine line = parser.parse(options, args);
      VirtualModelBuilder builder = new VirtualModelBuilder();
      for (Option option : line.getOptions()) {
        System.out.println(
            "Preparing option " + option.getLongOpt() + " with value " + option.getValuesList());
        ((VitruvCLIOption) option).prepare(line, configuration);
      }
      generateFiles(configuration);
      for (Option option : line.getOptions()) {
        System.out.println(
            "Preprocessing option "
                + option.getLongOpt()
                + " with value "
                + option.getValuesList());
        ((VitruvCLIOption) option).preBuild(line, builder, configuration);
      }
      ProcessBuilder pbuilder;
      String command = "mvn clean verify";
      if (System.getProperty("os.name").toLowerCase().contains("win")) {
        pbuilder = new ProcessBuilder("cmd.exe", "/c", command);
      } else {
        pbuilder = new ProcessBuilder("bash", "-c", command);
      }
      pbuilder.directory(
          new File(
              configuration
                  .getLocalPath()
                  .toFile()
                  .getAbsoluteFile()
                  .toString()
                  .replaceAll("\\s", "")));
      Process process = pbuilder.start();

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String oline;
      while ((oline = reader.readLine()) != null) {
        System.out.println(oline);
      }
      process.waitFor();
      if (process.exitValue() != 0) {
        throw new Error(
            "Error occurred during maven build! Please fix your setup accordingly! Exit code: "
                + process.exitValue());
      }
      for (Option option : line.getOptions()) {
        System.out.println(
            "Postprocessing option "
                + option.getLongOpt()
                + " with value "
                + option.getValuesList());
        ((VitruvCLIOption) option).postBuild(line, builder, configuration);
      }
      System.out.println(builder.buildAndInitialize());
    } catch (ParseException exp) {
      System.out.println("Parsing failed.  Reason: " + exp.getMessage());
    } catch (IOException | InterruptedException e) {
      System.out.println("Invoking maven to build the project failed.  Reason: " + e.getMessage());
    }
  }

  private void generateFiles(VitruvConfiguration configuration) throws IOException {
    GenerateFromTemplate.generateRootPom(
        new File((configuration.getLocalPath() + "/pom.xml").replaceAll("\\s", "")),
        configuration.getPackageName());
    GenerateFromTemplate.generateConsistencyPom(
        new File((configuration.getLocalPath() + "/consistency/pom.xml").replaceAll("\\s", "")),
        configuration.getPackageName());
    GenerateFromTemplate.generateModelPom(
        new File((configuration.getLocalPath() + "/model/pom.xml").replaceAll("\\s", "")),
        configuration.getPackageName());
    GenerateFromTemplate.generateProjectFile(
        new File((configuration.getLocalPath() + "/model/.project").replaceAll("\\s", "")),
        configuration.getPackageName());
    File workflow = new File(
        (configuration.getLocalPath() + "/model/workflow/generate.mwe2").replaceAll("\\s", ""));
    configuration.setWorkflow(workflow);
    GenerateFromTemplate.generateMwe2(
        workflow, configuration.getMetaModelLocations(), configuration);
    GenerateFromTemplate.generatePlugin(
        new File((configuration.getLocalPath() + "/model/plugin.xml").replaceAll("\\s", "")),
        configuration,
        configuration.getMetaModelLocations());
  }

}
