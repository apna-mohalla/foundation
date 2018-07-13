package apna.Maholla.exception;

public class UnAuthorizedAccess  implements ResourceFoundNotFound {
    private String resourceName;
    private String fieldValue;
    private String status;
    private String message;

    public UnAuthorizedAccess(String fieldName) {
        this.resourceName = "User";
        this.fieldValue = fieldName;
        this.status = "Unauthorized";
        this.message = "User does not have access to perform this action.";
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
