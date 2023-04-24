<%--
  Created by IntelliJ IDEA.
  User: 龙小洋
  Date: 2023/3/27
  Time: 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="nav-header">
            <a class="navbar-brand" href="#">轮播图管理</a>
        </div>
    </div>
</nav>
<ul class="nav nav-tabs">
    <li class="active">
        <a href="#">轮播图信息</a>
    </li>
</ul>
<script>
    $(function(){
        $("#bannerTable").jqGrid(
            {
                url :"${pageContext.request.contextPath}/banner/showAllBanners",
                datatype : "json",
                colNames : [ 'ID', '标题', '图片', '超链接', '创建时间','描述', '状态' ],
                colModel : [
                    {name : 'id',hidden:true},
                    {name : 'title',align:"center",editable:true,editrules:{required:true}},
                    {name : 'url',align:"center",formatter:function (data) {
                            return "<img style='width: 120px' src='"+data+"'/>"
                        },editable:true,edittype:"file",editoptions: {enctype:"multipart/form/-data"}}, //上传 文件 jqgrid
                    {name : 'href',align:"center",editable:true,editrules:{required:true}},
                    {name : 'create_date',align:"center"},
                    {name : 'desc',align:"center",editable:true,editrules:{required:true}},
                    {name : 'status',align:"center",formatter:function(data){
                        if(data=="1"){
                            return "展示";
                        }else  return  "冻结";
                     },editable:true,edittype:"select",editoptions:{value:"1:展示;2:冻结"}}
                ],
                rowNum : 10,
                rowList : [ 1, 2, 3 ],
                pager : '#bannerPage',
                mtype : "post",
                viewrecords : true,
                styleUI:"Bootstrap",
                autowidth:true,
                multiselect:true,
                editurl:"${pageContext.request.contextPath}/banner/edit",
            });
        $("#bannerTable").jqGrid('navGrid', '#bannerPage',
            {edit : true,add : true,del : true,search:true,refresh:true,
            edittext:"编辑",addtext:"添加",deltext:"删除"
            },

            //edit add del
               {
                   closeAfterEdit:true,
                //frm   表单对象
                beforeShowForm:function (frm) {
                    frm.find("#furl").attr("disables",true);
                }
            },
            {
            closeAfterAdd:true,
            afterSubmit:function (response) {
                var bannerID = response.responseJSON.bannerId;
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/banner/upload",
                    datatype: "json",
                    type:"post",
                    date:{bannerId:bannerID},
                    //指定的上传input框的 id
                    fileElementId:"url",
                    success:function (data) {
                        //输出上传成功
                        //jqgrid重新载入
                        $("#bannerTable").trigger("reloadGrid");
                    }
                })

              }
            },
        {

        },
            {sopt:['eq','ne','cn']}, //配置搜索条件如何;
        );
    });
</script>

<div>
    <table id="bannerTable"></table>
    <div id="bannerPage" style="height: 50px"></div>
</div>