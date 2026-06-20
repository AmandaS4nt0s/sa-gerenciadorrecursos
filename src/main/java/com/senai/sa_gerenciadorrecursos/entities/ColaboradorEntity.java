package com.senai.sa_gerenciadorrecursos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public class ColaboradorEntity {

    @Entity
    @Table(name = "colaborador")
    public static class Colaborador {

        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "nome")
        @NotBlank(message = "O nome é obrigatório.")
        private String nome;
        @Column(name = "email",unique = true)
        private String email;
        @Column(name = "senha")
        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 5,message = "A senha deve ter no mínimo 5 caracteres")
        private String senha;
        @Column(name = "matricula")
        @NotBlank(message = "A matrícula é obrigatória.")
        private String matricula;
        @PastOrPresent(message = "A data de nascimento não pode estar no futuro.")
        private LocalDate dataNascimento;

        public Colaborador() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getMatricula() {
            return matricula;
        }

        public void setMatricula(String matricula) {
            this.matricula = matricula;
        }

        public LocalDate getDataNascimento() {
            return dataNascimento;
        }

        public void setDataNascimento(LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
        }
    }
}
