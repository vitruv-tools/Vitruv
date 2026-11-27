package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

/**
 * Decoder for removeRootEObject changes.
 *
 * @author Andreas Loeffler
 */
public class RemoveRootEObjectDecoder extends EObjectNameDecoder {

  /** Instantiates a new remove root e object decoder. */
  public RemoveRootEObjectDecoder() {
    super("RemoveRootEObject", "oldValue");
  }
}
