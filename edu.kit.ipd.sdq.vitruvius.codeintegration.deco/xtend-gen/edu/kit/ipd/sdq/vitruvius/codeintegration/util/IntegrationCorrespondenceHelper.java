package edu.kit.ipd.sdq.vitruvius.codeintegration.util;

import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence;
import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import java.util.function.Supplier;

@SuppressWarnings("all")
public class IntegrationCorrespondenceHelper {
  private IntegrationCorrespondenceHelper() {
  }
  
  public static final CorrespondenceInstance<IntegrationCorrespondence> getEditableView(final CorrespondenceInstance<?> ci) {
    final Supplier<IntegrationCorrespondence> _function = () -> {
      return IntegrationCorrespondenceHelper.createIntegrationCorrespondence();
    };
    return ci.<IntegrationCorrespondence>getEditableView(IntegrationCorrespondence.class, _function);
  }
  
  public static final IntegrationCorrespondence createIntegrationCorrespondence() {
    final IntegrationCorrespondence integratedCorrespondence = IntegrationFactory.eINSTANCE.createIntegrationCorrespondence();
    integratedCorrespondence.setCreatedByIntegration(true);
    return integratedCorrespondence;
  }
}
