package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class link
 */
@WebServlet("/link")
public class link extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public link() {
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
        String T=object.getString("T");
        String soil=object.getString("soil");
        String lux=object.getString("lux");
        System.out.println(T+soil+lux);
        float T1=Float.parseFloat(T);
        int soil1=Integer.parseInt(soil);
        flower flowers=new flower();
        int lux1=Integer.parseInt(lux);
        if(T1<17)
        {
        	flowers.setT("偏低");
        }else if(T1>=17 && T1<=30)
        {
        	flowers.setT("合适");
        }else if(T1>30)
        {
        	flowers.setT("偏高");
        }
        if(soil1<400)
        {
        	flowers.setsoil("偏高");
        }else if(soil1>=400 && soil1<=550)
        {
        	flowers.setsoil("合适");
        }else if(soil1>550)
        {
        	flowers.setsoil("偏低");
        }
        if(lux1<300)
        {
        	flowers.setlux("偏低");
        }else if(lux1>=300 && lux1<=2000)
        {
        	flowers.setlux("合适");
        }else if(lux1>2000)
        {
        	flowers.setlux("偏高");
        }
        Gson gs = new Gson();
        String content = gs.toJson(flowers);
        System.out.println(content);
        // String result = "";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().append(content).flush();
	}

}
