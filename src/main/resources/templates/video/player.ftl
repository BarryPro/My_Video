<#include "../common/header.ftl">
<#include "../video/particle.ftl">
<body background="${my_path}/images/bg.jpg">
<div align="center">
    <img src="${my_path}/static/images/加载.gif" alt="加载" id="my_img" class="player">
    <video id="video_play" src="${my_path}/static/${srcpath}" controls="controls"
           autoplay="autoplay" width="1024" height="576" poster="${my_path}/static/images/loading.gif">
    </video>
</div>

<script type="text/javascript">
    $(function () {
        if ($("#video_play").attr("src") != "") {
            $("#my_img").attr("style", "display:none");
        }
    })
</script>
</body>
</html>
