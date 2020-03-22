package tools.vitruv.framework.tuid;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tools.vitruv.framework.util.VitruviusConstants;

public class TuidTest {

	@Test
    public void test() {
        String sep = VitruviusConstants.getTuidSegmentSeperator();
        // 1: p#s ..... -> p2#s ..... 2-> p2#s2 .............................. 5-> q#s2
        // 2: p#s#c.i . -> p2#s#c.i . 2-> p2#s2#c.i ........... 4-> p2#s2#ci.. 5-> q#s2#ci
        // 3: p#t#d ... -> p2#t#d ............................................ 5-> q#t#d
        // 4: q#t#e#a ............................. 3-> q#u#e#a ....................... 6-> r#u#e#a
        // 5: q#u#f ................................................................... 6-> r#u#f
        // 6: q#u#e ................................................................... 6-> r#u#e
        // 7: p#s#c.i#a -> p2#s#c.i#a 2-> p2#s2#c.i#a ......... 4-> p2#s2#ci#a 5-> q#s2#ci#a
        // 8: p#t ..... -> p2#t .............................................. 5-> q#t
        // 9: r
        // legend:
        // -> = after first operation (rename folder p to p2)
        // 2-> = after second operation (rename folder s to s2)
        // 3-> after third operation (move folder, package, or file t to existing folder,
        // package, or file u = merge)
        // 4-> after fourth operation (rename package c.i to ci)
        // 5-> after fifth operation (rename folder p2 to q)
        // 6-> after sixth operation (move folder u in q to u in r = no merge)

        /**************************/
        /** BEGIN INITIALIZATION **/
        /**************************/
        String s1prefix = "p";
        String s1 = s1prefix + sep + "s";
        Tuid tuid1 = Tuid.getInstance(s1);
        assertEquals(s1, tuid1.toString());
        String s2 = s1 + sep + "c.i";
        Tuid tuid2 = Tuid.getInstance(s2);
        assertEquals(s2, tuid2.toString());
        String s3 = s1prefix + sep + "t" + sep + "d";
        Tuid tuid3 = Tuid.getInstance(s3);
        assertEquals(s3, tuid3.toString());
        String s4prefix = "q" + sep + "t";
        String s4 = s4prefix + sep + "e" + sep + "a";
        Tuid tuid4 = Tuid.getInstance(s4);
        assertEquals(s4, tuid4.toString());
        String s5prefix = "q" + sep + "u";
        String s5 = s5prefix + sep + "f";
        Tuid tuid5 = Tuid.getInstance(s5);
        assertEquals(s5, tuid5.toString());
        String s6 = s5prefix + sep + "e";
        Tuid tuid6 = Tuid.getInstance(s6);
        assertEquals(s6, tuid6.toString());
        String s7 = s1 + sep + "c.i" + sep + "a";
        Tuid tuid7 = Tuid.getInstance(s7);
        assertEquals(s7, tuid7.toString());
        String s8 = s1prefix + sep + "t";
        Tuid tuid8 = Tuid.getInstance(s8);
        assertEquals(s8, tuid8.toString());
        String s9 = "r";
        Tuid tuid9 = Tuid.getInstance(s9);
        assertEquals(s9, tuid9.toString());
        System.out
        .println("**** BEGIN INITIALIZATION ****\n" + Tuid.toStrings() + "\n**** END INITIALIZATION ****\n\n");
        assertTrue(Tuid.validate());
        /************************/
        /** END INITIALIZATION **/
        /************************/

        /*********************************************/
        /** first operation (rename folder p to p2) **/
        /*********************************************/
        Tuid tuid1prefix = Tuid.getInstance(s1prefix);
        assertEquals(s1prefix, tuid1prefix.toString());
        String s1prefixr = "p2";
        Tuid tuid1prefixr = Tuid.getInstance(s1prefixr);
        tuid1prefix.updateTuid(tuid1prefixr);
        System.out.println("**** BEGIN FIRST OPERATION ****\n" + Tuid.toStrings()
                + "\n**** END FIRST OPERATION ****\n\n");
        assertEquals(s1prefixr, tuid1prefix.toString());
        String s1r = s1prefixr + sep + "s";
        assertEquals(s1r, tuid1.toString());
        String s2r = s1r + sep + "c.i";
        assertEquals(s2r, tuid2.toString());
        String s3r = s1prefixr + sep + "t" + sep + "d";
        assertEquals(s3r, tuid3.toString());
        String s7r = s1r + sep + "c.i" + sep + "a";
        assertEquals(s7r, tuid7.toString());
        String s8r = s1prefixr + sep + "t";
        assertEquals(s8r, tuid8.toString());
        assertTrue(Tuid.validate());

        /**********************************************/
        /** second operation (rename folder s to s2) **/
        /**********************************************/
        tuid1.updateTuid(Tuid.getInstance(s1prefixr + sep + "s2"));
        System.out.println("**** BEGIN SECOND OPERATION ****\n" + Tuid.toStrings()
                + "\n**** END SECOND OPERATION ****\n\n");
        String s1rr = "p2" + sep + "s2";
        assertEquals(s1rr, tuid1.toString());
        String s2rr = s1rr + sep + "c.i";
        assertEquals(s2rr, tuid2.toString());
        String s7rr = s2rr + sep + "a";
        assertEquals(s7rr, tuid7.toString());
        assertTrue(Tuid.validate());

        /***********************************************************/
        /** third operation (move t to existing folder u = merge) **/
        /***********************************************************/
        Tuid tuid4prefix = Tuid.getInstance(s4prefix);
        Tuid tuid5prefix = Tuid.getInstance(s5prefix);
        tuid4prefix.updateTuid(tuid5prefix);
        System.out.println("**** BEGIN THIRD OPERATION ****\n" + Tuid.toStrings()
                + "\n**** END THIRD OPERATION ****\n\n\n");
        assertEquals(s5prefix, tuid4prefix.toString());
        String s4rrr = s5prefix + sep + "e" + sep + "a";
        assertEquals(s4rrr, tuid4.toString());
        assertEquals(s5, tuid5.toString());
        assertTrue(Tuid.validate());

        /*************************************************/
        /** fourth operation (rename package c.i to ci) **/
        /*************************************************/
        String s2rrrr = s1rr + sep + "ci";
        tuid2.updateTuid(Tuid.getInstance(tuid1.toString() + sep + "ci"));
        System.out.println("**** BEGIN FOURTH OPERATION ****\n" + Tuid.toStrings()
                + "\n**** END FOURTH OPERATION ****\n\n");
        assertEquals(s2rrrr, tuid2.toString());
        String s7rrrr = s2rrrr + sep + "a";
        assertEquals(s7rrrr, tuid7.toString());
        assertTrue(Tuid.validate());

        /*********************************************/
        /** fifth operation (rename folder p2 to q) **/
        /*********************************************/
        String s1prefixrrrrr = "q";
        tuid1prefix.updateTuid(Tuid.getInstance(s1prefixrrrrr));
        System.out.println("**** BEGIN FIFTH OPERATION ****\n" + Tuid.toStrings()
                + "\n**** END FIFTH OPERATION ****\n\n");
        String s1rrrrr = s1prefixrrrrr + sep + "s2";
        assertEquals(s1rrrrr, tuid1.toString());
        String s2rrrrr = s1rrrrr + sep + "ci";
        assertEquals(s2rrrrr, tuid2.toString());
        String s3rrrrr = s1prefixrrrrr + sep + "t" + sep + "d";
        assertEquals(s3rrrrr, tuid3.toString());
        String s4rrrrr = s1prefixrrrrr + sep + "u" + sep + "e" + sep + "a";
        assertEquals(s4rrrrr, tuid4.toString());
        String s7rrrrr = s2rrrrr + sep + "a";
        assertEquals(s7rrrrr, tuid7.toString());
        String s8rrrrr = s1prefixrrrrr + sep + "t";
        assertEquals(s8rrrrr, tuid8.toString());

        // unchanged Tuids
        assertEquals(s5, tuid5.toString());
        assertEquals(s6, tuid6.toString());
        assertTrue(Tuid.validate());

        /***************************************************************/
        /** sixth operation (move folder u in q to u in r = no merge) **/
        /***************************************************************/
        String s5prefixrrrrrr = s9 + sep + "u";
        Tuid tuid5prefixrrrrrr = Tuid.getInstance(s5prefixrrrrrr);
        tuid5prefix.updateTuid(tuid5prefixrrrrrr);
        System.out.println("**** BEGIN SIXTH OPERATION ****\n" + Tuid.toStrings()
                + "\n**** END SIXTH OPERATION ****\n\n");
        assertEquals(s5prefixrrrrrr, tuid5prefix.toString());
        String s4rrrrrr = s5prefixrrrrrr + sep + "e" + sep + "a";
        assertEquals(s4rrrrrr, tuid4.toString());
        String s5rrrrrr = s5prefixrrrrrr + sep + "f";
        assertEquals(s5rrrrrr, tuid5.toString());
        String s6rrrrrr = s5prefixrrrrrr + sep + "e";
        assertEquals(s6rrrrrr, tuid6.toString());

        // unchanged Tuid
        assertEquals(s9, tuid9.toString());
        assertTrue(Tuid.validate());
    }

