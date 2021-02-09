package frc.robot;

public class ColorMatcher extends ColorMatch {
    static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    public void addColors() {
        addColorMatch(kBlueTarget);
        addColorMatch(kGreenTarget);
        addColorMatch(kRedTarget);
        addColorMatch(kYellowTarget);
    }

    public String matchColor(Color inputtedRGB) {
        ColorMatchResult match = matchClosestColor(detectedColor);

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