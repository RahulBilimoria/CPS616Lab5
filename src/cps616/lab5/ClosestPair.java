
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static void main(String[] args) throws IOException {
        //There is a lot of input so we use BufferedReader to read. Scanner would take too long!
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            int n = Integer.parseInt(sc.readLine());
            if (n == 0) {
                return;
            }
            //Create our array of points.
            Point2D[] arr = new Point2D[n];
            for (int i = 0; i < n; i++) {
                //Read each line, split the input at the space and create a new Point2D for each point.
                String[] split = sc.readLine().split(" ");
                arr[i] = new Point2D.Double(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
            }
            //We want to start by pre-sorting the points by their X-Coordinate
            Comparator<Point2D> ComparatorByX = new Comparator<Point2D>() {
                @Override
                public int compare(Point2D o1, Point2D o2) {
                    return Double.compare(o1.getX(), o2.getX());
                }
            };
            Arrays.sort(arr, ComparatorByX);

            //Find the closest pair.
            Point2D[] min = getClosestPair(arr);

            //Output the points. Don't worry if the output has a few extra zeroes.
            System.out.printf("%f %f %f %f \n", min[0].getX(), min[0].getY(), min[1].getX(), min[1].getY());

        }
    }

    private static Point2D[] getClosestPair(Point2D[] arr) {
        Point2D best1, best2, pl[], pr[];
        double dl, dr, d, m;
        int midLeft, midRight;
        if (arr.length < 3) {
            best1 = arr[0];
            best2 = arr[1];
        } else {
            midLeft = (int) Math.ceil(arr.length / (double) 2);
            midRight = (int) Math.floor(arr.length / (double) 2);
            pl = getClosestPair(Arrays.copyOfRange(arr, 0, midLeft));
            pr = getClosestPair(Arrays.copyOfRange(arr, midRight, arr.length));
            dl = pl[0].distanceSq(pl[1]);
            dr = pr[0].distanceSq(pr[1]);
            if (dl < dr) {
                best1 = pl[0];
                best2 = pl[1];
            } else {
                best1 = pr[0];
                best2 = pr[1];
            }
            m = arr[midLeft - 1].getX();
            d = Math.min(dl,dr);

            for (int x = midLeft - 1; x >= 0; x--) {
                if (Math.pow(arr[x].getX() - m,2)> d) {
                    break;
                }
                for (int y = midLeft; y < arr.length; y++) {
                    if (Math.pow(arr[x].getX() - arr[y].getX(),2) > d) {
                        break;
                    }
                    double dmin = arr[x].distanceSq(arr[y]);
                    if (dmin < d) {
                        d = dmin;
                        best1 = arr[x];
                        best2 = arr[y];
                    }
                }
            }
        }
        return new Point2D[]{best1, best2};        
    }
}
