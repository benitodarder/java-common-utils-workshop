package local.tin.tests.java.common.utils.xml.jaxb;

/**
 *
 * @author benitodarder
 */
public class SamplePOJO {
    
    private boolean success;
    private String message;
    
    public SamplePOJO() {
    }
    
    public SamplePOJO(boolean success, String message) {
        this.success = success;
        this.message = message;
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
