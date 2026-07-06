package com.senai.sa_gerenciadorrecursos.sessao;

public class SessaoDto {

    private long usuarioid;
    private String usuarioNome;

    public SessaoDto() {
    }

    public long getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(long usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }
}
