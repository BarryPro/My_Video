<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12 white-color">
            <div class="page-header">
                <h3>MyPlay 个人中心.</h3>
            </div>
            <div class="container-fluid">
                <div class="row-fluid">
                    <div class="span12">
                        <h3>设置中心</h3>
                        <div class="page-header">
                            <h3><small class="msg">用户信息</small></h3>
                        </div>
                        <#if Session["global_user"]??>
                         <div>
                             <label>昵称：</label>
                             <#if global_user.alias??>
                                 <span>${global_user.alias!}</span><br/>
                             <#else>
                                <span>${global_user.username!}</span><br/>
                             </#if>
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
                                    <label class="control-label">昵称： </label>
                                    <input id="inputAlias" type="text" placeholder="昵称" class="black-color"/>
                                </div>

                                <div class="controls">
                                    <label class="control-label" for="inputEmail">邮箱： </label>
                                    <input id="inputEmail" type="text" placeholder="邮箱" class="black-color"/>
                                </div>
                                <div class="controls">
                                    <label class="control-label">页面： </label>
                                    <select id="select-option-num" class="black-color">
                                        <option>6</option>
                                        <option>12</option>
                                        <option>18</option>
                                        <option>24</option>
                                    </select><label class="control-label">个视频数</label>
                                </div><hr/>
                                <button class="btn btn-block btn-info" type="button" id="save-btn">保存</button><hr/>
                            </div>
                        </form>
                       <#if global_user??>
                           <#if (global_user.username = "admin")>
                           <div class="order-control">
                               <h3>订单管理后台</h3>
                               <div class="page-header"><h4><small class="msg">管理员登录</small></h4></div>
                               <button class="btn btn-block btn-warning" type="button" id="webChat_login">登录</button>
                               <hr/>
                               <div class="page-header"><h4 class="msg"><small class="msg">订单查询</small></h4></div>
                               <label>order_id： </label>
                               <input id="inputOrderId" type="text" placeholder="订单ID" class="black-color"/><hr/>
                               <button class="btn btn-block btn-warning" type="button" id="order_select">查询</button><hr/>
                           </div>
                           </#if>
                       </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>