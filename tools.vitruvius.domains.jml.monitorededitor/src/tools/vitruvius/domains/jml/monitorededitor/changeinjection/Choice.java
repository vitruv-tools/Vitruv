package tools.vitruvius.domains.jml.monitorededitor.changeinjection;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import tools.vitruvius.framework.userinteraction.impl.UserInteractorDialog;


/**
 * Class for user choices.
 */
public class Choice {

    /**
     * Transformer for EObjects, which determines the string representation, which shall be
     * displayed.
     * 
     * @param <T> The type of the the EObjects to be transformed.
     */
    public interface EObjectToString<T extends EObject> {
        
        /**
         * Gets the title from the given object.
         * @param obj The object to be transformed.
         * @return The string title of the object.
         */
        String getTitle(T obj);
    }

    /**
     * Lets the user choose a element, which is returned afterwards.
     * 
     * @param objects The set of objects, which can be selected.
     * @param transformer The transformator, which creates the titles for the objects.
     * @param <T> The type of the elements.
     * @return The selected object.
     */
    public static <T extends EObject> T chooseFromEObjects(List<T> objects, EObjectToString<T> transformer) {
        List<String> keys = new ArrayList<String>();
        for (T object : objects) {
            keys.add(transformer.getTitle(object));
        }

        int selectedIndex = UserInteractorDialog.selectFromMessage(new Shell(Display.getDefault()), true, false,
                "Your selection", keys.toArray(new String[0]));
        if (selectedIndex < 0 || selectedIndex >= objects.size()) {
            return null;
        }

        return objects.get(selectedIndex);
    }

}
