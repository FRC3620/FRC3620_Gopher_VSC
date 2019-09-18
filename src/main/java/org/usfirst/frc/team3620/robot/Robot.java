package org.usfirst.frc.team3620.robot;

import org.usfirst.frc.team3620.robot.subsystems.*;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	
	public static LidSubsystem lidSubsystem;
	public static DriveSubsystem driveSubsystem;
    public static ShooterSubsystem shooterSubsystem;
    public static RumblerSubsystem rumblerSubsystem;

    Command autonomousCommand;
    SendableChooser chooser;
    
    // custom FRC 3620 stuff
    static RobotMode currentRobotMode = RobotMode.INIT, previousRobotMode; 
    static Logger logger;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        logger = EventLogging.getLogger(Robot.class, Level.INFO);
        
    	RobotMap.init();
    	
		driveSubsystem = new DriveSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		rumblerSubsystem = new RumblerSubsystem();
		lidSubsystem = new LidSubsystem();

        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();
        
        chooser = new SendableChooser();
        
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        
        shooterSubsystem.startup();
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit() {
        allInit(RobotMode.DISABLED);
    }
	
	public void disabledPeriodic() {
		allStartPeriodic();
		Scheduler.getInstance().run();
		allEndPeriodic();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        allInit(RobotMode.AUTONOMOUS);
        
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
	@Override
    public void autonomousPeriodic() {
		allStartPeriodic();
        Scheduler.getInstance().run();
        allEndPeriodic();
    }

	@Override
    public void teleopInit() {
	    allInit(RobotMode.TELEOP);
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
	@Override
    public void teleopPeriodic() {
    	allStartPeriodic();
        Scheduler.getInstance().run();
        allEndPeriodic();
    }
    
	@Override
	public void testInit() {
	    allInit(RobotMode.TEST);
	}

	/**
     * This function is called periodically during test mode
     */
	@Override
    public void testPeriodic() {
    	allStartPeriodic();
        LiveWindow.run();
        allEndPeriodic();
    }
    
    void allStartPeriodic() {
    	shooterSubsystem.allStartPeriodic(currentRobotMode);
    }

    void allEndPeriodic() {
    	SmartDashboard.putNumber("encoder", RobotMap.subsystem1TalonSRX1.getSensorCollection().getQuadraturePosition());
    	SmartDashboard.putNumber("p1", RobotMap.pressureSensor1.getVoltage());
    	SmartDashboard.putNumber("p2", RobotMap.pressureSensor2.getVoltage());
    }
    
    /*
     * this routine gets called whenever we change modes
     */
    void allInit(RobotMode newMode) {
        logger.info("Switching from {} to {}", currentRobotMode, newMode);
        //logger.info("isFMS {}, position {}{}", driverStation.isFMSAttached(), driverStation.getAlliance(), driverStation.getLocation());
        
        previousRobotMode = currentRobotMode;
        currentRobotMode = newMode;

        // if any subsystems need to know about mode changes, let
        // them know here.

        Robot.shooterSubsystem.allInit(newMode);
        Robot.driveSubsystem.allInit(newMode);
    }
    

}
