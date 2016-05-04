package edu.kit.ipd.sdq.vitruvius.codeintegration;

import edu.kit.ipd.sdq.vitruvius.codeintegration.PCMJaMoPPCorrespondenceModelTransformation;

@SuppressWarnings("all")
public interface PCMJaMoPPIntegrationExtending {
  public final static String ID = "edu.kit.ipd.sdq.vitruvius.codeintegration.pcmjamoppintegrationextending.afterbasictransformation";
  
  public abstract void afterBasicTransformations(final PCMJaMoPPCorrespondenceModelTransformation transformation);
}
