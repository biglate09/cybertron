package thaisamut.css.ws.address.model;

import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestUpdateTransporter implements Serializable
{
    private List<CssAddressTempEntity> prepareupdate = new ArrayList<>();
    private String policyNo;
    private String telNo;
    private String otpRef;
    private String otpToken;
    private String otp;

    public List<CssAddressTempEntity> getPrepareupdate()
    {
        return prepareupdate;
    }

    public void setPrepareupdate(List<CssAddressTempEntity> prepareupdate)
    {
        this.prepareupdate = prepareupdate;
    }

    public String getPolicyNo()
    {
        return policyNo;
    }

    public void setPolicyNo(String policyNo)
    {
        this.policyNo = policyNo;
    }

    public String getTelNo()
    {
        return telNo;
    }

    public void setTelNo(String telNo)
    {
        this.telNo = telNo;
    }

    public String getOtpRef()
    {
        return otpRef;
    }

    public void setOtpRef(String otpRef)
    {
        this.otpRef = otpRef;
    }

    public String getOtpToken()
    {
        return otpToken;
    }

    public void setOtpToken(String otpToken)
    {
        this.otpToken = otpToken;
    }

    public String getOtp()
    {
        return otp;
    }

    public void setOtp(String otp)
    {
        this.otp = otp;
    }
}
