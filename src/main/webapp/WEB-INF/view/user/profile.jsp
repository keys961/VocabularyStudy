<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/18 0018
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>个人信息&学习进度</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="container">
                <div class="jumbotron">
                    <div class="container">
                        <h1>个人信息</h1>
                        <hr>
                        <br>
                        <p class="glyphicon glyphicon-user"> 用户名: <c:out value="${user.username}"/> </p><br>
                        <p class="glyphicon glyphicon-envelope"> 电子邮箱: <a href="mailto:<c:out value="${user.email}"/>"><c:out value="${user.email}"/></a></p><br>
                        <div class="row">
                            <div class="col-sm-10 col-md-10 main">
                                <h1 class="page-header">学习进度</h1>
                                <c:forEach var="progress" items="${progressList}">
                                    <p class="glyphicon glyphicon-tags">
                                         <c:out value="${progress.category.category}"/>: <c:out value="${progress.learned}"/>/<c:out value="${progress.total}"/>
                                    </p><br>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../include/footer.jsp" %>
    <%@ include file="../include/script.jsp" %>
</body>
</html>
