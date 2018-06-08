package servlet;

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
/**
 * Servlet implementation class doctorlist
 */
@WebServlet("/doctorlist")
public class doctorlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public doctorlist() {
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
		response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        List<doctor> list = new ArrayList<doctor>();
        List<String> idlist=new ArrayList<String>();
        try {  
            Connection connect = DBUtil.getConnect(); 
            Statement statement = (Statement) connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��  
            ResultSet result;  
            String sqlQuery = "select did from daccount where ischeck = '1'";  
              
            // ��ѯ���������һ��ResultSet���ϣ�û�в鵽���ʱResultSet�ĳ���Ϊ0  
            result = statement.executeQuery(sqlQuery); // �Ȳ�ѯͬ�����˺ţ������ֻ��ţ��Ƿ����  
            while(result.next()){ // �Ѵ���  
            	String id=result.getString("did");
            	idlist.add(id);
            }
            for(int i = 0; i < idlist.size(); i++){
                String id=idlist.get(i);
            	doctor temp=new doctor();
            	temp.setid(id);
            	sqlQuery="select * from doctor where did="+id;
            	ResultSet result1 = statement.executeQuery(sqlQuery);
            	if(result1.next()){
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
                	list.add(temp);
            	}
            }
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
        Gson gson = new Gson();
        String resStr = gson.toJson(list);
        System.out.println(resStr);
        response.getWriter().append(resStr).flush();
        }
}
