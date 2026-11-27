package tools.vitruv.framework.vsum.branch.merge;

import lombok.Setter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Execution trace logger for the semantic merge pipeline.
 *
 * <p>Writes human-readable trace output to both the console (System.out) and a
 * per-scenario log file. The log file is named
 * {@code <caseStudyName>-<scenarioId>-<timestamp>.log} and placed in the
 * configured output directory (default: {@code merge-traces/} relative to
 * the working directory).
 *
 * <h3>Usage</h3>
 * <pre>{@code
 * // At the start of a test scenario:
 * MergeTracer.init("BrakeCaseStudy", "S1");
 *
 * // Throughout the merge engine:
 * MergeTracer.trace("[LOAD] Loaded 3 changelog DTOs");
 * MergeTracer.trace("");  // blank line
 *
 * // At the end of a test scenario:
 * MergeTracer.close();
 * }</pre>
 *
 * <h3>Switching off</h3>
 * <pre>{@code
 * MergeTracer.setEnabled(false);  // disables all output (console + file)
 * }</pre>
 *
 * <p>Tracing is <b>enabled by default</b>. When disabled, {@link #trace} and
 * {@link #section} calls are no-ops.
 */
public final class MergeTracer {

    private static final DateTimeFormatter FILE_TS =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");

    /**
     * -- SETTER --
     * Enables or disables trace output globally (console + file). Default is enabled.
     */
    @Setter
    private static boolean enabled = !Boolean.getBoolean("merge.trace.disabled");
    private static Path outputDir = Path.of("merge-traces");
    private static BufferedWriter fileWriter;
    private static Path currentLogFile;
    private static String currentScenarioId;

    private MergeTracer() { }

    /** Returns whether tracing is currently enabled. */
    public static boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the directory where log files are written.
     * Default is {@code merge-traces/} relative to the working directory.
     * Must be called before {@link #init}.
     */
    public static void setOutputDirectory(Path dir) {
        outputDir = dir;
    }

    /** Returns the path to the current scenario's log file, or {@code null} if no session is active. */
    public static Path getCurrentLogFile() {
        return currentLogFile;
    }

    /**
     * Starts a new trace session for a scenario.
     *
     * <p>Creates (or overwrites) a log file named
     * {@code <caseStudyName>-<scenarioId>-<timestamp>.log}.
     *
     * @param caseStudyName the case study name, e.g. "BrakeCaseStudy"
     * @param scenarioId    the scenario identifier, e.g. "S1"
     */
    public static void init(String caseStudyName, String scenarioId) {
        close(); // close any previous session
        currentScenarioId = scenarioId;
        if (!enabled) return;

        try {
            Files.createDirectories(outputDir);
            String timestamp = LocalDateTime.now().format(FILE_TS);
            String fileName = caseStudyName + "-" + scenarioId + "-" + timestamp + ".log";
            currentLogFile = outputDir.resolve(fileName);
            fileWriter = Files.newBufferedWriter(currentLogFile,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("[MergeTracer] Failed to create log file: " + e.getMessage());
            fileWriter = null;
            currentLogFile = null;
        }
    }

    /**
     * Closes the current trace session, flushing and closing the log file.
     */
    public static void close() {
        if (fileWriter != null) {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.err.println("[MergeTracer] Failed to close log file: " + e.getMessage());
            }
            fileWriter = null;
        }
        currentLogFile = null;
        currentScenarioId = null;
    }

    /**
     * Writes a single trace line to both the console and the log file.
     * No-op if tracing is disabled.
     */
    public static void trace(String message) {
        if (!enabled) return;
        System.out.println(message);
        writeLine(message);
    }

    /**
     * Writes a section separator to the trace.
     * Convenience for {@code trace("═".repeat(68))}.
     */
    public static void section(String title) {
        if (!enabled) return;
        trace("════════════════════════════════════════════════════════════════════");
        trace("  " + title);
        trace("════════════════════════════════════════════════════════════════════");
    }

    /**
     * Writes a box header (for scenario descriptions in tests).
     */
    public static void boxStart(String firstLine) {
        if (!enabled) return;
        trace("╔══════════════════════════════════════════════════════════════════════╗");
        trace("║  " + firstLine);
    }

    /** Writes a box continuation line. */
    public static void boxLine(String line) {
        if (!enabled) return;
        trace("║  " + line);
    }

    /** Writes the box footer. */
    public static void boxEnd() {
        if (!enabled) return;
        trace("╚══════════════════════════════════════════════════════════════════════╝");
    }

    /**
     * Writes a result box header.
     */
    public static void resultStart(String firstLine) {
        if (!enabled) return;
        trace("┌──────────────────────────────────────────────────────────────────────┐");
        trace("│  " + firstLine);
    }

    /** Writes a result box line. */
    public static void resultLine(String line) {
        if (!enabled) return;
        trace("│  " + line);
    }

    /** Writes the result box footer. */
    public static void resultEnd() {
        if (!enabled) return;
        trace("└──────────────────────────────────────────────────────────────────────┘");
    }

    private static void writeLine(String message) {
        if (fileWriter == null) return;
        try {
            fileWriter.write(message);
            fileWriter.newLine();
        } catch (IOException e) {
            // Silently ignore write failures -- console output still works
        }
    }
}
