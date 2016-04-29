<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>²�T�o�e�A�ȱ���</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">

$(document).ready(function(){

});
var leatherTypeList = [];
	function querySMS(){
		var acc = $("#account").val();
		var sdate = $("#sdate").val();
		var edate = $("#edate").val();
		var phone = $("#phone").val();
		if(!acc && !sdate && !edate){
			alert("�b���B����Цܤֿ�J�@������I");
			return;
		}
		if((sdate && !edate) || (!sdate && edate)){
			alert("���������]�t�_���I");
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
		    	   var nDate = new Date();
		    	  $.each(leatherTypeList, function(index, value){
		    		  var vDate = new Date(value.schedule.substring(0, 4)+"/"+value.schedule.substring(4, 6)+"/"+value.schedule.substring(6, 8)
		    				  +" "+value.schedule.substring(8, 10)+":"+value.schedule.substring(10, 12)+":"+value.schedule.substring(12, 14))
		    		  $("#table").append(	"<tr>"
		    		  							+"<td>"+value.msgid+"</td>"
		    		  							+"<td>"+value.seq+"</td>"
		    		  							+"<td>"+value.phoneno+"</td>"
		    		  							+"<td>"+value.schedule+"</td>"
		    		  							+"<td>"+value.msgbody+"</td>"
		    		  							+"<td>"+value.status+"</td>"
		    		  							+(vDate>nDate?"<td><input type='checkbox' onclick='checkBoxClick(this,"+index+")'></td>":"<td></td>")
		    		  						+"</tr>");
		    	  });
		    	  
		    	  //jQuery.parseJSON,JSON.parse(json)
		    	  //alert(json);
		    	  /* var list=$.parseJSON(json);
		    	  //$("#table1 tr:gt(0)").remove();//����>0����Ūtr
		    	  limitList=list['data'];
		    	  
		    	  if(limitList!=null)
		    		  dataList=limitList.slice(0);
		    	  
		    	  var error = list['error'];
		    	  $('#Error').html(error); */
		    	  },
		      error: function() { $("#Qmsg").html('something bad happened');  
		      },
	    	  beforeSend:function(){
	    		 /*  $("#Qmsg").html("���b�d�ߡA�еy��...");
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
		
		if(selected.length==0){
			alert("����ܭn�R�����ؼ�!");
			return;
		}
		
		var msg = 	"�T�w�n�R���U�C²�T\n"
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
		if(!$("#ndate").val()){
			alert("����J�n��諸�ؼЮɶ�!");
			return;
		}
		if(selected.length==0){
			alert("����ܧ�諸�ؼ�!");
			return;
		}
		
		var msg = 	"�T�w�n�ܧ�U�C²�T\n"
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
<a href="../index.jsp" >�^����</a>
<br>
	<label>�b��</label>
	<input type="text" id="account">
	<label>������</label>
	<input type="text" id="phone">
	<br>
	�Ƶ{
	<label>�}�l�ɶ�(yyyyMMddHHmiss)</label>
	<input type="text" id="sdate">
	<label>�����ɶ�(yyyyMMddHHmiss)</label>
	<input type="text" id="edate">
	<button onclick="querySMS()">query</button>
	<br>
	<label>�ܧ�ɶ�(yyyyMMddHHmiss)</label>
	<input type="text" id="ndate">
	<button onclick="changeSMS()">change</button>
	<br>
	<button onclick="deleteSMS()" >delete</button>
	<!-- <div>
		<textarea rows="30" cols="20" id="result"></textarea>
	</div> -->
	<br>
	
	<div align="center" style="width: 100%">
		<div align="left" style="width: 200px">
			�N������
			<br>
			�ǰe��:0,1,93,95,97,98,98
			<br>
			���\:2,9
			<br>
			����:3,4,5,7,8,94,96
		</div>
	
	<table id="table" border="1" background="yellow" align="center">
	</table>
	</div>
	
	
</body>
</html>