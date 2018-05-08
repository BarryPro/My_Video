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
        var returnNum = registerCheck();
        if (returnNum != 0) {
            $.session.set("session_username", $("#rusername").val());
            $.session.set("session_password", $("#rpassword").val());
            $("#r_form").submit();
            $("#label1").show(300).delay(2000).hide(300);
        }
    });

    //登陆
    $("#my_login").click(function () {
        $("#l_form").submit();
    });

    //取消提示消息
    $("#dispear").click(function () {
        $("#label1").hide()
        $("#play-area").show();
    });

    // 键盘按键控制
    keyController();

    //上传电影
    $("#upload").click(function () {
        var returnNum = uploadCheck();
        if (returnNum != 0) {
            $("#u_form").submit();
        }
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
            $("#setting").hide();
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
            var vip = $("#vip_value").attr("value");
            if (vip < 20) {
                getVipProduct(0);
            }
        }
    });

    // 用于判断是订单预览还是订单支付
    $("#common-area").on('click', 'button', function () {
        var order_pay_flag = $("h2").text();
        var vip_type = 0;
        var vip_time = 0;
        var vid = $("#hidden_vid").attr("value");
        if (order_pay_flag == "充值VIP." || order_pay_flag == "购买付费视频.") {
            $("select option:selected").each(function (i, data) {
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
                '&user_id=' + $("#cur_user_uid").attr("value") +
                '&vip_time=' + vip_time,
                dataType: "json",
                success: function (data) {
                    $('#order_switch').attr("value", data.order_switch);
                    $("#label1").html(data.msg).show(300).delay(3000).hide(300);
                    // 展示提单详情页
                    submitOrder(data.order);
                    // 设置全局订单变量
                    order_extra = data.order.extra;
                    order_user_id = $("#cur_user_uid").attr("value");
                    order_pay_total = data.order.pay_total;
                    order_pay_type = $("#pay_type").attr("value");
                    order_type = data.order.order_type;
                    order_vid = vid;

                    // 启动支付扫描程序
                    if (data.order_switch == 1) {
                        // 定时扫描消息队列
                        payMQ = window.setInterval(payMQJob, 1000);
                    }
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
        // 如果是微信支付
        if (payType == "0") {
            $("#panel-v").attr("class", "tab-pane active msg");
            $("#panel-z").attr("class", "tab-pane msg");
            $("#li-v").attr("class", "active");
            $("#li-z").attr("class", "");
        } else {
            $("#panel-z").attr("class", "tab-pane active msg");
            $("#panel-v").attr("class", "tab-pane msg");
            $("#li-z").attr("class", "active");
            $("#li-v").attr("class", "");
        }
    });

    // 获取vip图标标志
    getVipJpg();

    // 设置中心保存
    $("#save-btn").click(function () {
        var alias = $("#inputAlias").val();
        var email = $("#inputEmail").val();
        var num = $("#select-option-num").find("option:selected").text();
        $.ajax({
            url: _path + '/my_user/setting_save',
            type: "post",
            data: 'inputAlias=' + alias +
            '&inputEmail=' + email +
            '&num=' + num +
            '&user_id=' + $("#cur_user_uid").attr("value"),
            dataType: "json",
            success: function (data) {
                $("#label1").html(data.msg).show(300).delay(3000).hide(300);
                $("#setting").hide();
            }
        });
    });

    //  订单查询页
    $("#order_select").click(function () {
        var order_id = $("#inputOrderId").val();
        $.ajax({
            url: _path + '/my_order/order_query',
            type: "post",
            data: 'order_id=' + order_id,
            dataType: "json",
            success: function (data) {
                $("#order-area").show();
                $("#label1").html(data.msg).show(300).delay(3000).hide(300);
                $("#common-area").html('<div class="table-area">\n' +
                    '                        <div id="table-order-area">\n' +
                    '                            <h3 class="greenyellow-color"><b>订单相关信息</b></h3><hr/>\n' +
                    '                            <table class="table" style="color: white">\n' +
                    '                                <h5 class="greenyellow-color"><i><b>订单信息</b></i></h5><hr/>\n' +
                    '                                <thead><tr><td>订单ID</td><td>订单名称</td><td>订单金额</td>\n' +
                    '                                    <td>交易时间</td><td>顶单状态</td><td>用户ID</td></tr></thead>\n' +
                    '                                <tbody><tr><td>' + data.orderVideo.extra + '</td><td>' + data.orderVideo.order_name + '</td>\n' +
                    '                                    <td>' + data.orderVideo.pay_total + '</td><td>' + data.orderVideo.trade_time +
                    '                                </td><td>' + data.orderVideo.order_status + '</td><td>' + data.orderVideo.user_id + '</td></tr>\n' +
                    '                                </tbody></table><hr/></div>\n' +
                    '                        <div id="table-pay-area">\n' +
                    '                            <table class="table" style="color: white">\n' +
                    '                                <h5 class="greenyellow-color"><i><b>订单支付</b></i></h5><hr/>\n' +
                    '                                <thead><tr><td>订单ID</td><td>支付ID</td><td>支付金额</td><td>支付时间</td>\n' +
                    '                                    <td>支付状态状态</td><td>支付类型</td><td>用户ID</td></tr></thead>\n' +
                    '                                <tbody>\n' +
                    '                                <tr><td>' + data.orderVideo.extra + '</td><td>' + data.payOrder.extra + '</td><td>' + data.payOrder.pay_total + '</td>\n' +
                    '                                    <td>' + data.payOrder.pay_time + '</td><td>' + data.payOrder.pay_status + '</td><td>' + data.payOrder.pay_type + '</td>' +
                    '                                 <td>' + data.payOrder.user_id + '</td></tr>\n' +
                    '                                </tbody></table><hr/></div>\n' +
                    '                        <div id="table-pay-area">\n' +
                    '                            <table class="table" style="color: white">\n' +
                    '                                <h5 class="greenyellow-color"><i><b>用户信息</b></i></h5><hr/>\n' +
                    '                                <thead><tr><td>用户ID</td><td>用户名</td><td>VIP</td><td>VIP日期</td><td>页数设置</td></tr>\n' +
                    '                                </thead>\n' +
                    '                                <tbody>\n' +
                    '                                <tr><td>' + data.user.id + '</td><td>' + data.user.username + '</td><td>' + data.user.vip + '</td>\n' +
                    '                                    <td>' + data.user.period + '</td><td>' + data.user.pagenum + '</td></tr></tbody></table><hr/></div>\n' +
                    '                    </div>');
            }
        });
    });
    // 注册后登录
    registerSuccToLogin();

    // 视频后台管理
    videoManager();
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
        data: 'pay_total=' + order_pay_total,
        dataType: "json",
        success: function (data) {
            var payMsg = data.payMsg;
            if (payMsg == order_pay_total) {
                $("#label1").html("二维码收款" + payMsg + "元！").show(300).delay(1000).hide(300);
                $('#order-area').hide();
                // 监听支付消息然后提交信息,字符成功后才更新订单和支付的转态
                submitOrderPay();
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
    if ($("#cur_user_uid").attr("value") == -1) {
        $("#_login").trigger("click");
    } else {
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
                if (data.extra_switch == "10" || data.extra_switch == "100") {
                    $("#play-area").slideDown(600);
                    $("#play-player").html(
                        '<video id="video_play" src="' + _path + '/static/resources/movies/' + data.srcpath + '" controls="controls"' +
                        'autoplay="autoplay" width="1024" height="576" poster="' + _path + '/static/images/loading.gif">' +
                        '</video>'
                    );
                    $("#label1").html(data.msg).show(300).delay(3000).hide(300);
                } else if (data.extra_switch.indexOf("01") == 0) {
                    // 购买付费视频
                    var price_percent = 1;
                    if (data.extra_switch.indexOf("011") == 0) {
                        // vip付费
                        price_percent = 0.6;
                    }
                    getPayProduct(price_percent, data.vid);
                } else {
                    // 购买vip
                    getVipProduct(data.vid);
                }
            }
        });
    }
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
            '                    <li class="active" value="0" id="li-v"><a>微信</a></li>' +
            '                    <li value="1" id="li-z"><a>支付宝</a></li></ul>' +
            '                <div class="tab-content">' +
            '                   <input value=' + order.extra + ' name="order_id" type="hidden" />' +
            '                   <input value=' + order.pay_total + ' name="pay_total" type="hidden" />' +
            '                   <input value="0" id="pay_type" name="pay_type" type="hidden" />' +
            '                   <input value=' + uid + ' name="user_id"  type="hidden" />' +
            '                    <div class="tab-pane active msg" id="panel-v">' +
            '                        <img alt="支付二维码" src=' + _path + '/static/images/code/v' + order.pay_total + '.png class="sys-ewm"/>' +
            '                        <small class="msg-info margin">提示：请用【微信】扫二维码支付</small></div>' +
            '                    <div class="tab-pane msg" id="panel-z">' +
            '                        <img alt="支付二维码" src=' + _path + '/static/images/code/z' + order.pay_total + '.png class="sys-ewm"/>' +
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
function submitOrderPay() {
    $.ajax({
        url: _path + '/my_order/paySubmit',
        type: "post",
        data: 'order_id=' + order_extra +
        '&user_id=' + order_user_id +
        '&pay_total=' + order_pay_total +
        '&pay_type=' + order_pay_type +
        '&order_type=' + order_type +
        '&vid=' + order_vid,
        dataType: "json"
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
                    '<img src=' + _path + '/static/images/vip/Vip0.png' +
                    ' class="user_avatar vip-set myimg" style="border-radius:50%;overflow:hidden"/></a>')
                //  vip的级别
            } else if (vip >= 0 && vip < 10 || vip == 99) {
                $('#vip-area').html('<a id="order" href="javascript:void(0)" >' +
                    ' <img src=' + _path + '/static/images/vip/Vip' + vip + '.png' +
                    ' class="user_avatar vip-set myimg" style="border-radius:50%;overflow:hidden"/></a>');
            } else if (vip > 10 && vip < 99) {
                vip = vip + "";
                vip = vip.substr(1, vip.length);
                $('#vip-area').html(
                    '<a id="order" href="javascript:void(0)" >' +
                    '<img src=' + _path + '/static/images/vip/svip.png' +
                    ' class="user_avatar vip-set myimg" style="border-radius:50%;overflow:hidden"/>' +
                    '<img src=' + _path + '/static/images/vip/Vip' + vip + '.png' +
                    ' class="user_avatar vip-set myimg" style="border-radius:50%;overflow:hidden"/></a>');
            }
        }
    });
}

