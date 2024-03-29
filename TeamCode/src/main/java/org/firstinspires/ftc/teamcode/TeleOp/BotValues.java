package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.Autonomous.ExplosiveTachyonicParticle;

public class BotValues
{
                                                                                                                                                                                                                      /**
    ///////////////////////////////// Servo Positions /////////////////////////////////////                                                                                                                                   **/

    ///////////////// Arm /////////////////

    // Home (intake)
    public final static double LEFT_ARM_HOME = 0.08;
    public final static double RIGHT_ARM_HOME = 0.72;

    // Outtake
    public final static double LEFT_ARM_OUTTAKE = 0.8;
    public final static double RIGHT_ARM_OUTTAKE = 0;

    // Stack Topmost Pixel (5)
    public final static double LEFT_ARM_STACK_5 = 0.3;
    public final static double RIGHT_ARM_STACK_5 = 0.6;

    // Stack Top 2 Pixels (4-5)
    public final static double LEFT_ARM_STACK_45 = 0.25;
    public final static double RIGHT_ARM_STACK_45 = 0.55;

    // Stack Pixels 3-4
    public final static double LEFT_ARM_STACK_34 = 0.2;
    public final static double RIGHT_ARM_STACK_34 = 0.7;

    // Stack Pixels 2-3
    public final static double LEFT_ARM_STACK_23 = 0.15;
    public final static double RIGHT_ARM_STACK_23 = 0.65;

    // Stack Pixels 1-2
    public final static double LEFT_ARM_STACK_12 = 0.1;
    public final static double RIGHT_ARM_STACK_12 = 0.7;

    // Stack Up
    public final static double LEFT_ARM_STACK_UP = 0.4;
    public final static double RIGHT_ARM_STACK_UP = 0.4;


    ///////////////// Wrist ///////////////////

    // Home (fold)
    public final static double LEFT_WRIST_HOME = 0.225;
    public final static double RIGHT_WRIST_HOME = 0.875;

    // Intake
    public final static double LEFT_WRIST_INTAKE = 0.55;
    public final static double RIGHT_WRIST_INTAKE = 0.55;

    // Outtake Down
    public final static double LEFT_WRIST_OUTTAKE_DOWN = 0.1;
    public final static double RIGHT_WRIST_OUTTAKE_DOWN = 0.85;

    // Outtake Up
    public final static double LEFT_WRIST_OUTTAKE_UP = 0.75;
    public final static double RIGHT_WRIST_OUTTAKE_UP = 0.25;

    // Stack topmost pixel (5)
    public final static double LEFT_WRIST_STACK_5 = 0.55;
    public final static double RIGHT_WRIST_STACK_5 = 0.15;

    // Stack top 2 pixels (4-5)
    public final static double LEFT_WRIST_STACK_45 = 0.5;
    public final static double RIGHT_WRIST_STACK_45 = 0.2;

    // Stack Pixels 3-4
    public final static double LEFT_WRIST_STACK_34 = 0.45;
    public final static double RIGHT_WRIST_STACK_34 = 0.25;

    // Stack Pixels 2-3
    public final static double LEFT_WRIST_STACK_23 = 0.4;
    public final static double RIGHT_WRIST_STACK_23 = 0.3;

    // Stack Pixels 1-2
    public final static double LEFT_WRIST_STACK_12 = 0.35;
    public final static double RIGHT_WRIST_STACK_12 = 0.35;


    ////////////////// Left Claw ///////////////////
    public final static double LEFT_CLAW_HOME = 0.9;
    public final static double LEFT_CLAW_RANGE = 0.5;


    ////////////////// Right Claw //////////////////
    public final static double RIGHT_CLAW_HOME = 0.1;
    public final static double RIGHT_CLAW_RANGE = 0.5;


    //////////////// Plane Launcher /////////////////
    public final static double PLANE_LAUNCHER_HOME = 0.1;
    public final static double PLANE_LAUNCHER_RANGE = 0.4;

                                                                                                                                                                                                                                    /**
    //////////////////////////////////////// Times ////////////////////////////////////////////                                                                                                                                      **/

    ///////////////////// Arm ///////////////////////
    public final static double ARM_TO_OUTTAKE_TIME = 1000; // in milliseconds
    public final static double ARM_TO_INTAKE_TIME = 1000; // in milliseconds
    public final static double ARM_TO_STACK_INTAKE_TIME = 400; // in milliseconds
    public final static double ARM_TO_STACK_UP_TIME = 150; // in milliseconds


    ///////////////////// Wrist /////////////////////
    public final static double WRIST_TO_INTAKE_TIME = 200; // in milliseconds
    public final static double WRIST_TO_FOLD_TIME = 200; // in milliseconds
    public final static double WRIST_TO_OUTTAKE_DOWN_TIME = 200; // in milliseconds
    public final static double WRIST_TO_OUTTAKE_UP_TIME = 200; // in milliseconds
    public final static double WRIST_TO_STACK_INTAKE_TIME = 200; // in milliseconds


