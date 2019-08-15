package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LogCommand extends Command {
    String s;
    Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

    public LogCommand(String s) {
        this.s = s;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        logger.info("command {} started", s);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        logger.info("command {} ended", s);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        logger.info("command {} interrupted", s);
    }
}
