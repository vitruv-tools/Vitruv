package tools.vitruv.framework.remote.server;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Scanner;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import tools.vitruv.change.correspondence.CorrespondencePackage;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.impl.VitruvRemoteConnection;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.changederivation.DefaultStateBasedChangeResolutionStrategy;
import tools.vitruv.methodologisttemplate.model.model.Component;
import tools.vitruv.methodologisttemplate.model.model.ModelFactory;
import tools.vitruv.methodologisttemplate.model.model.ModelPackage;
import tools.vitruv.methodologisttemplate.model.model.Router;
import tools.vitruv.methodologisttemplate.model.model.System;
import tools.vitruv.methodologisttemplate.model.model2.Model2Package;
import tools.vitruv.methodologisttemplate.model.model2.Root;

/**
 * Interactive model client for the Vitruv REST API server.
 *
 * <p>Handles only model operations (options 1-7) via {@link VitruvRemoteConnection}.
 * All infrastructure operations (commits, branches, versions, merges) must be done
 * via curl against the running server.
 *
 * <p>Usage (server must already be running via {@link ServerMain}):
 * <pre>
 *   mvn exec:java \
 *     -pl remote \
 *     -Dexec.classpathScope=test \
 *     -Dexec.mainClass=tools.vitruv.framework.remote.server.ClientMain \
 *     -Dexec.args="localhost 8080 C:/tmp/vitruv-client-temp"
 * </pre>
 */
public class ClientMain {

    private static VitruvClient vitruvClient;
    private static Path clientTempDir;
    private static Scanner scanner;

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            java.lang.System.err.println("Usage: ClientMain <host> <port> <clientTempDir>");
            java.lang.System.err.println("Example: ClientMain localhost 8080 C:/tmp/vitruv-client-temp");
            java.lang.System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        clientTempDir = Paths.get(args[2]).toAbsolutePath();
        scanner = new Scanner(java.lang.System.in);

        CorrespondencePackage.eINSTANCE.eClass();
        ModelPackage.eINSTANCE.eClass();
        Model2Package.eINSTANCE.eClass();

        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
                .put("*", new XMIResourceFactoryImpl());

        Files.createDirectories(clientTempDir);

        vitruvClient = new VitruvRemoteConnection("http", host, port, clientTempDir);

