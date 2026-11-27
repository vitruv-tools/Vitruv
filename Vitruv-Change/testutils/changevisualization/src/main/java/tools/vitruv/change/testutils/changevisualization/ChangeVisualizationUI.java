package tools.vitruv.change.testutils.changevisualization;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import tools.vitruv.change.testutils.changevisualization.common.ModelRepositoryChanges;
import tools.vitruv.change.testutils.changevisualization.persistence.ChangeDataSetPersistenceHelper;
import tools.vitruv.change.testutils.changevisualization.ui.ChangesTab;
import tools.vitruv.change.testutils.changevisualization.ui.CloseableTabComponent;

/** The frame in which the change visualization is displayed. Also holds default fonts. */
public class ChangeVisualizationUI extends JFrame implements MonitoredRepositoryAddedListener {
  private static final long serialVersionUID = 8376935677939982608L;

  /** Constant for the default fallback font key. */
  private static final String DEFAULT_FONT_KEY = "Label.font";

  /**
   * Creates a font derived from the default font of the java's look and feel for a given fontKey.
   * If the fontKey is unknown, the font is derived from Label.font as fallback. For different
   * possible fontKeys search the web for "java default font uimanager". Valid fontSizes and styles
   * are not checked here and may therefore throw Exceptions if invalid values are given.
   *
   * @param fontKey The key identifying the font
   * @param size The font size
   * @param style The style (Font.bold and so on)
   * @return The derived font
   */
  private static Font createFont(String fontKey, float size, int style) {
    if (fontKey == null) {
      // Fallback is Label.font
      fontKey = DEFAULT_FONT_KEY;
    }
    Font defaultFont = (Font) UIManager.get(fontKey);
    if (defaultFont == null) {
      // Fallback is Label.font
      defaultFont = (Font) UIManager.get(DEFAULT_FONT_KEY);
      System.err.println(fontKey);
    }
    return defaultFont.deriveFont(size).deriveFont(style);
  }

  /** Default font used for titled borders. */
  public static final Font DEFAULT_TITLED_BORDER_FONT =
      createFont("TitledBorder.font", 14, Font.BOLD);

  /** Default font used for trees (=tree nodes). */
  public static final Font DEFAULT_TREE_FONT = createFont("Tree.font", 16, Font.PLAIN);

  /** Default font used for tables. */
  public static final Font DEFAULT_TABLE_FONT = createFont("Table.font", 16, Font.PLAIN);

  /** Default font used for table headers. */
  public static final Font DEFAULT_TABLE_HEADER_FONT =
      createFont("TableHeader.font", 14, Font.BOLD);

  /** Default font used for button. */
  public static final Font DEFAULT_BUTTON_FONT = createFont("Button.font", 16, Font.PLAIN);

  /** Default font used for labels. */
  public static final Font DEFAULT_LABEL_FONT = createFont(DEFAULT_FONT_KEY, 16, Font.PLAIN);

  /** Default font used for textFields. */
  public static final Font DEFAULT_TEXTFIELD_FONT = createFont("TextField.font", 16, Font.PLAIN);

  /** Default font used for textAreas. */
  public static final Font DEFAULT_TEXTAREA_FONT = createFont("TextArea.font", 16, Font.PLAIN);

  /** Default font used for checkBoxes . */
  public static final Font DEFAULT_CHECKBOX_FONT = createFont("CheckBox.font", 16, Font.PLAIN);

  /** Default font used for tabbed pane (titles) . */
  public static final Font DEFAULT_TABBED_PANE_FONT = createFont("TabbedPane.font", 14, Font.BOLD);

  /** Default font used for menusd. */
  public static final Font DEFAULT_MENU_FONT = createFont("Menu.font", 16, Font.PLAIN);

  /** Default font used for menuItems . */
  public static final Font DEFAULT_MENUITEM_FONT = createFont("MenuItem.font", 16, Font.PLAIN);

  // Define a constant in your class to make it more descriptive
  private static final int CLOSE_OPERATION = WindowConstants.DISPOSE_ON_CLOSE;

  private final transient ChangeVisualizationDataModel changeVisualizationDataModel;
  private JTabbedPane tabbedPane;

