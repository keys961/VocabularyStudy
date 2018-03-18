<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/18 0018
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>更改个人信息</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="container">
                <div class="jumbotron">
                    <div class="container form-group">
                        <h1>更改个人信息</h1>
                        <hr />
                        <sf:form commandName="form" method="post" onsubmit="return checkInfo()">
                            <label>用户名:</label>
                            <sf:input path="username" cssClass="form-control" autocomplete="true" readonly="true"/>
                            <br>
                            <label>邮箱:</label>
                            <sf:input path="email" cssClass="form-control"/>
                            <br>
                            <button class="btn btn-primary" type="submit">提交修改</button>
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
