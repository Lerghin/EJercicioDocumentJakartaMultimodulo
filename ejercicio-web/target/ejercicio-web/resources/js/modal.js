
function showModal() {
    document.getElementById('detailModal').style.display = 'flex';
}

function hideModal() {
    document.getElementById('detailModal').style.display = 'none';
}

document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') hideModal();
});