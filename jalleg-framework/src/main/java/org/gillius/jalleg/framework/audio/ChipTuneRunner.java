package org.gillius.jalleg.framework.audio;

import org.gillius.jalleg.framework.Timeline;

import java.util.concurrent.*;

public class ChipTuneRunner implements Runnable, AutoCloseable {
	private static double nanosPerSecond = TimeUnit.SECONDS.toNanos(1);

	private final ChipTuneSystem chipTuneSystem;
	private final Timeline<ChipTuneNoteInstance> timeline = new Timeline<>();

	private final ScheduledExecutorService ses = Executors.newScheduledThreadPool(1, new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread ret = new Thread(r, "ChipTuneRunner");
			ret.setDaemon(true);
			return ret;
		}
	});
	private boolean started = false;

	public ChipTuneRunner(ChipTuneSystem chipTuneSystem) {
		this.chipTuneSystem = chipTuneSystem;
	}

	public void add(ChipTuneNoteInstance ni, double time) {
		timeline.add(ni, time);
		//TODO: if started, reschedule event
	}

	public void start() {
		if (!started) {
			timeline.setStart(getCurrentTime());
			scheduleNext();
			started = true;
		}
	}

	@Override
	public void run() {
		try {
			ChipTuneNoteInstance n = timeline.poll(getCurrentTime());
//			System.out.println(getCurrentTime());
//			System.out.println("Playing " + n);
			if (n != null)
				chipTuneSystem.play(n);
			scheduleNext();

		} catch (RejectedExecutionException e) {
			//ignore (probably shutting down)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void scheduleNext() {
		//TODO: don't need max wait time when we can safely cancel and reschedule event
		double waitTime = Math.min(timeline.getTimeToNextEvent(getCurrentTime()), 0.1);
		long waitNanos = (long)(waitTime * nanosPerSecond);
		ses.schedule(this, waitNanos, TimeUnit.NANOSECONDS);
	}

	public void stop() throws InterruptedException {
		ses.shutdown();
		ses.awaitTermination(1, TimeUnit.SECONDS);
	}

	@Override
	public void close() throws InterruptedException {
		stop();
	}

	private double getCurrentTime() {
		return System.nanoTime() / nanosPerSecond;
	}
}
