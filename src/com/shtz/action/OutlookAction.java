package com.shtz.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.shtz.model.Module;
import com.shtz.model.User;
import com.shtz.service.AclService;

public class OutlookAction extends BaseAction {
	
	private AclService aservice;
	

	
	public void setAservice(AclService aservice) {
		this.aservice = aservice;
	}



	@Override
	public String execute() throws Exception {
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("login");
		System.out.println("=====OutlookAction======user:"+user);
		List<Module> list = aservice.searchModules(user.getId());
		
//		System.out.println("------OutlookAction--------list:" + list.size());
//		System.out.println("--------------list:" + list.toString());
		for(Module m : list) {
			System.out.println(m.getId()+"---"+m.getName()+"---"+m.getUrl());
		}
		
		ServletActionContext.getRequest().setAttribute("modules", list);
		
		return SUCCESS;
	}
	
}
