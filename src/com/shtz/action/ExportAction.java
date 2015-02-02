package com.shtz.action;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.shtz.model.ProcurementArrivalBean;
import com.shtz.model.ProcurementContractBean;
import com.shtz.model.ReportCompStatisticsBean;
import com.shtz.model.SalesContractArrivalBean;
import com.shtz.model.User;
import com.shtz.service.ContractService;
import com.shtz.service.ExportService;
import com.shtz.service.PlanService;
import com.shtz.service.SalesContractService;
import com.shtz.util.ExcelCreate;
import com.shtz.util.ExcelRead;
import com.shtz.util.PageModel;
import com.shtz.util.StatusName;

/**
 * @author sjm
 * 
 */
public class ExportAction extends ActionSupport {

	private ExcelCreate excelCreateService;

	private ExcelRead ExcelReadService;

	private ExportService eservice;

	private String importfilename;

	private PlanService pservice;
	
	private SalesContractService service;
	
	private ContractService cservice;
	
	private StatusName statusName;
	
	
	public void setStatusName(StatusName statusName) {
		this.statusName = statusName;
	}

	public void setService(SalesContractService service) {
		this.service = service;
	}

	public void setCservice(ContractService cservice) {
		this.cservice = cservice;
	}
	private int[] msg;
	
	private String result;
	//查询部门号
	private int orgId;
	//查询名称
	private String name;
	
	//查询名称
	private String contractNum;
	
	private String oldPlanNum;
	
	private Integer useCompId;
	 
	 private Integer suppliersId;
	 
	 private String procurementWay;
	 
	 private String contractExecutionWay;
	 
	 private String  sDate;
	 
	 private String eDate;
	
	 private String personName;
	 
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

	public String getProcurementWay() {
		return procurementWay;
	}

	public void setProcurementWay(String procurementWay) {
		this.procurementWay = procurementWay;
	}

	public String getContractExecutionWay() {
		return contractExecutionWay;
	}

