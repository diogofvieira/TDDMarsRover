package br.kata.tdd.KataMarsRover;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


//Requirements
//You are given the initial starting point (x,y) of a rover and the direction (N,S,E,W) it is facing. OK
//The rover receives a character array of commands. OK
//Implement commands that move the rover forward/backward (f,b). OK
//Implement commands that turn the rover left/right (l,r). OK
//Implement wrapping from one edge of the grid to another. (planets are spheres after all) OK
//Implement obstacle detection before each move to a new square. If a given sequence of commands encounters an obstacle, 
//	the rover moves up to the last possible point, aborts the sequence and reports the obstacle. OK

public class RoverTest {
	
	
	private Position startingPosition;
	private Direction startingDirection;
	private PlanetSize planetSize;
	private Rover rover;
	private String commands;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() {
		startingPosition = new Position(0, 0);
		startingDirection = new Direction("N");
		planetSize = new PlanetSize(2, 2);
		rover = new Rover(startingPosition, startingDirection, planetSize);
		commands = "f";
	}
	
	@Test
	public void testStartingPoint(){
		
		assertThat(rover.position(), equalTo(startingPosition));
		assertThat(rover.direction(), equalTo(startingDirection));
		
	}

	@Test
	public void testRoverLeftRotation(){

		assertThat(rover.movingLeft(), equalTo(new Direction("E")));

	}
	
	@Test
	public void testRoverRightRotation(){
		
		assertThat(rover.movingRight(), equalTo(new Direction("W")));

	}
	
	@Test
	public void testRoverMovingForward() throws MovingException {

		assertThat(rover.movingForward(), equalTo(new Position(0, 2)));

	}
	
	@Test
	public void testRoverMovingBackward() throws MovingException {

		assertThat(rover.movingBackward(), equalTo(new Position(0, 1)));

	}
	
	@Test
	public void testRoverWrappingXAxisEdge() throws MovingException {

		assertThat(new Rover(new Position(0,0), new Direction("W"), new PlanetSize(2,2))
							.commands(commands), equalTo(new Position(2,0)));

	}

	@Test
	public void testRoverWrappingYAxisEdge() throws MovingException {

		assertThat(rover.commands(commands), equalTo(new Position(0,2)));

	}

	@Test
	public void testRoverObstacleDetectionExpected() throws MovingException{

		rover.setObstaclePosition(new Position(0,2));
		
		exception.expect(MovingException.class);
		exception.expectMessage("Obstacle Detected");
		
		assertThat(rover.commands(commands), equalTo(new Position(0,2)));
		
	}
	
}
