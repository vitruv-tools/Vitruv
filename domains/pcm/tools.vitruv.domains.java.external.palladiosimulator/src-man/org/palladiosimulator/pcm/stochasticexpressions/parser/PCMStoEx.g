grammar PCMStoEx;


/*options { output=AST; defaultErrorHandler=false; k=2; }*/
 
@header {  
	package org.palladiosimulator.pcm.stochasticexpressions.parser;
	import de.uka.ipd.sdq.stoex.*;
	import de.uka.ipd.sdq.probfunction.*;
	import org.palladiosimulator.pcm.parameter.*;
	import java.util.ArrayList;
	import java.util.Collection;
	//import de.uka.ipd.sdq.stoex.analyser.visitors.StoExPrettyPrintVisitor;
}

@lexer::header {
  package org.palladiosimulator.pcm.stochasticexpressions.parser;
}
expression returns [Expression exp] 
		:  
		c=ifelseExpr EOF
		{exp = c;}; 

ifelseExpr returns [IfElse ifelseExp]
	:
	cond = boolAndExpr {ifelseExp = cond;} 
		({IfElseExpression newIfelseExp = StoexFactory.eINSTANCE.createIfElseExpression();
		  newIfelseExp.setConditionExpression(cond);}
		 '?' ifEx = boolAndExpr {newIfelseExp.setIfExpression(ifEx);} ':' elseEx = boolAndExpr {newIfelseExp.setElseExpression(elseEx);
		 ifelseExp = newIfelseExp;})?
	;

boolAndExpr returns [BooleanExpression boolExp] 
	:
	b1 = boolOrExpr {boolExp = b1;}
		({BooleanOperatorExpression boolExprNew = StoexFactory.eINSTANCE.createBooleanOperatorExpression();}
		AND {boolExprNew.setOperation(BooleanOperations.AND);}
		 b2 = boolOrExpr {boolExprNew.setLeft(b1); boolExprNew.setRight(b2); boolExp = boolExprNew;}
		)*
	;
boolOrExpr returns [BooleanExpression boolExp] 
	:
	b1 = compareExpr {boolExp = b1;}
		( {BooleanOperatorExpression boolExprNew = StoexFactory.eINSTANCE.createBooleanOperatorExpression();}
		(OR {boolExprNew.setOperation(BooleanOperations.OR);}|
		 XOR{boolExprNew.setOperation(BooleanOperations.XOR);} )
		 b2 = compareExpr
		    {boolExprNew.setLeft(b1); boolExprNew.setRight(b2); boolExp = boolExprNew;}
		)*
	;



compareExpr returns [Comparison comp]
	 :
			t1 = sumExpr {comp = t1;} (
				{CompareExpression compExp = StoexFactory.eINSTANCE.createCompareExpression();}
				(GREATER {compExp.setOperation(CompareOperations.GREATER);}|
				 LESS {compExp.setOperation(CompareOperations.LESS);}|
				 EQUAL {compExp.setOperation(CompareOperations.EQUALS);}|
				 NOTEQUAL {compExp.setOperation(CompareOperations.NOTEQUAL);}|
				 GREATEREQUAL {compExp.setOperation(CompareOperations.GREATEREQUAL);}|
				 LESSEQUAL {compExp.setOperation(CompareOperations.LESSEQUAL);}) 
				 t2 = sumExpr 
				 	{compExp.setLeft(t1); compExp.setRight(t2); comp=compExp;})? ;

sumExpr returns [Term t]
	 : 
	p1 = prodExpr {t = p1;} (
			{TermExpression termExp = StoexFactory.eINSTANCE.createTermExpression();}			
			(PLUS {termExp.setOperation(TermOperations.ADD);}|
			MINUS {termExp.setOperation(TermOperations.SUB);}) 
			p2 = prodExpr 
				{termExp.setLeft(t); termExp.setRight(p2); t = termExp;}
			)?
;
		
prodExpr returns [Product p] 
	 : 
		pw1 = powExpr {p = pw1;} 
			(
			{ProductExpression prodExp = StoexFactory.eINSTANCE.createProductExpression();}
			 (MUL {prodExp.setOperation(ProductOperations.MULT);} |
			  DIV {prodExp.setOperation(ProductOperations.DIV);} |
			  MOD {prodExp.setOperation(ProductOperations.MOD);} ) 
			  pw2 = powExpr 
			  	{prodExp.setLeft(p); prodExp.setRight(pw2); p = prodExp;}
			  )*
