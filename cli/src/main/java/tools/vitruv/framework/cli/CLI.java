package tools.vitruv.framework.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.vitruv.framework.cli.options.FileUtils;
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
    try {
      CommandLine line = parser.parse(options, args);
      VirtualModelBuilder builder = new VirtualModelBuilder();
      for (Option option : line.getOptions()) {
        System.out.println("Preprocessing option " + option.getLongOpt() + " with value " + option.getValuesList());
        ((VitruvCLIOption) option).preBuild(line, builder);
      }
      Path targetPath = copyFiles(((VitruvCLIOption) line.getOptions()[0]).getPath(line, builder));
      String command = "mvn clean verify";
      ProcessBuilder pbuilder = new ProcessBuilder("cmd.exe", "/c", command);
      pbuilder.directory(targetPath.toFile());
      Process process = pbuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String oline;
      while ((oline = reader.readLine()) != null) {
        System.out.println(oline);
      }
      process.waitFor();
      if (process.exitValue() != 0){
        throw new Error("Error occurred during maven build! Please fix your setup accordingly! Exit code: " + process.exitValue());
      }
      // TODO run maven build
      for (Option option : line.getOptions()) {
        System.out.println("Postprocessing option " + option.getLongOpt() + " with value " + option.getValuesList());
        ((VitruvCLIOption) option).postBuild(line, builder);
      }
      System.out.println(builder.buildAndInitialize());
    } catch (ParseException exp) {
      System.out.println("Parsing failed.  Reason: " + exp.getMessage());
    } catch (IOException | InterruptedException e) {
      System.out.println("Invoking maven to build the project failed.  Reason: " + e.getMessage());
    } 
  }

  private Path copyFiles(Path internalPath) {
    File target = FileUtils.copyFile("src/main/resources/pom.xml", internalPath, "");
    FileUtils.copyFile("src/main/resources/consistency/pom.xml", internalPath, "consistency/");
    FileUtils.copyFile("src/main/resources/model/pom.xml", internalPath, "model/");
    FileUtils.copyFile("src/main/resources/model/plugin.xml", internalPath, "model/");
    FileUtils.copyFile("src/main/resources/model/.project", internalPath, "model/");
    FileUtils.copyFile("src/main/resources/model/generate.mwe2", internalPath, "model/workflow/");
    return Path.of(target.getParent());
  }
}
