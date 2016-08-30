package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.tuid.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

public interface ModelProviding {
    ModelInstance getAndLoadModelInstanceOriginal(VURI uri);

    void saveExistingModelInstanceOriginal(VURI vuri);

    TransactionalEditingDomain getTransactionalEditingDomain();

    void detachTransactionalEditingDomain();

    void deleteModelInstanceOriginal(VURI vuri);

    void saveModelInstanceOriginalWithEObjectAsOnlyContent(VURI vuri, EObject rootEObject, TUID oldTUID);

    void forceReloadModelInstanceOriginalIfExisting(VURI modelURI);

}
