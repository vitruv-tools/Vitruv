package tools.vitruv.framework.remote.server.rest.endpoints.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * DOM-based parser for EMF XMI model files.
 * Has no dependency on EMF. Reads XMI as plain XML and extracts eClass, attributes,
 * and containment structure. Used by the version model and view endpoints.
 */
public class XmiModelParser {

    private static final String NS_XMI = "http://www.omg.org/XMI";
    private static final String NS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String NS_XMLNS = "http://www.w3.org/2000/xmlns/";

    /**
     * A parsed EMF element: its resolved eClass name, non-XMI attributes, and containment children.
     */
    public record ParsedElement(
            String eClass,
            Map<String, String> attributes,
            List<ChildEntry> children) {}

    /**
     * A containment child together with the feature name that contains it.
     *
     * <p>In EMF XMI the XML tag name of a child element IS the containment feature name.
     * The child's actual type is resolved from {@code xmi:type} or the tag name itself.
     */
    public record ChildEntry(String feature, ParsedElement element) {}

    /**
     * Parses an XMI byte array and returns the top-level model elements.
     * Handles both bare-root XMI (single root element = root EObject) and
     * wrapped XMI ({@code <xmi:XMI>} container with multiple children).
     *
     * @param xmiBytes raw bytes of the XMI file
     * @return list of top-level ParsedElement (usually one entry per .model file)
     * @throws ParserConfigurationException if the XML parser cannot be configured
     * @throws SAXException if the XMI content is malformed XML
     * @throws IOException if reading the byte array fails
     */
    public static List<ParsedElement> parse(byte[] xmiBytes)
            throws ParserConfigurationException, SAXException, IOException {
        var factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        var builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new DefaultHandler());
        var doc = builder.parse(new ByteArrayInputStream(xmiBytes));
        var root = doc.getDocumentElement();

        if ("XMI".equals(root.getLocalName())) {
            var roots = new ArrayList<ParsedElement>();
            NodeList children = root.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child instanceof Element e) {
                    roots.add(parseElement(e).element());
                }
            }
            return roots;
        }
        return List.of(parseElement(root).element());
    }

    private static ChildEntry parseElement(Element el) {
        String featureName = el.getLocalName();
        String eClass = resolveEClass(el);
        Map<String, String> attributes = extractAttributes(el);
        var children = new ArrayList<ChildEntry>();

        NodeList childNodes = el.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child instanceof Element childEl) {
                children.add(parseElement(childEl));
            }
        }
        return new ChildEntry(featureName, new ParsedElement(eClass, attributes, children));
    }

    private static String resolveEClass(Element el) {
        // xmi:type is the authoritative type annotation (e.g. "model:Router" -> "Router")
        String xmiType = el.getAttributeNS(NS_XMI, "type");
        if (xmiType == null || xmiType.isBlank()) {
            xmiType = el.getAttributeNS(NS_XSI, "type");
        }
        if (xmiType != null && !xmiType.isBlank()) {
            int colon = xmiType.indexOf(':');
            return colon >= 0 ? xmiType.substring(colon + 1) : xmiType;
        }
        // Fall back to the element's local name (strips namespace prefix)
        return el.getLocalName();
    }

    private static Map<String, String> extractAttributes(Element el) {
        var attrs = new LinkedHashMap<String, String>();
        NamedNodeMap nodeMap = el.getAttributes();
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node attr = nodeMap.item(i);
            String ns = attr.getNamespaceURI();
            // Skip XMI internals, schema-instance annotations, and namespace declarations
            if (NS_XMI.equals(ns) || NS_XSI.equals(ns) || NS_XMLNS.equals(ns)) continue;
            String name = attr.getNodeName();
            if (name.startsWith("xmlns")) continue;
            attrs.put(attr.getLocalName() != null ? attr.getLocalName() : name, attr.getNodeValue());
        }
        return attrs;
    }

    private XmiModelParser() {}
}
