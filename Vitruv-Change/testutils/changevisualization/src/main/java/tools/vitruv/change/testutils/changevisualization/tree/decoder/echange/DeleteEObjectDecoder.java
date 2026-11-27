package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

/**
 * Decoder for deleteEObject changes.
 *
 * @author Andreas Loeffler
 */
public class DeleteEObjectDecoder extends EObjectNameDecoder {

  /** Constructor. */
  public DeleteEObjectDecoder() {
    super("DeleteEObject", "affectedEObject");
  }
}
