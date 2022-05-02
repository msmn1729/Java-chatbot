public enum MathOperation {
    PLUS("+"), MINUS("-"), MULTI("*"), DIV("/");

    private final String operation;

    MathOperation(String operation) {
        this.operation = operation;
    }

    public static MathOperation forName(String operation) {
        switch (operation) {
            case "+":
                return PLUS;
            case "-":
                return MINUS;
            case "*":
                return MULTI;
            case "/":
                return DIV;
        }
        return null;
    }

    public String getOperation() {
        return operation;
    }
}
