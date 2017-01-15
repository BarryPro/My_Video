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
            <a class="navbar-brand" href="${my_path}/my_video/home"><h1><img src="${my_path}/images/logo.png" alt=""/></h1></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <div class="top-search">
                <form class="navbar-form navbar-right" id="form_search" method="post">
                    <input type="text" class="form-control" placeholder="Search..." id="txt" name="txt">
                    <input type="button" value=" " id="btn_search">
                </form>
                <div class="signin">
                    <label id="label1" style="color: red"><b><i></i></b></label>
                </div>
            </div>
            <div class="header-top-right">
                <div class="signin">
                <#include "../common/dropdown.ftl">
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
