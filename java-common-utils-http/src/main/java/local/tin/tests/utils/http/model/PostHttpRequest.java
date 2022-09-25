package local.tin.tests.utils.http.model;

import java.util.Objects;

/**
 *
 * @author benitodarder
 */
public class PostHttpRequest extends AbstractHttpRequest {
    
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.body);
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
        final PostHttpRequest other = (PostHttpRequest) obj;
        if (!Objects.equals(this.body, other.body)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "PostHttpRequest{" + "body=" + body + ", " + super.toString() + '}';
    }
    

    
}