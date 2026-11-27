package tools.vitruv.change.testutils.changevisualization.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * A component usable by {JTabbedPane} to display a closable tab selector.
 *
 * @author andreas Loeffler
 */
public class CloseableTabComponent extends JPanel {

  /** Never used but necessary to show no warnings. */
  private static final long serialVersionUID = 1L;

  /**
   * The size of the close button. This also affected the size of the whole
   * component.
   */
  private static final int SIZE = 20;

  /** The label displaying the tabs title. */
  private final JLabel label = new JLabel();

  /** the button implementing close. */
  private final JButton closeButton;

  /**
   * Creates a tab component with a given title and a close button.
   *
   * <p>
   * Use ActionListener methods to react to close clicks.
   *
   * @param title The title to display
   */
  public CloseableTabComponent(String title) {
    super(new FlowLayout());
    label.setText(" " + title + " ");
    closeButton = new TabButton();
    setBorder(new EmptyBorder(2, 2, 2, 2));
    setOpaque(false);
    add(label);
    add(closeButton);
  }

  @Override
  public void setFont(Font font) {
    super.setFont(font);
    label.setFont(font);
    
  }

  /**
   * Adds an <code>ActionListener</code> to the close button.
   *
   * @param actionListener the <code>ActionListener</code> to be added
   */
  public void addActionListener(ActionListener actionListener) {
    closeButton.addActionListener(actionListener);
  }

  /**
   * Removes an <code>ActionListener</code> from the close button.
   *
   * @param actionListener the listener to be removed
   */
  public void removeActionListener(ActionListener actionListener) {
    closeButton.removeActionListener(actionListener);
  }

  /**
   * This button draws a red/black X as used as the close button.
   *
   * @author Andreas Loeffler
   */
  private class TabButton extends JButton {

    /** Serialization id, serialization not used. */
    private static final long serialVersionUID = 1L;

    /** Creates a new TabButton instance. */
    public TabButton() {
      setPreferredSize(new Dimension(SIZE, SIZE));
      // Make the button looks the same for all Laf's
      setUI(new BasicButtonUI());
      // Make it transparent
      setContentAreaFilled(false);
      // No need to be focusable
      setFocusable(false);
      setBorder(BorderFactory.createEtchedBorder());
      setBorderPainted(false);
      // Making nice rollover effect
      setRolloverEnabled(true);
    }

    // we don't want to update UI for this button
    @Override
    public void updateUI() {
      // Intentionally left empty to prevent Look and Feel (LaF) updates.
      // This button uses a fixed BasicButtonUI and custom painting for consistent
      // appearance.
    }

    // paint the cross
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g.create();
      // shift the image for pressed buttons
      if (getModel().isPressed()) {
        g2.translate(1, 1);
      }
      g2.setStroke(new BasicStroke(2));
      g2.setColor(Color.BLACK);
      if (getModel().isRollover()) {
        g2.setColor(Color.RED);
      }
      int delta = 2;
      g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
      g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
      g2.dispose();
    }
  }
}
