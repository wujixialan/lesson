<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/26
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bizLesson" uri="http://com.biz.lesson/tag/core" %>
<%@ taglib prefix="lesson" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>欢迎使用 LESSION 6</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/webjars/layui/2.3.0/css/layui.css">
    <script language="JavaScript"
            src="${pageContext.servletContext.contextPath}/webjars/layui/2.3.0/layui.all.js"></script>
</head>
<body>
<lesson:page title="">
<jsp:attribute name="script">
        <script language="JavaScript">
            $(function () {
                layui.use(["upload", "form"], function () {
                    var $upload = layui.upload;
                    var $form = layui.form;
                    $form.render();

                    $form.verify({
                        subName: function (value, item) {
                            if (value.trim() === "") {
                                return "课程名称不能为空"
                            }
                        },
                    });

                    $form.on("submit(add)", function (data) {
                        $.ajax({
                            url: data.form.action,
                            type: data.form.method,
                            data: data.field,
                            dataType: "json",
                            success: function (res) {
                                console.log(res);
                                if (res.code === 400) {
                                }
                                if (res.code === 200) {
                                    window.location.href = "${pageContext.servletContext.contextPath}/lession/to/0/list.do";
                                }
                            }
                        });
                        return false;
                    });
                });
            });
        </script>
    </jsp:attribute>

<jsp:body>
<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="ace-icon fa fa-home home-icon"></i>
            <a href="welcome.do"><spring:message code="common.homepage"/></a>
        </li>

        <li class="active"><spring:message code="lession.title.add"/></li>
    </ul>
</div>
<br><br>
<div class="container">
    <div id="user-profile-1" class="user-profile row">

        <div class="col-sm-9">
            <form class="form-horizontal layui-form" role="form" id="clazzForm"
                  action="${pageContext.servletContext.contextPath}/lession/add.do" method="post">
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="subName">课程名称</label>
                    <div class="col-sm-9">
                        <input type="text" name="subName" id="subName" placeholder="请输入课程名称"
                               class="col-xs-10 col-sm-5" lay-verify="subName">
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="score">平均分</label>
                    <div class="col-sm-9">
                        <input type="text" name="score" id="score" class="col-xs-10 col-sm-5"
                               placeholder="请输入平均分" value="0" readonly>
                    </div>
                </div>


                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button class="btn btn-info" lay-submit lay-filter="add">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            添加
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="btn" type="reset">
                            <i class="ace-icon fa fa-undo bigger-110"></i>
                            重置
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    </jsp:body>
    </lesson:page>
</body>
</html>
