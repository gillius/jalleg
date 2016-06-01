package org.gillius.jalleg.framework.math;

public class Rect {
	public float x;
	public float y;
	public float w;
	public float h;

	public Rect(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean collidesWith(Rect other) {
		return x < other.x + other.w &&
		       x + w > other.x &&
		       y < other.y + other.h &&
		       y + h > other.y;
	}

	public void move(float dx, float dy) {
		x += dx;
		y += dy;
	}

	public void constrain(Rect other) {
		float dx = 0f;
		float dy = 0f;
		if (x < other.x)
			dx = other.x - x;
		else if (right() > other.right())
			dx = other.right() - right();

		if (y < other.y)
			dy = other.y - y;
		else if (bottom() > other.bottom())
			dy = other.bottom() - bottom();

		move(dx, dy);
	}

	public float left() {
		return x;
	}

	public float top() {
		return y;
	}

	public float right() {
		return x+w;
	}

	public float bottom() {
		return y+h;
	}
}
