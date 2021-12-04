# java-common-utils-use-cases

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