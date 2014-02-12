package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EcoreResourceBridge;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class TransformationUtils {
  private final static ResourceSet resourceSet = new Function0<ResourceSet>() {
    public ResourceSet apply() {
      ResourceSetImpl _resourceSetImpl = new ResourceSetImpl();
      return _resourceSetImpl;
    }
  }.apply();
  
  private TransformationUtils() {
  }
  
  public static void saveRootEObject(final EObject rootEObject, final VURI path) {
    try {
      URI _eMFUri = path.getEMFUri();
      final Resource resource = TransformationUtils.resourceSet.createResource(_eMFUri);
      EcoreResourceBridge.saveEObjectAsOnlyContent(rootEObject, resource);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
