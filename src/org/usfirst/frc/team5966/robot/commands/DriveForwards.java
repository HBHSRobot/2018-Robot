package org.usfirst.frc.team5966.robot.commands;

import org.usfirst.frc.team5966.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

public class DriveForwards extends Command{

	final double BASE_SPEED = -0.32;
	DriveTrain drivetrain = new DriveTrain();
	private boolean isAutonomous;
	
	public DriveForwards(boolean isAutonomous)
	{
		requires(drivetrain);
		this.isAutonomous = isAutonomous;
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
			drivetrain.forwardDrive(BASE_SPEED, 0);
		else
			drivetrain.forwardDrive(0, 0);
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
