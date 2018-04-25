<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="page-header">
                <h1>
                    MyPlay 个人中心.
                </h1>
            </div>
            <div class="container-fluid">
                <div class="row-fluid">
                    <div class="span12">
                        <div class="page-header">
                            <h3>
                                <small>用户信息</small>
                            </h3>
                        </div>
                        <#if Session["global_user"]??>
                         <div class="">
                             <label>昵称：</label>
                             <span>${global_user.username!}</span><br/>
                             <label>邮箱： </label>
                             <span>${global_user.email!}</span><br/>
                             <label>vip等级： </label>
                             <span>${global_user.vip!}级</span><br/>
                             <label>vip积分： </label>
                             <span>${global_user.vipGrade!}</span><br/>
                         </div><hr/>
                        </#if>

                        <form class="form-horizontal">
                            <div class="control-group">
                                <div class="controls">
                                    <label class="control-label">昵称：</label>
                                    <input id="inputAlias" type="text" />
                                </div>

                                <div class="controls">
                                    <label class="control-label" for="inputEmail">邮箱：</label>
                                    <input id="inputEmail" type="text" />
                                </div>
                                <div class="controls">
                                    <label class="control-label">设置页面</label>
                                    <select>
                                        <option>6</option>
                                        <option>12</option>
                                        <option>18</option>
                                        <option>24</option>
                                    </select><label class="control-label">个视频数</label>
                                </div><hr/>
                                <button class="btn btn-block btn-info" type="button">保存</button>
                            </div>
                        </form>
                       <#if global_user??>
                           <#if (global_user.username = "admin")>
                        <div class="page-header">
                            <h3>
                                <small>管理员登录</small>
                            </h3>
                        </div> <button class="btn btn-block btn-warning" type="button" id="webChat_login">登录</button>
                           </#if>
                       </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>