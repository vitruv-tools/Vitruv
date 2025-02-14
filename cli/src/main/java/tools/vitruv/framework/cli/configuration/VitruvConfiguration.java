package tools.vitruv.framework.cli.configuration;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VitruvConfiguration {
  private Path localPath;
  public Path getLocalPath() {
    return localPath;
  }
  public void setLocalPath(Path localPath) {
    this.localPath = localPath;
  }
  private List<MetamodelLocation> metamodelLocations = new ArrayList<>();

  public boolean addMetamodelLocations(MetamodelLocation metamodelLocations) {
    return this.metamodelLocations.add(metamodelLocations);
  }

  private File workflow;
  public File getWorkflow() {
    return workflow;
  }
  public void setWorkflow(File workflow) {
    this.workflow = workflow;
  }
}

