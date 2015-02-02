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
<!--截取文字start-->
<script type="text/javascript" src="../../js/text/text-overflow.js"></script>
<!--截取文字end-->
<script src="<%=path %>/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="<%=path %>/js/form/validationEngine.js" type="text/javascript"></script>
<script src="<%=path %>/js/abc.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=path %>/js/form/multiselect.js"></script>

<script type='text/javascript' src='dwr/interface/procurementContractManager.js'></script>
<script type='text/javascript' src='dwr/interface/planManager.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<script language="javascript">
$(function(){
	getSum();
})
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
			procurementContractManager.findProcurementContractByContractNum(
					cn,
					function(data){
						if(data.length>0){
							top.Dialog.alert("合同编号已存在");
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
			var advance = document.getElementById("advance");
			var totalMoney =  document.getElementById("totalMoney");
			var qualityMoney = document.getElementById("qualityMoney");
			var su = Number(advance.value).add(Number(qualityMoney.value));
			if(su > Number(totalMoney.value)){
				top.Dialog.alert("预付＋质保 不能大于 合同总额");
				access=false;
			}else{
				access = true;
			}
		}
		if(access){
			if(confirm("您确定要提交吗?")){
				this.form1.action = "addContract.action";
				this.form1.method="post";
				this.form1.submit();
			}
		}
}
function setProcurementMoney(pid,num){
	document.getElementById("procurementMoney"+pid).value = (Number(document.getElementById("procurementPrice"+pid).value)).mul(Number(document.getElementById("planContractNum"+pid).value));
}

function getSum(feil){
	var m = document.getElementsByName("procurementMoney");
	var i = 0;
	var temp = 0;
		for(i=0;i<m.length;i++){
			//temp +=parseFloat(m[i].value);
			temp = Number(temp).add(Number(m[i].value));
		}
	this.form1.totalMoney.value= temp;
	var   txtRange   =   document.selection.createRange(); 
	txtRange.moveStart(   "character",   feil.value.length); 
	txtRange.moveEnd(   "character",   0   ); 
	txtRange.select(); 
}


</script>

<body>
<form action="?" id="form1" name="form1" method="post" >
<input type="hidden" name="orgId" value="${person.org.id }"/>
<input type="hidden" name="noPayment" value="0"/>
<input type="hidden" name="ccflag" id="ccflag" value="" />
	<table class="tableStyle" >
			<tr>
				<th>ID</th>
				<th>购物车编号</th>
				<th>物料描述</th>
				<th>计划数量</th>
				<th>合同数量</th>
				<th>使用单位</th>
				<th>计划单价</th>
				<th>预算金额(元)</th>
				<th>采购单价</th>
				<th>采购金额(元)</th>
			</tr>
		<c:if test="${!empty plist}">
			<c:forEach items="${plist }" var="p">
			<tr>
				<td>${p.id}</td>
				<td>${p.oldPlanNum}</td>
				<td><span class="text_slice" style="width:100px;" title="${p.itemsName}">${p.itemsName }</span></td>
				<td>${p.num}</td>
				<td><input type="text" id="planContractNum${p.id}" name="planContractNum" value="${p.num}" class="validate[required]"  onblur="setProcurementMoney('${p.id}');" style="width:60px;"/></td>
				<td><span class="text_slice" style="width:50px;" title="${p.reportComp}">${p.reportComp}</span></td>
				<td><fmt:formatNumber value="${p.planPrice}" type="currency"/></td>
				<td><fmt:formatNumber value="${p.budget}" type="currency"/></td>
				<td><fmt:formatNumber value="${p.procurementPrice}" type="currency"/></td>
				<td><input type="text" id="procurementMoney${p.id}" name="procurementMoney" value="<fmt:formatNumber value="${p.num * p.procurementPrice}" pattern="#0.00"/>"  onblur="setProcurementMoney('${p.id}');" style="width:90px;" />
				<input type="hidden" name="pid" value="${p.id}"/>
				<input type="hidden" id="procurementPrice${p.id}" name="procurementPrice" value="${p.procurementPrice}"/>
				<input type="hidden" id="reportCompId_${p.id}" name="reportCompId" value="${p.reportCompId}"/>
				<input type="hidden" id="reportComp_${p.id}" name="reportComp" value="${p.reportComp}"/>
				</td>
				
			</tr>
			</c:forEach>
		</c:if>
	</table>
	<div class="box2" panelWidth="770" panelTitle="采购合同台帐" showStatus="false" roller="true" >
	<table class="tableStyle" useClick="false"  transMode="true" >
		<tr>
			<td>合同名称：<span class="star">*</span></td>
			<td colspan="2"><input type="text" name="contractName"  id="contractName" class="validate[required]" style="width:200px;" /></td>
		</tr>
		<tr>
			<td>合同编号：<span class="star">*</span></td>
			<td><input type="text" name="contractNum"  id="contractNum"  watermark="新合同号请勿填写"/></td>
			<!-- <td>
			<input type="hidden" name="contractNum"/>
			合同编号自动生成
			</td> -->
			<td>合同总额：<span class="star">*</span></td>
			<td><input id="totalMoney" type="text" name="totalMoney" onfocus="getSum(this);" value="0"  class="validate[required]"/>(元)</td>
		</tr>
		<tr>
			<td>供应商：<span class="star">*</span></td>
			<td>
				<select  name="suppliersId" id="suppliersId" class="validate[required]" autoWidth="true" style="width:200px;" multiple>
					<option value="" selected="selected">请选择</option>
						<c:if test="${!empty sessionSuppliers}">
						 	<c:forEach items="${sessionSuppliers }" var="supplier">  
								<option value="${supplier.id}"  rel="exclusive" >${supplier.name }</option>					 
							</c:forEach>
						</c:if>
						<option value="0" rel="headernocb"></option>
				 </select>
			</td>
			<td>采购方案：</td>
			<td>
			<c:if test="${procurementWay == '1' }">公开招标</c:if>
			<c:if test="${procurementWay == '2' }">邀请招标</c:if>
			<c:if test="${procurementWay == '3' }">竞争性谈判</c:if>
			<c:if test="${procurementWay == '4' }">单一来源</c:if>
			<c:if test="${procurementWay == '5' }">寻比价</c:if>
			<input type="hidden" id="procurementWay" name="procurementWay" value="${procurementWay }" />
			</td>
		</tr>

		<tr>
			<td width="10%">合同执行方式：</td>
			<td>
			<c:if test="${contractExecutionWay == '1' }">统谈统签统付</c:if>
			<c:if test="${contractExecutionWay == '2' }">统谈统签分付</c:if>
			<c:if test="${contractExecutionWay == '3' }">统谈分签</c:if>
			<input type="hidden" id="contractExecutionWay" name="contractExecutionWay" value="${contractExecutionWay }" />
			</td>
			<td>预付款：<span class="star">*</span></td>
			<td><input type="text" name="advance" id="advance" value="0"   class="validate[required]"/>(元)</td>
		</tr>
		<tr>
			<td>签订日期：</td>
			<td><input  id="signingDate" name="signingDate"   value="${signingDate }" class="date"/></td>
			<td>到货日期：</td>
			<td><input  id="arrivalDate" name="arrivalDate"  value="${arrivalDate }" class="date"/></td>
		</tr>
		<tr>
			<td>合同质保金额：</td>
			<td><input id="qualityMoney" type="text" name="qualityMoney" value="0"   class="validate[required]"/>(元)</td>
			<td>质保日期：</td>
			<td> <input  id="qualityDate" name="qualityDate"  value="${arrivalDate }" class="date"/>     
                  </td>
		</tr>
		<tr>
			<td>备注：</td>
			<td colspan="3">
				<span class="float_left">
				<textarea style="width:400px;" name="remark" ></textarea>
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="4">
			<input type="button"  onclick="ok()" value=" 提 交 "/>
				<!-- <input type="submit" value="提交"/> -->
				<input type="reset" value=" 重 置 "/>
			</td>
		</tr>
	</table>
	
	
	</div>

</form>
</body>