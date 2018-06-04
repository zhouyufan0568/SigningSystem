<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
    <%@ page import="java.io.*,java.util.*" %>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
            <title>课程表</title>
            <LINK href="./lib/easyui.css" rel="stylesheet" type="text/css">
            <LINK href="./lib/icon.css" rel="stylesheet" type="text/css">
            <LINK href="./lib/demo.css" rel="stylesheet" type="text/css">
            <SCRIPT src="./lib/jquery-1.11.3.min.js" type="text/javascript"></SCRIPT>
            <SCRIPT src="./lib/jquery.easyui.min.js" type="text/javascript"></SCRIPT>
            <script>
                window.onload = function() {
                    if (sessionStorage.id == null) {
                        window.location.href = "http://localhost:5716/SigningSystemServer/Login.jsp";
                    }
                    GetClass();
                    ChangeColor();
                }

                function GetClass() {
                    jQuery.ajax({
                        url: "<%=request.getContextPath()%>/SearchInfoServlet", //这里是传入的 servlet  
                        type: "post",
                        data: {
                            info: "classname",
                            id: sessionStorage.id,
                            usertype: "manager",
                        }, //这里是传进去的参数  
                        dataType: "text",
                        success: function(data) {
                            setClass(data);
                            var childs = document.getElementById("classlist").childNodes;
                            for (var i = 0; i != childs.length; i++) {
                                if (childs[i].innerHTML == sessionStorage.preSelected) {
                                    document.getElementById("classlist").selectedIndex = i;
                                    break;
                                }
                            }
                            GetMajorInfo();
                        }
                    });
                }

                function setClass(data) {
                    var infos = data.split(":")[1];
                    var info = infos.split(",");
                    console.log(info);
                    var list = document.getElementById("classlist");
                    for (var t in info) {
                        var option = document.createElement("option");
                        option.innerHTML = info[t];
                        list.appendChild(option);
                    }
                }

                function GetMajorInfo() {
                    GetSchedule();
                }

                function GetMajorCrouse() {
                    var classlist = document.getElementById("classlist");
                    var index = classlist.selectedIndex; // 选中索引
                    var classname = classlist.options[index].text;
                    jQuery.ajax({
                        url: "<%=request.getContextPath()%>/SearchInfoServlet", //这里是传入的 servlet  
                        type: "post",
                        data: {
                            info: "majorclass",
                            major: classname,
                            id: sessionStorage.id,
                            usertype: "manager",
                        }, //这里是传进去的参数  
                        dataType: "text",
                        success: function(data) {
                            setCrouse(data);
                        }
                    });
                }

                function setCrouse(data) {
                    var infos = data.split(":")[1];
                    var info = infos.split(",");
                    console.log(info);
                    var list = document.getElementById("crouselist");
                    console.log("crouselist length： " + list.childNodes.length);
                    var childs = list.childNodes;
                    for (var i = childs.length - 1; i >= 0; i--) {
                        list.removeChild(childs[i]);
                    }
                    console.log("crouselist length： " + list.childNodes.length);
                    for (var t in info) {
                        var div = document.createElement("div");
                        div.classList.add("item");
                        div.innerHTML = info[t];
                        var $obj = $(div);
                        $obj.draggable({
                            revert: true,
                            proxy: 'clone'
                        });
                        var td = document.createElement("td");
                        td.appendChild(div);
                        var tr = document.createElement("tr");
                        tr.appendChild(td);
                        list.appendChild(tr);
                    }
                }

                function GetSchedule() {
                    var classlist = document.getElementById("classlist");
                    var index = classlist.selectedIndex; // 选中索引
                    var classname = classlist.options[index].text;
                    document.getElementById("classname").innerHTML = classname;
                    jQuery.ajax({
                        url: "<%=request.getContextPath()%>/GetCrouseServlet", //这里是传入的 servlet  
                        type: "post",
                        data: {
                            id: sessionStorage.id,
                            classname: classname,
                        }, //这里是传进去的参数  
                        dataType: "json",
                        success: function(data) {
                            GetMajorCrouse();
                            ShowSchedule(data);
                        }
                    });
                }



                function ShowSchedule(data) {
                    console.log(data);
                    var crouse = document.getElementsByClassName("crouse");
                    for (var i in crouse) {
                        crouse[i].textContent = "";
                    }

                    var scheduleInfo = eval(data.scheduleInfo);
                    if (scheduleInfo != undefined) {
                        console.log("scheduleInfo.length info:" + scheduleInfo.length);
                        for (var i in scheduleInfo) {
                            var crouseInfo = scheduleInfo[i];
                            var day = crouseInfo.day;
                            var cindex = crouseInfo.cindex;
                            var cname = crouseInfo.cname;
                            console.log(crouseInfo.day + crouseInfo.cname);
                            crouse[5 * (cindex - 1) + (day - 1)].textContent = cname;
                        }
                    }
                }

                function ChangeColor() {

                    var color = new Array();
                    var bcolor = new Array();
                    for (i = 0; i < $(".crouse").length; i++) {
                        color[i] = "#" + Math.floor(Math.random() * 0xffffff).toString(16);
                        bcolor[i] = "#" + Math.floor(Math.random() * 0xffffff).toString(16);
                    }

                    $(".crouse").each(function() {

                        switch ($(this).text()) {
                            case "高等数学":
                                {
                                    $(this).css({
                                        "color": color[0],
                                        "background": bcolor[0]
                                    });
                                    break;
                                }
                            case "线性代数":
                                {
                                    $(this).css({
                                        "color": color[1],
                                        "background": bcolor[1]
                                    });
                                    break;
                                }
                            case "概率论与数理统计":
                                {
                                    $(this).css({
                                        "color": color[2],
                                        "background": bcolor[2]
                                    });
                                    break;
                                }
                            case "政治":
                                {
                                    $(this).css({
                                        "color": color[3],
                                        "background": bcolor[3]
                                    });
                                    break;
                                }
                            case "数据结构":
                                {
                                    $(this).css({
                                        "color": color[4],
                                        "background": bcolor[4]
                                    });
                                    break;
                                }
                            case "操作系统":
                                {
                                    $(this).css({
                                        "color": color[5],
                                        "background": bcolor[5]
                                    });
                                    break;
                                }
                            case "计算机组成原理":
                                {
                                    $(this).css({
                                        "color": color[6],
                                        "background": bcolor[6]
                                    });
                                    break;
                                }
                            case "计算机网络":
                                {
                                    $(this).css({
                                        "color": color[7],
                                        "background": bcolor[7]
                                    });
                                    break;
                                }
                            case "微机原理":
                                {
                                    $(this).css({
                                        "color": color[8],
                                        "background": bcolor[8]
                                    });
                                    break;
                                }
                            case "C语言":
                                {
                                    $(this).css({
                                        "color": color[9],
                                        "background": bcolor[9]
                                    });
                                    break;
                                }
                            case "Java":
                                {
                                    $(this).css({
                                        "color": color[10],
                                        "background": bcolor[10]
                                    });
                                    break;
                                }
                            case "Python":
                                {
                                    $(this).css({
                                        "color": color[11],
                                        "background": bcolor[11]
                                    });
                                    break;
                                }
                        }
                    });
                }
            </script>
        </head>

        <body>
            <STYLE type="text/css">
                .left {
                    width: 120px;
                    float: left;
                }
                
                .left table {
                    background: #E0ECFF;
                }
                
                .left td {
                    background: #eee;
                }
                
                .right {
                    float: right;
                    width: 570px;
                }
                
                .right table {
                    background: #E0ECFF;
                    width: 100%;
                }
                
                .right td {
                    background: #fafafa;
                    color: #444;
                    text-align: center;
                    padding: 2px;
                }
                
                .right td {
                    background: #E0ECFF;
                }
                
                .right td.drop {
                    background: #fafafa;
                    width: 100px;
                }
                
                .right td.over {
                    background: #FBEC88;
                }
                
                .item {
                    text-align: center;
                    border: 1px solid #499B33;
                    background: #fafafa;
                    color: #444;
                    width: 100px;
                }
                
                .assigned {
                    border: 1px solid #BC2A4D;
                }
                
                .trash {
                    background-color: red;
                }
            </STYLE>

            <div>
                <div style="width:700px;">
                    <div class="left">
                        <select id="classlist" onchange="GetMajorInfo()"></select>
                    </div>
                    <div class="right">
                        <input type="text" id="inputMajor"></input>
                        <button onclick="addMajor()">添加专业</button>
                        <button onclick="removeMajor()">删除专业</button>
                    </div>
                    <div class="right">
                        <input type="text" id="inputClass"></input>
                        <button onclick="addClass()">添加课程</button>
                        <button onclick="removeClass()">删除课程</button>
                    </div>
                </div>
            </div>

            <div style="width:700px;">
                <DIV style="width:700px;float:left;">
                    <DIV class="left">
                        <TABLE>
                            <TBODY id="crouselist"></TBODY>
                        </TABLE>
                    </DIV>
                    <DIV class="right">
                        <table border="1">
                            <TBODY>
                                <caption id="classname">物联网工程课程表</caption>
                                <tr>
                                    <td colspan=2 class="blank">&nbsp;</td>
                                    <td class="title">星期一</td>
                                    <td class="title">星期二</td>
                                    <td class="title">星期三</td>
                                    <td class="title">星期四</td>
                                    <td class="title">星期五</td>
                                </tr>
                                <tr>
                                    <td rowspan=5 class="time">上午</td>
                                    <td class="cindex">1</td>

                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>

                                </tr>
                                <tr>
                                    <td class="cindex">2</td>

                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>


                                </tr>
                                <tr>
                                    <td colspan=6 align="center">课间操</td>
                                </tr>
                                <tr>
                                    <td class="cindex">3</td>

                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>


                                </tr>
                                <tr>
                                    <td class="cindex">4</td>

                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>


                                </tr>
                                <tr>
                                    <td colspan=7 align="center">午间休息</td>
                                </tr>
                                <tr>
                                    <td rowspan=4 class="time">下午</td>
                                    <td class="cindex">5</td>

                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>


                                </tr>
                                <tr>
                                    <td class="cindex">6</td>

                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>


                                </tr>
                                <tr>
                                    <td class="cindex">7</td>

                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>


                                </tr>
                                <tr>
                                    <td class="cindex">8</td>

                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>


                                </tr>
                                <tr>
                                    <td colspan=7 align="center">晚间休息</td>
                                </tr>
                                <tr>
                                    <td rowspan=2 class="time">晚上</td>
                                    <td class="cindex">9</td>

                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>


                                </tr>
                                <tr>
                                    <td class="cindex">10</td>

                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>
                                    <td class="crouse drop"></td>


                                </tr>
                            </TBODY>
                        </table>

                    </DIV>

                </DIV>

                <div>
                    <button style="width:200px" onclick="SaveCrouse()">保存课程表</button>
                </div>

                <hr size="1" width="20%" color="black" noshade="noshade" align="left" />
                <div>
                    <input id="Days" list="day" />
                    <datalist id="day">
                <option value="星期1"></option>
                <option value="星期2"></option>
                <option value="星期3"></option>
                <option value="星期4"></option>
                <option value="星期5"></option>
            </datalist>

                    <input id="Crouses" list="crouse" />
                    <datalist id="crouse">
                <option value="第1节"></option>
                <option value="第2节"></option>
                <option value="第3节"></option>
                <option value="第4节"></option>
                <option value="第5节"></option>
                <option value="第6节"></option>
            </datalist>
                    <button onclick="GetCrouse()">Search</button>
                    <div id="res"></div>
                </div>
                <hr size="1" width="20%" color="black" noshade="noshade" align="left" />
                <tr>
                    <input type="button" onclick="ChangeColor()" value="Change color of table">
                </tr>
        </body>
        <script type="text/javascript">
            function GetCrouse() {
                var crou = String(document.getElementById('Crouses').value);
                var da = String(document.getElementById('Days').value);
                var w = parseInt(da.substr(da.indexOf("期") + 1, 1));
                var n = parseInt(crou.substr(crou.indexOf("第") + 1, 1));
                var result = document.getElementsByClassName("crouse")[5 * (n - 1) + (w - 1)].textContent;
                document.getElementById("res").innerHTML = "课程：" + result;
            }

            function SaveCrouse() {
                if ($('.crouse .item').length == 0) {
                    return;
                }
                var crouse = {};
                var classname = document.getElementById("classname").innerText;
                $('.crouse .item').each(function() {
                    var t = $(this).parent().parent().children(".cindex");
                    var day = $(this).parent().index() - t.index();
                    var cindex = t.text();
                    var cname = $(this).text();
                    var crou = day + " " + cindex;
                    crouse[crou] = cname;
                });
                var crousejson = JSON.stringify(crouse);
                jQuery.ajax({
                    url: "<%=request.getContextPath()%>/SaveCrouseServlet", //这里是传入的 servlet  
                    type: "post",
                    data: {
                        id: sessionStorage.id,
                        classname: classname,
                        crouse: crousejson
                    }, //这里是传进去的参数  
                    dataType: "text",
                    success: function(data) {
                        if (data == "SUCCEEDED") {
                            var sel = document.getElementById("classlist");
                            var index = sel.selectedIndex;
                            sessionStorage.preSelected = sel.options[index].text;
                            window.location.reload();
                        }
                    }
                });
            }

            function addMajor() {
                var major = document.getElementById("inputMajor").value;
                jQuery.ajax({
                    url: "<%=request.getContextPath()%>/MajorClassServlet", //这里是传入的 servlet  
                    type: "post",
                    data: {
                        id: sessionStorage.id,
                        usertype: "manager",
                        addordel: "add",
                        majororclass: "major",
                        majorName: major
                    }, //这里是传进去的参数  
                    dataType: "text",
                    success: function(data) {
                        if (data == "SUCCEEDED") {
                            var list = document.getElementById("classlist");
                            var option = document.createElement("option");
                            option.innerHTML = major;
                            list.appendChild(option);
                        }
                    }
                });
            }

            function removeMajor() {
                var major = document.getElementById("inputMajor").value;
                jQuery.ajax({
                    url: "<%=request.getContextPath()%>/MajorClassServlet", //这里是传入的 servlet  
                    type: "post",
                    data: {
                        id: sessionStorage.id,
                        usertype: "manager",
                        addordel: "del",
                        majororclass: "major",
                        majorName: major
                    }, //这里是传进去的参数  
                    dataType: "text",
                    success: function(data) {
                        if (data == "SUCCEEDED") {
                            var list = document.getElementById("classlist");
                            for (var i = 0; i != list.childNodes.length; i++) {
                                if (list.childNodes[i].innerHTML == major) {
                                    list.removeChild(list.childNodes[i])
                                    break;
                                }
                            }
                        }
                    }
                });
            }

            function addClass() {
                var major = document.getElementById("classname").innerHTML;
                var classname = document.getElementById("inputClass").value;
                jQuery.ajax({
                    url: "<%=request.getContextPath()%>/MajorClassServlet", //这里是传入的 servlet  
                    type: "post",
                    data: {
                        id: sessionStorage.id,
                        usertype: "manager",
                        addordel: "add",
                        majororclass: "class",
                        majorName: major,
                        className: classname
                    }, //这里是传进去的参数  
                    dataType: "text",
                    success: function(data) {
                        if (data == "SUCCEEDED") {
                            var list = document.getElementById("crouselist");
                            var div = document.createElement("div");
                            div.classList.add("item");
                            div.innerHTML = classname;
                            var $obj = $(div);
                            $obj.draggable({
                                revert: true,
                                proxy: 'clone'
                            });
                            var td = document.createElement("td");
                            td.appendChild(div);
                            var tr = document.createElement("tr");
                            tr.appendChild(td);
                            list.appendChild(tr);
                        }
                    }
                });
            }

            function removeClass() {
                var major = document.getElementById("classname").innerHTML;
                var classname = document.getElementById("inputClass").value;
                jQuery.ajax({
                    url: "<%=request.getContextPath()%>/MajorClassServlet", //这里是传入的 servlet  
                    type: "post",
                    data: {
                        id: sessionStorage.id,
                        usertype: "manager",
                        addordel: "del",
                        majororclass: "class",
                        majorName: major,
                        className: classname
                    }, //这里是传进去的参数  
                    dataType: "text",
                    success: function(data) {
                        if (data == "SUCCEEDED") {
                            var list = document.getElementById("crouselist");
                            for (var i = 0; i != list.children.length; i++) {
                                if (list.children[i].children[0].children[0].innerHTML == classname) {
                                    list.removeChild(list.children[i]);
                                    break;
                                }
                            }
                        }
                    }
                });
            }
        </script>
        <SCRIPT>
            $(function() {
                $('.left .item').draggable({
                    revert: true,
                    proxy: 'clone'
                });
                $('.right td.drop').droppable({
                    accept: '.item',
                    onDragEnter: function() {
                        $(this).addClass('over');
                    },
                    onDragLeave: function() {
                        $(this).removeClass('over');
                    },
                    onDrop: function(e, source) {
                        $(this).removeClass('over');
                        if ($(source).hasClass('assigned')) {
                            $(this).append(source);
                        } else {
                            var c = $(source).clone().addClass('assigned');
                            $(this).empty().append(c);
                            c.draggable({
                                revert: true
                            });
                        }
                    }
                });
                $('.left').droppable({
                    accept: '.assigned',
                    onDragEnter: function(e, source) {
                        $(source).addClass('trash');
                    },
                    onDragLeave: function(e, source) {
                        $(source).removeClass('trash');
                    },
                    onDrop: function(e, source) {
                        $(source).remove();
                    }
                });
            });
        </SCRIPT>

        </html>