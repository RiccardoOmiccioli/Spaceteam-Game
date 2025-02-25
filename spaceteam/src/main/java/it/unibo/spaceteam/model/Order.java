package it.unibo.spaceteam.model;

import it.unibo.spaceteam.utils.PeriodicTimer;

import java.util.Objects;

public class Order {

    private Control control;
    private String value;
    private long currentTime;
    private long maxTime;
    private PeriodicTimer timer;

    public Order(Control control, String value, long maxTime) {
        this.control = control;
        this.value = value;
        this.maxTime = maxTime;
    }

    public Control getControl() {
        return control;
    }

    public String getValue() {
        return value;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setTimer(PeriodicTimer timer) {
        this.timer = timer;
    }

    public boolean isComplete() {
        return timer.isStopped();
    }

    public void complete() {
        timer.stop();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Order order = (Order) object;
        return maxTime == order.maxTime && Objects.equals(control, order.control) && Objects.equals(value, order.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(control, value, maxTime);
    }

    @Override
    public String toString() {
        return "Order{" +
                "maxTime=" + maxTime +
                ", currentTime=" + currentTime +
                ", value='" + value + '\'' +
                ", control=" + control +
                '}';
    }

}