    //////////////////// Left Claw //////////////////
    public final static double LEFT_CLAW_OPEN_TIME = 200; // in milliseconds
    public final static double LEFT_CLAW_CLOSE_TIME = 200; // in milliseconds


    //////////////////// Right Claw /////////////////
    public final static double RIGHT_CLAW_OPEN_TIME = 200; // in milliseconds
    public final static double RIGHT_CLAW_CLOSE_TIME = 200; // in milliseconds


    ////////////////////// Pixels ////////////////////
    public final static double PURPLE_PIXEL_DROP_TIME = 500; // in milliseconds
    public final static double YELLOW_PIXEL_DROP_TIME = 500; // in milliseconds
    public final static double WHITE_PIXEL_DROP_TIME = 500; // in milliseconds


    ///////////////////// April Tags /////////////////
    public final static double APRIL_TAG_DETECTION_TIME = 250; // in milliseconds


    ///////////////// Image Recognition //////////////
    public final static double IMAGE_RECOGNITION_CUSHION_TIME = 50; // in milliseconds


    ////////////////////// Driving ///////////////////
    public final static double AWAY_FROM_SPIKE_MARK_TIME = 800; // in milliseconds
    public final static double AWAY_FROM_BACKDROP_TIME = 500; // in milliseconds


    ////////////////////// Op Mode ///////////////////
    public final static double AUTONOMOUS_TIME_CUSHION = 2; // in seconds

                                                                                                                                                                                                                                                        /**
    /////////////////////////////////////////// Distances /////////////////////////////////////////                                                                                                                                                        **/

    ////////////////////// Slides /////////////////////
    public final static double HIGH_SET_LINE = 20.1; // in inches
    public final static double MEDIUM_SET_LINE = 16; // in inches
    public final static double LOW_SET_LINE = 12.375; // in inches
    public final static double YELLOW_DROP_HEIGHT = 15; // in inches


    ////////////////////// Stacks //////////////////////
    public final static double STACK_SAFETY_DISTANCE = 5; // in inches


    ////////////////////// Backdrop ////////////////////
    public final static double BACKDROP_SAFETY_DISTANCE = 8; // in inches
    public final static double BACKDROP_ALIGNMENT_RANGE = 0.05; // in inches
    public static final double backdropBackOutDistance = 7; // in inches


    ////////////////////// Spike Mark //////////////////
    public static final double spikeMarkBackOutDistance = 7; // in inches
    public final static double QUICK_TURN_ANGLE = Math.toRadians(45);

                                                                                                                                                                                                                                                                    /**
    ////////////////////////////////////// Measurements /////////////////////////////////////////                                                                                                                                                                      **/
    public final static double SLIDE_HUB_DIAMETER = 1.5; // in inches

    // inches from camera to center of right claw (positive if camera is to the right of claw)
    public final static double CAMERA_DISTANCE_TO_CLAW = 3.0;

    ///////////// Motor Encoder Resolutions /////////////
    public final static double TICKS_PER_REV_312 = 537.7;

                                                                                                                                                                                                                                                                                                        /**
    //////////////////////////////////////// Powers //////////////////////////////////////////////                                                                                                                                                                                                           **/

    /////////////////////// Driving /////////////////////
    public static double pow = 0.5;//0.77; //voltageNormalize(0.77);
    public static double slowPow = 0.2; //voltageNormalize(0.2);
    public static double autoFastPow = 0.77; //voltageNormalizeForAuto(0.77);
    public static double autoSlowPow = 0.2; //voltageNormalizeForAuto(0.2);


    /////////////////////// Slides //////////////////////
    public static double slideUpAutoPow = 0.8; //voltageNormalize(0.8);
    public static double slideDownAutoPow = -0.5; //voltageNormalize(-0.5);
    public static double slideUpManualPow = 0.4; //voltageNormalize(0.4);
    public static double slideDownManualPow = -0.3; //voltageNormalize(-0.3);


    /////////////////////// Hanging //////////////////////
    public static double hangPow = /*voltageNormalize(1)*/ 1;

                                                                                                                                                                                                                                                                                    /**
    ////////////////////////////////////////// Dead Zones /////////////////////////////////////////                                                                                                                                                                                    **/
    public final static double driveStickDeadZoneLow = 0.05;
    public final static double driveStickDeadZoneHigh = 0.9;
    public final static double turningDeadZone = 0.05;

                                                                                                                                                                                                                                                                                        /**
    ///////////////////////////////////////////// IMU ////////////////////////////////////////////                                                                                                                                                                                        **/
    public final static double angleRoundingPlace = 100.0; // 100.0 for rounding to hundredths place

