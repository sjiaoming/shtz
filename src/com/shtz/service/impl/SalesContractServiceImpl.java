package com.shtz.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shtz.model.Billing;
import com.shtz.model.BillingDetail;
import com.shtz.model.ContractCredit;
import com.shtz.model.Organization;
import com.shtz.model.Payed;
import com.shtz.model.Person;
import com.shtz.model.Plan;
import com.shtz.model.ProcurementContract;
import com.shtz.model.SalesContract;
import com.shtz.model.SalesContractArrivalBean;
import com.shtz.model.UseComp;
import com.shtz.model.User;
import com.shtz.service.PageService;
import com.shtz.service.PlanService;
import com.shtz.service.SalesContractService;
import com.shtz.util.Arith;
import com.shtz.util.PageModel;

public class SalesContractServiceImpl extends HibernateDaoSupport implements SalesContractService {
	private PageService service;
	
	private PlanService  pservice;
	
	private PageModel pageModel;
	
	private Arith arithService;
	public void setArithService(Arith arithService) {
		this.arithService = arithService;
	}
	public void setPservice(PlanService pservice) {
		this.pservice = pservice;
	}
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	public void setService(PageService service) {
		this.service = service;
	}

	public PageModel searchPlans(int offset, int pageSize) {
		return service.getPageModel("from Plan p where p.procurementContract is not null and p.salesContract is null", offset, pageSize);
	}
	public String getMaxContractNum(String head){
		Object q = this.getSession().createQuery("select max(t.contractNum) from SalesContract t where t.contractNum like '"+head.trim()+"%'").uniqueResult();
		if(q != null){
			return (String)q;
		}
		return "" ;
	}

	public List<Plan> findCheckedPlan(String[] tags){
		List<Plan> planList=new ArrayList<Plan>();
		int id = 0;
		for(String temp : tags){
			Plan p = new Plan();
			id = Integer.parseInt(temp);
			p=pservice.findPlan(id);
			planList.add(p);
		}
		return planList;
	}

	public List<UseComp> findAllUseComp(){
		List<UseComp> useCompList=this.getSession().createQuery("from UseComp").list();
		return useCompList;
	}

	public void modifyPlanSales(Plan p){
		Plan plan=(Plan) this.getHibernateTemplate().load(Plan.class, p.getId());
		plan.setSalesMoney(p.getSalesMoney());
		plan.setSalesRatio(p.getSalesRatio());
		plan.setSalesPrice(arithService.div(p.getSalesMoney(), plan.getContractNum(), 2));
		//		plan.setSalesPrice(0.0);
		plan.setSalesContract(p.getSalesContract());
		plan.setPlanStatus("05");
		plan=(Plan) this.getHibernateTemplate().merge(plan);
		this.getHibernateTemplate().update(plan);
	}
	
