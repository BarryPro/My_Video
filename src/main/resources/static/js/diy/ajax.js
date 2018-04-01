/**
 * Created by belong on 16-11-4.
 */
$(document).ready(function () {
    $("#slider3").responsiveSlides({
        auto: true,
        pager: false,
        nav: true,
        speed: 500,
        namespace: "callbacks",
        before: function () {
            $('.events').append("<li>before event fired.</li>");
        },
        after: function () {
            $('.events').append("<li>after event fired.</li>");
        }
    });
    //从cookie中得到用户参数
    cookies();
    playArea();
    //用户注册
    $("#register").click(function () {
        //注册检测&&提交
        if ($("#rusername").val() == '') {
            $("#label1").html("用户名不能为空哦!").show(300).delay(3000).hide(300);
            return;
        }
        if ($("#rpassword").val().length < 6) {
            $("#label1").html("密码至少6位哦!").show(300).delay(3000).hide(300);
            return;
        }
        if ($("#rpassword").val() != $("#repwd").val()) {
            $("#label1").html("两次输入得密码不一致哦!").show(300).delay(3000).hide(300);
            return;
        }
        if ($("#file0").val() == '') {
            $("#label1").html("你还没有选择文件哦!").show(300).delay(3000).hide(300);
            return;
        }
        $("#r_form").submit();
        $("#label1").show(300).delay(3000).hide(300);
    });

    //登陆
    $("#my_login").click(function () {
        $("#l_form").submit();
    });

    //取消提示消息
    $("#dispear").click(function () {
        $("#label1").attr("style", "display:none");
    });

    //上传电影
    $("#upload").click(function () {
        if ($("#vname").val() == '') {
            $("#label1").html("你还没有编辑上传视频的名称哦!").show(300).delay(3000).hide(300);
            return;
        }
        if ($("#vdirector").val() == '') {
            $("#label1").html("你还没有编辑上传视频的导演呢!").show(300).delay(3000).hide(300);
            return;
        }
        if ($("#vactor").val() == '') {
            $("#label1").html("你还没有编辑上传视频的演员表哦!").show(300).delay(3000).hide(300);
            return;
        }
        if ($("#file2").val() == '') {
            $("#label1").html("你还没有选择上传视频的海报哦！").show(300).delay(3000).hide(300);
            return;
        }
        if ($("#vinfo").val() == '') {
            $("#label1").html("你还没有介绍你要上传的视频呐哦！，这样游客会没有兴趣的喔！").show(300).delay(3000).hide(300);
            return;
        }
        if ($("#file1").val() == '') {
            $("#label1").html("你还没有选择上传视频哦！").show(300).delay(3000).hide(300);
            return;
        }
        $("#u_form").submit();
    });
    //得到数据库信息
    ajax_page(0, 1);

    //得到电影
    $("#movie").click(function () {
        var n = 2;
        $("#cur_type").attr("value",n);
        ajax_page(n, 1);
    });
    //得到新闻
    $("#news").click(function () {
        var n = 4;
        $("#cur_type").attr("value",n);
        ajax_page(n, 1);
    });
    //得到TV
    $("#tv").click(function () {
        var n = 1;
        $("#cur_type").attr("value",n);
        ajax_page(n, 1);
    });
    //得到 MV
    $("#MV").click(function () {
        var n = 3;
        $("#cur_type").attr("value",n);
        ajax_page(n, 1);
    });

    //得到 主页
    $("#home").click(function () {
        var n = 0;
        $("#cur_type").attr("value",n);
        ajax_page(n, 1);
    });

    //查找
    $("#btn_search").click(function () {
        var userid = $("#my_image").attr("title");
        var cur = $("#cur_page").attr("title");
        var txt = $("#txt").val();
        var type = $("#type_id").attr("value");
        txt_search(userid, cur, txt, type);
    });

    //改变查找
    $("#txt").keyup(function () {
        var type = $("#type_id").attr("value");
        var userid = $("#my_image").attr("title");
        var cur = $("#cur_page").attr("title");
        var txt = $("#txt").val();
        txt_search(userid, cur, txt, type);
    })

    //获取访客

    vister();

    //处理页面布局
    $("#my_select").change(function () {
        _path = $("#_path").attr("value");
        $.ajax({
            url: _path + '/my_user/num_setting',
            type: "post",
            data: 'value=' + $("#my_select").val() + '&userid=' + $("#my_image").attr("title"),
            dataType: "text",
            success: function (data) {
                $("#label1").html(data).show(300).delay(3000).hide(300);
                //异步刷新界面
                ajax_page(0, 1);
            }
        });
    })

    //处理分页 首页
    $("#header").on('click', '#first_page', function () {
        var type = $("#type_id").attr("value");
        var cur = $("#cur_page").attr("title");
        var userid = $("#my_image").attr("title");
        var txt = $("#txt").val();
        if (type == 6) {
            ajax_page_search(userid, cur, txt, type)
        } else {
            ajax_page(type, cur);
        }

    });

    //处理分页 上一页
    $("#header").on('click', 'a', function () {
        var type = $("#type_id").attr("value");
        var cur = $("#cur_page").attr("title");
        var userid = $("#my_image").attr("title");
        var txt = $("#txt").val();
        cur--;
        if (cur >= 1 && type != 6) {
            ajax_page(type, cur);
        } else if (cur >= 1 && type == 6) {
            ajax_page_search(userid, cur, txt, type);
        }
    });

    //处理分页 下一页
    $("#tail").on('click', 'a', function () {
        var type = $("#type_id").attr("value");
        var cur = $("#cur_page").attr("title");
        var max = $("#last_page").attr("title");
        var userid = $("#my_image").attr("title");
        var txt = $("#txt").val();
        cur++;
        if (cur <= max && type != 6) {
            ajax_page(type, cur);
        } else if (cur <= max && type == 6) {
            ajax_page_search(userid, cur, txt, type);
        }
    });

    //处理分页 尾页
    $("#tail").on('click', '#last_page', function () {
        var type = $("#type_id").attr("value");
        var cur = $("#last_page").attr("title");
        var userid = $("#my_image").attr("title");
        var txt = $("#txt").val();
        if (type == 6) {
            ajax_page_search(userid, cur, txt, type);
        } else {
            ajax_page(type, cur);
        }

    });

    //处理分页 中间页
    $("#middle").on('click', 'a', function () {
        var type = $("#type_id").attr("value");
        var cur = $(this).attr("title");//this就是表示此时点击的那个超链的title了
        var userid = $("#my_image").attr("title");
        var txt = $("#txt").val();
        if (type == 6) {
            ajax_page_search(userid, cur, txt, type);
        } else {
            ajax_page(type, cur);
        }
    });

    //处理设置
    $("#my_setting").click(function () {
        _path = $("#_path").attr("value");//得到项目的绝对路径
        location.href = _path + "/my_user/setting";
    });

    $(window).keyup(function(e){
        if(e.keyCode==27){//此处代表按的是键盘的Esc键
            $("#play-area").slideUp(300);
            $("#order-area").slideUp(300);
            $("#play-area").empty();
            fixCurPage();
        }
    });

    $("#play-close").bind("click",function () {
        $("#play-area").slideUp(300);
        $("#play-player").empty();
        fixCurPage();
    });

    // 处理显示区域的关闭close是否显示
    $("#play-area").mouseleave(function(){
        $("#play_close").hide();
    });

    $("#play-area").mouseenter(function(){
        $("#play_close").show();
    });

    $("#_setting").click(function () {
        $("#setting").show(300);
    });

    $("#setting_close").click(function () {
        $("#setting").hide(300);
    });

    $("#order_close").click(function () {
        $("#order-area").slideUp(300);
    });

    $("#order").click(function () {
        $("#order-area").show(500);
    });

    $("#preview").click(function () {
        _path = $("#_path").attr("value");//得到项目的绝对路径
        $("#common-area").html(
            '<div class="container-fluid">' +
            '    <div class="row-fluid"><div class="span12">' +
            '            <div class="page-header"><h1 class="white-color">MyPlay 收银台.</h1></div>' +
            '            <h3 class="white-color">订单支付详情.</h3>' +
            '            <table class="table table-hover table-bordered">' +
            '                <tbody>' +
            '                <tr class="info"><td>订单标号</td><td>20180401123456</td></tr>' +
            '                <tr class="warning"><td>商品名称</td><td>MyPlay_vip会员_一个月</td></tr>' +
            '                <tr class="info"><td>交易时间</td><td>2018-04-01 10.21:00</td></tr>' +
            '                <tr class="warning"><td >支付方式</td><td>微信支付</td></tr>' +
            '                <tr class="info"><td>支付金额</td><td>6元</td></tr></tbody></table>' +
            '            <div class="tabbable" id="tabs-797679">' +
            '                <ul class="nav nav-tabs">' +
            '                    <li><a href="#panel-544732" data-toggle="tab">微信</a></li>' +
            '                    <li class="active"><a href="#panel-879116" data-toggle="tab">支付宝</a></li></ul>' +
            '                <div class="tab-content">' +
            '                    <div class="tab-pane msg" id="panel-544732">' +
            '                        <img alt="支付二维码" src='+_path+'/static/images/v6.jpg class="sys-ewm"/>' +
            '                        <small class="msg-info margin">提示：先扫【微信】二维码支付，再点击【支付提交】</small></div>' +
            '                    <div class="tab-pane active msg" id="panel-879116">' +
            '                        <img alt="支付二维码" src='+_path+'/static/images/z6.jpg class="sys-ewm"/>' +
            '                        <small class="msg-info margin">提示：先扫【支付宝】二维码支付，再点击【支付提交】</small></div></div>' +
            '            </div><hr/><button class="btn btn-block btn-primary" type="button" id="pay-submit"><b>支付提交</b></button><hr/></div></div>' +
            '</div>'
        );
    });

    $("#common-area").on('click', 'button', function () {
        $("#label1").html("支付成功！").show(300).delay(3000).hide(300);
        $("#order-area").hide(300);
    });

    $("#movies-list").on('click','button',function () {
        var cur = $(this).attr("value");
        alert(cur);
    });

    $("#vip-center").click(function () {
        $("#order-area").show();
        $("#common-area").html('<div class="container-fluid">' +
            '                    <div class="row-fluid">' +
            '                        <div class="span12">' +
            '                            <div class="hero-unit"><h1>Hello, 会员!</h1><hr/>' +
            '                                <p>你现在享有MyPlay网站上的所有视频资源，祝你观看愉快！</p><hr/></div></div></div></div>');
    });

});

