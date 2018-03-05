package tools.vitruv.extensions.changevisualization;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

import tools.vitruv.extensions.changevisualization.tree.TreeChangeDataSet;
import tools.vitruv.extensions.changevisualization.ui.ChangeVisualizationUI;
import tools.vitruv.extensions.changevisualization.ui.ChangesTab;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.vsum.PropagatedChangeListener;

/**
 * This class provides a single instance of the visualization. It is also used as the factory to create
 * individual {@link PropagatedChangeListener}s.
 * The basic visualization parameters are stored here as well as the methods necessary to interact with
 * the visualization component in an implementation independent way
 * 
 * @author Andreas Loeffler
 *
 */
public final class ChangeVisualization{
		
	/**
	 * The different modes of visualization
	 * 
	 * @author Andreas Loeffler
	 *
	 */
	public static enum VisualizationMode{
		TREE
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
	 * Returns a {@link PropagatedChangeListener} instance used to communicate with the visualization.
	 * The usual way to interact with the ui is through the listeners generated here. All results that
	 * are posted to the generated listener are grouped together through the given modelID and shown in the ui.
	 * If necessary, it is possible to interact directly with the ui at {@link ChangeVisualization#postChanges(String, VitruvDomain, VitruvDomain, List)}.
	 * Details are found there.
	 * 
	 * @param modelID The id for the created listener to group results that are belonging together 
	 * @return The change listener
	 */
	public static PropagatedChangeListener createPropagatedChangeListener(final String modelID) {
		return new PropagatedChangeListener() {
			@Override
			public void postChanges(VitruvDomain sourceDomain, VitruvDomain targetDomain,
					List<PropagatedChange> propagationResult) {
				ChangeVisualization.INSTANCE.postChanges(modelID, sourceDomain, targetDomain, propagationResult);
			}			
		};
	}	

	/**
	 * Makes the visualization ui visible and assures it displayed in front of all other windows.
	 * It is not in always on top state afterwards, just one time moved to front.
	 */
	public static void showUi() {
		if(!ChangeVisualization.INSTANCE.ui.isVisible()) {
			ChangeVisualization.INSTANCE.ui.setVisible(true);			
		}

		ChangeVisualization.INSTANCE.ui.setAlwaysOnTop(true);//This way the window is always moved to front, works more reliable
		ChangeVisualization.INSTANCE.ui.setAlwaysOnTop(false);//than toFront()				
	}

	/**
	 * Waits for this visualization ui to close before returning.
	 * Especially useful for junit tests when the visualization results should be used
	 * prior to java vm exit
	 */
	public static void waitForFrameClosing() {		
		synchronized(ChangeVisualization.INSTANCE.ui) {			
			if(ChangeVisualization.INSTANCE.ui.isVisible()) {			
				//We wait until the frame gets closed. During closing, the frame issus a notifyAll()
				try {
					ChangeVisualization.INSTANCE.ui.wait();
				} catch (InterruptedException e) {
					//nothing to do in this case. I never have had or heard of any reason for interruption
					//that occured here besides the intentional notify
				}
			}
		}
	}

	/**
	 * Show a file together with the change-tabs. Can be used to display arbitrary text files which may be useful.
	 * For example generated models, java files...
	 * 
	 * @param uri The file to load and show
	 */
	public static void addUri(URI uri) {
		ChangeVisualization.INSTANCE.ui.addUri(uri);
	}
	
	/**
	 * Returns the single instance of the {@link ChangeVisualizationUI}
	 *  
	 * @return The changeVisualizationUI
	 */
	public static ChangeVisualizationUI getChangeVisualizationUI() {
		return ChangeVisualization.INSTANCE.ui;
	}
	
	/**
	 * Saves all model data to given file
	 * 
	 * @param file The file to save to
	 * @return true if save was successful, false if no data to save exists
	 * @throws IOException If anything went wrong saving to the file
	 */
	public static boolean saveToFile(File file) throws IOException {
		return ChangeVisualization.INSTANCE.ui.saveToFile(file);		
	}

	//Instance methode and variables
	/**
	 * The visualization frame
	 */
	private final ChangeVisualizationUI ui;

	/**
	 * The different tabs that are registered in the ui
	 */
	private final Map<String,ChangesTab> changeTabs=new Hashtable<String,ChangesTab>();
	
	/**
	 * Constructs a new ChangeVisualization
	 */
	private ChangeVisualization() {
		ui=new ChangeVisualizationUI();
	}
	
	/**
	 * The {@link PropagatedChangeListener} generated through the factory method {@link ChangeVisualization#createPropagatedChangeListener(String)} call this method.
	 * It is responsible for grouping different models into individual tabs and adding them to the ui. It can be used directly from external implementations
	 * of {@link PropagatedChangeListener} if they organize correct usage of the modelID on their own. 
	 *  
	 * @param modelID The model's id to group results together
	 * @param sourceDomain The source Domain
	 * @param targetDomain The target Domain
	 * @param propagationResult The propagation result
	 */
	public synchronized void postChanges(String modelID, VitruvDomain sourceDomain,VitruvDomain targetDomain, List<PropagatedChange> propagationResult) {
		
		//Store in corresponding model vector
		ChangesTab tab = changeTabs.get(modelID);	
		if(tab==null) {
			//create the changes tab and the dataSet
			ChangeDataSet cds=createChangeDataSet(modelID+" 1", sourceDomain,targetDomain , propagationResult);
			tab=createChangesTab(cds);
			changeTabs.put(modelID,tab);
			ui.addTab(modelID,tab);
		}else{
			//on an existing tab, append the changes made
			ChangeDataSet cds=createChangeDataSet(modelID+" "+(tab.getChanges().size()+1), sourceDomain, targetDomain, propagationResult);
			tab.addChanges(cds);
			tab.repaint();
		}
	}	
	
	/**
	 * Creates a visualization-specific cds object from the given parameters
	 * 
	 * @param id The ID of the cds to create
	 * @param propagationResult The propagation result to process
	 * @param className The calling classes name
	 * @return
	 */
	private ChangeDataSet createChangeDataSet(String id, VitruvDomain sourceDomain, VitruvDomain targetDomain, List<PropagatedChange> propagationResult) {
		switch(ChangeVisualization.VISUALIZATION_MODE) {
		case TREE:
			return new TreeChangeDataSet(id,sourceDomain,targetDomain,propagationResult);		
		default:
			//As long as all cases are covered in switch statement, this code is never reached
			throw new RuntimeException("Uncovered case in witch statement, missing mode "+ChangeVisualization.VISUALIZATION_MODE);
		}

	}

	/**
	 * Create the tab for a new model
	 * 
	 * @param cds The cds used for the tab
	 * @return A tab visualizing the given cds
	 */
	private ChangesTab createChangesTab(ChangeDataSet cds) {		
		return new ChangesTab(cds,ChangeVisualization.VISUALIZATION_MODE);
	}	

}
