<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/24 0024
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>个人收藏</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="container">
                <div class="jumbotron">
                    <div class="container">
                        <h1>个人收藏管理</h1>
                        <hr>
                        <br>
                        <p><button id="remove" class="btn btn-danger">删除勾选的单词</button></p>
                        <div class="row">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>.</th>
                                    <th>单词</th>
                                    <th>类别</th>
                                </tr>
                                </thead>
                                <tbody id="collection">
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
                    "/collection/get/0",
                    function (result)
                    {
                        $("#collection").empty();
                        for(let i = 0; i < result.length; i++)
                        {
                            let appendText = getItem(result[i]);
                            $("#collection").append(appendText);
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
                                "/collection/get/" + offset,
                                function (result)
                                {
                                    for(let i = 0; i < result.length; i++)
                                    {
                                        let appendText = getItem(result[i]);
                                        $("#collection").append(appendText);
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

                $("#remove").click
                (
                  function ()
                  {
                      let collectionList = [];

                      $.each
                      (
                          $('input:checkbox:checked'),
                          function()
                          {
                              collectionList.push(this.id);
                          }
                      );

                      $.ajax
                      (
                        {
                              type: "POST",
                              url: "<c:url value='/collection/removeList'/>",
                              data: JSON.stringify(collectionList),
                              datatype: "json",
                              contentType: "application/json;charset=UTF-8",
                              success:
                                function ()
                                {
                                    alert("删除成功!");
                                    window.location.href = "<c:url value="/collection/page"/>";
                                },
                              error:
                                function()
                                {
                                    alert("删除失败!");
                                }
                        }
                      );
                  }
                );
            }
        );

        function getItem(item)
        {
            let id = item.id;
            let word = item.word.word;
            let tag = item.word.tag;

            let result = "<tr><td><input type='checkbox' class='check-box' id='" + id + "'/></td>"
                    + "<td><a href='/vocabulary/" + item.word.id + "'>" + word + "</a></td>"
                    + "<td>" + tag + "</td>";

            return result;
        }

    </script>
</body>
</html>