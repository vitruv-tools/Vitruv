// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g 2012-09-27 09:54:49

package org.palladiosimulator.pcm.stochasticexpressions.parser;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class PCMStoExLexer extends Lexer {

    public static final int INNER = 43;
    public static final int MOD = 17;
    public static final int BOOLPMF = 34;
    public static final int ENUMPMF = 31;
    public static final int GREATEREQUAL = 11;
    public static final int Exponent = 48;
    public static final int NOT = 19;
    public static final int ID = 23;
    public static final int AND = 4;
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

    public PCMStoExLexer() {
        ;
    }

    public PCMStoExLexer(final CharStream input) {
        this(input, new RecognizerSharedState());
    }

    public PCMStoExLexer(final CharStream input, final RecognizerSharedState state) {
        super(input, state);

    }

    @Override
    public String getGrammarFileName() {
        return "/Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g";
    }

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            final int _type = T__55;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:7:7:
            // ( '?' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:7:9:
            // '?'
            {
                match('?');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            final int _type = T__56;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:8:7:
            // ( ':' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:8:9:
            // ':'
            {
                match(':');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "T__56"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            final int _type = OR;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:391:2:
            // ( 'OR' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:391:4:
            // 'OR'
            {
                match("OR");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "OR"

    // $ANTLR start "XOR"
    public final void mXOR() throws RecognitionException {
        try {
            final int _type = XOR;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:395:2:
            // ( 'XOR' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:395:4:
            // 'XOR'
            {
                match("XOR");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "XOR"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            final int _type = AND;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:399:2:
            // ( 'AND' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:399:4:
            // 'AND'
            {
                match("AND");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "AND"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            final int _type = NOT;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:403:2:
            // ( 'NOT' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:403:4:
            // 'NOT'
            {
                match("NOT");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "NOT"

    // $ANTLR start "INTPMF"
    public final void mINTPMF() throws RecognitionException {
        try {
            final int _type = INTPMF;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:407:2:
            // ( 'IntPMF' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:407:4:
            // 'IntPMF'
            {
                match("IntPMF");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "INTPMF"

    // $ANTLR start "DOUBLEPMF"
    public final void mDOUBLEPMF() throws RecognitionException {
        try {
            final int _type = DOUBLEPMF;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:411:2:
            // ( 'DoublePMF' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:411:4:
            // 'DoublePMF'
            {
                match("DoublePMF");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "DOUBLEPMF"

    // $ANTLR start "ENUMPMF"
    public final void mENUMPMF() throws RecognitionException {
        try {
            final int _type = ENUMPMF;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:415:2:
            // ( 'EnumPMF' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:415:4:
            // 'EnumPMF'
            {
                match("EnumPMF");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "ENUMPMF"

    // $ANTLR start "DOUBLEPDF"
    public final void mDOUBLEPDF() throws RecognitionException {
        try {
            final int _type = DOUBLEPDF;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:419:2:
            // ( 'DoublePDF' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:419:4:
            // 'DoublePDF'
            {
                match("DoublePDF");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "DOUBLEPDF"

    // $ANTLR start "BOOLPMF"
    public final void mBOOLPMF() throws RecognitionException {
        try {
            final int _type = BOOLPMF;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:423:2:
            // ( 'BoolPMF' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:423:4:
            // 'BoolPMF'
            {
                match("BoolPMF");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "BOOLPMF"

    // $ANTLR start "UNIT"
    public final void mUNIT() throws RecognitionException {
        try {
            final int _type = UNIT;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:427:2:
            // ( 'unit' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:427:4:
            // 'unit'
            {
                match("unit");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "UNIT"

    // $ANTLR start "BOOL"
    public final void mBOOL() throws RecognitionException {
        try {
            final int _type = BOOL;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:431:2:
            // ( '\"bool\"' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:431:4:
            // '\"bool\"'
            {
                match("\"bool\"");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "BOOL"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            final int _type = FALSE;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:435:2:
            // ( 'false' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:435:4:
            // 'false'
            {
                match("false");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "FALSE"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            final int _type = TRUE;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:439:2:
            // ( 'true' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:439:4:
            // 'true'
            {
                match("true");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "TRUE"

    // $ANTLR start "BYTESIZE"
    public final void mBYTESIZE() throws RecognitionException {
        try {
            final int _type = BYTESIZE;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:443:2:
            // ( 'BYTESIZE' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:443:4:
            // 'BYTESIZE'
            {
                match("BYTESIZE");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "BYTESIZE"

    // $ANTLR start "STRUCTURE"
    public final void mSTRUCTURE() throws RecognitionException {
        try {
            final int _type = STRUCTURE;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:447:2:
            // ( 'STRUCTURE' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:447:4:
            // 'STRUCTURE'
            {
                match("STRUCTURE");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "STRUCTURE"

    // $ANTLR start "NUMBER_OF_ELEMENTS"
    public final void mNUMBER_OF_ELEMENTS() throws RecognitionException {
        try {
            final int _type = NUMBER_OF_ELEMENTS;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:451:2:
            // ( 'NUMBER_OF_ELEMENTS' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:451:4:
            // 'NUMBER_OF_ELEMENTS'
            {
                match("NUMBER_OF_ELEMENTS");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "NUMBER_OF_ELEMENTS"

    // $ANTLR start "TYPE"
    public final void mTYPE() throws RecognitionException {
        try {
            final int _type = TYPE;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:455:2:
            // ( 'TYPE' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:455:4:
            // 'TYPE'
            {
                match("TYPE");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "TYPE"

    // $ANTLR start "VALUE"
    public final void mVALUE() throws RecognitionException {
        try {
            final int _type = VALUE;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:459:2:
            // ( 'VALUE' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:459:4:
            // 'VALUE'
            {
                match("VALUE");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "VALUE"

    // $ANTLR start "INNER"
    public final void mINNER() throws RecognitionException {
        try {
            final int _type = INNER;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:463:2:
            // ( 'INNER' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:463:4:
            // 'INNER'
            {
                match("INNER");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "INNER"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            final int _type = PLUS;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:466:7:
            // ( '+' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:466:9:
            // '+'
            {
                match('+');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            final int _type = MINUS;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:467:7:
            // ( '-' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:467:9:
            // '-'
            {
                match('-');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "MINUS"

    // $ANTLR start "MUL"
    public final void mMUL() throws RecognitionException {
        try {
            final int _type = MUL;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:468:7:
            // ( '*' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:468:9:
            // '*'
            {
                match('*');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "MUL"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            final int _type = DIV;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:469:7:
            // ( '/' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:469:9:
            // '/'
            {
                match('/');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "DIV"

    // $ANTLR start "MOD"
    public final void mMOD() throws RecognitionException {
        try {
            final int _type = MOD;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:470:7:
            // ( '%' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:470:9:
            // '%'
            {
                match('%');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "MOD"

    // $ANTLR start "POW"
    public final void mPOW() throws RecognitionException {
        try {
            final int _type = POW;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:471:7:
            // ( '^' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:471:9:
            // '^'
            {
                match('^');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "POW"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            final int _type = LPAREN;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:472:7:
            // ( '(' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:472:9:
            // '('
            {
                match('(');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            final int _type = RPAREN;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:473:7:
            // ( ')' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:473:9:
            // ')'
            {
                match(')');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "RPAREN"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            final int _type = SEMI;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:474:7:
            // ( ';' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:474:9:
            // ';'
            {
                match(';');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "SEMI"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            final int _type = COLON;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:475:8:
            // ( ',' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:475:10:
            // ','
            {
                match(',');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "COLON"

    // $ANTLR start "DEFINITION"
    public final void mDEFINITION() throws RecognitionException {
        try {
            final int _type = DEFINITION;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:476:12:
            // ( '=' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:476:14:
            // '='
            {
                match('=');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "DEFINITION"

    // $ANTLR start "ORDERED_DEF"
    public final void mORDERED_DEF() throws RecognitionException {
        try {
            final int _type = ORDERED_DEF;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:478:2:
            // ( 'ordered' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:478:4:
            // 'ordered'
            {
                match("ordered");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "ORDERED_DEF"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            final int _type = EQUAL;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:479:7:
            // ( '==' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:479:9:
            // '=='
            {
                match("==");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "EQUAL"

    // $ANTLR start "SQUARE_PAREN_L"
    public final void mSQUARE_PAREN_L() throws RecognitionException {
        try {
            final int _type = SQUARE_PAREN_L;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:480:16:
            // ( '[' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:480:18:
            // '['
            {
                match('[');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "SQUARE_PAREN_L"

    // $ANTLR start "SQUARE_PAREN_R"
    public final void mSQUARE_PAREN_R() throws RecognitionException {
        try {
            final int _type = SQUARE_PAREN_R;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:481:16:
            // ( ']' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:481:18:
            // ']'
            {
                match(']');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "SQUARE_PAREN_R"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:482:16:
            // ( '0' .. '9' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:482:18:
            // '0' .. '9'
            {
                matchRange('0', '9');

            }

        } finally {
        }
    }

    // $ANTLR end "DIGIT"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            final int _type = NUMBER;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:483:8:
            // ( ( DIGIT )+ ( '.' ( DIGIT )+ )? ( Exponent )? )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:483:11:
            // ( DIGIT )+ ( '.' ( DIGIT )+ )? ( Exponent )?
            {
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:483:11:
                // ( DIGIT )+
                int cnt1 = 0;
                loop1: do {
                    int alt1 = 2;
                    final int LA1_0 = this.input.LA(1);

                    if (((LA1_0 >= '0' && LA1_0 <= '9'))) {
                        alt1 = 1;
                    }

                    switch (alt1) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:483:12:
                    // DIGIT
                    {
                        mDIGIT();

                    }
                        break;

                    default:
                        if (cnt1 >= 1) {
                            break loop1;
                        }
                        final EarlyExitException eee = new EarlyExitException(1, this.input);
                        throw eee;
                    }
                    cnt1++;
                } while (true);

                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:483:20:
                // ( '.' ( DIGIT )+ )?
                int alt3 = 2;
                final int LA3_0 = this.input.LA(1);

                if ((LA3_0 == '.')) {
                    alt3 = 1;
                }
                switch (alt3) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:483:21:
                // '.' ( DIGIT )+
                {
                    match('.');
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:483:25:
                    // ( DIGIT )+
                    int cnt2 = 0;
                    loop2: do {
                        int alt2 = 2;
                        final int LA2_0 = this.input.LA(1);

                        if (((LA2_0 >= '0' && LA2_0 <= '9'))) {
                            alt2 = 1;
                        }

                        switch (alt2) {
                        case 1:
                        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:483:26:
                        // DIGIT
                        {
                            mDIGIT();

                        }
                            break;

                        default:
                            if (cnt2 >= 1) {
                                break loop2;
                            }
                            final EarlyExitException eee = new EarlyExitException(2, this.input);
                            throw eee;
                        }
                        cnt2++;
                    } while (true);

                }
                    break;

                }

                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:483:36:
                // ( Exponent )?
                int alt4 = 2;
                final int LA4_0 = this.input.LA(1);

                if ((LA4_0 == 'E' || LA4_0 == 'e')) {
                    alt4 = 1;
                }
                switch (alt4) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:483:36:
                // Exponent
                {
                    mExponent();

                }
                    break;

                }

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "NUMBER"

    // $ANTLR start "Exponent"
    public final void mExponent() throws RecognitionException {
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:485:10:
            // ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:485:12:
            // ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
                if (this.input.LA(1) == 'E' || this.input.LA(1) == 'e') {
                    this.input.consume();

                } else {
                    final MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    recover(mse);
                    throw mse;
                }

                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:485:22:
                // ( '+' | '-' )?
                int alt5 = 2;
                final int LA5_0 = this.input.LA(1);

                if ((LA5_0 == '+' || LA5_0 == '-')) {
                    alt5 = 1;
                }
                switch (alt5) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:
                {
                    if (this.input.LA(1) == '+' || this.input.LA(1) == '-') {
                        this.input.consume();

                    } else {
                        final MismatchedSetException mse = new MismatchedSetException(null, this.input);
                        recover(mse);
                        throw mse;
                    }

                }
                    break;

                }

                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:485:33:
                // ( '0' .. '9' )+
                int cnt6 = 0;
                loop6: do {
                    int alt6 = 2;
                    final int LA6_0 = this.input.LA(1);

                    if (((LA6_0 >= '0' && LA6_0 <= '9'))) {
                        alt6 = 1;
                    }

                    switch (alt6) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:485:34:
                    // '0' .. '9'
                    {
                        matchRange('0', '9');

                    }
                        break;

                    default:
                        if (cnt6 >= 1) {
                            break loop6;
                        }
                        final EarlyExitException eee = new EarlyExitException(6, this.input);
                        throw eee;
                    }
                    cnt6++;
                } while (true);

            }

        } finally {
        }
    }

    // $ANTLR end "Exponent"

    // $ANTLR start "ALPHA"
    public final void mALPHA() throws RecognitionException {
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:486:16:
            // ( 'a' .. 'z' | 'A' .. 'Z' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:
            {
                if ((this.input.LA(1) >= 'A' && this.input.LA(1) <= 'Z')
                        || (this.input.LA(1) >= 'a' && this.input.LA(1) <= 'z')) {
                    this.input.consume();

                } else {
                    final MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    recover(mse);
                    throw mse;
                }

            }

        } finally {
        }
    }

    // $ANTLR end "ALPHA"

    // $ANTLR start "NOTEQUAL"
    public final void mNOTEQUAL() throws RecognitionException {
        try {
            final int _type = NOTEQUAL;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:487:10:
            // ( '<>' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:487:12:
            // '<>'
            {
                match("<>");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "NOTEQUAL"

    // $ANTLR start "GREATER"
    public final void mGREATER() throws RecognitionException {
        try {
            final int _type = GREATER;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:488:9:
            // ( '>' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:488:11:
            // '>'
            {
                match('>');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "GREATER"

    // $ANTLR start "LESS"
    public final void mLESS() throws RecognitionException {
        try {
            final int _type = LESS;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:489:6:
            // ( '<' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:489:8:
            // '<'
            {
                match('<');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "LESS"

    // $ANTLR start "GREATEREQUAL"
    public final void mGREATEREQUAL() throws RecognitionException {
        try {
            final int _type = GREATEREQUAL;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:490:14:
            // ( '>=' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:490:16:
            // '>='
            {
                match(">=");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "GREATEREQUAL"

    // $ANTLR start "LESSEQUAL"
    public final void mLESSEQUAL() throws RecognitionException {
        try {
            final int _type = LESSEQUAL;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:491:11:
            // ( '<=' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:491:13:
            // '<='
            {
                match("<=");

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "LESSEQUAL"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            final int _type = STRING_LITERAL;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:492:16:
            // ( '\\\"' ( ALPHA | '_' | ' ' | DIGIT )+ '\\\"' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:492:18:
            // '\\\"' ( ALPHA | '_' | ' ' | DIGIT )+ '\\\"'
            {
                match('\"');
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:492:23:
                // ( ALPHA | '_' | ' ' | DIGIT )+
                int cnt7 = 0;
                loop7: do {
                    int alt7 = 2;
                    final int LA7_0 = this.input.LA(1);

                    if ((LA7_0 == ' ' || (LA7_0 >= '0' && LA7_0 <= '9') || (LA7_0 >= 'A' && LA7_0 <= 'Z')
                            || LA7_0 == '_' || (LA7_0 >= 'a' && LA7_0 <= 'z'))) {
                        alt7 = 1;
                    }

                    switch (alt7) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:
                    {
                        if (this.input.LA(1) == ' ' || (this.input.LA(1) >= '0' && this.input.LA(1) <= '9')
                                || (this.input.LA(1) >= 'A' && this.input.LA(1) <= 'Z') || this.input.LA(1) == '_'
                                || (this.input.LA(1) >= 'a' && this.input.LA(1) <= 'z')) {
                            this.input.consume();

                        } else {
                            final MismatchedSetException mse = new MismatchedSetException(null, this.input);
                            recover(mse);
                            throw mse;
                        }

                    }
                        break;

                    default:
                        if (cnt7 >= 1) {
                            break loop7;
                        }
                        final EarlyExitException eee = new EarlyExitException(7, this.input);
                        throw eee;
                    }
                    cnt7++;
                } while (true);

                match('\"');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "STRING_LITERAL"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            final int _type = DOT;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:493:4:
            // ( '.' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:493:6:
            // '.'
            {
                match('.');

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "DOT"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            final int _type = ID;
            final int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:496:4:
            // ( IDSTART ( IDPART )* )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:496:6:
            // IDSTART ( IDPART )*
            {
                mIDSTART();
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:496:14:
                // ( IDPART )*
                loop8: do {
                    int alt8 = 2;
                    final int LA8_0 = this.input.LA(1);

                    if (((LA8_0 >= '0' && LA8_0 <= '9') || (LA8_0 >= 'A' && LA8_0 <= 'Z') || LA8_0 == '_'
                            || (LA8_0 >= 'a' && LA8_0 <= 'z'))) {
                        alt8 = 1;
                    }

                    switch (alt8) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:496:14:
                    // IDPART
                    {
                        mIDPART();

                    }
                        break;

                    default:
                        break loop8;
                    }
                } while (true);

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "ID"

    // $ANTLR start "IDSTART"
    public final void mIDSTART() throws RecognitionException {
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:497:18:
            // ( ALPHA | '_' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:
            {
                if ((this.input.LA(1) >= 'A' && this.input.LA(1) <= 'Z') || this.input.LA(1) == '_'
                        || (this.input.LA(1) >= 'a' && this.input.LA(1) <= 'z')) {
                    this.input.consume();

                } else {
                    final MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    recover(mse);
                    throw mse;
                }

            }

        } finally {
        }
    }

    // $ANTLR end "IDSTART"

    // $ANTLR start "IDPART"
    public final void mIDPART() throws RecognitionException {
        try {
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:498:17:
            // ( IDSTART | DIGIT )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:
            {
                if ((this.input.LA(1) >= '0' && this.input.LA(1) <= '9')
                        || (this.input.LA(1) >= 'A' && this.input.LA(1) <= 'Z') || this.input.LA(1) == '_'
                        || (this.input.LA(1) >= 'a' && this.input.LA(1) <= 'z')) {
                    this.input.consume();

                } else {
                    final MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    recover(mse);
                    throw mse;
                }

            }

        } finally {
        }
    }

    // $ANTLR end "IDPART"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            final int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:500:5:
            // ( ( ' ' | '\\r' | '\\t' | '\ ' | '\\n' ) )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:500:8:
            // ( ' ' | '\\r' | '\\t' | '\ ' | '\\n' )
            {
                if ((this.input.LA(1) >= '\t' && this.input.LA(1) <= '\n')
                        || (this.input.LA(1) >= '\f' && this.input.LA(1) <= '\r') || this.input.LA(1) == ' ') {
                    this.input.consume();

                } else {
                    final MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    recover(mse);
                    throw mse;
                }

                _channel = HIDDEN;

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "WS"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            final int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:504:5:
            // ( '/*' ( options {greedy=false; } : . )* '*/' )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:504:9:
            // '/*' ( options {greedy=false; } : . )* '*/'
            {
                match("/*");

                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:504:14:
                // ( options {greedy=false; } : . )*
                loop9: do {
                    int alt9 = 2;
                    final int LA9_0 = this.input.LA(1);

                    if ((LA9_0 == '*')) {
                        final int LA9_1 = this.input.LA(2);

                        if ((LA9_1 == '/')) {
                            alt9 = 2;
                        } else if (((LA9_1 >= '\u0000' && LA9_1 <= '.') || (LA9_1 >= '0' && LA9_1 <= '\uFFFF'))) {
                            alt9 = 1;
                        }

                    } else if (((LA9_0 >= '\u0000' && LA9_0 <= ')') || (LA9_0 >= '+' && LA9_0 <= '\uFFFF'))) {
                        alt9 = 1;
                    }

                    switch (alt9) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:504:42:
                    // .
                    {
                        matchAny();

                    }
                        break;

                    default:
                        break loop9;
                    }
                } while (true);

                match("*/");

                _channel = HIDDEN;

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            final int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:508:5:
            // ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? ( '\\n' | EOF ) )
            // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:508:7:
            // '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? ( '\\n' | EOF )
            {
                match("//");

                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:508:12:
                // (~ ( '\\n' | '\\r' ) )*
                loop10: do {
                    int alt10 = 2;
                    final int LA10_0 = this.input.LA(1);

                    if (((LA10_0 >= '\u0000' && LA10_0 <= '\t') || (LA10_0 >= '\u000B' && LA10_0 <= '\f')
                            || (LA10_0 >= '\u000E' && LA10_0 <= '\uFFFF'))) {
                        alt10 = 1;
                    }

                    switch (alt10) {
                    case 1:
                    // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:508:12:
                    // ~ ( '\\n' | '\\r' )
                    {
                        if ((this.input.LA(1) >= '\u0000' && this.input.LA(1) <= '\t')
                                || (this.input.LA(1) >= '\u000B' && this.input.LA(1) <= '\f')
                                || (this.input.LA(1) >= '\u000E' && this.input.LA(1) <= '\uFFFF')) {
                            this.input.consume();

                        } else {
                            final MismatchedSetException mse = new MismatchedSetException(null, this.input);
                            recover(mse);
                            throw mse;
                        }

                    }
                        break;

                    default:
                        break loop10;
                    }
                } while (true);

                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:508:26:
                // ( '\\r' )?
                int alt11 = 2;
                final int LA11_0 = this.input.LA(1);

                if ((LA11_0 == '\r')) {
                    alt11 = 1;
                }
                switch (alt11) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:508:26:
                // '\\r'
                {
                    match('\r');

                }
                    break;

                }

                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:508:32:
                // ( '\\n' | EOF )
                int alt12 = 2;
                final int LA12_0 = this.input.LA(1);

                if ((LA12_0 == '\n')) {
                    alt12 = 1;
                } else {
                    alt12 = 2;
                }
                switch (alt12) {
                case 1:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:508:33:
                // '\\n'
                {
                    match('\n');

                }
                    break;
                case 2:
                // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:508:38:
                // EOF
                {
                    match(EOF);

                }
                    break;

                }

                _channel = HIDDEN;

            }

            this.state.type = _type;
            this.state.channel = _channel;
        } finally {
        }
    }

    // $ANTLR end "LINE_COMMENT"

    @Override
    public void mTokens() throws RecognitionException {
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:8:
        // ( T__55 | T__56 | OR | XOR | AND | NOT | INTPMF | DOUBLEPMF | ENUMPMF | DOUBLEPDF |
        // BOOLPMF | UNIT | BOOL | FALSE | TRUE | BYTESIZE | STRUCTURE | NUMBER_OF_ELEMENTS | TYPE |
        // VALUE | INNER | PLUS | MINUS | MUL | DIV | MOD | POW | LPAREN | RPAREN | SEMI | COLON |
        // DEFINITION | ORDERED_DEF | EQUAL | SQUARE_PAREN_L | SQUARE_PAREN_R | NUMBER | NOTEQUAL |
        // GREATER | LESS | GREATEREQUAL | LESSEQUAL | STRING_LITERAL | DOT | ID | WS | COMMENT |
        // LINE_COMMENT )
        int alt13 = 48;
        alt13 = this.dfa13.predict(this.input);
        switch (alt13) {
        case 1:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:10:
        // T__55
        {
            mT__55();

        }
            break;
        case 2:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:16:
        // T__56
        {
            mT__56();

        }
            break;
        case 3:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:22:
        // OR
        {
            mOR();

        }
            break;
        case 4:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:25:
        // XOR
        {
            mXOR();

        }
            break;
        case 5:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:29:
        // AND
        {
            mAND();

        }
            break;
        case 6:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:33:
        // NOT
        {
            mNOT();

        }
            break;
        case 7:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:37:
        // INTPMF
        {
            mINTPMF();

        }
            break;
        case 8:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:44:
        // DOUBLEPMF
        {
            mDOUBLEPMF();

        }
            break;
        case 9:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:54:
        // ENUMPMF
        {
            mENUMPMF();

        }
            break;
        case 10:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:62:
        // DOUBLEPDF
        {
            mDOUBLEPDF();

        }
            break;
        case 11:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:72:
        // BOOLPMF
        {
            mBOOLPMF();

        }
            break;
        case 12:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:80:
        // UNIT
        {
            mUNIT();

        }
            break;
        case 13:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:85:
        // BOOL
        {
            mBOOL();

        }
            break;
        case 14:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:90:
        // FALSE
        {
            mFALSE();

        }
            break;
        case 15:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:96:
        // TRUE
        {
            mTRUE();

        }
            break;
        case 16:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:101:
        // BYTESIZE
        {
            mBYTESIZE();

        }
            break;
        case 17:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:110:
        // STRUCTURE
        {
            mSTRUCTURE();

        }
            break;
        case 18:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:120:
        // NUMBER_OF_ELEMENTS
        {
            mNUMBER_OF_ELEMENTS();

        }
            break;
        case 19:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:139:
        // TYPE
        {
            mTYPE();

        }
            break;
        case 20:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:144:
        // VALUE
        {
            mVALUE();

        }
            break;
        case 21:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:150:
        // INNER
        {
            mINNER();

        }
            break;
        case 22:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:156:
        // PLUS
        {
            mPLUS();

        }
            break;
        case 23:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:161:
        // MINUS
        {
            mMINUS();

        }
            break;
        case 24:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:167:
        // MUL
        {
            mMUL();

        }
            break;
        case 25:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:171:
        // DIV
        {
            mDIV();

        }
            break;
        case 26:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:175:
        // MOD
        {
            mMOD();

        }
            break;
        case 27:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:179:
        // POW
        {
            mPOW();

        }
            break;
        case 28:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:183:
        // LPAREN
        {
            mLPAREN();

        }
            break;
        case 29:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:190:
        // RPAREN
        {
            mRPAREN();

        }
            break;
        case 30:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:197:
        // SEMI
        {
            mSEMI();

        }
            break;
        case 31:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:202:
        // COLON
        {
            mCOLON();

        }
            break;
        case 32:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:208:
        // DEFINITION
        {
            mDEFINITION();

        }
            break;
        case 33:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:219:
        // ORDERED_DEF
        {
            mORDERED_DEF();

        }
            break;
        case 34:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:231:
        // EQUAL
        {
            mEQUAL();

        }
            break;
        case 35:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:237:
        // SQUARE_PAREN_L
        {
            mSQUARE_PAREN_L();

        }
            break;
        case 36:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:252:
        // SQUARE_PAREN_R
        {
            mSQUARE_PAREN_R();

        }
            break;
        case 37:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:267:
        // NUMBER
        {
            mNUMBER();

        }
            break;
        case 38:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:274:
        // NOTEQUAL
        {
            mNOTEQUAL();

        }
            break;
        case 39:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:283:
        // GREATER
        {
            mGREATER();

        }
            break;
        case 40:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:291:
        // LESS
        {
            mLESS();

        }
            break;
        case 41:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:296:
        // GREATEREQUAL
        {
            mGREATEREQUAL();

        }
            break;
        case 42:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:309:
        // LESSEQUAL
        {
            mLESSEQUAL();

        }
            break;
        case 43:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:319:
        // STRING_LITERAL
        {
            mSTRING_LITERAL();

        }
            break;
        case 44:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:334:
        // DOT
        {
            mDOT();

        }
            break;
        case 45:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:338:
        // ID
        {
            mID();

        }
            break;
        case 46:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:341:
        // WS
        {
            mWS();

        }
            break;
        case 47:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:344:
        // COMMENT
        {
            mCOMMENT();

        }
            break;
        case 48:
        // /Users/joerg/Documents/work/palladio-dev/org.palladiosimulator.pcm/src-man/de/uka/ipd/sdq/pcm/stochasticexpressions/parser/PCMStoEx.g:1:352:
        // LINE_COMMENT
        {
            mLINE_COMMENT();

        }
            break;

        }

    }

    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA13_eotS = "\3\uffff\11\44\1\uffff\5\44\3\uffff\1\73\6\uffff\1\75\1\44\3\uffff"
            + "\1\101\1\103\3\uffff\1\104\13\44\2\uffff\5\44\5\uffff\1\44\6\uffff"
            + "\1\127\1\130\1\131\10\44\1\uffff\6\44\3\uffff\7\44\1\160\1\uffff"
            + "\1\44\1\163\1\44\1\165\4\44\1\172\4\44\2\uffff\1\u0080\1\uffff\1"
            + "\44\1\uffff\1\u0082\2\44\1\u0085\1\uffff\4\44\2\uffff\1\44\1\uffff"
            + "\2\44\1\uffff\1\44\1\u0090\1\u0091\1\44\1\uffff\1\44\1\u0094\3\44"
            + "\2\uffff\1\u0098\1\44\1\uffff\1\44\1\u009b\1\u009c\1\uffff\1\u009d"
            + "\1\44\3\uffff\7\44\1\u00a6\1\uffff";
    static final String DFA13_eofS = "\u00a7\uffff";
    static final String DFA13_minS = "\1\11\2\uffff\1\122\1\117\1\116\1\117\1\116\1\157\1\156\1\131\1"
            + "\156\1\40\1\141\1\162\1\124\1\131\1\101\3\uffff\1\52\6\uffff\1\75"
            + "\1\162\3\uffff\2\75\3\uffff\1\60\1\122\1\104\1\124\1\115\1\164\1"
            + "\116\2\165\1\157\1\124\1\151\1\40\1\uffff\1\154\1\165\1\122\1\120"
            + "\1\114\5\uffff\1\144\6\uffff\3\60\1\102\1\120\1\105\1\142\1\155"
            + "\1\154\1\105\1\164\1\40\1\163\1\145\1\125\1\105\1\125\1\145\3\uffff"
            + "\1\105\1\115\1\122\1\154\2\120\1\123\1\60\1\40\1\145\1\60\1\103"
            + "\1\60\1\105\1\162\1\122\1\106\1\60\1\145\2\115\1\111\1\uffff\1\40"
            + "\1\60\1\uffff\1\124\1\uffff\1\60\1\145\1\137\1\60\1\uffff\1\120"
            + "\2\106\1\132\2\uffff\1\125\1\uffff\1\144\1\117\1\uffff\1\104\2\60"
            + "\1\105\1\uffff\1\122\1\60\3\106\2\uffff\1\60\1\105\1\uffff\1\137"
            + "\2\60\1\uffff\1\60\1\105\3\uffff\1\114\1\105\1\115\1\105\1\116\1" + "\124\1\123\1\60\1\uffff";
    static final String DFA13_maxS = "\1\172\2\uffff\1\122\1\117\1\116\1\125\1\156\1\157\1\156\1\157\1"
            + "\156\1\172\1\141\1\162\1\124\1\131\1\101\3\uffff\1\57\6\uffff\1"
            + "\75\1\162\3\uffff\1\76\1\75\3\uffff\1\172\1\122\1\104\1\124\1\115"
            + "\1\164\1\116\2\165\1\157\1\124\1\151\1\172\1\uffff\1\154\1\165\1"
            + "\122\1\120\1\114\5\uffff\1\144\6\uffff\3\172\1\102\1\120\1\105\1"
            + "\142\1\155\1\154\1\105\1\164\1\172\1\163\1\145\1\125\1\105\1\125"
            + "\1\145\3\uffff\1\105\1\115\1\122\1\154\2\120\1\123\2\172\1\145\1"
            + "\172\1\103\1\172\1\105\1\162\1\122\1\106\1\172\1\145\2\115\1\111"
            + "\1\uffff\2\172\1\uffff\1\124\1\uffff\1\172\1\145\1\137\1\172\1\uffff"
            + "\1\120\2\106\1\132\2\uffff\1\125\1\uffff\1\144\1\117\1\uffff\1\115"
            + "\2\172\1\105\1\uffff\1\122\1\172\3\106\2\uffff\1\172\1\105\1\uffff"
            + "\1\137\2\172\1\uffff\1\172\1\105\3\uffff\1\114\1\105\1\115\1\105" + "\1\116\1\124\1\123\1\172\1\uffff";
    static final String DFA13_acceptS = "\1\uffff\1\1\1\2\17\uffff\1\26\1\27\1\30\1\uffff\1\32\1\33\1\34"
            + "\1\35\1\36\1\37\2\uffff\1\43\1\44\1\45\2\uffff\1\54\1\55\1\56\15"
            + "\uffff\1\53\5\uffff\1\57\1\60\1\31\1\42\1\40\1\uffff\1\46\1\52\1"
            + "\50\1\51\1\47\1\3\22\uffff\1\4\1\5\1\6\26\uffff\1\14\2\uffff\1\17"
            + "\1\uffff\1\23\4\uffff\1\25\4\uffff\1\15\1\16\1\uffff\1\24\2\uffff"
            + "\1\7\4\uffff\1\15\5\uffff\1\11\1\13\2\uffff\1\41\3\uffff\1\20\2" + "\uffff\1\10\1\12\1\21\10\uffff\1\22";
    static final String DFA13_specialS = "\u00a7\uffff}>";
    static final String[] DFA13_transitionS = {
            "\2\45\1\uffff\2\45\22\uffff\1\45\1\uffff\1\14\2\uffff\1\26\2"
                    + "\uffff\1\30\1\31\1\24\1\22\1\33\1\23\1\43\1\25\12\40\1\2\1\32"
                    + "\1\41\1\34\1\42\1\1\1\uffff\1\5\1\12\1\44\1\10\1\11\3\44\1\7"
                    + "\4\44\1\6\1\3\3\44\1\17\1\20\1\44\1\21\1\44\1\4\2\44\1\36\1"
                    + "\uffff\1\37\1\27\1\44\1\uffff\5\44\1\15\10\44\1\35\4\44\1\16" + "\1\13\5\44",
            "", "", "\1\46", "\1\47", "\1\50", "\1\51\5\uffff\1\52", "\1\54\37\uffff\1\53", "\1\55", "\1\56",
            "\1\60\25\uffff\1\57", "\1\61",
            "\1\63\17\uffff\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\1\63" + "\1\62\30\63", "\1\64", "\1\65", "\1\66",
            "\1\67", "\1\70", "", "", "", "\1\71\4\uffff\1\72", "", "", "", "", "", "", "\1\74", "\1\76", "", "", "",
            "\1\100\1\77", "\1\102", "", "", "", "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\1\105", "\1\106",
            "\1\107", "\1\110", "\1\111", "\1\112", "\1\113", "\1\114", "\1\115", "\1\116", "\1\117",
            "\1\63\1\uffff\1\63\15\uffff\12\63\7\uffff\32\63\4\uffff\1\63" + "\1\uffff\16\63\1\120\13\63", "", "\1\121",
            "\1\122", "\1\123", "\1\124", "\1\125", "", "", "", "", "", "\1\126", "", "", "", "", "", "",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\1\132", "\1\133", "\1\134", "\1\135", "\1\136",
            "\1\137", "\1\140", "\1\141",
            "\1\63\1\uffff\1\63\15\uffff\12\63\7\uffff\32\63\4\uffff\1\63" + "\1\uffff\16\63\1\142\13\63", "\1\143",
            "\1\144", "\1\145", "\1\146", "\1\147", "\1\150", "", "", "", "\1\151", "\1\152", "\1\153", "\1\154",
            "\1\155", "\1\156", "\1\157", "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\63\1\uffff\1\63\15\uffff\12\63\7\uffff\32\63\4\uffff\1\63" + "\1\uffff\13\63\1\161\16\63", "\1\162",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\1\164",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\1\166", "\1\167", "\1\170", "\1\171",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\1\173", "\1\174", "\1\175", "\1\176", "",
            "\1\63\1\uffff\1\177\15\uffff\12\63\7\uffff\32\63\4\uffff\1" + "\63\1\uffff\32\63",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "", "\1\u0081", "",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\1\u0083", "\1\u0084",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "", "\1\u0086", "\1\u0087", "\1\u0088", "\1\u0089", "",
            "", "\1\u008b", "", "\1\u008c", "\1\u008d", "", "\1\u008f\10\uffff\1\u008e",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\u0092", "", "\1\u0093", "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\1\u0095", "\1\u0096",
            "\1\u0097", "", "", "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\1\u0099", "", "\1\u009a",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "\1\u009e", "", "", "", "\1\u009f", "\1\u00a0",
            "\1\u00a1", "\1\u00a2", "\1\u00a3", "\1\u00a4", "\1\u00a5",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44", "" };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        final int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(final BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }

        @Override
        public String getDescription() {
            return "1:1: Tokens : ( T__55 | T__56 | OR | XOR | AND | NOT | INTPMF | DOUBLEPMF | ENUMPMF | DOUBLEPDF | BOOLPMF | UNIT | BOOL | FALSE | TRUE | BYTESIZE | STRUCTURE | NUMBER_OF_ELEMENTS | TYPE | VALUE | INNER | PLUS | MINUS | MUL | DIV | MOD | POW | LPAREN | RPAREN | SEMI | COLON | DEFINITION | ORDERED_DEF | EQUAL | SQUARE_PAREN_L | SQUARE_PAREN_R | NUMBER | NOTEQUAL | GREATER | LESS | GREATEREQUAL | LESSEQUAL | STRING_LITERAL | DOT | ID | WS | COMMENT | LINE_COMMENT );";
        }
    }

}