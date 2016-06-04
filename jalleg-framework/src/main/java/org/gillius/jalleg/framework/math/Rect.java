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

	public void constrainWithin(Rect other) {
		float dx = 0f;
		float dy = 0f;
		if (leftOf(other))
			dx = other.x - x;
		else if (rightOf(other))
			dx = other.right() - right();

		if (above(other))
			dy = other.y - y;
		else if (below(other))
			dy = other.bottom() - bottom();

		move(dx, dy);
	}

	public boolean leftOf(Rect other) {
		return x < other.x;
	}

	public boolean rightOf(Rect other) {
		return right() > other.right();
	}

	public boolean outsideX(Rect other) {
		return leftOf(other) || rightOf(other);
	}

	public boolean above(Rect other) {
		return y < other.y;
	}

	public boolean below(Rect other) {
		return bottom() > other.bottom();
	}

	public boolean outsideY(Rect other) {
		return above(other) || below(other);
	}

	public boolean outside(Rect other) {
		return outsideX(other) || outsideY(other);
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
