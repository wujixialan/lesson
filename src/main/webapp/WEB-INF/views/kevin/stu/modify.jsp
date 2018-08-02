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
                $('.date-picker').datepicker({
                    autoclose: true,
                    todayHighlight: true
                });
                layui.use(["upload", "form"], function () {
                    var $upload = layui.upload;
                    var $form = layui.form;
                    $form.render();

                    $form.verify({
                        stuNum: function (value, item) {
                            if (value.trim() === "") {
                                return "学号不能为空"
                            }
                            if (!new RegExp(/^[\d+]{12,12}$/).test(value)) {
                                return "学号只能为数字且为 12 位";
                            }
                        },
                        stuName: function (value, item) {
                            if (value.trim() === "") {
                                return "姓名不能为空"
                            }
                        },
                        score: function (value, item) {
                            if (value.trim() === "") {
                                return "平均分不能为空"
                            }
                            if (!new RegExp(/^(0|\d{1,2}|100)(\.\d)?$/).test(value)) {
                                return "平均分填写不正确，必须是0-100之间的数字";
                            }
                        }
                    });

                    $form.on("submit(modify)", function (data) {
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
                                    window.location.href = "${pageContext.servletContext.contextPath}/stu/to/0/list.do";
                                }
                            }
                        });
                        return false;
                    });


                    $upload.render({
                        //绑定元素
                        elem: '#photo',
                        type: "post",
                        //上传接口
                        url: '${pageContext.servletContext.contextPath}/stu/upload.do',
                        done: function (res) {
                            //上传完毕回调
                            if (res.code === 200) {
                                // $("#img").prop("src", res.msg);
                                $("input[name=photo]").val(res.msg);
                            }
                        }
                        , error: function () {
                            //请求异常回调
                        }
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

        <li class="active"><spring:message code="student.title.edit"/></li>
    </ul>
</div>
<br><br>
<div class="container">
        <%--img地址： ${pageContext.servletContext.contextPath}/static-resource/ace/assets/images/avatars/profile-pic.jpg--%>
    <div id="user-profile-1" class="user-profile row">
        <div class="col-xs-12 col-sm-3 center">
            <span class="profile-picture">
                <img id="img" class="editable img-responsive" alt="Alex's Avatar"
                     src="${pageContext.servletContext.contextPath}/static-resource/ace/assets/images/avatars/profile-pic.jpg"/>
            </span>
            <br>
            <button type="button" class="layui-btn" id="photo">
                <i class="layui-icon">&#xe67c;</i>上传图片
            </button>
        </div>

        <div class="col-sm-9">
            <form class="form-horizontal layui-form" role="form"
                  action="${pageContext.servletContext.contextPath}/stu/modify.do" method="post">
                <input type="hidden" name="id" value="${stu.id}">
                <input type="hidden" name="_method" value="PUT">
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="stuNum">学号</label>
                    <div class="col-sm-9">
                        <input type="text" name="stuNum" id="stuNum" class="col-xs-10 col-sm-5"
                               placeholder="请输入学号" lay-verify="stuNum" value="${stu.stuNum}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="stuName">姓名</label>
                    <div class="col-sm-9">
                        <input type="text" name="stuName" id="stuName" class="col-xs-10 col-sm-5"
                               placeholder="请输入姓名" lay-verify="stuName" value="${stu.stuName}">
                    </div>
                </div>
                <input class="layui-hide" name="photo" value="">
                <div class="layui-form-item layui-col-lg-offset2">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-block">
                        <c:if test="${stu.gender == '1'}">
                            <input type="radio" name="gender" value="1" title="男" checked>
                            <input type="radio" name="gender" value="0" title="女">
                        </c:if>
                        <c:if test="${stu.gender == '0'}">
                            <input type="radio" name="gender" value="1" title="男">
                            <input type="radio" name="gender" value="0" title="女" checked>
                        </c:if>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="id-date-picker-1">出生年月</label>
                    <div class="col-sm-4">
                        <div class="input-group">
                            <input class="form-control date-picker" id="id-date-picker-1" type="text"
                                   data-date-format="yyyy-mm-dd" lay-verify="required" name="birth" readonly
                                   value="${stu.birth}">
                            <span class="input-group-addon">
                                    <i class="fa fa-calendar"></i>
                            </span>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="stuNum">平均分</label>
                    <div class="col-sm-9">
                        <input type="text" name="score" id="score" class="col-xs-10 col-sm-5"
                               placeholder="请输入平均分" lay-verify="score" value="${stu.score}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="lession">选修科目数</label>
                    <div class="col-sm-9">
                        <input type="text" name="lession" value="0" id="lession" class="col-xs-10 col-sm-5"
                               placeholder="请输入选修科目数" value="${stu.lession}" readonly lay-verify="lession">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="clazz">班级</label>
                    <div class="col-sm-4">
                        <select id="clazz" name="clazz.id" lay-verify="required">
                            <c:forEach items="${clazzes}" var="clazz">
                                <option value="${clazz.id}">${clazz.clazzName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button class="btn btn-info" lay-submit lay-filter="modify">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            修改
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
