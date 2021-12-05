package local.tin.tests.utils.aws.api.model;

/**
 *
 * @author developer01
 */
public class AWSAPIException extends Exception {

    public AWSAPIException(Exception e) {
        super(e);
    }

    public AWSAPIException(String string) {
        super(string);
    }

    public AWSAPIException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public AWSAPIException(Throwable thrwbl) {
        super(thrwbl);
    }


}
