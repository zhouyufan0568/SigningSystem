<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
    <%@ page import="java.io.*,java.util.*" %>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
            <title>主页</title>
            <script>
                window.onload = function() {
                    if (sessionStorage.id == null) {
                        window.location.href = "http://localhost:5716/SigningSystemServer/Login.jsp";
                    }
                }
            </script>
        </head>

        <body>
            <div>
                <button type="button" onclick="window.open('http://localhost:5716/SigningSystemServer/ClassSchedule.jsp')">课程表</button>
                <button type="button" onclick="window.open('http://localhost:5716/SigningSystemServer/CheckInfo.jsp')">考勤查询</button>
            </div>
        </body>

        </html>