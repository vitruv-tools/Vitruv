package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

/**
 * Decoder for insertRootEObject changes.
 *
 * @author Andreas Loeffler
 */
public class InsertRootEObjectDecoder extends EObjectNameDecoder {

  /** Instantiates a new insert root e object decoder. */
  public InsertRootEObjectDecoder() {
    super("InsertRootEObject", "newValue");
  }
}
