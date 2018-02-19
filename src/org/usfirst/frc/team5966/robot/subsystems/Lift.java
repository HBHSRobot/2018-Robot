package org.usfirst.frc.team5966.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {
	
	VictorSP liftMotorLeft = new VictorSP(4);
	VictorSP liftMotorRight = new VictorSP(5);
	SpeedControllerGroup liftMotors = new SpeedControllerGroup(liftMotorLeft, liftMotorRight);
	Encoder motorEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	double position = 0;
	
	public Lift()
	{
		super();
		motorEncoder.reset();
		motorEncoder.setDistancePerPulse(2048);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setLiftPosition(double positionToGoTo)
    {
    	if (position - positionToGoTo > 0)
    	{
    		liftDownDistance(position - positionToGoTo);
    	}
    	else
    	{
    		liftUpDistance(position - positionToGoTo);
    	}
    }
    
    public void liftUpDistance(double distance)
    {
    	motorEncoder.reset();
    	while (motorEncoder.getDistance() < distance)
    	{
    		liftMotors.set(0.5);
    	}
    	liftStop();
    }
    
    public void liftDownDistance(double distance)
    {
    	motorEncoder.reset();
    	while (motorEncoder.getDistance() < -1 * distance)
    	{
    		liftMotors.set(-0.5);
    	}
    	liftStop();
    }
    
    public void liftUpSpeed(double speed)
    {
    	motorEncoder.reset();
    	liftMotors.set(speed);
    }
    
    public void liftDownSpeed(double speed)
    {
    	motorEncoder.reset();
    	liftMotors.set(-1 * speed);
    }
    
    public void liftStop()
    {
    	position += motorEncoder.getDistance();
    	liftMotors.stopMotor();
    }
}

