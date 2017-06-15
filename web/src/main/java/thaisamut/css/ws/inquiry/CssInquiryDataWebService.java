package thaisamut.css.ws.inquiry;

import javax.jws.WebMethod;
import javax.jws.WebService;

import thaisamut.css.ws.address.model.PolicyBeanMapTransporter;

@WebService(name = "CssInquiryDataWebService")
public interface CssInquiryDataWebService
{
    @WebMethod
    public String getRiderMap(String riderCode, String policyType) throws Exception;
    
    @WebMethod
    public PolicyBeanMapTransporter getPolicies(String idNo, String email, String userName) throws Exception;
}
