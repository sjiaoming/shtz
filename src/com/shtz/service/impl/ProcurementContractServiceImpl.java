package com.shtz.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shtz.model.ContractCredit;
import com.shtz.model.ProcurementContract;
import com.shtz.model.User;
import com.shtz.service.PageService;
import com.shtz.service.ProcurementContractService;
import com.shtz.util.PageModel;

public class ProcurementContractServiceImpl extends HibernateDaoSupport implements ProcurementContractService {
	private PageService service;
	
	
	public void setService(PageService service) {
		this.service = service;
	}

	public PageModel searchPlans(int offset, int pageSize) {
		return service.getPageModel("from Plan where procurementContract is null", offset, pageSize);
	}
	
	public String getMaxContractNum(String head){
		Object q = this.getSession().createQuery("select max(t.contractNum) from ProcurementContract t where t.contractNum like '"+head.trim()+"%'").uniqueResult();
		if(q != null){
			return (String)q;
		}
		return "" ;
	}
//	public PageModel serchsearchPlansByParams(Map params,int offset, int pageSize){
//		String hql = "from Plan p where p.procurementContract is null ";
//		if(!params.isEmpty()){
//			Iterator it = params.entrySet().iterator();
//			Map.Entry pa = null;
//			while(it.hasNext()){
//				pa = (Entry) it.next();
//				String temp = pa.getKey().toString();
//				if(temp.startsWith("id_")){
//					hql += " and  p."+temp.substring(3)+" = "+pa.getValue();
//				}else{
//					hql += " and  p."+pa.getKey()+" like '%"+pa.getValue()+"%'";
//				}
//			}
//		}
//		
//		
//		
//		return service.getPageModel(hql, offset, pageSize);
//	}
	
	public PageModel serchsearchPlansByParams(Map params,int offset, int pageSize){
		String hql = "from Plan p where 1=1 ";
		if(!params.isEmpty()){
			Iterator it = params.entrySet().iterator();
			Map.Entry pa = null;
			while(it.hasNext()){
				pa = (Entry) it.next();
				String temp = pa.getKey().toString();
				if(temp.startsWith("id_")){
					hql += " and  p."+temp.substring(3)+" = "+pa.getValue();
				}else if(temp.startsWith("eq_")){
					hql += " and  p."+temp.substring(3)+" = '"+pa.getValue()+"'";
				}else if(temp.startsWith("in_")){
					hql += " and  p."+temp.substring(3)+" in ("+pa.getValue()+")";
				}else if(temp.startsWith("diy_")){
					hql += " and  p."+temp.substring(4)+" "+pa.getValue();
				}else if(temp.startsWith("obj_")){
					
					User u = (User)params.get("obj_user");
					if(u!=null)
						hql += " and p.person.id in ("+u.getAuth()+")";
				}else{
					hql += " and  p."+pa.getKey()+" like '%"+pa.getValue()+"%'";
				}
			}
		}
		PageModel pm=service.getPageModel(hql, offset, pageSize);
		
		String hql1="select count(p.id) as totalCount,sum(p.num) as totalNumber,sum(p.budget) as totalBudget,sum(p.procurementMoney) as totalMoney from Plan p  where 1=1 ";
		if(!params.isEmpty()){
			Iterator it = params.entrySet().iterator();
			Map.Entry pa = null;
			while(it.hasNext()){
				pa = (Entry) it.next();
				String temp = pa.getKey().toString();
				if(temp.startsWith("id_")){
					hql1 += " and  p."+temp.substring(3)+" = "+pa.getValue();
				}else if(temp.startsWith("eq_")){
					hql1 += " and  p."+temp.substring(3)+" = '"+pa.getValue()+"'";
				}else if(temp.startsWith("in_")){
					hql1 += " and  p."+temp.substring(3)+" in ("+pa.getValue()+")";
				}else if(temp.startsWith("diy_")){
					hql1 += " and  p."+temp.substring(4)+" "+pa.getValue();
				}else if(temp.startsWith("obj_")){
					User u = (User)params.get("obj_user");
					if(u!=null)
						hql += " and p.person.id in ("+u.getAuth()+")";
				}else{
					hql1 += " and  p."+pa.getKey()+" like '%"+pa.getValue()+"%'";
				}
			}
		}
		Object[] sumstr=(Object[]) this.getSession().createQuery(hql1).uniqueResult();
			pm.setTotal(Integer.parseInt(sumstr[0].toString()));
			if(sumstr[1]!=null){
				pm.setDouble1(Double.parseDouble(sumstr[1].toString()) );
			}else{
				pm.setDouble1(0.0);
			}
			if(sumstr[2]!=null){
				pm.setDouble2(Double.parseDouble(sumstr[2].toString()) );			
			}else{
				pm.setDouble2(0.0);
			}
			if(sumstr[3]!=null){
				pm.setDouble3(Double.parseDouble(sumstr[3].toString()) );
			}else{
				pm.setDouble3(0.0);
			}
		return pm;
	}
	public PageModel searchPlansForPlanAdd(Map params,int offset, int pageSize){
		String hql = "from Plan p where 1=1 ";
		if(!params.isEmpty()){
			Iterator it = params.entrySet().iterator();
			Map.Entry pa = null;
			while(it.hasNext()){
				pa = (Entry) it.next();
				String temp = pa.getKey().toString();
				if(temp.startsWith("id_")){
					hql += " and  p."+temp.substring(3)+" = "+pa.getValue();
				}else if(temp.startsWith("eq_")){
					hql += " and  p."+temp.substring(3)+" = '"+pa.getValue()+"'";
				}else if(temp.startsWith("in_")){
					hql += " and  p."+temp.substring(3)+" in ("+pa.getValue()+")";
				}else if(temp.startsWith("diy_")){
					hql += " and  p."+temp.substring(4)+" "+pa.getValue();
				}else if(temp.startsWith("obj_")){
					
					User u = (User)params.get("obj_user");
					if(u!=null)
						hql += " and p.person.id in ("+u.getAuth()+")";
				}else{
					hql += " and  p."+pa.getKey()+" like '%"+pa.getValue()+"%'";
				}
			}
		}
		 return service.getPageModel(hql, offset, pageSize);
		
	}
	
	public void addProcurementContract(ProcurementContract procurementContract){
		
		this.getHibernateTemplate().save(procurementContract);
		
	}
	public void addContractCredit(ContractCredit contractCredit){
		this.getHibernateTemplate().save(contractCredit);
	}
	
	public ProcurementContract findProcurementContractById(int id){
		return (ProcurementContract)this.getHibernateTemplate().get(ProcurementContract.class, id);
	}
	public List getPlanArrival(int planId){
		List l = new ArrayList();
		l = this.getSession().createSQLQuery("select sum(a.arrivalNum) as arrivalNum,sum(a.acceptanceNum) as acceptanceNum from t_arrivalitems a where a.aid = "+planId+" group by a.aid").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return l;
	}
	public List<?> findProcurementContractByContractNum(String contractNum){
		return this.getSession().createQuery("from ProcurementContract p where p.contractNum = ?").setParameter(0, contractNum).list();
	}
	public List<?> findArrivalsIdByPlanids(String ids){
		return this.getSession().createSQLQuery("select a.id from t_arrivalitems a where a.aid in ("+ids+")").list();
	}
	public String testdwr(){
		System.out.println("sssssssss");
		return "ssssssssss";
	}
}
