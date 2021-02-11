package frc.robot;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.util.Color;

public class ColorMatcher extends ColorMatch {
    static final Color kBlueTarget = makeColor(0.143, 0.427, 0.429);
    static final Color kGreenTarget = makeColor(0.197, 0.561, 0.240);
    static final Color kRedTarget = makeColor(0.561, 0.232, 0.114);
    static final Color kYellowTarget = makeColor(0.361, 0.524, 0.113);

    public void addColors() {
        super.addColorMatch(kBlueTarget);
        super.addColorMatch(kGreenTarget);
        super.addColorMatch(kRedTarget);
        super.addColorMatch(kYellowTarget);
    }

    public String colorCheck(Color inputtedRGB) {
        ColorMatchResult match = super.matchClosestColor(inputtedRGB);

        if (match.color == kBlueTarget) {
            return("Blue");
          } else if (match.color == kRedTarget) {
            return("Red");
          } else if (match.color == kGreenTarget) {
            return("Green");
          } else if (match.color == kYellowTarget) {
            return("Yellow");
          } else {
            return("Unknown");
          }
    }
}