function getVipProduct(vid) {
    $("#order-area").show();
    $("#common-area").html('<div class="container-fluid">' +
        '    <div class="row-fluid"> ' +
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
        '            <hr/><div ><input type="hidden" id="hidden_vid" value=' + vid + ' >' +
        '               <label class="msg">充值类型：</label>' +
        '                <select class="margin" name="vip_type"><option value="1">vip</option><option value="2">svip</option></select>' +
        '                <label class="msg">充值时长：</label>' +
        '                <select class="margin" name="vip_time"><option  value="1">1个月</option><option value="6">6个月</option>' +
        '                           <option value="12">12个月</option></select></div><hr/>' +
        '            <button class="btn btn-block btn-primary my_btn" >立即充值</button><hr/>' +
        '</div></div></div>'
    );
}

function getPayProduct(percent, vid) {
    $("#order-area").show();
    $("#common-area").html('<div class="container-fluid">' +
        '    <div class="row-fluid"> ' +
        '        <div class="span12">' +
        '            <div class="page-header">' +
        '            <h2 class="white-color">购买付费视频.</h2></div>' +
        '            <div ><input type="hidden" id="hidden_vid" value=' + vid + ' >' +
        '                <select class="margin" name="vip_type" hidden><option value="0"></option></select>' +
        '                <select class="margin" name="vip_time" hidden><option  value=' + percent + '></option></select></div>' +
        '            <button class="btn btn-block btn-primary my_btn" >立即付费</button><hr/>' +
        '</div></div></div>'
    );
}

