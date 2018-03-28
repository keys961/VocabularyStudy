<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/24 0024
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>测验</title>
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
                    <p>点击下面任何一个单词集测验按钮即可开始测验！</p>
                </div>
                <div class="jumbotron">
                    <div class="container">
                        <h1>测验</h1>
                        <br>
                        <p>选择下方的测验按钮即可开始测验,或者查看您的历史记录。
                            <a href="<c:url value="/test/history"/>"><button class="btn btn-primary">查看历史记录</button></a></p>
                        <hr>
                        <br>
                        <c:forEach var="category" items="${categoryList}">
                            <div class="row">
                                <div class="col-sm-10">
                                    <p class="glyphicon glyphicon-tags">
                                        测验: ${category.category}
                                    </p>
                                </div>
                                <div class="col-sm-2" align="right">
                                    <a href="<c:url value="/test/category/"/>${category.id}"><button class="btn btn-primary">点击开始测验</button></a>
                                </div><br>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <%@ include file="../include/footer.jsp" %>
    <%@ include file="../include/script.jsp" %>

</body>
</html>
