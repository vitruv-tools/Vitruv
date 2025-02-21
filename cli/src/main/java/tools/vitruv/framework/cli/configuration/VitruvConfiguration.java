package tools.vitruv.framework.cli.configuration;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class VitruvConfiguration {
  private Path localPath;
  private String packageName;

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

  public void setMetaModelLocations(String paths) {
    for (String modelPaths : paths.split(";")) {
      String metamodelPath = modelPaths.split(",")[0];
      String genmodelPath = modelPaths.split(",")[1];
      File metamodel = new File(metamodelPath);
      File genmodel = new File(genmodelPath);
      // getting the URI from the  genmodels
      Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
      reg.getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());
      ResourceSet resourceSet = new ResourceSetImpl();
      URI uri = URI.createFileURI(metamodel.getAbsolutePath().replaceAll("\\s", ""));
      Resource resource = resourceSet.getResource(uri, true);
      if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof EPackage) {
        EPackage ePackage = (EPackage) resource.getContents().get(0);
        this.addMetamodelLocations(new MetamodelLocation(metamodel, genmodel, ePackage.getNsURI()));
      }
    }
  }

  public List<MetamodelLocation> getMetaModelLocations() {
    return this.metamodelLocations;
  }

  public String getPackageName() {
    return this.packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }
}
