package edu.kit.ipd.sdq.vitruvius.framework.metarepository;

import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.MetamodelsReferring;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ViewType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ViewTypeManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableConcatHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableConcatMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;

public class MetaRepositoryImpl implements MetamodelManaging, ViewTypeManaging, MappingManaging {
	
	private ClaimableMap<URI, Metamodel> uri2MetamodelMap;
	private ClaimableMap<URI, ViewType> uri2ViewTypeMap;
	private ClaimableConcatMap<URI, Mapping> uris2MappingMap;

	public MetaRepositoryImpl() {
		this.uri2MetamodelMap = new ClaimableHashMap<URI, Metamodel>();
		this.uri2ViewTypeMap = new ClaimableHashMap<URI, ViewType>();
		this.uris2MappingMap = new ClaimableConcatHashMap<URI, Mapping>();
	}

	@Override
	public void addMetamodel(Metamodel metamodel) {
		URI uri = metamodel.getURI();
		this.uri2MetamodelMap.putClaimingNullOrSameMapped(uri, metamodel);
	}

	@Override
	public Metamodel getMetamodel(URI uri) {
		return this.uri2MetamodelMap.get(uri);
	}
	
	@Override
	public void addViewType(ViewType viewType) {
		claimReferredMetamodels(viewType);
		URI viewTypeURI = viewType.getURI();
		this.uri2ViewTypeMap.putClaimingNullOrSameMapped(viewTypeURI, viewType);
	}
	
	private void claimReferredMetamodels(MetamodelsReferring metamodelsReferring) {
		Set<URI> metamodelURIs = metamodelsReferring.getMetamodelURIs();
		for (URI metamodelURI : metamodelURIs) {
			this.uri2MetamodelMap.claimKeyIsMapped(metamodelURI);
		}
	}

	@Override
	public ViewType getViewType(URI uri) {
		return this.uri2ViewTypeMap.get(uri);
	}

	@Override
	public void addMapping(Mapping mapping) {
		claimReferredMetamodels(mapping);
		URI[] metamodelURIs = (URI[]) mapping.getMetamodelURIs().toArray();
		this.uris2MappingMap.putClaimingNullOrSameMapped(mapping, metamodelURIs);
	}

	@Override
	public Mapping getMapping(URI... metamodelURIs) {
		for (URI metamodelURI : metamodelURIs) {
			this.uri2MetamodelMap.claimKeyIsMapped(metamodelURI);
		}
		return this.uris2MappingMap.claimValueForKeys(metamodelURIs);
	}


}
