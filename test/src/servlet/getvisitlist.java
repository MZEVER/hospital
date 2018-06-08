package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.jdbc.Statement;

import dto.bodyinfo;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class getvisitlist
 */
@WebServlet("/getvisitlist")
public class getvisitlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getvisitlist() {
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
        
        List<Map> list = new ArrayList<Map>();
        try {
        	Connection connect = DBUtil.getConnect(); 
            Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现  
            ResultSet result;  
            String sqlQuery="select * from wait where did='"+ did+"' order by time ";
            result = statement.executeQuery(sqlQuery); // 先查询同样的账号（比如手机号）是否存在  
            while(result.next()){
            	Map<String,String> temp =new HashMap<>();
            	temp.put("pid", result.getString("pid"));
            	temp.put("checkrecords", result.getString("checkrecords"));
            	temp.put("content", result.getString("content"));
            	
            	list.add(temp);
            	
            }
        }catch (SQLException e) {  
            e.printStackTrace();  
        }
        Gson gson = new Gson();
        String resStr = gson.toJson(list);
        System.out.println(resStr);
        response.getWriter().append(resStr).flush();

	}

}
