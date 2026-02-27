package os;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        Queue readyQueue;
        Scanner scanner = new Scanner(System.in);
        Task[] tasks;
        Task runningTask = null;
        //how long task has been on cpu
        int newTaskRunTime = 0;
        //int[] available = new int[3];
        int numberOfTasks;
        String[][] tasksInformation;
        int time = 0;
        boolean isQueueEmpty = false;
        //read available resources
        //for (int i = 0; i < 3; i++) {
        //    available[i] = scanner.nextInt();
        // }
//        for(int i=0;i<3;i++){
//            System.out.println(available[i]);
//        }
        numberOfTasks = scanner.nextInt();
        scanner.nextLine();
        tasksInformation = new String[numberOfTasks][3];
        for (int i = 0; i < numberOfTasks; i++) {
            String lineEntered = scanner.nextLine();
            String[] parts = lineEntered.split(" ");
            System.arraycopy(parts, 0, tasksInformation[i], 0, 3);
        }
        tasks = new Task[numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            Task task = new Task(tasksInformation[i][0], Integer.parseInt(tasksInformation[i][2]), tasksInformation[i][1].charAt(0));
            tasks[i] = task;
        }
        System.out.println("running SJF algorithm:");
        readyQueue = scheduler.SFJAlgorithm(tasks);
        while (!isQueueEmpty) {
            System.out.println("current time unit: " + time);
            if (time == 0 && readyQueue.isEmpty()) {
                System.out.println("Queue empty returning now");
                break;
            }
            if (time == 0) {
                runningTask = readyQueue.element();
                if (runningTask.getDuration() != 0) {
                    newTaskRunTime = 0;
                    runningTask.changeAge(newTaskRunTime);
                    runningTask.changeState(3);
                    System.out.print("Queue :");
                    readyQueue.print();

                    System.out.println("\ntask running: " + runningTask.getName() + ". this task has been on CPU for "
                            + runningTask.getAge() + " time units");
                }

            } else {

                if (newTaskRunTime == runningTask.getDuration()) {
                    runningTask.changeAge(newTaskRunTime);
                    System.out.println("task " + runningTask.getName() + " finished it was in the CPU for "
                            + runningTask.getAge() + " time units");
                    runningTask.changeState(2);
                    readyQueue.remove();
                    if (!readyQueue.isEmpty()) {
                        runningTask = readyQueue.element();
                        newTaskRunTime = 0;
                        runningTask.changeAge(newTaskRunTime);

                        runningTask.changeState(3);
                        System.out.print("Queue :");
                        readyQueue.print();
                        System.out.println("\ntask running: " + runningTask.getName()
                                + ". this task has been on CPU for " + runningTask.getAge() + " time units");
                    } else {
                        isQueueEmpty = true;
                    }
                } else {
                    runningTask.changeAge(newTaskRunTime);
                    System.out.print("Queue :");
                    readyQueue.print();

                    System.out.println("\ntask running: " + runningTask.getName() + ". this task has been on CPU for "
                            + runningTask.getAge() + " time units");
                }

            }
            time++;
            newTaskRunTime++;

        }
        System.out.print("Queue :");
        readyQueue.print();

        isQueueEmpty = false;
        time = 0;
        newTaskRunTime = 0;


        System.out.println("\n\nrunning FCFS algorithm:");
        readyQueue = scheduler.FCFSAlgorithm(tasks);
        while (!isQueueEmpty) {
            System.out.println("current time unit: " + time);
            if (time == 0 && readyQueue.isEmpty()) {
                System.out.println("Queue empty returning now");
                break;
            }
            if (time == 0) {
                runningTask = readyQueue.element();
                if (runningTask.getDuration() != 0) {
                    newTaskRunTime = 0;
                    runningTask.changeAge(newTaskRunTime);
                    runningTask.changeState(3);
                    System.out.print("Queue :");
                    readyQueue.print();

                    System.out.println("\ntask running: " + runningTask.getName() + ". this task has been on CPU for "
                            + runningTask.getAge() + " time units");
                }

            } else {

                if (newTaskRunTime == runningTask.getDuration()) {
                    runningTask.changeAge(newTaskRunTime);
                    System.out.println("task " + runningTask.getName() + " finished it was in the CPU for "
                            + runningTask.getAge() + " time units");
                    runningTask.changeState(2);
                    readyQueue.remove();
                    if (!readyQueue.isEmpty()) {
                        runningTask = readyQueue.element();
                        newTaskRunTime = 0;
                        runningTask.changeAge(newTaskRunTime);

                        runningTask.changeState(3);
                        System.out.print("Queue :");
                        readyQueue.print();
                        System.out.println("\ntask running: " + runningTask.getName()
                                + ". this task has been on CPU for " + runningTask.getAge() + " time units");
                    } else {
                        isQueueEmpty = true;
                    }
                } else {
                    runningTask.changeAge(newTaskRunTime);
                    System.out.print("Queue :");
                    readyQueue.print();

                    System.out.println("\ntask running: " + runningTask.getName()
                            + ". this task has been on CPU for " + runningTask.getAge() + " time units");
                }

            }
            time++;
            newTaskRunTime++;

        }
        System.out.print("Queue :");
        readyQueue.print();

        isQueueEmpty = false;
        time = 0;
        newTaskRunTime = 0;

        System.out.println("\n\nrunning HRRN algorithm:");
        readyQueue = scheduler.HRRNAlgorithm(tasks);
        while (!isQueueEmpty) {
            System.out.println("current time unit: " + time);
            if (time == 0 && readyQueue.isEmpty()) {
                System.out.println("Queue empty returning now");
                break;
            }
            if (time == 0) {
                runningTask = readyQueue.element();
                if (runningTask.getDuration() != 0) {
                    newTaskRunTime = 0;
                    runningTask.changeAge(newTaskRunTime);
                    runningTask.changeState(3);
                    System.out.print("Queue :");
                    readyQueue.print();

                    System.out.println("\ntask running: " + runningTask.getName()
                            + ". this task has been on CPU for " + runningTask.getAge() + " time units");
                }

            } else {

                if (newTaskRunTime == runningTask.getDuration()) {
                    runningTask.changeAge(newTaskRunTime);
                    System.out.println("task " + runningTask.getName()
                            + " finished it was in the CPU for " + runningTask.getAge() + " time units");
                    runningTask.changeState(2);
                    readyQueue.remove();
                    if (!readyQueue.isEmpty()) {
                        runningTask = readyQueue.element();
                        newTaskRunTime = 0;
                        runningTask.changeAge(newTaskRunTime);

                        runningTask.changeState(3);
                        System.out.print("Queue :");
                        readyQueue.print();
                        System.out.println("\ntask running: " + runningTask.getName()
                                + ". this task has been on CPU for " + runningTask.getAge() + " time units");
                    } else {
                        isQueueEmpty = true;
                    }
                } else {
                    runningTask.changeAge(newTaskRunTime);
                    System.out.print("Queue :");
                    readyQueue.print();

                    System.out.println("\ntask running: " + runningTask.getName() + ". this task has been on CPU for "
                            + runningTask.getAge() + " time units");
                }

            }
            time++;
            newTaskRunTime++;

        }
        System.out.print("Queue :");
        readyQueue.print();

        System.out.println("\n\nRunning Round Robin Algorithm:");
        readyQueue = scheduler.RRAlgorithm(tasks);
