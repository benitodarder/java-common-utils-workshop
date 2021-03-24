package local.tin.tests.utils.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 *
 * @author benitodarder
 */
public class JSONMapper {

    private static ObjectMapper mapper;

    private JSONMapper() {
    }

    public synchronized static JSONMapper getInstance() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return JSONConverterHolder.INSTANCE;
    }

    private static class JSONConverterHolder {

        private static final JSONMapper INSTANCE = new JSONMapper();
    }

    public <C> C getObjectFromJSON(String json, Class<C> klass) throws IOException {
        return mapper.readValue(json, klass);
    }

    public <C> List<C> getObjectsFromJSON(String json, Class<C> klass) throws IOException {
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        return mapper.readValue(json, typeFactory.constructCollectionType(ArrayList.class, klass));
    }

    public String getJSONFromObject(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }

}
