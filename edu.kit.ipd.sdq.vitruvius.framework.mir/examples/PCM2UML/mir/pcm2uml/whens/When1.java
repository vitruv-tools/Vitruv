package mir.pcm2uml.whens;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class When1 {
  public Boolean apply(final OperationInterface oi, final OperationSignature ois) {
    return Boolean.valueOf("repo.repository.OperationInterface, repo.repository.OperationSignature".isEmpty());
  }
  
  private When1() {
    
  }
  
  public static When1 INSTANCE = new When1();
}
