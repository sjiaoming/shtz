<%@ page language="java" import="java.util.*" pageEncoding="gbk" isELIgnored="false"%>
<%@include file="/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--��ܱ���start-->
<script type="text/javascript" src="<%=path %>/js/jquery-1.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/framework.js"></script>
<link href="<%=path %>/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link  rel="stylesheet" type="text/css" id="skin"  prePath="<%=path %>/"/>
<!--��ܱ���end-->
<!--��ȡ����start-->
<script type="text/javascript" src="../../js/text/text-overflow.js"></script>
<!--��ȡ����end-->
<!--�๦�ܱ��start-->
<link rel="stylesheet" type="text/css" href="../../js/table/flexigrid/css/flexigrid/flexigrid.css">
<script type="text/javascript" src="../../js/table/flexigrid/flexigrid.js"></script>
<script src="../js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="../js/form/validationEngine.js" type="text/javascript"></script>
<script language="javascript">
function ok(){
		//alert("--");
		//var name = document.form1.name.value;
		//var dept = document.form1.dept.value;
		this.form1.action = "add_plan.action";
		this.form1.submit();
		top.Dialog.close();
		return;
	}

</script>

<body>
<form action="add_plan.action" id="form1" name="form1"  target="frmright">
<input type="hidden" name="orgId" value="${person.org.id}" />
<div class="box1" panelWidth="500">
	<table class="tableStyle" formMode="true">
		<tr>
			<th colspan="2">�ƻ���Ϣ</th>
		</tr>
		<tr>
			<td>�������ƣ�</td><td><input type="text"  name="itemsName"/></td>
		</tr>
		<tr>
			<td>����ͺţ�</td><td><input type="text"  name="model"/></td>
		</tr>
		<tr>
			<td>ԭʼ���ţ�</td><td><input type="text"  name="oldPlanNum"/></td>
		</tr>
		<tr>
			<td>�����λ��</td><td><input type="text"  name="reportComp"/></td>
		</tr>
		<tr>
			<td>�ƻ���ţ�</td><td><input type="text"  name="planNum"/></td>
		</tr>
		<tr>
			<td>������</td><td><input type="text"  name="num"/></td>
		</tr>

		<tr>
			<td colspan="2">
				<button  onclick="ok();">�ύ</button>
				<input type="reset" value=" �� �� "/>
			</td>
		</tr>
	</table>
	</div>
</form>
</body>