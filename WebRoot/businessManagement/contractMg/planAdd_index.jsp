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

<!--截取文字start-->
<script type="text/javascript" src="<%=path %>/js/text/text-overflow.js"></script>
<!--截取文字end-->

<!--多功能表格start-->
<link rel="stylesheet" type="text/css" href="<%=path %>/js/table/flexigrid/css/flexigrid/flexigrid.css">
<script type="text/javascript" src="<%=path %>/js/table/flexigrid/flexigrid.js"></script>
<!--多功能表格end-->
<script type="text/javascript" src="<%=path %>/js/form/tageditor.js"></script>


<script type="text/javascript">
$(function(){
		
		//top.Dialog.close();
		var s = document.getElementById("ccflag").value;
		//alert(s);
		if(s!=""){
			alert(s);
		}
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
		if(this.form1.contractMsg.value!=null && this.form1.contractMsg.value!=""){
			top.Dialog.alert(this.form1.contractMsg.value);
			this.form1.contractMsg.value="";
		}
	})
	//全选 
	function test(){
		tempc = document.getElementsByName("checkbox");
		for(var i=0;i<tempc.length;i++) {    
		f =tempc[i];    
		if(f.checked==false){      
			f.checked=true;     
			}
		}
		getSelectedCount();
	} 
	//反选 
	function ftest() { 
		tempc = document.getElementsByName("checkbox");
		for(var i=0;i<tempc.length;i++) {    
			f =tempc[i];    
			if(f.checked==false){      
				f.checked=true;
				}else{    
					f.checked=false;     
				} 
			}
		getSelectedCount();
		} 
	//取消全部 
	function CancelAll() { 
		tempc = document.getElementsByName("checkbox");
		for(var i=0;i<tempc.length;i++) {
		f =tempc[i];    
		if(f.checked==true)     {      
			f.checked=false;     
			} 
		}
		getSelectedCount();
	}
	function getCheckBoxLine(){
		var msg="";
		$("#checkboxTable input[type=checkbox]").each(function(){
			if($(this).attr("checked")){
				msg+=$(this).val()+",";
			}
		})
		return msg;
	}
	function search(offset){

		var url = "procurementContractSearch.action?pager.offset="+offset;
		getCheckBoxInformationnn(url);
	}
	function resetmethod(){
		document.getElementById("all_select").value = "";
		this.form1.action = "procurementContractSearch.action?method=planAdd";
		this.form1.submit();
		return;
	}
	function ok(){
		var tags = selectValidationReturnTags(true);
		if(tags != undefined){
			this.form1.action = "planAdd.action?tags="+tags;
			this.form1.submit();
			return;
			}
	}
	function selectValidationReturnTags(isAlert){
		var tags = getCheckBoxLine();
		var alls = document.getElementById("all_select").value;
		var tagsf = "";
		if(tags !=""){
			var tempOpn = ","+alls+",";
			var ttags = tags.split(",");
			for(var i=0;i<ttags.length;i++){
				var tempTag = ","+ttags[i]+",";
				if(tempOpn.indexOf(tempTag) < 0){
					tagsf += ttags[i]+",";
				}
			}
		}
		tags = tagsf+alls;
		var opn ="";
		if(tags!=""){
			opn= tags.split(",");
			opn = $.grep(opn, function(n) {return $.trim(n).length > 0;});
		}
		if(isAlert){
			if(opn==""){
				top.Dialog.alert("请选择计划");
				return;
			}
		}
		return opn+"";
	}
	function getSelectedCount(){
		var tags = selectValidationReturnTags(false);
		var tagsplit;
		if(tags != ""){
			tagsplit = tags.split(",");
			document.getElementById("selectCount").innerHTML = "已选择："+tagsplit.length+" 条";
		}else{
			document.getElementById("selectCount").innerHTML = "已选择：0 条";
		}
		
	}
	$(function(){
		$("#checkboxTable input[type=checkbox]").click(function(){
			if($(this).attr("checked") == false){
				var alls = document.getElementById("all_select").value;
				var opn ="";
				if(alls!=""){
					opn= alls.split(",");
					opn = $.grep(opn, function(n) {return $.trim(n).length > 0;});
			
					var tempAll="";
					var tempOpn = ","+opn+",";
					var tempTag = ","+$(this).val()+",";
					if(tempOpn.indexOf(tempTag) > -1){
						var allsplit = tempOpn.split(",");
						for(var i=0;i<allsplit.length;i++){
							if($(this).val() != allsplit[i]){
								tempAll +=allsplit[i]+",";
							}
						}
						document.getElementById("all_select").value = tempAll;
					}
					getSelectedCount();
				}
			}
		})
	})
	// 页面onload的时候计算当前页被选中项,并在页面表示
	function initPage() {
		if(document.getElementById("resetflag").value != ""){
			document.getElementById("all_select").value  = "";
			document.getElementById("resetflag").value = "";
		}
	//alert("initPage");
	getSelectedCount();
	//var all_selected = document.forms[0].all_select.value;
	var all_selected = document.form1.all_select.value;
	//alert(all_selected);
		if(all_selected != "" && all_selected!= null) {
		var arrall_select = all_selected.split(",");
		//alert("arrall_select.length:"+arrall_select.length);
			if(arrall_select.length > 0) {
			//alert("arrall_select for");
				for(k = 0; k < arrall_select.length; k++) {
				//alert("arrall_select[k]:"+arrall_select[k]);
					for(i = 0; i < document.getElementsByName("checkbox").length; i++) {
					//alert(document.getElementsByName("checkbox")[i].value+"=========="+arrall_select[k]);
						if(document.getElementsByName("checkbox")[i].value == arrall_select[k]) {
						//alert("checked");
						document.getElementsByName("checkbox")[i].checked = true; 
						} 
					}
				} 
			} 
		} 
	}
	//每次翻页的时候调用getCheckBoxInformation()方法,页面加载的时候调用initPage()方法.
	function getCheckBoxInformationnn(url) {
	//showProgressBar();
	var checkboxes = document.getElementsByName("checkbox");
	var checkedStr = "";
	var uncheckedStr = "";
	var all_selected = "";
	if(this.form1.all_select.value.split(",").length >1 ){
		all_selected = this.form1.all_select.value;
	}
	for(i = 0; i < checkboxes.length; i++) {
	var checkbox = checkboxes[i];
	if(checkbox.checked) {
	checkedStr = checkedStr + "," + checkbox.value;
	}else {
	uncheckedStr = uncheckedStr + "," + checkbox.value;
	}
	}
	url = url+"&method=planAdd&now_selected="+checkedStr+"&no_selected="+uncheckedStr+"&all_selected="+all_selected+"&flag=CHECKED";
	this.form1.action=url;
	this.form1.submit();
	return;
	}
	
