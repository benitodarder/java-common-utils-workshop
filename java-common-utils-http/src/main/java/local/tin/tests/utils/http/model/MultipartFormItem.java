package local.tin.tests.utils.http.model;

public class MultipartFormItem {

    private String charset;
    private String contentType;
    private String contentDisposition;
    private String formField;
    private Object formValue;
    private String fileName;

    public MultipartFormItem(String charset, String contentType, String contentDisposition, String formField, Object formValue, String fileName) {
        this.charset = charset;
        this.contentType = contentType;
        this.contentDisposition = contentDisposition;
        this.formField = formField;
        this.formValue = formValue;
        this.fileName = fileName;
    }

    public MultipartFormItem(String charset, String contentType, String contentDisposition, String formField, Object formValue) {
        this.charset = charset;
        this.contentType = contentType;
        this.contentDisposition = contentDisposition;
        this.formField = formField;
        this.formValue = formValue;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public String getFormField() {
        return formField;
    }

    public void setFormField(String formField) {
        this.formField = formField;
    }

    public Object getFormValue() {
        return formValue;
    }

    public void setFormValue(Object formValue) {
        this.formValue = formValue;
    }

}
