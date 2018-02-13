/**
 * 
 */
package tools.vitruv.extensions.changevisualization.table;

import java.util.List;
import java.util.Vector;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.utils.ModelHelper;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.change.echange.EChange;

/**
 * The table visualization was the first approach and displays most information wrong
 * Do not consider it during code review, it will either be updated or removed in the final plugin
 * 
 * @author Andreas Loeffler
 *
 */
public class TableChangeDataSet extends ChangeDataSet {

	/**
	 * @param cdsId
	 * @param propagationResult
	 */
	public TableChangeDataSet(String cdsId, List<PropagatedChange> propagationResult) {
		super(cdsId, propagationResult);
	}

	
	/**
	 * Vector of Vectors representing the data for a table.
	 * 
	 * It has to be vector to be usable by the DefaultTableModel constructor 
	 */
	private Vector<Vector<Object>> dataVector;

	protected void extractData(List<PropagatedChange> propagationResult) {
		dataVector = new Vector<Vector<Object>>();

		for(PropagatedChange propChange:propagationResult) {			
			encode(propChange);						
		}
	}


	private void encode(PropagatedChange propChange) {
		VitruviusChange origChange = propChange.getOriginalChange();					
		encode(origChange,ChangeType.ORIGINAL_CHANGE);			

		VitruviusChange consequentialChanges=propChange.getConsequentialChanges();		
		encode(consequentialChanges,ChangeType.CONSEQUENTIAL_CHANGE);
	}

	private void encode(VitruviusChange change, ChangeType type) {

		//Create vector dataset
		String changeString="";
		switch(type) {
		case ORIGINAL_CHANGE:
			changeString="Original Change";
			break;
		case CONSEQUENTIAL_CHANGE:
			changeString="Consequential Change";
			break;
		default:
			changeString="Unimplemented Change Type";
			break;
		}

		for(Vector<Object> line:encode(change)) {
			line.setElementAt(changeString+" ("+line.get(0)+")",0);
			dataVector.add(line);
		}		
	}


	private Vector<Vector<Object>> encode(VitruviusChange vChange) {
		try {
			Vector<Vector<Object>> lines=new Vector<Vector<Object>>();		
			for(EChange eChange:vChange.getEChanges()) {
				for(Vector<Object> line:encode(eChange)) {					
					line.insertElementAt(vChange.hashCode()+"/"+ModelHelper.getRootObjectName(vChange)+"/"+ModelHelper.getRootObjectID(vChange),1);					
					lines.add(line);
				}
			}
			return lines;
		}catch(Exception ex){
			ex.printStackTrace();
			return new Vector<Vector<Object>>();
		}
	}

	private Vector<Vector<Object>> encode(EChange eChange) {
		try {
			ModelHelper.inspectEChangeClass(eChange.getClass());
			Vector<Vector<Object>> lines=new Vector<Vector<Object>>();		
			//##			 eChange.eClass().getEStructuralFeatures()
			//##			eChange.eGet(EobjectPackage.Literals.EOBJECT_ADDED_ECHANGE__NEW_VALUE)
			for(EObject eObject:eChange.getInvolvedEObjects()) {
				Vector<Object> line = encode(eObject);								
				line.insertElementAt(eChange.hashCode()+"/"+eChange.getClass().getSimpleName(),0);
				line.insertElementAt(ModelHelper.getModel(eObject),0);
				lines.add(line);
			}
			return lines;
		}catch(Exception ex){
			ex.printStackTrace();
			return new Vector<Vector<Object>>();
		}
	}

	private Vector<Object> encode(EObject eObject) {
		Vector<Object> line=new Vector<Object>();
		//line.add(getModel(eObject));
		line.add(eObject.hashCode()+"");
		line.add(eObject.getClass().toString());		
		line.add(ModelHelper.getName(eObject));
		line.add(ModelHelper.getOldValue(eObject));
		line.add(ModelHelper.getNewValue(eObject));
		return line;
	}

	//################### End Data extraction for table

	@Override
	public Object getData() {
		return dataVector;
	}

}
