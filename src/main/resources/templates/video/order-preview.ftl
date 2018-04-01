<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="page-header">
                <h1 class="white-color">充值VIP.</h1></div>
            <table class="table table-hover table-bordered white-color">
                <thead>
                <tr>
                    <th>权限</th>
                    <th>普通</th>
                    <th>vip</th>
                    <th>svip</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>普通视频</td>
                    <td><img src="${my_path}/static/images/right.png" class="right-size"/></td>
                    <td><img src="${my_path}/static/images/right.png" class="right-size"/></td>
                    <td><img src="${my_path}/static/images/right.png" class="right-size"/></td>
                </tr>
                <tr >
                    <td>vip视频</td>
                    <td></td>
                    <td><img src="${my_path}/static/images/right.png" class="right-size"/></td>
                    <td><img src="${my_path}/static/images/right.png" class="right-size"/></td>
                </tr>
                <tr>
                    <td>付费/用券视频</td>
                    <td></td>
                    <td></td>
                    <td><img src="${my_path}/static/images/right.png" class="right-size"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <hr/>
            <div >
                <label class="msg">充值类型：</label>
                <select class="margin">
                    <option>vip</option>
                    <option>svip</option>
                </select>
                <label class="msg">充值时长：</label>
                <select class="margin">
                    <option>1个月</option>
                    <option>6个月</option>
                    <option>12个月</option>
                </select>
            </div><hr/>
            <button class="btn btn-block btn-primary my_btn" type="button" id="preview">立即充值</button><hr/>
        </div>
    </div>
</div>