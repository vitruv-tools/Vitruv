package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.ChangeHistory;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.AddFieldEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.AddMethodEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeMethodSignatureEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.CreateClassEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.CreateInterfaceEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.CreateTypeEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.DeleteClassEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.DeleteInterfaceEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.DeleteTypeEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.MoveMethodEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RemoveFieldEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RemoveMethodEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RenameClassEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RenameFieldEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RenameInterfaceEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RenameTypeEvent;

/**
 * @author messinger
 * 
 *         Gets a list of {@link ChangeClassifyingEvent}s and removes wrong events, adds optionally
 *         new events. Currently implemented:
 *         <ul>
 *         <li>Delete CreateType and DeleteType if covered by RenameType (Type = Class or Interface)
 *         </li>
 *         <li>Delete AddMethod and RemoveMethod if covered by RenameMethod</li>
 *         <li>Delete AddField and RemoveField if covered by RenameField</li>
 *         <li>Replace AddMethod with MoveMethod if fitting DeleteMethod was found in history of
 *         withheld changes</li>
 *         <li></li>
 *         </ul>
 * 
 */
public class HigherOperationEventsFilter {

    private static ASTMatcher astMatcher = new ASTMatcher();

    @SuppressWarnings("unchecked")
	public static List<ChangeClassifyingEvent> filter(List<ChangeClassifyingEvent> events, ChangeHistory changeHistory) {

        Map<Class<?>, List<ChangeClassifyingEvent>> sortedEvents = sortEventsByClassification(events);
        List<ChangeMethodSignatureEvent> changeSignatures = (List<ChangeMethodSignatureEvent>) getAllEventsOfType(
                events, ChangeMethodSignatureEvent.class);
        deleteAddMethodRemoveMethodIfChangeMethodSignature(sortedEvents, changeSignatures);
        addMoveMethodIfRemoveMethodInWithheldHistory(sortedEvents, changeHistory);
        deleteCreateInterfaceDeleteInterfaceIfRenamedInterface(sortedEvents);
        deleteAddClassRemoveClassIfRenamedClass(sortedEvents);
        deleteAddFieldRemoveFieldIfRenamedField(sortedEvents);

        return flatten(sortedEvents.values());
    }

    private static void deleteAddFieldRemoveFieldIfRenamedField(Map<Class<?>, List<ChangeClassifyingEvent>> sortedEvents) {
        List<ChangeClassifyingEvent> addFields = sortedEvents.get(AddFieldEvent.class);
        List<ChangeClassifyingEvent> removeFields = sortedEvents.get(RemoveFieldEvent.class);
        List<ChangeClassifyingEvent> renameFields = sortedEvents.get(RenameFieldEvent.class);

        if (addFields == null || removeFields == null || renameFields == null)
            return;

        ListIterator<ChangeClassifyingEvent> renameIt = renameFields.listIterator();
        while (renameIt.hasNext()) {
            RenameFieldEvent renameEvent = ((RenameFieldEvent) renameIt.next());
            ListIterator<ChangeClassifyingEvent> removeField = removeFields.listIterator();
            ListIterator<ChangeClassifyingEvent> addField = addFields.listIterator();
            AddFieldEvent add = null;
            while (addField.hasNext()) {
                add = (AddFieldEvent) addField.next();
                if (renameEvent.changed != add.field) {
                    add = null;
                } else
                    break;
            }
            RemoveFieldEvent remove = null;
            while (removeField.hasNext()) {
                remove = (RemoveFieldEvent) removeField.next();
                if (renameEvent.original != remove.field) {
                    remove = null;
                } else
                    break;
            }
            if (add != null && remove != null) {
                addField.remove();
                removeField.remove();
            }
        }
    }

    private static void deleteCreateInterfaceDeleteInterfaceIfRenamedInterface(
            Map<Class<?>, List<ChangeClassifyingEvent>> sortedEvents) {
        List<ChangeClassifyingEvent> createInterfaces = sortedEvents.get(CreateInterfaceEvent.class);
        List<ChangeClassifyingEvent> deleteInterfaces = sortedEvents.get(DeleteInterfaceEvent.class);
        List<ChangeClassifyingEvent> renameInterfaces = sortedEvents.get(RenameInterfaceEvent.class);

        if (createInterfaces == null || deleteInterfaces == null || renameInterfaces == null)
            return;

        deleteCreateTypeDeleteTypeIfRenameType(createInterfaces, deleteInterfaces, renameInterfaces);

    }

    private static void deleteAddClassRemoveClassIfRenamedClass(Map<Class<?>, List<ChangeClassifyingEvent>> sortedEvents) {
        List<ChangeClassifyingEvent> addClasses = sortedEvents.get(CreateClassEvent.class);
        List<ChangeClassifyingEvent> removeClasses = sortedEvents.get(DeleteClassEvent.class);
        List<ChangeClassifyingEvent> renameClasses = sortedEvents.get(RenameClassEvent.class);

        if (addClasses == null || removeClasses == null || renameClasses == null)
            return;

        deleteCreateTypeDeleteTypeIfRenameType(addClasses, removeClasses, renameClasses);

    }

