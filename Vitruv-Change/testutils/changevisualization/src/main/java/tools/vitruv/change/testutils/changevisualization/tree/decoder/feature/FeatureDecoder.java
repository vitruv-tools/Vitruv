package tools.vitruv.change.testutils.changevisualization.tree.decoder.feature;

import java.awt.Component;

/**
 * Interface for FeatureDecoders to implement.
 *
 * @author Andreas Loeffler
 */
public interface FeatureDecoder {

  /**
   * Returns the class with elements this decoder is suitable to decode.
   *
   * @return The class to decode
   */
  Class<?> getDecodedClass();

  /**
   * Creates a simple and short text suitable for display in a JLabel.
   *
   * @param obj The Object whose Information should be decoded
   * @return The simple Info text, must not be null
   */
  String decodeSimple(Object obj);

  /**
   * Creates a detailed String suitable for display in a JTextArea. May contain multiple lines. Only
   * one of decodeDetailed, decodeDetailedArray or decodeDetailedUI may be implemented.
   *
   * @param obf The Object whose Information should be decoded
   * @return The detailed Info text, may be null if the simple text is already sufficient
   */
  String decodeDetailed(Object obf);

  /**
   * Takes a given object and creates a String[][] that is used to create a scrollable panel of
   * label/value pairs. Therefore the returned arrays has to be of size [n][2], where [x][0]==label
   * text and [x][1]==the value to display Only one of decodeDetailed, decodeDetailedArray or
   * decodeDetailedUI may be implemented.
   *
   * @param obf The Object whose Information should be decoded
   * @return A String[][] of label/value pairs,may be null
   */
  String[][] decodeDetailedArray(Object obf);

  /**
   * Takes a given object and creates a Component that uses a individual display if the default
   * behavior of to show a detailed String is not sufficient. Only one of decodeDetailed,
   * decodeDetailedArray or decodeDetailedUI may be implemented.
   *
   * @param obf The Object whose Information should be decoded
   * @return A component displaying the detailed information,may be null
   */
  Component decodeDetailedUI(Object obf);
}