//获取电影界面
function movie_page(i, list) {
    _path = $("#_path").attr("value");
    pro_views = list.views;
    if (pro_views > 9999) {
        pro_views = "9999+";
    }
    $("#1").append(
        '<div class="col-md-2 resent-grid recommended-grid slider-top-grids">' +
        '<div class="resent-grid-img recommended-grid-img">' +
        '<div class="video-vip"><img src=' + _path + '/static/images/video-vip'+list.v_type+'.png width="48px" height="48px"  alt="tupian" /></div>' +
        '<a id="play_video" title='+_path+'/my_video/src/Vid/'+list.vid+' onclick="clickPlay(this)">' +
        '<img id="views" class="display-img" src=' + _path + '/my_video/pic/Vid/' + list.vid + ' alt="tupian" /></a>' +
        '<div class="time"><p>' + list.vdate + '</p></div>' +
        '</div>' +
        '<div class="resent-grid-info recommended-grid-info">' +
        '<h3><a href=' + _path + '/my_review/review/Vid/' + list.vid + ' class="title title-info"><b>' + list.vname +
        '</b><br/>简介：' + list.vinfo + '</a></h3>' +
        '<ul>' +
        '<li><p class="views views-info"><i class="fa fa-upload" aria-hidden="true" title="上传者" id="author">' +
        '&nbsp;&nbsp;' + list.user.username + '</i></p></li>' +
        '<li class="right-list">' +
        '<p class="views views-info">' +
        '<a href="' + _path + '/' + list.vsrc + '" download="' + _path + '/' + list.vsrc + '">' +
        '<i class="fa fa-eye" aria-hidden="true" title="观看次数"></i></a>&nbsp;&nbsp;' + pro_views + '次</p></li>' +
        '</ul>' +
        '</div>' +
        '<div class="clearfix"></div>'
    );
}

