package Model.Dao;
import Model.Dto.BoardDto;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BoardDao {
    private static BoardDao instance;
	public static BoardDao Instance()
	{
		if(instance == null)
		{
			instance = new BoardDao();
		}
		return instance;
	}
    private ResultSet rs;
    private PreparedStatement ps;
    public Integer GetMaxPage()
    {
    	try
    	{
    		String sql = "select count(*) from board";
    		ps = DBDao.Instance().con.prepareStatement(sql);
    		rs = ps.executeQuery();
    		rs.next();
    		
    		return rs.getInt(1);
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	return -1;
    }
	public void WriteBoard(String _w,String _t,String _c)
	{
		try {
			String sql = "insert into board (writer,title,content) values (?,?,?)";
			ps = DBDao.Instance().con.prepareStatement(sql);
			
			ps.setString(1,_w);
			ps.setString(2,_t);
			ps.setString(3,_c);
			ps.execute();
			
		}
		catch(Exception e)
		{
			System.out.println(e);
			
		}
	}
	public void DeleteBoard(int _id)
	{
		try {
			String sql = "delete from board where id = ?";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setInt(1,_id);
			ps.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void UpdateBoard(String _title, String _con,int _id)
	{
		try {
			String sql = "update board set content =?,title =? where id=?";
			//
			ps = DBDao.Instance().con.prepareStatement(sql);

			ps.setString(1, _con);
			ps.setString(2, _title);
			ps.setInt(3, _id);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	void AddViewCount(int _id,int _count)
	{
		try {
			String sql = "update board set viewcount =? where id=?";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setInt(1, _count+1);
			ps.setInt(2, _id);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//select * from board where writer like ? and title like ?;
	public ArrayList<BoardDto> SearchWriterandTitle(String _User,String _Title,int _page)
	{
		ArrayList<BoardDto> _blist = new ArrayList<BoardDto>();
		try {
			String sql = "select * from board where writer like ? and title like ? order by id desc limit ?,?";
			ps = DBDao.Instance().con.prepareStatement(sql);

			ps.setString(1, "%"+_User+"%");
			ps.setString(2, "%"+_Title+"%");
			ps.setInt(3, _page);
			ps.setInt(4, BoardDto.pageSize);
			rs = ps.executeQuery();

			while(rs.next())
			{

				_blist.add(rsToBdt(rs));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return _blist;
	}
	public BoardDto GetIdBoard(int _id)
	{
		BoardDto retBoard = new BoardDto();
		try {
			
			String sql = "select * from board where id like ?";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setString(1, "%"+Integer.toString(_id)+"%");
			
			ps.execute();
			rs = ps.executeQuery();
			rs.next();
			
			retBoard = rsToBdt(rs);
			retBoard.setViewCount(retBoard.getViewCount()+1);


			AddViewCount(_id,rs.getInt("viewcount"));
			
			
			
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
		return retBoard;
	}
	public ArrayList<BoardDto> GetMyBoard(String _User,int _page)
	{
		ArrayList<BoardDto> _blist = new ArrayList<BoardDto>();
		try {
			String sql = "select * from board where writer like ? order by id desc limit ?,?";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setString(1, _User);
			ps.setInt(2, _page);
			ps.setInt(3, BoardDto.pageSize);
			rs = ps.executeQuery();

			while(rs.next())
			{
				_blist.add(rsToBdt(rs));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return _blist;
	}
	/*
	 * System.out.println("번호 제목 작성자 조회수");
	 * 
	 */
	public ArrayList<String> SearchMyBoardCount(String _User)
	{
		ArrayList<String> countList = new ArrayList<String>();
		try {

			String sql = "select createdate, count(*) from board where writer like ? group by createdate";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setString(1, _User);			
			ps.execute();
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				countList.add(String.format("%s: %d개",rs.getString("createdate"),rs.getInt("count(*)")));
			}


		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}

		return countList;
	}

	public ArrayList<BoardDto> GetBoard(int _page)
	{
		ArrayList<BoardDto> _blist = new ArrayList<BoardDto>();
		try {
			String sql = "select * from board order by id desc limit ?,?";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setInt(1,_page);
			ps.setInt(2,BoardDto.pageSize);
			rs = ps.executeQuery();

			while(rs.next())
			{
				_blist.add(rsToBdt(rs));
				/*_blist.add(new BoardDto(rs.getInt("id"),
				rs.getString("writer"),
				rs.getString("title"),
				rs.getString("content"),
				rs.getDate("createdate"),
				rs.getInt("viewcount")));*/
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return _blist;
	}



	BoardDto rsToBdt(ResultSet _rs) throws SQLException
	{

		return new BoardDto(_rs.getInt("id"),
		_rs.getString("writer"),
		_rs.getString("title"),
		_rs.getString("content"),
		_rs.getDate("createdate"),
		_rs.getInt("viewcount"));
	}
}
