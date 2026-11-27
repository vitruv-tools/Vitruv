package tools.vitruv.methodologisttemplate.vsum;
import tools.vitruv.framework.vsum.branch.BranchAwareVirtualModel;
import mir.reactions.model2Model2.Model2Model2ChangePropagationSpecification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.branch.handler.VsumMergeWatcher;
import tools.vitruv.framework.vsum.branch.handler.VsumPostCommitWatcher;
import tools.vitruv.framework.vsum.branch.handler.VsumReloadWatcher;
import tools.vitruv.framework.vsum.branch.handler.VsumValidationWatcher;
import tools.vitruv.framework.vsum.branch.util.GitHookInstaller;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.methodologisttemplate.model.model.Component;
import tools.vitruv.methodologisttemplate.model.model.ModelFactory;
import tools.vitruv.methodologisttemplate.model.model.Router;
import tools.vitruv.methodologisttemplate.model.model.System;
import tools.vitruv.methodologisttemplate.model.model2.Root;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive manual test for Vitruvius branching functionality.
 * Demonstrates proper model creation, modification, and deletion via Vitruv API with Git branching integration.
 */
public class ManualTest {

    private static BranchAwareVirtualModel virtualModel;
    private static Path repoRoot;
    private static Scanner scanner;

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            java.lang.System.err.println("Usage: ManualTest <repository-path>");
            java.lang.System.err.println("Example: ManualTest C:/Users/user/vitruvius-manual-test");
            java.lang.System.exit(1);
        }
        repoRoot = Paths.get(args[0]).toAbsolutePath();
        scanner = new Scanner(java.lang.System.in);
        java.lang.System.out.println("VITRUVIUS BRANCHING - INTERACTIVE MANUAL TEST ");
        java.lang.System.out.println();
        java.lang.System.out.println("Repository: " + repoRoot);
        java.lang.System.out.println();

        // Register shutdown hook for Ctrl+C
        // ensures V-SUM is disposed cleanly
        // even if the user kills the process instead of using option 9
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (virtualModel != null) {
                try {
                    virtualModel.dispose();
                    java.lang.System.out.println("\nV-SUM disposed cleanly on shutdown.");
                } catch (Exception e) {
                    java.lang.System.err.println("Failed to dispose V-SUM on shutdown: " + e.getMessage());
                }
            }
        }));

        try {
            runInteractiveTest();
        } catch (Exception e) {
            java.lang.System.err.println("\nError: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runInteractiveTest() throws Exception {
        // Step 0: Setup
        java.lang.System.out.println("[0/4] Setting up test environment...");
        // Step 1: Initialize VSUM
        java.lang.System.out.println("\n[1/4] Initializing Vitruvius VSUM...");
        initializeVsum();
        // Step 2: Install Git hooks
        java.lang.System.out.println("\n[2/4] Installing Git hooks...");
        installGitHooks();
        // Step 3: Start background watchers
        java.lang.System.out.println("\n[3/4] Starting background watchers...");
        startBackgroundWatchers();
        // Step 4: Interactive menu
        java.lang.System.out.println("\n[4/4] VSUM initialized successfully.");
        java.lang.System.out.println("You can now perform Git operations and model changes.");
        java.lang.System.out.println();
        showMainMenu();
    }


    private static void showMainMenu() {
        while (true) {
            java.lang.System.out.println("\n");
            java.lang.System.out.println("MAIN MENU");
            java.lang.System.out.println("1. Create models (via Vitruv API)");
            java.lang.System.out.println("2. View current models");
            java.lang.System.out.println("3. Add component to system");
            java.lang.System.out.println("4. Add router to system");
            java.lang.System.out.println("5. Rename component");
            java.lang.System.out.println("6. Delete component");
            java.lang.System.out.println("7. Delete all models");
            java.lang.System.out.println("8. Exit");
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
                    case "8" -> {
                        java.lang.System.out.println("Exiting...");
                        virtualModel.dispose(); //flush vsum state cleanly
                        return;
                    }
                    default -> java.lang.System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                java.lang.System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void createModels() {
        java.lang.System.out.println("\nCreating Models via Vitruv API");
        View view = getDefaultView(virtualModel, List.of(System.class, Root.class));
        // Check if models already exist
        if (!view.getRootObjects(System.class).isEmpty()) {
            java.lang.System.out.println("Models already exist. Delete them first (option 7).");
            return;
        }
        CommittableView committableView = view.withChangeDerivingTrait(); // state-based delta derivation
        // Create System model
        System system = ModelFactory.eINSTANCE.createSystem();
        committableView.registerRoot(system, URI.createFileURI(repoRoot.resolve("example.model").toString()));
        committableView.commitChanges();
        java.lang.System.out.println("Models created successfully.");
        java.lang.System.out.println("- example.model (System)");
        java.lang.System.out.println("- example.model2 (Root - created by reactions)");
    }

    private static void viewModels() {
        java.lang.System.out.println("\nCurrent Models");
        View view = getDefaultView(virtualModel, List.of(System.class, Root.class));
        // View System models
        Collection<System> systems = view.getRootObjects(System.class);
        if (systems.isEmpty()) {
            java.lang.System.out.println("No models found. Create them first.");
            return;
        }

        for (System system : systems) {
            java.lang.System.out.println("\nSystem (example.model)");
            java.lang.System.out.println("Components:");
            if (system.getComponents().isEmpty()) {
                java.lang.System.out.println("(none)");
            } else {
                system.getComponents().forEach(c -> java.lang.System.out.println("- " + c.getName() + " (" + c.eClass().getName() + ")"));
            }
        }
        // View Root models
        Collection<Root> roots = view.getRootObjects(Root.class);
        for (Root root : roots) {
            java.lang.System.out.println("\nRoot (example.model2)");
            java.lang.System.out.println("Entities:");
            if (root.getEntities().isEmpty()) {
                java.lang.System.out.println("(none)");
            } else {
                root.getEntities().forEach(e -> java.lang.System.out.println("- " + e.getName()));
            }
        }
    }

    private static void addComponent() {
        java.lang.System.out.print("\nEnter component name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            java.lang.System.out.println("Name cannot be empty.");
            return;
        }
        CommittableView view = getDefaultView(virtualModel, List.of(System.class)).withChangeDerivingTrait();
        Collection<System> systems = view.getRootObjects(System.class);
        if (systems.isEmpty()) {
            java.lang.System.out.println("No system found. Create models first (option 1).");
            return;
        }
        Component component = ModelFactory.eINSTANCE.createComponent();
        component.setName(name);
        systems.iterator().next().getComponents().add(component);
        view.commitChanges();
        java.lang.System.out.println("Component '" + name + "' added");
    }

    private static void addRouter() {
        java.lang.System.out.print("\nEnter router name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            java.lang.System.out.println("Name cannot be empty.");
            return;
        }
        CommittableView view = getDefaultView(virtualModel, List.of(System.class)).withChangeDerivingTrait();
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
        // Read-only view just for display
        View readView = getDefaultView(virtualModel, List.of(System.class));
        Collection<System> systems = readView.getRootObjects(System.class);
        if (systems.isEmpty() || systems.iterator().next().getComponents().isEmpty()) {
            java.lang.System.out.println("No components found.");
            return;
        }
        // Display components from read view
        System system = systems.iterator().next();
        java.lang.System.out.println("\nComponents:");
        for (int i = 0; i < system.getComponents().size(); i++) {
            java.lang.System.out.println("  " + (i + 1) + ". " + system.getComponents().get(i).getName());
        }
        java.lang.System.out.print("Select component number: ");
        int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
        java.lang.System.out.print("Enter new name: ");
        String newName = scanner.nextLine().trim();
        // Fresh committable view for the modification
        CommittableView committableView = getDefaultView(virtualModel, List.of(System.class)).withChangeDerivingTrait();
        committableView.getRootObjects(System.class).iterator().next().getComponents().get(index).setName(newName);
        committableView.commitChanges();
        java.lang.System.out.println("Renamed to '" + newName + "'");
    }

    private static void deleteComponent() {
        View view = getDefaultView(virtualModel, List.of(System.class));
        Collection<System> systems = view.getRootObjects(System.class);
        if (systems.isEmpty() || systems.iterator().next().getComponents().isEmpty()) {
            java.lang.System.out.println("No components found.");
            return;
        }
        System system = systems.iterator().next();
        java.lang.System.out.println("\nComponents:");
        for (int i = 0; i < system.getComponents().size(); i++) {
            Component c = system.getComponents().get(i);
            java.lang.System.out.println("  " + (i + 1) + ". " + c.getName());
        }
        java.lang.System.out.print("Select component number to delete: ");
        int index = Integer.parseInt(scanner.nextLine().trim()) - 1;

        if (index < 0 || index >= system.getComponents().size()) {
            java.lang.System.out.println("Invalid selection.");
            return;
        }
        String name = system.getComponents().get(index).getName();
        CommittableView committableView = getDefaultView(virtualModel, List.of(System.class)).withChangeDerivingTrait();
        committableView.getRootObjects(System.class).iterator().next().getComponents().remove(index);
        committableView.commitChanges();
        java.lang.System.out.println("Component '" + name + "' deleted.");
    }

    private static void deleteAllModels() {
        java.lang.System.out.print("\nAre you sure you want to delete ALL models? (yes/no): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            java.lang.System.out.println("Cancelled.");
            return;
        }
        CommittableView view = getDefaultView(virtualModel, List.of(System.class)).withChangeDerivingTrait();
        Collection<System> systems = new java.util.ArrayList<>(view.getRootObjects(System.class));
        if (systems.isEmpty()) {
            java.lang.System.out.println("No models to delete.");
            return;
        }
        // Remove all components first
        for (System system : systems) {
            system.getComponents().clear();
        }
        view.commitChanges();
        // delete the empty root via a second commit
        // Clearing components first avoids the dangling containment issue
        CommittableView view2 = getDefaultView(virtualModel, List.of(System.class)).withChangeDerivingTrait();
        for (System system : new java.util.ArrayList<>(view2.getRootObjects(System.class))) {
            org.eclipse.emf.ecore.util.EcoreUtil.delete(system, true);
        }
        view2.commitChanges();
        java.lang.System.out.println("All models deleted.");
    }

    private static void initializeVsum() throws Exception {
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
        // Register EMF packages needed for standalone execution.
        // In Eclipse/OSGi these are auto-registered via plugin.xml.
        // In standalone IntelliJ, static initializers must be triggered manually
        // before buildAndInitialize() tries to load the existing correspondence file.
        tools.vitruv.change.correspondence.CorrespondencePackage.eINSTANCE.eClass();
        tools.vitruv.dsls.reactions.runtime.correspondence.CorrespondencePackage.eINSTANCE.eClass();

        InternalVirtualModel innerModel = (InternalVirtualModel) new VirtualModelBuilder()
                .withStorageFolder(repoRoot)
                .withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(new TestUserInteraction()))
                .withChangePropagationSpecifications(new Model2Model2ChangePropagationSpecification())
                .buildAndInitialize();
        innerModel.setChangePropagationMode(ChangePropagationMode.TRANSITIVE_CYCLIC);
        virtualModel = new BranchAwareVirtualModel(repoRoot, innerModel);
        boolean isResume = Files.exists(repoRoot.resolve("example.model"));
        java.lang.System.out.println("Active branch: " + virtualModel.getActiveBranch());
        java.lang.System.out.println(isResume ? "V-SUM resumed from existing state." : "V-SUM initialized fresh.");
    }

    private static void installGitHooks() throws Exception {
        GitHookInstaller hookInstaller = new GitHookInstaller(repoRoot);
        if (hookInstaller.areAllHooksInstalled()) {
            java.lang.System.out.println("Git hooks already installed, skipping.");
        } else {
            hookInstaller.installAllHooks();
            java.lang.System.out.println("Git hooks installed.");
        }
        createMasterMetadataIfNeeded();
    }

    private static void createMasterMetadataIfNeeded() throws IOException {
        Path vitruviusDir = repoRoot.resolve(".vitruvius");
        Path branchesDir = vitruviusDir.resolve("branches");
        Path masterMetadata = branchesDir.resolve("master.metadata");

        if (!Files.exists(masterMetadata)) {
            Files.createDirectories(branchesDir);

            String timestamp = java.time.LocalDateTime.now().toString();
            String metadata = String.format("""
            {
              "branchName": "master",
              "state": "ACTIVE",
              "parentBranch": "master",
              "createdAt": "%s",
              "lastModified": "%s"
            }
            """, timestamp, timestamp);

            Files.writeString(masterMetadata, metadata);
            java.lang.System.out.println("Master branch metadata created");
        }
    }

    private static void startBackgroundWatchers() {
        VsumValidationWatcher validationWatcher = new VsumValidationWatcher(virtualModel, repoRoot);
        validationWatcher.start();
        java.lang.System.out.println("Validation watcher started");

        VsumPostCommitWatcher postCommitWatcher = new VsumPostCommitWatcher(repoRoot);
        postCommitWatcher.attachSemanticChangeTracking(
                virtualModel.getChangeBuffer(),
                virtualModel::getUuidResolver,
                virtualModel::getViewSourceModels);
        postCommitWatcher.start();
        java.lang.System.out.println("Post-commit watcher started");

        VsumReloadWatcher reloadWatcher = new VsumReloadWatcher(virtualModel, repoRoot);
        reloadWatcher.start();
        java.lang.System.out.println("Reload watcher started");

        VsumMergeWatcher mergeWatcher = new VsumMergeWatcher(virtualModel, repoRoot);
        mergeWatcher.start();
        java.lang.System.out.println("Merge watcher started");
    }

    private static View getDefaultView(VirtualModel vsum, Collection<Class<?>> rootTypes) {
        var selector = vsum.createSelector(ViewTypeFactory.createIdentityMappingViewType("default"));
        selector.getSelectableElements().stream().filter(element -> rootTypes.stream().anyMatch(it -> it.isInstance(element))).forEach(it -> selector.setSelected(it, true));
        return selector.createView();
    }
}