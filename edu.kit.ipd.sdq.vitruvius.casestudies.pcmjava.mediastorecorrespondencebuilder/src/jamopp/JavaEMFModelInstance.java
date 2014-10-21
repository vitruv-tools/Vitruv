package jamopp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emftext.commons.layout.LayoutPackage;
import org.emftext.language.java.JavaPackage;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.resource.JavaSourceOrClassFileResourceFactoryImpl;
import org.emftext.language.java.resource.java.IJavaOptions;

import utils.JamoppModelUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * @author Jonas Kunz
 * 
 */
public class JavaEMFModelInstance {

    // private static final Logger LOGGER = LoggerFactory.getLogger(JavaEMFModelInstance.class);

    private static final Logger LOGGER = Logger.getLogger(JavaEMFModelInstance.class);
    private final ResourceSet rs;
    private final Set<String> packagesOfInterest;

    private JavaEMFModelInstance() {
        this.rs = new ResourceSetImpl();
        this.packagesOfInterest = new HashSet<String>();
        setUp();
    }

    /**
     * Returns an Root Object giving acces to the actual model instance (for finding concrete
     * lements etc..).
     * 
     * @param additionalRelevantPackages
     *            Additional packages to add to the root. All CompilationUnits in this packages are
     *            als oconsidered when scanning the root for elements or references.
     * @return the model root to traverse the tree with
     */
    public JavaEMFModelRoot getModelRoot(String[] additionalRelevantPackages) {

        HashSet<String> allowedPackages = new HashSet<String>();
        allowedPackages.addAll(this.packagesOfInterest);
        if (additionalRelevantPackages != null) {
            allowedPackages.addAll(Arrays.asList(additionalRelevantPackages));
        }

        ArrayList<CompilationUnit> unitsOfInterest = new ArrayList<CompilationUnit>();

        for (Resource res : this.rs.getResources()) {
            for (EObject elem : res.getContents()) {
                if (elem instanceof CompilationUnit) {
                    CompilationUnit unit = (CompilationUnit) elem;
                    String namespace = JamoppModelUtils.getCompilationUnitPackage(unit);
                    if (allowedPackages.contains(namespace)) {
                        unitsOfInterest.add(unit);

                    }
                }
            }
        }
        return new JavaEMFModelRoot(unitsOfInterest);
    }

