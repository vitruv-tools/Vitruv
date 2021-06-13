package tools.vitruv.variability.vave;

import java.io.IOException;
import java.nio.file.Path;

public interface VirtualVaVeModel {

	public VirtualProductModel externalizeProduct(Path storageFolder, String configuration) throws Exception;

	public void internalizeChanges(VirtualProductModel virtualModel) throws IOException;

	public void init(Path storageFolder) throws IOException;

}
