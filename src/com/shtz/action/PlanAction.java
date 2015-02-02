package com.shtz.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.shtz.model.Change;
import com.shtz.model.Organization;
import com.shtz.model.Person;
import com.shtz.model.Plan;
import com.shtz.model.ProcurementContract;
import com.shtz.model.SalesContract;
import com.shtz.model.User;
import com.shtz.service.PlanService;
import com.shtz.util.PageModel;

/**
 * @author sjm
 *  
 */
public class PlanAction extends ActionSupport {
	
	private PlanService service;
	
	
	private int id;
	
	private Integer[] pid;
	private String itemsName;
	private String model;
	private String oldPlanNum;
	private String reportComp;
	private String planNum;
	private Date planReceiptDate;
	private String company;
	private Double num;
	private Date arrivalDate;
	private String arrivalSite;
	private Double budget;
	private String procurementFlag;
	private String remark;
	private String bNumber;
	private String procurementWay;
	private Date procurementDate;
	private String procurementRemark;
	private Date searchAnnouncedDate;
	private String procurementName;
	private Date searchDate;
	private String contractExecutionWay;
	private Double contractMoney;
	private Double[] procurementPrice;
	private Double[] procurementMoney;
	private Double salesMoney;
	private Double salesRatio;
	private Change change;
	
	private String tags;
	
	private Map form;
	
	private Map map;
	
	private String flag;
	
	private String all_selected;
	
	private String now_selected;
	
	private String no_selected;
	
	private String planId;

	 private Date changeTime;
	 
	 private Double changenum;
	 
	 private double changebudget;

	 private String changereason;
	 
	 private double changecontractMoney;
	
	 private Double contractMoney1;
	 
	 private Plan plan;
	
	 private String  sDate;
	 
	 private String eDate;
	 
	 private int ybPlanCount;
	 
	 private Double ybPlanMoney;
	 
	 private int yqProcurementContract;
	 
	 private int xYCount;
	 
	 private Double xYMoney;
	 
	 private int dXyCount;
	 
	 private Double dXyMoney;
	 
	 private Double arrivalMoney;
	 
	 private int salesContractCount;
	 
	 private Double SalesContractMoney;
	 
	 private Double yqProcurementMoney;
	 
	 private Double yfMoney;
	 
	 private Double creditMoney;
	 
	 private Double billingMoney;
	 
	 private Double ysMoney;
	 
	 private Double jz;
	 
	 private Double[] contractNum;
	 
	 private Double planPrice;
	 
	 private String contractMsg;
	 
	 private String b;
	 
	 private String cflag;
	 
	 private Integer useCompId;
	 
	 private Integer suppliersId;
	 
	 private String procurementStatus;
	 
	 private String modifyFlag;
	 
	 private String spid;
	 private String  sopn;
	 
	 private String method;
	 
	 private Integer myOffset; 
	 
	 private String add;
	 
	 private String resetflag;
	 
	 private String personName;
	 
	 private String cancelStatus;
	 