;

powExpr returns [Power pw]  
	 : 
		a1 = unaryExpr {pw = a1;} 
			(POW a2 = unaryExpr
				{PowerExpression pwExp = StoexFactory.eINSTANCE.createPowerExpression();
					pwExp.setBase(a1); pwExp.setExponent(a2); pw = pwExp;
				}
			)? 		
;
unaryExpr returns [Unary u] :
		  // unary minus
		  MINUS uminus = unaryExpr
		  {NegativeExpression ne = StoexFactory.eINSTANCE.createNegativeExpression();
		  ne.setInner(uminus);
		  u = ne;}
		  |
		  NOT unot = unaryExpr
		  {NotExpression no = StoexFactory.eINSTANCE.createNotExpression();
		  no.setInner(unot);
		  u = no;}
		  |
		  a = atom {u = a;}
		  ;
atom returns [Atom a]
	 :
		(
		  // numeric literals (int, double)
		  number=NUMBER 
			{
				String value = number.getText();
				if (value.indexOf('.') < 0)
				{
					IntLiteral il = StoexFactory.eINSTANCE.createIntLiteral();
					il.setValue(Integer.parseInt(value));
					a = il;
				}
				else
				{
					DoubleLiteral dl = StoexFactory.eINSTANCE.createDoubleLiteral();
					dl.setValue(Double.parseDouble(value));
					a = dl;
				}
			}
		  |
		  // probability function literals
		  def = definition
		  {a=def;}
		  |
		  // string literal
		  sl=STRING_LITERAL
		  {
		  	StringLiteral stringLiteral = StoexFactory.eINSTANCE.createStringLiteral();
		  	stringLiteral.setValue(sl.getText().replace("\"",""));
		  	a = stringLiteral;
		  }
		  |
		  // boolean literal
		  bl = boolean_keywords
		  {
		  	BoolLiteral boolLiteral = StoexFactory.eINSTANCE.createBoolLiteral();
	   		boolLiteral.setValue(bl.equals("true"));
	   		a = boolLiteral;
	   	  } 
		  |
		  // variables
		  id = scoped_id DOT type = characterisation 
		 { a = ParameterFactory.eINSTANCE.createCharacterisedVariable();
		  	((CharacterisedVariable)a).setId_Variable(id);
		  	((CharacterisedVariable)a).setCharacterisationType(type);
		  }
		  |
		  // function call
		  fid = ID {FunctionLiteral flit = StoexFactory.eINSTANCE.createFunctionLiteral();
		  	    flit.setId(fid.getText());}
		  	args = arguments
		  	{flit.getParameters_FunctionLiteral().addAll(args);
		  	a = flit;}
		  | 
		  // parenthesis expression
		  LPAREN
		  inner = ifelseExpr
		  RPAREN
		  {
			Parenthesis paren = StoexFactory.eINSTANCE.createParenthesis();
			paren.setInnerExpression(inner);
			a = paren;
		  }
	    ) 
;
     
arguments returns [Collection<Expression> parameters]
	@init{parameters = new ArrayList<Expression>();}    
	:   
	LPAREN paramList = expressionList? {parameters.addAll(paramList);} RPAREN
	;
	
expressionList returns [Collection<Expression> parameters]
	@init{parameters = new ArrayList<Expression>();}    
	:   
    		p1 = boolAndExpr {parameters.add(p1);} (COLON p2 = boolAndExpr {parameters.add(p2);})*
    	;
    
characterisation returns [VariableCharacterisationType ct]
	 :
	type = characterisation_keywords
	{if(type.equals("TYPE"))
		ct = VariableCharacterisationType.TYPE;
	 else if(type.equals("BYTESIZE"))
		ct = VariableCharacterisationType.BYTESIZE;
	 else if(type.equals("NUMBER_OF_ELEMENTS"))
		ct = VariableCharacterisationType.NUMBER_OF_ELEMENTS;
	 else if(type.equals("VALUE"))
		ct = VariableCharacterisationType.VALUE;
	 else if(type.equals("STRUCTURE"))
		ct = VariableCharacterisationType.STRUCTURE;
	}
;

