package os;

import java.util.ArrayList;

public class Queue {

    private ArrayList<Task> tasks = new ArrayList<>();

    public void add(Task newTask) {
        tasks.add(newTask);
    }

    //removes last object in array
    public void remove() {
        tasks.remove(0);
    }

    //returns object on top of the array
    public Task element() {
        return tasks.get(0);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public void print() {
        for (Task task : tasks)
            System.out.print(" " + task.getName());
    }

}
