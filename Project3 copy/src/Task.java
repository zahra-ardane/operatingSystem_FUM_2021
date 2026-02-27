package os;

public class Task {
    private final String name;
    //type : X = 1, Y = 2, Z = 3, zero means invalid
    //state : ready = 1, done = 2, running = 3
    private final int duration, type;
    private int state, age, timeLeft;

    public Task(String name, int duration, char type) {
        this.name = name;
        if (type == 'X' || type == 'x')
            this.type = 1;
        else if (type == 'Y' || type == 'y')
            this.type = 2;
        else if (type == 'Z' || type == 'z')
            this.type = 3;
        else
            this.type = 0;
        this.duration = duration;
        state = 1;
        age = 0;
        timeLeft = duration;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public int getState() {
        return state;
    }

    public void changeState(int newState) {
        state = newState;
    }

    public int getAge() {
        return age;
    }

    public void changeAge(int newAge) {
        age = newAge;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void reduceTimeLeft() {
        timeLeft--;
    }

//    public boolean isTypeValid() {
//        return type != 0;
//    }L

}