definition returns [ProbabilityFunctionLiteral pfl] 
	@init {pfl = StoexFactory.eINSTANCE.createProbabilityFunctionLiteral();
	 ProbabilityFunction probFunction = null; } : 
		
		// Numeric PMF
			
			INTPMF
				{probFunction = ProbfunctionFactory.eINSTANCE.createProbabilityMassFunction();
				   pfl.setFunction_ProbabilityFunctionLiteral(probFunction);}
			SQUARE_PAREN_L 
				( 
				  isample = numeric_int_sample
				  {((ProbabilityMassFunction)probFunction).getSamples().add(isample);})+ 
	 		SQUARE_PAREN_R 
	 		|
		 	DOUBLEPMF 
				{probFunction = ProbfunctionFactory.eINSTANCE.createProbabilityMassFunction();
				   pfl.setFunction_ProbabilityFunctionLiteral(probFunction);}
		 	SQUARE_PAREN_L 
				( 
				rsample = numeric_real_sample
			   	{((ProbabilityMassFunction)probFunction).getSamples().add(rsample);})+ 
			SQUARE_PAREN_R
			| 
		// Enum PMF
			ENUMPMF 
				{probFunction = ProbfunctionFactory.eINSTANCE.createProbabilityMassFunction();
				   pfl.setFunction_ProbabilityFunctionLiteral(probFunction);
				   ((ProbabilityMassFunction)probFunction).setOrderedDomain(false);
				   }
			(LPAREN
			  ORDERED_DEF
			  {((ProbabilityMassFunction)probFunction).setOrderedDomain(true);}
			RPAREN)?
			SQUARE_PAREN_L 
				( 
				ssample = stringsample
			   	{((ProbabilityMassFunction)probFunction).getSamples().add(ssample);})+ 
			SQUARE_PAREN_R
			|
			DOUBLEPDF
				{probFunction = ProbfunctionFactory.eINSTANCE.createBoxedPDF();
				   pfl.setFunction_ProbabilityFunctionLiteral(probFunction);}
			SQUARE_PAREN_L 
				( 
				  pdf_sample = real_pdf_sample
				  {((BoxedPDF)probFunction).getSamples().add(pdf_sample);})+ 
	 		SQUARE_PAREN_R 
			|
			BOOLPMF 
				{probFunction = ProbfunctionFactory.eINSTANCE.createProbabilityMassFunction();
				   pfl.setFunction_ProbabilityFunctionLiteral(probFunction);
				   ((ProbabilityMassFunction)probFunction).setOrderedDomain(false);
				   }
			(LPAREN
			  ORDERED_DEF
			  {((ProbabilityMassFunction)probFunction).setOrderedDomain(true);}
			RPAREN)?
			SQUARE_PAREN_L 
				( 
				ssample = boolsample
			   	{((ProbabilityMassFunction)probFunction).getSamples().add(ssample);})+ 
			SQUARE_PAREN_R
;	 		

numeric_int_sample returns [Sample s]
	@init {s = null;} : 
		LPAREN
			{s = ProbfunctionFactory.eINSTANCE.createSample();} 
			n=signed_number
			{s.setValue(Integer.parseInt(n));} 
			SEMI 
			n2=NUMBER 
			{s.setProbability(Double.parseDouble(n2.getText()));} 
			RPAREN;
		
numeric_real_sample returns [Sample s]
	@init {s = null;} : 
		LPAREN
			{s = ProbfunctionFactory.eINSTANCE.createSample();} 
			n=signed_number
			{s.setValue(Double.parseDouble(n));} 
			SEMI 
			n2=NUMBER 
			{s.setProbability(Double.parseDouble(n2.getText()));} 
			RPAREN;
			
real_pdf_sample returns [ContinuousSample s]
	@init {s = null;} : 
		LPAREN
			{s = ProbfunctionFactory.eINSTANCE.createContinuousSample();} 
			n=signed_number
			{s.setValue(Double.parseDouble(n));} 
			SEMI 
			n2=NUMBER 
			{s.setProbability(Double.parseDouble(n2.getText()));} 
		 RPAREN;
			
stringsample returns [Sample s] 
	 : 
		LPAREN
			{s = ProbfunctionFactory.eINSTANCE.createSample();} 
		str=STRING_LITERAL 
			{s.setValue(str.getText().replace("\"",""));} 
		SEMI
		n=NUMBER 
			{s.setProbability(Double.parseDouble(n.getText()));} 
		RPAREN;

