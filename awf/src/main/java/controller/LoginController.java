package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.mapper.Mapper;
import org.json.simple.JSONObject;

import Model.Dao.AccountDao;
import Model.Dao.DBDao;
import Model.Dto.AccountDto;
import Service.FileService;
import Service.HashService;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static LoginController instance;
	public static LoginController Instance()
	{
		if(instance == null)
		{
			instance = new LoginController();
		}
		return instance;
	}

    public String Login(String _id,String _pwd)
    {
        String _e =AccountDao.Instance().AccountLogin(_id,_pwd);
		if(_e.equals(_id))
		{
			AccountDao.Instance().adt = new AccountDto();
			AccountDao.Instance().adt.SetId(_id);
			FileService.Instance().SaveLoginLog(_id,DBDao.Instance().GetDBTime());
		}
        return _e;
    }       
	public String SignUp(String _id,String _pwd)
    {
        String _e = AccountDao.Instance().CheckAccount(_id);
		if(_e.equals(""))
		{
			AccountDao.Instance().CreateUserAccount(_id, HashService.Instance().GetHash256(_id, _pwd));
			return _id;
		}
        return _e;
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String _getId = request.getParameter("userId");
		String _getPwd = request.getParameter("userPwd");
		//Login SignUp
		String _result = "";
		Boolean isSuccess = false;
		if(request.getParameter("action").equals("SignUp"))
		{
			_result = SignUp(_getId,_getPwd);
			if(_result.equals(_getId))
			{
				_result += "가입 성공!!";
				isSuccess = true;
			}
		}
		else if(request.getParameter("action").equals("Login"))
		{
			_result = Login(_getId,_getPwd);
			if(_result.equals(_getId))
			{
				_result += "로그인 성공!!";
				isSuccess = true;
				
				request.getSession().setAttribute("userId", _getId);
				request.getSession().setAttribute("isLogin", true);
			}
		}
		response.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> resHM = new HashMap<String, Object>();
		resHM.put("UserId",_getId);
		resHM.put("isSuccess",isSuccess);
		resHM.put("msg", _result);
		
		JSONObject resJs = new JSONObject();
		resJs.putAll(resHM);
		
		response.getWriter().print(resJs);
	}

}
