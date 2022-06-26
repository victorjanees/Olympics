package com.victor.olympics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Event> events = getAthleteEventsData();
        List<Region> regions = getNocRegionsData();
        findYearWiseNumberOfGoldMedalsWonByEachPlayers(events);
        findAthletesWhoWonGoldMedalIn2000AndAgeIsLessThan30Years(events);
        findEventWiseNumberOfGoldSilverBronzeMedalsInYear2000(events);

    }


    public static List<Event> getAthleteEventsData() {
        String path = "resources/Athlete.csv";
        String line = "";
        int header = 1;
        List<Event> events = new ArrayList<>();
        try {
            BufferedReader dataReader = new BufferedReader(new FileReader(path));
            while ((line = dataReader.readLine()) != null) {
                if (header == 1) {
                    header = header + 1;
                    continue;
                }
                String[] athleteEventData = line.split(",");
                Event event = new Event();

                Integer age = Integer.parseInt(athleteEventData[3]);
                Integer year = Integer.parseInt(athleteEventData[9]);

                event.setId(athleteEventData[0]);
                event.setName(athleteEventData[1]);
                event.setSex(athleteEventData[2]);
                event.setAge(age);
                event.setHeight(athleteEventData[4]);
                event.setWeight(athleteEventData[5]);
                event.setTeam(athleteEventData[6]);
                event.setNoc(athleteEventData[7]);
                event.setGames(athleteEventData[8]);
                event.setYear(year);
                event.setSeason(athleteEventData[10]);
                event.setCity(athleteEventData[11]);
                event.setSport(athleteEventData[12]);
                event.setEvent(athleteEventData[13]);
                event.setMedal(athleteEventData[14]);
                events.add(event);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Exception Handled");
            ;
        }
        return events;
    }

    public static List<Region> getNocRegionsData() {
        String path = "resources/noc_regions.csv";
        String line = "";
        int header = 1;
        List<Region> regions = new ArrayList<>();
        try {
            BufferedReader dataReader = new BufferedReader(new FileReader(path));
            while ((line = dataReader.readLine()) != null) {
                if (header == 1) {
                    header = header + 1;
                    continue;
                }
                String[] nocRegionsData = line.split(",");
                Region region = new Region();

                region.setNoc(nocRegionsData[0]);
                region.setRegion(nocRegionsData[1]);
                regions.add(region);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return regions;
    }

    public static void findYearWiseNumberOfGoldMedalsWonByEachPlayers(List<Event> events) {
        HashMap<Integer, HashMap> yearWiseNumberOfGoldMedalsWonByEachPlayer = new HashMap<>();

        for (Event player : events) {
            String medal = player.getMedal();
            if (medal.contains("Gold")) {
                Integer year = player.getYear();
                String name = player.getName();
                if (yearWiseNumberOfGoldMedalsWonByEachPlayer.containsKey(year)) {
                    HashMap peopleWonGold = yearWiseNumberOfGoldMedalsWonByEachPlayer.get(year);
                    if (peopleWonGold.containsKey(name)) {
                        int medalsCount = (int) peopleWonGold.get(name);
                        peopleWonGold.put(name, medalsCount + 1);
                    } else {
                        peopleWonGold.put(name, 1);
                    }
                } else {
                    HashMap<String, Integer> numberOfGoldMedalsWonByEachPlayer = new HashMap<>();
                    numberOfGoldMedalsWonByEachPlayer.put(name, 1);
                    yearWiseNumberOfGoldMedalsWonByEachPlayer.put(year, numberOfGoldMedalsWonByEachPlayer);
                }
            }
        }
        System.out.println(yearWiseNumberOfGoldMedalsWonByEachPlayer);


    }

    private static void findAthletesWhoWonGoldMedalIn2000AndAgeIsLessThan30Years(List<Event> events) {
        List<String> athletes = new ArrayList<>();
        for (Event player : events) {
            if (player.getYear() == 2000 && player.getAge() < 30 && player.getMedal().contains("Gold")) {
                athletes.add(player.getName());
            }
        }
        System.out.println(athletes);
    }

    private static void findEventWiseNumberOfGoldSilverBronzeMedalsInYear2000(List<Event> events) {
        HashMap<String,HashMap> eventWiseMedals = new HashMap<>();
        for (Event event:events) {
            Integer year = event.getYear();
            String medal = event.getMedal();
            if(year==2000){
                String eventName = event.getEvent();
                if(eventWiseMedals.containsKey(eventName)){
                    HashMap medalsPerEvent = eventWiseMedals.get(eventName);
                    if(medalsPerEvent.containsKey(medal)){
                        Integer medalCount = (int)medalsPerEvent.get(medal);
                        medalsPerEvent.put(medal,medalCount+1);
                    }
                    else {
                        medalsPerEvent.put(medal,1);
                    }
                }else {
                    HashMap<String,Integer> numberOfMedalsPerEachEvent = new HashMap<>();
                    numberOfMedalsPerEachEvent.put(medal,1);
                    eventWiseMedals.put(eventName,numberOfMedalsPerEachEvent);
                }
            }
        }
        System.out.println(eventWiseMedals);
    }
}
