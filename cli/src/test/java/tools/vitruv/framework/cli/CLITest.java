package tools.vitruv.framework.cli;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CLITest {

  // @Test
  // public void methodologistTemplateTest() {
  //   CLI.main(
  //       new String[] {
  //           "-m",  "src/test/resources/model/model.ecore,src/test/resources/model/model.genmodel;src/test/resources/model/model2.ecore,src/test/resources/model/model2.genmodel",
  //           "-f",  "target/internal/",
  //           "-u", "default",
  //           "-r", "src/test/resources/consistency/templateReactions.reactions"
  //       });
  // }

  /**
   * Test is an example usage that executes the server, i.e., it starts the server and keeps it running until it is closed, therefore it is not suited for a test.
   */
  // @Disabled
  @Test
  public void test() {
    CLI.main(
        new String[] {
            "-m",  "src/test/resources/model/model.ecore,src/test/resources/model/model.genmodel;src/test/resources/model/model2.ecore,src/test/resources/model/model2.genmodel",
            "-f",  "target/internal/",
            "-u", "default",
            "-r", "src/test/resources/consistency/templateReactions.reactions",
            "-s", "50897"
        });
  }
}
