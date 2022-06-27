package com.victor.olympics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static final int ID = 0;
    public static final int NAME = 1;
    public static final int SEX = 2;
    public static final int AGE = 3;
    public static final int HEIGHT = 4;
    public static final int WEIGHT = 5;
    public static final int TEAM = 6;
    public static final int NOC = 7;
    public static final int GAMES = 8;
    public static final int YEAR = 9;
    public static final int SEASON = 10;
    public static final int CITY = 11;
    public static final int SPORT = 12;
    public static final int EVENT = 13;
    public static final int MEDAL = 14;

    public static void main(String[] args) {
        List<Event> events = getAthleteEventsData();
        findYearWiseNumberOfGoldMedalsWonByEachPlayers(events);
        findAthletesWhoWonGoldMedalIn2000AndAgeIsLessThan30Years(events);
        findEventWiseNumberOfGoldSilverBronzeMedalsInYear2000(events);
        findGoldWinnerOfFootballInEveryOlympics(events);
        findFemaleAthleteWonMaximumGoldInAllOlympics(events);
        findAthletesWhoParticipatedInMoreThanThreeOlympics(events);

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

                Integer age = Integer.parseInt(athleteEventData[AGE]);
                Integer year = Integer.parseInt(athleteEventData[YEAR]);

                event.setId(athleteEventData[ID]);
                event.setName(athleteEventData[NAME]);
                event.setSex(athleteEventData[SEX]);
                event.setAge(age);
                event.setHeight(athleteEventData[HEIGHT]);
                event.setWeight(athleteEventData[WEIGHT]);
                event.setTeam(athleteEventData[TEAM]);
                event.setNoc(athleteEventData[NOC]);
                event.setGames(athleteEventData[GAMES]);
                event.setYear(year);
                event.setSeason(athleteEventData[SEASON]);
                event.setCity(athleteEventData[CITY]);
                event.setSport(athleteEventData[SPORT]);
                event.setEvent(athleteEventData[EVENT]);
                event.setMedal(athleteEventData[MEDAL]);
                events.add(event);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Exception Handled");
        }
        return events;
    }


    private static void findYearWiseNumberOfGoldMedalsWonByEachPlayers(List<Event> events) {
        HashMap<Integer, HashMap> yearWiseNumberOfGoldMedalsWonByEachPlayer = new HashMap<>();

        for (Event event : events) {
            String medal = event.getMedal();
            if (medal.contains("Gold")) {
                Integer year = event.getYear();
                String name = event.getName();
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
        for (Event event : events) {
            if (event.getYear() == 2000 && event.getAge() < 30 && event.getMedal().contains("Gold")) {
                athletes.add(event.getName());
            }
        }
        System.out.println(athletes);
    }

    private static void findEventWiseNumberOfGoldSilverBronzeMedalsInYear2000(List<Event> events) {
        HashMap<String, HashMap> eventWiseMedals = new HashMap<>();
        for (Event event : events) {
            Integer year = event.getYear();
            if (year == 2000) {
                String eventName = event.getEvent();
                if (eventWiseMedals.containsKey(eventName)) {
                    String medal = event.getMedal();
                    if (medal.equals("Gold") || medal.equals("Silver") || medal.equals("Bronze")) {
                        HashMap numberOfMedalsPerEachEvent = eventWiseMedals.get(eventName);
                        if (numberOfMedalsPerEachEvent.containsKey(medal)) {
                            Integer medalCount = (int) numberOfMedalsPerEachEvent.get(medal);
                            numberOfMedalsPerEachEvent.put(medal, medalCount + 1);
                        } else {
                            numberOfMedalsPerEachEvent.put(medal, 1);
                        }
                    }
                } else {
                    HashMap<String, Integer> numberOfMedalsPerEachEvent = new HashMap<>();
                    String medal = event.getMedal();
                    if (medal.equals("Gold") || medal.equals("Silver") || medal.equals("Bronze")) {
                        eventWiseMedals.put(eventName, numberOfMedalsPerEachEvent);
                        eventWiseMedals.get(eventName).put(medal, 1);
                    }
                }
            }
        }
        System.out.println(eventWiseMedals);
    }

    private static void findGoldWinnerOfFootballInEveryOlympics(List<Event> events) {
        HashMap<Integer, String> goldWinnerOfFootballInEveryOlympics = new HashMap<>();
        for (Event event : events) {
            if (event.getSport().contains("Football") && event.getMedal().contains("Gold")) {
                goldWinnerOfFootballInEveryOlympics.put(event.getYear(), event.getTeam());
            }
        }
        System.out.println(goldWinnerOfFootballInEveryOlympics);
    }

    private static void findFemaleAthleteWonMaximumGoldInAllOlympics(List<Event> events) {
        HashMap<String, Integer> medalsOfFemaleAthletes = new HashMap<>();
        for (Event event : events) {
            String name = event.getName();
            if (event.getSex().contains("F") && event.getMedal().contains("Gold")) {
                if (medalsOfFemaleAthletes.containsKey(name)) {
                    medalsOfFemaleAthletes.put(name, medalsOfFemaleAthletes.get(name) + 1);
                } else {
                    medalsOfFemaleAthletes.put(name, 1);
                }
            }
        }
        Integer femaleAthleteWonMaximumGoldInAllOlympics = (Collections.max(medalsOfFemaleAthletes.values()));
        for (Map.Entry<String, Integer> entry : medalsOfFemaleAthletes.entrySet()) {
            if (entry.getValue() == femaleAthleteWonMaximumGoldInAllOlympics) {
                System.out.println("The Female Athlete with maximum Gold In All Olympics = " + entry.getKey() + " With " + entry.getValue() + " Golds");
            }

        }
    }

    private static void findAthletesWhoParticipatedInMoreThanThreeOlympics(List<Event> events) {
        HashMap<String, Integer> numberOfOlympicsPlayedByPlayer = new HashMap<>();
        List<String> playersParticipatedMoreThanThreeOlympics = new ArrayList<>();
        for (Event event : events) {
            String name = event.getName();
            if (numberOfOlympicsPlayedByPlayer.containsKey(name)) {
                numberOfOlympicsPlayedByPlayer.put(name, numberOfOlympicsPlayedByPlayer.get(name) + 1);
            } else {
                numberOfOlympicsPlayedByPlayer.put(name, 1);
            }
        }
        Integer athletesPlayedMoreThanThreeOlympics = (Collections.max(numberOfOlympicsPlayedByPlayer.values()));
        for (Map.Entry<String, Integer> entry : numberOfOlympicsPlayedByPlayer.entrySet()) {
            if (entry.getValue() == athletesPlayedMoreThanThreeOlympics && entry.getValue() > 3) {
                playersParticipatedMoreThanThreeOlympics.add(entry.getKey());
            }
        }
        System.out.println("PlayersWhoPlayedMoreThanThreeOlympics = " + playersParticipatedMoreThanThreeOlympics);
    }
}
