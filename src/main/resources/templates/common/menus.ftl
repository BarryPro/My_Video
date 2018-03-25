<link href="${my_path}/static/font-awesome-4.7.0/css/font-awesome.min.css" type="text/css" rel="stylesheet">
<div class="col-sm-3 col-md-2 sidebar">
    <div >
        <ul class="nav nav-sidebar">
            <li><a href="javascript:void(0)" class="menu1" id="sport">
                <span class="fa fa-eye" aria-hidden="true" id="counter"></span>
            </a></li>
            <li class="active"><a href="javascript:void(0)" id="home" class="home-icon">
                <span class="fa fa-home" aria-hidden="true">&nbsp;&nbsp;首页</span>
            </a></li>
            <li><a href="javascript:void(0)" class="user-icon" id="tv">
                <span class="fa fa-tv" aria-hidden="true">&nbsp;&nbsp;电视剧</span>
            </a></li>
            <li><a href="javascript:void(0)" class="menu1" id="movie">
                <span class="fa fa-film" aria-hidden="true">&nbsp;&nbsp;电影</span>
            </a></li>
            <li><a href="javascript:void(0)" class="menu1" id="vip">
                <span class="fa fa-diamond" aria-hidden="true">&nbsp;&nbsp;VIP会员</span>
            </a></li>
            <li><a href="javascript:void(0)" class="menu1" id="MV">
                <span class="fa fa-headphones" aria-hidden="true">&nbsp;&nbsp;音乐MV</span>
            </a></li>
            <li><a href="javascript:void(0)" class="song-icon">
                <span class="fa fa-rss" aria-hidden="true">&nbsp;&nbsp;综艺</span>
            </a></li>
            <li><a href="javascript:void(0)" class="news-icon" id="news">
                <span class="fa fa-caret-square-o-right" aria-hidden="true">&nbsp;&nbsp;动漫</span>
            </a></li>
        </ul>
        <div class="side-bottom">
            <div class="copyright">
                <ul class="nav nav-sidebar">
                    <li><a href="javascript:void(0)" class="menu1" id="sport">
                        <span class="fa fa-copyright" aria-hidden="true">&nbsp;&nbsp;Copyright 2018. from author: Barry</span>
                    </a></li>
                </ul>
            </div>
        </div>
        <div class="top-20">
            <div class="video-list">
                <ul class="nav nav-sidebar">
                    <li><a href="javascript:void(0)" class="menu1" id="sport">
                        <span class="fa fa-trophy">&nbsp;&nbsp;热门电影</span>
                    </a></li>
                    <div id="top20-list"></div>
                </ul>
            </div>
        </div>

        <div class="setting" id="setting">
            <img title="Close (ESC)" id="setting_close" class="setting_close _close" src="${my_path}/static/images/close.png">
        </div>

        <div align="center">
            <#--播放区-->
            <div class="play-area " id="play-area">
                <div id="play-close">
                    <img title="Close (ESC)" id="play_close" class="my_close _close" src="${my_path}/static/images/close.png">
                </div>
                <div id="play-player"></div>
            </div>
            <#--影视简介评论区-->
            <div class="commit-area " id="commit-area">
                <img title="Close (ESC)" id="commit_close" class="commit_close _close" src="${my_path}/static/images/close.png">
            </div>
        </div>
    </div>
</div>