    private static void deleteCreateTypeDeleteTypeIfRenameType(List<ChangeClassifyingEvent> createTypes,
            List<ChangeClassifyingEvent> deleteTypes, List<ChangeClassifyingEvent> renameTypes) {
        ListIterator<ChangeClassifyingEvent> renameIt = renameTypes.listIterator();
        while (renameIt.hasNext()) {
            RenameTypeEvent renameEvent = ((RenameTypeEvent) renameIt.next());
            ListIterator<ChangeClassifyingEvent> deleteType = deleteTypes.listIterator();
            ListIterator<ChangeClassifyingEvent> createType = createTypes.listIterator();
            CreateTypeEvent create = null;
            while (createType.hasNext()) {
                create = (CreateTypeEvent) createType.next();
                if (renameEvent.renamed != create.type) {
                    create = null;
                } else
                    break;
            }
            DeleteTypeEvent remove = null;
            while (deleteType.hasNext()) {
                remove = (DeleteTypeEvent) deleteType.next();
                if (renameEvent.original != remove.type) {
                    remove = null;
                } else
                    break;
            }
            if (create != null && remove != null) {
                createType.remove();
                deleteType.remove();
            }
        }
    }

    @SuppressWarnings("unchecked")
	private static void addMoveMethodIfRemoveMethodInWithheldHistory(
            Map<Class<?>, List<ChangeClassifyingEvent>> sortedEvents, ChangeHistory changeHistory) {
        List<ChangeClassifyingEvent> addMethods = sortedEvents.get(AddMethodEvent.class);
        List<RemoveMethodEvent> oldRemoveMethods = (List<RemoveMethodEvent>) getAllEventsOfType(
                changeHistory.getEvents(), RemoveMethodEvent.class);

        if (addMethods == null || oldRemoveMethods == null)
            return;

        ListIterator<ChangeClassifyingEvent> addMethodListIterator = addMethods.listIterator();
        Class<?> moveClass = MoveMethodEvent.class;
        if (!sortedEvents.containsKey(moveClass)) {
            sortedEvents.put(moveClass, new ArrayList<ChangeClassifyingEvent>());
        }
        List<ChangeClassifyingEvent> moveMethods = sortedEvents.get(moveClass);
        while (addMethodListIterator.hasNext()) {
            ChangeClassifyingEvent event = addMethodListIterator.next();
            if (!(event instanceof AddMethodEvent))
                continue;
            AddMethodEvent add = (AddMethodEvent) event;
            ListIterator<RemoveMethodEvent> oldRemoveListIterator = oldRemoveMethods.listIterator();
            while (oldRemoveListIterator.hasNext()) {
                RemoveMethodEvent oldRemove = oldRemoveListIterator.next();
                if (corresponds(add.method, oldRemove.method)) {
                    oldRemoveListIterator.remove();
                    changeHistory.getEvents().remove(oldRemove);
                    addMethodListIterator.remove();

                    moveMethods.add(new MoveMethodEvent(oldRemove.typeAfterRemove, add.typeBeforeAdd, oldRemove.method,
                            add.method));
                }
            }
        }

    }

    private static boolean corresponds(MethodDeclaration method1, MethodDeclaration method2) {
        boolean subtreeMatch = method1.subtreeMatch(astMatcher, method2);
        return subtreeMatch;
    }

    private static <T> List<T> flatten(Collection<List<T>> lists) {
        List<T> result = new ArrayList<T>();
        for (List<T> l : lists)
            result.addAll(l);
        return result;
    }

    // a generic instanceof <Class> would be nice, Java..., or lambda
    // expressions...
    @SuppressWarnings("unchecked")
	private static <T extends ChangeClassifyingEvent> List<? extends ChangeClassifyingEvent> getAllEventsOfType(
            List<ChangeClassifyingEvent> events, Class<?> type) {
        List<T> filteredEvents = new ArrayList<T>(events.size());
        for (ChangeClassifyingEvent event : events) {
            if (type.isInstance(event)) {
                filteredEvents.add((T) event);
            }
        }
        return filteredEvents;
    }

    private static void deleteAddMethodRemoveMethodIfChangeMethodSignature(
            Map<Class<?>, List<ChangeClassifyingEvent>> sortedEvents,
            List<ChangeMethodSignatureEvent> changeMethodSignatureEvents) {
        for (ChangeMethodSignatureEvent signatureChange : changeMethodSignatureEvents) {
            List<ChangeClassifyingEvent> addMethods = sortedEvents.get(AddMethodEvent.class);
            List<ChangeClassifyingEvent> removeMethods = sortedEvents.get(RemoveMethodEvent.class);

            AddMethodEvent addMethod = null;
            if (addMethods != null) {
                for (ChangeClassifyingEvent add : addMethods) {
                    if (((AddMethodEvent) add).method == signatureChange.renamed) {
                        addMethod = (AddMethodEvent) add;
                        break;
                    }
                }
            }

            RemoveMethodEvent removeMethod = null;
            if (removeMethods != null) {
                for (ChangeClassifyingEvent remove : removeMethods) {
                    if (((RemoveMethodEvent) remove).method == signatureChange.original) {
                        removeMethod = (RemoveMethodEvent) remove;
                        break;
                    }
                }
            }

            if (addMethod != null && removeMethod != null) {
                addMethods.remove(addMethod);
                removeMethods.remove(removeMethod);
            }
        }
    }

    private static Map<Class<?>, List<ChangeClassifyingEvent>> sortEventsByClassification(
            List<ChangeClassifyingEvent> events) {
        Map<Class<?>, List<ChangeClassifyingEvent>> map = new HashMap<Class<?>, List<ChangeClassifyingEvent>>();
        for (ChangeClassifyingEvent event : events) {
            Class<?> cls = event.getClass();
            if (!map.containsKey(cls)) {
                map.put(cls, new ArrayList<ChangeClassifyingEvent>());
            }
            map.get(cls).add(event);
        }
        return map;
    }

}
