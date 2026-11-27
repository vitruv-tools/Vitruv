package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

/**
 * Decoder for create eObject changes.
 *
 * @author Andreas Loeffler
 */
public class CreateEObjectDecoder extends EObjectNameDecoder {

  /** Constructor. */
  public CreateEObjectDecoder() {
    super("CreateEObject", "affectedEObject");
  }
}
