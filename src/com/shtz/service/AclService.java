package com.shtz.service;

import java.util.List;

import com.shtz.model.Module;

/**
 * @author sjm
 *  
 */
public interface AclService {
	/**
	 * ��Ȩ
	 * @param principalType ��������
	 * @param principalSn ������
	 * @param resourceSn ��Դ���
	 * @param permission CRUD
	 * @param yes �Ƿ�����
	 */
	public void addOrModifyPermission(String principalType,int principalSn,
			int resourceSn,int permission,boolean yes);
	
	public void deletePermission(String principalType,int principalSn,
			int resourceSn);
	/**
	 * add or modify �û��̳�����
	 * @param userId
	 * @param resourceSn
	 * @param yes �Ƿ�����̳�
	 */
	public void addOrModifyUserExtends(int userId,int resourceSn,boolean yes);
	/**
	 * ��֤���ж��û���ĳ��Դ��ĳ�����Ƿ���Ч
	 * @param userId
	 * @param resourceSn
	 * @param permission
	 * @return true or false
	 */
	public boolean  hasPermission(int userId,int resourceSn,int permission);
	/**
	 * ��ѯ�û���ӵ�ж�ȡȨ�޵�ģ���б�
	 * @param userId
	 * @return ģ���б�
	 */
	public List<Module> searchModules(int userId);
	//��ʼ�����
	public List<?> searchAclRecord(String principalType, int principalSn);
	//��ʱ��֤
	public boolean hasPermissionByResourceSn(int userId, String sn, int permission);
	//dwr���÷���
	public boolean hasPermission1(int permission);
	
	public void addOrModifyDataPermission(int personId,int resourceSn,boolean yes);
	
	//��ʼ�����
	public String[] searchDataAclRecord(int personId,int orgId,String name);
	
	
}
