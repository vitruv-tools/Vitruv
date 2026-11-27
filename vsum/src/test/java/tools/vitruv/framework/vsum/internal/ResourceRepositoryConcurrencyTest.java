package tools.vitruv.framework.vsum.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.persistence.StateBasedModelPersistence;

/**
 * Tests that the resource repository of a virtual model stays consistent when models are created
 * and loaded from several threads.
 */
@ExtendWith(TestProjectManager.class)
class ResourceRepositoryConcurrencyTest {
  private static final int THREADS = 8;
  private static final int MODELS_PER_THREAD = 100;

  @Test
  @DisplayName("create models from several threads")
  void createModelsConcurrently(@TestProject Path projectFolder) throws Exception {
    var repository = createRepository(projectFolder);
    List<String> failures = new CopyOnWriteArrayList<>();
    runConcurrently(
        thread -> {
          for (int i = 0; i < MODELS_PER_THREAD; i++) {
            if (i % 2 == 0) {
              repository.getModelResource(modelUri(projectFolder, thread, i));
            } else {
              var root = aet.Root();
              root.setId("root" + thread + "_" + i);
              repository.persistAsRoot(root, modelUri(projectFolder, thread, i));
            }
          }
        },
        failures);

    assertEquals(List.of(), failures.stream().distinct().limit(10).toList());
    assertEquals(THREADS * MODELS_PER_THREAD, repository.getModelResources().size());
    for (int thread = 0; thread < THREADS; thread++) {
      for (int i = 0; i < MODELS_PER_THREAD; i++) {
        var uri = modelUri(projectFolder, thread, i);
        var model = repository.getModel(uri);
        assertNotNull(model, "no model for " + uri);
        assertEquals(i % 2, model.getResource().getContents().size(), "contents of " + uri);
      }
    }
    repository.close();
  }

  @Test
  @DisplayName("demand-load the same models from several threads")
  void demandLoadModelsConcurrently(@TestProject Path projectFolder) throws Exception {
    var writer = createRepository(projectFolder);
    for (int i = 0; i < MODELS_PER_THREAD; i++) {
      var root = aet.Root();
      root.setId("root" + i);
      writer.persistAsRoot(root, modelUri(projectFolder, 0, i));
    }
    writer.saveOrDeleteModels();
    writer.close();

    // a fresh repository without loading the existing models, so that each access demand-loads
    // its model like the resolution of a proxy into an unloaded model does
    var repository = createRepository(projectFolder);
    ResourceSet resourceSet =
        repository.getModelResource(modelUri(projectFolder, THREADS, 0)).getResourceSet();
    List<String> failures = new CopyOnWriteArrayList<>();
    List<Set<Resource>> loadedPerModel = new ArrayList<>();
    for (int i = 0; i < MODELS_PER_THREAD; i++) {
      loadedPerModel.add(ConcurrentHashMap.newKeySet());
    }
    var barrier = new CyclicBarrier(THREADS);
    runConcurrently(
        thread -> {
          for (int i = 0; i < MODELS_PER_THREAD; i++) {
            await(barrier);
            Resource resource = resourceSet.getResource(modelUri(projectFolder, 0, i), true);
            loadedPerModel.get(i).add(resource);
          }
        },
        failures);

    assertEquals(List.of(), failures.stream().distinct().limit(10).toList());
    for (int i = 0; i < MODELS_PER_THREAD; i++) {
      var uri = modelUri(projectFolder, 0, i);
      assertEquals(1, loadedPerModel.get(i).size(), "distinct resources loaded for " + uri);
      assertEquals(
          1,
          repository.getModelResources().stream().filter(r -> uri.equals(r.getURI())).count(),
          "resources in the resource set for " + uri);
      assertNotNull(repository.getModel(uri), "no model for " + uri);
    }
    repository.close();
  }

  private static ResourceRepositoryImpl createRepository(Path projectFolder) throws Exception {
    var layout = new VsumFileSystemLayout(projectFolder.resolve("vsum"));
    layout.prepare();
    return new ResourceRepositoryImpl(layout, new StateBasedModelPersistence());
  }

  private static URI modelUri(Path projectFolder, int thread, int index) {
    return URI.createFileURI(
        projectFolder.resolve("model" + thread + "_" + index + ".allElementTypes").toString());
  }

  private static void await(CyclicBarrier barrier) {
    try {
      barrier.await(10, TimeUnit.SECONDS);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private static void runConcurrently(IntConsumer work, List<String> failures)
      throws InterruptedException {
    List<Thread> threads = new ArrayList<>();
    for (int t = 0; t < THREADS; t++) {
      int thread = t;
      threads.add(
          new Thread(
              () -> {
                try {
                  work.accept(thread);
                } catch (RuntimeException e) {
                  failures.add(e.toString());
                }
              }));
    }
    threads.forEach(Thread::start);
    for (Thread thread : threads) {
      thread.join();
    }
  }
}