	 private String procurementWayNum;
	 
	 
	public String getProcurementWayNum() {
		return procurementWayNum;
	}
	public void setProcurementWayNum(String procurementWayNum) {
		this.procurementWayNum = procurementWayNum;
	}
	public String getbNumber() {
		return bNumber;
	}
	public void setbNumber(String bNumber) {
		this.bNumber = bNumber;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getResetflag() {
		return resetflag;
	}
	public void setResetflag(String resetflag) {
		this.resetflag = resetflag;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public Integer getMyOffset() {
		return myOffset;
	}
	public void setMyOffset(Integer myOffset) {
		this.myOffset = myOffset;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getSopn() {
		return sopn;
	}
	public void setSopn(String sopn) {
		this.sopn = sopn;
	}
	public String getSpid() {
		return spid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}
	public String getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	public String getProcurementStatus() {
		return procurementStatus;
	}
	public void setProcurementStatus(String procurementStatus) {
		this.procurementStatus = procurementStatus;
	}
	public Integer getUseCompId() {
		return useCompId;
	}
	public void setUseCompId(Integer useCompId) {
		this.useCompId = useCompId;
	}
	public Integer getSuppliersId() {
		return suppliersId;
	}
	public void setSuppliersId(Integer suppliersId) {
		this.suppliersId = suppliersId;
	}
	public String getCflag() {
		return cflag;
	}
	public void setCflag(String cflag) {
		this.cflag = cflag;
	}
	public String getB() {
			return b;
		}
	public void setB(String b) {
			this.b = b;
		}
	 
	public Double getBillingMoney() {
		return billingMoney;
	}
	public void setBillingMoney(Double billingMoney) {
		this.billingMoney = billingMoney;
	}
	public Double getYsMoney() {
		return ysMoney;
	}
	public void setYsMoney(Double ysMoney) {
		this.ysMoney = ysMoney;
	}
	public Double getYfMoney() {
		return yfMoney;
	}
	public void setYfMoney(Double yfMoney) {
		this.yfMoney = yfMoney;
	}
	public Double getCreditMoney() {
		return creditMoney;
	}
	public void setCreditMoney(Double creditMoney) {
		this.creditMoney = creditMoney;
	}
	public int getSalesContractCount() {
		return salesContractCount;
	}
	public void setSalesContractCount(int salesContractCount) {
		this.salesContractCount = salesContractCount;
	}
	public Double getSalesContractMoney() {
		return SalesContractMoney;
	}
	public void setSalesContractMoney(Double salesContractMoney) {
		SalesContractMoney = salesContractMoney;
	}
	public Double getArrivalMoney() {
		return arrivalMoney;
	}
	public void setArrivalMoney(Double arrivalMoney) {
		this.arrivalMoney = arrivalMoney;
	}
	public int getdXyCount() {
		return dXyCount;
	}
	public void setdXyCount(int dXyCount) {
		this.dXyCount = dXyCount;
	}
	public Double getdXyMoney() {
		return dXyMoney;
	}
	public void setdXyMoney(Double dXyMoney) {
		this.dXyMoney = dXyMoney;
	}
	public String getContractMsg() {
		return contractMsg;
	}
	public void setContractMsg(String contractMsg) {
		this.contractMsg = contractMsg;
	}
	public Double getPlanPrice() {
		return planPrice;
	}
	public void setPlanPrice(Double planPrice) {
		this.planPrice = planPrice;
	}
	public Integer[] getPid() {
		return pid;
	}
	public void setPid(Integer[] pid) {
		this.pid = pid;
	}
	public Double[] getContractNum() {
		return contractNum;
	}
	public void setContractNum(Double[] contractNum) {
		this.contractNum = contractNum;
	}
	public Double getJz() {
		return jz;
	}
	public void setJz(Double jz) {
		this.jz = jz;
	}
	public Double getYqProcurementMoney() {
		return yqProcurementMoney;
	}
	public void setYqProcurementMoney(Double yqProcurementMoney) {
		this.yqProcurementMoney = yqProcurementMoney;
	}
	public Double getxYMoney() {
		return xYMoney;
	}
	public void setxYMoney(Double xYMoney) {
		this.xYMoney = xYMoney;
	}
	public Double getYbPlanMoney() {
		return ybPlanMoney;
	}
	public void setYbPlanMoney(Double ybPlanMoney) {
		this.ybPlanMoney = ybPlanMoney;
	}
	public int getxYCount() {
		return xYCount;
	}
	public void setxYCount(int xYCount) {
		this.xYCount = xYCount;
	}
	public int getYqProcurementContract() {
		return yqProcurementContract;
	}
	public void setYqProcurementContract(int yqProcurementContract) {
		this.yqProcurementContract = yqProcurementContract;
	}
	public int getYbPlanCount() {
		return ybPlanCount;
	}
	public void setYbPlanCount(int ybPlanCount) {
		this.ybPlanCount = ybPlanCount;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public double getChangecontractMoney() {
		return changecontractMoney;
	}
	public void setChangecontractMoney(double changecontractMoney) {
		this.changecontractMoney = changecontractMoney;
	}
	public Double getContractMoney1() {
		return contractMoney1;
	}
	public void setContractMoney1(Double contractMoney1) {
		this.contractMoney1 = contractMoney1;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Double getChangenum() {
		return changenum;
	}
	public void setChangenum(Double changenum) {
		this.changenum = changenum;
	}
	public double getChangebudget() {
		return changebudget;
	}
	public void setChangebudget(double changebudget) {
		this.changebudget = changebudget;
	}
	public String getChangereason() {
		return changereason;
	}
	public void setChangereason(String changereason) {
		this.changereason = changereason;
	}
	public String getAll_selected() {
		return all_selected;
	}
	public void setAll_selected(String all_selected) {
		this.all_selected = all_selected;
	}
	public String getNow_selected() {
		return now_selected;
	}
	public void setNow_selected(String now_selected) {
		this.now_selected = now_selected;
	}
	public String getNo_selected() {
		return no_selected;
	}
	public void setNo_selected(String no_selected) {
		this.no_selected = no_selected;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Map getForm() {
		return form;
	}
	public void setForm(Map form) {
		this.form = form;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	private int orgId;

	private List l;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public void setService(PlanService service) {
		this.service = service;
	}
	
	public String getItemsName() {
		return itemsName;
	}
	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getOldPlanNum() {
		return oldPlanNum;
	}
	public void setOldPlanNum(String oldPlanNum) {
		this.oldPlanNum = oldPlanNum;
	}
	public String getReportComp() {
		return reportComp;
	}
	public void setReportComp(String reportComp) {
		this.reportComp = reportComp;
	}
	public String getPlanNum() {
		return planNum;
	}
	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}
	public Date getPlanReceiptDate() {
		return planReceiptDate;
	}
	public void setPlanReceiptDate(Date planReceiptDate) {
		this.planReceiptDate = planReceiptDate;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}

	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getArrivalSite() {
		return arrivalSite;
	}
	public void setArrivalSite(String arrivalSite) {
		this.arrivalSite = arrivalSite;
	}
	public Double getBudget() {
		return budget;
	}
	public void setBudget(Double budget) {
		this.budget = budget;
	}
	public String getProcurementFlag() {
		return procurementFlag;
	}
	public void setProcurementFlag(String procurementFlag) {
		this.procurementFlag = procurementFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProcurementWay() {
		return procurementWay;
	}
	public void setProcurementWay(String procurementWay) {
		this.procurementWay = procurementWay;
	}
	public Date getProcurementDate() {
		return procurementDate;
	}
	public void setProcurementDate(Date procurementDate) {
		this.procurementDate = procurementDate;
	}
	public String getProcurementRemark() {
		return procurementRemark;
	}
	public void setProcurementRemark(String procurementRemark) {
		this.procurementRemark = procurementRemark;
	}
	public Date getSearchAnnouncedDate() {
		return searchAnnouncedDate;
	}
	public void setSearchAnnouncedDate(Date searchAnnouncedDate) {
		this.searchAnnouncedDate = searchAnnouncedDate;
	}
	public Date getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	public String getContractExecutionWay() {
		return contractExecutionWay;
	}
	public void setContractExecutionWay(String contractExecutionWay) {
		this.contractExecutionWay = contractExecutionWay;
	}
	public Double getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}

	public Double[] getProcurementPrice() {
		return procurementPrice;
	}
	public void setProcurementPrice(Double[] procurementPrice) {
		this.procurementPrice = procurementPrice;
	}
	public Double[] getProcurementMoney() {
		return procurementMoney;
	}
	public void setProcurementMoney(Double[] procurementMoney) {
		this.procurementMoney = procurementMoney;
	}
	public Double getSalesMoney() {
		return salesMoney;
	}
	public void setSalesMoney(Double salesMoney) {
		this.salesMoney = salesMoney;
	}
	public Double getSalesRatio() {
		return salesRatio;
	}
	public void setSalesRatio(Double salesRatio) {
		this.salesRatio = salesRatio;
	}
	public Change getChange() {
		return change;
	}
	public void setChange(Change change) {
		this.change = change;
	}
	public PlanService getService() {
		return service;
	}
	public String getCancelStatus() {
		return cancelStatus;
	}
	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}
	public String getProcurementName() {
		return procurementName;
	}
	public void setProcurementName(String procurementName) {
		this.procurementName = procurementName;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		int offset = 0;
		try {
			offset = Integer.parseInt(ServletActionContext.getRequest().getParameter("pager.offset"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("分页异常");
		}
		int pageSize = 50;
		Map params = new HashMap();
		if(orgId!=0)
			params.put("id_org", orgId);
		if(oldPlanNum!=null&&!"".equals(oldPlanNum))
			params.put("oldPlanNum", oldPlanNum);
		params.put("eq_planStatus", "01");
		if(sDate!=null && !"".equals(sDate.trim())  && eDate!=null && !"".equals(eDate.trim()) ){
			params.put("dy_p.planReceiptDate", " between '"+sDate+"' and '"+eDate+"'");
			//params.put("dy_p.planReceiptDate", " <='"+eDate+"' ");
		}
		if(useCompId!=null)
			params.put("id_reportCompId", useCompId);
		//params.put("obj_user", (User)ServletActionContext.getRequest().getSession().getAttribute("login"));
		PageModel pm = service.serchsearchPlansByParams(params, offset, pageSize);
		//PageModel pm = service.searchPlans(offset, pageSize);
		Map form = new HashMap();
		form.put("all_selected", all_selected);
		form.put("no_selected", no_selected);
		form.put("now_selected", now_selected);
		
		try {
			this.operationCheckInfo(map, form, ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		String add = ServletActionContext.getRequest().getParameter("add");
//		ServletActionContext.getRequest().setAttribute("add", add);
		ServletActionContext.getRequest().setAttribute("pm", pm);
		ServletActionContext.getRequest().setAttribute("params", params);
		this.setMyOffset(offset);
		if(b!=null&&!"".equals(b))
			ServletActionContext.getRequest().setAttribute("b", b);
				
		return SUCCESS;
	}
	public String add() throws Exception {
		System.out.println("-----Plan Action add()----");
		Plan plan = new Plan();
		plan.setItemsName(itemsName);
		plan.setModel(model);
		plan.setOldPlanNum(oldPlanNum);
		plan.setReportComp(reportComp);
		plan.setPlanNum(planNum);
		plan.setNum(num);
		
		service.addPlan(plan, orgId);
		this.execute();
		return "add_success";
	}
	public String delete() throws Exception{
		service.deletePlan(id);
		this.execute();
		return "del_success";
	}
	public String add_input() throws Exception {
		
		return SUCCESS;
	}
	public String addOrModify() throws Exception {
		
		Plan p = service.findPlan(id);
		if(p!=null){
			ServletActionContext.getRequest().setAttribute("plan", p);
			return "modify_input";
		}else{
			return "add_input";
		}
		
	}
	public String doProcurementWay() throws Exception{
		String[] str = tags.split(",");
		String ps = "02";
		if(modifyFlag !=null && modifyFlag.equals("seekSource"))
			ps = "03";
		service.addProcurementWay(str,procurementName, procurementWay, procurementDate, procurementRemark,ps);
		//this.planTrackSearch();
		ServletActionContext.getRequest().setAttribute("cflag", "success");
		if(modifyFlag !=null && modifyFlag.equals("seekSource"))
			return "seekSource_success";
		return "success";
		
	}
	public String addPlanBNumber() throws Exception{
		String[] str = tags.split(",");
		service.addPlanBNumber(str, plan.getbNumber());
		ServletActionContext.getRequest().setAttribute("cflag", "success");
		if(modifyFlag !=null && modifyFlag.equals("seekSource"))
			return "seekSource_success";
		return "success";
		
	}
	public String procurementWay() throws Exception {
		
		Plan p = service.findPlan(id);
	
		ServletActionContext.getRequest().setAttribute("plan", p);
		return "procurementWay_input";
	
		
	}
	public String planTrackSearch() throws Exception {
		// TODO Auto-generated method stub
		int offset = 0;
		try {
			offset = Integer.parseInt(ServletActionContext.getRequest().getParameter("pager.offset"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("fen ye error");
		}
		this.setMyOffset(offset);
		int pageSize = 50;
		Map params = new HashMap();
		if(method!=null && !"".equals(method) && method.equals("planAdd")){
			params.put("in_planStatus", "01");
		}else{
			params.put("in_planStatus", "01,02");
		}
		if(orgId!=0)
			params.put("id_org", orgId);
		if(oldPlanNum!=null  && !oldPlanNum.equals(""))
			params.put("oldPlanNum", oldPlanNum);
		if(procurementStatus!=null && !"".equals(procurementStatus)){
			if(procurementStatus.equals("01")){
				params.put("dy_p.procurementDate", " is not null");
			}else if(procurementStatus.equals("02")){
				params.put("dy_p.procurementWay", " is not null");
				params.put("dy_p.procurementDate", " is null");
			}else if(procurementStatus.equals("03")){
				params.put("dy_p.procurementWay", " is null");
				params.put("dy_p.procurementDate", " is null");
			}
		}
		params.put("dy_p.searchAnnouncedDate", "is null");
		if(procurementWayNum!=null  && !procurementWayNum.equals(""))
			params.put("procurementWayNum", procurementWayNum);
		
//		if(procurementWay!=null && !"0".equals(procurementWay.trim()))
//			params.put("eq_procurementWay", procurementWay.trim());
		if(remark!=null && !"".equals(remark.trim()))
			params.put("remark", remark.trim());
		if(bNumber!=null && !"".equals(bNumber.trim()))
			params.put("bNumber", bNumber.trim().trim());
		if(procurementName!=null && !"".equals(procurementName.trim()))
			params.put("procurementName", procurementName.trim());
		if(sDate!=null && !"".equals(sDate.trim())  && eDate!=null && !"".equals(eDate.trim()) ){
			params.put("dy_p.planReceiptDate", " between '"+sDate+"' and '"+eDate+"'");
			//params.put("dy_p.planReceiptDate", " <='"+eDate+"' ");
		}
		if(personName!=null && !personName.equals(""))
			params.put("person.name", personName.trim());
		params.put("obj_user", (User)ServletActionContext.getRequest().getSession().getAttribute("login"));
		PageModel pm = service.serchsearchPlansByParams(params,offset, pageSize);
		params.put("personName", personName.trim());
		//System.out.println("======now_selected===="+now_selected);
		//System.out.println("======no_selected===="+no_selected);
		Map form = new HashMap();
		form.put("all_selected", all_selected);
		form.put("no_selected", no_selected);
		form.put("now_selected", now_selected);
		
		try {
			this.operationCheckInfo(map, form, ServletActionContext.getRequest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("pm", pm);
		ServletActionContext.getRequest().setAttribute("params", params);
		if(method!=null && !"".equals(method) && method.equals("planAdd")){
			return "planAdd_success";
		}
		return SUCCESS;
	}
	public String SeekSourceSearch() throws Exception {
		int offset = 0;
		try {
			offset = Integer.parseInt(ServletActionContext.getRequest().getParameter("pager.offset"));
		} catch (Exception e) {
			System.out.println("fen ye error");
		}
		this.setMyOffset(offset);
		int pageSize = 50;
		Map params = new HashMap();
		params.put("in_planStatus", "02,03");
		if(orgId!=0)
			params.put("id_org", orgId);
		if(oldPlanNum!=null  && !oldPlanNum.equals(""))
			params.put("oldPlanNum", oldPlanNum);
		if(procurementWayNum!=null  && !procurementWayNum.equals(""))
			params.put("procurementWayNum", procurementWayNum);
		params.put("dy_p.procurementDate", "is not null");
		if(sDate!=null && !"".equals(sDate.trim())  && eDate!=null && !"".equals(eDate.trim()) ){
			params.put("dy_p.planReceiptDate", " between '"+sDate+"' and '"+eDate+"'");
		}
		if(remark!=null && !"".equals(remark.trim()))
			params.put("remark", remark.trim());
		if(contractExecutionWay!=null && "0".equals(contractExecutionWay.trim())){
			params.put("dy_(p.contractExecutionWay <> '3' or p.contractExecutionWay"," is null)");
			//params.put("no_contractExecutionWay","3");
			//params.put("or_contractExecutionWay", " is null");
		}else if(contractExecutionWay!=null && "3".equals(contractExecutionWay.trim())){
			params.put("eq_contractExecutionWay","3");
		}
		if(bNumber!=null && !"".equals(bNumber.trim()))
			params.put("bNumber", bNumber.trim().trim());
		if(personName!=null && !personName.equals(""))
			params.put("person.name", personName.trim());
		params.put("obj_user", (User)ServletActionContext.getRequest().getSession().getAttribute("login"));
		PageModel pm = service.serchsearchPlansByParams(params,offset, pageSize);
		params.put("personName", personName.trim());
		Map form = new HashMap();
		form.put("all_selected", all_selected);
		form.put("no_selected", no_selected);
		form.put("now_selected", now_selected);
		
		try {
			this.operationCheckInfo(map, form, ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("pm", pm);
		ServletActionContext.getRequest().setAttribute("params", params);
		return SUCCESS;
	}
	
	private void operationCheckInfo(Map map, Map form, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (request.getParameter("flag") != null && request.getParameter("flag").equals("CHECKED")) {
		String all_select = (String) form.get("all_selected");
		
		if (all_select == null || all_select.equals("")) {
		all_select = "";
		}
		String now_select = (String) form.get("now_selected");
		String[] all_now_select = now_select.split(",");
		String no_select = (String)form.get("no_selected");
		String[] all_now_no_select = no_select.split(",");
//		System.out.println("======now_select======"+now_select);
		for (int i = 1; i < all_now_select.length; i++) {
		String temp_all_select = ","+all_select;
		String strBoxSelected = all_now_select[i];
		String strSearchWith = ","+strBoxSelected + ",";
		if (temp_all_select.indexOf(strSearchWith) == -1) {
		all_select = all_select + strSearchWith;
		} 
		}
		for (int i = 1; i < all_now_no_select.length; i++) {
			String temp_all_select = ","+all_select;
			String strBoxNoselected = all_now_no_select[i];
			String strSearchWith = ","+strBoxNoselected + ",";
			int iSearchIndex = temp_all_select.indexOf(strSearchWith);
			if (iSearchIndex != -1) {
			all_select = all_select.replaceAll(strSearchWith, "");
			} 
		}
//		System.out.println("======all======"+all_select);
		request.setAttribute("all", all_select);
		
		}
		}
		
	public String doSeekSource() throws Exception {
		if(pid.length>0){
			for(int i=0;i<pid.length;i++){
				if(pid[i]!=null){
					Plan pn = new Plan();
						pn.setId(pid[i]);
					if(procurementPrice[i] != null)
						pn.setProcurementPrice(procurementPrice[i]);
					if(procurementMoney[i] != null)
						pn.setProcurementMoney(procurementMoney[i]);
					if(searchAnnouncedDate != null)
						pn.setSearchAnnouncedDate(searchAnnouncedDate);
					if(contractExecutionWay != null && !contractExecutionWay.equals("0"))
						pn.setContractExecutionWay(contractExecutionWay);
					if(searchDate!=null && !"".equals(searchDate)){
						pn.setSearchDate(searchDate);
						pn.setPlanStatus("03");
					}
						service.modifyPlanPM(pn);
				}
			}
		}
		this.setCflag("success");
		//ServletActionContext.getRequest().setAttribute("cflag", "success");
		return "success";
		}
	public String modifyPlanClearSeekDate() throws Exception {
		String[] str = tags.split(",");
		if(str.length>0){
			for(int i=0;i<str.length;i++){
					if(str[i]!=null){
							Plan pn = new Plan();
							pn.setId(Integer.parseInt(str[i]));
								service.modifyPlanClearSeekDate(pn);
					}
			}
		}
		
		return "success";
		}
	public String doSeekSourcePmodify() throws Exception {
		String[] str = tags.split(",");
		if(str.length>0){
			for(int i=0;i<str.length;i++){
				if(str[i]!=null){
					Plan pn = new Plan();
					pn.setId(Integer.parseInt(str[i]));
					if(procurementPrice != null)
						pn.setProcurementPrice(procurementPrice[i]);
					if(procurementMoney != null)
						pn.setProcurementMoney(procurementMoney[i]);
					if(searchAnnouncedDate != null)
						pn.setSearchAnnouncedDate(searchAnnouncedDate);
					if(contractExecutionWay != null)
						pn.setContractExecutionWay(contractExecutionWay);
					if(searchDate != null && !"".equals(searchDate)){
						pn.setSearchDate(searchDate);
						pn.setPlanStatus("03");
					}
					service.modifyPlanPM(pn);
				}
			}
		}
		this.setCflag("success");
		return "success";
		}
	public String modifySeekSource() throws Exception {
		if(pid.length>0){
			for(int i=0;i<pid.length;i++){
				if(pid[i]!=null){
					Plan pn = new Plan();
					pn.setId(pid[i]);
					pn.setProcurementPrice(procurementPrice[i]);
					pn.setProcurementMoney(procurementMoney[i]);
					pn.setSearchAnnouncedDate(searchAnnouncedDate);
					pn.setSearchDate(searchDate);
					pn.setContractExecutionWay(contractExecutionWay);
					service.modifyPlanPM(pn);
				}
			}
		}
		this.setCflag("success");
		return SUCCESS;
	}
	public String doChange() throws Exception {
		Date changetime=new Date();
		Person ps = (Person)ServletActionContext.getRequest().getSession().getAttribute("person");
		Plan p = service.findPlan(Integer.parseInt(planId));
		service.addOrUpdageChange(p, changetime, p.getNum(), p.getBudget(), changereason,p.getPlanPrice(),ps.getName());
		p.setNum(changenum);
		p.setBudget(changecontractMoney);
		p.setPlanPrice(planPrice);
		service.modifyPlanChange(planId, changenum, changecontractMoney,planPrice);
		//service.addOrUpdageChange(planId, changetime, changenum, changecontractMoney, changereason,planPrice,p.getName());
		this.setCflag("success");
		return SUCCESS;
		}
	
	public String modifyPlan() throws Exception {
		int id=0;
		if(planId!=null){
			id=Integer.parseInt(planId);
		}
		Plan p = service.findPlan(id);
		if(p!=null){
			ServletActionContext.getRequest().setAttribute("plan", p);
		}
		return "modifyPlan_success";
	}
	public String doModifyPlan() throws Exception {
		//System.out.println(plan.getItemsName());
		service.modifyPlan(plan);
		ServletActionContext.getRequest().setAttribute("cflag", "success");
		//this.planTrackSearch();
		return SUCCESS;
	}
	public String doModifyPlanMg() throws Exception {
		//System.out.println(plan.getItemsName());
		service.modifyPlanMg(plan);
		return "doModifyPlanMg_success";
	}
	
	public String changeSearch() throws Exception {
		// TODO Auto-generated method stub
		int offset = 0;
		try {
			offset = Integer.parseInt(ServletActionContext.getRequest().getParameter("pager.offset"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("fenye error");
		}
		this.setMyOffset(offset);
		int pageSize = 50;
		Map params = new HashMap();
		
		if(orgId!=0)
			params.put("id_org", orgId);
		if(oldPlanNum!=null && !oldPlanNum.trim().equals(""))
			params.put("oldPlanNum", oldPlanNum);
		if(useCompId!=null)
			params.put("id_reportCompId", useCompId);
		if(cancelStatus!=null && cancelStatus.trim().equals("06")){
			params.put("eq_planStatus", "06");
		}else{
			params.put("in_planStatus", "01,02,03");
		}
			
		//params.put("dy_p.searchDate", "is null");
		if(sDate!=null && !"".equals(sDate.trim())  && eDate!=null && !"".equals(eDate.trim()) ){
			params.put("dy_p.planReceiptDate", " between '"+sDate+"' and '"+eDate+"'");
			//params.put("dy_p.planReceiptDate", " <='"+eDate+"' ");
		}
		if(procurementFlag!=null && !procurementFlag.equals("0")){
				params.put("eq_procurementFlag",procurementFlag);
		}

		//params.put("obj_user", (User)ServletActionContext.getRequest().getSession().getAttribute("login"));
		PageModel pm = service.serchsearchPlansByParams(params,offset, pageSize);
		Map form = new HashMap();
		form.put("all_selected", all_selected);
		form.put("no_selected", no_selected);
		form.put("now_selected", now_selected);
		
		try {
			this.operationCheckInfo(map, form, ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("pm", pm);
		ServletActionContext.getRequest().setAttribute("params", params);
		
		if(contractMsg!=null && !"".equals(contractMsg))
			ServletActionContext.getRequest().setAttribute("contractMsg", contractMsg);
		return SUCCESS;
	}
	
	public String getSave()throws Exception{
		Map params = new HashMap();
		if(orgId!=0)
			params.put("id_org.id", orgId);
		
		Map params1 = new HashMap();
		if(orgId!=0)
			params1.put("id_orgId", orgId);
		Map params2 = new HashMap();
		if(orgId!=0)
			params2.put("id_plan.org.id", orgId);
		Map params3 = new HashMap();
		if(orgId!=0)
			params3.put("id_procurementContract.orgId", orgId);
		Map params4 = new HashMap();
		if(orgId!=0)
			params4.put("id_salesContract.orgId", orgId);
		ybPlanCount =service.getPlanCountByDate(sDate, eDate,params);
		
		ybPlanMoney = service.getPlanMoneyByDate(sDate, eDate,params);
		
		xYCount = service.getPlanXYCountByDate(sDate, eDate,params);
		
		xYMoney = service.getPlanXYMoneyByDate(sDate, eDate,params);
		
		dXyCount = service.getPlanDXYCountByDate(sDate, eDate,params);
		 
		dXyMoney = service.getPlanDXYMoneyByDate(sDate, eDate,params);

		yqProcurementContract = service.getProcurementContractCountByDate(sDate, eDate,params1);
		
		yqProcurementMoney = service.getProcurementMoneyCountByDate(sDate, eDate,params1);

		arrivalMoney = service.getPlanArrivalMoneyByDate(sDate, eDate,params2);
		
		salesContractCount = service.getSalesContractCountByDate(sDate, eDate,params1);
		
		SalesContractMoney = service.getSalesContractMoneyByDate(sDate, eDate,params1);
		
		creditMoney = service.getContractCreditMoneyByDate(sDate, eDate,params3);
		
		List<ProcurementContract> pList=service.getProcurementsByDate(sDate, eDate,params1);
		double temp=0;
		for(ProcurementContract p : pList){
			if(p.getPayed() !=null && !"".equals(p.getPayed())){
				String a = "&";
				String[] str = p.getPayed().split(",");
					if(str.length>=1){
						for (int j = 0; j < str.length; j++) {
							if(str[j]!=null && !str[j].equals("") && str[j].indexOf(a)>0){
								String[] temps = str[j].split(a);
								if(temps.length == 2){
									temp += Double.parseDouble(temps[0]);
								}
							}
						}
					}
			}
		}
		yfMoney = temp;
		
		List<SalesContract> sList=service.getSalesContractsByDate(sDate, eDate,params1);
		double temp1=0;
		for(SalesContract p : sList){
			if(p.getContractReceivedMoney()!=null ){
				String a = "&";
				String[] str = p.getContractReceivedMoney().split(",");
					if(str.length>=1){
						for (int j = 0; j < str.length; j++) {
							if(str[j]!=null && !str[j].equals("") && str[j].indexOf(a)>0){
								String[] temps = str[j].split(a);
								if(temps.length == 2){
									temp1 += Double.parseDouble(temps[0]);
								}
							}
						}
					}
			}
		}
		ysMoney = temp1;
		billingMoney = service.getbillingMoneyByDate(sDate, eDate,params4);
		jz = service.getJzByDate(sDate, eDate,params,xYMoney);
		if(flag!=null && !"".equals(flag) && flag.equals("y"))
			return "dySuccess";
		else
			return SUCCESS;
	}
	
	public String cfPlan()throws Exception{
		
		service.addcfPlan(id, contractNum,cflag);
		ServletActionContext.getRequest().setAttribute("ccflag", "success");
		if(cflag!=null && !"".equals(cflag) && cflag.equals("pcontract"))
			return "pcontract_success";
		else
			return SUCCESS;
	}
	
	public String toSeekSource()throws Exception{
		
		String[] str = tags.split(",");
		Integer[] ia=new Integer[str.length];
		for(int i=0;i<str.length;i++){
		   ia[i]=Integer.parseInt(str[i]);
		}
		
		List plist = service.getPlans(ia);
		ServletActionContext.getRequest().setAttribute("plist", plist);
		if(flag!=null && flag.equals("success_price"))
			return "success_price";
		return SUCCESS;
	}
	public String toModifySeekSource()throws Exception{
		
		
		if(modifyFlag !=null){
			if(id==0)
				ServletActionContext.getRequest().setAttribute("pids", tags);
			else{
				Plan p = service.findPlan(id);
				ServletActionContext.getRequest().setAttribute("plan", p);
				ServletActionContext.getRequest().setAttribute("pids", id);
				ServletActionContext.getRequest().setAttribute("procurementRemark", p.getProcurementRemark());
				ServletActionContext.getRequest().setAttribute("bNumber", p.getbNumber());
			}
			if(modifyFlag.equals("procurementRemark"))
				return "seekSource_success";
			if(modifyFlag.equals("seekSource"))
				return "seekSource_success";
			if(modifyFlag.equals("procurementBNumber_success"))
				return "procurementBNumber_success";
			
		}
		return "modifySuccess";
	}
	public String getPlanStatusList()throws Exception{
		int offset = 0;
		try {
			offset = Integer.parseInt(ServletActionContext.getRequest().getParameter("pager.offset"));
		} catch (Exception e) {
			System.out.println("fen ye error");
		}
		int pageSize = 50;
		Map params = new HashMap();
		if(orgId!=0)
			params.put("id_oid", orgId);
		if(oldPlanNum!=null && !"".equals(oldPlanNum.trim()))
			params.put("oldPlanNum", oldPlanNum);
		if(useCompId!=null)
			params.put("id_reportCompId", useCompId);
		if(procurementWay!=null && !"".equals(procurementWay.trim()) && !("0".equals(procurementWay.trim())))
			params.put("id_procurementWay", procurementWay);
		if(contractExecutionWay!=null && !"".equals(contractExecutionWay.trim()) && !("0".equals(contractExecutionWay.trim())))
			params.put("id_contractExecutionWay", contractExecutionWay);
		if(sDate!=null && !"".equals(sDate.trim())  && eDate!=null && !"".equals(eDate.trim()) ){
			params.put("dy_p.planReceiptDate", " between '"+sDate+"' and '"+eDate+"'");
			//params.put("dy_p.planReceiptDate", " <='"+eDate+"' ");
		}
		if(suppliersId!=null)
			params.put("dy_pc.suppliersid", "="+suppliersId+"  ");
		params.put("dy_p.model", "<> '02'");
		params.put("dy_p.planStatus", "<> '06'");
		if(personName!=null && !personName.equals("")){
			params.put("dy_ps.name", " like '%"+personName.trim()+"%'");
		}
		PageModel  pm = service.getPlanStatusList(offset,pageSize,params,true);
		if(personName!=null && !personName.equals("")){
			params.put("personName", personName.trim());
		}
		ServletActionContext.getRequest().setAttribute("pm", pm);
		ServletActionContext.getRequest().setAttribute("params", params);
		return SUCCESS;
	}
	public String getReportCompList()throws Exception{
		int offset = 0;
		try {
			offset = Integer.parseInt(ServletActionContext.getRequest().getParameter("pager.offset"));
		}catch (Exception e){
			System.out.println("fen ye error");
		}
		int pageSize = 50;
		Map params = new HashMap();
//		if(orgId!=0)
//			params.put("id_oid", orgId);
//		if(useCompId!=null)
//			params.put("id_reportCompId", useCompId);
//		if(sDate!=null && !"".equals(sDate.trim())  && eDate!=null && !"".equals(eDate.trim()) ){
//			params.put("dy_p.planReceiptDate", " between '"+sDate+"' and '"+eDate+"'");
			//params.put("dy_p.planReceiptDate", " <='"+eDate+"' ");
//		}
		if(useCompId==null){
			useCompId=0;
		}
		PageModel  pm = service.getReportCompList(offset,pageSize,params,true,sDate,eDate,orgId,useCompId);
		ServletActionContext.getRequest().setAttribute("pm", pm);
		ServletActionContext.getRequest().setAttribute("params", params);
		return SUCCESS;
	}
	public String getPlanss() throws Exception {
		// TODO Auto-generated method stub
		int offset = 0;
		try {
			offset = Integer.parseInt(ServletActionContext.getRequest().getParameter("pager.offset"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("fen ye error");
		}
		this.setMyOffset(offset);
		int pageSize = 50;
		Map params = new HashMap();
		params.put("in_planStatus", "01");
		if(sDate!=null && !"".equals(sDate.trim())  && eDate!=null && !"".equals(eDate.trim()) ){
			params.put("dy_p.planReceiptDate", " between '"+sDate+"' and '"+eDate+"'");
			//params.put("dy_p.planReceiptDate", " <='"+eDate+"' ");
		}
		if(remark!=null && !"".equals(remark.trim()))
			params.put("remark", remark.trim());
		Person p = (Person) ServletActionContext.getRequest().getSession().getAttribute("person");
		if(p!=null)
			params.put("id_org", p.getOrg().getId());
		else
			return LOGIN;
		if(oldPlanNum!=null && !"".equals(oldPlanNum.trim()))
			params.put("oldPlanNum", oldPlanNum);
		params.put("dy_p.person ", " is null");
		params.put("dy_p.user ", " is null");
//		Person p = (Person)ServletActionContext.getRequest().getSession().getAttribute("person");
//		if(p!=null)
//			params.put("obj_org", ((Person)ServletActionContext.getRequest().getSession().getAttribute("person")).getOrg().getId());
//		else
//			return LOGIN;
		PageModel pm = service.serchsearchPlansByParams(params,offset, pageSize);
		
		Map form = new HashMap();
		form.put("all_selected", all_selected);
		form.put("no_selected", no_selected);
		form.put("now_selected", now_selected);
		
		try {
			this.operationCheckInfo(map, form, ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("pm", pm);
		ServletActionContext.getRequest().setAttribute("params", params);
		
		return SUCCESS;
	}
	public String getPlanssPerson() throws Exception {
		Plan plan = new Plan();
		Person p= (Person) ServletActionContext.getRequest().getSession().getAttribute("person");
		User u = (User) ServletActionContext.getRequest().getSession().getAttribute("login");
		String[] str = tags.split(",");
		Integer[] ia=new Integer[str.length];
		for(int i=0;i<str.length;i++){
		   ia[i]=Integer.parseInt(str[i]);
		   plan.setId(ia[i]);
		   plan.setPerson(p);
		   plan.setUser(u);
		   service.modifyPlansByIds(plan);
		}
		
		
		//service.modifyPlansByIds(p.getId(), u.getId(), tags);
//		Plan pl = new Plan();
//		pl.setPerson(p);
//		pl.setUser(u);
		return SUCCESS;
	}
	public String modifyPlansByIdsBack() throws Exception {
		service.modifyPlansByIdsBack(tags);
		String from = ServletActionContext.getRequest().getParameter("from");
		if(from!=null && !"".equals(from) && from.equals("seek"))
			return "seek_success";
		return SUCCESS;
	}
	public String cancelPlans() throws Exception {
		service.modifycancelPlans(tags);
		return SUCCESS;
	}
	public String redoPlans() throws Exception {
		service.modifyredoPlans(tags);
		return SUCCESS;
	}
	public String deletePlansByIds() throws Exception {
		service.deletePlansByIds(tags);
		return SUCCESS;
	}
	public String modifyPlansOrg() throws Exception {
		service.modifyPlansOrg(tags,id);
		ServletActionContext.getRequest().setAttribute("cflag", "success");
		return SUCCESS;
	}
}
