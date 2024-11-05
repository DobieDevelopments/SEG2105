package jdobi037.seg2105.assignment1.animalcrackers;
/**
 * The Dog class represents a dog, extending the Mammal class.
 * it includes attributes and methods specific to dogs. 
 * And requires that it implements the Soundmaker interface to have 
 * the Dog make a sound specific to it.
 * 
 * @author John Dobie - jdobi037@uottawa.ca - 300443432
 */
public class Dog extends Mammal implements SoundMaker
{
	/**
	 * Constructs a new Dog, 
	 * outputs a message that it has been created and does not name the dog.
	 */
    protected Dog()
    {
        super();
        System.out.println("Dog Constructor Called");
    }

    /**
	 * Constructs a new Dog, 
	 * outputs a message that it has been created and names the dog to the custom name passed in.
	 * @param - the name of the dog
	 */
    protected Dog(String dogsName){
        super(dogsName);
        this.name = dogsName;
        System.out.println("Dog Constructor with name called");
    }

    /**
     * overrides the interface SoundMaker's MakeSound method to have the animal
     * output a custom sound unique to it's class.
     */
    @Override
    public void MakeSound(){
        System.out.println("Woof!");
    }

    /**
	 * An override of the base class' GetAnimalType method that 
	 * differentiates this Animal from the rest.
	 */
    @Override
    protected String GetAnimalType(){
            return "Dog";
    }
}