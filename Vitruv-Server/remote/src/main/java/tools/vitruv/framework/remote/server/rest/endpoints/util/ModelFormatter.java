package tools.vitruv.framework.remote.server.rest.endpoints.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import tools.vitruv.framework.remote.server.rest.endpoints.util.XmiModelParser.ChildEntry;
import tools.vitruv.framework.remote.server.rest.endpoints.util.XmiModelParser.ParsedElement;

/**
 * Formats a parsed EMF model tree (from {@link XmiModelParser}) into three output styles:
 * <ul>
 *   <li>{@link #toJsonTree}: a nested map suitable for JSON serialization</li>
 *   <li>{@link #toAsciiTree}: a terminal-readable containment tree</li>
 *   <li>{@link #toMermaid}: a Mermaid {@code graph TD} string renderable in a browser</li>
 * </ul>
 */
public class ModelFormatter {

    /**
     * Converts the model element tree to a nested map for Jackson JSON serialization.
     *
     * <p>Example output structure:
     * <pre>
     * { "eClass": "System", "attributes": {},
     *   "children": [ { "feature": "components", "eClass": "Component",
     *                   "attributes": { "name": "c1" }, "children": [] } ] }
     * </pre>
     */
    public static Map<String, Object> toJsonTree(ParsedElement root) {
        var map = new LinkedHashMap<String, Object>();
        map.put("eClass", root.eClass());
        map.put("attributes", root.attributes());
        var children = new ArrayList<Map<String, Object>>();
        for (ChildEntry child : root.children()) {
            var childMap = new LinkedHashMap<String, Object>();
            childMap.put("feature", child.feature());
            childMap.putAll(toJsonTree(child.element()));
            children.add(childMap);
        }
        map.put("children", children);
        return map;
    }

    /**
     * Converts the model element trees to a terminal-readable ASCII containment tree.
     *
     * <p>Example:
     * <pre>
     * System
     * ├── Component  { name="c1" }
     * └── Component  { name="c2" }
     * </pre>
     */
    public static String toAsciiTree(List<ParsedElement> roots) {
        var sb = new StringBuilder();
        for (int r = 0; r < roots.size(); r++) {
            if (r > 0) sb.append("\n");
            appendAsciiNode(roots.get(r), sb, "");
        }
        return sb.toString().stripTrailing();
    }

    private static void appendAsciiNode(ParsedElement el, StringBuilder sb, String prefix) {
        String attrs = formatAttrs(el.attributes());
        sb.append(el.eClass());
        if (!attrs.isEmpty()) sb.append("  { ").append(attrs).append(" }");
        sb.append("\n");

        List<ChildEntry> children = el.children();
        for (int i = 0; i < children.size(); i++) {
            ChildEntry child = children.get(i);
            boolean last = i == children.size() - 1;
            String connector  = last ? "└── " : "├── ";
            String childPfx   = last ? prefix + "    " : prefix + "│   ";
            sb.append(prefix).append(connector);
            appendAsciiNode(child.element(), sb, childPfx);
        }
    }

    private static String formatAttrs(Map<String, String> attrs) {
        if (attrs.isEmpty()) return "";
        var sb = new StringBuilder();
        attrs.forEach((k, v) -> {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(k).append("=\"").append(v).append("\"");
        });
        return sb.toString();
    }

    /**
     * Converts the model element trees to a Mermaid {@code graph TD} diagram string.
     *
     * <p>The string can be embedded directly into an HTML page that loads Mermaid.js,
     * or pasted into https://mermaid.live for immediate rendering.
     *
     * <p>Example:
     * <pre>
     * graph TD
     *   n0["System"]
     *   n1["Component&lt;br/&gt;name=c1"]
     *   n0 --&gt;|"components"| n1
     * </pre>
     */
    public static String toMermaid(List<ParsedElement> roots) {
        var sb = new StringBuilder("graph TD\n");
        var counter = new AtomicInteger(0);
        for (ParsedElement root : roots) {
            appendMermaidNode(root, sb, counter, null, null);
        }
        return sb.toString();
    }

    private static String appendMermaidNode(ParsedElement el, StringBuilder sb,
            AtomicInteger counter, String parentId, String feature) {
        String id = "n" + counter.getAndIncrement();

        // Node label: eClass + first attribute as hint (e.g. "Component<br/>name=c1")
        var label = new StringBuilder(escapeHtml(el.eClass()));
        if (!el.attributes().isEmpty()) {
            var first = el.attributes().entrySet().iterator().next();
            label.append("<br/>").append(escapeHtml(first.getKey()))
                 .append("=").append(escapeHtml(first.getValue()));
        }
        sb.append("  ").append(id).append("[\"").append(label).append("\"]\n");

        if (parentId != null) {
            sb.append("  ").append(parentId)
              .append(" -->|\"").append(escapeHtml(feature)).append("\"| ")
              .append(id).append("\n");
        }
        for (ChildEntry child : el.children()) {
            appendMermaidNode(child.element(), sb, counter, id, child.feature());
        }
        return id;
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private ModelFormatter() {}
}
