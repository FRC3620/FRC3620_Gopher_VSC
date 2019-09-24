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
	double xMove = 0.0;
	double yRotate = 0.0;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public void arcadeDriveControl (Joystick joystick) {
		//if up and not down
		if (joystick.getRawButton(3) && !joystick.getRawButton(2)) {xMove = 1.0;}
		//if down and not up
		else if (joystick.getRawButton(2) && !joystick.getRawButton(3)) {xMove = -1.0;}
		//if up and down OR not up nor down
		else {xMove = 0.0;}

		//if left and not right
		if (joystick.getRawButton(1) && !joystick.getRawButton(4)) {yRotate = -1.0;}
		//if right and not left
		else if (joystick.getRawButton(4) && !joystick.getRawButton(1)) {yRotate = 1.0;}
		//if left and right OR not left nor right
		else {yRotate = 0.0;}

		robotDrive.arcadeDrive(xMove, yRotate);
	}

	public void tankDriveControl (Joystick joystick) {
		//xMove is left, yRotate is right

		//if forward left and not backward left
		if (joystick.getRawButton(7) && !joystick.getRawButton(6)) {xMove = 1.0;}
		//if backward left and not forward left
		else if (joystick.getRawButton(6) && !joystick.getRawButton(7)) {xMove = -1.0;}
		//if forward left and backward left OR not forward left nor backward left
		else {xMove = 0.0;}

		//if forward right and not backward right
		if (joystick.getRawButton(8) && !joystick.getRawButton(5)) {yRotate = -1.0;}
		//if backward right and not forward right
		else if (joystick.getRawButton(5) && !joystick.getRawButton(8)) {yRotate = 1.0;}
		//if forward right and backward right OR not forward right nor backward right
		else {yRotate = 0.0;}

		robotDrive.tankDrive(xMove, yRotate);
	}

	public void driveControl(double move, double rotate){
		//unused, but available
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

