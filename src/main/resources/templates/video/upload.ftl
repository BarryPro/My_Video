<div id="small-dialog3" class="mfp-hide">
    <h3>上传</h3>
    <#include "../common/left_page.ftl">
    <div class="signup">
        <form id="u_form" method="post" enctype="multipart/form-data" action="${my_path}/my_video/upload">
            <input type="text" class="email" placeholder="电影名字" name="vname" id="vname"/><br/>
            <input type="text" class="email" placeholder="导演名" name="vdirector" id="vdirector"/><br/>
            <input type="text" class="email" placeholder="演员表" name="vactor" id="vactor"/><br/>
            <div align="left"><b>海报支持得格式：.jpg .gif .bmp .png</b></div>
            <a href="javascript:;" class="file">选择你要上传的海报文件
                <input type="file" name="filep" id="file2">
            </a><br/><br/><br/>
            <input type="hidden" name="id" value=""/>
            选择上传视频类型:
                <select name="type" id="type">
                <option value="1">电视</option>
                <option value="2" selected>电影</option>
                <option value="3">MV</option>
                <option value="4">动漫</option>
                <option value="5">其他</option>
            </select>
            <textarea style="width: 90%;" name="vinfo" placeholder="电影描述" id="vinfo"></textarea><br/>
            <div align="left"><b>视频支持得格式：.mp4 .avi</b></div>
            <a href="javascript:;" class="file">选择你要上传的电影文件
                <input type="file" name="filem" id="file1" onchange="filesize(this)">
            </a><br/><br/><br/>
            <hr/>
            <input type="button" class="button" value="上传" id="upload"/>
        </form>
    </div>
    <div class="clearfix"></div>
</div>
