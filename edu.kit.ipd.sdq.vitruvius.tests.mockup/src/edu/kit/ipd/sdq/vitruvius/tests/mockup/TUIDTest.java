package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;

public class TUIDTest {

    @Test
    public void test() {
        String sep = VitruviusConstants.getTUIDSegmentSeperator();
        // 1: p#s -> p2#s --> p2#s2
        // 2: p#s#c.i -> p2#s#c.i --> p2#s2#c.i ----> p2#s2#ci
        // 3: p#t#d -> p2#t#d -----> q#t#d
        // 4: q#t#e ---> q#u
        // 5: q#u
        // 6: r
        // 7: p#s#c.i#a -> p2#s#c.i#a --> p2#s2#c.i#a ----> p2#s2#ci#a
        // 8: p#t -> p2#t -----> q#t
        // legend:
        // -> = after first operation (rename folder p to p2)
        // --> = after second operation (rename folder s to s2)
        // ---> after third operation (move folder, package, or file u to existing folder,
        // package, or file u = merge)
        // ----> after fourth operation (rename package c.i to ci)
        // -----> after fifth operation (move folder t in p to t in q)

        String s1 = "p" + sep + "s";
        TUID tuid1 = TUID.getInstance(s1);
        assertEquals(s1, tuid1.toString());
        String s2 = s1 + sep + "c.i";
        TUID tuid2 = TUID.getInstance(s2);
        assertEquals(s2, tuid2.toString());
        String s3 = "p" + sep + "t" + sep + "d";
        TUID tuid3 = TUID.getInstance(s3);
        assertEquals(s3, tuid3.toString());
        String s4 = "q" + sep + "t" + sep + "e";
        TUID tuid4 = TUID.getInstance(s4);
        assertEquals(s4, tuid4.toString());
        String s5 = "q" + sep + "u";
        TUID tuid5 = TUID.getInstance(s5);
        assertEquals(s5, tuid5.toString());
        String s6 = "r";
        TUID tuid6 = TUID.getInstance(s6);
        assertEquals(s6, tuid6.toString());
        String s7 = s1 + sep + "c.i" + sep + "a";
        TUID tuid7 = TUID.getInstance(s7);
        assertEquals(s7, tuid7.toString());
        String s8 = "p" + sep + "t";
        TUID tuid8 = TUID.getInstance(s8);
        assertEquals(s8, tuid8.toString());

        String s0 = "p";
        TUID tuid0 = TUID.getInstance(s0);
        assertEquals(s0, tuid0.toString());
        String s0r = "p2";
        tuid0.renameLastSegment(s0r);
        assertEquals(s0r, tuid0.toString());
        String s1r = "p2" + sep + "s";
        assertEquals(s1r, tuid1.toString());
        String s2r = s1r + sep + "c.i";
        assertEquals(s2r, tuid2.toString());
        String s3r = "p2" + sep + "t" + "d";
        assertEquals(s3r, tuid3.toString());
        String s7r = s1r + sep + "c.i" + sep + "a";
        assertEquals(s7r, tuid7.toString());
        String s8r = "p2" + sep + "t";
        assertEquals(s8r, tuid8.toString());

        String s2rr = "p2" + sep + "s2" + sep + "c.i";
        tuid2.renameLastSegment(s2rr);
        assertEquals(s2rr, tuid2.toString());
        String s1rr = "p2" + sep + "s2";
        assertEquals(s1rr, tuid1.toString());
        String s7rr = "p2" + sep + "s2" + sep + "c.i" + sep + "a";
        assertEquals(s7rr, tuid7.toString());

        tuid4.renameLastSegment(s5);
        assertEquals(s5, tuid4.toString());
        assertEquals(s5, tuid5.toString());
        assertEquals(tuid4, tuid5);

        String s2rrrr = "p2" + sep + "s2" + sep + "ci";
        tuid2.renameLastSegment(s2rrrr);
        assertEquals(s2rrrr, tuid2.toString());
        String s7rrrr = "p2" + sep + "s2" + sep + "ci" + sep + "a";
        assertEquals(s7rrrr, tuid7.toString());

        String s8rrrrr = "q" + sep + "t";
        tuid8.renameLastSegment(s8rrrrr);
        assertEquals(s8rrrrr, tuid8.toString());
        String s3rrrrr = "q" + sep + "t" + "d";
        assertEquals(s3rrrrr, tuid3.toString());
    }
}
