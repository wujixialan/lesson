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
                $('#birthVal').daterangepicker({
                    'applyClass': 'btn-sm btn-success',
                    'cancelClass': 'btn-sm btn-default',

                    startDate: '1900-01-01',
                    endDate: '2050-12-31',
                    locale: {
                        applyLabel: '确定', //确定按钮文本
                        cancelLabel: '取消', //取消按钮文本
                        format: 'YYYY-MM-DD',
                        fromLabel: '起始时间',
                        toLabel: '结束时间',
                        separator: "~",
                    },
                    showDropdowns: true,
                    endDate: moment(new Date()), //设置结束器日期
                    maxDate: moment(new Date()), //设置最大日期
                }).prev().on(ace.click_event, function () {
                    $(this).next().focus();
                });

                layui.use(["table", "util", "layer", "laytpl"], function () {

                    var $table = layui.table;
                    var $utils = layui.util;
                    var $layer = layui.layer;
                    var $laytpl = layui.laytpl;
                    var $ = layui.$, active = {
                        reload: function () {
                            var demoReload = $('#demoReload');
                            $table.reload('stu', {
                                page: true,
                                where: {
                                    stuNum: $("#stuNum").val(),
                                    stuName: $("#stuName").val(),
                                    birth: $("#birthVal").val(),
                                }
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
                        elem: '#stu',
                        height: 471,
                        //数据接口,
                        url: '${pageContext.servletContext.contextPath}/stu/list.do',
                        method: "post",
                        //开启分页
                        page: true,
                        //表头
                        cols: [[
                            {field: 'stuNum', title: '学号', align: 'center', width: 150,},
                            {field: 'id', title: 'ID', align: 'center', width: 150,},
                            {field: 'stuName', title: '姓名', align: 'center', width: 150,},
                            {field: 'birth', title: '出生年月', align: 'center', templet: "#birth", width: 150,},
                            {field: 'score', title: '平均分', align: 'center', width: 100,},
                            {field: 'lession', title: '选修科目数', align: 'center', width: 100,},
                            {field: 'clazzName', title: '班级', align: 'center', templet: "#clazzName", width: 150,},
                            {field: 'right', title: '操作', toolbar: "#barDemo", align: 'center', width: 310,}
                        ]],
                        where: {
                            stuNum: $("#stuNum").val(),
                            stuName: $("#stuName").val(),
                            birth: $("#birthVal").val(),
                        },
                        done: function (res) {
                            /**
                             * 隐藏 id 列。
                             */
                            $("[data-field='id']").css('display', 'none');
                        }
                    });

                    $table.on("tool(stu)", function (obj) {
                        if (obj.event === "edit") {
                            console.log(obj.data.id);
                            window.location.href = "${pageContext.servletContext.contextPath}/stu/to/" + obj.data.id + "/edit.do";
                        }

                        if (obj.event === "enter") {
                            //    录入学生成绩
                            window.location.href = "${pageContext.servletContext.contextPath}/stu/to/"+ obj.data.id +"/chooseList.do";
                        }

                        if (obj.event === "chooseLession") {
                            //    选课。
                            window.location.href = "${pageContext.servletContext.contextPath}/stu/" + obj.data.id + "/findAll.do"
                        }


                        if (obj.event === "del") {
                            var stuName = obj.data.stuName;
                            bootbox.confirm(
                                {
                                    size: "small",
                                    message: "确认删除 " + stuName + " 吗?",
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
                                                url: "${pageContext.servletContext.contextPath}/stu/del.do",
                                                type: "post",
                                                data: {
                                                    id: obj.data.id,
                                                    _method: "DELETE",
                                                },
                                                dataType: 'json',
                                                success: function (res) {
                                                    if (res.code === 200) {
                                                        $table.reload('stu', {
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
            <a class="layui-btn  layui-btn-radius layui-icon layui-icon-add-circle"
                href="${pageContext.servletContext.contextPath}/stu/to/0/add.do">添加</a>
            <div class="layui-row demoTable">
                <div class="layui-inline  layui-col-sm3">
                    <label class="layui-form-label layui-col-sm1">学号</label>
                    <div class="layui-inline">
                        <input class="layui-input" type="text" name="stuNum" id="stuNum">
                    </div>
                </div>
                <div class="layui-inline  layui-col-sm3">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-inline">
                        <input class="layui-input layui-form-pane" type="text" name="stuName" id="stuName">
                    </div>
                </div>
                <div class="input-group col-sm-4">
                    <label class="input-group-addon">出生年月</label>
                    <input class="form-control date-range-picker" id="birthVal" type="text"
                           data-date-format="yyyy-mm-dd" name="birth" readonly>
                    <span class="input-group-addon">
                        <i class="fa fa-calendar"></i>
                    </span>
                </div>
                <div class="input-group">
                    <button class="layui-btn layui-hide layui-icon layui-icon-search" data-type="reload"> 搜索
                    </button>
                </div>

            </div>


            <table id="stu" lay-filter="stu"></table>

            <script type="text/html" id="barDemo">
                <a class="layui-btn layui-btn-xs layui-btn-radius layui-icon layui-icon-edit" lay-event="enter">
                    录入</a>
                <a class="layui-btn layui-btn-xs layui-btn-radius layui-icon layui-icon-edit" lay-event="chooseLession">
                    选课</a>
                <a class="layui-btn layui-btn-xs layui-btn-radius layui-icon layui-icon-edit" lay-event="edit">
                    编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-radius layui-btn-xs layui-icon layui-icon-delete"
                   lay-event="del">删除</a>
            </script>

            <script th:inline="javascript" type="text/html" id="clazzName">
                {{#
                console.log(d);
                return d.clazz.clazzName;
                }}
            </script>

            <script inline="javascript" type="text/html" id="birth">
                {{#
                var birth = d.birth;
                var result = birth.year + "-";
                if(birth.monthValue < 10) {
                result += "0" +birth.monthValue + "-";
                } else {
                result += birth.monthValue + "-";
                }

                if(birth.dayOfMonth < 10) {
                result += "0" +birth.dayOfMonth;
                } else {
                result += birth.dayOfMonth;
                }
                return result;
                }}
            </script>

        </div>
    </jsp:body>
</lesson:page>

</body>
</html>
