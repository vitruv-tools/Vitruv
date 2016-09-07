package tools.vitruvius.domains.jml.monitorededitor.changeinjection;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

/**
 * State holding class for the Eclipse framework. This is used to decide whether a menu item should
 * be enabled for instance.
 * 
 * @author Stephan Seifermann
 *
 */
public class CommandState extends AbstractSourceProvider {

    /**
     * An enumeration of all commands, which are represented in the outer state class.
     * 
     * @author Stephan Seifermann
     *
     */
    public static enum Commands {
        JMLREFACTORINGS("tools.vitruvius.casestudies.jml.run.monitorededitor.state.jmlrefactorings");

        private final String id;

        /**
         * Constructor.
         * 
         * @param id
         *            The id of the command.
         */
        private Commands(String id) {
            this.id = id;
        }

        /**
         * @return The id of the command.
         */
        private String getId() {
            return id;
        }
    }

    private static final Map<String, String> STATE = createStateMap();
    private static CommandState instance;

    /**
     * @return The initially created state map. Every state is initially an empty string.
     */
    private static Map<String, String> createStateMap() {
        Map<String, String> state = new HashMap<String, String>();
        for (Commands cmd : Commands.values()) {
            state.put(cmd.getId(), "");
        }
        return state;
    }

    /**
     * Constructor.
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
     * Changes the state for the given command to a new value.
     * 
     * @param cmd
     *            The commands whichs state should be changed.
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
