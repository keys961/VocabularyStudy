<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>单词学习::主页</title>
</head>
<body>

    <%@ include file="include/header.jsp" %>

    <div class="container">
        <div class="row clearfix">
            <div class="col-sm-12 col-md-12 column">
                <div class="container">
                    <div class="jumbotron">
                        <div class="container">
                            <h1>欢迎来到单词学习网站</h1>
                            <hr>
                            <%if(session.getAttribute(SecurityConfig.SESSION_KEY) == null){%>
                            <p><small>开始学习请: </small><p>
                            <a href="<c:url value="/user/register"/>"><button class="btn btn-primary">注册</button></a>
                            或
                            <a href="<c:url value="/user/login"/>"><button class="btn">登陆</button></a>
                            <%}else{%>
                            <p>Hello, <%=((User)session.getAttribute(SecurityConfig.SESSION_KEY)).getUsername()%></p>
                            <c:choose>
                                <c:when test="${plan != null}">
                                    <p>当前正在学习: <c:out value="${plan.category.category}"/>, 进度: <c:out value="${learned}"/>/<c:out value="${total}"/>.</p>
                                    <p>今日要学习的单词数: ${needLearn}!</p>
                                    <a href="<c:url value="/learn/task"/>"><button class="btn btn-primary">点击继续学习!</button></a>
                                    <a href="<c:url value="/revision/recent/"/>"><button class="btn btn-primary">复习最近学习的单词!</button></a>
                                    <a href="<c:url value="/revision/all/"/>"><button class="btn btn-primary">复习所有单词!</button></a>
                                </c:when>
                                <c:otherwise>
                                    <p>当前您没有正在学习的单词集.</p>
                                    <a href="<c:url value="/plan/learnPlanPage"/>"><button class="btn btn-primary">点此开始学习!</button></a>
                                </c:otherwise>
                            </c:choose>
                            <%}%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="include/footer.jsp" %>
    <%@ include file="include/script.jsp" %>

</body>
</html>
