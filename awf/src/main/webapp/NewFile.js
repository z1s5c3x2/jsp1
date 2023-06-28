/**
 * 
 */

 function SignUp()
 {
	 let _id = document.querySelector(".SignId").value
	 let _pwd = document.querySelector(".SignPwd").value
	 
	 $.ajax({
	url : "test",
	type : 'post',
	data : {
		action : "SignUp",
		userId : _id,
		userPwd : _pwd,
	},
	success : function(data) {
				document.querySelector(".signresult").innerHTML = `${_id}님 가입 성공`
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
	url : "test",
	type : 'post',
	data : {
		action : "Login",
		userId : _id,
		userPwd : _pwd,
	},
	success : function(data) {
				document.querySelector(".LoginResult").innerHTML = `${_id}님 로그인 성공`
     },
	error : function() {
		alert("error");
	}
});
 }