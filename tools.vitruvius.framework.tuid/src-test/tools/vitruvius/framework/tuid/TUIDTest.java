package tools.vitruvius.framework.tuid;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tools.vitruvius.framework.tuid.TUID;
import tools.vitruvius.framework.util.VitruviusConstants;

public class TUIDTest {

    @SuppressWarnings("deprecation")
	@Test
    public void test() {
        String sep = VitruviusConstants.getTUIDSegmentSeperator();
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
        TUID tuid1 = TUID.getInstance(s1);
        assertEquals(s1, tuid1.toString());
        String s2 = s1 + sep + "c.i";
        TUID tuid2 = TUID.getInstance(s2);
        assertEquals(s2, tuid2.toString());
        String s3 = s1prefix + sep + "t" + sep + "d";
        TUID tuid3 = TUID.getInstance(s3);
        assertEquals(s3, tuid3.toString());
        String s4prefix = "q" + sep + "t";
        String s4 = s4prefix + sep + "e" + sep + "a";
        TUID tuid4 = TUID.getInstance(s4);
        assertEquals(s4, tuid4.toString());
        String s5prefix = "q" + sep + "u";
        String s5 = s5prefix + sep + "f";
        TUID tuid5 = TUID.getInstance(s5);
        assertEquals(s5, tuid5.toString());
        String s6 = s5prefix + sep + "e";
        TUID tuid6 = TUID.getInstance(s6);
        assertEquals(s6, tuid6.toString());
        String s7 = s1 + sep + "c.i" + sep + "a";
        TUID tuid7 = TUID.getInstance(s7);
        assertEquals(s7, tuid7.toString());
        String s8 = s1prefix + sep + "t";
        TUID tuid8 = TUID.getInstance(s8);
        assertEquals(s8, tuid8.toString());
        String s9 = "r";
        TUID tuid9 = TUID.getInstance(s9);
        assertEquals(s9, tuid9.toString());
        System.out
        .println("**** BEGIN INITIALIZATION ****\n" + TUID.toStrings() + "\n**** END INITIALIZATION ****\n\n");
        assertTrue(TUID.validate());
        /************************/
        /** END INITIALIZATION **/
        /************************/

        /*********************************************/
        /** first operation (rename folder p to p2) **/
        /*********************************************/
        TUID tuid1prefix = TUID.getInstance(s1prefix);
        assertEquals(s1prefix, tuid1prefix.toString());
        String s1prefixr = "p2";
        tuid1prefix.renameLastSegment(s1prefixr, null);
        System.out.println("**** BEGIN FIRST OPERATION ****\n" + TUID.toStrings()
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
        assertTrue(TUID.validate());

        /**********************************************/
        /** second operation (rename folder s to s2) **/
        /**********************************************/
        tuid1.renameLastSegment("s2", null);
        System.out.println("**** BEGIN SECOND OPERATION ****\n" + TUID.toStrings()
                + "\n**** END SECOND OPERATION ****\n\n");
        String s1rr = "p2" + sep + "s2";
        assertEquals(s1rr, tuid1.toString());
        String s2rr = s1rr + sep + "c.i";
        assertEquals(s2rr, tuid2.toString());
        String s7rr = s2rr + sep + "a";
        assertEquals(s7rr, tuid7.toString());
        assertTrue(TUID.validate());

        /***********************************************************/
        /** third operation (move t to existing folder u = merge) **/
        /***********************************************************/
        TUID tuid4prefix = TUID.getInstance(s4prefix);
        TUID tuid5prefix = TUID.getInstance(s5prefix);
        tuid4prefix.renameSegments(tuid5prefix);
        System.out.println("**** BEGIN THIRD OPERATION ****\n" + TUID.toStrings()
                + "\n**** END THIRD OPERATION ****\n\n\n");
        assertEquals(s5prefix, tuid4prefix.toString());
        String s4rrr = s5prefix + sep + "e" + sep + "a";
        assertEquals(s4rrr, tuid4.toString());
        assertEquals(s5, tuid5.toString());
        assertTrue(TUID.validate());

        /*************************************************/
        /** fourth operation (rename package c.i to ci) **/
        /*************************************************/
        String s2rrrr = s1rr + sep + "ci";
        tuid2.renameLastSegment("ci", null);
        System.out.println("**** BEGIN FOURTH OPERATION ****\n" + TUID.toStrings()
                + "\n**** END FOURTH OPERATION ****\n\n");
        assertEquals(s2rrrr, tuid2.toString());
        String s7rrrr = s2rrrr + sep + "a";
        assertEquals(s7rrrr, tuid7.toString());
        assertTrue(TUID.validate());

        /*********************************************/
        /** fifth operation (rename folder p2 to q) **/
        /*********************************************/
        String s1prefixrrrrr = "q";
        tuid1prefix.renameLastSegment(s1prefixrrrrr, null);
        System.out.println("**** BEGIN FIFTH OPERATION ****\n" + TUID.toStrings()
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

        // unchanged TUIDs
        assertEquals(s5, tuid5.toString());
        assertEquals(s6, tuid6.toString());
        assertTrue(TUID.validate());

        /***************************************************************/
        /** sixth operation (move folder u in q to u in r = no merge) **/
        /***************************************************************/
        String s5prefixrrrrrr = s9 + sep + "u";
        TUID tuid5prefixrrrrrr = TUID.getInstance(s5prefixrrrrrr);
        tuid5prefix.renameSegments(tuid5prefixrrrrrr);
        System.out.println("**** BEGIN SIXTH OPERATION ****\n" + TUID.toStrings()
                + "\n**** END SIXTH OPERATION ****\n\n");
        assertEquals(s5prefixrrrrrr, tuid5prefix.toString());
        String s4rrrrrr = s5prefixrrrrrr + sep + "e" + sep + "a";
        assertEquals(s4rrrrrr, tuid4.toString());
        String s5rrrrrr = s5prefixrrrrrr + sep + "f";
        assertEquals(s5rrrrrr, tuid5.toString());
        String s6rrrrrr = s5prefixrrrrrr + sep + "e";
        assertEquals(s6rrrrrr, tuid6.toString());

        // unchanged TUID
        assertEquals(s9, tuid9.toString());
        assertTrue(TUID.validate());
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

    @SuppressWarnings("deprecation")
	private void testUpdateSegment(final String[] input, final String[] expected, final String oldTUIDString,
            final String newTUIDString, final boolean updateMultipleSegments) {
        // construct TUIDs
        List<TUID> tuids = new ArrayList<TUID>();
        for (String tuidString : input) {
            tuids.add(TUID.getInstance(tuidString));
        }

        TUID oldTUID = TUID.getInstance(oldTUIDString);
        TUID newTUID = TUID.getInstance(newTUIDString);
        
        oldTUID.renameSegments(newTUID);

        // validate TUID
        assertTrue(TUID.validate());

        // check renaming
        String[] actual = new String[tuids.size()];
        for (int i = 0; i < tuids.size(); ++i) {
            actual[i] = tuids.get(i).toString();
        }
        assertArrayEquals(expected, actual);

    }

    @Test
    public void testMultipleSegmentChange() {
        String[] input = new String[] { "pre#fix#b#suffix", "pre#fix#b#c#suffix", "pre#fix#b#c#d#suffix",
        "pre#fix#b#c2#d#suffix" };
        String[] expected = new String[] { "pre#fix#b2#suffix", "pre#fix#b2#c2#suffix2", "pre#fix#b2#c2#d#suffix",
        "pre#fix#b2#c2#d#suffix" };
        boolean updateMultipleSegments = true;
        testUpdateSegment(input, expected, "pre#fix#b#c#suffix", "pre#fix#b2#c2#suffix2", updateMultipleSegments);
    }
    
    @Test
    public void testScatteredSegmentChange() {
        String[] input = new String[] { "pre#a#b#c#d#e#f", "pre#a#b", "pre#a#b#cc", "pre#a#b#c#d#e", 
        "pre#a#b#c#d#e#ff" };
        String[] expected = new String[] { "pre#a2#b2#c#d2#e2#f", "pre#a2#b2", "pre#a2#b2#cc", "pre#a2#b2#c#d2#e2",
                "pre#a2#b2#c#d2#e2#ff" };
        boolean updateMultipleSegments = true;
        testUpdateSegment(input, expected, "pre#a#b#c#d#e#f", "pre#a2#b2#c#d2#e2#f", updateMultipleSegments);
    }

    @SuppressWarnings("deprecation")
	@Test
    public void testUpdateSingleSegmentTargetContainsNoChildren() {
        String[] input = new String[] { "prefix2#a", "prefix2#a#b", "prefix2#a#c" };
        String[] expected = new String[] { "prefix2#a", "prefix2#a#c", "prefix2#a#c" };

        // construct TUIDs
        List<TUID> tuids = new ArrayList<TUID>();
        for (String tuidString : input) {
            tuids.add(TUID.getInstance(tuidString));
        }

        // rename TUIDs
        TUID oldTUID = TUID.getInstance("prefix2#a#b");
        TUID newTUID = TUID.getInstance("prefix2#a#c");
        oldTUID.renameSegments(newTUID);

        // validate TUID
        assertTrue(TUID.validate());

        // check renaming
        String[] actual = new String[tuids.size()];
        for (int i = 0; i < tuids.size(); ++i) {
            actual[i] = tuids.get(i).toString();
        }
        assertArrayEquals(expected, actual);
    }
}
