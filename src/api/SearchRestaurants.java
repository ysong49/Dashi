package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MongoDBConnection;
import db.MySQLDBConnection;

/**
 * Servlet implementation class SearchRestaurants
 */
@WebServlet("/restaurants") // mapping url to this class
public class SearchRestaurants extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static final DBConnection connection = new MongoDBConnection();
	private static final DBConnection connection = new MySQLDBConnection();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchRestaurants() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		// // Hello World test.
		// response.setContentType("application/json"); // 告诉浏览器返回文件是什么格式
		// response.addHeader("Access-Control-Allow-Origin", "*"); // 设置访问限制，
		// ‘*’ is all, if '*.google.com' only users from google can visit
		// String username = "";
		// PrintWriter out = response.getWriter(); // buffer writer
		// /**
		// * only change this part is enough, other things are fixed for JEE
		// * eg. url: .../restaurants?username=abcd
		// * full url:
		// http://localhost:8080/Dashi/restaurants?username=Yang%20Song!
		// */
		// if (request.getParameter("username") != null) {
		// username = request.getParameter("username");
		// out.print("Hello " + username);
		// }
		// out.flush(); // 冲马桶， means send to writer.
		// out.close(); // close 冲马桶的管道

		// // test html
		// response.setContentType("text/html");
		// PrintWriter out = response.getWriter();
		// out.println("<html><body>");
		// out.println("<h1>This is a HTML page</h1>");
		// out.println("</body></html>");
		// out.flush();
		// out.close();

		// // test json
		// response.setContentType("application/json");
		// response.addHeader("Access-Control-Allow-Origin", "*");
		//
		// String username = "";
		// if (request.getParameter("username") != null) {
		// username = request.getParameter("username");
		// }
		// JSONObject obj = new JSONObject();
		// try {
		// obj.put("username", username);
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// PrintWriter out = response.getWriter();
		// out.print(obj);
		// out.flush();
		// out.close();

		// JSONArray array = new JSONArray();
		// try {
		// if (request.getParameterMap().containsKey("user_id")
		// && request.getParameterMap().containsKey("lat")
		// && request.getParameterMap().containsKey("lon")) {
		// String userId = request.getParameter("user_id");
		// double lat = Double.parseDouble(request.getParameter("lat"));
		// double lon = Double.parseDouble(request.getParameter("lon"));
		// // 此处省略了很多代码， 后面put的信息应该来自数据库。
		// // return some fake restaurants
		// JSONObject obj = new JSONObject();
		// obj.put("name", "Panda Express");
		// obj.put("location", "Santa Clara");
		// array.put(obj);
		// array.put(new JSONObject().put("name", "Hong Kong Express"));
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// // RpcParser 相当于是一个helper的类
		// RpcParser.writeOutput(response, array);

		
		
		// allow access only if session exists
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.setStatus(403);
			return;
		}
		
		JSONArray array = new JSONArray();
//		DBConnection connection = new MySQLDBConnection();
		if (request.getParameterMap().containsKey("user_id")
				&& request.getParameterMap().containsKey("lat")
				&& request.getParameterMap().containsKey("lon")) {
			
			String userId = request.getParameter("user_id");
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lon = Double.parseDouble(request.getParameter("lon"));
			
			array = connection.searchRestaurants(userId, lat, lon);
		}
		RpcParser.writeOutput(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
