package mir.pcm2uml.wheres;

import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;

@SuppressWarnings("all")
public class Where1 {
  public void apply(final Interface ui, final Operation uioo) {
    "uml.Interface, uml.Operation".isEmpty();
  }
  
  private Where1() {
    
  }
  
  public static Where1 INSTANCE = new Where1();
}
