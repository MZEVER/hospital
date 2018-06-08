package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
 * Servlet implementation class setdoctorinfo
 */
@WebServlet("/setdoctorinfo")
public class setdoctorinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public setdoctorinfo() {
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
        Gson gson = new Gson();
        doctor temp=gson.fromJson(req, doctor.class);
        String res="";
        Map<String,String> a=new HashMap<String,String>();
        try {  
            Connection connect = DBUtil.getConnect(); 
            Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现  
            ResultSet result;  
            String did=temp.getid();
            String sqlQuery = "select * from doctor where did="+did;
            result = statement.executeQuery(sqlQuery);
            System.out.println("ok");
            if(result.next()){
            	String sqlInsertPass ="UPDATE doctor set name='"+temp.getName()+"',pno='"+temp.getpno()+"',address='"+temp.getaddress()+
            			"',department='"+temp.getdepartment()+"',introduction='"+temp.getintroduction()+"',sex='"+temp.getsex()+"',email='"+temp.getemail()
            			+"',dno='"+temp.getdno()+"'"+
            			"where did="+did;
            	System.out.println(sqlInsertPass);
            	int row1=statement.executeUpdate(sqlInsertPass);
            	if(row1 == 1){  
            		a.put("code", "1"); 
               } else {  
            	   a.put("code", "0");
               }  
            }else {
            	String sqlInsertPass = "insert into doctor "  + " values('"+temp.getid()+"','"+temp.getName()+"','"+temp.getpno()+"','"+temp.getaddress()
            	+"','"+temp.getdepartment()+"','"+temp.getintroduction()+"','"+temp.getsex()+"','"+temp.getemail()+"','"+temp.getdno()+"')";
            	int row1=statement.executeUpdate(sqlInsertPass);
            	if(row1 == 1){  
            		a.put("code", "1");
                  } else {  
                	  a.put("code", "0"); 
                  }
            }
        }
        catch (SQLException e) {  
            e.printStackTrace();  
        }
        String resStr = JSONObject.fromObject(a).toString();
        System.out.println(resStr);
        response.getWriter().append(resStr).flush();
	}

}
