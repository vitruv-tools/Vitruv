package tools.vitruv.extensions.changevisualization.ui;

import java.awt.Component;
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
		setContentPane(new JTabbedPane());

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
				area.setFont(area.getFont().deriveFont(16f));
				area.setText(new String(bytes));
				area.addMouseWheelListener(mwl);
				JScrollPane scroller=new JScrollPane(area);
				addTab(file.getName(),scroller);
			}catch(Exception ex) {
				JTextArea area=new JTextArea();
				area.setFont(area.getFont().deriveFont(16f));
				area.setText("Could not load uri :"+uri.toString());
				area.addMouseWheelListener(mwl);
				JScrollPane scroller=new JScrollPane(area);
				addTab("Uri error",scroller);
			}
		}
	}
}
