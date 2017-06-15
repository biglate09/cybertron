package thaisamut.css.ws.address;

import thaisamut.css.dwh.service.pojo.ProvinceBean;
import thaisamut.css.model.CssUser;
import thaisamut.css.ws.address.model.ConfirmUpdateTransporter;
import thaisamut.css.ws.address.model.RequestUpdateTransporter;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "CssAddressWebService")
public interface CssAddressWebService
{
    @WebMethod
    public CssAddressTempEntity queryCssAddressTempEntityWaitingStatus(String policyNo, String username) throws Exception;
    @WebMethod
    public RequestUpdateTransporter prepareAddressForUpdate(final String username, Parameter param) throws Exception;
    @WebMethod
    public ConfirmUpdateTransporter confirmOTP(final String username,
                                               String otpRef,
                                               String otpCode,
                                               List<CssAddressTempEntity> addressUpdated) throws Exception;
    @WebMethod
    List<ProvinceBean> getActiveProvince() throws Exception;

    public class Parameter {
        private String policyNo;
        private String addressLine1;
        private String province;
        private String povDesc;
        private String disDesc;
        private String policyType;
        private String district;
        private String postcode;
        private String subdistrictName;
        private String mobile1;
        private String mobile2;
        private String phone1;
        private String ext1;
        private String phone2;
        private String ext2;
        private String email;
        private String[] followPolicy;
        public String getPolicyNo() {
            return policyNo;
        }
        public void setPolicyNo(String policyNo) {
            this.policyNo = policyNo;
        }
        public String getAddressLine1() {
            return addressLine1;
        }
        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }
        public String getProvince() {
            return province;
        }
        public void setProvince(String province) {
            this.province = province;
        }
        public String getDistrict() {
            return district;
        }
        public void setDistrict(String district) {
            this.district = district;
        }

        public String getPostcode() {
            return postcode;
        }
        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }
        public String getMobile1() {
            return mobile1;
        }
        public void setMobile1(String mobile1) {
            this.mobile1 = mobile1;
        }
        public String getMobile2() {
            return mobile2;
        }
        public void setMobile2(String mobile2) {
            this.mobile2 = mobile2;
        }
        public String getPhone1() {
            return phone1;
        }
        public void setPhone1(String phone1) {
            this.phone1 = phone1;
        }
        public String getExt1() {
            return ext1;
        }
        public void setExt1(String ext1) {
            this.ext1 = ext1;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String[] getFollowPolicy() {
            return followPolicy;
        }
        public void setFollowPolicy(String[] followPolicy) {
            this.followPolicy = followPolicy;
        }
        public String getSubdistrictName() {
            return subdistrictName;
        }
        public void setSubdistrictName(String subdistrictName) {
            this.subdistrictName = subdistrictName;
        }
        public String getPolicyType() {
            return policyType;
        }
        public void setPolicyType(String policyType) {
            this.policyType = policyType;
        }
        public String getPovDesc() {
            return povDesc;
        }
        public void setPovDesc(String povDesc) {
            this.povDesc = povDesc;
        }
        public String getDisDesc() {
            return disDesc;
        }
        public void setDisDesc(String disDesc) {
            this.disDesc = disDesc;
        }
        public String getPhone2() {
            return phone2;
        }
        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }
        public String getExt2() {
            return ext2;
        }
        public void setExt2(String ext2) {
            this.ext2 = ext2;
        }
    }
}
