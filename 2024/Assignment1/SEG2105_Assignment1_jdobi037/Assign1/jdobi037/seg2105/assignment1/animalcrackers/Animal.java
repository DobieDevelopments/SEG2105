package jdobi037.seg2105.assignment1.animalcrackers;
/**
 * The Animal class is the base class for all of the other animals to represent as a generic creature.
 * it is an abstract class that requires subclasses to implement the GetAnimalType method.
 * 
 * @author John Dobie - jdobi037@uottawa.ca - 300443432
 */
public abstract class Animal implements SoundMaker{
	
	//the current number of animals
    private static int numberOfAnimals = 0;
    //the animal's name
    protected String name;

    /**
	 * Constructs a new Animal adds it to the count, 
	 * outputs a message that it has been created and names the animal.
	 */
    Animal()
    {
        numberOfAnimals++;
        System.out.println("Animal Constructor Called");
        this.name = "Unknown Animal";
    }

    /**
	 * Constructs a new Animal adds it to the count, 
	 * outputs a message that it has been created and names the animal to the custom name passed in.
	 * @param - the name of the animal
	 */
    Animal(String animalName)
    {
        numberOfAnimals++;
        System.out.println("Animal Constructor with name called");
        this.name = animalName;
    }
    
    /**
     * A getter for the kind of animal associated with the class.
     * @return - The type of animal
     */
    abstract String GetAnimalType();
    
    /**
     * A getter for accessing the numberOfAnimals property.
     * @return the current number of animals.
     */
    public static int getNumberOfAnimals()
    {
        return numberOfAnimals;
    }
}