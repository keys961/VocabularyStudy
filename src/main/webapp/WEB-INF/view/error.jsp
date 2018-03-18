<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/18 0018
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>出错啦</title>
</head>
<body>
<script type="text/javascript">
    alert("<%=(String)request.getAttribute("info")%>");
    window.location.href = "<%=(String)request.getAttribute("link")%>";
</script>
</body>
</html>
