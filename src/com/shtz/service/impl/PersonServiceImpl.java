package com.shtz.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shtz.model.Organization;
import com.shtz.model.Person;
import com.shtz.service.PageService;
import com.shtz.service.PersonService;
import com.shtz.util.PageModel;

public class PersonServiceImpl extends HibernateDaoSupport implements PersonService {
	private PageService service;
	
	
	public void setService(PageService service) {
		this.service = service;
	}

	public void addPerson(Person person, int orgId) {

		if(orgId == 0){
			throw new RuntimeException("机构不允许为空！");
		}
		
		person.setOrg(
				(Organization)getHibernateTemplate().load(Organization.class, orgId)
			);
		getHibernateTemplate().save(person);
	}

	public void deletePerson(int personId) {
		getHibernateTemplate().delete(
				getHibernateTemplate().load(Person.class, personId)
			);
	}
	public void modifyPerson(Person person) {	
		person=(Person) this.getHibernateTemplate().merge(person);
			this.getHibernateTemplate().update(person);
	}
	public Person findPerson(int personId) {
		return (Person)this.getSession().createQuery("from Person p where p.id="+personId).uniqueResult();
	}

	public PageModel searchPersons(int offset, int pageSize) {
		return service.getPageModel("from Person", offset, pageSize);
	}
	
	public PageModel searchPersons(int orgId,int offset, int pageSize) {
		return service.getPageModel("select p from Person p where p.org.id = "+orgId, offset, pageSize);
	}
	
	public PageModel serchPersionByParams(Map params,int offset, int pageSize){
		String hql = "select p from Person p where 1=1 ";
		if(!params.isEmpty()){
			Iterator it = params.entrySet().iterator();
			Map.Entry pa = null;
			while(it.hasNext()){
				pa = (Entry) it.next();
				String temp = pa.getKey().toString();
				if(temp.startsWith("id_")){
					hql += " and  p."+temp.substring(3)+" = "+pa.getValue();
				}else{
					hql += " and  p."+pa.getKey()+" like '%"+pa.getValue()+"%'";
				}
			}
		}
		
		
		
		return service.getPageModel(hql, offset, pageSize);
	}
}