function registerSuccToLogin() {
    var msg = $("#label1").text();
    if (msg != "") {
        if (msg == "恭喜你注册成功了") {
            $("#username").val($.session.get("session_username"));
            $("#password").val($.session.get("session_password"));
            $("#my_login").trigger("click");
        }
        $("#label1").show(300).delay(1000).hide(300);
    }
}

/**
 * 用于定义键盘按键的控制
 */
function keyController() {
    $(window).keyup(function (e) {
        // esc键，控制关闭
        if (e.keyCode == 27) {//此处代表按的是键盘的Esc键
            $("#play-area").slideUp(300);
            $("#order-area").slideUp(300);
            $("#play-area").empty();
            $('#webchat-area').hide();
            $("#setting").hide();
            fixCurPage();
        }
        // 回车键，控制提交
        if (e.keyCode == 13) {
            // 登录提交
            var display_login = $("#small-dialog").css("display");
            if (display_login == "block") {
                $("#l_form").submit();
            }
            // 注册提交
            var display_register = $("#small-dialog2").css("display");
            if (display_register == "block") {
                var returnNum = registerCheck();
                if (returnNum != 0) {
                    $("#r_form").submit();
                }
            }
            // 上传提交
            var display_upload = $("#small-dialog3").css("display");
            if (display_upload == "block") {
                var returnNum = uploadCheck();
                if (returnNum != 0) {
                    $("#u_form").submit();
                }
            }
        }
        // 上键,音量加
        if (e.keyCode == 38) {
            console.log(e.keyCode);
        }
        // 下键,音量减
        if (e.keyCode == 40) {
            console.log(e.keyCode);
        }
        // 左键，快退
        if (e.keyCode == 37) {
            console.log($("#video_play").currentTime);
        }
        // 右键，快进
        if (e.keyCode == 39) {
            $("#video_play").on(
                "timeupdate",
                function () {
                    if (this.currentTime > this.duration) {
                        this.currentTime = this.duration;
                    } else {
                        this.currentTime++;
                    }
                });
        }
        // 空格，暂停
        if (e.keyCode == 32) {
            var url = _path + '/static/resources/movies/蚁人_bd.mp4';
            $("#video_play").attr("src",url);

        }
    });
}