                                                                                                                                                                                                                                                                                                    /**
    //////////////////////////////////////// Computer Vision /////////////////////////////////////                                                                                                                                                                                                      **/

    //////////////// Inference Thresholds ////////////////
    public final static Float INFERENCE_CONFIDENCE_THRESHOLD = 0.5f;


    //////////////// Camera Resolutions //////////////////
    public final static int INTAKE_RESOLUTION_WIDTH = 1280;
    public final static int INTAKE_RESOLUTION_HEIGHT = 720;
    public final static int OUTTAKE_RESOLUTION_WIDTH = 1280;
    public final static int OUTTAKE_RESOLUTION_HEIGHT = 720;


    //////////////////// Model Names /////////////////////
    public final static String RB_MODEL_NAME = "redBack.tflite";
    public final static String RA_MODEL_NAME = "redFront.tflite";
    public final static String BB_MODEL_NAME = "blueBack.tflite";
    public final static String BA_MODEL_NAME = "blueFront.tflite";
    public final static String STACK_DETECTION_MODEL_NAME = "stackDetection.tflite";


    /////////////////// Detection Labels /////////////////

    // Team Prop
    public final static int PROP_LEFT = 0;
    public final static int PROP_CENTER = 1;
    public final static int PROP_RIGHT = 2;

    // Stack of White Pixels
    public final static int TOO_LEFT_OF_STACK = 0;
    public final static int TOO_RIGHT_OF_STACK = 1;
    public final static int ALIGNED_WITH_STACK = 2;


    /////////////////// April Tag IDs //////////////////
    public final static int BLUE_LEFT_APRIL_TAG = 1;
    public final static int BLUE_CENTER_APRIL_TAG = 2;
    public final static int BLUE_RIGHT_APRIL_TAG = 3;
    public final static int RED_LEFT_APRIL_TAG = 4;
    public final static int RED_CENTER_APRIL_TAG = 5;
    public final static int RED_RIGHT_APRIL_TAG = 6;

                                                                                                                                                                                                                                                                                                                                                /**
    //////////////////////////////////////////// Road Runner ///////////////////////////////////////////                                                                                                                                                                                                                                            **/
    public final static double MAX_DRIVE_RPM = 312;
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(0, 0, 0, getMotorVelocityF(MAX_DRIVE_RPM / 60 * TICKS_PER_REV_312));
    public final static boolean RUN_USING_ENCODER = false;


    // These are physical constants that can be determined from your robot (including the track
    // width; it will be tune empirically later although a rough estimate is important). Users are
    // free to chose whichever linear distance unit they would like so long as it is consistently
    // used. The default values were selected with inches in mind. Road runner uses radians for
    // angular distances although most angular parameters are wrapped in Math.toRadians() for
    // convenience. Make sure to exclude any gear ratio included in MOTOR_CONFIG from GEAR_RATIO.
    public static double WHEEL_RADIUS = 2; // in
    public static double GEAR_RATIO = 1.937902592371828; // output (wheel) speed / input (motor) speed
    public static double TRACK_WIDTH = 18.02; // in


    // These are the feedforward parameters used to model the drive motor behavior. If you are using
    // the built-in velocity PID, these values are fine as is. However, if you do not have drive
    // motor encoders or have elected not to use them for velocity control, these values should be
    // empirically tuned.
    public static double kV = 0.009480; // Volt-seconds per meter
    public static double kA = 0.00260; // Volt-seconds^2 per meter
    public static double kStatic = 0.098; // Volts


    // These values are used to generate the trajectories for you robot. To ensure proper operation,
    // the constraints should never exceed ~80% of the robot's actual capabilities. While Road
    // Runner is designed to enable faster autonomous motion, it is a good idea for testing to start
    // small and gradually increase them later after everything is working. All distance units are
    // inches.
    public static double MAX_VEL = 52.276; // inches per second
    public static double MAX_ACCEL = 52.276; // inches per second per second
    public static double MAX_ANG_VEL = Math.toRadians(180); // radians per second
    public static double MAX_ANG_ACCEL = Math.toRadians(180); // radians per second per second


    // Adjust the orientations here to match your robot. See the FTC SDK documentation for details.
    public static RevHubOrientationOnRobot.LogoFacingDirection LOGO_FACING_DIR = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
    public static RevHubOrientationOnRobot.UsbFacingDirection USB_FACING_DIR = RevHubOrientationOnRobot.UsbFacingDirection.UP;

    public static double encoderTicksToInches(double ticks) {return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV_312;}

    public static double rpmToVelocity(double rpm) {return rpm * GEAR_RATIO * 2 * Math.PI * WHEEL_RADIUS / 60.0;}

    public static double getMotorVelocityF(double ticksPerSecond)
    {
        // see https://docs.google.com/document/d/1tyWrXDfMidwYyP_5H4mZyVgaEswhOC35gvdmP-V-5hA/edit#heading=h.61g9ixenznbx
        return 32767 / ticksPerSecond;
    }

