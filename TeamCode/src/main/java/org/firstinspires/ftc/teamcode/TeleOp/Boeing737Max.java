package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp (name = "Boeing 737 Max")
public class Boeing737Max extends LinearOpMode
{

    double leftF_rightB_pair;
    double Turning;
    double leftb_rightF_pair;



    // Constants
    public static final double BACKDROP_SAFETY_DISTANCE = 14.0; // in inches
    public final static double LEFT_CLAW_TURNER_HOME = 0.5;
    public final static double RIGHT_CLAW_TURNER_HOME = LEFT_CLAW_TURNER_HOME;
    public final static double LEFT_CLAW_TURNER_OUTTAKE_DOWN = 0.999;
    public final static double LEFT_CLAW_TURNER_OUTTAKE_UP = 0.15;
    public final static double RIGHT_CLAW_TURNER_OUTTAKE_DOWN = LEFT_CLAW_TURNER_OUTTAKE_DOWN;
    public final static double RIGHT_CLAW_TURNER_OUTTAKE_UP = LEFT_CLAW_TURNER_OUTTAKE_UP;
    public final static double LEFT_CLAW_TURNER_INITIAL = 0.8; // 0.8
    public final static double RIGHT_CLAW_TURNER_INITIAL = LEFT_CLAW_TURNER_INITIAL;
    public final static double LEFT_CLAW_OPENER_HOME = 0;
    public final static double LEFT_CLAW_OPENER_RANGE = 0.2;
    public final static double RIGHT_CLAW_OPENER_HOME = LEFT_CLAW_OPENER_HOME;
    public final static double RIGHT_CLAW_OPENER_RANGE = LEFT_CLAW_OPENER_RANGE;
    public final static double PLANE_LAUNCHER_HOME = 0;
    public final static double PLANE_LAUNCHER_RANGE = 0.5;
    public final static double PLANE_LOCK_HOME = 0;
    public final static double PLANE_LOCK_RANGE = 0.67;
    public final static double ARM_OUTTAKE_ROTATION = 0.48;
    public final static double ARM_AIRPLANE_ROTATION = 0.55;
    public final static double ARM_INTAKE_ROTATION = 0.08;
    public final static double HIGH_SET_LINE = 20.1; // in inches
    public final static double MEDIUM_SET_LINE = 16; // in inches
    public final static double LOW_SET_LINE = 12.375; // in inches
    public final static double WHEEL_DIAMETER = 4.0; // in inches
    public final static double SLIDE_HUB_DIAMETER = 1.5; // in inches
    public final static double TICKS_PER_REV_312 = 537.7;
    public final static double TICKS_PER_REV_43 = 3895.9;
    public final static double pow = 0.77;
    public final static double slowPow = 0.2;
    public final static double slideUpAutoPow = 0.8;
    public final static double slideDownAutoPow = -0.5;
    public final static double slideUpManualPow = 0.4;
    public final static double slideDownManualPow = -0.3;
    public final static double armAutoPow = 1;
    public final static double armManualPow = 0.5;
    public final static DistanceUnit BACKDROP_DISTANCE_UNIT = DistanceUnit.INCH;
    public final static double DOUBLE_EQUALITY_THRESHOLD = 0.0000001;
    public final static int LAUNCHER_DELAY = 1200;
    public final static double QUICK_TURN_ANGLE = 45;



    // Motors
    private DcMotorEx frontleft, backright, backleft, frontright;
    private DcMotorEx leftHanger, rightHanger;
    private DcMotorEx slides;
    private DcMotorEx arm;
    private Servo leftClawTurner, rightClawTurner;
    private Servo leftClawOpener, rightClawOpener;
    private Servo planeLauncher, planeLock;



    // Sensors
    private IMU imu;
    private Orientation lastAngles;
    private double currentAngle;
    private TouchSensor touchSensor;
    private ElapsedTime timer;
    private TouchSensor backdropSwitch;
    private DistanceSensor leftDistanceSensor, rightDistanceSensor;



    // Positions
    private int slidePos;
    private int armPos;

