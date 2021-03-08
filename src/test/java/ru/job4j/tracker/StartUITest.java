package ru.job4j.tracker;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StartUITest {
    @Test
    public void whenCreateItem() {
        Output output = new StubOutput();
        Input in = new StubInput(
                new String[]{"0", "Item name", "1"}
        );
        Tracker tracker = new Tracker();
        UserAction[] actions = {
                new CreateAction(output),
                new Exit()
        };
        new StartUI(output).init(in, tracker, actions);
        assertThat(tracker.findAll()[0].getName(), is("Item name"));
    }

    @Test
    public void whenReplaceItem() {
        Output output = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("Replaced item"));
        String replacedName = "New item name";
        Input in = new StubInput(
                new String[]{"0", String.valueOf(item.getId()), replacedName, "1"}
        );
        UserAction[] actions = {
                new ReplaceAction(output),
                new Exit()
        };
        new StartUI(output).init(in, tracker, actions);
        assertThat(tracker.findById(item.getId()).getName(), is(replacedName));
    }

    @Test
    public void whenDeleteItem() {
        Output output = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("Deleted item"));
        Input in = new StubInput(
                new String[]{"0", String.valueOf(item.getId()), "1"}
        );
        UserAction[] actions = {
                new DeleteItemAction(output),
                new Exit()
        };
        new StartUI(output).init(in, tracker, actions);
        assertThat(tracker.findById(item.getId()), is(nullValue()));
    }

    @Test
    public void whenExit() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[]{"0"}
        );
        Tracker tracker = new Tracker();
        UserAction[] actions = {
                new Exit()
        };
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is(
                "Меню." + System.lineSeparator() +
                        "0. Выход" + System.lineSeparator()
        ));
    }

    @Test
    public void whenFindAllAction() {
        Output output = new StubOutput();
        Input input = new StubInput(new String[]{"0", "1"});
        Tracker tracker = new Tracker();
        tracker.add(new Item("findAll"));
        UserAction[] actions = {new FindAllAction(output), new Exit()};
        new StartUI(output).init(input, tracker, actions);
        assertThat(output.toString(), is("Меню." + System.lineSeparator() + "0. Посмотреть все заявки" +
                System.lineSeparator() + "1. Выход" + System.lineSeparator()
                + "==Список всех заявок==" + System.lineSeparator() + "Item{id=1, name='findAll'}" + System.lineSeparator()
                + "Меню." + System.lineSeparator() + "0. Посмотреть все заявки" + System.lineSeparator() + "1. Выход" + System.lineSeparator()));

/*                "Меню.n0. Посмотреть все заявки\n1. Выход\\n==Список всех заявок==\\" +
                        "nItem{id=1, name='findAll'}\\nМеню.\\n0. Посмотреть все заявки\\n1." +
                        " Выход\\n"*/

    }

    @Test
    public void whenFindByNameAction() {
        Output output = new StubOutput();
        Input input = new StubInput(new String[]{"0", "FindByName", "1"});
        Tracker tracker = new Tracker();
        tracker.add(new Item("FindByName"));
        UserAction[] actions = {new SearchItemNameAction(output), new Exit()};
        new StartUI(output).init(input, tracker, actions);
        assertThat(output.toString(), is("Меню." + System.lineSeparator() + "0. Найти заявки по имени"
                + System.lineSeparator() + "1. Выход" + System.lineSeparator() + "==Найти заявки по имени==" + System.lineSeparator() + "Item{id=1, name='FindByName'}"
                + System.lineSeparator() + "Меню." + System.lineSeparator() + "0. Найти заявки по имени" + System.lineSeparator()
                + "1. Выход" + System.lineSeparator()));
    }

    @Test
    public void whenFindByIdAction() {
        Output output = new StubOutput();
        Input input = new StubInput(new String[]{"0", "1", "1"});
        Tracker tracker = new Tracker();
        tracker.add(new Item("FindByName"));
        UserAction[] actions = {new SearchItemIdAction(output), new Exit()};
        new StartUI(output).init(input, tracker, actions);
        assertThat(output.toString(), is("Меню." + System.lineSeparator() + "0. Найти заявку по Id"
                + System.lineSeparator() + "1. Выход" + System.lineSeparator() + "==Найти заявку по Id==" + System.lineSeparator() + "Item{id=1, name='FindByName'}"
                + System.lineSeparator() + "Меню." + System.lineSeparator() + "0. Найти заявку по Id" + System.lineSeparator()
                + "1. Выход" + System.lineSeparator()));
    }

}