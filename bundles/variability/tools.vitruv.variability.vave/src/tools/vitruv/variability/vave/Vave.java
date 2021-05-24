package tools.vitruv.variability.vave;

import java.nio.file.Path;

public interface Vave {

	public VirtualModelProduct externalizeProduct(Path storageFolder, String configuration) throws Exception;

	public void internalizeChanges(VirtualModelProduct virtualModel);

}
