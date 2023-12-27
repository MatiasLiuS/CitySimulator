import java.util.Random;
//A cat searches up to 20 grid points (as measured by the GridPoint.distance() method) for a mouse to chase.
//If the cat finds a mouse, it moves towards the mouse and is displayed using the color cyan. (This is to make it easier for you to debug, and for us to grade).
//If the cat cannot find a mouse, it moves normaly and is displayed in yellow.

public class Cat extends Creature {
      int movesSinceEat;
      boolean foundPrey;
      int radius;
      Creature prey = null;

      public Cat (int x, int y, City cty, Random rnd) {
          super(x, y, cty, rnd);
          dead = false;
          lab = LAB_YELLOW; //Cats are displayed as a yellow dot.
          stepLen = 2; //Cats jump two spaces at a time. 
          movesSinceEat = 0;
          prey = null; 
          foundPrey = false;
          radius = 20;
      }  

      //A cat searches up to 20 grid points (as measured by the GridPoint.distance() method) for a mouse to chase.
        public boolean search() {
        for(Creature creature : city.creatures){
            if (creature instanceof Mouse && creature.dist(this) <= radius) {
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
            lab = LAB_YELLOW;
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
            //if the cord of the cat is lesser than the prey, then the cat moves towards the north
            if (prey.getY() > this.getY()) {
                setDir(NORTH);
            //if the cord of the cat is greater than the prey, then the cat moves towards the south
            } else if (prey.getY() < this.getY()) {
                setDir(SOUTH);
            
            }
        }
        //if at same location, kill.
        if((distX == 0) && (distY == 0)) {
            prey.dead = true;
            prey = null;
            foundPrey = false;
            movesSinceEat = 0;
            lab = LAB_YELLOW;
            return;
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

        //If a cat doesn’t eat a mouse within 50 moves, the cat dies and becomes zombie
        if (movesSinceEat == 50) {
            dead = true;
            city.creaturesToAdd.add(new ZombieCat(getX(), getY() ,city, rand));
            return;
        }
        // If no prey has been found
        if (!foundPrey) {
            // Search for prey
            search();
            if(foundPrey == true)
            {
                // If prey found, hunt
                lab = LAB_CYAN;
                hunt();
                
            // If the cat cannot find a prey, it moves normally and is displayed in yellow.  
            } else {
                // Make a random turn 5% of the time.
                lab = LAB_YELLOW;
                if (rand.nextInt(20) < 1) {
                    randomTurn();
                }
            }
        } else {
            // Otherwise, if prey found, hunt.
            lab = LAB_CYAN;
            hunt();     
        }
     movesSinceEat++;
    }
}       