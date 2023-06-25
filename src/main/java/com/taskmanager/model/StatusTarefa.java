package com.taskmanager.model;

public enum StatusTarefa {
    concluido ("Concluído"),
    naoConcluido ("Não Concluído");

    private final String status;
    StatusTarefa(String statusTarefa) {
        status = statusTarefa;
    }

    public String getStatus(){
        return status;
    }
}
