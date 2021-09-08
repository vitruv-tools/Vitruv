package tools.vitruv.variability.vave;

import java.io.IOException;
import java.nio.file.Path;

import tools.vitruv.variability.vave.impl.FeatureModel;
import vavemodel.Configuration;
import vavemodel.Expression;
import vavemodel.FeatureOption;
import vavemodel.System;


public interface VirtualVaVeModel {

	public VirtualProductModel externalizeProduct(Path storageFolder, Configuration configuration) throws Exception;

	public void internalizeChanges(VirtualProductModel virtualModel, Expression<FeatureOption> expression) throws Exception;
	
	public void internalizeDomain(FeatureModel fm) throws Exception;

	public void init(Path storageFolder) throws IOException;
	
	public System getSystem(); 

}
