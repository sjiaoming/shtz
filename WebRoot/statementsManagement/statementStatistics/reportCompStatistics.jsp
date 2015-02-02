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
<link  rel="stylesheet" type="text/css" id="skin" prePath="<%=path %>/"/>
<!--框架必需end-->
<script type="text/javascript" src="<%=path %>/js/form/multiselect.js"></script>

<!--多功能表格start-->
<link rel="stylesheet" type="text/css" href="<%=path %>/js/table/flexigrid/css/flexigrid/flexigrid.css">
<script type="text/javascript" src="<%=path %>/js/table/flexigrid/flexigrid.js"></script>

<!--多功能表格end-->
<script type="text/javascript">
	$(function(){
		top.Dialog.close();
		var parentMainHeight = window.parent.document.documentElement.clientHeight;
		var flexiGridHeight=parentMainHeight-parentTopHeight-parentBottomHeight-fixHeight-45;
		//alert(flexiGridHeight);
		//定义flexiGridHeight是为了让表格自适应页面高度，如果不需要可以将下面的height设为一个具体数值
		if(parentMainHeight == 0 || parentMainHeight == undefined){
			$('.flexiStyle').flexigrid({
				height:400,
				resizable: false,
				showToggleBtn: false,
				minColToggle: 1
				});	
		}else{
			$('.flexiStyle').flexigrid({
				height:parentMainHeight*0.65,
				resizable: false,
				showToggleBtn: false,
				minColToggle: 1
				});	
		}
	})
	//导出
	function exportexcel(){
		if(document.getElementById("sDate").value == '' || document.getElementById("eDate").value == ''){
			top.Dialog.alert("请填写日期");
			return;
		}
		/* if(document.getElementById("dd").value == '' || document.getElementById("dd").value == 0){
			top.Dialog.close();
			top.Dialog.alert("请选择部门");
			return;
		} */
	     this.form1.action = "getReportCompListExport.action";
		this.form1.submit();
		return;
	}
	
	function toNext(url){
		
		//var name = document.form1.name.value;
		//alert(name);
		this.form1.action = url;
		this.form1.submit();
		return;
	}
	function getReportCompList(){
		if(document.getElementById("sDate").value == '' || document.getElementById("eDate").value == ''){
			top.Dialog.close();
			top.Dialog.alert("请填写日期");
			return;
		}
		/* if(document.getElementById("dd").value == '' || document.getElementById("dd").value == 0){
			top.Dialog.close();
			top.Dialog.alert("请选择部门");
			return;
		} */
		this.form1.action = "getReportCompList.action";
		this.form1.submit();
		return;
	}
