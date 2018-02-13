package tools.vitruv.extensions.changevisualization.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.eclipse.emf.common.util.URI;

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
			System.err.println("falsche font : null");
		}
		Font defaultFont=(Font)UIManager.get(fontKey);
		if(defaultFont==null) {
			System.err.println("falsche font : "+fontKey);
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
	public static final Font DEFAULT_CHECKBOX_FONT=createFont(".font",16,Font.PLAIN);
	
	/**
	 * Default font used for tabbed pane (titles)
	 */
	public static final Font DEFAULT_TABBED_PANE_FONT=createFont("TabbedPane.font",14,Font.BOLD);
	
	/**
	 * Constructs a ChangeVisualizationUI and initializes its basic layout and behaviour
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
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(ChangeVisualizationUI.DEFAULT_TABBED_PANE_FONT);//used just for the title
		setContentPane(tabbedPane);

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
}
