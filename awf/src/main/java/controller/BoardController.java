package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import Model.Dao.AccountDao;
import Model.Dao.BoardDao;
import Model.Dto.BoardDto;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  private static BoardController instance;
		public static BoardController Instance()
		{
			if(instance == null)
			{
				instance = new BoardController();
			}
			return instance;
		}
	    
	
	    public void WriteBoardInit(String _User ,String _title, String _content)
	    {
	    	
	       // String _w = "asd";//AccountDao.Instance().adt.GetId();
	        BoardDao.Instance().WriteBoard(_User,_title,_content);

	    }
	    public void UpdateBoard(BoardDto _bdt)
	    {
	        BoardDao.Instance().UpdateBoard(_bdt);
	    }
	    public void DeleteBoard(int _id)
	    {
	        BoardDao.Instance().DeleteBoard(_id);
	    }
	    public ArrayList<BoardDto> MyBoard(int _page)
	    {
	        return BoardDao.Instance().GetMyBoard(AccountDao.Instance().adt.GetId(),_page*BoardDto.pageSize);
	    }

	    public ArrayList<BoardDto> SearchDetail(String _User,String _title,int _page)
	    {
	        return BoardDao.Instance().SearchWriterandTitle(_User, _title, _page*BoardDto.pageSize);
	    }
	    public ArrayList<String> GetCreateCount()
	    {
	        return BoardDao.Instance().SearchMyBoardCount(AccountDao.Instance().adt.GetId());
	    }
	    public ArrayList<BoardDto> ViewBoard(int _page)
	    {
	        ArrayList<BoardDto> _glist = new ArrayList<BoardDto>();
	        
	        _glist = BoardDao.Instance().GetBoard(_page*BoardDto.pageSize);    

	        return  _glist;
	    }
	    public BoardDto SearchIdBoard(int _num)
	    {
	        return BoardDao.Instance().GetIdBoard(_num);
	    }
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		if(request.getParameter("action").equals("maxpage"))
		{
			int _m = BoardDao.Instance().GetMaxPage();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(_m);

			
		}
		else if(request.getParameter("action").equals("Detail"))
		{
			String jsonBoard = new Gson().toJson(SearchIdBoard(Integer.parseInt(request.getParameter("id"))));
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonBoard);
		}
		else if(request.getParameter("action").equals("Write"))
		{
			String _uid = request.getSession().getAttribute("userId").toString();
			WriteBoardInit(_uid,request.getParameter("title"),request.getParameter("content"));
			
		}
		else {
			
				int _page = Integer.parseInt(request.getParameter("page")) -1;

			
				ArrayList<BoardDto> retBoardList = ViewBoard(_page);
				String jsonBoardList = new Gson().toJson(retBoardList);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonBoardList);
				//System.out.println(retBoardList.size());
				if(retBoardList.size() == 0)
				{
					throw new ServletException("페이지 탐색 오류");
					
				}

			
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
	}

}
