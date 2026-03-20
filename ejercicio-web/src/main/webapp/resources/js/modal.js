
function showModal() {
    document.getElementById('detailModal').style.display = 'flex';
}

function hideModal() {
    document.getElementById('detailModal').style.display = 'none';
}

document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') hideModal();
});

function handleModalEvent(data) {
    console.log('Estado ajax:', data.status);
    if (data.status === 'complete') {
        setTimeout(showModal, 50);
    }
}