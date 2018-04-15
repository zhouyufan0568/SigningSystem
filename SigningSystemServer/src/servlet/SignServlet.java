package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MyService;

public class SignServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  
    static int SIGN_FAILED = 4;  
    static int SIGN_SUCCEEDED = 5;
         
    public SignServlet() {  
        super();  
    }  
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {  
        doPost(request, response);    
        response.getWriter().append("Served at: ").append(request.getContextPath());  
    }  
      
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {  
        // �����ַ���    
        String responseMsg="FAILED";    
        // �����    
        PrintWriter out = response.getWriter();    
        // ���ñ�����ʽ    
        request.setCharacterEncoding("utf-8");      
        // ��ȡ��������    
        String id = request.getParameter("id");    
        String time = request.getParameter("time"); 
        String sign = request.getParameter("sign"); 
        System.out.println("id:" + id + " --try to sign");  
        
        // �������ݿ�    
        int value = MyService.sign(id, time, sign);  
        if(value == SIGN_SUCCEEDED) {    
            responseMsg = "SUCCEEDED";    
        }  
        System.out.println("sign servlet responseMsg:" + responseMsg);    
        out.print(responseMsg);  
    }  
}