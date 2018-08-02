<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/26
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bizLesson" uri="http://com.biz.lesson/tag/core" %>
<%@ taglib prefix="lesson" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>欢迎使用 LESSION 6</title>
</head>
<body>

<lesson:page title="">

    <jsp:body>
        <table id="simple-table" class="table  table-bordered table-hover">
            <tr>
                <td>${stu}</td>
            </tr>
            <c:forEach items="${stu.lessions}" var="lession">
                <tr>
                    <td>${lession.id}</td>
                    <td>${lession.id}</td>
                </tr>
            </c:forEach>
        </table>
    </jsp:body>
</lesson:page>

</body>
</html>
