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
 * @author Andreas Löffler
 *
 */
public class TableChangeDataSet extends ChangeDataSet {

	/**
	 * @param cdsId
	 * @param className
	 * @param testName
	 * @param propagationResult
	 */
	public TableChangeDataSet(String cdsId, String className, String testName,
			List<PropagatedChange> propagationResult) {
		super(cdsId, className, testName, propagationResult);
	}
	
	private Vector<Vector> dataVector;
	
	protected void extractData(List<PropagatedChange> propagationResult) {
		dataVector = new Vector<Vector>();
		
		for(PropagatedChange propChange:propagationResult) {			
			encode(propChange);						
		}
	}
	
	
	private void encode(PropagatedChange propChange) {
		VitruviusChange origChange = propChange.getOriginalChange();					
		encode(origChange,ChangeType.ORIGINAL_CHANGE);			
		
		VitruviusChange consequentialChanges=propChange.getConsequentialChanges();		
		encode(origChange,ChangeType.CONSEQUENTIAL_CHANGE);
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
		
		for(Vector line:encode(change)) {
			line.setElementAt(changeString+" ("+line.get(0)+")",0);
			dataVector.add(line);
		}		
	}


	private Vector<Vector> encode(VitruviusChange vChange) {
		try {
			Vector<Vector> lines=new Vector<Vector>();		
			for(EChange eChange:vChange.getEChanges()) {
				for(Vector line:encode(eChange)) {					
					line.insertElementAt(vChange.hashCode()+"/"+ModelHelper.getRootObjectName(vChange)+"/"+ModelHelper.getRootObjectID(vChange),1);					
					lines.add(line);
				}
			}
			return lines;
		}catch(Exception ex){
			ex.printStackTrace();
			return new Vector<Vector>();
		}
	}

	private Vector<Vector> encode(EChange eChange) {
		try {
			ModelHelper.inspectEChangeClass(eChange.getClass());
			Vector<Vector> lines=new Vector<Vector>();		
//##			 eChange.eClass().getEStructuralFeatures()
//##			eChange.eGet(EobjectPackage.Literals.EOBJECT_ADDED_ECHANGE__NEW_VALUE)
			for(EObject eObject:eChange.getInvolvedEObjects()) {
				Vector line = encode(eObject);								
				line.insertElementAt(eChange.hashCode()+"/"+eChange.getClass().getSimpleName(),0);
				line.insertElementAt(ModelHelper.getModel(eObject),0);
				lines.add(line);
			}
			return lines;
		}catch(Exception ex){
			ex.printStackTrace();
			return new Vector<Vector>();
		}
	}

	private Vector encode(EObject eObject) {
		Vector line=new Vector();
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
