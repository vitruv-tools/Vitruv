package edu.kit.ipd.sdq.vitruvius.integration.transformations;

@SuppressWarnings("all")
public interface ICreateCorrespondenceModel {
  /**
   * Creates correspondences between model elements from two different metamodels and adds them to the vsum.
   * They are necessary to propagate changes.
   */
  public abstract void createCorrespondences();
}
