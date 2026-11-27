package tools.vitruv.framework.vsum.branch.merge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MergeTracer}.
 *
 * <p>Tests verify the enable/disable flag, the init/trace/close lifecycle,
 * that trace messages are written to the log file, and that disabled tracing
 * produces no file output.
 *
 * <p>Because {@link MergeTracer} uses static state, each test resets it
 * in {@link #afterEach()} via {@link MergeTracer#close()} and re-enables tracing.
 */
class MergeTracerTest {

    @TempDir
    Path traceDir;

    @BeforeEach
    void setUp() {
        MergeTracer.setEnabled(true);
        MergeTracer.setOutputDirectory(traceDir);
        MergeTracer.close();
    }

    @AfterEach
    void afterEach() {
        MergeTracer.close();
        MergeTracer.setEnabled(true);
    }

    @Test
    @DisplayName("isEnabled returns true by default (after explicitly enabling)")
    void isEnabledDefaultsToTrue() {
        assertTrue(MergeTracer.isEnabled());
    }

    @Test
    @DisplayName("setEnabled(false) makes isEnabled() return false")
    void setEnabledFalse() {
        MergeTracer.setEnabled(false);
        assertFalse(MergeTracer.isEnabled());
    }

    @Test
    @DisplayName("init creates a log file in the output directory")
    void initCreatesLogFile() throws IOException {
        MergeTracer.init("CaseStudy", "S1");

        Path logFile = MergeTracer.getCurrentLogFile();
        assertNotNull(logFile, "getCurrentLogFile() must return a non-null path after init");
        assertTrue(Files.exists(logFile), "the log file must exist on disk after init");
        assertTrue(logFile.getFileName().toString().startsWith("CaseStudy-S1"),
                "log file name must begin with caseStudyName-scenarioId");
    }

    @Test
    @DisplayName("trace writes the message to the log file")
    void traceWritesToLogFile() throws IOException {
        MergeTracer.init("CS", "S2");
        MergeTracer.trace("test message 42");
        MergeTracer.close();

        Path logFile = traceDir.toFile().listFiles((d, n) -> n.startsWith("CS-S2"))[0].toPath();
        String content = Files.readString(logFile);
        assertTrue(content.contains("test message 42"));
    }

    @Test
    @DisplayName("close flushes the log file and getCurrentLogFile() returns null")
    void closeFlushesAndClearsLogFile() {
        MergeTracer.init("CS", "S3");
        assertNotNull(MergeTracer.getCurrentLogFile());

        MergeTracer.close();

        assertNull(MergeTracer.getCurrentLogFile(),
                "getCurrentLogFile() must return null after close");
    }

    @Test
    @DisplayName("When disabled, init does not create a log file")
    void disabledInitCreatesNoLogFile() {
        MergeTracer.setEnabled(false);
        MergeTracer.init("CS", "S4");

        assertNull(MergeTracer.getCurrentLogFile(),
                "no log file must be created when tracing is disabled");
    }

    @Test
    @DisplayName("When disabled, trace is a no-op and writes nothing to disk")
    void disabledTraceWritesNothing() throws IOException {
        MergeTracer.setEnabled(false);
        MergeTracer.trace("this must not appear");

        long fileCount = Files.list(traceDir).count();
        assertEquals(0, fileCount, "no files must be created when tracing is disabled");
    }

    @Test
    @DisplayName("A second init call closes the previous session before starting a new one")
    void secondInitClosesPreviousSession() throws IOException {
        MergeTracer.init("CS", "A");
        Path firstLog = MergeTracer.getCurrentLogFile();
        assertNotNull(firstLog);

        MergeTracer.init("CS", "B");
        Path secondLog = MergeTracer.getCurrentLogFile();

        assertNotNull(secondLog);
        assertNotEquals(firstLog, secondLog,
                "a second init must create a different log file");
    }

    @Test
    @DisplayName("section writes a visible separator block to the log file")
    void sectionWritesSeparator() throws IOException {
        MergeTracer.init("CS", "S5");
        MergeTracer.section("Phase 1");
        MergeTracer.close();

        Path logFile = traceDir.toFile().listFiles((d, n) -> n.startsWith("CS-S5"))[0].toPath();
        String content = Files.readString(logFile);
        assertTrue(content.contains("Phase 1"), "section title must appear in the log");
    }
}
