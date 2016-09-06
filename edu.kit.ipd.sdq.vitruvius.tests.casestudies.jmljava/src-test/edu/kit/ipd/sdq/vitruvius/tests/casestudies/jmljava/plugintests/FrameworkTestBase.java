package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;
import org.jmlspecs.openjml.Factory;
import org.jmlspecs.openjml.IAPI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.common.base.Stopwatch;
import com.google.common.io.Files;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.changesynchronizer.ChangeSynchronizerRegistry;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.initializer.ActivateHandler;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.initializer.DeactivateHandler;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.TransformationAbortCause;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests.util.CodeElementUtil;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests.util.DirDiffer;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests.util.EditorManipulator;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests.util.ProjectModelsUtils;

public abstract class FrameworkTestBase {

    private static final class BooleanContainer {
        private boolean value;

        public BooleanContainer(final boolean value) {
            super();
            this.value = value;
        }

        public boolean isValue() {
            return this.value;
        }

        public void setValue(final boolean value) {
            this.value = value;
        }
    }

    private static File jmlRuntimeLib;
    private static File jmlSpecsLib;
    private static final File diffDir = getOrCreateDiffDir();
    private static final ActivateHandler activator = new ActivateHandler();
    private static final DeactivateHandler deactivator = new DeactivateHandler();
    private static final File originalProjectFilesLocation = extractOriginalProjectFiles();
    private final BooleanContainer synchronisationHasBeenStarted = new BooleanContainer(false);
    private final BooleanContainer synchronisationInProgress = new BooleanContainer(false);
    private IProject project;
    private boolean synchronisationAborted;

    protected static final EditorManipulator editorManipulator = new EditorManipulator();
    protected CodeElementUtil codeElementUtil;
    protected ProjectModelsUtils projectModelUtils;

    @BeforeClass
    public static void init() throws IOException {
        final File tmpDir = Files.createTempDir();
        FileUtils.forceDeleteOnExit(tmpDir);

        jmlRuntimeLib = writeToDir(tmpDir, "resources/jmlruntime.jar.resource", "jmlruntime.jar");
        jmlSpecsLib = writeToDir(tmpDir, "resources/jmlspecs.jar.resource", "jmlspecs.jar");
    }

    private static File writeToDir(final File dir, final String resourceString, final String fileName)
            throws IOException {
        InputStream libIn = null;
        OutputStream libOut = null;
        final File createdFile = new File(dir, fileName);

        try {
            libIn = FrameworkTestBase.class.getResourceAsStream(resourceString);
            libOut = new FileOutputStream(createdFile);
            IOUtils.copy(libIn, libOut);
            return createdFile;
        } finally {
            IOUtils.closeQuietly(libIn);
            IOUtils.closeQuietly(libOut);
        }
    }

    @Before
    public void setup() throws Exception {
        this.project = createNewProject();
        FileUtils.forceDeleteOnExit(this.project.getLocation().makeAbsolute().toFile());
        this.codeElementUtil = new CodeElementUtil(this.project);
        this.projectModelUtils = new ProjectModelsUtils(this.project);
        this.synchronisationAborted = false;

        synchronized (this.synchronisationInProgress) {
            this.synchronisationHasBeenStarted.setValue(false);
        }

        activator.activate(this.project.getName());
        ChangeSynchronizerRegistry.getInstance().getChangeSynchronizer().register(new SynchronisationListener() {
            @Override
            public void syncStarted() {
                synchronized (FrameworkTestBase.this.synchronisationInProgress) {
                    FrameworkTestBase.this.synchronisationHasBeenStarted.setValue(true);
                    FrameworkTestBase.this.synchronisationInProgress.setValue(true);
                    FrameworkTestBase.this.synchronisationInProgress.notifyAll();
                }
            }

            @Override
            public void syncFinished() {
                synchronized (FrameworkTestBase.this.synchronisationInProgress) {
                    FrameworkTestBase.this.synchronisationInProgress.setValue(false);
                    FrameworkTestBase.this.synchronisationInProgress.notifyAll();
                }
            }

            @Override
            public void syncAborted(final TransformationAbortCause cause) {
                FrameworkTestBase.this.synchronisationAborted = true;
            }

            @Override
            public void syncAborted(final EMFModelChange abortedChange) {
                FrameworkTestBase.this.synchronisationAborted = true;
            }
        });
    }

