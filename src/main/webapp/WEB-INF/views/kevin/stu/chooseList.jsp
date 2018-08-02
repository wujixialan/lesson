<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/26
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bizLesson" uri="http://com.biz.lesson/tag/core" %>
<%@ taglib prefix="lesson" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>欢迎使用 LESSION 6</title>
</head>
<body>

<lesson:page title="">
    <jsp:attribute name="script">
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/webjars/layui/2.3.0/css/layui.css">
        <script language="JavaScript"
                src="${pageContext.servletContext.contextPath}/webjars/layui/2.3.0/layui.all.js"></script>
        <script language="JavaScript"
                src="${pageContext.servletContext.contextPath}/static-resource/ace/assets/js/daterangepicker.min.js"></script>
        <script language="JavaScript">
            $(function () {
                layui.use(["table", "util", "layer", "laytpl"], function () {

                    var $table = layui.table;
                    var $utils = layui.util;
                    var $layer = layui.layer;
                    var $laytpl = layui.laytpl;
                    var $ = layui.$, active = {
                        reload: function () {
                            var demoReload = $('#demoReload');
                            $table.reload('chooseList', {
                                page: true,
                            });
                        }
                    };

                    $('.demoTable .layui-btn').on('click', function () {
                        console.log(this);
                        var type = $(this).data('type');
                        console.log(active[type]);
                        active[type] ? active[type].call(this) : '';
                    });

                    $("#stuNum").keyup(function () {
                        $('.demoTable .layui-btn').click();
                    });
                    $("#stuName").keyup(function () {
                        $('.demoTable .layui-btn').click();
                    });
                    $("#birthVal").change(function () {
                        $('.demoTable .layui-btn').click();
                    });

                    //第一个实例
                    $table.render({
                        elem: '#chooseList',
                        height: 471,
                        //数据接口,
                        url: '${pageContext.servletContext.contextPath}/stu/${stuId}/chooseList.do',
                        method: "post",
                        //开启分页
                        page: true,
                        //表头
                        cols: [[
                            {field: 'id', title: 'ID', align: 'center', width: 150,},
                            {field: 'subName', title: '学科名称', align: 'center',},
                            {field: 'perNum', title: '选课人数', align: 'center',},
                            {field: 'score', title: '平均分', align: 'center',},
                            {field: 'right', title: '操作', toolbar: "#barDemo", align: 'center',}
                        ]],

                    });

                    $table.on("tool(chooseList)", function (obj) {
                        if (obj.event === "enter") {
                            console.log(obj.data);
                            bootbox.prompt({
                                title: "成绩",
                                type: "number",
                                buttons: {
                                    confirm: {
                                        label: '确认',
                                        className: 'btn-success'
                                    },
                                    cancel: {
                                        label: '取消',
                                        className: 'btn-danger'
                                    }
                                },
                                callback: function(value) {
                                    console.log(value);
                                    if (value != null) {
                                        if (value < 0 || value > 100) {
                                            bootbox.alert("成绩只能0-100");
                                            return false;
                                        }
                                        if (!new RegExp(/^(0|\d{1,2}|100)(\.\d)?$/).test(value)) {
                                            bootbox.alert("成绩只能是数字");
                                            return false;
                                        }

                                        $.ajax({
                                            url: "${pageContext.servletContext.contextPath}/stu/enter.do",
                                            type: "post",
                                            data: {
                                                score: value,
                                                lessionId: obj.data.id,
                                                stuId: $("input[name=stuId]").val(),
                                            },
                                            dataType: "json",
                                            success: function (res) {
                                                if (res.code === 200) {
                                                    window.location.href = "${pageContext.servletContext.contextPath}/stu/to/" + ${stuId} + "/chooseList.do"
                                                }
                                            },
                                        });
                                    }
                                },
                            });
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

                <li class="active"><spring:message code="student.title.list"/></li>
            </ul>
        </div>
        <div>
            <br>
            <div class="layui-row">
                <br>
                <label class="layui-card layui-col-lg-offset5"><h3> ${stu.stuName} </h3></label>
                <input type="hidden" name="stuId" value="${stu.id}">
            </div>
            <table id="chooseList" lay-filter="chooseList"></table>

            <script type="text/html" id="barDemo">
                <a class="layui-btn layui-btn-xs layui-btn-radius layui-icon layui-icon-edit" lay-event="enter">
                    录入</a>
            </script>


        </div>
    </jsp:body>
</lesson:page>

</body>
</html>
