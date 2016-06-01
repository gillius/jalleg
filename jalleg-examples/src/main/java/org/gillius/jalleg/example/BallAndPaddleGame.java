package org.gillius.jalleg.example;

import org.gillius.jalleg.binding.ALLEGRO_COLOR;
import org.gillius.jalleg.binding.ALLEGRO_FONT;
import org.gillius.jalleg.binding.ALLEGRO_TRANSFORM;
import org.gillius.jalleg.framework.AllegroAddon;
import org.gillius.jalleg.framework.Game;
import org.gillius.jalleg.framework.math.Rect;

import java.util.Random;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

/**
 * Example ball and paddle game taking advantage of the jalleg-framework. This version is built with a design that could
 * support a larger game. See {@link BallAndPaddleGameSingleFile} for a version using only the pure Allegro API.
 */
public class BallAndPaddleGame extends Game {

	private ALLEGRO_COLOR black;
	private ALLEGRO_COLOR white;
	private ALLEGRO_FONT font;

	private Rect leftPlayer;
	private Rect rightPlayer;
	private Rect ball;

	private Rect board;

	private Random rnd = new Random();
	private float balldX;
	private float balldY;

	private int leftScore;
	private int rightScore;

	@Override
	protected void onAllegroStarted() {
		initAddons(AllegroAddon.Primitives, AllegroAddon.Font, AllegroAddon.Keyboard);

		black = al_map_rgb_f(0f, 0f, 0f);
		white = al_map_rgb_f(1f, 1f, 1f);
		font = al_create_builtin_font();

		leftPlayer = new Rect(4.5f, 45, 1, 10);
		rightPlayer = new Rect(94.5f, 45, 1, 10);

		board = new Rect(0, 0, 100, 100);

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
		if (isKeyDown(ALLEGRO_KEY_A))
			leftPlayer.move(0f, -PADDLE_SPEED);
		if (isKeyDown(ALLEGRO_KEY_Z))
			leftPlayer.move(0f, PADDLE_SPEED);

		if (isKeyDown(ALLEGRO_KEY_UP))
			rightPlayer.move(0f, -PADDLE_SPEED);
		if (isKeyDown(ALLEGRO_KEY_DOWN))
			rightPlayer.move(0f, PADDLE_SPEED);

		ball.move(balldX, balldY);

		if (ball.collidesWith(rightPlayer) ||
		    ball.collidesWith(leftPlayer))
			balldX *= -1.2f;

		if (ball.y <= board.y ||
				ball.bottom() >= board.bottom())
			balldY *= -1;

		if (ball.right() > board.right()) {
			leftScore++;
			resetBall();
		}

		if (ball.x < board.x) {
			rightScore++;
			resetBall();
		}

		leftPlayer.constrain(board);
		rightPlayer.constrain(board);
	}

	@Override
	protected void render() {
		ALLEGRO_TRANSFORM t = new ALLEGRO_TRANSFORM();
		al_identity_transform(t);
		al_scale_transform(t, getDisplayWidth() / 100f, getDisplayHeight() / 100f);
		al_use_transform(t);

		al_clear_to_color(black);

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

	public static void main(String[] args) {
		new BallAndPaddleGame().run();
	}
}