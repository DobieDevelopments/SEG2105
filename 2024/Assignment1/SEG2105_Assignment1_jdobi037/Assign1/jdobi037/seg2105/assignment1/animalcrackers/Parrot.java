package jdobi037.seg2105.assignment1.animalcrackers;
/**
 * The Parrot class represents a parrot, extending the Bird class.
 * it includes attributes and methods specific to parrots. 
 * And requires that it implements the Soundmaker interface to have 
 * the parrot make a sound specific to it.
 * 
 * @author John Dobie - jdobi037@uottawa.ca - 300443432
 */
public class Parrot extends Bird implements SoundMaker
{
	/**
	 * Constructs a new Parrot, 
	 * outputs a message that it has been created and does not name the parrot.
	 */
    protected Parrot()
    {
        super();
        System.out.println("Parrot Constructor Called");
    }

    /**
	 * Constructs a new Parrot, 
	 * outputs a message that it has been created and names the parrot to the custom name passed in.
	 * @param - the name of the parrot
	 */
    protected Parrot(String parrotsName){
        super(parrotsName);
        this.name = parrotsName;
        System.out.println("Parrot Constructor with name called");
    }

    /**
     * overrides the interface SoundMaker's MakeSound method to have the animal
     * output a custom sound unique to it's class.
     */
    @Override
    public void MakeSound(){
        System.out.println("Tweet!");
    }

    /**
	 * An override of the base class' GetAnimalType method that 
	 * differentiates this Animal from the rest.
	 */
    @Override
    protected String GetAnimalType(){
            return "Bird";
    }
}