package thaisamut.css.ws.address;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import thaisamut.commons.AutowireHelper;
import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.data.service.CssPolicyDataService;
import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.dwh.service.pojo.ProvinceBean;
import thaisamut.css.email.CssEmailConnectionService;
import thaisamut.css.model.CssUser;
import thaisamut.css.otp.OTPHandler;
import thaisamut.css.sms.CssSMSConnectionService;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.css.ws.address.model.ConfirmUpdateTransporter;
import thaisamut.css.ws.address.model.RequestUpdateTransporter;
import thaisamut.css.ws.util.DataConverter;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.css.person.action.PersonalInfoAction;
import thaisamut.util.CssConvertUtils;

import javax.annotation.PostConstruct;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.Map;

import static javax.jws.soap.SOAPBinding.ParameterStyle.WRAPPED;
import static javax.jws.soap.SOAPBinding.Style.DOCUMENT;

@WebServlet(name = "CssAddressWebService", urlPatterns = "/ws/CssAddressWebService")
@WebService(serviceName = "CssAddressWebService")
@SOAPBinding(style = DOCUMENT, parameterStyle = WRAPPED)
public class CssAddressWebServiceImpl implements CssAddressWebService
{
    private static final Logger LOG = LoggerFactory.getLogger(CssAddressWebServiceImpl.class);

    @Autowired
    DataWarehouseService dwhService;
    @Autowired
    CssMasterDataService cssMasterDataService;
    @Autowired
    CssPolicyDataService cssPolicyDataService;
    @Autowired
    CssSMSConnectionService cssSMSConnectionService;
    @Autowired
    CssMemberService cssMemberService;
    @Autowired
    CssEmailConnectionService cssEmailConnectionService;

    @Autowired
    OTPHandler otpHandler;

    private final String OTP_TTL = "15";

    @PostConstruct
    private void init(){
        AutowireHelper.autowire(this);
    }

    @Override
    public CssAddressTempEntity queryCssAddressTempEntityWaitingStatus(String policyNo, String username) throws Exception
    {
        return cssPolicyDataService.queryCssAddressTempEntityWaitingStatus(policyNo, username);
    }

    @Override
    public RequestUpdateTransporter prepareAddressForUpdate(final String username, Parameter param) throws Exception
    {
        if (LOG.isDebugEnabled()) {
            LOG.debug("~On CssAddressWebServiceImpl : prepareAddressForUpdate");
        }

        CssMemberEntity member = cssMemberService.findMemberByUserName(username);
        CssUser user =  DataConverter.convertToCssUser((member));

        RequestUpdateTransporter results = new RequestUpdateTransporter();
        try {
            OTPHandler.OTP o = otpHandler.generate(String.format("%sm", OTP_TTL), username);

            String message = checkIntrigity(username, param);

            if (StringUtils.isNotBlank(message)) {
                throw new Exception(message);
            } else {
                PersonalInfoAction.Parameter cssParam = new PersonalInfoAction().getParam();
                BeanUtils.copyProperties(cssParam, param);
                List<CssAddressTempEntity> prepareupdate = cssPolicyDataService.prepareAddress(cssParam, user, o.getToken());
                results.setPrepareupdate(prepareupdate);
                results.setPolicyNo(param.getPolicyNo());
                results.setTelNo(user.getTelNo());
                results.setOtpRef(o.getRefNo());
                results.setOtpToken(o.getToken());
                results.setOtp(o.getOtp());

                try {
                    cssSMSConnectionService.sendSmsOtpSuccess(user, o.getRefNo(), o.getOtp(), user.getTelNo(), CssSMSConnectionService.MAP_SMS_METHOD_CHANGE_ADDRESS, o.getExpiredDate(), true);
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                    throw new Exception("ไม่สามารถขอรหัส OTP ได้ เนื่องจากเกินจำนวนครั้งที่บริษัทกำหนด");
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug(String.format("confirm OTP : %s = %s", o.getRefNo(), o.getOtp()));
                }
            }
        }
        catch (Exception ex)
        {
            LOG.error(ex.getMessage(), ex);
            throw ex;
        }

        return results;
    }

