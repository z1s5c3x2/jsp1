/**
 * let title = document.getElementById("title").value;
    let content = document.getElementById("content").value;
    let author = document.getElementById("author").value;
 */

 
 function SendPost()
 {
	 $.ajax({
	url : "../BoardController",
	type : 'post',
	dataType: 'text',
	async:false,
	data : {
		
			action : "Write",
		 title : document.getElementById("title").value,
    	 content : document.getElementById("content").value

	},
	success : function(data) {
		
		
     },
	error : function(e) {
		console.log(e)
		alert("실패")	
		
	}
	});
	location.href= "/awf/Board/Board.html?page=1"
 }