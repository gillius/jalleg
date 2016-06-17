package org.gillius.jalleg.example;

import com.sun.jna.ptr.FloatByReference;
import org.gillius.jalleg.binding.ALLEGRO_COLOR;
import org.gillius.jalleg.binding.ALLEGRO_FONT;
import org.gillius.jalleg.binding.ALLEGRO_MOUSE_STATE;
import org.gillius.jalleg.binding.ALLEGRO_TRANSFORM;
import org.gillius.jalleg.framework.AllegroAddon;
import org.gillius.jalleg.framework.Direction;
import org.gillius.jalleg.framework.Game;
import org.gillius.jalleg.framework.audio.Beeper;
import org.gillius.jalleg.framework.math.Point;
import org.gillius.jalleg.framework.math.Rect;

import java.util.Random;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

/**
 * Example ball and paddle game taking advantage of the jalleg-framework. This version is built with a design that could
 * support a larger game. See {@link BallAndPaddleGameSingleFile} for a version using only the pure Allegro API.
 */
public class BallAndPaddleGame extends Game {

	private ALLEGRO_COLOR white;
	private ALLEGRO_FONT font;

	private ALLEGRO_TRANSFORM worldTransform;
	private ALLEGRO_TRANSFORM worldInverseTransform;

	private Rect leftPlayer;
	private Rect rightPlayer;
	private Rect ball;

	private Rect board;

	private Random rnd = new Random();
	private float balldX;
	private float balldY;

	private int leftScore;
	private int rightScore;

	private Beeper beeper;

	public BallAndPaddleGame() {
		setAutoResize(true);
	}

	@Override
	protected void onAllegroStarted() {
		initAddons(AllegroAddon.Primitives, AllegroAddon.Font, AllegroAddon.Keyboard, AllegroAddon.Joystick,
		           AllegroAddon.Mouse, AllegroAddon.Audio);
		al_reserve_samples(1);

		white = al_map_rgb_f(1f, 1f, 1f);
		font = al_create_builtin_font();

		leftPlayer = new Rect(4.5f, 45, 1, 10);
		rightPlayer = new Rect(94.5f, 45, 1, 10);

		board = new Rect(0, 0, 100, 100);

		beeper = new Beeper();
		beeper.setGain(0.1f);

		worldTransform = new ALLEGRO_TRANSFORM();
		worldInverseTransform = new ALLEGRO_TRANSFORM();

		resetBall();
	}

	private void resetBall() {
		ball = new Rect(49.5f, 49.5f, 1, 1);
		balldX = 0.25f * (rnd.nextBoolean() ? 1f : -1f);
		balldY = 0.25f * (rnd.nextBoolean() ? 1f : -1f);
	}

	@Override
	protected void update() {
		float PADDLE_SPEED = 1f;
		boolean useMouse = isMouseButtonDown(0);
		Point mousePos = null;
		if (useMouse) {
			al_copy_transform(worldInverseTransform, worldTransform);
			al_invert_transform(worldInverseTransform);
			mousePos = getMousePosTransformed(worldInverseTransform);
		}

		if (isKeyDown(ALLEGRO_KEY_A) || (useMouse && mousePos.x < 50 && mousePos.y < leftPlayer.centerY()))
			leftPlayer.move(0f, -PADDLE_SPEED);
		if (isKeyDown(ALLEGRO_KEY_Z) || (useMouse && mousePos.x < 50 && mousePos.y > leftPlayer.centerY()))
			leftPlayer.move(0f, PADDLE_SPEED);

		if (isKeyDown(ALLEGRO_KEY_UP) || isJoyDirection(Direction.Up) || (useMouse && mousePos.x > 50 && mousePos.y < rightPlayer.centerY()))
			rightPlayer.move(0f, -PADDLE_SPEED);
		if (isKeyDown(ALLEGRO_KEY_DOWN) || isJoyDirection(Direction.Down) || (useMouse && mousePos.x > 50 && mousePos.y > rightPlayer.centerY()))
			rightPlayer.move(0f, PADDLE_SPEED);

		ball.move(balldX, balldY);

		if (ball.collidesWith(rightPlayer)) {
			beeper.beep(200, gameTime + 0.1);
			balldX *= -1.2f;
		} else if (ball.collidesWith(leftPlayer)) {
			beeper.beep(150, gameTime + 0.1);
			balldX *= -1.2f;
		}

		if (ball.y <= board.y ||
				ball.bottom() >= board.bottom()) {
			balldY *= -1;
			beeper.beep(125, gameTime + 0.1);
		}

		if (ball.right() > board.right()) {
			leftScore++;
			resetBall();
		}

		if (ball.x < board.x) {
			rightScore++;
			resetBall();
		}

		leftPlayer.constrainWithin(board);
		rightPlayer.constrainWithin(board);

		beeper.update(gameTime);
	}

	@Override
	protected void render() {
		al_identity_transform(worldTransform);
		al_scale_transform(worldTransform, getDisplayWidth() / 100f, getDisplayHeight() / 100f);
		al_use_transform(worldTransform);

		draw(leftPlayer);
		draw(rightPlayer);
		draw(ball);

		//Draw the line
		al_draw_line(50f, 0f, 50f, 100f, white, 1f);

		//Draw the scores
		al_draw_text(font, white, 25f, 0f, ALLEGRO_ALIGN_CENTRE, String.valueOf(leftScore));
		al_draw_text(font, white, 75f, 0f, ALLEGRO_ALIGN_CENTRE, String.valueOf(rightScore));
	}

	private void draw(Rect rect) {
		al_draw_filled_rectangle(rect.left(), rect.top(), rect.right(), rect.bottom(), white);
	}

	@Override
	protected void onStopped() {
		try {
			beeper.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		new BallAndPaddleGame().run();
	}
}
