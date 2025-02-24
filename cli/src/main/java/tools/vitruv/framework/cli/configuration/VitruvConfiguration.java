package tools.vitruv.framework.cli.configuration;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
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
    // Register the GenModel resource factory
    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
    reg.getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());
    reg.getExtensionToFactoryMap().put("genmodel", new XMIResourceFactoryImpl());

    // Register the GenModel package
    GenModelPackage.eINSTANCE.eClass();

    for (String modelPaths : paths.split(";")) {
      String metamodelPath = modelPaths.split(",")[0];
      String genmodelPath = modelPaths.split(",")[1];
      File metamodel = new File(metamodelPath);
      File genmodel = new File(genmodelPath);

      // getting the URI from the genmodels
      ResourceSet resourceSet = new ResourceSetImpl();
      URI uri = URI.createFileURI(metamodel.getAbsolutePath().replaceAll("\\s", ""));
      Resource resource = resourceSet.getResource(uri, true);
      if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof EPackage) {
        EPackage ePackage = (EPackage) resource.getContents().get(0);
        this.addMetamodelLocations(new MetamodelLocation(metamodel, genmodel, ePackage.getNsURI()));

        // Load the GenModel to get the modelPluginID
        URI genmodelURI = URI.createFileURI(genmodel.getAbsolutePath());
        Resource genmodelResource = resourceSet.getResource(genmodelURI, true);
        if (!genmodelResource.getContents().isEmpty() && genmodelResource.getContents().get(0) instanceof GenModel) {
          GenModel genModel = (GenModel) genmodelResource.getContents().get(0);
          String packageString = removeLastSegment(genModel.getModelPluginID());
          this.setPackageName(packageString);
        }
      }
    }
  }

  public static String removeLastSegment(String input) {
    int lastDotIndex = input.lastIndexOf('.');
    if (lastDotIndex == -1) {
      // No dot found, return the original string
      return input;
    }
    return input.substring(0, lastDotIndex);
  }

  public List<MetamodelLocation> getMetaModelLocations() {
    return this.metamodelLocations;
  }

  public String getPackageName() {
    return this.packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName.replace("\\s", "");
  }

}