    // States
    private enum SlideState
    {
        INITIAL, UP_LOW, LOW, UP_MEDIUM, MEDIUM, UP_HIGH, HIGH, DOWN, UP_MANUAL, DOWN_MANUAL, STATIONARY;
    }
    private SlideState slideState, previousSlideState;
    private enum DriveState
    {
        STATIONARY, FORWARD, BACKWARD, LEFT, RIGHT, SLOW_FORWARD, SLOW_BACKWARD, SLOW_LEFT, SLOW_RIGHT,
        DIAGONAL45, DIAGONAL135, DIAGONAL225, DIAGONAL315, COUNTERCLOCKWISE, CLOCKWISE;
    }
    private DriveState driveState;
    private enum AutoDriveState {INITIAL, QUICK_CLOCKWISE, QUICK_COUNTERCLOCKWISE;}
    private AutoDriveState autoDriveState;
    private enum ArmState {INTAKE, TO_OUTTAKE, OUTTAKE, TO_AIRPLANE, AIRPLANE, TO_INTAKE;}
    private ArmState armState, previousArmState;
    private enum TurnerState {INTAKE, TO_OUTTAKE_DOWN, OUTTAKE_DOWN, TO_OUTTAKE_UP, OUTTAKE_UP, TO_INTAKE, INITIAL;}
    private TurnerState turnerState;
    private enum ClawState {OPEN, TO_CLOSED, CLOSED, TO_OPEN;}
    private ClawState leftClawState, rightClawState;
    private enum AirplaneState {INITIAL, UNLOCKING, LAUNCHING, LAUNCHED, RETRACTING, LOCKING;}
    private AirplaneState airplaneState;



    // Controls
    private boolean forward, backward, left, right, slowForward, slowBackward, slowLeft, slowRight;
    private boolean diagonal45, diagonal135, diagonal225, diagonal315;
    private boolean counterClockwise, clockwise, quickClockwise, quickCounterClockwise, stopDrive;
    private boolean launchAirplane, hangUp, hangDown, slidesDown, slideStop;
    private boolean slidesUpLow, slidesUpMid, slidesUpHigh, slidesUpManual, slidesDownManual, slideTouchingSensor;
    private boolean openLeftClaw, closeLeftClaw, openRightClaw, closeRightClaw;
    private boolean armToOuttake, armToIntake, armToAirplane, armUpManual, armDownManual;
    private boolean turnClawForIntake, turnClawForUpOuttake, turnClawForDownOuttake;
    private boolean holdArm;



    @Override
    public void runOpMode()
    {
        initMotors();
        initSensors();
        initStates();
        telemetry.addData("C4", "Ready");
        telemetry.update();


        waitForStart();
        while (opModeIsActive())
        {
            //Conditions for Controller1/Gamepad1 controls
            forward = gamepad1.left_stick_y > 0.25;
            backward = gamepad1.left_stick_y < -0.25;
            left = gamepad1.left_stick_x < -0.25;
            right = gamepad1.left_stick_x > 0.25;

            if (!(forward || backward || left || right))
            {
                diagonal45 = gamepad1.right_stick_y > 0.25 && gamepad1.right_stick_x > 0.25;
                diagonal135 = gamepad1.right_stick_y > 0.25 && gamepad1.right_stick_x < -0.25;
                diagonal225 = gamepad1.right_stick_y < -0.25 && gamepad1.right_stick_x < -0.25;
                diagonal315 = gamepad1.right_stick_y < -0.2 && gamepad1.right_stick_x > 0.25;
            }
            slowForward = gamepad1.dpad_up;
            slowBackward = gamepad1.dpad_down;
            slowLeft = gamepad1.dpad_left;
            slowRight = gamepad1.dpad_right;
            counterClockwise = gamepad1.left_bumper;
            clockwise = gamepad1.right_bumper;
            quickClockwise = gamepad1.left_stick_button;
            quickCounterClockwise = gamepad1.right_stick_button;
            stopDrive = gamepad1.left_trigger > 0.25;
            launchAirplane = gamepad1.x;
            hangUp = gamepad1.y;
            hangDown = gamepad1.a;

            //Conditions for Controller2/Gamepad2 controls
            slidesDown = gamepad2.dpad_left;
            slidesUpHigh = gamepad2.dpad_up;
            slidesUpMid = gamepad2.dpad_right;
            slidesUpLow = gamepad2.dpad_down;
            slidesUpManual = gamepad2.left_stick_y > 0.25;
            slidesDownManual = gamepad2.left_stick_y < -0.25;
            slideStop = gamepad2.left_stick_button;
            slideTouchingSensor = touchSensor.isPressed();
            openLeftClaw = gamepad2.left_trigger > 0.25;
            closeLeftClaw = gamepad2.left_bumper;
            openRightClaw = gamepad2.right_trigger > 0.25;
            closeRightClaw = gamepad2.right_bumper;
            //armToIntake = gamepad2.left_stick_x < -0.25;
            //armToOuttake = gamepad2.left_stick_x > 0.25;
            //armToAirplane = gamepad2.right_stick_y < -0.25;
            armToIntake = gamepad2.a;
            armToOuttake = gamepad2.y;
            armToAirplane = gamepad2.x;
            //armUpManual = gamepad2.left_stick_x > 0.25;
            //armDownManual = gamepad2.left_stick_x < -0.25;
            turnClawForIntake = gamepad2.right_stick_x < -0.25;
            //turnClawForDownOuttake = gamepad2.right_stick_y > 0.25;
            turnClawForUpOuttake = gamepad2.right_stick_x > 0.25;
            //holdArm = gamepad2.b;
            turnClawForDownOuttake = gamepad2.b;

            // Drivetrain
            if (stopDrive)
            {
                power(0);
                driveState = DriveState.STATIONARY;
            }
            else if (forward)
            {
                if (leftDistanceSensor.getDistance(BACKDROP_DISTANCE_UNIT) < (BACKDROP_SAFETY_DISTANCE) || rightDistanceSensor.getDistance(BACKDROP_DISTANCE_UNIT) < (BACKDROP_SAFETY_DISTANCE))
                {
                    driveState = DriveState.SLOW_FORWARD;
                }
                else {driveState = DriveState.FORWARD;}
            }
            else if (backward) {driveState = DriveState.BACKWARD;}
            else if (left) {driveState = DriveState.LEFT;}
            else if (right) {driveState = DriveState.RIGHT;}
            else if (diagonal45) {driveState = DriveState.DIAGONAL45;}
            else if (diagonal135) {driveState = DriveState.DIAGONAL135;}
            else if (diagonal225) {driveState = DriveState.DIAGONAL225;}
            else if (diagonal315) {driveState = DriveState.DIAGONAL315;}
            else if (slowForward) {driveState = DriveState.SLOW_FORWARD;}
            else if (slowBackward) {driveState = DriveState.SLOW_BACKWARD;}
            else if (slowLeft) {driveState = DriveState.SLOW_LEFT;}
            else if (slowRight) {driveState = DriveState.SLOW_RIGHT;}
            else if (counterClockwise) {driveState = DriveState.COUNTERCLOCKWISE;}
            else if (clockwise) {driveState = DriveState.CLOCKWISE;}
            else {driveState = DriveState.STATIONARY;}

            if (quickClockwise)
            {
                autoDriveState = AutoDriveState.QUICK_CLOCKWISE;
            }
            else if (quickCounterClockwise)
            {
                autoDriveState = AutoDriveState.QUICK_COUNTERCLOCKWISE;
            }

            triggerActions();
        }
    }

