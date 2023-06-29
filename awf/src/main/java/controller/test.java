package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Dao.AccountDao;
import Model.Dao.DBDao;
import Service.HashService;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class test extends HttpServlet {
	boolean isC = false;
	private static final long serialVersionUID = 1L;

    public test() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(!isC)
		{
			DBDao.Instance().DBConnect();
			
			isC=true;
		}
		String _getId = request.getParameter("userId");
		String _getPwd = request.getParameter("userPwd");
		if(request.getParameter("action").equals("SignUp"))
		{
			AccountDao.Instance().CreateUserAccount(_getId,HashService.Instance().GetHash256(_getId, _getPwd) 
			);
		}
		else if(request.getParameter("action").equals("Login"))
		{
			AccountDao.Instance().AccountLogin(_getId, HashService.Instance().GetHash256(_getId, _getPwd));
			
		}
		response.getWriter().print( "sing sucess" );
	}

}
