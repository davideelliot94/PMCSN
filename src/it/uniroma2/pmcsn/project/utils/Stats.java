package it.uniroma2.pmcsn.project.utils;

import it.uniroma2.pmcsn.project.entity.Area;
import it.uniroma2.pmcsn.project.entity.LastState;
import it.uniroma2.pmcsn.project.entity.State;

import java.util.ArrayList;

import static it.uniroma2.pmcsn.project.utils.Constants.NUM_BATCH;
import static java.lang.StrictMath.sqrt;

public class Stats {

    private double responseBatch;
    private double responseType1Batch;
    private double responseType2Batch;
    private  double responseTimeInterrupted;
    private double jobInterr;
    private  double trBatch;
    private  double trType1Batch;
    private  double trType2Batch;
    private  double trCloudlet;
    private  double tr1CloudletBatch;
    private  double tr2CloudletBatch;
    private  double resp1Cloud;
    private  double resp2Cloud;
    private   double respCloud;
    private   double resp1Cloudlet;
    private   double resp2Cloudlet;
    private   double respCloudlet;
    private   double averagePopCloud;
    private   double averagePopCloudlet;
    private double tr1CloudBatch;
    private double tr2CloudBatch;
    private double trCloud;

    Rvms rvms = new Rvms();

    public double getTrCloud() {
        return trCloud;
    }

    public void setTrCloud(double trCloud) {
        this.trCloud = trCloud;
    }

    public double getTr1CloudBatch() {
        return tr1CloudBatch;
    }

    public void setTr1CloudBatch(double tr1CloudBatch) {
        this.tr1CloudBatch = tr1CloudBatch;
    }

    public double getTr2CloudBatch() {
        return tr2CloudBatch;
    }

    public void setTr2CloudBatch(double tr2CloudBatch) {
        this.tr2CloudBatch = tr2CloudBatch;
    }

    public double getResponseBatch() {
        return responseBatch;
    }

    public void setResponseBatch(double responseBatch) {
        this.responseBatch = responseBatch;
    }

    public double getResponseType1Batch() {
        return responseType1Batch;
    }

    public void setResponseType1Batch(double responseType1Batch) {
        this.responseType1Batch = responseType1Batch;
    }

    public double getResponseType2Batch() {
        return responseType2Batch;
    }

    public void setResponseType2Batch(double responseType2Batch) {
        this.responseType2Batch = responseType2Batch;
    }

    public double getResponseTimeInterrupted() {
        return responseTimeInterrupted;
    }

    public void setResponseTimeInterrupted(double responseTimeInterrupted) {
        this.responseTimeInterrupted = responseTimeInterrupted;
    }

    public double getJobInterr() {
        return jobInterr;
    }

    public void setJobInterr(double jobInterr) {
        this.jobInterr = jobInterr;
    }

    public double getTrBatch() {
        return trBatch;
    }

    public void setTrBatch(double trBatch) {
        this.trBatch = trBatch;
    }

    public double getTrType1Batch() {
        return trType1Batch;
    }

    public void setTrType1Batch(double trType1Batch) {
        this.trType1Batch = trType1Batch;
    }

    public double getTrType2Batch() {
        return trType2Batch;
    }

    public void setTrType2Batch(double trType2Batch) {
        this.trType2Batch = trType2Batch;
    }

    public double getTrCloudlet() {
        return trCloudlet;
    }

    public void setTrCloudlet(double trCloudlet) {
        this.trCloudlet = trCloudlet;
    }

    public double getTr1CloudletBatch() {
        return tr1CloudletBatch;
    }

    public void setTr1CloudletBatch(double tr1CloudletBatch) {
        this.tr1CloudletBatch = tr1CloudletBatch;
    }

    public double getTr2CloudletBatch() {
        return tr2CloudletBatch;
    }

    public void setTr2CloudletBatch(double tr2CloudletBatch) {
        this.tr2CloudletBatch = tr2CloudletBatch;
    }

    public double getResp1Cloud() {
        return resp1Cloud;
    }

    public void setResp1Cloud(double resp1Cloud) {
        this.resp1Cloud = resp1Cloud;
    }

