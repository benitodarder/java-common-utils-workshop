package local.tin.tests.utils.aws.api.model.s3;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class S3Response {

    private byte[] file;
    private List<String> list;
    private Map<String, String> tags;
    private Date timestamp;
    private boolean success;
    private String message;
    
    public Map<String, String> getTags() {
        if (tags == null) {
            tags = new HashMap<>();
        }
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Arrays.hashCode(this.file);
        hash = 83 * hash + Objects.hashCode(this.list);
        hash = 83 * hash + Objects.hashCode(this.tags);
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
        final S3Response other = (S3Response) obj;
        if (!Arrays.equals(this.file, other.file)) {
            return false;
        }
        if (!Objects.equals(this.list, other.list)) {
            return false;
        }
        return Objects.equals(this.tags, other.tags);
    }

    @Override
    public String toString() {
        return "StorageResponse{" + "file=" + file + ", list=" + list + ", tags=" + tags + '}';
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
