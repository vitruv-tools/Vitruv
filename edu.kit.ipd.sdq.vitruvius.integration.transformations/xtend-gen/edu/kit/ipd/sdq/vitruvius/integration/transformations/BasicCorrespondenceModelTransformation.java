package edu.kit.ipd.sdq.vitruvius.integration.transformations;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.integration.transformations.ICreateCorrespondenceModel;
import java.util.HashSet;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Class that provides general methods for creating correspondences between two metamodels.
 * Should be extended by custom transformations.
 * 
 * @author Benjamin Hettwer
 */
@SuppressWarnings("all")
public abstract class BasicCorrespondenceModelTransformation implements ICreateCorrespondenceModel {
  private HashSet<String> existingEntries = new HashSet<String>();
  
  protected Logger logger = Logger.getRootLogger();
  
  /**
   * Returns the current used {@link CorrespondenceInstance}
   */
  public abstract CorrespondenceInstance getCorrespondenceInstance();
  
  protected Correspondence addCorrespondence(final EObject pcmObject, final EObject jamoppObject) {
    return this.addCorrespondence(pcmObject, jamoppObject, null);
  }
  
  /**
   * Creates an {@link EObjectCorrespondence} between the given EObjects
   * and adds it to the {@link CorrespondenceInstance}
   */
  protected Correspondence addCorrespondence(final EObject objectA, final EObject objectB, final Correspondence parent) {
    boolean _or = false;
    boolean _equals = Objects.equal(objectA, null);
    if (_equals) {
      _or = true;
    } else {
      boolean _equals_1 = Objects.equal(objectB, null);
      _or = _equals_1;
    }
    if (_or) {
      throw new IllegalArgumentException("Corresponding elements must not be null!");
    }
    EObject deresolvedA = this.deresolveIfNesessary(objectA);
    EObject deresolvedB = this.deresolveIfNesessary(objectB);
    CorrespondenceInstance _correspondenceInstance = this.getCorrespondenceInstance();
    TUID _calculateTUIDFromEObject = _correspondenceInstance.calculateTUIDFromEObject(deresolvedA);
    String _string = _calculateTUIDFromEObject.toString();
    CorrespondenceInstance _correspondenceInstance_1 = this.getCorrespondenceInstance();
    TUID _calculateTUIDFromEObject_1 = _correspondenceInstance_1.calculateTUIDFromEObject(deresolvedB);
    String _string_1 = _calculateTUIDFromEObject_1.toString();
    String identifier = (_string + _string_1);
    boolean _contains = this.existingEntries.contains(identifier);
    boolean _not = (!_contains);
    if (_not) {
      CorrespondenceInstance _correspondenceInstance_2 = this.getCorrespondenceInstance();
      EObjectCorrespondence correspondence = _correspondenceInstance_2.createAndAddEObjectCorrespondence(deresolvedA, deresolvedB);
      this.existingEntries.add(identifier);
      this.logger.info(((("Created Correspondence for element: " + objectA) + " and Element: ") + objectB));
      return correspondence;
    }
    return null;
  }
  
  /**
   * Converts the absolute resource URI of given EObject to platform URI
   * or does nothing if it already has one.
   */
  private EObject deresolveIfNesessary(final EObject object) {
    Resource _eResource = object.eResource();
    URI uri = _eResource.getURI();
    boolean _isPlatform = uri.isPlatform();
    boolean _not = (!_isPlatform);
    if (_not) {
      IWorkspace _workspace = ResourcesPlugin.getWorkspace();
      IWorkspaceRoot _root = _workspace.getRoot();
      IPath _location = _root.getLocation();
      String _string = _location.toString();
      String _plus = (_string + Character.valueOf(IPath.SEPARATOR));
      URI base = URI.createFileURI(_plus);
      URI relativeUri = uri.deresolve(base);
      Resource _eResource_1 = object.eResource();
      String _string_1 = relativeUri.toString();
      URI _createPlatformResourceURI = EMFBridge.createPlatformResourceURI(_string_1);
      _eResource_1.setURI(_createPlatformResourceURI);
    }
    return object;
  }
  
  /**
   * Returns the SameTypeCorrespondence for the given eObjects a and b and throws a
   * {@link RuntimeException} if there is no such correspondence. Note that a has to be an element
   * of metamodel a and b an instance of metamodel b.
   */
  protected SameTypeCorrespondence getUniqueSameTypeCorrespondence(final EObject objectA, final EObject objectB) {
    EObject deresolvedA = this.deresolveIfNesessary(objectA);
    EObject deresolvedB = this.deresolveIfNesessary(objectB);
    CorrespondenceInstance _correspondenceInstance = this.getCorrespondenceInstance();
    return _correspondenceInstance.claimUniqueSameTypeCorrespondence(deresolvedA, deresolvedB);
  }
}
