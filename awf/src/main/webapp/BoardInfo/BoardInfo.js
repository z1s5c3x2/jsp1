
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
	/*
	ref = 최상위 댓글
	reforder = 그룹 내 순서
	step = 깊이
	parcom = 상위 부모
	anscnt = 자식 답글 수
	
	 */
 
 $.ajax({ //get init boar and comment
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
		if(data.isupdate)
		{
			
			document.querySelector(".writerOptin").innerHTML=
			`<button onclick="DeletePost(${data.item.id})"> 게시글 삭제 </button>
                	<button onclick="UpdatePost(${data.item.id})"> 게시글 수정 </button>`
		}
		CommentPirnt(JSON.parse(data.commentList),lgu,0)

     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});
function GetReCommentList(_parid)
{
	$.ajax({
	url : "../BoardController",
	type : 'get',
	dataType: 'json',
	async:false,
	data : {
		action:"GetReComment",
		boardid : new URL(window.location).searchParams.get("boardId"),
		commentid : _parid

	},
	success : function(data) {
		CommentPirnt(JSON.parse(data.commentList),data.loginUser,_parid)
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});
}
function CommentPirnt(Jl,lgu,_parid)
{

	for(comitem of Jl)
		{
			
			let _recom = document.createElement("div")
			_recom.className = "comment-meta citem_"+comitem.id
			_recom.style.paddingLeft = comitem.step*50+"px"
			_recom.innerHTML = ` 
          	<span class="comment-author">작성자 : ${comitem.writer}</span> |
          	<span class="comment-date">댓글 작성 날짜:${comitem.createdate}</span>
          	<button onclick="AddReComment(${comitem.id})">답글</button>
        	</div>
        	<div class="recom${comitem.id}">
        	</div>
        	<div class="comment-content">
          	내용 : ${comitem.content}</div>
        	`
        	let parhtml
        	if(_parid >0)
			{
				parhtml = document.querySelector(".recom"+_parid)
			}
			else
			{
				parhtml =document.querySelector(".comment")
			}
			console.log(parhtml)
        	parhtml.appendChild(_recom)
        	if(lgu == comitem.writer)
        	{
				parhtml.innerHTML += `<div class="comment-actions">
          <button onclick="EditComment(${comitem.id})">댓글 수정</button>
          <button onclick="DeleteComment(${comitem.id})">댓글 삭제</button>
        </div>`
            if(comitem.anscnt > 0)
            {
				document.querySelector(".citem_"+comitem.id).innerHTML +=`
				<button onclick="GetReCommentList(${comitem.id})">답글 보기</button>`
			}
			}
		}
		
}
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
function AddReComment(_gid)
{
	$.ajax({
	url : "../BoardController",
	type : 'put',
	dataType: 'text',
	async:false,
	data : {
		action:"ReComment",
		id : _gid,
		content: document.querySelector(".inputComment").value
		

	},
	success : function(data) {
		location.href= "/awf/BoardInfo/BoardInfo.html?boardId="+new URL(window.location).searchParams.get("boardId")
		
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});
}
