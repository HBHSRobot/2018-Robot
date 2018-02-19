package org.usfirst.frc.team5966.robot.commands;

import org.usfirst.frc.team5966.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

public class DriveForwards extends Command{

	final double BASE_SPEED = -0.32;
	DriveTrain drivetrain = new DriveTrain();
	private boolean isAutonomous;
	private double speed, rotation;
	
	public DriveForwards(boolean isAutonomous)
	{
		super("DriveForwards");
		requires(drivetrain);
		this.isAutonomous = isAutonomous;
		this.speed = 0;
		this.rotation = 0;
	}

	public void setSpeed(double speed)
    {
    	this.speed = speed;
    }
    
    public void setRotation(double rotation)
    {
    	this.rotation = rotation;
    }
	
    public void changeAutonomousMode()
    {
    	isAutonomous = !isAutonomous;
    }
    
	protected void initialize()
	{
		
	}
	
	protected void execute()
	{
		if (isAutonomous)
		{
			drivetrain.forwardDrive(BASE_SPEED, 0);
		}			
		else
		{
			drivetrain.forwardDrive(speed, rotation);
		}
	}
	
	protected boolean isFinished() 
	{
		return false;
	}
	
	// Called once after isFinished returns true
    protected void end() 
    {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	
    }
	
}
