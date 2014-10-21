package changeanalysis;

import jamopp.JavaEMFModelRoot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;

import pcm.PCMRoot;
import de.uka.ipd.sdq.pcm.PcmPackage;
import de.uka.ipd.sdq.pcm.core.entity.Entity;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.util.PcmResourceFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.code.jamopp.JaMoPPParser;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;

/**
 * @author Jonas Kunz, Dominik Messinger
 * 
 */
public class CorrespondencesBuilder {

    public CorrespondencesBuilder() throws JavaModelException, IOException {
        System.err.println("CorrespondenceBuilder started");
    }

    public static void main(String[] args) throws JavaModelException, IOException {
        // CorrespondencesBuilder cb = new CorrespondencesBuilder();
        // cb.buildCorrespondenceModel();
    }

    public List<MyEObjectCorrespondence> buildCorrespondenceModel() throws JavaModelException, IOException {
        String fileLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()
                + "/MediaStoreCorrespondence/mediastoreCorrespondenceMapping.txt";
        File f = new File(fileLocation);
        String[] mapping = parseMappingFile(f);
        System.out.println("Show correspondence result\n BEGIN");
        for (String s : mapping) {
            System.out.println(s);
        }
        System.out.println("END");

        // List<CompilationUnit> cus =
        // parseJaMoPP("C:/Users/messinger/MediaStore/MediaStore_JavaEE/MediaStore/ejbModule/de");
        List<CompilationUnit> cus = parseJaMoPP2("MediaStore");
        Repository repo = getRepository(URI.createPlatformResourceURI("MediaStore_v2/mediastore.repository", true));
        // Correspondences correspondences = createCorrespondeceModel(new JavaEMFModelRoot(cus), new
        // PCMRoot(repo),
        // mapping);
        // return correspondences;
        return createMyEObjectCorrespondences(new JavaEMFModelRoot(cus), new PCMRoot(repo), mapping);

        // createAndSerializeVURIs(correspondence);
        // System.out.println("Save correspondence instance");
        // serializeCorrespondencesAsObjectOutput(correspondences);
        // System.out.println("Done.");
    }

    // private void createAndSerializeVURIs(List<CompilationUnit> cus, Repository repo) {
    // List<EObject> eObjectList = new LinkedList<EObject>();
    // eObjectList.addAll(cus);
    // eObjectList.add(repo);
    // createAndSerializeVURIforEObjectList(eObjectList);
    // }

    // private void createAndSerializeVURIforEObjectList(List<EObject> eObjects) {
    // HashSet<VURI> set = new HashSet<VURI>();
    // for (EObject unit : eObjects) {
    // VURI vuri = VURI.getInstance(unit.eResource());
    // set.add(vuri);
    // }
    // FileSystemHelper.saveVSUMvURIsToFile(set);
    // }
    //
    // private void serializeCorrespondences(Correspondences correspondences) throws IOException {
    // ResourceSet rs = new ResourceSetImpl();
    // URI uri =
    // URI.createPlatformResourceURI("vitruvius.meta/correspondence/mediaStore.correspondence_instance",
    // true);
    // Resource resource = rs.createResource(uri);
    // resource.getContents().add(correspondences);
    // // register correspondence model for xmi files
    // EPackage.Registry.INSTANCE.put(CorrespondencePackage.eNS_URI,
    // CorrespondencePackage.eINSTANCE);
    //
    // Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("correspondence_instance",
    // new XMIResourceFactoryImpl());
    // HashMap<String, String> saveCorrespondenceOptions = new HashMap<String, String>();
    // saveCorrespondenceOptions.put(VSUMConstants.OPTION_PROCESS_DANGLING_HREF,
    // VSUMConstants.OPTION_PROCESS_DANGLING_HREF_DISCARD);
    // resource.save(saveCorrespondenceOptions);
    // }

    private void serializeCorrespondencesAsObjectOutput(Correspondences correspondences) throws IOException {
        File file = new File("mediastore-correspondences.out");
        file.createNewFile();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(correspondences);
        out.close();
    }

    private Repository getRepository(URI uri) throws IOException {
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();

        // register PCM
        EPackage.Registry.INSTANCE.put(PcmPackage.eNS_URI, PcmPackage.eINSTANCE);
        m.put("repository", new PcmResourceFactoryImpl());

        ResourceSet rs = new ResourceSetImpl();
        Resource res = rs.createResource(uri);
        res.load(null);
        return (Repository) res.getAllContents().next();
    }

    private static List<CompilationUnit> parseJaMoPP2(String projectName) throws JavaModelException, IOException {
        List<CompilationUnit> compilationUnits = new LinkedList<CompilationUnit>();
        JaMoPPParser parser = new JaMoPPParser();

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = root.getProject("MediaStore");

        IJavaProject javaProject = JavaCore.create(project);
        // IPath projectLocation = project.getLocation();
        // projectLocation = projectLocation.removeLastSegments(1);
        for (IPackageFragment pkg : javaProject.getPackageFragments()) {
            for (ICompilationUnit unit : pkg.getCompilationUnits()) {
                // IPath path = projectLocation.append(unit.getPath());
                String path = unit.getPath().toString();
                CompilationUnit cu = parser.parseCompilationUnitFromDisk(URI.createPlatformResourceURI(path, true));
                compilationUnits.add(cu);
            }
        }
        return compilationUnits;
    }

