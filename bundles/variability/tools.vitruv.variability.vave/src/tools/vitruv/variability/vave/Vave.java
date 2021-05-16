package tools.vitruv.variability.vave;

import java.nio.file.Path;

import tools.vitruv.framework.vsum.VirtualModel;

public interface Vave {

	public VirtualModel externalizeProduct(Path storageFolder);

}
