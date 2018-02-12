package org.usfirst.frc.team5966.robot.commands;

import org.usfirst.frc.team5966.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command{

	int timer;
	boolean leftPosition;
	final double BASE_SPEED = -0.32;
	//double speed;
	DriveTrain drivetrain = new DriveTrain();
	boolean forwards, backwards;
	
	public AutoDrive()
	{
		requires(drivetrain);
	}

	protected void initialize()
	{
		
	}
	
	protected void execute()
	{
		drivetrain.forwardDrive(BASE_SPEED, 0);
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
