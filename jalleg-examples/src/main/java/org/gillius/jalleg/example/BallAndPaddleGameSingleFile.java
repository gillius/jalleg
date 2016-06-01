package org.gillius.jalleg.example;

import org.gillius.jalleg.binding.*;
import org.gillius.jalleg.framework.math.Rect;

import java.util.Random;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

/**
 * Example ball and paddle game using purely straight Allegro API, and none of the extra framework classes provided by
 * jalleg. The only exception to this rule is the {@link Rect} class used for logic, which doesn't use or wrap any
 * Allegro APIs. The goal is to demonstrate Allegro API usage in the simplest example of a real game. It is not meant
 * as an example of how to write a larger project (where code would be broken up).
 */
public class BallAndPaddleGameSingleFile {

	private static ALLEGRO_COLOR black;
	private static ALLEGRO_COLOR white;
	private static ALLEGRO_FONT font;

	private static Rect leftPlayer;
	private static Rect rightPlayer;
	private static Rect ball;

	private static Rect board;

	private static Random rnd = new Random();
	private static float balldX;
	private static float balldY;

	private static int leftScore;
	private static int rightScore;

	private static ALLEGRO_DISPLAY mainDisplay;

	private static ALLEGRO_KEYBOARD_STATE keys;

	public static void main(String[] args) {
		al_install_system(ALLEGRO_VERSION_INT, null);
		al_install_keyboard();
		al_init_primitives_addon();
		al_init_font_addon();

		ALLEGRO_EVENT_QUEUE eventQueue = al_create_event_queue();
		ALLEGRO_TIMER mainTimer = al_create_timer(1.0 / 60.0);
		mainDisplay = al_create_display(640, 480);

		al_register_event_source(eventQueue, al_get_timer_event_source(mainTimer));
		al_register_event_source(eventQueue, al_get_display_event_source(mainDisplay));

		black = al_map_rgb_f(0f, 0f, 0f);
		white = al_map_rgb_f(1f, 1f, 1f);
		font = al_create_builtin_font();

		leftPlayer = new Rect(4.5f, 45, 1, 10);
		rightPlayer = new Rect(94.5f, 45, 1, 10);

		board = new Rect(0, 0, 100, 100);

		resetBall();

		ALLEGRO_EVENT event = new ALLEGRO_EVENT();
		keys = new ALLEGRO_KEYBOARD_STATE();

		al_start_timer(mainTimer);

		boolean run = true;

		while(run) {
			event.setType(Integer.TYPE);
			al_wait_for_event(eventQueue, event);

			if (event.type == ALLEGRO_EVENT_TIMER) {
				al_get_keyboard_state(keys);

				update();

			} else if (event.type == ALLEGRO_EVENT_DISPLAY_CLOSE) {
				run = false;
			}

			if (al_is_event_queue_empty(eventQueue))
				render();
		}

		al_destroy_timer(mainTimer);
		al_destroy_event_queue(eventQueue);
		al_destroy_display(mainDisplay);

		al_shutdown_font_addon();
		al_shutdown_primitives_addon();
		al_uninstall_keyboard();
		al_uninstall_system();
	}


	private static void resetBall() {
		ball = new Rect(49.5f, 49.5f, 1, 1);
		balldX = 0.25f * (rnd.nextBoolean() ? 1f : -1f);
		balldY = 0.25f * (rnd.nextBoolean() ? 1f : -1f);
	}

	static void update() {
		float PADDLE_SPEED = 1f;
		if (al_key_down(keys, ALLEGRO_KEY_A))
			leftPlayer.move(0f, -PADDLE_SPEED);
		if (al_key_down(keys, ALLEGRO_KEY_Z))
			leftPlayer.move(0f, PADDLE_SPEED);

		if (al_key_down(keys, ALLEGRO_KEY_UP))
			rightPlayer.move(0f, -PADDLE_SPEED);
		if (al_key_down(keys, ALLEGRO_KEY_DOWN))
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

	static void render() {
		ALLEGRO_TRANSFORM t = new ALLEGRO_TRANSFORM();
		al_identity_transform(t);
		al_scale_transform(t, al_get_display_width(mainDisplay) / 100f, al_get_display_height(mainDisplay) / 100f);
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

		al_flip_display();
	}

	private static void draw(Rect rect) {
		al_draw_filled_rectangle(rect.left(), rect.top(), rect.right(), rect.bottom(), white);
	}
}
