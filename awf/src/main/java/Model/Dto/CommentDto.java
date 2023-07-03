package Model.Dto;
import java.sql.Date;
public class CommentDto {
    private int boardId;
    private String writer;
    private String content;
    private Date createdate;
    private int id;
    private int ref;
    private int step;
    private int reord;
    public CommentDto()
    {}
    public CommentDto(int _id,int _bid,String _w,String _c,Date _d,int _ref,int _step,int _reord)
    {
        super();
        this.id = _id;
        this.boardId = _bid;
        this.writer = _w;
        this.content = _c;
        this.createdate = _d;
        this.ref = _ref;
        this.step = _step;
        this.reord = _reord;
        		
    }
    public int getRef()
    {
    	return this.ref;
    }
    public int getStep()
    {return this.step;}
    public int getReord()
    {return this.reord;}
    public void setRef(int _ref)
    {
    	this.ref = _ref;
    }
    public void setStep(int _step)
    {this.step = _step;}
    public void setReord(int _reord)
    {this.reord = _reord;}
    public int getId()
    {
    	return this.id;
    }
    public void setId(int _gid)
    {
    	this.id = _gid;
    }
    public int getBoardId() {
        return this.boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getWriter() {
        return this.writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

}
