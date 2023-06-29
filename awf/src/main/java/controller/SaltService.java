package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Dao.DBDao;
import Service.FileService;

/**
 * Servlet implementation class SaltService
 */
@WebServlet("/SaltService")
public class SaltService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private static SaltService instance;
	public static SaltService Instance()
	{
		if(instance == null)
		{
			instance = new SaltService();
		}
		return instance;
	}
	private static Long getTime;
    private final int updateCycle = 5000; //1000 = 1sec
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaltService() {
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
		//System.out.println(this.getServletContext().getRealPath(""));
		DBDao.Instance().DBConnect();
		SaltUpdater();
		
	}
	void SaltUpdater() throws IOException
	{
		 getTime = FileService.Instance().GetSaltUpdateTime();
         while (true) {
             // System.currentTimeMillis() 1000 =1sec
             if (System.currentTimeMillis() > getTime+updateCycle) {
                 // System.out.println(System.currentTimeMillis());
					//System.out.printf("%d ? %d\n",getTime+updateCycle,System.currentTimeMillis());
                 FileService.Instance().SaltSave(System.currentTimeMillis(),RandomSalt());
					getTime = System.currentTimeMillis();

             }
         }
	}
	 public String RandomSalt()
		{
			String R = "1234567890qwertyuiop[]as;dlfkgjhzx/c.,v./mbn\"'?><}{=-+_/*-`~"; // salt에 들어갈 문자열
			String _salt = "";
			int _saltSize = 20;
			
			for(int i=0;i<_saltSize;i++)
			{
				_salt += R.charAt((int)(Math.random()*R.length()));
			}
			
			return _salt;
			
		}
}
