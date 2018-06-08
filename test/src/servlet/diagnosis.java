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

import com.mysql.jdbc.Statement;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class diagnosis
 */
@WebServlet("/diagnosis")
public class diagnosis extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public diagnosis() {
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
        String pid=object.getString("pid");
        String did=object.getString("did");
        String content=object.getString("content");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Timestamp d = new Timestamp(System.currentTimeMillis()); 
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time=sdf.format(d);
        String res="";
        
        try {  
            Connection connect = DBUtil.getConnect(); 
            Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现  
            ResultSet result;
            String sql="select * from wait where pid='"+pid+"'and did='"+did+ "'";
            result=statement.executeQuery(sql);
            
            if(result.next()){
            	String sqlInsertPass = "insert into record (pid,did,time,content,ischeck) values('"+pid+"','"+did+"','"+time+"','"+content+"','"+"0')";  
                // 更新类操作返回一个int类型的值，代表该操作影响到的行数  
                statement.executeUpdate(sqlInsertPass); // 插入帐号密码  
                sqlInsertPass="delete from wait where pid='"+pid+"' and did='"+did+ "'";
                statement.executeUpdate(sqlInsertPass);
                res = "{\"code\":\"1\"}"; 
            }else {
            	res = "{\"code\":\"0\"}"; 
            }
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
        String resStr = JSONObject.fromObject(res).toString();
        System.out.println(resStr);
        response.getWriter().append(resStr).flush();
	}

}
