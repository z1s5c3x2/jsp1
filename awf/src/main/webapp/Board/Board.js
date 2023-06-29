// 게시글 배열
//  { title: '두 번째 게시글', content: '두 번째 게시글 내용입니다.' },
let posts = [
];

let tableInit = `<table>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>내용</th>
            <th>작성자</th>
            <th>날짜</th>
            <th>조회수</th>
        </tr>
    </table>`
    
$.ajax({
	url : "../BoardController",
	type : 'get',
	dataType: 'json',
	async:false,
	data : {
		
		action : "fisrt",
		page: 0 

	},
	success : function(data) {

		updatePostList(data);
     },
	error : function(e) {
		console.log(e)
	}
	});
// 게시글 목록 업데이트 함수
function updatePostList(getBoardList) {
	
 	document.querySelector(".boardTable").innerHTML =tableInit
 	for(boardItem of getBoardList)
 	{
		 
 		document.querySelector(".boardTable").innerHTML+=`<tr onclick="BoardViewDetail(${boardItem.id})">
            <td id="postNumber">${boardItem.id}</td>
            <td id="postTitle">${boardItem.title}</td>
            <td id="postContent">${boardItem.writer}</td>
            <td id="postAuthor">${boardItem.content}</td>
            <td id="postDate">${boardItem.createDate}</td>
            <td id="postViews">${boardItem.viewCount}</td>
        </tr>`
        
       }
       
       
       posts=[]
       	for(item of getBoardList)
		{
			posts.push(item)
		}
}
function BoardViewDetail(num)
{
	location.href="/awf/BoardInfo/BoardInfo.html?boardId="+num 
}
function PageControll(getPage)
{
	$.ajax({
	url : "../BoardController",
	type : 'get',
	dataType: 'json',
	async:false,
	data : {
		action:"pagecontroll",
		page : getPage,

	},
	success : function(data) {

		updatePostList(data);
     },
	error : function(xhr, textStatus, errorThrown) {
		if(getPage == -1) alert("첫번째페이지입니다")		
		else if(getPage == 1) alert("마지막페이지입니다")
	}
	});
}
// 페이지 로드 시 게시글 목록 업데이트
//window.addEventListener('load', function() {
//  updatePostList();
//});



