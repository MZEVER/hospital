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

import dto.record;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class getrecordlist
 */
@WebServlet("/getrecordlist")
public class getrecordlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getrecordlist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("��֧��GET����;");
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

        // ��һ������ȡ �ͻ��� ���������󣬻ָ���Json��ʽ����>��Ҫ�ͻ��˷�����ʱҲ��װ��Json��ʽ
        JSONObject object = JSONObject.fromObject(req);
        String pid=object.getString("pid");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        List<record> list = new ArrayList<record>();
        
        try {
        	Connection connect = DBUtil.getConnect(); 
            Statement statement = (Statement) connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��  
            ResultSet result;  
            String sqlQuery="select * from record where pid='"+ pid+"' order by time ";
            result = statement.executeQuery(sqlQuery); // �Ȳ�ѯͬ�����˺ţ������ֻ��ţ��Ƿ����  
            while(result.next()){
            	record a=new record();
            	a.setpid(pid);
            	a.setcontent(result.getString("content"));
            	a.setrid(result.getString("rid"));
            	a.setdid(result.getString("did"));
            	a.settime(result.getString("time"));
            	a.setchecke(result.getString("ischeck"));
            	list.add(a);
            	
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
