import java.awt.*;

public class Player {
    private int x,y;
    private int dX, dY;
    private Image image;
    private int score;
    private int speed;

    public Player(int x,int y, int speed, Image image) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.image = image;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getDx() {
        return this.dX;
    }
    public int getDy() {
        return this.dY;
    }
    public Image getImage() {
        return this.image;
    }
    public int getScore() {
        return this.score;
    }
    public int getSpeed() {
        return this.speed;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setdX(int dX) {
        this.dX = dX;
    }
    public void setdY(int dY) {
        this.dY = dY;
    }
    public void setImage(Image image){
        this.image = image;
    }
    public void setScale(int score){
        this.score = score;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
}
