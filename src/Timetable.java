import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Comparator;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();
    public HashSet<Coach> coaches = new HashSet<>(); // при создании нового тренера его нужно будет добавлять в множество

    Comparator<TimeOfDay> comparator = new Comparator<TimeOfDay>() {
        @Override
        public int compare(TimeOfDay o1, TimeOfDay o2) {
            if (o1.getHours() == o2.getHours()) {
                return Integer.compare(o1.getMinutes(), o2.getMinutes());
            }
            return Integer.compare(o1.getHours(), o2.getHours());
        }
    };

    Comparator<Coach> comparatorForCoaches = new Comparator<Coach>() {
        @Override
        public int compare(Coach c1, Coach c2) {
            int count1 = getCoachCount(c1);
            int count2 = getCoachCount(c2);
            return Integer.compare(count2, count1);
        }
    };


    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, TrainingSession> value = timetable.get(day);
        if (value == null) {
            value = new TreeMap<>(comparator);
            timetable.put(day, value);
        }
        value.put(time, trainingSession); //сохраняем занятие в расписании
    }

    public TreeMap<TimeOfDay, TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, TrainingSession> sessions = timetable.get(dayOfWeek);
        return sessions.get(timeOfDay);
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public TreeMap<Coach, Integer> getCountByCoaches() {
        TreeMap<Coach, Integer> coachesCount = new TreeMap<>(comparatorForCoaches);
        for (Coach coach : coaches) {
            int count = 0;
            for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> sessionsByDay : timetable.entrySet()) {
                TreeMap<TimeOfDay, TrainingSession> session = sessionsByDay.getValue();
                for (Map.Entry<TimeOfDay, TrainingSession> sessionEntry : session.entrySet()) {
                    TrainingSession trainingSession = sessionEntry.getValue();
                    if (coach.equals(trainingSession.getCoach())) {
                        count++;
                    }
                }
            }
            coachesCount.put(coach, count);
        }
        return coachesCount;
    }

    public int getCoachCount(Coach coach) {
        int count = 0;
        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> sessionsByDay : timetable.entrySet()) {
            TreeMap<TimeOfDay, TrainingSession> session = sessionsByDay.getValue();
            for (Map.Entry<TimeOfDay, TrainingSession> sessionEntry : session.entrySet()) {
                TrainingSession trainingSession = sessionEntry.getValue();
                if (coach.equals(trainingSession.getCoach())) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "timetable=" + timetable +
                '}';
    }
}