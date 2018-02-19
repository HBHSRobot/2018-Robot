package org.usfirst.frc.team5966.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends Subsystem {
	
	Spark motorLeft = new Spark(6);
	Spark motorRight = new Spark(7);
	
	SpeedControllerGroup grabberMotors = new SpeedControllerGroup(motorLeft, motorRight);

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void grabberForwards(double speed)
    {
    	grabberMotors.set(speed);
    }
    
    public void grabberBackwards(double speed)
    {
    	grabberMotors.set(-1 * speed);
    }
}

