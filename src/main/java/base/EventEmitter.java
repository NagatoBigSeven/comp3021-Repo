package base;
import java.util.EventListener;
import java.util.EventObject;
public interface EventEmitter{
    class Event extends EventObject{
        /**
         * Constructs a prototypical Event.
         *
         * @param source the object on which the Event initially occurred
         * @throws IllegalArgumentException if source is null
         */
        public Event(Object source){
            super(source);
        }
    }
    @FunctionalInterface
    interface Listener extends EventListener{
        void handle(Event event);
    }
    /**
     * Register a new listener
     *
     * @param listener the event listener
     */
    default void addListener(Listener listener){
        throw new RuntimeException("Please implement the method");
    }
    /**
     * Unregister a listener
     *
     * @param listener event listener
     */
    default void removeListener(Listener listener){
        throw new RuntimeException("Please implement the method");
    }
    /**
     * Emit the {@link Event} to every registered listeners
     *
     * @param event the event to emit
     */
    default void emitEvent(Event event){
        throw new RuntimeException("Please implement the method");
    }
}
