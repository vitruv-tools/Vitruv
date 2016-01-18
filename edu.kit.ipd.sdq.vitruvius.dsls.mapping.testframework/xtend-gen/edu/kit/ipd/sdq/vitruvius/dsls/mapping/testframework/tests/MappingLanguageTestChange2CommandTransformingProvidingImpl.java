package edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests;

import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import java.util.List;

@SuppressWarnings("all")
public class MappingLanguageTestChange2CommandTransformingProvidingImpl extends Change2CommandTransformingProvidingImpl {
  public MappingLanguageTestChange2CommandTransformingProvidingImpl() {
    ClaimableHashMap<Pair<VURI, VURI>, Change2CommandTransforming> _claimableHashMap = new ClaimableHashMap<Pair<VURI, VURI>, Change2CommandTransforming>();
    this.transformationExecuterMap = _claimableHashMap;
  }
  
  public void addChange2CommandTransforming(final Change2CommandTransforming transformationExecuting) {
    final List<Pair<VURI, VURI>> transformableMetamodels = transformationExecuting.getTransformableMetamodels();
    for (final Pair<VURI, VURI> transformableMetamodel : transformableMetamodels) {
      this.transformationExecuterMap.put(transformableMetamodel, transformationExecuting);
    }
  }
}
