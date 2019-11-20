package org.usfirst.frc.team3620.robot.commands;

import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotDriveMode;
import org.usfirst.frc.team3620.robot.subsystems.FireModeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeDriveModeCommand extends Command {

    /**The robot's mode before it enters firing mode. Defaults to arcade */
    private RobotDriveMode prevMode = RobotDriveMode.ARCADE;
    /**Whether or not the bot is issued to change to or from fire mode */
    private boolean fireMode;

    public ChangeDriveModeCommand(boolean fireMode) {
        this.fireMode = fireMode;
    } 

    // Called just before this Command runs the first time
    protected void initialize() {
        //Enters the robot into firing mode
        if (fireMode && Robot.driveMode != RobotDriveMode.FIRING) {
            Robot.driveMode = RobotDriveMode.FIRING;
            Robot.fireModeSubsystem.activate();
        }

        //Shuts the robot out of firing mode
        else if (fireMode && Robot.driveMode == RobotDriveMode.FIRING) Robot.driveMode = prevMode;

        else if (!fireMode && Robot.driveMode == RobotDriveMode.FIRING) {return;}
        
        //Switches between arcade and tank drive
        if (Robot.driveMode != RobotDriveMode.FIRING) {
                if (Robot.driveMode == RobotDriveMode.ARCADE) {Robot.driveMode = RobotDriveMode.TANK;}
                else if (Robot.driveMode == RobotDriveMode.TANK) {Robot.driveMode = RobotDriveMode.ARCADE;}
                prevMode = Robot.driveMode;
        }
        System.out.println("Activating drive mode: " + Robot.driveMode);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}