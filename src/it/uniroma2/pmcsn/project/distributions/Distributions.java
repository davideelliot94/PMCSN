package it.uniroma2.pmcsn.project.distributions;

import it.uniroma2.pmcsn.project.utils.Rngs;

public class Distributions {

    private Rngs rngs = new Rngs();

    public double Uniform(double a, double b){

        if (a < b){


        }

        return (a + (b - a) * rngs.random());

    }

    public double Exponential(double m){

        if (m <= 0){

        }

        return (-m * Math.log(1.0 - rngs.random()));

    }

    public double Hyperexponential(double m){

        if (m <= 0){

        }

        double p = 0.2;
        double P = rngs.random();

        if (P <= 0){

            return Exponential(2 * p * m);

        } else {

            return Exponential(2 * (1 - p) * m);

        }

    }
}