    public void initMotors()
    {
        // Initialize Motors
        frontleft = hardwareMap.get(DcMotorEx.class, "front left");
        frontleft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontleft.setZeroPowerBehavior((DcMotorEx.ZeroPowerBehavior.BRAKE));

        frontright = hardwareMap.get(DcMotorEx.class, "front right");
        frontright.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontright.setZeroPowerBehavior((DcMotorEx.ZeroPowerBehavior.BRAKE));

        backleft = hardwareMap.get(DcMotorEx.class, "back left");
        backleft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backleft.setZeroPowerBehavior((DcMotorEx.ZeroPowerBehavior.BRAKE));

        backright = hardwareMap.get(DcMotorEx.class, "back right");
        backright.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backright.setZeroPowerBehavior((DcMotorEx.ZeroPowerBehavior.BRAKE));

        slides = hardwareMap.get(DcMotorEx.class, "slides");
        slides.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        slides.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        leftHanger = hardwareMap.get(DcMotorEx.class, "left hanger");
        leftHanger.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftHanger.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        leftHanger.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        rightHanger = hardwareMap.get(DcMotorEx.class, "right hanger");
        rightHanger.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightHanger.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightHanger.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        arm = hardwareMap.get(DcMotorEx.class, "arm");
        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


        // May need to change this depending on how robot behaves
        frontleft.setDirection(DcMotorEx.Direction.FORWARD);
        backleft.setDirection(DcMotorEx.Direction.REVERSE);
        backright.setDirection(DcMotorEx.Direction.REVERSE);
        frontright.setDirection(DcMotorEx.Direction.REVERSE);
        slides.setDirection(DcMotorEx.Direction.REVERSE);
        leftHanger.setDirection(DcMotorEx.Direction.FORWARD);
        rightHanger.setDirection(DcMotorEx.Direction.FORWARD);
        arm.setDirection(DcMotorEx.Direction.REVERSE);



        leftClawTurner = hardwareMap.get(Servo.class, "left claw turner");
        leftClawTurner.setDirection(Servo.Direction.FORWARD);
        leftClawTurner.setPosition(LEFT_CLAW_TURNER_INITIAL);

        rightClawTurner = hardwareMap.get(Servo.class, "right claw turner");
        rightClawTurner.setDirection(Servo.Direction.REVERSE);
        rightClawTurner.setPosition(RIGHT_CLAW_TURNER_INITIAL);

        leftClawOpener = hardwareMap.get(Servo.class, "left claw opener");
        leftClawOpener.setDirection(Servo.Direction.FORWARD);
        leftClawOpener.setPosition(LEFT_CLAW_OPENER_RANGE);

        rightClawOpener = hardwareMap.get(Servo.class, "right claw opener");
        rightClawOpener.setDirection(Servo.Direction.REVERSE);
        rightClawOpener.setPosition(RIGHT_CLAW_OPENER_RANGE);

        planeLauncher = hardwareMap.get(Servo.class, "plane launcher");
        planeLauncher.setDirection(Servo.Direction.REVERSE);
        planeLauncher.setPosition(PLANE_LAUNCHER_HOME);

        planeLock = hardwareMap.get(Servo.class, "plane lock");
        planeLock.setDirection(Servo.Direction.FORWARD);
        planeLock.setPosition(PLANE_LOCK_HOME);
    }

