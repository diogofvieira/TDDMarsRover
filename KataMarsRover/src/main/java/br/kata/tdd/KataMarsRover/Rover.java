package br.kata.tdd.KataMarsRover;

public class Rover {

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

	public Direction movingLeft(String rotation) {
		for (int i = 0; i < rotation.length(); i++) {
			if(direction.equals(new Direction("N"))) {
				direction = new Direction("E");
			}else if(direction.equals(new Direction("E"))) {
				direction = new Direction("S");
			} else if(direction.equals(new Direction("S"))) {
				direction = new Direction("W");
			}else {
				direction = new Direction("N");
			}
		}
		return direction;
	}

	public Direction movingRigth(String rotation) {
		for (int i = 0; i < rotation.length(); i++) {
			if(direction.equals(new Direction("N"))) {
				direction = new Direction("W");
			}else if (direction.equals(new Direction("W"))) {
				direction = new Direction("S");
			}else if (direction.equals(new Direction("S"))) {
				direction = new Direction("E");
			}else {
				direction = new Direction("N");
			}
		}
		return direction;
	}

	public Position movingFoward(String moviment) throws MovingException {
		for (int i = 0; i < moviment.length(); i++) {
			
			if(direction.equals(new Direction("N"))) {
				if(position.getY() == 0){
					position = checkObstacle(new Position(position.getX(), planetSize.getSizeY()));
				}else {
					position = checkObstacle(new Position(position.getX(), position.getY() - 1));
				}
			}
			else if(direction.equals(new Direction("S"))) 
			{
				if(position.getY() == planetSize.getSizeY()){
					position = checkObstacle(new Position(position.getX(), 0));
				}else {
					position = checkObstacle(new Position(position.getX(), position.getY() + 1));
				}
			}
			else if(direction.equals(new Direction("W"))) 
			{
				if(position.getX() == 0){
					position = checkObstacle(new Position(planetSize.getSizeX(), position.getY()));
				}else {
					position = checkObstacle(new Position(position.getX() -1, position.getY()));
				}
			}
			else 
			{
				if(position.getX() == planetSize.getSizeX()) {
					position = checkObstacle(new Position(0, position.getY()));
				}else {
					position = checkObstacle(new Position(position.getX() +1, position.getY()));
				}
			}
			
		}
		return position;
	}

	public Position movingbackward(String moviment) throws MovingException {
		for (int i = 0; i < moviment.length(); i++) {

			if(direction.equals(new Direction("N"))) {
				if(position.getY() == planetSize.getSizeY()){
					position = checkObstacle(new Position(position.getX(), 0));
				}else {
					position = checkObstacle(new Position(position.getX(), position.getY() + 1));
			}
				
			}else if(direction.equals(new Direction("S"))) {
				if(position.getY() == 0){
					position = checkObstacle(new Position(position.getX(), planetSize.getSizeY()));
				}else {
					position = checkObstacle(new Position(position.getX(), position.getY() - 1));
				}
			}else if(direction.equals(new Direction("W"))) {
				if(position.getX() == planetSize.getSizeX()){
					position = checkObstacle(new Position(0, position.getY()));
				}else {
					position = checkObstacle(new Position(position.getX() +1, position.getY()));
				}
				
			}else {
				if(position.getX() == 0) {
					position = checkObstacle(new Position(planetSize.getSizeX(), position.getY()));
				}else {
					position = checkObstacle(new Position(position.getX() -1, position.getY()));
				}
			}
		}
		return position;
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
			if(command == 'f') {
				position = movingFoward("f");
			}else if(command == 'r') {
				direction = movingRigth("r");
			}else if(command == 'l') {
				direction = movingLeft("l");
			}else {
				position = movingbackward("b");
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
