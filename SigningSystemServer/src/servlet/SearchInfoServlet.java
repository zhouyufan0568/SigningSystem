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

@WebServlet("/SearchInfoServlet")
public class SearchInfoServlet extends HttpServlet {
private static final long serialVersionUID = 1L;  
	
	public SearchInfoServlet() {  
        super();  
    }  
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {  
        doPost(request, response);    
        response.getWriter().append("Served at: ").append(request.getContextPath());  
    }  
      
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException { 
    	String res = null; 
        // 输出流    
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
    	response.setCharacterEncoding("UTF-8"); 
    	response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();    
        // 设置编码形式    
        // 获取传入数据    
        String info = request.getParameter("info");  
        String major = request.getParameter("major"); 
        String id = request.getParameter("id");  
        String usertype = request.getParameter("usertype");
        System.out.println("user: "+id+" info:" + info + " --try to search info");  
        
        // 访问数据库    
        res = MyService.searchInfo(id, usertype, info, major);
        if(res != null) {
        	System.out.println("searchinfo servlet response: NOT NULL"); 
        	res = info + ":" + res;
            System.out.println(info + ":" + res);
        }else {
        	System.out.println("search servlet response: NULL"); 
        }
        out.print(res);
        out.flush();  
        out.close(); 
    }  

}
