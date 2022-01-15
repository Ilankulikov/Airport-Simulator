import java.util.Random;


public class AirportSimulator {
    private final static int NUM_OF_AIRPORTS = 2;
    private final static int NUM_OF_RUNWAYS = 3;
    private final static int NUM_OF_FLIGHTS = 10;
    private final static String[] AIRPORT_NAMES = {"SALZBURG", "BEN GURION"};
    private final static String[] FLIGHT_NUMBERS = {"OS 508", "LH 686", "AA 052", "IZ 842", "UA 090", "AA 146", "DL 234", "FR 6472", "HV 5803", "IZ 426" };

    public static void main(String[] args) {
        Random rand = new Random();
        Airport[] airports = initAirports();
        int depart, land;
        // Randomly selects the airport for departing and landing and creates
        // threads of flighs according to NUM_OF_FLIGHTS.
        for( int i = 0; i < NUM_OF_FLIGHTS; i++) {
            depart = rand.nextInt(2);
            land = Math.abs(depart - 1);
            new Flight(FLIGHT_NUMBERS[i], airports[depart], airports[land]).start();

        }
    }

    /**
     * This method initialize the airports into array of Airport objects.
     * @return an array of airports
     */
    private static Airport[] initAirports(){
        Airport[] airports = new Airport[NUM_OF_AIRPORTS];
        for( int i = 0; i < NUM_OF_AIRPORTS; i++){
            airports[i] = new Airport(AIRPORT_NAMES[i], NUM_OF_RUNWAYS );
        }
        return airports;
    }
}
