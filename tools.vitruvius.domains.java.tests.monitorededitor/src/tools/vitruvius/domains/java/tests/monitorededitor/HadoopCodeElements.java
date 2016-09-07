package tools.vitruvius.domains.java.tests.monitorededitor;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class HadoopCodeElements {

    enum HadoopCoompilationUnitSize {
        LOC28, LOC350, LOC1045, LOC2050, LOC15812, LOC67175
    }

    // compilation units
    private static String loc28CU = "ByteArray.java";
    private static String loc350CU = "INode.java";
    private static String loc1045CU = "FSEditLog.java";
    private static String loc2050CU = "DFSClient.java";
    // generated compilation units
    private static String loc15812CU = "DataTransferProtos.java";
    private static String loc67175CU = "ClientNamenodeProtocolProtos.java";

    // types
    private static String loc28TYPE = "ByteArray";
    private static String loc350TYPE = "INode";
    private static String loc1045TYPE = "FSEditLog";
    private static String loc2050TYPE = "DFSClient";
    private static String loc15812TYPE = "DataTransferProtos";
    private static String loc67175TYPE = "ClientNamenodeProtocolProtos";

    // methods
    private static String loc28METHOD = "getBytes";
    private static String loc350METHOD = "getParent";
    private static String loc1045METHOD = "initJournals";
    private static String loc2050METHOD = "connectToDN";
    private static String loc15812METHOD = "registerAllExtensions";
    private static String loc67175METHOD = "registerAllExtensions";

    static IMethod getMethod(HadoopCoompilationUnitSize size) throws JavaModelException {
        switch (size) {
        case LOC28:
            return CodeElementUtil.getMethod(loc28CU, loc28TYPE, loc28METHOD);
        case LOC350:
            return CodeElementUtil.getMethod(loc350CU, loc350TYPE, loc350METHOD);
        case LOC1045:
            return CodeElementUtil.getMethod(loc1045CU, loc1045TYPE, loc1045METHOD);
        case LOC2050:
            return CodeElementUtil.getMethod(loc2050CU, loc2050TYPE, loc2050METHOD);
        case LOC15812:
            return CodeElementUtil.getMethod(loc15812CU, loc15812TYPE, loc15812METHOD);
        case LOC67175:
            return CodeElementUtil.getMethod(loc67175CU, loc67175TYPE, loc67175METHOD);

        default:
            return null;
        }
    }

    static ICompilationUnit getCompilationUnit(HadoopCoompilationUnitSize size) throws JavaModelException {
        switch (size) {
        case LOC28:
            CodeElementUtil.getCompilationUnit(loc28CU);
        case LOC350:
            return CodeElementUtil.getCompilationUnit(loc350CU);
        case LOC1045:
            CodeElementUtil.getCompilationUnit(loc1045CU);
        case LOC2050:
            return CodeElementUtil.getCompilationUnit(loc2050CU);
        case LOC15812:
            return CodeElementUtil.getCompilationUnit(loc15812CU);
        case LOC67175:
            return CodeElementUtil.getCompilationUnit(loc67175CU);

        default:
            return null;
        }
    }

    static IType getType(HadoopCoompilationUnitSize size) throws JavaModelException {
        switch (size) {
        case LOC28:
            return CodeElementUtil.getType(loc28CU, loc28TYPE);
        case LOC350:
            return CodeElementUtil.getType(loc350CU, loc350TYPE);
        case LOC1045:
            return CodeElementUtil.getType(loc1045CU, loc1045TYPE);
        case LOC2050:
            return CodeElementUtil.getType(loc2050CU, loc2050TYPE);
        case LOC15812:
            return CodeElementUtil.getType(loc15812CU, loc15812TYPE);
        case LOC67175:
            return CodeElementUtil.getType(loc67175CU, loc67175TYPE);

        default:
            return null;
        }
    }

}
