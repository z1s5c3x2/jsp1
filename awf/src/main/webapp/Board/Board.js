// 게시글 배열
//  { title: '두 번째 게시글', content: '두 번째 게시글 내용입니다.' },
let posts = [
];


$.ajax({
	url : "LoginController",
	type : 'get',
	async:false,
	data : {
		
		action : "BoardGetList",

	},
	success : function(data) {
			let Jdata = JSON.parse(data)
				alert(Jdata.msg)
     },
	error : function() {
		alert("error");
	}
	});
// 게시글 목록 업데이트 함수
function updatePostList() {
  const postList = document.getElementById('post-list');
  postList.innerHTML = '';

  for (let i = 0; i < posts.length; i++) {
    const post = posts[i];

    const postDiv = document.createElement('div');
    postDiv.classList.add('post');

    const titleElement = document.createElement('h2');
    titleElement.textContent = post.title;

    const contentElement = document.createElement('p');
    contentElement.textContent = post.content;

    postDiv.appendChild(titleElement);
    postDiv.appendChild(contentElement);

    postList.appendChild(postDiv);
  }
}

// 페이지 로드 시 게시글 목록 업데이트
window.addEventListener('load', function() {
  updatePostList();
});


