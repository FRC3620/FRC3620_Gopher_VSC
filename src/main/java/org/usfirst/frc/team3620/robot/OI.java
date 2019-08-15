package org.usfirst.frc.team3620.robot;

import org.usfirst.frc.team3620.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {
	
	public Joystick driverJoystick;
	
	public JoystickButton requestFill0Button, requestFill1Button;
	
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


	public OI(){
		driverJoystick = new Joystick(0);
		
		requestFill0Button = new JoystickButton(driverJoystick, 1);
        requestFill0Button.whenPressed(new RequestFillCommand0());
        requestFill1Button = new JoystickButton(driverJoystick, 2);
        requestFill1Button.whenPressed(new RequestFillCommand1());
        
//        requestShotButton = new JoystickButton(driverJoystick, 1);
//        requestShotButton.whenPressed(new RequestShotCommand());
        
        SmartDashboard.putData("LiftDownCommand", new BumpLidDownCommand());
        SmartDashboard.putData("LiftUpCommand", new BumpLidUpCommand());
        SmartDashboard.putData("RapidFireCommand", new RapidFire());
        
        DPad dpad = new DPad(driverJoystick);
        //dpad.getDown().whenPressed(new BumpLidDownCommand());
        //dpad.getUp().whenPressed(new BumpLidUpCommand());
        dpad.getDown().whileHeld(new ManualLidDownCommand());
        dpad.getUp().whileHeld(new ManualLidUpCommand());

	}
}
