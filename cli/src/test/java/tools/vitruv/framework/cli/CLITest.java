package tools.vitruv.framework.cli;

import org.junit.jupiter.api.Test;

public class CLITest {

  @Test
  public void test() {
    CLI.parseCLI(new String[]{"-m", "D:/temp/vitruv-cli/Methodologist-Template/model/src/main/ecore/model.ecore,D:/temp/vitruv-cli/Methodologist-Template/model/src/main/ecore/model.genmodel"});
  }
  
}
