package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import global.CheckInfo;
import service.MyService;

@WebServlet("/SendInfoServlet")
public class SendInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	static int SETINFO_SUCCEEDED = 10;
    static int SETINFO_FAILED = 11;
	
	public SendInfoServlet() {  
        super();  
    }  
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {  
        doPost(request, response);    
        response.getWriter().append("Served at: ").append(request.getContextPath());  
    }  
      
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException { 
    	String res; 
        // 输出流    
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
    	response.setCharacterEncoding("UTF-8"); 
    	response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();    
        request.setCharacterEncoding("UTF-8");
        // 设置编码形式    
        // 获取传入数据    
        String username = request.getParameter("username");  
        String sex = request.getParameter("sex"); 
        String classname = request.getParameter("classname"); 
        String id = request.getParameter("id");  
        String usertype = request.getParameter("usertype");
        System.out.println("user: " + id + " --try to send info");  
        
        // 访问数据库    
        int value = MyService.setInfo(id, usertype, username, sex, classname);
        if(value == SETINFO_SUCCEEDED) {
        	System.out.println("Sendinfo servlet response: SETINFO_SUCCEEDED"); 
        	res = "SUCCEEDED";
        }else {
        	System.out.println("Sendinfo servlet response: SETINFO_FAILED"); 
        	res = "FAILED";
        }
        out.print(res);
        out.flush();  
        out.close(); 
    }  

}