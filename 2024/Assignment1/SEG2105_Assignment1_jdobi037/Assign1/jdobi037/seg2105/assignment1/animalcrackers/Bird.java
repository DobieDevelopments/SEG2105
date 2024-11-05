package jdobi037.seg2105.assignment1.animalcrackers;

/**
 * The Bird class represents a bird, extending the Animal class.
 * it includes attributes and methods specific to birds. 
 * And requires that it implements the Soundmaker interface to have 
 * the Bird make a sound specific to it.
 * This class is also a base class to be the general template for bird type animals
 * 
 * @author John Dobie - jdobi037@uottawa.ca - 300443432
 */
public class Bird extends Animal implements SoundMaker
    {
		/**
		 * An override of the base class' GetAnimalType method that 
		 * differentiates this Animal from the rest.
		 */
		@Override
        protected String GetAnimalType(){
            return "Bird";
        }
		
		//the current number of birds
        private static int numberOfBirds = 0;

        /**
    	 * Constructs a new Bird adds it to the count, 
    	 * outputs a message that it has been created and does not name the bird.
    	 */
        protected Bird(){
            super();
            numberOfBirds++;
            System.out.println("Bird Constructor Called");
        }

        /**
    	 * Constructs a new Bird and adds it to the count, 
    	 * outputs a message that it has been created and names the bird to the custom name passed in.
    	 * @param - the name of the bird
    	 */
        protected Bird(String birdsName){
            super(birdsName);
            numberOfBirds++;
            System.out.println("Bird Constructor with name called");
        }
        
        /**
         * overrides the interface SoundMaker's MakeSound method to have the animal
         * output a custom sound unique to it's class.
         */
        @Override
        public void MakeSound(){
            System.out.println("BirdSound");
        }
        
        /**
         * A getter for accessing the numberOfBirds property.
         * @return - the current number of birds
         */
        public static int getNumberOfBirds(){
            return numberOfBirds;
        }
    }