    @Test
    public void testUpdateSingleSegmentTargetContainsChildren() {
        boolean useUpdateMultipleSegmentsMethod = false;
        testUpdateSingleSegment(useUpdateMultipleSegmentsMethod);
    }

    @Test
    public void testUpdateSingleSegmentUsingMultipleSegmentChange() {
        boolean useUpdateMultipleSegmentsMethod = true;
        testUpdateSingleSegment(useUpdateMultipleSegmentsMethod);
    }

    public void testUpdateSingleSegment(final boolean useUpdateMultipleSegmentsMethod) {
        String[] input = new String[] { "prefix#b", "prefix#b#c", "prefix#b#c#d", "prefix#b#c2#d" };
        String[] expected = new String[] { "prefix#b", "prefix#b#c2", "prefix#b#c2#d", "prefix#b#c2#d" };
        testUpdateSegment(input, expected, "prefix#b#c", "prefix#b#c2", useUpdateMultipleSegmentsMethod);
    }

	private void testUpdateSegment(final String[] input, final String[] expected, final String oldTuidString,
            final String newTuidString, final boolean updateMultipleSegments) {
        // construct Tuids
        List<Tuid> tuids = new ArrayList<Tuid>();
        for (String tuidString : input) {
            tuids.add(Tuid.getInstance(tuidString));
        }

        Tuid oldTuid = Tuid.getInstance(oldTuidString);
        Tuid newTuid = Tuid.getInstance(newTuidString);
        
        oldTuid.updateTuid(newTuid);

        // validate Tuid
        assertTrue(Tuid.validate());

        // check renaming
        String[] actual = new String[tuids.size()];
        for (int i = 0; i < tuids.size(); ++i) {
            actual[i] = tuids.get(i).toString();
        }
        assertArrayEquals(expected, actual);

    }

