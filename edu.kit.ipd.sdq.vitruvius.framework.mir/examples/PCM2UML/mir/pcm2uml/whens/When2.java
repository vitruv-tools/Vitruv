package mir.pcm2uml.whens;

import de.uka.ipd.sdq.pcm.repository.DataType;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class When2 {
  public Boolean apply(final OperationInterface oi, final OperationSignature ois, final DataType oisrt) {
    return Boolean.valueOf("repo.repository.OperationInterface, repo.repository.OperationSignature, repo.repository.DataType".isEmpty());
  }
  
  private When2() {
    
  }
  
  public static When2 INSTANCE = new When2();
}
