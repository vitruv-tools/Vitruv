package tools.vitruv.variability.vave;

import java.io.IOException;
import java.nio.file.Path;

import vavemodel.Configuration;

public interface VirtualVaVeModel {

	public VirtualProductModel externalizeProduct(Path storageFolder, Configuration configuration) throws Exception;

	public void internalizeChanges(VirtualProductModel virtualModel) throws IOException;

	public void init(Path storageFolder) throws IOException;

}
