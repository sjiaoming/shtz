package com.shtz.model;

import java.util.Date;
import java.util.Set;

/**
 * �ɹ���ͬ
 * @author sjm
 * @hibernate.class table="t_procurementcontract"  
 */
public class ProcurementContract {

	/**
	 * @hibernate.id
	 * generator-class = "native"
	 */
	private int id;
	/**
	 * ��ͬ���
	 * @hibernate.property
	 * not-null="true"
	 * unique="true"
	 */
	private String contractNum;
	/**
	 * ��Ӧ��id
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private Integer suppliersId;
	/**
	 * ��Ӧ��name
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private String suppliersName;
	/**
	 * ��ִͬ�з�ʽ
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private String contractExecutionWay;
	/**
	 * Ԥ����
	 * @hibernate.property
	 * 
	 */
	private Double advance;
	/**
	 * ҵ��id
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private Integer orgId;
	/**
	 * ҵ��������
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private String orgName;
	
	/**
	 * ��ͬǩ������
	 * @hibernate.property
	 * 
	 */
	private Date signingDate;
	/**
	 * ��ͬ��������
	 * @hibernate.property
	 * 
	 */
	private Date arrivalDate;
	/**
	 * ��ͬ�ܶ�
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private Double totalMoney;
	/**
	 * �ɹ�����
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private String procurementWay;
	/**
	 * ��ͬ�ʱ����
	 * @hibernate.property
	 * 
	 * 
	 */
	private Double qualityMoney;
	/**
	 * ��ͬ�ʱ�����
	 * @hibernate.property
	 * 
	 * 
	 */
	private Date qualityDate;
	/**
	 * ��ͬδ�����
	 * @hibernate.property
	 * 
	 * 
	 */
	private Double noPayment;
	/**
	 * ��ͬӦ�����
	 * @hibernate.property
	 * 
	 * 
	 */
	private Double shouldPayment;
	/**
	 * ��ͬ��ע
	 * @hibernate.property
	 * 
	 * 
	 */
	private String remark;
	
	/**
	 * ��ͬ�Ѹ����
	 * @hibernate.property
	 * 
	 * 
	 */
	private String payed;
	
	/**
	 * �ƻ�
	 * @hibernate.set inverse = "true"
	 * @hibernate.key column = "pid"
	 * @hibernate.one-to-many
	 *  class = "com.shtz.model.Plan"
	 */
	private Set<Plan> plan;
	/**
	 * ����
	 * @hibernate.set inverse = "true"
	 * @hibernate.key column = "cid"
	 * @hibernate.one-to-many
	 *  class = "com.shtz.model.ContractCredit"
	 */
	private Set<ContractCredit> contractCredit;

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
	 * ��ͬ����
	 * @hibernate.property
	 * 
	 */
	private String contractName;
	/**
	 * ʹ�õ�λ
	 * @hibernate.property
	 * 
	 */
	private String reportCompId_pc;
	/**
	 * ʹ�õ�λ	
	 * @hibernate.property
	 * 
	 */
	private String reportCompName_pc;
	

	public String getReportCompId_pc() {
		return reportCompId_pc;
	}
	public void setReportCompId_pc(String reportCompId_pc) {
		this.reportCompId_pc = reportCompId_pc;
	}
	public String getReportCompName_pc() {
		return reportCompName_pc;
	}
	public void setReportCompName_pc(String reportCompName_pc) {
		this.reportCompName_pc = reportCompName_pc;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
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
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}


	public Integer getSuppliersId() {
		return suppliersId;
	}
	public void setSuppliersId(Integer suppliersId) {
		this.suppliersId = suppliersId;
	}
	public String getSuppliersName() {
		return suppliersName;
	}
	public void setSuppliersName(String suppliersName) {
		this.suppliersName = suppliersName;
	}

	public Double getAdvance() {
		return advance;
	}
	public void setAdvance(Double advance) {
		this.advance = advance;
	}

	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Date getSigningDate() {
		return signingDate;
	}
	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getProcurementWay() {
		return procurementWay;
	}
	public void setProcurementWay(String procurementWay) {
		this.procurementWay = procurementWay;
	}

	public Double getNoPayment() {
		return noPayment;
	}
	public void setNoPayment(Double noPayment) {
		this.noPayment = noPayment;
	}
	public Double getShouldPayment() {
		return shouldPayment;
	}
	public void setShouldPayment(Double shouldPayment) {
		this.shouldPayment = shouldPayment;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set<Plan> getPlan() {
		return plan;
	}
	public void setPlan(Set<Plan> plan) {
		this.plan = plan;
	}
	public Set<ContractCredit> getContractCredit() {
		return contractCredit;
	}
	public void setContractCredit(Set<ContractCredit> contractCredit) {
		this.contractCredit = contractCredit;
	}
	public String getContractExecutionWay() {
		return contractExecutionWay;
	}
	public void setContractExecutionWay(String contractExecutionWay) {
		this.contractExecutionWay = contractExecutionWay;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Double getQualityMoney() {
		return qualityMoney;
	}
	public void setQualityMoney(Double qualityMoney) {
		this.qualityMoney = qualityMoney;
	}
	public Date getQualityDate() {
		return qualityDate;
	}
	public void setQualityDate(Date qualityDate) {
		this.qualityDate = qualityDate;
	}
	public void setPayed(String payed) {
		this.payed = payed;
	}
	public String getPayed() {
		return payed;
	}

	
	
}
