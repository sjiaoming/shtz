package com.shtz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * �ƻ�����
 * @author sjm
 * @hibernate.class table="t_plan" 
 */
public class Plan implements Serializable{
	/**
	 * @hibernate.id
	 * generator-class = "native"
	 */
	private int id;
	/**
	 * �������
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private String itemsName;
	/**
	 * �Ƿ���
	 * @hibernate.property
	 * 
	 * 
	 */
	private String model;
	/**
	 * ԭ�ƻ���
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private String oldPlanNum;
	/**
	 * ʹ�õ�λ
	 * @hibernate.property
	 * 
	 * 
	 */
	private String reportComp;
	/**
	 * ʹ�õ�λID
	 * @hibernate.property
	 * 
	 * 
	 */
	private Integer reportCompId;
	/**
	 * ��Ŀ��
	 * @hibernate.property
	 * 
	 * 
	 */
	private String planNum;
	/**
	 * �ƻ���������
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private Date planReceiptDate;
	/**
	 * ��λ
	 * @hibernate.property
	 * 
	 * 
	 */
	private String unit;
	/**
	 * �ƻ�����
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private Double num;
	/**
	 * ��ͬǩ������
	 * @hibernate.property
	 * 
	 * 
	 */
	private Double contractNum;
	/**
	 * �ɹ�����
	 * @hibernate.property
	 *
	 */
	private Double planPrice;
	/**
	 * ����ʱ��
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private Date arrivalDate;
	/**
	 * �����ص�
	 * @hibernate.property
	 * 
	 * 
	 */
	private String arrivalSite;
	/**
	 * Ԥ����
	 * @hibernate.property
	 * 
	 * 
	 */
	private Double budget;
	/**
	 * ���״̬
	 * @hibernate.property
	 * 
	 * 
	 */
	private String procurementFlag;
	/**
	 * ��ע
	 * @hibernate.property
	 * 
	 * 
	 */
	private String remark;
	
	/**
	 * �ɹ�����
	 * @hibernate.property
	 * 
	 * 
	 */
	private String procurementWay;

	/**
	 * �ɹ�������
	 * @hibernate.property
	 * 
	 * 
	 */
	private String procurementWayNum;
	/**
	 * �ɹ�ǩ������
	 * @hibernate.property
	 * 
	 * 
	 */
	private Date procurementDate;
	/**
	 * �ɹ�������ע
	 * @hibernate.property
	 * 
	 * 
	 */
	private String procurementRemark;

	/**
	 * ѰԴ��������
	 * @hibernate.property
	 * 
	 * 
	 */
	private Date searchAnnouncedDate;
	/**
	 * ѰԴǩ������
	 * @hibernate.property
	 * 
	 */
	private Date searchDate;
	/**
	 * ��ִͬ�з�ʽ
	 * @hibernate.property
	 * 
	 */
	private String contractExecutionWay;
	/**
	 * ��ͬ���
	 * @hibernate.property
	 *  
	 */
	private Double contractMoney;

	/**
	 * �ɹ�����
	 * @hibernate.property
	 *
	 */
	private Double procurementPrice;
	/**
	 * �ɹ����
	 * @hibernate.property
	 * 
	 */
	private Double procurementMoney;
	/**
	 * ���۽��
	 * @hibernate.property
	 *  
	 */
	private Double salesMoney;
	
	/**
	 * ���۱���
	 * @hibernate.property
	 *  
	 */
	private Double salesRatio;
	/**
	 * @see ���۵���
	 * @hibernate.property
	 *  
	 */
	private Double salesPrice;
	/**
	 * ������
	 * @hibernate.one-to-one
	 * property-ref="plan"
	 * cascade="all"
	 */
	private Change change;
	
	/**
	 * �ɹ���ͬ
	 * @hibernate.many-to-one
	 * column = "pid"
	 * cascade="save-update"
	 */
	private ProcurementContract procurementContract;
	
	/**
	 * ���ۺ�ͬ
	 * @hibernate.many-to-one
	 * column = "sid"
	 */
	private SalesContract salesContract;
	/**
	 * ��������
	 * @hibernate.set inverse = "true"
	 * @hibernate.key column = "aid"
	 * @hibernate.one-to-many
	 *  class = "com.shtz.model.ArrivalItems"
	 */
	private Set<ArrivalItems> arrivalItems;
	
	/**
	 * �ƻ���������
	 * @hibernate.many-to-one
	 * column = "oid"
	 */
	private Organization org;
	
	/**
	 * ���ϱ��
	 * @hibernate.property
	 * 
	 * 
	 */
	private String itemsNum;
	
	/**
	 * �û�
	 * @hibernate.many-to-one
	 * column = "uid"
	 */
	private User user;
	
	/**
	 * ��Ա
	 * @hibernate.many-to-one
	 * column = "psid"
	 */
	private Person person;
	/**
	 * �ƻ���״̬
	 * @hibernate.property
	 * 01:δ����
	 * 02:�Ѳɹ�����
	 * 03:��ѰԴ
	 * 04:�Ѳɹ�
	 * 05:������
	 *  06:��ȡ��
	 */
	private String planStatus;
	/**
	 * @hibernate.set inverse = "true"
	 * @hibernate.key column = "pcid"
	 * @hibernate.one-to-many
	 *  class = "com.shtz.model.Plan"
	 */
	private Set<Plan> children;
	/**
	 * @hibernate.many-to-one
	 * column = "pcid"  not-found="ignore"
	 */
	private Plan parent;
	
