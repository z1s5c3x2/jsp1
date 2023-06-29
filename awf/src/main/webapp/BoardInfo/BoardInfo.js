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
                <span id="postViews">조회수:${data.viewCount}</span>
		`
		
     },
	error : function(e) {
		console.log(e+"?")
	}
	});
