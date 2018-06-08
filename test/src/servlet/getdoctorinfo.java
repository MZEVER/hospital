package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.jdbc.Statement;

import dto.doctor;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class getdoctorinfo
 */
@WebServlet("/getdoctorinfo")
public class getdoctorinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getdoctorinfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("不支持GET方法;");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		BufferedReader read = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = read.readLine()) != null) {
            sb.append(line);
        }

        String req = sb.toString();
        System.out.println(req);

        // 第一步：获取 客户端 发来的请求，恢复其Json格式——>需要客户端发请求时也封装成Json格式
        JSONObject object = JSONObject.fromObject(req);
        String did=object.getString("did");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        
        doctor temp=new doctor();
        try {
        	Connection connect = DBUtil.getConnect(); 
            Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现  
            ResultSet result;  
            String sqlQuery="select * from doctor where did="+did;
        	ResultSet result1 = statement.executeQuery(sqlQuery);
        	if(result1.next()){
            	temp.setid(did);
        		System.out.println("1");
            	String name=result1.getString(2);
            	System.out.println("2");
            	temp.setName(name);
            	String pno=result1.getString("pno");
            	temp.setpno(pno);
            	String address=result1.getString("address");
            	temp.setaddress(address);
            	String department=result1.getString("department");
            	temp.setdepartment(department);
            	String introduction=result1.getString("introduction");
            	temp.setintroduction(introduction);
            	String sex=result1.getString("sex");
            	temp.setsex(sex);
            	String email=result1.getString("email");
            	temp.setmail(email);
            	String dno=result1.getString("dno");
            	temp.setdno(dno);
            	}
        	 sqlQuery="select * from daccount where did="+did;
        	 result1 = statement.executeQuery(sqlQuery);
        	if(result1.next()){
        		temp.setischeck(result1.getString("ischeck"));
        	}
        }catch (SQLException e) {  
            e.printStackTrace();  
        }
        Gson gson = new Gson();
        String resStr = gson.toJson(temp);
        System.out.println(resStr);
        response.getWriter().append(resStr).flush();

	}

}
