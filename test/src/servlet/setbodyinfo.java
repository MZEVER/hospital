package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.jdbc.Statement;

import dto.bodyinfo;
import dto.doctor;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class setbodyinfo
 */
@WebServlet("/setbodyinfo")
public class setbodyinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public setbodyinfo() {
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
        bodyinfo temp=gson.fromJson(req, bodyinfo.class);
        Timestamp d = new Timestamp(System.currentTimeMillis()); 
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
        temp.settime(sdf.format(d));
        String res="";
        try {
        	Connection connect = DBUtil.getConnect(); 
            Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现  
            ResultSet result; 
            String sqlInsertPass = "insert into bodyinfo "  + " values('"+temp.getid()+"','"+temp.getxueya()+"','"+temp.getxuetang()+"','"+temp.gettiwen()
        	+"','"+temp.getxinlv()+"','"+temp.gettime()+"')";
        	int row1=statement.executeUpdate(sqlInsertPass);
        	if(row1 == 1){  
             	 res = "{\"code\":\"1\"}"; 
             } else {  
             	 res = "{\"code\":\"0\"}"; 
             }
        }catch (SQLException e) {  
            e.printStackTrace();  
        }
        String resStr = JSONObject.fromObject(res).toString();
        System.out.println(resStr);
        response.getWriter().append(resStr).flush();
	}

}
