package org.gillius.jalleg.example;

import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_BITMAP;
import org.gillius.jalleg.framework.AllegroAddon;
import org.gillius.jalleg.framework.Game;
import org.gillius.jalleg.framework.io.AllegroLoader;
import org.gillius.jalleg.framework.math.Rect;

import java.io.IOException;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

public class ImageExample extends Game {
	private ALLEGRO_BITMAP bitmap;

	private Rect image;
	private int dx = 2;
	private int dy = 2;

	@Override
	protected void onAllegroStarted() throws IOException {
		initAddons(AllegroAddon.Image);

		bitmap = AllegroLoader.loadBitmapFromClasspath("/org/gillius/jalleg/example/mysha256x256.png");
		image = new Rect(0, 0, al_get_bitmap_width(bitmap), al_get_bitmap_height(bitmap));
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
	}

	public static void main(String[] args) {
		new ImageExample().run();
	}
}
