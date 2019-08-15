
package org.usfirst.frc.team3620.robot.subsystems;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.RobotMode;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BarrelPrototype {

    DigitalOutput supplyValve, tankValve, shotValve;
    AnalogInput pressureSensor;
    AirMaster fillMaster;
    String barrelName;
    
    Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

    public String getBarrelName() {
        return barrelName;
    }

    boolean fillRequested = false;
    boolean shotRequested = false;
    boolean readyToFill = false;
    boolean readyToShoot = false;

    public boolean isReadyToFill() {
        return readyToFill;
    }

    BarrelState idleState = new IdleState();
    BarrelState waitFillState = new WaitFillState();
    BarrelState seatingState = new SeatingState();
    BarrelState fillingState = new FillingState();
    BarrelState waitFireState = new WaitFireState();
    BarrelState preFireState = new PreFireState();
    BarrelState firingState = new FiringState();

    BarrelState currentBarrelState;

    public void startup() {
        currentBarrelState = idleState;
        logger.info("{}: moving to state {}", barrelName, currentBarrelState.getStateName());
        currentBarrelState.logAndEnter();
    }

    public void makeTheBarrelWork() {
        BarrelState newState = currentBarrelState.running();
        if (newState != null && newState != currentBarrelState) {
            logger.info("{}: moving to state {} from {}", barrelName, newState.getStateName(), currentBarrelState.getStateName());
            currentBarrelState.exit();
            newState.logAndEnter();
            currentBarrelState = newState;
        }
    }

    public BarrelPrototype(DigitalOutput _supplyValve, DigitalOutput _tankValve,
            DigitalOutput _shotValve, AnalogInput _pressureSensor,
            AirMaster _fillMaster, String _barrelName) {
        super();
        this.supplyValve = _supplyValve;
        this.tankValve = _tankValve;
        this.shotValve = _shotValve;
        this.pressureSensor = _pressureSensor;
        this.fillMaster = _fillMaster;
        this.barrelName = _barrelName;
        
        closeValve(supplyValve);
        closeValve(tankValve);
        closeValve(shotValve);
    }

    public void requestFill() {
        fillRequested = true;
    }

    public void requestShot() {
        shotRequested = true;
    }

    abstract class BarrelState {
        void logAndEnter() {
            SmartDashboard.putString(barrelName + " status", getStateName());
            enter();
        }
        
        void enter() {
        }

        abstract BarrelState running();

        void exit() {
        }
        
        String stateName = null;
        public String getStateName() {
            if (stateName == null) {
                stateName = getClass().getSimpleName();
            }
            return stateName;
        }
    }

    /*
     * The state 'Idle': empty tank, waiting for fill to be requested.
     * 
     * Entry conditions are either entering for the first time or after the Firing state.
     * 
     * No code is
     * run by the running() method except for possibly State related jargon.
     * 
     * Exit conditions are either automatic exit to the 'Waiting to Fill' state
     * or user input.
     */

    class IdleState extends BarrelState {
        @Override
        void enter() {
            closeValve(supplyValve);
            closeValve(tankValve);
            closeValve(shotValve);

            fillRequested = false;
            readyToFill = true;
        }

        @Override
        BarrelState running() {
            if (fillRequested) {
                return waitFillState;
            }
            return null;
        }

        @Override
        void exit() {
            readyToFill = false;
        }
    }

    /*
     * The state 'WaitFill' waits for the shared air components tpo become 
     * available.
     * 
     * Entry conditions is either automatic from 'Idle' or accomplished with user input.
     * 
     * The running() method will verify that no Barrel is currently filling.
     * 
     * Exit condition is that no other Barrel is filling.
     */
    class WaitFillState extends BarrelState {
        @Override
        void enter() {
            // probably redundant
            closeValve(supplyValve);
            closeValve(tankValve);
            closeValve(shotValve);
        }

        @Override
        BarrelState running() {
            if (fillMaster.reserve(BarrelPrototype.this)) {
                return seatingState;
            } else {
                return null;
            }
        }

        @Override
        void exit() {
        }
    }

    /*
     * The state 'SeatingValve' runs code to seat the valve before Filling.
     * 
     * The running() method runs code to seat the
     * piston for a Barrel.
     * 
     * Exit condition is automatic exit to the
     * 'Filling' state once the valve is seated.
     */
    class SeatingState extends BarrelState {
        Timer timer = new Timer();

        @Override
        void enter() {
            openValve(supplyValve);
            closeValve(tankValve);
            closeValve(shotValve);

            timer.reset();
            timer.start();
        }

        @Override
        BarrelState running() {
            if (timer.hasPeriodPassed(0.50)) {
                return fillingState;
            }
            return null;
        }

        @Override
        void exit() {
            closeValve(supplyValve);
        }
    }

    /*
     * The state 'Filling': we are filling the tank.
     * 
     * The running() method runs code to fill the tank.
     * 
     * We exit to 'WaitFire' state once filling is complete.
     */
    class FillingState extends BarrelState {
        Timer timer = new Timer();

        @Override
        void enter() {
            openValve(supplyValve);
            openValve(tankValve);
            closeValve(shotValve);

            readyToShoot = false;

            timer.reset();
            timer.start();
        }

        @Override
        BarrelState running() {
            if (timer.hasPeriodPassed(3.0)) {
                return waitFireState;
            }
            /*
             * if (pressureSensor.getVoltage() > 2.5) { return waitFireState; }
             */
            return null;
        }

        @Override
        void exit() {
            readyToShoot = true;

            closeValve(supplyValve);
            closeValve(tankValve);
            closeValve(shotValve);

            fillMaster.free(BarrelPrototype.this);
        }
    }

    /*
     * The state 'WaitFire', is the fourth state to be run. Entry condition is
     * that the Filling state is complete. The running() method will run code
     * that waits for user input. Exit condition is specific user input.
     */

    class WaitFireState extends BarrelState {
    	Timer timer = new Timer();
        @Override
        void enter() {
            // keep the tank valve open so the piston does not move
            closeValve(supplyValve);
            openValve(tankValve);
            closeValve(shotValve);

            shotRequested = false;
            readyToShoot = true;
            
            timer.reset();
            timer.start();
        }

        @Override
        BarrelState running() {
            if (timer.hasPeriodPassed(0.5)) {
                return preFireState;
            }
            return null;
        }

        @Override
        void exit() {
            readyToShoot = false;

            closeValve(supplyValve);
            closeValve(tankValve);
            closeValve(shotValve);
        }
    }
    
    class PreFireState extends BarrelState {
        Timer timer = new Timer();

        @Override
        void enter() {
            closeValve(supplyValve);
            closeValve(tankValve);
            closeValve(shotValve);
            
            timer.reset();
            timer.start();
        }

        @Override
        BarrelState running() {
            if (timer.hasPeriodPassed(0.5)) {
                return firingState;
            }
            return null;
        }

        @Override
        void exit() {
            closeValve(supplyValve);
            closeValve(tankValve);
            closeValve(shotValve);
        }
    }

    /*
     * The state 'Firing', is the last state to be run. Entry condition is exit
     * from the 'WaitFire' state The running() method runs code to fire the
     * specified Barrel Exit condition is automatic exit to the 'Idle' state
     * once firing is complete.
     */
    class FiringState extends BarrelState {
        Timer timer = new Timer();

        @Override
        void enter() {
            closeValve(supplyValve);
            closeValve(tankValve);
            openValve(shotValve);
            
            timer.reset();
            timer.start();
        }

        @Override
        BarrelState running() {
            if (timer.hasPeriodPassed(1.5)) {
                return idleState;
            }
            return null;
        }

        @Override
        void exit() {
            closeValve(supplyValve);
            closeValve(tankValve);
            closeValve(shotValve);
        }
    }

    void openValve(DigitalOutput v) {
        v.set(true);
    }

    void closeValve(DigitalOutput v) {
        v.set(false);
    }
    
    public void allInit(RobotMode newRobotMode) {
        if (newRobotMode == RobotMode.TELEOP) {
            shotRequested = false;
            fillRequested = false;
        }
    }
}
