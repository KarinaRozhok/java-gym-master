package ru.yandex.practicum.gym;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
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

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> sessions = new ArrayList<>();
        TreeMap<TimeOfDay, List<TrainingSession>> daySessions = timetable.getOrDefault(dayOfWeek, new TreeMap<>());

        for (List<TrainingSession> sessionList : daySessions.values()) {
            sessions.addAll(sessionList);
        }
        return sessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);
        if (sessionsForDay != null) {
            return sessionsForDay.getOrDefault(timeOfDay, new ArrayList<>());
        } else {
            return new ArrayList<>();
        }
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
        sortedCoaches.sort((c1, c2) -> c2.getCount() - c1.getCount()); // Сортировка по убыванию

        return sortedCoaches;
    }
}