                                                                                                                                                                                                                                                                                                                                                                /**
    ////////////////////////////////////////////////// Robot Positions /////////////////////////////////////////////////                                                                                                                                                                                                                                           **/

    ////////////////////// Starting Position //////////////
    public static final Pose2d startPoseRA = new Pose2d(66, -36, Math.toRadians(0));
    public static final Pose2d startPoseRB = new Pose2d(66, 12, Math.toRadians(0));
    public static final Pose2d startPoseBA = new Pose2d(-66, -36, Math.toRadians(180));
    public static final Pose2d startPoseBB = new Pose2d(-66, 12, Math.toRadians(180));


    //////////////////////// Spike Marks /////////////////
    public static final Vector2d leftSpikeRA = new Vector2d(30, -47);
    public static final Vector2d centerSpikeRA = new Vector2d(25, -36);
    public static final Vector2d rightSpikeRA = new Vector2d(30, -25);

    public static final Vector2d leftSpikeRB = new Vector2d(30, 1);
    public static final Vector2d centerSpikeRB = new Vector2d(25, 12);
    public static final Vector2d rightSpikeRB = new Vector2d(30, 23);

    public static final Vector2d leftSpikeBA = new Vector2d(-30, -25);
    public static final Vector2d centerSpikeBA = new Vector2d(-25, -30);
    public static final Vector2d rightSpikeBA = new Vector2d(-30, -47);

    public static final Vector2d leftSpikeBB = new Vector2d(-30, 23);
    public static final Vector2d centerSpikeBB = new Vector2d(-25, 12);
    public static final Vector2d rightSpikeBB = new Vector2d(-30, 1);


    ///////////////////////// Stacks ////////////////////////
    public static final Vector2d stack1 = new Vector2d(-36, -71);
    public static final Vector2d stack2 = new Vector2d(-24, -71);
    public static final Vector2d stack3 = new Vector2d(-12, -71);
    public static final Vector2d stack4 = new Vector2d(12, -71);
    public static final Vector2d stack5 = new Vector2d(24, -71);
    public static final Vector2d stack6 = new Vector2d(36, -71);


    ////////////////// April Tag Detection Spots ////////////
    public static final Vector2d aprilTagDetectionSpotRed = new Vector2d(18, 36);
    public static final Vector2d aprilTagDetectionSpotBlue = new Vector2d(-18, 36);


    ////////////////// Backdrop Spots for Scoring ///////////
    public static final Vector2d backdropRedLeft = new Vector2d(30, 52);
    public static final Vector2d backdropRedCenter = new Vector2d(36, 52);
    public static final Vector2d backdropRedRight = new Vector2d(42, 52);
    public static final Vector2d backdropBlueLeft = new Vector2d(-42, 52);
    public static final Vector2d backdropBlueCenter = new Vector2d(-36, 52);
    public static final Vector2d backdropBlueRight = new Vector2d(-30, 52);


    /////////////////////////// Parking //////////////////////
    public static final Vector2d redParkingLeft = new Vector2d(12, 60);
    public static final Vector2d redParkingRight = new Vector2d(60, 60);
    public static final Vector2d blueParkingRight = new Vector2d(-12, 60);
    public static final Vector2d blueParkingLeft = new Vector2d(-60, 60);

                                                                                                                                                                                                                                                                                                                                                                        /**
    /////////////////////////////////////////// Custom Methods ///////////////////////////////////////////////                                                                                                                                                                                                                                                              **/
    public static double normalizeAngle(double radians)
    {
        double angle = radians;
        if (radians > Math.PI) {angle -= (2 * Math.PI);}
        else if (radians <= (-1 * Math.PI)) {angle += (2 * Math.PI);}
        return angle;
    }
    public static double voltageNormalize(double power) throws NullPointerException
    {
        if ((power * (12.0 / ExplosiveTachyon.voltageSensor.getVoltage())) > 1) {return 1.0;}
        else {return (power * (12.0 / ExplosiveTachyon.voltageSensor.getVoltage()));}
    }
    public static double voltageNormalizeForAuto(double power) throws NullPointerException
    {
        if ((power * (12.0 / ExplosiveTachyonicParticle.voltageSensor.getVoltage())) > 1) {return 1.0;}
        else {return (power * (12.0 / ExplosiveTachyonicParticle.voltageSensor.getVoltage()));}
    }
    public static double acceleratorTransform(double distance)
    {
        return Math.cos(distance * ((2 * Math.PI) / (4 * BACKDROP_SAFETY_DISTANCE)));
    }
    public static double acceleratorTransformTeleOp(double stick)
    {
        return Math.abs(Math.sin(0.7 * stick * (Math.PI / 3.0)));
    }
}

