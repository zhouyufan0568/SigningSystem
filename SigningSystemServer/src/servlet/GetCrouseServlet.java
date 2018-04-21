package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import global.CheckInfo;
import global.ScheduleInfo;
import service.MyService;

/**
 * Servlet implementation class GetCrouseServlet
 */
@WebServlet("/GetCrouseServlet")
public class GetCrouseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static int GETCROUSE_FAILED = 8;  
    static int GETCROUSE_SUCCEEDED = 9;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCrouseServlet() {
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
		List<ScheduleInfo> list = null; 
        // 输出流    
		response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();    
        // 设置编码形式    
        request.setCharacterEncoding("utf-8");
        // 获取传入数据    
        String id = request.getParameter("id");
        String classname = request.getParameter("classname"); 
        System.out.println("id:" + id + " --try to get class schedule:" + classname);  
        
        // 访问数据库    
        list = MyService.getCrouse(classname);  
        JSONObject json = new JSONObject();
        if(!list.isEmpty()) {
        	System.out.println("GetCrouse servlet response: NOT NULL"); 
        	String scheduleInfo = new ObjectMapper().writeValueAsString(list);
            json.put("scheduleInfo", scheduleInfo);  
            System.out.println("scheduleInfo:" + scheduleInfo);
        }else {
        	System.out.println("GetCrouse servlet response: NULL"); 
        }
        System.out.println("json:" + json);
        out.print(json);
        out.flush();  
        out.close(); 
	}

}
