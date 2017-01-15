<html lang="en">
<#include "../common/header.ftl">
<#include "../common/top.ftl">
<style>
    ._mycolor{
        color: #adadad;
    }
    ._my_img{
        width: 17px;
        height: 17px;
    }
</style>
<#include "../video/particle.ftl">
<body>
<input type="hidden" id="_path" value="${my_path}/"/>
<input type="hidden" id="_user" value=""/>
<c:set var="review" value=""></c:set>
<div align="center" style="padding-top: inherit">
    <table width="790px" cellpadding="0" cellspacing="1" border="1">
        <tr>
            <td rowspan="6" width="28%">
                <a href="${my_path}/my_video/src/Vid/${review.vid}">
                <img width="240" height="338" id="_views"
                     src="${my_path}/my_video/pic/Vid/${review.vid}" alt="tupian" /></a>
            </td>
            <td width="72%"><span class="_mycolor">影片名：</span>${review.video.vname}</td>
        </tr>
        <tr>
            <td><span class="_mycolor">导演名：</span>${review.vdirector}</td>
        </tr>
        <tr>
            <td><span class="_mycolor">演员表：</span>${review.vactor}</td>
        </tr>
        <tr>
            <td><span class="_mycolor">上传时间：</span>${review.video.vdate}</td>
        </tr>
        <tr>
            <td><span class="_mycolor">影片介绍：</td>
        </tr>
        <tr>
            <td>
                <DIV style="overflow-y:scroll; WIDTH:560px;HEIGHT:200px;background-color:white color:black">
                ${review.video.vinfo}</DIV>
            </td>
        </tr>
    </table>
</div>
<div align="center">
    <span id="article_head"></span>
    <span id="article_info"></span>
    <div align="center">
        <a id="del">上一页</a>
        <a id="first">首页</a>
        <span id="herf_num"></span>
        <a id="add">下一页</a>
    </div>
</div>
<div align="center">
    <textarea id="content" name="content" rows="5"
              cols="50"></textarea>
    <script type="text/javascript">
        //配置ckeditor
        CKEDITOR.replace('content',{
            filebrowserBrowseUrl : '${my_path}/static/ckfinder/ckfinder.html',
            filebrowserImageBrowseUrl : '${my_path}/static/ckfinder/ckfinder.html?type=Images',
            filebrowserFlashBrowseUrl : '${my_path}/static/ckfinder/ckfinder.html?type=Flash',
            filebrowserUploadUrl : '${my_path}/static/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
            filebrowserImageUploadUrl : '${my_path}/static/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
            filebrowserFlashUploadUrl : '${my_path}/static/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
        });
    </script>
