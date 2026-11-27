package tools.vitruv.framework.remote.common.apm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.step.StepRegistryConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VitruvStepMeterRegistryTest {
  private VitruvStepMeterRegistry registry;
  private Path tempFile;
  private StepRegistryConfig mockConfig;

  @BeforeEach
  void setUp() {
    try {
      tempFile = Files.createTempFile("metrics", ".log");
      mockConfig = mock(StepRegistryConfig.class);
      when(mockConfig.step()).thenReturn(Duration.of(1, ChronoUnit.MINUTES));
      when(mockConfig.enabled()).thenReturn(true);
      registry = new VitruvStepMeterRegistry(mockConfig, Clock.SYSTEM, tempFile);
    } catch (IOException e) {
      fail("Failed to create temp file: " + e.getMessage());
    }
  }

  @AfterEach
  void tearDown() {
    try {
      Files.deleteIfExists(tempFile);
    } catch (IOException e) {
      fail("Failed to delete temp file: " + e.getMessage());
    }
  }

  @Test
  void testMetricsAreWrittenToFile() {
    Timer timer = Timer.builder("test.timer").register(registry);
    timer.record(Duration.ofMillis(100));

    registry.publish();

    try {
      String fileContent = Files.readString(tempFile);
      assertTrue(fileContent.contains("test.timer"), "Metric ID should be present in the file");
    } catch (IOException e) {
      fail("Failed to read temp file: " + e.getMessage());
    }
  }

  @Test
  void testMultipleMetricsAreWrittenToFile() {
    Timer timer1 = Timer.builder("test.timer1").register(registry);
    Timer timer2 = Timer.builder("test.timer2").register(registry);

    timer1.record(Duration.ofMillis(150));
    timer2.record(Duration.ofMillis(200));

    registry.publish();

    try {
      String fileContent = Files.readString(tempFile);
      assertTrue(
          fileContent.contains("test.timer1"), "Metric test.timer1 should be present in the file");
      assertTrue(
          fileContent.contains("test.timer2"), "Metric test.timer2 should be present in the file");
    } catch (IOException e) {
      fail("Failed to read temp file: " + e.getMessage());
    }
  }

  @Test
  void testFileRemainsEmptyIfNoMetricsAreRecorded() {
    registry.publish();
    try {
      List<String> lines = Files.readAllLines(tempFile);
      assertTrue(lines.isEmpty(), "File should be empty if no metrics are recorded");
    } catch (IOException e) {
      fail("Failed to read temp file: " + e.getMessage());
    }
  }

  @Test
  void testFileWritingErrorHandling() {
    try {
      Files.writeString(tempFile, "Pre-existing content");
      tempFile.toFile().setReadOnly();
    } catch (IOException e) {
      fail("Setup failed: " + e.getMessage());
    }

    registry.publish();

    tempFile.toFile().setWritable(true);

    try {
      String fileContent = Files.readString(tempFile);
      assertEquals(
          "Pre-existing content",
          fileContent,
          "File content should remain unchanged due to error handling");
    } catch (IOException e) {
      fail("Failed to read temp file: " + e.getMessage());
    }
  }

  @Test
  void testTimersAreClearedAfterPublishing() {
    Timer timer = registry.timer("test.timer");
    timer.record(Duration.ofMillis(100));

    assertFalse(
        registry.getMeters().isEmpty(),
        "There should be at least one recorded metric before publishing.");

    registry.publish();

    boolean hasRemainingMetrics =
        registry.getMeters().stream()
            .anyMatch(meter -> meter.getId().getName().equals("test.timer"));

    assertTrue(hasRemainingMetrics, "Timer should still be registered.");
  }
}
