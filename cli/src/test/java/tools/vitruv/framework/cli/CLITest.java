package tools.vitruv.framework.cli;

import org.junit.jupiter.api.Test;

public class CLITest {

  @Test
  public void test() {
    CLI.main(new String[] { "-m",
        "src/test/resources/model/model.ecore,src/test/resources/model/model.genmodel;src/test/resources/model/model2.ecore,src/test/resources/model/model2.genmodel",
        "-f", "target/internal/",
        "-u", "default",
        "r", "src/test/resources/consistency/templateReactions.reactions" });
  }

}