boolsample returns [Sample s] 
	 : 
		LPAREN
			{s = ProbfunctionFactory.eINSTANCE.createSample();} 
		str = boolean_keywords
		{s.setValue(str.equals("true"));}
		SEMI
		n=NUMBER 
			{s.setProbability(Double.parseDouble(n.getText()));} 
		RPAREN;

boolean_keywords returns [String keyword]
	:
		(
		FALSE
			{keyword = "false";} 
		|
		TRUE
			{keyword = "true"; }
		);
		
signed_number returns [String stringValue]
@init {stringValue = "";}
: (MINUS
{
  stringValue +="-";
})?
n=NUMBER
{
stringValue += n.getText();
}
;

characterisation_keywords returns [String keyword] 
@init {keyword = null;}:
 (BYTESIZE {keyword="BYTESIZE";}
 | STRUCTURE {keyword="STRUCTURE";}
 | NUMBER_OF_ELEMENTS {keyword="NUMBER_OF_ELEMENTS";}
 | TYPE {keyword="TYPE";}
 | VALUE {keyword="VALUE";}
);
 	
scoped_id returns [AbstractNamedReference ref]
	@init {ref = null;
		ArrayList<String> nameParts = new ArrayList<String>();} :
		
	id1=ID {nameParts.add(id1.getText());} 
	    (DOT (id2=ID {nameParts.add(id2.getText());} | INNER {nameParts.add("INNER");} ))*
	{
	AbstractNamedReference firstNsRef=null;
	NamespaceReference lastNsRef = null;
	for (int i=0; i < nameParts.size()-1; i++)
	{
		NamespaceReference nsRef = StoexFactory.eINSTANCE.createNamespaceReference();
		nsRef.setReferenceName(nameParts.get(i));
		if (lastNsRef != null)
			lastNsRef.setInnerReference_NamespaceReference(nsRef);
		if (i == 0)
		   	firstNsRef = nsRef;
		lastNsRef = nsRef;
	}
	VariableReference varRef = StoexFactory.eINSTANCE.createVariableReference();
	varRef.setReferenceName(nameParts.get(nameParts.size()-1));
		if (lastNsRef != null) {
			lastNsRef.setInnerReference_NamespaceReference(varRef);
			ref = firstNsRef;
		}
		else
			ref = varRef;
	}
;

OR
	:	'OR'
	;

XOR
	:	'XOR'
	;

AND
	:	'AND'
	;

NOT
	:	'NOT'
	;

INTPMF
	:	'IntPMF'
	;

DOUBLEPMF
	:	'DoublePMF'
	;

ENUMPMF
	:	'EnumPMF'
	;

DOUBLEPDF
	:	'DoublePDF'
	;

BOOLPMF
	:	'BoolPMF'
	;

UNIT
	:	'unit'
	;

BOOL
	:	'"bool"'
	;

FALSE
	:	'false'
	;

TRUE
	:	'true'
	;

BYTESIZE
	:	'BYTESIZE'
	;

STRUCTURE
	:	'STRUCTURE'
	;

NUMBER_OF_ELEMENTS
	:	'NUMBER_OF_ELEMENTS'
	;

TYPE
	:	'TYPE'
	;

VALUE
	:	'VALUE'
	;

INNER
	:	'INNER'
	;

PLUS  : '+' ;
MINUS : '-' ;
MUL   : '*' ;
DIV   : '/' ;
MOD   : '%' ;
POW   : '^' ;
LPAREN: '(' ;
RPAREN: ')' ;
SEMI  : ';' ;
COLON 	:	',';
DEFINITION : '=' ;
ORDERED_DEF
	:	'ordered';
EQUAL : '==' ;
SQUARE_PAREN_L : '[' ;
SQUARE_PAREN_R : ']' ;
fragment DIGIT : '0'..'9' ;
NUMBER :  (DIGIT)+ ('.' (DIGIT)+)? Exponent?;
fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;
fragment ALPHA : 'a'..'z' | 'A'..'Z' ;
NOTEQUAL : '<>' ;
GREATER : '>' ;
LESS : '<' ;
GREATEREQUAL : '>=' ;
LESSEQUAL : '<=' ;
STRING_LITERAL : '\"' (ALPHA|'_'|' '|DIGIT)+ '\"' ;
DOT: '.';

/* Inspired by Java grammar */
ID : IDSTART IDPART*;
fragment IDSTART : ALPHA|'_';
fragment IDPART : IDSTART | DIGIT; 

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? ('\n'|EOF) {$channel=HIDDEN;}
    ;


