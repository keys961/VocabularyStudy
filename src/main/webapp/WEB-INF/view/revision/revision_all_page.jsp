<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/28 0028
  Time: 19:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>复习单词</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="alert alert-dismissable alert-info">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <h4>
                提示!
            </h4>
            <p>学习结束后请点击“结束复习”按钮，否则复习进度不会被记录！</p>
        </div>
        <div class="col-md-3 column">
        </div>
        <div class="col-md-6 column">
            <div class="jumbotron">
                <a id="link" href="#">
                    <h1 id="word">

                    </h1>
                </a>
                <h2><small>进度: <span id="progress"></span>/<span id="total"></span></small></h2>
                <div class="collapse" id="show">
                    <p>
                        <b>音标:</b>
                    <p id="phonetic">
                    </p>
                    </p>
                    <p>
                        <b>释义:</b><br>
                    <p id="translation"></p>
                    </p>
                </div>
                <p align="right">
                    <button class="btn btn-primary" data-toggle="collapse"
                            data-target="#show">释义</button>
                    <button class="btn btn-success" id="yes">知道</button>
                    <button class="btn btn-warning" id="no">不知道</button>
                    <button class="btn btn-danger" id="end">结束复习</button>
                </p>
            </div>
        </div>
        <div class="col-md-3 column">
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
            var learnTaskItemList;
            var learnedList = [];
            var currentIndex = 0;
            var progress = 0;
            //TODO: Front-end of revision page
            $.get
            (
                "<c:url value="/learn/getLearnTask"/>",
                function (result)
                {
                    learnTaskItemList = result;
                    $("#total").text(result.length);
                    $("#progress").text(progress);
                    showResult(learnTaskItemList, 0);
                }
            );

            $("#yes").click
            (
                function ()
                {
                    if(currentIndex + 1 === learnTaskItemList.length)
                    {
                        alert("这是最后一个单词了！请您点击结束按钮结束学习！");
                        return;
                    }
                    learnedList.push(currentIndex);
                    $("#progress").text(++progress);
                    currentIndex++;
                    showResult(learnTaskItemList, currentIndex);
                }
            );

            $("#no").click
            (
                function ()
                {
                    if(currentIndex + 1 === learnTaskItemList.length)
                    {
                        alert("这是最后一个单词了！请您点击结束按钮结束学习！");
                        return;
                    }

                    currentIndex++;
                    showResult(learnTaskItemList, currentIndex);
                }
            );

            $("#end").click
            (
                function ()
                {
                    $.ajax
                    (
                        {
                            type: "POST",
                            url: "<c:url value="/learn/save"/>",
                            data:
                                JSON.stringify
                                (
                                    {
                                        learnTaskItemList : learnTaskItemList ,
                                        learnedList : learnedList
                                    }
                                ),
                            datatype: "json",
                            contentType: "application/json;charset=UTF-8",
                            success:
                                function ()
                                {
                                    alert("您的学习进度已更新!");
                                    window.location.href = "<c:url value="/"/>";
                                },
                            error:
                                function()
                                {
                                    alert("学习进度提交失败!");
                                }
                        }
                    );
                }
            );
        }
    );

    function showResult(learnTaskItemList, index)
    {
        let word = learnTaskItemList[index].word.word;
        let translation = learnTaskItemList[index].word.translation;
        let phonetic = learnTaskItemList[index].word.phonetic;

        let line = translation.split('\n');

        $("#word").text(word);
        $("#link").attr("href", "/vocabulary/" + learnTaskItemList[index].word.id);
        $("#translation").empty();
        for(let i = 0; i < line.length; i++)
        {
            $("#translation").append(line[i]);
            $("#translation").append("<br/>");
        }
        $("#phonetic").text(phonetic);
        $("#show").attr("class", "collapse");
    }

</script>
</body>
</html>

