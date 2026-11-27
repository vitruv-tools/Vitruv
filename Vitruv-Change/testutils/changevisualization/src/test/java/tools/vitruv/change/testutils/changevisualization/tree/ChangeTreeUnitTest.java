package tools.vitruv.change.testutils.changevisualization.tree;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChangeTreeUnitTest {

  private ChangeTree changeTree;
  private TabHighlighting dummyHighlighting;

  @BeforeEach
  void setUp() throws Exception {
    SwingUtilities.invokeAndWait(
        () -> {
          dummyHighlighting =
              new TabHighlighting() {
                @Override
                public void setHighlightID(String highlightID) {
                  // Intentionally left blank for testing.
                  // This test does not require real highlighting behavior.
                }

                @Override
                public String getHighlightID() {
                  // Intentionally returns a dummy value for testing.
                  // This test does not depend on actual highlight ID logic.
                  return "";
                }
              };
          changeTree = new ChangeTree(dummyHighlighting);
        });
  }

  @Test
  void constructor_initializesUILayout() throws Exception {
    SwingUtilities.invokeAndWait(
        () -> {
          assertThat(changeTree.getLayout()).isInstanceOf(BorderLayout.class);

          boolean hasSplitPane = false;
          for (int i = 0; i < changeTree.getComponentCount(); i++) {
            if (changeTree.getComponent(i) instanceof JSplitPane) {
              hasSplitPane = true;
              break;
            }
          }
          assertThat(hasSplitPane).isTrue();
        });
  }

  @Test
  void treeUI_andDetailsComponents_areNotNull() throws Exception {
    SwingUtilities.invokeAndWait(
        () -> {
          assertThat(changeTree.getTreeUI()).isNotNull();
          assertThat(changeTree.getDetailsUI()).isNotNull();
          assertThat(changeTree.getTreeScroller()).isNotNull();
          assertThat(changeTree.getDetailsSplitpane()).isNotNull();
        });
  }

  @Test
  void zoomInWithCtrlIncreasesFontSize() throws Exception {
    SwingUtilities.invokeAndWait(
        () -> {
          var tree = changeTree.getTreeUI();
          float originalSize = tree.getFont().getSize2D();

          simulateCtrlMouseWheel(-1);

          float newSize = tree.getFont().getSize2D();
          assertThat(newSize).isGreaterThan(originalSize).isLessThanOrEqualTo(30.0f);
        });
  }

  @Test
  void zoomOutWithCtrlDecreasesFontSize() throws Exception {
    SwingUtilities.invokeAndWait(
        () -> {
          var tree = changeTree.getTreeUI();
          tree.setFont(tree.getFont().deriveFont(20.0f));
          float originalSize = tree.getFont().getSize2D();

          simulateCtrlMouseWheel(1);

          float newSize = tree.getFont().getSize2D();
          assertThat(newSize).isLessThan(originalSize).isGreaterThanOrEqualTo(5.0f);
        });
  }

  @Test
  void zoomDoesNotExceedMaxLimit() throws Exception {
    SwingUtilities.invokeAndWait(
        () -> {
          var tree = changeTree.getTreeUI();
          tree.setFont(tree.getFont().deriveFont(29.0f));

          simulateCtrlMouseWheel(-1);
          float newSize = tree.getFont().getSize2D();
          assertThat(newSize).isEqualTo(30.0f);
        });
  }

  @Test
  void zoomDoesNotGoBelowMinLimit() throws Exception {
    SwingUtilities.invokeAndWait(
        () -> {
          var tree = changeTree.getTreeUI();
          tree.setFont(tree.getFont().deriveFont(6.0f));

          simulateCtrlMouseWheel(1);
          float newSize = tree.getFont().getSize2D();
          assertThat(newSize).isEqualTo(5.0f);
        });
  }

  private void simulateCtrlMouseWheel(int rotation) {
    var event =
        new MouseWheelEvent(
            changeTree.getTreeScroller(),
            MouseWheelEvent.MOUSE_WHEEL,
            System.currentTimeMillis(),
            InputEvent.CTRL_DOWN_MASK,
            100,
            100,
            0,
            false,
            MouseWheelEvent.WHEEL_UNIT_SCROLL,
            1,
            rotation);

    for (var listener : changeTree.getTreeScroller().getMouseWheelListeners()) {
      listener.mouseWheelMoved(event);
    }
  }
}
