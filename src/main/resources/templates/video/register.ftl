<div id="small-dialog2" class="mfp-hide">
    <h3>注册</h3>
    <#include "../common/left_page.ftl">
    <div class="signup">
        <form id="r_form" method="post" enctype="multipart/form-data" action="${my_path}/my_user/register">
            <input type="text" class="email" placeholder="用户名" name="username" id="rusername"/>
            <input type="password" placeholder="密码" name="password" id="rpassword"/>
            <input type="password" placeholder="确认密码" id="repwd"/>
            <a href="javascript:;" class="file">选择文件.jpg|.png|.gif|.bmp
                <input type="file" name="file0" id="file0">
            </a>
            <br/><br/><br/><br/>
            <hr/>
            <input type="button" class="button" value="注册" id="register"/>
        </form>
    </div>
    <div class="clearfix"></div>
</div>
