package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class json
 */
@WebServlet("/json")
public class json extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public json() {
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
        String account=object.getString("account");
        String password=object.getString("password");
        System.out.println(account+password);
        // String result = "";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if("aa".equals(account)   
                && "bb".equals(password)){ // 模拟登陆账号、密码验证  
            String result = "{\"message\":\"成功\"}"; 
            String resStr = JSONObject.fromObject(result).toString();
            System.out.println(resStr);
//          response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
            response.getWriter().append(resStr).flush();
        }else {  
        	String result = "{\"message\":\"失败\"}";
        	String resStr = JSONObject.fromObject(result).toString();
            System.out.println(resStr);
//          response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
            response.getWriter().append(resStr).flush();
        }
	}

}
