package org.usfirst.frc.team5966.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {
	
	VictorSP liftMotorLeft= new VictorSP(4);
	VictorSP liftMotorRight = new VictorSP(5);
	SpeedControllerGroup liftMotors = new SpeedControllerGroup(liftMotorLeft, liftMotorRight);
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void liftUp(double speed)
    {
    	liftMotors.set(speed);
    }
    
    public void liftDown(double speed)
    {
    	liftMotors.set(-1 * speed);
    }
    
    public void liftStop()
    {
    	liftMotors.stopMotor();
    }
}