//        String[] names = new String[tasks.length];
//        int[] times = new int[tasks.length];
//        for (int i = 0; i < tasks.length; i++) {
//            names[i] = tasks[i].getName();
//            times[i] = 0;
//        }
//        time = 0;
//        int i;
//        runningTask = readyQueue.element();
//        for (i = 0; i < names.length; i++) {
//            if (readyQueue.element().getName().equals(names[i]))
//                break;
//        }
//        while (!readyQueue.isEmpty()) {
//            System.out.println("current time unit: " + time++);
//            if (times[i] == runningTask.getDuration()) {
//                runningTask.changeState(2);
//                System.out.println("task " + runningTask.getName() + ".finished it was in the CPU for "
//                        + times[i] + " time units.");
//            } else
//                runningTask.changeState(1);
//            runningTask = readyQueue.element();
//            runningTask.changeState(3);
//            System.out.print("Queue :");
//            readyQueue.print();
//            System.out.println();
//            for (i = 0; i < names.length; i++) {
//                if (runningTask.getName().equals(names[i])) {
//                    //times[i]++;
//                    break;
//                }
//            }
//            System.out.println("task running: " + runningTask.getName() + ". this task has been on CPU for "
//                    + times[i] + " time unit(s).");
//            times[i]++;
//            readyQueue.remove();
//        }
//        System.out.println("current time unit: " + time);
//        runningTask.changeState(2);
//        System.out.println("task " + runningTask.getName() + " finished. it was in the CPU for "
//                + times[i] + " time units.");


        time = 0;
        runningTask = readyQueue.element();
        while (!readyQueue.isEmpty()) {
            System.out.println("current time unit: " + time);

            if (runningTask.getTimeLeft() == 0) {
                runningTask.changeState(2);
                System.out.println("task " + runningTask.getName() + " finished. it was in the CPU for "
                        + (runningTask.getDuration() - runningTask.getTimeLeft()) + " time units.");
                readyQueue.remove();
            } else if (time != 0) {
                readyQueue.add(runningTask);
                runningTask.changeState(1);
                readyQueue.remove();
            }

            System.out.print("Queue :");
            readyQueue.print();
            System.out.println();

            if (!readyQueue.isEmpty()) {

                runningTask = readyQueue.element();
                runningTask.changeState(3);
                System.out.println("task running: " + runningTask.getName() + ". this task has been on CPU for "
                        + (runningTask.getDuration() - runningTask.getTimeLeft()) + " time unit(s).");
                runningTask.reduceTimeLeft();
            }


            time++;
        }

    }
}
