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
                        <p> <b>发音:</b> ${word.phonetic} </p>
                        <p> <b>释义:</b> <br> ${word.translation} </p>
                        <p class="glyphicon glyphicon-tags"> <b>类别标签</b>: ${word.tag} </p><br>
                        <c:choose>
                            <c:when test="${isCollected}">
                                <button id="collection" class="btn btn-success">取消收藏</button>
                            </c:when>
                            <c:otherwise>
                                <button id="collection" class="btn btn-primary">点击收藏</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../include/footer.jsp" %>
    <%@ include file="../include/script.jsp" %>

    <script type="text/javascript">
        $(document).ready
        (
           function ()
           {
              let vocabularyId = ${word.id};
              $("#collection").click
              (
                function ()
                {
                    if($("#collection").text() == "点击收藏")
                    {
                        $.post
                        (
                            "<c:url value="/collection/add"/>",
                            {id: vocabularyId},
                            function ()
                            {
                                alert("收藏成功!");
                                $("#collection").text("取消收藏");
                                $("#collection").attr("class", "btn btn-success");
                            }
                        )
                    }
                    else
                    {
                        $.post
                        (
                            "<c:url value="/collection/remove"/>",
                            {id: vocabularyId},
                            function ()
                            {
                                alert("取消收藏成功!");
                                $("#collection").text("点击收藏");
                                $("#collection").attr("class", "btn btn-primary");
                            }
                        )
                    }
                }
              );
           }
        );
    </script>

</body>
</html>
