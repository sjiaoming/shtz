package com.shtz.util;

import com.shtz.service.AclService;

//JSTL���������Ȩ�޵ļ�ʱ��֤
public class SecurityFunction {
	
	private static AclService aclService;
	/**
	 * 
	 * @param userId
	 * @param sn
	 * @param permission 0--����   1--��ȡ   2--�޸�   3--ɾ��
	 * @return
	 */
	public static boolean method(int userId,String sn,int permission) {
		
		return aclService.hasPermissionByResourceSn(userId, sn, permission);
	}

	public void setAclService(AclService aclService) {
		SecurityFunction.aclService = aclService;
	}
}
