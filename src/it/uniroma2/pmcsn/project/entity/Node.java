package it.uniroma2.pmcsn.project.entity;

public class Node {

    private double instant_end_service;
    private int task_type;//tipo di job:1 o 2
    private double time_arrive;//istante di arrivo del job
    private double time_lost_in_cloudlet;
    private int interrupted;//0 se task non interrotto,1 se task interrotto

    public double getInstant_end_service() {
        return instant_end_service;
    }

    public void setInstant_end_service(double instant_end_service) {
        this.instant_end_service = instant_end_service;
    }

    public int getTask_type() {
        return task_type;
    }

    public void setTask_type(int task_type) {
        this.task_type = task_type;
    }

    public double getTime_arrive() {
        return time_arrive;
    }

    public void setTime_arrive(double time_arrive) {
        this.time_arrive = time_arrive;
    }

    public double getTime_lost_in_cloudlet() {
        return time_lost_in_cloudlet;
    }

    public void setTime_lost_in_cloudlet(double time_lost_in_cloudlet) {
        this.time_lost_in_cloudlet = time_lost_in_cloudlet;
    }

    public int getInterrupted() {
        return interrupted;
    }

    public void setInterrupted(int interrupted) {
        this.interrupted = interrupted;
    }

}