	public Billing addBilling(double billingMoney,Date billingDate,SalesContract salesContract){
		Billing bill=new Billing();
		bill.setBillingMoney(billingMoney);
		bill.setBillingDate(billingDate);
		bill.setSalesContract(salesContract);
		this.getHibernateTemplate().save(bill);
		return bill;
	}
	public void modifyBilling(Billing bill){
		this.getHibernateTemplate().update(bill);
	}
	public Billing getBillingById(int id){
//		return (Billing)this.getHibernateTemplate().load(Billing.class, id);
		return (Billing)this.getHibernateTemplate().get(Billing.class, id);
	}
	public BillingDetail getBillingDetailById(int id){
		return (BillingDetail)this.getHibernateTemplate().get(BillingDetail.class, id);
	}
	public void deleteBillingDetail(int id){
		this.getHibernateTemplate().delete(this.getBillingDetailById(id));
	}
	public void addOrModifySalesContract(SalesContract salesContract,String[] addmoney,String[] addmoneyDate,String[] addmoney1,String[] payedStyle){
		if(salesContract.getId()==0){
			salesContract.setOrgName(this.findOrganization(salesContract.getOrgId()).getName());
			this.getHibernateTemplate().save(salesContract);
		}
		SimpleDateFormat smf=new SimpleDateFormat("yyyy-MM-dd");
		double temp=0;
		Date d=new Date();
		//添加开票信息
		for (int i = 0; i < addmoney.length; i++) {
			if(addmoney[i]!=null&&!"".equals(addmoney[i])){
				if(Double.parseDouble(addmoney[i])<=0){
					continue;
				}
				try {
					if(addmoneyDate[i]!=null && addmoneyDate[i]!=""){
						d=smf.parse(addmoneyDate[i]);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				this.addBilling(Double.parseDouble(addmoney[i]),d,salesContract);
				temp+=Double.parseDouble(addmoney[i]);
			}
		}
		//String temp1="";
		StringBuffer sb = new StringBuffer();
		double total=0;
		for (int i = 0; i < addmoney1.length; i++) {
			if(addmoney1[i]!=null&&!"".equals(addmoney1[i])){
				if(Double.parseDouble(addmoney1[i])==0){
					continue;
				}
				if(i==0){
					//temp1+=addmoney1[i];
					 sb.append(addmoney1[i]+"&"+payedStyle[i]);
				}else{
					//temp1=temp1+","+addmoney1[i];
					 sb.append(','+addmoney1[i]+"&"+payedStyle[i]);
				}
				total+=Double.parseDouble(addmoney1[i]);
				}
		}
		if(salesContract.getId()!=0){
			SalesContract s = findSalesContractById(salesContract.getId());
			s.setTotalContractInvoiceMoney(salesContract.getTotalContractInvoiceMoney());
			s.setContractReceivedMoney(sb.toString());
			s.setTotalcontractReceivedMoney(total);
			this.getHibernateTemplate().update(s);
		}
	}
	public void addOrModifySalesContract(SalesContract salesContract,String[] addmoney1,String[] payedStyle,String[] contractReceivedDate){
		if(salesContract.getId()==0){
			salesContract.setOrgName(this.findOrganization(salesContract.getOrgId()).getName());
			this.getHibernateTemplate().save(salesContract);
		}
		StringBuffer sb = new StringBuffer();
		double total=0;
		for (int i = 0; i < addmoney1.length; i++) {
			if(addmoney1[i]!=null&&!"".equals(addmoney1[i])){
				if(Double.parseDouble(addmoney1[i])==0){
					continue;
				}
				if(i==0){
					//temp1+=addmoney1[i];
					 sb.append(addmoney1[i]+"&"+payedStyle[i]+"&"+contractReceivedDate[i]);
				}else{
					//temp1=temp1+","+addmoney1[i];
					 sb.append(','+addmoney1[i]+"&"+payedStyle[i]+"&"+contractReceivedDate[i]);
				}
				total+=Double.parseDouble(addmoney1[i]);
				}
		}
		if(salesContract.getId()!=0){
			SalesContract s = findSalesContractById(salesContract.getId());
			s.setTotalContractInvoiceMoney(salesContract.getTotalContractInvoiceMoney());
			s.setContractReceivedMoney(sb.toString());
			s.setTotalcontractReceivedMoney(total);
			this.getHibernateTemplate().update(s);
		}
	}
	public SalesContract addSalesContract(SalesContract salesContract){
			
		if(salesContract.getId()==0){
			salesContract.setOrgName(this.findOrganization(salesContract.getOrgId()).getName());
			this.getHibernateTemplate().save(salesContract);
		}else{
			this.modifySalesContract(salesContract);
		}
		return salesContract;
	}
	public Organization findOrganization(int organizationId) {
		String hql="from Organization o where o.id=?";
		return (Organization) this.getSession().createQuery(hql).setParameter(0, organizationId).uniqueResult();
	}
	public SalesContract findSalesContractByContractNum(String contractNum){
		String hql="from SalesContract sc where sc.contractNum=?";
		//SalesContract sc= (SalesContract) this.getSession().createQuery(hql).setParameter(0,contractNum).uniqueResult();
		return (SalesContract) this.getSession().createQuery(hql).setParameter(0,contractNum).uniqueResult();
	}
	public List<Organization> findAllOrganization() {
		return (List<Organization>)this.getHibernateTemplate().loadAll(Organization.class);
	}

	public PageModel searchsalesContracts(int offset, int pageSize) {
		return service.getPageModel("from SalesContract", offset, pageSize);
	}
	public List<Plan> getPlans(int salesContractId) {
		String hql="from Plan p where p.salesContract.id=?";
		List<Plan> plans=this.getSession().createQuery(hql).setParameter(0,salesContractId).list();
		return plans;
	}
	public void modifyplansid(int planid) {
		Plan p=(Plan) this.getHibernateTemplate().load(Plan.class, planid);
		p.setSalesContract(null);
		p=(Plan) this.getHibernateTemplate().merge(p);
		this.getHibernateTemplate().update(p);
	}
	public SalesContract findSalesContractById(int salesContractId) {
		return (SalesContract) this.getHibernateTemplate().load(SalesContract.class, salesContractId);
	}
	public void addBillingDetail(BillingDetail b){
		this.getHibernateTemplate().save(b);
	}
	public void deleteSalesContract(int salesContractId) {
		SalesContract sc = this.findSalesContractById(salesContractId);
		List<Billing> b = this.findAllBillingBySalesContract(sc);
		List<Plan> plans=this.getPlans(salesContractId);
		if(b==null || b.size()==0){
			 try {
				 if(sc!=null){
					 for(Plan p : plans){
						 p.setSalesContract(null);
						 p.setPlanStatus("04");
						 p.setSalesRatio(null);
						 p.setSalesMoney(null);
						this.getHibernateTemplate().update(p);
					 }
				 }
				this.getHibernateTemplate().delete(sc);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		 }else{
			 throw new RuntimeException("haveOthers");
		 }
	}
	
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
				}else if(temp.startsWith("dy_")){
					hql += " and  p."+temp.substring(3)+" "+pa.getValue()+" ";
				}else if(temp.startsWith("obj_")){
					
					User u = (User)params.get("obj_user");
					if(u!=null)
						hql += " and p.person.id in ("+u.getAuth()+")";
				}else{
					hql += " and  p."+pa.getKey()+" like '%"+pa.getValue()+"%'";
				}
			}
		}
		PageModel pm=service.getPageModel(hql+" and  p.salesContract is null", offset, pageSize);
		
