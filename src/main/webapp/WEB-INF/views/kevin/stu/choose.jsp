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
                    //第一个实例
                    $table.render({
                        elem: '#chooseLession',
                        height: 471,
                        //数据接口,
                        url: '${pageContext.servletContext.contextPath}/lession/list.do',
                        method: "post",
                        //开启分页
                        page: true,
                        //表头
                        cols: [[
                            {type:'checkbox'},
                            {field: 'id', title: 'ID', align: 'center', width: 100,},
                            {field: 'subName', title: '科目名称', align: 'center',},
                            {field: 'perNum', title: '选修人数', align: 'center', },
                        ]],
                        done: function (res) {
                            /**
                             * 隐藏 id 列。
                             */
                            // $("[data-field='id']").css('display', 'none');
                            console.log(res);
                        }
                    });

                    $("#choose").click(function () {
                        var checkData = $table.checkStatus("chooseLession").data,
                            data = checkData;
                        if (checkData.length <= 0 ) {
                            bootbox.alert("至少选择一门课。");
                        }
                        if (checkData.length > 0) {
                            bootbox.confirm(
                                {
                                    size: "small",
                                    message: "确定选择该课程，一旦确认无法退选？",
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
                                    callback: function (result) {
                                        if (result === true) {
                                            var ids = "";
                                            $.each(data, function (index, value) {
                                                ids += value.id + ",";
                                            });
                                            ids = ids.substr(0, ids.length-1);
                                            $.ajax({
                                                url: "${pageContext.servletContext.contextPath}/stu/choose.do",
                                                type: "post",
                                                data: {
                                                    stuId: $("input[name=stuId]").val(),
                                                    lessionIds: ids,
                                                },
                                                dataType: "json",
                                                success: function (res) {
                                                    console.log(res);
                                                    if (res.code === 200) {
                                                        window.location.href = "${pageContext.servletContext.contextPath}/stu/to/"+ res.msg +"/chooseList.do";
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

                    <li class="active"><spring:message code="lession.title.list"/></li>
                </ul>
            </div>

            <div class="layui-row">
                <br>
                    <label class="layui-card layui-col-lg-offset5"><h3>欢迎 ${stu.stuName} 选课</h3></label>
                <input type="hidden" name="stuId" value="${stu.id}">
            </div>

            <table id="chooseLession" lay-filter="chooseLession"></table>
            <a class="layui-btn layui-btn-sm layui-btn-radius layui-col-lg-offset11 layui-icon"
               lay-event="chooseLession" id="choose">完成选课</a>


        </div>
    </jsp:body>
</lesson:page>

</body>
</html>
