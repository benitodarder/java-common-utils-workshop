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
    
    @Test
    public void can_decode_encoded_string() throws Exception {
        
        String result = Base64.getInstance().decode(Base64.getInstance().encode(SAMPLE_STRING_01));
        
        assertThat(result, equalTo(SAMPLE_STRING_01));
    }
    
}
