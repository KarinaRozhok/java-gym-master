package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //  Cитуация, когда trainingSession равно null
        if (trainingSession == null) {
            throw new IllegalArgumentException("Training session cannot be null");
        } else {
            DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
            TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

            if (!timetable.containsKey(dayOfWeek)) {
                timetable.put(dayOfWeek, new TreeMap<>());
            }

            TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);

            if (!sessionsForDay.containsKey(timeOfDay)) {
                sessionsForDay.put(timeOfDay, new ArrayList<>());
            }
            sessionsForDay.get(timeOfDay).add(trainingSession);
        }
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);
        if (sessionsForDay != null) {
            return sessionsForDay.getOrDefault(timeOfDay, new ArrayList<>());
        } else {
            return new ArrayList<>();
        }
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> sessions = new ArrayList<>();
        TreeMap<TimeOfDay, List<TrainingSession>> daySessions = timetable.getOrDefault(dayOfWeek, new TreeMap<>());

        for (List<TrainingSession> sessionList : daySessions.values()) {
            sessions.addAll(sessionList);
        }
        return sessions;
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySessions : timetable.values()) {
            for (List<TrainingSession> sessionList : daySessions.values()) {
                for (TrainingSession session : sessionList) {
                    Coach coach = session.getCoach();
                    coachCounts.put(coach, coachCounts.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> sortedCoaches = new ArrayList<>(coachCounts.size());
        coachCounts.forEach((coach, count) -> sortedCoaches.add(new CounterOfTrainings(coach, count)));
        sortedCoaches.sort(Comparator.comparingInt((CounterOfTrainings c) -> c.getCount()).reversed());
        return sortedCoaches;
    }
}

