/**
 * 
 */

 /*
 
			 
  */
 
 
 
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
		document.querySelector(".post-detail").innerHTML =
		`
		<h3 id="postNumber">번호 :${data.id}</h3>
            <h3 id="postTitle">제목 : ${data.title}</h3>
            <p id="postContent">내용 :${data.content}</p>
            <div class="post-meta">
                <span id="postAuthor">작성자 : ${data.writer}</span> |
                <span id="postDate">작성 날짜:${data.createDate}</span> |
                <span id="postViews">조회수:${data.viewCount}</span></div>
		`
		
     },
	error : function(e) {
		console.log(e+"?")
	}
	});

/*


    <div class="comment-edit">
      <textarea></textarea>
      <button onclick="UpdateComment()">수정</button>
      <button onclick="CancelEdit()">취소</button>
    </div>

댓글 수정 창

<div class="comment-actions">
          <button onclick="EditComment()">댓글 수정</button>
          <button onclick="DeleteComment()">댓글 삭제</button>
        </div>
        
 작성자와 로그인한 사람이 일치하면
 */