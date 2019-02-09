package br.kata.tdd.KataMarsRover;

public class Rover {

    public enum CardinalDirections {
        NORTH("W", "E", "N"),
        EAST("N", "S", "E"),
        SOUTH("E", "W", "S"),
        WEST("S", "N", "W");

        private String left;
        private String right;
        private String value;

        CardinalDirections(String left, String right, String value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        public static CardinalDirections of(String value) {
            if (value == "N") return NORTH;
            if (value == "W") return WEST;
            if (value == "S") return SOUTH;
            if (value == "E") return EAST;
            throw new IllegalArgumentException("Invalid value");
        }
    }

    private static final String AXIS_Y = "Y";
    private static final String AXIS_X = "X";
    private static final String NORTH = "N";
    private static final String EAST = "E";
    private static final String SOUTH = "S";
    private static final String WEST = "W";
    private static final char FORWARD = 'f';
    private static final char RIGHT = 'r';
    private static final char LEFT = 'l';
    private static final char BACKWARDS = 'b';

    private Position position;
    private CardinalDirections direction;
    private PlanetSize planetSize;
    private Position obstaclePosition;

    public Rover(Position position, Direction direction, PlanetSize planetSize) {
        this.position = position;
        this.direction = CardinalDirections.of(direction.getDirection());
        this.planetSize = planetSize;
    }

    public Position position() {
        return position;
    }

    public Direction direction() {
        return new Direction(direction.value);
    }

    public Position execute(String commands) throws MovingException {
        // loop commands
        // execute each

        for (int i = 0; i < commands.length(); i++) {
            char command = commands.charAt(i);

            if (command == FORWARD) {
                position = movingForward();
            } else if (command == RIGHT) {
                movingRight();
            } else if (command == LEFT) {
                movingLeft();
            } else if (command == BACKWARDS) {
                position = movingBackward();
            }
        }
        return position;
    }

    public Position getObstaclePosition() {
        return obstaclePosition;
    }

    public void setObstaclePosition(Position obstaclePosition) {
        this.obstaclePosition = obstaclePosition;
    }

    private void movingLeft() {
        this.direction = CardinalDirections.of(this.direction.left);
    }

    private void movingRight() {
        this.direction = CardinalDirections.of(this.direction.right);
    }

    private Position movingForward() throws MovingException {
        if (direction.value.equals(NORTH)) {
            move(0, planetSize.getSizeY(), position.getY() - 1, AXIS_Y);
        }
        if (direction.value.equals(SOUTH)) {
            move(planetSize.getSizeY(), 0, position.getY() + 1, AXIS_Y);
        }
        if (direction.value.equals(WEST)) {
            move(0, planetSize.getSizeX(), position.getX() - 1, AXIS_X);
        }
        if (direction.value.equals(EAST)) {
            move(planetSize.getSizeX(), 0, position.getX() + 1, AXIS_X);
        }
        return position;
    }

    private Position movingBackward() throws MovingException {
        if (direction.value.equals(NORTH)) {
            move(planetSize.getSizeY(), 0, position.getY() + 1, AXIS_Y);
        }
        if (direction.value.equals(SOUTH)) {
            move(0, planetSize.getSizeY(), position.getY() - 1, AXIS_Y);
        }
        if (direction.value.equals(WEST)) {
            move(planetSize.getSizeX(), 0, position.getX() + 1, AXIS_X);
        }
        if (direction.value.equals(EAST)) {
            move(0, planetSize.getSizeX(), position.getX() - 1, AXIS_X);
        }
        return position;
    }

    private void move(int initialPosition, Integer size, int index, String axis) throws MovingException {
        if (axis.equals(AXIS_Y)) {
            if (position.getY() == initialPosition) {
                position = checkObstacle(new Position(position.getX(), size));
            } else {
                position = checkObstacle(new Position(position.getX(), index));
            }
        } else {
            if (position.getX() == initialPosition) {
                position = checkObstacle(new Position(size, position.getY()));
            } else {
                position = checkObstacle(new Position(index, position.getY()));
            }
        }
    }

    private Position checkObstacle(Position checkPosition) throws MovingException {
        if (checkPosition.equals(getObstaclePosition())) {
            throw new MovingException("Obstacle Detected");
        }
        return checkPosition;
    }
}
