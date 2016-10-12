package app.logic;

class Unit {
    private boolean alive;
    private int x;
    private int y;
    private int alive_neighbours;

    Unit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    boolean isAlive() {
        return alive;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getAliveNeighboursCount() {
        return alive_neighbours;
    }

    void neighbourDied() {
        alive_neighbours--;
    }

    void neighbourBorn() {
        alive_neighbours++;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Unit clone() {
        Unit clone = new Unit(this.x, this.y);
        clone.setAlive(this.alive);
        clone.alive_neighbours = this.alive_neighbours;
        return clone;
    }
}