	@Test
    public void testUpdateSingleSegmentTargetContainsNoChildren() {
        String[] input = new String[] { "prefix2#a", "prefix2#a#b", "prefix2#a#c" };
        String[] expected = new String[] { "prefix2#a", "prefix2#a#c", "prefix2#a#c" };

        // construct Tuids
        List<Tuid> tuids = new ArrayList<Tuid>();
        for (String tuidString : input) {
            tuids.add(Tuid.getInstance(tuidString));
        }

        // rename Tuids
        Tuid oldTuid = Tuid.getInstance("prefix2#a#b");
        Tuid newTuid = Tuid.getInstance("prefix2#a#c");
        oldTuid.updateTuid(newTuid);

        // validate Tuid
        assertTrue(Tuid.validate());

        // check renaming
        String[] actual = new String[tuids.size()];
        for (int i = 0; i < tuids.size(); ++i) {
            actual[i] = tuids.get(i).toString();
        }
        assertArrayEquals(expected, actual);
    }
    
    
    @Test
    public void testRenameHierarchical() {
    	String sep = VitruviusConstants.getTuidSegmentSeperator();
    	String prefix = "prefix" + sep;
    	String s1 = prefix + "a";
    	Tuid t1 = Tuid.getInstance(s1);
    	String s1new = prefix + "d";
    	Tuid t1new = Tuid.getInstance(s1new);
    	String s1new2 = prefix + "e";
    	Tuid t1new2 = Tuid.getInstance(s1new2);
    	String s2 = prefix + "a" + sep + "b";
    	Tuid t2 = Tuid.getInstance(s2);
    	String s2new = prefix + "d" + sep + "b";
    	Tuid t2new = Tuid.getInstance(s2new);
    	String s3 = prefix + "a" + sep + "b" + sep + "c";
    	Tuid t3 = Tuid.getInstance(s3);
    	t2.updateTuid(t2new);
    	assertEquals(t2, t2new);
    	assertEquals(s2new + sep + "c", t3.toString());
    	t1.updateTuid(t1new);
    	assertEquals(t1, t1new);
    	String s4 = prefix + "f" + sep + "b";
    	Tuid t4 = Tuid.getInstance(s4);
    	String s4new = prefix + "e" + sep + "b";
    	Tuid t4new = Tuid.getInstance(s4new);
    	t4.updateTuid(t4new);
    	assertEquals(t4, t4new);
    	t1.updateTuid(t1new2);
    	assertEquals(t1new2, t1new);
    	assertEquals(t1new2, t1);
    	assertEquals(prefix + "e" + sep + "b", t2.toString());
    	assertEquals(prefix + "e" + sep + "b" + sep + "c", t3.toString());
    	assertEquals(t2, t4);
    	assertEquals(t2new, t4);
    }
    
