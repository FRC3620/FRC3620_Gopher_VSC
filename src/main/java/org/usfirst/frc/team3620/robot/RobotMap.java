package org.usfirst.frc.team3620.robot;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
    public static TalonSRX subsystem1TalonSRX1;

    public static PWMSpeedController driveSpeedController0;
    public static PWMSpeedController driveSpeedController1;
    
    public static DifferentialDrive robotDrive;

    public static Relay compressorRelay;
    public static DigitalInput compressorSwitch;

    public static AnalogInput lidSensor;
    public static AnalogInput pressureSensor1;
    public static AnalogInput pressureSensor2;
    public static AnalogInput pressureSensor3;

    public static Relay valveMaster;
    public static DigitalOutput fillValve1;
    public static DigitalOutput tankValve1;
    public static DigitalOutput shotValve1;
    public static DigitalOutput fillValve2;
    public static DigitalOutput tankValve2;
    public static DigitalOutput shotValve2;
    public static DigitalOutput fillValve3;
    public static DigitalOutput tankValve3;
    public static DigitalOutput shotValve3;

    public static void init() {

        subsystem1TalonSRX1 = new TalonSRX(1);
        //subsystem1TalonSRX1.setName("Subsystem 1", "TalonSRX 1");
        //LiveWindow.add(subsystem1TalonSRX1);

        driveSpeedController0 = new Talon(0);
        driveSpeedController0.setName("Drive", "Speed Controller 0");
        LiveWindow.add(driveSpeedController0);

        driveSpeedController1 = new Talon(1);
        driveSpeedController1.setName("Drive", "Speed Controller 1");
        LiveWindow.add(driveSpeedController1);

        robotDrive = new DifferentialDrive(driveSpeedController0, driveSpeedController1);

        robotDrive.setSafetyEnabled(false);
        robotDrive.setExpiration(0.1);
        //robotDrive.setSensitivity(0.5);
        robotDrive.setMaxOutput(1.0);
        // robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);

        /** TODO - what do we need to do to get these on the dashboard?*/
          compressorRelay = new Relay(0, Direction.kForward);
          LiveWindow.addActuator("shooterSubsystem", "compressorRelay", compressorRelay);
          
                  compressorSwitch = new DigitalInput(0);
          LiveWindow.addSensor("shooterSubsystem", "compressorSwitch", compressorSwitch);

          lidSensor = new AnalogInput(0);
          LiveWindow.addSensor("shooterSubsystem", "lidSensor", lidSensor);

          pressureSensor1 = new AnalogInput(1);
          LiveWindow.addSensor("shooterSubsystem", "PressureSensor1", pressureSensor1);

        pressureSensor2 = new AnalogInput(2);
          LiveWindow.addSensor("shooterSubsystem", "PressureSensor2", pressureSensor2);

          pressureSensor3 = new AnalogInput(3);
          LiveWindow.addSensor("shooterSubsystem", "PressureSensor3", pressureSensor3);

          valveMaster = new Relay(1, Direction.kForward);
          LiveWindow.addActuator("shooterSubsystem", "valveMaster", valveMaster);

          fillValve1 = new DigitalOutput(1);
          LiveWindow.addActuator("shooterSubsystem", "fillValve1", fillValve1);

          tankValve1 = new DigitalOutput(2);
          LiveWindow.addActuator("shooterSubsystem", "tankValve1", tankValve1);

          shotValve1 = new DigitalOutput(3);
          LiveWindow.addActuator("shooterSubsystem", "shotValve1", shotValve1);

          fillValve2 = new DigitalOutput(4);
          LiveWindow.addActuator("shooterSubsystem", "fillValve2", fillValve2);

          tankValve2 = new DigitalOutput(5);
          LiveWindow.addActuator("shooterSubsystem", "tankValve2", tankValve2);

          shotValve2 = new DigitalOutput(6);
          LiveWindow.addActuator("shooterSubsystem", "shotValve2", shotValve2);

          fillValve3 = new DigitalOutput(7);
          LiveWindow.addActuator("shooterSubsystem", "fillValve3", fillValve3);

         tankValve3 = new DigitalOutput(8);
          LiveWindow.addActuator("shooterSubsystem", "tankValve3", tankValve3);

          shotValve3 = new DigitalOutput(9);
          LiveWindow.addActuator("shooterSubsystem", "shotValve3", shotValve3);
    }
}
