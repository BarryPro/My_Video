<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>设置</title>
    <#assign mypath =Request.request.contextPath />
    <link rel="stylesheet" href="${my_path}/static/css/my_style.css" media="screen" type="text/css" />
</head>
<body>
<div class="container">
    <form id="contact" action="" method="post">
        <h3>设置</h3>
        <h4>Contact us today, and get reply with in 24 hours!</h4>
        <fieldset>
            <input placeholder="Your name" type="text" tabindex="1" required autofocus>
        </fieldset>
        <fieldset>
            <input placeholder="Your Email Address" type="email" tabindex="2" required>
        </fieldset>
        <fieldset>
            <input placeholder="Your Phone Number" type="tel" tabindex="3" required>
        </fieldset>
        <fieldset>
            <input placeholder="Your Web Site starts with http://" type="url" tabindex="4" required>
        </fieldset>
        <fieldset>
            <span>性别:</span>
            <input type="radio" name="sex" tabindex="5" value="m" required>男
            <input type="radio" name="sex" tabindex="6" value="f" required>女
        </fieldset>
        <fieldset>
            <textarea placeholder="Type your Message Here...." tabindex="7" required></textarea>
        </fieldset>
        <fieldset>
            <button name="submit" type="submit" id="contact-submit" data-submit="...Sending">Submit</button>
        </fieldset>
    </form>

</div>
</body>

</html>