	public void setContractExecutionWay(String contractExecutionWay) {
		this.contractExecutionWay = contractExecutionWay;
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

	public ExcelCreate getExcelCreateService() {
		return excelCreateService;
	}

	public ExcelRead getExcelReadService() {
		return ExcelReadService;
	}

	public ExportService getEservice() {
		return eservice;
	}

	public PlanService getPservice() {
		return pservice;
	}

	public String getOldPlanNum() {
		return oldPlanNum;
	}

	public void setOldPlanNum(String oldPlanNum) {
		this.oldPlanNum = oldPlanNum;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setEservice(ExportService eservice) {
		this.eservice = eservice;
	}

	public void setPservice(PlanService pservice) {
		this.pservice = pservice;
	}

	public int[] getMsg() {
		return msg;
	}

	public void setMsg(int[] msg) {
		this.msg = msg;
	}

	public String getImportfilename() {
		return importfilename;
	}

	public void setImportfilename(String importfilename) {
		this.importfilename = importfilename;
	}

	public void setExcelReadService(ExcelRead excelReadService) {
		ExcelReadService = excelReadService;
	}

	public void setExcelCreateService(ExcelCreate excelCreateService) {
		this.excelCreateService = excelCreateService;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	// 计划执行明细表
	public String planSheet() throws Exception {
		// 写入excel
		// 表头
		List<String> headList = new ArrayList<String>();
		headList.add("序号");
		headList.add("计划提报单位");
		headList.add("购物车编码");
		headList.add("行号");
		headList.add("计划接收日期");
		headList.add("物料编码");
		headList.add("物料描述");
		headList.add("计划数量");
		headList.add("计量单位");
		headList.add("预算金额");
		headList.add("是否取消");
		headList.add("采购方式");
		headList.add("采购方案审批通过日期");
		headList.add("寻源公布日期");
		headList.add("寻源结果审批通过日期");
		headList.add("合同执行方式");
		headList.add("合同签订数量");
		headList.add("采购单价");
		headList.add("采购金额");
		headList.add("采购合同编号");
		headList.add("采购签订日期");
		headList.add("采购合同要求到货时间");
		headList.add("采购合同金额");
		headList.add("供应商");
		headList.add("销售合同编号");
		headList.add("销售合同签订日期");
		headList.add("销售合同金额");
		headList.add("到货数量");
		headList.add("到货金额");
		headList.add("验货数量");
		headList.add("验收金额");
		headList.add("业务部门");
		headList.add("操作人");
		ExcelCreate s = new ExcelCreate();
		s.createSheet("计划执行明细表");
		s.addHeader(headList, true);
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
		}
		if(suppliersId!=null)
			params.put("dy_pc.suppliersid", "="+suppliersId+"  ");
			params.put("dy_p.model", "<> '02'");
			params.put("dy_p.planStatus", "<> '06'");
		if(personName!=null && !personName.equals("")){
			params.put("dy_ps.name", " like '%"+personName.trim()+"%'");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PageModel  pm = pservice.getPlanStatusList(0, 0, params, false);
		if(personName!=null && !personName.equals("")){
			params.put("personName", personName.trim());
		}
		List<ProcurementContractBean> l = pm.getList();
		HSSFCellStyle dtStyle  = s.setdtStyle();
		HSSFCellStyle dateStyle = s.setdateStyle();
		for (int i = 0; i < pm.getList().size(); i++) {
			List list = new ArrayList();
			list.add(i+1);
			list.add(l.get(i).getReportCompName());
			list.add(l.get(i).getOldPlanNum() );
			list.add(l.get(i).getPlanNum() );
			list.add(l.get(i).getPlanReceiptDate() );
			list.add(l.get(i).getItemsNum() );
			list.add(l.get(i).getItemsName() );
			list.add(l.get(i).getNum() );
			list.add(l.get(i).getUnit() );
			list.add(l.get(i).getBudget() );
			if(l.get(i).getPlanStatus() !=null)
				list.add(l.get(i).getPlanStatus().equals("06")?"已取消":"" );
			else
				list.add("");
			list.add(l.get(i).getProcurementWay() );
			if(l.get(i).getProcurementDate() !=null)
			list.add(sdf.format(l.get(i).getProcurementDate()));
			else
				list.add("");
			if(l.get(i).getSearchAnnouncedDate() !=null)
			list.add(sdf.format(l.get(i).getSearchAnnouncedDate()));
			else
				list.add("");
			if(l.get(i).getSearchDate() !=null)
			list.add(sdf.format(l.get(i).getSearchDate()));
			else
				list.add("");
			list.add(l.get(i).getContractExecutionWay() );
			list.add(l.get(i).getPlancontractNum());
			list.add(l.get(i).getProcurementPrice() );
			list.add(l.get(i).getProcurementMoney() );
			list.add(l.get(i).getContractNum() );
			if(l.get(i).getSigningDate() !=null)
				list.add(sdf.format(l.get(i).getSigningDate()));
			else
				list.add("");
			if(l.get(i).getArrivalDate() !=null)
				list.add(sdf.format(l.get(i).getArrivalDate()) );
			else
				list.add("");
			list.add(l.get(i).getTotalMoney() );
			list.add(l.get(i).getSuppliersName() );
			list.add(l.get(i).getSaleContractNum() );
			list.add(l.get(i).getSaleContractSigningDate() );
			list.add(l.get(i).getSaleContractMoney() );
			list.add(l.get(i).getArrivalNum() );
			list.add(l.get(i).getArrivalMoney() );
			list.add(l.get(i).getAcceptanceNum() );
			list.add(l.get(i).getAcceptanceMoney() );
			list.add(l.get(i).getOrgName() );
			list.add(l.get(i).getPersonName() );
			s.addRow(list,dtStyle,dateStyle);
		}
		Date now = new Date();
		
		String date = sdf.format(now);
		String fileName ="计划执行明细表" + date + ".xls";
		result=ExportAction.exportExcel(fileName, s);

		return null;
	}

	// 销售合同报表生成
	public String exportSalesContract() throws Exception {	
		// 写入excel
		List<String> headList = new ArrayList<String>();
		headList.add("系统ID");
		headList.add("销售合同名称");
		headList.add("销售合同编号");
		headList.add("合同签订时间");
		headList.add("计划提报单位");
		headList.add("合同签订单位");
		headList.add("合同到货地址");
		headList.add("合同金额");
		headList.add("合同质保金额");
		headList.add("合同质保日期");
		headList.add("合同开票金额");
		headList.add("合同已收金额");
		headList.add("合同应收金额");
		headList.add("到货总金额");
		headList.add("验收总金额");
		headList.add("操作人");
		ExcelCreate s = new ExcelCreate();
		s.createSheet("销售合同报表");
		s.addHeader(headList, true);
		
		Map params = new HashMap();
		//params.put("in_planStatus", "04");
		if(orgId!=0){
			params.put("id_orgId", orgId);
		}
		if( contractNum!=null && !contractNum.equals("") ){
			params.put("contractNum", contractNum);
		}
		if(useCompId!=null)
			params.put("id_reportCompId_sc", useCompId);
		if(sDate!=null && !"".equals(sDate.trim())  && eDate!=null && !"".equals(eDate.trim()) ){
			params.put("dy_p.signingDate", " between '"+sDate+"' and '"+eDate+"'");
		}
		PageModel pm = service.getSalesContractList(0,0,params,false);
		List<SalesContractArrivalBean> l = pm.getList();
//		User u=(User) ServletActionContext.getRequest().getSession().getAttribute("login");
//		List<List<String>> rowList = eservice.findSalesContract(orgId,contractNum,u.getAuth());
//		for (int i = 0; i < rowList.size(); i++) {
//			s.addRow(rowList.get(i));
//		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HSSFCellStyle dtStyle  = s.setdtStyle();
		HSSFCellStyle dateStyle = s.setdateStyle();
		for (int i = 0; i < pm.getList().size(); i++) {
			List list = new ArrayList();
			list.add(i+1);
			list.add(l.get(i).getSalesContractName());
			list.add(l.get(i).getContractNum() );
			if(l.get(i).getSigningDate() !=null)
				list.add(sdf.format(l.get(i).getSigningDate()));
			else
				list.add("");
			list.add(l.get(i).getReportCompName() );
			list.add(l.get(i).getSignComp() );
			list.add(l.get(i).getArrivalAddress() );
			list.add(l.get(i).getContractMoney() );
			list.add(l.get(i).getQualityMoney());
			if(l.get(i).getQualityDate() !=null)
				list.add(sdf.format(l.get(i).getQualityDate()));
			else
				list.add("");
			list.add(l.get(i).getTotalContractInvoiceMoney() );
			list.add(l.get(i).getTotalcontractReceivedMoney());
			list.add(l.get(i).getContractReceivableMoney());
			list.add(l.get(i).getArrivalMoney() );
			list.add(l.get(i).getAcceptanceMoney() );
			if(l.get(i).getPerson() !=null)
				list.add(l.get(i).getPerson().getName() );
			else
				list.add("");
			
			s.addRow(list,dtStyle,dateStyle);
		}
		Date now = new Date();
		String date = sdf.format(now);
		String fileName ="销售合同报表" + date + ".xls";
		//excelCreateService.exportExcel(file);
		result=ExportAction.exportExcel(fileName, s);
		return null;
	}

	// 采购合同报表生成
	public String exportProcurementContract() throws Exception {
		// 写入excel
		List<String> headList = new ArrayList<String>();
		headList.add("系统ID");
		headList.add("采购合同名称");
		headList.add("采购合同编号");
		headList.add("合同签订时间");
		headList.add("计划提报单位");
		headList.add("合同执行方式");
		headList.add("合同金额");
		headList.add("预付金额");
		headList.add("合同质保金额");
		headList.add("合同质保日期");
		headList.add("供应商");
		headList.add("合同到货日期");
		headList.add("采购方案");
		headList.add("已付总金额");
		headList.add("挂帐总金额");
		headList.add("应付金额");
		headList.add("到货金额");
		headList.add("验收金额");
		headList.add("备注");
		headList.add("部门");
		headList.add("操作人");
		ExcelCreate s = new ExcelCreate();
		s.createSheet("采购合同报表");
		s.addHeader(headList, true);
//		List<List<String>> rowList = eservice.findProcurementContract(orgId,contractNum);
//		for (int i = 0; i < rowList.size(); i++) {
//			s.addRow(rowList.get(i));
//		}
		Map params = new HashMap();
		if(orgId!=0)
			params.put("id_orgId", orgId);
		if(contractNum!=null&&!"".equals(contractNum.trim()))
			params.put("contractNum", contractNum);
		if(useCompId!=null)
			params.put("id_reportCompId_pc", useCompId);
		if(sDate!=null && !"".equals(sDate.trim())  && eDate!=null && !"".equals(eDate.trim()) ){
			params.put("dy_p.signingDate", " between '"+sDate+"' and '"+eDate+"'");
		}
		//PageModel pm = service.searchContracts(params,offset, pageSize);
		PageModel pm = cservice.getProcurementList(0,0, params, false);
		List<ProcurementArrivalBean> l=pm.getList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HSSFCellStyle dtStyle  = s.setdtStyle();
		HSSFCellStyle dateStyle = s.setdateStyle();
		for (int i = 0; i < l.size(); i++) {
			List list = new ArrayList();
			list.add(i+1);
			list.add(l.get(i).getContractName());
			list.add(l.get(i).getContractNum() );
			if(l.get(i).getSigningDate() !=null)
				list.add(sdf.format(l.get(i).getSigningDate()));
			else
				list.add("");
			list.add(l.get(i).getReportCompName() );
			if(l.get(i).getContractExecutionWay() !=null)
				list.add(statusName.getContractExecutionWay(l.get(i).getContractExecutionWay()) );
			else
				list.add("");
			list.add(l.get(i).getTotalMoney());
			list.add(l.get(i).getAdvance());
			list.add(l.get(i).getQualityMoney());
			if(l.get(i).getQualityDate() !=null)
				list.add(sdf.format(l.get(i).getQualityDate()));
			else
				list.add("");
			list.add(l.get(i).getSuppliersName() );
			if(l.get(i).getArrivalDate() !=null)
				list.add(sdf.format(l.get(i).getArrivalDate()));
			else
				list.add("");
			if(l.get(i).getProcurementWay() !=null)
				list.add(statusName.getProcurementWay(l.get(i).getProcurementWay()) );
			else
				list.add("");
			double temp=0;
			if(l.get(i).getPayed()!=null){
				String a = "&";
				String[] str = l.get(i).getPayed().split(",");
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
			list.add(temp);
			double t=cservice.searchContractcreditMoney(l.get(i).getId());
			list.add(t);
			list.add(l.get(i).getShouldPayment());
			list.add(l.get(i).getArrivalMoney() );
			list.add(l.get(i).getAcceptanceMoney() );
			list.add(l.get(i).getRemark());
			list.add(l.get(i).getOrgName());
			list.add(l.get(i).getPerson().getName() );
			s.addRow(list,dtStyle,dateStyle);
		}
		Date now = new Date();
		String date = sdf.format(now);
		String fileName ="采购合同报表" + date + ".xls";
		//excelCreateService.exportExcel(file);
		result=ExportAction.exportExcel(fileName, s);
		return null;
	}
	public String exportPlan() throws Exception {
		
		List<String> headList = new ArrayList<String>();
		headList.add("序号");
		headList.add("使用单位");
		headList.add("物资集团编号");
		headList.add("原始计划号");
		headList.add("物资名称");
		headList.add("单位");
		headList.add("数量");
		headList.add("预算金额");
		headList.add("变更日期");
		headList.add("内容");
		headList.add("金额");
		headList.add("采购形式");
		headList.add("签报日期");
		headList.add("寻源公布日期");
		headList.add("签报日期");
		headList.add("执行方式");
		headList.add("签订日期");
		headList.add("合同编号");
		headList.add("合同要求到货时间");
		headList.add("合同金额");
		headList.add("供应商");
		headList.add("到货数量");
		headList.add("验货数量");
		headList.add("到货金额");
		headList.add("验收金额");
		headList.add("负责人");
		
		ExcelCreate s = new ExcelCreate();
		s.createSheet("计划执行明细表");
		s.addHeader(headList, true);
		User u=(User) ServletActionContext.getRequest().getSession().getAttribute("login");
		//List<List<String>> rowList = eservice.findPlanSheet(orgId,oldPlanNum,u.getAuth());
		
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
		
		PageModel  pm = pservice.getPlanStatusList(0, 0, params, true);
		// ExcelCreate workbook=new ExcelCreate();
		List<ProcurementContractBean> l = pm.getList();
		for (int i = 0; i < pm.getList().size(); i++) {
			List list = new ArrayList();
			list.add(i);
			list.add(l.get(i).getReportComp());
			//s.addRow(l.get(i).get);
		}
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(now);
		String fileName ="计划执行明细表" + date + ".xls";
		result=ExportAction.exportExcel(fileName, s);

		return "planSheet_success";
		
	}
	
	// 统计表
		public String getReportCompListExport() throws Exception {

			// 写入excel
			// 表头
			List<String> headList = new ArrayList<String>();
			headList.add("序号");
//			headList.add("部门");
			headList.add("计划提报单位");
			headList.add("采购计划接收金额");
			headList.add("公开招标");
			headList.add("邀请招标");
			headList.add("竞争性谈判");
			headList.add("询比价");
			headList.add("单一来源");
			headList.add("合计");
			headList.add("节支");
			headList.add("采购合同份数");
			headList.add("采购合同金额");
			headList.add("销售合同份数");
			headList.add("销售合同金额");
			headList.add("到货金额");
			headList.add("回款金额");
			ExcelCreate s = new ExcelCreate();
			s.createSheet("需求单位统计表");
			s.addHeader(headList, true);
			//s.hebing(1, 1, 1, 2);
			//User u=(User) ServletActionContext.getRequest().getSession().getAttribute("login");
			//List<List<String>> rowList = eservice.findPlanSheet(orgId,oldPlanNum,u.getAuth());
			// ExcelCreate workbook=new ExcelCreate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Map params = new HashMap();
//			if(orgId!=0)
//				params.put("id_oid", orgId);
//			if(useCompId!=null)
//				params.put("id_reportCompId", useCompId);
//			if(sDate!=null && !"".equals(sDate.trim())  && eDate!=null && !"".equals(eDate.trim()) ){
//				params.put("dy_p.planReceiptDate", " between '"+sDate+"' and '"+eDate+"'");
//				//params.put("dy_p.planReceiptDate", " <='"+eDate+"' ");
//			}
			if(useCompId==null){
				useCompId=0;
			}
			PageModel  pm = pservice.getReportCompList(0,0,params,false,sDate,eDate,orgId,useCompId);
			
			// ExcelCreate workbook=new ExcelCreate();
			List<ReportCompStatisticsBean> l = pm.getList();
			HSSFCellStyle dtStyle  = s.setdtStyle();
			HSSFCellStyle dateStyle = s.setdateStyle();
			String orgName = "";
			for (int i = 0; i < pm.getList().size(); i++) {
				List list = new ArrayList();
				list.add(i+1);
//				list.add(l.get(i).getOrgName() );
				list.add(l.get(i).getReportCompName());
				list.add(l.get(i).getBudget() );
				list.add(l.get(i).getApm() );
				list.add(l.get(i).getBpm() );
				list.add(l.get(i).getCpm() );
				list.add(l.get(i).getDpm() );
				list.add(l.get(i).getEpm() );
				list.add(l.get(i).getProcurementMoney() );
				list.add(l.get(i).getJz() );
				list.add(l.get(i).getContractCount() );
				list.add(l.get(i).getContractMoney());
				list.add(l.get(i).getSalesCount() );
				list.add(l.get(i).getSalesMoney() );
				list.add(l.get(i).getArrivalMoney() );
				list.add(l.get(i).getTotalcontractReceivedMoney() );
				s.addRow(list,dtStyle,dateStyle);
			}
//			if(l!=null && l.size()>0){
//				if(!"".equals(l.get(0).getOrgName())){
//					orgName = l.get(0).getOrgName();
//				}else{
//					orgName = "所有部门";
//				}
//			}
			Date now = new Date();
			
			String date = sdf.format(now);
			String fileName ="需求单位统计表_"+ date + ".xls";
			result=ExportAction.exportExcel(fileName, s);

			return null;
		}
		
	public final static String exportExcel(String fileName, ExcelCreate s) {
		String result = "系统提示：Excel文件导出成功！";
		// 以下开始输出到EXCEL
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			HttpServletResponse response = ServletActionContext.getResponse();
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end

			s.exportExcel(os);

		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}

}
