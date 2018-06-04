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
 * Servlet implementation class MajorClassServlet
 */
@WebServlet("/MajorClassServlet")
public class MajorClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static int MODIFY_SUCCEEDED = 12;
    static int MODIFY_FAILED = 13;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MajorClassServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);    
        response.getWriter().append("Served at: ").append(request.getContextPath());  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String res; 
        // 输出流    
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
    	response.setCharacterEncoding("UTF-8"); 
    	response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();    
        request.setCharacterEncoding("UTF-8");
        // 设置编码形式    
        // 获取传入数据    
        String id = request.getParameter("id");
        String majororclass = request.getParameter("majororclass"); 
        String addordel = request.getParameter("addordel");  
        String majorName = request.getParameter("majorName");
        String className = request.getParameter("className");
        String usertype = request.getParameter("usertype");
        if(majororclass.equals("major")) {	
        	System.out.println("user: " + id + " --try to " + addordel + " " + majororclass + " " + majorName);  
        }else {
        	System.out.println("user: " + id + " --try to " + addordel + " " + majororclass + " " + className);  
        }
        
        // 访问数据库    
        int value = MyService.ModifyMajorOrClass(id, usertype, addordel, majororclass, majorName, className);
        if(value == MODIFY_SUCCEEDED) {
        	System.out.println("MajorClassServlet servlet response: MODIFY_SUCCEEDED"); 
        	res = "SUCCEEDED";
        }else {
        	System.out.println("MajorClassServlet servlet response: MODIFY_FAILED"); 
        	res = "FAILED";
        }
        out.print(res);
        out.flush();  
        out.close(); 
	}

}
