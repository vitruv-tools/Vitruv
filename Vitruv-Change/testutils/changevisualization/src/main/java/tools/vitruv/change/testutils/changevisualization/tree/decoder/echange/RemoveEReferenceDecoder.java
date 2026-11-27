package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

/**
 * Decoder for removeEReference changes.
 *
 * @author Andreas Loeffler
 */
public class RemoveEReferenceDecoder extends InsertRemoveDecoder {

  /** Constructor. */
  public RemoveEReferenceDecoder() {
    super("RemoveEReference", "oldValue");
  }
}
