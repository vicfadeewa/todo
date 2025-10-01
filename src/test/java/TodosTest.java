import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TodosTest {

    // исходный тест
    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);
        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = {simpleTask, epic, meeting};
        Task[] actual = todos.findAll();
        assertArrayEquals(expected, actual);
    }

    // тест для SimpleTask
    @Test
    public void shouldFindInSimpleTaskTitle() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");
        assertTrue(simpleTask.matches("родителям"), "должен находить 'родителям' в заголовке");
        assertFalse(simpleTask.matches("друзьям"), "не должен находить 'друзьям'");
    }

    // тест для Epic
    @Test
    public void shouldFindInEpicSubtasks() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);
        assertTrue(epic.matches("Яйца"), "должен находить 'Яйца'");
        assertFalse(epic.matches("Мясо"), "не должен находить 'Мясо'");
    }

    // тест для Meeting
    @Test
    public void shouldFindInMeetingTopicOrProject() {
        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );
        assertTrue(meeting.matches("Выкатка"), "должен находить 'Выкатка' в теме");
        assertTrue(meeting.matches("НетоБанка"), "должен находить 'НетоБанка' в проекте");
        assertFalse(meeting.matches("Версия"), "не должен находить 'Версия'");
    }

    // тест для менеджера
    @Test
    public void shouldSearchInTodos() {
        SimpleTask simpleTask = new SimpleTask(5, "позвонить родителям");
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);
        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        // проверка поиска по SimpleTask
        Task[] result = todos.search("родителям");
        assertArrayEquals(new Task[]{simpleTask}, result, "должен найти SimpleTask");

        // проверка поиска по Epic
        result = todos.search("Хлеб");
        assertArrayEquals(new Task[]{epic}, result, "должен найти Epic");

        // проверка поиска по Meeting
        result = todos.search("НетоБанка");
        assertArrayEquals(new Task[]{meeting}, result, "должен найти Meeting по проекту");

        // Проверка поиска по Meeting
        result = todos.search("Выкатка");
        assertArrayEquals(new Task[]{meeting}, result, "должен найти Meeting по теме");
    }

    // Тест на отсутствие результатов
    @Test
    public void shouldNotFindNonExistentQuery() {
        SimpleTask simpleTask = new SimpleTask(5, "позвонить родителям");
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);
        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] result = todos.search("Собака");
        assertEquals(0, result.length, "не должно быть результатов для 'Собака'");
    }
}
