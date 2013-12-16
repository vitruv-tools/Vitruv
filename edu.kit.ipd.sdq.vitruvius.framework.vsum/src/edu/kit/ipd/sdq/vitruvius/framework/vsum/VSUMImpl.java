package edu.kit.ipd.sdq.vitruvius.framework.vsum;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceMMProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ViewTypeManaging;


public class VSUMImpl implements ModelProviding {
	private final MappingManaging mappingManaging;
	private final MetamodelManaging metamodelManaging;
	private final ViewTypeManaging viewTypeManaging;
	private final CorrespondenceMMProviding correspondenceMMproviding;

	public VSUMImpl(MetamodelManaging metamodelManaging, ViewTypeManaging viewTypeManaging,
			MappingManaging mappingManaging, CorrespondenceMMProviding correspondenceMMproviding){
		this.metamodelManaging = metamodelManaging;
		this.viewTypeManaging = viewTypeManaging;
		this.mappingManaging = mappingManaging;
		this.correspondenceMMproviding = correspondenceMMproviding;
	}

	@Override
	public ModelInstance getModelInstanceCopy(URI uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelInstance getModelInstanceOriginal(URI uri) {
		// TODO Auto-generated method stub
		return null;
	}	
}
