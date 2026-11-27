package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

import tools.vitruv.change.atomic.EChange;

/**
 * Interface to implement for ChangeDecoders that want to realize a eChange individual text to
 * display in the tree.
 *
 * @author Andreas Loeffler
 */
public interface ChangeDecoder {

  /**
   * Creates a simple and short text suitable for display in a JLabel.
   *
   * @param eChange The eChange whose Information should be decoded
   * @return The simple Info text, can be null (=ignore)
   */
  String decode(EChange eChange);

  /**
   * Returns the name of the eClass decoded by this decoder.
   *
   * @return The name of the eCLass that can be decoded
   */
  String getDecodedEClassName();
}
