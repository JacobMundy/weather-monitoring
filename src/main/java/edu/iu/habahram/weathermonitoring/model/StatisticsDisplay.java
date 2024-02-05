package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

@Component
public class StatisticsDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private float pressure;

    private Subject weatherData;
    private List<Float> temperatureList = new ArrayList<>();



    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
    }

    private void listController(float temperature) {
        temperatureList.add(temperature);
        if(temperatureList.size() >= 100) {
            temperatureList.remove(0);
        }
    }

    @Override
    public String display() {
        listController(temperature);
        String html = "";
        html += String.format("<div style=\"background-image: " +
                "url(/images/sky.webp); " +
                "height: 400px; " +
                "width: 647.2px;" +
                "display:flex;flex-wrap:wrap;justify-content:center;align-content:center;" +
                "\">");
        html += "<section>";
        html += String.format("<label>Avg Temp: %s</label><br />", temperatureList.stream().reduce((float) 0.0, Float::sum) / temperatureList.size());
        html += String.format("<label>Min Temp: %s</label><br />", Collections.min(temperatureList));
        html += String.format("<label>Max Temp: %s</label>", Collections.max(temperatureList));
        html += "</section>";
        html += "</div>";
        html += "<section>";
        html += "<a href=\"/displays/statistics/subscribe\">Subscribe</a>";
        html += "<a href=\"/displays/statistics/unsubscribe\">Unsubscribe</a>";
        html += "</section>";
        return html;

    }

    @Override
    public String name() {
        return "Statistics Display";
    }

    @Override
    public String id() {
        return "statistics";
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }
}
