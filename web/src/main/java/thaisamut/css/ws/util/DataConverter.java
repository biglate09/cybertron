package thaisamut.css.ws.util;

import thaisamut.css.model.CssUser;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;

/**
 * Created by wirapong.ch on 5/19/2017 AD.
 */
public class DataConverter
{
    public static CssUser convertToCssUser(CssMemberEntity member){
        CssUser user = null;

        if (member != null) {
            user = new CssUser();
            user.setUsername(member.getUsername());
            user.setFullname(member.getFullname());
            user.setCardNo(member.getCardNo());
            user.setBirthDate(member.getBirthDate());
            user.setTelNo(member.getTelNo());
            user.setEmail(member.getEmail());
            user.setStatus(member.getStatus());
            user.setCustCode(member.getCustCode());
            user.setTitleDesc(member.getTitleDesc());
            user.setFirstName(member.getFirstName());
            user.setLastName(member.getLastName());
        }

        return user;
    }
}
