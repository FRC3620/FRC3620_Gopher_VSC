package org.usfirst.frc.team3620.robot.commands;

import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotDriveMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class FireModeCommand extends Command {
    private Joystick joystick;

    private ManualLidDownCommand lidDown;
    private ManualLidUpCommand lidUp;

    private RequestFillCommand0 fill0;
    private RequestFillCommand1 fill1;

    private RequestShotCommand shot;
    
    public FireModeCommand(Joystick joystick) {
        this.joystick = joystick;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        lidDown = new ManualLidDownCommand();
        lidUp = new ManualLidUpCommand();
        fill0 = new RequestFillCommand0();
        fill1 = new RequestFillCommand1();
        shot = new RequestShotCommand();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (joystick.getRawButton(3)) lidUp.start(); //up
        else lidUp.cancel();
        if (joystick.getRawButton(2)) lidDown.start(); //down
        else lidDown.cancel();

        if (joystick.getRawButton(4)) fill1.start(); //right
        else fill1.cancel();
        if (joystick.getRawButton(1)) fill0.start(); //left
        else fill0.cancel();

        if ((joystick.getRawButton(6) && joystick.getRawButton(8)) || (joystick.getRawButton(5) && joystick.getRawButton(7))) {
            shot.start();
        } else shot.cancel();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.driveMode == RobotDriveMode.FIRING) return false;
        else return true;
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