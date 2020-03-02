package roarusko.simpleTimeTracker.controller.main;

import javafx.event.Event;
import javafx.event.EventType;

public class UpdateEvent extends Event {

    /**
     * 
     */
    private static final long serialVersionUID = -267570215864910085L;
    /**
     * 
     */
    
    public static final EventType<UpdateEvent> UPDATE_EVENT =
            new EventType<>(Event.ANY, "UPDATE_EVENT");
    
    
    /**
     */
    public UpdateEvent() {
        super(UPDATE_EVENT);
        System.out.println("event fired");
    }

}
