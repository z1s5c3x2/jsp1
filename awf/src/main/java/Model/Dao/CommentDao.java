package Model.Dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Model.Dto.CommentDto;
public class CommentDao {
    private static CommentDao instance;
	public static CommentDao Instance()
	{
		if(instance == null)
		{
			instance = new CommentDao();
		}
		return instance;
	}
    private ResultSet rs;
    private PreparedStatement ps;
    public ArrayList<CommentDto> GetCommentList(int _boardId)
    {
        ArrayList<CommentDto> comlist = new ArrayList<CommentDto>();

        try {
            String sql = "select * from comment where board_id like ? order by ref desc,reorder";
            ps = DBDao.Instance().con.prepareStatement(sql);
            ps.setInt(1, _boardId);
            rs = ps.executeQuery();
            while(rs.next())
            {
                comlist.add(new CommentDto(rs.getInt("id"),rs.getInt("board_id"), rs.getString("writer"), rs.getString("content"), rs.getDate("createdate"),rs.getInt("ref"),rs.getInt("step"),rs.getInt("reorder")));
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage()+"asdkasd");
        }
        return comlist;
    }
    
    public void AddComment(String _writer,String _com,int _boardId)
    {
        try {
        	
            String sql = "insert into comment (board_id,writer,content,step,ref,reorder) values(?,?,?,0,(select * from(select ifnull(max(ref),0)+1 from comment) as temp),0)";
            ps = DBDao.Instance().con.prepareStatement(sql);
            ps.setInt(1, _boardId);
            ps.setString(2, _writer);
            ps.setString(3, _com);
            ps.execute();
        } catch (Exception e) {
            // TODO: handle exception
        	System.out.println(e);
        }
    }
    public void ReOrdAdderbig(int _ref,int _reo)
    {
    	try {
			String sql = "update comment set reorder=reorder+1 where reorder > ? and ref like ?";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setInt(1, _reo);
			ps.setInt(2, _ref);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
    }
    public void ReOrdAdderequl(int _ref,int _step,int _reo)
    {
    	try {
			String sql = "update comment set reorder=reorder+1 where (step < ? and reorder > ?) and ref like ?";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setInt(1, _step);
			ps.setInt(2, _reo);
			ps.setInt(3, _step);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
    }
    public void AddReComment(String _writer,int _par,String _com)
    {
    	try {
    		CommentDto _parcom = SearchCommentByID(_par);
    		String sql = "select max(step),max(reorder) from comment where ref like ?";
    		ps = DBDao.Instance().con.prepareStatement(sql);
    		ps.setInt(1, _parcom.getRef());
    		rs = ps.executeQuery();
    		rs.next();
    		System.out.println("?????");
    		int mstep = rs.getInt(1); // step
    		int mord = rs.getInt(2); // reord
    		
    		int savstep = _parcom.getStep()+1;
    		
            
    		if(mstep > savstep)
    		{	
    			sql = "insert into comment (board_id,writer,content,step,ref,reorder) values(?,?,?,?,?,?)";
        		ps = DBDao.Instance().con.prepareStatement(sql);
        		ps.setInt(1, _parcom.getBoardId());
                ps.setString(2, _writer);
                ps.setString(3, _com);
                ps.setInt(4, _parcom.getStep()+1);
                ps.setInt(5, _parcom.getRef());
    			ps.setInt(6, mord+1);
    			ps.execute();
    			return;
    		}
    		else if(mstep == savstep)
    		{
    			ReOrdAdderequl(_parcom.getRef(),savstep,_parcom.getReord()); // +1해서 저장
    		}
    		else if(mstep < savstep)
    		{
    			ReOrdAdderbig(_parcom.getRef(),_parcom.getReord());
	
    		}
    		sql = "insert into comment (board_id,writer,content,step,ref,reorder) values(?,?,?,?,?,?)";
    		ps = DBDao.Instance().con.prepareStatement(sql);
    		ps.setInt(1, _parcom.getBoardId());
            ps.setString(2, _writer);
            ps.setString(3, _com);
            ps.setInt(4, _parcom.getStep()+1);
            ps.setInt(5, _parcom.getRef());
    		ps.setInt(6, _parcom.getReord()+1);
    		System.out.println("여기");
    		 ps.execute();
    		 System.out.println("여기2");
    		/*
        	ps = DBDao.Instance().con.prepareStatement(sql);
        	
            ps.setInt(4, _parcom.getStep()+1);
            ps.setInt(5, _parcom.getRef());
            ps.execute();
            recommentUpdate(_parcom.getRef(),_parcom.getStep()+1);*/
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e+"recom");
		}
    }
    public void DeleteComment(int _id)
    {
    	try {
    		String sql ="delete from comment where id = ?";
    		ps = DBDao.Instance().con.prepareStatement(sql);
    		ps.setInt(1, _id);
    		ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
    }
    public CommentDto SearchCommentByID(int _id)
    {
    	CommentDto retcom = new CommentDto();
    	try {
			String sql = "select * from comment where id like ?";
			ps = DBDao.Instance().con.prepareStatement(sql);
    		ps.setInt(1, _id);
    		rs = ps.executeQuery();
    		rs.next();
    		retcom = new CommentDto(rs.getInt("id"),rs.getInt("board_id"), rs.getString("writer"), rs.getString("content"), rs.getDate("createdate"),rs.getInt("ref"),rs.getInt("step"),rs.getInt("reorder"));

			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
    	return retcom;
    }
}
