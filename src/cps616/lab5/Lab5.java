/*
1) Yes this approach is the most efficient to solve this problem
2) An alternate approach is finding the distance of a number with every other number after it in the array
3) This approach is O(nlogn) and the alternative approach would be 0(n^2) since you would be checking every value after
   the array and not before
*/
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Lab5 {

    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(sc.readLine());
        for (int x = 0; x < count; x++){
            int size = Integer.parseInt(sc.readLine());
            Point2D arr[] = new Point2D[size];
            String[] split = sc.readLine().split(" ");
            for (int y = 0; y < size; y++){
                arr[y] = new Point2D.Double(Double.parseDouble(split[y]),0);
            }
            
            Comparator<Point2D> ComparatorByX = new Comparator<Point2D>() {
                @Override
                public int compare(Point2D o1, Point2D o2) {
                    return Double.compare(o1.getX(), o2.getX());
                }
            };
            Arrays.sort(arr, ComparatorByX);
            
            Point2D[] min = getClosestPair(arr);
            System.out.println((int)Math.abs(min[0].getX() - min[1].getX()));
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
                if (Math.pow(Math.abs(arr[x].getX() - m),2) > d) {
                    break;
                }
                for (int y = midLeft; y < arr.length; y++) {
                    if (Math.pow(Math.abs(arr[x].getX() - arr[y].getX()),2) > d) {
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