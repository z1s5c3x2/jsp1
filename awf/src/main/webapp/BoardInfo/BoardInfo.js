
 /*
 $.ajax({
	url : "../BoardController",
	type : 'put',
	dataType: 'text',
	async:false,
	data : {
		test:"123",
		test1:"12334"

	},
	success : function(data) {
		
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});*/
	
 
 $.ajax({
	url : "../BoardController",
	type : 'get',
	dataType: 'json',
	async:false,
	data : {
		action:"Detail",
		id : new URL(window.location).searchParams.get("boardId"),
		


	},
	success : function(data) {
		let lgu = data.loginUser
		
		document.querySelector(".post-detail").innerHTML =
		`
		<h3 id="postNumber">번호 :${data.item.id}</h3>
            <h3 id="postTitle">제목 : ${data.item.title}</h3>
            <p id="postContent">내용 :${data.item.content}</p>
            <div class="post-meta">
                <span id="postAuthor">작성자 : ${data.item.writer}</span> |
                <span id="postDate">작성 날짜:${data.item.createDate}</span> |
                <span id="postViews">조회수:${data.item.viewCount}</span></div>
                
		`
		let Jl = JSON.parse(data.commentList)
		for(comitem of Jl)
		{
		
			document.querySelector(".comment").innerHTML += ` <div class="comment-meta citem_${comitem.id}">
          	<span class="comment-author">작성자 : ${comitem.writer}</span> |
          	<span class="comment-date">댓글 작성 날짜:${comitem.createdate}</span>
        	</div>
        	<div class="comment-content">
          	내용 : ${comitem.content}
        	</div>`
        	if(lgu == comitem.writer)
        	{
				document.querySelector(".comment").innerHTML += `<div class="comment-actions">
          <button onclick="EditComment(${comitem.id})">댓글 수정</button>
          <button onclick="DeleteComment(${comitem.id})">댓글 삭제</button>
        </div>`
			}
		}
		if(data.isupdate)
		{
			
			document.querySelector(".writerOptin").innerHTML=
			`<button onclick="DeletePost(${data.item.id})"> 게시글 삭제 </button>
                	<button onclick="UpdatePost(${data.item.id})"> 게시글 수정 </button>`
		}
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});
function DeletePost(gid)
{
	$.ajax({
	url : "../BoardController",
	type : 'get',
	dataType: 'json',
	async:false,
	data : {
		action:"delete",
		id : gid,
		


	},
	success : function(data) {
		
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});
	location.href= "/awf/Board/Board.html?page=1"
}

function UpdatePost(gid)
{

	location.href= "/awf/BoardModify/BoardModify.html?boardId="+gid
	//http://localhost:8080/awf/Board/Board.html?page=1
}
function SubmitComment()
{
	$.ajax({
	url : "../BoardController",
	type : 'get',
	dataType: 'text',
	async:false,
	data : {
		action:"Addcomment",
		id : new URL(window.location).searchParams.get("boardId"),
		content : document.querySelector(".inputComment").value
		


	},
	success : function(data) {
		location.href= "/awf/BoardInfo/BoardInfo.html?boardId="+new URL(window.location).searchParams.get("boardId")
		
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});
}

function DeleteComment(_gid)
{
	$.ajax({
	url : "../BoardController",
	type : 'delete',
	dataType: 'text',
	async:false,
	data : {
		action:"CommentDelete",
		id : _gid,


	},
	success : function(data) {
		location.href= "/awf/BoardInfo/BoardInfo.html?boardId="+new URL(window.location).searchParams.get("boardId")
		
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});
}
function EditComment(onpos)
{
	
	document.querySelector(".citem_"+onpos).innerHTML +=`<div class="comment-edit">
      			<textarea class="editarray"></textarea>
      			<button onclick="UpdateComment(${onpos})">수정</button>
      				<button onclick="CancelEdit(${onpos})">취소</button>
    			</div>`
}
function UpdateComment(_comid)
{
	$.ajax({
	url : "../BoardController",
	type : 'put',
	dataType: 'text',
	async:false,
	data : {
		action:"CommentModify",
		id : _comid,
		content: document.querySelector("editarray").value
		

	},
	success : function(data) {
		location.href= "/awf/BoardInfo/BoardInfo.html?boardId="+new URL(window.location).searchParams.get("boardId")
		
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});
}
function CancelEdit(_gstr)
{
	
	document.querySelector(".comment-edit").remove()
}
