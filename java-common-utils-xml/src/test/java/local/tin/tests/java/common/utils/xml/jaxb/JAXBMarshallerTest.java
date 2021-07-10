package local.tin.tests.java.common.utils.xml.jaxb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author benito.darder
 */
public class JAXBMarshallerTest {
    
    protected static final String DESCRIPTION = "Description";
    protected static final String NAME = "Name";    
    protected static final int ID = 666;    
    private static final String SAMPLE_XML_FILE = "samplePojo.xml";
    private SamplePOJO response;
    private String sampleXML;
    
    @Before
    public void setUp() throws IOException {
        sampleXML = getResourceFileAsString(JAXBMarshallerTest.class, SAMPLE_XML_FILE);
        response = new SamplePOJO();
        response.setMessage("message");
        response.setSuccess(true);
      
    }

    
    @Test
    public void toString_is_not_empty() throws Exception {

        String result = JAXBMarshaller.getInstance().toString(response);
        
        assertThat(result.isEmpty(), equalTo(false));
    }
    
    @Test
    public void toString_returns_expected_result() throws Exception  {
        
        String result = JAXBMarshaller.getInstance().toString(response);
        
        assertThat(result.contains("<message>message</message>"), equalTo(true));
        assertThat(result.contains("<success>true</success>"), equalTo(true));
    }
    
    private String getResourceFileAsString(Class packageClass, String fileName) throws IOException {
        String filePath = packageClass.getResource(fileName).getPath();
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String string = bufferedReader.readLine();
        while (string != null) {
            stringBuilder.append(string);
            string = bufferedReader.readLine();
        }
        return stringBuilder.toString();
    }    
    
    @Test
    public void toObject_returns_expected_result() throws Exception  {
        
        SamplePOJO result = (SamplePOJO) JAXBMarshaller.getInstance().toObject(sampleXML, SamplePOJO.class);
        
        assertThat(result.isSuccess(), equalTo(false));
        assertThat(result.getMessage(), equalTo("666"));
    }
    
    @Test
    public void toObject_with_string_returns_expected_result() throws Exception  {
        
        SamplePOJO result = (SamplePOJO) JAXBMarshaller.getInstance().toObject(sampleXML, SamplePOJO.class.getCanonicalName());
        
        assertThat(result.isSuccess(), equalTo(false));
        assertThat(result.getMessage(), equalTo("666"));
    }    
}