</script>

<style>
body{
	line-height:150%;
}
</style>
<body onload="initPage()">	
<div class="box2" panelTitle="功能面板 " roller="false">
	<form id="form1" name="form1" action="?"  method="post">
	<input type="hidden" id="path" value="<%=path %>">
	<input type="hidden" id="contractMsg" name="contractMsg" value="${contractMsg }">
	<input type="hidden" id="all_select" name="all_select" value="${all }">
	<input type="hidden" name="ccflag" id="ccflag" value="${ccflag }" />
	<input type="hidden" name="myOffset" id="myOffset" value="${myOffset }" />
	<input type="hidden" name="resetflag" id="resetflag" value="${resetflag }" />
	<input type="hidden" id="pcid" name="pcid" value="${param.pcid}" />
	<input type="hidden" id="pccid" name="pccid" value="${pcid}" />
		<table>
			<tr>
				<td>部门:</td>
				<td><select id="dd" name="orgId">
						<option value="0">请选择</option>
						<c:if test="${!empty sessionOrgs}">
						 	<c:forEach items="${sessionOrgs }" var="organization">  
								<option value="${organization.id}" <c:if test="${organization.id == params.id_org}">selected="selected"</c:if> >${organization.name }</option>					 
							</c:forEach>
						</c:if>
					</select></td>
				<td>购物车号：</td>
				<td><input type="text" name="oldPlanNum" value="${params.oldPlanNum }"/></td>
				<td>搜备注：</td>
				<td><input type="text" name="remark" value="${params.remark }"/></td>
				<td>方案号：</td>
				<td><input type="text" name="procurementWayNum" value="${params.procurementWayNum }"/></td>
				
			</tr>
			<tr>
			
			<td>搜包号：</td>
				<td><input type="text" name="bNumber" value="${params.bNumber }"/></td>
				<td>操作人：</td>
				<td><input type="text" name="personName" value="${params.personName }"/></td>
				<td></td>
				<td colspan="3">
				<input type="button" value="查询" onclick='search(0);'/>
				<input type="button" value="追加" onclick='top.Dialog.confirm("您确定要追加？",function(){ok()});'/>
				<input type="button" value="重新选择" onclick='top.Dialog.confirm("您确定要重新选择？",function(){resetmethod()});'/>
				</td>
				<td colspan="2"><font color="red"><b><div id="selectCount" ></div></b></font></td>
			</tr>
		</table>
		</form>

	</div>
