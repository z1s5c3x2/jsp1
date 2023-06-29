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
	    public int nowPage = 0;
	
	    public void WriteBoardInit(String _title, String _content)
	    {
	        String _w = AccountDao.Instance().adt.GetId();
	        BoardDao.Instance().WriteBoard(_w,_title,_content);

	    }
	    public void UpdateBoard(BoardDto _bdt)
	    {
	        BoardDao.Instance().UpdateBoard(_bdt);
	    }
	    public void DeleteBoard(int _id)
	    {
	        BoardDao.Instance().DeleteBoard(_id);
	    }
	    public ArrayList<BoardDto> MyBoard()
	    {
	        return BoardDao.Instance().GetMyBoard(AccountDao.Instance().adt.GetId(),nowPage*BoardDto.pageSize);
	    }
	    public boolean PageCheck(int _page)
	    {
	        if(nowPage+_page < 0 || nowPage+_page > 10)
	        {

	            return false;
	        }

	        return true;
	    }
	    public ArrayList<BoardDto> SearchDetail(String _User,String _title)
	    {
	        return BoardDao.Instance().SearchWriterandTitle(_User, _title, nowPage*BoardDto.pageSize);
	    }
	    public ArrayList<String> GetCreateCount()
	    {
	        return BoardDao.Instance().SearchMyBoardCount(AccountDao.Instance().adt.GetId());
	    }
	    public ArrayList<BoardDto> ViewBoard()
	    {
	        ArrayList<BoardDto> _glist = new ArrayList<BoardDto>();
	        
	        _glist = BoardDao.Instance().GetBoard(nowPage*BoardDto.pageSize);    

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
		

		if(request.getParameter("action").equals("Detail"))
		{
			String jsonBoard = new Gson().toJson(SearchIdBoard(Integer.parseInt(request.getParameter("id"))));
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonBoard);
		}
		else {
			
			int _page = Integer.parseInt(request.getParameter("page"));

				nowPage += _page;
				ArrayList<BoardDto> retBoardList = ViewBoard();
				String jsonBoardList = new Gson().toJson(retBoardList);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonBoardList);
				System.out.println(retBoardList.size());
				if(retBoardList.size() == 0)
				{
					nowPage -= _page;
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
