/**
 * 
 */
let tmplist;

$.ajax({
	url : "SaltService",
	type : 'post',

	success : function(data) {
		
     },
	error : function(e) {
		console.log(e)
	}
});
 function SignUp()
 {
	 
	 let _id = document.querySelector(".SignId").value
	 let _pwd = document.querySelector(".SignPwd").value
	 
	$.ajax({
	url : "LoginController",
	type : 'post',
	async:false,
	data : {
		
		action : "SignUp",
		userId : _id,
		userPwd : _pwd,
	},
	success : function(data) {
			let Jdata = JSON.parse(data)
				alert(Jdata.msg)
     },
	error : function() {
		alert("error");
	}
	});
	
	
 }
 
function Login()
 {
	 let _id = document.querySelector(".LoginId").value
	 let _pwd = document.querySelector(".LoginPwd").value
	 
	 $.ajax({
	url : "LoginController",
	type : 'post',
	data : {
		action : "Login",
		userId : _id,
		userPwd : _pwd,
		async : false
	},
	success : function(data) {
		
		let Jdata = JSON.parse(data)
		alert(Jdata.msg)
		if(Jdata.isSuccess)
		{
			location.href= "/awf/Board/Board.html"			
		}
		
		
				//document.querySelector(".LoginResult").innerHTML = `${_id}님 로그인 성공`
     },
	error : function() {
		alert("error");
	}
});
 }