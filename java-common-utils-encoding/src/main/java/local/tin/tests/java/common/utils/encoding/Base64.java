package local.tin.tests.java.common.utils.encoding;

/**
 *
 * @author benitodarder
 */
public class Base64 {

    public static final char[] CHAR_INDEXES_ENCODING = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    public static final int[] CHAR_INDEXES_DECODING = { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 0,  0,  0,  0,
                                                        0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 0,  0,  0,  0,
                                                        0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 62, 63, 62, 62, 63, 
                                                        52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 0,  0,  0,  0,  0,  0,  
                                                        0,  0,  1,  2,  3,  4,  5,  6, 7,  8,  9, 10, 11, 12, 13, 14,
                                                        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,  0, 0,  0,  0, 63,
                                                        0, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                                                        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
    public static final char PADDING_CHAR = '=';
    public static final char NULL_CHAR = '\u0000';

    private Base64() {
    }

    public static Base64 getInstance() {
        return Base64Holder.INSTANCE;
    }

    private static class Base64Holder {

        private static final Base64 INSTANCE = new Base64();
    }

    public byte[] encode(String string) {
        int paddingChars = 0;
        StringBuilder stringBuilder = new StringBuilder(string);
        while (stringBuilder.length() % 3 != 0) {
            paddingChars++;
            stringBuilder.append(NULL_CHAR);
        }
        String workingString = stringBuilder.toString();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < stringBuilder.length(); i = i + 3) {
            int blockAsInteger = (workingString.charAt(i) << 16) + (workingString.charAt(i + 1) << 8) + (workingString.charAt(i + 2));
            output.append(CHAR_INDEXES_ENCODING[((blockAsInteger >> 18) & 63)]).append(CHAR_INDEXES_ENCODING[((blockAsInteger >> 12) & 63)]).append(CHAR_INDEXES_ENCODING[((blockAsInteger >> 6) & 63)]).append(CHAR_INDEXES_ENCODING[blockAsInteger & 63]);
        }
        for (int i = 0; i < paddingChars; i++) {
            output.append(PADDING_CHAR);
        }
        return output.toString().getBytes();
    }

    public String decode(byte[] source) {
        for (int i = (source.length - 1); source[i] == PADDING_CHAR; i++) {
            source[i] = NULL_CHAR;
        }
        String sourceAsString = new String(source);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sourceAsString.length(); i = i + 4) {
            int blockAsInteger = (sourceAsString.charAt(i) << 18) | (sourceAsString.charAt(i + 1) << 12) | (sourceAsString.charAt(i + 2) << 6) | sourceAsString.charAt(i + 3);
            stringBuilder.append((char) ((blockAsInteger >>> 16))).append((char)((blockAsInteger >>> 8) & 255)).append((char)(blockAsInteger & 255));
        }
        return stringBuilder.toString();
    }

}
