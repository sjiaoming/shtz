package com.shtz.util;

public class StatusName {
	
	public String getProcurementWay(String procurementWay){
		String procurementWay1 = "";
		if(procurementWay!=null && !procurementWay.equals("")){
		switch (Integer.parseInt(procurementWay)){
		case 1 :
			procurementWay1 =  "公开招标";
			break;
		case 2 :
			procurementWay1 =  "邀请招标";
			break;
		case 3 :
			procurementWay1 =  "竞争性谈判";
			break;
		case 4 :
			procurementWay1 =  "单一来源";
			break;
		case 5 :
			procurementWay1 =  "寻比价";
			break;
		}
		}
		return procurementWay1;
	}
	
	public String getContractExecutionWay(String contractExecutionWay){
		String contractExecutionWay1 = "";
		if(contractExecutionWay!=null && !contractExecutionWay.equals("")){
		switch (Integer.parseInt(contractExecutionWay)){
		case 1 :
			contractExecutionWay1 =  "统谈统签统付";
			break;
		case 2 :
			contractExecutionWay1 =  "统谈统签分付";
			break;
		case 3 :
			contractExecutionWay1 =  "统谈分签";
			break;
		}
		}
		return contractExecutionWay1;
	}
	
/*	public String getProcurementWay(String procurementWay){
		String procurementWay1 = "";
		if(procurementWay!=null){
		switch (procurementWay){
		case "1" :
			procurementWay1 =  "公开招标";
			break;
		case "2" :
			procurementWay1 =  "邀请招标";
			break;
		case "3" :
			procurementWay1 =  "竞争性谈判";
			break;
		case "4" :
			procurementWay1 =  "单一来源";
			break;
		case "5" :
			procurementWay1 =  "寻比价";
			break;
		}
		}
		return procurementWay1;
	}
	
	public String getContractExecutionWay(String contractExecutionWay){
		String contractExecutionWay1 = "";
		if(contractExecutionWay!=null){
		switch (contractExecutionWay){
		case "1" :
			contractExecutionWay1 =  "统谈统签统付";
			break;
		case "2" :
			contractExecutionWay1 =  "统谈统签分付";
			break;
		case "3" :
			contractExecutionWay1 =  "统谈分签";
			break;
		}
		}
		return contractExecutionWay1;
	}*/
}
