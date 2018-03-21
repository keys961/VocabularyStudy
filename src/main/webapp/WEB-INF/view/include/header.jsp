<%--
  Created by IntelliJ IDEA.
  User: keys9
  Date: 2018/3/17 0017
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="vocabularystudy.config.SecurityConfig" %>
<%@ page import="vocabularystudy.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<div class="container">
    <!-- Navbar -->
    <div class="row clearfix">
        <div class="col-md-12 column">
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="<c:url value="/"/>">单词学习</a>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <% if(session.getAttribute(SecurityConfig.SESSION_KEY) != null) { %>
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="<c:url value="/user/profile"/>">欢迎: <%=((User)session.getAttribute(SecurityConfig.SESSION_KEY)).getUsername()%></a>
                        </li>
                        <li>
                            <a href="<c:url value="/user/logout"/>">登出</a>
                        </li>
                    </ul>
                    <% }
                    else { %>
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="<c:url value="/user/login"/>">登录</a>
                        </li>
                        <li>
                            <a href="<c:url value="/user/register"/>">注册</a>
                        </li>
                    </ul>
                    <% } %>
                    <ul class="nav navbar-nav navbar-right ">
                        <% if(session.getAttribute(SecurityConfig.SESSION_KEY) != null) { %>
                        <li class="dropdown">
                            <a id="id_for_notice" href="#" class="dropdown-toggle" data-toggle="dropdown">
                                选项与设置
                                <strong class="caret"></strong>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="<c:url value="/user/modifyProfile"/>">编辑个人资料</a>
                                </li>
                                <li>
                                    <a href="<c:url value="/user/password"/>">更改密码</a>
                                </li>
                                <li>
                                    <a href="<c:url value="/plan/learnPlanPage"/>">选择学习单词集</a>
                                </li>
                                <li>
                                    <a href="<c:url value="/user/profile"/>">查看个人学习&学习进度</a>
                                </li>
                                <li>
                                    <a href="#">查看自定义收藏单词</a>
                                </li>
                                <li>
                                    <a href="#">测验</a>
                                </li>
                                <li class="divider">
                                </li>
                                <li>
                                    <a href="<c:url value="/user/logout"/>">登出</a>
                                </li>
                            </ul>
                        </li>
                        <% } %>
                        <li>
                            <a>    </a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="container-fluid">
                <div class="row-fluid">
                    <div class="span12">
                        <h2>
                            <br />
                        </h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
