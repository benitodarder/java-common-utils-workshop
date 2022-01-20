# java-common-utils-use-cases

* Alternate logging properties configuration: -Djava.util.logging.config.file=<File location>
* HttpClient:
    - java -cp java-common-utils-use-cases-1.0-jar-with-dependencies.jar local.tin.tests.java.common.utils.use.cases.HttpClient \<Properties file\>
        * method=\<POST/GET\>
        * url=\<URL\>
        * body=\<Single line\>
        * tls12=\<true/false\>
        * headers=\<Comma separated pairs of header name and value\>
* EncryptFile:
    - java -cp target/java-common-utils-use-cases-1.0-jar-with-dependencies.jar local.tin.tests.java.common.utils.use.cases.EncryptFile \<secret file\> \<Input file\> \<Output file\>
      * Secret used, must be 16/32/64 bytes
      * Input file
      * Output encrypted file
* DecryptFile:
    - java -cp target/java-common-utils-use-cases-1.0-jar-with-dependencies.jar local.tin.tests.java.common.utils.use.cases.DecryptFile \<secret file\> \<Compressed input file\> \<Output file\>
      * Secret used, must be 16/32/64 bytes
      * Compressed input file
      * Output encrypted file
* ListAccessKeys:
    - java -cp target/java-common-utils-use-cases-1.0-jar-with-dependencies.jar local.tin.tests.java.common.utils.use.cases.aws.iam.ListAccessKeys \<Properties file\>
      * Access.key
      * Secret.key
      * Region 
* GetObject:
    - java -cp target/java-common-utils-use-cases-1.0-jar-with-dependencies.jar local.tin.tests.java.common.utils.use.cases.aws.s3.GetObject \<Properties file\>
      * Access key
      * Secret key
      * Region
      * Bucket name
      * Object key
      * File path
* PutObject:
    - java -cp target/java-common-utils-use-cases-1.0-jar-with-dependencies.jar local.tin.tests.java.common.utils.use.cases.aws.s3.PutObject \<Properties file\>
      * Access key
      * Secret key
      * Region
      * Bucket name
      * Object key
      * File path