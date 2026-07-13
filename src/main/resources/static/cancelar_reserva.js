function cancelarReserva(id) {

    const observacao = prompt("Informe a observação do cancelamento:");

    if (observacao === null) {
        return;
    }

    if (observacao.trim() === "") {
        alert("A observação é obrigatória.");
        return;
    }

    const form = document.getElementById("formCancelar" + id);

    const campoObservacao = document.getElementById("observacao" + id);

    campoObservacao.value = observacao;

    form.submit();
}