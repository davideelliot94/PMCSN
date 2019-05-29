package it.uniroma2.pmcsn.project.entity;

public class LastState {

    private int last_num_task_completed;
    private  int last_num_task_arrived;
    private int last_num_task1_arrived;
    private int last_num_task2_arrived;
    private int last_num_task_interrupted;
    private int last_num_type1_completed;
    private int last_num_type2_completed;
    private  int last_area;
    private  int last_task_arrived;
    private   int last_num_type1_cloudlet;
    private   int last_num_type2_cloudlet;
    private   int last_num_type1_cloud;
    private  int last_num_type2_cloud;
    private   double last_area_interrupted;
    private   int old_num_interr;
    private   double last_area_cloud_type1;
    private   int last_num_task_type1_cloud;
    private   double last_area_cloud_type2;
    private   int last_num_task_type2_cloud;
    private   double last_area_cloudlet_type1;
    private   double last_area_cloudlet_type2;
    private   int last_num_task_type1_cloudlet;
    private   int last_num_task_type2_cloudlet;
    private   double old_area_cloud;
    private   double old_area_cloudlet;
    private   double last_area_type1;
    private   double last_area_type2;
    private   double last_obs;

    public LastState() {
    }

    public int getLast_num_task_completed() {
        return last_num_task_completed;
    }

    public void setLast_num_task_completed(int last_num_task_completed) {
        this.last_num_task_completed = last_num_task_completed;
    }

    public int getLast_num_task_arrived() {
        return last_num_task_arrived;
    }

    public void setLast_num_task_arrived(int last_num_task_arrived) {
        this.last_num_task_arrived = last_num_task_arrived;
    }

    public int getLast_num_task1_arrived() {
        return last_num_task1_arrived;
    }

    public void setLast_num_task1_arrived(int last_num_task1_arrived) {
        this.last_num_task1_arrived = last_num_task1_arrived;
    }

    public int getLast_num_task2_arrived() {
        return last_num_task2_arrived;
    }

    public void setLast_num_task2_arrived(int last_num_task2_arrived) {
        this.last_num_task2_arrived = last_num_task2_arrived;
    }

    public int getLast_num_task_interrupted() {
        return last_num_task_interrupted;
    }

    public void setLast_num_task_interrupted(int last_num_task_interrupted) {
        this.last_num_task_interrupted = last_num_task_interrupted;
    }

    public int getLast_num_type1_completed() {
        return last_num_type1_completed;
    }

    public void setLast_num_type1_completed(int last_num_type1_completed) {
        this.last_num_type1_completed = last_num_type1_completed;
    }

    public int getLast_num_type2_completed() {
        return last_num_type2_completed;
    }

    public void setLast_num_type2_completed(int last_num_type2_completed) {
        this.last_num_type2_completed = last_num_type2_completed;
    }

    public int getLast_area() {
        return last_area;
    }

    public void setLast_area(int last_area) {
        this.last_area = last_area;
    }

    public int getLast_task_arrived() {
        return last_task_arrived;
    }

    public void setLast_task_arrived(int last_task_arrived) {
        this.last_task_arrived = last_task_arrived;
    }

    public int getLast_num_type1_cloudlet() {
        return last_num_type1_cloudlet;
    }

    public void setLast_num_type1_cloudlet(int last_num_type1_cloudlet) {
        this.last_num_type1_cloudlet = last_num_type1_cloudlet;
    }

    public int getLast_num_type2_cloudlet() {
        return last_num_type2_cloudlet;
    }

    public void setLast_num_type2_cloudlet(int last_num_type2_cloudlet) {
        this.last_num_type2_cloudlet = last_num_type2_cloudlet;
    }

    public int getLast_num_type1_cloud() {
        return last_num_type1_cloud;
    }

    public void setLast_num_type1_cloud(int last_num_type1_cloud) {
        this.last_num_type1_cloud = last_num_type1_cloud;
    }

    public int getLast_num_type2_cloud() {
        return last_num_type2_cloud;
    }

    public void setLast_num_type2_cloud(int last_num_type2_cloud) {
        this.last_num_type2_cloud = last_num_type2_cloud;
    }

    public double getLast_area_interrupted() {
        return last_area_interrupted;
    }

    public void setLast_area_interrupted(double last_area_interrupted) {
        this.last_area_interrupted = last_area_interrupted;
    }

    public int getOld_num_interr() {
        return old_num_interr;
    }

    public void setOld_num_interr(int old_num_interr) {
        this.old_num_interr = old_num_interr;
    }

    public double getLast_area_cloud_type1() {
        return last_area_cloud_type1;
    }

    public void setLast_area_cloud_type1(double last_area_cloud_type1) {
        this.last_area_cloud_type1 = last_area_cloud_type1;
    }

    public int getLast_num_task_type1_cloud() {
        return last_num_task_type1_cloud;
    }

    public void setLast_num_task_type1_cloud(int last_num_task_type1_cloud) {
        this.last_num_task_type1_cloud = last_num_task_type1_cloud;
    }

    public double getLast_area_cloud_type2() {
        return last_area_cloud_type2;
    }

    public void setLast_area_cloud_type2(double last_area_cloud_type2) {
        this.last_area_cloud_type2 = last_area_cloud_type2;
    }

    public int getLast_num_task_type2_cloud() {
        return last_num_task_type2_cloud;
    }

    public void setLast_num_task_type2_cloud(int last_num_task_type2_cloud) {
        this.last_num_task_type2_cloud = last_num_task_type2_cloud;
    }

    public double getLast_area_cloudlet_type1() {
        return last_area_cloudlet_type1;
    }

    public void setLast_area_cloudlet_type1(double last_area_cloudlet_type1) {
        this.last_area_cloudlet_type1 = last_area_cloudlet_type1;
    }

    public double getLast_area_cloudlet_type2() {
        return last_area_cloudlet_type2;
    }

    public void setLast_area_cloudlet_type2(double last_area_cloudlet_type2) {
        this.last_area_cloudlet_type2 = last_area_cloudlet_type2;
    }

    public int getLast_num_task_type1_cloudlet() {
        return last_num_task_type1_cloudlet;
    }

    public void setLast_num_task_type1_cloudlet(int last_num_task_type1_cloudlet) {
        this.last_num_task_type1_cloudlet = last_num_task_type1_cloudlet;
    }

    public int getLast_num_task_type2_cloudlet() {
        return last_num_task_type2_cloudlet;
    }

    public void setLast_num_task_type2_cloudlet(int last_num_task_type2_cloudlet) {
        this.last_num_task_type2_cloudlet = last_num_task_type2_cloudlet;
    }

    public double getOld_area_cloud() {
        return old_area_cloud;
    }

    public void setOld_area_cloud(double old_area_cloud) {
        this.old_area_cloud = old_area_cloud;
    }

    public double getOld_area_cloudlet() {
        return old_area_cloudlet;
    }

    public void setOld_area_cloudlet(double old_area_cloudlet) {
        this.old_area_cloudlet = old_area_cloudlet;
    }

    public double getLast_area_type1() {
        return last_area_type1;
    }

    public void setLast_area_type1(double last_area_type1) {
        this.last_area_type1 = last_area_type1;
    }

    public double getLast_area_type2() {
        return last_area_type2;
    }

    public void setLast_area_type2(double last_area_type2) {
        this.last_area_type2 = last_area_type2;
    }

    public double getLast_obs() {
        return last_obs;
    }

    public void setLast_obs(double last_obs) {
        this.last_obs = last_obs;
    }
}
