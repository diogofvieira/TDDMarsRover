package br.kata.tdd.KataMarsRover;

public class Position {

	private Integer x;
	private Integer y;

	public Position(Integer x, Integer y) {
		this.setX(x);
		this.setY(y);
	}

	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getX() == null) ? 0 : getX().hashCode());
		result = prime * result + ((getY() == null) ? 0 : getY().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (getX() == null) {
			if (other.getX() != null)
				return false;
		} else if (!getX().equals(other.getX()))
			return false;
		if (getY() == null) {
			if (other.getY() != null)
				return false;
		} else if (!getY().equals(other.getY()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

}
