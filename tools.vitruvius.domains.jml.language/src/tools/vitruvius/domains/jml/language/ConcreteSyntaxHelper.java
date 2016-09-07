package tools.vitruvius.domains.jml.language;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.parser.IParseResult;

import com.google.inject.Guice;

import tools.vitruvius.domains.jml.language.generator.JMLToString;
import tools.vitruvius.domains.jml.language.jML.BlockStatement;
import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.parser.antlr.JMLParser;

/**
 * Utility class for converting an excerpt of concrete JML syntax into a model part. The supported
 * model elements are located in a internal map.
 */
public class ConcreteSyntaxHelper {

    private static final JMLParser JML_PARSER = Guice.createInjector(new JMLRuntimeModule()).getInstance(
            JMLParser.class);
    private static final Map<Class<? extends EObject>, ParserRule> CLASS_TO_PARSER_RULE = createMapping();

    /**
     * Converts the given JML object to its concrete syntax.
     * @param obj The JML object.
     * @return The concrete syntax.
     */
    public static String convertToConcreteSyntax(EObject obj) {
        return JMLToString.valueOf(obj);
    }
    
    /**
     * Converts a excerpt of concrete JML syntax to model elements. The excerpt has to exactly
     * describe the model element. Otherwise there will be a parsing error.
     * 
     * @param jmlConcreteSyntax
     *            The concrete syntax excerpt.
     * @param eobjectType
     *            The class of the model element, which is described by the concrete syntax.
     * @param <T>
     *            Type parameter for the model element.
     * @throws Exception
     *             In case of a parsing error.
     * @return The parsed object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends EObject> T convertFromConcreteSyntax(CharSequence jmlConcreteSyntax, Class<T> eobjectType)
            throws Exception {
        InputStream inputStream = IOUtils.toInputStream(jmlConcreteSyntax);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        ParserRule startRule = CLASS_TO_PARSER_RULE.get(eobjectType);
        if (startRule == null) {
            startRule = CLASS_TO_PARSER_RULE.get(null);
        }

        try {
            IParseResult parseResult = JML_PARSER.parse(startRule, inputStreamReader);
            return (T) parseResult.getRootASTElement();
        } catch (WrappedException e) {
            throw e.exception();
        }
    }

    /**
     * Creates a map from classes to parsing rules, which can be used to parse them from concrete
     * syntax.
     * 
     * @return The map.
     */
    private static Map<Class<? extends EObject>, ParserRule> createMapping() {
        Map<Class<? extends EObject>, ParserRule> map = new HashMap<Class<? extends EObject>, ParserRule>();
        map.put(BlockStatement.class, JML_PARSER.getGrammarAccess().getBlockStatementRule());
        map.put(Expression.class, JML_PARSER.getGrammarAccess().getExpressionRule());
        map.put(null, JML_PARSER.getGrammarAccess().getCompilationUnitRule());
        return map;
    }
}
