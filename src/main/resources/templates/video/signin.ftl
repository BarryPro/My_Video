<#--
  Created by IntelliJ IDEA.
  User: belong
  Date: 16-11-17
  Time: 下午6:58
  To change this template use File | Settings | File Templates.
-->
<div id="small-dialog" class="mfp-hide">
    <h3>登录</h3>
    <#include "../common/left_page.ftl">
    <div class="signup">
        <form method="post" id="l_form" action="${my_path}/my_user/login">
            <input type="text" class="email" placeholder="用户名" name="username" id="username"/>
            <input type="hidden" id="_action" name="action" value="0"/>
            <input type="password" placeholder="密码" name="password" id="password"/><br>
            <div class="wrap" style="width: 200px;">
                <p>是否记住密码一个星期</p>
                <input type="checkbox" id="checkbox_d1" name="cookie" class="chk_4"/><label
                    for="checkbox_d1"></label>
            </div>
            <hr/>
            <input type="button" class="button" value="登录" id="my_login"/>
        </form>
        <div class="forgot">
            <a href="javascript:void(0)">Forgot password ?</a>
        </div>
    </div>
    <div class="clearfix"></div>
</div>

