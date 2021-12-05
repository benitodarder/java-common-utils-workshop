package local.tin.tests.utils.aws.api.model.s3;

import local.tin.tests.utils.aws.api.model.AWSAPIException;



/**
 *
 * @author developer01
 */
public class S3Exception extends AWSAPIException {

    public S3Exception(Exception e) {
        super(e);
    }

    public S3Exception(String string) {
        super(string);
    }

    public S3Exception(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public S3Exception(Throwable thrwbl) {
        super(thrwbl);
    }

}
