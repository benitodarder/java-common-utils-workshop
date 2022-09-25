package local.tin.tests.utils.http.model;

import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author benitodarder
 */
public class MultipartHttpRequest extends AbstractHttpRequest {
    
    private Collection<MultipartItem> multipartItems;

    public Collection<MultipartItem> getMultipartItems() {
        return multipartItems;
    }

    public void setMultipartItems(Collection<MultipartItem> multipartItems) {
        this.multipartItems = multipartItems;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.multipartItems);
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
        final MultipartHttpRequest other = (MultipartHttpRequest) obj;
        if (!Objects.equals(this.multipartItems, other.multipartItems)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "MultipartHttpRequest{" + "multipartItems=" + multipartItems + ", " + super.toString() + '}';
    }

 

    
}