    public double getResp2Cloud() {
        return resp2Cloud;
    }

    public void setResp2Cloud(double resp2Cloud) {
        this.resp2Cloud = resp2Cloud;
    }

    public double getRespCloud() {
        return respCloud;
    }

    public void setRespCloud(double respCloud) {
        this.respCloud = respCloud;
    }

    public double getResp1Cloudlet() {
        return resp1Cloudlet;
    }

    public void setResp1Cloudlet(double resp1Cloudlet) {
        this.resp1Cloudlet = resp1Cloudlet;
    }

    public double getResp2Cloudlet() {
        return resp2Cloudlet;
    }

    public void setResp2Cloudlet(double resp2Cloudlet) {
        this.resp2Cloudlet = resp2Cloudlet;
    }

    public double getRespCloudlet() {
        return respCloudlet;
    }

    public void setRespCloudlet(double respCloudlet) {
        this.respCloudlet = respCloudlet;
    }

    public double getAveragePopCloud() {
        return averagePopCloud;
    }

    public void setAveragePopCloud(double averagePopCloud) {
        this.averagePopCloud = averagePopCloud;
    }

    public double getAveragePopCloudlet() {
        return averagePopCloudlet;
    }

    public void setAveragePopCloudlet(double averagePopCloudlet) {
        this.averagePopCloudlet = averagePopCloudlet;
    }

