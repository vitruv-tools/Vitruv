package tools.vitruvius.applications.jmljava.initializer;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

/**
 * State provider for this plugin. It is used by the Eclipse framework to determine visibility of
 * menu items for instance.
 * 
 * @author Stephan Seifermann
 *
 */
public class CommandState extends AbstractSourceProvider {

    /**
     * Enum of all supported commands by this state provider.
     * 
     * @author Stephan Seifermann
     *
     */
    public static enum Commands {
        ACTIVATE("tools.vitruvius.applications.jmljava.initializer.command.activate.active");

        private final String stateVariableName;

        /**
         * Constructor.
         * 
         * @param stateVariableName
         *            The ID of the command.
         */
        private Commands(String stateVariableName) {
            this.stateVariableName = stateVariableName;
        }

        /**
         * @return The ID of the command.
         */
        private String getId() {
            return stateVariableName;
        }
    }

    private static final Map<String, String> STATE = createStateMap();
    private static CommandState instance;

    /**
     * Creates the initial state map and fills it with the supported state variables. The initial
     * value is TRUE for all items.
     * 
     * @return The state map.
     */
    private static Map<String, String> createStateMap() {
        Map<String, String> stateMap = new HashMap<String, String>();
        for (Commands cmd : Commands.values()) {
            stateMap.put(cmd.getId(), "TRUE");
        }
        return stateMap;
    }

    /**
     * Constructor
     */
    public CommandState() {
        instance = this;
    }

    @Override
    public void dispose() {
    }

    @Override
    public Map<String, String> getCurrentState() {
        return STATE;
    }

    @Override
    public String[] getProvidedSourceNames() {
        return STATE.keySet().toArray(new String[0]);
    }

    /**
     * Changes the value of a state variable.
     * 
     * @param cmd
     *            The command for which the state variable shall be changed.
     * @param value
     *            The new value.
     */
    public static void changeState(Commands cmd, String value) {
        STATE.put(cmd.getId(), value);
        if (instance != null) {
            instance.fireSourceChanged(ISources.WORKBENCH, cmd.getId(), value);
        }
    }

}
