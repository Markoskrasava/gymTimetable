import org.junit.jupiter.api.Test;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertTrue(singleTrainingSession.getDayOfWeek().equals(DayOfWeek.MONDAY)); //Проверить, что за понедельник вернулось одно занятие
        assertFalse(singleTrainingSession.getDayOfWeek().equals(DayOfWeek.TUESDAY));
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeMap<TimeOfDay, TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        TreeMap<TimeOfDay, TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        TreeMap<TimeOfDay, TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);


        assertEquals(1, mondaySessions.size());// Проверить, что за понедельник вернулось одно занятие
        assertEquals(2, thursdaySessions.size());// Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TimeOfDay time = thursdaySessions.firstKey();
        assertEquals(13, time.getHours());
        assertNull(tuesdaySessions);// Проверить, что за вторник не вернулось занятий
        System.out.println(timetable);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertNotNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0))); //Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)));//Проверить, что за понедельник в 14:00 не вернулось занятий

    }

    @Test
    void testGetCountByCoachesForSingle() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.coaches.add(coach);
        timetable.addNewTrainingSession(singleTrainingSession);
        TreeMap<Coach, Integer> result = timetable.getCountByCoaches();
        assertEquals(1, result.get(coach));
        System.out.println(result);

    }
    @Test
    void testGetCountByCoachesForMultiple() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Group group1 = new Group("Бокс для детей", Age.CHILD, 60);
        TrainingSession singleTrainingSession1 = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coach1 = new Coach("Николаев", "Сергей", "Сергеевич");
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.coaches.add(coach);
        timetable.coaches.add(coach1);

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        TreeMap<Coach, Integer> result = timetable.getCountByCoaches();
        assertEquals(2, result.get(coach));
        assertEquals(1, result.get(coach1));
        System.out.println(result);
    }

    @Test
    void testGetCountByCoachesForThreeCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Group group1 = new Group("Бокс для детей", Age.CHILD, 60);
        TrainingSession singleTrainingSession1 = new TrainingSession(group1, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(14, 0));

        Group group11 = new Group("Бокс для детей", Age.CHILD, 50);
        TrainingSession singleTrainingSessionForChilds = new TrainingSession(group11, coach,
                DayOfWeek.SUNDAY, new TimeOfDay(16, 0));

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coach1 = new Coach("Николаев", "Сергей", "Сергеевич");
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        Group groupAdult1 = new Group("Акробатика для взрослых", Age.ADULT, 120);
        TrainingSession fridayAdultTrainingSession = new TrainingSession(groupAdult1, coach1,
                DayOfWeek.FRIDAY, new TimeOfDay(19, 0));

        Group group2 = new Group("Бокс для взрослых", Age.ADULT, 75);
        Coach coach2 = new Coach("Васильян", "Алексей", "Сергеевич");
        TrainingSession adultSession = new TrainingSession(group2, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(18, 0));

        timetable.coaches.add(coach);
        timetable.coaches.add(coach1);
        timetable.coaches.add(coach2);

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSessionForChilds);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(fridayAdultTrainingSession);
        timetable.addNewTrainingSession(adultSession);

        TreeMap<Coach, Integer> result = timetable.getCountByCoaches();
        assertEquals(3, result.get(coach));
        assertEquals(2, result.get(coach1));
        assertEquals(1, result.get(coach2));
        System.out.println(result);
    }

}