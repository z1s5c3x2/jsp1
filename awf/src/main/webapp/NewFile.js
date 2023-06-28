/**
 * 
 */

 function asd()
 {
	 let _id = document.querySelector(".SignId").value
	 let _pwd = document.querySelector(".SignPwd").value
	 
	 $.ajax({
	url : "test",
	type : 'post',
	data : {
		userId : _id,
		userPwd : _pwd,
	},
	success : function(data) {
				
     },
	error : function() {
		alert("error");
	}
});
 }