        java.lang.System.out.println("VITRUVIUS MODEL CLIENT");
        java.lang.System.out.println("Server: http://" + host + ":" + port);
        java.lang.System.out.println("Client temp: " + clientTempDir);
        java.lang.System.out.println("Use curl for commits, branches, versions, and merges.");
        java.lang.System.out.println();

        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            java.lang.System.out.println("\nMODEL OPERATIONS");
            java.lang.System.out.println("  1. Create System model");
            java.lang.System.out.println("  2. View current models");
            java.lang.System.out.println("  3. Add component to system");
            java.lang.System.out.println("  4. Add router to system");
            java.lang.System.out.println("  5. Rename component");
            java.lang.System.out.println("  6. Delete component");
            java.lang.System.out.println("  7. Delete all models");
            java.lang.System.out.println("  0. Exit");
            java.lang.System.out.print("Choose option: ");

            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> createModels();
                    case "2" -> viewModels();
                    case "3" -> addComponent();
                    case "4" -> addRouter();
                    case "5" -> renameComponent();
                    case "6" -> deleteComponent();
                    case "7" -> deleteAllModels();
                    case "0" -> {
                        java.lang.System.out.println("Exiting.");
                        return;
                    }
                    default -> java.lang.System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                java.lang.System.err.println("Error: " + e.getMessage());
                e.printStackTrace(java.lang.System.err);
            }
        }
    }

    private static void createModels() {
        java.lang.System.out.println("\nCreating System model via Vitruv view API...");
        java.lang.System.out.print("Enter server-side model path (e.g. C:/vitruv-test/system.model): ");
        String pathStr = scanner.nextLine().trim();
        if (pathStr.isEmpty()) {
            java.lang.System.out.println("Cancelled.");
            return;
        }

        View rawView = openView(null);
        CommittableView view = rawView.withChangeDerivingTrait(
                new DefaultStateBasedChangeResolutionStrategy());
        System system = ModelFactory.eINSTANCE.createSystem();
        view.registerRoot(system, URI.createFileURI(pathStr));
        view.commitChanges();

        java.lang.System.out.println("System created at " + pathStr);
        java.lang.System.out.println("Reaction should have created example.model2 automatically.");
    }

    private static void viewModels() {
        java.lang.System.out.println("\nCurrent Model State:");

        View view = openView(null);
        Collection<System> systems = view.getRootObjects(System.class);
        Collection<Root> roots = view.getRootObjects(Root.class);

        if (systems.isEmpty() && roots.isEmpty()) {
            java.lang.System.out.println("No models found. Create them first (option 1).");
            return;
        }

        for (System system : systems) {
            String uri = system.eResource() != null ? system.eResource().getURI().lastSegment() : "?";
            java.lang.System.out.println("\nSystem [" + uri + "]:");
            if (system.getComponents().isEmpty()) {
                java.lang.System.out.println("  Components: (none)");
            } else {
                system.getComponents().forEach(c ->
                    java.lang.System.out.println("  - " + c.getName() + " [" + c.eClass().getName() + "]"));
            }
        }

        for (Root root : roots) {
            String uri = root.eResource() != null ? root.eResource().getURI().lastSegment() : "?";
            java.lang.System.out.println("\nRoot [" + uri + "] (created by reaction):");
            if (root.getEntities().isEmpty()) {
                java.lang.System.out.println("  Entities: (none)");
            } else {
                root.getEntities().forEach(e ->
                    java.lang.System.out.println("  - " + e.getName()));
            }
        }
    }

    private static void addComponent() {
        java.lang.System.out.print("\nEnter component name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) return;

        CommittableView view = openView(System.class)
                .withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
        Collection<System> systems = view.getRootObjects(System.class);
        if (systems.isEmpty()) {
            java.lang.System.out.println("No system found. Create models first (option 1).");
            return;
        }

        Component component = ModelFactory.eINSTANCE.createComponent();
        component.setName(name);
        systems.iterator().next().getComponents().add(component);
        view.commitChanges();
        java.lang.System.out.println("Component '" + name + "' added.");
    }

    private static void addRouter() {
        java.lang.System.out.print("\nEnter router name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) return;

        CommittableView view = openView(System.class)
                .withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
        Collection<System> systems = view.getRootObjects(System.class);
        if (systems.isEmpty()) {
            java.lang.System.out.println("No system found. Create models first.");
            return;
        }

        Router router = ModelFactory.eINSTANCE.createRouter();
        router.setName(name);
        systems.iterator().next().getComponents().add(router);
        view.commitChanges();
        java.lang.System.out.println("Router '" + name + "' added.");
    }

    private static void renameComponent() {
        View readView = openView(System.class);
        Collection<System> systems = readView.getRootObjects(System.class);
        if (systems.isEmpty() || systems.iterator().next().getComponents().isEmpty()) {
            java.lang.System.out.println("No components found.");
            return;
        }
        System system = systems.iterator().next();
        java.lang.System.out.println("\nComponents:");
        for (int i = 0; i < system.getComponents().size(); i++) {
            java.lang.System.out.println("  " + (i + 1) + ". " + system.getComponents().get(i).getName());
        }
        java.lang.System.out.print("Select component number: ");
        int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
        java.lang.System.out.print("New name: ");
        String newName = scanner.nextLine().trim();

        CommittableView writeView = openView(System.class)
                .withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
        writeView.getRootObjects(System.class).iterator().next()
                .getComponents().get(index).setName(newName);
        writeView.commitChanges();
        java.lang.System.out.println("Renamed to '" + newName + "'.");
    }

    private static void deleteComponent() {
        View readView = openView(System.class);
        Collection<System> systems = readView.getRootObjects(System.class);
        if (systems.isEmpty() || systems.iterator().next().getComponents().isEmpty()) {
            java.lang.System.out.println("No components found.");
            return;
        }
        System system = systems.iterator().next();
        java.lang.System.out.println("\nComponents:");
        for (int i = 0; i < system.getComponents().size(); i++) {
            java.lang.System.out.println("  " + (i + 1) + ". " + system.getComponents().get(i).getName());
        }
        java.lang.System.out.print("Select component number to delete: ");
        int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
        String name = system.getComponents().get(index).getName();

        CommittableView writeView = openView(System.class)
                .withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
        writeView.getRootObjects(System.class).iterator().next().getComponents().remove(index);
        writeView.commitChanges();
        java.lang.System.out.println("Deleted '" + name + "'.");
    }

    private static void deleteAllModels() {
        java.lang.System.out.print("\nAre you sure? (yes/no): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            java.lang.System.out.println("Cancelled.");
            return;
        }
        CommittableView view1 = openView(System.class)
                .withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
        Collection<System> systems = view1.getRootObjects(System.class);
        if (systems.isEmpty()) {
            java.lang.System.out.println("No models to delete.");
            return;
        }
        systems.forEach(s -> s.getComponents().clear());
        view1.commitChanges();

        CommittableView view2 = openView(System.class)
                .withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
        view2.getRootObjects(System.class).forEach(s ->
                org.eclipse.emf.ecore.util.EcoreUtil.delete(s, true));
        view2.commitChanges();
        java.lang.System.out.println("All models deleted.");
    }

    @SuppressWarnings("unchecked")
    private static View openView(Class<?> rootTypeFilter) {
        Collection<ViewType<?>> types = vitruvClient.getViewTypes();
        if (types.isEmpty()) {
            throw new IllegalStateException(
                "Server returned no view types. Is the server running and configured correctly?");
        }
        ViewType<ViewSelector> defaultType =
            (ViewType<ViewSelector>) types.stream()
                .filter(t -> "default".equals(t.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("View type 'default' not found on server."));

        ViewSelector selector = vitruvClient.createSelector(defaultType);
        selector.getSelectableElements().forEach(e -> {
            boolean include = rootTypeFilter == null || rootTypeFilter.isInstance(e);
            selector.setSelected(e, include);
        });
        return selector.createView();
    }
}
