// 게시글 배열
//  { title: '두 번째 게시글', content: '두 번째 게시글 내용입니다.' },
let posts = [
];

let tableInit = `<table>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>내용</th>
            <th>날짜</th>
            <th>조회수</th>
        </tr>
    </table>`
    

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
function WritePost()
{
	location.href="/awf/WritePost/WritePost.html"
}
function PageControll(getPage)
{
	if(totalPages <getPage)
	{
		location.href = "/awf/Board/Board.html?page="+totalPages
		alert("마지막페이지입니다")
		return
	}
	else if(getPage == 0)
	{
		location.href = "/awf/Board/Board.html?page=1"
		alert("첫번째페이지입니다")
		return
	}
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
		
	}
	});
}

// 페이지 로드 시 게시글 목록 업데이트
//window.addEventListener('load', function() {
//  updatePostList();
//});
let totalPages = 0
let viewByPage = 3
let currentPage = new URL(window.location).searchParams.get("page")
$.ajax({
	url : "../BoardController",
	type : 'get',
	dataType: 'text',
	async:false,
	data : {
		
		action : "maxpage"

	},
	success : function(data) {
		totalPages = Math.ceil(Number(data)/viewByPage)
		PageNumbering()
     },
	error : function(e) {
		console.log(e)
	}
	});
function PageNumbering()
{
	
	

	let paginationList = document.getElementById("pagination-list");
    //<button onclick="PageControll(-1)"> < </button>
    //<button onclick="PageControll(1)"> > </button>	
    let startP = parseInt((currentPage-1)/viewByPage)*viewByPage+1
    let endP = startP + viewByPage
    if(endP>totalPages)
    {
		endP=totalPages+1
	}
	let pli = document.createElement("li");
     let pa = document.createElement("a");
     pa.href = "?page="+(Number(currentPage)-1)
     pa.innerText ="이전"
     pli.appendChild(pa);
       pli.appendChild(pa);
      paginationList.appendChild(pli);
    for (var i = startP ; i < endP; i++) 
    {
      var li = document.createElement("li");
      var a = document.createElement("a");
      a.href = "?page="+i;
      a.innerText = i;
      if (i == currentPage) {
        a.className = "active";
      }
      li.appendChild(a);
      paginationList.appendChild(li);
    }
    pli = document.createElement("li");
     pa = document.createElement("a");
     pa.href = "?page="+(Number(currentPage)+1)
     pa.innerText ="다음"
     pli.appendChild(pa);
       pli.appendChild(pa);
      paginationList.appendChild(pli);
}

PageControll(currentPage)
//console.log()

/*$.ajax({
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
	});*/