function registerCheck() {
    //注册检测&&提交
    if ($("#rusername").val() == '') {
        $("#label1").html("用户名不能为空哦!").show(300).delay(3000).hide(300);
        return 0;
    }
    if ($("#rpassword").val().length < 6) {
        $("#label1").html("密码至少6位哦!").show(300).delay(3000).hide(300);
        return 0;
    }
    if ($("#rpassword").val() != $("#repwd").val()) {
        $("#label1").html("两次输入得密码不一致哦!").show(300).delay(3000).hide(300);
        return 0;
    }
    if ($("#file0").val() == '') {
        $("#label1").html("你还没有选择文件哦!").show(300).delay(3000).hide(300);
        return 0;
    }
}

function uploadCheck() {
    if ($("#vname").val() == '') {
        $("#vname").val("未知");
    }
    if ($("#vdirector").val() == '') {
        $("#vdirector").val("未知");
    }
    if ($("#vactor").val() == '') {
        $("#vactor").val("未知");
    }
    if ($("#file2").val() == '') {
        $("#label1").html("你还没有选择上传视频的海报哦！").show(300).delay(3000).hide(300);
        return 0;
    }
    if ($("#vinfo").val() == '') {
        $("#vinfo").val("未知");
    }
    if ($("#file1").val() == '') {
        $("#label1").html("你还没有选择上传视频哦！").show(300).delay(3000).hide(300);
        return 0;
    }
}