    /**
     * Saves the laoded model to the given xmi file. WARNING: Destroys some proxies at the moment
     * 
     * @param file
     *            the xmi file to save to
     * @param packageFilterFile
     *            the text file to save the packages of interest to
     */
    /* public */private void saveToXMIFile(File file, File packageFilterFile) {
        try {
            File parentDir = file.getParentFile();
            if (parentDir == null) {
                parentDir = new File(System.getProperty("user.dir"));
            } else if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            URI outUri = URI.createFileURI(file.getCanonicalPath());
            Resource xmiResource = this.rs.createResource(outUri);
            for (Resource javaResource : new ArrayList<Resource>(this.rs.getResources())) {
                xmiResource.getContents().addAll(javaResource.getContents());
            }

            // save the metamodels (schemas) for dynamic loading
            serializeMetamodel(parentDir);
            saveXmiResource(xmiResource);

            // create the package fitler information file
            if (packageFilterFile != null) {
                parentDir = packageFilterFile.getParentFile();
                if (parentDir == null) {
                    parentDir = new File(System.getProperty("user.dir"));
                } else if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }
                packageFilterFile.createNewFile();
                FileWriter writer = new FileWriter(packageFilterFile);
                for (String packageStr : this.packagesOfInterest) {
                    writer.write(packageStr + "\n");
                }
                writer.close();

            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void saveXmiResource(Resource xmiResource) throws IOException {
        Map<Object, Object> options = new HashMap<Object, Object>();
        options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
        xmiResource.save(options);
    }

    private void serializeMetamodel(File outFolder) throws IOException {
        URI outUri = URI.createFileURI(outFolder.getCanonicalPath());

        URI javaEcoreURI = outUri.appendSegment("java.ecore");
        Resource javaEcoreResource = this.rs.createResource(javaEcoreURI);
        javaEcoreResource.getContents().addAll(JavaPackage.eINSTANCE.getESubpackages());

        javaEcoreResource.save(null);

        URI layoutEcoreURI = outUri.appendSegment("layout.ecore");
        Resource layoutEcoreResource = this.rs.createResource(layoutEcoreURI);
        layoutEcoreResource.getContents().add(LayoutPackage.eINSTANCE);

        layoutEcoreResource.save(null);
    }

    private static boolean wasSetUp = false;

    private void setUp() {
        this.rs.getLoadOptions().put(IJavaOptions.DISABLE_LOCATION_MAP, Boolean.TRUE);
        if (!wasSetUp) {
            wasSetUp = true;
            EPackage.Registry.INSTANCE.put("http://www.emftext.org/java", JavaPackage.eINSTANCE);
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("java",
                    new JavaSourceOrClassFileResourceFactoryImpl());
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
                    Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
        }
    }

    /**
     * WARNING: destroys some references in the model (adds unresolveable proxies) not only in the
     * saved one, but also in the originally loaded one
     * 
     * @param xmiFile
     * @param packageFilterFile
     * @return
     */
    /* public */private static JavaEMFModelInstance createFromXmiFile(File xmiFile, File packageFilterFile) {
        // TODO: fix unresolveable proxies problem
        JavaEMFModelInstance instance = new JavaEMFModelInstance();
        instance.rs.getResource(URI.createURI(xmiFile.getPath()), true);
        instance.resolveAllProxies(0);

        if (packageFilterFile != null && packageFilterFile.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(packageFilterFile));
                String packageStr = br.readLine();
                while (packageStr != null) {
                    instance.packagesOfInterest.add(packageStr);
                    packageStr = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return instance;
    }

    /**
     * Cosntructs a model instance from the sourcecode in the given directory.
     * 
     * @param directory
     *            the sourcecode directory
     * @return the model of the code in the given directory
     */
    public static JavaEMFModelInstance createFromSourceCodeDirectory(File directory) {
        JavaEMFModelInstance instance = new JavaEMFModelInstance();
        try {
            instance.loadAllFilesInResourceSet(directory, ".java");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        instance.resolveAllProxies(0);
        /*
         * if (!instance.resolveAllProxies(0)) { Iterator<Notifier> it =
         * instance.rs.getAllContents(); try { throw new
         * RuntimeException("Not all proxies could be resolved of : "+directory.getCanonicalPath());
         * } catch (IOException e) { throw new RuntimeException(e.getMessage()); } }
         */
        return instance;
    }

    private void loadAllFilesInResourceSet(File startFolder, String extension) throws IOException {
        for (File member : startFolder.listFiles()) {
            if (member.isFile()) {
                if (member.getName().endsWith(extension)) {
                    // System.out.println("Parsing " + member);
                    parseResource(member);
                } else {
                    // System.out.println("Skipping " + member);
                }
            }
            if (member.isDirectory()) {
                if (!member.getName().startsWith(".")) {
                    // System.out.println("Recursing into " + member);
                    loadAllFilesInResourceSet(member, extension);
                } else {
                    // System.out.println("Skipping " + member);
                }
            }
        }
    }

    private void parseResource(File file) throws IOException {
        loadResource(file.getCanonicalPath());
    }

    private void loadResource(String filePath) throws IOException {
        loadResource(URI.createFileURI(filePath));
    }

    private void loadResource(URI uri) throws IOException {

        Resource res = this.rs.getResource(uri, true);

        for (EObject elem : res.getContents()) {
            if (elem instanceof CompilationUnit) {
                CompilationUnit unit = (CompilationUnit) elem;
                String namespace = JamoppModelUtils.getCompilationUnitPackage(unit);
                this.packagesOfInterest.add(namespace);
            }
        }

    }

    /*
     * private boolean resolveAllProxies(int resourcesProcessedBefore) { boolean failure = false;
     * List<EObject> eobjects = new LinkedList<EObject>(); for (Iterator<Notifier> i =
     * rs.getAllContents(); i.hasNext();) { Notifier next = i.next(); if (next instanceof EObject) {
     * eobjects.add((EObject) next); } } int resourcesProcessed = rs.getResources().size(); if
     * (resourcesProcessed == resourcesProcessedBefore) { return true; }
     * 
     * //System.out.println("Resolving cross-references of " + eobjects.size() + " EObjects."); for
     * (EObject next : eobjects) { InternalEObject nextElement = (InternalEObject) next; for
     * (EObject crElement : nextElement.eCrossReferences()) { crElement =
     * EcoreUtil.resolve(crElement, rs); if (crElement.eIsProxy()) { failure = true; } } } //call
     * this method again, because the resolving might have triggered loading //of additional
     * resources that may also contain references that need to be resolved. return !failure &&
     * resolveAllProxies(resourcesProcessed); }
     */
    private boolean resolveAllProxies(int resourcesProcessedBefore) {
        List<EObject> eobjects = new LinkedList<EObject>();
        for (Iterator<Notifier> i = this.rs.getAllContents(); i.hasNext();) {
            Notifier next = i.next();
            if (next instanceof EObject) {
                eobjects.add((EObject) next);
            }
        }

        if (eobjects.size() == resourcesProcessedBefore) {
            return true;
        }

        int proxiesFound = 0;
        int proxiesResolved = 0;
        for (EObject next : eobjects) {
            InternalEObject nextElement = (InternalEObject) next;
            for (EObject crElement : nextElement.eCrossReferences()) {
                proxiesFound++;
                crElement = EcoreUtil.resolve(crElement, this.rs);
                if (!crElement.eIsProxy()) {
                    proxiesResolved++;
                }

            }
        }
        LOGGER.info("Resolved " + proxiesResolved + " of " + proxiesFound + " proxies (Total EObjs " + eobjects.size()
                + ")");
        resolveAllProxies(eobjects.size());
        return true;
    }

}
