package local.tin.tests.java.common.utils.encoding;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 *
 * @author benitodarder
 */
public class Base64Test {
    
    private static final String SAMPLE_STRING_01 = "abcdef";
    private static final byte[] SAMPLE_BYTE_ARRAY_01 = {0,65,13,66,97,63};
    
    @Test
    public void can_decode_encoded_string() throws Exception {
        
        String result = Base64.getInstance().decode(Base64.getInstance().encode(SAMPLE_STRING_01));
        
        assertThat(result, equalTo(SAMPLE_STRING_01));
    }


    @Test
    public void can_decode_encoded_byte_array() throws Exception {
        
        String result = Base64.getInstance().decode(Base64.getInstance().encode(new String(SAMPLE_BYTE_ARRAY_01)));
        
        assertThat(result.getBytes(), equalTo(SAMPLE_BYTE_ARRAY_01));
    }    
}
