package local.tin.tests.utils.aws.api.model.s3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import local.tin.tests.utils.aws.api.model.Request;

public class S3Request extends Request {

    private String bucketName;
    private long timestamp;
    private String key;
    private byte[] file;
    private Map<String, String> tags;

    public Map<String, String> getTags() {
        if (tags == null) {
            tags = new HashMap<>();
        }
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.bucketName);
        hash = 37 * hash + (int) (this.timestamp ^ (this.timestamp >>> 32));
        hash = 37 * hash + Objects.hashCode(this.key);
        hash = 37 * hash + Arrays.hashCode(this.file);
        hash = 37 * hash + Objects.hashCode(this.tags);
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
        final S3Request other = (S3Request) obj;
        if (this.timestamp != other.timestamp) {
            return false;
        }
        if (!Objects.equals(this.bucketName, other.bucketName)) {
            return false;
        }
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        if (!Arrays.equals(this.file, other.file)) {
            return false;
        }
        return Objects.equals(this.tags, other.tags);
    }

    @Override
    public String toString() {
        return "S3Request{" + "bucketName=" + bucketName + ", timestamp=" + timestamp + ", key=" + key + ", file=" + file + ", tags=" + tags + '}';
    }

}
