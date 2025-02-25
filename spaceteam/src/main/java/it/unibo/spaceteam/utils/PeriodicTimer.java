package it.unibo.spaceteam.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class PeriodicTimer {

    private final Timer timer;
    private final long startTime;
    private final long duration;
    private final long interval;
    private final long delay;
    private boolean stopped;

    public PeriodicTimer(long duration, long interval, long delay, Runnable onComplete, Consumer<Long> onUpdate) {
        this.timer = new Timer();
        this.startTime = System.currentTimeMillis();
        this.duration = duration;
        this.interval = interval;
        this.delay = delay;
        this.stopped = false;

        if (interval > 0) {
            TimerTask periodicTask = new TimerTask() {
                @Override
                public void run() {
                    onUpdate.accept(getElapsedTime());
                }
            };
            timer.scheduleAtFixedRate(periodicTask, delay, interval);
        }

        TimerTask completionTask = new TimerTask() {
            @Override
            public void run() {
                onComplete.run();
                timer.cancel();
            }
        };
        timer.schedule(completionTask, delay + duration);
    }

    public void stop() {
        stopped = true;
        timer.cancel();
    }

    public boolean isStopped() {
        return stopped;
    }

    public long getElapsedTime() {
        return Math.max(0, System.currentTimeMillis() - startTime);
    }

    public long getRemainingTime() {
        long elapsedTime = getElapsedTime();
        return Math.max(0, duration - elapsedTime);
    }

    @Override
    public String toString() {
        return "PeriodicTimer{" +
                "startTime=" + startTime +
                ", duration=" + duration +
                ", interval=" + interval +
                ", delay=" + delay +
                ", stopped=" + stopped +
                '}';
    }

}
