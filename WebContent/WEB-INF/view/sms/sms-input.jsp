<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/iglomo.css"></link>
<title>簡訊發送服務</title>
<s:head />

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
	function palceholderSet(obj,iniString){		
		$(obj).val(iniString).css("color","gray");
		$(obj).click(function(){
			if($(this).css("color")!=$("#send_reqItem_requestItemList_0__callee").css("color"))
				$(this).val("").css("color","black");
		});
		$(obj).focusout(function(){
			if($(this).val()=="")
					$(this).val(iniString).css("color","gray");
		});
	}

	function getDateString(){
		var today = new Date();
	    var y = today.getFullYear().toString();

	    for(var i =y.length ;i<4;i++){
	    	y="0"+y;
	    }
	    var m = (today.getMonth()+1).toString();
	    for(var i =m.length ;i<2;i++){
	    	m="0"+m;
	    }
	    var d = today.getDate().toString();
	    for(var i =d.length ;i<2;i++){
	    	d="0"+d;
	    }
	    var h = today.getHours().toString();
	    for(var i =h.length ;i<2;i++){
	    	h="0"+h;
	    }
	    var mi = today.getMinutes().toString();
	    for(var i =mi.length ;i<2;i++){
	    	mi="0"+mi;
	    }
	    var s = today.getSeconds().toString();
	    for(var i =s.length ;i<2;i++){
	    	s="0"+s;
	    }
	    
	    var req_time = y+"/"+m+"/"+d+" "+h+":"+mi+":"+s;
	    
	    return req_time;
	}
	
	 
	
	$(document).ready(function(){
		$("#send_reqItem_requestItemList_0__callee").css("color","black");
		palceholderSet($("#send_reqItem_username"),"85292040071");
		palceholderSet($("#send_reqItem_passowrd"),"85292040071");
		palceholderSet($("#send_reqItem_orgcode"),"85292040071");
		
		if(!$("#send_reqItem_requestItemList_0__schedule").val())
			$("#send_reqItem_requestItemList_0__schedule").val(getDateString());

	});
</script>
</head>
<body>
<a href="../index.jsp" >回首頁</a>
	<h3>簡訊發送服務</h3>

	<s:form action="send" method="post" validate="true" cssClass="bordered">

		<s:textfield name="reqItem.username" label="使用者名稱" class="placehoder"/>
		<s:password name="reqItem.passowrd" label="密碼" />
		<s:textfield name="reqItem.orgcode" label="組織代碼" />
		<s:textfield name="reqItem.requestItemList[0].schedule" label="排程時間　　　　　　　(0為不排程)" />
		<s:textarea name="reqItem.requestItemList[0].callee" label="電話號碼 　　　　　　　(需帶國碼，兩門以上以換行符號作間隔)" cols="25" rows="5"/>
		<s:textarea name="reqItem.requestItemList[0].message" label="簡訊內容" cols="25" rows="5" />
		<s:textarea name="reqItem.remark" label="附註" cols="25" rows="1" />
		<s:submit value="發送" />
	</s:form>

	<br/>
	<!-- 已預約時間	<input type="text" name="reqItem.requestItemList[0].schedule" disabled="disabled"></input>	 -->
	<br/>

	<p>
		<s:if test="resItem != null">
			<b>發送簡訊結果</b>
			<ul>
				<li>使用者名稱: <s:text name="%{resItem.username}" /></li>
				<li>組織代碼: <s:text name="%{resItem.orgcode}" /></li>
				<li>訂單識別碼: <s:text name="%{resItem.uid}" /></li>
				<li>訂單狀態: <s:text name="%{resItem.responseItemList[0].code}" /></li>
			</ul>
		</s:if>
	</p>

</body>

</html>
