package local.tin.tests.utils.aws.api.model.iam;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author developer01
 */
public class AccessKey {
    
    private String userName;
    private String accessKeyId;
    private String secret;
    private String status;
    private Date creationDate;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.userName);
        hash = 53 * hash + Objects.hashCode(this.accessKeyId);
        hash = 53 * hash + Objects.hashCode(this.secret);
        hash = 53 * hash + Objects.hashCode(this.status);
        hash = 53 * hash + Objects.hashCode(this.creationDate);
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
        final AccessKey other = (AccessKey) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.accessKeyId, other.accessKeyId)) {
            return false;
        }
        if (!Objects.equals(this.secret, other.secret)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return Objects.equals(this.creationDate, other.creationDate);
    }

    @Override
    public String toString() {
        return "AccessKey{" + "userName=" + userName + ", accessKeyId=" + accessKeyId + ", secret=<sssshhhh>, status=" + status + ", creationDate=" + creationDate + '}';
    }

    
    
}
