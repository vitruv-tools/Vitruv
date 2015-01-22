package a.b.c;

class GhostFieldReference {

	/*@ ghost public Object owner; */
		
	/*@
		requires owner2 != null;
		public model Object modelMethod() {
			return owner2;
		}
	 */

}