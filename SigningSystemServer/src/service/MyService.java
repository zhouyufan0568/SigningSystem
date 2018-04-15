package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import db.DBManager;
import global.CheckInfo;

public class MyService {
	static PreparedStatement preparedStatement = null;    
    static ResultSet resultSet = null;    
    static int updateRowCnt = 0;  
    static int LOGIN_FAILED = 0;  
    static int LOGIN_SUCCEEDED = 1;  
    static int REGISTER_FAILED = 2;  
    static int REGISTER_SUCCEEDED = 3;
    static int SIGN_FAILED = 4;  
    static int SIGN_SUCCEEDED = 5;
        
    public static int login(String id, String password) {    
        int result = LOGIN_FAILED;    
        resultSet = null;  
        // 执行 SQL 查询语句    
        String sql = "select * from user_list where id='" + id +"'";    
        try {       
            Connection con = DBManager.getConnection();    
            preparedStatement = con.prepareStatement(sql);    
            try{    
                resultSet = preparedStatement.executeQuery();    
                // 查询结果    
                if(resultSet.next()){      
                    if(resultSet.getString("password").equals(password)){  
                        result = LOGIN_SUCCEEDED;  
                        System.out.println("id:" + id   
                            + " username:" + resultSet.getString("username")  
                            + " --login");  
                    }  
                }    
                preparedStatement.close();    
                con.close();    
            }catch(Exception e){    
                e.printStackTrace();    
            }    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        System.out.println("login service result:" + result);  
        return result;    
    }    
      
    public static int register(String id, String username, String password) {    
        int result = REGISTER_FAILED;    
        updateRowCnt = 0;  
        // 执行 SQL 插入语句    
        String sql = "insert into user_list(`id`, `username`,`password`) values ('"  
                + id + "', '" + username + "', '" + password + "')";  
        try {       
            Connection con = DBManager.getConnection();    
            preparedStatement = con.prepareStatement(sql);    
            try{    
                updateRowCnt = preparedStatement.executeUpdate();
                // 插入结果    
                if(updateRowCnt != 0){      
                        result = REGISTER_SUCCEEDED;  
                        System.out.println("id:" + id   
                            + " username:" + username
                            + " --register");  
                }    
                preparedStatement.close();    
                con.close();    
            }catch(Exception e){    
                e.printStackTrace();    
            }    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        System.out.println("register service result:" + result);  
        return result;    
    }  
    
    public static int sign(String id, String time, String sign) {
    	int result = SIGN_FAILED;
    	updateRowCnt = 0;
    	String date = time.substring(0, time.indexOf(" "));
    	String datetime = time.substring(time.indexOf(" ")+1);
    	// 执行 SQL 插入语句    
    	String sql = null;
    	if(sign.equals("signin")){
    		sql = "insert into staff_check(id,date,signin,signout) values('"+id+"','"+date+"','"+datetime+"','')";
    	}else if(sign.equals("signout")) {
    		sql = "insert into staff_check(id,date,signin,signout) values('"+id+"','"+date+"','','"+datetime+"')" + " ON DUPLICATE KEY update " + sign + "='" + datetime + "'";
    	}
        try {       
            Connection con = DBManager.getConnection();    
            preparedStatement = con.prepareStatement(sql);    
            try{    
                updateRowCnt = preparedStatement.executeUpdate();
                // 插入结果    
                if(updateRowCnt != 0){      
                        result = SIGN_SUCCEEDED;  
                        System.out.println("id:" + id + " " + datetime   
                            + " --" + sign);  
                }else {
                	System.out.println("id:" + id + " -- mysql: update 0 raw");
                }
                preparedStatement.close();    
                con.close();    
            } catch(MySQLIntegrityConstraintViolationException e) {
            	System.out.println("id:" + id + " --modify mysql fail: Duplicate entry '123123-2018/3/15' for key 'PRIMARY'");
            }catch(Exception e){    
                e.printStackTrace();    
            }    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        System.out.println("sign service result:" + result);  
        return result;    
    }

	public static List<CheckInfo> search(String date) {
		// TODO Auto-generated method stub
		List<CheckInfo> list= new ArrayList<CheckInfo>();
		resultSet = null;  
		// 执行 SQL 插入语句    
    	String sql = "select * from staff_check where date ='" + date + "'";  
        try {       
            Connection con = DBManager.getConnection();    
            preparedStatement = con.prepareStatement(sql);    
            try{    
                resultSet = preparedStatement.executeQuery();    
                // 查询结果    
                while(resultSet.next()){      
                    list.add(new CheckInfo(resultSet.getString("id"),resultSet.getString("date"),resultSet.getString("signin"),resultSet.getString("signout")));
                    System.out.println("Search 1 raw:" + resultSet.getString("id") + " " + resultSet.getString("date") + " " + resultSet.getString("signin") + " " + resultSet.getString("signout"));
                }    
                preparedStatement.close();    
                con.close();    
            }catch(Exception e){    
                e.printStackTrace();    
            }    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
		return list;
	}
}
