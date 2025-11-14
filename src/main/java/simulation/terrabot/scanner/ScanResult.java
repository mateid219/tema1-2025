package simulation.terrabot.scanner;

import entities.Scannable;
import simulation.events.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ScanResult {
    @Getter private String message;
    @Getter private String name;
    @Getter private List<Event> newEvents;
    @Getter private Scannable entity;

    public ScanResult(String message, String name, List<Event> newEvents, Scannable entity) {
        this.message = message;
        this.name = name;
        this.newEvents = newEvents;
        this.entity = entity;
    }
}
