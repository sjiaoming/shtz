<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%@include file="/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--框架必需start-->
<script type="text/javascript" src="<%=path %>/js/jquery-1.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/framework.js"></script>
<link href="<%=path %>/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link  rel="stylesheet" type="text/css" id="skin"  prePath="<%=path %>/"/>
<!--框架必需end-->

<script type='text/javascript' src='<%=path %>/dwr/interface/planManager.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/interface/salesContractManager.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
<script type="text/javascript" src="<%=path %>/js/form/multiselect.js"></script>
<!--截取文字start-->
<script type="text/javascript" src="../../js/text/text-overflow.js"></script>
<!--截取文字end-->
<script src="<%=path %>/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="<%=path %>/js/form/validationEngine.js" type="text/javascript"></script>
<script src="<%=path %>/js/abc.js" type="text/javascript"></script>

<script language="javascript">
/* function ok(){
	var access=false;
	access=$('#form1').validationEngine({returnIsValid:true});
	if(access){
		if(confirm("您确定要提交吗?")){
			this.form1.action = "doModifyPlansales.action";
			this.form1.submit();
			return;
		}
	}
}
 */
function ok(){
	var cn = document.getElementById("contractNum").value;
	var reg=/[\u4E00-\u9FA5]/g;
	cn = cn.replace(/\s/ig,'');
	cn = cn.replace(reg,'');
	document.getElementById("contractNum").value = cn;
	if(cn.length !=0 && cn.length != 14){
		top.Dialog.alert("合同编号非法,请重新输入");
		return;
	}else {
		salesContractManager.findSalesContractListByContractNum(
			cn,
			function(data){
				if(data.length>0){
					top.Dialog.alert("合同编号已存在");
					//document.getElementById("contractNum").value = "";
					return;
				}else{
				
				doSubmit();
				}
		});
	}
}
function doSubmit(){
	var access=false;
		access=$('#form1').validationEngine({returnIsValid:true});
		if(access){
			if(confirm("您确定要提交吗?")){
				this.form1.action = "doModifyPlansales.action";
				this.form1.method="post";
				this.form1.submit();
				return;
			}
		}
}
function validation(id){
	var obj1=document.getElementsByName(id);
	if(obj1.length>1){
		for(var i=0;i<obj1.length;i++){
			if(obj1[i].value!="")
			obj1[i].style.border="";
		}
	}
	if(obj1.length==1){
		var obj=document.getElementById(id);
		if(obj.value!=""){
		obj.style.border="";
		}
	}
}
function setContractMoney(){
	
	var sales = document.getElementsByName("salesMoney");
	var sumContractMoney=0;
	for(var i=0;i<sales.length;i++){
		sumContractMoney=sumContractMoney+Number(sales[i].value);
	}
	document.form1.contractMoney.value=sumContractMoney;
	var   txtRange   =   document.selection.createRange(); 
	txtRange.moveStart(   "character",   document.form1.contractMoney.value.length); 
	txtRange.moveEnd(   "character",   0   ); 
	txtRange.select(); 
	 
}

function setSalesRatio1(){
	var	salesRatio=document.getElementById("salesRatioo");
	var	tags=document.getElementById("tags");
	var temp;	
	if(tags.value!=null && tags.value!=""){
		temp = tags.value.split(',');	
	} 
	if(temp.length > 0){
		for(var i=0; i<temp.length; i++){
			var procurementPrice = document.getElementById(temp[i]+"_procurementPrice").value;
			var PriceRatio = Number(Number(procurementPrice).add((Number(salesRatio.value).div(100)).mul(Number(procurementPrice)))).toFixed(2);
			document.getElementById(temp[i]+"_salesMoney").value = Number(Number(PriceRatio).mul(Number(document.getElementById(temp[i]+"_contractNum").value))).toFixed(2);
		}
			
	}
	setContractMoney();
}


function pm(){
	var cn = document.getElementById("contractNum").value;
	if(cn!=null && cn!=""){
		salesContractManager.findSalesContractListByContractNum(
				cn,
				function(data){
					if(data.length>0){
						top.Dialog.alert("合同编号已存在");
						document.getElementById("contractNum").value = "";
						return;
					}
		});
	}
}
</script>

