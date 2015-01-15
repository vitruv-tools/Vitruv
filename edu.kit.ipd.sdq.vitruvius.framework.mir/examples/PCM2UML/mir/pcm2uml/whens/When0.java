package mir.pcm2uml.whens;

import de.uka.ipd.sdq.pcm.repository.OperationInterface;

@SuppressWarnings("all")
public class When0 {
  public Boolean apply(final OperationInterface oi) {
    return Boolean.valueOf("repo.repository.OperationInterface".isEmpty());
  }
  
  private When0() {
    
  }
  
  public static When0 INSTANCE = new When0();
}
