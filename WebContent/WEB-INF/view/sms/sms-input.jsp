<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/iglomo.css">
<title>簡訊發送服務</title>
<s:head />
</head>
<body>
	<h3>簡訊發送服務</h3>

	<s:form action="send" method="post" validate="true" cssClass="bordered">

		<s:textfield name="reqItem.username" label="使用者名稱" />
		<s:password name="reqItem.passowrd" label="密碼" />
		<s:textfield name="reqItem.orgcode" label="組織代碼" />
		<s:textfield name="reqItem.requestItemList[0].schedule" label="排程時間" />
		<s:textfield name="reqItem.requestItemList[0].callee" label="電話號碼" />
		<s:textarea name="reqItem.requestItemList[0].message" label="簡訊內容"
			cols="25" rows="5" />
		<s:textarea name="reqItem.remark" label="附註" cols="25" rows="1" />
		<s:submit value="發送" />

	</s:form>

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