function videoManager() {
    $("#video_manager").click(function () {
        $("#label1").html("正在查询……").show(300).delay(1000).hide(300);
        $("#order-area").show();
        $("#common-area").empty();
        $.ajax({
            url: _path + '/my_video/lately10',
            type: "post",
            dataType: "json",
            success:function (data) {
                $("#common-area").append('<form id="execute_form" action="'+_path+'/my_video/execute_update_video" method="post">' +
                    '<div class="container-fluid"><div class="row-fluid">' +
                    '<table class="span12">' +
                    '<h3 class="text-center white-color">视频管理后台.</h3><hr/><div>' +
                    '<label class="white-color">更新后类型：</label>' +
                    '<select name="opeation_type">' +
                    '  <option value="1">VIP视频</option>' +
                    '  <option value="3">付费视频</option>' +
                    '  <option value="2">用券视频</option>' +
                    '  <option value="0">普通视频</option></select>' +
                    '<label class="white-color">操作：</label>' +
                    '<input type="text" id="search-text"/>' +
                    '<button type="button" id="search-video-update">查找</button>' +
                    '<button type="button" id="batchFx">反选</button>' +
                    '<button type="button" id="batchFalse">重置</button>' +
                    '<button type="button" id="batchTrue">全选</button>' +
                    '</div><hr/>' +
                    '<table class="table table-condensed white-color">' +
                    '<thead><tr><th>操作</th><th>序号</th><th>视频名称</th><th>上传时间</th>' +
                    '<th>点击量</th><th>视频类型</th><th>上传者</th></tr>' +
                    '</thead><tbody id="opeation_video">'+
                    '</tbody></table><hr/><button class="btn btn-block btn-warning" id="execute_submit">执行</button><hr/></div></div></form>');
                videoAppend(data);
            }
        });
        searchUpdateVideoType();
        executeOpeation();
    });
}

function autoExtendHeight(){
    var str_height = $("#common-area").css("height")
    var num_height = parseInt(str_height);
    if (num_height >= 637){
        $("#order-area").css("height","637px")
    }
}

function searchUpdateVideoType(){
    $("#common-area").on('click','#search-video-update',function () {
        $.ajax({
            url: _path + '/my_video/search_video_info',
            type: "post",
            data: 'video_info=' + $("#search-text").val(),
            dataType: "json",
            success:function (data) {
                $("#label1").html(data.msg).show(300).delay(1000).hide(300);
                videoAppend(data);
            }
        });
        autoExtendHeight();
    });
    // 反选
    $("#common-area").on('click','#batchFx',function () {
        $('input:checkbox').each(function () {
            if ($(this).prop("checked")){
                $(this).prop("checked",false);
            } else {
                $(this).prop("checked",true);
            }
        })
    });
    // 全取消
    $("#common-area").on('click','#batchFalse',function () {
        $('input:checkbox').each(function () {
            $(this).prop("checked",false);
        })
    });
    // 全选择
    $("#common-area").on('click','#batchTrue',function () {
        $('input:checkbox').each(function () {
            $(this).prop("checked",true);
        })
    });
}

function videoAppend(data){
    $("#opeation_video").empty();
    $(data.moviesList).each(function (i, video) {
        var type = video.v_type;
        switch (type){
            case 0:type = "普通视频";break;
            case 1:type = "VIP视频";break;
            case 2:type = "用券视频";break;
            case 3:type = "付费视频";break;
        }
        $("#opeation_video").append('<tr><td><input type="checkbox" name="video_checkbox" value="'+video.vid+'"/><td>'+(i+1)+'</td>' +
            '</td><td>'+video.vname+'</td><td>'+video.vdate+'</td><td>'+video.views+'</td><td>'+type+'</td><td>'+video.user.username+'</td></tr>');
    });
}

function executeOpeation(){
    $("#common-area").on("click","#execute_submit",function(){
        $("#execute_form").submit();
    });
}