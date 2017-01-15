<#--
  Created by IntelliJ IDEA.
  User: belong
  Date: 16-11-16
  Time: 下午8:05
  To change this template use File | Settings | File Templates.
-->


<#if Session["global_user"]??>
    <input type="hidden" title="${global_user.id}" id="my_image"/>
    <img src="${mypath}/my_user/pic/userid/${global_user.id}"
         alt="" class="user_avatar myimg" style="border-radius:50%;overflow:hidden"/>
    <b id="my_setting"><i>${global_user.username}</i></b>
<#else>
    <input type="hidden" title="-1" id="my_image"/>
    <img src="static/images/login.png"
         alt="" class="user_avatar myimg" style="border-radius:50%;overflow:hidden"/>
    <b class="my_font"><i>游客</i></b>
</#if>
