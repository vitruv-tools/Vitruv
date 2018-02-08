package tools.vitruv.extensions.changevisualization;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import tools.vitruv.extensions.changevisualization.table.TableChangeDataSet;
import tools.vitruv.extensions.changevisualization.tree.TreeChangeDataSet;
import tools.vitruv.extensions.changevisualization.ui.ChangeVisualizationUI;
import tools.vitruv.extensions.changevisualization.ui.ChangesTab;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.vsum.ChangeListener;

/**
 * This class provides a single instance of a change listener and a corresponding visualization instance.
 * The basic visualization parameters are stored here as well as the methods necessary to interact with
 * the visualization component in an implementation independent way
 * 
 * @author Andreas Loeffler
 *
 */
public final class ChangeVisualization implements ChangeListener{
	
	public static enum VisualizationMode{
		TREE,
		TABLE
	}
	
	private static final VisualizationMode VISUALIZATION_MODE=VisualizationMode.TREE;
		
	private static final ChangeVisualization INSTANCE=new ChangeVisualization();

	public static ChangeListener getChangeListener() {
		return INSTANCE;
	}	

	public static void showUi() {
		if(!INSTANCE.ui.isVisible()) {
			INSTANCE.ui.setVisible(true);			
		}

		INSTANCE.ui.setAlwaysOnTop(true);//This way the window is always moved to front, works more reliable
		INSTANCE.ui.setAlwaysOnTop(false);//than toFront()
		
		//################## Ignore, will be removed in final code
		//On my setup move it to left monitor to speed up debugging and testing
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] gs = ge.getScreenDevices();
			if(gs!=null&&gs.length==3) {
				INSTANCE.ui.setLocation(-(INSTANCE.ui.getWidth()+50),100);
				//frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			}
		}
		//#########################
	}
	
	public static void waitForFrameClosing() {		
		synchronized(INSTANCE.ui) {
			//if already closed, return
			if(!INSTANCE.ui.isVisible()) return;
			
			//Otherwise we wait until the frame gets closed
			try {
				INSTANCE.ui.wait();
			} catch (InterruptedException e) {
				//nothing to do in this case
			}
		}
	}
	
	/**
	 * Show a file together with the change-tabs
	 * 
	 * @param uri The file to load and show
	 */
	public static void addUri(URI uri) {
		INSTANCE.ui.addUri(uri);
	}		
	
	private static String extractCallerClassName() {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		if(trace==null||trace.length<5) {			
			return "couldNotDetermineClassName";
		}
		return trace[4].getClassName();
	}
	
	private static ChangeDataSet createChangeDataSet(String simpleID, List<PropagatedChange> propagationResult, String className, String name) {
		switch(VISUALIZATION_MODE) {
		case TREE:
			return new TreeChangeDataSet(simpleID+" <"+propagationResult.hashCode()+">",className,name,propagationResult);
		case TABLE:
			return new TableChangeDataSet(simpleID+" <"+propagationResult.hashCode()+">",className,name,propagationResult);
		default:
			//As long as all cases are covered in switch statement, this code is never reached
			throw new RuntimeException("Uncovered case in witch statement, missing mode "+VISUALIZATION_MODE);
		}
		
	}

	private static ChangesTab createChangesTab(ChangeDataSet cds) {
		//Create the tab for a new model
		return new ChangesTab(cds,VISUALIZATION_MODE);
	}	

	//Instance methode and variables
	
	private final ChangeVisualizationUI ui;

	private final Hashtable<String,ChangesTab> changeTabs=new Hashtable<String,ChangesTab>();
	
	private ChangeVisualization() {
		ui=new ChangeVisualizationUI();
	}
	
	@Override
	public void postChanges(String name, List<PropagatedChange> propagationResult) {

		String className=extractCallerClassName();

		//Create dataSet
		final String simpleID=className+"."+name;
		
		final ChangeDataSet cds=createChangeDataSet(simpleID, propagationResult, className, name);	

		//Store in corresponding model vector
		ChangesTab tab = changeTabs.get(simpleID);	
		if(tab==null) {
			//create the changes tab
			tab=createChangesTab(cds);
			changeTabs.put(simpleID,tab);
			ui.addTab(simpleID,tab);
		}else{
			//on an existing tab, append the changes made
			tab.addChanges(cds);
			tab.repaint();
		}
	
	}
	
}
