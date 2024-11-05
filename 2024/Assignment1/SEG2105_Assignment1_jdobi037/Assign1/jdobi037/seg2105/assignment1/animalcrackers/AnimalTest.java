package jdobi037.seg2105.assignment1.animalcrackers;

import java.util.ArrayList;
import java.util.List;

/**
 * The Main class is the entry point for the Animal Test polymorphism OOP application.
 * It instantiates multiple of each animal with and without names, and then has them make sounds, before counting them.
 * 
 * @author John Dobie - jdobi037@uottawa.ca - 300443432
 */
public class AnimalTest {
	
	/**
	 * The main method to run the Animal Test application.
	 * 
	 */
    public static void main(String[] args) {
    	//a list of animals to be created, counted, and made to make noises.
        List<Animal> animals = new ArrayList<Animal>();
        //a list of mammals to be created, counted, and made to make noises.
        List<Mammal> mammals = new ArrayList <Mammal>();
        //a list of birds to be created, counted, and made to make noises.
        List<Bird> birds = new ArrayList<Bird>();
        //a list of dogs to be created, counted, and made to make noises.
        List<Dog> dogs = new ArrayList<Dog>();
        //a list of parrots to be created, counted, and made to make noises.
        List<Parrot> parrots = new ArrayList<Parrot>();

        //create all of the critters, and name some of them.
        animals.add(new Mammal());
        animals.add(new Bird());
        animals.add(new Dog("Wallace"));
        animals.add(new Dog("Maya"));
        System.out.println();
        mammals.add(new Mammal());
        mammals.add(new Mammal());
        mammals.add(new Mammal("Elephant"));
        mammals.add(new Mammal("Horse"));
        System.out.println();
        dogs.add(new Dog());
        dogs.add(new Dog());
        dogs.add(new Dog("Sunny"));
        dogs.add(new Dog("Flash"));
        System.out.println();
        birds.add(new Bird());
        birds.add(new Bird());
        birds.add(new Bird("pigeon"));
        birds.add(new Bird("chickadee"));
        System.out.println();
        parrots.add(new Parrot());
        parrots.add(new Parrot());
        parrots.add(new Parrot("Polly"));
        parrots.add(new Parrot("Jack"));
       
        //iterate through all of the created animals, and tell them to all make sounds
        int count = 0;
        int loopsize = Math.max(parrots.size(), 
        					Math.max(Math.max(animals.size(), mammals.size()), 
        						Math.max(dogs.size(), birds.size())));
        
        for(int i = 0; i < loopsize; ++i)
        {
        	switch(count)
        	{
        	case 0:
        		if(i == 0)
        		{
        			System.out.println("\nAnimals making sounds:");
        		}
        		animals.get(i).MakeSound();
        		if(i == animals.size() - 1) {
        			count ++;
        			i = -1;
        		}
        		break;
        	case 1:
        		if(i == 0)
        		{
        			System.out.println("\n" + mammals.get(0).GetAnimalType() + " making sounds:");
        		}
        		mammals.get(i).MakeSound();
        		if(i == mammals.size() - 1) {
        			count ++;
        			i = -1;
        		}
        		break;
        	case 2:
        		if(i == 0)
        		{
        			System.out.println("\n" + birds.get(0).GetAnimalType() + " making sounds:");
        		}
        		birds.get(i).MakeSound();
        		if(i == birds.size() - 1) {
        			count ++;
        			i = -1;
        		}
        		break;
        	case 3:
        		if(i == 0)
        		{
        			System.out.println("\n" + dogs.get(0).GetAnimalType() + " making sounds:");
        		}
        		dogs.get(i).MakeSound();
        		if(i == dogs.size() - 1) {
        			count ++;
        			i = -1;
        		}
        		break;
        	case 4:
        		if(i == 0)
        		{
        			System.out.println("\n" + parrots.get(0).GetAnimalType() + " making sounds:");
        		}
        		parrots.get(i).MakeSound();
        		break;
        	}
        }
        
        //count all of the animals and output that to the screen.
        System.out.println("\nTotal number of animals: " + Animal.getNumberOfAnimals());
    	System.out.println("Total number of mammals: " + Mammal.getNumberOfMammals());
    	System.out.println("Total number of birds: " + Bird.getNumberOfBirds());
    }
}
