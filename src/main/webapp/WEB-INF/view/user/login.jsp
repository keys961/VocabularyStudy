<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/18 0018
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登陆</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="container">
                <div class="jumbotron">
                    <div class="container">
                        <h1>用户登陆</h1>
                        <hr>
                        <sf:form commandName="loginForm" method="post">
                            <label>用户名:</label>
                            <sf:input path="username" cssClass="form-control" autocomplete="true" maxlength="20"/>
                            <br>
                            <label>密码:</label>
                            <sf:password path="password" cssClass="form-control" autocomplete="true" maxlength="20"/>
                            <br>
                            <button class="btn btn-primary" type="submit">登录</button>
                            <br><br>
                            <!-- <a href="#">忘记密码？</a> -->
                        </sf:form>
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