    public ArrayList<Object> calculateBatch(double timeNext, State state, Area area, LastState lastState, Stats stats) {

        stats.setTrBatch((state.getNum_task_completed() - lastState.getLast_num_task_completed())/(timeNext-lastState.getLast_obs()));//batch[i] del throughput
        stats.setTrType1Batch((state.getNum_task_type1() - lastState.getLast_num_type1_completed())/(timeNext-lastState.getLast_obs()));
        stats.setTrType2Batch((state.getNum_task_type2() - lastState.getLast_num_type2_completed())/(timeNext-lastState.getLast_obs()));

        double complete_task_cloudlet_type1=state.getNum_task_type1_cloudlet();
        double complete_task_cloudlet_type2=state.getNum_task_type2_cloudlet()-state.getNum_task_interrupted();
        double complete_task_cloud_type1=state.getNum_task_type1_cloud();
        double complete_task_cloud_type2=state.getNum_task_type2_cloud()-state.getNum_task_interrupted();

        stats.setTr1CloudletBatch((complete_task_cloudlet_type1 - lastState.getLast_num_type1_cloudlet())/(timeNext - lastState.getLast_obs()));
        stats.setTr2CloudletBatch((complete_task_cloudlet_type2 - lastState.getLast_num_type2_cloudlet())/(timeNext - lastState.getLast_obs()));
        stats.setTr1CloudBatch((complete_task_cloud_type1 - lastState.getLast_num_type1_cloud())/(timeNext - lastState.getLast_obs()));
        stats.setTr1CloudBatch((complete_task_cloud_type2 - lastState.getLast_num_type2_cloud())/(timeNext - lastState.getLast_obs()));

        stats.setTrCloudlet(((complete_task_cloudlet_type1 + complete_task_cloudlet_type2) - (lastState.getLast_num_type1_cloudlet() + lastState.getLast_num_type2_cloudlet()))/(timeNext - lastState.getLast_obs()));
        stats.setTrCloud(((complete_task_cloud_type1 + complete_task_cloud_type2) - (lastState.getLast_num_type1_cloud() + lastState.getLast_num_type2_cloud()))/(timeNext - lastState.getLast_obs()));

        stats.setJobInterr((double)(state.getNum_task_interrupted()-lastState.getLast_num_task_interrupted())/(state.getNum_task_arrived()-lastState.getLast_num_task_arrived()));
        stats.setResponseBatch(((area.getType1() + area.getType2()) - lastState.getLast_area())/(state.getNum_task_arrived() - lastState.getLast_task_arrived()));
        stats.setResponseType1Batch(((area.getType1() - lastState.getLast_area_type1())/(state.getNum_task_type1() - lastState.getLast_num_task1_arrived())));
        stats.setResponseType2Batch(((area.getType2() - lastState.getLast_area_type2())/(state.getNum_task_type2() - lastState.getLast_num_task2_arrived())));

        stats.setResponseTimeInterrupted((area.getJob_interr() - lastState.getLast_area_interrupted())/(state.getNum_task_interrupted() - lastState.getOld_num_interr()));

        stats.setResp1Cloud(((area.getCloud_type1()-lastState.getLast_area_cloud_type1())/(state.getNum_task_type1_cloud() - lastState.getLast_num_task_type1_cloud())));
        stats.setResp2Cloud(((area.getCloud_type2() - lastState.getLast_area_cloud_type2())/(state.getNum_task_type2_cloud() - lastState.getLast_num_task_type2_cloud())));
        stats.setRespCloud((area.getCloud() - (lastState.getLast_area_cloud_type2() + lastState.getLast_area_cloud_type1()))/((state.getNum_task_type2_cloud() + state.getNum_task_type1_cloud())-(lastState.getLast_num_task_type1_cloud() + lastState.getLast_num_task_type2_cloud())));
        stats.setResp1Cloudlet(((area.getCloudlet_type1()-lastState.getLast_area_cloudlet_type1())/(state.getNum_task_type1_cloudlet() - lastState.getLast_num_task_type1_cloudlet())));
        stats.setResp2Cloudlet(((area.getCloudlet_type2_v()-lastState.getLast_area_cloudlet_type2())/((state.getNum_task_type2_cloudlet()-state.getNum_task_interrupted()) - (lastState.getLast_num_task_type2_cloudlet()-lastState.getLast_num_task_interrupted()))));
        stats.setRespCloudlet(((area.getCloudlet()-lastState.getOld_area_cloudlet())/(state.getNum_task_cloudlet() - (lastState.getLast_num_task_type1_cloudlet()+lastState.getLast_num_task_type2_cloudlet()))));
        stats.setAveragePopCloud(((area.getCloud() - lastState.getOld_area_cloud()))/(timeNext - lastState.getLast_obs()));
        stats.setAveragePopCloudlet(((area.getCloudlet() - lastState.getOld_area_cloudlet()))/(timeNext - lastState.getLast_obs()));

        lastState.setLast_num_type1_cloudlet(state.getNum_task_type1_cloudlet());
        lastState.setLast_num_type2_cloudlet(state.getNum_task_type2_cloudlet() - state.getNum_task_interrupted());
        lastState.setLast_num_type1_cloud(state.getNum_task_type1_cloud());
        lastState.setLast_num_type2_cloud(state.getNum_task_type2_cloud() - state.getNum_task_interrupted());
        lastState.setOld_area_cloud(area.getCloud());
        lastState.setOld_area_cloudlet(area.getCloudlet());
        lastState.setLast_area((int) (area.getType1() + area.getType2()));
        lastState.setLast_area_interrupted(area.getJob_interr());
        lastState.setLast_num_task_interrupted(state.getNum_task_interrupted());
        lastState.setOld_num_interr(state.getNum_task_interrupted());
        lastState.setLast_task_arrived(state.getNum_task_arrived());
        lastState.setLast_num_task1_arrived(state.getNum_task_type1());
        lastState.setLast_num_task2_arrived(state.getNum_task_type2());
        lastState.setLast_area_type1(area.getType1());
        lastState.setLast_area_type2(area.getType2());
        lastState.setLast_num_type1_completed(state.getNum_task_type1());
        lastState.setLast_num_type2_completed(state.getNum_task_type2());
        lastState.setLast_num_task_arrived(state.getNum_task_arrived());
        lastState.setLast_num_task_completed(state.getNum_task_completed());
        lastState.setLast_num_task_interrupted(state.getNum_task_interrupted());
        lastState.setLast_area_cloud_type1(area.getCloud_type1());
        lastState.setLast_area_cloud_type2(area.getCloud_type2());
        lastState.setLast_num_task_type1_cloud(state.getNum_task_type1_cloud());
        lastState.setLast_num_task_type2_cloud(state.getNum_task_type2_cloud());
        lastState.setLast_area_cloudlet_type1(area.getCloudlet_type1());
        lastState.setLast_area_cloudlet_type2(area.getCloudlet_type2_v());
        lastState.setLast_num_task_type1_cloudlet(state.getNum_task_type1_cloudlet());
        lastState.setLast_num_task_type2_cloudlet(state.getNum_task_type2_cloudlet());

        Constants.i++;//aumento batch i;

        lastState.setLast_obs(timeNext);

        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(lastState);
        arrayList.add(stats);

        return arrayList;
    }

