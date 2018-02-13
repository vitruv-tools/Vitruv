package tools.vitruv.extensions.changevisualization;

import java.util.Hashtable;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import tools.vitruv.extensions.changevisualization.table.TableChangeDataSet;
import tools.vitruv.extensions.changevisualization.tree.TreeChangeDataSet;
import tools.vitruv.extensions.changevisualization.ui.ChangeVisualizationUI;
import tools.vitruv.extensions.changevisualization.ui.ChangesTab;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.vsum.PropagatedChangeListener;

/**
 * This class provides a single instance of a change listener and a corresponding visualization instance.
 * The basic visualization parameters are stored here as well as the methods necessary to interact with
 * the visualization component in an implementation independent way
 * 
 * @author Andreas Loeffler
 *
 */
public final class ChangeVisualization implements PropagatedChangeListener{
		
	/**
	 * The different modes of visualization
	 * 
	 * @author Andreas Loeffler
	 *
	 */
	public static enum VisualizationMode{
		TREE,
		TABLE
	}

	/**
	 * The active visualization mode
	 */
	private static final VisualizationMode VISUALIZATION_MODE=VisualizationMode.TREE;

	/**
	 * The single instance of change visualization
	 */
	private static final ChangeVisualization INSTANCE=new ChangeVisualization();

	/**
	 * Returns the single change listener instance used to communicate with the visualization
	 * 
	 * @return The change listener
	 */
	public static PropagatedChangeListener getChangeListener() {
		return INSTANCE;
	}	

	/**
	 * Makes the visualization ui visible and assures it displayed in front of all other windows.
	 * It is not in always on top state afterwards, just one time moved to front.
	 */
	public static void showUi() {
		if(!INSTANCE.ui.isVisible()) {
			INSTANCE.ui.setVisible(true);			
		}

		INSTANCE.ui.setAlwaysOnTop(true);//This way the window is always moved to front, works more reliable
		INSTANCE.ui.setAlwaysOnTop(false);//than toFront()				
	}

	/**
	 * Waits for this visualization ui to close before returning.
	 * Especially useful for junit tests when the visualization results should be used
	 * prior to java vm exit
	 */
	public static void waitForFrameClosing() {		
		synchronized(INSTANCE.ui) {			
			if(INSTANCE.ui.isVisible()) {			
				//We wait until the frame gets closed. During closing, the frame issus a notifyAll()
				try {
					INSTANCE.ui.wait();
				} catch (InterruptedException e) {
					//nothing to do in this case. I never have had or heard of any reason for interruption
					//that occured here besides the intentional notify
				}
			}
		}
	}

	/**
	 * Show a file together with the change-tabs. Can be used to display arbitry text files which may be useful.
	 * For example generated models, java files...
	 * 
	 * @param uri The file to load and show
	 */
	public static void addUri(URI uri) {
		INSTANCE.ui.addUri(uri);
	}		

	/**
	 * Extracts the calling classes name. This information is used to distinguish between different models.
	 * 
	 * @return The calling classes name
	 */
	private static String extractCallerClassName() {
		//This implementation works jor JUnit tests, but not for live models
		//Live models have to use other ways (for example using eclipse information to get the filename)
		//This will be implemented in the future
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		if(trace==null||trace.length<5) {			
			return "couldNotDetermineClassName";
		}		
		//It is always position 4 when junit tests are run
		return trace[4].getClassName();
	}

	/**
	 * Creates a visualization-specific cds object from the given parameters
	 * 
	 * @param simpleID The ID of the cds to create
	 * @param propagationResult The propagation result to process
	 * @param className The calling classes name
	 * @return
	 */
	private static ChangeDataSet createChangeDataSet(String simpleID, List<PropagatedChange> propagationResult, String className) {
		switch(VISUALIZATION_MODE) {
		case TREE:
			return new TreeChangeDataSet(simpleID+" <"+propagationResult.hashCode()+">",propagationResult);
		case TABLE:
			return new TableChangeDataSet(simpleID+" <"+propagationResult.hashCode()+">",propagationResult);
		default:
			//As long as all cases are covered in switch statement, this code is never reached
			throw new RuntimeException("Uncovered case in witch statement, missing mode "+VISUALIZATION_MODE);
		}

	}

	/**
	 * Create the tab for a new model
	 * 
	 * @param cds The cds used for the tab
	 * @return A tab visualizing the given cds
	 */
	private static ChangesTab createChangesTab(ChangeDataSet cds) {		
		return new ChangesTab(cds,VISUALIZATION_MODE);
	}	

	//Instance methode and variables

	/**
	 * The visualization frame
	 */
	private final ChangeVisualizationUI ui;

	/**
	 * The different tabs that are registered in the ui
	 */
	private final Hashtable<String,ChangesTab> changeTabs=new Hashtable<String,ChangesTab>();

	/**
	 * Constructs a new ChangeVisualization
	 */
	private ChangeVisualization() {
		ui=new ChangeVisualizationUI();
	}

	@Override
	public void postChanges(String name, List<PropagatedChange> propagationResult) {

		//extract class name
		String className=extractCallerClassName();

		//Create cds id
		final String simpleID=className+"."+name;

		//Create dataSet
		final ChangeDataSet cds=createChangeDataSet(simpleID, propagationResult, className);	

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
