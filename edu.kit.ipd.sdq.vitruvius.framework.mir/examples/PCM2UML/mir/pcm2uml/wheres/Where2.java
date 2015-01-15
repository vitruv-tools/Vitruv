package mir.pcm2uml.wheres;

import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;

@SuppressWarnings("all")
public class Where2 {
  public void apply(final Interface ui, final Operation uioo) {
    uioo.setBodyCondition(null);
  }
  
  private Where2() {
    
  }
  
  public static Where2 INSTANCE = new Where2();
}
