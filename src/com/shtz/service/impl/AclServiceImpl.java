package com.shtz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory.WebContextBuilder;
import org.directwebremoting.impl.DefaultWebContextBuilder;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import uk.ltd.getahead.dwr.WebContextFactory;

import com.shtz.model.ACL;
import com.shtz.model.Module;
import com.shtz.model.Person;
import com.shtz.model.User;
import com.shtz.service.AclService;
import com.shtz.util.Permission;

public class AclServiceImpl extends HibernateDaoSupport implements AclService {
	
	//��Ȩ
	public void addOrModifyPermission(String principalType, int principalSn,
			int resourceSn, int permission, boolean yes) {
		//��������ʶ����Դ��ʶ����ACLʵ��
		System.out.println("==addOrModifyPermission==");
		ACL acl = this.findAcl(principalType, principalSn, resourceSn);
		
		//������ACLʵ�������
		if(acl != null) {
			acl.setPermission(permission, yes);
			this.getHibernateTemplate().update(acl);
			return;
		}
		
		//������ACLʵ��
		acl = new ACL();
		acl.setPrincipalType(principalType);
		acl.setPrincipalSn(principalSn);
		acl.setResourceSn(resourceSn);
		acl.setPermission(permission, yes);
		
		this.getHibernateTemplate().save(acl);

	}
	
	//�����û��ļ̳�����
	//true��ʾ�̳У�false��ʾ���̳�
	public void addOrModifyUserExtends(int userId, int resourceSn, boolean yes) {
		//��������ʶ����Դ��ʶ����ACLʵ��
		System.out.println("==addOrModifyUserExtends==");
		ACL acl = this.findAcl(ACL.TYPE_USER, userId, resourceSn);
		
		//������ACLʵ�������
		if(acl != null) {
			acl.setExtends(yes);
			this.getHibernateTemplate().update(acl);
			return;
		}
		
		//������ACLʵ��
		acl = new ACL();
		acl.setPrincipalType(ACL.TYPE_USER);
		acl.setPrincipalSn(userId);
		acl.setResourceSn(resourceSn);
		acl.setExtends(yes);
		
		this.getHibernateTemplate().save(acl);

	}
	
	//ɾ��
	public void deletePermission(String principalType, int principalSn,
			int resourceSn) {
		this.getHibernateTemplate().delete(this.findAcl(principalType, principalSn, resourceSn));

	}
	public boolean hasPermission1(int permission) {
		HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
		User u=(User)request.getSession().getAttribute("login");
		int snid= (Integer)request.getSession().getAttribute("snid");
		int userId=u.getId();
		int resourceSn=snid;
		boolean yes_no=hasPermission(userId,resourceSn, permission);
		//User u=(User) ServletActionContext.getRequest().getSession().getAttribute("login");
		//int snid= (Integer) ServletActionContext.getRequest().getSession().getAttribute("snid");
		//int userId=u.getId();
		//int resourceSn=snid;
		//boolean yes_no=hasPermission(userId,resourceSn, permission);
		//return yes_no;
		return yes_no;
	}
	
	//��ʱ��֤
	@SuppressWarnings("unchecked")
	public boolean hasPermission(int userId, int resourceSn, int permission) {
		//����ֱ�������û�����Ȩ
		ACL acl = this.findAcl(ACL.TYPE_USER, userId, resourceSn);
		
		if(acl != null) {
			int result = acl.getPermission(permission);
			if(result != ACL.ACL_NEUTRAL) {
				return result == ACL.ACL_YES ? true : false;
			}
		}
		
		//��������û��Ľ�ɫ��Ȩ
		String hql = "select r.id from UsersRoles ur join ur.role r join ur.user u " +
				"where u.id = ? order by ur.orderNum";
		
		List<Integer> aclIds = this.getHibernateTemplate().find(hql, userId);
		//���ս�ɫ���ȼ�������ACLʵ��
		for(int id : aclIds) {
			acl = this.findAcl(ACL.TYPE_ROLE, id, resourceSn);
			if(acl != null) {
				return acl.getPermission(permission) == ACL.ACL_YES ? true : false;
			}
		}
		
		return false;
	}
	
	public boolean hasPermissionByResourceSn(int userId, String sn, int permission){
//		System.out.println("=AclServiceImpl=hasPermissionByResourceSn=WebContextFactory.get():"+WebContextFactory.get());
//		HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
//		System.out.println("=AclServiceImpl=hasPermissionByResourceSn=request:"+request);
//		User u=(User)request.getSession().getAttribute("login");
		System.out.println("=AclServiceImpl=hasPermissionByResourceSn=userId:"+userId+"====sn:"+sn+"====permission:"+permission);
//		System.out.println("=AclServiceImpl=hasPermissionByResourceSn=User:"+u);
		String hql = "select m.id from Module m where m.sn = ?";
		
		return this.hasPermission(
							userId, 
							(Integer)this.getSession().createQuery(hql).setParameter(0, sn).uniqueResult(), 
							permission);
	}
	
