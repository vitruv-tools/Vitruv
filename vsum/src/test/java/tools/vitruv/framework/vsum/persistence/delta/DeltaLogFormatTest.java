package tools.vitruv.framework.vsum.persistence.delta;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import allElementTypes.AllElementTypesPackage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.TypeInferringAtomicEChangeFactory;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RootFactory;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/** The log format: dictionary pin, codec, dual IDs, and the Deltachain schema of its changes. */
@ExtendWith(TestProjectManager.class)
class DeltaLogFormatTest {

  /** The Deltachain's pin ({@code PayloadCodecTest.DICTIONARY_V1_SHA256} there). */
  private static final String DICTIONARY_V1_SHA256 =
      "f756cd37834a0d5bf0f69a8fca15a176a111296084c60880a50b034c07d53fa0";

  private static final Pattern CHANGE_CLASS = Pattern.compile("[^#\\s]+#[A-Za-z]\\w*");
  private static final Set<String> PREFIXES =
      Set.of(
          EChangeJson.HID_PREFIX,
          EChangeJson.ECLASS_PREFIX,
          EChangeJson.EFEATURE_PREFIX,
          EChangeJson.DATA_VALUE_PREFIX);

  @Test
  @DisplayName("dictionary v1 is the Deltachain's, byte for byte")
  void dictionaryIsPinned() throws Exception {
    byte[] dictionary = PayloadCodec.dictionaryV1();
    assertEquals(
        DICTIONARY_V1_SHA256,
        java.util.HexFormat.of()
            .formatHex(MessageDigest.getInstance("SHA-256").digest(dictionary)));
    assertTrue(dictionary.length <= 32 * 1024, "fits the DEFLATE window");
  }

  @Test
  @DisplayName("payloads shrink and round-trip; what does not shrink stays JSON")
  void codecRoundTrip() throws Exception {
    String change =
        "{\"_class\":\"http://vitruv.tools/metamodels/change/atomic/feature/attribute/2.0"
            + "#ReplaceSingleValuedEAttribute\",\"affectedElement\":"
            + "\"__hid__:root.allElementTypes#/|u=_abcdefghijklmnopqrstuv\","
            + "\"newValue\":\"__value__:7\"}";
    byte[] json =
        ("[[" + String.join(",", Collections.nCopies(20, change)) + "]]").getBytes(UTF_8);
    byte[] payload = PayloadCodec.encode(json);
    assertEquals(PayloadCodec.DEFLATE_DICT_V1, payload[0]);
    assertTrue(payload.length * 10 < json.length, payload.length + " vs " + json.length);
    assertArrayEquals(json, PayloadCodec.toJson(payload));

    byte[] small = "[]".getBytes(UTF_8);
    assertSame(small, PayloadCodec.encode(small));
    assertSame(small, PayloadCodec.toJson(small));
  }

  @Test
  @DisplayName("broken payloads are reported")
  void brokenPayloads() {
    String json = "[{\"_class\":\"x\"},{\"_class\":\"x\"},{\"_class\":\"x\"}]".repeat(10);
    byte[] payload = PayloadCodec.encode(json.getBytes(UTF_8));
    assertThrows(
        IOException.class, () -> PayloadCodec.toJson(Arrays.copyOf(payload, payload.length / 2)));
    byte[] unknown = payload.clone();
    unknown[0] = 0x02;
    assertTrue(
        assertThrows(IOException.class, () -> PayloadCodec.toJson(unknown))
            .getMessage()
            .contains("codec byte"));
    byte[] corrupt = payload.clone();
    Arrays.fill(corrupt, 1, corrupt.length, (byte) 0xff);
    assertThrows(IOException.class, () -> PayloadCodec.toJson(corrupt));
  }

  @Test
  @DisplayName("dual IDs: path first, UUID after |u=, other Deltachain fields ignored")
  void dualIds(@TestProject Path folder) {
    assertEquals("m.x#/|u=_a", ElementIds.encode("m.x#/", "_a"));
    assertEquals("m.x#/", ElementIds.encode("m.x#/", null));
    assertEquals(new ElementIds.Ref("m.x#//@c.1", "_a"), ElementIds.decode("m.x#//@c.1|u=_a|a=^"));
    assertEquals(new ElementIds.Ref("cache:/0", "_b"), ElementIds.decode("cache:/0|c=x#Y|u=_b"));
    assertNull(ElementIds.decode("m.x#/").uuid());

    ElementIds ids = new ElementIds(folder);
    String inside = org.eclipse.emf.common.util.URI.createFileURI(
        folder.resolve("sub/m.x").toString()).toString() + "#//@c.1";
    assertEquals("sub/m.x#//@c.1", ids.relativize(inside));
    assertEquals(inside, ids.absolutize("sub/m.x#//@c.1"));
    String outside = org.eclipse.emf.common.util.URI.createFileURI(
        folder.getParent().resolve("o.x").toString()).toString();
    assertEquals("../o.x", ids.relativize(outside));
    assertEquals(outside, ids.absolutize("../o.x"));
    assertEquals("cache:/3", ids.relativize("cache:/3"));
    String global = "http://www.eclipse.org/emf/2002/Ecore#//EString";
    assertEquals(global, ids.relativize(global));
    assertEquals(global, ids.absolutize(global));
  }

