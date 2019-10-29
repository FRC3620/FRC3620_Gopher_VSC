package org.usfirst.frc.team3620.robot.commands;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotDriveMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class FireModeCommand extends Command {
    private Joystick joystick;

    private ManualLidDownCommand lidDown;
    private ManualLidUpCommand lidUp;

    private RequestFillCommand0 fill0;
    private RequestFillCommand1 fill1;

    private RequestShotCommand shot;

    private String[] CORRECT_SEQUENCE = {"up", "up", "down", "down", "left", "right", "left", "right", "X", "O"};
    private List<String> patternSequence = new ArrayList<String>();
    private boolean patternActive = false;
    private Timer time;
    
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

    void startFirePattern() {
        patternSequence.clear();
        patternActive = true;
        time.start();
    }

    void endFirePattern() {
        shot.cancel();
        patternSequence.clear();
        patternActive = false;
        time.stop();
        time.reset();
        System.out.println("Firing pattern ended!");
    }

    void checkFirePattern() {
        if (patternActive && time.get() <= 10.0) {
        //check for up up down down left right left right X O start
            if (patternSequence.size() == 10) {
                for (int i = 0; i< 10; i++) {
                    if (patternSequence.remove(0).equals(CORRECT_SEQUENCE[i])) return;
                    else {
                        endFirePattern();
                        break;
                    }
                }
                shot.start();
                endFirePattern();
            }
        }
        else endFirePattern();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (!patternActive) {
            if (joystick.getRawButton(3)) lidUp.start(); //up
            else lidUp.cancel();
            if (joystick.getRawButton(2)) lidDown.start(); //down
            else lidDown.cancel();

            if (joystick.getRawButton(4)) fill1.start(); //right
            else fill1.cancel();
            if (joystick.getRawButton(1)) fill0.start(); //left
            else fill0.cancel();

            if ((joystick.getRawButton(5) && joystick.getRawButton(6))) startFirePattern();
        }

        if (joystick.getRawButton(3)) patternSequence.add("up");
        else if (joystick.getRawButton(2)) patternSequence.add("down");
        else if (joystick.getRawButton(4)) patternSequence.add("right");
        else if (joystick.getRawButton(1)) patternSequence.add("left");
        else if (joystick.getRawButton(7)) patternSequence.add("X");
        else if (joystick.getRawButton(8)) patternSequence.add("O");
        else if (joystick.getRawButton(5) || joystick.getRawButton(6) || joystick.getRawButton(9) || joystick.getRawButton(10)) endFirePattern();

        checkFirePattern();
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