<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/28
  Time: 8:11
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
                            $table.reload('clazz', {
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

                    //第一个实例
                    $table.render({
                        elem: '#clazz',
                        height: 471,
                        //数据接口,
                        url: '${pageContext.servletContext.contextPath}/clazz/list.do',
                        method: "post",
                        //开启分页
                        page: true,
                        //表头
                        cols: [[
                            {field: 'id', title: 'ID', align: 'center', width: 150,},
                            {field: 'clazzName', title: '班级名', align: 'center', width: 200,},
                            {field: 'perNum', title: '人数', align: 'center', width: 200,},
                            {field: 'score', title: '平均分', align: 'center', width: 200,},
                            {field: 'right', title: '操作', toolbar: "#barDemo", align: 'center',}
                        ]],
                        done: function (res) {
                            /**
                             * 隐藏 id 列。
                             */
                            // $("[data-field='id']").css('display', 'none');
                            console.log(res);
                        }
                    });

                    $table.on("tool(clazz)", function (obj) {
                        if (obj.event === "edit") {
                            console.log(obj.data.id);
                            window.location.href = "${pageContext.servletContext.contextPath}/clazz/to/" + obj.data.id + "/edit.do";
                        }


                        if (obj.event === "del") {
                            var clazzName = obj.data.clazzName;
                            bootbox.confirm(
                                {
                                    size: "small",
                                    message: "确认删除 " + clazzName + " 吗?",
                                    buttons: {
                                        confirm: {
                                            label: '删除',
                                            className: 'btn-success'
                                        },
                                        cancel: {
                                            label: '取消',
                                            className: 'btn-danger'
                                        }
                                    },
                                    callback: function (result) {
                                        if (result === true) {
                                            $.ajax({
                                                url: "${pageContext.servletContext.contextPath}/clazz/del.do",
                                                type: "post",
                                                data: {
                                                    id: obj.data.id,
                                                    _method: "DELETE",
                                                },
                                                dataType: 'json',
                                                success: function (res) {
                                                    if (res.code === 400) {
                                                        bootbox.alert("该班级不能删除");
                                                    }
                                                    if (res.code === 200) {
                                                        $table.reload('clazz', {
                                                            page: true,
                                                        });
                                                    }

                                                }
                                            });
                                        }
                                    }
                                }
                            );
                        }
                    });
                });
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div>
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="welcome.do"><spring:message code="common.homepage"/></a>
                    </li>

                    <li class="active"><spring:message code="clazz.title.list"/></li>
                </ul>
            </div>
            <br>
            <a class="layui-btn layui-btn-sm3 layui-btn-radius layui-icon layui-icon-add-circle"
                href="${pageContext.servletContext.contextPath}/clazz/to/0/add.do">添加</a>
            <div class="layui-row demoTable">
                <div class="input-group">
                    <button class="layui-btn layui-hide layui-icon layui-icon-search" data-type="reload"> 搜索
                    </button>
                </div>

            </div>


            <table id="clazz" lay-filter="clazz"></table>

            <script type="text/html" id="barDemo">
                <a class="layui-btn layui-btn-xs layui-btn-radius layui-icon layui-icon-edit" lay-event="edit">
                    编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-radius layui-btn-xs layui-icon layui-icon-delete"
                   lay-event="del">删除</a>
            </script>


        </div>
    </jsp:body>
</lesson:page>

</body>
</html>
