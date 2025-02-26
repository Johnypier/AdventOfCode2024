package advent.of.code;

import advent.of.code.util.AdventOfCodeUtils;
import advent.of.code.util.Direction;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;

final class DaySix {
    static final List<char[]> input = AdventOfCodeUtils.readAllFileLines("inputD6.txt")
                                                       .stream()
                                                       .map(String::toCharArray)
                                                       .toList();

    public static void main(String[] args) {
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part one: " + DaySix.solutionPartOne());
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part two: " + DaySix.solutionPartTwo());
    }

    static int solutionPartOne() {
        HashSet<Position> visitedPositions = new HashSet<>();

        Position guardCurrentPosition = findGuardStartingPosition();

        if (guardCurrentPosition != null) {
            visitedPositions.add(
                    new Position(guardCurrentPosition.x, guardCurrentPosition.y, guardCurrentPosition.direction));
        }

        while (guardCurrentPosition != null) {
            switch (guardCurrentPosition.direction) {
                case UP -> guardCurrentPosition = moveUpDown(visitedPositions, guardCurrentPosition,
                                                             guardCurrentPosition.getY() - 1);
                case BOTTOM -> guardCurrentPosition = moveUpDown(visitedPositions, guardCurrentPosition,
                                                                 guardCurrentPosition.getY() + 1);
                case LEFT -> guardCurrentPosition = moveLeftRight(visitedPositions, guardCurrentPosition,
                                                                  guardCurrentPosition.getX() - 1);
                case RIGHT -> guardCurrentPosition = moveLeftRight(visitedPositions, guardCurrentPosition,
                                                                   guardCurrentPosition.getX() + 1);
                default -> throw new InvalidParameterException("Unknown direction detected!");
            }
        }

        return visitedPositions.size();
    }

    static int solutionPartTwo() {
        return 0;
    }

    static boolean checkYNotOutOfBound(int y) {
        return y >= 0 && y <= input.size() - 1;
    }

    static boolean checkXNotOutOfBound(int x) {
        return x >= 0 && x <= input.get(0).length - 1;
    }

    static Position moveLeftRight(HashSet<Position> visitedPositions, Position guardCurrentPosition,
                                  int updatedX) {
        if (checkXNotOutOfBound(updatedX)) {
            if (input.get(guardCurrentPosition.getY())[updatedX] == '#') {
                guardCurrentPosition.setDirection(guardTurn(guardCurrentPosition.getDirection()));
            } else {
                guardCurrentPosition.setX(updatedX);
                visitedPositions.add(new Position(guardCurrentPosition.getX(), guardCurrentPosition.getY(),
                                                  guardCurrentPosition.getDirection()));
            }
            return guardCurrentPosition;
        } else {
            return null;
        }
    }

    static Position moveUpDown(HashSet<Position> visitedPositions, Position guardCurrentPosition,
                               int updatedY) {
        if (checkYNotOutOfBound(updatedY)) {
            if (input.get(updatedY)[guardCurrentPosition.getX()] == '#') {
                guardCurrentPosition.setDirection(guardTurn(guardCurrentPosition.getDirection()));
            } else {
                guardCurrentPosition.setY(updatedY);
                visitedPositions.add(new Position(guardCurrentPosition.getX(), guardCurrentPosition.getY(),
                                                  guardCurrentPosition.getDirection()));
            }
            return guardCurrentPosition;
        } else {
            return null;
        }
    }

    static Direction guardTurn(Direction previousDirection) {
        return switch (previousDirection) {
            case UP -> Direction.RIGHT;
            case BOTTOM -> Direction.LEFT;
            case RIGHT -> Direction.BOTTOM;
            case LEFT -> Direction.UP;
            default -> throw new IllegalStateException("Unknown direction detected!");
        };
    }

    static Position findGuardStartingPosition() {
        for (int i = 0; i < input.size(); i++) {
            char[] line = input.get(i);

            for (int j = 0; j < line.length - 1; j++) {
                if (line[j] == '^') {
                    return new Position(j, i, Direction.UP);
                }
            }
        }

        return null;
    }

    static class Position {
        private int y;
        private int x;
        private Direction direction;

        public Position(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        @Override
        public final boolean equals(Object o) {
            if (!(o instanceof Position position)) {
                return false;
            }

            return getY() == position.getY() && getX() == position.getX();
        }

        @Override
        public int hashCode() {
            int result = getY();
            result = 31 * result + getX();
            return result;
        }

        @Override
        public String toString() {
            return "Position{" +
                   "y=" + y +
                   ", x=" + x +
                   ", direction=" + direction +
                   '}';
        }
    }
}
