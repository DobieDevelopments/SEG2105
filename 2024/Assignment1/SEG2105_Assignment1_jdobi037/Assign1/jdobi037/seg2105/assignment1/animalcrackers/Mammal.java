package jdobi037.seg2105.assignment1.animalcrackers;
/**
 * The Mammal class represents a mammal, extending the Animal class.
 * it includes attributes and methods specific to mammals. 
 * And requires that it implements the Soundmaker interface to have 
 * the Mammal make a sound specific to it.
 * This class is also a base class to be the general template for mammal type animals
 * 
 * @author John Dobie - jdobi037@uottawa.ca - 300443432
 */
public class Mammal extends Animal implements SoundMaker
    {
		/**
		 * An override of the base class' GetAnimalType method that 
		 * differentiates this Animal from the rest.
		 */
		@Override
        protected String GetAnimalType(){
            return "Mammal";
        }
		
		//the current number of mammals
        private static int numberOfMammals = 0;

        /**
    	 * Constructs a new Mammal and adds it to the count, 
    	 * outputs a message that it has been created and does not name the mammal.
    	 */
        protected Mammal(){
            super();
            numberOfMammals++;
            System.out.println("Mammal Constructor Called");
        }

        /**
    	 * Constructs a new Mammal and adds it to the count, 
    	 * outputs a message that it has been created and names the mammal to the custom name passed in.
    	 * @param - the name of the mammal
    	 */
        protected Mammal(String mammalsName){
            super(mammalsName);
            numberOfMammals++;
            System.out.println("Mammal Constructor with name called");
        }

        /**
         * overrides the interface SoundMaker's MakeSound method to have the animal
         * output a custom sound unique to it's class.
         */
        @Override
        public void MakeSound(){
            System.out.println("MammalSound");
        }

        /**
         * A getter for accessing the numberOfMammals property.
         * @return - the current number of mammals
         */
        public static int getNumberOfMammals(){
            return numberOfMammals;
        }
    }