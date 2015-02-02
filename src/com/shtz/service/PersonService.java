package com.shtz.service;

import java.util.Map;

import com.shtz.model.Person;
import com.shtz.util.PageModel;

public interface PersonService {
	

	public PageModel searchPersons(int offset, int pageSize);
	

	public PageModel searchPersons(int orgId,int offset, int pageSize);

	public Person findPerson(int personId);

	public void addPerson(Person person,int orgId);
	

	public void deletePerson(int personId);
	
	public PageModel serchPersionByParams(Map params,int offset, int pageSize);
	
	public void modifyPerson(Person person);
	
}
