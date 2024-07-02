<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="dao" class="com.mbcaca.boardDAO"/>
<jsp:useBean id="b" class="com.mbcaca.bVO">
	<jsp:setProperty name = "b" property="title" param = "title"/>
	<jsp:setProperty name = "b" property="author" param = "author"/>
	<jsp:setProperty name = "b" property="contents" param = "contents"/>
</jsp:useBean>

<c:set var="saved" value="${dao.addBnum(b)}"/>
{"saved" : ${saved}}