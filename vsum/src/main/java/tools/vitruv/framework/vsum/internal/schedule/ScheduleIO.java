package tools.vitruv.framework.vsum.internal.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.vsum.schedule.Schedule;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;

public final class ScheduleIO {

    // ---------- Public API ----------

    public static void save(
            Path targetDir,
            Schedule schedule,
            Function<VitruviusChange<Uuid>, EObject> toEChange
    ) throws IOException {

        Files.createDirectories(targetDir);

        // 1) Persist each time slot (t -> list of changes) as XMI (EMF)
        ResourceSet rs = new ResourceSetImpl();
        registerVitruvPackages(rs);
        registerXmiFactory(rs);

        Map<Integer, String> index = new TreeMap<>();
        for (Map.Entry<Integer, List<VitruviusChange<Uuid>>> e : schedule.schedule().entrySet()) {
            Integer step = e.getKey();
            List<VitruviusChange<Uuid>> changes = e.getValue();

            String fileName = "step-" + step + ".xmi";
            Path stepFile = targetDir.resolve(fileName);

            Resource r = rs.createResource(URI.createFileURI(stepFile.toString()));
            // Convert each VitruviusChange to the serializable EMF change (EChange/EObject)
            for (VitruviusChange<Uuid> vc : changes) {
                EObject emfChange = Objects.requireNonNull(toEChange.apply(vc),
                        "toEChange returned null for change at step " + step);
                r.getContents().add(emfChange);
            }
            r.save(defaultSaveOptions());
            index.put(step, fileName);
        }

        // 2) Persist the index + original hash order as JSON
        IndexJson payload = new IndexJson(index, schedule.originalChangeHashOrder());
        writeJson(targetDir.resolve("index.json"), payload);
    }

    public static Schedule load(
            Path sourceDir,
            Function<EObject, VitruviusChange<Uuid>> toVitruviusChange
    ) throws IOException {

        // 1) Read the index + original hash order
        IndexJson index = readJson(sourceDir.resolve("index.json"), IndexJson.class);

        ResourceSet rs = new ResourceSetImpl();
        registerVitruvPackages(rs);
        registerXmiFactory(rs);

        Map<Integer, List<VitruviusChange<Uuid>>> schedule = new TreeMap<>();

        for (Map.Entry<Integer, String> e : index.stepsToFiles.entrySet()) {
            Integer step = e.getKey();
            Path file = sourceDir.resolve(e.getValue());

            Resource r = rs.getResource(URI.createFileURI(file.toString()), true);
            List<VitruviusChange<Uuid>> restored = new ArrayList<>(r.getContents().size());
            for (EObject eo : r.getContents()) {
                VitruviusChange<Uuid> vc = Objects.requireNonNull(
                        toVitruviusChange.apply(eo),
                        "toVitruviusChange returned null when reading " + file
                );
                restored.add(vc);
            }
            schedule.put(step, restored);
        }

        return new Schedule(schedule, index.originalChangeHashOrder);
    }

    // ---------- EMF setup ----------

    private static void registerXmiFactory(ResourceSet rs) {
        rs.getResourceFactoryRegistry()
                .getExtensionToFactoryMap()
                .put("xmi", new XMIResourceFactoryImpl());
    }

    /**
     * Register Vitruv change packages so EMF knows how to (de)serialize EChanges.
     * Adjust to the exact packages you use (atomic/composite etc.).
     */
    private static void registerVitruvPackages(ResourceSet rs) {
        // Examples — put the correct EPackages you are using here:
        // rs.getPackageRegistry().put(AtomicPackage.eNS_URI, AtomicPackage.eINSTANCE);
        // rs.getPackageRegistry().put(CompositePackage.eNS_URI, CompositePackage.eINSTANCE);
        //
        // You’ll find the packages in the artifacts listed under the tools.vitruv group
        // (e.g., tools.vitruv.change.atomic, tools.vitruv.change.composite).
        // See Maven repository listing. [3](https://repo.maven.apache.org/maven2/tools/vitruv/)[4](https://mvnrepository.com/artifact/tools.vitruv)
    }

    private static Map<Object, Object> defaultSaveOptions() {
        Map<Object, Object> m = new HashMap<>();
        // Add EMF/XMI options if you need stable IDs / type info, etc.
        // m.put(XMLResource.OPTION_ENCODING, "UTF-8");
        // m.put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);
        return m;
    }

    // ---------- JSON boilerplate ----------

    private record IndexJson(Map<Integer, String> stepsToFiles,
                             List<Integer> originalChangeHashOrder) {}

    private static final ObjectMapper OM = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private static void writeJson(Path file, Object payload) throws IOException {
        try (var out = Files.newBufferedWriter(file)) {
            OM.writeValue(out, payload);
        }
    }

    private static <T> T readJson(Path file, Class<T> type) throws IOException {
        try (var in = Files.newBufferedReader(file)) {
            return OM.readValue(in, type);
        }
    }
}