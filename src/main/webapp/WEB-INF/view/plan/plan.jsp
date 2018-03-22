<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/21 0021
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>学习计划</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="container">
                <div class="jumbotron">
                    <div class="container">
                        <h1>学习计划</h1>
                        <hr>
                        <br>
                        <c:choose>
                            <c:when test="${learnPlan != null}">
                                <h2>您正在学习:</h2><br>
                                <p class="glyphicon glyphicon-tags">
                                    <c:out value="${learnPlan.category.category}"/>
                                </p>
                                <p>进度: ${learned}/${total}</p>
                                <p>开始时间: ${learnPlan.startTime.toString()}</p>
                                <p>结束时间: ${learnPlan.endTime.toString()}</p>
                                <button class="btn btn-danger" id="cancel">取消计划</button>
                            </c:when>
                            <c:otherwise>
                                <h2>您暂时没有制定任何学习计划，请选择一个学习计划并确定截止时间：</h2>
                                <div class="row">
                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                    <p class="glyphicon glyphicon-time"> 截止日期:</p>
                                    </div>
                                    <div class="col-sm-10 col-md-10 col-lg-10">
                                        <input type="date" class="form-control" id="endTime">
                                    </div>
                                </div>
                                <c:forEach var="i" begin="0" end="${categoryList.size() - 1}" step="1">
                                    <div class="row">
                                        <div class="col-sm-10">
                                        <p class="glyphicon glyphicon-tags">
                                            <c:out value="${categoryList.get(i).category}"/>: 总共<c:out value="${totalAmountList.get(i)}"/>词
                                        </p>
                                        </div>
                                        <div class="col-sm-2" align="right">
                                        <button class="btn btn-primary" id="${categoryList.get(i).category}">点击学习</button>
                                        </div><br>
                                    </div>
                                </c:forEach>
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
                <c:choose>
                <c:when test="${learnPlan != null}">
                $("#cancel").click
                (
                    function ()
                    {
                        $.post
                        (
                            "<c:url value="/plan/removePlan"/>",
                            {categoryId: <c:out value="${learnPlan.category.id}"/>},
                            function ()
                            {
                                alert("已取消当前计划!");
                                window.location.href = "<c:url value='/plan/learnPlanPage'/>";
                            }
                        );
                    }
                );
                </c:when>
                <c:otherwise>
                <c:forEach var="i" begin="0" end="${categoryList.size() - 1}" step="1">
                $("#<c:out value='${categoryList.get(i).category}'/>").click
                (
                    function ()
                    {
                        var date = $("#endTime").val();
                        if("" == date)
                        {
                            alert("您未输入截止日期!");
                            return;
                        }

                        $.post
                        (
                            "<c:url value='/plan/addPlan/'/>",
                            {
                                categoryId: <c:out value="${categoryList.get(i).id}"/>,
                                date: date
                            },
                            function ()
                            {
                                alert("添加计划成功!");
                                window.location.href = "<c:url value='/plan/learnPlanPage'/>";
                            }
                        )
                    }
                );
                </c:forEach>
                </c:otherwise>
                </c:choose>
            }
        );
    </script>
</body>
</html>
