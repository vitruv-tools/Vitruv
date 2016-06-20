package edu.kit.ipd.sdq.vitruvius.codeintegration.change2command;

import java.util.List;
import org.eclipse.emf.common.command.Command;

@SuppressWarnings("all")
public class IntegrationChange2CommandResult {
  private List<? extends Command> commands;
  
  public IntegrationChange2CommandResult(final List<? extends Command> commands) {
    this.commands = commands;
  }
  
  public boolean isIntegrationChange() {
    int _size = this.commands.size();
    return (_size > 0);
  }
  
  public List<? extends Command> getCommands() {
    return this.commands;
  }
}
