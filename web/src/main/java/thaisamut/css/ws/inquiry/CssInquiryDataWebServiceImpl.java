package thaisamut.css.ws.inquiry;

import static javax.jws.soap.SOAPBinding.ParameterStyle.WRAPPED;
import static javax.jws.soap.SOAPBinding.Style.DOCUMENT;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.commons.AutowireHelper;
import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.data.service.CssPolicyDataService;
import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.ws.address.model.PolicyBeanMapTransporter;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;

@WebServlet(name = "CssInquiryDataWebService", urlPatterns = "/ws/CssInquiryDataWebService")
@WebService(serviceName = "CssInquiryDataWebService")
@SOAPBinding(style = DOCUMENT, parameterStyle = WRAPPED)
public class CssInquiryDataWebServiceImpl implements CssInquiryDataWebService
{
    @Autowired
    private CssMasterDataService cssMasterDataService;
    
    @Autowired
	private CssPolicyDataService cssPolicyDataService;
    
    @Autowired
	DataWarehouseService dwhService;

    @PostConstruct
    private void init(){
        AutowireHelper.autowire(this);
    }

    @Override
    public String getRiderMap(String riderCode, String policyType) throws Exception
    {
        return cssMasterDataService.getRiderMap(riderCode, policyType);
    }

	@Override
	public PolicyBeanMapTransporter getPolicies(String idNo, String email, String userName) throws Exception {
		PolicyBeanMapTransporter returnBean = new PolicyBeanMapTransporter();
		Map<String, PolicyBean> policies = dwhService.getPolicyData(idNo, email);
		for (Entry<String, PolicyBean> entry : policies.entrySet()) {
			CssAddressTempEntity tmp = cssPolicyDataService.queryCssAddressTempEntityWaitingStatus(entry.getKey(), userName);
			if (tmp != null) {
				setTempAddres(entry.getValue(), tmp);
			}
		}
		returnBean.setPolicies(policies);
		return returnBean;
	}
	
	private void setTempAddres(PolicyBean value, CssAddressTempEntity tmp) {
		if (value != null && tmp != null) {
			value.setAddressLine1(tmp.getAddr());
			value.setSubdistrictName(tmp.getSdtDesc());
			value.setDistrictName(tmp.getDtDesc());
			value.setDtCode(tmp.getDtCode());
			value.setProvinceName(tmp.getPvDesc());
			value.setPvCode(tmp.getPvCode());
			value.setPostcode(tmp.getZipCode());
			value.setPhone1(tmp.getPhone1());
			value.setExt1(tmp.getExt1());
			value.setPhone2(tmp.getPhone2());
			value.setExt2(tmp.getExt2());
			value.setMobile1(tmp.getMobile1());
			value.setMobile2(tmp.getMobile2());
			value.setEmail(tmp.getEmail());
			value.setTemp(true);
		}
	}

}
