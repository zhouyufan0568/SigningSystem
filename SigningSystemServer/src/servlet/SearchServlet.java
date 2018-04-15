package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import global.CheckInfo;
import service.MyService;
import sun.misc.CharacterEncoder;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	
	public SearchServlet() {  
        super();  
    }  
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {  
        doPost(request, response);    
        response.getWriter().append("Served at: ").append(request.getContextPath());  
    }  
      
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException { 
    	List<CheckInfo> list = null; 
        // �����    
        PrintWriter out = response.getWriter();    
        // ���ñ�����ʽ    
        request.setCharacterEncoding("utf-8");      
        // ��ȡ��������    
        String date = request.getParameter("date");    
        System.out.println("date:" + date + " --try to search check info");  
        
        // �������ݿ�    
        list = MyService.search(date);
        JSONObject json = new JSONObject();
        if(!list.isEmpty()) {
        	System.out.println("search servlet response: NOT NULL"); 
        	String checkInfo = new ObjectMapper().writeValueAsString(list);
            json.put("checkInfo", checkInfo);  
            System.out.println("checkInfo:" + checkInfo);
        }else {
        	System.out.println("search servlet response: NULL"); 
        }
        out.print(json);
        out.flush();  
        out.close(); 
    }  
}