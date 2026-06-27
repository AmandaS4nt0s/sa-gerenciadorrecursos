<script>
    function excluirRecurso(id) {
        if (!confirm('Deseja realmente excluir este recurso?')) {
            return;
        }

        fetch('/recursoexcluir/' + id, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Recurso excluído com sucesso!');
                window.location.reload();
            } else {
                alert('Erro ao excluir recurso.');
            }
        })
        .catch(error => {
            console.error(error);
            alert('Erro ao excluir recurso.');
        });
    }
</script>