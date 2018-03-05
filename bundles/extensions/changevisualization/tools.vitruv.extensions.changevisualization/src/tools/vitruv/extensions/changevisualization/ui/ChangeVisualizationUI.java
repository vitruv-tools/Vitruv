package tools.vitruv.extensions.changevisualization.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import org.eclipse.emf.common.util.URI;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.ChangeVisualization.VisualizationMode;
import tools.vitruv.extensions.changevisualization.utils.ChangeDataSetPersistenceHelper;

/**
 * The frame in which the change visualization is displayed 
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

	//Instance methods and variables
	
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
	public ChangeVisualizationUI() {
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

	private void loadChanges() {
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
		chooser.showOpenDialog(this);
		final File file=chooser.getSelectedFile();
		if(file==null) return;
		Thread loader=new Thread() {
			public void run() {
				try {
					if(file==null) return;
					List<ChangesTab> loadedTabs=ChangeDataSetPersistenceHelper.load(file,ChangeVisualizationUI.this);
					if(loadedTabs==null||loadedTabs.isEmpty()) {
						JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Loading changes aborted","Load aborted",JOptionPane.ERROR_MESSAGE);
					}else {
						for(ChangesTab changesTab:loadedTabs) {		
							//We use the components otherwise not needed name feature to transfer the title
							tabbedPane.addTab(changesTab.getName(), changesTab);						
						}
						JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Changes successfully loaded");
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Error loading changes : "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		loader.start();
	}

	private void saveChanges() {
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
		chooser.showSaveDialog(this);
		final File file=chooser.getSelectedFile();
		if(file==null) return;
		Thread saver=new Thread() {
			public void run() {
				try {
					List<ChangesTab> changesTabs=new Vector<ChangesTab>();
					for(int tab=0;tab<tabbedPane.getTabCount();tab++) {
						if(tabbedPane.getComponent(tab) instanceof ChangesTab) {
							ChangesTab changesTab=(ChangesTab) tabbedPane.getComponent(tab);
							String title=tabbedPane.getTitleAt(tab);	
							//We use the components otherwise not needed name feature to transfer the title
							changesTab.setName(title);
							changesTabs.add(changesTab);
						}
					}
					if(ChangeDataSetPersistenceHelper.save(file,changesTabs,ChangeVisualizationUI.this)) {
						JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Changes successfully saved");
					}else {
						JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Saving changes aborted","Save aborted",JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(ChangeVisualizationUI.this,"Error saving changes : "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}		
			}
		};
		saver.start();
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
		List<ChangesTab> changesTabs=new Vector<ChangesTab>();
		for(int tab=0;tab<tabbedPane.getTabCount();tab++) {
			if(tabbedPane.getComponent(tab) instanceof ChangesTab) {
				ChangesTab changesTab=(ChangesTab) tabbedPane.getComponent(tab);
				String title=tabbedPane.getTitleAt(tab);	
				//We use the components otherwise not needed name feature to transfer the title
				changesTab.setName(title);
				changesTabs.add(changesTab);
			}
		}
		if(ChangeDataSetPersistenceHelper.save(file,changesTabs,null,true)) {
			//with noQuestion==true, the headless mode is activated and no frame needed
			return true;
		}else {
			return false;
		}

	}

	/**
	 * Adds a new tab to this frame
	 * @param title
	 * @param component
	 */
	public void addTab(String title, Component component) {
		((JTabbedPane)getContentPane()).addTab(title, component);
	}	

	/**
	 * Show a file together with the change-tabs
	 * 
	 * @param uri The file to load and show
	 */
	public void addUri(URI uri) {
		if(uri.isFile()) {
			try {
				File file=new File(uri.toFileString());
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
				area.setText("Could not load uri :"+uri.toString());
				area.addMouseWheelListener(mwl);
				JScrollPane scroller=new JScrollPane(area);
				addTab("Uri error",scroller);
			}
		}
	}

	/**
	 * Gets the active {@link ChangesTab}
	 * 
	 * @return The active tab
	 */
	public ChangesTab getActiveChangesTab() {
		return (ChangesTab) tabbedPane.getSelectedComponent();
	}

}
