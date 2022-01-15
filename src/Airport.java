import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is an implementation of an airport
 */

public class Airport {
    private String name;
    private boolean[] runways;
    private Queue<String> departingFlights;
    private Queue<String> landingFlights;

    public Airport(String name, int runwaysNumber) {
        this.name = name;
        this.runways = new boolean[runwaysNumber];
        Arrays.fill(this.runways, Boolean.TRUE);
        departingFlights = new LinkedList<>();
        landingFlights  = new LinkedList<>();
    }

    /**
     * This method implements departing of a flight.
     * Adds the flight to the queue of departing flights, prints the request of the flight to depart ,prints the available runways,
     * if there is no empty runways or the flight is not the first in the queue then the flight will wait for its turn.
     * finally the flight will be removed from the queue and the runway will be allocated.
     * For convenience the runways will be allocated according to their availability and order
     */
    public synchronized int depart(String flightNumber, String departAirport, String landAirport){
        int runway;
        departingFlights.add(flightNumber);
        System.out.println("Flight number " + flightNumber + " from " + departAirport + " to " + landAirport  + " asked to depart.");
        printAvailableRunways();
        while((runway = allocateRunway()) == 0 || departingFlights.peek() != flightNumber){
            try {
                wait();
            }catch (InterruptedException e){}
        }
        departingFlights.poll();
        runways[runway - 1] = false;
        System.out.println("Allocated runway number: " + runway + " to flight : " + flightNumber + " to depart.");
        return runway;
    }

    /**
     * This method implements landing of a flight.
     * Adds the flight to the queue of landing flights, prints the request of the flight to land ,prints the available runways,
     * if there is no empty runways or the flight is not the first in the queue then the flight will wait for its turn.
     * finally the flight will be removed from the queue and the runway will be allocated.
     * For convenience the runways will be allocated according to their availability and order
     */
    public synchronized int land(String flightNumber, String departAirport, String landAirport){
        landingFlights.add(flightNumber);
        int runway;
        System.out.println("Flight number " + flightNumber + " from " + departAirport + " to " + landAirport  + " asked to land.");
        printAvailableRunways();
        while((runway = allocateRunway()) == 0 || landingFlights.peek() != flightNumber){
            try {
                wait();
            }catch (InterruptedException e){}
        }
        landingFlights.poll();
        runways[runway - 1] = false;
        System.out.println("Allocated runway number: " + runway + " to flight : " + flightNumber + " to land.");
        return runway;
    }

    /**
     * This method releases a runway and releases the waiting flights by notifying them that
     * there is empty runway.
     * @param flightNumber flight number
     * @param vacantRunway the runway to release
     */
    public synchronized void freeRunway(String flightNumber, int vacantRunway){
        System.out.println("Runway number " + vacantRunway + " in " + this.name + " is now available.");
        runways[vacantRunway - 1] = true;
        notifyAll();
    }

    /**
     * This method prints available runways.
     */
    private synchronized void printAvailableRunways(){
        StringBuilder res = new StringBuilder();
        for( int i = 0; i < runways.length; i++) {
            if(runways[i])
                res.append(i + 1).append(" ");
        }
        System.out.println("Available runways: " + res);
    }

    /**
     * Allocates empty runway for a flight.
     * @return the runway number or 0 if there is no free runway.
     */
    private synchronized int allocateRunway(){
        for( int i = 0; i < runways.length; i++) {
            if(runways[i]) {
                return (i + 1);
            }
        }
        return 0;
    }

    public String getName() {
        return name;
    }
}
