package it.uniroma2.pmcsn.project.entity.utils;

public class Constants {

    static double START = 0.0;
    static long STOP_SIMULATION = 1000000;
    static long INF = STOP_SIMULATION*10;
    static double NO_TIME_LOST = 0.0;
    static int NUM_BATCH = 20;
    static double LENGTH_BATCH_TIME = STOP_SIMULATION/NUM_BATCH;
    static double NUM_MAX_SERVER = 5; //NUM_MAX_SERVER==N,numero massimo di server della cloudlet

     //arrival rate and service rate

    static double ARRIVALONE = 4.0 ;     //lambda1 cloudlet   [task/s]
    static double ARRIVALTWO =  6.25 ;   //lambda2 cloudlet  [task/s]
    static double SERVICEONE = 0.45;     //u1 cloudlet       [task/s]
    static double SERVICETWO = 0.27 ; //u2 cloudlet       [task/s]
    static double SERVICE_CLOUD_ONE =  0.25  ; //u1 cloud  [task/s]
    static double SERVICE_CLOUD_TWO = 0.22 ;  //u2 cloud  [task/s]
    static double SETUP = 0.8; //                         [sec]

    //streams per arrivi e servizi
    static int STREAM_ARR1 = 0;
    static int STREAM_ARR2 = 10;
    static int STREAM_SERV_CLOUDLET1 = 20;
    static int STREAM_SERV_CLOUDLET2 = 30;
    static int STREAM_SERV_CLOUD1 = 40;
    static int STREAM_SERV_CLOUD2 = 50;
    static int STREAM_SETUP = 60;

    //constants
    static int TASK_TYPE1 = 1;
    static int TASK_TYPE2 = 2;
    static int NOT_INTERRUPTED = 0;
    static int INTERRUPTED = 1;
    static int DIRECT_CLOUD = 1;
    static int NOT_DIRECT_CLOUD = 2;
    static int DIRECT_CLOUDLET = 3;

    static long SEED_DEFAULT = 991102;
    static double ALPHA = 0.05;
}
