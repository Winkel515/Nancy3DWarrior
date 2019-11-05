package nancy3dwarrior;

import java.util.Random;

public class Dice {
    private static final Random RANDOM = new Random();
    private int die1;
    private int die2;
    
    public Dice() {
        die1 = RANDOM.nextInt(6) + 1;
        die2 = RANDOM.nextInt(6) + 1;
    };

    public int getDie1() {
        return die1;
    }

    public int getDie2() {
        return die2;
    }
    
    public int rollDice() {
        die1 = RANDOM.nextInt(6) + 1;
        die2 = RANDOM.nextInt(6) + 1;
        return die1+die2;
    }
    
    public boolean isDouble() {
        return die1 == die2;
    }
    
    @Override
    public String toString() {
        return "Dice: " + die1 + "," + die2;
    }
}
