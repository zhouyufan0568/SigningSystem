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
 * Servlet implementation class SaveCrouseServlet
 */
@WebServlet("/SaveCrouseServlet")
public class SaveCrouseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static int SAVECROUSE_FAILED = 6;  
    static int SAVECROUSE_SUCCEEDED = 7;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveCrouseServlet() {
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
		// 返回字符串    
        String responseMsg="FAILED";    
        // 输出流    
        PrintWriter out = response.getWriter();    
        // 设置编码形式    
        request.setCharacterEncoding("utf-8");      
        // 获取传入数据    
        String id = request.getParameter("id");
        String classname = request.getParameter("classname");
        String crouse = request.getParameter("crouse"); 
        System.out.println("id:" + id + " --try to modify class schedule:" + classname);  
        System.out.println("crouse: " + crouse);  
        // 访问数据库    
        int value = MyService.saveCrouse(classname, crouse);  
        if(value == SAVECROUSE_SUCCEEDED) {    
            responseMsg = "SUCCEEDED";    
        }  
        System.out.println("SaveCrouse servlet responseMsg:" + responseMsg);    
        out.print(responseMsg);  
	}

}
