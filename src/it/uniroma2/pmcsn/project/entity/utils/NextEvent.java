package it.uniroma2.pmcsn.project.entity.utils;

import it.uniroma2.pmcsn.project.entity.distributions.Distributions;

import java.util.concurrent.CopyOnWriteArrayList;

public class NextEvent {

    private int init = 0;
    private double[] mean = new double[]{1 / Constants.ARRIVALONE, 1 / Constants.ARRIVALTWO};
    private double[] arrivals = new double[]{Constants.START, Constants.START};
    private double service;
    private double setup;
    private double serviceCloud;

    private Rngs rngs = new Rngs();
    private Distributions dist = new Distributions();

    public double getArrivalCloudlet(int taskType, int newReplication) {

        if (taskType != 1 && taskType != 2) {
            // gestisci errore
        }




        if (newReplication == 1) {

            init = 0;
            arrivals[0] = Constants.START;
            arrivals[1] = Constants.START;

        }


        double arrival;
        if (init < 2) {
            if (init == 0) {
                rngs.selectStream(Constants.STREAM_ARR1);
                arrivals[0] += dist.Exponential(mean[0]);
                init++;
                return arrivals[0];
            }
            if (init == 1) {

                rngs.selectStream(Constants.STREAM_ARR2);
                arrivals[1] += dist.Exponential(mean[1]);
                init++;
                return arrivals[1];

            }
        }
        if (taskType == 1) {
            rngs.selectStream(Constants.STREAM_ARR1);
            arrivals[0] += dist.Exponential(mean[0]);
            arrival = arrivals[0];
        } else {
            rngs.selectStream(Constants.STREAM_ARR2);
            arrivals[0] += dist.Exponential(mean[1]);
            arrival = arrivals[1];
        }
        return arrival;
    }

    public double getTimeServiceCloudlet(int taskType){

        if( taskType != Constants.TASK_TYPE1 && taskType != Constants.TASK_TYPE2){
            //gestisci errore
        }
        if(taskType == Constants.TASK_TYPE1){
            rngs.selectStream(Constants.STREAM_SERV_CLOUDLET1)
            service = dist.Hyperexponential(1/Constants.SERVICEONE);
        }else(taskType == Constants.TASK_TYPE2){
            rngs.selectStream(Constants.STREAM_SERV_CLOUDLET2);
            service = dist.Hyperexponential(1/Constants.SERVICETWO);
        }

    }

    public double getTimeServiceCloud(int taskType, int interrupted){

       if(taskType!= Constants.TASK_TYPE1 && taskType != Constants.TASK_TYPE2){
           // gestiisci l'errore
       }
       if( taskType == Constants.TASK_TYPE1) {
           if (interrupted == Constants.INTERRUPTED) {
               //gestisci errore
           }
           rngs.selectStream(Constants.STREAM_SERV_CLOUD1);
           serviceCloud = dist.Exponential(1 / Constants.SERVICE_CLOUD_ONE);
       }else{
           if(interrupted == Constants.INTERRUPTED){

               setup = getTimeSetup();
               rngs.selectStream(Constants.STREAM_SERV_CLOUD2);
               serviceCloud = dist.Exponential(1/Constants.SERVICE_CLOUD_TWO);

           }else{
               rngs.selectStream(Constants.STREAM_SERV_CLOUD2);
               serviceCloud = dist.Exponential(1/Constants.SERVICE_CLOUD_TWO);
           }
       }
       return serviceCloud;
    }

    private double getTimeSetup(){

        double timeSetup;
        rngs.selectStream(Constants.STREAM_SETUP);
        timeSetup = dist.Exponential(Constants.SETUP);
        return timeSetup;

    }

    public void assignTaskToCloudlet( double timeCurrent, int taskType){

        double timeEndService = timeCurrent + getTimeServiceCloudlet(taskType);

    }

}