		String hql1="select count(p.id) as totalCount,sum(p.contractNum) as contractNum,sum(p.procurementMoney) as totalMoney from Plan p  where 1=1 ";
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
				}else if(temp.startsWith("dy_")){
					hql1 += " and  p."+temp.substring(3)+" "+pa.getValue()+" ";
				}else if(temp.startsWith("obj_")){
					
					User u = (User)params.get("obj_user");
					if(u!=null)
						hql1 += " and p.person.id in ("+u.getAuth()+")";
				}else{
					hql1 += " and  p."+pa.getKey()+" like '%"+pa.getValue()+"%'";
				}
			}
		}
		Object[] sumstr=(Object[]) this.getSession().createQuery(hql1+" and  p.salesContract is null").uniqueResult();
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
		return pm;
	}
	public PageModel serchsearchPlansByParams1(Map params,int offset, int pageSize){
		String hql = "select sc from Plan p,SalesContract sc where p.salesContract=sc  ";
		if(!params.isEmpty()){
			Iterator it = params.entrySet().iterator();
			Map.Entry pa = null;
			while(it.hasNext()){
				pa = (Entry) it.next();
				String temp = pa.getKey().toString();
				if(temp.startsWith("id_")){
					hql += " and  sc."+temp.substring(3)+" = "+pa.getValue();
				}else if(temp.startsWith("eq_")){
					hql += " and  p."+temp.substring(3)+" = '"+pa.getValue()+"'";
				}else if(temp.startsWith("in_")){
					hql += " and  p."+temp.substring(3)+" in ("+pa.getValue()+")";
				}else{
					hql += " and  sc."+pa.getKey()+" like '%"+pa.getValue()+"%'";
				}
			}
		}
		
		
		
		return service.getPageModel(hql, offset, pageSize);
	}
	public PageModel serchsearchSalesByParams(Map params,int offset, int pageSize){
		String hql = "from SalesContract p where 1=1 ";
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

	public List<Billing> findAllBillingBySalesContract(SalesContract sc) {
		String hql="from Billing b where b.salesContract=?";
		List<Billing> billingList =this.getSession().createQuery(hql).setParameter(0, sc).list();
		if(billingList==null){
			return null;
		}
		return billingList;
	}
	
	public void deleteBilling(int id) {
		SalesContract p = ((Billing)this.getHibernateTemplate().load(Billing.class, id)).getSalesContract();
		Billing b = (Billing)this.getHibernateTemplate().load(Billing.class, id);
		Double temp = 0.0;
		if(p.getContractReceivedMoney()!=null&&!"".equals(p.getContractReceivedMoney())){
			String a = "&";
			String[] str=p.getContractReceivedMoney().split(",");
			for (int i = 0; i < str.length; i++) {
				if(str[i]!=null && !str[i].equals("") && str[i].indexOf(a)>0){
					String[] temps = str[i].split(a);
					if(temps.length == 2){
						temp += Double.parseDouble(temps[0]);
					}
				}
			}
		}
		Double dds = 0.0;
		Double tcim = 0.0;
		if(p.getTotalContractInvoiceMoney()!=null&&!"".equals(p.getTotalContractInvoiceMoney())){
			tcim = p.getTotalContractInvoiceMoney() - b.getBillingMoney();
			dds = (tcim > 0 ? tcim: 0) - temp;
		}
		p.setTotalContractInvoiceMoney(tcim > 0 ? tcim: 0);
		p.setContractReceivableMoney(dds > 0 ? dds : 0);
		this.getHibernateTemplate().delete(b);
		this.getHibernateTemplate().update(p);
	}

	public void modifySalesContract(SalesContract salesContract) {
		this.getHibernateTemplate().update(salesContract);
	}
	public List<?> findSalesContractListByContractNum(String contractNum){
		return this.getSession().createQuery("from SalesContract p where p.contractNum = ?").setParameter(0, contractNum).list();
	}
	public PageModel getSalesContractList(int offset,int pageSize,Map params,boolean isLimit){
		String sql="select p.*,sum(a.arrivalNum * pl.salesPrice) as arrivalMoney ,sum(a.acceptanceNum * pl.salesPrice) as acceptanceMoney,pl.reportcomp from t_salescontract p " +
				"left join t_plan pl on pl.sid = p.id " +
				"left join t_arrivalitems a on a.aid = pl.id " +
				"left join t_person ps on ps.id = p.psid  ";
		String countSql = "select count(s.pid) from (select p.id as pid from t_salescontract p " +
				"left join t_plan pl on pl.sid = p.id " +
				"left join t_arrivalitems a on a.aid = pl.id  " +
				"left join t_person ps on ps.id = p.psid  ";
		String sumsql="select count(s.pid),sum(s.double1),sum(s.double2),sum(s.double3),sum(s.double4),sum(s.double5),sum(s.double6),sum(s.double7) from (select p.id pid,p.contractMoney double1,p.qualityMoney double2,p.totalContractInvoiceMoney double3,p.totalcontractReceivedMoney double4,p.contractReceivableMoney double5," +
				"sum(a.arrivalNum * pl.procurementPrice) double6,sum(a.acceptanceNum * pl.procurementPrice) double7  from  t_salescontract p " +
				"left join t_plan pl on pl.sid = p.id " +
				"left join t_arrivalitems a on a.aid = pl.id  " +
				"left join t_person ps on ps.id = p.psid  ";
		String lastSql = "  group by p.id order by p.id desc";
		sql = this.getParamsSql(sql, params, lastSql);
		String clastSql = " group by p.id) s";
		String slastSql = " group by p.id) s";
		countSql = this.getParamsSql(countSql, params, clastSql);
		sumsql= this.getParamsSql(sumsql, params, slastSql);
		Query q = this.getSession().createSQLQuery(sql)
				.addEntity(SalesContract.class)
				.addScalar("arrivalMoney", Hibernate.DOUBLE)
				.addScalar("acceptanceMoney", Hibernate.DOUBLE)
				.addScalar("reportcomp", Hibernate.STRING);
		
		if(isLimit){
			q.setFirstResult(offset);
			q.setMaxResults(pageSize);
		}
		BigInteger o = (BigInteger)this.getSession().createSQLQuery(countSql).uniqueResult();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		@SuppressWarnings("unchecked")
		List<Object[]> l = q.list();
		List<SalesContractArrivalBean> list = new ArrayList<SalesContractArrivalBean>();
		for(int i=0;i<l.size();i++){
			SalesContractArrivalBean pcb = new SalesContractArrivalBean();
			
			pcb.setId(((SalesContract)l.get(i)[0]).getId());
			pcb.setContractNum(((SalesContract)l.get(i)[0]).getContractNum());
			pcb.setSigningDate(((SalesContract)l.get(i)[0]).getSigningDate());
			pcb.setReportComp(((SalesContract)l.get(i)[0]).getReportCompId_sc());
			pcb.setSalesContractName(((SalesContract)l.get(i)[0]).getSalesContractName());
			pcb.setSignComp(((SalesContract)l.get(i)[0]).getSignComp());
			pcb.setContractMoney(((SalesContract)l.get(i)[0]).getContractMoney());
			pcb.setQualityMoney(((SalesContract)l.get(i)[0]).getQualityMoney());
			pcb.setQualityDate(((SalesContract)l.get(i)[0]).getQualityDate());
			pcb.setTotalContractInvoiceMoney(((SalesContract)l.get(i)[0]).getTotalContractInvoiceMoney());
			pcb.setTotalcontractReceivedMoney(((SalesContract)l.get(i)[0]).getTotalcontractReceivedMoney());
			pcb.setContractReceivableMoney(((SalesContract)l.get(i)[0]).getContractReceivableMoney());
			pcb.setUser(((SalesContract)l.get(i)[0]).getUser());
			pcb.setPerson(((SalesContract)l.get(i)[0]).getPerson());
			pcb.setReportCompName(((SalesContract)l.get(i)[0]).getReportCompName_sc());
			pcb.setArrivalAddress(((SalesContract)l.get(i)[0]).getArrivalAddress());
			if(l.get(i)[1]!=null){
				pcb.setArrivalMoney(Double.parseDouble( (l.get(i)[1]).toString() ));
			}else{
				pcb.setArrivalMoney(0.0);
			}
			if(l.get(i)[2]!=null){
				pcb.setAcceptanceMoney(Double.parseDouble((l.get(i)[2]).toString() ));
			}else{
				pcb.setAcceptanceMoney(0.0);
			}
			
			list.add(pcb);
		}
		pageModel.setList(list);
		pageModel.setTotal(o==null?0:o.intValue());
		Object[] sumq = (Object[]) this.getSession().createSQLQuery(sumsql).uniqueResult();
		pageModel.setTotal1(Integer.parseInt(sumq[0].toString()));
		if(sumq[1]!=null){
			pageModel.setDouble1(Double.parseDouble(sumq[1].toString()));
		}else{
			pageModel.setDouble1(0.0);
		}
		if(sumq[2]!=null){
			pageModel.setDouble2(Double.parseDouble(sumq[2].toString()));	
		}else{
			pageModel.setDouble2(0.0);
		}
		if(sumq[3]!=null){
			pageModel.setDouble3(Double.parseDouble(sumq[3].toString()));
		}else{
			pageModel.setDouble3(0.0);
		}
		if(sumq[4]!=null){
			pageModel.setDouble4(Double.parseDouble(sumq[4].toString()));
		}else{
			pageModel.setDouble4(0.0);
		}
		if(sumq[5]!=null){
			pageModel.setDouble5(Double.parseDouble(sumq[5].toString()));
		}else{
			pageModel.setDouble5(0.0);
		}
		if(sumq[6]!=null){
			pageModel.setDouble6(Double.parseDouble(sumq[6].toString()));
		}else{
			pageModel.setDouble6(0.0);
		}
		if(sumq[7]!=null){
			pageModel.setDouble7(Double.parseDouble(sumq[7].toString()));//到货数量
		}else{
			pageModel.setDouble7(0.0);
		}
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
					//sql += " and  p."+temp.substring(3)+" = "+pa.getValue();
				}else if(temp.startsWith("eq_")){
					sb.append(" and  p."+temp.substring(3)+" = '"+pa.getValue()+"' ");
					//sql += " and  p."+temp.substring(3)+" = '"+pa.getValue()+"'";
				}else if(temp.startsWith("in_")){
					sb.append(" and  p."+temp.substring(3)+" in ("+pa.getValue()+") ");
					//sql += " and  p."+temp.substring(3)+" in ("+pa.getValue()+")";
				}else if(temp.startsWith("dy_")){
					sb.append(" and  "+temp.substring(3)+" "+pa.getValue()+" ");
					//sql += " and  p."+temp.substring(3)+" "+pa.getValue()+" ";
				}else if(temp.startsWith("obj_")){
					User u = (User)params.get("obj_user");
					if(u!=null){
						sb.append(" and p.psid in ("+u.getAuth()+") ");
						//sql += " and p.person.id in ("+u.getAuth()+")";
					}
					Person ps = (Person)params.get("obj_org");
					if(ps!=null){
						sb.append(" and p.orgid = "+ps.getOrg().getId()+" ");
						//sql += " and p.person.id in ("+u.getAuth()+")";
					}
				}else{
					sb.append(" and  p."+pa.getKey()+" like '%"+pa.getValue()+"%' ");
					//sql += " and  p."+pa.getKey()+" like '%"+pa.getValue()+"%'";
				}
			}
			
		}
		if(lastSql!=null && !"".equals(lastSql)){
			sb.append(lastSql);
			//sql += lastSql;
		}
		return sb.toString();
	}
}
