package thaisamut.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import thaisamut.css.dwh.service.pojo.PolicyBean;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class CssSortPolicy implements TemplateMethodModelEx {

	@Override
	public Object exec(List arg0) throws TemplateModelException {
		freemarker.core.CollectionAndSequence list = (freemarker.core.CollectionAndSequence) arg0.get(0);
		List<PolicyBean> pList = new ArrayList<PolicyBean>();
		for(int i = 0 ; i<list.size();i++){
			if(list.get(i)!=null && list.get(i) instanceof StringModel && ((StringModel) list.get(i)).getWrappedObject() instanceof PolicyBean){
				pList.add((PolicyBean) ((StringModel) list.get(i)).getWrappedObject());
			}
		}
		Collections.sort(pList);
		return pList;
	}

}
