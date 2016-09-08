// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g 2012-09-27 09:54:49

package org.palladiosimulator.pcm.stochasticexpressions.parser;

import java.util.ArrayList;
import java.util.Collection;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.palladiosimulator.pcm.parameter.CharacterisedVariable;
import org.palladiosimulator.pcm.parameter.ParameterFactory;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;

import de.uka.ipd.sdq.probfunction.BoxedPDF;
import de.uka.ipd.sdq.probfunction.ContinuousSample;
import de.uka.ipd.sdq.probfunction.ProbabilityFunction;
import de.uka.ipd.sdq.probfunction.ProbabilityMassFunction;
import de.uka.ipd.sdq.probfunction.ProbfunctionFactory;
import de.uka.ipd.sdq.probfunction.Sample;
import de.uka.ipd.sdq.stoex.AbstractNamedReference;
import de.uka.ipd.sdq.stoex.Atom;
import de.uka.ipd.sdq.stoex.BoolLiteral;
import de.uka.ipd.sdq.stoex.BooleanExpression;
import de.uka.ipd.sdq.stoex.BooleanOperations;
import de.uka.ipd.sdq.stoex.BooleanOperatorExpression;
import de.uka.ipd.sdq.stoex.CompareExpression;
import de.uka.ipd.sdq.stoex.CompareOperations;
import de.uka.ipd.sdq.stoex.Comparison;
import de.uka.ipd.sdq.stoex.DoubleLiteral;
import de.uka.ipd.sdq.stoex.Expression;
import de.uka.ipd.sdq.stoex.FunctionLiteral;
import de.uka.ipd.sdq.stoex.IfElse;
import de.uka.ipd.sdq.stoex.IfElseExpression;
import de.uka.ipd.sdq.stoex.IntLiteral;
import de.uka.ipd.sdq.stoex.NamespaceReference;
import de.uka.ipd.sdq.stoex.NegativeExpression;
import de.uka.ipd.sdq.stoex.NotExpression;
import de.uka.ipd.sdq.stoex.Parenthesis;
import de.uka.ipd.sdq.stoex.Power;
import de.uka.ipd.sdq.stoex.PowerExpression;
import de.uka.ipd.sdq.stoex.ProbabilityFunctionLiteral;
import de.uka.ipd.sdq.stoex.Product;
import de.uka.ipd.sdq.stoex.ProductExpression;
import de.uka.ipd.sdq.stoex.ProductOperations;
import de.uka.ipd.sdq.stoex.StoexFactory;
import de.uka.ipd.sdq.stoex.StringLiteral;
import de.uka.ipd.sdq.stoex.Term;
import de.uka.ipd.sdq.stoex.TermExpression;
import de.uka.ipd.sdq.stoex.TermOperations;
import de.uka.ipd.sdq.stoex.Unary;
import de.uka.ipd.sdq.stoex.VariableReference;

//import de.uka.ipd.sdq.stoex.analyser.visitors.StoExPrettyPrintVisitor;

public class PCMStoExParser extends Parser {

    public static final String[] tokenNames = new String[] { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AND", "OR", "XOR",
            "GREATER", "LESS", "EQUAL", "NOTEQUAL", "GREATEREQUAL", "LESSEQUAL", "PLUS", "MINUS", "MUL", "DIV", "MOD",
            "POW", "NOT", "NUMBER", "STRING_LITERAL", "DOT", "ID", "LPAREN", "RPAREN", "COLON", "INTPMF",
            "SQUARE_PAREN_L", "SQUARE_PAREN_R", "DOUBLEPMF", "ENUMPMF", "ORDERED_DEF", "DOUBLEPDF", "BOOLPMF", "SEMI",
            "FALSE", "TRUE", "BYTESIZE", "STRUCTURE", "NUMBER_OF_ELEMENTS", "TYPE", "VALUE", "INNER", "UNIT", "BOOL",
            "DEFINITION", "DIGIT", "Exponent", "ALPHA", "IDSTART", "IDPART", "WS", "COMMENT", "LINE_COMMENT", "'?'",
            "':'" };
    public static final int INNER = 43;
    public static final int MOD = 17;
    public static final int BOOLPMF = 34;
    public static final int Exponent = 48;
    public static final int GREATEREQUAL = 11;
    public static final int ENUMPMF = 31;
    public static final int NOT = 19;
    public static final int AND = 4;
    public static final int ID = 23;
    public static final int EOF = -1;
    public static final int STRUCTURE = 39;
    public static final int LPAREN = 24;
    public static final int TYPE = 41;
    public static final int NOTEQUAL = 10;
    public static final int T__55 = 55;
    public static final int T__56 = 56;
    public static final int RPAREN = 25;
    public static final int GREATER = 7;
    public static final int STRING_LITERAL = 21;
    public static final int POW = 18;
    public static final int EQUAL = 9;
    public static final int LESS = 8;
    public static final int DEFINITION = 46;
    public static final int PLUS = 13;
    public static final int DIGIT = 47;
    public static final int COMMENT = 53;
    public static final int DOT = 22;
    public static final int DOUBLEPDF = 33;
    public static final int XOR = 6;
    public static final int INTPMF = 27;
    public static final int LINE_COMMENT = 54;
    public static final int DOUBLEPMF = 30;
    public static final int BOOL = 45;
    public static final int NUMBER = 20;
    public static final int NUMBER_OF_ELEMENTS = 40;
    public static final int VALUE = 42;
    public static final int MINUS = 14;
    public static final int MUL = 15;
    public static final int SEMI = 35;
    public static final int TRUE = 37;
    public static final int ALPHA = 49;
    public static final int COLON = 26;
    public static final int SQUARE_PAREN_R = 29;
    public static final int UNIT = 44;
    public static final int WS = 52;
    public static final int SQUARE_PAREN_L = 28;
    public static final int OR = 5;
    public static final int IDPART = 51;
    public static final int IDSTART = 50;
    public static final int BYTESIZE = 38;
    public static final int DIV = 16;
    public static final int ORDERED_DEF = 32;
    public static final int FALSE = 36;
    public static final int LESSEQUAL = 12;

    // delegates
    // delegators

    public PCMStoExParser(final TokenStream input) {
        this(input, new RecognizerSharedState());
    }

    public PCMStoExParser(final TokenStream input, final RecognizerSharedState state) {
        super(input, state);

    }

    @Override
    public String[] getTokenNames() {
        return PCMStoExParser.tokenNames;
    }

    @Override
    public String getGrammarFileName() {
        return "/Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g";
    }

