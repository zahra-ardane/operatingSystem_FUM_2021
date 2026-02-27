package os;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Scheduler {

    //First Come First Served
    public Queue FCFSAlgorithm(Task[] tasks) {
        Queue queue = new Queue();
        for (Task task : tasks) {
            queue.add(task);
        }
        return queue;
    }

    //Round Robin
    public Queue RRAlgorithm(Task[] tasks) {
        Queue sorted = new Queue();
//        int done = 0;
//        int i = 0;
//        while (done < tasks.length) {
//            if (i == tasks.length)
//                i = 0;
//            if (tasks[i].getTimeLeft() > 0) {
//                sorted.add(tasks[i]);
//                tasks[i].reduceTimeLeft();
//            } else if (tasks[i].getTimeLeft() == 0) {
//                done++;
//                tasks[i].reduceTimeLeft();
//            }
//            i++;
//        }
        for (Task task : tasks) {
            sorted.add(task);
        }
        return sorted;
    }

    public Queue SFJAlgorithm(Task[] tasks) {
        Queue sorted = new Queue();
        Task[] copiedTasks = Arrays.copyOf(tasks, tasks.length);
        Arrays.sort(copiedTasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getDuration() - o2.getDuration();
            }
        });
        for (Task task : copiedTasks) {
            sorted.add(task);
        }
        return sorted;

    }

    public Queue HRRNAlgorithm(Task[] tasks) {
        double maxResponseRatio = -9999;
        int responseRatioIndex = 0;
        int currentWaitingTime = 0;
        Queue sorted = new Queue();
        List<Task> tasksList = Arrays.asList(tasks);
        ArrayList<Task> tasksArrayList = new ArrayList<Task>(tasksList);
        for (int j = 0; j < tasks.length; j++) {
            responseRatioIndex = 0;
            maxResponseRatio = -9999;
            for (int i = 0; i < tasksArrayList.size(); i++) {
                double newResponseTime = ((double) currentWaitingTime
                        + (double) tasksArrayList.get(i).getDuration()) / (double) tasksArrayList.get(i).getDuration();
                if (newResponseTime > maxResponseRatio) {
                    maxResponseRatio = newResponseTime;
                    responseRatioIndex = i;
                }
            }
            sorted.add(tasksArrayList.get(responseRatioIndex));
            currentWaitingTime += tasksArrayList.get(responseRatioIndex).getDuration();
            tasksArrayList.remove(responseRatioIndex);
        }
        return sorted;
    }

}
