package com.shtz.action;

import org.apache.struts2.ServletActionContext;

import com.shtz.model.ACL;
import com.shtz.service.ModuleService;
import com.shtz.service.RoleService;
import com.shtz.service.UserService;
import com.shtz.util.PageModel;

@SuppressWarnings("serial")
public class AclAction extends BaseAction {
	
	private ModuleService moduleService;
	
	private UserService userService;
	
	private RoleService roleService;
	
	private String principalType;
		
	private int principalSn;
	private PageModel pageModel;

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	public String getPrincipalType() {
		return principalType;
	}

	public void setPrincipalType(String principalType) {
		this.principalType = principalType;
	}

	public int getPrincipalSn() {
		return principalSn;
	}

	public void setPrincipalSn(int principalSn) {
		this.principalSn = principalSn;
	}
	
	//��ACL��Ȩ����
	//���ܲ�����principalType��principalSn
	//���������ģ���б���ɫ���û�
	@Override
	public String execute() throws Exception {
		System.out.println("--------AclAction------------");
		
		//������������ǽ�ɫ���û�
		if(ACL.TYPE_ROLE.equals(principalType)) {
			ServletActionContext.getRequest().setAttribute("role", roleService.findRole(principalSn));
		} else if(ACL.TYPE_USER.equals(principalType)) {
			ServletActionContext.getRequest().setAttribute("user", userService.findUser(principalSn));
		} else {
			throw new RuntimeException("���Ϸ�����������");
		}
		
		//�����ʹ���ҳ���ж�
		ServletActionContext.getRequest().setAttribute("type", principalType);
		//�ѱ�Ŵ���ҳ��
		ServletActionContext.getRequest().setAttribute("sn", principalSn);
		//��ȡ���ж���ģ���б�
		//SystemContext.setOffset(0);
		//SystemContext.setPagesize(Integer.MAX_VALUE);
		pageModel = moduleService.findModules(0, 0, Integer.MAX_VALUE);
		
		ServletActionContext.getRequest().setAttribute("modules", pageModel.getList());
		
		return SUCCESS;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}


	
}
