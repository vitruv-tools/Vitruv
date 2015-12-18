package edu.kit.ipd.sdq.vitruvius.framework.changepreparer;

import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.ConcreteChangePreparer;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class EMFModelPreparer extends ConcreteChangePreparer {
  private final ModelProviding modelProviding;
  
  private final CorrespondenceProviding correspondenceProviding;
  
  public EMFModelPreparer(final ModelProviding modelProviding, final CorrespondenceProviding correspondenceProviding) {
    super();
    this.modelProviding = modelProviding;
    this.correspondenceProviding = correspondenceProviding;
  }
  
  @Override
  public Change prepareChange(final Change change) {
    if ((change instanceof EMFModelChange)) {
      final EMFModelChange emc = ((EMFModelChange) change);
      VURI _uRI = emc.getURI();
      final ModelInstance modelInstance = this.modelProviding.getAndLoadModelInstanceOriginal(_uRI);
      final EChange eChange = emc.getEChange();
      if ((eChange instanceof EFeatureChange<?>)) {
        final EFeatureChange<?> featureChange = ((EFeatureChange<?>) eChange);
        final EObject oldEObject = featureChange.getOldAffectedEObject();
        final EObject newEObject = featureChange.getNewAffectedEObject();
        VURI _metamodeURI = modelInstance.getMetamodeURI();
        final Set<InternalCorrespondenceInstance> correspondenceInstances = this.correspondenceProviding.getOrCreateAllCorrespondenceInstances(_metamodeURI);
        for (final InternalCorrespondenceInstance correspondenceInstance : correspondenceInstances) {
          correspondenceInstance.updateTUID(oldEObject, newEObject);
        }
      }
      return emc;
    } else {
      throw new IllegalArgumentException((("Cannot prepare the change \'" + change) + "\' as EMFModelChange"));
    }
  }
}
