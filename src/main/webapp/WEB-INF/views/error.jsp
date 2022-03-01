<%--
  Created by IntelliJ IDEA.
  User: choeuiseung
  Date: 2021/12/21
  Time: 4:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <p>오류가 발생하였습니다. => [${path}] ${status} ${error}</p>
    <p>일시 : ${timestamp.toLocaleString()}</p>
    <p>내용 : ${message}</p>
</body>
</html>
