<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/18 0018
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>注册</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="container">
                <div class="alert alert-dismissable alert-info">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <h4>
                        提示!
                    </h4>
                    <p>用户名长度在6~20之间，英文字母和数字且由英文开头。不得使用已注册过的用户名！</p>
                    <p>密码长度在6~20之间。</p>
                    <p>邮箱格式为example@domain.com，不得使用已注册过的邮箱！</p>
                </div>
                <div class="jumbotron">
                    <div class="container">
                        <h1>用户注册</h1>
                        <hr>
                        <sf:form commandName="user" method="post" onsubmit="return checkInfo()">
                            <label>用户名:</label>
                            <sf:input path="username" cssClass="form-control" autocomplete="true" maxlength="20"/>
                            <br>
                            <label>密码:</label>
                            <sf:password path="password" cssClass="form-control" autocomplete="true" maxlength="20"/>
                            <br>
                            <label>邮箱:</label>
                            <sf:input path="email" cssClass="form-control"/>
                            <br>
                            <button class="btn btn-primary" type="submit">提交注册</button>
                            <br><br>
                        </sf:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../include/footer.jsp" %>
<%@ include file="../include/script.jsp" %>

<script type="text/javascript">
    function checkInfo()
    {
        var username = $("#username").val();
        if(username == "" || (username.length > 20))
        {
            alert("用户名格式不正确");
            return false;
        }
        var password = $("#password").val();
        if(password == "" || (password.length > 20))
        {
            alert("密码格式不正确");
            return false;
        }
        var email = $("#email").val();
        var emailRegex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        if(!emailRegex.test(email))
        {
            alert("邮箱格式不正确!");
            return false;
        }
        return true;
    }
</script>
</body>
</html>

