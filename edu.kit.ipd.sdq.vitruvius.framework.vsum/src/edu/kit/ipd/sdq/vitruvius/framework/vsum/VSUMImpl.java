package edu.kit.ipd.sdq.vitruvius.framework.vsum;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceMMProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ViewTypeManaging;
import edu.kit.ipd.sdq.vitruvius.framework.correspmmprovider.CorrespondenceMMProviderImpl;
import edu.kit.ipd.sdq.vitruvius.framework.design.metamodelmanager.MetamodelManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.design.mir.manager.MIRManager;
import edu.kit.ipd.sdq.vitruvius.framework.design.viewtype.manager.ViewTypeManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;

public class VSUMImpl implements ModelProviding, CorrespondenceProviding {
    private final MappingManaging mappingManaging;
    private final MetamodelManaging metamodelManaging;
    private final ViewTypeManaging viewTypeManaging;
    private final CorrespondenceMMProviding correspondenceMMproviding;

    public VSUMImpl(MetamodelManaging metamodelManaging, ViewTypeManaging viewTypeManaging,
            MappingManaging mappingManaging, CorrespondenceMMProviding correspondenceMMproviding) {
        this.metamodelManaging = metamodelManaging;
        this.viewTypeManaging = viewTypeManaging;
        this.mappingManaging = mappingManaging;
        this.correspondenceMMproviding = correspondenceMMproviding;
    }

    @Override
    public ModelInstance getModelInstanceCopy(VURI uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    /**
     * 
     */
    public ModelInstance getModelInstanceOriginal(VURI uri) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public CorrespondenceInstance getCorrespondenceInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