</script>
<style>
body{
	line-height:150%;
}
</style>
<body>	
<div class="box2" panelTitle="功能面板" roller="false" panelHeight="70">
	<form id="form1" name="form1" action="#"  method="post">
	<input type="hidden" id="path" value="<%=path %>">
		<table>
			<tr>
				<td>部门:</td>
				<td><select id="dd" name="orgId">
						<option value="0">请选择</option>
						<c:if test="${!empty sessionOrgs}">
						 	<c:forEach items="${sessionOrgs }" var="organization">  
								<option value="${organization.id}" <c:if test="${organization.id == orgId}">selected="selected"</c:if> >${organization.name }</option>					 
							</c:forEach>
						</c:if>
					</select>
				</td>
				<td>计划提报单位：</td>
				<td>
					<select  name="useCompId" id="useCompId"  style="width:100px;" multiple >
						<c:if test="${empty useCompId }">
						<option value="" selected="selected" >请选择</option>
						</c:if>
						<c:if test="${!empty useCompId }">
						<option value="" >请选择</option>
						</c:if>
						<c:if test="${!empty sessionUseComps}">
						 	<c:forEach items="${sessionUseComps }" var="uc">
								<option value="${uc.id}" <c:if test="${uc.id == useCompId}">selected="selected"</c:if> rel="exclusive" >${uc.name }</option>					 
							</c:forEach>
						</c:if>
						<option value="0" rel="headernocb"></option>
					 </select>
				</td>
				<td>统计日期 从:</td>
				<td>
                        <input  id="sDate" name="sDate"  value="${sDate }" class="date"/>
				</td>
				<td>到：</td>
				<td>
                         <input  id="eDate" name="eDate"  value="${eDate }" class="date"/>
				</td>
				
				<%-- <c:if test="${my:method(login.id,'planmgr',1)}"> --%>
				<td>
				<input  type="button" value="查询" onclick='showProgressBar();getReportCompList()'/></td>
				<%-- </c:if> --%>
				
				<td><input  type="button" value="导出" onclick='exportexcel()'/></td>
			</tr>
		</table>
		</form>
	</div>

	<table class="flexiStyle" >
		<thead>
			<tr>
				<th width="20">序号</th>
				<!-- <th width="120">部门</th> -->
				<th width="120">计划提报单位</th>
				<th width="100">采购计划接收金额</th>
				<th width="100">公开招标</th>
				<th width="100">邀请招标</th>
				<th width="100">竞争性谈判</th>
				<th width="100">询比价</th>
				<th width="100">单一来源</th>
				<th width="120">合计</th>
				<th width="120">节支</th>
				<th width="60">采购合同份数</th>
				<th width="100">采购合同金额</th>
				<th width="60">销售合同份数</th>
				<th width="100">销售合同金额</th>
				<th width="100">到货金额</th>
				<th width="100">回款金额</th>
			</tr>
		</thead> 
		                 
	    <tbody>  
	   
		<c:if test="${!empty pm.list}">
        <c:forEach items="${pm.list }" var="plan">  
		<tr>
			<td >${plan.number }</td>
			<%-- <td >${plan.orgName }</td> --%>
			<td >${plan.reportCompName }</td>
			<td ><fmt:formatNumber value="${plan.budget }" pattern="#,#00.0#" /></td>
			<td ><fmt:formatNumber value="${plan.apm }" pattern="#,#00.0#" /></td>
			<td ><fmt:formatNumber value="${plan.bpm }" pattern="#,#00.0#" /></td>
			<td ><fmt:formatNumber value="${plan.cpm }" pattern="#,#00.0#" /></td>
			<td ><fmt:formatNumber value="${plan.epm }" pattern="#,#00.0#" /></td>
			<td ><fmt:formatNumber value="${plan.dpm }" pattern="#,#00.0#" /></td>
			<td ><fmt:formatNumber value="${plan.procurementMoney }" pattern="#,#00.0#" /></td>
			<td ><fmt:formatNumber value="${plan.jz }" pattern="#,#00.0#" /></td>
			<td >${plan.contractCount }</td>
			<td ><fmt:formatNumber value="${plan.contractMoney }" pattern="#,#00.0#" /></td>
			<td >${plan.salesCount }</td>
			<td ><fmt:formatNumber value="${plan.salesMoney }" pattern="#,#00.0#" /></td>
			<td ><fmt:formatNumber value="${plan.arrivalMoney }" pattern="#,#00.0#" /></td>
			<td ><fmt:formatNumber value="${plan.totalcontractReceivedMoney }" pattern="#,#00.0#" /></td>
			
		</tr>
		</c:forEach>
		</c:if>
		<c:if test="${empty pm.list}">
			<tr><td >无数据</td><td colspan="25">无数据</td><td colspan="25">无数据</td><td colspan="25">无数据</td></tr>
		</c:if>
		
		</tbody> 
		
	</table>

<div style="height:35px;">
	<pg:pager items="${pm.total }" url="getReportCompList.action" export="currentPageNumber=pageNumber" maxPageItems="50">
	<div class="float_right padding5 paging">
		<div class="float_left padding_top4">
		<c:choose>
			<c:when test="${ currentPageNumber == 1}">
				<span class="paging_disabled"><a href="javascript:;">首页</a></span>
			</c:when>
			<c:otherwise>
				<span ><pg:first><a href="#" onclick="showProgressBar();toNext('${pageUrl}')">首页</a></pg:first></span>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${ currentPageNumber > 1}">
				<span ><pg:prev><a href="#" onclick="showProgressBar();toNext('${pageUrl}')">上一页</a></pg:prev></span>
			</c:when>
			<c:otherwise>
				<span class="paging_disabled"><a href="javascript:;">上一页</a></span>
			</c:otherwise>
		</c:choose>
		
			<pg:pages>
				<c:choose>
					<c:when test="${ currentPageNumber eq pageNumber}">
					<span class="paging_current"><a href="javascript:;">${pageNumber}</a></span>
					</c:when>
					<c:otherwise>
					<span><a href="#" onclick="showProgressBar();toNext('${pageUrl}')">${pageNumber }</a></span>	
					</c:otherwise>
				</c:choose>
			</pg:pages>
			
		<span><pg:next><a href="#" onclick="showProgressBar();toNext('${pageUrl}')">下页</a></pg:next></span>
		<span><pg:last><a href="#" onclick="showProgressBar();toNext('${pageUrl}')">尾页</a></pg:last></span>
		<div class="clear"></div>
	</div>
	</pg:pager>
	<div class="clear"></div>
</div>
		
</body>
