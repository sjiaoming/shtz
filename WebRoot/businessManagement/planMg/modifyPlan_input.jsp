<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%@include file="/common/common.jsp" %>
<jsp:useBean id="now" class="java.util.Date" /> 
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

<script language="javascript">
function ok(){
		//alert("--");
		//var name = document.form1.name.value;
		//var dept = document.form1.dept.value;
		this.form1.action = "doModifyPlanMg.action";
		this.form1.submit();
		top.Dialog.close();
		return;
	}
</script>

<body>
<form action="doChange.action" id="form1" name="form1" target="frmright">
<input type="hidden" name="planId" value="<%=request.getParameter("planId")%>"/>
<div class="box1" panelWidth="500">
	<table class="tableStyle" formMode="true">
		<tr>
			<td>序号</td><td ><input type="text" name="plan.id"  readonly="readonly"  value="${plan.id }"/></td>
		</tr>
		<tr>
			<td>计划接收日期</td><td ><input type="text" class="date" name="plan.planReceiptDate" value="<fmt:formatDate value="${plan.planReceiptDate }" pattern="yyyy-MM-dd"/>"/></td>
		</tr>
		<tr>
			<td>购物车编号</td><td ><input type="text" name="plan.oldPlanNum"  value="${plan.oldPlanNum }"/></td>
		</tr>
		<tr>
			<td>物料编码</td><td ><input type="text" name="plan.itemsNum"  value="${plan.itemsNum }"/></td>
		</tr>
		<tr>
			<td>物料描述</td><td ><input type="text" name="plan.itemsName"  value="${plan.itemsName }"/></td>
		</tr>
		<tr>
			<td>单价</td><td ><input type="text" name="plan.procurementPrice" value="${plan.planPrice }"/></td>
		</tr>
		<tr>
			<td>数量</td><td ><input type="text" name="plan.num" value="${plan.num }"/></td>
		</tr>
		<tr>
			<td>计量单位</td><td ><input type="text" name="plan.unit" value="${plan.unit }"/></td>
		</tr>
		<tr>
			<td>金额</td><td ><input type="text" name="plan.budget" value="${plan.budget }"/></td>
		</tr>
		<tr>
			<td>负责的采购组</td><td ><input type="text" name="plan.org.name"  readonly="readonly"  value="${plan.org.name }"/></td>
		</tr>
		<tr>
			<td>负责人</td><td ><input type="text" name="plan.person.name" readonly="readonly" value="${plan.person.name }"/></td>
		</tr>
		<tr>
			<td>交货日期</td><td ><input type="text" class="date"  name="plan.arrivalDate" value="<fmt:formatDate value="${plan.arrivalDate }" pattern="yyyy-MM-dd"/>"/></td>
		</tr>
		<tr>
			<td>提报单位</td><td ><input type="text" name="plan.reportComp" value="${plan.reportComp }"/></td>
		</tr>
		<tr>
			<td>备注</td><td ><textarea name="plan.remark">${plan.remark }</textarea></td>
		</tr>
		<tr>
			<td colspan="3">
				<button  onclick="ok();">提交</button>
				<input type="reset" value=" 重 置 "/>
			</td>
		</tr>
	</table>
	</div>
</form>
</body>