//显示电影加分页图标
function ajax_page(type, cur) {
    _path = $("#_path").attr("value");//得到项目的绝对路径
    $.ajax({
        url: _path + "/my_video/db_info",
        type: "post",
        data: 'n=' + type + '&userid=' + $("#my_image").attr("title") + '&cur_page=' + cur,
        dataType: "json",
        success: function (data) {
            $("#1").empty();
            $(data.data).each(function (i, list) {
                movie_page(i, list);
            });
            //分页超链
            page_href(type, data);
        }
    });
}

//显示分页超链图标
function page_href(n, data) {
    $("#header").empty();
    $("#header").append(
        '<a href="javascript:void(0)" class="previous" title="上一页" id="del_page"><span class="Bg"><b>&nbsp;</b></span></a>' +
        '<a href="javascript:void(0)" class="cur" id="first_page" title="1"><span class="Bg"><b >首页</b></span></a>' +
        '<input type="hidden" id="type_id" value="' + n + '" />' +
        //cur_page 用于全局共享当前页（但是当前页是通过middle里面的类标签点击算出来的cur_page只是用来存储）
        '<label style="display:none" id="cur_page" title="' + data.cur_page + '"></label>'
    )

    $("#middle").empty();
    for (var j = 2; j <= data.page_total - 1; j++) {
        $("#middle").append(
            '<a href="javascript:void(0)" id="mid_page' + j + '" title="' + j + '"><span class="Bg"><b>' + j + '</b></span></a>'
        );
    }

    $("#tail").empty();
    $("#tail").append(
        '<a href="javascript:void(0)" id="last_page" title="' + data.page_total + '"><span class="Bg"><b>尾页</b></span></a>' +
        '<a href="javascript:void(0)" class="nextpage" title="下一页" id="add_page"><span class="Bg"><b>&nbsp;</b></span></a>'
        + '<label style="color: #03BAAD">&nbsp;&nbsp;&nbsp;总计：' + data.row_total + '部视频</label>'
    );

}