    public void initSensors()
    {
        lastAngles = new Orientation();
        imu = hardwareMap.get(BHI260IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT, RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
        currentAngle = 0.0;
        touchSensor = hardwareMap.get(TouchSensor.class, "touch sensor");
        timer = new ElapsedTime();
        backdropSwitch = hardwareMap.get(TouchSensor.class, "backdrop switch");
        leftDistanceSensor = hardwareMap.get(DistanceSensor.class, "left distance sensor");
        rightDistanceSensor = hardwareMap.get(DistanceSensor.class, "right distance sensor");
    }

    public void initStates()
    {
        driveState = DriveState.STATIONARY;
        autoDriveState = AutoDriveState.INITIAL;
        slideState = SlideState.INITIAL;
        armState = ArmState.INTAKE;
        turnerState = TurnerState.INITIAL;
        leftClawState = ClawState.CLOSED;
        rightClawState = ClawState.CLOSED;
        airplaneState = AirplaneState.INITIAL;
        previousSlideState = SlideState.INITIAL;
        previousArmState = ArmState.INTAKE;
    }


    // Motor Methods
    public void power(double pow)
    {
        frontleft.setPower(pow);
        frontright.setPower(pow);
        backleft.setPower(pow);
        backright.setPower(pow);
    }

    public void power(double fL, double fR, double bL, double bR)
    {
        frontleft.setPower(fL);
        frontright.setPower(fR);
        backleft.setPower(bL);
        backright.setPower(bR);
    }

    public void resetAngle()
    {
        lastAngles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
        currentAngle = 0.0;
    }

    public double getAngle()
    {
        Orientation orientation = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
        double deltaTheta = orientation.firstAngle - lastAngles.firstAngle;
        if (deltaTheta > 180) {deltaTheta -= 360;}
        else if (deltaTheta <= -180) {deltaTheta += 360;}
        currentAngle += deltaTheta;
        lastAngles = orientation;
        return currentAngle;
    }

    public void triggerActions()
    {
        // Drivetrain FSM
        if (autoDriveState == AutoDriveState.INITIAL)
        {
            resetAngle();

            if (stopDrive) {power(0);}
            else if (forward) {power(pow, pow, pow, pow);}
            else if (backward) {power(-1*pow, -1*pow, -1*pow, -1*pow);}
            else if (right) {power(-1*pow, pow, pow, -1*pow);}
            else if (left) {power(pow, -1*pow, -1*pow, pow);}
            else if (diagonal45) {power(0, pow, pow, 0);}
            else if (diagonal135) {power(pow, 0, 0, pow);}
            else if (diagonal225) {power(0, -1*pow, -1*pow, 0);}
            else if (diagonal315) {power(-1*pow, 0, 0, -1*pow);}
            else if (slowBackward) {power(slowPow, slowPow, slowPow, slowPow);}
            else if (slowForward) {power(-1*slowPow, -1*slowPow, -1*slowPow, -1*slowPow);}
            else if (slowRight) {power(-1*slowPow, slowPow, slowPow, -1*slowPow);}
            else if (slowLeft) {power(slowPow, -1*slowPow, -1*slowPow, slowPow);}
            else if (clockwise) {power(pow, -1*pow, pow, -1*pow);}
            else if (counterClockwise) {power(-1*pow, pow, -1*pow, pow);}
            else {power(0);}
        }
        else if (autoDriveState == AutoDriveState.QUICK_CLOCKWISE)
        {
            if (Math.abs(QUICK_TURN_ANGLE - getAngle()) > 3 && !(stopDrive)) {power(1, -1, 1, -1);}
            else
            {
                power(0);
                autoDriveState = AutoDriveState.INITIAL;
            }
        }
        else if (autoDriveState == AutoDriveState.QUICK_COUNTERCLOCKWISE)
        {
            if (Math.abs(QUICK_TURN_ANGLE - getAngle()) > 3 && !(stopDrive)) {power(-1, 1, -1, 1);}
            else
            {
                power(0);
                autoDriveState = AutoDriveState.INITIAL;
            }
        }


        // Slides FSM
        if (slideStop)
        {
            slides.setPower(0);
            slideState = SlideState.STATIONARY;
        }
        else if (slideState == SlideState.STATIONARY)
        {
            slides.setPower(0);
            slides.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            slides.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            if (slidesDown) {slideState = SlideState.DOWN;}
            else if (slidesUpManual) {slideState = SlideState.UP_MANUAL;}
            else if (slidesDownManual) {slideState = SlideState.DOWN_MANUAL;}
        }
        else if (slideState == SlideState.INITIAL)
        {
            slides.setPower(0);
            slides.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            slides.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            if (slidesUpLow)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_LOW;
            }
            else if (slidesUpMid)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_MEDIUM;
            }
            else if (slidesUpHigh)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_HIGH;
            }
            else if (slidesUpManual)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_MANUAL;
            }
        }
        else if (slideState == SlideState.UP_LOW)
        {
            if (!(slides.isBusy()) && (int)(slides.getCurrentPosition()) != 0)
            {
                slideState = SlideState.LOW;
            }
            else if (!(slides.isBusy()))
            {
                double distance = 0;
                if (previousSlideState == SlideState.INITIAL) {distance = LOW_SET_LINE;}
                else if (previousSlideState == SlideState.MEDIUM) {distance = LOW_SET_LINE - MEDIUM_SET_LINE;}
                else if (previousSlideState == SlideState.HIGH) {distance = LOW_SET_LINE - HIGH_SET_LINE;}
                slides.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                slides.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                slidePos = (int)((distance / (SLIDE_HUB_DIAMETER * Math.PI)) * TICKS_PER_REV_312);
                slides.setTargetPosition(slidePos);
                slides.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

                // set power for moving up
                if (distance > 0) {slides.setPower(slideUpAutoPow);}
                else {slides.setPower(slideDownAutoPow);}
            }
        }
        else if (slideState == SlideState.LOW)
        {
            slides.setPower(0);
            slides.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            slides.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            if (slidesDown)
            {
                previousSlideState = slideState;
                slideState = SlideState.DOWN;
            }
            else if (slidesUpMid)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_MEDIUM;
            }
            else if (slidesUpHigh)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_HIGH;
            }
            else if (slidesUpManual)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_MANUAL;
            }
            else if (slidesDownManual)
            {
                previousSlideState = slideState;
                slideState = SlideState.DOWN_MANUAL;
            }
        }
        else if (slideState == SlideState.UP_MEDIUM)
        {
            if (!(slides.isBusy()) && (int)(slides.getCurrentPosition()) != 0)
            {
                slideState = SlideState.MEDIUM;
            }
            else if (!(slides.isBusy()))
            {
                double distance = 0;
                if (previousSlideState == SlideState.INITIAL) {distance = MEDIUM_SET_LINE;}
                else if (previousSlideState == SlideState.LOW) {distance = MEDIUM_SET_LINE - LOW_SET_LINE;}
                else if (previousSlideState == SlideState.HIGH) {distance = MEDIUM_SET_LINE - HIGH_SET_LINE;}
                slides.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                slides.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                slidePos = (int)((distance / (SLIDE_HUB_DIAMETER * Math.PI)) * TICKS_PER_REV_312);
                slides.setTargetPosition(slidePos);
                slides.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

                // set power for moving up
                if (distance > 0) {slides.setPower(slideUpAutoPow);}
                else {slides.setPower(slideDownAutoPow);}
            }
        }
        else if (slideState == SlideState.MEDIUM)
        {
            slides.setPower(0);
            slides.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            slides.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            if (slidesDown)
            {
                previousSlideState = slideState;
                slideState = SlideState.DOWN;
            }
            else if (slidesUpLow)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_LOW;
            }
            else if (slidesUpHigh)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_HIGH;
            }
            else if (slidesUpManual)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_MANUAL;
            }
            else if (slidesDownManual)
            {
                previousSlideState = slideState;
                slideState = SlideState.DOWN_MANUAL;
            }
        }
        else if (slideState == SlideState.UP_HIGH)
        {
            if (!(slides.isBusy()) && (int)(slides.getCurrentPosition()) != 0)
            {
                slideState = SlideState.HIGH;
            }
            else if (!(slides.isBusy()))
            {
                double distance = 0;
                if (previousSlideState == SlideState.INITIAL) {distance = HIGH_SET_LINE;}
                else if (previousSlideState == SlideState.LOW) {distance = HIGH_SET_LINE - LOW_SET_LINE;}
                else if (previousSlideState == SlideState.MEDIUM) {distance = HIGH_SET_LINE - MEDIUM_SET_LINE;}
                slides.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                slides.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                slidePos = (int)((distance / (SLIDE_HUB_DIAMETER * Math.PI)) * TICKS_PER_REV_312);
                slides.setTargetPosition(slidePos);
                slides.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

                // set power for moving up
                if (distance > 0) {slides.setPower(slideUpAutoPow);}
                else {slides.setPower(slideDownAutoPow);}
            }
        }
        else if (slideState == SlideState.HIGH)
        {
            slides.setPower(0);
            slides.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            slides.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            if (slidesDown)
            {
                previousSlideState = slideState;
                slideState = SlideState.DOWN;
            }
            else if (slidesUpLow)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_LOW;
            }
            else if (slidesUpMid)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_MEDIUM;
            }
            else if (slidesUpManual)
            {
                previousSlideState = slideState;
                slideState = SlideState.UP_MANUAL;
            }
            else if (slidesDownManual)
            {
                previousSlideState = slideState;
                slideState = SlideState.DOWN_MANUAL;
            }
        }
        else if (slideState == SlideState.DOWN)
        {
            if (slideTouchingSensor) {slideState = SlideState.INITIAL;}
            else {slides.setPower(slideDownAutoPow);}
        }
        else if (slideState == SlideState.UP_MANUAL)
        {
            if (!(slidesUpManual)) {slideState = SlideState.STATIONARY;}
            else if (slidesDown) {slideState = SlideState.DOWN;}
            else if (slidesDownManual) {slideState = SlideState.DOWN_MANUAL;}
            else {slides.setPower(slideUpManualPow);}
        }
        else if (slideState == SlideState.DOWN_MANUAL)
        {
            if (!(slidesDownManual)) {slideState = SlideState.STATIONARY;}
            else if (slidesDown) {slideState = SlideState.DOWN;}
            else if (slidesUpManual) {slideState = SlideState.UP_MANUAL;}
            else {slides.setPower(slideDownManualPow);}
        }


        // Arm FSM
        if (armState == ArmState.INTAKE)
        {
            telemetry.addData("State", "Intake");
            telemetry.update();
            arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            if (armToOuttake)
            {
                arm.setPower(0);
                previousArmState = armState;
                arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                armState = ArmState.TO_OUTTAKE;
            }
            else if (armToAirplane)
            {
                arm.setPower(0);
                previousArmState = armState;
                arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                armState = ArmState.TO_AIRPLANE;
            }
            else {arm.setPower(0.0015);}
        }
        else if (armState == ArmState.TO_OUTTAKE)
        {
            telemetry.addData("State", "To Outtake");
            telemetry.update();
            if (!(arm.isBusy()) && (int)(arm.getCurrentPosition()) != 0)
            {
                arm.setPower(0);
                armState = ArmState.OUTTAKE;
            }
            else if (!(arm.isBusy()))
            {
                arm.setPower(0);
                double rotation = 0;
                if (previousArmState == ArmState.INTAKE) {rotation = ARM_OUTTAKE_ROTATION - ARM_INTAKE_ROTATION;}
                else if (previousArmState == ArmState.AIRPLANE) {rotation = ARM_OUTTAKE_ROTATION - ARM_AIRPLANE_ROTATION;}
                arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                armPos = (int)(rotation * TICKS_PER_REV_43);
                arm.setTargetPosition(armPos);
                arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

                // set power for moving up
                arm.setPower(armAutoPow);
            }
        }
        else if (armState == ArmState.OUTTAKE)
        {
            telemetry.addData("State", "Outtake");
            telemetry.update();
            arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            if (armToIntake)
            {
                arm.setPower(0);
                previousArmState = armState;
                arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                armState = ArmState.TO_INTAKE;
            }
            else if (armToAirplane)
            {
                arm.setPower(0);
                previousArmState = armState;
                arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                armState = ArmState.TO_AIRPLANE;
            }
            else {arm.setPower(-0.0005);}
        }
        else if (armState == ArmState.TO_AIRPLANE)
        {
            telemetry.addData("State", "To Airplane");
            telemetry.update();
            if (!(arm.isBusy()) && (int)(arm.getCurrentPosition()) != 0)
            {
                arm.setPower(0);
                armState = ArmState.AIRPLANE;
            }
            else if (!(arm.isBusy()))
            {
                arm.setPower(0);
                double rotation = 0;
                if (previousArmState == ArmState.INTAKE) {rotation = ARM_AIRPLANE_ROTATION - ARM_INTAKE_ROTATION;}
                else if (previousArmState == ArmState.OUTTAKE) {rotation = ARM_AIRPLANE_ROTATION - ARM_OUTTAKE_ROTATION;}
                arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                armPos = (int)(rotation * TICKS_PER_REV_43);
                arm.setTargetPosition(armPos);
                arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

                // set power for moving up
                arm.setPower(armAutoPow);
            }
        }
        else if (armState == ArmState.AIRPLANE)
        {
            telemetry.addData("State", "Airplane");
            telemetry.update();
            arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            if (armToIntake)
            {
                arm.setPower(0);
                previousArmState = armState;
                arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                armState = ArmState.TO_INTAKE;
            }
            else if (armToOuttake)
            {
                arm.setPower(0);
                previousArmState = armState;
                arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                armState = ArmState.TO_OUTTAKE;
            }
            else {arm.setPower(-0.004);}
        }
        else if (armState == ArmState.TO_INTAKE)
        {
            telemetry.addData("State", "To Intake");
            telemetry.update();
            if (!(arm.isBusy()) && (int) (arm.getCurrentPosition()) != 0)
            {
                arm.setPower(0);
                armState = ArmState.INTAKE;
            }
            else if (!(arm.isBusy()))
            {
                arm.setPower(0);
                double rotation = 0;
                if (previousArmState == ArmState.AIRPLANE) {
                    rotation = ARM_INTAKE_ROTATION - ARM_AIRPLANE_ROTATION;
                } else if (previousArmState == ArmState.OUTTAKE) {
                    rotation = ARM_INTAKE_ROTATION - ARM_OUTTAKE_ROTATION;
                }
                arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                armPos = (int) (rotation * TICKS_PER_REV_43);
                arm.setTargetPosition(armPos);
                arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

                // set power for moving up
                arm.setPower(armAutoPow);
            }
        }

        // Claw Turner FSM
        if (turnerState == TurnerState.INTAKE)
        {
            telemetry.addData("Claw Turner State", "Intake");
            telemetry.update();
            if (turnClawForDownOuttake) {turnerState = TurnerState.TO_OUTTAKE_DOWN;}
            else if (turnClawForUpOuttake) {turnerState = TurnerState.TO_OUTTAKE_UP;}
        }
        else if (turnerState == TurnerState.TO_OUTTAKE_DOWN)
        {
            telemetry.addData("Claw Turner State", "To Outtake Down");
            telemetry.update();
            rightClawTurner.setPosition(RIGHT_CLAW_TURNER_OUTTAKE_DOWN);
            leftClawTurner.setPosition(LEFT_CLAW_TURNER_OUTTAKE_DOWN);
            turnerState = TurnerState.OUTTAKE_DOWN;
        }
        else if (turnerState == TurnerState.OUTTAKE_DOWN)
        {
            telemetry.addData("Claw Turner State", "Outtake Down");
            telemetry.update();
            if (turnClawForIntake) {turnerState = TurnerState.TO_INTAKE;}
            else if (turnClawForUpOuttake) {turnerState = TurnerState.TO_OUTTAKE_UP;}
        }
        else if (turnerState == TurnerState.TO_OUTTAKE_UP)
        {
            telemetry.addData("Claw Turner State", "To Outtake Up");
            telemetry.update();
            leftClawTurner.setPosition(LEFT_CLAW_TURNER_OUTTAKE_UP);
            rightClawTurner.setPosition(RIGHT_CLAW_TURNER_OUTTAKE_UP);
            turnerState = TurnerState.OUTTAKE_UP;
        }
        else if (turnerState == TurnerState.OUTTAKE_UP)
        {
            telemetry.addData("Claw Turner State", "Outtake Up");
            telemetry.update();
            if (turnClawForIntake) {turnerState = TurnerState.TO_INTAKE;}
            else if (turnClawForDownOuttake) {turnerState = TurnerState.TO_OUTTAKE_DOWN;}
        }
        else if (turnerState == TurnerState.TO_INTAKE)
        {
            telemetry.addData("Claw Turner State", "To Intake");
            telemetry.update();
            leftClawTurner.setPosition(LEFT_CLAW_TURNER_HOME);
            rightClawTurner.setPosition(RIGHT_CLAW_TURNER_HOME);
            turnerState = TurnerState.INTAKE;
        }
        else if (turnerState == TurnerState.INITIAL)
        {
            telemetry.addData("Claw Turner State", "Initial");
            telemetry.update();
            leftClawTurner.setPosition(LEFT_CLAW_TURNER_INITIAL);
            rightClawTurner.setPosition(RIGHT_CLAW_TURNER_INITIAL);
            if (turnClawForIntake) {turnerState = TurnerState.TO_INTAKE;}
            if (turnClawForDownOuttake) {turnerState = TurnerState.TO_OUTTAKE_DOWN;}
        }


        // Left Claw Opener FSM
        if (leftClawState == ClawState.CLOSED && openLeftClaw) {leftClawState = ClawState.TO_OPEN;}
        else if (leftClawState == ClawState.TO_OPEN)
        {
            leftClawOpener.setPosition(LEFT_CLAW_OPENER_RANGE);
            leftClawState = ClawState.OPEN;
        }
        else if (leftClawState == ClawState.OPEN && closeLeftClaw) {leftClawState = ClawState.TO_CLOSED;}
        else if (leftClawState == ClawState.TO_CLOSED)
        {
            leftClawOpener.setPosition(LEFT_CLAW_OPENER_HOME);
            leftClawState = ClawState.CLOSED;
        }


        // Right Claw Opener FSM
        if (rightClawState == ClawState.CLOSED && openRightClaw) {rightClawState = ClawState.TO_OPEN;}
        else if (rightClawState == ClawState.TO_OPEN)
        {
            rightClawOpener.setPosition(RIGHT_CLAW_OPENER_RANGE);
            rightClawState = ClawState.OPEN;
        }
        else if (rightClawState == ClawState.OPEN && closeRightClaw) {rightClawState = ClawState.TO_CLOSED;}
        else if (rightClawState == ClawState.TO_CLOSED)
        {
            rightClawOpener.setPosition(RIGHT_CLAW_OPENER_HOME);
            rightClawState = ClawState.CLOSED;
        }


        // Airplane FSM
        if(airplaneState == AirplaneState.INITIAL)
        {
            if (launchAirplane) {airplaneState = AirplaneState.UNLOCKING;}
        }
        else if (airplaneState == AirplaneState.UNLOCKING)
        {
            if (Math.abs(planeLock.getPosition() - PLANE_LOCK_RANGE) < DOUBLE_EQUALITY_THRESHOLD) {airplaneState = AirplaneState.LAUNCHING;}
            else {planeLock.setPosition(PLANE_LOCK_RANGE);}
        }
        else if (airplaneState == AirplaneState.LAUNCHING)
        {
            if (Math.abs(planeLauncher.getPosition() - PLANE_LAUNCHER_RANGE) < DOUBLE_EQUALITY_THRESHOLD)
            {
                airplaneState = AirplaneState.LAUNCHED;
                timer.reset();
            }
            else {planeLauncher.setPosition(PLANE_LAUNCHER_RANGE);}
        }
        else if (airplaneState == AirplaneState.LAUNCHED)
        {
            if ((int)(timer.milliseconds()) >= LAUNCHER_DELAY) {airplaneState = AirplaneState.RETRACTING;}
        }
        else if (airplaneState == AirplaneState.RETRACTING)
        {
            if (Math.abs(planeLauncher.getPosition() - PLANE_LAUNCHER_HOME) < DOUBLE_EQUALITY_THRESHOLD) {airplaneState = AirplaneState.LOCKING;}
            else {planeLauncher.setPosition(PLANE_LAUNCHER_HOME);}
        }
        else if (airplaneState == AirplaneState.LOCKING)
        {
            if (Math.abs(planeLock.getPosition() - PLANE_LOCK_HOME) < DOUBLE_EQUALITY_THRESHOLD) {airplaneState = AirplaneState.INITIAL;}
            else {planeLock.setPosition(PLANE_LOCK_HOME);}
        }


        // Hanging
        if (hangUp)
        {
            leftHanger.setPower(1);
            rightHanger.setPower(1);
        }
        else if (hangDown)
        {
            leftHanger.setPower(-1);
            rightHanger.setPower(-1);
        }
        else
        {
            leftHanger.setPower(0);
            rightHanger.setPower(0);
        }

        // Arm
        /*if (armUpManual)
        {
            arm.setPower(armManualPow);
        }
        else if (armDownManual)
        {
            arm.setPower(-1*armManualPow);
        }
        else if (holdArm){
            arm.setPower(0.01);
        }
        else
        {
            arm.setPower(0);
        }*/
    }
}
