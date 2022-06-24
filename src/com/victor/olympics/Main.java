package com.victor.olympics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

    }

    public static List<AthleteEvent> getAthleteEventsData() {
        String path = "src/com/victor/olympics/athlete_events.csv";
        String line = "";
        int header = 1;
        List<AthleteEvent> athleteEvents = new ArrayList<>();
        try {
            BufferedReader dataReader = new BufferedReader(new FileReader(path));
            while ((line = dataReader.readLine()) != null) {
                if (header == 1) {
                    header = header + 1;
                    continue;
                }
                String[] athleteEventData = line.split(",");
                AthleteEvent athleteEvent = new AthleteEvent();

                Integer id = Integer.parseInt(athleteEventData[0]);
                Integer age = Integer.parseInt(athleteEventData[3]);
                Integer height = Integer.parseInt(athleteEventData[4]);
                Integer weight = Integer.parseInt(athleteEventData[5]);
                Integer year = Integer.parseInt(athleteEventData[9]);

                athleteEvent.setId(id);
                athleteEvent.setName(athleteEventData[1]);
                athleteEvent.setSex(athleteEventData[2]);
                athleteEvent.setAge(age);
                athleteEvent.setHeight(height);
                athleteEvent.setWeight(weight);
                athleteEvent.setTeam(athleteEventData[6]);
                athleteEvent.setNoc(athleteEventData[7]);
                athleteEvent.setGames(athleteEventData[8]);
                athleteEvent.setYear(year);
                athleteEvent.setSeason(athleteEventData[10]);
                athleteEvent.setCity(athleteEventData[11]);
                athleteEvent.setSport(athleteEventData[12]);
                athleteEvent.setEvent(athleteEventData[13]);
                athleteEvent.setMedal(athleteEventData[14]);
                athleteEvents.add(athleteEvent);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
return athleteEvents;
    }
}