    // $ANTLR start "expression"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:19:1:
    // expression returns [Expression exp] : c= ifelseExpr EOF ;
    public final Expression expression() throws RecognitionException {
        Expression exp = null;

        IfElse c = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:20:3:
            // (c= ifelseExpr EOF )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:21:3:
            // c= ifelseExpr EOF
            {
                pushFollow(FOLLOW_ifelseExpr_in_expression41);
                c = ifelseExpr();

                this.state._fsp--;

                match(this.input, EOF, FOLLOW_EOF_in_expression43);
                exp = c;

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return exp;
    }

    // $ANTLR end "expression"

    // $ANTLR start "ifelseExpr"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:24:1:
    // ifelseExpr returns [IfElse ifelseExp] : cond= boolAndExpr ( '?' ifEx= boolAndExpr ':' elseEx=
    // boolAndExpr )? ;
    public final IfElse ifelseExpr() throws RecognitionException {
        IfElse ifelseExp = null;

        BooleanExpression cond = null;

        BooleanExpression ifEx = null;

        BooleanExpression elseEx = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:25:2:
            // (cond= boolAndExpr ( '?' ifEx= boolAndExpr ':' elseEx= boolAndExpr )? )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:26:2:
            // cond= boolAndExpr ( '?' ifEx= boolAndExpr ':' elseEx= boolAndExpr )?
            {
                pushFollow(FOLLOW_boolAndExpr_in_ifelseExpr66);
                cond = boolAndExpr();

                this.state._fsp--;

                ifelseExp = cond;
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:27:3:
                // ( '?' ifEx= boolAndExpr ':' elseEx= boolAndExpr )?
                int alt1 = 2;
                final int LA1_0 = this.input.LA(1);

                if ((LA1_0 == 55)) {
                    alt1 = 1;
                }
                switch (alt1) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:27:4:
                // '?' ifEx= boolAndExpr ':' elseEx= boolAndExpr
                {
                    final IfElseExpression newIfelseExp = StoexFactory.eINSTANCE.createIfElseExpression();
                    newIfelseExp.setConditionExpression(cond);
                    match(this.input, 55, FOLLOW_55_in_ifelseExpr79);
                    pushFollow(FOLLOW_boolAndExpr_in_ifelseExpr85);
                    ifEx = boolAndExpr();

                    this.state._fsp--;

                    newIfelseExp.setIfExpression(ifEx);
                    match(this.input, 56, FOLLOW_56_in_ifelseExpr89);
                    pushFollow(FOLLOW_boolAndExpr_in_ifelseExpr95);
                    elseEx = boolAndExpr();

                    this.state._fsp--;

                    newIfelseExp.setElseExpression(elseEx);
                    ifelseExp = newIfelseExp;

                }
                    break;

                }

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return ifelseExp;
    }

    // $ANTLR end "ifelseExpr"

    // $ANTLR start "boolAndExpr"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:33:1:
    // boolAndExpr returns [BooleanExpression boolExp] : b1= boolOrExpr ( AND b2= boolOrExpr )* ;
    public final BooleanExpression boolAndExpr() throws RecognitionException {
        BooleanExpression boolExp = null;

        BooleanExpression b1 = null;

        BooleanExpression b2 = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:34:2:
            // (b1= boolOrExpr ( AND b2= boolOrExpr )* )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:35:2:
            // b1= boolOrExpr ( AND b2= boolOrExpr )*
            {
                pushFollow(FOLLOW_boolOrExpr_in_boolAndExpr120);
                b1 = boolOrExpr();

                this.state._fsp--;

                boolExp = b1;
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:36:3:
                // ( AND b2= boolOrExpr )*
                loop2: do {
                    int alt2 = 2;
                    final int LA2_0 = this.input.LA(1);

                    if ((LA2_0 == AND)) {
                        alt2 = 1;
                    }

                    switch (alt2) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:36:4:
                    // AND b2= boolOrExpr
                    {
                        final BooleanOperatorExpression boolExprNew = StoexFactory.eINSTANCE
                                .createBooleanOperatorExpression();
                        match(this.input, AND, FOLLOW_AND_in_boolAndExpr131);
                        boolExprNew.setOperation(BooleanOperations.AND);
                        pushFollow(FOLLOW_boolOrExpr_in_boolAndExpr142);
                        b2 = boolOrExpr();

                        this.state._fsp--;

                        boolExprNew.setLeft(b1);
                        boolExprNew.setRight(b2);
                        boolExp = boolExprNew;

                    }
                        break;

                    default:
                        break loop2;
                    }
                } while (true);

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return boolExp;
    }

    // $ANTLR end "boolAndExpr"

    // $ANTLR start "boolOrExpr"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:41:1:
    // boolOrExpr returns [BooleanExpression boolExp] : b1= compareExpr ( ( OR | XOR ) b2=
    // compareExpr )* ;
    public final BooleanExpression boolOrExpr() throws RecognitionException {
        BooleanExpression boolExp = null;

        Comparison b1 = null;

        Comparison b2 = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:42:2:
            // (b1= compareExpr ( ( OR | XOR ) b2= compareExpr )* )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:43:2:
            // b1= compareExpr ( ( OR | XOR ) b2= compareExpr )*
            {
                pushFollow(FOLLOW_compareExpr_in_boolOrExpr169);
                b1 = compareExpr();

                this.state._fsp--;

                boolExp = b1;
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:44:3:
                // ( ( OR | XOR ) b2= compareExpr )*
                loop4: do {
                    int alt4 = 2;
                    final int LA4_0 = this.input.LA(1);

                    if (((LA4_0 >= OR && LA4_0 <= XOR))) {
                        alt4 = 1;
                    }

                    switch (alt4) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:44:5:
                    // ( OR | XOR ) b2= compareExpr
                    {
                        final BooleanOperatorExpression boolExprNew = StoexFactory.eINSTANCE
                                .createBooleanOperatorExpression();
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:45:3:
                        // ( OR | XOR )
                        int alt3 = 2;
                        final int LA3_0 = this.input.LA(1);

                        if ((LA3_0 == OR)) {
                            alt3 = 1;
                        } else if ((LA3_0 == XOR)) {
                            alt3 = 2;
                        } else {
                            final NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);

                            throw nvae;
                        }
                        switch (alt3) {
                        case 1:
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:45:4:
                        // OR
                        {
                            match(this.input, OR, FOLLOW_OR_in_boolOrExpr182);
                            boolExprNew.setOperation(BooleanOperations.OR);

                        }
                            break;
                        case 2:
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:46:4:
                        // XOR
                        {
                            match(this.input, XOR, FOLLOW_XOR_in_boolOrExpr190);
                            boolExprNew.setOperation(BooleanOperations.XOR);

                        }
                            break;

                        }

                        pushFollow(FOLLOW_compareExpr_in_boolOrExpr202);
                        b2 = compareExpr();

                        this.state._fsp--;

                        boolExprNew.setLeft(b1);
                        boolExprNew.setRight(b2);
                        boolExp = boolExprNew;

                    }
                        break;

                    default:
                        break loop4;
                    }
                } while (true);

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return boolExp;
    }

    // $ANTLR end "boolOrExpr"

