package mir.pcm2uml.whens;

import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class When2 {
  public Boolean apply(final OperationInterface oi, final OperationSignature ois, final DataType oisrt) {
    return Boolean.valueOf("repo.repository.OperationInterface, repo.repository.OperationSignature, repo.repository.DataType".isEmpty());
  }
  
  private When2() {
    
  }
  
  public static When2 INSTANCE = new When2();
}
