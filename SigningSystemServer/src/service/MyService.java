package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import db.DBManager;
import global.CheckInfo;
import global.ScheduleInfo;

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
	static int SAVECROUSE_FAILED = 6;
	static int SAVECROUSE_SUCCEEDED = 7;
	static int GETCROUSE_FAILED = 8;  
    static int GETCROUSE_SUCCEEDED = 9;
    static int SETINFO_SUCCEEDED = 10;
    static int SETINFO_FAILED = 11;

	public static String login(String id, String password, String usertype) {
		int result = LOGIN_FAILED;
		resultSet = null;
		// 执行 SQL 查询语句
		String sql = null;
		switch (usertype) {
		case "manager":
			sql = "select * from manager_list where id='" + id + "'";
			break;
		case "user":
			sql = "select * from user_list where id='" + id + "'";
			break;
		default:
			sql = "select * from user_list where id='" + id + "'";
			break;
		}
		try {
			Connection con = DBManager.getConnection();
			preparedStatement = con.prepareStatement(sql);
			try {
				resultSet = preparedStatement.executeQuery();
				// 查询结果
				if (resultSet.next()) {
					if (resultSet.getString("password").equals(password)) {
						result = LOGIN_SUCCEEDED;
						System.out.println(usertype + " " + "id:" + id + " username:" + resultSet.getString("username")
								+ " --login");
					}
				}
				preparedStatement.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("login service result:" + result);
		
		String res = null;
		if(result == LOGIN_SUCCEEDED) {
			res = getInfo(id, usertype);
		}
		return res;
	}

	public static String register(String id, String username, String password, String sex, String usertype) {
		int result = REGISTER_FAILED;
		updateRowCnt = 0;
		// 执行 SQL 插入语句
		String sql = null;
		switch (usertype) {
		case "manager":
			sql = "insert into manager_list(`id`, `username`,`password`, `sex`) values ('" + id + "', '" + username + "', '"
					+ password  + "', '" + sex + "')";
			break;

		default:
			sql = "insert into user_list(`id`, `username`,`password`, `sex`) values ('" + id + "', '" + username + "', '"
					+ password  + "', '" + sex + "')";
			break;
		}
		try {
			Connection con = DBManager.getConnection();
			preparedStatement = con.prepareStatement(sql);
			try {
				updateRowCnt = preparedStatement.executeUpdate();
				// 插入结果
				if (updateRowCnt != 0) {
					result = REGISTER_SUCCEEDED;
					System.out.println(usertype + " " + "id:" + id + " username:" + username + " sex:" + sex + " --register");
				}
				preparedStatement.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("register service result:" + result);
		
		String res = null;
		if(result == REGISTER_SUCCEEDED) {
			res = getInfo(id, usertype);
		}
		return res;
	}

	public static int sign(String id, String time, String sign) {
		int result = SIGN_FAILED;
		updateRowCnt = 0;
		String date = time.substring(0, time.indexOf(" "));
		String datetime = time.substring(time.indexOf(" ") + 1);
		// 执行 SQL 插入语句
		String sql = null;
		if (sign.equals("signin")) {
			sql = "insert into staff_check(id,date,signin,signout) values('" + id + "','" + date + "','" + datetime
					+ "','')";
		} else if (sign.equals("signout")) {
			sql = "insert into staff_check(id,date,signin,signout) values('" + id + "','" + date + "','','" + datetime
					+ "')" + " ON DUPLICATE KEY update " + sign + "='" + datetime + "'";
		}
		try {
			Connection con = DBManager.getConnection();
			preparedStatement = con.prepareStatement(sql);
			try {
				updateRowCnt = preparedStatement.executeUpdate();
				// 插入结果
				if (updateRowCnt != 0) {
					result = SIGN_SUCCEEDED;
					System.out.println("id:" + id + " " + datetime + " --" + sign);
				} else {
					System.out.println("id:" + id + " -- mysql: update 0 raw");
				}
				preparedStatement.close();
				con.close();
			} catch (MySQLIntegrityConstraintViolationException e) {
				System.out.println(
						"id:" + id + " --modify mysql fail: Duplicate entry '123123-2018/3/15' for key 'PRIMARY'");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("sign service result:" + result);
		return result;
	}

	public static List<CheckInfo> search(String date) {
		// TODO Auto-generated method stub
		List<CheckInfo> list = new ArrayList<CheckInfo>();
		resultSet = null;
		// 执行 SQL 插入语句
		String sql = "select * from staff_check where date ='" + date + "'";
		try {
			Connection con = DBManager.getConnection();
			preparedStatement = con.prepareStatement(sql);
			try {
				resultSet = preparedStatement.executeQuery();
				// 查询结果
				while (resultSet.next()) {
					list.add(new CheckInfo(resultSet.getString("id"), resultSet.getString("date"),
							resultSet.getString("signin"), resultSet.getString("signout")));
					System.out.println("Search 1 raw:" + resultSet.getString("id") + " " + resultSet.getString("date")
							+ " " + resultSet.getString("signin") + " " + resultSet.getString("signout"));
				}
				preparedStatement.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static int saveCrouse(String classname, String crouse) {
		// TODO Auto-generated method stub
		int result = SAVECROUSE_SUCCEEDED;
		String sql = null;
		JSONObject json = new JSONObject(crouse);
		Iterator iterator = json.keys();
		try {
			Connection con = DBManager.getConnection();

			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				int day = Integer.parseInt(key.substring(0, key.indexOf(" ")));
				int cindex = Integer.parseInt(key.substring(key.indexOf(" ") + 1));
				sql = "insert into class_schedule(class,day,cindex,cname) values('" + classname + "'," + day + ","
						+ cindex + ",'" + json.getString(key) + "')" + " ON DUPLICATE KEY update cname='"
						+ json.getString(key) + "'";
				System.out.println(sql);
				preparedStatement = con.prepareStatement(sql);
				updateRowCnt = preparedStatement.executeUpdate();
				// 插入结果
				if (updateRowCnt != 0) {
					System.out.println("class schedule:" + classname + " -- has been modified");
				} else {
					result = SAVECROUSE_FAILED;
					System.out.println("class schedule:" + classname + " -- mysql: update 0 raw");
				}
			}
			preparedStatement.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static List<ScheduleInfo> getCrouse(String classname) {
		// TODO Auto-generated method stub
		List<ScheduleInfo> list = new ArrayList<ScheduleInfo>();
		resultSet = null;
		// 执行 SQL 插入语句
		String sql = "select * from class_schedule where class ='" + classname + "'";
		try {
			Connection con = DBManager.getConnection();
			preparedStatement = con.prepareStatement(sql);
			try {
				resultSet = preparedStatement.executeQuery();
				// 查询结果
				while (resultSet.next()) {
					list.add(new ScheduleInfo(resultSet.getString("class"), resultSet.getInt("day"),
							resultSet.getInt("cindex"), resultSet.getString("cname")));
					System.out.println("Search 1 raw:" + resultSet.getString("class") + " " + resultSet.getInt("day")
							+ " " + resultSet.getInt("cindex") + " " + resultSet.getString("cname"));
				}
				preparedStatement.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String searchInfo(String id, String usertype, String info) {
		// TODO Auto-generated method stub
		StringBuilder res = new StringBuilder();
		resultSet = null;
		// 执行 SQL 插入语句
		String sql = null;
		switch (info) {
			case "classname":{
				sql = "select distinct class from class_schedule";
				break;
			}
			case "username":{
				if(usertype.equals("user")) {					
					sql = "select username from user_list where id='" + id + "'";
				}else {
					sql = "select username from manager_list where id='" + id + "'";
				}
				break;
			}
			case "myclass":{
				if(usertype.equals("user")) {					
					sql = "select class from user_list where id='" + id + "'";
				}else {
					sql = "select class from manager_list where id='" + id + "'";
				}
				break;
			}
			default:{
				sql = "select distinct class from class_schedule";
				break;
			}
		}
		try {
			Connection con = DBManager.getConnection();
			preparedStatement = con.prepareStatement(sql);
			try {
				resultSet = preparedStatement.executeQuery();
				// 查询结果
				while (resultSet.next()) {
					switch (info) {
						case "classname":{
							res.append(resultSet.getString("class")+",");
							System.out.println("Search 1 raw: classname: " + resultSet.getString("class"));
							break;
						}
						case "username":{
							res.append(resultSet.getString("username")+",");
							if(usertype.equals("user")) {					
								System.out.println("Search 1 raw: user id: " + id + " username: " + resultSet.getString("username"));
							}else {
								System.out.println("Search 1 raw: manager id: " + id + " username: " + resultSet.getString("username"));
							}
							break;
						}
						case "myclass":{
							res.append(resultSet.getString("class")+",");
							if(usertype.equals("user")) {					
								System.out.println("Search 1 raw: user id: " + id + " class: " + resultSet.getString("class"));
							}else {
								System.out.println("Search 1 raw: manager id: " + id + " class: " + resultSet.getString("class"));
							}
							break;
						}
						default:
							break;
					}
				}
				res.setLength(res.length()-1);
				System.out.println("SearchInfo res: "+res.toString());
				preparedStatement.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res.toString();
	}

	public static int setInfo(String id, String usertype, String username, String sex, String classname) {
		// TODO Auto-generated method stub
		int result = SETINFO_SUCCEEDED;
		String sql = null;
		try {
			Connection con = DBManager.getConnection();
			if(usertype.equals("user")) {
				sql = "update user_list set username=?,sex=?,class=? where id=?";
			}else {
				sql = "update manager_list set username=?,sex=?,class=? where id=?";
			}
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, sex);
			preparedStatement.setString(3, classname);
			preparedStatement.setString(4, id);
			updateRowCnt = preparedStatement.executeUpdate();
			System.out.println(sql);
			// 插入结果
			if (updateRowCnt != 0) {
				System.out.println(usertype + " " + id + " -- has been modified");
			} else {
				result = SETINFO_FAILED;
				System.out.println("Update failed -- mysql: update 0 raw");
			}
			preparedStatement.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public static String getInfo(String id, String usertype) {
		// TODO Auto-generated method stub
		String res = null;
		resultSet = null;
		// 执行 SQL 插入语句
		String sql = null;
		switch (usertype) {
		case "user":{
			sql = "select id,username,sex,class from user_list where id='" + id + "'";
			break;
		}
		case "manager":{
			sql = "select id,username,sex,class from manager_list where id='" + id + "'";
			break;
		}
		default:
			break;
		}
		try {
			Connection con = DBManager.getConnection();
			preparedStatement = con.prepareStatement(sql);
			try {
				resultSet = preparedStatement.executeQuery();
				// 查询结果
				while (resultSet.next()) {
					res = resultSet.getString("id") + "," + resultSet.getString("username") + "," + resultSet.getString("sex") + "," + resultSet.getString("class");
					if(usertype.equals("user")) {
						System.out.println("Search 1 raw: user " + id + " info: " + res);
					}else {
						System.out.println("Search 1 raw: manager " + id + " info: " + res);
					}
				}
				System.out.println("SearchInfo res: "+res);
				preparedStatement.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
}
