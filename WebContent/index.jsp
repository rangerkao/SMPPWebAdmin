<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SMS WEB - Welcome</title>
</head>
<body>
<h1>Welcome!</h1>


<p><a href="<s:url action='sms/input'  />" >SMS SEND</a></p>
<p><a href="<s:url action='sms/control'  />" >SCHEDUALED SMS CONTROL</a></p>
<p><a href="http://192.168.10.199:8080/smpp/" >SMS REPORT</a></p>

</body>
</html>