
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
		//CommentPirnt(JSON.parse(data.commentList),lgu,0)
		InitComment(JSON.parse(data.commentList),lgu,0)

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
		InitComment(JSON.parse(data.commentList),data.loginUser,_parid)
		//CommentPirnt(JSON.parse(data.commentList),data.loginUser,_parid)
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});
}
function InitComment(_commentList,_logUser,_parid)
{
	let comarea
	if(_parid == 0)
	 {
		 comarea= document.querySelector('.comment-list')
	}else{
		comarea = document.querySelector('.com-'+_parid).querySelector('.recommentlist-box')
		comarea.style.marginLeft = "50px"
		comarea.style.display = "block"
		
	}
	for(item of _commentList)
	{
		let _html=``
		_html += `
		<div class="comment com-${item.id}">
       <span class="comment-writer"> 작성자 : ${ item.writer} </span>|
       <span class="comment-createdate"> 작성 날짜: ${ item.createdate} </span>
       <div class="comment-content">
       	${item.content}
       </div>
       <div class="comment-btn-list">`
       if(_logUser == item.writer)
       {
		   _html += `<button onclick="EnableModifyBox(${ item.id})" class="comment-enable-modify"> 수정 </button>
       		<button onclick="DeleteComment(${ item.id})" class="comment-delete"> 삭제 </button>`
       	}
       
       _html +=`
       <button onclick="EnableReComment(${item.id})" class="enable-recomment"> 답글달기 </button>`
       if(item.anscnt > 0)
       {
       	 _html += `<button onclick="GetReCommentList(${item.id})" class="comment-get-recomment"> 답글보기 </button>`
       	}
       	
       _html += `</div>
       <div class="recomment-box">
       <textarea placeholder="답글을 입력하세요" class="inputReComment"></textarea>
       <button onclick="AddReComment(${item.id})"> 답글 작성 </button>
       <button onclick="disableRecomment(${item.id})"> 취소 </button>
        </div>
        <div class="modify-box">
        	<textarea class="ModifiyComment"> ${item.content}</textarea>
        	<button onclick="modifyComment(${ item.id})"> 수정 완료</button>
        	<button onclick="disableModify(${ item.id})"> 취소 </button>
        </div>
        <div class="recommentlist-box">

        	</div>	
        </div>`
		comarea.innerHTML+= _html
	}
}
function modifyComment(_id)
{
	$.ajax({
	url : "../BoardController",
	type : 'put',
	dataType: 'text',
	async:false,
	data : {
		action:"ModiyfyComment",
		id : _id,
		content : document.querySelector('.com-'+_id).querySelector('.ModifiyComment').value

	},
	success : function(data) {
		location.href= "/awf/BoardInfo/BoardInfo.html?boardId="+new URL(window.location).searchParams.get("boardId")
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});

}
function DeletePost(gid)
{
	$.ajax({
	url : "../BoardController",
	type : 'delete',
	dataType: 'json',
	async:false,
	data : {
		action:"Board",
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


function SubmitComment()
{
	$.ajax({
	url : "../BoardController",
	type : 'put',
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
		action:"Comment",
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
		content: document.querySelector(".com-"+_gid).querySelector('.inputReComment').value
		

	},
	success : function(data) {
		location.href= "/awf/BoardInfo/BoardInfo.html?boardId="+new URL(window.location).searchParams.get("boardId")
		
     },
     
	error : function(e,b) {
		console.log(e,b)
	}
	});
}


function UpdatePost(gid)
{
	location.href= "/awf/BoardModify/BoardModify.html?boardId="+gid
}
function EnableModifyBox(_id)
{
	document.querySelector('.com-'+_id).querySelector('.modify-box').style.display = "block";
}
function disableModify(_id)
{
	document.querySelector('.com-'+_id).querySelector('.modify-box').style.display = "none";
}
function EnableReComment(_id)
{
	document.querySelector('.com-'+_id).querySelector('.recomment-box').style.display = "block";
}
function disableRecomment(_id)
{
	document.querySelector('.com-'+_id).querySelector('.recomment-box').style.display = "none";
}