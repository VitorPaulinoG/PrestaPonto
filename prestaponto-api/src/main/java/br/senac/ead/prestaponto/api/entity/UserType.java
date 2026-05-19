package br.senac.ead.prestaponto.api.entity;

public enum UserType {
    CLIENT("client"),
    PROVIDER("provider");

    private final String description;

    UserType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
