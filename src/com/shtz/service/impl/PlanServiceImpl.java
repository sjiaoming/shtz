package com.shtz.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shtz.model.Change;
import com.shtz.model.Organization;
import com.shtz.model.Person;
import com.shtz.model.Plan;
import com.shtz.model.ProcurementContract;
import com.shtz.model.ProcurementContractBean;
import com.shtz.model.ReportCompStatisticsBean;
import com.shtz.model.SalesContract;
import com.shtz.model.User;
import com.shtz.service.PageService;
import com.shtz.service.PlanService;
import com.shtz.util.Arith;
import com.shtz.util.PageModel;
import com.shtz.util.StatusName;

public class PlanServiceImpl extends HibernateDaoSupport implements PlanService {
	private PageService service;
	
	private Arith arithService;
	
	private PageModel pageModel;
	
	private StatusName statusName;
	
	

	public void setStatusName(StatusName statusName) {
		this.statusName = statusName;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	
	public void setArithService(Arith arithService){
		this.arithService = arithService;
	}
	
	public void setService(PageService service) {
		this.service = service;
	}

	public void addPlan(Plan plan, int orgId) {

		if(orgId == 0){
			throw new RuntimeException("部门不允许为空！");
		}
		
		plan.setOrg(
				(Organization)getHibernateTemplate().load(Organization.class, orgId)
			);
		getHibernateTemplate().save(plan);
	}
	public void addPlans(Plan plan) {
		getHibernateTemplate().save(plan);
	}
	public void deletePlan(int planId) {
		getHibernateTemplate().delete(
				getHibernateTemplate().load(Plan.class, planId)
			);
	}

	public Plan findPlan(int planId) {
		return (Plan)getHibernateTemplate().load(Plan.class, planId);
	}

	public PageModel searchPlans(int offset, int pageSize) {
		return service.getPageModel("from Plan  where procurementContract is null", offset, pageSize);
	}
	public PageModel serchsearchPlansByParams(Map params,int offset, int pageSize){
		String hql = "from Plan p ";
		hql = this.getParamsSql(hql, params, " order by p.planReceiptDate asc,p.reportCompId,p.oldPlanNum");
		PageModel pm=service.getPageModel(hql, offset, pageSize);
		String hql1="select count(p.id) as totalCount,sum(p.num) as totalNumber,sum(p.budget) as totalBudget,sum(p.procurementMoney) as procurementMoney   from Plan p ";
		hql1= this.getParamsSql(hql1, params,"");
		Object[] sumstr=(Object[]) this.getSession().createQuery(hql1).uniqueResult();
			pm.setTotal1(Integer.parseInt(sumstr[0].toString()));
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
	public PageModel searchPlansByOrg(int orgId,int offset, int pageSize) {
		return service.getPageModel("select p from Plan p where p.org.id = "+orgId, offset, pageSize);
	}
	
	public void addProcurementWay(String[] tags,String procurementName,String procurementWay,Date procurementDate,String procurementRemark,String planStatus){
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		int id = 0;
		Plan p = new Plan();
		for(String temp : tags){
			id = Integer.parseInt(temp);
			p =(Plan)this.getHibernateTemplate().load(Plan.class, id);
			if(procurementName != null)
				p.setProcurementName(procurementName);
			if(procurementWay != null)
				p.setProcurementWay(procurementWay);
			if(procurementDate != null)
				p.setProcurementDate(procurementDate);
			if(procurementRemark != null)
				p.setProcurementRemark(procurementRemark);
			if(planStatus != null && !"".equals(planStatus))
				p.setPlanStatus(planStatus);
			//System.out.println(new Date());
			if(p.getProcurementSigningleDate() == null)
				p.setProcurementSigningleDate(new Date());
			this.getHibernateTemplate().update(p);
		}
	}
	public void addPlanBNumber(String[] tags,String bNumber){
		int id = 0;
		Plan p = new Plan();
		for(String temp : tags){
			id = Integer.parseInt(temp);
			p =(Plan)this.getHibernateTemplate().load(Plan.class, id);
			if(bNumber != null)
				p.setbNumber(bNumber);
			this.getHibernateTemplate().update(p);
		}
	}
	public void modifyPlanChange(String planId,Double changenum,double changecontractMoney,double planPrice){
		int id =Integer.parseInt(planId);
			Plan p  =(Plan)this.getHibernateTemplate().load(Plan.class, id);
			p.setNum(changenum);
			p.setBudget(changecontractMoney);
			p.setPlanPrice(planPrice);
			p.setProcurementFlag("02");
			//p.setContractMoney(changecontractMoney);
			this.getHibernateTemplate().update(p);
	}
	
	public void addSeekSource(String[] tags,Map m){
		int id = 0;
		for(String temp : tags){
			Plan p = new Plan();
			id = Integer.parseInt(temp);
			p =(Plan)this.getHibernateTemplate().load(Plan.class, id);
			if(m.get("searchAnnouncedDate")!=null)
				p.setSearchAnnouncedDate((Date)m.get("searchAnnouncedDate"));
			if(m.get("searchDate")!=null)
				p.setSearchDate((Date)m.get("searchDate"));
			if(m.get("contractExecutionWay")!=null)
				p.setContractExecutionWay((String)m.get("contractExecutionWay"));
			p.setContractMoney((Double)m.get("contractMoney"));
			p.setPlanStatus("03");
			this.getHibernateTemplate().update(p);
		}
			
	}
	public void modifySeekSource(int id,Map m){
			Plan p =(Plan)this.getHibernateTemplate().load(Plan.class, id);
		if(m.get("searchAnnouncedDate")!=null)
			p.setSearchAnnouncedDate((Date)m.get("searchAnnouncedDate"));
		if(m.get("searchDate")!=null)
			p.setSearchDate((Date)m.get("searchDate"));
		if(m.get("contractExecutionWay")!=null)
			p.setContractExecutionWay((String)m.get("contractExecutionWay"));
		p.setContractMoney((Double)m.get("contractMoney"));
			this.getHibernateTemplate().update(p);
			
	}
	/**
	 * @see 寻源导入
	 * @param Plan p
	 */
	public void modifySeekSource(Plan p ){
		Plan plan = (Plan)this.getSession().createQuery("from Plan p where p.oldPlanNum = ? and p.planNum = ? ").setParameter(0, p.getOldPlanNum()).setParameter(1, p.getPlanNum()).uniqueResult();
		if(plan != null){
			plan.setProcurementPrice(p.getProcurementPrice());
			plan.setProcurementMoney(p.getProcurementPrice() * plan.getNum());
			this.getHibernateTemplate().update(plan);
		}
		
	}
	/**
	 * @see 方案导入
	 * @param Plan p
	 */
	public void modifyPlanTrack(Plan p ){
		Plan plan = (Plan)this.getSession().createQuery("from Plan p where p.oldPlanNum = ? and p.planNum = ? ").setParameter(0, p.getOldPlanNum()).setParameter(1, p.getPlanNum()).uniqueResult();
		if(plan != null){
			plan.setProcurementWayNum(p.getProcurementWayNum());
			plan.setbNumber(p.getbNumber());
			this.getHibernateTemplate().update(plan);
		}
		
	}
	public void addOrUpdageChange(Plan p ,Date changeTime,Double changenum,double changebudget,String changereason,double planPrice,String name){
		Change ch=(Change) this.getSession().createQuery("from Change c where c.plan.id="+p.getId()).uniqueResult();
		if(ch == null){
			ch = new Change();
			ch.setChangeTime(changeTime);
			ch.setNum(changenum);
			ch.setContractMoney(changebudget);
			ch.setReason(changereason);
			ch.setPlanPrice(planPrice);
			ch.setPlan(p);
			ch.setPersonName(name);
			this.getHibernateTemplate().save(ch);
		}else{
			ch.setChangeTime(changeTime);
			ch.setNum(changenum);
			ch.setContractMoney(changebudget);
			ch.setReason(changereason);
			ch.setPlanPrice(planPrice);
			ch.setPlan(p);
			ch.setPersonName(name);
			this.getHibernateTemplate().update(ch);
		}
	}

	public void modifyPlan(Plan plan){
		Plan p=new Plan();
		p=this.findPlan(plan.getId());
		if(p!=null){
			if(plan.getReportComp()!=null && !"".equals(plan.getReportComp()))
				p.setReportComp(plan.getReportComp());
			if(plan.getOldPlanNum()!=null && !"".equals(plan.getOldPlanNum()))
				p.setOldPlanNum(plan.getOldPlanNum());
			if(plan.getItemsName()!=null && !"".equals(plan.getItemsName()))
				p.setItemsName(plan.getItemsName());
			if(plan.getModel()!=null && !"".equals(plan.getModel()))
				p.setModel(plan.getModel());
			if(plan.getPlanNum()!=null && !"".equals(plan.getPlanNum()))
				p.setPlanNum(plan.getPlanNum());
			if(plan.getProcurementWay()!=null && !"".equals(plan.getProcurementWay()))
				p.setProcurementWay(plan.getProcurementWay());
			if(plan.getProcurementDate()!=null && !"".equals(plan.getProcurementDate()))
				p.setProcurementDate(plan.getProcurementDate());
			if(plan.getProcurementRemark()!=null && !"".equals(plan.getProcurementRemark()))
				p.setProcurementRemark(plan.getProcurementRemark());
			//p=(Plan) this.getHibernateTemplate().merge(p);
			this.getHibernateTemplate().update(p);
		}
	}
	public void modifyPlanMg(Plan plan){
		Plan p=new Plan();
		p=this.findPlan(plan.getId());
		if(p!=null){
			p.setPlanReceiptDate(plan.getPlanReceiptDate());
			p.setOldPlanNum(plan.getOldPlanNum());
			p.setItemsNum(plan.getItemsNum());
			p.setItemsName(plan.getItemsName());
			p.setProcurementPrice(plan.getProcurementPrice());
			p.setNum(plan.getNum());
			p.setUnit(plan.getUnit());
			p.setProcurementMoney(plan.getProcurementMoney());
			p.setArrivalDate(plan.getArrivalDate());
			p.setReportComp(plan.getReportComp());
			p.setRemark(plan.getRemark());
			//p=(Plan) this.getHibernateTemplate().merge(p);
			this.getHibernateTemplate().update(p);
		}
	}
	public List getPlans(Integer[] ids){
		//Query  q =this.getSession().createQuery("select p from Plan p where p.id in (:ids)").setParameterList("ids",ids);
		//System.out.println(q.list().size());
		
		return this.getSession().createQuery("select p from Plan p where p.id in (:ids)").setParameterList("ids",ids).list();
		
	}
	public void modifyPlanPM(Plan plan){
		Plan p=this.findPlan(plan.getId());
		if(p!=null){
				if(plan.getProcurementContract()!=null)
					p.setProcurementContract(plan.getProcurementContract());
				if(plan.getPlanStatus()!=null)
					p.setPlanStatus(plan.getPlanStatus());
				if(plan.getContractNum()!=null)
					p.setContractNum(plan.getContractNum());
				if(plan.getSearchDate()!=null)
					p.setSearchDate(plan.getSearchDate());
				if(plan.getProcurementPrice()!=null)
					p.setProcurementPrice(plan.getProcurementPrice());
				if(plan.getProcurementMoney()!=null)
					p.setProcurementMoney(plan.getProcurementMoney());
				if(plan.getSearchAnnouncedDate()!=null)
					p.setSearchAnnouncedDate(plan.getSearchAnnouncedDate());
				if(plan.getContractExecutionWay()!=null)
					p.setContractExecutionWay(plan.getContractExecutionWay());
			}
			this.getHibernateTemplate().update(p);
		
	}
	public void modifyPlanClearSeekDate(Plan plan){
		Plan p=this.findPlan(plan.getId());
		if(p!=null){
				p.setSearchDate(null);
				p.setProcurementPrice(null);
				p.setProcurementMoney(null);
				p.setSearchAnnouncedDate(null);
				p.setContractExecutionWay(null);
				p.setPlanStatus("02");
			this.getHibernateTemplate().update(p);
		}
	}
	public Organization findOrgByNames(String orgName) {
		String hql="from Organization o where o.name=?";
		Organization o=(Organization) this.getSession().createQuery(hql).setParameter(0, orgName).uniqueResult();
		if(o == null)
			return null;
		return  o;
	
	}
	public List getPlanListByOldPlanNum(String oldPlanNum,String planNum){
		return this.getSession().createQuery("from Plan p where p.oldPlanNum = ? and p.planNum = ?").setParameter(0, oldPlanNum).setParameter(1, planNum).list();
	}
	public User findUserByName(String userName){
		User u = (User)this.getSession().createQuery("select u from User u where u.username = ?")
				.setParameter(0, userName).uniqueResult();
		if(u == null)
			return null;
		return u;
	}
	
	public Person findPersonByName(String userName,Organization org){
		Person u = (Person)this.getSession().createQuery("select p from Person p where p.name = ? and p.org = ?")
				.setParameter(0, userName).setParameter(1, org).uniqueResult();
		if(u == null)
			return null;
		return u;
	}
	//修改
	public void modifyPlan(int pid, double salesRatio, double procurementMoney) {
		Plan p=(Plan) this.getHibernateTemplate().load(Plan.class, pid);
		p.setSalesRatio(salesRatio);
		p.setContractMoney(salesRatio*procurementMoney);
		p.setSalesMoney(salesRatio*procurementMoney);
		this.getHibernateTemplate().update(p);
		
	}

	public void modifyPlanSalesMoney(int pid, double salesMoney) {
		Plan p=(Plan) this.getHibernateTemplate().load(Plan.class, pid);
		p.setContractMoney(salesMoney);
		this.getHibernateTemplate().update(p);
	}
	
	public int getPlanCountByDate(String s,String e,Map params){
		//Map newparams = params;
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select count(p.id) from Plan p ";
		newparams.put("dy_p.model ", " <> '02' ");
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.planReceiptDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, " and p.planStatus <> '06'");
		Long a = (Long)this.getSession().createQuery(sql).uniqueResult();
		return a.intValue();
	}
	public Double getPlanMoneyByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select sum(p.budget) from Plan p ";
		newparams.put("dy_p.model ", " <> '02' ");
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.planReceiptDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, " and p.planStatus <> '06'");
		return (Double)this.getSession().createQuery(sql).uniqueResult();
	}
	public int getProcurementContractCountByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select count(p.id) from ProcurementContract p ";
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.signingDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, "");
		Long a = (Long)this.getSession().createQuery(sql).uniqueResult();
		return a.intValue();
	}
	public int getPlanXYCountByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select count(p.id) from Plan p ";
		newparams.put("dy_p.model ", " <> '02' ");
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.searchDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, " and p.planStatus <> '06'");
		Long a = (Long)this.getSession().createQuery(sql).uniqueResult();
		return a.intValue();
	}
	public Double getPlanXYMoneyByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select sum(p.procurementMoney) from Plan p  ";
		newparams.put("dy_p.model ", " <> '02' ");
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.searchDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, " and p.planStatus <> '06'");
		return (Double)this.getSession().createQuery(sql).uniqueResult();
	}
	public int getPlanDXYCountByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select count(p.id) from Plan p  ";
		newparams.put("dy_p.parent ", " is null ");
		newparams.put("dy_p.searchDate ", " is null ");
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.planReceiptDate", " <='"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, " and p.planStatus <> '06'");
		Long a = (Long)this.getSession().createQuery(sql).uniqueResult();
		return a.intValue();
	}
	public Double getPlanDXYMoneyByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select sum(p.budget) from Plan p  ";
		newparams.put("dy_p.parent ", " is null ");
		newparams.put("dy_p.searchDate ", " is null ");
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.planReceiptDate", " <='"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, " and p.planStatus <> '06'");
		return (Double)this.getSession().createQuery(sql).uniqueResult();
	}
	
	public Double getProcurementMoneyCountByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select sum(p.totalMoney) from ProcurementContract p ";
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.signingDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, "");
		return (Double)this.getSession().createQuery(sql).uniqueResult();
	}
	
	public Double getPlanArrivalMoneyByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select sum(p.plan.procurementPrice * p.arrivalNum) from ArrivalItems p ";
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.plan.planReceiptDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, "");
		return (Double)this.getSession().createQuery(sql).uniqueResult();
	}
	public int getSalesContractCountByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select count(p.id) from SalesContract p ";
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.signingDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, "");
		Long a = (Long)this.getSession().createQuery(sql).uniqueResult();
		return a.intValue();
	}
	public Double getSalesContractMoneyByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select sum(p.contractMoney) from SalesContract p ";
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.signingDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, "");
		return (Double)this.getSession().createQuery(sql).uniqueResult();
	}
	public Double getContractCreditMoneyByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select sum(p.contractCreditMoney) from ContractCredit p  ";
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.procurementContract.signingDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, "");
		return (Double)this.getSession().createQuery(sql).uniqueResult();
	}
	public List<ProcurementContract> getProcurementsByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "from ProcurementContract p ";
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.signingDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, "");
		return this.getSession().createQuery(sql).list();
	}
	public List<SalesContract> getSalesContractsByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "from SalesContract p ";
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.signingDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, "");
		return this.getSession().createQuery(sql).list();
	}
	public Double getbillingMoneyByDate(String s,String e,Map params){
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select sum(p.billingMoney) from Billing p  ";
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.salesContract.signingDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, "");
		return (Double)this.getSession().createQuery(sql).uniqueResult();
	}
	public Double getJzByDate(String s,String e,Map params,Double xYMoney){
		Session session = this.getSession();
		Map newparams = new HashMap();
		Set<String> keys = params.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String key = (String)it.next();
			newparams.put(key, params.get(key));
		}
		String sql = "select sum(p.budget) from Plan p  ";
		newparams.put("dy_p.parent ", " is null ");
		//newparams.put("dy_p.model ", " <> '02' ");
		newparams.put("dy_p.searchDate ", " is not null ");
		if(s!=null && !"".equals(s.trim())  && e!=null && !"".equals(e.trim()) ){
			newparams.put("dy_p.searchDate", " between '"+s+"' and '"+e+"'");
		}
		sql = this.getParamsSql(sql, newparams, " and p.planStatus <> '06'");
		Double temp1 =  (Double)this.getSession().createQuery(sql).uniqueResult();
		releaseSession(session);
		return arithService.sub(temp1 == null ? 0.0 : temp1,xYMoney == null ? 0.0 : xYMoney);
	}
	public void addcfPlan(int id,Double[] s,String cflag){
		//List<Plan> l = new ArrayList<Plan>();
		
		int tempId = id;
		Plan p = this.findPlan(tempId);
		
		if(s.length>0){
			Double tempPlanPrice = arithService.div(p.getBudget(),p.getNum(), 5);
			for(int i=0;i<s.length;i++){
				Plan p1 = new Plan();
				p1.setItemsName(p.getItemsName());
				p1.setOldPlanNum(p.getOldPlanNum());
				p1.setReportComp(p.getReportComp());
				p1.setReportCompId(p.getReportCompId());
				p1.setPlanNum(p.getPlanNum());
				p1.setPlanReceiptDate(p.getPlanReceiptDate());
				p1.setUnit(p.getUnit());
				p1.setArrivalDate(p.getArrivalDate());
				p1.setBudget(arithService.mul(tempPlanPrice, s[i]));
				p1.setProcurementWay(p.getProcurementWay());
				p1.setProcurementDate(p.getProcurementDate());
				p1.setProcurementRemark(p.getProcurementRemark());
				p1.setSearchAnnouncedDate(p.getSearchAnnouncedDate());
				p1.setSearchDate(p.getSearchDate());
				p1.setContractExecutionWay(p.getContractExecutionWay());
				p1.setPlanPrice(p.getPlanPrice());
				p1.setPlanStatus(p.getPlanStatus());
				p1.setOrg(p.getOrg());
				p1.setItemsNum(p.getItemsNum());
				p1.setPerson(p.getPerson());
				p1.setUser(p.getUser());
				p1.setModel("01");
				p1.setProcurementPrice(p.getProcurementPrice());
				p1.setProcurementContract(p.getProcurementContract());
				p1.setProcurementMoney(arithService.mul(p.getProcurementPrice(), s[i]) );
				p1.setProcurementSigningleDate(p.getProcurementSigningleDate());
				p1.setProcurementName(p.getProcurementName());
				p1.setbNumber(p.getbNumber());
				p1.setFaNumber(p.getFaNumber());;
				p1.setArrivalAddress(p.getArrivalAddress());
				p1.setProcurementWayNum(p.getProcurementWayNum());
				/**
				 * if 采购合同拆分，拆分的是计划的数量
				 * else 销售合同拆分，拆分的是计划的合同数量
				 */
				if(cflag!=null && !"".equals(cflag) && cflag.equals("pcontract")){
					p1.setNum(s[i]);
				}
				else{
					p1.setNum(s[i]);
					p1.setContractNum(s[i]);
				}
					
				
				if(p.getParent() ==null){
					p1.setParent(p);
				}else{
					p1.setParent(p.getParent());
				}
				p1.setOldPlanNum(p1.getOldPlanNum());
				this.addPlans(p1);
				
//				if(p.getParent() ==null){
//					p1.setOldPlanNum(p1.getOldPlanNum()+"_"+p1.getId());
//				}else{
//					p1.setOldPlanNum(p1.getParent().getOldPlanNum()+"_"+p1.getId());
//				}
//				this.getHibernateTemplate().update(p1);
			}
			//如果拆分的是已经拆分过的计划，就删除。
			if(p.getParent()!=null){
				this.getHibernateTemplate().delete(p);
			}else{
				p.setModel("02");
				this.getHibernateTemplate().update(p);
			}
		}
	}
	public PageModel getPlanStatusList(int offset,int pageSize,Map params,boolean isLimit){
		String sql="select p.*,pc.*,pc.contractNum as pccn,sum(a.arrivalNum) as totalArrivalNum,sum(a.acceptanceNum) as totalAcceptanceNum,pc.arrivaldate as cad from t_plan p  " +
				"left join t_arrivalitems a on a.aid = p.id " +
				"left join t_procurementcontract pc on pc.id = p.pid "+
				"left join t_person ps on ps.id = p.psid ";
		String sql_last = "  group by p.id order by p.planReceiptDate asc";
		sql = this.getParamsSql(sql, params, sql_last);
		Query q = this.getSession().createSQLQuery(sql)
				.addEntity(Plan.class)
				.addEntity(ProcurementContract.class)
				.addScalar("pccn", Hibernate.STRING)
				.addScalar("totalArrivalNum", Hibernate.DOUBLE)
				.addScalar("totalAcceptanceNum", Hibernate.DOUBLE)
				.addScalar("cad", Hibernate.DATE);
		Query totalQuery = null;
		BigInteger o = null;
		if(isLimit){
		String countSql = "select count(s.pid) from (select p.id as pid from t_plan p " +
					"left join t_arrivalitems a on a.aid = p.id  " +
					"left join t_procurementcontract pc on pc.id = p.pid  "+
					"left join t_person ps on ps.id = p.psid ";
		String countSql_last = " group by p.id) s";
		countSql = this.getParamsSql(countSql, params, countSql_last);
		o = (BigInteger)this.getSession().createSQLQuery(countSql).uniqueResult();
		
		String totalSql = "select count(s.pid) as totalCount,sum(s.budget) as totalBudget,sum(s.procurementMoney) as totalProcurementMoney," +
					" sum(s.totalArrivalNum) as totalArrivalNum,sum(s.totalArricalMoney) as totalArricalMoney,sum(s.totalAcceptanceNum) as totalAcceptanceNum," +
					" sum(s.totalAcceptanceMoney) as totalAcceptanceMoney " +
					" from (select p.id as pid,p.budget,p.procurementMoney,sum(a.arrivalNum) as totalArrivalNum," +
					" sum(a.arrivalNum*p.procurementPrice) as totalArricalMoney, sum(a.acceptanceNum) as totalAcceptanceNum," +
					" sum(a.acceptanceNum*p.procurementPrice) as totalAcceptanceMoney from t_plan p " +
					" left join t_arrivalitems a on a.aid = p.id  " +
					" left join t_procurementcontract pc on pc.id = p.pid "+
					"left join t_person ps on ps.id = p.psid ";
		String totalSql_last = " group by p.id) s";
		totalSql = this.getParamsSql(totalSql, params, totalSql_last);
		totalQuery = this.getSession().createSQLQuery(totalSql)
				.addScalar("totalCount", Hibernate.INTEGER)
				.addScalar("totalBudget", Hibernate.DOUBLE)
				.addScalar("totalProcurementMoney", Hibernate.DOUBLE)
				.addScalar("totalArrivalNum", Hibernate.DOUBLE)
				.addScalar("totalArricalMoney", Hibernate.DOUBLE)
				.addScalar("totalAcceptanceNum", Hibernate.DOUBLE)
				.addScalar("totalAcceptanceMoney", Hibernate.DOUBLE);
			q.setFirstResult(offset);
			q.setMaxResults(pageSize);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		@SuppressWarnings("unchecked")
		List<Object[]> l = q.list();
		List<ProcurementContractBean> list = new ArrayList<ProcurementContractBean>();
		for(int i=0;i<l.size();i++){
			
			ProcurementContractBean pcb = new ProcurementContractBean();
			pcb.setNumber(offset+1+i);
			pcb.setReportComp(((Plan)l.get(i)[0]).getReportCompId());
			pcb.setReportCompName(((Plan)l.get(i)[0]).getReportComp());
			pcb.setOldPlanNum(((Plan)l.get(i)[0]).getOldPlanNum());
			pcb.setItemsName(((Plan)l.get(i)[0]).getItemsName());
			pcb.setItemsNum(((Plan)l.get(i)[0]).getItemsNum());
			pcb.setUnit(((Plan)l.get(i)[0]).getUnit());
			pcb.setPlanNum(((Plan)l.get(i)[0]).getPlanNum());
			pcb.setPlanPrice(((Plan)l.get(i)[0]).getPlanPrice());
			pcb.setBudget(((Plan)l.get(i)[0]).getBudget());
			pcb.setProcurementWay(statusName.getProcurementWay(((Plan)l.get(i)[0]).getProcurementWay()));
			pcb.setProcurementDate(((Plan)l.get(i)[0]).getProcurementDate());
			pcb.setSearchAnnouncedDate(((Plan)l.get(i)[0]).getSearchAnnouncedDate());
			pcb.setSearchDate(((Plan)l.get(i)[0]).getSearchDate());
			pcb.setContractExecutionWay(statusName.getContractExecutionWay(((Plan)l.get(i)[0]).getContractExecutionWay()));
			pcb.setProcurementPrice(((Plan)l.get(i)[0]).getProcurementPrice());
			pcb.setPlancontractNum(((Plan)l.get(i)[0]).getContractNum());
			pcb.setProcurementMoney(((Plan)l.get(i)[0]).getProcurementMoney());
			pcb.setPlanReceiptDate(((Plan)l.get(i)[0]).getPlanReceiptDate());
			pcb.setNum(((Plan)l.get(i)[0]).getNum());
			pcb.setPlanStatus(((Plan)l.get(i)[0]).getPlanStatus());
			pcb.setOrgName(((Plan)l.get(i)[0]).getOrg().getName());
			if(((Plan)l.get(i)[0]).getPerson()!=null)
				pcb.setPersonName(((Plan)l.get(i)[0]).getPerson().getName());
			if(l.get(i)[2]!=null)
				pcb.setContractNum((l.get(i)[2]).toString());
			else
				pcb.setContractNum("");
			pcb.setSigningDate(((ProcurementContract)l.get(i)[1]).getSigningDate());
			if(l.get(i)[5]!=null)
				try {
					pcb.setArrivalDate(sdf.parse((l.get(i)[5]).toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			pcb.setTotalMoney(((ProcurementContract)l.get(i)[1]).getTotalMoney());
			pcb.setSuppliersName(((ProcurementContract)l.get(i)[1]).getSuppliersName());
			
			
			if(l.get(i)[3]!=null){
				pcb.setArrivalNum(Double.parseDouble( (l.get(i)[3]).toString()   ));
			}else{
				pcb.setArrivalNum(0.0);
			}
			pcb.setArrivalMoney(arithService.mul(pcb.getArrivalNum(), (pcb.getProcurementPrice()==null?0:pcb.getProcurementPrice())));
			if(l.get(i)[4]!=null){
				pcb.setAcceptanceNum(Double.parseDouble((l.get(i)[4]).toString()));
			}else{
				pcb.setAcceptanceNum(0.0);
			}
			pcb.setAcceptanceMoney(arithService.mul(pcb.getAcceptanceNum(), (pcb.getProcurementPrice()==null?0:pcb.getProcurementPrice())));
			pcb.setSaleContractNum(((Plan)l.get(i)[0]).getSalesContract()==null?"":((Plan)l.get(i)[0]).getSalesContract().getContractNum());
			if(((Plan)l.get(i)[0]).getSalesContract()!=null){
				pcb.setSaleContractSigningDate(((Plan)l.get(i)[0]).getSalesContract().getSigningDate());
			}
			if(((Plan)l.get(i)[0]).getSalesContract()!=null)
			pcb.setSaleContractMoney(((Plan)l.get(i)[0]).getSalesContract().getContractMoney());
			list.add(pcb);
		}
		if(isLimit){
			List<Object[]> totalList = totalQuery.list();
			for(int i=0;i<totalList.size();i++){
				if(totalList.get(i)[0]!=null){
					pageModel.setTotal1(Integer.parseInt(totalList.get(i)[0].toString()));
				}else{
					pageModel.setTotal1(0);
				}
				if(totalList.get(i)[1]!=null){
					pageModel.setDouble1(Double.parseDouble(totalList.get(i)[1].toString()));
				}else{
					pageModel.setDouble1(0.0);
				}
				if(totalList.get(i)[2]!=null){
					pageModel.setDouble2(Double.parseDouble(totalList.get(i)[2].toString()));
				}else{
					pageModel.setDouble2(0.0);
				}
				if(totalList.get(i)[3]!=null){
					pageModel.setDouble3(Double.parseDouble(totalList.get(i)[3].toString()));
				}else{
					pageModel.setDouble3(0.0);
				}
				if(totalList.get(i)[4]!=null){
					pageModel.setDouble4(Double.parseDouble(totalList.get(i)[4].toString()));
				}else{
					pageModel.setDouble4(0.0);
				}
				if(totalList.get(i)[5]!=null){
					pageModel.setDouble5(Double.parseDouble(totalList.get(i)[5].toString()));
				}else{
					pageModel.setDouble5(0.0);
				}
				if(totalList.get(i)[6]!=null){
					pageModel.setDouble6(Double.parseDouble(totalList.get(i)[6].toString()));
				}else{
					pageModel.setDouble6(0.0);
				}
				
			}
		pageModel.setTotal(o==null?0:o.intValue());
		}
		
		pageModel.setList(list);
		
		return pageModel;
	}
	public PageModel getReportCompList(int offset,int pageSize,Map params,boolean isLimit,String sDate,String eDate,int orgId,int useCompId){
		String sql = "";
		String countSql = "";
		if(orgId == 0){
			sql = "select uc.id reportCompId,uc.name reportComp," +
					"(SELECT ROUND(sum(pl.budget), 2) FROM t_plan pl WHERE pl.reportCompId = uc.id AND pl.planStatus <> '06' AND pl.planReceiptDate BETWEEN '"+sDate+"' and '"+eDate+"'  and pl.pcid is null ) budget,"+
					"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.procurementWay = '1'  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null ) apm," +
					"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.procurementWay = '2'  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null  ) bpm," +
					"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.procurementWay = '3'  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null  ) cpm," +
					"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.procurementWay = '4'  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null  ) dpm," +
					"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.procurementWay = '5'  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null  ) epm," +
					"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.reportCompId = uc.id  AND pl.planStatus <> '06' and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null    ) procurementMoney," +
					"(select ROUND((sum(pl.budget) - sum(pl.procurementMoney)),2) from t_plan pl where pl.reportCompId = uc.id  AND pl.planStatus <> '06' and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null  ) jz," +
					"(select ROUND(sum(pl.procurementPrice * pl.contractNum),2) from t_plan pl,t_procurementcontract pc  where uc.id in (SUBSTRING(pc.reportCompId_pc,2,LENGTH(pc.reportCompId_pc)-1))  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.pid = pc.id and pc.signingDate between '"+sDate+"' and '"+eDate+"'  ) contractMoney," +
					"(select count(sc.id) from t_salescontract sc where uc.id = sc.reportCompId_sc and sc.signingDate between '"+sDate+"' and '"+eDate+"'  ) salesCount," +
					"(select ROUND(sum(sc.contractMoney),2) from t_salescontract sc where uc.id = sc.reportCompId_sc and sc.signingDate between '"+sDate+"' and '"+eDate+"'  ) salesMoney," +
					"(select ROUND(sum(pls.procurementPrice * pls.arrivalNumTotal),2)  from t_plan pls,t_arrivalitems ai  where pls.reportCompId = uc.id  AND pls.planStatus <> '06' and ai.aid = pls.id and ai.arrivalDate between '"+sDate+"' and '"+eDate+"' ) arrivalMoney," +
					"(select ROUND(sum(sc.totalcontractReceivedMoney),2) from t_salescontract sc where uc.id = sc.reportCompId_sc and sc.signingDate between '"+sDate+"' and '"+eDate+"'   ) totalcontractReceivedMoney," +
					"(select count(pc.id) from t_procurementcontract pc where uc.id in (SUBSTRING(pc.reportCompId_pc,2,LENGTH(pc.reportCompId_pc)-1)) and pc.signingDate between '"+sDate+"' and '"+eDate+"'  ) contractCount " +
					"from t_usecomp uc " 
					;
		 countSql = "select count(s.id) from (" +
					"select uc.id  from t_usecomp uc " ;
		}else{
			sql = "select uc.id reportCompId,uc.name reportComp," +
				"(SELECT ROUND(sum(pl.budget), 2) FROM t_plan pl WHERE pl.reportCompId = uc.id AND pl.planStatus <> '06' AND pl.planReceiptDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null  and pl.oid = "+orgId+"  ) budget,"+
				"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.procurementWay = '1'  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null and pl.oid = "+orgId+" ) apm," +
				"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.procurementWay = '2'  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null and pl.oid = "+orgId+" ) bpm," +
				"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.procurementWay = '3'  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null and pl.oid = "+orgId+" ) cpm," +
				"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.procurementWay = '4'  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null and pl.oid = "+orgId+" ) dpm," +
				"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.procurementWay = '5'  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null and pl.oid = "+orgId+" ) epm," +
				"(select ROUND(sum(pl.procurementMoney),2) from t_plan pl where pl.reportCompId = uc.id  AND pl.planStatus <> '06' and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null  and pl.oid ="+orgId+"  ) procurementMoney," +
				"(select ROUND((sum(pl.budget) - sum(pl.procurementMoney)),2) from t_plan pl where pl.reportCompId = uc.id  AND pl.planStatus <> '06' and pl.searchDate between '"+sDate+"' and '"+eDate+"' and pl.pcid is null  and pl.oid ="+orgId+" ) jz," +
				"(select ROUND(sum(pl.procurementPrice * pl.contractNum),2) from t_plan pl,t_procurementcontract pc  where uc.id in (SUBSTRING(pc.reportCompId_pc,2,LENGTH(pc.reportCompId_pc)-1))  AND pl.planStatus <> '06' and pl.reportCompId = uc.id and pl.pid = pc.id and pc.signingDate between '"+sDate+"' and '"+eDate+"'  and pc.orgId ="+orgId+"  ) contractMoney," +
				"(select count(sc.id) from t_salescontract sc where uc.id = sc.reportCompId_sc and sc.signingDate between '"+sDate+"' and '"+eDate+"' and sc.orgId ="+orgId+" ) salesCount," +
				"(select ROUND(sum(sc.contractMoney),2) from t_salescontract sc where uc.id = sc.reportCompId_sc and sc.signingDate between '"+sDate+"' and '"+eDate+"' and sc.orgId ="+orgId+" ) salesMoney," +
				"(select ROUND(sum(pls.procurementPrice * pls.arrivalNumTotal),2)  from t_plan pls,t_arrivalitems ai  where pls.reportCompId = uc.id  AND pls.planStatus <> '06' and ai.aid = pls.id and ai.arrivalDate between '"+sDate+"' and '"+eDate+"' and pls.oid ="+orgId+" ) arrivalMoney," +
				"(select ROUND(sum(sc.totalcontractReceivedMoney),2) from t_salescontract sc where uc.id = sc.reportCompId_sc and sc.signingDate between '"+sDate+"' and '"+eDate+"'  and sc.orgId ="+orgId+" ) totalcontractReceivedMoney," +
				"(select count(pc.id) from t_procurementcontract pc where uc.id in (SUBSTRING(pc.reportCompId_pc,2,LENGTH(pc.reportCompId_pc)-1)) and pc.signingDate between '"+sDate+"' and '"+eDate+"' and pc.orgId ="+orgId+" ) contractCount " +
				"from t_usecomp uc " 
				;
			countSql = "select count(s.id) from (" +
					"select uc.id  from t_usecomp uc ";
		}
		String lastSql = "   group by uc.id";
		String clastSql = "   group by uc.id) s ";
		if(useCompId!=0){
			lastSql="  where uc.id="+useCompId+" group by uc.id";
			clastSql="  where  uc.id="+useCompId+" group by uc.id) s ";
		}
		sql = this.getParamsSql(sql, params, lastSql);
		countSql = this.getParamsSql(countSql, params, clastSql);
		Query q = this.getSession().createSQLQuery(sql)
				.addScalar("reportCompId", Hibernate.INTEGER)
				.addScalar("reportComp", Hibernate.STRING)
				.addScalar("budget", Hibernate.DOUBLE)
				.addScalar("apm", Hibernate.DOUBLE)
				.addScalar("bpm", Hibernate.DOUBLE)
				.addScalar("cpm", Hibernate.DOUBLE)
				.addScalar("dpm", Hibernate.DOUBLE)
				.addScalar("epm", Hibernate.DOUBLE)
				.addScalar("procurementMoney", Hibernate.DOUBLE)
				.addScalar("jz", Hibernate.DOUBLE)
				.addScalar("contractMoney", Hibernate.DOUBLE)
				.addScalar("salesCount", Hibernate.INTEGER)
				.addScalar("salesMoney", Hibernate.DOUBLE)
				.addScalar("arrivalMoney", Hibernate.DOUBLE)
				.addScalar("totalcontractReceivedMoney", Hibernate.DOUBLE)
//				.addScalar("name", Hibernate.STRING)
				.addScalar("contractCount", Hibernate.STRING);
				
		if(isLimit){
			q.setFirstResult(offset);
			q.setMaxResults(pageSize);
		}
		BigInteger o = (BigInteger)this.getSession().createSQLQuery(countSql).uniqueResult();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		@SuppressWarnings("unchecked")
		List<Object[]> l = q.list();
		List<ReportCompStatisticsBean> list = new ArrayList<ReportCompStatisticsBean>();
		for(int i=0;i<l.size();i++){
			
			ReportCompStatisticsBean pcb = new ReportCompStatisticsBean();
			pcb.setNumber(offset+1+i);
			pcb.setReportComp(Integer.parseInt( (l.get(i)[0]).toString()   ));
			pcb.setReportCompName((l.get(i)[1]).toString());
			pcb.setBudget(Double.parseDouble( (l.get(i)[2] == null ? 0 : l.get(i)[2]).toString()   ));
			pcb.setApm(Double.parseDouble( (l.get(i)[3] == null ? 0 : l.get(i)[3]).toString()   ));
			pcb.setBpm(Double.parseDouble( (l.get(i)[4] == null ? 0 : l.get(i)[4]).toString()   ));
			pcb.setCpm(Double.parseDouble( (l.get(i)[5] == null ? 0 : l.get(i)[5]).toString()   ));
			pcb.setDpm(Double.parseDouble( (l.get(i)[6] == null ? 0 : l.get(i)[6]).toString()   ));
			pcb.setEpm(Double.parseDouble( (l.get(i)[7] == null ? 0 : l.get(i)[7]).toString()   ));
			pcb.setProcurementMoney(Double.parseDouble( (l.get(i)[8] == null ? 0 : l.get(i)[8]).toString()   ));
			Double jz  = Double.parseDouble( (l.get(i)[9] == null ? 0 : l.get(i)[9]).toString()   );
			pcb.setJz(jz < 0 ? 0 : jz);
			pcb.setContractMoney(Double.parseDouble( (l.get(i)[10] == null ? 0 : l.get(i)[10]).toString()   ));
			pcb.setSalesCount(Integer.parseInt( (l.get(i)[11] == null ? 0 : l.get(i)[11]).toString()   ));
			pcb.setSalesMoney(Double.parseDouble( (l.get(i)[12] == null ? 0 : l.get(i)[12]).toString()   ));
			pcb.setArrivalMoney(Double.parseDouble( (l.get(i)[13] == null ? 0 : l.get(i)[13]).toString()   ));
			pcb.setTotalcontractReceivedMoney(Double.parseDouble( (l.get(i)[14] == null ? 0 : l.get(i)[14]).toString()   ));
			
//			if(orgId == 0){
//				pcb.setOrgName("");
//			}else{
//				pcb.setOrgName((l.get(i)[15]) == null ? "" : (l.get(i)[15]).toString());
//			}
			pcb.setContractCount(Integer.parseInt( (l.get(i)[15] == null ? 0 : l.get(i)[15]).toString()   ));
			list.add(pcb);
		}
		
		pageModel.setList(list);
		pageModel.setTotal(o==null?0:o.intValue());
		return pageModel;
	}
	private String getParamsSql(String sql,Map params,String lastSql){
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if(!params.isEmpty()){
			sb.append(" where 1=1 ");
			Iterator it = params.entrySet().iterator();
			Map.Entry pa = null;
			while(it.hasNext()){
				pa = (Entry) it.next();
				String temp = pa.getKey().toString();
				if(temp.startsWith("id_")){
					sb.append(" and  p."+temp.substring(3)+" = "+pa.getValue());
				}else if(temp.startsWith("eq_")){
					sb.append(" and  p."+temp.substring(3)+" = '"+pa.getValue()+"' ");
				}else if(temp.startsWith("in_")){
					sb.append(" and  p."+temp.substring(3)+" in ("+pa.getValue()+") ");
				}else if(temp.startsWith("dy_")){
					sb.append(" and  "+temp.substring(3)+" "+pa.getValue()+" ");
				}else if(temp.startsWith("no_")){
					sb.append(" and  p."+temp.substring(3)+" <> "+pa.getValue()+" ");
				}else if(temp.startsWith("or_")){
					sb.append(" or  p."+temp.substring(3)+" "+pa.getValue()+" ");
				}else if(temp.startsWith("obj_")){
					User u = (User)params.get("obj_user");
					if(u!=null){
						sb.append(" and p.person.id in ("+u.getAuth()+") ");
					}
					Person ps = (Person)params.get("obj_org");
					if(ps!=null){
						sb.append(" and p.org.id = "+ps.getOrg().getId()+" ");
						//sql += " and p.person.id in ("+u.getAuth()+")";
					}
				}else{
					if(pa.getValue()!=null && !pa.getValue().equals(""))
						sb.append(" and  p."+pa.getKey()+" like '%"+pa.getValue()+"%' ");
					//sql += " and  p."+pa.getKey()+" like '%"+pa.getValue()+"%'";
				}
			}
			
		}
		if(lastSql!=null && !"".equals(lastSql)){
			sb.append(lastSql);
		}
		return sb.toString();
	}
	public void modifyPlansByIds(Plan plan) {
		Plan p=this.findPlan(plan.getId());
		if(p!=null){
			if(plan.getPerson() !=null)
				p.setPerson(plan.getPerson());
			if(plan.getUser()!=null)
				p.setUser(plan.getUser());
			this.getHibernateTemplate().update(p);
		}
	}
	public void modifyPlanByObj(Plan p){
		this.getHibernateTemplate().update(p);
	}
	public void modifyPlansByIdsBack(String ids) {
		this.getSession().createSQLQuery("update t_plan p set p.uid = null,p.psid = null,p.planStatus='01',p.procurementWay=null,p.procurementDate=null,p.procurementRemark='',p.procurementSigningleDate=null,p.searchAnnouncedDate=null,p.searchDate=null,p.contractExecutionWay=null,p.procurementPrice=null,p.procurementMoney=null  where p.id in ("+ids+")").executeUpdate();
	}
	public void modifycancelPlans(String ids) {
		this.getSession().createSQLQuery("update t_plan p set p.planStatus='06',p.procurementFlag='02'  where p.id in ("+ids+")").executeUpdate();
	}
	public void deletePlansByIds(String ids) {
		this.getSession().createSQLQuery("delete from t_plan where id in ("+ids+")").executeUpdate();
	}
	public void modifyredoPlans(String ids) {
		this.getSession().createSQLQuery("update t_plan p set p.planStatus='01',p.procurementFlag='01'  where p.id in ("+ids+")").executeUpdate();
	}
	public void modifyPlansOrg(String ids,int orgId) {
		this.getSession().createSQLQuery("update t_plan p set p.oid = "+orgId+"  where p.id in ("+ids+")").executeUpdate();
	}
	public String validationToAddContract(String tags){
		String msg = "";
		String[] str = tags.split(",");
		Integer[] ia=new Integer[str.length];
		for(int i=0;i<str.length;i++){
		   ia[i]=Integer.parseInt(str[i]);
		}
		List<Plan> plist = this.getPlans(ia);
		
		if(plist.size() > 1){
			for(int i=1;i<plist.size() ;i++){
//				System.out.println(plist.get(i-1).getProcurementWay());
//				System.out.println(plist.get(i).getProcurementWay());
//				System.out.println(plist.get(i-1).getContractExecutionWay());
//				System.out.println(plist.get(i).getContractExecutionWay());
//				if(!plist.get(i).getProcurementWay().equals(plist.get(i-1).getProcurementWay())){
//					msg = "请选择相同采购方式的计划";
//					break;
//				}
				if(!plist.get(i).getContractExecutionWay().equals(plist.get(i-1).getContractExecutionWay())){
					msg = "请选择相同合同执行方式的计划";
					break;
				}
			}
		}
		return msg;
	}
	public String validationToAddSalesContract(String tags){
		String msg = "";
		String[] str = tags.split(",");
		Integer[] ia=new Integer[str.length];
		for(int i=0;i<str.length;i++){
		   ia[i]=Integer.parseInt(str[i]);
		}
		List<Plan> plist = this.getPlans(ia);
		if(plist.size() > 1){
			for(int i=1;i<plist.size() ;i++){
				if(!plist.get(i).getReportCompId().equals(plist.get(i-1).getReportCompId())){
					msg = "请选择相同计划提报单位";
					break;
				}
			}
		}
		return msg;
	}
	public String validationToAddSeekSource(String tags,String flag){
		String msg = "";
		String[] str = tags.split(",");
		Integer[] ia=new Integer[str.length];
		for(int i=0;i<str.length;i++){
		   ia[i]=Integer.parseInt(str[i]);
		}
		List<Plan> plist = this.getPlans(ia);
		if(plist.size() > 1){
			if(flag != null && flag.equals("toAddSearchDate")){
				for(int i=1;i<plist.size() ;i++){
					if(plist.get(i).getSearchAnnouncedDate()==null || plist.get(i).getSearchAnnouncedDate().equals("")){
						msg = "寻源公布日期为空，不能添加寻源结果审批通过日期";
						break;
					}
					if(plist.get(i).getContractExecutionWay()==null || plist.get(i).getContractExecutionWay().equals("") || plist.get(i).getContractExecutionWay().equals("0")){
						msg = "合同执行方式为空，不能添加寻源结果审批通过日期";
						break;
					}
					if(plist.get(i).getProcurementPrice()==null || plist.get(i).getProcurementPrice() < 0){
						msg = "寻源金额为空，不能添加寻源结果审批通过日期";
						break;
					}
				}
			}else if(flag != null && flag.equals("toAddContractExcutionWay")){
				for(int i=1;i<plist.size() ;i++){
					if(plist.get(i).getSearchAnnouncedDate()==null || plist.get(i).getSearchAnnouncedDate().equals("")){
						msg = "寻源公布日期为空，不能添加合同执行方式";
						break;
					}
				}
			}else if(flag != null && flag.equals("toAddPrice")){
				for(int i=1;i<plist.size() ;i++){
					if(plist.get(i).getSearchAnnouncedDate()==null || plist.get(i).getSearchAnnouncedDate().equals("")){
						msg = "寻源公布日期为空，不能添加寻源单价";
						break;
					}
				}
			}
		}
		return msg;
	}
}
