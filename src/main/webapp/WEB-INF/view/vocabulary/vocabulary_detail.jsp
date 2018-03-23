<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/23 0023
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${word.word}</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="container">
                <div class="jumbotron">
                    <div class="container">
                        <h1>${word.word}</h1>
                        <hr>
                        <br>
                        <p class="glyphicon glyphicon-tags"> 发音: ${word.phonetic} </p><br>
                        <p class="glyphicon glyphicon-tags"> 释义: <br><br> ${word.translation} </p><br>
                        <p class="glyphicon glyphicon-tags"> 类别标签: ${word.tag} </p><br>
                        <button id="collection" class="btn btn-primary">点击收藏</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../include/footer.jsp" %>
    <%@ include file="../include/script.jsp" %>

</body>
</html>
