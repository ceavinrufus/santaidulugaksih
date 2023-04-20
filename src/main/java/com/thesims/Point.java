public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(Point p) {
        x = p.getX();
        y = p.getY();
    }

    public void move(int x, int y) {
        // x : new X coordinate
        // y : new Y coordinate
        setX(x);
        setY(y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point point = (Point) obj;
            return (point.getX() == x && point.getY() == y);
        } else {
            return false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", getX(), getY());
    }

    // public static void main(String[] args) {
    //     Point point1 = new Point(6, 3);
    //     System.out.println(point1.toString());
    //     Point point2 = new Point();
    //     System.out.println(point2.toString());
    //     System.out.println(point1.equals(point2));
    //     point2.move(6, 3);
    //     System.out.println(point1.equals(point2));
    //     point1.setX(2);
    //     System.out.println(point1.toString());
    //     Point point3 = new Point(point2);
    //     System.out.println(point3.toString());
    // }
}