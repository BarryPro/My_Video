<script type="text/javascript" src="${my_path}/static/js/modernizr.custom.min.js"></script>
<link href="${my_path}/static/css/popuo-box.css" rel="stylesheet" type="text/css" media="all"/>
<link href="${my_path}/static/css/diy.css" rel="stylesheet" type="text/css" media="all"/>
<script src="${my_path}/static/js/jquery.magnific-popup.js" type="text/javascript"></script>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <input type="hidden" id="_path" value="${my_path}"/>
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${my_path}/my_video/home"><h1><img src="${my_path}/images/logo.png" alt=""/>
            </h1></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <div class="top-search">
                <form class="navbar-form navbar-right" id="form_search" method="post">
                    <input type="text" class="form-control" placeholder="Search..." id="txt" name="txt">
                    <input type="button" value=" " id="btn_search">
                    <input type="hidden" id="cur_type" value="0"/>
                    <input type="hidden" id="msg_flag" value="0"/>
                    <input type="hidden" id="order_switch" value="0"/>
                    <input type="hidden" id="cur_page_str" value="0"/>
                    <#if Session["global_user"]??>
                        <input type="hidden" id="cur_user_uid" value="${global_user.id!}"/>
                    <#else>
                        <input type="hidden" id="cur_user_uid" value="-1"/>
                    </#if>
                </form>
                <div class="info-view">
                    <a id="vip-area"></a>
                    <#if Session["global_user"]??>
                        <img  id="_setting" src="${my_path!}/my_user/pic/userid/${global_user.id!}"
                              title="${global_user.username!}" class="user_avatar myimg" style="border-radius:50%;overflow:hidden"/>
                    <#else>
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
                    <label id="label1" style="color: red">${(msg)?default("")}</label>
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
