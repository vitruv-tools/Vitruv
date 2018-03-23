package tools.vitruv.extensions.changevisualization;

import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import tools.vitruv.extensions.changevisualization.common.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.common.VisualizationMode;
import tools.vitruv.extensions.changevisualization.tree.decoder.TreeChangeDataSetDecoder;
import tools.vitruv.extensions.changevisualization.ui.ChangeVisualizationUI;
import tools.vitruv.extensions.changevisualization.ui.ChangesTab;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.vsum.PropagatedChangeListener;

/**
 * This class provides a single instance used to communicate with the visualization ui.
 * The basic visualization parameters are stored here as well as the methods necessary to interact with
 * the {@link ChangeComponent} in an implementation independent way.
 * 
 * @author Andreas Loeffler
 *
 */
public final class ChangeVisualization implements PropagatedChangeListener{	
	
	/**
	 * The active visualization mode
	 */
	private static final VisualizationMode VISUALIZATION_MODE=VisualizationMode.TREE;

	/**
	 * The single instance of this class
	 */
	private static final ChangeVisualization instance=new ChangeVisualization();

	/**
	 * Returns a {@link PropagatedChangeListener} instance used to communicate with the visualization.
	 *  
	 * @return The propagated change listener
	 */
	public static PropagatedChangeListener getPropagatedChangeListener() {
		return instance;
	}	

	/**
	 * Makes the visualization ui visible and assures it displayed in front of all other windows.
	 * It is not in always on top state afterwards, just one time moved to front.
	 */
	public static void showUi() {
		if(!ChangeVisualization.instance.ui.isVisible()) {
			ChangeVisualization.instance.ui.setVisible(true);			
		}

		ChangeVisualization.instance.ui.setAlwaysOnTop(true);//This way the window is always moved to front, works more reliable
		ChangeVisualization.instance.ui.setAlwaysOnTop(false);//than toFront()				
	}

	/**
	 * Waits for the visualization ui to close before returning.
	 * Especially useful for junit tests when the visualization results should be used
	 * prior to java virtual machine exit
	 */
	public static void waitForFrameClosing() {		
		synchronized(ChangeVisualization.instance.ui) {			
			if(ChangeVisualization.instance.ui.isVisible()) {			
				//We wait until the frame gets closed. During closing, the frame issus a notifyAll()
				try {
					ChangeVisualization.instance.ui.wait();
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
	public static void addFile(File file) {
		ChangeVisualization.instance.ui.addFile(file);
	}
		
	/**
	 * Saves all model data to given file.
	 * No user interaction occurs. All tabs are saved.
	 * 
	 * @param file The file to save to
	 * @return true if save was successful, false if no data to save exists
	 * @throws IOException If anything went wrong saving to the file
	 */
	public static boolean saveToFile(File file) throws IOException {
		return ChangeVisualization.instance.ui.saveToFile(file);		
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
		ui=ChangeVisualizationUI.getInstance();
		
		//If tabs get closed in the ui, we remove them from our local map too
		ui.addContainerListener(new ContainerListener() {
			@Override
			public void componentAdded(ContainerEvent e) {
				//Adding is not of interest
			}
			@Override
			public void componentRemoved(ContainerEvent e) {
				//When a tab gets removed, update local state
				if(e.getChild() instanceof ChangesTab) {
					ChangesTab tab=(ChangesTab) e.getChild();
					changeTabs.values().remove(tab);
				}else {
					//Nothing to do, we do not track other user added tabs
				}
			}			
		});
	}
	
	@Override
	public synchronized void postChanges(String virtualModelName, VitruvDomain sourceDomain,VitruvDomain targetDomain, List<PropagatedChange> propagationResult) {
		
		//Store in corresponding changes tab
		ChangesTab tab = changeTabs.get(virtualModelName);	
		if(tab==null) {
			//create the changes tab and the dataSet
			ChangeDataSet cds=createChangeDataSet(virtualModelName+" 1", sourceDomain,targetDomain , propagationResult);
			tab=createChangesTab(cds,virtualModelName);
			changeTabs.put(virtualModelName,tab);
			ui.addTab(tab.getTitle(),tab);			
		}else{
			//on an existing tab, append the changes made
			ChangeDataSet cds=createChangeDataSet(virtualModelName+" "+(tab.getChanges().size()+1), sourceDomain, targetDomain, propagationResult);
			tab.addChanges(cds);
			tab.repaint();
		}
	}	
	
	/**
	 * Creates a visualization-specific {@link ChangeDataSet} from the given parameters
	 *  
	 * @param id The ID of the changeDataSet to create
	 * @param sourceDomain The source domain, may be null
     * @param targetDomain The target domain, may be null
	 * @param propagationResult The propagation result to process	 
	 * @return The created changeDataSet
	 */
	private ChangeDataSet createChangeDataSet(String id, VitruvDomain sourceDomain, VitruvDomain targetDomain, List<PropagatedChange> propagationResult) {
		switch(ChangeVisualization.VISUALIZATION_MODE) {
		case TREE:
			return new TreeChangeDataSetDecoder(id,sourceDomain,targetDomain,propagationResult).getChangeDataSet();		
		default:
			//As long as all cases are covered in switch statement, this code is never reached
			throw new RuntimeException("Uncovered case in witch statement, missing mode "+ChangeVisualization.VISUALIZATION_MODE);
		}

	}

	/**
	 * Create a {@link ChangesTab} for a new model
	 * 
	 * @param changeDataSet The changeDataSet used for the tab
	 * @param title The title for the tab
	 * @return A tab visualizing the given changeDataSet
	 */
	private ChangesTab createChangesTab(ChangeDataSet changeDataSet,String title) {		
		return new ChangesTab(changeDataSet,ChangeVisualization.VISUALIZATION_MODE,title,false);
	}	

}