  @Test
  @DisplayName("the change JSON is the Deltachain schema, pinned by a fixture")
  void changeJsonIsTheDeltachainSchema() throws Exception {
    var factory = TypeInferringAtomicEChangeFactory.getInstance();
    HierarchicalId root = new HierarchicalId("root.allElementTypes#/|u=_root");
    HierarchicalId created = new HierarchicalId(HierarchicalId.CACHE_PREFIX + "0|u=_created");
    InsertRootEObject<HierarchicalId> insertRoot = RootFactory.eINSTANCE.createInsertRootEObject();
    insertRoot.setNewValue(root);
    insertRoot.setUri("root.allElementTypes");
    insertRoot.setIndex(0);
    var create = factory.createCreateEObjectChange(created);
    create.setAffectedEObjectType(AllElementTypesPackage.Literals.NON_ROOT);
    List<EChange<HierarchicalId>> changes =
        List.of(
            insertRoot,
            create,
            factory.createInsertReferenceChange(
                root,
                AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE,
                created,
                3),
            factory.createReplaceSingleAttributeChange(
                new HierarchicalId("root.allElementTypes#//@multiValuedContainmentEReference.2"
                    + "|u=_other"),
                AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_EATTRIBUTE,
                10,
                5));
    List<Object> json = new ArrayList<>();
    changes.forEach(change -> json.add(EChangeJson.toJson(change)));
    assertEquals(fixture("deltachain-schema.json"), Json.write(json), "the pinned schema");
    for (Object change : json) {
      @SuppressWarnings("unchecked")
      Map<String, Object> map = (Map<String, Object>) change;
      assertEquals(map, EChangeJson.toJson(EChangeJson.fromJson(map)), "round trip: " + map);
      assertFollowsSchema(map);
    }
  }

  @Test
  @DisplayName("logged records follow the schema: relative paths, a UUID in every model ID")
  void loggedRecordsFollowTheSchema(@TestProject Path folder) throws Exception {
    InternalVirtualModel vsum =
        DeltaBasedModelPersistenceTest.vsum(folder, DeltaBasedModelPersistenceTest.delta(1000));
    DeltaBasedModelPersistenceTest.runScenario(vsum, folder);
    vsum.dispose();
    List<ChangeLog.Entry> entries = new ChangeLog(folder.resolve("vsum/changes.log"), false)
        .read();
    assertEquals(10, entries.size());
    int ids = 0;
    for (ChangeLog.Entry entry : entries) {
      assertEquals(entry.line(), entry.seq());
      assertFalse(entry.originalGroups().isEmpty());
      List<List<Map<String, Object>>> groups = new ArrayList<>(entry.originalGroups());
      groups.addAll(entry.consequentialGroups());
      for (List<Map<String, Object>> group : groups) {
        for (Map<String, Object> change : group) {
          assertFollowsSchema(change);
          for (Object value : change.values()) {
            if (value instanceof String text && text.startsWith(EChangeJson.HID_PREFIX)) {
              String id = text.substring(EChangeJson.HID_PREFIX.length());
              assertFalse(id.startsWith("file:"), "relative: " + id);
              assertTrue(id.startsWith("http:") || ElementIds.decode(id).uuid() != null, id);
              ids++;
            }
          }
        }
      }
    }
    assertTrue(ids > 30, "IDs checked: " + ids);
    // The first record creates the copy as a consequential change of the inserted root.
    assertFalse(entries.get(0).consequentialGroups().isEmpty());
  }

  static void assertFollowsSchema(Map<String, Object> change) {
    assertTrue(
        change.get(EChangeJson.CHANGE_CLASS) instanceof String name
            && CHANGE_CLASS.matcher(name).matches(),
        "_class: " + change);
    for (Map.Entry<String, Object> field : change.entrySet()) {
      Object value = field.getValue();
      assertTrue(
          value instanceof String || value instanceof Number || value instanceof Boolean,
          "plain value: " + field);
      if (value instanceof String text && text.startsWith("__")) {
        assertTrue(PREFIXES.stream().anyMatch(text::startsWith), "known prefix: " + field);
      }
    }
  }

  private static String fixture(String name) throws IOException {
    try (InputStream in = DeltaLogFormatTest.class.getResourceAsStream(name)) {
      assertTrue(in != null, "missing fixture " + name);
      return new String(in.readAllBytes(), UTF_8).trim();
    }
  }
}
