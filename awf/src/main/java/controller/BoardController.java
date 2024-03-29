package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import Model.Dao.AccountDao;
import Model.Dao.BoardDao;
import Model.Dao.CommentDao;
import Model.Dto.BoardDto;
import Model.Dto.CommentDto;

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
		/*
		public ArrayList<CommentDto> GetCommentList(int _boardId)
	    {
	        return CommentDao.Instance().GetCommentList(_boardId);
	    }*/
		
	    public void AddComment(String _writer,String  _com,int _boardId)
	    {
	        CommentDao.Instance().AddComment(_writer, _com, _boardId);
	    }
	    
	    public void WriteBoardInit(String _User ,String _title, String _content)
	    {
	    	
	       // String _w = "asd";//AccountDao.Instance().adt.GetId();
	        BoardDao.Instance().WriteBoard(_User,_title,_content);

	    }
	    public void UpdateBoard(BoardDto _bdt)
	    {
	        //BoardDao.Instance().UpdateBoard(_bdt);
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
	
	    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	    {

	    	if(request.getParameter("action").equals("ReComment"))
	    	{
	    		CommentDao.Instance().AddReComment(request.getSession().getAttribute("userId").toString(),Integer.parseInt(request.getParameter("id")),request.getParameter("content"));
	    	}
	    	else if(request.getParameter("action").equals("Addcomment"))
			{
				AddComment(request.getSession().getAttribute("userId").toString(),request.getParameter("content"),Integer.parseInt(request.getParameter("id")));
			}
	    	else if(request.getParameter("action").equals("ModiyfyComment"))
	    	{
	    		CommentDao.Instance().ModifyComment(Integer.parseInt(request.getParameter("id")), request.getParameter("content"));
	    	}else if(request.getParameter("action").equals("BoardModify"))
			{
				BoardDao.Instance().UpdateBoard(request.getParameter("title"),request.getParameter("content"),Integer.parseInt(request.getParameter("id")));
			}
	    	
	    }
	    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	    {
	    	if(request.getParameter("action").equals("Comment"))
	    	{
	    		CommentDao.Instance().DeleteComment(Integer.parseInt(request.getParameter("id")));
	    	}
	    	else if(request.getParameter("action").equals("Board"))
			{
				 BoardDao.Instance().DeleteBoard(Integer.parseInt(request.getParameter("id")));
			}
	    	

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
		
		else if(request.getParameter("action").equals("GetReComment"))
		{
			JSONObject jo = new JSONObject();
			String recomlist = new Gson().toJson(CommentDao.Instance().GetReCommentList(Integer.parseInt(request.getParameter("boardid")), Integer.parseInt(request.getParameter("commentid"))));
			jo.put("loginUser", request.getSession().getAttribute("userId"));
			jo.put("commentList", recomlist);
			String jsoncomlist = new Gson().toJson(jo);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsoncomlist);
		}
		else if(request.getParameter("action").equals("Detail"))
		{
			BoardDto _bdt =  SearchIdBoard(Integer.parseInt(request.getParameter("id")));
			JSONObject jo = new JSONObject();
			
			String retcomlist = new Gson().toJson(CommentDao.Instance().GetCommentList(Integer.parseInt(request.getParameter("id"))));

			jo.put("loginUser", request.getSession().getAttribute("userId"));
			jo.put("commentList", retcomlist);
			jo.put("item", _bdt);
			jo.put("isupdate", request.getSession().getAttribute("userId").equals(_bdt.getWriter()));
			String jsonBoard = new Gson().toJson(jo);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonBoard);

		}
		else { //pagemove
			
				int _page = Integer.parseInt(request.getParameter("page")) -1;

			
				ArrayList<BoardDto> retBoardList = ViewBoard(_page);
				String jsonBoardList = new Gson().toJson(retBoardList);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonBoardList);
				//System.out.println(retBoardList.size());


			
			
		}
	}
	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 
	protected void doPut(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println(req.getParameter("title"));
		// TODO Auto-generated method stub
		
	}
	*/
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("action").equals("Write"))
		{
			String _uid = request.getSession().getAttribute("userId").toString();
			WriteBoardInit(_uid,request.getParameter("title"),request.getParameter("content"));
			
		}
		
	}

}
