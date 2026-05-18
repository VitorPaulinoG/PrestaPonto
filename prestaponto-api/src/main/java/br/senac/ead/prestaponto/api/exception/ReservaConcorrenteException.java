package br.senac.ead.prestaponto.api.exception;

public class ReservaConcorrenteException extends RuntimeException {

    public ReservaConcorrenteException() {
        super("Este horário acabou de ser reservado por outro cliente. Por favor, escolha outro horário.");
    }
}