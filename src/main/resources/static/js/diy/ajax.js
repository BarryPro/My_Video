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
        $("#label1").hide()
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
        $("#cur_type").attr("value", n);
        ajax_page(n, 1);
    });
    //得到新闻
    $("#vip").click(function () {
        var n = 4;
        $("#cur_type").attr("value", n);
        ajax_page(n, 1);
    });
    //得到TV
    $("#tv").click(function () {
        var n = 1;
        $("#cur_type").attr("value", n);
        ajax_page(n, 1);
    });
    //得到 MV
    $("#MV").click(function () {
        var n = 3;
        $("#cur_type").attr("value", n);
        ajax_page(n, 1);
    });

    //得到 ZY
    $("#ZY").click(function () {
        var n = 5;
        $("#cur_type").attr("value", n);
        ajax_page(n, 1);
    });

    //得到 DM
    $("#DM").click(function () {
        var n = 6;
        $("#cur_type").attr("value", n);
        ajax_page(n, 1);
    });
    //得到 主页
    $("#home").click(function () {
        var n = 0;
        $("#cur_type").attr("value", n);
        ajax_page(n, 1);
    });

    //查找
    $("#btn_search").click(function () {
        var userid = $("#cur_user_uid").attr("value");
        var cur = $("#cur_page").attr("title");
        var txt = $("#txt").val();
        var type = $("#type_id").attr("value");
        txt_search(userid, cur, txt, type);
    });

    //改变查找
    $("#txt").keyup(function () {
        var type = $("#type_id").attr("value");
        var userid = $("#cur_user_uid").attr("value");
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
            data: 'value=' + $("#my_select").val() + '&userid=' + $("#cur_user_uid").attr("value"),
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
        var userid = $("#cur_user_uid").attr("value");
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
        var userid = $("#cur_user_uid").attr("value");
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
        var userid = $("#cur_user_uid").attr("value");
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
        var userid = $("#cur_user_uid").attr("value");
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
        var userid = $("#cur_user_uid").attr("value");
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

    $(window).keyup(function (e) {
        if (e.keyCode == 27) {//此处代表按的是键盘的Esc键
            $("#play-area").slideUp(300);
            $("#order-area").slideUp(300);
            $("#play-area").empty();
            $('#webchat-area').hide();
            fixCurPage();
        }
    });

    $("#play-close").bind("click", function () {
        $("#play-area").slideUp(300);
        $("#play-player").empty();
        fixCurPage();
    });

    // 处理显示区域的关闭close是否显示
    $("#play-area").mouseleave(function () {
        $("#play_close").hide();
    });

    $("#play-area").mouseenter(function () {
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

    $("#vip-area").on('click', '#order', function () {
        if ($("#cur_user_uid").attr("value") == -1) {
            $("#_login").trigger("click");
        } else {
            var uid = $("#cur_user_uid").attr("value");
            $("#order-area").show(500);
            $("#common-area").html('<div class="container-fluid">' +
                '    <div class="row-fluid"> '+
                '        <div class="span12">' +
                '            <div class="page-header">' +
                '                <h2 class="white-color">充值VIP.</h2></div>' +
                '            <table class="table table-hover table-bordered white-color">' +
                '                <thead><tr><th>权限</th><th>普通（免费）</th><th>vip（6元/月）</th><th>svip（15元/月）</th></tr></thead>' +
                '                <tbody>' +
                '                <tr><td>普通视频</td>' +
                '                    <td><img src=' + _path + '/static/images/right.png class="right-size"/></td>' +
                '                    <td><img src=' + _path + '/static/images/right.png class="right-size"/></td>' +
                '                    <td><img src=' + _path + '/static/images/right.png class="right-size"/></td></tr>' +
                '                <tr ><td>vip视频</td><td></td>' +
                '                    <td><img src=' + _path + '/static/images/right.png class="right-size"/></td>' +
                '                    <td><img src=' + _path + '/static/images/right.png class="right-size"/></td></tr>' +
                '                <tr><td>付费/用券视频</td><td></td><td></td>' +
                '                    <td><img src=' + _path + '/static/images/right.png class="right-size"/></td></tr>' +
                '                </tbody>' +
                '            </table>' +
                '            <hr/><div ><input type="hidden" name="user_id" value=' + uid + ' >' +
                '               <label class="msg">充值类型：</label>' +
                '                <select class="margin" name="vip_type"><option value="1">vip</option><option value="2">svip</option></select>' +
                '                <label class="msg">充值时长：</label>' +
                '                <select class="margin" name="vip_time"><option  value="1">1个月</option><option value="6">6个月</option>' +
                '                           <option value="12">12个月</option></select></div><hr/>' +
                '            <button class="btn btn-block btn-primary my_btn" >立即充值</button><hr/>' +
                '</div></div></div>'
            );
        }
    });

    // 用于判断是订单预览还是订单支付
    $("#common-area").on('click', 'button', function () {
        var order_pay_flag = $("h2").text();
        var vip_type = 0;
        var vip_time = 0;
        if (order_pay_flag == "充值VIP.") {
            $("select option:selected").each(function (i,data) {
                var value = data.value;
                if (i == 2) {
                    vip_type = value;
                } else {
                    vip_time = value;
                }
            });
            // 发送异步请求
            $.ajax({
                url: _path + '/my_order/preview',
                type: "post",
                data: 'vip_type=' + vip_type +
                '&user_id=' + $("#cur_user_uid").attr("value")+
                '&vip_time='+vip_time,
                dataType: "json",
                success: function (data) {
                    $('#order_switch').attr("value",data.order_switch);
                    $("#label1").html(data.msg).show(300).delay(3000).hide(300);
                    // 展示提单详情页
                    submitOrder(data.order);
                    // 监听支付消息然后提交信息
                    submitOrderPay(data.order);
                }
            });
            $("#order-area").hide(300);
        }
    });

    $("#movies-list").on('click', 'button', function () {
        var cur = $(this).attr("value");
        var uid = $("#cur_user_uid").attr("value");
        cur = _path + '/my_video/src/Vid/' + cur + '/Uid/' + uid;
        clickPlay(this, cur);
    });

    $("#vip-center").click(function () {
        $("#order-area").show();
        $("#common-area").html('<div class="container-fluid">' +
            '                    <div class="row-fluid">' +
            '                        <div class="span12">' +
            '                            <div class="hero-unit"><h1>Hello, 会员!</h1><hr/>' +
            '                                <p>你现在享有MyPlay网站上的所有视频资源，祝你观看愉快！</p><hr/></div></div></div></div>');
    });

    // 设置评论页不可以选择视频分类
    $("#cur_page_str").attr("value", $("#_page_str").attr("value"));
    if ($("#cur_page_str").attr("value") == 1) {
        $("#cover-menus").show();
    }

    $("#webChat_login").click(function () {
        $('#webchat-area').show();
        $('#webchat-area').html('<img src="' + _path + '/weChat/loginEntry">');
        timeOutJob();
        $("#label1").html("正在获取登录二维码……").show(300).delay(3000).hide(300);
    });
    // 设置支付类型
    $("#common-area").on('click', 'li', function () {
        var payType = $(this).attr("value");
        $("#pay_type").attr("value",payType);
    });

    // 获取vip图标标志
    getVipJpg();
});

// 定是任务
function timeOutJob() {
    //重复执行某个方法 setInterval重复执行
    loginCode = window.setInterval(loginCodeJob, 500);
}

function setMsgFlag() {
    $.ajax({
        url: _path + '/weChat/msgFlag',
        type: "post"
    });
}

function payMQJob() {
    $.ajax({
        url: _path + '/weChat/payMQ',
        type: "post",
        dataType: "json",
        success: function (data) {
            var payMsg = data.payMsg;
            if (""!= payMsg){
                $("#label1").html(payMsg).show(300).delay(1000).hide(300);
                $('#order-area').hide();
                // 清空支付消息任务和扫描信息任务
                window.clearTimeout(payMQ);
                getVipJpg();
            }
        }
    });
}

function loginCodeJob() {
    $.ajax({
        url: _path + '/weChat/loginCode',
        type: "post",
        dataType: "json",
        success: function (data) {
            var code = data.loginCode;
            if (code == 1) {
                $('#webchat-area').hide();
                window.clearTimeout(loginCode);
                $("#label1").html("二维码登录成功！").show(300).delay(3000).hide(300);
            }
        }
    });
}

//获取电影界面
function movie_page(i, list) {
    _path = $("#_path").attr("value");
    var uid = $("#cur_user_uid").attr("value");
    pro_views = list.views;
    if (pro_views > 9999) {
        pro_views = "9999+";
    }
    $("#1").append(
        '<div class="col-md-2 resent-grid recommended-grid slider-top-grids">' +
        '<div class="resent-grid-img recommended-grid-img">' +
        '<div class="video-vip"><img src=' + _path + '/static/images/video/video-vip' + list.v_type + '.png width="48px" height="48px"  alt="tupian" /></div>' +
        '<a id="play_video" title=' + _path + '/my_video/src/Vid/' + list.vid + '/Uid/' + uid + ' onclick="clickPlay(this,0)">' +
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
        data: 'n=' + type + '&userid=' + $("#cur_user_uid").attr("value") + '&cur_page=' + cur,
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
    $("#cover-menus").hide();
    $("#order-area").hide();
    $('#webchat-area').hide();

    _path = $("#_path").attr("value");//得到项目的绝对路径
    var url = _path + '/my_video/top20';
    $.ajax({
        url: url,
        type: "post",
        dataType: "json",
        success: function (data) {
            $("#top20-list").empty();
            $(data).each(function (i, list) {
                $("#top20-list").append(
                    '<li><a href=' + _path + '/my_review/review/Vid/' + list.vid + ' class="menu1" id="sport">' +
                    '<span class="fa">' + (i + 1) + '&nbsp;&nbsp;' + list.vname + '</span>' +
                    '</a></li>'
                );
            })
        }
    });
}

// 处理video点击播放
function clickPlay(a, value) {
    _path = $("#_path").attr("value");//得到项目的绝对路径
    if (value == 0) {
        url = $(a).attr("title");//this就是表示此时点击的那个超链的title了
    } else {
        url = value;
    }
    $.ajax({
        url: url,
        type: "post",
        dataType: "json",
        scriptCharset: 'utf-8',
        success: function (data) {
            if (data.play_switch == 1) {
                $("#play-area").slideDown(600);
                $("#play-player").empty();
                $("#play-player").append(
                    '<video id="video_play" src="' + _path + '/static/resources/movies/' + data.srcpath + '" controls="controls"' +
                    'autoplay="autoplay" width="1024" height="576" poster="' + _path + '/static/images/loading.gif">' +
                    '</video>'
                );
                $("#label1").html(data.msg).show(300).delay(3000).hide(300);
            } else {
                $("#order-area").show();
            }
        }
    });
}

function fixCurPage() {
    var n = $("#cur_type").attr("value");
    var cur = $("#cur_page").attr("title");
    ajax_page(n, cur);
}

/**
 * 提交订单展示
 * @param order
 */
function submitOrder(order) {
    var order_switch = $('#order_switch').attr("value");
    var uid = $("#cur_user_uid").attr("value");
    if (order_switch == 1) {
        $("#common-area").html(
            '<div class="container-fluid">' +
            '    <div class="row-fluid"><div class="span12">' +
            '            <div class="page-header"><h1 class="white-color">MyPlay 收银台.</h1></div>' +
            '            <h3 class="white-color">订单支付详情.</h3>' +
            '            <table class="table table-hover table-bordered">' +
            '                <tbody>' +
            '                <tr class="info"><td>订单标号</td><td>' + order.extra + '</td></tr>' +
            '                <tr class="warning"><td>商品名称</td><td>' + order.order_name + '</td></tr>' +
            '                <tr class="info"><td>交易时间</td><td>' + order.trade_time + '</td></tr>' +
            '                <tr class="info"><td>订单类型</td><td>' + order.order_type + '</td></tr>' +
            '                <tr class="info"><td>支付金额</td><td>' + order.pay_total + '元</td></tr></tbody></table>' +
            '            <div class="tabbable" id="tabs-797679">' +
            '                <ul class="nav nav-tabs">' +
            '                    <li class="active" value="0"><a href="#panel-544732" data-toggle="tab">微信</a></li>' +
            '                    <li value="1"><a href="#panel-879116" data-toggle="tab">支付宝</a></li></ul>' +
            '                <div class="tab-content">' +
            '                   <input value=' + order.extra + ' name="order_id" type="hidden" />' +
            '                   <input value=' + order.pay_total + ' name="pay_total" type="hidden" />' +
            '                   <input value="0" id="pay_type" name="pay_type" type="hidden" />' +
            '                   <input value=' + uid + ' name="user_id"  type="hidden" />'+
            '                    <div class="tab-pane active msg" id="panel-544732">' +
            '                        <img alt="支付二维码" src=' + _path + '/static/images/code/v6.jpg class="sys-ewm"/>' +
            '                        <small class="msg-info margin">提示：请用【微信】扫二维码支付</small></div>' +
            '                    <div class="tab-pane msg" id="panel-879116">' +
            '                        <img alt="支付二维码" src=' + _path + '/static/images/code/z6.jpg class="sys-ewm"/>' +
            '                        <small class="msg-info margin">提示：请用【支付宝】扫二维码支付</small></div></div>' +
            '            </div><hr/>' +
            '</div>'
        );
    }
    $("#order-area").show(500);
}

/**
 * 提交订单支付
 * @param order 订单信息
 */
function submitOrderPay(order){
    $.ajax({
        url: _path + '/my_order/paySubmit',
        type: "post",
        data: 'order_id=' + order.extra +
        '&user_id=' + $("#cur_user_uid").attr("value")+
        '&pay_total='+order.pay_total+
        '&pay_type='+$("#pay_type").attr("value"),
        dataType: "json",
        success: function (data) {
            var pay_status = data.pay_status;
            if (pay_status == 1) {
                // 定时扫描消息队列
                payMQ = window.setInterval(payMQJob, 1000);
            }
        }
    });
}

function getVipJpg() {
    $.ajax({
        url: _path + '/my_user/getVipJpg',
        type: "post",
        data: 'user_id=' + $("#cur_user_uid").attr("value"),
        dataType: "json",
        success: function (data) {
            var vip = data.vip;
            if (vip == -1) {
                $('#vip-area').html(
                    '<a id="order" href="javascript:void(0)" class="">' +
                    '<img src='+_path+'/static/images/vip/Vip0.png' +
                    ' class="user_avatar vip-set myimg" style="border-radius:50%;overflow:hidden"/></a>')
            } else if (vip >=0 && vip < 10 ){
                $('#vip-area').html(
                    '<a id="order" href="javascript:void(0)" >'+
                    '<img src='+_path+'/static/images/vip/Vip'+vip+'.png'+
                    ' class="user_avatar vip-set myimg" style="border-radius:50%;overflow:hidden"/></a>')
            } else {
                $('#vip-area').html('<a id="vip-center" href="javascript:void(0)" >' +
                    ' <img src='+_path+'/static/images/vip/Vip'+vip+'.png' +
                    ' class="user_avatar vip-set myimg" style="border-radius:50%;overflow:hidden"/></a>');
            }
        }
    });
}