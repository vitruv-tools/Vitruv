package tools.vitruv.extensions.changevisualization.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import tools.vitruv.extensions.changevisualization.common.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.common.VisualizationMode;
import tools.vitruv.extensions.changevisualization.ui.ChangesTab;

/**
 * This class helps to store and load {@link ChangesTab}s
 * 
 * @author Andreas  Loeffler
 */
public class ChangeDataSetPersistenceHelper {

	/**
	 * The default file ending
	 */
	public static final String FILE_ENDING=".pcf";
	
	/**
	 * The description to use in dialogs
	 */
	public static final String FILE_DESCRIPTION="Propagated Changes Files ("+FILE_ENDING+")";

	/**
	 * This marks the end of the file
	 */
	private static final String EOF_MARKER="THE_END";
	
	/**
	 * No instances of ChangeDataSetPersistenceHelper are used
	 */
	private ChangeDataSetPersistenceHelper() {
		//Not used
	}

	/**
	 * Loads {@link ChangesTab}s from a given file. 
	 * 
	 * @param file The file to load
	 * @return List of ChangesTabs loaded
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<ChangesTab> load(File file) throws IOException, ClassNotFoundException {
		List<ChangesTab> changesTabs=new Vector<ChangesTab>();
		
		//Open a fileinputstream
		FileInputStream fIn=new FileInputStream(file);
		
		//Open a gzip inputstream stream wrapping the file in
		GZIPInputStream gzIn=new GZIPInputStream(fIn);
		
		//finally let the objectinputstream read from the gzip in
		ObjectInputStream oIn=new ObjectInputStream(gzIn);
		
		//1. Read title String until the EOF_MARKER is read
		String title=(String) oIn.readObject();
		while(!EOF_MARKER.equals(title)) {		
			//2. Read the visualization mode
			VisualizationMode mode=(VisualizationMode)oIn.readObject();
			//3. Read the number of changeDataSets available
			int cdsCount=oIn.readInt();
			
			//4. The first read cds is used to create the changesTab
			ChangesTab changesTab=new ChangesTab((ChangeDataSet) oIn.readObject(),mode,title,true);
			
			//We use the components otherwise not needed name feature to transfer the title
			changesTab.setName(title);
			for(int n=1;n<cdsCount;n++) {
				//4. Read the remaining changeDataSets and add them to the tab
				changesTab.addChanges((ChangeDataSet) oIn.readObject());
			}			
			changesTabs.add(changesTab);			
			
			//1. Read title String until the EOF_MARKER is read
			title=(String) oIn.readObject();
		}
		oIn.close();
		
		return changesTabs;
	}	
	
	/**
	 * Saves given {@link ChangesTab}s to a given file.
	 *  
	 * @param file The file to save to
	 * @param changesToSave List of tabs to save
	 * @return true if saving was successful, false if nothing was saved
	 * @throws IOException
	 */
	public static boolean save(File file, List<ChangesTab> changesToSave) throws IOException{
		
		if(changesToSave==null||changesToSave.isEmpty()){
			return false;
		}
		
		FileOutputStream fOut=null;
		if(!file.getName().toLowerCase().endsWith(FILE_ENDING)) {
			fOut=new FileOutputStream(new File(file.getParentFile(),file.getName()+FILE_ENDING));
		}else {
			fOut=new FileOutputStream(file);
		}		
		
		//Create a gzip outputstream and wrap the fileoutputstream with it
		GZIPOutputStream gzOut=new GZIPOutputStream(fOut);
		
		//And finally let the objectoutputstream write to the gzip out
		ObjectOutputStream oOut=new ObjectOutputStream(gzOut);
		for(ChangesTab changesTab:changesToSave) {
			//1. write title
			String title=changesTab.getTitle();			
			oOut.writeObject(title);
			//2. write visualization mode
			oOut.writeObject(changesTab.getVisualizationMode());
			//3. write the number of changeDataSets the tab holds
			oOut.writeInt(changesTab.getChanges().size());
			//4. Now write each changeDataSet
			for(ChangeDataSet cds:changesTab.getChanges()) {
				oOut.writeObject(cds);
			}
		}
		//Last step, write the EOF_MARKER
		oOut.writeObject(EOF_MARKER);
		oOut.flush();
		oOut.close();
		return true;
	}	

}
