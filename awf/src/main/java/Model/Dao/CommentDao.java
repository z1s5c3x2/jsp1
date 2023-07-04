package Model.Dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.crypto.spec.PSource;

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
            String sql = "select * from comment where board_id like ? order by ref desc,reforder";
            ps = DBDao.Instance().con.prepareStatement(sql);
            ps.setInt(1, _boardId);
            rs = ps.executeQuery();
            while(rs.next())
            {
                comlist.add(new CommentDto(rs.getInt("id"),
        				rs.getInt("board_id"),
        				rs.getString("writer"),
        				rs.getString("content"),
        				rs.getDate("createdate"),
        				rs.getInt("ref"),
        				rs.getInt("step"),
        				rs.getInt("reforder"),
        				rs.getInt("anscount"),
        				rs.getInt("parid")));
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
        	
            String sql = "insert into comment (board_id,writer,content,step,ref,reforder,anscount,parid) values(?,?,?,0,(select * from(select ifnull(max(ref),0)+1 from comment) as temp),0,0,0)";
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
    public void UpdateRefOrder(int _ref)
    {
    	try {
			String sql = "update comment set reforder=reforder+1 where reforder >= ?";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setInt(1, _ref);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e+"Updatereforder");
		}
    }
    public void ParUpdateAnscnt(int _id)
    {
    	try {
			String sql = "update comment set anscount=anscount+1 where id like ?";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setInt(1, _id);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("parupdateanscount"+e);
		}
    }
    public int GetMaxStep(int _ref)
    {
    	int retnum = 0;
    	try {
			String sql = "select max(step) from (select step from comment where ref like ?)as tmp";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setInt(1, _ref);
			rs = ps.executeQuery();
			rs.next();
			retnum = rs.getInt(1);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getmax"+e);
		}
    	return retnum;
    }
    public int GetSumAnsCount(int _ref)
    {
    	int retnum = 0;
    	try {
			String sql = "select sum(anscount) from (select anscount from comment where ref like ?)as tmp";
			ps = DBDao.Instance().con.prepareStatement(sql);
			ps.setInt(1, _ref);
			rs = ps.executeQuery();
			rs.next();
			retnum = rs.getInt(1);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getmax"+e);
		}
    	return retnum;
    }
    public void AddReComment(String _writer,int _par,String _com)
    {
    	try {  
    		CommentDto _parcomment = SearchCommentByID(_par);
    		int instep = _parcomment.getStep()+1;
    		int mstep = GetMaxStep(_parcomment.getRef()); // ref max step
    		
    		int insreford = 0;
    		if(mstep > instep)
    		{
    			insreford =  GetSumAnsCount(_parcomment.getRef())+1;
    		}
    		else if(mstep == instep)
    		{
    			insreford = _parcomment.getcnt()+_parcomment.getRefOrd()+1;
    		}
    		else
    		{
    			insreford =  _parcomment.getRefOrd()+1;
    		}
    		UpdateRefOrder(insreford);
    		String sql = "insert into comment (board_id,writer,content,step,ref,reforder,anscount,parid) values"
					+ "(?,?,?,?,?,?,0,?)";
    		ps = DBDao.Instance().con.prepareStatement(sql);
    		ps.setInt(1, _parcomment.getBoardId());
			ps.setString(2, _writer);
			ps.setString(3, _com);
			ps.setInt(4, instep);
			ps.setInt(5, _parcomment.getRef());
			ps.setInt(6, insreford);
			ps.setInt(7, _par);
			ps.execute();
			ParUpdateAnscnt(_par);
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
    		retcom = new CommentDto(rs.getInt("id"),
    				rs.getInt("board_id"),
    				rs.getString("writer"),
    				rs.getString("content"),
    				rs.getDate("createdate"),
    				rs.getInt("ref"),
    				rs.getInt("step"),
    				rs.getInt("reforder"),
    				rs.getInt("anscount"),
    				rs.getInt("parid"));

			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
    	return retcom;
    }
}
