package org.usfirst.frc.team3620.robot.subsystems;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.RobotMode;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
    Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

    public final int NUMBER_OF_BARRELS = 2;

    AirMaster fillMaster = new AirMaster();
    BarrelPrototype[] barrels = new BarrelPrototype[NUMBER_OF_BARRELS];

    public ShooterSubsystem() {
        super();
        init();
    }

    public ShooterSubsystem(String name) {
        super(name);
        init();
    }

    @SuppressWarnings("unused")
	void init() {
        barrels[0] = new BarrelPrototype(RobotMap.fillValve1, RobotMap.tankValve1,
                RobotMap.shotValve1, RobotMap.pressureSensor1, fillMaster, "b1");
        if (NUMBER_OF_BARRELS > 1) {
            barrels[1] = new BarrelPrototype(RobotMap.fillValve2, RobotMap.tankValve2,
                    RobotMap.shotValve2, RobotMap.pressureSensor2, fillMaster, "b2");
        }
        if (NUMBER_OF_BARRELS > 2) {
            barrels[2] = new BarrelPrototype(RobotMap.fillValve3, RobotMap.tankValve3,
                    RobotMap.shotValve3, RobotMap.pressureSensor3, fillMaster, "b3");
        }
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void startup() {
        for (int i = 0; i < NUMBER_OF_BARRELS; i++) {
            barrels[i].startup();
        }
    }

    public void allStartPeriodic(RobotMode currentMode) {
        for (int i = 0; i < NUMBER_OF_BARRELS; i++) {
            barrels[i].makeTheBarrelWork();
        }
        if (currentMode == RobotMode.TELEOP || currentMode == RobotMode.AUTONOMOUS) {
            setRelayValue(getCompressorSwitch() ? Relay.Value.kOn : Relay.Value.kOff);
        } else if (currentMode == RobotMode.TEST){
        	// let the test dashboard
        } else {
            setRelayValue(Relay.Value.kOff);
        }
    }
    
    boolean getCompressorSwitch() {
    	return !RobotMap.compressorSwitch.get();
    }
    
    Relay.Value lastValue = null;
    public void setRelayValue(Relay.Value relayValue) {
        if (lastValue != relayValue) {
            logger.info("Turning compressor to {}", relayValue);
            lastValue = relayValue;
        }
        RobotMap.compressorRelay.set(relayValue);
    }

    public void allInit(RobotMode newRobotMode) {
        for (int i = 0; i < NUMBER_OF_BARRELS; i++) {
            barrels[i].allInit(newRobotMode);
        }
        if (newRobotMode == RobotMode.TELEOP || newRobotMode == RobotMode.AUTONOMOUS) {
            RobotMap.valveMaster.set(Value.kOn);
        } else {
            RobotMap.valveMaster.set(Value.kOff);
        }
    }
    
    public boolean requestFill() {
        boolean rv = false;
        for (int i = 0; i < NUMBER_OF_BARRELS; i++) {
            if (barrels[i].readyToFill) {
                barrels[i].requestFill();
                rv = true;
                break;
            }
        }
        return rv;
    }

    public boolean requestShot() {
        boolean rv = false;
        for (int i = 0; i < NUMBER_OF_BARRELS; i++) {
            if (barrels[i].readyToShoot) {
                barrels[i].requestShot();
                rv = true;
                break;
            }
        }
        return rv;
    }
    
    public boolean requestFill(int i) {
        if (i <= NUMBER_OF_BARRELS && barrels[i].readyToFill) {
            barrels[i].requestFill();
            return true;
        }
        return false;
    }

    public boolean requestShot(int i) {
        if (i <= NUMBER_OF_BARRELS && barrels[i].readyToShoot) {
            barrels[i].requestShot();
            return true;
        }
        return false;
    }
}