  /**
   * Creates a new change visualization UI.
   *
   * @param changeVisualizationDataModel The data model for the change visualization.
   */
  public ChangeVisualizationUI(ChangeVisualizationDataModel changeVisualizationDataModel) {
    super("Vitruv Change Visualization");
    this.changeVisualizationDataModel = changeVisualizationDataModel;
    initializeWindow();
    initializeContents();
    initializeFileMenu();
    setupMonitoringForMonitoredRepositoryChanges(changeVisualizationDataModel);
  }

  private void initializeContents() {
    tabbedPane = new JTabbedPane();
    tabbedPane.setFont(ChangeVisualizationUI.DEFAULT_TABBED_PANE_FONT); // used just for the title
    setContentPane(tabbedPane);
  }

  private void initializeWindow() {
    setDefaultCloseOperation(CLOSE_OPERATION);
    int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    setSize(Math.min(screenWidth - 30, 1890), Math.min(screenHeight - 60, 1020));
    setLocationRelativeTo(null);
  }

  private void setupMonitoringForMonitoredRepositoryChanges(
      ChangeVisualizationDataModel changeVisualization) {
    changeVisualization.registerMonitoredRepositoryAddedListener(this);
    tabbedPane.addContainerListener(
        new ContainerListener() {
          @Override
          public void componentAdded(ContainerEvent e) {}

          @Override
          public void componentRemoved(ContainerEvent e) {
            if (e.getChild() instanceof ChangesTab) {
              ChangesTab tab = (ChangesTab) e.getChild();
              changeVisualization.removeMonitoredRepository(tab.getTitle());
            }
          }
        });
    tabbedPane.addChangeListener(
        event -> {
          for (Component component : tabbedPane.getComponents()) {
            if (component instanceof ChangesTab) {
              ((ChangesTab) component).setEnabled(tabbedPane.getSelectedComponent() == component);
            }
          }
        });
  }

  @Override
  public void setVisible(boolean isVisible) {
    super.setVisible(isVisible);
    if (isVisible) {
      setAlwaysOnTop(true);
      setAlwaysOnTop(false);
    }
  }

