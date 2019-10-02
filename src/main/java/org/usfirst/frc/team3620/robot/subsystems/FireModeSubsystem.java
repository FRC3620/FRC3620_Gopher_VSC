package org.usfirst.frc.team3620.robot.subsystems;

import org.usfirst.frc.team3620.robot.*;
import org.usfirst.frc.team3620.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class FireModeSubsystem extends Subsystem {

    public void activate() {
        new FireModeCommand(Robot.oi.driverJoystick).start();
    }
    

    @Override
    protected void initDefaultCommand() {
        
    }
}