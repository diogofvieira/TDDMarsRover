package br.kata.tdd.KataMarsRover;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


//Requirements
//You are given the initial starting point (x,y) of a rover and the direction (N,S,E,W) it is facing. OK
//The rover receives a character array of execute. OK
//Implement execute that move the rover forward/backward (f,b). OK
//Implement execute that turn the rover left/right (l,r). OK
//Implement wrapping from one edge of the grid to another. (planets are spheres after all) OK
//Implement obstacle detection before each move to a new square. If a given sequence of execute encounters an obstacle,
//	the rover moves up to the last possible point, aborts the sequence and reports the obstacle. OK

public class RoverTest {

    private static final Direction NORTH = new Direction("N");
    private static final Direction EAST = new Direction("E");
    private static final Direction WEST = new Direction("W");

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Position startingPosition;
    private Direction startingDirection;
    private PlanetSize planetSize;
    private Rover rover;

    @Before
    public void setUp() {
        startingPosition = new Position(0, 0);
        startingDirection = NORTH;
        planetSize = new PlanetSize(2, 2);
        rover = new Rover(startingPosition, startingDirection, planetSize);
    }

    @Test
    public void testStartingPoint() {
        assertThat(rover.position(), equalTo(startingPosition));
        assertThat(rover.direction(), equalTo(startingDirection));
    }

    @Test
    public void testRoverLeftRotation() {
        rover.execute("l");
        assertThat(rover.direction(), equalTo(new Direction("W")));
    }

    @Test
    public void testRoverRightRotation() {
        rover.execute("r");
        assertThat(rover.direction(), equalTo(new Direction("E")));

    }

    @Test
    public void testRoverMovingForward() throws MovingException {
        rover = new Rover(startingPosition, EAST, planetSize);
        rover.execute("f");

        assertThat(rover.position(), equalTo(new Position(1, 0)));
    }

    @Test
    public void testRoverMovingBackward() throws MovingException {
        assertThat(rover.execute("b"), equalTo(new Position(0, 1)));
    }

    @Test
    public void testRoverWrappingXAxisEdge() throws MovingException {
        rover = new Rover(startingPosition, WEST, planetSize);

        assertThat(rover.execute("f"), equalTo(new Position(2, 0)));
    }

    @Test
    public void testRoverWrappingYAxisEdge() throws MovingException {
        assertThat(rover.execute("f"), equalTo(new Position(0, 2)));
    }

    @Test
    public void testRoverDetectsObstacle() throws MovingException {
        exception.expect(MovingException.class);
        exception.expectMessage("Obstacle Detected");

        Position obstacle = new Position(0, 2);
        rover.setObstaclePosition(obstacle);

        rover.execute("f");
    }

    @Test
    public void testMultipleMovements() {
        // 1, 2, 3
        // 4, 5, 6
        // 7, 8, 9
        String commands = "rffrfrfflfrbb";

        rover.execute(commands);

        assertEquals(new Position(2, 2), rover.position());
    }
}
