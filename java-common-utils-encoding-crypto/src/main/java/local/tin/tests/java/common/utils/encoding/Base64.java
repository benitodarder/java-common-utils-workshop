package local.tin.tests.java.common.utils.encoding;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author benitodarder
 */
public class Base64 {



    public static final char[] CHAR_INDEXES_ENCODING
            = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    public static final int[] CHAR_INDEXES_DECODING
            = {80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
                80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
                80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 62, 80, 80, 80, 0x3F,
                52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 80, 80, 80, 64, 80, 80,
                80, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 80, 80, 80, 80, 80,
                80, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 80, 80, 80, 80, 80};

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

    /**
     * Encodes in Base64 the byte array and returns the resulting string.
     *
     * @param source as byte array
     * @return String
     */
    public String encode(byte[] source) {
        int workingSourceSize = source.length;
        while (workingSourceSize % 3 != 0) {
            workingSourceSize++;
        }
        byte[] workingSource = new byte[workingSourceSize];
        System.arraycopy(source, 0, workingSource, 0, source.length);
        for (int i = source.length; i < workingSourceSize; i++) {
            workingSource[i] = NULL_CHAR;
        }
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < workingSource.length; i = i + 3) {
            int blockAsInteger = ((workingSource[i]  & 0xFF) << 0x10) + ((workingSource[i + 1] & 0xFF) << 0x08)  + (workingSource[i + 2] &  0xFF);
            output.append(CHAR_INDEXES_ENCODING[((blockAsInteger >> 0x12) & 0x3F)]).
                    append(CHAR_INDEXES_ENCODING[((blockAsInteger >> 0x0C) & 0x3F)]).
                    append(CHAR_INDEXES_ENCODING[((blockAsInteger >> 0x06) & 0x3F)]).
                    append(CHAR_INDEXES_ENCODING[blockAsInteger & 0x3F]);
        }
        for (int i = output.length() - (workingSourceSize - source.length); i < output.length(); i++) {
            output.setCharAt(i, PADDING_CHAR);
        }
        return output.toString();
    }

    /**
     * Decodes in Base64 the given source string.
     *
     * @param source as String
     * @return byte array
     */
    public byte[] decode(String source) {
        StringBuilder sourceAsString = new StringBuilder(source);
        int originalPaddingChars = getOriginalPaddingCount(sourceAsString);
        int totalPaddingChars = originalPaddingChars;
        for (; sourceAsString.length() % 4 != 0;) {
            totalPaddingChars++;
            sourceAsString.append(PADDING_CHAR);
        }
        List<Byte> byteList = new ArrayList();
        for (int i = 0; i < sourceAsString.length(); i = i + 4) {
            int blockAsInteger = getDecodingBlockAsInteger(sourceAsString, i);
            byteList.add((byte) ((blockAsInteger >>> 0x10)));
            byteList.add((byte) ((blockAsInteger >>> 0x08) & 0xFF));
            byteList.add((byte) (blockAsInteger & 0xFF));
        }
        byte[] result = new byte[byteList.size() - totalPaddingChars];
        for (int i = 0; i < result.length; i++) {
            result[i] = byteList.get(i);
        }
        return result;
    }

    private int getOriginalPaddingCount(StringBuilder sourceAsString) {
        int originalPaddingChars = 0;
        for (int i = sourceAsString.length() - 1; sourceAsString.charAt(i) == PADDING_CHAR; i--) {
            originalPaddingChars++;
        }
        return originalPaddingChars;
    }

    private int getDecodingBlockAsInteger(StringBuilder sourceAsString, int i) {
        int blockAsInteger = 0;
        if (sourceAsString.charAt(i) != PADDING_CHAR) {
            blockAsInteger = blockAsInteger + (CHAR_INDEXES_DECODING[(int) sourceAsString.charAt(i)] << 0x12);
        }
        if (sourceAsString.charAt(i + 1) != PADDING_CHAR) {
            blockAsInteger = blockAsInteger + (CHAR_INDEXES_DECODING[(int) sourceAsString.charAt(i + 1)] << 0x0C);
        }
        if (sourceAsString.charAt(i + 2) != PADDING_CHAR) {
            blockAsInteger = blockAsInteger + (CHAR_INDEXES_DECODING[(int) sourceAsString.charAt(i + 2)] << 0x06);
        }
        if (sourceAsString.charAt(i + 3) != PADDING_CHAR) {
            blockAsInteger = blockAsInteger + CHAR_INDEXES_DECODING[(int) sourceAsString.charAt(i + 3)];
        }
        return blockAsInteger;
    }

}

