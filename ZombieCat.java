import java.util.Random;
//Zombie cats chase both mice and other non-zombie cats

//Zombie cats eating a mouse is the same as a normal cat. The mouse dies and is removed from the simulation.
//When a zombie cat eats a cat, that cat becomes a zombie cat placed at the same location in the grid square
//A zombie cat when not chasing another creature is displayed as red dot.
//A zombie cat chasing another creature is displayed as a black dot
//A zombie cat jumps 3 spaces at time. It does not move through the intervening space. That is, if it is at (5,10) it moves directly to (5,13).
//A zombie cat that doesn’t eat anything within 100 rounds dies.

public class ZombieCat extends Cat {
      int movesSinceBirth;
      public ZombieCat (int x, int y, City cty, Random rnd) {
          super(x, y, cty, rnd);
          dead = false;
          lab = LAB_RED;
          stepLen = 3; //moe by 3 
          movesSinceEat = 0;
          prey = null;
          foundPrey = false;
          radius = 40; //Zombie cats can search up to 40 gird squares away (as measured by GridPoint.distance()
      }  

      //A zombie cat searches up to 40 grid points for a mouse or cat o chase.
      public boolean search() {
        for(Creature creature : city.creatures){
          if (creature instanceof Mouse && creature.dist(this) <= radius) {
                prey = creature;
                foundPrey = true;
                return foundPrey; 
            }
            else if((creature instanceof Cat && !(creature instanceof ZombieCat)) && creature.dist(this) <= radius){
                prey = creature;
                foundPrey = true;
                return foundPrey;    
            }
        }
        return foundPrey;
    }

    public void hunt() {
        //if the prey died of natural causes, stop the hunt
        if(prey.dead == true){
            foundPrey = false;
            prey = null;
            lab = LAB_RED;
            return;
        }

        //Compare Horizontal Distance of Cat and Prey 
        int distX = this.getX() - prey.getX();
        int distY = this.getY() - prey.getY();

        if (Math.abs(distX) > Math.abs(distY)) {

            //if the cord of the cat is greater than the prey, then the cat moves towards the west
            if (prey.getX() < this.getX()) {
                setDir(WEST);
          
              //if the cord of the cat is lesser than the prey, then the cat moves towards the east
            } else if (prey.getX() > this.getX()) {
                setDir(EAST);
     
            }
        } else if (Math.abs(distX) < Math.abs(distY)){
            //if the cord of the cat is lesser than the prey, then the cat moves towards the east
            if (prey.getY() > this.getY()) {
                setDir(NORTH);

            } else if (prey.getY() < this.getY()) {
                setDir(SOUTH);
            
            }
        }
        if((distX == 0) && (distY == 0)) {
            if(prey instanceof Cat && !(prey instanceof ZombieCat)){ //if prey is a cat and it is eaten make it a zombie cat
                prey.dead = true;
                prey = null;
                foundPrey = false;
                movesSinceEat = 0;
                lab = LAB_RED;
                city.creaturesToAdd.add(new ZombieCat(getX(), getY() ,city, rand)); 
                return;
              }
            else if(prey instanceof Mouse){ //if prey is a mouse just kill
                prey.dead = true;
                prey = null;
                foundPrey = false;
                movesSinceEat = 0;
                lab = LAB_RED;
                return;
            }
        }
    }

           /*  //A cat eats a mouse if in same spot as mouse
            for(Creature creature : city.creatures){
                if(this.getX() == creature.getX() && this.getY() == creature.getY() && creature instanceof Mouse){
                 creature.dead = true;
                 movesSinceEat = 0; 
                 return;
             }
             }
            }
*/
    public void takeAction() {

        //If a zombiecat doesn’t eat a mouse within 100 moves, the zombiecat dies.
        if (movesSinceEat == 100) {
            dead = true;
            return;
        }
        // If no prey has been found
        if (!foundPrey) {
            // Search for prey
            search();
            if(foundPrey == true)
            {
                // If prey has been found hunt
                lab = LAB_BLACK;
                hunt();
                
            // If the cat cannot find a prey, it moves normally and is displayed in red.  
            } else {
                // Make a random turn 5% of the time.
                lab = LAB_RED;
                if (rand.nextInt(20) < 1) {
                    randomTurn();
                }
            }
        } else {
            // Otherwise, if prey has beeen found. HUNT. 
            lab = LAB_BLACK;
            hunt();     
        }
     movesSinceEat++;
    }
}