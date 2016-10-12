package app.logic;

class Field {
    private Unit[][] field;
    private int width;
    private int height;

    Field(int width, int height) {
        this.width = width;
        this.height = height;
        field = new Unit[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                field[x][y] = new Unit(x, y);
            }
        }
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    Unit getUnit(int x, int y) {
        return field[Math.abs((x + width) % width)][Math.abs((y + height) % height)];
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Field clone() {
        Field clone = new Field(this.width, this.height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                clone.field[x][y] = this.field[x][y].clone();
            }
        }
        return clone;
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
