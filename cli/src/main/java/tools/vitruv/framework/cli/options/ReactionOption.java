package tools.vitruv.framework.cli.options;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import org.apache.commons.cli.CommandLine;
import tools.vitruv.framework.cli.configuration.VitruvConfiguration;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.dsls.reactions.runtime.reactions.AbstractReactionsChangePropagationSpecification;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class ReactionOption extends VitruvCLIOption {

  private File reactionsFile;

  public ReactionOption() {
    super("r", "reaction", true, "The path to the file the Reactions are stored in.");
  }

  @Override
  public VirtualModelBuilder applyInternal(
      CommandLine cmd, VirtualModelBuilder builder, VitruvConfiguration configuration) {
    String reactionsPath = cmd.getOptionValue(getOpt());
    reactionsFile = FileUtils.copyFile(
            reactionsPath, getPath(cmd, builder), "/consistency/src/main/reactions/");
    return builder;
  }

  @Override
  public VirtualModelBuilder postBuild(
      CommandLine cmd, VirtualModelBuilder builder, VitruvConfiguration configuration) {
      ChangePropagationSpecification loadedClass = null;
      try {
        String name = FileUtils.findOption(reactionsFile, "reactions:");
        System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1) + "ChangePropagationSpecification");
        // URLClassLoader urlClassLoader = new URLClassLoader({new URL("D:\temp\vitruv-cli\Vitruv\cli\target\internal\consistency\target\tools.vitruv.methodologisttemplate.consistency-0.1.0-SNAPSHOT.jar")}, ClassLoader.getSystemClassLoader());
        FileUtils.addJarToClassPath("D:/temp/vitruv-cli/Vitruv/cli/target/internal/model/target/tools.vitruv.methodologisttemplate.model-0.1.0-SNAPSHOT.jar");
        FileUtils.addJarToClassPath("D:/temp/vitruv-cli/Vitruv/cli/target/internal/consistency/target/tools.vitruv.methodologisttemplate.consistency-0.1.0-SNAPSHOT.jar");
        loadedClass = (ChangePropagationSpecification) FileUtils.CLASS_LOADER.loadClass("mir.reactions." + name + "." + name.substring(0, 1).toUpperCase() + name.substring(1) + "ChangePropagationSpecification").getDeclaredConstructor().newInstance();
        System.out.println("that works");
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    // TODO extract the name of the generated reaction, find that, load that, and add that to the
    // classpath as well as the builder
    return builder.withChangePropagationSpecification(loadedClass);
  }

  @Override
  public void prepare(CommandLine cmd, VitruvConfiguration configuration) {}
}
