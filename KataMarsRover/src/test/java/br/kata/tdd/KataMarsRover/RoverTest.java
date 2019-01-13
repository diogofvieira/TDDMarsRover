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
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() {
		startingPosition = new Position(0, 0);
		startingDirection = new Direction("N");
		planetSize = new PlanetSize(2, 2);
		rover = new Rover(startingPosition, startingDirection, planetSize);
	}
	
	@Test
	public void testStartingPoint(){
		
		assertThat(rover.position(), equalTo(startingPosition));
		assertThat(rover.direction(), equalTo(startingDirection));
		
	}

	@Test
	public void testLeftRotation(){

		assertThat(rover.movingLeft("l"), equalTo(new Direction("E")));
		assertThat(rover.movingLeft("l"), equalTo(new Direction("S")));
		assertThat(rover.movingLeft("l"), equalTo(new Direction("W")));
		assertThat(rover.movingLeft("l"), equalTo(new Direction("N")));
		assertThat(rover.movingLeft("l"), equalTo(new Direction("E")));
		assertThat(rover.movingLeft("ll"), equalTo(new Direction("W")));
		assertThat(rover.movingLeft("ll"), equalTo(new Direction("E")));
		assertThat(rover.movingLeft("lll"), equalTo(new Direction("N")));
		
	}
	
	@Test
	public void testRigthRotation(){
		
		assertThat(rover.movingRigth("r"), equalTo(new Direction("W")));
		assertThat(rover.movingRigth("r"), equalTo(new Direction("S")));
		assertThat(rover.movingRigth("r"), equalTo(new Direction("E")));
		assertThat(rover.movingRigth("r"), equalTo(new Direction("N")));
		assertThat(rover.movingRigth("r"), equalTo(new Direction("W")));
		assertThat(rover.movingRigth("rr"), equalTo(new Direction("E")));
		assertThat(rover.movingRigth("rr"), equalTo(new Direction("W")));
		assertThat(rover.movingRigth("rrr"), equalTo(new Direction("N")));
		
	}
	
	@Test
	public void testMovingFoward() throws MovingException {

		assertThat(rover.movingFoward("f"), equalTo(new Position(0, 2)));

	}
	
	@Test
	public void testMovingBackward() throws MovingException {
		rover.movingLeft("l");
		rover.movingFoward("fff");
		
		assertThat(rover.movingbackward("b"), equalTo(new Position(2, 0)));
	}
	
	@Test
	public void testWrappingEdge() throws MovingException {
		
		assertThat(rover.commands("ffflfrffflfrffflfr"), equalTo(new Position(0,0)));
		assertThat(rover.commands("bbbrblbbbrblbbbrbl"), equalTo(new Position(0,0)));
		assertThat(rover.commands("llfffrflfffrflfffrfr"), equalTo(new Position(0,0)));
		assertThat(rover.commands("rrbbblbrbbblbrbbblbl"), equalTo(new Position(0,0)));
		assertThat(rover.commands("lffflfrffflfrfffrff"), equalTo(new Position(0,0)));
		assertThat(rover.commands("lbbbrblbbbrblbbblbbll"), equalTo(new Position(0,0)));
		assertThat(rover.commands("rfffrflfffrflffflff"), equalTo(new Position(0,0)));
		assertThat(rover.commands("rbbbrflbbbrflbbb"), equalTo(new Position(0,2)));

	}
	
	@Test
	public void testObstacleDetection() throws MovingException{

		Position expectedObstacle = new Position(0,1);
		rover.setObstaclePosition(expectedObstacle);
		
		exception.expect(MovingException.class);
		exception.expectMessage("Obstacle Detected");
		
		assertThat(rover.commands("fff"), equalTo(new Position(0,0)));		
		
	}
	
}
