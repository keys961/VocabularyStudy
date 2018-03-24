<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/24 0024
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>测验::${test.category.category}</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="container">
                <div class="jumbotron">
                    <div class="container">
                        <h1>测验::${test.category.category}</h1>
                        <hr>
                        <br>
                        <sf:form commandName="test" method="post" onsubmit="return checkInfo()">
                            <c:forEach var="item" items="${test.problemList}">

                            </c:forEach>
                        </sf:form>
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
            return true;
        }

    </script>

</body>
</html>

