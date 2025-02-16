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

public final class GenerateMwe2Generator {

    private GenerateMwe2Generator() {}

    public static void generate(File filePath, List<MetamodelLocation> models,
            VitruvConfiguration config) throws IOException {

        // Configure FreeMarker
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        List<Map<String, Object>> items = new ArrayList<>();
        for (MetamodelLocation model : models) {
            items.add(Map.of("targetDir", config.getLocalPath().toString().replaceAll("\\s", ""),
                    "modelName", model.genmodel().getName()));
        }

        // Load template
        Template template = null;
        try {
            template = cfg.getTemplate("generator.ftl");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("items", items);

        FileUtils.createFile(filePath.getAbsolutePath());

        // Write output to file
        try (Writer fileWriter = new FileWriter(filePath.getAbsolutePath(), false)) {
            template.process(data, fileWriter);
            fileWriter.flush();
            System.out.println("writing to " + filePath.getAbsolutePath());
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
