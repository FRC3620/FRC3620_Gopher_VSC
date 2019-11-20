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

    /**Whether or not the pad is accepting firing inputs */
    private boolean firing = false;
    /**Timer tracking the window to enter firing inputs */
    private Timer time = new Timer();
    /**Amount of time, in seconds, that the firing window is open */
    private double firingTime = 6.0;
    /**0 is left, 1 is right*/
    private int barrel = 0;
    /**Number of buttons pressed in firing sequence*/
    private int numPressed = 0;
    /**Required number of presses to fire*/
    final int MAX_PRESSES = 16;
    
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

    /** Opens the window for entering firing inputs
     * @param barrel Which barrel the bot is firing from. 0 is left, 1 is right*/
    void startFire(int barrel) {
        this.barrel = barrel;
        numPressed = 0;
        firing = true;
        time.start();
        System.out.println("Starting firing pattern! Press start or select to interrupt.");
    }

    /**Checks the entered inputs for having reached the maximum, then fires and ends firing mode
     * @return Whether or not to fire the barrel
     */
    boolean checkFire() {
        if (time.get() < firingTime) {
            if (joystick.getRawButtonReleased(1)) {numPressed++; System.out.println("Buttons pressed in current sequence: " + numPressed);}
            if (joystick.getRawButtonReleased(2)) {numPressed++; System.out.println("Buttons pressed in current sequence: " + numPressed);}
            if (joystick.getRawButtonReleased(3)) {numPressed++; System.out.println("Buttons pressed in current sequence: " + numPressed);}
            if (joystick.getRawButtonReleased(4)) {numPressed++; System.out.println("Buttons pressed in current sequence: " + numPressed);}
            if (joystick.getRawButtonReleased(5)) {numPressed++; System.out.println("Buttons pressed in current sequence: " + numPressed);}
            if (joystick.getRawButtonReleased(6)) {numPressed++; System.out.println("Buttons pressed in current sequence: " + numPressed);}
            if (joystick.getRawButtonReleased(7)) {numPressed++; System.out.println("Buttons pressed in current sequence: " + numPressed);}
            if (joystick.getRawButtonReleased(8)) {numPressed++; System.out.println("Buttons pressed in current sequence: " + numPressed);}

            if (joystick.getRawButtonReleased(9)) {System.out.println("Interrupting firing sequence... Exiting to fire mode"); endFire();}
            if (joystick.getRawButtonReleased(10)) {System.out.println("Interrupting firing sequence... Exiting to previous drive mode"); end();}

            if (numPressed >= MAX_PRESSES) {
                endFire();
                return true;
            }
        }
        else {
            System.out.println("Expiring firing sequence...");
            endFire();
        }
        return false;
    }

    /**Closes the window for enter firing inputs */
    void endFire() {
        time.stop();
        time.reset();
        numPressed = 0;
        if (firing) System.out.println("Firing sequence ended!");
        firing = false;
    }

    /**Fires the selected barrel */
    void fire() {
        if (barrel == 0) fill0.start();
        else if (barrel == 1) fill1.start();
        System.out.println("Firing barrel " + barrel);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (!firing) {
            if (joystick.getRawButton(3)) lidUp.start(); //up
            else lidUp.cancel();
            if (joystick.getRawButton(2)) lidDown.start(); //down
            else lidDown.cancel();

            if (joystick.getRawButton(4)) startFire(1); //right
            if (joystick.getRawButton(1)) startFire(0); //left
        }
        else {
            if (checkFire()) fire();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.driveMode == RobotDriveMode.FIRING) return false;
        else return true;
    }

    // Called once after isFinished returns true
    protected void end() {
        endFire();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}