    @Override
    public ConfirmUpdateTransporter confirmOTP(final String username,
                                               String otpRef,
                                               String otpCode,
                                               List<CssAddressTempEntity> addressUpdated) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("~On CssAddressWebServiceImpl : confirmotp");
        }

        CssMemberEntity member = cssMemberService.findMemberByUserName(username);
        CssUser user =  DataConverter.convertToCssUser((member));

        ConfirmUpdateTransporter results = new ConfirmUpdateTransporter();

        String telNo = user.getTelNo();
        String email = user.getEmail();

        OTPHandler.OTP o = otpHandler.validOTP(otpRef, otpCode);
        if (o != null && o.isOwner(user.getUsername()))
        {
            if (o.isNotExpired())
            {
                results.setStatus("SUCCESS");
                if (addressUpdated != null && !addressUpdated.isEmpty())
                {
                    addressUpdated = cssPolicyDataService.saveAddress(addressUpdated, user);

                    if (addressUpdated != null && !addressUpdated.isEmpty()) {
                        CssAddressTempEntity updateMember=addressUpdated.get(0);

                        for (CssAddressTempEntity tmpUpdate : addressUpdated)
                        {
                            email = StringUtils.defaultString(tmpUpdate.getEmail(), email);
                            telNo = StringUtils.defaultString(tmpUpdate.getMobile1(), telNo);
                        }

                        updateMember(user, updateMember);

                        if (StringUtils.isNotBlank(email))
                        {
                            try {
                                Map<String, PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(), user.getEmail());
                                cssEmailConnectionService.sendEmail(CssJsonAction.DEFAULT_EMAIL_SENDER_NAME, email,
                                        "แจ้งการแก้ไขข้อมูลการติดต่อของกรมธรรม์ในบริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต",
                                        PersonalInfoAction.createEmailBody(user, addressUpdated, policies));
                            } catch (Exception ex) {
                                LOG.error(String.format("ไม่สามารถส่งมีเมลยืนยันไปที่ %s", email), ex);
                            }
                        }
//								if (!LOG.isDebugEnabled()) {
                        if(StringUtils.isNotBlank(telNo))
                        {
                            String SUCCESS_MESSAGE = "OceanLife iService ได้รับคำร้องการแก้ไขข้อมูลของท่านแล้วตามที่แสดงในหน้า Website โดยข้อมูลใหม่จะมีผลในอีก 3 วันทำการ";
                            try {
                                cssSMSConnectionService.sendSmsSuccess(telNo, SUCCESS_MESSAGE);
                                results.setMessage(SUCCESS_MESSAGE);
                            } catch (Exception e) {
                                LOG.error(e.getMessage());
                            }
                        }

                    }
                } else {
                    results.setStatus("WARNING");
                    results.setMessage("ไม่พบข้อมูลที่ต้องการอัพเดท");
                }

            } else {
                results.setStatus("WARNING");
                results.setMessage("รหัส OTP ของท่านหมดอายุ กรุณาทำรายการใหม่");
            }
        } else {
            results.setStatus("WARNING");
            results.setMessage("รหัส OTP ไม่ถูกต้อง กรุณาตรวจสอบใหม่");
        }

        return results;
    }

    @Override
    public List<ProvinceBean> getActiveProvince() throws Exception
    {
        return cssMasterDataService.getActiveProvince();
    }

    private void updateMember(CssUser user, CssAddressTempEntity updateMember) throws Exception {
        //REGION update mobile1 and email for cssMember
        CssMemberEntity member = cssMemberService.findMemberByUserName(user.getUsername());
        boolean needUpdate = false;
        if(member!=null&& updateMember!=null){
            if(StringUtils.isNotBlank(updateMember.getMobile1())&&!updateMember.getMobile1().equals(member.getTelNo())){
                member.setTelNo(updateMember.getMobile1());
                user.setTelNo(updateMember.getMobile1());
                needUpdate = true;
            }
            String updateEmail = StringUtils.defaultString(updateMember.getEmail());
            String memberEmail = StringUtils.defaultString(member.getEmail());
            if(!updateEmail.equals(memberEmail)){
                member.setEmail(updateMember.getEmail());
                user.setEmail(updateMember.getEmail());
                needUpdate = true;
            }
            if(needUpdate){
                cssMemberService.updateCssMember(member, user.getUsername());
            }
        }
        //REGION end;
    }
    private String checkIntrigity(String username, Parameter param) {
        StringBuilder sb = new StringBuilder(1000);

        try{
            if(StringUtils.isBlank(param.getAddressLine1())){
                sb.append("กรอกที่อยู่ <br/>");
            }
            if(StringUtils.isBlank(param.getProvince())){
                sb.append("กรอกจังหวัด <br/>");
            }
            if(StringUtils.isBlank(param.getDistrict())){
                sb.append("กรอกอำเภอ <br/>");
            }
            if(StringUtils.isBlank(param.getSubdistrictName())){
                sb.append("กรอกตำบล <br/>");
            }
            if(StringUtils.isBlank(param.getPostcode())){
                sb.append("กรอกรหัสไปรษณี <br/>");
            }
            if(StringUtils.isBlank(param.getMobile1())){
                sb.append("กรอกโทรศัพท์มือถือ <br/>");
            }else if(!CssConvertUtils.isValideMobileNumber(param.getMobile1())){
                sb.append("รูปแบบเบอร์โทรศัพท์มือถือ1ไม่ถูกต้อง <br/>");
            }
            if(StringUtils.isNotBlank(param.getMobile2())&&!CssConvertUtils.isValideMobileNumber(param.getMobile2())){
                sb.append("รูปแบบเบอร์โทรศัพท์มือถือ2ไม่ถูกต้อง <br/>");
            }
            if(StringUtils.isNotBlank(param.getPhone1())&&!CssConvertUtils.isValidePhoneNumber(param.getPhone1())){
                sb.append("รูปแบบเบอร์โทรศัพท์บ้านไม่ถูกต้อง <br/>");
            }
            if(StringUtils.isNotBlank(param.getPhone2())&&!CssConvertUtils.isValidePhoneNumber(param.getPhone2())){
                sb.append("รูปแบบเบอร์โทรศัพท์ที่ทำงานไม่ถูกต้อง <br/>");
            }
            //duplicate email
            if(StringUtils.isNotEmpty(param.getEmail()) && cssMasterDataService.isExistEmail(param.getEmail(), username)){
                sb.append("อีเมล ").append(param.getEmail()).append(" ถูกใช้งานไปแล้วโดยบุคคลอื่น <br/>");
            }
            if(cssMasterDataService.isExistMobile1(param.getMobile1(), username)){
                sb.append("โทรศัพท์มือถือ1 ").append(param.getMobile1()).append(" ถูกใช้งานไปแล้วโดยบุคคลอื่น <br/>");
            }
            if(param.getMobile1()!=null&&param.getMobile1().equals(param.getMobile2())){
                sb.append("โทรศัพท์มือถือ1 และ โทรศัพท์มือถือ2 ซ้ำกัน");
            }


        }catch(Exception ex){
            sb.append(ex.getMessage());
        }
        return sb.toString();
    }
}
