package local.tin.tests.utils.http.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author benitodarder
 */
public class MultipartRequest extends AbstractHttpRequest {

    private List<MultipartItem> multipartItems;
    private String boundary;
    
    public List<MultipartItem> getMultipartItems() {
        if (multipartItems == null) {
            multipartItems = new ArrayList<>();
        }
        return multipartItems;
    }

    public void setMultipartItems(List<MultipartItem> multipartItems) {
        this.multipartItems = multipartItems;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.multipartItems);
        hash = 89 * hash + Objects.hashCode(this.boundary);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MultipartRequest other = (MultipartRequest) obj;
        if (!Objects.equals(this.boundary, other.boundary)) {
            return false;
        }
        if (!Objects.equals(this.multipartItems, other.multipartItems)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "MultipartRequest{" + "multipartItems=" + multipartItems + ", boundary=" + boundary + ", AbstractHttpRequest: " + super.toString() + '}';
    }

 

    
}
