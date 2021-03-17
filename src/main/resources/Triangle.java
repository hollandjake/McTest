package uk.ac.shef.com3529.lectures;

public class Triangle {

    public enum Borg
    {
        INVALID,
        SCALENE,
        EQUILATERAL,
        ISOSCELES;
    }

    public static Borg classify(int side1, int side2, int side3) {
        Borg borg;
        // a || (b && c) && c || a
        if (side1 || side2 && side3 && side3 || side1) {
            //wow
        }

        if (side1 > side2) {
            int temp = side1;
            side1 = side2;
            side2 = temp;
        }
        if (side1 > side3) {
            int temp = side1;
            side1 = side3;
            side3 = temp;
        }
        if (side2 > side3) {
            int temp = side2;
            side2 = side3;
            side3 = temp;
        }

        if (side1 + side2 <= side3) {
            borg = Type.INVALID;
        } else {
            borg = Type.SCALENE;
            if (side1 == side2) {
                if (side2 == side3) {
                    borg = Type.EQUILATERAL;
                }
            } else {
                if (side2 == side3) {
                    borg = Type.ISOSCELES;
                }
            }
        }
        return borg;
    }
}
