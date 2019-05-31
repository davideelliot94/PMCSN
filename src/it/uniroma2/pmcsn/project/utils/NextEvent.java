package it.uniroma2.pmcsn.project.utils;

import it.uniroma2.pmcsn.project.distributions.Distributions;
import it.uniroma2.pmcsn.project.entity.Node;
import it.uniroma2.pmcsn.project.entity.State;

import java.util.ArrayList;

import static it.uniroma2.pmcsn.project.utils.Constants.*;

public class NextEvent {

    private double[] mean = new double[]{1 / Constants.ARRIVALONE, 1 / Constants.ARRIVALTWO};
    private double[] arrivals = new double[]{Constants.START, Constants.START};
    int init = 0;
    Rngs rngs = new Rngs();
    Distributions distributions = new Distributions();

    public double getArrivalCloudlet(int taskType, int newReplication) {

        if (taskType != 1 && taskType != 2) {
            // gestisci errore
        }

        if (newReplication == 1) {

            init = 0;
            arrivals[0] = Constants.START;
            arrivals[1] = Constants.START;

        }

        Rngs rngs = new Rngs();
        Distributions dist = new Distributions();

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


    public State update_state(int taskType, int interrupted, int location, State state){//aggiorna le variabili del sistema quando c'è un arrivo di tipo 1 o di tipo 2
        if(state== null){
            //handle_error_with_exit("error in update state\n");
        }
        if(taskType!=TASK_TYPE1 && taskType!=TASK_TYPE2){
            //handle_error_with_exit("error in update state task_type\n");
        }
        if(interrupted!=NOT_INTERRUPTED && interrupted!=INTERRUPTED){
            //handle_error_with_exit("error in update_state interrupted\n");
        }
        if(location!=DIRECT_CLOUD && location!=DIRECT_CLOUDLET && location!=NOT_DIRECT_CLOUD){
            //handle_error_with_exit("error in update state location\n");
        }
        state.setNum_task_arrived(state.getNum_task_arrived() +1);
        if(taskType==TASK_TYPE1){//task tipo 1
            state.setNum_task_type1(state.getNum_task_type1() +1);
            if(interrupted==INTERRUPTED){//task tipo 2 interrotto,e arrivo di tipo 1
                if(location==DIRECT_CLOUDLET){//task tipo 2 interrotto,e arrivo di tipo 1 nella cloudlet
                    state.setNum_task_interrupted(state.getNum_task_interrupted() +1);
                    state.setN1(state.getN1()+1);
                    state.setN2(state.getN2()-1);
                    state.setN2_cloud(state.getN2_cloud()+1);
                    state.setNum_task_cloud(state.getNum_task_cloud()+1);//un task tipo 2 arriva nel cloud
                    state.setNum_task_cloudlet(state.getNum_task_cloudlet()+1);//un task tipo 1 arriva nella cloudlet
                    state.setNum_task_type1_cloudlet(state.getNum_task_type1_cloudlet() +1);
                    state.setNum_task_type2_cloud(state.getNum_task_type2_cloud() +1);
                    state.setNum_job_interr_in_list(state.getNum_job_interr_in_list()+1);
                }
                else{//task tipo 2 interrotto,e arrivo di tipo 1 nel cloud direttamente o non
                    //handle_error_with_exit("error\n");
                }
            }
            else if(location==DIRECT_CLOUDLET){//task type 1 e diretto sulla cloudlet(non interrotto)
                state.setN1(state.getN1()+1);
                state.setNum_task_cloudlet(state.getNum_task_cloudlet()+1);
                state.setNum_task_type1_cloudlet(state.getNum_task_type1_cloudlet() +1);
            }
            else if(location==DIRECT_CLOUD){//task type 1 e diretto sul  cloud(non interrotto)
                state.setN1_cloud(state.getN1_cloud()+1);
                state.setNum_task_type1_cloud(state.getNum_task_type1_cloud() +1);
                state.setNum_task_cloud(state.getNum_task_cloud()+1);
                state.setNum_task_directed_on_cloud(state.getNum_task_directed_on_cloud() +1);
            }
            else{//task type 1 e non diretto sul cloud(non interrotto)
                //handle_error_with_exit("error\n");
            }
        }
        else{//task tipo 2
            state.setNum_task_type2(state.getNum_task_type2() +1);
            if(interrupted==INTERRUPTED) {//task tipo 2 che arriva e viene interrotto
                //handle_error_with_exit("error\n");
            }
            else{//task tipo 2 non_interrotto
                if(location==DIRECT_CLOUDLET) {//task tipo 2 non interrotto e diretto sulla cloudlet
                    state.setN2(state.getN2()+1);
                    state.setNum_task_cloudlet(state.getNum_task_cloudlet()+1);
                    state.setNum_task_type2_cloudlet(state.getNum_task_type2_cloudlet() +1);
                }
                else if(location==DIRECT_CLOUD) {//task tipo 2 non interrotto e diretto sul cloud
                    state.setN2_cloud(state.getN2_cloud()+1);
                    state.setNum_task_cloud(state.getNum_task_cloud()+1);
                    state.setNum_task_directed_on_cloud(state.getNum_task_directed_on_cloud() +1);
                    state.setNum_task_type2_cloud(state.getNum_task_type2_cloud() +1);
                    state.setNum_task_type2_directed_on_cloud(state.getNum_task_type2_directed_on_cloud() +1);
                }
                else {//task tipo 2 non interrotto e non diretto sul cloud
                    //handle_error_with_exit("error\n");
                }
            }
        }
        return state;
    }


    public ArrayList<Node> assignTaskToCloudlet(double time_current, int task_type, ArrayList<Node> nodes){//assegna un task alla cloudlet,quindi alloca un server nella lista dinamica
        //della cloudlet e imposta il tempo di completamento di quel task

        double time_end_service=time_current+ getTimeServiceCloudlet(task_type);
        return insertOrdered(time_end_service,task_type,NOT_INTERRUPTED,time_current,NO_TIME_LOST, nodes);

    }

    public ArrayList<Node> assignTaskToCloud(double timeCurrent, int taskType, ArrayList<Node> nodes, int interrupted, double timeLostInCloudlet){//assegna un task al cloud,quindi alloca un server nella lista dinamica
        //del cloud e imposta il tempo di completamento di quel task

        double timeEndService = timeCurrent + getTimeServiceCloud(taskType, interrupted);
        return insertOrdered(timeEndService,taskType,interrupted,timeCurrent,timeLostInCloudlet, nodes);

    }


    private double getTimeServiceCloudlet(int task_type) {//ritorna il tempo di servizio del job del tipo specificato nella cloudlet
        double service;
        if (task_type != TASK_TYPE1 && task_type != TASK_TYPE2) {
            //handle_error_with_exit("error in getTimeServiceCloudlet\n");
        }
        if (task_type == TASK_TYPE1) {//il job è di tipo 1
            rngs.selectStream(STREAM_SERV_CLOUDLET1);//selezionare stream servizio1 cloudlet
            service = distributions.Hyperexponential(1/SERVICEONE);//genera servizio
            //service=Exponential(1/SERVICEONE);
        }
        else{//il job è di tipo 2
            rngs.selectStream(STREAM_SERV_CLOUDLET2);//seleziona stream servizio2 cloudlet
            service = distributions.Hyperexponential(1/SERVICETWO);//genera servizio
            //service=Exponential(1/SERVICETWO);
        }

        return service;
    }

    private double getTimeServiceCloud(int task_type, int interrupted){//ritorna il tempo di servizio del job del tipo specificato nel cloud

        double service_cloud;
        double setup;

        if (task_type != TASK_TYPE1 && task_type != TASK_TYPE2) {
            //handle_error_with_exit("error in getTimeServiceCloudlet\n");
        }

        if (task_type == TASK_TYPE1) {//il job è di tipo 1
            if(interrupted == 1){
                //handle_error_with_exit("error in get time service cloud\n");
            }
            rngs.selectStream(STREAM_SERV_CLOUD1);
            service_cloud = distributions.Exponential(1/SERVICE_CLOUD_ONE);

        } else {//job di tipo 2

            if(interrupted == 1) {//job di tipo 2 interrotto

                setup = getTimeSetup();
                rngs.selectStream(STREAM_SERV_CLOUD2);//seleziona lo stream del servizio del cloud
                service_cloud = distributions.Exponential(1/SERVICE_CLOUD_TWO) + setup;//ottieni il servizio del cloud aggiungendo il tempo di setup

            } else {//job di tipo 2 non interrotto
                rngs.selectStream(STREAM_SERV_CLOUD2);
                service_cloud = distributions.Exponential(1/SERVICE_CLOUD_TWO);//genera tempo di servizio senza aggiungere il tempo di setup
            }

        }
        return service_cloud;
    }


    private double getTimeSetup(){//genera tempo di setup esponenziale

        double time_setup;
        rngs.selectStream(STREAM_SETUP);
        time_setup = distributions.Exponential(SETUP);
        return time_setup;
    }


    //ritorna INF se la lista dinamica è vuota,
    // altrimenti ritorna l'istante di completamento della testa della lista,ossia l'istante del prossimo completamento
    public double findNextTerminationCloudlet(ArrayList<Node> nodes, int taskTypeTermination) {


        if (nodes.isEmpty()) {

            return INF;
        }

        if ((taskTypeTermination != TASK_TYPE1 && taskTypeTermination != TASK_TYPE2) || jobTerminatedInterrupted == 2) {
            //handle_error_with_exit("error in fin_next_termination\n");
        }


        return getInstantEndServiceFromNodeListCloudlet(nodes, taskTypeTermination);


    }

    //ritorna INF se la lista dinamica è vuota,
    // altrimenti ritorna l'istante di completamento della testa della lista,ossia l'istante del prossimo completamento
    public double findNextTerminationCloud(ArrayList<Node> nodes, int taskTypeTermination) {


        if (nodes.isEmpty()) {

            return INF;
        }

        if ((taskTypeTermination != TASK_TYPE1 && taskTypeTermination != TASK_TYPE2) || jobTerminatedInterrupted == 2) {
            //handle_error_with_exit("error in fin_next_termination\n");
        }

        return getInstantEndServiceFromNodeListCloud(nodes, taskTypeTermination);

    }

    public double getInstantEndServiceFromNodeListCloudlet(ArrayList<Node> nodeArrayList, int taskTypeTermination) {

        if (nodeArrayList.isEmpty()) {
            //handle_error_with_exit("linked list is empty\n");
        }

        taskTypeCloudletTermination = nodeArrayList.get(nodeArrayList.size() - 1).getTask_type();
        jobTerminatedInterrupted = nodeArrayList.get(nodeArrayList.size() - 1).getInterrupted();

        return nodeArrayList.get(nodeArrayList.size() - 1).getInstant_end_service();
    }

    public double getInstantEndServiceFromNodeListCloud(ArrayList<Node> nodeArrayList, int taskTypeTermination) {

        if (nodeArrayList.isEmpty()) {
            //handle_error_with_exit("linked list is empty\n");
        }

        taskTypeCloudTermination = nodeArrayList.get(nodeArrayList.size() - 1).getTask_type();
        jobTerminatedInterrupted = nodeArrayList.get(nodeArrayList.size() - 1).getInterrupted();

        return nodeArrayList.get(nodeArrayList.size() - 1).getInstant_end_service();
    }

    private ArrayList<Node> insertOrdered(double instantEndService, int taskType, int interrupted, double timeArrive, double timeLostInCloudlet, ArrayList<Node> nodes){
        //inserisce ordinatamente un nodo nella lista ordinata per istanti temporali,partendo dalla coda

        Node node = new Node();
        node.setInstant_end_service(instantEndService);
        node.setTask_type(taskType);
        node.setInterrupted(interrupted);
        node.setTime_arrive(timeArrive);
        node.setTime_lost_in_cloudlet(timeLostInCloudlet);

        if(nodes == null){
            //handle_error_with_exit("error in insertOrdered,head or tail are NULL\n");
        }
        if(nodes.isEmpty()){

            nodes.add(node);
            return nodes;
        }

        Node lastNode = nodes.get(nodes.size() - 1);

        if(lastNode.getInstant_end_service() >= node.getInstant_end_service()){

            nodes.add(node);
            return nodes;

        } else {

            int i = 1;

            while(lastNode.getInstant_end_service() < node.getInstant_end_service()){

                if(i < nodes.size()){

                    lastNode = nodes.get(nodes.size() - i);
                    i++;

                } else {

                    nodes.add(0, node);
                    return nodes;
                }
            }

            nodes.add(i, node);
            return nodes;

        }

    }

    public ArrayList<Node> interruptLastJobType2(ArrayList<Node> nodes){//interrompe l'ultimo job di tipo 2 nella lista dinamica

        /*struct node*p=*tail;

        if(nodes == null || timeLostInCloudlet==NULL || timeCurrent<=0){
            handle_error_with_exit("error in interrupt_job_type2\n");
        }*/

        Node lastNode = nodes.get(nodes.size() - 1);

        int i = 1;

        while(lastNode != null && lastNode.getTask_type() != 2){

            lastNode = nodes.get(nodes.size() - i);
            i++;

        }

        if(lastNode == null){
            //handle_error_with_exit("no job of type 2 founded\n");
        }

        Constants.time_lost_in_cloudlet = (Constants.time_current - lastNode.getTime_arrive());//salvare tempo perso nel cloud

        nodes.remove(i);

        return nodes;
    }


}