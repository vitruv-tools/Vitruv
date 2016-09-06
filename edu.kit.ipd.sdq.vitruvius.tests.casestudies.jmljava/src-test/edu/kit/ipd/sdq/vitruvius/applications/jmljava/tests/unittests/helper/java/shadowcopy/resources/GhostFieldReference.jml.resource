package a.b.c;

class GhostFieldReference {

	/*@ ghost public Object owner; */
		
	/*@
		requires owner != null;
		public model Object modelMethod() {
			return owner;
		}
	 */

}