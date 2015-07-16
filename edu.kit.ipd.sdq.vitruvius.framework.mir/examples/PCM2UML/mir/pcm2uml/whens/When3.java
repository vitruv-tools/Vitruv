package mir.pcm2uml.whens;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;

@SuppressWarnings("all")
public class When3 {
  public Boolean apply(final OperationInterface oi, final OperationSignature ois, final Parameter oisp) {
    return Boolean.valueOf("repo.repository.OperationInterface, repo.repository.OperationSignature, repo.repository.Parameter".isEmpty());
  }
  
  private When3() {
    
  }
  
  public static When3 INSTANCE = new When3();
}
