package org.gillius.jalleg.example;

import org.gillius.jalleg.binding.ALLEGRO_COLOR;
import org.gillius.jalleg.binding.ALLEGRO_FONT;
import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_BITMAP;
import org.gillius.jalleg.framework.AllegroAddon;
import org.gillius.jalleg.framework.Game;
import org.gillius.jalleg.framework.io.AllegroLoader;
import org.gillius.jalleg.framework.math.Rect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

public class ImageExample extends Game {
	private ALLEGRO_COLOR white;
	private ALLEGRO_FONT font;

	private ALLEGRO_BITMAP bitmap;
	private int myshaWidth;
	private int myshaHeight;

	private Random random = new Random();

	private List<ImageInstance> images = new ArrayList<>();

	public ImageExample() {
		setAutoResize(true);
	}

	@Override
	protected void onAllegroStarted() throws IOException {
		initAddons(AllegroAddon.Image, AllegroAddon.Keyboard);

		white = al_map_rgb_f(1f, 1f, 1f);
		font = al_create_builtin_font();

		bitmap = AllegroLoader.loadBitmapFromClasspath("/org/gillius/jalleg/example/mysha256x256.png");
		myshaWidth = al_get_bitmap_width(bitmap);
		myshaHeight = al_get_bitmap_height(bitmap);

		initStatsCollection(1, TimeUnit.SECONDS);
	}

	@Override
	protected void update() {
		Rect screen = new Rect(0, 0, getDisplayWidth(), getDisplayHeight());

		if (images.isEmpty() || isKeyDown(ALLEGRO_KEY_UP)) {
			images.add(new ImageInstance(
					new Rect(random.nextFloat() * screen.right(), random.nextFloat() * screen.bottom(), myshaWidth, myshaHeight)
			));
		} else if (isKeyDown(ALLEGRO_KEY_DOWN)) {
			images.remove(images.size() - 1);
		}

		for (ImageInstance image : images)
			image.update(screen);
	}

	@Override
	protected void render() {
		for (ImageInstance image : images)
			image.render();

		al_draw_text(font, white, 1, getDisplayHeight() - al_get_font_line_height(font) - 1, ALLEGRO_ALIGN_LEFT,
		             "Myshas: " + images.size() + " (up or down key to change)");

		if (lastStats != null)
			al_draw_text(font, white, 1, 1, ALLEGRO_ALIGN_LEFT, lastStats.getStatsString());
	}

	public static void main(String[] args) {
		new ImageExample().run();
	}

	private class ImageInstance {
		private Rect pos;
		private int dx = 2;
		private int dy = 2;

		public ImageInstance(Rect pos) {
			this.pos = pos;
		}

		public void update(Rect screen) {
			if (pos.outsideX(screen))
				dx = -dx;
			if (pos.outsideY(screen))
				dy = -dy;
			pos.constrainWithin(screen);

			pos.move(dx, dy);
		}

		public void render() {
			al_draw_bitmap(bitmap, pos.x, pos.y, 0);
		}
	}
}
