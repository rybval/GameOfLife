package app.logic;

class Field {
    private Unit[][] field;
    private int width;
    private int height;

    // Constructors
    Field(int width, int height) {
        this.width = width;
        this.height = height;
        field = new Unit[width][height];

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

    boolean isEquals(Field obj) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (field[x][y].isAlive() != obj.field[x][y].isAlive())
                    return false;
            }
        }
        return true;
    }
}
