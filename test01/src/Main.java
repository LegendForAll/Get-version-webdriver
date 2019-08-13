import java.io.File;

import java.io.IOException;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.VerRsrc.VS_FIXEDFILEINFO;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public class Main {

    public static void main(String[] args) {
//        int re = compareVersion("1.10.0.0.0.1", "1.10",".");
//        System.out.println("Output: " + re);
        getVersion("D:\\Setup\\Chrome_Setup\\ChromeSetup.exe");
    }

    private static void getVersion(String pathfile) {
        String filePath = pathfile;

        IntByReference dwDummy = new IntByReference();
        dwDummy.setValue(0);

        int versionlength = com.sun.jna.platform.win32.Version.INSTANCE.GetFileVersionInfoSize(
                        filePath, dwDummy);

        byte[] bufferarray = new byte[versionlength];
        Pointer lpData = new Memory(bufferarray.length);
        PointerByReference lplpBuffer = new PointerByReference();
        IntByReference puLen = new IntByReference();

        boolean fileInfoResult =
                com.sun.jna.platform.win32.Version.INSTANCE.GetFileVersionInfo(
                        filePath, 0, versionlength, lpData);

        boolean verQueryVal =
                com.sun.jna.platform.win32.Version.INSTANCE.VerQueryValue(
                        lpData, "\\", lplpBuffer, puLen);

        VS_FIXEDFILEINFO lplpBufStructure = new VS_FIXEDFILEINFO(lplpBuffer.getValue());
        lplpBufStructure.read();

        int v1 = (lplpBufStructure.dwFileVersionMS).intValue() >> 16;
        int v2 = (lplpBufStructure.dwFileVersionMS).intValue() & 0xffff;
        int v3 = (lplpBufStructure.dwFileVersionLS).intValue() >> 16;
        int v4 = (lplpBufStructure.dwFileVersionLS).intValue() & 0xffff;

        System.out.println(
                String.valueOf(v1) + "." +
                        String.valueOf(v2) + "." +
                        String.valueOf(v3) + "." +
                        String.valueOf(v4));
    }

    private static int compareVersion(String s1, String s2, String delimeter) {
        Integer versionA = Integer.parseInt( s1.replaceAll( "\\.", "" ) );
        Integer versionB = Integer.parseInt( s2.replaceAll( "\\.", "" ) );
        System.out.println("Output: " + versionA + "  "+ versionB);
        return versionA.compareTo(versionB);  //values are equal
    }
}
