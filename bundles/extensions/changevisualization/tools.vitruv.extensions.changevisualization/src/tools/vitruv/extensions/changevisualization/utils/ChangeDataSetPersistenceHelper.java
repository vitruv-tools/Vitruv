/**
 * 
 */
package tools.vitruv.extensions.changevisualization.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.ChangeVisualization.VisualizationMode;
import tools.vitruv.extensions.changevisualization.ui.ChangesTab;

/**
 * @author andreas
 *
 */
public class ChangeDataSetPersistenceHelper {

	public static final String FILE_ENDING=".pcf";
	
	public static final String FILE_DESCRIPTION="Propagated Changes Files (pcf)";

	private static final String EOF_MARKER="THE_END";
	
	private static final String LOAD_MARKER="*";
	

	private ChangeDataSetPersistenceHelper() {
		//Not used
	}

	public static List<ChangesTab> load(File file, JFrame frame) throws IOException, ClassNotFoundException {
		List<ChangesTab> changesTabs=new Vector<ChangesTab>();
		FileInputStream fIn=new FileInputStream(file);
		GZIPInputStream gzIn=new GZIPInputStream(fIn);
		ObjectInputStream oIn=new ObjectInputStream(gzIn);
		String title=(String) oIn.readObject();
		while(!EOF_MARKER.equals(title)) {				
			VisualizationMode mode=(VisualizationMode)oIn.readObject();
			int cdsCount=oIn.readInt();
			ChangesTab changesTab=new ChangesTab((ChangeDataSet) oIn.readObject(),mode);
			changesTab.setName(title);
			for(int n=1;n<cdsCount;n++) {
				changesTab.addChanges((ChangeDataSet) oIn.readObject());
			}			
			changesTabs.add(changesTab);			
			title=(String) oIn.readObject();
		}
		oIn.close();
		
		List<ChangesTab> loadedChanges=getListOfTabsToLoad(changesTabs,frame);
		return loadedChanges;
	}

	private static List<ChangesTab> getListOfTabsToLoad(List<ChangesTab> changesTabs, JFrame frame) {
		String[] titles=new String[changesTabs.size()];
		for(int n=0;n<titles.length;n++) {
			titles[n]=changesTabs.get(n).getName();
		}
		SelectionDialog selectionDialog=new SelectionDialog(frame,titles);
		selectionDialog.setTitle("Select tabs to load");
		selectionDialog.setVisible(true);
		
		boolean[] result=selectionDialog.getResult();		
		List<ChangesTab> tabsToLoad=new Vector<ChangesTab>();
		for(int n=0;n<result.length;n++) {
			if(result[n]) {
				changesTabs.get(n).setName(LOAD_MARKER+changesTabs.get(n).getName());
				tabsToLoad.add(changesTabs.get(n));
			}
		}
		return tabsToLoad;
	}
	
	public static boolean save(File file, List<ChangesTab> changesTabs, JFrame frame) throws IOException{
		return save(file,changesTabs,frame,false);
	}

	public static boolean save(File file, List<ChangesTab> changesTabs, JFrame frame, boolean noQuestion) throws IOException{
		
		List<ChangesTab> changesToSave=noQuestion?changesTabs:getListOfTabsToSave(changesTabs,frame);
		if(changesToSave==null||changesToSave.isEmpty()){
			return false;
		}
		
		FileOutputStream fOut=null;
		if(!file.getName().toLowerCase().endsWith(FILE_ENDING)) {
			fOut=new FileOutputStream(new File(file.getParentFile(),file.getName()+FILE_ENDING));
		}else {
			fOut=new FileOutputStream(file);
		}		
		
		GZIPOutputStream gzOut=new GZIPOutputStream(fOut);
		ObjectOutputStream oOut=new ObjectOutputStream(gzOut);
		for(ChangesTab changesTab:changesToSave) {			
			String title=changesTab.getName();		
			if(title.startsWith(LOAD_MARKER)) {
				title=title.substring(LOAD_MARKER.length());//Remove prior to saving
			}
			oOut.writeObject(title);
			oOut.writeObject(changesTab.getVisualizationMode());
			oOut.writeInt(changesTab.getChanges().size());
			for(ChangeDataSet cds:changesTab.getChanges()) {
				oOut.writeObject(cds);
			}
		}
		oOut.writeObject(EOF_MARKER);
		oOut.flush();
		oOut.close();
		return true;
	}

	private static List<ChangesTab> getListOfTabsToSave(List<ChangesTab> changesTabs, JFrame frame) {
		String[] titles=new String[changesTabs.size()];
		boolean[] initialValues=new boolean[changesTabs.size()];
		for(int n=0;n<titles.length;n++) {
			titles[n]=changesTabs.get(n).getName();
			initialValues[n]=!titles[n].startsWith(LOAD_MARKER);
		}
		SelectionDialog selectionDialog=new SelectionDialog(frame,titles,initialValues);
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

}