    @After
    public void teardown() throws Exception {
        deactivator.deactivate();
        this.project.delete(true, true, null);
    }

    @AfterClass
    public static void deinitialize() throws IOException {
        FileUtils.forceDelete(jmlRuntimeLib);
        FileUtils.forceDelete(jmlSpecsLib);
    }

    protected void waitForSynchronisationToFinish() throws InterruptedException {
        final Stopwatch waitingTime = Stopwatch.createStarted();
        synchronized (this.synchronisationInProgress) {
            while (!this.synchronisationHasBeenStarted.isValue() || this.synchronisationInProgress.isValue()) {
                if (!this.synchronisationHasBeenStarted.isValue() && waitingTime.elapsed(TimeUnit.MINUTES) > 1) {
                    fail("No synchronisation has been started.");
                }
                this.synchronisationInProgress.wait(10000);
            }
        }
        Thread.sleep(5000); // Wait for the model serialization to finish
        // TODO add observer to serialization job in ChangeSynchronizer and wait for event
    }

    protected void assertTransformationAborted() {
        assertTrue(this.synchronisationAborted);
    }

    protected void createAndCompareDiff(final String testMethodName)
            throws IOException, InterruptedException, ExecutionException, TimeoutException {
        this.createAndCompareDiff(testMethodName, true);
    }

    protected void createAndCompareDiff(final String testMethodName, final boolean failIfNoReference)
            throws IOException, InterruptedException, ExecutionException, TimeoutException {
        final File diffFilePath = this.createDiffFilePath(testMethodName);
        final String patchStringActual = this.createPatch();

        if (!diffFilePath.exists()) {
            final File diffFilePathIntermediate = new File(diffFilePath.getAbsolutePath() + ".intermediate");
            final OutputStream os = new FileOutputStream(diffFilePathIntermediate);
            IOUtils.write(patchStringActual, os);
            os.close();
        }

        // we try to compile it after we serialized the diff, so we can examine the cause of the
        // problem
        this.assertCompilable();

        if (diffFilePath.exists()) {
            final InputStream is = new FileInputStream(diffFilePath);
            final String patchStringExpected = StringUtils.join(IOUtils.readLines(is), '\n');
            IOUtils.closeQuietly(is);
            assertEquals(patchStringExpected.trim(), patchStringActual.trim());
        } else if (!failIfNoReference) {
            return;
        } else {
            fail("There is no reference diff.");
        }
    }

    private File createDiffFilePath(final String testMethodName) {
        final String fileName = this.getClass().getSimpleName() + "_" + testMethodName + ".diff";
        return new File(diffDir, fileName);
    }

    private void assertCompilable() throws InterruptedException, ExecutionException, TimeoutException {
        final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final File baseDirChanged = this.project.getLocation().makeAbsolute().toFile();
        final Iterator<File> filesIter = FileUtils.iterateFiles(baseDirChanged, new SuffixFileFilter(".java"),
                DirectoryFileFilter.INSTANCE);
        final Collection<Future<Pair<Integer, String>>> futures = new Vector<Future<Pair<Integer, String>>>();
        while (filesIter.hasNext()) {
            final File javaFile = filesIter.next();
            final Callable<Pair<Integer, String>> compileTask = new Callable<Pair<Integer, String>>() {
                @Override
                public Pair<Integer, String> call() throws Exception {
                    IAPI openjmlAPI;
                    final PrintWriter discardPrintWriter = new PrintWriter(new NullOutputStream());
                    final StringWriter outputStringWriter = new StringWriter();
                    final PrintWriter outputPrintWriter = new PrintWriter(outputStringWriter);
                    try {
                        openjmlAPI = Factory.makeAPI(discardPrintWriter, null, null, new String[0]);
                        final String[] params = new String[] { "-check", "-jml", "-nullableByDefault=false",
                                "-no-internalSpecs", "-no-internalRuntime", "-purityCheck=true", "-d",
                                new File(baseDirChanged.getAbsolutePath(), "bin").getAbsolutePath(), "-sourcepath",
                                new File(baseDirChanged.getAbsolutePath(), "src").getAbsolutePath(), "-specspath",
                                new File(baseDirChanged.getAbsolutePath(), "specs").getAbsolutePath(), "-cp",
                                new File(baseDirChanged.getAbsolutePath(), "bin").getAbsolutePath()
                                        + File.pathSeparatorChar + jmlSpecsLib.getAbsolutePath()
                                        + File.pathSeparatorChar + jmlRuntimeLib.getAbsolutePath(),
                                javaFile.getAbsolutePath() };
                        final int returnCode = openjmlAPI.execute(outputPrintWriter, null, null, params);
                        return new Pair<Integer, String>(returnCode, outputStringWriter.getBuffer().toString());
                    } catch (final Exception e) {
                        return new Pair<Integer, String>(-1, e.getMessage());
                    } finally {
                        IOUtils.closeQuietly(discardPrintWriter);
                        IOUtils.closeQuietly(outputPrintWriter);
                        IOUtils.closeQuietly(outputStringWriter);
                    }
                }
            };
            futures.add(executor.submit(compileTask));
        }
        executor.shutdown();

        // assert compilation result
        for (final Future<Pair<Integer, String>> future : futures) {
            final Pair<Integer, String> result = future.get(5, TimeUnit.MINUTES);
            assertEquals(result.getSecond(), 0, result.getFirst().intValue());
        }

        // assert termination
        assertTrue(executor.awaitTermination(5, TimeUnit.MINUTES));
    }

