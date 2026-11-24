package tools.vitruv.framework.vsum.persistence.delta;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import tools.vitruv.change.atomic.AtomicPackage;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;

/**
 * One {@code EChange<HierarchicalId>} as JSON in the Deltachain's schema ({@code
 * EChangeSerializer}): {@code _class} is {@code nsURI#Name}; each set, non-transient feature
 * follows in feature order, IDs as {@code __hid__:<id>}, classes as {@code __eclass__:nsURI#Name},
 * features as {@code __feature__:nsURI#Class#name}, an attribute change's old/new value as {@code
 * __value__:} plus the changed attribute's string form. Live objects and resources are left out.
 */
final class EChangeJson {

  static final String CHANGE_CLASS = "_class";
  static final String HID_PREFIX = "__hid__:";
  static final String ECLASS_PREFIX = "__eclass__:";
  static final String EFEATURE_PREFIX = "__feature__:";
  static final String DATA_VALUE_PREFIX = "__value__:";
  private static final Set<String> VALUE_FEATURES = Set.of("oldValue", "newValue");

  static {
    AtomicPackage.eINSTANCE.eClass(); // registers the change metamodel's packages
  }

  private EChangeJson() {}

  /**
   * Converts a change to its JSON object.
   *
   * @param change the change with hierarchical IDs
   * @return the JSON object, in feature order
   */
  static Map<String, Object> toJson(EChange<HierarchicalId> change) {
    Map<String, Object> json = new LinkedHashMap<>();
    EClass eClass = change.eClass();
    json.put(CHANGE_CLASS, eClass.getEPackage().getNsURI() + "#" + eClass.getName());
    EAttribute valueType = affectedAttribute(change);
    for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
      if (feature.isTransient()
          || feature.isDerived()
          || !change.eIsSet(feature)
          || change.eGet(feature) == null) {
        continue;
      }
      Object value = change.eGet(feature);
      String name = feature.getName();
      if (valueType != null
          && VALUE_FEATURES.contains(name)
          && !(value instanceof HierarchicalId)) {
        json.put(
            name,
            DATA_VALUE_PREFIX + EcoreUtil.convertToString(valueType.getEAttributeType(), value));
      } else if (value instanceof HierarchicalId hid) {
        json.put(name, HID_PREFIX + hid.getId());
      } else if (value instanceof EClass valueClass) {
        json.put(
            name,
            ECLASS_PREFIX + valueClass.getEPackage().getNsURI() + "#" + valueClass.getName());
      } else if (value instanceof EStructuralFeature valueFeature) {
        EClass owner = valueFeature.getEContainingClass();
        json.put(
            name,
            EFEATURE_PREFIX
                + owner.getEPackage().getNsURI()
                + "#"
                + owner.getName()
                + "#"
                + valueFeature.getName());
      } else if (value instanceof Resource || value instanceof EObject) {
        // live references: runtime only
        continue;
      } else if (value instanceof Enumerator enumerator) {
        json.put(name, enumerator.getLiteral());
      } else if (value instanceof Boolean) {
        json.put(name, value);
      } else if (value instanceof Integer
          || value instanceof Long
          || value instanceof Short
          || value instanceof Byte) {
        json.put(name, ((Number) value).longValue());
      } else if (value instanceof Number number) {
        json.put(name, number.doubleValue());
      } else {
        json.put(name, value.toString());
      }
    }
    return json;
  }

  /**
   * Converts a JSON object back to its change.
   *
   * @param json the JSON object
   * @return the change with hierarchical IDs
   * @throws IllegalArgumentException for an unknown change class, feature or package
   */
  @SuppressWarnings("unchecked")
  static EChange<HierarchicalId> fromJson(Map<String, Object> json) {
    Object classRef = json.get(CHANGE_CLASS);
    if (!(classRef instanceof String className)) {
      throw new IllegalArgumentException("change without " + CHANGE_CLASS);
    }
    EClass eClass = eClass(className);
    EChange<HierarchicalId> change =
        (EChange<HierarchicalId>) eClass.getEPackage().getEFactoryInstance().create(eClass);
    Map<EStructuralFeature, String> typedValues = new LinkedHashMap<>();
    for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
      Object raw = json.get(feature.getName());
      if (feature.isTransient() || feature.isDerived() || raw == null) {
        continue;
      }
      if (raw instanceof Map || raw instanceof List) {
        throw new IllegalArgumentException(
            "not a value: " + feature.getName() + " in " + className);
      }
      if (raw instanceof String text && text.startsWith(DATA_VALUE_PREFIX)) {
        typedValues.put(feature, text.substring(DATA_VALUE_PREFIX.length()));
        continue;
      }
      Object coerced = coerce(feature, raw);
      if (coerced != null) {
        change.eSet(feature, coerced);
      }
    }
    // typed values once affectedFeature, which carries their type, is set
    EAttribute valueType = affectedAttribute(change);
    typedValues.forEach(
        (feature, text) -> {
          if (valueType == null) {
            throw new IllegalArgumentException(
                "typed value without affected attribute in " + className);
          }
          change.eSet(feature, EcoreUtil.createFromString(valueType.getEAttributeType(), text));
        });
    return change;
  }

  /** The attribute an attribute-value change modifies, or null for any other change. */
  static EAttribute affectedAttribute(EChange<?> change) {
    EStructuralFeature affected = change.eClass().getEStructuralFeature("affectedFeature");
    return affected != null && change.eGet(affected) instanceof EAttribute attribute
        ? attribute
        : null;
  }

  private static Object coerce(EStructuralFeature feature, Object value) {
    if (value instanceof String text) {
      if (text.startsWith(HID_PREFIX)) {
        return new HierarchicalId(text.substring(HID_PREFIX.length()));
      }
      if (text.startsWith(ECLASS_PREFIX)) {
        return eClass(text.substring(ECLASS_PREFIX.length()));
      }
      if (text.startsWith(EFEATURE_PREFIX)) {
        return eFeature(text.substring(EFEATURE_PREFIX.length()));
      }
      if (feature.getEType() instanceof EEnum eEnum) {
        EEnumLiteral literal = eEnum.getEEnumLiteralByLiteral(text);
        return literal != null ? literal.getInstance() : null;
      }
      return text;
    }
    if (value instanceof Boolean) {
      return value;
    }
    Number number = (Number) value;
    Class<?> type = feature.getEType().getInstanceClass();
    if (type == int.class || type == Integer.class) {
      return number.intValue();
    }
    if (type == long.class || type == Long.class) {
      return number.longValue();
    }
    if (type == boolean.class || type == Boolean.class) {
      return number.intValue() != 0;
    }
    return number.doubleValue();
  }

  private static EClass eClass(String nsUriAndName) {
    int hash = nsUriAndName.lastIndexOf('#');
    if (hash < 0) {
      throw new IllegalArgumentException("not nsURI#Class: " + nsUriAndName);
    }
    EPackage pkg = EPackage.Registry.INSTANCE.getEPackage(nsUriAndName.substring(0, hash));
    if (pkg == null) {
      throw new IllegalArgumentException("unknown EPackage: " + nsUriAndName);
    }
    if (!(pkg.getEClassifier(nsUriAndName.substring(hash + 1)) instanceof EClass eClass)) {
      throw new IllegalArgumentException("unknown EClass: " + nsUriAndName);
    }
    return eClass;
  }

  private static EStructuralFeature eFeature(String nsUriClassAndName) {
    int last = nsUriClassAndName.lastIndexOf('#');
    if (last < 0) {
      throw new IllegalArgumentException("not nsURI#Class#feature: " + nsUriClassAndName);
    }
    EStructuralFeature feature =
        eClass(nsUriClassAndName.substring(0, last))
            .getEStructuralFeature(nsUriClassAndName.substring(last + 1));
    if (feature == null) {
      throw new IllegalArgumentException("unknown feature: " + nsUriClassAndName);
    }
    return feature;
  }
}
