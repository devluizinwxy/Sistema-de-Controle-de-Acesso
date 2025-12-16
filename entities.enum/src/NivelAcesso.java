public enum NivelAcesso {
    VISITANTE,
    FUNCIONARIO,
    GERENTE,
    ADMINISTRADOR;


    // Verifica se este nível é suficiente para acessar algo
    public boolean temAcessoA(NivelAcesso minimo) {
        return this.ordinal() >= minimo.ordinal();
    }
}