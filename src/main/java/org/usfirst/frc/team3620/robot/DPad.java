package org.usfirst.frc.team3620.robot;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class DPad {
    Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
    
    Joystick joystick;

    public DPad(Joystick _joystick) {
        super();
        joystick = _joystick;
    }

    Button down, up, left, right;

    public Button getDown() {
        if (down == null) {
            down = new Down();
        }
        return down;
    }

    public Button getUp() {
        if (up == null) {
            up = new Up();
        }
        return up;
    }

    public class Down extends Button {
        @Override
        public boolean get() {
            int angle = joystick.getPOV(0);

            boolean pressed = false;

            // Is the DPad pushed?
            if (angle > -1) {
                if (angle > 134 && angle < 226) {
                    pressed = true;
                }
            }
            // logger.info("POV = {}, pressed (up) = {}", angle, pressed);
            return pressed;
        }
    }
    
    public class Up extends Button {
        @Override
        public boolean get() {
            int angle = joystick.getPOV(0);

            boolean pressed = false;

            // Is the DPad pushed?
            if (angle > -1) {
                if (angle > 314 || angle < 46) {
                    pressed = true;
                }
            }
            // logger.info("POV = {}, pressed (up) = {}", angle, pressed);
            return pressed;
        }
    }
}
