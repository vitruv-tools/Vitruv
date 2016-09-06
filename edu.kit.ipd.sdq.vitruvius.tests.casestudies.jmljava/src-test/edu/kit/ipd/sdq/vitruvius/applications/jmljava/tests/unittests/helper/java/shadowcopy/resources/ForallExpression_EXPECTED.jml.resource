package a.b.c;

class ForallExpression {

    /*@ 
      requires length >= 0;
	  requires qq123 != null;
	  requires srcOff >= 0 && srcOff + length <= qq123.length;
	  requires dest != null;
	  requires destOff >= 0 && destOff + length <= dest.length;
	  ensures (\forall short i; i>=0 && i<=length; 
	     dest[destOff + i] == \old(qq123[srcOff + i]));
    @*/
    public static short arrayCopy(byte[] src, short  srcOff,
                                  byte[] dest, short  destOff, short  length)
                                  throws TransactionException, NullPointerException, ArrayIndexOutOfBoundsException;

}