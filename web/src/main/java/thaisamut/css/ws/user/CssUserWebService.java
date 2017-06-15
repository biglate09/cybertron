package thaisamut.css.ws.user;

import thaisamut.css.model.CssUser;
import thaisamut.css.ws.address.model.ResetPasswordTransport;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "CssUserWebService")
public interface CssUserWebService
{
    @WebMethod
    ResetPasswordTransport changePassword(Parameter parameter);
    @WebMethod
    public CssUser getUser(String userName);

    public class Parameter {
        private String captchaCheck;
        private String captchaTimeStamp;
        private String captchaCode;
        private String confirmNewPassword;
        private String newPassword;
        private String userName;
        private String oldPassword;
        private String sessionId;

        public String getCaptchaCheck() {
            return captchaCheck;
        }

        public void setCaptchaCheck(String captchaCheck) {
            this.captchaCheck = captchaCheck;
        }

        public String getCaptchaTimeStamp() {
            return captchaTimeStamp;
        }

        public void setCaptchaTimeStamp(String captchaTimeStamp) {
            this.captchaTimeStamp = captchaTimeStamp;
        }

        public String getCaptchaCode() {
            return captchaCode;
        }

        public void setCaptchaCode(String captchaCode) {
            this.captchaCode = captchaCode;
        }

        public String getConfirmNewPassword() {
            return confirmNewPassword;
        }

        public void setConfirmNewPassword(String confirmNewPassword) {
            this.confirmNewPassword = confirmNewPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }

}
