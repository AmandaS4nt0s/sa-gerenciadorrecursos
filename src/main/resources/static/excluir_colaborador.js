<script>
    function excluirColaborador(id) {
        if (!confirm('Deseja realmente excluir este colaborador?')) {
            return;
        }

        fetch('/colaboradorexcluir/' + id, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Colaborador excluído com sucesso!');
                window.location.reload();
            } else {
                alert('Erro ao excluir colaborador.');
            }
        })
        .catch(error => {
            console.error(error);
            alert('Erro ao excluir colaborador.');
        });
    }
</script>