/**
 * let title = document.getElementById("title").value;
    let content = document.getElementById("content").value;
    let author = document.getElementById("author").value;
 */

 
 function SendPost()
 {
	 $.ajax({
	url : "../BoardController",
	type : 'get',
	dataType: 'json',
	async:false,
	data : {
		
		action : "Write",
		 title : document.getElementById("title").value,
    	 content : document.getElementById("content").value,

	},
	success : function(data) {
		
		window.location.href ="/awf/Board/Board.html";
     },
	error : function(e) {
		console.log(e)
	}
	});
	
	
 }