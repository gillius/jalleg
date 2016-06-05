package org.gillius.jalleg.example;

import org.gillius.jalleg.binding.ALLEGRO_COLOR;
import org.gillius.jalleg.binding.ALLEGRO_FONT;
import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_BITMAP;
import org.gillius.jalleg.framework.AllegroAddon;
import org.gillius.jalleg.framework.Game;
import org.gillius.jalleg.framework.io.AllegroLoader;
import org.gillius.jalleg.framework.math.Rect;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

public class ImageExample extends Game {
	private ALLEGRO_COLOR white;
	private ALLEGRO_FONT font;

	private ALLEGRO_BITMAP bitmap;

	private Rect image;
	private int dx = 2;
	private int dy = 2;

	@Override
	protected void onAllegroStarted() throws IOException {
		initAddons(AllegroAddon.Image);

		white = al_map_rgb_f(1f, 1f, 1f);
		font = al_create_builtin_font();

		bitmap = AllegroLoader.loadBitmapFromClasspath("/org/gillius/jalleg/example/mysha256x256.png");
		image = new Rect(0, 0, al_get_bitmap_width(bitmap), al_get_bitmap_height(bitmap));

		initStatsCollection(1, TimeUnit.SECONDS);
	}

	@Override
	protected void update() {
		Rect screen = new Rect(0, 0, getDisplayWidth(), getDisplayHeight());

		if (image.outsideX(screen))
			dx = -dx;
		if (image.outsideY(screen))
			dy = -dy;
		image.constrainWithin(screen);

		image.move(dx, dy);
	}

	@Override
	protected void render() {
		al_draw_bitmap(bitmap, image.x, image.y, 0);

		if (lastStats != null)
			al_draw_text(font, white, 1, 1, ALLEGRO_ALIGN_LEFT, lastStats.getStatsString());
	}

	public static void main(String[] args) {
		new ImageExample().run();
	}
}
