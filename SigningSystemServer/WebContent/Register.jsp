<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>考勤系统</title>
        <style type="text/css">
            body {
                font-size: 12px;
                background-color: #ffffff
            }
            
            td {
                font-size: 12px;
            }
            
            span {
                font-size: 12px;
            }
            
            .outer_box {
                position: absolute;
                width: 428px;
                height: 296px;
                left: 50%;
                top: 50%;
                margin-left: -214px;
                margin-top: -148px;
            }
            
            .prompt {
                width: 428px;
                height: 296px;
                text-align: left;
                display: block;
                filter: progid: DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src="images/prompt_bg.png" mce_src="images/prompt_bg.png");
                background: url(images/prompt_bg.png);
                _background: none;
                overflow: hidden;
            }
            
            .prompt h1 {
                float: left;
                font-size: 14px;
                font-weight: bold;
                text-align: left;
                margin: 18px 0 0 20px;
                color: #334f67;
                display: inline;
            }
            
            .prompt .close {
                position: relative;
                float: right;
                margin: 7px 18px 0 0;
            }
            
            .prompt h2 {
                clear: both;
                margin: 0 0 25px 28px;
                line-height: 18px;
                font-size: 13px;
                font-weight: bold;
                text-align: left;
                color: #334f67;
            }
            
            .prompt h3 {
                margin: 0px 0 6px 60px;
                text-align: left;
                font-size: 14px;
            }
            
            .prompt h3 .input_t {
                position: relative;
                border: #c4c7c8 1px solid;
                width: 186px;
                height: 20px;
                line-height: 20px;
            }
            
            .prompt h4 {
                margin: 0 0 10px 80px;
                font-size: 12px;
            }
            
            .prompt .ts5 {
                float: left;
                border: 1px solid #000;
                margin: -27px 0px 0 230px;
                height: 20px;
                line-height: 20px;
                background: #fffce9;
                border: 1px solid #edddab;
                padding: 0 5px;
                color: #c0880f;
                display: inline;
            }
            
            .prompt h4 .input_c {
                position: relative;
            }
            
            .prompt h4 span {
                float: left;
                margin-left: 5px;
                height: 30px;
                line-height: 30px;
            }
            
            .prompt h4 span a:link {
                position: relative;
                color: #fd6c01;
                text-decoration: underline;
            }
            
            .prompt h4 span a:visited {
                position: relative;
                color: #fd6c01;
                text-decoration: underline;
            }
            
            .prompt h4 .bottom {
                position: relative;
                float: left;
                margin-left: 4px;
                width: 88px;
                height: 30px;
                background: url(images/bottom.gif) no-repeat;
                border: none;
                cursor: pointer;
                font-size: 14px;
                font-weight: bold;
                color: #fff;
            }
        </style>
        <script type="text/javascript" src="./lib/md5.js"></script>
        <script src="./lib/jquery.min.js"></script>
    </head>

    <body>
        <table>
            <div id="registerattach" style="position: absolute; width: 428px; height: 296px; left: 739px; top: 148px; z-index: 10002;">
                <div class="prompt">
                    <h1>用户登录</h1><span class="close"><img src="images/close.gif" onclick="do_close()"></span>
                    <h2><br>感谢使用管理员考勤系统，请注册：</h2>
                    <h3>账　号：<input name="id" id="id" type="text" class="input_t" onmouseover="this.style.borderColor='#7dbde2'" onmouseout="this.style.borderColor='#c4c7c8'" onclick="this.value='';this.focus();" style="border-color: rgb(196, 199, 200);"></h3>
                    <h3>用户名：<input name="username" id="username" type="text" class="input_t" onmouseover="this.style.borderColor='#7dbde2'" onmouseout="this.style.borderColor='#c4c7c8'" onclick="this.value='';this.focus();" style="border-color: rgb(196, 199, 200);"></h3>
                    <h3>密　码：<input name="password" id="password" type="password" class="input_t" onmouseover="this.style.borderColor='#7dbde2'" onmouseout="this.style.borderColor='#c4c7c8'" onclick="this.value='';this.focus();" style="border-color: rgb(196, 199, 200);"></h3>
                    <h4><input type="button" name="register" value="注册" class="bottom" onclick="Register()"></h4>
                </div>
            </div>
        </table>

        <script type="text/javascript">
            function Register() {
                var id = document.getElementById("id").value;
                var username = document.getElementById("username").value;
                var password = document.getElementById("password").value;
                jQuery.ajax({
                    url: "<%=request.getContextPath()%>/RegisterServlet", //这里是传入的 servlet  
                    type: "post",
                    data: {
                        "id": id,
                        "username": username,
                        "password": password,
                        "usertype": "manager"
                    }, //这里是传进去的参数  
                    dataType: "text",
                    success: function(data) {
                        var infos = data.split(":")[1];
                        if (infos != "null") {
                            var info = infos.split(",");
                            sessionStorage.id = info[0];
                            sessionStorage.username = info[1];
                            sessionStorage.sex = info[2];
                            sessionStorage.classname = info[3];
                            window.location.href = "http://localhost:5716/SigningSystemServer/Login.jsp";
                        }
                    }
                });
            }
        </script>
    </body>

    </html>