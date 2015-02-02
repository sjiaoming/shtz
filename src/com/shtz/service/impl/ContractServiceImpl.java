package com.shtz.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shtz.model.ContractCredit;
import com.shtz.model.Organization;
import com.shtz.model.Person;
import com.shtz.model.Plan;
import com.shtz.model.ProcurementArrivalBean;
import com.shtz.model.ProcurementContract;
import com.shtz.model.Suppliers;
import com.shtz.model.User;
import com.shtz.service.ContractService;
import com.shtz.service.PageService;
import com.shtz.service.PlanService;
import com.shtz.util.Arith;
import com.shtz.util.PageModel;

public class ContractServiceImpl extends HibernateDaoSupport implements ContractService {
	private PageService service;
	private PageModel pageModel;
	private PlanService  pservice;
	private Arith arithService;
	public void setArithService(Arith arithService){
		this.arithService = arithService;
	}
	public void setPservice(PlanService pservice) {
		this.pservice = pservice;
	}

	public void setService(PageService service) {
		this.service = service;
	}
	

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

	public PageModel searchContracts(int offset, int pageSize) {
		return service.getPageModel("from ProcurementContract", offset, pageSize);
	}
	public PageModel searchContracts(Map params,int offset, int pageSize){
		String hql = "from ProcurementContract p where 1=1 ";
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
	public PageModel searchContracts1(Map params,int offset, int pageSize){
		String hql = "select new com.shtz.model.ProcurementContractBean(p.id,p.contractNum,p.contractExecutionWay,p.orgName,p.totalMoney,p.procurementWay,p.payed,sum(pl.procurementPrice * IFNULL(a.arrivalNum,0)) as arrivalTotalMoney,sum(pl.procurementPrice * IFNULL(a.acceptanceNum,0)) as acceptanceTotalMoney)" +
				"from ProcurementContract p,Plan pl " +
				"left join pl.arrivalItems a  where pl.arrivalItems.id = a.id and p.plan.id = p.id";
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
						hql += " and p.user.id in ("+u.getAuth()+")";
				}else{
					hql += " and  p."+pa.getKey()+" like '%"+pa.getValue()+"%'";
				}
			}
		}
		hql +=" group by p.contractNum";
		
		System.out.println(hql);
		
		
		
		
		
		
		return service.getPageModel(hql, offset, pageSize);
	}
	public double searchContractcreditMoney(int id) {
		String hql="select sum(contractCreditMoney) from ContractCredit c where c.procurementContract.id=?";
		Object tcm =this.getSession().createQuery(hql).setParameter(0, id).uniqueResult();
		if(tcm==null){
			return 0;
		}
		return Double.parseDouble(tcm.toString());
	}
	public List<Plan> getPlans(int procurementContractId) {
		String hql="from Plan p where p.procurementContract.id=?";
		List<Plan> plans=this.getSession().createQuery(hql).setParameter(0,procurementContractId).list();
		return plans;
	}
	public ProcurementContract findProcurementContractById(int procurementContractId) {
		return (ProcurementContract) this.getHibernateTemplate().load(ProcurementContract.class, procurementContractId);
	}
	public List<Organization> findAllOrganization() {
		return (List<Organization>)this.getHibernateTemplate().loadAll(Organization.class);
	}
	public List<Suppliers> findAllSuppliers(){
		return (List<Suppliers>)this.getHibernateTemplate().loadAll(Suppliers.class);
	}
	public List<ContractCredit> findContractCreditByPid(int pid){
		return (List<ContractCredit>)this.getSession().createQuery("from ContractCredit cc where cc.procurementContract.id="+pid).list();
	}
	public void deleteContractCredit(int id) {
		ProcurementContract p = ((ContractCredit)this.getHibernateTemplate().load(ContractCredit.class, id)).getProcurementContract();
		ContractCredit c = (ContractCredit)getHibernateTemplate().load(ContractCredit.class, id);
		p.setShouldPayment(Arith.sub(p.getShouldPayment(),c.getContractCreditMoney()));
		this.getHibernateTemplate().delete(c);
		this.getHibernateTemplate().update(p);
		
	}
	public void modifyProcurementContract(ProcurementContract pc){
		this.getHibernateTemplate().update(pc);
	}
	public void modifyplanpid(int planId) {
		Plan p=(Plan) this.getHibernateTemplate().load(Plan.class, planId);
		//p=(Plan) this.getHibernateTemplate().merge(p);
		ProcurementContract pc = p.getProcurementContract();
		double temptm = arithService.sub(pc.getTotalMoney(), p.getProcurementMoney());
		pc.setTotalMoney(temptm >0 ?temptm:0.0);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if(!pc.getPlan().isEmpty()){
//			int i=0;
			List<Integer> list = new ArrayList<Integer>();
			List<String> list1 = new ArrayList<String>();
			for(Plan pl : pc.getPlan()){
				if(pl.getId() != p.getId()){
					if(!list.contains(pl.getReportCompId())) {//如果数组 list 不包含当前项，则增加该项到数组中
						list.add(pl.getReportCompId());
					}
					if(!list1.contains(pl.getReportComp())) {//如果数组 list 不包含当前项，则增加该项到数组中
						list1.add(pl.getReportComp());
					}
					
//					sb.append(",");
//					sb.append(pl.getReportCompId());
//					if(i!=0)
//						sb1.append(",");
//					sb1.append(pl.getReportComp());
//					i++;
				}
			}
//			sb.append(",");
			if(list.size()>0 && list.size() == list1.size()){
				for(int i=0;i<list.size();i++){
					sb.append(",");
					sb.append(list.get(i));
					if(i!=0)
						sb1.append(",");
					sb1.append(list1.get(i));
				}
				sb.append(",");
			}
		}
		
		pc.setReportCompId_pc(sb.toString());
		pc.setReportCompName_pc(sb1.toString());
		this.getHibernateTemplate().update(pc);
		p.setProcurementContract(null);
		p.setPlanStatus("03");
		if(p.getProcurementWay()!=null && p.getProcurementWay().equals("6")){
			p.setPlanStatus("01");
			p.setProcurementDate(null);
			p.setProcurementMoney(0.0);
			p.setProcurementPrice(0.0);
			p.setProcurementSigningleDate(null);
			p.setContractNum(0.0);
		}
		this.getHibernateTemplate().update(p);
	}
	public void deleteProcurementContract(int pcontractId){
		
		 List<ContractCredit> cc=this.findContractCreditByPid(pcontractId);
		 List<Plan> pList=this.getPlans(pcontractId);
		 boolean y = true;
		 for(Plan p : pList){
			 if(p.getArrivalItems().size() > 0){
				 y = false;
			 }
		 }
		 if((cc==null || cc.size()==0) && y){
			 ProcurementContract p = (ProcurementContract)this.getHibernateTemplate().load(ProcurementContract.class, pcontractId);
			 try {
				 if(p!=null){
					 for(Plan ps : pList){
						 ps.setProcurementContract(null);
						 ps.setContractNum(0.0);
						 ps.setPlanStatus("03");
						this.getHibernateTemplate().update(p);
					 }
				 }
				this.getHibernateTemplate().delete(p);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		 }else{
			 throw new RuntimeException("haveContractCredit");
		 }
		
	}
	public void addProcurementContract(ProcurementContract pc){
		this.getHibernateTemplate().update(pc);
	}
	public void addOrUpdateContractCredit(ContractCredit cc,int ccid){
		ContractCredit CCreditthis=(ContractCredit)this.getHibernateTemplate().get(ContractCredit.class, ccid);
		if(CCreditthis==null){
			this.getHibernateTemplate().save(cc);
		}else{
			this.getHibernateTemplate().update(cc);
		}
	}
	public List<ContractCredit> findContractCreditById(int pid){
		 List<ContractCredit> contractCreditList=(List<ContractCredit>) this.getSession().createQuery("from ContractCredit cc where cc.procurementContract.id=?").setParameter(0, pid).list();
		return contractCreditList;
		
	}
	public ContractCredit findContractCreditByIds(int id){
		return (ContractCredit)this.getHibernateTemplate().load(ContractCredit.class, id);
	}
	public PageModel getProcurementList(int offset,int pageSize,Map params,boolean isLimit){
		String sql="select p.*,sum(a.arrivalNum * pl.procurementPrice) as arrivalMoney ,sum(a.acceptanceNum * pl.procurementPrice) as acceptanceMoney,ps.* from t_procurementcontract p " +
				"left join t_plan pl on pl.pid = p.id " +
				"left join t_arrivalitems a on a.aid = pl.id " +
				"left join t_person ps on ps.id = p.psid ";
		String countSql = "select count(s.pid) from (select p.id as pid from t_procurementcontract p " +
				"left join t_plan pl on pl.pid = p.id " +
				"left join t_arrivalitems a on a.aid = pl.id   " +
				"left join t_person ps on ps.id = p.psid ";
		String sumsql="select count(s.pid),sum(s.double1),sum(s.double2),sum(s.double3),sum(s.double4),sum(s.double5),sum(s.double6),sum(s.double7),sum(s.double8),sum(s.double9) " +
				"from (select p.id pid,p.totalMoney double1,p.advance double2,p.qualityMoney double3,p.payed double4,(select sum(cc.contractCreditMoney) from t_contractcredit cc where cc.cid = p.id) double5, " +
				"p.shouldPayment double6,sum(a.arrivalNum)  double7,sum(a.arrivalNum * pl.procurementPrice)  double8,sum(a.acceptanceNum * pl.procurementPrice)  double9  from t_procurementcontract p " +
				"left join t_plan pl on pl.pid = p.id " +
				"left join t_arrivalitems a on a.aid = pl.id " +
				"left join t_person ps on ps.id = p.psid ";
		String lastSql = "  group by p.id order by p.id desc";
		sql = this.getParamsSql(sql, params, lastSql);
		String clastSql = " group by p.id) s";
		countSql = this.getParamsSql(countSql, params, clastSql);
		sumsql= this.getParamsSql(sumsql, params, clastSql);
		Query q = this.getSession().createSQLQuery(sql)
				.addEntity(ProcurementContract.class)
				.addScalar("arrivalMoney", Hibernate.DOUBLE)
				.addScalar("acceptanceMoney", Hibernate.DOUBLE)
				.addEntity(Person.class);
		
		if(isLimit){
			q.setFirstResult(offset);
			q.setMaxResults(pageSize);
		}
		BigInteger o = (BigInteger)this.getSession().createSQLQuery(countSql).uniqueResult();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		@SuppressWarnings("unchecked")
		List<Object[]> l = q.list();
		List<ProcurementArrivalBean> list = new ArrayList<ProcurementArrivalBean>();
		for(int i=0;i<l.size();i++){
			ProcurementArrivalBean pcb = new ProcurementArrivalBean();
			
			pcb.setId(((ProcurementContract)l.get(i)[0]).getId());
			pcb.setContractName(((ProcurementContract)l.get(i)[0]).getContractName());
			pcb.setContractNum(((ProcurementContract)l.get(i)[0]).getContractNum());
			pcb.setContractExecutionWay(((ProcurementContract)l.get(i)[0]).getContractExecutionWay());
			pcb.setOrgName(((ProcurementContract)l.get(i)[0]).getOrgName());
			pcb.setTotalMoney(((ProcurementContract)l.get(i)[0]).getTotalMoney());
			pcb.setProcurementWay(((ProcurementContract)l.get(i)[0]).getProcurementWay());
			pcb.setPayed(((ProcurementContract)l.get(i)[0]).getPayed());
			pcb.setContractCredit(((ProcurementContract)l.get(i)[0]).getContractCredit());
			pcb.setShouldPayment(((ProcurementContract)l.get(i)[0]).getShouldPayment());
			pcb.setPerson(((Person)l.get(i)[3]));
			pcb.setAdvance(((ProcurementContract)l.get(i)[0]).getAdvance());
			pcb.setQualityMoney(((ProcurementContract)l.get(i)[0]).getQualityMoney());
			pcb.setArrivalDate(((ProcurementContract)l.get(i)[0]).getArrivalDate());
			pcb.setSuppliersName(((ProcurementContract)l.get(i)[0]).getSuppliersName());
			pcb.setSigningDate(((ProcurementContract)l.get(i)[0]).getSigningDate());
			pcb.setReportCompName(((ProcurementContract)l.get(i)[0]).getReportCompName_pc());
			pcb.setReportComp(((ProcurementContract)l.get(i)[0]).getReportCompId_pc());
			pcb.setRemark(((ProcurementContract)l.get(i)[0]).getRemark());
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
		if(sumq[8]!=null){
			pageModel.setDouble8(Double.parseDouble(sumq[8].toString()));//到货金额
		}else{
			pageModel.setDouble8(0.0);
		}
		if(sumq[9]!=null){
			pageModel.setDouble9(Double.parseDouble(sumq[9].toString()));//验收金额
		}else{
			pageModel.setDouble9(0.0);
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
