package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

/**
 * Decoder for insertEReference changes.
 *
 * @author Andreas Loeffler
 */
public class InsertEReferenceDecoder extends InsertRemoveDecoder {
  /** Instantiates a new InsertEReferenceDecoder. */
  public InsertEReferenceDecoder() {
    super("InsertEReference", "newValue");
  }
}
