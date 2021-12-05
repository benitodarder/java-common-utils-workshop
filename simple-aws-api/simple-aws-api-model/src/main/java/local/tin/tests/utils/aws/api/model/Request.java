package local.tin.tests.utils.aws.api.model;

import java.util.Objects;

/**
 *
 * @author developer01
 */
public class Request {
    
    private String userName;
    private String accessKeyId;
    private String secret;
    private String region;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.userName);
        hash = 79 * hash + Objects.hashCode(this.accessKeyId);
        hash = 79 * hash + Objects.hashCode(this.secret);
        hash = 79 * hash + Objects.hashCode(this.region);
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
        final Request other = (Request) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.accessKeyId, other.accessKeyId)) {
            return false;
        }
        if (!Objects.equals(this.secret, other.secret)) {
            return false;
        }
        return Objects.equals(this.region, other.region);
    }

    @Override
    public String toString() {
        return "Request{" + "userName=" + userName + ", accessKeyId=" + accessKeyId + ", secret=<sssshhhh>, region=" + region + '}';
    }

        

}
