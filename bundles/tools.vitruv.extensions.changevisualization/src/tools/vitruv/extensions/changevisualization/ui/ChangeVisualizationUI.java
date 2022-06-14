package tools.vitruv.extensions.changevisualization.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

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
import javax.swing.filechooser.FileFilter;

import tools.vitruv.extensions.changevisualization.persistence.ChangeDataSetPersistenceHelper;

/**
 * The frame in which the change visualization is displayed.
 * Also holds default fonts.
 * 
 * @author Andreas Loeffler
 *
 */
public class ChangeVisualizationUI extends JFrame {

	/**
	 * Needed for eclipse to stop warning about serialVersionIds. This feature will never been used. 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a font derived from the default font of the java's look and feel for a given fontKey.
	 * 
	 * If the fontKey is unknown, the font is derived from Label.font as fallback. For different possible
	 * fontKeys search the web for "java default font uimanager". Valid fontSizes and styles are not checked here
	 * and may therefore throw Exceptions if invalid values are given.
	 * 
	 * @param fontKey The key identifying the font 
	 * @param size The font size
	 * @param style The style (Font.bold and so on)
	 * @return The derived font
	 */
	private static Font createFont(String fontKey,float size, int style) {
		if(fontKey==null) {
			//Fallback is Label.font
			fontKey="Label.font";
		}
		Font defaultFont=(Font)UIManager.get(fontKey);
		if(defaultFont==null) {
			//Fallback is Label.font
			defaultFont=(Font)UIManager.get("Label.font");
			System.err.println(fontKey);
		}
		return defaultFont.deriveFont(size).deriveFont(style);
	};

	/**
	 * Default font used for titled borders
	 */
	public static final Font DEFAULT_TITLED_BORDER_FONT=createFont("TitledBorder.font",14,Font.BOLD);

	/**
	 * Default font used for trees (=tree nodes)
	 */
	public static final Font DEFAULT_TREE_FONT=createFont("Tree.font",16,Font.PLAIN);

	/**
	 * Default font used for tables
	 */
	public static final Font DEFAULT_TABLE_FONT=createFont("Table.font",16,Font.PLAIN);

	/**
	 * Default font used for table headers
	 */
	public static final Font DEFAULT_TABLE_HEADER_FONT=createFont("TableHeader.font",14,Font.BOLD);

	/**
	 * Default font used for button
	 */
	public static final Font DEFAULT_BUTTON_FONT=createFont("Button.font",16,Font.PLAIN);

	/**
	 * Default font used for labels
	 */
	public static final Font DEFAULT_LABEL_FONT=createFont("Label.font",16,Font.PLAIN);

	/**
	 * Default font used for textFields
	 */
	public static final Font DEFAULT_TEXTFIELD_FONT=createFont("TextField.font",16,Font.PLAIN);

	/**
	 * Default font used for textAreas
	 */
	public static final Font DEFAULT_TEXTAREA_FONT=createFont("TextArea.font",16,Font.PLAIN);

	/**
	 * Default font used for checkBoxes
	 */
	public static final Font DEFAULT_CHECKBOX_FONT=createFont("CheckBox.font",16,Font.PLAIN);

	/**
	 * Default font used for tabbed pane (titles)
	 */
	public static final Font DEFAULT_TABBED_PANE_FONT=createFont("TabbedPane.font",14,Font.BOLD);

	/**
	 * Default font used for menusd
	 */
	public static final Font DEFAULT_MENU_FONT=createFont("Menu.font",16,Font.PLAIN);

	/**
	 * Default font used for menuItems
	 */
	public static final Font DEFAULT_MENUITEM_FONT=createFont("MenuItem.font",16,Font.PLAIN);

	/**
	 * The string is used as a prefix to loaded ChangesTab so the user can easily identify them
	 */
	private static final String LOAD_MARKER="*";

	/**
	 * The single instance of the frame
	 */
	private static ChangeVisualizationUI instance=new ChangeVisualizationUI();

	/**
	 * Gets the single visualizationUI instance
	 * @return The visualizationUI
	 */
	public static ChangeVisualizationUI getInstance(){
		return instance;
	}

