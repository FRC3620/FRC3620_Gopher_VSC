package org.usfirst.frc.team3620.robot.subsystems;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

public class AirMaster {
    Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	BarrelPrototype whoIsUsingIt = null;
	
	public boolean reserve(BarrelPrototype b) {
		if(whoIsUsingIt == null){
			whoIsUsingIt = b;
			logger.info("{} reserved the air supply", b.barrelName);
			return true;
		}
		else{
			return false;
		}
	}
	public void free (BarrelPrototype b) {
		whoIsUsingIt = null;
        logger.info("{} freed the air supply", b.barrelName);
	}

}
