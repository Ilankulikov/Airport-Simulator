import java.util.Random;

/**
 * This class represents a flight thread.
 */

public class Flight extends Thread{
    private String flightNumber;
    private Airport departAirport, landAirport;

    /**
     * Class constructor, initializes the flight.
     * @param flightNumber Flight number
     * @param departAirport Origin airport
     * @param landAirport Destination airport
     */
    public Flight(String flightNumber, Airport departAirport, Airport landAirport) {
        this.flightNumber = flightNumber;
        this.departAirport = departAirport;
        this.landAirport = landAirport;
    }


    public void run(){
        Random rand = new Random();
        int runway;
        runway = departAirport.depart(flightNumber, departAirport.getName(), landAirport.getName());
        simulation(flightNumber, departAirport.getName(), landAirport.getName(), rand.nextInt(2000) + 3001, "departing", runway);
        departAirport.freeRunway(flightNumber, runway);
        try{
            sleep(rand.nextInt(4000) + 3000);
        }catch (InterruptedException e){}
        runway = landAirport.land(flightNumber, departAirport.getName(), landAirport.getName());
        simulation(flightNumber, departAirport.getName(), landAirport.getName(), rand.nextInt(2000) + 3001, "landing", runway);
        landAirport.freeRunway(flightNumber,runway);
    }

    /**
     * This method simulates the landing or takeoff of an airplane,
     * the method goes to sleep 'departDuration' seconds until the airplane lands or takes off.
     * @param flightNumber The flight number
     * @param departAirport origin airport
     * @param landAirport destination
     * @param departDuration The time it takes for a plane to take off, a random number between 3 and 5 seconds
     * @param action depart / land
     * @param runway runway number
     */
    private void simulation(String flightNumber, String departAirport, String landAirport, long departDuration, String action,int runway){
        String announcement = "Flight number " + flightNumber + " from " + departAirport + " to " + landAirport + " is " + action + " on runway: " + runway + " ";
        System.out.println(announcement);
        try {
            sleep(departDuration);
        }catch (InterruptedException e){}

    }
}
