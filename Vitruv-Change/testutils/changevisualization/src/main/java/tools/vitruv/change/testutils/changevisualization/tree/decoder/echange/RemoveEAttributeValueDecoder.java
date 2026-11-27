package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

/**
 * Decoder for removeEAttribute changes.
 *
 * @author Andreas Loeffler
 */
public class RemoveEAttributeValueDecoder extends InsertRemoveDecoder {

  /** Constructor. */
  public RemoveEAttributeValueDecoder() {
    super("RemoveEAttributeValue", "oldValue");
  }
}