    @Test
    public void testRenameDuplicateTuidInstance() {
    	String sep = VitruviusConstants.getTuidSegmentSeperator();
    	String prefix = "prefix" + sep;
    	String s1 = prefix + "a";
    	Tuid t1 = Tuid.getInstance(s1);
    	String s1new = prefix + "d";
    	Tuid t1new = Tuid.getInstance(s1new);
    	String s1new2 = prefix + "e";
    	Tuid t1new2 = Tuid.getInstance(s1new2);
    	t1.updateTuid(t1new);
    	assertEquals(t1, t1new);
    	t1.updateTuid(t1new2);
    	assertEquals(t1new2, t1new);
    	assertEquals(t1new2, t1);
    }
    
    
    @Test
    public void testRenameChildMerge() {
    	String sep = VitruviusConstants.getTuidSegmentSeperator();
    	String prefix = "prefix" + sep;
    	String s1 = prefix + "a";
    	Tuid t1 = Tuid.getInstance(s1);
    	String s1new = prefix + "b";
    	Tuid t1new = Tuid.getInstance(s1new);
    	String s2 = prefix + "a" + sep + "c";
    	Tuid t2 = Tuid.getInstance(s2);
    	String s2new = prefix + "b" + sep + "c";
    	Tuid t2new = Tuid.getInstance(s2new);
    	String s2new2 = prefix + "b" + sep + "d";
    	Tuid t2new2 = Tuid.getInstance(s2new2);
    	t1.updateTuid(t1new);
    	assertEquals(t1, t1new);
    	assertEquals(t2, t2new);
    	t2new.updateTuid(t2new2);
    	assertEquals(t2, t2new2);
    	assertEquals(t2, t2new);
    	assertEquals(prefix + "b" + sep + "d", t2.toString());
    	assertEquals(prefix + "b" + sep + "d", t2new.toString());
    }
}
