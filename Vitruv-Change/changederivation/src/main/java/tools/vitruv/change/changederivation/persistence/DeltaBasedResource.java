package tools.vitruv.change.changederivation.persistence;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.val;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.changederivation.DefaultStateBasedChangeResolutionStrategy;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;

/**
 * This class provides functionality to save and load a resource in a model delta representation.
 * It uses the {@link DefaultStateBasedChangeResolutionStrategy} to save a state-based resource
 * as a sequence of {@link EChange}s. The saved delta sequence can be loaded again, 
 * and the resulting changes can be applied to create the state-based resource again.
**/
public class DeltaBasedResource extends ResourceImpl {

  /**
   * Creates a new DeltaBasedRessource.
   *
   * @param uri - URI to the file containing the single EChanges.
   */
  public DeltaBasedResource(URI uri) {
    super(uri);
  }

  private static List<EChange<HierarchicalId>> loadDeltas(URI modelUri) {
    val resourceSet = withGlobalFactories(new ResourceSetImpl());
    val resource = resourceSet.getResource(modelUri, true);
    return resource.getContents()
      .stream()
      .map(eObject -> (EChange<HierarchicalId>) eObject)
      .toList();
  }

  @Override
  public void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
    val deltas = loadDeltas(this.getURI());
    VitruviusChangeResolverFactory.forHierarchicalIds(resourceSet)
        .resolveAndApply(VitruviusChangeFactory.getInstance().createTransactionalChange(deltas));
  }

  @Override
  public void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
    val deltaChanges = new DefaultStateBasedChangeResolutionStrategy()
        .getChangeSequenceForCreated(this)
        .getEChanges();
    val resourceSet = withGlobalFactories(new ResourceSetImpl());
    val resource = resourceSet.createResource(this.getURI());
    resource.getContents().addAll(deltaChanges);
    try (val out = outputStream) {
      resource.save(out, Collections.emptyMap());
    }
  }
}
