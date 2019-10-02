package org.usfirst.frc.team3620.robot.commands;

import java.io.Console;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RequestShotCommand extends Command {
    Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
    RumbleCommand rumbleCommand = new RumbleCommand();

    public RequestShotCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        logger.info ("shot requested");
        if (! Robot.shooterSubsystem.requestShot(0)) {logger.info("Barrel 0 not ready");}
        if (! Robot.shooterSubsystem.requestShot(1)) {logger.info("Barrel 1 not ready");}
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
    }
}