</div>
<link href="${my_path}/static/css/diy.css" rel='stylesheet' type='text/css' media="all"/>
<div align="center">
    <div id="_submit"></div>
    </table>
    <div style="display: none"><#include "../common/user.ftl"></div>
    <script>
        $(function(){
            //页面标志
            $("#_action").attr("value","1");
            //显示评论信息
            release();
            show_article(1);
            add_Article();
            //用于控制一个评论只可以被一个用户点击1次
            var flag = '';
            //处理回复
            $("#article_info").on('click','img',function(){
                _uid = $("#my_image").attr("title");
                if(_uid != -1){
                    username = $(this).attr("value");
                    if(username!=undefined){
                        old_context = $(this).attr("alt")
                        $("#_submit").empty();
                        $("#_submit").append(
                                '<table width="800px" id="tabelarticle" class="table-striped table-bordered">'+
                                '<tr><td width="90%"><span style="color: #00CCFF">你对这部电影有什么看法，快和大家一起分享一下吧?</span></td>' +
                                '<td width="10%"><input style="background: chocolate" type="button" id="reply" value="   回   帖   "/></td></tr>' +
                                '</table>'
                        );
                        reply(username,old_context);
                    } else {
                        value = $(this).attr("title");
                        _aid = $(this).attr("alt");
                        if(flag != _aid){
                            flag = _aid;
                            if(value=="赞"){//点赞
                                $.ajax({
                                    url:_path+'/my_review/agree',
                                    type:'post',
                                    data:"aid="+_aid,
                                    dataType:'json',
                                    success:view
                                });
                            } else {//点踩
                                $.ajax({
                                    url:_path+'/my_review/disagree',
                                    type:'post',
                                    data:"aid="+_aid,
                                    dataType:'json',
                                    success:view
                                });
                            }
                        }
                        if(value=="删帖"){
                            $.ajax({
                                url: _path + '/my_review/delete',
                                type: 'post',
                                data: 'aid=' + _aid
                            });
                            show_article(1);
                        }
                    }
                } else {
                    $("#_login").trigger("click");
                }

            })
            //显示评论信息表
            function showArticle(data){
                $("#article_head").empty();
                $("#article_head").append(
                        '<table width="800px" class="table-striped table-bordered">'+
                        '<tr>' +
                            '<th width="10%""><span>头像</span></th>' +
                            '<th width="80%">评论内容</th>' +
                        '</tr></table>'
                );
                $("#article_info").empty();
                $(data.articles).each(function(i,article){
                    _path = $("#_path").attr("value");
                    if($("#my_image").attr("title") == article.uid){
                        article_info_delete(data,article);
                    } else {
                        article_info(data,article);
                    }

                });
                $("#herf_num").empty();
                for(var j = 2;j<=data.page_total;j++){
                    if(j==data.page_total){
                        $("#herf_num").append(
                                '<a title="'+j+'">尾页</a>'
                        );
                    } else {
                        $("#herf_num").append(
                                '<a title="'+j+'"> '+j+' </a>'
                        );
                    }
                }

            }

            //返回点赞信息
            function view(){
                show_article(1);
            }

            function show_article(n){
                _path = $("#_path").attr("value");
                $.ajax({
                    url:_path+'/my_review/query',
                    type:'post',
                    data:'content='+CKEDITOR.instances.content.getData()+
                    '&userid='+$("#my_image").attr("title")+'&rootid='+0+'&Vid='+
                    ${review.vid}+'&cur_page='+n,
                    dataType:'json',
                    success:showArticle
                });
            }

            function add_Article(){
                $("#_article").click(function() {
                    //只有登陆的用户才有发评论的资格
                    context = CKEDITOR.instances.content.getData();
                    if($("#my_image").attr("title") != -1){
                        if(context != ''){
                            _path = $("#_path").attr("value");
                            $.ajax({
                                url:_path+'/my_review/add_article',
                                type:'post',
                                data:'acontent='+context+
                                '&uid='+$("#my_image").attr("title")+'&arootid='+0+'&Vid='+
                                ${review.vid}+'&cur_page='+$("#max_page_hidden").attr("title"),
                                dataType:'json',
                                success:showArticle
                            });
                        }
                    } else {
                        $("#_login").trigger("click");
                    }
                    //清空在线编辑器的内容
                    CKEDITOR.instances.content.setData("");
                    show_article(1);
                })
            }

            function article_info(data,article){
                $("#article_info").append(
                        '<table id="_table" width="800px" class="table-striped table-bordered">'+
                        '<tr>' +
                        '<td width="10%">' +
                        '<div style="width:36px; height:36px; border-radius:50%;overflow: hidden" class="_articleImg">' +
                        '<img width="36px" height="36px" src='+_path+'/my_user/pic/userid/'+article.uid+' class="user_avatar' +
                        'style="width:100% "/></div><span class="fontuser">'+article.user.username+'</span></td>' +
                        '<td width="90%"><span>'+article.acontent+'</span>'+
                        '<div class="_articlelayout"><span class="_articlefont">'+article.adate+'</span>' +
                        '<img src='+_path+'/static/images/agree.png class="_my_img" title="赞" alt='+article.aid+' ><span>'+article.agree+'</span>' +
                        '<img src='+_path+'/static/images/disagree.png class="_my_img" title="踩" alt='+article.aid+'><span>'+article.disagree+'</span>' +
                        '<img src='+_path+'/static/images/reply.png class="_my_img" alt='+article.aid+' value='+article.user.username+' title="回贴"></div></td>' +
                        '</table>'+
                        '<label style="display:none" id="cur_page_hidden" title='+data.cur_page+'/>'+
                        '<label style="display:none" id="max_page_hidden" title='+data.page_total+'/>'
                );
            }

            //显示发帖按钮
            function release(){
                $("#_submit").empty();
                $("#_submit").append(
                        '<table width="800px" id="tabelarticle" class="table-striped table-bordered">'+
                        '<tr><td width="90%"><span style="color: #00CCFF">你对这部电影有什么看法，快和大家一起分享一下吧?(如果发帖没发出去，请刷新后重新发送)</span></td>' +
                        '<td width="10%"><input style="background: chocolate" type="button" id="_article" value="   发   帖   "/></td></tr>' +
                        '</table>'
                );
            }

            function article_info_delete(data,article){
                $("#article_info").append(
                        '<table id="_table" width="800px" class="table-striped table-bordered">'+
                        '<tr>' +
                        '<td width="10%">' +
                        '<div style="width:36px; height:36px; border-radius:50%;overflow: hidden" class="_articleImg">' +
                        '<img width="36px" height="36px" src='+_path+'/my_user/pic/userid/'+article.uid+' class="user_avatar" ' +
                        'style="width:100% "/></div><span class="fontuser">'+article.user.username+'</span></td>' +
                        '<td width="90%">'+article.acontent+
                        '<div class="_articlelayout"><span class="_articlefont">'+article.adate+'</span>' +
                        '<img src='+_path+'/static/images/agree.png class="_my_img" title="赞" alt='+article.aid+' ><span>'+article.agree+'</span>' +
                        '<img src='+_path+'/static/images/disagree.png class="_my_img" title="踩" alt='+article.aid+'><span>'+article.disagree+'</span>' +
                        '<img src='+_path+'/static/images/reply.png class="_my_img" alt='+article.aid+' value='+article.user.username+' title="回贴">' +
                        '<img src='+_path+'/static/images/delete.png class="_my_img" alt='+article.aid+' title="删帖"></div></td>' +
                        '</table>'+
                        '<label style="display:none" id="cur_page_hidden" title='+data.cur_page+'/>'+
                        '<label style="display:none" id="max_page_hidden" title='+data.page_total+'/>'
                );
            }

            //回复帖子
            function reply(username, old_context) {
                if ($("#my_image").attr("title") != -1) {
                    CKEDITOR.instances.content.setData($("#_user").attr("value") + ': @' + username);
                    $("#reply").click(function() {
                        context = "<hr/>"+CKEDITOR.instances.content.getData();
                        _path = $("#_path").attr("value");
                        $.ajax({
                            url: _path + '/my_review/reply',
                            type: 'post',
                            data: 'acontent=' + context + '&aid=' + old_context
                        });
                        CKEDITOR.instances.content.setData("");
                        show_article(1);
                        release();
                    })
                }
                else {
                    $("#_login").trigger("click");
                }
            }
            //处理分页
            $("#first").click(function(){
                show_article(1);
            })
            $("#herf_num").on('click','a',function(){                ;
                show_article($(this).attr("title"));
            })
            $("#del").click(function(){
                var cur = $("#cur_page_hidden").attr("title");
                if(cur != 1){
                    show_article(cur-1);
                }
            })
            $("#add").click(function(){
                var cur = $("#cur_page_hidden").attr("title");
                cur++;
                var max = $("#max_page_hidden").attr("title");
                if( cur <= max){
                    show_article(cur);
                }
            })
        })

    </script>
</div>
</body>
</html>
