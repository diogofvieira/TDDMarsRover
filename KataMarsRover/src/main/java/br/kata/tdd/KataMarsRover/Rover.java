package br.kata.tdd.KataMarsRover;

public class Rover {

	private static final String AXIS_Y = "Y";
	private static final String AXIS_X = "X";
	private static final String NORTH = "N";
	private static final String EAST = "E";
	private static final String SOUTH = "S";
	private static final String WEST = "W";
	private static final char CHAR_f = 'f';
	private static final char CHAR_r = 'r';
	private static final char CHAR_l = 'l';
	private static final char CHAR_b = 'b';

	private Position position;
	private Direction direction;
	private PlanetSize planetSize;
	private Position obstaclePosition;

	public Rover(Position position, Direction direction, PlanetSize planetSize) {
		this.position = position;
		this.direction = direction;
		this.planetSize = planetSize;
	}

	public Position position() {
		return position;
	}

	public Direction direction() {
		return direction;
	}
	
	public PlanetSize size() {
		return planetSize;
	}

	public Direction movingLeft() {
		if(direction.equals(new Direction(NORTH))) {
			direction = new Direction(EAST);
		}else if(direction.equals(new Direction(EAST))) {
			direction = new Direction(SOUTH);
		} else if(direction.equals(new Direction(SOUTH))) {
			direction = new Direction(WEST);
		}else {
			direction = new Direction(NORTH);
		}
		return direction;
	}

	public Direction movingRight() {
		if(direction.equals(new Direction(NORTH))) {
			direction = new Direction(WEST);
		}else if (direction.equals(new Direction(WEST))) {
			direction = new Direction(SOUTH);
		}else if (direction.equals(new Direction(SOUTH))) {
			direction = new Direction(EAST);
		}else {
			direction = new Direction(NORTH);
		}
		return direction;
	}

	public Position movingForward() throws MovingException {
		if(direction.equals(new Direction(NORTH))) {
			move(0, planetSize.getSizeY(), position.getY() - 1, AXIS_Y);
		}else if(direction.equals(new Direction(SOUTH))){
			move(planetSize.getSizeY(), 0, position.getY() + 1, AXIS_Y);
		}else if(direction.equals(new Direction(WEST))){
			move(0, planetSize.getSizeX(), position.getX() - 1, AXIS_X);
		}else{
			move(planetSize.getSizeX(), 0, position.getX() +1, AXIS_X);
		}
		return position;
	}

	public Position movingBackward() throws MovingException {
		if(direction.equals(new Direction(NORTH))) {
			move(planetSize.getSizeY(), 0, position.getY() + 1, AXIS_Y);
		}
		if(direction.equals(new Direction(SOUTH))) {
			move(0, planetSize.getSizeY(), position.getY() - 1, AXIS_Y);
		}
		if(direction.equals(new Direction(WEST))) {
			move(planetSize.getSizeX(), 0, position.getX() +1, AXIS_X);

		}
		if (direction.equals(new Direction(EAST))){
			move(0, planetSize.getSizeX(), position.getX() -1, AXIS_X);
		}
		return position;
	}

	private void move(int initialPosition, Integer size, int index, String axis) throws MovingException {
		if(axis.equals(AXIS_Y)) {
			if (position.getY() == initialPosition) {
				position = checkObstacle(new Position(position.getX(), size));
			} else {
				position = checkObstacle(new Position(position.getX(), index));
			}
		}else{
			if (position.getX() == initialPosition) {
				position = checkObstacle(new Position(size, position.getY()));
			} else {
				position = checkObstacle(new Position(index, position.getY()));
			}
		}
	}

	private Position checkObstacle(Position checkPosition) throws MovingException {
		if(checkPosition.equals(getObstaclePosition())) {
			throw new MovingException("Obstacle Detected");
		}
		return checkPosition;
	}

	public Position commands(String listCommands) throws MovingException {
		for (int i = 0; i < listCommands.length(); i++) {
			char command = listCommands.charAt(i);
			if(command == CHAR_f) {
				position = movingForward();
			}else if(command == CHAR_r) {
				direction = movingRight();
			}else if(command == CHAR_l) {
				direction = movingLeft();
			}else if(command == CHAR_b){
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
}
