package org.usfirst.frc.team5966.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	final double ROTATION_REDUCTION = 0.7;
	boolean reductionEnabled = true;
	
	//4 motors
	VictorSP frontLeft = new VictorSP(2);	
	VictorSP backLeft = new VictorSP(3);
	VictorSP frontRight = new VictorSP(0);
	VictorSP backRight = new VictorSP(1);
	
	//2 motor groups
	SpeedControllerGroup leftMotors = new SpeedControllerGroup(frontLeft, backLeft);
	SpeedControllerGroup rightMotors = new SpeedControllerGroup(frontRight, backRight);
	
	//1 drive train
	DifferentialDrive robotDrive = new DifferentialDrive(leftMotors, rightMotors);
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());

    }
    
    public void baseRegularDrive(double speed, double rotation)
    {
    	if (reductionEnabled)
    	{
    		robotDrive.curvatureDrive(speed, rotation * ROTATION_REDUCTION, !reductionEnabled);
    	}
    	else
    	{
    		robotDrive.curvatureDrive(speed, rotation, !reductionEnabled);
    	}
    }
    
    public void forwardDrive(double speed, double rotation)
    {
    	baseRegularDrive(speed, rotation);
    }
    
    public void reverseDrive(double speed, double rotation)
    {
    	baseRegularDrive(-1 * speed, -1 * rotation);
    }
    
    public void setReductionMode(boolean enable)
    {
    	reductionEnabled = enable;
    }
}

