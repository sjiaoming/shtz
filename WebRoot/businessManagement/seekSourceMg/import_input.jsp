<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%@include file="/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--框架必需start-->
<script type="text/javascript" src="<%=path %>/js/jquery-1.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/framework.js"></script>
<link href="<%=path %>/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link  rel="stylesheet" type="text/css" id="skin" prePath="<%=path %>/"/>
<!--框架必需end-->
<script src="<%=path %>/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="<%=path %>/js/form/validationEngine.js" type="text/javascript"></script>
<!--截取文字start-->
<script type="text/javascript" src="<%=path %>/js/text/text-overflow.js"></script>
<!--截取文字end-->

<!--多功能表格start-->
<link rel="stylesheet" type="text/css" href="<%=path %>/js/table/flexigrid/css/flexigrid/flexigrid.css">
<script type="text/javascript" src="<%=path %>/js/table/flexigrid/flexigrid.js"></script>
<!--多功能表格end-->
<script language="javascript">
	function pageForword(){
		var temp = document.form1.some.value.split(".")[document.form1.some.value.split(".").length-1];
		var ex;
		var actionName ;
		if('undefined'!=temp&&temp!=""&&temp!=null){
			ex = temp;
			if(ex == 'xls'){
				actionName = "importfiles.action?ex=xls";
				
			}else if(ex == 'xlsx'){
				actionName = "importfiles.action?ex=xlsx";
			}else{
			top.Dialog.alert("不是有效的excel文件");
			return;
			}
			
		}else{
			top.Dialog.alert("不是有效的excel文件");
			return;
		}
		this.form1.action = actionName;
		this.form1.submit();
	}
</script>
</head>
<body>
<form  id="form1" name="form1"  action="?" method="post"  enctype="multipart/form-data">
<div class="box1" panelWidth="400">
	<table class="tableStyle" formMode="true">
		<tr>
		<td ><input type="file" name="some" class="default" id="some" />
		<input type="submit" value="导入" onclick='pageForword()'></input></td>
		</tr>
	</table>
	<table border="0" cellpadding="3" cellspacing="1" width="100%" align="center" style="background-color: #b9d8f3;" >
	<tr><b></>Excel 格式如下 : </b></tr>
		<tr bgcolor='#F4FAFF'><td>序号</td><td>购物车号</td><td>行号</td><td>计划单价</td><td>计划金额</td></tr>
		<tr bgcolor='#F4FAFF'><td>1</td><td>1000002301</td><td>2</td><td>11112.233</td><td>111122.33</td></tr>
		<tr bgcolor='#F4FAFF'><td>2</td><td>1000002301</td><td>3</td><td>323112.233</td><td>1133122.33</td></tr>
		<tr bgcolor='#F4FAFF'><td>3</td><td>1000002301</td><td>4</td><td>155112.233</td><td>166122.33</td></tr>
	</table>
	</div>
</form>
</body>
