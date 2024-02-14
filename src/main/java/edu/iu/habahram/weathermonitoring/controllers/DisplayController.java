package edu.iu.habahram.weathermonitoring.controllers;

import edu.iu.habahram.weathermonitoring.model.CurrentConditionDisplay;
import edu.iu.habahram.weathermonitoring.model.ForecastDisplay;
import edu.iu.habahram.weathermonitoring.model.StatisticsDisplay;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/displays")
public class DisplayController {
    private CurrentConditionDisplay currentConditionDisplay;
    private StatisticsDisplay statisticsDisplay;
    private ForecastDisplay forecastDisplay;

    public DisplayController(CurrentConditionDisplay currentConditionDisplay, StatisticsDisplay statisticsDisplay,
            ForecastDisplay forecastDisplay) {
        this.currentConditionDisplay = currentConditionDisplay;
        this.statisticsDisplay = statisticsDisplay;
        this.forecastDisplay = forecastDisplay;
    }

    @GetMapping
    public ResponseEntity<?> index() {
        String html = String.format("<h1>Available screens:</h1>");
        html += "<ul>";
        html += "<li>";
        html += String.format("<a href=/displays/%s>%s</a>", currentConditionDisplay.id(),
                currentConditionDisplay.name());
        html += "</li>";
        html += "<li>";
        html += String.format("<a href=/displays/%s>%s</a>", statisticsDisplay.id(), statisticsDisplay.name());
        html += "</li>";
        html += "<li>";
        html += String.format("<a href=/displays/%s>%s</a>", forecastDisplay.id(), forecastDisplay.name());
        html += "</li>";

        html += "</ul>";
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(html);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> display(@PathVariable String id) {
        String html = "";
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (id.equalsIgnoreCase(currentConditionDisplay.id())) {
            html = currentConditionDisplay.display();
            status = HttpStatus.FOUND;
        } else if (id.equalsIgnoreCase(statisticsDisplay.id())) {
            html = statisticsDisplay.display();
            status = HttpStatus.FOUND;
        } else if (id.equalsIgnoreCase(forecastDisplay.id())) {
            html = forecastDisplay.display();
            status = HttpStatus.FOUND;
        } else {
            html = "The screen id is invalid.";
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(html);
    }

    @GetMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable String id) {
        String html = "";
        HttpStatus status = null;
        if (id.equalsIgnoreCase(currentConditionDisplay.id())) {
            currentConditionDisplay.subscribe();
            html = "Subscribed!";
            status = HttpStatus.FOUND;
        } else if (id.equalsIgnoreCase(statisticsDisplay.id())) {
            statisticsDisplay.subscribe();
            html = "Subscribed!";
            html += "<a href=\"/displays/statistics\">Statistics</a>";
            status = HttpStatus.FOUND;
        } else if (id.equalsIgnoreCase(forecastDisplay.id())) {
            forecastDisplay.subscribe();
            html = "Subscribed!";
            html += "<a href=\"/displays/heat-index\">Heat Index</a>";
            status = HttpStatus.FOUND;
        } else {
            html = "The screen id is invalid.";
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(html);
    }

    @GetMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable String id) {
        String html = "";
        HttpStatus status = null;
        if (id.equalsIgnoreCase(currentConditionDisplay.id())) {
            currentConditionDisplay.unsubscribe();
            html = "Unsubscribed!";
            status = HttpStatus.FOUND;
        } else if (id.equalsIgnoreCase(statisticsDisplay.id())) {
            statisticsDisplay.unsubscribe();
            html = "Unsubscribed!";
            html += "<a href=\"/displays/statistics\">Statistics</a>";
            status = HttpStatus.FOUND;
        } else if (id.equalsIgnoreCase(forecastDisplay.id())) {
            forecastDisplay.unsubscribe();
            html = "Unsubscribed!";
            html += "<a href=\"/displays/heat-index\">heat-index</a>";
            status = HttpStatus.FOUND;
        } else {
            html = "The screen id is invalid.";
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(html);
    }
}
