package com.shtz.model;

import java.util.Date;
import java.util.Set;

/**
 * ���ۺ�ͬ
 * @author sjm
 * @hibernate.class table="t_salescontract"   
 */
public class SalesContract {
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
	 * ��ͬǩ������
	 * @hibernate.property
	 * 
	 */
	private Date  signingDate;
	/**
	 * ʹ�õ�λ
	 * @hibernate.property
	 * 
	 */
	private Integer reportCompId_sc;
	/**
	 * ʹ�õ�λ	
	 * @hibernate.property
	 * 
	 */
	private String reportCompName_sc;
	/**
	 * ��ͬǩ����λ	
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private String signComp;
	/**
	 * ��ͬǩ�����
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private String signFlag;
	
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
	 * ��ͬ���
	 * @hibernate.property
	 *  
	 */
	private Double contractMoney;
	/**
	 * ��ͬ���ս��
	 * @hibernate.property
	 *  
	 */
	private String contractReceivedMoney;
	/**
	 * ��ͬ�����ܶ�
	 * @hibernate.property
	 *  
	 */
	private Double totalcontractReceivedMoney;
	/**
	 * ��ͬӦ�ս��
	 * @hibernate.property
	 *  
	 */
	private Double contractReceivableMoney;
	
	/**
	 * @hibernate.set inverse = "true"
	 * @hibernate.key column = "sid"
	 * @hibernate.one-to-many
	 *  class = "com.shtz.model.Plan"
	 */
	private Set<Plan> plan;
	/**
	 * ҵ��id
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private int orgId;
	/**
	 * ҵ�������
	 * @hibernate.property
	 * not-null="true"
	 * 
	 */
	private String orgName;
	/**
	 * ��ͬ��Ʊ�ܶ�
	 * @hibernate.property
	 *  
	 */
	private Double totalContractInvoiceMoney;
	
	/**
	 * ��ͬ��Ʊ���
	 * @hibernate.set inverse = "true"
	 * @hibernate.key column = "sid"
	 * @hibernate.one-to-many
	 *  class = "com.shtz.model.Billing"
	 */
	private Set<Billing> billing;

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
	 * ��ͬ���
	 * @hibernate.property
	 * 
	 */
	private String salesContractName;
	/**
	 * @see ������ַ
	 * @hibernate.property
	 * 
	 */
	private String arrivalAddress;

	
	public String getArrivalAddress() {
		return arrivalAddress;
	}

	public void setArrivalAddress(String arrivalAddress) {
		this.arrivalAddress = arrivalAddress;
	}

	public String getSalesContractName() {
		return salesContractName;
	}

	public void setSalesContractName(String salesContractName) {
		this.salesContractName = salesContractName;
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

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getSignComp() {
		return signComp;
	}

	public void setSignComp(String signComp) {
		this.signComp = signComp;
	}

	public String getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
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

	public Double getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}

	public Set<Billing> getBilling() {
		return billing;
	}

	public void setBilling(Set<Billing> billing) {
		this.billing = billing;
	}

	public String getContractReceivedMoney() {
		return contractReceivedMoney;
	}

	public void setContractReceivedMoney(String contractReceivedMoney) {
		this.contractReceivedMoney = contractReceivedMoney;
	}



	public Double getTotalcontractReceivedMoney() {
		return totalcontractReceivedMoney;
	}

	public void setTotalcontractReceivedMoney(Double totalcontractReceivedMoney) {
		this.totalcontractReceivedMoney = totalcontractReceivedMoney;
	}

	public Double getContractReceivableMoney() {
		return contractReceivableMoney;
	}

	public void setContractReceivableMoney(Double contractReceivableMoney) {
		this.contractReceivableMoney = contractReceivableMoney;
	}

	public Set<Plan> getPlan() {
		return plan;
	}

	public void setPlan(Set<Plan> plan) {
		this.plan = plan;
	}

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public Double getTotalContractInvoiceMoney() {
		return totalContractInvoiceMoney;
	}

	public void setTotalContractInvoiceMoney(Double totalContractInvoiceMoney) {
		this.totalContractInvoiceMoney = totalContractInvoiceMoney;
	}

	public Integer getReportCompId_sc() {
		return reportCompId_sc;
	}

	public void setReportCompId_sc(Integer reportCompId_sc) {
		this.reportCompId_sc = reportCompId_sc;
	}

	public String getReportCompName_sc() {
		return reportCompName_sc;
	}

	public void setReportCompName_sc(String reportCompName_sc) {
		this.reportCompName_sc = reportCompName_sc;
	}

	@Override
	public String toString() {
		return "SalesContract [id=" + id + ", contractNum=" + contractNum
				+ ", signingDate=" + signingDate + ", reportCompId_sc="
				+ reportCompId_sc + ", reportCompName_sc=" + reportCompName_sc
				+ ", signComp=" + signComp + ", signFlag=" + signFlag
				+ ", qualityMoney=" + qualityMoney + ", qualityDate="
				+ qualityDate + ", contractMoney=" + contractMoney
				+ ", contractReceivedMoney=" + contractReceivedMoney
				+ ", totalcontractReceivedMoney=" + totalcontractReceivedMoney
				+ ", contractReceivableMoney=" + contractReceivableMoney
				+ ", plan=" + plan + ", orgId=" + orgId + ", orgName="
				+ orgName + ", totalContractInvoiceMoney="
				+ totalContractInvoiceMoney + ", billing=" + billing
				+ ", user=" + user + ", person=" + person
				+ ", salesContractName=" + salesContractName
				+ ", arrivalAddress=" + arrivalAddress + "]";
	}
	
	
	
}
