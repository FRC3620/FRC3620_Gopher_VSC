package org.usfirst.frc.team3620.robot.subsystems;

import org.usfirst.frc.team3620.robot.*;
import org.usfirst.frc.team3620.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
	DifferentialDrive robotDrive = RobotMap.robotDrive;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public void driveControl (Joystick joystick) {
		robotDrive.arcadeDrive(joystick.getY(), -joystick.getX());
	}

	public void driveControl(double move, double rotate){
		if(Math.abs(move) <= .2){
			move = 0;
		}
		
		if(Math.abs(rotate) <= .2){
			rotate = 0;
		}
		
		robotDrive.arcadeDrive(move, rotate);
	}
	
	public void stopDrivingNow(){
		robotDrive.stopMotor();
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new DriveCommand());
    }
    
    public void allInit(RobotMode robotMode)
    {
    }
}

