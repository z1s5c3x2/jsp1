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
		
		document.getElementById('title').value = data.item.title
		document.getElementById('content').value = data.item.content
                //<span id="postAuthor">작성자 : ${data.item.writer}</span> |


     },
     
	error : function(e) {
		console.log(e)
	}
	});

	
	
function ModifyPost()
{
	$.ajax({
	url : "../BoardController",
	type : 'get',
	dataType: 'text',
	async:false,
	data : {
		action:"Modify",
		id : new URL(window.location).searchParams.get("boardId"),
		title : document.getElementById('title').value,
		content: document.getElementById('content').value

	},
	success : function(data) {
		 
		alert("수정 완료")
		location.href="/awf/BoardInfo/BoardInfo.html?boardId="+new URL(window.location).searchParams.get("boardId")

     },
     
	error : function(e) {
		console.log(e)
		alert("실패")
	}
	});
	
	
}