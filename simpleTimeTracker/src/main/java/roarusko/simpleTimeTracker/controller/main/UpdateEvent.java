package roarusko.simpleTimeTracker.controller.main;

import javafx.event.Event;
import javafx.event.EventType;


/**
 * Hävyttömän yksinkertainen tapahtumaluokka.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class UpdateEvent extends Event {


    private static final long serialVersionUID = -267570215864910085L;
   
    /**
     * Luo uuden tapahtumatyypin
     */
    public static final EventType<UpdateEvent> UPDATE_EVENT =
            new EventType<>(Event.ANY, "UPDATE_EVENT");
    
    
    /**
     * Luo uuden tapahtuman
     */
    public UpdateEvent() {
        super(UPDATE_EVENT);
    }

}
