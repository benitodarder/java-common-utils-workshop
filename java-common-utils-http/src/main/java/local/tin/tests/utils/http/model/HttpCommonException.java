package local.tin.tests.utils.http.model;


/**
 *
 * @author tubdapmi
 */
public class HttpCommonException extends  Exception {
    
    public HttpCommonException(Throwable cause) {
        super(cause);
    }      

    public HttpCommonException(String msg, Throwable cause) {
        super(msg, cause);
    }    

    public HttpCommonException(String msg) {
        super(msg);
    }     
}
