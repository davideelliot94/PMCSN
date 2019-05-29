package it.uniroma2.pmcsn.project.entity.utils;

import it.uniroma2.pmcsn.project.entity.distributions.Distributions;

public class NextEvent {

    public double getArrivalCloudlet(int taskType, int newReplication) {

        if (taskType != 1 && taskType != 2) {
            // gestisci errore
        }

        double[] mean = new double[]{1 / Constants.ARRIVALONE, 1 / Constants.ARRIVALTWO};
        double[] arrivals = new double[]{Constants.START, Constants.START};

        static int init = 0;
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
}