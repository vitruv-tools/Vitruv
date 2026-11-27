package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

/**
 * Decoder for insertEAttribute value changes.
 *
 * @author Andreas Loeffler
 */
public class InsertEAttributeValueDecoder extends InsertRemoveDecoder {
  /** Instantiates a new InsertEAttributeValueDecoder. */
  public InsertEAttributeValueDecoder() {
    super("InsertEAttributeValue", "newValue");
  }
}
