<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>簡訊發送服務控制</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
var leatherTypeList = [];
	function querySMS(){
		var acc = $("#account").val();
		var sdate = $("#sdate").val();
		var edate = $("#edate").val();
		var phone = $("#phone").val();
		if(!acc && !sdate && !edate){
			alert("帳號、日期請至少輸入一項條件！");
			return;
		}
			
		
		$("#table").empty();
		$.ajax({
		      url: '<s:url action="querySMSStatus"/>',
		      data: {input:acc,sdate:sdate,edate:edate,phone:phone},//parameters go here in object literal form
		      type: 'POST',
		      datatype: 'json',
		      success: function(json) {  
		    	  //alert(json);
		    	  $("#result").val(json);
		    	  leatherTypeList = $.parseJSON(json);
		    	  //alert(leatherTypeList);
		    	   $("#table").append(	"<tr>"
		    		  							+"<td>Msgid</td>"
		    		  							+"<td>seq</td>"
		    		  							+"<td>Phone number</td>"
		    		  							+"<td>schedule</td>"
		    		  							+"<td>Msg</td>"
		    		  							+"<td>status</td>"
		    		  							+"<td></td>"
		    		  						+"</tr>");
		    	  $.each(leatherTypeList, function(index, value){
		    		  $("#table").append(	"<tr>"
		    		  							+"<td>"+value.msgid+"</td>"
		    		  							+"<td>"+value.seq+"</td>"
		    		  							+"<td>"+value.phoneno+"</td>"
		    		  							+"<td>"+value.schedule+"</td>"
		    		  							+"<td>"+value.msgbody+"</td>"
		    		  							+"<td>"+value.status+"</td>"
		    		  							+"<td><input type='checkbox' onclick='checkBoxClick(this,"+index+")'></td>"
		    		  						+"</tr>");
		    	  });
		    	  
		    	  //jQuery.parseJSON,JSON.parse(json)
		    	  //alert(json);
		    	  /* var list=$.parseJSON(json);
		    	  //$("#table1 tr:gt(0)").remove();//移除>0之後讀tr
		    	  limitList=list['data'];
		    	  
		    	  if(limitList!=null)
		    		  dataList=limitList.slice(0);
		    	  
		    	  var error = list['error'];
		    	  $('#Error').html(error); */
		    	  },
		      error: function() { $("#Qmsg").html('something bad happened');  
		      },
	    	  beforeSend:function(){
	    		 /*  $("#Qmsg").html("正在查詢，請稍待...");
		    		$('#Error').html("");
		    		dataList=[];
		    		disableButton(); */
	          },
	          complete:function(){
	        	/*   enableButton();
	        	  pagination();
	        	  $("#IMSI").val("");
	        		$("#LIMSI").html("&nbsp;");	
	        		$("#Msisdn").val("");
	        		$("#LMsisdn").html("&nbsp;"); */
	          }
	    });
	}
	
	
	
	var selected=[];
	
	function checkBoxClick(obj,index){
		if($(obj).prop("checked"))
			selected.push(index);
		else
			selected = jQuery.grep(selected, function(value) {
		        return value != index;
		      });
		 
		
		selected.sort(function(a, b) {
		    return a>b;
		});
		
		//alert(selected); 
	}
	
	function deleteSMS(obj,index){
		var msg = 	"確定要刪除下列簡訊\n"
					+"msgid        content        sendTime\n";
		var selectedList = [];
		 $.each(selected, function(index, value){
			 msg+=leatherTypeList[value].phoneno+"     "+leatherTypeList[value].msgbody+"     "+leatherTypeList[value].sendtime+"\n";
			 selectedList.push(leatherTypeList[value]);
		 });
		 
		 if(confirm(msg)){
			 var input = JSON.stringify(selectedList);
			 //alert(input);
			 $.ajax({
			      url: '<s:url action="deleteSMS"/>',
			      data: {input:input},//parameters go here in object literal form
			      type: 'POST',
			      datatype: 'json',
			      success: function(json) {  
			    	  alert(json);
			    	  querySMS();
			    	},
			      error: function() { $("#Qmsg").html('something bad happened');  
			      },
			      beforeSend:function(){
		    		  $(':button').attr('disabled', 'disabled');
		          },
		          complete:function(){
		        	  $(':button').removeAttr('disabled');
		          }
		    });
		 };
		 
		 selected=[];
		
	}
	function changeSMS(obj,index){
		var msg = 	"確定要變更下列簡訊\n"
					+"msgid        content        sendTime\n";
		var selectedList = [];
		$.each(selected, function(index, value){
			 msg+=leatherTypeList[value].phoneno+"     "+leatherTypeList[value].msgbody+"     "+leatherTypeList[value].sendtime+"\n";
			 selectedList.push(leatherTypeList[value]);
		 });
		 
		if(confirm(msg)){
			 var input = JSON.stringify(selectedList);
			 var ndate = $("#ndate").val();
			 $.ajax({
			      url: '<s:url action="changeSMS"/>',
			      data: {input:input,ndate:ndate},//parameters go here in object literal form
			      type: 'POST',
			      datatype: 'json',
			      success: function(json) {  
			    	  alert(json);
			    	  querySMS();
			    	},
			      error: function() { $("#Qmsg").html('something bad happened');  
			      },
		    	  beforeSend:function(){
		    		  $(':button').attr('disabled', 'disabled');
		          },
		          complete:function(){
		        	  $(':button').removeAttr('disabled');
		          }
		    });
		 };
		 
		 selected=[];
		
	}
	
</script>
</head>
<body>
<a href="../index.jsp" >回首頁</a>
<br>
	<label>帳號</label>
	<input type="text" id="account">
	<label>對方門號</label>
	<input type="text" id="phone">
	<br>
	排程
	<br>
	<label>開始時間(yyyyMMddHHmiss)</label>
	<input type="text" id="sdate">
	<label>結束時間(yyyyMMddHHmiss)</label>
	<input type="text" id="edate">
	<br>
	<label>變更時間(yyyyMMddHHmiss)</label>
	<input type="text" id="ndate">
	<button onclick="querySMS()">query</button>
	<button onclick="changeSMS()">change</button>
	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	<button onclick="deleteSMS()" >delete</button>
	<!-- <div>
		<textarea rows="30" cols="20" id="result"></textarea>
	</div> -->
	<br>
	<br>
	<table id="table" border="1" background="yellow" align="center">
	</table>
	
</body>
</html>