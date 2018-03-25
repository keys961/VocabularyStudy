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
    <title class="title">测验</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="container">
                <div class="jumbotron">
                    <div class="container">
                        <h1 class="title">测验</h1>
                        <hr>
                        <br>
                        <div class="row form-horizontal controls" id="problemSet">
                            <div class="form-group">
                                <div class="col-sm-3">
                                    <p class="glyphicon glyphicon-tags form-control-static">   Fuck</p>
                                </div>
                                <div class="col-sm-9" align="right">
                                    <label class="radio-inline">
                                        <input type="radio" name="optionsRadiosinline" value="option1" checked> 选项 1
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="optionsRadiosinline" value="option2"> 选项 2
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="optionsRadiosinline" value="option2"> 选项 2
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-3">
                                    <p class="glyphicon glyphicon-tags form-control-static">   Fuck</p>
                                </div>
                                <div class="col-sm-9" align="right">
                                    <label class="radio-inline">
                                        <input type="radio" name="optionsRadiosinline1" value="option1" checked> 选项 1
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="optionsRadiosinline1" value="option2"> 选项 2
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="optionsRadiosinline1" value="option2"> 选项 2
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="row" align="right">
                            <button class="btn btn-primary" id="submit">提交结果</button>
                        </div>
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
                var testData; //test data - json format
                $.get
                (
                    "<c:url value="/test/generate/"/>${categoryId}",
                    function (result)
                    {
                        testData = result;
                        $(".title").text("测验::" + result.category.category);

                        let problemList = result.problemList;
                        $("#problemSet").empty();
                        for(let i = 0; i < problemList.length; i++)
                        {
                            $("#problemSet").append(generateProblemItem(i + 1, problemList[i]));
                        }
                    }
                );

                $("#submit").click
                (
                    function ()
                    {
                        $("input:radio:checked").each
                        (
                            function ()
                            {
                                testData.answerList.push(this.value);
                            }
                        );

                        $.ajax
                        (
                            {
                                type: "POST",
                                url: "<c:url value="/test/submit"/>",
                                data: JSON.stringify(testData),
                                datatype: "json",
                                contentType: "application/json;charset=UTF-8",
                                success:
                                    function (result)
                                    {
                                        let score = result.score;
                                        let total = result.totalScore;
                                        alert("提交成功!您的得分为:" + score + "/" + total);
                                        window.location.href = "<c:url value="/test/history"/>";
                                    },
                                error:
                                    function(result)
                                    {
                                        alert("内部错误,请重试!");
                                    }
                            }
                        )
                    }
                );
            }
        );

        function generateProblemItem(id, item)
        {
            let problemText = item.problem;
            let optionList = item.options;

            return  "<div class='form-group'>\n" +
                "                                <div class='col-sm-3'>\n" +
                "                                    <p class='glyphicon glyphicon-tags form-control-static'>  "+ problemText  + "</p>\n" +
                "                                </div>\n" +
                "                                <div class='col-sm-9' align=>\n" +
                "                                    <label class='radio-inline'>\n" +
                "                                        <input type='radio' name='"+ problemText +"' value='0' checked>A. "+ optionList[0] + "\n" +
                "                                    </label>\n" +
                "                                    <label class='radio-inline'>\n" +
                "                                        <input type='radio' name='"+ problemText +"' value='1'>B. "+ optionList[1] + "\n" +
                "                                    </label>\n" +
                "                                    <label class='radio-inline'>\n" +
                "                                        <input type='radio' name='"+ problemText +"' value='2'>C. "+ optionList[2] + "\n" +
                "                                    </label>\n" +
                "                                </div>\n" +
                "                            </div>"
        }

    </script>

</body>
</html>

