package edu.kit.ipd.sdq.vitruvius.framework.metarepository;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.MetamodelsReferring;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProjectInput;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ViewType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ProjectPreparing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ViewTypeManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableConcatHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableConcatMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;

public class MetaRepositoryImpl implements MetamodelManaging, ViewTypeManaging, MappingManaging, ProjectPreparing {
	
	private ClaimableMap<VURI, Metamodel> uri2MetamodelMap;
	private ClaimableMap<VURI, ViewType> uri2ViewTypeMap;
	private ClaimableConcatMap<VURI, Mapping> uris2MappingMap;

	public MetaRepositoryImpl() {
		this.uri2MetamodelMap = new ClaimableHashMap<VURI, Metamodel>();
		this.uri2ViewTypeMap = new ClaimableHashMap<VURI, ViewType>();
		this.uris2MappingMap = new ClaimableConcatHashMap<VURI, Mapping>();
	}

	@Override
	public void addMetamodel(Metamodel metamodel) {
		VURI uri = metamodel.getURI();
		this.uri2MetamodelMap.putClaimingNullOrSameMapped(uri, metamodel);
	}

	@Override
	public Metamodel getMetamodel(VURI uri) {
		return this.uri2MetamodelMap.get(uri);
	}
	
	@Override
	public void addViewType(ViewType viewType) {
		claimReferredMetamodels(viewType);
		VURI viewTypeURI = viewType.getURI();
		this.uri2ViewTypeMap.putClaimingNullOrSameMapped(viewTypeURI, viewType);
	}
	
	private void claimReferredMetamodels(MetamodelsReferring metamodelsReferring) {
		VURI[] metamodelURIs = metamodelsReferring.getMetamodelURIs();
		for (VURI metamodelURI : metamodelURIs) {
			this.uri2MetamodelMap.claimKeyIsMapped(metamodelURI);
		}
	}

	@Override
	public ViewType getViewType(VURI uri) {
		return this.uri2ViewTypeMap.get(uri);
	}

	@Override
	public void addMapping(Mapping mapping) {
		claimReferredMetamodels(mapping);
		VURI[] metamodelURIs = mapping.getMetamodelURIs();
		this.uris2MappingMap.putClaimingNullOrSameMapped(mapping, metamodelURIs);
	}

	@Override
	public Mapping getMapping(VURI... metamodelURIs) {
		for (VURI metamodelURI : metamodelURIs) {
			this.uri2MetamodelMap.claimKeyIsMapped(metamodelURI);
		}
		return this.uris2MappingMap.claimValueForKeys(metamodelURIs);
	}

	@Override
	public ProjectInput getProjectInput() {
		// TODO Auto-generated method stub
		return null;
	}
}
