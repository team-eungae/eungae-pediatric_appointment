function toggleDeleteButton(element) {
    var deleteForm = element.nextElementSibling;
    var editForm = deleteForm.nextElementSibling;
    deleteForm.style.display = deleteForm.style.display === 'none' ? 'block' : 'none';
    editForm.style.display = editForm.style.display === 'none' ? 'block' : 'none';
    // if (deleteForm.style.display === "none") {
    //     deleteForm.style.display = "block";
    // } else {
    //     deleteForm.style.display = "none";
    // }
}

// AJAX를 통한 게시글 삭제 함수
function deleteArticle(articleId) {
    if (!confirm('이 게시글을 삭제하시겠습니까?')) {
        return;
    }

    fetch('/articles/' + articleId, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                alert('게시글이 삭제되었습니다.');
                window.location.reload(); // 현재 페이지를 다시 로드
            } else {
                alert('게시글 삭제에 실패했습니다.');
            }
        })
        .catch(error => console.error('Error:', error));
}