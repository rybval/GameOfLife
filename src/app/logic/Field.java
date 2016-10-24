package app.logic;

import java.util.HashSet;
import java.util.Set;

class Field {
    private final Unit[][] field;
    private final int width;
    private final int height;
    private Set<Unit> alive_units_set;

    // Constructors
    Field(int width, int height) {
        this.width = width;
        this.height = height;
        field = new Unit[width][height];

        alive_units_set = new HashSet<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                field[x][y] = new Unit(x, y, this);
            }
        }
    }

    // Getters
    int getWidth() {
        return width;
    }

    Unit getUnit(int x, int y) {
        return field[Math.abs((x + width) % width)][Math.abs((y + height) % height)];
    }

    // Setters
    int getHeight() {
        return height;
    }

    // Utility methods
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Field clone() {
        Field field_clone = new Field(this.width, this.height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                field_clone.field[x][y] = this.field[x][y].clone(field_clone);
            }
        }
        return field_clone;
    }

    int getHash() {
        int result = 0;
        int counter = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int tmp_result = Integer.rotateLeft(field[x][y].getHash(), counter++);
                result += tmp_result;
            }
        }
        return result;
    }

    int getAliveUnitsCount() {
        return alive_units_set.size();
    }

    Set<Unit> getAliveUnits() {
        return alive_units_set;
    }

    void bornUnit(Unit unit) {
        alive_units_set.add(unit);
    }

    void killUnit(Unit unit) {
        alive_units_set.remove(unit);
    }

    void OR(Field another_field) {
        for (Unit unit : another_field.getAliveUnits()) {
            bornUnit(unit);
            getUnit(unit.getX(), unit.getY()).born();
        }
    }
}
