package app.logic;

class Unit {
    private boolean alive;
    private final int x;
    private final int y;
    private int alive_neighbours;
    private Field field;

    private static final boolean BORN_UNIT = true;
    private static final boolean KILL_UNIT = false;

    // Constructors
    Unit(int x, int y, Field field) {
        this.x = x;
        this.y = y;
        this.field = field;
    }

    // Getters
    int getAliveNeighboursCount() {
        return alive_neighbours;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    boolean isAlive() {
        return alive;
    }

    // Setters
    private void setAlive(boolean alive) {
        this.alive = alive;
    }

    void killUnit() {
        setAlive(KILL_UNIT);
        notifyNeighbours(KILL_UNIT);
    }

    void bornUnit() {
        setAlive(BORN_UNIT);
        notifyNeighbours(BORN_UNIT);
    }

    // Utility methods
    private void notifyNeighbours(boolean alive) {
        for (int i = -1; i <= 1; i++) {
            if (alive == BORN_UNIT) {
                field.getUnit(x + i, y + 1).neighbourBorn();
                field.getUnit(x + i, y - 1).neighbourBorn();
            } else {
                field.getUnit(x + i, y + 1).neighbourDied();
                field.getUnit(x + i, y - 1).neighbourDied();
            }
        }

        if (alive == BORN_UNIT) {
            field.getUnit(x - 1, y).neighbourBorn();
            field.getUnit(x + 1, y).neighbourBorn();
        }
        else {
            field.getUnit(x - 1, y).neighbourDied();
            field.getUnit(x + 1, y).neighbourDied();
        }
    }

    private void neighbourDied() {
        alive_neighbours--;
    }

    private void neighbourBorn() {
        alive_neighbours++;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Unit clone() {
        Unit unit_clone = new Unit(this.x, this.y, this.field);
        unit_clone.setAlive(this.alive);
        unit_clone.alive_neighbours = this.alive_neighbours;
        return unit_clone;
    }

    Unit clone(Field field) {
        Unit unit_clone = this.clone();
        unit_clone.field = field;
        return unit_clone;
    }

    int getHash() {
        return 5 * x + 7 * y + ((alive) ? (11) : (0));
    }
}
