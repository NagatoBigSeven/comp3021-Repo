package base;
import java.time.Duration;
import java.util.*;
public class Service implements EventEmitter {
    private final List<Listener> listeners = new ArrayList<>();
    Timer timer;
    // TODO 1: Implement the methods addListener, removeListener and emitEvent in EventEmitter interface
    @Override
    public void addListener(Listener listener){
        listeners.add(listener);
    }
    @Override
    public void removeListener(Listener listener){
        listeners.remove(listener);
    }
    @Override
    public void emitEvent(Event event){
        for(Listener listener: listeners){
            listener.handle(event);
        }
    }
    /**
     * TODO 2: Complete the method startTick
     * Start time ticking, emit {@link Event} immediately and then periodically emit events with the given time interval.
     * That is to say, if the interval is 1 second,
     * the first event is emitted at 0-th second,
     * second event is emitted at 1-th second, etc.
     * The emitted {@link Event} should use the current time ({@link Date} object) as the value of {@link Event#getSource()}.
     * You are expect to use {@link Timer#schedule(TimerTask, Date, long)} to schedule the task.
     *
     * @param interval the time interval to emit events periodically.
     */
    public void startTick(Duration interval){
        // TODO
        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                emitEvent(new Event(new Date()));
            }
        }, new Date(), interval.toMillis());
    }
    /**
     * TODO 3: Complete the method stopTick
     * Stop the time ticking that is currently working.
     * If no ticker is working, do nothing.
     */
    public void stopTick(){
        // TODO
        timer.cancel();
    }
}