<body>
<form action="?" id="form1" name="form1" >
<input type="hidden" name="tags" id="tags" value="<%=request.getParameter("tags") %>" />
<input type="hidden" name="salesContract.orgId" value="${person.org.id }" />
<input type="hidden" name="salesContract.signFlag" value="已签订" />
<input type="hidden" name="salesContract.reportCompId_sc" value="${reportCompId }" />
<input type="hidden" name="salesContract.reportCompName_sc" value="${reportCompName }" />
<input type="hidden" name="ccflag" id="ccflag" value="" />
<div class="box1" panelWidth="790">
	<table class="tableStyle"   id="ContractMoney_Calculation">
		<tr>
			<th colspan="10">物资列表</th>
		</tr>
		<tr>
			<!-- <td width="30">ID</td> -->
			<td width="80"> 购物车编号</td>
			<td width="80"> 物料描述</td>
			<td width="80"> 合同签订数量</td>
			<td width="80"> 单价</td>
			<td width="80"> 计划提报单位</td>
			<td width="80"> 到货地址</td>
			<td width="80"> 采购金额(元)</td>
			<td width="80"> 销售金额(元)</td>	
		</tr>
		<c:if test="${!empty planList}">
			<c:forEach items="${planList}" var="plan">  
				<tr >
					<%-- <td align="left"> ${plan.id}</td> --%>
					<td align="left"><span class="text_slice" style="width:70px;" title="${plan.oldPlanNum }">${plan.oldPlanNum }</span></td>
					<td align="left"><span class="text_slice" style="width:140px;" title="${plan.itemsName }">${plan.itemsName }</span></td>
					<td align="left"> ${plan.contractNum }
					<input type="hidden" id="${plan.id}_contractNum" name="scontractNum" value="${plan.contractNum}"/>
					</td>
					<td align="left"><fmt:formatNumber value="${plan.procurementPrice}" pattern="#0.00"/>
					<input type="hidden" id="${plan.id}_procurementPrice" name="sprocurementPrice" value="${plan.procurementPrice}"/>
					</td>
					<td align="left"><span class="text_slice" style="width:50px;" title="${plan.reportComp}">${plan.reportComp}</span></td>
					<td align="left"><span class="text_slice" style="width:50px;" title="${plan.arrivalAddress }">${plan.arrivalAddress }</span></td>
					<td align="left"> <fmt:formatNumber value="${plan.procurementMoney}" type="currency"/>
					<input type="hidden" id="${plan.id}_procurementMoney" name="sprocurementMoney" value="${plan.procurementMoney}"/>
					</td>
					<td align="left" ><input type="text" id="${plan.id}_salesMoney"  class=" validate[required]" name="salesMoney"  style="width:70px;" value="<fmt:formatNumber value="${plan.procurementMoney*plan.salesRatio}" pattern="#0.00"/>"/>
					<input type="hidden" name="pid" value="${plan.id}"/></td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</div>
<div class="box1" panelWidth="790">
	<table class="tableStyle"  useClick="false"  transMode="true" >
		<tr>
			<td>合同名称：<span class="star">*</span></td><td><input type="text" name="salesContract.salesContractName" id="contractName"  class=" validate[required]" style="width:200px;"/></td>
			<td>销售费率：<span class="star">*</span></td><td><input type="text" name="salesRatioo" id="salesRatioo"  class=" validate[required]" onblur="setSalesRatio1()"/>%</td>
		</tr>
		<tr>
			<td>合同编号：<span class="star">*</span></td>
			<td><input type="text" name="salesContract.contractNum" id="contractNum"   watermark="新合同号请勿填写"/>   </td>
			<!-- <td><input type="hidden" name="salesContract.contractNum"/>
			合同编号自动生成
			</td> -->
			<td>合同签订日期:</td><td>
			 <input  id="signingDate" name="salesContract.signingDate"  value="" class="date"/>
			</td>
		</tr>
		<tr>
			<td>合同金额：<span class="star">*</span></td>
			<td ><input type="text" name="contractMoney" id="contractMoney"  class=" validate[required]" onfocus="setContractMoney()"/>(元)</td>
		
			<td>合同签订单位：<span class="star">*</span></td>
			<td ><input type="text" name="salesContract.signComp" id="signComp"  class=" validate[required]"/></td>
		</tr>
		<tr>
			<td>合同质保金额：</td><td><input type="text" name="salesContract.qualityMoney" id="qualityMoney" value="0" />(元)</td>	
			<td>合同质保日期：</td><td>
			<input  id="qualityDate" name="salesContract.qualityDate"  value="" class="date"/>
			</td>			
		</tr>
		<!--
		<tr>
			<td>合同收款日期：</td><td><input  id="signingDate" name="salesContract.gatheringDate"  value="" class="date"/></td>	
			<td></td><td>
			</td>			
		</tr>
		-->
		<tr>
			<td>到货地址：</td><td colspan="3"><textarea style="width:550px;height:50px;" name="salesContract.arrivalAddress"></textarea></td>	
		</tr>
		<tr>
			<td colspan="4" align="center">
			<input type="button"  onclick="ok()" value=" 提 交 "/>
				<!-- <input type="submit" value=" 提 交 "/> -->
				<input type="reset" value=" 重 置 "/>
			</td>
		</tr>
		</table>
</div>
</form>
</body>