    /**
     * Parses a java - pcm mapping file into the format used by createCorrespondeceModel.
     * 
     * @param file
     *            The file to parse
     * @return The list of corresponding elements
     */
    public static String[] parseMappingFile(File file) {
        List<String> mapping = new ArrayList<String>();
        try {
            String[] currentPrefixes = { "", "" };
            String[] lastElements = { "", "" };
            Deque<String[]> parentHistory = new ArrayDeque<String[]>();
            parentHistory.push(lastElements);

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace(" ", "");
                line = line.replace("\t", "");
                if (line.startsWith("#") || line.length() == 0) {
                    continue;
                } else if (line.equals("{")) {
                    String[] parents = new String[] { lastElements[0] + ".", lastElements[1] + "." };
                    parentHistory.push(parents);
                } else if (line.equals("}")) {
                    parentHistory.pop();
                } else if (line.startsWith("$PREFIXES:")) {
                    line = line.substring("$PREFIXES:".length());
                    currentPrefixes = line.split("-");
                } else {
                    String[] parents = parentHistory.getFirst();
                    lastElements = line.split("-");
                    lastElements[0] = parents[0] + lastElements[0];
                    lastElements[1] = parents[1] + lastElements[1];
                    mapping.add(currentPrefixes[0] + lastElements[0]);
                    mapping.add(currentPrefixes[1] + lastElements[1]);
                }

            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        String[] result = new String[mapping.size()];
        return mapping.toArray(result);
    }

    /**
     * Creates a correspondence model for the given java & pcm-model from the given mapping.
     * 
     * @param javaModel
     *            the java model
     * @param palladioModel
     *            the palladio model
     * @param mapping
     *            the mapping, consisting of the fully aulified names. mapping[0] = java-element 1
     *            --> mapping[1] = pcm-element 1 mapping[2] = java-element 2 --> mapping[3] =
     *            pcm-element 2 and so on
     * @return
     */
    public static Correspondences createCorrespondeceModel(JavaEMFModelRoot javaModel, PCMRoot palladioModel,
            String[] mapping) {
        Correspondences correspondeces = CorrespondenceFactory.eINSTANCE.createCorrespondences();
        for (int i = 0; i < mapping.length; i += 2) {
            String javaElementName = mapping[i];
            String pcmElementName = mapping[i + 1];

            List<NamedElement> foundJavaElements = javaModel.getAllElementsByFullName(javaElementName);
            if (foundJavaElements.size() != 1) {
                throw new RuntimeException("Element " + javaElementName + " was found " + foundJavaElements.size()
                        + " times!");
            }
            EObject javaElement = foundJavaElements.get(0);

            List<Entity> foundPalladioElements = palladioModel.getAllElementsByFullName(pcmElementName);
            if (foundPalladioElements.size() != 1) {
                throw new RuntimeException("Element " + pcmElementName + " was found " + foundPalladioElements.size()
                        + " times!");
            }
            EObject palladioElement = foundPalladioElements.get(0);

            EObjectCorrespondence correspondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();

            // JaMoPPTUIDCalculatorAndResolver jamoppTuidGen = new
            // JaMoPPTUIDCalculatorAndResolver();
            // correspondence.setElementATUID(TUID.getInstance(jamoppTuidGen.calculateTUIDFromEObject(javaElement)));
            // DefaultTUIDCalculatorAndResolver defaultTUIDGen = new
            // DefaultTUIDCalculatorAndResolver();
            // correspondence.setElementBTUID(TUID.getInstance(defaultTUIDGen.calculateTUIDFromEObject(palladioElement)));

            correspondeces.getCorrespondences().add(correspondence);
        }
        return correspondeces;
    }

    public static class MyEObjectCorrespondence {
        public final EObject a, b;

        public MyEObjectCorrespondence(EObject a, EObject b) {
            this.a = a;
            this.b = b;
        }
    }

    public static List<MyEObjectCorrespondence> createMyEObjectCorrespondences(JavaEMFModelRoot javaModel,
            PCMRoot palladioModel, String[] mapping) {
        List<MyEObjectCorrespondence> correspondences = new LinkedList<CorrespondencesBuilder.MyEObjectCorrespondence>();
        for (int i = 0; i < mapping.length; i += 2) {
            String javaElementName = mapping[i];
            String pcmElementName = mapping[i + 1];

            List<NamedElement> foundJavaElements = javaModel.getAllElementsByFullName(javaElementName);
            if (foundJavaElements.size() != 1) {
                throw new RuntimeException("Element " + javaElementName + " was found " + foundJavaElements.size()
                        + " times!");
            }
            EObject javaElement = foundJavaElements.get(0);

            List<Entity> foundPalladioElements = palladioModel.getAllElementsByFullName(pcmElementName);
            if (foundPalladioElements.size() != 1) {
                throw new RuntimeException("Element " + pcmElementName + " was found " + foundPalladioElements.size()
                        + " times!");
            }
            EObject palladioElement = foundPalladioElements.get(0);
            correspondences.add(new MyEObjectCorrespondence(javaElement, palladioElement));
        }
        return correspondences;
    }

}
