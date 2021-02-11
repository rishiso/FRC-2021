package frc.robot;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.util.Color;

public class ColorMatcher extends ColorMatch {
    static final Color kBlueTarget = super.makeColor(0.143, 0.427, 0.429);
    static final Color kGreenTarget = super.makeColor(0.197, 0.561, 0.240);
    static final Color kRedTarget = super.makeColor(0.561, 0.232, 0.114);
    static final Color kYellowTarget = super.makeColor(0.361, 0.524, 0.113);

    public void addColors() {
        super.addColorMatch(kBlueTarget);
        super.addColorMatch(kGreenTarget);
        super.addColorMatch(kRedTarget);
        super.addColorMatch(kYellowTarget);
    }

    public String matchColor(Color inputtedRGB) {
        ColorMatchResult match = super.matchClosestColor(inputtedRGB);

        switch (match.color) {
            case kBlueTarget :
                return("Blue");
                break;
            case kGreenTarget :
                return("Green");
                break;
            case kRedTarget :
                return("Red");
                break;
            case kYellowTarget :
                return("Yellow");
                break;
            default :
                return("L");
                break;
        }
    }
}
