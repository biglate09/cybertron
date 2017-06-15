package thaisamut.css.ws.user;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import thaisamut.commons.AutowireHelper;
import thaisamut.css.ldap.LDAPConnectionService;
import thaisamut.css.model.CssUser;
import thaisamut.css.sms.CssSMSConnectionService;
import thaisamut.css.ws.address.model.ResetPasswordTransport;
import thaisamut.css.ws.util.DataConverter;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;

import javax.annotation.PostConstruct;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.annotation.WebServlet;

import static javax.jws.soap.SOAPBinding.ParameterStyle.WRAPPED;
import static javax.jws.soap.SOAPBinding.Style.DOCUMENT;

@WebServlet(name = "CssUserWebService", urlPatterns = "/ws/CssUserWebService")
@WebService(serviceName = "CssUserWebService")
@SOAPBinding(style = DOCUMENT, parameterStyle = WRAPPED)
public class CssUserWebServiceImpl implements CssUserWebService
{
    @Autowired
    private CssMemberService cssMemberService;
    @Autowired
    private LDAPConnectionService ldapService;
    @Autowired
    private CssSMSConnectionService cssSMSConnectionService;

    @PostConstruct
    private void init(){
        AutowireHelper.autowire(this);
    }

    @Override
    public CssUser getUser(String userName){

        CssMemberEntity member = cssMemberService.findMemberByUserName(userName);
        return DataConverter.convertToCssUser((member));
    }

    @Override
    public ResetPasswordTransport changePassword(Parameter parameter) {

        String captchaTimeStamp = parameter.getCaptchaTimeStamp();
        String captchaCode = parameter.getCaptchaCode();
        String newPassword = parameter.getNewPassword();
        String confirmNewPassword = parameter.getConfirmNewPassword();
        String captchaCheck = parameter.getCaptchaCheck();
        String sessionId = parameter.getSessionId();
        String userName = parameter.getUserName();
        String password = parameter.getOldPassword();

        String resultsMsg = StringUtils.EMPTY;
        Boolean success = false;
        String captchaParam = captchaTimeStamp+"|"+captchaCode;
        Boolean validatePassword = ldapService.validatePassword(userName, password);

        if(validatePassword) {
            if (!newPassword.equalsIgnoreCase(confirmNewPassword)) {
                resultsMsg = "รหัสผ่านใหม่กับยืนยันรหัสผ่านไม่ตรงกัน";
            } else if (!StringUtils.equalsIgnoreCase(captchaCheck, captchaParam)) {
                resultsMsg = "กรอก Security Code ไม่ถูกต้อง";
            } else {

                // ทำการเปลี่ยนรหัสผ่าน
                success = ldapService.updatePassword(userName, newPassword);
                if (success) {
                    CssMemberEntity member = cssMemberService.findMemberByUserName(userName);

//                    String content = cssMemberService.getEmailContent(sessionId, CssEmailConnectionService.MAP_EMAIL_CHANGE_PASSWORD_METHOD, getBaseUrl(), params.username);
//                    // Send email success กรณีมีข้อมูลอีเมลในระบบ
//                    if (StringUtils.isNotBlank(member.getEmail())) {
//                        try {
//                            sendEmail(DEFAULT_EMAIL_SENDER_NAME, member.getEmail(), "ยืนยันการเปลี่ยนรหัสผ่าน (Password) บริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต", content);
//                        } catch (Exception ex) {
//                            LOG.error(String.format("ไม่สามารถส่งมีเมลยืนยันไปที่ %s", member.getEmail()), ex);
//                        }
//                    }

                    // Send sms success
                    cssSMSConnectionService.sendSmsSuccess(member.getTelNo(), CssSMSConnectionService.MAP_SMS_METHOD_CHANGE_PASSWORD);

                    resultsMsg = "เปลี่ยนรหัสผ่านเรียบร้อยแล้ว";
                } else {
                    resultsMsg = "เกิดข้อผิดพลาดกรุณาติดต่อผู้ดูแลระบบ";
                }
            }
        } else {
            resultsMsg = "บัญชีผู้ใช้งานหรือรหัสผ่านเดิมไม่ถูกต้อง";
        }

        return new ResetPasswordTransport(success,resultsMsg);

    }
}
