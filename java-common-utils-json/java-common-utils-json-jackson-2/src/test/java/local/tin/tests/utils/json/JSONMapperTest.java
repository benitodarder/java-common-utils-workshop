package local.tin.tests.utils.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author benitodarder
 */
public class JSONMapperTest {

    private Response response;
    private String sampleJSON;

    @Before
    public void setUp() throws IOException {
        sampleJSON = getResourceFileAsString(getClass(), "sampleJSON.json");
        response = new Response();
        response.setMessage("message");
        response.setSuccess(true);

    }

    @Test
    public void getJSONFromObject_returns_expected_string() throws IOException {

        String result = JSONMapper.getInstance().getJSONFromObject(response);

        assertThat(result.contains("\"message\":\"message\""), equalTo(true));
        assertThat(result.contains("\"success\":true"), equalTo(true));
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
    public void getObjectFromJSON_returns_expected_object() throws IOException {

        Response result = JSONMapper.getInstance().getObjectFromJSON(sampleJSON, Response.class);

        assertThat(result, equalTo(response));
    }
    
    @Test
    public void getObjectsFromJSON_returns_expected_list() throws IOException {
        List<Response> inputList = new ArrayList<>();
        inputList.add(response);
        inputList.add(response);
        String input = JSONMapper.getInstance().getJSONFromObject(inputList);
        
        List<Response> result = JSONMapper.getInstance().getObjectsFromJSON(input, Response.class);

        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo(response));
        assertThat(result.get(1), equalTo(response));
    }    
}
