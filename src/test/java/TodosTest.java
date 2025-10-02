import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TodosTest {

    // исходный тест 1
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

    // тест для SimpleTask 2
    @Test
    public void shouldFindInSimpleTaskTitle() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");
        assertTrue(simpleTask.matches("родителям"), "должен находить 'родителям' в заголовке");
        assertFalse(simpleTask.matches("друзьям"), "не должен находить 'друзьям'");
    }

    // тест для Epic 3
    @Test
    public void shouldFindInEpicSubtasks() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);
        assertTrue(epic.matches("Яйца"), "должен находить 'Яйца'");
        assertFalse(epic.matches("Мясо"), "не должен находить 'Мясо'");
    }

    // тест для Meeting 4
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

    // тест для менеджера 5
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

        // проверка поиска по Meeting
        result = todos.search("Выкатка");
        assertArrayEquals(new Task[]{meeting}, result, "должен найти Meeting по теме");
    }

    // Тест на отсутствие результатов 6
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

    // тест на поиск нескольких задач 7
    @Test
    public void shouldFindMultipleTasks() {
        SimpleTask task1 = new SimpleTask(1, "Купить Хлеб");
        Epic task2 = new Epic(2, new String[]{"Хлеб", "Молоко"});
        Meeting task3 = new Meeting(3, "Хлебная встреча", "Пекарня", "12:00");

        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        Task[] result = todos.search("Хлеб");
        assertArrayEquals(new Task[]{task1, task2, task3}, result,
                "Должны найти все задачи с 'Хлеб'");
    }

    // тест на поиск одной задачи 8
    @Test
    public void shouldFindSingleTask() {
        SimpleTask task1 = new SimpleTask(1, "Купить Молоко");
        Epic task2 = new Epic(2, new String[]{"Яйца"});

        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);

        Task[] result = todos.search("Молоко");
        assertArrayEquals(new Task[]{task1}, result,
                "Должны найти только задачу с 'Молоко'");
    }

    // ?тест на пустой результат при пустом списке задач
    @Test
    public void shouldHandleEmptyTaskList() {
        Todos todos = new Todos();
        Task[] result = todos.search("Что-угодно");
        assertEquals(0, result.length,
                "Должен возвращать пустой массив при отсутствии задач");
    }


    // ?при наличии совпадения в любой из подзадач метод matches() должен вернуть true
    @Test
    public void shouldFindMultipleSubtasksInEpic() {
        Epic epic = new Epic(1, new String[]{"Хлеб", "Молоко", "Хлебобулочные изделия"});
        assertTrue(epic.matches("Хлеб"),
                "Должен находить совпадения в нескольких подзадачах");
    }
}
