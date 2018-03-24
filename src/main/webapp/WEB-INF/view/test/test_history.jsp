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
    <title>测验记录</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="container">
                <div class="jumbotron">
                    <div class="container">
                        <h1>测验记录</h1>
                        <hr>
                        <br>
                        <div class="row">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>测验号</th>
                                    <th>测验单词集</th>
                                    <th>您的分数</th>
                                    <th>总分</th>
                                </tr>
                                </thead>
                                <tbody id="testHistory">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="btn btn-info" style="position:fixed; right:10px; bottom:10px;" id="toTop">返回顶部</div>

    <%@ include file="../include/footer.jsp" %>
    <%@ include file="../include/script.jsp" %>

    <script type="text/javascript">
        $(document).ready
        (
            function ()
            {
                var offset = 0; //global vars..
                $.get //init load
                (
                    "/test/history/0",
                    function (result)
                    {
                        $("#testHistory").empty();
                        for(let i = 0; i < result.length; i++)
                        {
                            let appendText = getItem(result[i]);
                            $("#testHistory").append(appendText);
                        }
                        offset += result.length;
                    }
                );


                $(window).scroll
                (
                    function ()
                    {
                        let scrollTop = $(this).scrollTop();
                        let scrollHeight = $(document).height();
                        let clientHeight = $(this).height();
                        if(scrollTop + clientHeight + 10 >= scrollHeight)
                        {
                            $.get
                            (
                                "/test/history/" + offset,
                                function (result)
                                {
                                    for(let i = 0; i < result.length; i++)
                                    {
                                        let appendText = getItem(result[i]);
                                        $("#testHistory").append(appendText);
                                    }

                                    offset += result.length;
                                }
                            )
                        }
                    }
                );

                $("#toTop").click
                (
                    function ()
                    {
                        $("html,body").animate({scrollTop: 0}, 500);
                    }
                );
            }
        );

        function getItem(item)
        {
            let id = item.id;
            let category = item.category.category;
            let score = item.score;
            let totalScore = item.totalScore;

            return "<tr><td>"+ id +"</td>"
                + "<td>" + category + "</td>"
                + "<td>" + score + "</td>" +
                "<td>" + totalScore + "</td></tr>";
        }

    </script>
</body>
</html>