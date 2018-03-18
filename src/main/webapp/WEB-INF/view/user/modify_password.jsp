<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/18 0018
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>更改密码</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-sm-12 col-md-12 column">
            <div class="container">
                <div class="jumbotron">
                    <div class="container form-group">
                        <h1>更改密码</h1>
                        <hr />
                        <sf:form commandName="form" method="post" onsubmit="return checkInfo()">
                            <label>用户名:</label>
                            <sf:input path="username" cssClass="form-control" autocomplete="true" readonly="true"/>
                            <br>
                            <label>旧密码:</label>
                            <sf:password path="originPassword" cssClass="form-control" maxlength="20"/>
                            <br>
                            <label>新密码:</label>
                            <sf:password path="newPassword" cssClass="form-control" maxlength="20"/>
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
        var oldPassword = $("#oldPassword").val();
        var newPassword = $("#newPassword").val();

        if(oldPassword == "" || newPassword == "" || oldPassword === newPassword)
        {
            alert("输入有误！请重试！");
            return false;
        }

        return true;
    }
</script>
</body>
</html>
