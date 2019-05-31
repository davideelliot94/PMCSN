import it.uniroma2.pmcsn.project.entity.Area;
import it.uniroma2.pmcsn.project.entity.LastState;
import it.uniroma2.pmcsn.project.entity.Node;
import it.uniroma2.pmcsn.project.entity.State;
import it.uniroma2.pmcsn.project.utils.*;

import java.util.ArrayList;

import static it.uniroma2.pmcsn.project.utils.Constants.*;

public class Main {



    public static void main(String[] args) {

        int N = Constants.NUM_MAX_SERVER;
        int S = Constants.S;
        double STOP = Constants.STOP_SIMULATION;

        System.out.println("Start simulation");

        Rngs rngs = new Rngs();
        rngs.plantSeeds(123456789);

        State state = new State();
        LastState lastState = new LastState();
        Area area = new Area();

        NextEvent nextEvent = new NextEvent();

        double nextArrival;//prossimo arrivo
        double next_arrival_type1 = nextEvent.getArrivalCloudlet(1, 1);//prossimo arrivo di tipo 1
        double next_arrival_type2 = nextEvent.getArrivalCloudlet(2, 0);//prossimo arrivo di tipo 2

        double nextCloudletTermination = INF;//prossimo completamento sulla cloudlet
        double nextCloudTermination = INF;//prossimo completamento sul cloud

        ArrayList<Node> cloud = new ArrayList<>();
        ArrayList<Node> cloudlet = new ArrayList<>();

        Basic basicFunctions = new Basic();
        Node node_functions = new Node();
        Stats stats = new Stats();
        ArrayList<Object> results = new ArrayList<>();

        //finquando non termina la simulazione o ci sono job nel sistema
        while (time_current < STOP || state.getN1() > 0 || state.getN2() > 0 || state.getN1_cloud() > 0 || state.getN2_cloud() > 0) {

            basicFunctions.checkStateVariablesDuringSimulation(N, S, state);

            //calcolo degli istanti dei prossimi eventi
            if (time_current > STOP) {//se la condizione di termine viene raggiunta allora non si generano più arrivi
                nextArrival = INF;
            } else {//il prossimo arrivo è il minimo tra gli arrivi dei job di classe 1 e quelli di classe 2
                nextArrival = basicFunctions.min(next_arrival_type1, next_arrival_type2, taskTypeNextArrival);
            }

            nextCloudletTermination = nextEvent.findNextTerminationCloudlet(cloudlet, taskTypeCloudletTermination);//istante di prossimo completamento nella cloudlet
            nextCloudTermination = nextEvent.findNextTerminationCloud(cloud, taskTypeCloudTermination);//istante di prossimo completamento nella cloudlet

            timeNext = basicFunctions.min3(nextCloudletTermination, nextArrival, nextCloudTermination);

            area = area.updateArea(state, area, time_current, timeNext);


            if (timeNext - lastState.getLast_obs() >= LENGTH_BATCH_TIME) {

                results = stats.calculateBatch(timeNext, state, area, lastState, stats);
            }

            lastState = (LastState) results.get(0);
            stats = (Stats) results.get(1);

            time_current = timeNext;//rimposta tempo simulazione
            //gestione del prossimo evento,capire che tipo di evento è,gestirlo e generare nuovi eventi inerenti all'evento scatenante
            if (time_current == nextCloudletTermination) {//task nel cloud terminato

                state.setNum_task_completed(state.getNum_task_completed() + 1);

                cloud = basicFunctions.popLast(cloud);//segna server come idle
                area.setCloud_v(area.getCloud_v() + time_current - time_arrive);
                area.setSystem_v(area.getSystem_v() + time_current - time_arrive);

                if (taskTypeCloudTermination == TASK_TYPE1) {//task di tipo 1 terminato nel cloud
                    state.setN1_cloud(state.getN1_cloud() - 1);
                    area.setType1_v(area.getType1_v()+ time_current - time_arrive);
                    area.setCloud_type1_v( area.getCloud_type1_v()+ (time_current - time_arrive));

                    if (jobTerminatedInterrupted == INTERRUPTED) {//task di tipo 1 terminato nel cloud e interrotto
                        //handle_error_with_exit("errore task tipo 1 interrotto\n");
                    }
                } else {//task di tipo 2 terminato nel cloud

                    state.setN2_cloud(state.getN2_cloud() -1);
                    area.setType2_v(area.getType2_v() + time_current - time_arrive);
                    area.setCloud_type2_v(area.getCloud_type2_v() + (time_current - time_arrive));

                    if (jobTerminatedInterrupted == INTERRUPTED) {//task di tipo 2 terminato nel cloud e interrotto
                        state.setNum_job_interr_in_list(state.getNum_job_interr_in_list() +1);//aggiornare numero job interrotti in lista
                        area.setSystem_v(area.getSystem_v() + time_lost_in_cloudlet);//aggiungere il tempo perso nella cloudlet
                        area.setType2_v(area.getType2_v()+ time_lost_in_cloudlet);//aggiungere il tempo perso nella cloudlet
                        area.setJob_interr_v(area.getJob_interr_v() + time_current - time_arrive);
                    }
                }
                continue;
            }
            if (time_current == nextArrival) {//il prossimo evento è un arrivo
                if (taskTypeNextArrival == TASK_TYPE1) {//handle arrival type 1
                    next_arrival_type1 = nextEvent.getArrivalCloudlet(TASK_TYPE1, 0);//genera un nuovo arrivo di tipo 1
                    if (state.getN1() == N) {//task type 1 sent on the cloud

                        state = nextEvent.update_state(TASK_TYPE1, NOT_INTERRUPTED, DIRECT_CLOUD, state);
                        cloud = nextEvent.assignTaskToCloud(time_current, TASK_TYPE1,  cloud, NOT_INTERRUPTED, NO_TIME_LOST);

                    } else if (state.getN1() + state.getN2() < S) {//task type 1 accepted on the cloudnet

                        state = nextEvent.update_state(TASK_TYPE1, NOT_INTERRUPTED, DIRECT_CLOUDLET,  state);
                        cloudlet = nextEvent.assignTaskToCloudlet(time_current, TASK_TYPE1, cloudlet);
                        //alloca un nuovo server che gestirà il job di tipo 1 e impostare il tempo di fine a time_current+get_service_type1
                    } else if (state.getN2() > 0) {//task type 2 interrupted on the cloudlet and task type 1 accepted on the cloudlet

                        state = nextEvent.update_state(TASK_TYPE1, INTERRUPTED, DIRECT_CLOUDLET,  state);
                        cloudlet = nextEvent.interruptLastJobType2(cloudlet);

                        area.setPerm_type2(area.getPerm_type2() + (time_current - time_arrive) * 1);
                        cloud = nextEvent.assignTaskToCloud(time_current, TASK_TYPE2, cloud, INTERRUPTED, time_lost_in_cloudlet);
                        cloudlet = nextEvent.assignTaskToCloudlet(time_current, TASK_TYPE1, cloudlet);

                    } else {//task type 1 accepted on the cloudnet

                        state = nextEvent.update_state(TASK_TYPE1, NOT_INTERRUPTED, DIRECT_CLOUDLET,  state);
                        cloudlet = nextEvent.assignTaskToCloudlet(time_current, TASK_TYPE1, cloudlet)
                        ;//alloca un nuovo server che gestirà il job di tipo 1 e impostare tempo di fine a time_current+get_service_type1
                    }
                } else {//handle_arrival type2

                    next_arrival_type2 = nextEvent.getArrivalCloudlet(TASK_TYPE2, 0);

                    if (state.getN1() + state.getN2() >= S) {//task type 2 sent on the cloud

                        state = nextEvent.update_state(TASK_TYPE2, NOT_INTERRUPTED, DIRECT_CLOUD,  state);
                        cloud = nextEvent.assignTaskToCloud(time_current, TASK_TYPE2, cloud, NOT_INTERRUPTED, NO_TIME_LOST);

                    } else {//task type 2 accepted on the cloudlet

                        state = nextEvent.update_state(TASK_TYPE2, NOT_INTERRUPTED, DIRECT_CLOUDLET,  state);
                        cloudlet = nextEvent.assignTaskToCloudlet(time_current, TASK_TYPE2, cloudlet)
                        ;//alloca un nuovo server che gestirà il job di tipo 2 e impostare il tempo di fine a time_current+get_service_type2
                    }
                }
            } else if (time_current == nextCloudletTermination) {//il prossimo evento è un completamento nella cloudlet

                state.setNum_task_completed(state.getNum_task_completed() + 1);
                //delete_head(cloudlet_head, time_arrive, time_lost_in_cloudlet);//segna server come idle

                cloudlet.remove(0);

                area.setCloudlet_v(area.getCloudlet_v() + time_current - time_arrive);
                area.setSystem_v(area.getSystem_v() + time_current - time_arrive);

                if (taskTypeCloudletTermination == TASK_TYPE1) {//task type 1 terminated

                    state.setN1(state.getN1() - 1);
                    area.setType1_v(area.getType1_v() + time_current - time_arrive);
                    area.setCloud_type1_v(area.getCloudlet_type1_v() + (time_current - time_arrive));

                } else if (taskTypeCloudletTermination == TASK_TYPE2) {//task type 2 terminated

                    state.setN2(state.getN2() - 1);
                    area.setType2_v(area.getType2_v() + time_current - time_arrive);
                    area.setCloudlet_type2_v(area.getCloudlet_type2_v() + (time_current - time_arrive));

                } else {//task type 2 terminated
                    //handle_error_with_exit("error\n");

                }
            } else {
                //handle_error_with_exit("error 3\n");
            }
            //ritorna al ciclo while scansionando nuovi eventi
        }

        //check_state_variables_after_simulation(state);//verifica variabili di stato

        stats.printConfidenceInterval(stats);

        System.out.println("end simulation\n");
    }
}
