package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.jdbc.Statement;

import dto.doctor;
import dto.patient;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class setpatieninfo
 */
@WebServlet("/setpatieninfo")
public class setpatieninfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public setpatieninfo() {
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
        patient temp=gson.fromJson(req, patient.class);
        String res="";
        try {  
            Connection connect = DBUtil.getConnect(); 
            Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现  
            ResultSet result;  
            String sqlQuery = "select * from patient where pid="+temp.getid();
            result = statement.executeQuery(sqlQuery);
            if(result.next()){
            	String sqlInsertPass ="UPDATE patient set name='"+temp.getName()+"',pno='"+temp.getpno()+"',address='"+temp.getaddress()+
            			"',sex='"+temp.getsex()+"',email='"+temp.getemail()
            			+"'where pid="+temp.getid();
            	int row1=statement.executeUpdate(sqlInsertPass);
            	if(row1 == 1){  
                	 res = "{\"code\":\"1\"}"; 
                } else {  
                	 res = "{\"code\":\"0\"}";
                }
            }else {
            	String sqlInsertPass = "insert into patient "  + " values('"+temp.getid()+"','"+temp.getName()+"','"+temp.getpno()+"','"+temp.getaddress()
            	+"','"+temp.getsex()+"','"+temp.getemail()+"')";
            	int row1=statement.executeUpdate(sqlInsertPass);
            	if(row1 == 1){  
                	 res = "{\"code\":\"1\"}"; 
                } else {  
                	 res = "{\"code\":\"0\"}"; 
                }
            }
        }catch (SQLException e) {  
            e.printStackTrace();  
        }
        String resStr = JSONObject.fromObject(res).toString();
        System.out.println(resStr);
        response.getWriter().append(resStr).flush();
	}

}
