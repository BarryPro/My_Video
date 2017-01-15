<#include "../common/header.ftl">
<#include "../video/particle.ftl">
<body background="${mypath}/images/bg.jpg">
<div align="center">
    <img src="static/images/加载.gif" alt="加载" id="my_img" class="player">
    <video id="video_play" src="${mypath}/${srcpath}" controls="controls"
           autoplay="autoplay" width="1024" height="576" poster="static/images/loading.gif">
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
