package tools.vitruv.change.propagation;

import java.util.List;
import tools.vitruv.change.composite.MetamodelDescriptor;

/**
 * {@link ChangePropagationSpecificationProvider} provide {@link ChangePropagationSpecification}
 * that are applicable for some source metamodel, or one specified metamodel.
 */
public interface ChangePropagationSpecificationProvider
    extends Iterable<ChangePropagationSpecification> {
  /**
   * Returns change propagation specifications that react to changes in instances of the metamodel
   * of the given descriptor. This covers specifications for the exact same metamodel
   * and for those metamodels in which the given one is contained.
   *
   * @param sourceMetamodelDescriptor - A descriptor for the metamodel 
   *      to find change propagation specifications for.
   * @return {@link ChangePropagationSpecification}s reacting to changes 
   *      in instances of the metamodel of the <code>sourceMetamodelDescriptor</code>.
   * @see MetamodelDescriptor#contains
   */
  List<ChangePropagationSpecification> getChangePropagationSpecifications(
      MetamodelDescriptor sourceMetamodelDescriptor);
}