  /** Listener for the usual zoom in/out on text elements. */
  private final MouseWheelListener mwl =
      (MouseWheelEvent e) -> {
        if (!(e.getSource() instanceof JTextArea)) {
          return;
        }
        if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == 0) {
          return;
        }
        // Strg is pressed
        JTextArea area = (JTextArea) e.getSource();
        if (e.getWheelRotation() <= -1) {
          float newSize = (float) area.getFont().getSize() + 2;
          if (newSize > 30) {
            newSize = 30;
          }
          area.setFont(area.getFont().deriveFont(newSize));
        } else if (e.getWheelRotation() >= 1) {
          float newSize = (float) area.getFont().getSize() - 2;
          if (newSize < 5) {
            newSize = 5;
          }
          area.setFont(area.getFont().deriveFont(newSize));
        }
      };

  private void initializeFileMenu() {
    JMenuItem saveItem = new JMenuItem("Save");
    saveItem.addActionListener(e -> selectAndSaveFile());

    JMenuItem loadItem = new JMenuItem("Load");
    loadItem.addActionListener(e -> selectAndLoadFile());

    saveItem.setFont(DEFAULT_MENUITEM_FONT);
    loadItem.setFont(DEFAULT_MENUITEM_FONT);

    JMenu fileMenu = new JMenu("File");
    fileMenu.setFont(DEFAULT_MENU_FONT);

    fileMenu.add(saveItem);
    fileMenu.addSeparator();
    fileMenu.add(loadItem);
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(fileMenu);
    this.setJMenuBar(menuBar);
  }

  private void selectAndLoadFile() {
    // Separate thread to not block UI thread
    new Thread(
            () -> {
              JFileChooser fileChooser = createChangesFileChooser();
              fileChooser.showOpenDialog(ChangeVisualizationUI.this);
              final File selectedFile = fileChooser.getSelectedFile();
              if (selectedFile != null) {
                load(selectedFile);
              }
            })
        .start();
  }

  private void load(final File file) {
    SwingUtilities.invokeLater(
        () -> {
          try {
            changeVisualizationDataModel.loadFromFile(file);
          } catch (IOException e) {
            JOptionPane.showMessageDialog(
                ChangeVisualizationUI.this,
                "Error loading changes : " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });
  }

  private void selectAndSaveFile() {
    // Separate thread to not block UI thread
    new Thread(
            () -> {
              JFileChooser fileChooser = createChangesFileChooser();
              fileChooser.showSaveDialog(ChangeVisualizationUI.this);
              final File selectedFile = fileChooser.getSelectedFile();
              if (selectedFile != null) {
                save(selectedFile);
              }
            })
        .start();
  }

  private JFileChooser createChangesFileChooser() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(
        new FileFilter() {
          @Override
          public boolean accept(File f) {
            return f.isDirectory()
                || (f.isFile()
                    && f.getName()
                        .toLowerCase()
                        .endsWith(ChangeDataSetPersistenceHelper.FILE_ENDING));
          }

          @Override
          public String getDescription() {
            return ChangeDataSetPersistenceHelper.FILE_DESCRIPTION;
          }
        });
    return chooser;
  }

  private void save(final File file) {
    SwingUtilities.invokeLater(
        () -> {
          try {
            changeVisualizationDataModel.saveToFile(file);
          } catch (IOException e) {
            JOptionPane.showMessageDialog(
                ChangeVisualizationUI.this,
                "Error saving changes : " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });
  }

  /**
   * Adds a new tab to this frame.
   *
   * @param title The tab title
   * @param component The tab component
   */
  private void addTab(final String title, Component component) {
    this.tabbedPane.addTab(title, component);

    final CloseableTabComponent closeableTabComponent = new CloseableTabComponent(title);
    closeableTabComponent.setFont(ChangeVisualizationUI.DEFAULT_TABBED_PANE_FONT);

    // Add a listener to the tabComponent to react on its close button
    closeableTabComponent.addActionListener(
        e -> {
          int choice =
              JOptionPane.showConfirmDialog(
                  ChangeVisualizationUI.this,
                  "Close tab " + title,
                  "Close tab",
                  JOptionPane.YES_NO_CANCEL_OPTION);
          if (choice != JOptionPane.YES_OPTION) {
            return;
          }
          for (int index = 0; index < tabbedPane.getTabCount(); index++) {
            if (tabbedPane.getTabComponentAt(index) == closeableTabComponent) {
              tabbedPane.removeTabAt(index);
              return;
            }
          }
        });
    this.tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, closeableTabComponent);
  }

  /**
   * Show a file together with the change-tabs.
   *
   * @param file The file to load and show
   */
  public void addFile(File file) {
    try (InputStream in = new FileInputStream(file)) {
      byte[] bytes = new byte[(int) file.length()];
      int read = 0;
      while (read < bytes.length) {
        read += in.read(bytes, read, bytes.length - read);
      }
      JTextArea area = new JTextArea();
      area.setFont(ChangeVisualizationUI.DEFAULT_TEXTAREA_FONT);
      area.setText(new String(bytes));
      area.addMouseWheelListener(mwl);
      JScrollPane scroller = new JScrollPane(area);
      addTab(file.getName(), scroller);
    } catch (Exception ex) {
      JTextArea area = new JTextArea();
      area.setFont(ChangeVisualizationUI.DEFAULT_TEXTAREA_FONT);
      area.setText("Could not file :" + file);
      area.addMouseWheelListener(mwl);
      JScrollPane scroller = new JScrollPane(area);
      addTab("File error", scroller);
    }
  }

  @Override
  public void addedMonitoredRepository(ModelRepositoryChanges newModelRepositoryChanges) {
    String repositoryName = newModelRepositoryChanges.getRepositoryName();
    addTab(repositoryName, new ChangesTab(newModelRepositoryChanges, false));
  }

  @Override
  public void addedMonitoredRepositoryFromFile(ModelRepositoryChanges newModelRepositoryChanges) {
    String repositoryName = newModelRepositoryChanges.getRepositoryName();
    addTab(repositoryName, new ChangesTab(newModelRepositoryChanges, true));
  }
}