	/**
	 * Main method to start offline instance of the ChangeVisualization
	 * @param args Command line arguments, are ignored
	 */
	public static void main(String[] args) {
		getInstance().setVisible(true);
	}

	//Instance methods and variables

	/**
	 * The tabbed pane holding the ChangesTabs
	 */
	private final JTabbedPane tabbedPane;

	/**
	 * Listener for the usual zoom in/out on text elements
	 */
	private final MouseWheelListener mwl=new MouseWheelListener() {
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {			
			if(!(e.getSource() instanceof JTextArea)) {
				return;
			}

			if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == 0) return;

			//Strg is pressed
			JTextArea area=(JTextArea) e.getSource();
			if(e.getWheelRotation()<=-1) {
				float newSize=area.getFont().getSize()+2;
				if(newSize>30) newSize=30;
				area.setFont(area.getFont().deriveFont(newSize));
			}else if(e.getWheelRotation()>=1) {
				float newSize=area.getFont().getSize()-2;
				if(newSize<5) newSize=5;
				area.setFont(area.getFont().deriveFont(newSize));
			}
		}
	};	

	/**
	 * Constructs a ChangeVisualizationUI and initializes its basic layout and behavior
	 */
	private ChangeVisualizationUI() {
		super("Vitruv Change Visualization");

		// Just dispose on close, do not close the whole VM
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//Set reasonable size
		int screenWidth=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setSize(Math.min(screenWidth-30,1890),Math.min(screenHeight-60,1020));
		setLocationRelativeTo(null);

		//set the content pane
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(ChangeVisualizationUI.DEFAULT_TABBED_PANE_FONT);//used just for the title
		setContentPane(tabbedPane);

		//Create file-menu
		createMenu();

		//Needed for ChangeVisualization.waitForFrameClosing() to work
		addWindowListener(new WindowAdapter() {
			/**
			 * Informs the ChangeVisualization of the frame closing event
			 */
			public void windowClosing(WindowEvent e) {
				synchronized(ChangeVisualizationUI.this) {
					ChangeVisualizationUI.this.notifyAll();
				}
			}
		});
	}	

	/**
	 * Adds the specified container listener to receive container events
	 * from the tabbedPane holding the visualized model tabs.
	 * If l is null, no exception is thrown and no action is performed.
	 * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
	 * >AWT Threading Issues</a> for details on AWT's threading model.
	 *
	 * @param    l the container listener
	 *
	 * @see #removeContainerListener
	 * @see #getContainerListeners
	 */
	public void addContainerListener(ContainerListener containerListener) {
		tabbedPane.addContainerListener(containerListener);
	}

	/**
	 * Creates the file-menu
	 */
	private void createMenu() {

		JMenuItem saveItem=new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				saveChanges();
			}			
		});

		JMenuItem loadItem=new JMenuItem("Load");
		loadItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				loadChanges();
			}			
		});

		saveItem.setFont(DEFAULT_MENUITEM_FONT);
		loadItem.setFont(DEFAULT_MENUITEM_FONT);

		JMenu fileMenu=new JMenu("File");
		fileMenu.setFont(DEFAULT_MENU_FONT);

		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(loadItem);
		JMenuBar menuBar=new JMenuBar();
		menuBar.add(fileMenu);		
		this.setJMenuBar(menuBar);
	}

	/**
	 * Let's the user select the file to load from and load it
	 */
	private void loadChanges() {
		//We use a seperate Thread for that to not block the ui thread
		Thread loader=new Thread() {
			public void run() {
				//Let the user select the file
				JFileChooser chooser=new JFileChooser();
				chooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						return f.isDirectory()||(f.isFile()&&f.getName().toLowerCase().endsWith(ChangeDataSetPersistenceHelper.FILE_ENDING));
					}
					@Override
					public String getDescription() {
						return ChangeDataSetPersistenceHelper.FILE_DESCRIPTION;
					}			
				});
				chooser.showOpenDialog(ChangeVisualizationUI.this);
				final File file=chooser.getSelectedFile();
				
				//Now load it
				load(file);
			}
		};
		loader.start();
	}

	/**
	 * Loads {@link ChangesTabs} from the given file. The user can select which of them to load.
	 * 
	 * @param file The file to load
	 */
	private void load(final File file) {
		//Do not load a null file
		if(file==null) return;

		try {
			//Get all ChangesTabs from the file through the persistence helper
			List<ChangesTab> changesTabs=ChangeDataSetPersistenceHelper.load(file);

			//If the file was valid but contained nothing, map to an error
			if(changesTabs.isEmpty()) {
				throw new Exception("The selected file did not contain any saved data.");
			}

			//Let the user decide which changes to load
			final List<ChangesTab> loadedTabs=selectTabsToLoad(changesTabs);

			if(loadedTabs.isEmpty()) {
				//The user actively selected not to load anything. We assume he/she knows that and just return
				return;
			}else {						
				//Updating the tabbedPane has to be done back in awt Thread
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						for(ChangesTab changesTab:loadedTabs) {		
							addTab(LOAD_MARKER+changesTab.getTitle(), changesTab);						
						}
						//Feedback of success currently disabled JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Changes successfully loaded");
					}							
				});						
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Error loading changes : "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Prompts the user to select a list of {@link ChangesTab}s from a dialog
	 * 
	 * @param changesTabs The tabs to select from
	 * @return List of ChangesTabs selected
	 */
	private List<ChangesTab> selectTabsToLoad(List<ChangesTab> changesTabs) {
		String[] titles=new String[changesTabs.size()];
		for(int n=0;n<titles.length;n++) {
			titles[n]=changesTabs.get(n).getTitle();
		}
		SelectionDialog selectionDialog=new SelectionDialog(this,titles);
		selectionDialog.setTitle("Select tabs to load");
		selectionDialog.setVisible(true);

		boolean[] result=selectionDialog.getResult();		
		List<ChangesTab> tabsToLoad=new Vector<ChangesTab>();
		for(int n=0;n<result.length;n++) {
			if(result[n]) {				
				tabsToLoad.add(changesTabs.get(n));
			}
		}
		return tabsToLoad;
	}

	/**
	 * Select a file and save to it
	 */
	private void saveChanges() {
		if(tabbedPane.getTabCount()==0) {
			JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"There is no data to save","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}

		//We use a seperate Thread for that to not block the ui thread
		Thread saver=new Thread() {
			public void run() {
				//Select the file
				JFileChooser chooser=new JFileChooser();
				chooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						return f.isDirectory()||(f.isFile()&&f.getName().toLowerCase().endsWith(ChangeDataSetPersistenceHelper.FILE_ENDING));
					}
					@Override
					public String getDescription() {
						return ChangeDataSetPersistenceHelper.FILE_DESCRIPTION;
					}			
				});
				chooser.showSaveDialog(ChangeVisualizationUI.this);
				final File file=chooser.getSelectedFile();
				//Save to it
				save(file);
			}
		};
		saver.start();
	}	

	/**
	 * Saves to the given file. The user can select which of the active tabs to save.
	 * 
	 * @param file The file to save
	 */
	private void save(File file) {
		if(file==null) return;
		try {
			List<ChangesTab> changesTabs=getChangesTabs();		
			
			//Let the user select which tabs to save
			changesTabs=selectTabsToSave(changesTabs);
			
			if(ChangeDataSetPersistenceHelper.save(file,changesTabs)) {
				//Feedback of success currently disabled JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Changes successfully saved");
			}else {
				JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Saving changes aborted","Save aborted",JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Error saving changes : "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Prompts the user to select a list of {@link ChangesTab}s from a dialog to save
	 * 
	 * @param changesTabs The tabs to select from
	 * @return List of ChangesTabs selected
	 */
	private List<ChangesTab> selectTabsToSave(List<ChangesTab> changesTabs) {
		String[] titles=new String[changesTabs.size()];
		boolean[] initialValues=new boolean[changesTabs.size()];
		for(int n=0;n<titles.length;n++) {
			titles[n]=(changesTabs.get(n).isLoadedFromFile()?"*":"")+changesTabs.get(n).getTitle();			
			initialValues[n]=!changesTabs.get(n).isLoadedFromFile();
		}
		SelectionDialog selectionDialog=new SelectionDialog(this,titles,initialValues);
		selectionDialog.setTitle("Select tabs to save");
		selectionDialog.setVisible(true);
		
		boolean[] result=selectionDialog.getResult();		
		List<ChangesTab> tabsToSave=new Vector<ChangesTab>();
		for(int n=0;n<result.length;n++) {
			if(result[n]) {
				tabsToSave.add(changesTabs.get(n));
			}
		}		
		
		return tabsToSave;
	}

	/**
	 * Called externally to save of current tabs into given file.
	 * It can be called in headless mode, no visible user interaction will occur.
	 * 
	 * @param file The file to save to
	 * @return true if save was successful, false if no data to save exists
	 * @throws IOException If anything went wrong saving to the file
	 */
	public boolean saveToFile(File file) throws IOException {
		List<ChangesTab> changesTabs=getChangesTabs();
		return ChangeDataSetPersistenceHelper.save(file,changesTabs);
	}

	/**
	 * Returns a list of all actual {@link ChangesTabs} in the ui
	 * @return List of all tabs
	 */
	private List<ChangesTab> getChangesTabs() {
		List<ChangesTab> changesTabs=new Vector<ChangesTab>();
		for(int tab=0;tab<tabbedPane.getTabCount();tab++) {
			if(tabbedPane.getComponentAt(tab) instanceof ChangesTab) {
				ChangesTab changesTab=(ChangesTab) tabbedPane.getComponentAt(tab);
				changesTabs.add(changesTab);
			}
		}		
		return changesTabs;
	}

	/**
	 * Adds a new tab to this frame
	 * @param title The tab title
	 * @param component The tab component
	 */
	public void addTab(final String title, Component component) {
		this.tabbedPane.addTab(title, component);
		
		final CloseableTabComponent closeableTabComponent=new CloseableTabComponent(title);
		closeableTabComponent.setFont(ChangeVisualizationUI.DEFAULT_TABBED_PANE_FONT);

		//Add a listener to the tabComponent to react on its close button
		closeableTabComponent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice=JOptionPane.showConfirmDialog(ChangeVisualizationUI.this,"Close tab "+title,"Close tab",JOptionPane.YES_NO_CANCEL_OPTION);
				if(choice!=JOptionPane.YES_OPTION) {
					return;
				}
				for(int index=0;index<tabbedPane.getTabCount();index++) {
					if(tabbedPane.getTabComponentAt(index)==closeableTabComponent) {
						tabbedPane.removeTabAt(index);
						return;
					}
				}
			}			
		});
		this.tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1,closeableTabComponent);
	}	

	/**
	 * Show a file together with the change-tabs
	 * 
	 * @param uri The file to load and show
	 */
	public void addFile(File file) {
		try {
			InputStream in=new FileInputStream(file);
			byte[]bytes=new byte[(int)file.length()];
			int read=0;
			while(read<bytes.length) {
				read+=in.read(bytes,read,bytes.length-read);
			}
			in.close();
			JTextArea area=new JTextArea();
			area.setFont(ChangeVisualizationUI.DEFAULT_TEXTAREA_FONT);
			area.setText(new String(bytes));
			area.addMouseWheelListener(mwl);
			JScrollPane scroller=new JScrollPane(area);
			addTab(file.getName(),scroller);
		}catch(Exception ex) {
			JTextArea area=new JTextArea();
			area.setFont(ChangeVisualizationUI.DEFAULT_TEXTAREA_FONT);
			area.setText("Could not file :"+file);
			area.addMouseWheelListener(mwl);
			JScrollPane scroller=new JScrollPane(area);
			addTab("File error",scroller);
		}		
	}

	/**
	 * Gets the active {@link ChangesTab}
	 * 
	 * @return The active tab, may be null
	 */
	public ChangesTab getActiveChangesTab() {
		Component component = tabbedPane.getSelectedComponent();
		if(component==null||!(component instanceof ChangesTab)) {
			return null;
		}
		return (ChangesTab) component;
	}	

}
