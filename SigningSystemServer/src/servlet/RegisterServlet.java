package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MyService;

/**
 * Servlet implementation class RegisterrServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  
    static int LOGIN_FAILED = 0;  
    static int LOGIN_SUCCEEDED = 1;  
    static int REGISTER_FAILED = 2;  
    static int REGISTER_SUCCEEDED = 3;  
         
    public RegisterServlet() {  
        super();  
    }  
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {  
        doPost(request, response);    
        response.getWriter().append("Served at: ").append(request.getContextPath());  
    }  
      
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {
        // 返回字符串    
        String res = null;    
        // 输出流    
        response.setHeader("Content-type", "text/html;charset=UTF-8");
    	response.setCharacterEncoding("UTF-8"); 
    	response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();    
        // 设置编码形式    
        request.setCharacterEncoding("utf-8");      
        // 获取传入数据    
        String usertype = request.getParameter("usertype");
        String id = request.getParameter("id");
        String username = request.getParameter("username");  
        String password = request.getParameter("password");  
        String sex = request.getParameter("sex");  
        System.out.println("id:" + id + " --try to register");  
    
        // 访问数据库    
        res = MyService.register(id, username, password, sex, usertype);  
        if(res != null) {    
        	System.out.println("login success");    
        }else {
        	System.out.println("login failed"); 
        }
        res = "loginInfo:" + res;
        System.out.println("register servlet responseMsg:" + res);    
        out.print(res);  
    }  

}