	@SuppressWarnings("unchecked")
	public List<Module> searchModules(int userId) {
		
		Map<Integer, ACL> temp = new HashMap<Integer, ACL>();
		//������ȼ��ӵ͵��߲����û�ӵ�еĽ�ɫ
		String hql = "select r.id from UsersRoles ur join ur.role r join ur.user u " +
				"where u.id = ? order by ur.orderNum desc";
		
		List<Integer> rIds = this.getHibernateTemplate().find(hql, userId);
		
		//���λ�ý�ɫ��Ȩ���б�
		for(int rId : rIds) {
			List<ACL> acls = this.findRoleACLs(rId);
			//����Ȩ������ʱ����
			for(ACL acl : acls) {
				temp.put(acl.getResourceSn(), acl);
			}
		}
		
		//����ֱ�������û�����Ȩ�б�
		List<ACL> acls = this.findUserAcls(userId);
		for(ACL acl : acls) {
			temp.put(acl.getResourceSn(), acl);
		}
		
		//�Ѿ�����û���ӵ�е�������Ȩ
		//ȥ��û�ж�ȡȨ�޵���Ȩ
		//��ʱ����
		List<Integer> delR = new ArrayList<Integer>();
		Set<Map.Entry<Integer, ACL>> entries = temp.entrySet();
		for(Map.Entry<Integer, ACL> entry : entries) {
			
			ACL acl = entry.getValue();
			
			if(acl.getPermission(Permission.READ) == ACL.ACL_NO) {
				delR.add(entry.getKey());
			}
		}
		
		for(Integer key : delR) {
			System.out.println(key);
		}
		
		//ɾ��û�ж�ȡȨ�޵���Ȩ
		for(Integer key : delR) {
			temp.remove(key);
		}
		//�ж�
		if(temp.isEmpty()) {
			return new ArrayList<Module>();
		}
		
		String searchModules = "select m from Module m where m.id in (:ids)";
		
		return this.getSession().createQuery(searchModules)
										.setParameterList("ids", temp.keySet())
										.list();
	}
	
	//����û�����ֱ�������û���Ȩ���б?����Ԫ����ACLʵ��ע�⣺��Ȩ���Ǽ̳еģ���Ӧ�ð����б��У�
	@SuppressWarnings({ "unchecked", "unused" })
	private List<ACL> findUserAcls(int userId) {
		String hql = "select acl from ACL acl where acl.principalType = ? " +
		"and acl.principalSn = ? and acl.extendsState = 0";
		
		return this.getHibernateTemplate().find(hql, new Object[]{ACL.TYPE_USER,userId});
	}
	
	
	//��ݽ�ɫ���ҽ�ɫ����Ȩ�б?�����б��Ԫ���ǣ�ACL
	@SuppressWarnings({ "unused", "unchecked" })
	private List<ACL> findRoleACLs(int roleId) {
		String hql = "select acl from ACL acl where acl.principalType = ? " +
				"and acl.principalSn = ?";
		
		return this.getHibernateTemplate().find(hql, new Object[]{ACL.TYPE_ROLE,roleId});
	}
	
	//���ҷ��������ACLʵ��
	private ACL findAcl(String principalType, int principalSn,
			int resourceSn) {
		
		return (ACL)this.getSession().createQuery(
				"select acl from ACL acl where acl.principalType = ? " +
				"and acl.principalSn = ? and acl.resourceSn = ?")
				.setParameter(0, principalType)
				.setParameter(1, principalSn)
				.setParameter(2, resourceSn)
				.uniqueResult();
	}
	
	public List<?> searchAclRecord(String principalType, int principalSn) {
		
		String sql = "select resourceSn,aclState&1,aclState&2," +
					"aclState&4,aclState&8,extendsState " +
					"from t_acl where principalType = '"+principalType + 
					"' and principalSn = "+principalSn;

		return this.getSession().createSQLQuery(sql).list();
	}
	
	
	public void addOrModifyDataPermission(int personId, int resourceSn,boolean yes) {
		System.out.println("==addOrModifyDataPermission==");
		User user=this.findUserByPersonId(personId);
		String auth="";
		if(yes){
			if(user.getAuth()!=null && !user.getAuth().equals("")){
				String[] str=user.getAuth().split(",");
				for (int i = 0; i < str.length; i++) {
					if(Integer.parseInt(str[i])==resourceSn) return;
				}
				auth=user.getAuth()+","+resourceSn;
			}else{
				auth=resourceSn+"";
			}
		}else{
			if(user.getAuth()!=null && !user.getAuth().equals("")){
				String[] str=user.getAuth().split(",");
				for (int i = 0; i < str.length; i++) {
					if(Integer.parseInt(str[i])!=resourceSn){
						if(auth.length()!=0)auth=auth+","+str[i];
						else auth=str[i];
					}
				}
			}
		}
		user.setAuth(auth);
		this.getHibernateTemplate().update(user);
	}
	
	private User findUserByPersonId(int personId) {
		String hql="from User u where u.person.id=?";
		User user=(User) this.getSession().createQuery(hql).setParameter(0, personId).uniqueResult();
		if(user==null){
			throw new RuntimeException("���û���û�е�½�˺�,���ȷ����û��˺�!");
		}
		return user;
	}

	
	public String[] searchDataAclRecord(int personId,int orgId,String name) {
		String hql="from Person p where 1=1 ";
		User user=this.findUserByPersonId(personId);
		String temp="";
		if(user.getAuth()!=null && !user.getAuth().equals("")){
			String[] str=user.getAuth().split(",");
			if(orgId!=0){
				hql += " and  p.org.id="+orgId;
			}
			if(name!=null && !name.equals("")){
				hql += " and  p.name like '%"+name+"%'";
			}
			List<Person> psrsonList=this.getSession().createQuery(hql).list();
			List<Integer>	personIdList=new ArrayList<Integer>();
			for (int i = 0; i < psrsonList.size(); i++) {
				personIdList.add(psrsonList.get(i).getId());
			}
			for (int j = 0; j < str.length; j++) {
				if(!personIdList.contains(Integer.parseInt(str[j]))){
					continue;
				}else{
					if(temp.trim().length()==0){
						temp+=str[j];
					}else{
						temp+=","+str[j];
					}
					
				}
			}
		}
			
		
		return temp.split(",");
	}

}