	/**
	 * �ɹ��ƶ�����
	 * @hibernate.property
	 * 
	 * 
	 */
	private Date procurementSigningleDate;
	/**
	 * 
	 * @hibernate.property
	 * @see ����������
	 *  
	 */
	private Double arrivalNumTotal;
	/**
	 * 
	 * @hibernate.property
	 * @see ����������
	 *  
	 */
	private Double acceptanceNumTotal;
	/**
	 * 
	 * @hibernate.property
	 * @see �����ܽ��
	 *  
	 */
	private Double arrivalMoneyTotal;
	/**
	 * 
	 * @hibernate.property
	 * @see �����ܽ��
	 *  
	 */
	private Double acceptanceMoneyTotal;
	/**
	 * @ee ��Ʊ��ϸ
	 * @hibernate.set inverse = "true"
	 * @hibernate.key column = "plan_id"
	 * @hibernate.one-to-many
	 *  class = "com.shtz.model.BillingDetail"
	 */
	private Set<BillingDetail> billingDetail;
	/**
	 * @hibernate.property
	 * @see ������ַ
	 */
	private String arrivalAddress;
	/**
	 * @see ����
	 * @hibernate.property
	 */
	private String dType;
	/**
	 * @see ����
	 * @hibernate.property
	 */
	private String zType;
	/**
	 * @see С��
	 * @hibernate.property
	 */
	private String xType;
	/**
	 * @see ���
	 * @hibernate.property
	 */
	private String bNumber;
	/**
	 * @see ������
	 * @hibernate.property
	 */
	private String faNumber;
	/**
	 * �ɹ��������
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private String procurementName;
	
	
	public String getbNumber() {
		return bNumber;
	}

	public void setbNumber(String bNumber) {
		this.bNumber = bNumber;
	}

	public String getFaNumber() {
		return faNumber;
	}

	public void setFaNumber(String faNumber) {
		this.faNumber = faNumber;
	}

	public String getdType() {
		return dType;
	}

	public void setdType(String dType) {
		this.dType = dType;
	}

	public String getzType() {
		return zType;
	}

	public void setzType(String zType) {
		this.zType = zType;
	}

	public String getxType() {
		return xType;
	}

	public void setxType(String xType) {
		this.xType = xType;
	}

	public String getArrivalAddress() {
		return arrivalAddress;
	}

	public void setArrivalAddress(String arrivalAddress) {
		this.arrivalAddress = arrivalAddress;
	}

	public Set<BillingDetail> getBillingDetail() {
		return billingDetail;
	}

	public void setBillingDetail(Set<BillingDetail> billingDetail) {
		this.billingDetail = billingDetail;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Double getAcceptanceNumTotal() {
		return acceptanceNumTotal;
	}

	public void setAcceptanceNumTotal(Double acceptanceNumTotal) {
		this.acceptanceNumTotal = acceptanceNumTotal;
	}

	public Double getArrivalMoneyTotal() {
		return arrivalMoneyTotal;
	}

	public void setArrivalMoneyTotal(Double arrivalMoneyTotal) {
		this.arrivalMoneyTotal = arrivalMoneyTotal;
	}

	public Double getAcceptanceMoneyTotal() {
		return acceptanceMoneyTotal;
	}

	public void setAcceptanceMoneyTotal(Double acceptanceMoneyTotal) {
		this.acceptanceMoneyTotal = acceptanceMoneyTotal;
	}

	public Double getArrivalNumTotal() {
		return arrivalNumTotal;
	}

	public void setArrivalNumTotal(Double arrivalNumTotal) {
		this.arrivalNumTotal = arrivalNumTotal;
	}

	public Date getProcurementSigningleDate() {
		return procurementSigningleDate;
	}

	public void setProcurementSigningleDate(Date procurementSigningleDate) {
		this.procurementSigningleDate = procurementSigningleDate;
	}

	public Integer getReportCompId() {
		return reportCompId;
	}

	public void setReportCompId(Integer reportCompId) {
		this.reportCompId = reportCompId;
	}

	public Set<Plan> getChildren() {
		return children;
	}

	public void setChildren(Set<Plan> children) {
		this.children = children;
	}

	public Plan getParent() {
		return parent;
	}

	public void setParent(Plan parent) {
		this.parent = parent;
	}

	public Double getPlanPrice() {
		return planPrice;
	}

	public void setPlanPrice(Double planPrice) {
		this.planPrice = planPrice;
	}

	public Double getContractNum() {
		return contractNum;
	}

	public void setContractNum(Double contractNum) {
		this.contractNum = contractNum;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getItemsNum() {
		return itemsNum;
	}

	public void setItemsNum(String itemsNum) {
		this.itemsNum = itemsNum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Double getProcurementPrice() {
		return procurementPrice;
	}

	public void setProcurementPrice(Double procurementPrice) {
		this.procurementPrice = procurementPrice;
	}

	public Double getProcurementMoney() {
		return procurementMoney;
	}

	public void setProcurementMoney(Double procurementMoney) {
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

	public ProcurementContract getProcurementContract() {
		return procurementContract;
	}

	public void setProcurementContract(ProcurementContract procurementContract) {
		this.procurementContract = procurementContract;
	}

	public SalesContract getSalesContract() {
		return salesContract;
	}

	public void setSalesContract(SalesContract salesContract) {
		this.salesContract = salesContract;
	}

	public Set<ArrivalItems> getArrivalItems() {
		return arrivalItems;
	}

	public void setArrivalItems(Set<ArrivalItems> arrivalItems) {
		this.arrivalItems = arrivalItems;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
	public String getProcurementName() {
		return procurementName;
	}
	public void setProcurementName(String procurementName) {
		this.procurementName = procurementName;
	}

	public String getProcurementWayNum() {
		return procurementWayNum;
	}

	public void setProcurementWayNum(String procurementWayNum) {
		this.procurementWayNum = procurementWayNum;
	}

	
	
	
}
