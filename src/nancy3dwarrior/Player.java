package nancy3dwarrior;

public class Player {
    public final static int DOUBLE_ENERGY = 2;
    private String name;
    private int level, x, y, energy;
    
    public Player() {
        // level, x, y are already 0 by default
        name = "";
        energy = 10;
    }
    
    public Player(String name) {
        // level, x, y are already 0 by default
        this.name = name;
        energy = 10;
    }
    
    public Player(int level, int x, int y) {
        this.level = level;
        this.x = x;
        this.y = y;
        name = "";
        energy = 10;
    }
    
    public Player(Player other) {
        name = other.name;
        level = other.level;
        x = other.x;
        y = other.y;
        energy = other.energy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getEnergy() {
        return energy;
    }
    
    public void moveTo(Player p) {
        level = p.level;
        x = p.x;
        y = p.y;
    }
    
    public boolean won(Board b) {
        // -1 to board values because index starts at 0
        return (level == b.getLevel()-1 && x == b.getSize()-1 && y == b.getSize()-1);
    }
    
    public void addEnergy(int energy){
        this.energy += energy;
    }
    
    public boolean equals(Player p) {
        return (level == p.level && x == p.x && y == p.y);
    }
    
    @Override
    public String toString() {
        return name + " is on level " + level + " at location (" + x + ", " + y + ") and has " + energy + " units of energy.";
    }
}