//访问者
function vister() {
    _path = $("#_path").attr("value");//得到项目的绝对路径
    $.ajax({
        url: _path + '/my_user/visitor',
        type: "post",
        success: function (data) {
            $("#counter").empty();
            $("#counter").append(
                '&nbsp;&nbsp;第' + data + '位访客'
            );
        }
    });
}

//搜索引擎
function txt_search(userid, cur, txt) {
    _path = $("#_path").attr("value");//得到项目的绝对路径
    if ($("#txt").val() != '') {
        var url = _path + '/my_video/search';
        var data = 'userid=' + userid + '&cur_page=' + cur + '&txt=' + txt;
        $.ajax({
            url: url,
            type: "post",
            data: data,
            dataType: "json",
            success: function (data) {
                for (var j = 1; j <= 5; j++) {
                    $("#" + j).empty();
                }
                if (data.data != '') {
                    $(data.data).each(function (i, list) {
                        movie_page(i, list);
                    });
                    page_href(6, data);
                } else {
                    $("#1").append(
                        '<div align="center">' +
                        '<label style="color: red"><h3>对不起没有找到亲要找到的电影，' +
                        '我们会尽快为你添加库源的，请先回到主页看看吧！</h3></label></div>'
                    )
                }
            }
        });
    }
}

//cookies
function cookies() {
    var username = $.cookie('com.belong.username');
    var password = $.cookie('com.belong.password');
    $("#username").val(username);
    $("#password").val(password);
    $("input[name='cookie']").attr("checked", "checked");
}

function ajax_page_search(userid, cur, txt, type) {
    _path = $("#_path").attr("value");//得到项目的绝对路径
    var url = _path + '/my_video/search';
    var data = 'userid=' + userid + '&cur_page=' + cur + '&txt=' + txt;
    $.ajax({
        url: url,
        type: "post",
        data: data,
        dataType: "json",
        success: function (data) {
            $("#1").empty();
            $(data.data).each(function (i, list) {
                movie_page(i, list);
            });
            //分页超链
            page_href(type, data);
        }
    });
}

function playArea() {
    $("#play-area").hide();
    $("#setting").hide();
    $("#order-area").hide();
    _path = $("#_path").attr("value");//得到项目的绝对路径
    var url = _path + '/my_video/top20';
    $.ajax({
        url: url,
        type: "post",
        dataType: "json",
        success: function (data) {
            $("#top20-list").empty();
            $(data).each(function (i,list) {
                $("#top20-list").append(
                    '<li><a href=' + _path + '/my_review/review/Vid/'+list.vid+' class="menu1" id="sport">'+
                    '<span class="fa">'+(i+1)+'&nbsp;&nbsp;'+list.vname+'</span>'+
                    '</a></li>'
                );
            })
        }
    });
}

// 处理video点击播放
function clickPlay(a){
    _path = $("#_path").attr("value");//得到项目的绝对路径
    url = $(a).attr("title");//this就是表示此时点击的那个超链的title了
    $.ajax({
        url: url,
        type: "post",
        dataType: "json",
        scriptCharset: 'utf-8',
        success: function (data) {
            $("#play-area").slideDown(600);
            $("#play-player").empty();
            $("#play-player").append(
                '<video id="video_play" src="'+_path+'/static/resources/movies/'+data.srcpath+'" controls="controls"' +
                'autoplay="autoplay" width="1024" height="576" poster="'+_path+'/static/images/loading.gif">' +
                '</video>'
            );
            $("#label1").html(data.msg).show(300).delay(3000).hide(300);
        }
    });
}

function fixCurPage() {
    var n = $("#cur_type").attr("value");
    var cur = $("#cur_page").attr("title");
    ajax_page(n, cur);
}