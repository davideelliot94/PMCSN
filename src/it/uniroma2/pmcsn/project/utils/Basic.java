package it.uniroma2.pmcsn.project.utils;

import it.uniroma2.pmcsn.project.entity.Node;
import it.uniroma2.pmcsn.project.entity.State;

import java.util.ArrayList;

public class Basic {

    public void checkStateVariablesDuringSimulation(int N,int S, State state){
        if(state.getN1( )<0 || state.getN2_cloud() < 0){
            //handle_error_with_exit("n1 and n2 must be greater than 0\n");
        }
        if(state.getN1() > N || state.getN2() > N){
            //handle_error_with_exit("n1 and n2 must be smaller than N\n");
        }
        if(state.getN1() + state.getN2() > N){
            //handle_error_with_exit("sum of n1 and n2 must be smaller than N+1\n");
        }
        if((state.getN2() == N && S < N)){
            //handle_error_with_exit("n2 must be smaller than N if S<N\n");
        }
        if(state.getNum_job_interr_in_list() > state.getN2_cloud()){
            //handle_error_with_exit("error1\n");
        }
        if(state.getNum_task_type1() + state.getNum_task_type2() != state.getNum_task_arrived()){
            //handle_error_with_exit("error 2\n");
        }
        if(state.getNum_task_cloud() != (state.getNum_task_directed_on_cloud() + state.getNum_task_interrupted())){
            //handle_error_with_exit("error 5\n");
        }
        if(state.getNum_task_type1_cloudlet() + state.getNum_task_type1_cloud() != state.getNum_task_type1()){
            //handle_error_with_exit("error 7\n");
        }
        if(state.getNum_task_cloud() != state.getNum_task_type1_cloud() + state.getNum_task_type2_cloud()){
            //handle_error_with_exit("error 8\n");
        }
        if(state.getNum_task_cloudlet() != state.getNum_task_type1_cloudlet()+state.getNum_task_type2_cloudlet()){
            //handle_error_with_exit("error 9\n");
        }
        if(state.getNum_task_type2_cloud() != state.getNum_task_type2_directed_on_cloud() + state.getNum_task_interrupted()){
            //handle_error_with_exit("error 10\n");
        }
    }

    public double min(double i, double j, int index){//ritorna il valore massimo tra i e j e riempie index

        if(i <= j){

            return i;

        } else if(i == j){

            return i;

        }

        return j;
    }

    public double min3(double i, double j, double k){//ritorna il minimo tra i,j e k

        if(i<=j && i<=k){

            return i;

        }

        if(j <= k){

            return j;

        }

        return k;
    }

    public ArrayList<Node> popLast(ArrayList<Node> nodes) {

        if (nodes.isEmpty() || Constants.time_arrive < 0){

            //handle_error_with_exit("error in delete head\n");

        }

        if (nodes.size() == 1){

            Constants.time_arrive = nodes.get(0).getTime_arrive();
            Constants.time_lost_in_cloudlet = nodes.get(0).getTime_lost_in_cloudlet();
            nodes.remove(0);
            return nodes;

        } else {

            Constants.time_arrive = nodes.get(0).getTime_arrive();
            Constants.time_lost_in_cloudlet = nodes.get(0).getTime_lost_in_cloudlet();
            nodes.remove(nodes.size() - 1);
            return nodes;

        }


    }


}
