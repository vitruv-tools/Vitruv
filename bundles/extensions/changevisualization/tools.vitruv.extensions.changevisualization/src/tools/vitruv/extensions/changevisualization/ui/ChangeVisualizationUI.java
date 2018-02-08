/**
 * 
 */
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
 * @author Andreas Löffler
 *
 */
public class ChangeVisualizationUI extends JFrame {
	
	public ChangeVisualizationUI() {
		super("Vitruv Change Visualization");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1900,1020);
		setLocationRelativeTo(null);
		setContentPane(new JTabbedPane());

		//Needed for Stop-Test-Hack
		addWindowListener(new WindowAdapter() {
			/**
			 * Invoked when a window is in the process of being closed.
			 * The close operation can be overridden at this point.
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
