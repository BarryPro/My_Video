<script type="text/javascript" src="${my_path}/static/js/modernizr.custom.min.js"></script>
<link href="${my_path}/static/css/popuo-box.css" rel="stylesheet" type="text/css" media="all"/>
<link href="${my_path}/static/css/diy.css" rel="stylesheet" type="text/css" media="all"/>
<script src="${my_path}/static/js/jquery.magnific-popup.js" type="text/javascript"></script>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <input type="hidden" id="_path" value="${my_path}"/>
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${my_path}/my_video/home"><h1><img src="${my_path}/images/logo.png" alt=""/>
            </h1></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <div class="top-search">
                <form class="navbar-form navbar-right" id="form_search" method="post">
                    <input type="text" class="form-control" placeholder="Search..." id="txt" name="txt">
                    <input type="button" value=" " id="btn_search">
                    <input type="hidden" id="cur_type" value="0"/>
                    <#if loginCode??>
                        <input type="hidden" id="login_code" value="${loginCode!}"/>
                    </#if>
                    <#if order??>
                        <input type="hidden" id="order_switch" value="${order_switch!}"/>
                        <input type="hidden" id="order_id" value="${order.order_id?c}"/>
                        <input type="hidden" id="order_name" value="${order.order_name!}"/>
                        <input type="hidden" id="trade_time" value="${order.trade_time!}"/>
                        <input type="hidden" id="order_type" value="${order.order_type!}"/>
                        <input type="hidden" id="pay_total" value="${order.pay_total!}"/>
                    </#if>
                    <#if Session["global_user"]??>
                        <input type="hidden" id="cur_user_uid" value="${global_user.id!}"/>
                    <#else>
                        <input type="hidden" id="cur_user_uid" value="-1"/>
                    </#if>
                </form>
                <div class="info-view">
                    <#if Session["global_user"]??>
                    <#if (global_user.vip >= 0 && global_user.vip < 10)>
                        <a id="order" href="javascript:void(0)" class="">
                        <img src="${my_path!}/static/images/vip/Vip${global_user.vip!}.png"
                             class="user_avatar vip-set myimg" style="border-radius:50%;overflow:hidden"/></a>
                    <#else>
                        <a id="vip-center" href="javascript:void(0)" class="">
                            <img src="${my_path!}/static/images/vip/Vip${global_user.vip!}.png"
                                 class="user_avatar vip-set myimg" style="border-radius:50%;overflow:hidden"/></a>
                    </#if>
                        <input type="hidden" title="${global_user.id!}" id="my_image"/>
                        <img  id="_setting" src="${my_path!}/my_user/pic/userid/${global_user.id!}"
                             title="${global_user.username!}" class="user_avatar myimg" style="border-radius:50%;overflow:hidden"/>
                    <#else>
                        <a id="order" href="javascript:void(0)" class="">
                        <img src="${my_path!}/static/images/vip/Vip0.png"
                             class="user_avatar vip-set myimg" style="border-radius:50%;overflow:hidden"/></a>
                        <input type="hidden" title="-1" id="my_image"/>
                        <a id="_login" href="#small-dialog" class="play-icon popup-with-zoom-anim">
                            <img src="${my_path!}/static/images/login.png" class="user_avatar myimg"
                                 title="Visitor" style="border-radius:50%;overflow:hidden"/></a>
                    </#if>

                    <a href="#small-dialog2" class="play-icon popup-with-zoom-anim">
                        <img title="注册" src="${my_path!}/static/images/register.png" class="user_avatar myimg"/></a>

                    <#if Session["global_user"]?exists>
                        <a href="#small-dialog3" class="play-icon popup-with-zoom-anim">
                            <img title="上传" src="${my_path!}/static/images/upload.png" class="user_avatar myimg"/></a>
                    </#if>
                    <a href="${my_path}/my_user/logout">
                        <img title="注销" src="${my_path!}/static/images/Sign-out.png" class="user_avatar myimg" /></a>
                    <a href="javascript:void(0)" id="dispear">
                        <img title="消息" src="${my_path!}/static/images/msg.png" class="user_avatar myimg" /></a>
                </div>
                <div class="info-msg">
                    <label id="label1" style="color: red"><b><i>${(msg)?default("")}</i></b></label>
                </div>
            </div>
            <div class="signin">
            <#include "../video/signin.ftl">
            <#include "../video/register.ftl">
            <#include "../video/upload.ftl">
                <script>
                    $(document).ready(function () {
                        $('.popup-with-zoom-anim').magnificPopup({
                            type: 'inline',
                            fixedContentPos: false,
                            fixedBgPos: true,
                            overflowY: 'auto',
                            closeBtnInside: true,
                            preloader: false,
                            midClick: true,
                            removalDelay: 300,
                            mainClass: 'my-mfp-zoom-in'
                        });
                    });
                </script>
            </div>
        </div>
    </div>
</nav>
