    import java.util.Random;
//Zombie cats chase both mice and other non-zombie cats

//Zombie cats eating a mouse is the same as a normal cat. The mouse dies and is removed from the simulation.
//When a zombie cat eats a cat, that cat becomes a zombie cat placed at the same location in the grid square
//A zombie cat when not chasing another creature is displayed as red dot.
//A zombie cat chasing another creature is displayed as a black dot
//A zombie cat jumps 3 spaces at time. It does not move through the intervening space. That is, if it is at (5,10) it moves directly to (5,13).
//A zombie cat that doesn’t eat anything within 100 rounds dies.

public class Scientist extends Creature {
    boolean foundPatient;
    int radius;
    Creature patient = null;
      public Scientist (int x, int y, City cty, Random rnd) {
          super(x, y, cty, rnd);
          dead = false;
          lab = LAB_GREEN;
          stepLen = 4; //moe by 4
          patient = null;
          foundPatient = false;
          radius = 50; //Zombie cats can search up to 40 gird squares away (as measured by GridPoint.distance()
      }  

        //A Scientist searches up to 50 grid points (as measured by the GridPoint.distance() method) for a ZombieCat to chase.
        public boolean search() {
            for(Creature creature : city.creatures){
                if (creature instanceof ZombieCat && creature.dist(this) <= radius) {
                    patient = creature;
                    foundPatient = true;
                    return foundPatient; 
                }
            }
            return foundPatient;
        }
        public void hunt() {
            //if the prey died of natural causes, stop the hunt
            if(patient.dead == true){
                foundPatient = false;
                patient = null;
                lab = LAB_GREEN;
                return;
            }
    
            //Compare Horizontal Distance of Scientist and Prey 
            int distX = this.getX() - patient.getX();
            int distY = this.getY() - patient.getY();
    
            if (Math.abs(distX) > Math.abs(distY)) {
    
                //if the cord of the cat is greater than the prey, then the cat moves towards the west
                if (patient.getX() < this.getX()) {
                    setDir(WEST);
              
                  //if the cord of the cat is lesser than the prey, then the cat moves towards the east
                } else if (patient.getX() > this.getX()) {
                    setDir(EAST);
    
                }
            } else if (Math.abs(distX) < Math.abs(distY)){
                //if the cord of the cat is lesser than the prey, then the cat moves towards the north
                if (patient.getY() > this.getY()) {
                    setDir(NORTH);
                //if the cord of the cat is greater than the prey, then the cat moves towards the south
                } else if (patient.getY() < this.getY()) {
                    setDir(SOUTH);
                
                }
            }
            //if at same location, heal.
            if((distX == 0) && (distY == 0)) {
                patient.dead = true;
                patient = null;
                foundPatient = false;
                lab = LAB_GREEN;
                city.creaturesToAdd.add(new Cat(getX(), getY() ,city, rand)); 
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
            // If no prey has been found
            if (!foundPatient) {
                // Search for prey
                search();
                if(foundPatient == true)
                {
                    // If prey found, hunt
                    lab = LAB_ORANGE;
                    hunt();
                    
                // If the scientist cannot find zombie cat then act as normal
                } else {
                    // Make a random turn 1% of the time.
                    lab = LAB_GREEN;
                    if (rand.nextInt(100) < 1) {
                        randomTurn();
                    }
                }
            } else {
                // Otherwise, if prey found, hunt.
                lab = LAB_ORANGE;
                hunt();     
            }
        }
    }       