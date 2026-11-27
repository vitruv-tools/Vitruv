package tools.vitruv.change.testutils.changevisualization.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import tools.vitruv.change.testutils.changevisualization.ChangeVisualizationUI;

/**
 * Displays a String array in a scrollable UI. The string array must be of size
 * [x][2]. The values
 * at [x][0] are used as label text on the left, [x][1] are displayed in
 * textFields on the right.
 * The usual strg+mousewheel zoom behavior is implemented.
 *
 * @author Andreas Loeffler
 */
public class LabelValuePanel extends JScrollPane {

  /** Serialization id, serialization not used. */
  private static final long serialVersionUID = 1L;

  /** Implements the usual strg + mousewheel zoom behaviour. */
  private transient final MouseWheelListener mwl = e -> {
    // Implements the usual strg + mousewheel behaviour for zooming
    if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == 0) {
      return;
    }
    if (e.getWheelRotation() <= -1) {
      for (JLabel label : getAllLabels()) {
        float newSize = (float) label.getFont().getSize() + 2;
        if (newSize > 30) {
          newSize = 30;
        }
        label.setFont(label.getFont().deriveFont(newSize));
      }
      for (JTextField field : getAllFields()) {
        float newSize = (float) field.getFont().getSize() + 2;
        if (newSize > 30) {
          newSize = 30;
        }
        field.setFont(field.getFont().deriveFont(newSize));
      }
    } else if (e.getWheelRotation() >= 1) {
      for (JLabel label : getAllLabels()) {
        float newSize = (float) label.getFont().getSize() - 2;
        if (newSize < 5) {
          newSize = 5;
        }
        label.setFont(label.getFont().deriveFont(newSize));
      }
      for (JTextField field : getAllFields()) {
        float newSize = (float) field.getFont().getSize() - 2;
        if (newSize < 5) {
          newSize = 5;
        }
        field.setFont(field.getFont().deriveFont(newSize));
      }
    }
  };

  /** List of all added JTextFields used for mouse wheel zooming. */
  private List<JTextField> allFields = new ArrayList<>();

  /** List of all added JLabels used for mouse wheel zooming. */
  private List<JLabel> allLabels = new ArrayList<>();

  /**
   * Constructs an LabelValuePanel visualizing a string array.
   *
   * <p>
   * The string array must be of size [x][2]. The values at [x][0] are used as
   * label text on the
   * left, [x][1] are displayed in textFields on the right.
   *
   * @param array The array to visualize
   */
  public LabelValuePanel(String[][] array) {
    // Create the basic layout and panel structure
    JPanel pane = new JPanel(new BorderLayout());
    JPanel left = new JPanel(new GridLayout(1, 1));
    left.setBorder(new EmptyBorder(5, 5, 5, 5));
    JPanel center = new JPanel(new GridLayout(1, 1));
    center.setBorder(new EmptyBorder(5, 5, 5, 5));
    pane.add(left, BorderLayout.WEST);
    pane.add(center, BorderLayout.CENTER);

    for (int n = 0; n < array.length; n++) {
      createLine(center, left, array[n][0], array[n][1]);
    }

    // Put it into this scrollPane
    setViewportView(pane);

    this.addMouseWheelListener(mwl);
  }

  /**
   * Returns all text fields (right components).
   *
   * @return The text fields
   */
  private List<JTextField> getAllFields() {
    return allFields;
  }

  /**
   * Return all labels (left components).
   *
   * @return The labels
   */
  private List<JLabel> getAllLabels() {
    return allLabels;
  }

  /**
   * Create a line in the center and left panel with the given texts.
   *
   * @param center    The center panel
   * @param left      The left panel
   * @param labelText The text for the label
   * @param fieldText The text for the field
   */
  private void createLine(JPanel center, JPanel left, String labelText, String fieldText) {
    // Create the label
    JLabel label = new JLabel(labelText, SwingConstants.RIGHT);
    label.setFont(ChangeVisualizationUI.DEFAULT_LABEL_FONT);
    allLabels.add(label);

    // Create the field
    JTextField field = new JTextField(fieldText);
    field.setEditable(false);
    field.setFont(ChangeVisualizationUI.DEFAULT_TEXTFIELD_FONT);
    allFields.add(field);

    // Add label to left and increase the layout dimension
    ((GridLayout) left.getLayout()).setRows(((GridLayout) left.getLayout()).getRows() + 1);
    left.add(label);

    // Add field to center and increase the layout dimension
    ((GridLayout) center.getLayout()).setRows(((GridLayout) center.getLayout()).getRows() + 1);
    center.add(field);
  }
}
