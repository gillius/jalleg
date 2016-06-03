package org.gillius.jalleg.framework.audio;

import org.gillius.jalleg.binding.ALLEGRO_SAMPLE_ID;

import java.io.Closeable;
import java.io.IOException;

import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_AUDIO_PAN_NONE;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_PLAYMODE.ALLEGRO_PLAYMODE_LOOP;
import static org.gillius.jalleg.binding.AllegroLibrary.al_play_sample;
import static org.gillius.jalleg.binding.AllegroLibrary.al_stop_sample;

/**
 * Plays looping sample data with al_play_sample until a specified time.
 */
public class LoopingSingleInstanceSample implements AutoCloseable {
	private final SampleData sampleData;
	private final ALLEGRO_SAMPLE_ID id;
	private boolean playing;
	private double endTime;

	private float gain = 1f;
	private float pan = ALLEGRO_AUDIO_PAN_NONE;
	private float speed = 1f;

	public LoopingSingleInstanceSample(SampleData sampleData) {
		this.sampleData = sampleData;
		id = new ALLEGRO_SAMPLE_ID();
	}

	@Override
	public void close() throws Exception {
		sampleData.close();
	}

	public float getGain() {
		return gain;
	}

	public LoopingSingleInstanceSample setGain(float gain) {
		this.gain = gain;
		return this;
	}

	public float getPan() {
		return pan;
	}

	public LoopingSingleInstanceSample setPan(float pan) {
		this.pan = pan;
		return this;
	}

	public float getSpeed() {
		return speed;
	}

	public LoopingSingleInstanceSample setSpeed(float speed) {
		this.speed = speed;
		return this;
	}

	public SampleData getSampleData() {
		return sampleData;
	}

	public boolean isPlaying() {
		return playing;
	}

	public double getEndTime() {
		return endTime;
	}

	/**
	 * Updates this sample. If t is greater than or equal to the endTime, stop playing the sample.
	 */
	public void update(double t) {
		if (playing && t >= endTime) {
			stop();
		}
	}

	public void play(double endTime) {
		play(gain, pan, speed, endTime);
	}

	private void play(float gain, float pan, float speed, double endTime) {
		stop();

		playing = al_play_sample(sampleData.getSample(), gain, pan, speed, ALLEGRO_PLAYMODE_LOOP, id);
		this.endTime = endTime;
	}

	public void stop() {
		if (playing) {
			al_stop_sample(id);
			playing = false;
		}
	}
}
