/**
 * Created by Участник on 20.05.2017.
 */
public class Point {
    private int x,y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point(String[] split) {
        this.x = Integer.parseInt(split[0]);
        this.y = Integer.parseInt(split[1]);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return getX() + "," + getY();
    }
}
