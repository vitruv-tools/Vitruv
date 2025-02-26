package tools.vitruv.framework.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import tools.vitruv.framework.cli.configuration.MetamodelLocation;
import tools.vitruv.framework.cli.configuration.VitruvConfiguration;
import tools.vitruv.framework.cli.options.FileUtils;

public final class GenerateFromTemplate {
  private GenerateFromTemplate() {
  }

  private static Configuration getConfiguration() throws IOException {
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
    cfg.setDefaultEncoding("UTF-8");
    cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    cfg.setLogTemplateExceptions(false);
    cfg.setWrapUncheckedExceptions(true);
    return cfg;
  }

  private static void writeTemplate(Template template, File filePath, Map<String, Object> data)
      throws IOException {
    FileUtils.createFile(filePath.getAbsolutePath());
    // Write output to file
    try (Writer fileWriter = new FileWriter(filePath.getAbsolutePath(), false)) {
      template.process(data, fileWriter);
      fileWriter.flush();
    } catch (TemplateException e) {
      e.printStackTrace();
    }
  }

  public static void generateRootPom(File filePath, String packageName) throws IOException {
    Configuration cfg = getConfiguration();

    Map<String, Object> data = new HashMap<>();
    data.put("packageName", packageName.replaceAll("\\s", ""));

    Template template = null;
    try {
      template = cfg.getTemplate("rootPom.ftl");
    } catch (IOException e) {
      e.printStackTrace();
    }
    writeTemplate(template, filePath, data);
  }

  public static void generateProjectFile(File filePath, String packageName) throws IOException {
    Configuration cfg = getConfiguration();

    Map<String, Object> data = new HashMap<>();
    data.put("packageName", packageName.replaceAll("\\s", ""));

    Template template = null;
    try {
      template = cfg.getTemplate("project.ftl");
    } catch (IOException e) {
      e.printStackTrace();
    }
    writeTemplate(template, filePath, data);
  }

  public static void generateModelPom(File filePath, String packageName) throws IOException {
    Configuration cfg = getConfiguration();

    Map<String, Object> data = new HashMap<>();
    data.put("packageName", packageName);

    Template template = null;
    try {
      template = cfg.getTemplate("modelPom.ftl");
    } catch (IOException e) {
      e.printStackTrace();
    }
    writeTemplate(template, filePath, data);
  }

  public static void generateConsistencyPom(File filePath, String packageName) throws IOException {
    Configuration cfg = getConfiguration();

    Map<String, Object> data = new HashMap<>();
    data.put("packageName", packageName);

    Template template = null;
    try {
      template = cfg.getTemplate("consistencyPom.ftl");
    } catch (IOException e) {
      e.printStackTrace();
    }
    writeTemplate(template, filePath, data);
  }

  public static void generateMwe2(
      File filePath, List<MetamodelLocation> models, VitruvConfiguration config)
      throws IOException {

    Configuration cfg = getConfiguration();
    List<Map<String, Object>> items = new ArrayList<>();
    for (MetamodelLocation model : models) {
      items.add(
          Map.of(
              "targetDir",
              config.getLocalPath().toString().replaceAll("\\s", ""),
              "modelName",
              model.genmodel().getName(),
              "packageName", config.getPackageName().replaceAll("\\s", "").concat(".model")));
    }
    // Load template
    Template template = null;
    try {
      template = cfg.getTemplate("generator.ftl");
    } catch (IOException e) {
      e.printStackTrace();
    }
    Map<String, Object> data = new HashMap<>();
    data.put("items", items);

    writeTemplate(template, filePath, data);
  }

  public static void generatePlugin(
      File filePath, VitruvConfiguration config, List<MetamodelLocation> models)
      throws IOException {
    Configuration cfg = getConfiguration();
    List<Map<String, Object>> items = new ArrayList<>();
    for (MetamodelLocation model : models) {
      items.add(
          Map.of(
              "packageName",
              config.getPackageName(),
              "modelUri",
              model.genmodelUri(),
              "modelNameCap",
              model.genmodel().getName().substring(0, 1).toUpperCase()
                  + model
                      .genmodel()
                      .getName()
                      .substring(1, model.genmodel().getName().indexOf('.')),
              "genmodelName",
              model.genmodel().getName()));
    }
    // Load template
    Template template = null;
    try {
      template = cfg.getTemplate("plugin.ftl");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Map<String, Object> data = new HashMap<>();
    data.put("items", items);
    writeTemplate(template, filePath, data);
  }
}
