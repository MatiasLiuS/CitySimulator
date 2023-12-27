import java.util.Random;
/*Level 1:

Full implement mice actions such that:
After 20 rounds of simulation time, a mouse produces a new baby mouse at the same location
A mouse dies after 30 rounds simulation time
A mouse randomly turns, changes directions 20% of the time
A mouse is displayed as a blue dot. DONE
 
*/

public class Mouse extends Creature {
    int movesSinceBirth;

    public Mouse (int x, int y, City cty, Random rnd) {
        super(x, y, cty, rnd);
      //dead = false;
        lab = LAB_BLUE; //A mouse is displayed as a blue dot.
        stepLen = 1; //Steps 1 by 1
        movesSinceBirth = 0; //Counter
    }  
 
public void takeAction() {
    movesSinceBirth++;
    //After 20 rounds of simulation time, a mouse produces a new baby mouse at the same location
    if (movesSinceBirth == 20) {
        city.creaturesToAdd.add(new Mouse(getX(), getY() ,city, rand));
        return;
    }
    //A mouse dies after 30 rounds simulation time
    if (movesSinceBirth == 30) {
        dead = true;
        return;
    }
     //A mouse randomly turns, changes directions 20% of the time
     if(rand.nextInt(5) == 0){
        randomTurn();
    }
    
}

}

