package edu.kit.ipd.sdq.vitruvius.domains.java.util.gitchangereplay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class IoUtil {
	
	public static String readerToString(Reader r) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader br = new BufferedReader(r);

        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = br.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        br.close();

        return  fileData.toString();
    }

}