    // $ANTLR start "compareExpr"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:54:1:
    // compareExpr returns [Comparison comp] : t1= sumExpr ( ( GREATER | LESS | EQUAL | NOTEQUAL |
    // GREATEREQUAL | LESSEQUAL ) t2= sumExpr )? ;
    public final Comparison compareExpr() throws RecognitionException {
        Comparison comp = null;

        Term t1 = null;

        Term t2 = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:55:3:
            // (t1= sumExpr ( ( GREATER | LESS | EQUAL | NOTEQUAL | GREATEREQUAL | LESSEQUAL ) t2=
            // sumExpr )? )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:56:4:
            // t1= sumExpr ( ( GREATER | LESS | EQUAL | NOTEQUAL | GREATEREQUAL | LESSEQUAL ) t2=
            // sumExpr )?
            {
                pushFollow(FOLLOW_sumExpr_in_compareExpr240);
                t1 = sumExpr();

                this.state._fsp--;

                comp = t1;
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:56:30:
                // ( ( GREATER | LESS | EQUAL | NOTEQUAL | GREATEREQUAL | LESSEQUAL ) t2= sumExpr )?
                int alt6 = 2;
                final int LA6_0 = this.input.LA(1);

                if (((LA6_0 >= GREATER && LA6_0 <= LESSEQUAL))) {
                    alt6 = 1;
                }
                switch (alt6) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:57:5:
                // ( GREATER | LESS | EQUAL | NOTEQUAL | GREATEREQUAL | LESSEQUAL ) t2= sumExpr
                {
                    final CompareExpression compExp = StoexFactory.eINSTANCE.createCompareExpression();
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:58:5:
                    // ( GREATER | LESS | EQUAL | NOTEQUAL | GREATEREQUAL | LESSEQUAL )
                    int alt5 = 6;
                    switch (this.input.LA(1)) {
                    case GREATER: {
                        alt5 = 1;
                    }
                        break;
                    case LESS: {
                        alt5 = 2;
                    }
                        break;
                    case EQUAL: {
                        alt5 = 3;
                    }
                        break;
                    case NOTEQUAL: {
                        alt5 = 4;
                    }
                        break;
                    case GREATEREQUAL: {
                        alt5 = 5;
                    }
                        break;
                    case LESSEQUAL: {
                        alt5 = 6;
                    }
                        break;
                    default:
                        final NoViableAltException nvae = new NoViableAltException("", 5, 0, this.input);

                        throw nvae;
                    }

                    switch (alt5) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:58:6:
                    // GREATER
                    {
                        match(this.input, GREATER, FOLLOW_GREATER_in_compareExpr257);
                        compExp.setOperation(CompareOperations.GREATER);

                    }
                        break;
                    case 2:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:59:6:
                    // LESS
                    {
                        match(this.input, LESS, FOLLOW_LESS_in_compareExpr267);
                        compExp.setOperation(CompareOperations.LESS);

                    }
                        break;
                    case 3:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:60:6:
                    // EQUAL
                    {
                        match(this.input, EQUAL, FOLLOW_EQUAL_in_compareExpr277);
                        compExp.setOperation(CompareOperations.EQUALS);

                    }
                        break;
                    case 4:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:61:6:
                    // NOTEQUAL
                    {
                        match(this.input, NOTEQUAL, FOLLOW_NOTEQUAL_in_compareExpr287);
                        compExp.setOperation(CompareOperations.NOTEQUAL);

                    }
                        break;
                    case 5:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:62:6:
                    // GREATEREQUAL
                    {
                        match(this.input, GREATEREQUAL, FOLLOW_GREATEREQUAL_in_compareExpr297);
                        compExp.setOperation(CompareOperations.GREATEREQUAL);

                    }
                        break;
                    case 6:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:63:6:
                    // LESSEQUAL
                    {
                        match(this.input, LESSEQUAL, FOLLOW_LESSEQUAL_in_compareExpr307);
                        compExp.setOperation(CompareOperations.LESSEQUAL);

                    }
                        break;

                    }

                    pushFollow(FOLLOW_sumExpr_in_compareExpr322);
                    t2 = sumExpr();

                    this.state._fsp--;

                    compExp.setLeft(t1);
                    compExp.setRight(t2);
                    comp = compExp;

                }
                    break;

                }

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return comp;
    }

    // $ANTLR end "compareExpr"

    // $ANTLR start "sumExpr"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:67:1:
    // sumExpr returns [Term t] : p1= prodExpr ( ( PLUS | MINUS ) p2= prodExpr )? ;
    public final Term sumExpr() throws RecognitionException {
        Term t = null;

        Product p1 = null;

        Product p2 = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:68:3:
            // (p1= prodExpr ( ( PLUS | MINUS ) p2= prodExpr )? )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:69:2:
            // p1= prodExpr ( ( PLUS | MINUS ) p2= prodExpr )?
            {
                pushFollow(FOLLOW_prodExpr_in_sumExpr354);
                p1 = prodExpr();

                this.state._fsp--;

                t = p1;
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:69:26:
                // ( ( PLUS | MINUS ) p2= prodExpr )?
                int alt8 = 2;
                final int LA8_0 = this.input.LA(1);

                if (((LA8_0 >= PLUS && LA8_0 <= MINUS))) {
                    alt8 = 1;
                }
                switch (alt8) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:70:4:
                // ( PLUS | MINUS ) p2= prodExpr
                {
                    final TermExpression termExp = StoexFactory.eINSTANCE.createTermExpression();
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:71:4:
                    // ( PLUS | MINUS )
                    int alt7 = 2;
                    final int LA7_0 = this.input.LA(1);

                    if ((LA7_0 == PLUS)) {
                        alt7 = 1;
                    } else if ((LA7_0 == MINUS)) {
                        alt7 = 2;
                    } else {
                        final NoViableAltException nvae = new NoViableAltException("", 7, 0, this.input);

                        throw nvae;
                    }
                    switch (alt7) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:71:5:
                    // PLUS
                    {
                        match(this.input, PLUS, FOLLOW_PLUS_in_sumExpr372);
                        termExp.setOperation(TermOperations.ADD);

                    }
                        break;
                    case 2:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:72:4:
                    // MINUS
                    {
                        match(this.input, MINUS, FOLLOW_MINUS_in_sumExpr380);
                        termExp.setOperation(TermOperations.SUB);

                    }
                        break;

                    }

                    pushFollow(FOLLOW_prodExpr_in_sumExpr393);
                    p2 = prodExpr();

                    this.state._fsp--;

                    termExp.setLeft(t);
                    termExp.setRight(p2);
                    t = termExp;

                }
                    break;

                }

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return t;
    }

    // $ANTLR end "sumExpr"

    // $ANTLR start "prodExpr"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:78:1:
    // prodExpr returns [Product p] : pw1= powExpr ( ( MUL | DIV | MOD ) pw2= powExpr )* ;
    public final Product prodExpr() throws RecognitionException {
        Product p = null;

        Power pw1 = null;

        Power pw2 = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:79:3:
            // (pw1= powExpr ( ( MUL | DIV | MOD ) pw2= powExpr )* )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:80:3:
            // pw1= powExpr ( ( MUL | DIV | MOD ) pw2= powExpr )*
            {
                pushFollow(FOLLOW_powExpr_in_prodExpr431);
                pw1 = powExpr();

                this.state._fsp--;

                p = pw1;
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:81:4:
                // ( ( MUL | DIV | MOD ) pw2= powExpr )*
                loop10: do {
                    int alt10 = 2;
                    final int LA10_0 = this.input.LA(1);

                    if (((LA10_0 >= MUL && LA10_0 <= MOD))) {
                        alt10 = 1;
                    }

                    switch (alt10) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:82:4:
                    // ( MUL | DIV | MOD ) pw2= powExpr
                    {
                        final ProductExpression prodExp = StoexFactory.eINSTANCE.createProductExpression();
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:83:5:
                        // ( MUL | DIV | MOD )
                        int alt9 = 3;
                        switch (this.input.LA(1)) {
                        case MUL: {
                            alt9 = 1;
                        }
                            break;
                        case DIV: {
                            alt9 = 2;
                        }
                            break;
                        case MOD: {
                            alt9 = 3;
                        }
                            break;
                        default:
                            final NoViableAltException nvae = new NoViableAltException("", 9, 0, this.input);

                            throw nvae;
                        }

                        switch (alt9) {
                        case 1:
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:83:6:
                        // MUL
                        {
                            match(this.input, MUL, FOLLOW_MUL_in_prodExpr451);
                            prodExp.setOperation(ProductOperations.MULT);

                        }
                            break;
                        case 2:
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:84:6:
                        // DIV
                        {
                            match(this.input, DIV, FOLLOW_DIV_in_prodExpr462);
                            prodExp.setOperation(ProductOperations.DIV);

                        }
                            break;
                        case 3:
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:85:6:
                        // MOD
                        {
                            match(this.input, MOD, FOLLOW_MOD_in_prodExpr473);
                            prodExp.setOperation(ProductOperations.MOD);

                        }
                            break;

                        }

                        pushFollow(FOLLOW_powExpr_in_prodExpr489);
                        pw2 = powExpr();

                        this.state._fsp--;

                        prodExp.setLeft(p);
                        prodExp.setRight(pw2);
                        p = prodExp;

                    }
                        break;

                    default:
                        break loop10;
                    }
                } while (true);

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return p;
    }

    // $ANTLR end "prodExpr"

    // $ANTLR start "powExpr"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:91:1:
    // powExpr returns [Power pw] : a1= unaryExpr ( POW a2= unaryExpr )? ;
    public final Power powExpr() throws RecognitionException {
        Power pw = null;

        Unary a1 = null;

        Unary a2 = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:92:3:
            // (a1= unaryExpr ( POW a2= unaryExpr )? )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:93:3:
            // a1= unaryExpr ( POW a2= unaryExpr )?
            {
                pushFollow(FOLLOW_unaryExpr_in_powExpr530);
                a1 = unaryExpr();

                this.state._fsp--;

                pw = a1;
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:94:4:
                // ( POW a2= unaryExpr )?
                int alt11 = 2;
                final int LA11_0 = this.input.LA(1);

                if ((LA11_0 == POW)) {
                    alt11 = 1;
                }
                switch (alt11) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:94:5:
                // POW a2= unaryExpr
                {
                    match(this.input, POW, FOLLOW_POW_in_powExpr539);
                    pushFollow(FOLLOW_unaryExpr_in_powExpr545);
                    a2 = unaryExpr();

                    this.state._fsp--;

                    final PowerExpression pwExp = StoexFactory.eINSTANCE.createPowerExpression();
                    pwExp.setBase(a1);
                    pwExp.setExponent(a2);
                    pw = pwExp;

                }
                    break;

                }

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return pw;
    }

    // $ANTLR end "powExpr"

    // $ANTLR start "unaryExpr"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:100:1:
    // unaryExpr returns [Unary u] : ( MINUS uminus= unaryExpr | NOT unot= unaryExpr | a= atom );
    public final Unary unaryExpr() throws RecognitionException {
        Unary u = null;

        Unary uminus = null;

        Unary unot = null;

        Atom a = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:100:29:
            // ( MINUS uminus= unaryExpr | NOT unot= unaryExpr | a= atom )
            int alt12 = 3;
            switch (this.input.LA(1)) {
            case MINUS: {
                alt12 = 1;
            }
                break;
            case NOT: {
                alt12 = 2;
            }
                break;
            case NUMBER:
            case STRING_LITERAL:
            case ID:
            case LPAREN:
            case INTPMF:
            case DOUBLEPMF:
            case ENUMPMF:
            case DOUBLEPDF:
            case BOOLPMF:
            case FALSE:
            case TRUE: {
                alt12 = 3;
            }
                break;
            default:
                final NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);

                throw nvae;
            }

            switch (alt12) {
            case 1:
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:102:5:
            // MINUS uminus= unaryExpr
            {
                match(this.input, MINUS, FOLLOW_MINUS_in_unaryExpr581);
                pushFollow(FOLLOW_unaryExpr_in_unaryExpr587);
                uminus = unaryExpr();

                this.state._fsp--;

                final NegativeExpression ne = StoexFactory.eINSTANCE.createNegativeExpression();
                ne.setInner(uminus);
                u = ne;

            }
                break;
            case 2:
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:107:5:
            // NOT unot= unaryExpr
            {
                match(this.input, NOT, FOLLOW_NOT_in_unaryExpr605);
                pushFollow(FOLLOW_unaryExpr_in_unaryExpr611);
                unot = unaryExpr();

                this.state._fsp--;

                final NotExpression no = StoexFactory.eINSTANCE.createNotExpression();
                no.setInner(unot);
                u = no;

            }
                break;
            case 3:
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:112:5:
            // a= atom
            {
                pushFollow(FOLLOW_atom_in_unaryExpr633);
                a = atom();

                this.state._fsp--;

                u = a;

            }
                break;

            }
        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return u;
    }

    // $ANTLR end "unaryExpr"

    // $ANTLR start "atom"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:114:1:
    // atom returns [Atom a] : (number= NUMBER | def= definition | sl= STRING_LITERAL | bl=
    // boolean_keywords | id= scoped_id DOT type= characterisation | fid= ID args= arguments |
    // LPAREN inner= ifelseExpr RPAREN ) ;
    public final Atom atom() throws RecognitionException {
        Atom a = null;

        Token number = null;
        Token sl = null;
        Token fid = null;
        ProbabilityFunctionLiteral def = null;

        String bl = null;

        AbstractNamedReference id = null;

        VariableCharacterisationType type = null;

        Collection<Expression> args = null;

        IfElse inner = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:115:3:
            // ( (number= NUMBER | def= definition | sl= STRING_LITERAL | bl= boolean_keywords | id=
            // scoped_id DOT type= characterisation | fid= ID args= arguments | LPAREN inner=
            // ifelseExpr RPAREN ) )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:116:3:
            // (number= NUMBER | def= definition | sl= STRING_LITERAL | bl= boolean_keywords | id=
            // scoped_id DOT type= characterisation | fid= ID args= arguments | LPAREN inner=
            // ifelseExpr RPAREN )
            {
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:116:3:
                // (number= NUMBER | def= definition | sl= STRING_LITERAL | bl= boolean_keywords |
                // id= scoped_id DOT type= characterisation | fid= ID args= arguments | LPAREN
                // inner= ifelseExpr RPAREN )
                int alt13 = 7;
                switch (this.input.LA(1)) {
                case NUMBER: {
                    alt13 = 1;
                }
                    break;
                case INTPMF:
                case DOUBLEPMF:
                case ENUMPMF:
                case DOUBLEPDF:
                case BOOLPMF: {
                    alt13 = 2;
                }
                    break;
                case STRING_LITERAL: {
                    alt13 = 3;
                }
                    break;
                case FALSE:
                case TRUE: {
                    alt13 = 4;
                }
                    break;
                case ID: {
                    final int LA13_5 = this.input.LA(2);

                    if ((LA13_5 == LPAREN)) {
                        alt13 = 6;
                    } else if ((LA13_5 == DOT)) {
                        alt13 = 5;
                    } else {
                        final NoViableAltException nvae = new NoViableAltException("", 13, 5, this.input);

                        throw nvae;
                    }
                }
                    break;
                case LPAREN: {
                    alt13 = 7;
                }
                    break;
                default:
                    final NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);

                    throw nvae;
                }

                switch (alt13) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:118:5:
                // number= NUMBER
                {
                    number = (Token) match(this.input, NUMBER, FOLLOW_NUMBER_in_atom668);

                    final String value = number.getText();
                    if (value.indexOf('.') < 0) {
                        final IntLiteral il = StoexFactory.eINSTANCE.createIntLiteral();
                        il.setValue(Integer.parseInt(value));
                        a = il;
                    } else {
                        final DoubleLiteral dl = StoexFactory.eINSTANCE.createDoubleLiteral();
                        dl.setValue(Double.parseDouble(value));
                        a = dl;
                    }

                }
                    break;
                case 2:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:136:5:
                // def= definition
                {
                    pushFollow(FOLLOW_definition_in_atom695);
                    def = definition();

                    this.state._fsp--;

                    a = def;

                }
                    break;
                case 3:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:140:5:
                // sl= STRING_LITERAL
                {
                    sl = (Token) match(this.input, STRING_LITERAL, FOLLOW_STRING_LITERAL_in_atom720);

                    final StringLiteral stringLiteral = StoexFactory.eINSTANCE.createStringLiteral();
                    stringLiteral.setValue(sl.getText().replace("\"", ""));
                    a = stringLiteral;

                }
                    break;
                case 4:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:148:5:
                // bl= boolean_keywords
                {
                    pushFollow(FOLLOW_boolean_keywords_in_atom747);
                    bl = boolean_keywords();

                    this.state._fsp--;

                    final BoolLiteral boolLiteral = StoexFactory.eINSTANCE.createBoolLiteral();
                    boolLiteral.setValue(bl.equals("true"));
                    a = boolLiteral;

                }
                    break;
                case 5:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:156:5:
                // id= scoped_id DOT type= characterisation
                {
                    pushFollow(FOLLOW_scoped_id_in_atom775);
                    id = scoped_id();

                    this.state._fsp--;

                    match(this.input, DOT, FOLLOW_DOT_in_atom777);
                    pushFollow(FOLLOW_characterisation_in_atom783);
                    type = characterisation();

                    this.state._fsp--;

                    a = ParameterFactory.eINSTANCE.createCharacterisedVariable();
                    ((CharacterisedVariable) a).setId_Variable(id);
                    ((CharacterisedVariable) a).setCharacterisationType(type);

                }
                    break;
                case 6:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:163:5:
                // fid= ID args= arguments
                {
                    fid = (Token) match(this.input, ID, FOLLOW_ID_in_atom810);
                    final FunctionLiteral flit = StoexFactory.eINSTANCE.createFunctionLiteral();
                    flit.setId(fid.getText());
                    pushFollow(FOLLOW_arguments_in_atom823);
                    args = arguments();

                    this.state._fsp--;

                    flit.getParameters_FunctionLiteral().addAll(args);
                    a = flit;

                }
                    break;
                case 7:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:170:5:
                // LPAREN inner= ifelseExpr RPAREN
                {
                    match(this.input, LPAREN, FOLLOW_LPAREN_in_atom848);
                    pushFollow(FOLLOW_ifelseExpr_in_atom858);
                    inner = ifelseExpr();

                    this.state._fsp--;

                    match(this.input, RPAREN, FOLLOW_RPAREN_in_atom864);

                    final Parenthesis paren = StoexFactory.eINSTANCE.createParenthesis();
                    paren.setInnerExpression(inner);
                    a = paren;

                }
                    break;

                }

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return a;
    }

    // $ANTLR end "atom"

    // $ANTLR start "arguments"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:181:1:
    // arguments returns [Collection<Expression> parameters] : LPAREN (paramList= expressionList )?
    // RPAREN ;
    public final Collection<Expression> arguments() throws RecognitionException {
        Collection<Expression> parameters = null;

        Collection<Expression> paramList = null;

        parameters = new ArrayList<Expression>();
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:183:2:
            // ( LPAREN (paramList= expressionList )? RPAREN )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:184:2:
            // LPAREN (paramList= expressionList )? RPAREN
            {
                match(this.input, LPAREN, FOLLOW_LPAREN_in_arguments910);
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:184:19:
                // (paramList= expressionList )?
                int alt14 = 2;
                final int LA14_0 = this.input.LA(1);

                if ((LA14_0 == MINUS || (LA14_0 >= NOT && LA14_0 <= STRING_LITERAL)
                        || (LA14_0 >= ID && LA14_0 <= LPAREN) || LA14_0 == INTPMF
                        || (LA14_0 >= DOUBLEPMF && LA14_0 <= ENUMPMF) || (LA14_0 >= DOUBLEPDF && LA14_0 <= BOOLPMF)
                        || (LA14_0 >= FALSE && LA14_0 <= TRUE))) {
                    alt14 = 1;
                }
                switch (alt14) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:184:19:
                // paramList= expressionList
                {
                    pushFollow(FOLLOW_expressionList_in_arguments916);
                    paramList = expressionList();

                    this.state._fsp--;

                }
                    break;

                }

                parameters.addAll(paramList);
                match(this.input, RPAREN, FOLLOW_RPAREN_in_arguments921);

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return parameters;
    }

    // $ANTLR end "arguments"

    // $ANTLR start "expressionList"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:187:1:
    // expressionList returns [Collection<Expression> parameters] : p1= boolAndExpr ( COLON p2=
    // boolAndExpr )* ;
    public final Collection<Expression> expressionList() throws RecognitionException {
        Collection<Expression> parameters = null;

        BooleanExpression p1 = null;

        BooleanExpression p2 = null;

        parameters = new ArrayList<Expression>();
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:189:2:
            // (p1= boolAndExpr ( COLON p2= boolAndExpr )* )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:190:7:
            // p1= boolAndExpr ( COLON p2= boolAndExpr )*
            {
                pushFollow(FOLLOW_boolAndExpr_in_expressionList959);
                p1 = boolAndExpr();

                this.state._fsp--;

                parameters.add(p1);
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:190:46:
                // ( COLON p2= boolAndExpr )*
                loop15: do {
                    int alt15 = 2;
                    final int LA15_0 = this.input.LA(1);

                    if ((LA15_0 == COLON)) {
                        alt15 = 1;
                    }

                    switch (alt15) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:190:47:
                    // COLON p2= boolAndExpr
                    {
                        match(this.input, COLON, FOLLOW_COLON_in_expressionList964);
                        pushFollow(FOLLOW_boolAndExpr_in_expressionList970);
                        p2 = boolAndExpr();

                        this.state._fsp--;

                        parameters.add(p2);

                    }
                        break;

                    default:
                        break loop15;
                    }
                } while (true);

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return parameters;
    }

    // $ANTLR end "expressionList"

    // $ANTLR start "characterisation"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:193:1:
    // characterisation returns [VariableCharacterisationType ct] : type= characterisation_keywords
    // ;
    public final VariableCharacterisationType characterisation() throws RecognitionException {
        VariableCharacterisationType ct = null;

        String type = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:194:3:
            // (type= characterisation_keywords )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:195:2:
            // type= characterisation_keywords
            {
                pushFollow(FOLLOW_characterisation_keywords_in_characterisation1003);
                type = characterisation_keywords();

                this.state._fsp--;

                if (type.equals("TYPE")) {
                    ct = VariableCharacterisationType.TYPE;
                } else if (type.equals("BYTESIZE")) {
                    ct = VariableCharacterisationType.BYTESIZE;
                } else if (type.equals("NUMBER_OF_ELEMENTS")) {
                    ct = VariableCharacterisationType.NUMBER_OF_ELEMENTS;
                } else if (type.equals("VALUE")) {
                    ct = VariableCharacterisationType.VALUE;
                } else if (type.equals("STRUCTURE")) {
                    ct = VariableCharacterisationType.STRUCTURE;
                }

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return ct;
    }

    // $ANTLR end "characterisation"

    // $ANTLR start "definition"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:209:1:
    // definition returns [ProbabilityFunctionLiteral pfl] : ( INTPMF SQUARE_PAREN_L (isample=
    // numeric_int_sample )+ SQUARE_PAREN_R | DOUBLEPMF SQUARE_PAREN_L (rsample= numeric_real_sample
    // )+ SQUARE_PAREN_R | ENUMPMF ( LPAREN ORDERED_DEF RPAREN )? SQUARE_PAREN_L (ssample=
    // stringsample )+ SQUARE_PAREN_R | DOUBLEPDF SQUARE_PAREN_L (pdf_sample= real_pdf_sample )+
    // SQUARE_PAREN_R | BOOLPMF ( LPAREN ORDERED_DEF RPAREN )? SQUARE_PAREN_L (ssample= boolsample
    // )+ SQUARE_PAREN_R );
    public final ProbabilityFunctionLiteral definition() throws RecognitionException {
        ProbabilityFunctionLiteral pfl = null;

        Sample<Integer> isample = null;

        Sample<Double> rsample = null;

        Sample<Comparable<?>> ssample = null;

        ContinuousSample pdf_sample = null;

        pfl = StoexFactory.eINSTANCE.createProbabilityFunctionLiteral();
        ProbabilityFunction probFunction = null;
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:211:46:
            // ( INTPMF SQUARE_PAREN_L (isample= numeric_int_sample )+ SQUARE_PAREN_R | DOUBLEPMF
            // SQUARE_PAREN_L (rsample= numeric_real_sample )+ SQUARE_PAREN_R | ENUMPMF ( LPAREN
            // ORDERED_DEF RPAREN )? SQUARE_PAREN_L (ssample= stringsample )+ SQUARE_PAREN_R |
            // DOUBLEPDF SQUARE_PAREN_L (pdf_sample= real_pdf_sample )+ SQUARE_PAREN_R | BOOLPMF (
            // LPAREN ORDERED_DEF RPAREN )? SQUARE_PAREN_L (ssample= boolsample )+ SQUARE_PAREN_R )
            int alt23 = 5;
            switch (this.input.LA(1)) {
            case INTPMF: {
                alt23 = 1;
            }
                break;
            case DOUBLEPMF: {
                alt23 = 2;
            }
                break;
            case ENUMPMF: {
                alt23 = 3;
            }
                break;
            case DOUBLEPDF: {
                alt23 = 4;
            }
                break;
            case BOOLPMF: {
                alt23 = 5;
            }
                break;
            default:
                final NoViableAltException nvae = new NoViableAltException("", 23, 0, this.input);

                throw nvae;
            }

            switch (alt23) {
            case 1:
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:215:4:
            // INTPMF SQUARE_PAREN_L (isample= numeric_int_sample )+ SQUARE_PAREN_R
            {
                match(this.input, INTPMF, FOLLOW_INTPMF_in_definition1040);
                probFunction = ProbfunctionFactory.eINSTANCE.createProbabilityMassFunction();
                pfl.setFunction_ProbabilityFunctionLiteral(probFunction);
                match(this.input, SQUARE_PAREN_L, FOLLOW_SQUARE_PAREN_L_in_definition1051);
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:219:5:
                // (isample= numeric_int_sample )+
                int cnt16 = 0;
                loop16: do {
                    int alt16 = 2;
                    final int LA16_0 = this.input.LA(1);

                    if ((LA16_0 == LPAREN)) {
                        alt16 = 1;
                    }

                    switch (alt16) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:220:7:
                    // isample= numeric_int_sample
                    {
                        pushFollow(FOLLOW_numeric_int_sample_in_definition1071);
                        isample = numeric_int_sample();

                        this.state._fsp--;

                        ((ProbabilityMassFunction) probFunction).getSamples().add(isample);

                    }
                        break;

                    default:
                        if (cnt16 >= 1) {
                            break loop16;
                        }
                        final EarlyExitException eee = new EarlyExitException(16, this.input);
                        throw eee;
                    }
                    cnt16++;
                } while (true);

                match(this.input, SQUARE_PAREN_R, FOLLOW_SQUARE_PAREN_R_in_definition1088);

            }
                break;
            case 2:
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:224:5:
            // DOUBLEPMF SQUARE_PAREN_L (rsample= numeric_real_sample )+ SQUARE_PAREN_R
            {
                match(this.input, DOUBLEPMF, FOLLOW_DOUBLEPMF_in_definition1101);
                probFunction = ProbfunctionFactory.eINSTANCE.createProbabilityMassFunction();
                pfl.setFunction_ProbabilityFunctionLiteral(probFunction);
                match(this.input, SQUARE_PAREN_L, FOLLOW_SQUARE_PAREN_L_in_definition1114);
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:228:5:
                // (rsample= numeric_real_sample )+
                int cnt17 = 0;
                loop17: do {
                    int alt17 = 2;
                    final int LA17_0 = this.input.LA(1);

                    if ((LA17_0 == LPAREN)) {
                        alt17 = 1;
                    }

                    switch (alt17) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:229:5:
                    // rsample= numeric_real_sample
                    {
                        pushFollow(FOLLOW_numeric_real_sample_in_definition1132);
                        rsample = numeric_real_sample();

                        this.state._fsp--;

                        ((ProbabilityMassFunction) probFunction).getSamples().add(rsample);

                    }
                        break;

                    default:
                        if (cnt17 >= 1) {
                            break loop17;
                        }
                        final EarlyExitException eee = new EarlyExitException(17, this.input);
                        throw eee;
                    }
                    cnt17++;
                } while (true);

                match(this.input, SQUARE_PAREN_R, FOLLOW_SQUARE_PAREN_R_in_definition1149);

            }
                break;
            case 3:
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:234:4:
            // ENUMPMF ( LPAREN ORDERED_DEF RPAREN )? SQUARE_PAREN_L (ssample= stringsample )+
            // SQUARE_PAREN_R
            {
                match(this.input, ENUMPMF, FOLLOW_ENUMPMF_in_definition1163);
                probFunction = ProbfunctionFactory.eINSTANCE.createProbabilityMassFunction();
                pfl.setFunction_ProbabilityFunctionLiteral(probFunction);
                ((ProbabilityMassFunction) probFunction).setOrderedDomain(false);

                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:239:4:
                // ( LPAREN ORDERED_DEF RPAREN )?
                int alt18 = 2;
                final int LA18_0 = this.input.LA(1);

                if ((LA18_0 == LPAREN)) {
                    alt18 = 1;
                }
                switch (alt18) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:239:5:
                // LPAREN ORDERED_DEF RPAREN
                {
                    match(this.input, LPAREN, FOLLOW_LPAREN_in_definition1176);
                    match(this.input, ORDERED_DEF, FOLLOW_ORDERED_DEF_in_definition1183);
                    ((ProbabilityMassFunction) probFunction).setOrderedDomain(true);
                    match(this.input, RPAREN, FOLLOW_RPAREN_in_definition1195);

                }
                    break;

                }

                match(this.input, SQUARE_PAREN_L, FOLLOW_SQUARE_PAREN_L_in_definition1202);
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:244:5:
                // (ssample= stringsample )+
                int cnt19 = 0;
                loop19: do {
                    int alt19 = 2;
                    final int LA19_0 = this.input.LA(1);

                    if ((LA19_0 == LPAREN)) {
                        alt19 = 1;
                    }

                    switch (alt19) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:245:5:
                    // ssample= stringsample
                    {
                        pushFollow(FOLLOW_stringsample_in_definition1220);
                        ssample = stringsample();

                        this.state._fsp--;

                        ((ProbabilityMassFunction) probFunction).getSamples().add(ssample);

                    }
                        break;

                    default:
                        if (cnt19 >= 1) {
                            break loop19;
                        }
                        final EarlyExitException eee = new EarlyExitException(19, this.input);
                        throw eee;
                    }
                    cnt19++;
                } while (true);

                match(this.input, SQUARE_PAREN_R, FOLLOW_SQUARE_PAREN_R_in_definition1237);

            }
                break;
            case 4:
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:249:4:
            // DOUBLEPDF SQUARE_PAREN_L (pdf_sample= real_pdf_sample )+ SQUARE_PAREN_R
            {
                match(this.input, DOUBLEPDF, FOLLOW_DOUBLEPDF_in_definition1247);
                probFunction = ProbfunctionFactory.eINSTANCE.createBoxedPDF();
                pfl.setFunction_ProbabilityFunctionLiteral(probFunction);
                match(this.input, SQUARE_PAREN_L, FOLLOW_SQUARE_PAREN_L_in_definition1258);
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:253:5:
                // (pdf_sample= real_pdf_sample )+
                int cnt20 = 0;
                loop20: do {
                    int alt20 = 2;
                    final int LA20_0 = this.input.LA(1);

                    if ((LA20_0 == LPAREN)) {
                        alt20 = 1;
                    }

                    switch (alt20) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:254:7:
                    // pdf_sample= real_pdf_sample
                    {
                        pushFollow(FOLLOW_real_pdf_sample_in_definition1278);
                        pdf_sample = real_pdf_sample();

                        this.state._fsp--;

                        ((BoxedPDF) probFunction).getSamples().add(pdf_sample);

                    }
                        break;

                    default:
                        if (cnt20 >= 1) {
                            break loop20;
                        }
                        final EarlyExitException eee = new EarlyExitException(20, this.input);
                        throw eee;
                    }
                    cnt20++;
                } while (true);

                match(this.input, SQUARE_PAREN_R, FOLLOW_SQUARE_PAREN_R_in_definition1295);

            }
                break;
            case 5:
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:258:4:
            // BOOLPMF ( LPAREN ORDERED_DEF RPAREN )? SQUARE_PAREN_L (ssample= boolsample )+
            // SQUARE_PAREN_R
            {
                match(this.input, BOOLPMF, FOLLOW_BOOLPMF_in_definition1306);
                probFunction = ProbfunctionFactory.eINSTANCE.createProbabilityMassFunction();
                pfl.setFunction_ProbabilityFunctionLiteral(probFunction);
                ((ProbabilityMassFunction) probFunction).setOrderedDomain(false);

                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:263:4:
                // ( LPAREN ORDERED_DEF RPAREN )?
                int alt21 = 2;
                final int LA21_0 = this.input.LA(1);

                if ((LA21_0 == LPAREN)) {
                    alt21 = 1;
                }
                switch (alt21) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:263:5:
                // LPAREN ORDERED_DEF RPAREN
                {
                    match(this.input, LPAREN, FOLLOW_LPAREN_in_definition1319);
                    match(this.input, ORDERED_DEF, FOLLOW_ORDERED_DEF_in_definition1326);
                    ((ProbabilityMassFunction) probFunction).setOrderedDomain(true);
                    match(this.input, RPAREN, FOLLOW_RPAREN_in_definition1338);

                }
                    break;

                }

                match(this.input, SQUARE_PAREN_L, FOLLOW_SQUARE_PAREN_L_in_definition1345);
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:268:5:
                // (ssample= boolsample )+
                int cnt22 = 0;
                loop22: do {
                    int alt22 = 2;
                    final int LA22_0 = this.input.LA(1);

                    if ((LA22_0 == LPAREN)) {
                        alt22 = 1;
                    }

                    switch (alt22) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:269:5:
                    // ssample= boolsample
                    {
                        pushFollow(FOLLOW_boolsample_in_definition1363);
                        ssample = boolsample();

                        this.state._fsp--;

                        ((ProbabilityMassFunction) probFunction).getSamples().add(ssample);

                    }
                        break;

                    default:
                        if (cnt22 >= 1) {
                            break loop22;
                        }
                        final EarlyExitException eee = new EarlyExitException(22, this.input);
                        throw eee;
                    }
                    cnt22++;
                } while (true);

                match(this.input, SQUARE_PAREN_R, FOLLOW_SQUARE_PAREN_R_in_definition1380);

            }
                break;

            }
        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return pfl;
    }

    // $ANTLR end "definition"

    // $ANTLR start "numeric_int_sample"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:274:1:
    // numeric_int_sample returns [Sample s] : LPAREN n= signed_number SEMI n2= NUMBER RPAREN ;
    public final Sample<Integer> numeric_int_sample() throws RecognitionException {
        Sample<Integer> s = null;

        Token n2 = null;
        String n = null;

        s = null;
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:275:20:
            // ( LPAREN n= signed_number SEMI n2= NUMBER RPAREN )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:276:3:
            // LPAREN n= signed_number SEMI n2= NUMBER RPAREN
            {
                match(this.input, LPAREN, FOLLOW_LPAREN_in_numeric_int_sample1406);
                s = ProbfunctionFactory.eINSTANCE.createSample();
                pushFollow(FOLLOW_signed_number_in_numeric_int_sample1419);
                n = signed_number();

                this.state._fsp--;

                s.setValue(Integer.parseInt(n));
                match(this.input, SEMI, FOLLOW_SEMI_in_numeric_int_sample1430);
                n2 = (Token) match(this.input, NUMBER, FOLLOW_NUMBER_in_numeric_int_sample1438);
                s.setProbability(Double.parseDouble(n2.getText()));
                match(this.input, RPAREN, FOLLOW_RPAREN_in_numeric_int_sample1450);

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return s;
    }

    // $ANTLR end "numeric_int_sample"

    // $ANTLR start "numeric_real_sample"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:285:1:
    // numeric_real_sample returns [Sample s] : LPAREN n= signed_number SEMI n2= NUMBER RPAREN ;
    public final Sample<Double> numeric_real_sample() throws RecognitionException {
        Sample<Double> s = null;

        Token n2 = null;
        String n = null;

        s = null;
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:286:20:
            // ( LPAREN n= signed_number SEMI n2= NUMBER RPAREN )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:287:3:
            // LPAREN n= signed_number SEMI n2= NUMBER RPAREN
            {
                match(this.input, LPAREN, FOLLOW_LPAREN_in_numeric_real_sample1473);
                s = ProbfunctionFactory.eINSTANCE.createSample();
                pushFollow(FOLLOW_signed_number_in_numeric_real_sample1486);
                n = signed_number();

                this.state._fsp--;

                s.setValue(Double.parseDouble(n));
                match(this.input, SEMI, FOLLOW_SEMI_in_numeric_real_sample1497);
                n2 = (Token) match(this.input, NUMBER, FOLLOW_NUMBER_in_numeric_real_sample1505);
                s.setProbability(Double.parseDouble(n2.getText()));
                match(this.input, RPAREN, FOLLOW_RPAREN_in_numeric_real_sample1517);

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return s;
    }

    // $ANTLR end "numeric_real_sample"

    // $ANTLR start "real_pdf_sample"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:296:1:
    // real_pdf_sample returns [ContinuousSample s] : LPAREN n= signed_number SEMI n2= NUMBER RPAREN
    // ;
    public final ContinuousSample real_pdf_sample() throws RecognitionException {
        ContinuousSample s = null;

        Token n2 = null;
        String n = null;

        s = null;
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:297:20:
            // ( LPAREN n= signed_number SEMI n2= NUMBER RPAREN )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:298:3:
            // LPAREN n= signed_number SEMI n2= NUMBER RPAREN
            {
                match(this.input, LPAREN, FOLLOW_LPAREN_in_real_pdf_sample1541);
                s = ProbfunctionFactory.eINSTANCE.createContinuousSample();
                pushFollow(FOLLOW_signed_number_in_real_pdf_sample1554);
                n = signed_number();

                this.state._fsp--;

                s.setValue(Double.parseDouble(n));
                match(this.input, SEMI, FOLLOW_SEMI_in_real_pdf_sample1565);
                n2 = (Token) match(this.input, NUMBER, FOLLOW_NUMBER_in_real_pdf_sample1573);
                s.setProbability(Double.parseDouble(n2.getText()));
                match(this.input, RPAREN, FOLLOW_RPAREN_in_real_pdf_sample1585);

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return s;
    }

    // $ANTLR end "real_pdf_sample"

    // $ANTLR start "stringsample"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:307:1:
    // stringsample returns [Sample s] : LPAREN str= STRING_LITERAL SEMI n= NUMBER RPAREN ;
    public final Sample<Comparable<?>> stringsample() throws RecognitionException {
        Sample<Comparable<?>> s = null;

        Token str = null;
        Token n = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:308:3:
            // ( LPAREN str= STRING_LITERAL SEMI n= NUMBER RPAREN )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:309:3:
            // LPAREN str= STRING_LITERAL SEMI n= NUMBER RPAREN
            {
                match(this.input, LPAREN, FOLLOW_LPAREN_in_stringsample1606);
                s = ProbfunctionFactory.eINSTANCE.createSample();
                str = (Token) match(this.input, STRING_LITERAL, FOLLOW_STRING_LITERAL_in_stringsample1618);
                s.setValue(str.getText().replace("\"", ""));
                match(this.input, SEMI, FOLLOW_SEMI_in_stringsample1629);
                n = (Token) match(this.input, NUMBER, FOLLOW_NUMBER_in_stringsample1635);
                s.setProbability(Double.parseDouble(n.getText()));
                match(this.input, RPAREN, FOLLOW_RPAREN_in_stringsample1646);

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return s;
    }

    // $ANTLR end "stringsample"

    // $ANTLR start "boolsample"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:318:1:
    // boolsample returns [Sample s] : LPAREN str= boolean_keywords SEMI n= NUMBER RPAREN ;
    public final Sample<Comparable<?>> boolsample() throws RecognitionException {
        Sample<Comparable<?>> s = null;

        Token n = null;
        String str = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:319:3:
            // ( LPAREN str= boolean_keywords SEMI n= NUMBER RPAREN )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:320:3:
            // LPAREN str= boolean_keywords SEMI n= NUMBER RPAREN
            {
                match(this.input, LPAREN, FOLLOW_LPAREN_in_boolsample1664);
                s = ProbfunctionFactory.eINSTANCE.createSample();
                pushFollow(FOLLOW_boolean_keywords_in_boolsample1678);
                str = boolean_keywords();

                this.state._fsp--;

                s.setValue(str.equals("true"));
                match(this.input, SEMI, FOLLOW_SEMI_in_boolsample1686);
                n = (Token) match(this.input, NUMBER, FOLLOW_NUMBER_in_boolsample1692);
                s.setProbability(Double.parseDouble(n.getText()));
                match(this.input, RPAREN, FOLLOW_RPAREN_in_boolsample1703);

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return s;
    }

    // $ANTLR end "boolsample"

    // $ANTLR start "boolean_keywords"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:329:1:
    // boolean_keywords returns [String keyword] : ( FALSE | TRUE ) ;
    public final String boolean_keywords() throws RecognitionException {
        String keyword = null;

        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:330:2:
            // ( ( FALSE | TRUE ) )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:331:3:
            // ( FALSE | TRUE )
            {
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:331:3:
                // ( FALSE | TRUE )
                int alt24 = 2;
                final int LA24_0 = this.input.LA(1);

                if ((LA24_0 == FALSE)) {
                    alt24 = 1;
                } else if ((LA24_0 == TRUE)) {
                    alt24 = 2;
                } else {
                    final NoViableAltException nvae = new NoViableAltException("", 24, 0, this.input);

                    throw nvae;
                }
                switch (alt24) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:332:3:
                // FALSE
                {
                    match(this.input, FALSE, FOLLOW_FALSE_in_boolean_keywords1722);
                    keyword = "false";

                }
                    break;
                case 2:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:335:3:
                // TRUE
                {
                    match(this.input, TRUE, FOLLOW_TRUE_in_boolean_keywords1736);
                    keyword = "true";

                }
                    break;

                }

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return keyword;
    }

    // $ANTLR end "boolean_keywords"

    // $ANTLR start "signed_number"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:339:1:
    // signed_number returns [String stringValue] : ( MINUS )? n= NUMBER ;
    public final String signed_number() throws RecognitionException {
        String stringValue = null;

        Token n = null;

        stringValue = "";
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:341:1:
            // ( ( MINUS )? n= NUMBER )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:341:3:
            // ( MINUS )? n= NUMBER
            {
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:341:3:
                // ( MINUS )?
                int alt25 = 2;
                final int LA25_0 = this.input.LA(1);

                if ((LA25_0 == MINUS)) {
                    alt25 = 1;
                }
                switch (alt25) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:341:4:
                // MINUS
                {
                    match(this.input, MINUS, FOLLOW_MINUS_in_signed_number1765);

                    stringValue += "-";

                }
                    break;

                }

                n = (Token) match(this.input, NUMBER, FOLLOW_NUMBER_in_signed_number1773);

                stringValue += n.getText();

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return stringValue;
    }

    // $ANTLR end "signed_number"

    // $ANTLR start "characterisation_keywords"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:351:1:
    // characterisation_keywords returns [String keyword] : ( BYTESIZE | STRUCTURE |
    // NUMBER_OF_ELEMENTS | TYPE | VALUE ) ;
    public final String characterisation_keywords() throws RecognitionException {
        String keyword = null;

        keyword = null;
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:352:24:
            // ( ( BYTESIZE | STRUCTURE | NUMBER_OF_ELEMENTS | TYPE | VALUE ) )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:353:2:
            // ( BYTESIZE | STRUCTURE | NUMBER_OF_ELEMENTS | TYPE | VALUE )
            {
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:353:2:
                // ( BYTESIZE | STRUCTURE | NUMBER_OF_ELEMENTS | TYPE | VALUE )
                int alt26 = 5;
                switch (this.input.LA(1)) {
                case BYTESIZE: {
                    alt26 = 1;
                }
                    break;
                case STRUCTURE: {
                    alt26 = 2;
                }
                    break;
                case NUMBER_OF_ELEMENTS: {
                    alt26 = 3;
                }
                    break;
                case TYPE: {
                    alt26 = 4;
                }
                    break;
                case VALUE: {
                    alt26 = 5;
                }
                    break;
                default:
                    final NoViableAltException nvae = new NoViableAltException("", 26, 0, this.input);

                    throw nvae;
                }

                switch (alt26) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:353:3:
                // BYTESIZE
                {
                    match(this.input, BYTESIZE, FOLLOW_BYTESIZE_in_characterisation_keywords1795);
                    keyword = "BYTESIZE";

                }
                    break;
                case 2:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:354:4:
                // STRUCTURE
                {
                    match(this.input, STRUCTURE, FOLLOW_STRUCTURE_in_characterisation_keywords1802);
                    keyword = "STRUCTURE";

                }
                    break;
                case 3:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:355:4:
                // NUMBER_OF_ELEMENTS
                {
                    match(this.input, NUMBER_OF_ELEMENTS, FOLLOW_NUMBER_OF_ELEMENTS_in_characterisation_keywords1809);
                    keyword = "NUMBER_OF_ELEMENTS";

                }
                    break;
                case 4:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:356:4:
                // TYPE
                {
                    match(this.input, TYPE, FOLLOW_TYPE_in_characterisation_keywords1816);
                    keyword = "TYPE";

                }
                    break;
                case 5:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:357:4:
                // VALUE
                {
                    match(this.input, VALUE, FOLLOW_VALUE_in_characterisation_keywords1823);
                    keyword = "VALUE";

                }
                    break;

                }

            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return keyword;
    }

    // $ANTLR end "characterisation_keywords"

    // $ANTLR start "scoped_id"
    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:360:1:
    // scoped_id returns [AbstractNamedReference ref] : id1= ID ( DOT (id2= ID | INNER ) )* ;
    public final AbstractNamedReference scoped_id() throws RecognitionException {
        AbstractNamedReference ref = null;

        Token id1 = null;
        Token id2 = null;

        ref = null;
        final ArrayList<String> nameParts = new ArrayList<String>();
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:362:59:
            // (id1= ID ( DOT (id2= ID | INNER ) )* )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:364:2:
            // id1= ID ( DOT (id2= ID | INNER ) )*
            {
                id1 = (Token) match(this.input, ID, FOLLOW_ID_in_scoped_id1853);
                nameParts.add(id1.getText());
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:365:6:
                // ( DOT (id2= ID | INNER ) )*
                loop28: do {
                    int alt28 = 2;
                    final int LA28_0 = this.input.LA(1);

                    if ((LA28_0 == DOT)) {
                        final int LA28_1 = this.input.LA(2);

                        if ((LA28_1 == ID || LA28_1 == INNER)) {
                            alt28 = 1;
                        }

                    }

                    switch (alt28) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:365:7:
                    // DOT (id2= ID | INNER )
                    {
                        match(this.input, DOT, FOLLOW_DOT_in_scoped_id1864);
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:365:11:
                        // (id2= ID | INNER )
                        int alt27 = 2;
                        final int LA27_0 = this.input.LA(1);

                        if ((LA27_0 == ID)) {
                            alt27 = 1;
                        } else if ((LA27_0 == INNER)) {
                            alt27 = 2;
                        } else {
                            final NoViableAltException nvae = new NoViableAltException("", 27, 0, this.input);

                            throw nvae;
                        }
                        switch (alt27) {
                        case 1:
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:365:12:
                        // id2= ID
                        {
                            id2 = (Token) match(this.input, ID, FOLLOW_ID_in_scoped_id1869);
                            nameParts.add(id2.getText());

                        }
                            break;
                        case 2:
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:365:53:
                        // INNER
                        {
                            match(this.input, INNER, FOLLOW_INNER_in_scoped_id1875);
                            nameParts.add("INNER");

                        }
                            break;

                        }

                    }
                        break;

                    default:
                        break loop28;
                    }
                } while (true);

                AbstractNamedReference firstNsRef = null;
                NamespaceReference lastNsRef = null;
                for (int i = 0; i < nameParts.size() - 1; i++) {
                    final NamespaceReference nsRef = StoexFactory.eINSTANCE.createNamespaceReference();
                    nsRef.setReferenceName(nameParts.get(i));
                    if (lastNsRef != null) {
                        lastNsRef.setInnerReference_NamespaceReference(nsRef);
                    }
                    if (i == 0) {
                        firstNsRef = nsRef;
                    }
                    lastNsRef = nsRef;
                }
                final VariableReference varRef = StoexFactory.eINSTANCE.createVariableReference();
                varRef.setReferenceName(nameParts.get(nameParts.size() - 1));
                if (lastNsRef != null) {
                    lastNsRef.setInnerReference_NamespaceReference(varRef);
                    ref = firstNsRef;
                } else {
                    ref = varRef;
                }
            }

        } catch (final RecognitionException re) {
            reportError(re);
            recover(this.input, re);
        } finally {
        }
        return ref;
    }

    // $ANTLR end "scoped_id"

    // Delegated rules

    public static final BitSet FOLLOW_ifelseExpr_in_expression41 = new BitSet(new long[] { 0x0000000000000000L });
    public static final BitSet FOLLOW_EOF_in_expression43 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_boolAndExpr_in_ifelseExpr66 = new BitSet(new long[] { 0x0080000000000002L });
    public static final BitSet FOLLOW_55_in_ifelseExpr79 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_boolAndExpr_in_ifelseExpr85 = new BitSet(new long[] { 0x0100000000000000L });
    public static final BitSet FOLLOW_56_in_ifelseExpr89 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_boolAndExpr_in_ifelseExpr95 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_boolOrExpr_in_boolAndExpr120 = new BitSet(new long[] { 0x0000000000000012L });
    public static final BitSet FOLLOW_AND_in_boolAndExpr131 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_boolOrExpr_in_boolAndExpr142 = new BitSet(new long[] { 0x0000000000000012L });
    public static final BitSet FOLLOW_compareExpr_in_boolOrExpr169 = new BitSet(new long[] { 0x0000000000000062L });
    public static final BitSet FOLLOW_OR_in_boolOrExpr182 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_XOR_in_boolOrExpr190 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_compareExpr_in_boolOrExpr202 = new BitSet(new long[] { 0x0000000000000062L });
    public static final BitSet FOLLOW_sumExpr_in_compareExpr240 = new BitSet(new long[] { 0x0000000000001F82L });
    public static final BitSet FOLLOW_GREATER_in_compareExpr257 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_LESS_in_compareExpr267 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_EQUAL_in_compareExpr277 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_NOTEQUAL_in_compareExpr287 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_GREATEREQUAL_in_compareExpr297 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_LESSEQUAL_in_compareExpr307 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_sumExpr_in_compareExpr322 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_prodExpr_in_sumExpr354 = new BitSet(new long[] { 0x0000000000006002L });
    public static final BitSet FOLLOW_PLUS_in_sumExpr372 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_MINUS_in_sumExpr380 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_prodExpr_in_sumExpr393 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_powExpr_in_prodExpr431 = new BitSet(new long[] { 0x0000000000038002L });
    public static final BitSet FOLLOW_MUL_in_prodExpr451 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_DIV_in_prodExpr462 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_MOD_in_prodExpr473 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_powExpr_in_prodExpr489 = new BitSet(new long[] { 0x0000000000038002L });
    public static final BitSet FOLLOW_unaryExpr_in_powExpr530 = new BitSet(new long[] { 0x0000000000040002L });
    public static final BitSet FOLLOW_POW_in_powExpr539 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_unaryExpr_in_powExpr545 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_MINUS_in_unaryExpr581 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_unaryExpr_in_unaryExpr587 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_NOT_in_unaryExpr605 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_unaryExpr_in_unaryExpr611 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_atom_in_unaryExpr633 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_NUMBER_in_atom668 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_definition_in_atom695 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_STRING_LITERAL_in_atom720 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_boolean_keywords_in_atom747 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_scoped_id_in_atom775 = new BitSet(new long[] { 0x0000000000400000L });
    public static final BitSet FOLLOW_DOT_in_atom777 = new BitSet(new long[] { 0x000007C000000000L });
    public static final BitSet FOLLOW_characterisation_in_atom783 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_ID_in_atom810 = new BitSet(new long[] { 0x0000000001000000L });
    public static final BitSet FOLLOW_arguments_in_atom823 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_atom848 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_ifelseExpr_in_atom858 = new BitSet(new long[] { 0x0000000002000000L });
    public static final BitSet FOLLOW_RPAREN_in_atom864 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_arguments910 = new BitSet(new long[] { 0x00000036CBB84000L });
    public static final BitSet FOLLOW_expressionList_in_arguments916 = new BitSet(new long[] { 0x0000000002000000L });
    public static final BitSet FOLLOW_RPAREN_in_arguments921 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_boolAndExpr_in_expressionList959 = new BitSet(new long[] { 0x0000000004000002L });
    public static final BitSet FOLLOW_COLON_in_expressionList964 = new BitSet(new long[] { 0x00000036C9B84000L });
    public static final BitSet FOLLOW_boolAndExpr_in_expressionList970 = new BitSet(new long[] { 0x0000000004000002L });
    public static final BitSet FOLLOW_characterisation_keywords_in_characterisation1003 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_INTPMF_in_definition1040 = new BitSet(new long[] { 0x0000000010000000L });
    public static final BitSet FOLLOW_SQUARE_PAREN_L_in_definition1051 = new BitSet(new long[] { 0x0000000001000000L });
    public static final BitSet FOLLOW_numeric_int_sample_in_definition1071 = new BitSet(
            new long[] { 0x0000000021000000L });
    public static final BitSet FOLLOW_SQUARE_PAREN_R_in_definition1088 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DOUBLEPMF_in_definition1101 = new BitSet(new long[] { 0x0000000010000000L });
    public static final BitSet FOLLOW_SQUARE_PAREN_L_in_definition1114 = new BitSet(new long[] { 0x0000000001000000L });
    public static final BitSet FOLLOW_numeric_real_sample_in_definition1132 = new BitSet(
            new long[] { 0x0000000021000000L });
    public static final BitSet FOLLOW_SQUARE_PAREN_R_in_definition1149 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_ENUMPMF_in_definition1163 = new BitSet(new long[] { 0x0000000011000000L });
    public static final BitSet FOLLOW_LPAREN_in_definition1176 = new BitSet(new long[] { 0x0000000100000000L });
    public static final BitSet FOLLOW_ORDERED_DEF_in_definition1183 = new BitSet(new long[] { 0x0000000002000000L });
    public static final BitSet FOLLOW_RPAREN_in_definition1195 = new BitSet(new long[] { 0x0000000010000000L });
    public static final BitSet FOLLOW_SQUARE_PAREN_L_in_definition1202 = new BitSet(new long[] { 0x0000000001000000L });
    public static final BitSet FOLLOW_stringsample_in_definition1220 = new BitSet(new long[] { 0x0000000021000000L });
    public static final BitSet FOLLOW_SQUARE_PAREN_R_in_definition1237 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DOUBLEPDF_in_definition1247 = new BitSet(new long[] { 0x0000000010000000L });
    public static final BitSet FOLLOW_SQUARE_PAREN_L_in_definition1258 = new BitSet(new long[] { 0x0000000001000000L });
    public static final BitSet FOLLOW_real_pdf_sample_in_definition1278 = new BitSet(
            new long[] { 0x0000000021000000L });
    public static final BitSet FOLLOW_SQUARE_PAREN_R_in_definition1295 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_BOOLPMF_in_definition1306 = new BitSet(new long[] { 0x0000000011000000L });
    public static final BitSet FOLLOW_LPAREN_in_definition1319 = new BitSet(new long[] { 0x0000000100000000L });
    public static final BitSet FOLLOW_ORDERED_DEF_in_definition1326 = new BitSet(new long[] { 0x0000000002000000L });
    public static final BitSet FOLLOW_RPAREN_in_definition1338 = new BitSet(new long[] { 0x0000000010000000L });
    public static final BitSet FOLLOW_SQUARE_PAREN_L_in_definition1345 = new BitSet(new long[] { 0x0000000001000000L });
    public static final BitSet FOLLOW_boolsample_in_definition1363 = new BitSet(new long[] { 0x0000000021000000L });
    public static final BitSet FOLLOW_SQUARE_PAREN_R_in_definition1380 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_numeric_int_sample1406 = new BitSet(new long[] { 0x0000000000104000L });
    public static final BitSet FOLLOW_signed_number_in_numeric_int_sample1419 = new BitSet(
            new long[] { 0x0000000800000000L });
    public static final BitSet FOLLOW_SEMI_in_numeric_int_sample1430 = new BitSet(new long[] { 0x0000000000100000L });
    public static final BitSet FOLLOW_NUMBER_in_numeric_int_sample1438 = new BitSet(new long[] { 0x0000000002000000L });
    public static final BitSet FOLLOW_RPAREN_in_numeric_int_sample1450 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_numeric_real_sample1473 = new BitSet(
            new long[] { 0x0000000000104000L });
    public static final BitSet FOLLOW_signed_number_in_numeric_real_sample1486 = new BitSet(
            new long[] { 0x0000000800000000L });
    public static final BitSet FOLLOW_SEMI_in_numeric_real_sample1497 = new BitSet(new long[] { 0x0000000000100000L });
    public static final BitSet FOLLOW_NUMBER_in_numeric_real_sample1505 = new BitSet(
            new long[] { 0x0000000002000000L });
    public static final BitSet FOLLOW_RPAREN_in_numeric_real_sample1517 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_real_pdf_sample1541 = new BitSet(new long[] { 0x0000000000104000L });
    public static final BitSet FOLLOW_signed_number_in_real_pdf_sample1554 = new BitSet(
            new long[] { 0x0000000800000000L });
    public static final BitSet FOLLOW_SEMI_in_real_pdf_sample1565 = new BitSet(new long[] { 0x0000000000100000L });
    public static final BitSet FOLLOW_NUMBER_in_real_pdf_sample1573 = new BitSet(new long[] { 0x0000000002000000L });
    public static final BitSet FOLLOW_RPAREN_in_real_pdf_sample1585 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_stringsample1606 = new BitSet(new long[] { 0x0000000000200000L });
    public static final BitSet FOLLOW_STRING_LITERAL_in_stringsample1618 = new BitSet(
            new long[] { 0x0000000800000000L });
    public static final BitSet FOLLOW_SEMI_in_stringsample1629 = new BitSet(new long[] { 0x0000000000100000L });
    public static final BitSet FOLLOW_NUMBER_in_stringsample1635 = new BitSet(new long[] { 0x0000000002000000L });
    public static final BitSet FOLLOW_RPAREN_in_stringsample1646 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_boolsample1664 = new BitSet(new long[] { 0x0000003000000000L });
    public static final BitSet FOLLOW_boolean_keywords_in_boolsample1678 = new BitSet(
            new long[] { 0x0000000800000000L });
    public static final BitSet FOLLOW_SEMI_in_boolsample1686 = new BitSet(new long[] { 0x0000000000100000L });
    public static final BitSet FOLLOW_NUMBER_in_boolsample1692 = new BitSet(new long[] { 0x0000000002000000L });
    public static final BitSet FOLLOW_RPAREN_in_boolsample1703 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_FALSE_in_boolean_keywords1722 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_TRUE_in_boolean_keywords1736 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_MINUS_in_signed_number1765 = new BitSet(new long[] { 0x0000000000100000L });
    public static final BitSet FOLLOW_NUMBER_in_signed_number1773 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_BYTESIZE_in_characterisation_keywords1795 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_STRUCTURE_in_characterisation_keywords1802 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_NUMBER_OF_ELEMENTS_in_characterisation_keywords1809 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_TYPE_in_characterisation_keywords1816 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_VALUE_in_characterisation_keywords1823 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_ID_in_scoped_id1853 = new BitSet(new long[] { 0x0000000000400002L });
    public static final BitSet FOLLOW_DOT_in_scoped_id1864 = new BitSet(new long[] { 0x0000080000800000L });
    public static final BitSet FOLLOW_ID_in_scoped_id1869 = new BitSet(new long[] { 0x0000000000400002L });
    public static final BitSet FOLLOW_INNER_in_scoped_id1875 = new BitSet(new long[] { 0x0000000000400002L });

}