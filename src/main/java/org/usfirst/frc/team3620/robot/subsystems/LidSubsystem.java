// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc.team3620.robot.subsystems;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LidSubsystem extends Subsystem {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	final Double[] vals = new Double[] { 0.0, 20.0, 40.0, 60.0 };
	final int N_POSITIONS = vals.length;
	int currentPosition = 0;

	PWMTalonSRX lidTalonSRX = RobotMap.subsystem1TalonSRX1;

	public LidSubsystem() {
		super();
		// lidCANTalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		// lidCANTalon.changeControlMode(TalonControlMode.Position);
		// lidCANTalon.setPID(0.4, 0, 0);
		// lidCANTalon.setPosition(0);
		// lidCANTalon.setSetpoint(vals[0]);
		//lidTalonSRX.changeControlMode();
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void manualLidUp() {
		lidTalonSRX.set(-0.8);
	}

	public void manualLidDown() {
		if (RobotMap.lidSensor.getVoltage() < 2) {
			lidTalonSRX.set(0.6);
		} else {
			lidTalonSRX.set(0.0);
		}
	}

	public void bumpLidUp() {
		if (currentPosition < N_POSITIONS) {
			currentPosition = currentPosition + 1;
			logger.info("new lift setpoint = {}", currentPosition);
			lidTalonSRX.set(vals[currentPosition]);
		} else
			logger.info("Cannot move further up");
	}

	public void bumpLidDown() {
		if (currentPosition > 0) {
			currentPosition = currentPosition - 1;
			logger.info("new lift setpoint = {}", currentPosition);
			lidTalonSRX.set(vals[currentPosition]);
		} else
			logger.info("Cannot move further down");
	}

	public void stopMotor() {
		lidTalonSRX.set(0.0);
	}

}
