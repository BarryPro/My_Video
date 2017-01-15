<link href="${my_path}/static/css/my_menu.css" rel='stylesheet' type='text/css' media="all"/>
<ul class="menu">
    <li><#include "../common/user.ftl">
        <ul class="submenu">
            <li><a id="_login" href="#small-dialog" class="play-icon popup-with-zoom-anim">登录</a></li>
            <li><a href="#small-dialog2" class="play-icon popup-with-zoom-anim">注册</a></li>
            <#if Session["global_user"]?exists>
                <li><a href="#small-dialog3" class="play-icon popup-with-zoom-anim">上传</a></li>
            </#if>
            <li><a href="${my_path}/my_user/logout">注销</a></li>
            <li><a href="javascript:void(0)" id="dispear">消息</a></li>
            <li>
                <select name="row" id="my_select">
                    <option value="3">设置</option>
                    <option value="4">04==rows</option>
                    <option value="8">08==rows</option>
                    <option value="12">12==rows</option>
                </select>
            </li>
        </ul>
    </li>
</ul>
	
	
	
	
	

