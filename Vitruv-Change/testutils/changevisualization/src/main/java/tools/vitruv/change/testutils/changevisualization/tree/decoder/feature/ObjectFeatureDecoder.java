package tools.vitruv.change.testutils.changevisualization.tree.decoder.feature;

import java.awt.Component;

/**
 * Implements a feature decoder suitable to process all java object. It is used as a fallback if no
 * special decoder for a feature exists. It creates no detail-information of any kind and uses
 * String.valueOf() to create the simple text.
 *
 * @author Andreas Loeffler
 */
public class ObjectFeatureDecoder implements FeatureDecoder {

  @Override
  public Class<?> getDecodedClass() {
    return Object.class;
  }

  @Override
  public String decodeSimple(Object obj) {
    // String.valueOf assures no null value errors
    return String.valueOf(obj);
  }

  @Override
  public String decodeDetailed(Object obf) {
    return null;
  }

  @Override
  public Component decodeDetailedUI(Object obf) {
    return null;
  }

  @Override
  public String[][] decodeDetailedArray(Object obf) {
    return new String[0][0];
  }
}
