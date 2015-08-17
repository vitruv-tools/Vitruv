package edu.kit.ipd.sdq.vitruvius.framework.mir.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractDelegatingCorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper;

public class MappedCorrespondenceInstance extends AbstractDelegatingCorrespondenceInstanceDecorator<Map<String,Map<String,String>>> {
	private final Map<SameTypeCorrespondence,MIRMappingRealization> correspondence2MappingMap;
 
	public MappedCorrespondenceInstance(CorrespondenceInstanceDecorator correspondenceInstance) {
		super(correspondenceInstance);
		this.correspondence2MappingMap = new HashMap<SameTypeCorrespondence, MIRMappingRealization>();
	}

	@Override
	protected String getDecoratorFileExtPrefix() {
		return MIRHelper.getCorrespondenceDecoratorFileExtPrefix();
	}
	
	@Override
	protected Map<String, Map<String, String>> getDecoratorObject() {
		// FIXME create map for persistence by traversing correspondence2MappingMap
		// BEGIN MOCKUP
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
		Map<String,String> secondmap = new HashMap<String, String>();
		secondmap.put("tuid2", "mappingID");
		map.put("tuid1", secondmap);
		// END MOCKUP
		return map;
	}

	@Override
	protected void initializeFromDecoratorObject(Map<String, Map<String, String>> object) {
		// FIXME build correspondence2MappingMap from object
		for (Entry<String, Map<String,String>> e : object.entrySet()) {
			for (Entry<String,String> e2 : e.getValue().entrySet()) {
				System.out.println("correspondence for " + e.getKey() + " and " + e2.getKey() + " created by mapping " + e2.getValue());
			}
		}
	}

	@Override
	public void addSameTypeCorrespondence(SameTypeCorrespondence correspondence) {
		super.addSameTypeCorrespondence(correspondence);
		// FIXME store correct mapping realization
		// this.correspondence2MappingMap.put(correspondence, mapping);
	}

}