    public void printConfidenceInterval(Stats stats) {

        System.out.println("\nREQUEST C.3.1:\n\n");
        calculate_and_print_interval(stats.getResponseBatch(),"Average system response time");

        calculate_and_print_interval(stats.getResponseType1Batch(),"Average system response time class 1");
        calculate_and_print_interval(stats.getResponseType2Batch(),"Average system response time class 2");

        calculate_and_print_interval(stats.getTrBatch(),"Throughput global");
        calculate_and_print_interval(stats.getTrType1Batch(),"Throughput global class 1");
        calculate_and_print_interval(stats.getTrType2Batch(),"Throughput global class 2");

        System.out.println("----------------------------------------------------------\n");
        System.out.println("\nREQUEST C.3.2:\n\n");

        calculate_and_print_interval(stats.getTrCloudlet(),"Throughput cloudlet global");
        //calculate_and_print_interval(stats.gett,"Throughput cloudlet class 1");
        //calculate_and_print_interval(tr_cloudlet_type2,"Throughput cloudlet class 2");

        System.out.println("----------------------------------------------------------\n");
        System.out.println("\nREQUEST C.3.3\n\n");
        calculate_and_print_interval(stats.getTrCloud(),"Throughput cloud global");
        calculate_and_print_interval(stats.getTr1CloudBatch(),"Throughput cloud class 1");
        calculate_and_print_interval(stats.getTr2CloudBatch(),"Throughput cloud class 2");

        System.out.println("----------------------------------------------------------\n");
        System.out.println("\nREQUEST C.3.4\n\n");
        calculate_and_print_interval(stats.getRespCloud(),"Average system response time cloud");
        calculate_and_print_interval(stats.getResp1Cloud(),"Average system response time cloud class_1");
        calculate_and_print_interval(stats.getResp2Cloud(),"Average system response time cloud class_2");
        calculate_and_print_interval(stats.getRespCloudlet(),"Average system response time cloudlet");
        calculate_and_print_interval(stats.getResp1Cloudlet(),"Average system response time cloudlet class_1");
        calculate_and_print_interval(stats.getResp2Cloudlet(),"Average system response time cloudlet class_2");
        calculate_and_print_interval(stats.getAveragePopCloud(),"Average population cloud");
        calculate_and_print_interval(stats.getAveragePopCloudlet(),"Average population cloudlet");
    }

    private void calculate_and_print_interval(double batch, String string)
    {
        double alpha = 0.05;
        double t,delta;//t=valore critico della student,delta=lunghezza intervallo/2

        double mean = 0.0, s = 0.0;

        if(string == null){
            //handle_error_with_exit("error  in calculate and print interval");
        }
        t  = rvms.idfStudent(NUM_BATCH - 1, 1-alpha/2);//calcolo del valore critico

        calculate_mean_and_deviation(batch, NUM_BATCH, mean, s);

        delta = t * s / (sqrt(NUM_BATCH-1));//calcolo di delta
        System.out.println(string);
        System.out.println(mean + " " + delta + " " + (mean-delta) + " " + (mean+delta));//stampa intervallo
    }

    private double calculate_mean_and_deviation(double array, int length, double mean, double deviation){//calcola media e deviazione standard con un solo ciclo for
        /*if(array == null || length<=0|| mean== NULL || deviation==NULL){
            handle_error_with_exit("error in calculate mean_and_deviation\n");
        }*/

        double m=0.0;
        double  res= 0.0;

        for(int i = 0; i <length; i++){
            m = m + array;
            res = res + ((array)*(array));

        }
        m=m/length;
        mean=m;
        deviation= res - length*m*m;
        return deviation;

    }
}
