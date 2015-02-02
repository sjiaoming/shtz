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


<script type="text/javascript">

	$(function(){
		var parentMainHeight = window.parent.document.documentElement.clientHeight;
		var flexiGridHeight=parentMainHeight-parentTopHeight-parentBottomHeight-fixHeight-35;
		//alert(parentMainHeight +'---'+flexiGridHeight);
		if(parentMainHeight == 0 || parentMainHeight == undefined){
			$('.flexiStyle').flexigrid({
				height:400,
				resizable: false,
				showToggleBtn: false,
				minColToggle: 1
				});	
		}else{
			$('.flexiStyle').flexigrid({
				height:parentMainHeight*0.6,
				resizable: false,
				showToggleBtn: false,
				minColToggle: 1
				});	
		}
	})
	$(document).ready(function(){
		top.Dialog.close();
		var s = document.getElementById("cflag").value;
		if(s!=""&&s=="success"){
			//action ServletActionContext.getRequest().setAttribute("cflag", "success");
			alert("操作成功");
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
	function gettgvalue(tagss){
		if(tagss == 'add'){
			if(confirm("您确定要将这些计划执行采购方案吗？")){
				this.openWin();
			}
		}else if(tagss == 'mpw'){
			if(confirm("您确定要批量修改这些计划的采购方案吗？")){
				this.openWin3();
			}
		}else if(tagss == 'mpd'){
			if(confirm("您确定要批量修改这些计划的采购签报日期吗？")){
				this.openWin4();
			}
		}
	}

	function search(offset){
		var url = "planTrackSearch.action?pager.offset="+offset;
		getCheckBoxInformationnn(url);
	}
	function resetmethod(){
		document.getElementById("all_select").value = "";
		this.form1.action = "planTrackSearch.action";
		this.form1.submit();
		return;
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
		/* if(isAlert){
			if(opn==""){
				top.Dialog.alert("请选择计划");
				return;
			}
		} */
		return opn+"";
	}
	function backPlans(){
			var tags = selectValidationReturnTags(true);
			if(tags==""){
				top.Dialog.alert("请选择计划");
				return;
			}
				this.form1.action = "modifyPlansByIdsBack.action?tags="+tags;
				this.form1.submit();
				return;
	}
	function openWin(){
		
		var tags = selectValidationReturnTags(true);
		if(tags==""){
			top.Dialog.alert("请选择计划");
			return;
		}
		var diag = new top.Dialog();
		diag.Title = "添加方案";
		diag.URL = "businessManagement/planTrackMg/procurementWay_input.jsp?pids="+tags;
		diag.Height=480;
		diag.MessageTitle="添加采购方案";
		diag.Message="添加采购方案明细";
		diag.CancelEvent = function(){
			document.getElementById("resetflag").value = "add";
			var inputValue = diag.innerFrame.contentWindow.document.getElementById('cflag').value;
			if(inputValue!=""){
				search(document.getElementById("myOffset").value);
			}
			diag.close();
			};
		diag.show();
	}
	function openWin3(){
		var tags = selectValidationReturnTags(true);
		if(tags==""){
			top.Dialog.alert("请选择计划");
			return;
		}
		var diag = new top.Dialog();
		diag.Title = "添加方案";
		diag.URL = "businessManagement/planTrackMg/procurementWay_modify.jsp?pids="+tags;
		diag.Height=258;
		diag.Width=508;
		diag.CancelEvent = function(){
			document.getElementById("resetflag").value = "add";
			var inputValue = diag.innerFrame.contentWindow.document.getElementById('cflag').value;
			if(inputValue!=""){
				search(document.getElementById("myOffset").value);
			}
			diag.close();
			};
		diag.show();
	}
	function openWin4(){
		var tags = selectValidationReturnTags(true);
		if(tags==""){
			top.Dialog.alert("请选择计划");
			return;
		}
		var diag = new top.Dialog();
		diag.Title = "添加方案";
		diag.URL = "businessManagement/planTrackMg/procurementDate_modify.jsp?pids="+tags;
		diag.Height=258;
		diag.Width=508;
		diag.CancelEvent = function(){
			document.getElementById("resetflag").value = "add";
			var inputValue = diag.innerFrame.contentWindow.document.getElementById('cflag').value;
			if(inputValue!=""){
				search(document.getElementById("myOffset").value);
			}
			diag.close();
			};
		diag.show();
	}
	
		//修改窗口
	function openWin2(planId){
		var diag = new top.Dialog();
		diag.Title = "修改管理";
		diag.URL = "modifyPlan.action?planId="+planId;
		diag.Height=480;
		diag.MessageTitle="修改管理";
		diag.Message="修改管理明细";
		diag.CancelEvent = function(){
			document.getElementById("resetflag").value = "add";
			var inputValue = diag.innerFrame.contentWindow.document.getElementById('cflag').value;
			if(inputValue!=""){
				search(document.getElementById("myOffset").value);
			}
			diag.close();
			};
		diag.show();
	}
	function openWin7(){
		var tags = selectValidationReturnTags(true);
		if(tags==""){
			top.Dialog.alert("请选择计划");
			return;
		}
		var tagsplit = tags.split(",");
		var diag = new top.Dialog();
		diag.Title = "添加方案";
		if(tagsplit.length > 1 ){
			diag.URL = "toModifySeekSource.action?modifyFlag=procurementRemark&tags="+tags;
		}else if(tagsplit.length == 1 ){
			diag.URL = "toModifySeekSource.action?modifyFlag=procurementRemark&id="+tagsplit[0];
		}
		diag.Height=258;
		diag.Width=508;
		diag.CancelEvent = function(){
			document.getElementById("resetflag").value = "add";
			var inputValue = diag.innerFrame.contentWindow.document.getElementById('cflag').value;
			if(inputValue!=""){
				search(document.getElementById("myOffset").value);
			}
			diag.close();
			};
		diag.show();
	}
	function openWin8(){
		var tags = selectValidationReturnTags(true);
		if(tags==""){
			top.Dialog.alert("请选择计划");
			return;
		}
		var tagsplit = tags.split(",");
		var diag = new top.Dialog();
		diag.Title = "添加方案";
		if(tagsplit.length > 1 ){
			diag.URL = "toModifySeekSource.action?modifyFlag=procurementBNumber_success&tags="+tags;
		}else if(tagsplit.length == 1 ){
			diag.URL = "toModifySeekSource.action?modifyFlag=procurementBNumber_success&id="+tagsplit[0];
		}
		diag.Height=258;
		diag.Width=508;
		diag.CancelEvent = function(){
			document.getElementById("resetflag").value = "add";
			var inputValue = diag.innerFrame.contentWindow.document.getElementById('cflag').value;
			if(inputValue!=""){
				search(document.getElementById("myOffset").value);
			}
			diag.close();
			};
		diag.show();
	}
	//导入窗口
	function openWin6(){
		
		var diag = new top.Dialog();
		diag.Title = "导入窗口";
		diag.CancelEvent = function(){
			diag.close();
			};
		diag.URL = "businessManagement/planTrackMg/import_input.jsp";
		diag.Height=480;
		diag.Width=430;
		diag.MessageTitle="导入excel文件";
		diag.Message="选择您的导入文件";
	
		diag.show();
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
	// 页面onload的时候计算当前页被选中项,并在页面表示
	function initPage() {
		if(document.getElementById("resetflag").value != ""){
			document.getElementById("all_select").value  = "";
			document.getElementById("resetflag").value = "";
		}
	getSelectedCount();
	//alert("initPage");
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
	//alert(document.all.checkbox.length);
	//alert(url);
	var checkboxes = document.getElementsByName("checkbox");
	//checkboxes[1].checked = true;
	//alert(checkboxes.length);
	
	var checkedStr = "";
	var uncheckedStr = "";
	var all_selected = "";
	if(this.form1.all_select.value.split(",").length >1 ){
		all_selected = this.form1.all_select.value;
	}
	for(i = 0; i < checkboxes.length; i++) {
	var checkbox = checkboxes[i];
	if(checkbox.checked) {
	//alert("checked");
	checkedStr = checkedStr + "," + checkbox.value;
	}else {
	uncheckedStr = uncheckedStr + "," + checkbox.value;
	}
	}
	url = url+"&now_selected="+checkedStr+"&no_selected="+uncheckedStr+"&all_selected="+all_selected+"&flag=CHECKED";
	//alert(url);
	
	this.form1.action=url;
	this.form1.submit();
	return;
	}
	
		//取消计划（新增）
	function cancelPlans(){
				var tags = selectValidationReturnTags(true);
				if(tags==""){
					top.Dialog.alert("请选择计划");
					return;
				}
				this.form1.action = "cancelPlans_procurementWay.action?tags="+tags;
				this.form1.submit();
				return;
		}

</script>

<style>
body{
	line-height:150%;
}
</style>
<body onload="initPage()" >	
<div class="box2" panelTitle="功能面板 " roller="false">
	<form id="form1" name="form1" method="post">
	<input type="hidden" id="path" value="<%=path %>">
	<input type="hidden" id="all_select" name="all_select" value="${all }">
	<input type="hidden" name="cflag" id="cflag" value="${cflag }" />
	<input type="hidden" name="myOffset" id="myOffset" value="${myOffset }" />
	<input type="hidden" name="resetflag" id="resetflag" value="${resetflag }" />
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
				<td>购物车编号：</td>
				<td><input type="text" name="oldPlanNum" value="${params.oldPlanNum }"/></td>
				<td>方案号:</td>
				<td><input type="text" name="procurementWayNum" value="${params.procurementWayNum}"/></td>
				<td>搜包号：</td>
				<td><input type="text" name="bNumber" value="${params.bNumber }"/></td>
				<td>采购方案名称：</td>
				<td><input type="text" name="procurementName" value="${params.procurementName}"/></td>
				<!--
				<td >
					<select id="procurementWay" name="procurementWay">
						<option value="0">请选择</option>
						<option value="1" <c:if test="${procurementWay == '1'}">selected="selected"</c:if> >公开招标</option>	
						<option value="2" <c:if test="${procurementWay == '2'}">selected="selected"</c:if> >邀请招标</option>	
						<option value="3" <c:if test="${procurementWay == '3'}">selected="selected"</c:if> >竞争性谈判</option>
						<option value="4" <c:if test="${procurementWay == '4'}">selected="selected"</c:if> >单一来源</option>	
						<option value="5" <c:if test="${procurementWay == '5'}">selected="selected"</c:if> >寻比价</option>						 
					</select>
				</td>
				-->
				
				
				
				</tr>
				<tr>
				<td>采购方案状态:</td>
				<td><select id="procurementStatus" name="procurementStatus">
						<option value="0">请选择</option>
						<option value="01" <c:if test="${procurementStatus == '01'}">selected="selected"</c:if> >已签报</option>	
						<option value="02" <c:if test="${procurementStatus == '02'}">selected="selected"</c:if> >已做采购方案</option>	
						<option value="03" <c:if test="${procurementStatus == '03'}">selected="selected"</c:if> >未做采购方案</option>					 
					</select></td>
				<td>搜备注：</td>
				<td><input type="text" name="remark" value="${params.remark }"/></td>
				<td>操作人：</td>
				<td><input type="text" name="personName" value="${params.personName }"/></td>
				<td>计划接收日期 从:</td>
				<td>
                        <input  id="sDate" name="sDate"  value="${sDate }" class="date"/>
				</td>
				<td>到：</td>
				<td>
                         <input  id="eDate" name="eDate"  value="${eDate }" class="date"/>
				</td>
				<c:if test="${my:method(login.id,'procurementWayMgr',1)}">
				
				<td><button  onclick='showProgressBar();search(0);'><span class="icon_find">查询</span></button></td>
				</c:if>
				<c:if test="${my:method(login.id,'procurementWayMgr',2)}">
				<td><input type="button" value="导入" onclick='openWin6()'/></td>
				</c:if>
			</tr>
			
			<tr>
				<td colspan="8">
				<c:if test="${my:method(login.id,'procurementWayMgr',0)}">
				<button type="button"  onclick='top.Dialog.confirm("您确定要重新选择？",function(){resetmethod()});' ><span class="icon_reload">重新选择</span></button>
				<button type="button"  onclick='top.Dialog.confirm("您确定要将这些计划退回吗？",function(){backPlans()});' ><span class="icon_remove">计划退回</span></button>
				<button type="button"  onclick='top.Dialog.confirm("您确定要取消计划吗？",function(){cancelPlans()});' ><span class="icon_remove">计划取消</span></button>
				<button type="button"  onclick='top.Dialog.confirm("您确定要添加采购方案吗？",function(){openWin()});' ><span class="icon_add">添加采购方案</span></button>
				
				</c:if>
				<c:if test="${my:method(login.id,'procurementWayMgr',2)}">
				<button type="button"  onclick='top.Dialog.confirm("您确定要重新选择？",function(){openWin3()});' ><span class="icon_page">批量修改采购方案</span></button>
				<button type="button"  onclick='top.Dialog.confirm("您确定要修改签报日期？",function(){openWin4()});' ><span class="icon_pencil">批量修改签报日期</span></button>
				<button type="button"  onclick='top.Dialog.confirm("您确定要将这些计划修改备注吗？",function(){openWin7()});' ><span class="icon_page">批量改备注</span></button>
				<button type="button"  onclick='top.Dialog.confirm("您确定要批量添加包号吗？",function(){openWin8()});' ><span class="icon_page">批量加包号</span></button>
				</c:if>
				</td>
				<td colspan="3"><font color="red"><b><div id="selectCount" ></div></b></font></td>
			</tr>
			<tr>
				<td colspan="11">
				<b>
				总条数：<fmt:formatNumber value="${pm.total}"  pattern="#0.00"/>&nbsp;&nbsp;&nbsp;&nbsp;
				总计划数量：<fmt:formatNumber value="${pm.double1 }"  pattern="#0.00"/>&nbsp;&nbsp;&nbsp;&nbsp;
				总预算金额：<fmt:formatNumber value="${pm.double2 }"  pattern="#0.00"/>&nbsp;&nbsp;&nbsp;&nbsp;
				</b>
				</td>
				
			</tr>
		</table>
		</form>

	</div>
<div id="scrollContent" >
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
		<th width="60">计划接收日期</th>
		<th width="100">物料编码</th>
		<th width="160">物料描述</th>
		<th width="80">计划单价</th>
		<th width="80">数量</th>
		<th width="40">计量单位</th>
		<th width="80">预算金额</th>
		<th width="160">提报单位</th>
		<th width="100">方案号</th>
		<th width="100">计划包号</th>
		<th width="56">采购方案名称</th>
		<th width="50">采购方案</th>
		<th width="60">采购签报日期</th>
		<th width="100">采购备注</th>
		<th width="100">计划备注</th>
		<th width="60">采购制定日期</th>
		<th width="40">操作人</th>
		<th width="20">操作</th>
	</tr>
</thead>
	<tbody>	
	<c:if test="${!empty pm.list}">
        <c:forEach items="${pm.list }" var="plan" varStatus="index">  
			<tr >
			<td><input  id="tg"  name="checkbox" type="checkbox" value="${plan.id }" onclick='getSelectedCount()'/></td>
			<td >${index.count }</td>
			<td >${plan.oldPlanNum }</td>
			<td >${plan.planNum }</td>
			<td ><fmt:formatDate value="${plan.planReceiptDate }" pattern="yyyy-MM-dd"/></td>
			<td >${plan.itemsNum }</td>
			<td >${plan.itemsName }</td>
			<td ><fmt:formatNumber value="${plan.planPrice }" type="currency"/></td>
			<td >${plan.num }</td>
			<td >${plan.unit }</td>
			<td ><fmt:formatNumber value="${plan.budget }" type="currency"/></td>
			<td >${plan.reportComp }</td>
			<td >${plan.procurementWayNum}</td>
			<td >${plan.bNumber }</td>
			<td >${plan.procurementName}</td>
			<td >
			<c:if test="${plan.procurementWay == '1' }">公开招标</c:if>
			<c:if test="${plan.procurementWay == '2' }">邀请招标</c:if>
			<c:if test="${plan.procurementWay == '3' }">竞争性谈判</c:if>
			<c:if test="${plan.procurementWay == '4' }">单一来源</c:if>
			<c:if test="${plan.procurementWay == '5' }">寻比价</c:if>
			</td>
			<td ><fmt:formatDate value="${plan.procurementDate }" pattern="yyyy-MM-dd"/></td>
			<td >${plan.procurementRemark }</td>
			<td >${plan.remark }</td>
			<td ><fmt:formatDate value="${plan.procurementSigningleDate }" pattern="yyyy-MM-dd"/>
			<input type="hidden" id="procurementSigningleDate_${plan.id }" name="procurementSigningleDate" value="${plan.procurementSigningleDate }" />
			</td>
			
			<td >${plan.person.name }</td>
			<td >
			<c:if test="${!empty plan.procurementWay}">
				<span class="img_edit hand" title="修改" onclick="openWin2(${plan.id})"></span>
			</c:if>
			</td>
		</tr>
		</c:forEach>
		</c:if>
		<c:if test="${empty pm.list}">
			<tr><td>无数据</td></tr>
		</c:if>
		</tbody>
</table>
</div>
<div style="height:35px;">
	<pg:pager items="${pm.total }" url="planTrackSearch.action" export="currentPageNumber=pageNumber" maxPageItems="50" >

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
			
		<span><pg:next><a href="#"  onclick="showProgressBar();getCheckBoxInformationnn('${pageUrl }')" >下页</a></pg:next></span>
		<span><pg:last><a href="#"  onclick="getCheckBoxInformationnn('${pageUrl }')" >尾页</a></pg:last></span>
		<div class="clear"></div>
	</div>
	</pg:pager>
	<div class="clear"></div>
</div>
		
</body>
</html>
