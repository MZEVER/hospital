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
 * Servlet implementation class pvisit
 */
@WebServlet("/pvisit")
public class pvisit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pvisit() {
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
        String did=object.getString("did");
        String check=object.getString("check");
        String content=object.getString("content");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Timestamp d = new Timestamp(System.currentTimeMillis()); 
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time=sdf.format(d);
        try {  
            Connection connect = DBUtil.getConnect(); 
            Statement statement = (Statement) connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��  
            ResultSet result;
            String sqlInsertPass = "insert into wait values('"+did+"','"+pid+"','"+check+"','"+time+"','"+content+"')";  
            // �������������һ��int���͵�ֵ������ò���Ӱ�쵽������  
            statement.executeUpdate(sqlInsertPass); // �����ʺ�����  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
        
	}

}
