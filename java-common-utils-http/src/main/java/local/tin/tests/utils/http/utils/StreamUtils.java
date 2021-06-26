package local.tin.tests.utils.http.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import local.tin.tests.utils.http.model.HttpCommonException;

/**
 *
 * @author benitodarder
 */
public class StreamUtils {

    private StreamUtils() {
    }

    public static StreamUtils getInstance() {
        return StreamsUtilsHolder.INSTANCE;
    }

    private static class StreamsUtilsHolder {
        private static final StreamUtils INSTANCE = new StreamUtils();
    }
    
    public GZIPInputStream getGZIPInputStream(InputStream inputStream) throws HttpCommonException {
        try {
            return new GZIPInputStream(inputStream);
        } catch (IOException ex) {
            throw new HttpCommonException(ex);
        }
    }
    
    public ByteArrayOutputStream getByteArrayOutputStream() {
        return new ByteArrayOutputStream();
    }
    
    public byte[] getBuffer(int size) {
        return new byte[size];
    }
 }
