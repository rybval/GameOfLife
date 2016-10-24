package app.logic;

import java.util.HashSet;
import java.util.Set;

public class Game {
    private Field next_generation;
    private Field previous_generation;
    private Field visited_map;
    private Set<Integer> generation_hash;

    public Game(int width, int height) {
        next_generation = new Field(width, height);
        previous_generation = new Field(width, height);
        generation_hash = new HashSet<>();
        visited_map = new Field(width, height);
    }

    public boolean doStep() {
        visited_map.OR(next_generation);
        previous_generation = next_generation.clone();
        Set<Unit> units_for_update = getUnitsForUpdate();
        if (!generation_hash.add(next_generation.getHash())) {
            return false; // if next generation duplicated one of previous generations game ends
        }

        for (Unit unit : units_for_update) {
            int alive_neighbours = previous_generation.getUnit(unit.getX(), unit.getY()).getAliveNeighboursCount();
            if (unit.isAlive()) {
                if (alive_neighbours < 2 || alive_neighbours > 3) {
                    killUnit(unit.getX(), unit.getY());
                }
            } else {
                if (alive_neighbours == 3) {
                    bornUnit(unit.getX(), unit.getY());
                }
            }
        }

        return !(next_generation.getAliveUnitsCount() == 0); // if there are no alive units the game ends
    }

    public boolean isUnitAlive(int x, int y) {
        return next_generation.getUnit(x, y).isAlive();
    }

    public boolean isUnitWasAlive(int x, int y) {
        return visited_map.getUnit(x, y).isAlive();
    }

    public void bornUnit(int x, int y) {
        Unit unit_tmp = next_generation.getUnit(x, y);
        unit_tmp.born();
        next_generation.bornUnit(unit_tmp);
    }

    public void killUnit(int x, int y) {
        Unit unit_tmp = next_generation.getUnit(x, y);
        unit_tmp.kill();
        next_generation.killUnit(unit_tmp);
    }

    public int getFieldWidth() {
        return next_generation.getWidth();
    }

    public int getFieldHeight() {
        return next_generation.getHeight();
    }

    private Set<Unit> getUnitsForUpdate() {
        Set<Unit> result = new HashSet<>(next_generation.getAliveUnits());
        for (Unit tmp_unit : next_generation.getAliveUnits()) {
            for (int i = -1; i <= 1; i++) {
                result.add(next_generation.getUnit(tmp_unit.getX() + i, tmp_unit.getY() + 1));
                result.add(next_generation.getUnit(tmp_unit.getX() + i, tmp_unit.getY() - 1));
            }

            result.add(next_generation.getUnit(tmp_unit.getX() - 1, tmp_unit.getY()));
            result.add(next_generation.getUnit(tmp_unit.getX() + 1, tmp_unit.getY()));
        }

        return result;
    }

    public void reset() {
        int width = next_generation.getWidth();
        int height = next_generation.getHeight();
        next_generation = new Field(width, height);
        previous_generation = new Field(width, height);
        generation_hash = new HashSet<>();
    }
}