    private String createPatch() throws IOException, InterruptedException {
        final File baseDirOriginal = originalProjectFilesLocation;
        final File baseDirChanged = this.project.getLocation().makeAbsolute().toFile();

        final DirDiffer differ = new DirDiffer(baseDirOriginal, baseDirChanged, ".java", ".jml");
        return differ.getPatch();
    }

    protected void createJavaProject(final IProject testProject) throws CoreException {
    }

    private static File getOrCreateDiffDir() {
        final File baseDir = Paths.get("diffs").toAbsolutePath().toFile();
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        return baseDir;
    }

    private static File extractOriginalProjectFiles() {
        try {
            final File tmpDir = Files.createTempDir();
            FileUtils.forceDeleteOnExit(tmpDir);

            InputStream is = null;
            ZipArchiveInputStream zais = null;
            try {
                is = FrameworkTestBase.class
                        .getResourceAsStream("resources/edu.kit.ipd.sdq.seifermann.casestudies.javacard.zip");
                zais = new ZipArchiveInputStream(is);
                for (ZipArchiveEntry entry = zais.getNextZipEntry(); entry != null; entry = zais.getNextZipEntry()) {
                    final File destinationFile = new File(tmpDir, entry.getName());
                    if (entry.isDirectory()) {
                        destinationFile.mkdirs();
                        continue;
                    }
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(destinationFile);
                        IOUtils.copy(zais, fos);
                    } finally {
                        IOUtils.closeQuietly(fos);
                    }
                }
            } finally {
                IOUtils.closeQuietly(zais);
                IOUtils.closeQuietly(is);
            }

            return tmpDir;
        } catch (final IOException e) {
            return null;
        }
    }

    protected static IProject createNewProject() throws CoreException, IOException {
        // https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Creating_Eclipse_Java_Projects_Programmatically

        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        final IProject project = root.getProject(RandomStringUtils.random(10, true, true));
        project.create(null);
        project.open(null);

        final IProjectDescription description = project.getDescription();
        description.setNatureIds(new String[] { JavaCore.NATURE_ID });
        project.setDescription(description, null);

        final IJavaProject javaProject = JavaCore.create(project);

        final IFolder binFolder = project.getFolder("bin");
        if (!binFolder.exists()) {
            binFolder.create(false, true, null);
        }
        javaProject.setOutputLocation(binFolder.getFullPath(), null);

        final List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
        final IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
        final LibraryLocation[] locations = JavaRuntime.getLibraryLocations(vmInstall);
        for (final LibraryLocation element : locations) {
            entries.add(JavaCore.newLibraryEntry(element.getSystemLibraryPath(), null, null));
        }
        // add libs to project class path
        javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);

        final IFolder sourceFolder = project.getFolder("src");
        sourceFolder.create(false, true, null);

        final IPackageFragmentRoot pgkRoot = javaProject.getPackageFragmentRoot(sourceFolder);
        final IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
        final IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
        System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
        newEntries[oldEntries.length] = JavaCore.newSourceEntry(pgkRoot.getPath());
        javaProject.setRawClasspath(newEntries, null);

        final File baseDirForExtraction = project.getLocation().makeAbsolute().toFile();
        FileUtils.copyDirectory(originalProjectFilesLocation, baseDirForExtraction);

        project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());

        return project;
    }

}