<table class="flexiStyle" id="checkboxTable">
<thead>
	<tr>
		<th width="50">
		<a herf="#" onclick="test()">全</a>|
		<a herf="#" onclick="ftest()" >反</a>|
		<a herf="#" onclick="CancelAll()" >不</a>
		</th>
		<th width="30">序号</th>
		<th width="80">购物车编号</th>
		<th width="30">行号</th>
		<th width="150">物料描述</th>
		<th width="80">单价</th>
		<th width="80">数量</th>
		<th width="30">计量单位</th>
		<th width="80">预算金额(元)</th>
		<th width="80">提报单位</th>
		<th width="80">方案号</th>
		<th width="80">计划包号</th>
		<th width="50">采购方案</th>
		<th width="60">采购签报日期</th>
		<th width="60">寻源公布日期</th>
		<th width="60">寻源签报日期</th>
		<th width="80">合同执行方式</th>
		<th width="80">寻源金额(元)</th>
		<th width="80">采购备注</th>
		<th width="80">计划备注</th>
		<th width="30">操作人</th>
	</tr>
</thead>
	<tbody>
	<c:if test="${!empty pm.list}">
        <c:forEach items="${pm.list }" var="plan" varStatus="index">  
		<tr>
			<td><input  id="tg_${plan.id }"  name="checkbox" type="checkbox" value="${plan.id }"  onclick='getSelectedCount()'/></td>
			<td >${index.count }</td>
			<td >${plan.oldPlanNum }</td>
			<td >${plan.planNum }</td>
			<td >${plan.itemsName }</td>
			<td ><fmt:formatNumber value="${plan.procurementPrice }" type="currency"/></td>
			<td >${plan.num }</td>
			<td >${plan.unit }</td>
			<td ><fmt:formatNumber value="${plan.budget }" type="currency"/></td>
			<td >${plan.reportComp }
			<input type="hidden" id="reportCompId_${plan.id }" name="reportCompId_${plan.id }" value="${plan.reportCompId }" />
			</td>
			<td >${plan.procurementWayNum }</td>
			<td >${plan.bNumber }</td>
			<td >
			<c:if test="${plan.procurementWay == '1' }">公开招标</c:if>
			<c:if test="${plan.procurementWay == '2' }">邀请招标</c:if>
			<c:if test="${plan.procurementWay == '3' }">竞争性谈判</c:if>
			<c:if test="${plan.procurementWay == '4' }">单一来源</c:if>
			<c:if test="${plan.procurementWay == '5' }">寻比价</c:if>
			<input type="hidden" id="procurementWay_${plan.id }" name="procurementWay_${plan.id }" value="${plan.procurementWay }" />
			</td>
			
			<td ><fmt:formatDate value="${plan.procurementDate }" pattern="yyyy-MM-dd"/></td>
			<td ><fmt:formatDate value="${plan.searchAnnouncedDate}" pattern="yyyy-MM-dd"/></td>
			<td ><fmt:formatDate value="${plan.searchDate}" pattern="yyyy-MM-dd"/></td>
			<td >
			<c:if test="${plan.contractExecutionWay == '1' }">统谈统签统付</c:if>
			<c:if test="${plan.contractExecutionWay == '2' }">统谈统签分付</c:if>
			<c:if test="${plan.contractExecutionWay == '3' }">统谈分签</c:if>
			<input type="hidden" id="contractExecutionWay_${plan.id }" name="contractExecutionWay_${plan.id }" value="${plan.contractExecutionWay }" />
			</td>
			<td ><fmt:formatNumber value="${plan.procurementMoney }" type="currency"/></td>
			<td >${plan.procurementRemark }</td>
			<td >${plan.remark }</td>
			<td >${plan.person.name }</td>
		</tr>
		</c:forEach>
		</c:if>
		<c:if test="${empty pm.list}">
			<tr><td>无数据</td></tr>
		</c:if>
		</tbody>
</table>
<div style="height:35px;">
	<pg:pager items="${pm.total }" url="procurementContractSearch.action" export="currentPageNumber=pageNumber" maxPageItems="50" >

	<div class="float_right padding5 paging">
		<div class="float_left padding_top4">
		<c:choose>
			<c:when test="${ currentPageNumber == 1}">
				<span class="paging_disabled"><a href="javascript:;">首页</a></span>
			</c:when>
			<c:otherwise>
				<span ><pg:first><a href="#" onclick="getCheckBoxInformationnn('${pageUrl }')" >首页</a></pg:first></span>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${ currentPageNumber > 1}">
				<span ><pg:prev><a href="#"  onclick="getCheckBoxInformationnn('${pageUrl }')" >上一页</a></pg:prev></span>
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
					<span><a href="#"  onclick="getCheckBoxInformationnn('${pageUrl }')" >${pageNumber }</a></span>	
					</c:otherwise>
				</c:choose>
			</pg:pages>
			
		<span><pg:next><a href="#"  onclick="getCheckBoxInformationnn('${pageUrl }')" >下页</a></pg:next></span>
		<span><pg:last><a href="#"  onclick="getCheckBoxInformationnn('${pageUrl }')" >尾页</a></pg:last></span>
		<div class="clear"></div>
	</div>
	</pg:pager>
	<div class="clear"></div>
</div>


</body>
</html>
