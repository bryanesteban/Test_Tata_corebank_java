package ec.com.corebank.banquito.Util;

public enum transaccionTipo {

    DEPOSITO("DEPOSITO", 1),
    RETIRO("RETIRO", -1);

    private final String nombre;
    private final int formula;

    transaccionTipo(String nombre, int formula) {
        this.nombre = nombre;
        this.formula = formula;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFormula() {
        return formula;
    }
}
