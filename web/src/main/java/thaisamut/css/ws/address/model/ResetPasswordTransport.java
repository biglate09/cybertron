package thaisamut.css.ws.address.model;

import java.io.Serializable;

public class ResetPasswordTransport implements Serializable
{

    private Boolean success;
    private String message;

    public ResetPasswordTransport() {
    }

    public ResetPasswordTransport(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
